package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.ui.UploadDialog;

public class UploadAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Uploader uploader = new Uploader(event.getDataContext());
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            Environment env = dialog.getSelectedEnvironment();
            uploader.upload(env, dialog.deployOptions());
        }
    }

}
