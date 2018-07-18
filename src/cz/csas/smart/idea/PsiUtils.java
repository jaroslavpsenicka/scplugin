package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.json.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import cz.csas.smart.idea.model.NameType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.strip;

public class PsiUtils {

	public static String getPath(PsiElement element) {
		StringBuilder buff = new StringBuilder();
		while (element instanceof JsonElement) {
			PsiElement text =  (element instanceof JsonProperty) ?
				((JsonProperty) element).getNameElement() : element.getFirstChild();
			if ((text instanceof JsonStringLiteral || text instanceof JsonReferenceExpression) &&
				!CompletionInitializationContext.DUMMY_IDENTIFIER_TRIMMED.equals(text.getText())) {
				if (buff.length() > 0) buff.insert(0, "/");
				buff.insert(0, strip(text.getText(), "\""));
			}

			element = element.getParent();
		}

		buff.insert(0, "/");
		return buff.toString();
	}

	public static PsiElement findRoot(PsiElement element) {
		return PsiTreeUtil.getParentOfType(element, JsonFile.class);
	}

	public static PsiElement findTaskRoot(PsiElement element) {
		JsonProperty topmostProperty = PsiTreeUtil.getTopmostParentOfType(element, JsonProperty.class);
		return topmostProperty != null ? topmostProperty.getParent() : null;
	}

	public static List<NameType> getAttributes(PsiElement element, String ofType) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "attributes".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new NameType(strip(jp.getValue().getText(), "\""), findAttributeType(jp)))
				.filter(nt -> nt.isType(ofType))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static List<NameType> getActivities(PsiElement element) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findTaskRoot(element), JsonProperty.class).stream()
			.filter(jp -> "activities".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new NameType(strip(jp.getValue().getText(), "\""), null))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static List<NameType> getTasks(PsiElement element, String ofType) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "tasks".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new NameType(strip(jp.getValue().getText(), "\""), findTaskType(jp)))
				.filter(nt -> nt.isType(ofType))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	private static String findAttributeType(JsonProperty element) {
		return PsiTreeUtil.findChildrenOfType(element.getParent(), JsonProperty.class).stream()
			.filter(jp -> "type".equals(jp.getName()))
			.map(jp -> strip(jp.getValue().getText(), "\""))
			.findFirst().orElse(null);
	}

	private static String findTaskType(JsonProperty element) {
		Optional<String> at = PsiTreeUtil.findChildrenOfType(element.getParent(), JsonProperty.class).stream()
			.filter(jp -> "runners".equals(jp.getName()))
			.map(jp -> strip(jp.getValue().getText(), "\""))
			.findFirst();
		Optional<String> ct = PsiTreeUtil.findChildrenOfType(element.getParent(), JsonProperty.class).stream()
			.filter(jp -> "clientConfigurations".equals(jp.getName()))
			.map(jp -> strip(jp.getValue().getText(), "\""))
			.findFirst();

		return at.isPresent() ? "AT" : ct.isPresent()? "CT" : "HT";
	}


	public static String getEditor(PsiElement element) {
		JsonProperty propertyNameElement = PsiTreeUtil.getParentOfType(element.getParent(), JsonProperty.class);
		JsonProperty propertiesElement = PsiTreeUtil.getParentOfType(propertyNameElement, JsonProperty.class);
		JsonProperty editorElement = PsiTreeUtil.getParentOfType(propertiesElement, JsonProperty.class);
		return PsiTreeUtil.findChildrenOfType(editorElement, JsonProperty.class).stream()
			.filter(jp -> "name".equals(jp.getName()))
			.map(jp -> strip(jp.getValue().getText(), "\""))
			.findFirst().orElse(null);
	}
}
