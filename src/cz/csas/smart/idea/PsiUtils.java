package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.json.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import cz.csas.smart.idea.model.NameType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.strip;

public class PsiUtils {

	public static String getPath(PsiElement element) {
		StringBuilder buff = new StringBuilder();
		while (element instanceof JsonElement) {
			PsiElement text =  (element instanceof JsonProperty) ?
				((JsonProperty) element).getNameElement() : element.getFirstChild();
			if ((text instanceof JsonStringLiteral || text instanceof JsonReferenceExpression) &&
				!CompletionInitializationContext.DUMMY_IDENTIFIER.equals(text.getText())) {
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
			.filter(e -> childName.equals(e.getText()) || ("\"" + childName + "\"").equals(e.getText()))
			.findFirst().orElse(null);
	}

	public static List<NameType> getAttributes(PsiElement element) {
		PsiElement attributesElement = findFirstChild(findRoot(element), "attributes");
		if (attributesElement != null) {
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(p -> "name".equals(p.getNameElement().getText()) || "\"name\"".equals(p.getNameElement().getText()))
				.map(p -> new NameType(strip(p.getValue().getText(), "\""), findType(p)))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	private static String findType(JsonProperty property) {
		PsiElement typeElement = findFirstChild(property, "type");
		if (typeElement instanceof JsonProperty) {
			return ((JsonProperty) typeElement).getValue().getText();
		}

		return "";
	}
}
