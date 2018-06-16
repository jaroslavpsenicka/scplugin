package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonElement;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static org.apache.commons.lang.StringUtils.strip;

public class SmartCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> AFTER_CURLYBRACE = psiElement()
        .afterLeaf("{"); //.withSuperParent(2, JsonObject.class)
//        .andNot(psiElement().withParent(JsonStringLiteral.class));

    public SmartCompletionContributor() {
        this.extend(CompletionType.BASIC, AFTER_CURLYBRACE, KeywordsCompletionProvider.INSTANCE);
    }

    private static class KeywordsCompletionProvider extends CompletionProvider<CompletionParameters> {

	    private static final KeywordsCompletionProvider INSTANCE = new KeywordsCompletionProvider();
	    private static final String[] KEYWORDS = new String[]{"name", "label", "description"};

	    @Override
	    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
		    String path = this.getPath(parameters.getPosition().getParent());
		    result.addLookupAdvertisement(path);
		    for (String keyword : KEYWORDS) {
			    result.addElement(LookupElementBuilder.create("\"" + keyword + "\": ").withPresentableText(keyword));
		    }
	    }

	    private String getPath(PsiElement element) {
		    StringBuffer buff = new StringBuffer();
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
