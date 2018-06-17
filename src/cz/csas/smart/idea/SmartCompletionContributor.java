package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonElement;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.model.Completion;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static org.apache.commons.lang.StringUtils.strip;

public class SmartCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> AFTER_CURLYBRACE = psiElement().afterLeaf("{")
	    .andNot(psiElement().withParent(JsonStringLiteral.class));
	private static final PsiElementPattern.Capture<PsiElement> AFTER_COMMA = psiElement().afterLeaf(",")
		.andNot(psiElement().withParent(JsonStringLiteral.class));

    public SmartCompletionContributor() {
	    this.extend(CompletionType.BASIC, AFTER_CURLYBRACE, KeywordsCompletionProvider.INSTANCE);
	    this.extend(CompletionType.BASIC, AFTER_COMMA, KeywordsCompletionProvider.INSTANCE);
    }

    private static class KeywordsCompletionProvider extends CompletionProvider<CompletionParameters> {

	    private static final KeywordsCompletionProvider INSTANCE = new KeywordsCompletionProvider();

	    @Override
	    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		    String path = this.getPath(parameters.getPosition().getParent());
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

	    private String getPath(PsiElement element) {
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

    }
}
