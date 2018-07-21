package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.EnvironmentList;

import java.util.ArrayList;
import java.util.List;

public class UserComponent {

	private String user = System.getenv().get("USERNAME");

	private static UserComponent instance = new UserComponent();

	public static final UserComponent getInstance() {
		return instance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
