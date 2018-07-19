package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.ProfileComponent;
import cz.csas.smart.idea.PsiUtils;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.EditorDef;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ValueCompletionContributor extends CompletionProvider<CompletionParameters> {

	public static final ValueCompletionContributor INSTANCE = new ValueCompletionContributor();

	@Override
	protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		String path = PsiUtils.getPath(parameters.getPosition().getParent());
		List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
		if (completions != null) {
			AtomicInteger idx = new AtomicInteger(0);
			completions.forEach(i -> getValues(parameters, i).stream()
				.sorted(PsiUtils.bySeverityAndName)
				.forEach(j -> result.addElement(PrioritizedLookupElement.withPriority(
					createElement(j), idx.getAndIncrement())))
			);
		}
	}

	@NotNull
	private LookupElement createElement(Completion.Value value) {
		return LookupElementBuilder.create(value.getText())
			.withPresentableText(value.getText())
			.withBoldness(value.required())
			.withTypeText(value.getType(), true);
	}

	private List<Completion.Value> getValues(CompletionParameters parameters, Completion.Value value) {
		try {
			if (Completion.Value.ATTRIBUTE_NAME.equalsIgnoreCase(value.getType())) {
				return PsiUtils.getAttributes(parameters.getPosition(), value.getOf());
			} else if (Completion.Value.ACTIVITY_NAME.equalsIgnoreCase(value.getType())) {
				return PsiUtils.getActivities(parameters.getPosition());
			} else if (Completion.Value.TASK_NAME.equalsIgnoreCase(value.getType())) {
				return PsiUtils.getTasks(parameters.getPosition(), value.getOf());
			} else if (Completion.Value.USER_NAME.equalsIgnoreCase(value.getType())) {
				return getCurrentUsername();
			} else if (Completion.Value.CURRENT_TIME.equalsIgnoreCase(value.getType())) {
				return getCurrentTime();
			} else if (Completion.Value.EDITOR_NAME.equalsIgnoreCase(value.getType())) {
				return getEditorNames();
			} else if (Completion.Value.EDITOR_PROPERTY_NAME.equalsIgnoreCase(value.getType())) {
				return getEditorPropertyNames(parameters.getPosition());
			} else if (Completion.Value.ENUM.equalsIgnoreCase(value.getType())) {
				return Collections.singletonList(new Completion.Value(value.getText(), value.getNotes()));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problem processing value (" + ex.getMessage() + ").");
		}

		return Collections.emptyList();
	}

	private List<Completion.Value> getEditorPropertyNames(PsiElement position) throws IOException {
		String editorName = PsiUtils.getEditorOfProperty(position);
		EditorDef editor = SmartCaseAPIClient.getInstance().getEditors().get(editorName);
		if (editor != null && editor.getProperties() != null) {
			return editor.getProperties().stream()
				.map(e -> new Completion.Value(e.getName(), e.getLabel(), e.required()))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	private List<Completion.Value> getEditorNames() throws IOException {
		return SmartCaseAPIClient.getInstance().getEditors().values().stream()
			.map(e -> new Completion.Value(e.getName(), e.getApplication()))
			.collect(Collectors.toList());
	}

	private List<Completion.Value> getCurrentTime() {
		Long now = System.currentTimeMillis();
		return Collections.singletonList(new Completion.Value(String.valueOf(now), "aktuální čas (ms od 1970)"));
	}

	private List<Completion.Value> getCurrentUsername() {
		String username = System.getenv().get("USERNAME");
		if (StringUtils.isNotEmpty(username)) {
			return Collections.singletonList(new Completion.Value(username, "aktuální uživatel"));
		}

		return Collections.emptyList();
	}

}
