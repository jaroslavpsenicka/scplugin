package cz.csas.smart.idea;

import com.intellij.json.findUsages.JsonWordScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cz.csas.smart.idea.PreProcessor.IMPORT_REGEX;

public class SmartFindUsagesProvider implements FindUsagesProvider {

    private final Pattern pattern;

    public SmartFindUsagesProvider() {
        pattern = Pattern.compile(IMPORT_REGEX);
    }

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new JsonWordScanner();
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiComment && pattern.matcher(psiElement.getText()).matches();
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement psiElement) {
        return (psiElement instanceof PsiComment) ? "import" : "";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement psiElement) {
        Matcher matcher = pattern.matcher(psiElement.getText());
        if (psiElement instanceof PsiComment && matcher.find()) {
            return matcher.group(1);
        }

        return "<unnamed>";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement psiElement, boolean useFullName) {
        return getDescriptiveName(psiElement);
    }
}
