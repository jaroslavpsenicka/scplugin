package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonProperty;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.ProfileComponent;
import cz.csas.smart.idea.PsiUtils;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.EditorDef;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static cz.csas.smart.idea.model.Completion.Value.SELECTOR_ATTRIBUTE_NAME;
import static cz.csas.smart.idea.model.Completion.Value.SELECTOR_NAME;

public class NameCompletionContributor extends CompletionProvider<CompletionParameters> {

	public static final NameCompletionContributor INSTANCE = new NameCompletionContributor();

	@Override
	protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		String path = PsiUtils.getPath(parameters.getPosition().getParent());
		List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
		if (completions != null) {
			AtomicInteger idx = new AtomicInteger(0);
			Boolean useQuotes = ProfileComponent.getInstance().useQuotes();
			completions.stream()
				.sorted(PsiUtils.bySeverityAndName)
				.forEach(i -> getValues(parameters, i).stream()
//					.filter(c -> notYetUsed(c, parameters.getPosition().getParent()))
					.sorted(PsiUtils.bySeverityAndName)
					.forEach(j -> result.addElement(PrioritizedLookupElement.withPriority(
						createElement(j, useQuotes), idx.getAndIncrement())))
			);
		}
	}

	private List<Completion.Value> getValues(CompletionParameters parameters, Completion.Value value) {
		try {
			if (SELECTOR_NAME.equals(value.getType())) {
				return getSelectorNames(parameters.getPosition());
			} else if (SELECTOR_ATTRIBUTE_NAME.equals(value.getType())) {
				return getSelectorAttributeNames(parameters.getPosition());
			}

			return Collections.singletonList(value);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Problem processing value (" + ex.getMessage() + ").");
		}

		return Collections.emptyList();
	}

	private LookupElement createElement(Completion.Value value, Boolean useQuotes) {
		String key = useQuotes ? ("\"" + value + "\"") : value.getText();
		return LookupElementBuilder.create(key + ": " + getDefaultValue(value))
			.withPresentableText(value.getText())
			.withBoldness(value.required())
			.withIcon(value.icon())
			.withTypeText(value.getNotes() != null ? value.getNotes() : value.getType(), true)
			.withInsertHandler((ctx, item) -> handleInsert(ctx, value));
	}

	private boolean notYetUsed(Completion.Value value, PsiElement element) {
		return PsiTreeUtil.findChildrenOfType(element.getParent().getParent(), JsonProperty.class).stream()
			.noneMatch(p -> ("\"" + value.getText() + "\"").equals(p.getNameElement().getText()))
		&& PsiTreeUtil.findChildrenOfType(element.getParent().getParent(), JsonProperty.class).stream()
			.noneMatch(p -> (value.getText()).equals(p.getNameElement().getText()));
	}

	@NotNull
	private String getDefaultValue(Completion.Value value) {
		String defaultValue = value.getDefaultValue() != null ? value.getDefaultValue() : "";
		return Completion.Value.ARRAY.equalsIgnoreCase(value.getType()) ? "[" + defaultValue + "]" :
			Completion.Value.OBJECT.equalsIgnoreCase(value.getType()) ? "{" + defaultValue + "}" :
			Completion.Value.STRING.equalsIgnoreCase(value.getType()) ? "\"" + defaultValue + "\"" :
			defaultValue;
	}

	private void handleInsert(InsertionContext ctx, Completion.Value value) {
		if (value.getDefaultValue() == null) {
			CaretModel caretModel = ctx.getEditor().getCaretModel();
			caretModel.moveToOffset(caretModel.getOffset() - 1);
		}
	}

	private List<Completion.Value> getSelectorNames(PsiElement position) throws IOException {
		String editorName = PsiUtils.getEditorOfSelector(position.getParent());
		EditorDef editor = SmartCaseAPIClient.getInstance().getEditors().get(editorName);
		if (editor != null && editor.getModel() != null) {
			return editor.getModel().entrySet().stream()
				.map(e -> new Completion.Value(e.getKey(), e.getValue().getLabel()))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	private List<Completion.Value> getSelectorAttributeNames(PsiElement position) throws IOException {
		String editorName = PsiUtils.getEditorOfSelector(position.getParent());
		EditorDef editor = SmartCaseAPIClient.getInstance().getEditors().get(editorName);
		if (editor != null && editor.getModel() != null) {
			return editor.getModel().entrySet().stream()
				.map(e -> new Completion.Value(e.getKey(), e.getValue().getType()))
				.collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

}
