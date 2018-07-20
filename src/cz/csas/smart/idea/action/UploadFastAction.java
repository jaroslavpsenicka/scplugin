package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.SmartFileType;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.UploadResponse;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;

public class UploadFastAction extends AnAction {

    @Override
    public void update(AnActionEvent event) {
        VirtualFile file = (VirtualFile) event.getDataContext().getData(DataKeys.VIRTUAL_FILE.getName());
        if (file != null) {
            Presentation pres = event.getPresentation();
            pres.setVisible(file.getName().toLowerCase().endsWith(SmartFileType.DEFAULT_EXTENSION));
        }
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile data = (VirtualFile) event.getDataContext().getData(DataKeys.VIRTUAL_FILE.getName());
        EnvironmentComponent env = EnvironmentComponent.getInstance();
        new Uploader(env.getActiveEnvironment()).upload(data, env.isAutoDeploy() ? UploadDialog.DeployOptions.DEPLOY : UploadDialog.DeployOptions.NONE);
    }

}
