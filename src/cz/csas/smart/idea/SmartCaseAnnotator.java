package cz.csas.smart.idea;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import cz.csas.smart.idea.model.Violation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cz.csas.smart.idea.PreProcessor.IMPORT_REGEX;

public class SmartCaseAnnotator implements Annotator {

	private final Pattern pattern;

	public SmartCaseAnnotator() {
		pattern = Pattern.compile(IMPORT_REGEX);
	}


	@Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
	    annotatePreprocessingErrors(element, holder);
    	annotateServerViolations(element, holder);
    }

	private void annotatePreprocessingErrors(PsiElement element, AnnotationHolder holder) {
		if (element instanceof PsiComment) {
			PsiComment comment = (PsiComment) element;
			if (comment.getText().indexOf("@import") > 0) {
				Matcher matcher = pattern.matcher(comment.getText());
				if (matcher.find()) {
					VirtualFile parent = element.getContainingFile().getVirtualFile().getParent();
					VirtualFile refFile = parent.findFileByRelativePath(matcher.group(1));
					if (refFile == null || !refFile.exists()) {
						int start = element.getTextRange().getStartOffset() + matcher.start(1);
						int end = element.getTextRange().getStartOffset() + matcher.end(1);
						holder.createErrorAnnotation(new TextRange(start, end), "file " + matcher.group(1) + " does not exist");
					}
				}
			}
		}
	}

	private void annotateServerViolations(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
		String file = element.getContainingFile().getName();
		Map<String, Violation> currentViolations = ValidationComponent.getInstance().getViolations(file);
		if (currentViolations != null && currentViolations.size() > 0) {
		    String path = PsiUtils.getJsonPath(element);
		    if (currentViolations.containsKey(path)) {
		        TextRange range = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
		        holder.createErrorAnnotation(range, currentViolations.get(path).getMessage());
		    }
		}
	}

}
