package cz.csas.smart.idea;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;

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

}
