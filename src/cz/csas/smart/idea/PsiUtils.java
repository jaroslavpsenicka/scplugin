package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.json.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import cz.csas.smart.idea.model.Completion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.strip;

public class PsiUtils {

	public static final Comparator<Completion.Value> bySeverityAndName = (first, second) -> {
		if (first == null || second == null) return 0;
		else if (!first.required() && second.required()) return -1;
		else if (first.required() && !second.required()) return 1;
		else return second.getText().compareTo(first.getText());
	};

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

	public static List<Completion.Value> getAttributes(PsiElement element, String ofType) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "attributes".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement, JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new Completion.Value(strip(jp.getValue().getText(), "\""), findAttributeType(jp)))
				.map(nt -> nt.requiredIfType(ofType))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static List<Completion.Value> getActivities(PsiElement element) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findTaskRoot(element), JsonProperty.class).stream()
			.filter(jp -> "activities".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new Completion.Value(strip(jp.getValue().getText(), "\""), null))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public static List<Completion.Value> getTasks(PsiElement element, String ofType) {
		Optional<JsonProperty> attributesProperty = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "tasks".equals(jp.getName()))
			.findFirst();
		if (attributesProperty.isPresent()) {
			JsonProperty attributesElement = attributesProperty.get();
			return PsiTreeUtil.findChildrenOfType(attributesElement.getParent(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> new Completion.Value(strip(jp.getValue().getText(), "\""), findTaskType(jp)))
				.map(val -> val.requiredIfType(ofType))
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


	public static String getEditorOfProperty(PsiElement element) {
		JsonProperty propertyNameElement = PsiTreeUtil.getParentOfType(element.getParent(), JsonProperty.class);
		JsonProperty propertiesElement = PsiTreeUtil.getParentOfType(propertyNameElement, JsonProperty.class);
		JsonProperty editorElement = PsiTreeUtil.getParentOfType(propertiesElement, JsonProperty.class);
		return PsiTreeUtil.findChildrenOfType(editorElement, JsonProperty.class).stream()
			.filter(jp -> "name".equals(jp.getName()))
			.map(jp -> strip(jp.getValue().getText(), "\""))
			.findFirst().orElse(null);
	}

	public static String getEditorOfSelector(PsiElement element) {
		JsonProperty attributeElement = PsiTreeUtil.getParentOfType(element.getParent(), JsonProperty.class);
		JsonProperty attributesElement = PsiTreeUtil.getParentOfType(attributeElement, JsonProperty.class);
		JsonProperty selectorElement = PsiTreeUtil.getParentOfType(attributesElement, JsonProperty.class);
		JsonObject fieldElement = PsiTreeUtil.getParentOfType(selectorElement, JsonObject.class);
		Optional<JsonProperty> editorElement = PsiTreeUtil.findChildrenOfType(fieldElement, JsonProperty.class).stream()
			.filter(jp -> "editor".equals(jp.getName()))
			.findFirst();
		if (editorElement.isPresent()) {
			return PsiTreeUtil.findChildrenOfType(editorElement.get().getValue(), JsonProperty.class).stream()
				.filter(jp -> "name".equals(jp.getName()))
				.map(jp -> strip(jp.getValue().getText(), "\""))
				.findFirst().orElse(null);
		}

		return null;
	}

	public static String getNameOfSelector(PsiElement element) {
		JsonProperty selectorElement = PsiTreeUtil.getParentOfType(element.getParent(), JsonProperty.class);
		return selectorElement.getName();
	}
}
