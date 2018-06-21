package cz.csas.smart.idea.ui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.SmartFileType;
import cz.csas.smart.idea.model.Environment;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UploadDialog extends DialogWrapper {

    private JComboBox<Environment> environmentCombo;
    private JCheckBox deployCheckBox;

    public UploadDialog(Project project) {
        super(project, true);
        this.setTitle("Upload to server");
        this.setResizable(true);
        this.setModal(true);
        this.setOKActionEnabled(true);
        this.init();
        this.pack();
    }

    public Environment getSelectedEnvironment() {
        return (Environment) environmentCombo.getSelectedItem();
    }

    public boolean doDeploy() {
        return deployCheckBox.isSelected();
    }

    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();

        environmentCombo = new JComboBox<>();
        EnvironmentComponent.getInstance().getEnvironments().forEach(p -> environmentCombo.addItem(p));
        environmentCombo.setSelectedItem(EnvironmentComponent.getInstance().getActiveEnvironment());
        deployCheckBox = new JCheckBox("Deploy");
        deployCheckBox.setSelected(EnvironmentComponent.getInstance().isAutoDeploy());

        panel.setLayout(new SpringLayout());
        panel.add(new JLabel("Upload to: "));
        panel.add(environmentCombo);
        panel.add(new JLabel(""));
        panel.add(deployCheckBox);
        panel.setSize(300, 75);
        SpringUtilities.makeCompactGrid(panel, 2, 2, 0, 0, 5, 5);

        return panel;
    }
}
