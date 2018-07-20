package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import cz.csas.smart.idea.ui.UploadDialog;

public class UploadAction extends UploadFastAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            super.actionPerformed(event);
        }
    }

}
