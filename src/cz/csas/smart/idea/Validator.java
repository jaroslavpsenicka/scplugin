package cz.csas.smart.idea;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.Violation;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Validator {

    private final VirtualFile file;

    public Validator(VirtualFile file) {
        this.file = file;
    }

    public Validator(DataContext ctx) {
        this(ctx.getData(DataKeys.VIRTUAL_FILE));
    }

    public VirtualFile getFile() {
        return file;
    }

    public List<Violation> validate(Environment env) {
        try {
            SmartCaseAPIClient client = SmartCaseAPIClient.getInstance();
            return client.validate(file, env);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error validating file, " + ex.toString());
        }

        return Collections.emptyList();
    }
}
