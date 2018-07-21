package cz.csas.smart.idea.ui;

import com.intellij.ide.IdeBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.DocumentAdapter;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.SmartFileType;
import cz.csas.smart.idea.UserComponent;
import cz.csas.smart.idea.action.Uploader;
import cz.csas.smart.idea.model.Environment;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class UploadDialog extends DialogWrapper {

    private JComboBox<Environment> environmentCombo;
    private JCheckBox deployCheckBox;
    private JCheckBox hotdeployCheckBox;
    private JTextField usernameField;

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

    public String getUsername() {
        return usernameField.getText();
    }

    public Uploader.DeployOptions getDeployOptions() {
        return hotdeployCheckBox.isSelected() ? Uploader.DeployOptions.HOTDEPLOY :
            deployCheckBox.isSelected() ? Uploader.DeployOptions.DEPLOY : Uploader.DeployOptions.UPLOAD;
    }

    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();

        environmentCombo = new JComboBox<>();
        EnvironmentComponent.getInstance().getEnvironments().stream()
            .filter(p -> p.getUrl().startsWith("http://"))
            .forEach(p -> environmentCombo.addItem(p));
        environmentCombo.setSelectedItem(EnvironmentComponent.getInstance().getActiveEnvironment());
        deployCheckBox = new JCheckBox("Deploy");
        deployCheckBox.setSelected(EnvironmentComponent.getInstance().isAutoDeploy());
        deployCheckBox.addItemListener(l -> hotdeployCheckBox.setEnabled(deployCheckBox.isSelected()));
        hotdeployCheckBox = new JCheckBox("Hotdeploy");
        hotdeployCheckBox.setEnabled(deployCheckBox.isSelected());

        String user = UserComponent.getInstance().getUser();
        usernameField = new JTextField();
        usernameField.setText(user);
        usernameField.getDocument().addDocumentListener(new DocumentAdapter() {
            protected void textChanged(DocumentEvent documentEvent) {
                getOKAction().setEnabled(usernameField.getText().length() > 0);
            }
        });

        panel.setLayout(new SpringLayout());
        panel.add(new JLabel("Upload to: "));
        panel.add(environmentCombo);
        panel.add(new JLabel("as CEN/EXT: "));
        panel.add(usernameField);
        panel.add(new JLabel(""));
        panel.add(deployCheckBox);
        panel.add(new JLabel(""));
        panel.add(hotdeployCheckBox);
        panel.setSize(300, 75);
        SpringUtilities.makeCompactGrid(panel, 4, 2, 0, 0, 5, 5);

        return panel;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        getOKAction().setEnabled(usernameField.getText().length() > 0);
        return StringUtils.isNotEmpty(usernameField.getText()) ? environmentCombo : usernameField;
    }

}
