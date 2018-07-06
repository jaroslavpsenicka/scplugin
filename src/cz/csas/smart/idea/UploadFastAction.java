package cz.csas.smart.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.io.IOException;

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
        VirtualFile file = (VirtualFile) event.getDataContext().getData(DataKeys.VIRTUAL_FILE.getName());
        if (file != null) try {
            EnvironmentComponent environment = EnvironmentComponent.getInstance();
            SmartCaseAPIClient client = new SmartCaseAPIClient(environment.getActiveEnvironment().getUrl());
            String id = client.upload(file.contentsToByteArray());
            if (environment.isAutoDeploy()) client.deploy(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file, " + ex.toString());
        }
    }

}
