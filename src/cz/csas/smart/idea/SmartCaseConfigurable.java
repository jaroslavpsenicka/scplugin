package cz.csas.smart.idea;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import cz.csas.smart.idea.model.Profile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SmartCaseConfigurable implements Configurable {

    private JPanel component;
    private JComboBox<Profile> profileCombo;
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
	    profileCombo.addActionListener(actionEvent -> isDirty = true);

	    JLabel profileLabel = new JLabel("Profile:");
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

        JPanel profilePanel = new JPanel(new BorderLayout(10, 10)) {{
	        add(profileLabel, BorderLayout.WEST);
	        add(profileCombo, BorderLayout.CENTER);
	        add(new JPanel(new BorderLayout(10, 10)) {{
		        add(profileNew, BorderLayout.EAST);
	        }});
        }};

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
