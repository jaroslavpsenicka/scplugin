package cz.csas.smart.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class UploadAction extends AnAction {

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
            int shiftKeyDown = event.getInputEvent().getModifiers() & InputEvent.SHIFT_MASK;
            if (shiftKeyDown > 0) {
                EnvironmentComponent environment = EnvironmentComponent.getInstance();
                proceed(file, environment.getActiveEnvironment().getUrl(), environment.isAutoDeploy());
            } else {
                UploadDialog dialog = new UploadDialog(event.getProject());
                if (dialog.showAndGet()) {
                    proceed(file, dialog.getSelectedEnvironment().getUrl(), dialog.doDeploy());
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file, " + ex.toString());
        }
    }

    private void proceed(VirtualFile file, String url, boolean deploy) throws IOException {
        SmartCaseAPIClient client = new SmartCaseAPIClient(url);
        String id = client.upload(file.contentsToByteArray());
        if (deploy) client.deploy(id);
    }

}
