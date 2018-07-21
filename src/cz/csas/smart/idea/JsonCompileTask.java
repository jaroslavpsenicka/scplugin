package cz.csas.smart.idea;

import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileTask;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.fileEditor.FileEditor;
import cz.csas.smart.idea.model.Environment;

public class JsonCompileTask implements CompileTask {

    public JsonCompileTask(CompilerManager compileManager) {
        compileManager.addAfterTask(this);
    }

    @Override
    public boolean execute(CompileContext context) {
        ValidationComponent validationComponent = ValidationComponent.getInstance();
        if (validationComponent.isAutoValidate()) {
            Validator validator = new Validator();
            Environment env = EnvironmentComponent.getInstance().getActiveEnvironment();
            validationComponent.setCurrentViolations(validator.validate(env));


        }

//        System.out.println("Compiling: " + context.getProject());
//        context.addMessage(CompilerMessageCategory.ERROR, "Whoa", null, -1, -1);
//
//        try {
//            FileEditor[] editors = FileEditorManager.getInstance(context.getProject()).getAllEditors();
//            System.out.println("Editors: " + new String(editors[0].getFile().contentsToByteArray()));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        return false;
    }
}
