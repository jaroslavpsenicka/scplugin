package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonElement;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;

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
		    List<String> names = this.collectParentNames(parameters.getPosition().getParent());
		    result.addLookupAdvertisement(names.toString());
		    for (String keyword : KEYWORDS) {
			    result.addElement(LookupElementBuilder.create("\"" + keyword + "\": ").withPresentableText(keyword));
		    }
	    }

	    private List<String> collectParentNames(PsiElement element) {
		    return collectParentNames(element, new ArrayList<>());
	    }


	    private List<String> collectParentNames(PsiElement element, List<String> names) {
		    PsiElement parent = findParent(element, names.size() == 0 ? 3 : 2);
		    if (parent != null && parent instanceof JsonElement) {
			    PsiElement text = parent.getFirstChild();
			    if (text instanceof JsonStringLiteral) {
				    names.add(0, text.getText());
			    }

			    collectParentNames(parent, names);
		    }

		    return names;
	    }

	    private PsiElement findParent(PsiElement element, int level) {
		    for (int i = 0; i < level; i++) {
			    if (element instanceof JsonElement) {
				    element = element.getParent();
			    } else return null;
		    }

		    return element;
	    }

    }
}
