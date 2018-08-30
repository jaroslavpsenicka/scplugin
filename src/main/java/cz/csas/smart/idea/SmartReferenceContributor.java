package cz.csas.smart.idea;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cz.csas.smart.idea.PreProcessor.IMPORT_REGEX;

public class SmartReferenceContributor extends PsiReferenceContributor {

    private static final Logger LOG = LoggerFactory.getLogger(SmartReferenceContributor.class);
    private final Pattern pattern;

    public SmartReferenceContributor() {
        pattern = Pattern.compile(IMPORT_REGEX);
    }

    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar reg) {
        reg.registerReferenceProvider(PlatformPatterns.psiElement(PsiComment.class), new PsiReferenceProvider() {
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                PsiComment comment = (PsiComment) element;
                Matcher matcher = pattern.matcher(comment.getText());
                while (matcher.find() && element.getContainingFile().getVirtualFile() != null) try {
                    VirtualFile parentDir = element.getContainingFile().getVirtualFile().getParent();
                    VirtualFile refFile = parentDir.findFileByRelativePath(matcher.group(1));
                    if (refFile != null && refFile.exists()) {
                        return new PsiReference[]{
                                new JsonFileReference(element, new TextRange(matcher.start(1), matcher.end(1)), refFile)
                        };
                    }
                } catch (Exception ex) {
                    LOG.error("Error processing {}", comment, ex);
                }

                return PsiReference.EMPTY_ARRAY;
            }
        });
    }

}
