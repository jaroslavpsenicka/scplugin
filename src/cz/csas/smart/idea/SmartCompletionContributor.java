package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.model.Completion;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SmartCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> AFTER_CURLYBRACE = psiElement()
	    .afterLeaf("{")
	    .andNot(psiElement().withParent(JsonStringLiteral.class));
	private static final PsiElementPattern.Capture<PsiElement> AFTER_COMMA = psiElement()
		.afterLeaf(",")
		.andNot(psiElement().withParent(JsonStringLiteral.class));
	private static final PsiElementPattern.Capture<PsiElement> IN_VALUE = psiElement()
		.withSuperParent(2, JsonProperty.class)
		.and(psiElement().withParent(JsonStringLiteral.class));

    public SmartCompletionContributor() {
	    this.extend(CompletionType.BASIC, AFTER_CURLYBRACE, StaticCompletionContributor.INSTANCE);
	    this.extend(CompletionType.BASIC, AFTER_COMMA, StaticCompletionContributor.INSTANCE);
	    this.extend(CompletionType.BASIC, IN_VALUE, DynamicCompletionContributor.INSTANCE);
    }

	private static class StaticCompletionContributor extends CompletionProvider<CompletionParameters> {
	    private static final StaticCompletionContributor INSTANCE = new StaticCompletionContributor();

	    @Override
	    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		    String path = PsiUtils.getPath(parameters.getPosition().getParent());
		    result.addLookupAdvertisement(path);
		    List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
		    if (completions != null) completions.forEach(c ->
			    result.addElement(LookupElementBuilder.create("\"" + c + "\": " + getDefaultValue(c))
				    .withPresentableText(c.getText())
				    .withInsertHandler((ctx, item) -> handleInsert(ctx, c))
			    )
		    );
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

	private static class DynamicCompletionContributor extends CompletionProvider<CompletionParameters> {
		private static final DynamicCompletionContributor INSTANCE = new DynamicCompletionContributor();

		@Override
		protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
			String path = PsiUtils.getPath(parameters.getPosition().getParent());
			result.addLookupAdvertisement(path);
			List<Completion.Value> completions = ProfileComponent.getInstance().getActiveProfile().getCompletionsForPath(path);
			if (completions != null) {
				result.addAllElements(completions.stream()
					.map(i -> LookupElementBuilder.create(getValue(parameters, i)))
					.collect(Collectors.toList()));
			}
		}

		private List<String> getValue(CompletionParameters parameters, Completion.Value value) {
			if (Completion.Value.ATTRIBUTE_NAME.equalsIgnoreCase(value.getType())) {
				return PsiUtils.getAttributeNames(parameters.getPosition());
			}

			return Collections.emptyList();
		}

	}

}
