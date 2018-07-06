package cz.csas.smart.idea.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import cz.csas.smart.idea.ProfileComponent;
import cz.csas.smart.idea.PsiUtils;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.NameType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DynamicCompletionContributor extends CompletionProvider<CompletionParameters> {

	public static final DynamicCompletionContributor INSTANCE = new DynamicCompletionContributor();

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
		if (Completion.Value.ATTRIBUTE_NAME.equalsIgnoreCase(value.getType())) {
			return PsiUtils.getAttributes(parameters.getPosition(), value.getOf());
		}

		return Collections.emptyList();
	}


}
