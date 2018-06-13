package cz.csas.smart.idea.ui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.SmartFileType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ValidateDialog extends DialogWrapper {

    private SmartCaseAPIClient client;
    private String text;

    public ValidateDialog(Project project, String serverUrl, String text) {
        super(project, true);
        this.client = new SmartCaseAPIClient(serverUrl);
        this.text = text;
        this.setTitle("Validate");
        this.setResizable(false);
        this.setModal(true);
        this.setOKActionEnabled(false);
        this.init();
        this.pack();
    }

    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JProgressBar dpb = new JProgressBar(0, 500);
        panel.add(BorderLayout.CENTER, dpb);
        panel.add(BorderLayout.NORTH, new JLabel("Validating..."));
        panel.setSize(300, 75);
        return panel;
    }

    private void validateText() throws IOException {
        client.validate(text);
    }
}
