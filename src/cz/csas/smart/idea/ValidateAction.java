package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.ui.ValidateDialog;

import javax.swing.*;

public class ValidateAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        VirtualFile file = (VirtualFile) e.getDataContext().getData(DataKeys.VIRTUAL_FILE.getName());
        if (file != null) {
            boolean enabled = file.getName().toLowerCase().endsWith(SmartFileType.DEFAULT_EXTENSION);
            e.getPresentation().setEnabled(enabled);
            e.getPresentation().setVisible(enabled);
        }
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        String serverUrl = PropertiesComponent.getInstance().getValue(SmartCaseConfigurable.SERVER_URL);
        if (serverUrl == null || serverUrl.length() == 0) {
            JOptionPane.showMessageDialog(null, "SmartCase server URL is not configured, please setup it first.");
        } else if (!serverUrl.startsWith("http")) {
            JOptionPane.showMessageDialog(null, "The URL must be http(s)-based.");
        } else try {
            Editor editor = FileEditorManager.getInstance(event.getProject()).getSelectedTextEditor();
            if (editor != null) {
                ValidateDialog dialog = new ValidateDialog(event.getProject(), serverUrl, editor.getDocument().getText());
                dialog.showAndGet();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
