package cz.csas.smart.idea;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.Profile;
import cz.csas.smart.idea.ui.SpringUtilities;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SmartCaseConfigurable implements Configurable {

    private JPanel component;
    private JComboBox<Profile> profileCombo;
    private JComboBox<Environment> environmentCombo;
    private JButton profileReload;
    private JButton profileOpenInExplorer;
    private JCheckBox deployCheckBox;
    private JCheckBox preProcessCheckBox;
    private JCheckBox quotesCheckbox;
    private JCheckBox autoValidateCheckbox;
    private boolean isDirty;

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
        JPanel contents = new JPanel(new SpringLayout());
        JPanel component1 = createEnvironmentPanel();
        contents.add(createLabel("Environment:", component1));
        contents.add(component1);
        JPanel component2 = createProfilePanel();
        contents.add(createLabel("Completion Profile:", component2));
        contents.add(component2);
        JPanel component3 = createValidationPanel();
        contents.add(createLabel("Validation:", component3));
        contents.add(component3);

        SpringUtilities.makeCompactGrid(contents, contents.getComponentCount() / 2, 2, 0, 0, 5, 20);

        this.component = new JPanel(new BorderLayout());
        this.component.add(contents, BorderLayout.NORTH);
        return this.component;
    }

    @Override
    public boolean isModified() {
        return isDirty;
    }

    @Override
    public void apply() {
        EnvironmentComponent.getInstance().setActiveEnvironment((Environment) environmentCombo.getSelectedItem());
        EnvironmentComponent.getInstance().setPreProcess(preProcessCheckBox.isSelected());
        EnvironmentComponent.getInstance().setAutoDeploy(deployCheckBox.isSelected());
        ProfileComponent.getInstance().setActiveProfile((Profile) profileCombo.getSelectedItem());
        ProfileComponent.getInstance().setUseQuotes(quotesCheckbox.isSelected());
        ValidationComponent.getInstance().setAutoValidate(autoValidateCheckbox.isSelected());
        SmartCaseAPIClient.getInstance().reset();
    }

    @Override
    public void reset() {
        environmentCombo.setSelectedItem(EnvironmentComponent.getInstance().getActiveEnvironment());
        preProcessCheckBox.setSelected(EnvironmentComponent.getInstance().isPreProcess());
        deployCheckBox.setSelected(EnvironmentComponent.getInstance().isAutoDeploy());
        profileCombo.setSelectedItem(ProfileComponent.getInstance().getActiveProfile());
        quotesCheckbox.setSelected(ProfileComponent.getInstance().useQuotes());
        autoValidateCheckbox.setSelected(ValidationComponent.getInstance().isAutoValidate());
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }

    @NotNull
    private JPanel createLabel(String text, JPanel component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setLabelFor(component);
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    @NotNull
    private JPanel createEnvironmentPanel() {
        environmentCombo = new JComboBox<>();
        EnvironmentComponent.getInstance().getEnvironments().forEach(p -> environmentCombo.addItem(p));
        environmentCombo.setSelectedItem(EnvironmentComponent.getInstance().getActiveEnvironment());
        environmentCombo.addActionListener(actionEvent -> isDirty = true);

        preProcessCheckBox = new JCheckBox("Preprocess before upload");
        preProcessCheckBox.setSelected(EnvironmentComponent.getInstance().isPreProcess());

        deployCheckBox = new JCheckBox("Auto deploy after successful upload");
        deployCheckBox.setSelected(EnvironmentComponent.getInstance().isAutoDeploy());

        JPanel environmentPanel = new JPanel(new BorderLayout(5, 5));
        environmentPanel.add(environmentCombo, BorderLayout.NORTH);
        environmentPanel.add(preProcessCheckBox, BorderLayout.CENTER);
        environmentPanel.add(deployCheckBox, BorderLayout.SOUTH);
        return environmentPanel;
    }

    @NotNull
    private JPanel createProfilePanel() {
        profileCombo = new JComboBox<>();
        ProfileComponent.getInstance().getProfiles().forEach(p -> profileCombo.addItem(p));
        profileCombo.setSelectedItem(ProfileComponent.getInstance().getActiveProfile());
        profileCombo.addActionListener(actionEvent -> {
            Profile profile = (Profile) profileCombo.getSelectedItem();
            isDirty = true;
            if (profile != null) {
                profileReload.setEnabled(profile.canReload());
                profileOpenInExplorer.setEnabled(Desktop.isDesktopSupported() && profile.canReload());
            }
        });

        JButton profileNew = new JButton("Add profile");
        profileNew.addActionListener(actionEvent -> {
            List<Profile> newProfiles = ProfileComponent.getInstance().addProfiles(FileChooser.chooseFiles(new FileChooserDescriptor(
                            true, false, false, false, false, true)
                            .withTitle("Select Profile")
                            .withFileFilter(f -> "profile".equals(f.getExtension())),
                    null, null));
            if (!newProfiles.isEmpty()) {
                isDirty = true;
                newProfiles.forEach(p -> profileCombo.addItem(p));
                profileCombo.setSelectedItem(newProfiles.get(0));
            }
        });

        profileReload = new JButton("Reload");
        profileReload.setEnabled(false);
        profileReload.addActionListener(actionEvent -> {
            Profile profile = (Profile) profileCombo.getSelectedItem();
            if (profile != null) {
                profile.reload();
                JOptionPane.showMessageDialog(null, "Reloaded.");
            }
        });

        profileOpenInExplorer = new JButton("Open in Explorer");
        profileOpenInExplorer.setEnabled(false);
        profileOpenInExplorer.addActionListener(actionEvent -> {
            Profile profile = (Profile) profileCombo.getSelectedItem();
            if (profile != null) {
                try {
                    Desktop.getDesktop().open(new File(profile.getPath()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error opening the url: " +
                            profile.getPath() + ". Help yourself please.");
                }
            }
        });

        quotesCheckbox = new JCheckBox("Quote element names");
        quotesCheckbox.setSelected(ProfileComponent.getInstance().useQuotes());

        JPanel profilePanel = new JPanel(new BorderLayout(5, 5));
        profilePanel.add(profileCombo, BorderLayout.CENTER);
        JPanel profileButtons = new JPanel();
        profileButtons.add(profileOpenInExplorer);
        profileButtons.add(profileReload);
        profileButtons.add(profileNew);
        JPanel profileInfo = new JPanel(new BorderLayout());
        profileInfo.add(profileButtons, BorderLayout.EAST);
        profileInfo.add(quotesCheckbox, BorderLayout.SOUTH);
        profilePanel.add(profileInfo, BorderLayout.SOUTH);
        return profilePanel;
    }

    @NotNull
    private JPanel createValidationPanel() {
        autoValidateCheckbox = new JCheckBox("Validate open editors on build");
        autoValidateCheckbox.setSelected(ValidationComponent.getInstance().isAutoValidate());

        JPanel environmentPanel = new JPanel(new BorderLayout(5, 5));
        environmentPanel.add(autoValidateCheckbox, BorderLayout.NORTH);
        return environmentPanel;
    }

}
