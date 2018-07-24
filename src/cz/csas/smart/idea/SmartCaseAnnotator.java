package cz.csas.smart.idea;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import cz.csas.smart.idea.model.Violation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SmartCaseAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        Map<String, Violation> currentViolations = ValidationComponent.getInstance().getCurrentViolations();
        if (currentViolations != null && currentViolations.size() > 0) {
            String path = PsiUtils.getJsonPath(element);
            if (currentViolations.containsKey(path)) {
                TextRange range = new TextRange(element.getTextRange().getStartOffset(), element.getTextRange().getEndOffset());
                holder.createErrorAnnotation(range, currentViolations.get(path).getMessage());
            }
        }
    }

}
