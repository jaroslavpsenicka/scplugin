package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import cz.csas.smart.idea.UserComponent;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.ui.UploadDialog;

public class UploadAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Uploader uploader = new Uploader(event.getDataContext());
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            Environment env = dialog.getSelectedEnvironment();
            UserComponent.getInstance().setUser(dialog.getUsername());
            uploader.upload(env, dialog.getDeployOptions());
        }
    }

}
