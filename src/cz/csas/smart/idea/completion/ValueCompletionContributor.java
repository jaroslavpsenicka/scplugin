package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.ProfileComponent;
import cz.csas.smart.idea.PsiUtils;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.NameType;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValueCompletionContributor extends CompletionProvider<CompletionParameters> {

	private List<NameType> editors;

	public static final ValueCompletionContributor INSTANCE = new ValueCompletionContributor();

	@Override
	protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		String path = PsiUtils.getPath(parameters.getPosition().getParent());
		List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
		if (completions != null) {
			completions.forEach(i -> getValue(parameters, i).forEach(j -> result.addElement(
				LookupElementBuilder.create(j.getName())
					.withBoldness(true).withTypeText(j.getType(), true))));
		}
	}

	private List<NameType> getValue(CompletionParameters parameters, Completion.Value value) {
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
			} else if (Completion.Value.ENUM.equalsIgnoreCase(value.getType())) {
				return Collections.singletonList(new NameType(value.getText(), value.getNotes()));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problem processing value (" + ex.getMessage() + ").");
		}

		return Collections.emptyList();
	}

	public void reset() {
		this.editors = null;
	}

	private List<NameType> getEditorNames() throws IOException {
		if (editors == null) {
			EnvironmentComponent environment = EnvironmentComponent.getInstance();
			SmartCaseAPIClient client = new SmartCaseAPIClient(environment.getActiveEnvironment().getUrl());
			this.editors = client.readEditors();
			return editors;
		}

		return editors;
	}

	private List<NameType> getCurrentTime() {
		Long now = System.currentTimeMillis();
		return Collections.singletonList(new NameType(String.valueOf(now), "aktuální čas (ms od 1970)"));
	}

	private List<NameType> getCurrentUsername() {
		String username = System.getenv().get("USERNAME");
		if (StringUtils.isNotEmpty(username)) {
			return Collections.singletonList(new NameType(username, "aktuální uživatel"));
		}

		return Collections.emptyList();
	}


}
