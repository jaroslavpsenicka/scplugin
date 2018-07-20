package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import cz.csas.smart.idea.completion.ValueCompletionContributor;
import cz.csas.smart.idea.completion.NameCompletionContributor;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SmartCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> AFTER_CURLYBRACE = psiElement()
	    .afterLeaf("{")
	    .andNot(psiElement().withParent(JsonStringLiteral.class));
	private static final PsiElementPattern.Capture<PsiElement> AFTER_COMMA = psiElement()
		.afterLeaf(",")
		.andNot(psiElement().withParent(JsonStringLiteral.class));
	private static final PsiElementPattern.Capture<PsiElement> IN_VALUE = psiElement()
		.afterLeaf(":")
		.and(psiElement().withSuperParent(2, JsonProperty.class))
		.and(psiElement().withParent(JsonStringLiteral.class));

    public SmartCompletionContributor() {
	    this.extend(CompletionType.BASIC, AFTER_CURLYBRACE, NameCompletionContributor.INSTANCE);
	    this.extend(CompletionType.BASIC, AFTER_COMMA, NameCompletionContributor.INSTANCE);
	    this.extend(CompletionType.BASIC, IN_VALUE, ValueCompletionContributor.INSTANCE);
    }

}
