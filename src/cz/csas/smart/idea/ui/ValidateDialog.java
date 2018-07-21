package cz.csas.smart.idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.DocumentAdapter;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.UserComponent;
import cz.csas.smart.idea.model.Environment;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;

public class ValidateDialog extends DialogWrapper {

    private JComboBox<Environment> environmentCombo;
    private JTextField usernameField;

    public ValidateDialog(Project project) {
        super(project, true);
        this.setTitle("Validate");
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

    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();

        environmentCombo = new JComboBox<>();
        EnvironmentComponent.getInstance().getEnvironments().stream()
            .filter(p -> p.getUrl().startsWith("http://"))
            .forEach(p -> environmentCombo.addItem(p));
        environmentCombo.setSelectedItem(EnvironmentComponent.getInstance().getActiveEnvironment());

        String user = UserComponent.getInstance().getUser();
        usernameField = new JTextField();
        usernameField.setText(user);
        usernameField.getDocument().addDocumentListener(new DocumentAdapter() {
            protected void textChanged(DocumentEvent documentEvent) {
                getOKAction().setEnabled(usernameField.getText().length() > 0);
            }
        });

        panel.setLayout(new SpringLayout());
        panel.add(new JLabel("Validate via: "));
        panel.add(environmentCombo);
        panel.add(new JLabel("as CEN/EXT: "));
        panel.add(usernameField);
        panel.setSize(300, 75);
        SpringUtilities.makeCompactGrid(panel, 2, 2, 0, 0, 5, 5);

        return panel;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        getOKAction().setEnabled(usernameField.getText().length() > 0);
        return StringUtils.isNotEmpty(usernameField.getText()) ? environmentCombo : usernameField;
    }

}
