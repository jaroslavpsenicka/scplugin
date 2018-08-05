package cz.csas.smart.idea;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JsonFileReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

	private final VirtualFile refFile;

	public JsonFileReference(PsiElement element, TextRange textRange, VirtualFile refFile) {
		super(element, textRange);
		this.refFile = refFile;
	}

	@NotNull
	@Override
	public ResolveResult[] multiResolve(boolean incompleteCode) {
		List<ResolveResult> results = new ArrayList<>();
		PsiFile file = PsiManager.getInstance(getElement().getProject()).findFile(refFile);
		results.add(new PsiElementResolveResult(file));
		return results.toArray(new ResolveResult[results.size()]);
	}

	@Nullable
	@Override
	public PsiElement resolve() {
		ResolveResult[] resolveResults = multiResolve(false);
		return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
	}

	@NotNull
	@Override
	public Object[] getVariants() {
		return new Object[0];
	}
}
