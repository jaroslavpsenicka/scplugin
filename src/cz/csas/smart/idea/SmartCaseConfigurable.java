package cz.csas.smart.idea;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import cz.csas.smart.idea.model.Profile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SmartCaseConfigurable implements Configurable {

    private JPanel component;
    private JComboBox<Profile> profileCombo;
    private JLabel profileDescription;
    private JButton profileReload;
	private JButton profileOpenInExplorer;
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
	    profileCombo = new JComboBox<>();
	    ProfileComponent.getInstance().getProfiles().forEach(p -> profileCombo.addItem(p));
	    profileCombo.setSelectedItem(ProfileComponent.getInstance().getActiveProfile());
	    profileCombo.addActionListener(actionEvent -> {
		    Profile profile = (Profile) profileCombo.getSelectedItem();
		    if (profile != null) {
				profileReload.setEnabled(profile.canReload());
				profileOpenInExplorer.setEnabled(Desktop.isDesktopSupported() && profile.canReload());
			}
		    profileDescription.setText(profile != null ? profile.getDescription() : "");
	    	isDirty = true;
	    });

	    JLabel profileLabel = new JLabel("Completion Profile:");
	    profileLabel.setLabelFor(profileCombo);

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

	    profileDescription = new JLabel(ProfileComponent.getInstance().getActiveProfile().getDescription());
		profileDescription.setForeground(Color.lightGray);

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

		JPanel profilePanel = new JPanel(new BorderLayout(10, 10));
        profilePanel.add(profileLabel, BorderLayout.WEST);
	    profilePanel.add(profileCombo, BorderLayout.CENTER);
	    JPanel profileButtons = new JPanel();
		profileButtons.add(profileOpenInExplorer);
	    profileButtons.add(profileReload);
	    profileButtons.add(profileNew);
	    JPanel profileInfo = new JPanel(new BorderLayout());
	    profileInfo.add(profileDescription, BorderLayout.CENTER);
	    profileInfo.add(profileButtons, BorderLayout.EAST);
	    profilePanel.add(profileInfo, BorderLayout.SOUTH);

        component = new JPanel(new BorderLayout());
        component.add(profilePanel, BorderLayout.NORTH);
        return component;
    }

    @Override
    public boolean isModified() {
        return isDirty;
    }

    @Override
    public void apply() {
	    ProfileComponent.getInstance().setActiveProfile((Profile) profileCombo.getSelectedItem());
    }

    @Override
    public void reset() {
	    profileCombo.setSelectedItem(ProfileComponent.getInstance().getActiveProfile());
    }

    @Override
    public void disposeUIResources() {
        component = null;
    }

}
