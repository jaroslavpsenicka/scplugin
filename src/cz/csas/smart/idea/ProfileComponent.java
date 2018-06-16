package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.model.Profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileComponent {

	private List<Profile> profiles;

	private static ProfileComponent instance = new ProfileComponent();

	public static final String ACTIVE_PROFILE_NAME = "cz.csas.smart.idea.activeProfile";
	public static final String CUSTOM_PROFILES = "cz.csas.smart.idea.customProfiles";

	public static final ProfileComponent getInstance() {
		return instance;
	}

	private ProfileComponent() {
		profiles = new ArrayList<Profile>() {{
			add(new Profile(getClass().getResourceAsStream("/default.profile")));
			String[] customProfiles = PropertiesComponent.getInstance().getValues(CUSTOM_PROFILES);
			if (customProfiles != null) Arrays.stream(customProfiles).forEach(p -> {
				try {
					add(new Profile(p));
				} catch (IOException ex) {
					Notifications.Bus.notify(new Notification("SmartCase", "Profile loading error",
					"Error loading profile file: " + p, NotificationType.WARNING));
				}
			});
		}};
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public Profile getActiveProfile() {
		String activeProfile = PropertiesComponent.getInstance().getValue(ACTIVE_PROFILE_NAME);
		if (activeProfile != null) {
			for (int i = 0; i < profiles.size(); i++) {
				if (activeProfile.equals(profiles.get(i).getName())) {
					return profiles.get(i);
				}
			}
		}

		return profiles.get(0);
	}

	public void setActiveProfile(Profile profile) {
		PropertiesComponent.getInstance().setValue(ACTIVE_PROFILE_NAME, profile.getName());
	}

	public List<Profile> addProfiles(VirtualFile[] files) {
		if (files != null && files.length > 0) {
			List<Profile> profiles = Arrays.stream(files)
				.map(Profile::new)
				.collect(Collectors.toList());
			this.profiles.addAll(profiles);
			return profiles;
		}

		return Collections.emptyList();
	}
}
