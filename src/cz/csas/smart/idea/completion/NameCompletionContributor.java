package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonProperty;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.ProfileComponent;
import cz.csas.smart.idea.PsiUtils;
import cz.csas.smart.idea.model.Completion;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NameCompletionContributor extends CompletionProvider<CompletionParameters> {

	private static final Logger LOG = Logger.getInstance(NameCompletionContributor.class);
	public static final NameCompletionContributor INSTANCE = new NameCompletionContributor();

	private final Comparator<Completion.Value> bySeverityAndName = (first, second) -> {
		if (first == null || second == null) return 0;
		else if (!first.required() && second.required()) return -1;
		else if (first.required() && !second.required()) return 1;
		else return second.getText().compareTo(first.getText());
	};

	@Override
	protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		String path = PsiUtils.getPath(parameters.getPosition().getParent());
		List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
		if (completions != null) {
			Boolean useQuotes = ProfileComponent.getInstance().useQuotes();
			AtomicInteger idx = new AtomicInteger(0);
			completions.stream()
				.filter(c -> notYetUsed(c, parameters.getPosition().getParent()))
				.sorted(bySeverityAndName)
				.forEach(c -> {
					String key = useQuotes ? ("\"" + c + "\"") : c.getText();
					LookupElement element = LookupElementBuilder.create(key + ": " + getDefaultValue(c))
						.withPresentableText(c.getText())
						.withBoldness(c.required())
						.withIcon(c.icon())
						.withTypeText(c.getNotes() != null ? c.getNotes() : c.getType(), true)
						.withInsertHandler((ctx, item) -> handleInsert(ctx, c));
					result.addElement(PrioritizedLookupElement.withPriority(element, idx.getAndIncrement()));
				}
			);
		}
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

}
