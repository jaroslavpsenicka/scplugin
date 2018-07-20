package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.ui.UploadDialog;

public class UploadAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile file = event.getDataContext().getData(DataKeys.VIRTUAL_FILE);
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            new Uploader(dialog.getSelectedEnvironment()).upload(file, dialog.deployOptions());
        }
    }

}
