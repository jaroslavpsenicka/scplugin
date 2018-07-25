package cz.csas.smart.idea;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileTask;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.model.Environment;

import java.util.Arrays;

public class JsonCompileTask implements CompileTask {

    public JsonCompileTask(CompilerManager compileManager) {
        compileManager.addAfterTask(this);
    }

    @Override
    public boolean execute(CompileContext context) {
        ValidationComponent validationComponent = ValidationComponent.getInstance();
        if (validationComponent.isAutoValidate()) {
            Environment env = EnvironmentComponent.getInstance().getActiveEnvironment();
            FileEditor[] editors = FileEditorManager.getInstance(context.getProject()).getAllEditors();
            Arrays.stream(editors).forEach(editor -> {
                VirtualFile file = editor.getFile();
                Validator validator = new Validator(file);
                validationComponent.setViolations(file.getName(), validator.validate(env));
            });
        }

        return false;
    }
}
