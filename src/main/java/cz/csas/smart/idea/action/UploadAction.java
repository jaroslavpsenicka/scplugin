package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.PreProcessor;
import cz.csas.smart.idea.Uploader;
import cz.csas.smart.idea.UserComponent;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;

public class UploadAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        DataContext ctx = event.getDataContext();
        VirtualFile file = ctx.getData(DataKeys.VIRTUAL_FILE);
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(ctx));

        if (EnvironmentComponent.getInstance().isPreProcess()) {
            try {
                file = new PreProcessor().process(file);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error pre-processing the file, " + ex.getMessage());
                return;
            }
        }

        Uploader uploader = new Uploader(file, statusBar);
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            Environment env = dialog.getSelectedEnvironment();
            UserComponent.getInstance().setUser(dialog.getUsername());
            uploader.upload(env, dialog.getDeployOptions());
        }
    }

}
