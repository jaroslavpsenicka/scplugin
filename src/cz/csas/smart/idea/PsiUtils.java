package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.json.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.Task;
import cz.csas.smart.idea.model.Transition;
import org.apache.commons.lang.StringUtils;

import java.util.*;
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
			PsiElement text = (element instanceof JsonProperty) ?
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

	public static String getJsonPath(PsiElement element) {
		StringBuilder buff = new StringBuilder();
		while (element instanceof JsonProperty) {
			buff.insert(0, ((JsonProperty) element).getName());
			buff.insert(0, isInArray(element) ? "[" + getIndexOf(element.getParent()) + "]." : ".");
			element = PsiTreeUtil.getParentOfType(element, JsonProperty.class);
		}

		return buff.length() > 0 ? buff.toString().substring(1) : "";
	}

	private static boolean isInArray(PsiElement element) {
		return element.getParent() != null && element.getParent().getParent() != null &&
			JsonArray.class.isAssignableFrom(element.getParent().getParent().getClass());
	}

	private static Integer getIndexOf(PsiElement element) {
		List<JsonValue> values = ((JsonArray) element.getParent()).getValueList();
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) == element) {
				return i;
			}
		}

		return null;
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

	public static List<Task> getTasks(PsiElement element, String ofType) {
		Optional<JsonProperty> tasksPropOpt = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "tasks".equals(jp.getName()))
			.findFirst();
		if (tasksPropOpt.isPresent()) {
			if (tasksPropOpt.get().getValue() instanceof JsonArray) {
				return findChildren((JsonArray) tasksPropOpt.get().getValue(), JsonObject.class).stream()
					.map(a -> Arrays.stream(a.getChildren())
						.filter(o -> o instanceof JsonProperty)
						.map(jp -> (JsonProperty) jp)
						.filter(jp -> "name".equals(jp.getName()))
						.map(jp -> new Task(strip(jp.getValue().getText(), "\""), findTaskType(jp)))
						.findFirst().orElse(null))
					.filter(t -> t.getName() != null)
					.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}

	public static List<Transition> getTransitions(PsiElement element) {
		Optional<JsonProperty> tranPropOpt = PsiTreeUtil.findChildrenOfType(findRoot(element), JsonProperty.class).stream()
			.filter(jp -> "transitions".equals(jp.getName()))
			.findFirst();
		if (tranPropOpt.isPresent()) {
			if (tranPropOpt.get().getValue() instanceof JsonArray) {
				return findChildren((JsonArray) tranPropOpt.get().getValue(), JsonObject.class).stream()
					.map(o -> new Transition(findName(o), findLink(o,"from"), findLink(o, "to")))
					.filter(t -> t.getName() != null && t.getSource() != null && t.getTarget() != null)
					.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}

	private static String findName(JsonValue value) {
		return Arrays.stream(value.getChildren())
			.filter(o -> o instanceof JsonProperty)
			.map(jp -> (JsonProperty) jp)
			.filter(jp -> "name".equals(jp.getName()))
			.map(jp -> jp.getValue().getText())
			.findFirst().orElse(null);
	}

	private static String findLink(JsonValue value, String linkName) {
		Optional<JsonProperty> from = PsiTreeUtil.findChildrenOfType(value, JsonProperty.class).stream()
			.filter(jp -> linkName.equals(jp.getName()))
			.findFirst();
		if (from.isPresent()) {
			String type = PsiTreeUtil.findChildrenOfType(from.get(), JsonProperty.class).stream()
				.filter(jp -> "type".equals(jp.getName()))
				.map(jp -> StringUtils.strip(jp.getValue().getText(), "\""))
				.findFirst().orElse(null);
			if ("TASK".equals(type)) {
				return PsiTreeUtil.findChildrenOfType(from.get(), JsonProperty.class).stream()
					.filter(jp -> "name".equals(jp.getName()))
					.map(jp -> jp.getValue().getText())
					.findFirst().orElse(null);
			}

			return type;
		}

		return null;
	}

	private static List<JsonValue> findChildren(JsonArray element, Class<JsonObject> objectClass) {
		return element.getValueList().stream()
			.filter(o -> objectClass.isAssignableFrom(o.getClass()))
			.collect(Collectors.toList());
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
