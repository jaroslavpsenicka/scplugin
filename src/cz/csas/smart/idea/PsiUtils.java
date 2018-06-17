package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.json.psi.JsonElement;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang.StringUtils.strip;

public class PsiUtils {

	public static String getPath(PsiElement element) {
		StringBuilder buff = new StringBuilder();
		while (element instanceof JsonElement) {
			PsiElement text = element.getFirstChild();
			if (text instanceof JsonStringLiteral && !CompletionInitializationContext.DUMMY_IDENTIFIER.equals(text.getText())) {
				if (buff.length() > 0) buff.insert(0, "/");
				buff.insert(0, strip(text.getText(), "\""));
			}

			element = element.getParent();
		}

		buff.insert(0, "/");
		return buff.toString();
	}

	public static PsiElement findRoot(PsiElement element) {
		if (element == null) return null;
		return PsiTreeUtil.getParentOfType(element, JsonFile.class);
	}

	private static PsiElement findFirstChild(PsiElement element, String childName) {
		if (element == null) return null;
		return PsiTreeUtil.findChildrenOfType(element, JsonStringLiteral.class).stream()
			.filter(e -> childName.equals(e.getName()))
			.findFirst().orElse(null);
	}

	public static List<String> getAttributeNames(PsiElement element) {
		PsiElement attributesElement = findFirstChild(findRoot(element), "attributes");
		if (attributesElement != null) {

		}

		return Collections.emptyList();
	}
}
