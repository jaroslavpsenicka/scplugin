package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.EnvironmentList;

import java.util.ArrayList;
import java.util.List;

public class UserComponent {

	private static UserComponent instance = new UserComponent();

	public static final UserComponent getInstance() {
		return instance;
	}

	public String getUser() {
		return System.getenv().get("USERNAME");
	}
}
