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

public class UploadAction extends UploadFastAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        UploadDialog dialog = new UploadDialog(event.getProject());
        if (dialog.showAndGet()) {
            super.actionPerformed(event);
        }
    }

}
