package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.DocumentAdapter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmartCaseConfigurable implements Configurable {

    private JPanel component;
    private JTextField textServerUrl;
    private boolean isDirty;

    public static final String SERVER_URL = "cz.csas.smart.serverUrl";

    @Nls
    @Override
    public String getDisplayName() {
        return "SmartCase";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JLabel labelServerUrl = new JLabel("Server URL:");
        textServerUrl = new JTextField(20);
        textServerUrl.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent documentEvent) {
                isDirty = true;
            }
        });
        labelServerUrl.setLabelFor(textServerUrl);
        JButton testButton = new JButton("Connectivity Test");
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean result = false;
                String errorMessage = "";
                try {
                    SmartCaseAPIClient smartCaseAPIClient = new SmartCaseAPIClient(textServerUrl.getText());
                    result = smartCaseAPIClient.ping();
                } catch (Exception ex) {
                    result = false;
                    errorMessage = ex.getMessage();
                } finally {
                    JOptionPane.showMessageDialog(null, result ?
                        "OK, looks good!" : "Nope, " + errorMessage +
                        ", make sure the server URL contains both the port and context.");
                }
            }
        });

        JPanel serverPanel = new JPanel(new BorderLayout(10, 0));
        serverPanel.add(labelServerUrl, BorderLayout.WEST);
        serverPanel.add(textServerUrl, BorderLayout.CENTER);
        JPanel serverTools = new JPanel(new BorderLayout(10, 0));
        serverTools.add(testButton, BorderLayout.EAST);
        serverPanel.add(serverTools, BorderLayout.SOUTH);

        component = new JPanel(new BorderLayout());
        component.add(serverPanel, BorderLayout.NORTH);
        return component;
    }

    @Override
    public boolean isModified() {
        return isDirty;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent.getInstance().setValue(SERVER_URL, textServerUrl.getText());
    }

    @Override
    public void reset() {
        String serverUrlValue = PropertiesComponent.getInstance().getValue(SERVER_URL);
        if (serverUrlValue != null) {
            textServerUrl.setText(serverUrlValue);
        }
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }
}
