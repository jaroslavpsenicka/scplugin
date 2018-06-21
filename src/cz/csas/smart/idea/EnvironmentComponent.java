package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.EnvironmentList;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentComponent {

	private List<Environment> environments;

	private static EnvironmentComponent instance = new EnvironmentComponent();

	public static final String ACTIVE_ENVIRONMENT_NAME = "cz.csas.smart.idea.activeEnvironment";
	public static final String AUTODEPLOY = "cz.csas.smart.idea.autoDeploy";

	public static final EnvironmentComponent getInstance() {
		return instance;
	}

	public EnvironmentComponent() {
		environments = new ArrayList<Environment>() {{
			addAll(new EnvironmentList(getClass().getResourceAsStream("/default.environments")).getEnvironments());
		}};
	}

	public List<Environment> getEnvironments() {
		return environments;
	}

	public Environment getActiveEnvironment() {
		String activeEnvironmentName = PropertiesComponent.getInstance().getValue(ACTIVE_ENVIRONMENT_NAME);
		if (activeEnvironmentName != null) {
			for (int i = 0; i < environments.size(); i++) {
				if (activeEnvironmentName.equals(environments.get(i).getName())) {
					return environments.get(i);
				}
			}
		}

		return environments.get(0);
	}

	public void setActiveEnvironment(Environment environment) {
		PropertiesComponent.getInstance().setValue(ACTIVE_ENVIRONMENT_NAME, environment.getName());
	}

	public boolean isAutoDeploy() {
		String value = PropertiesComponent.getInstance().getValue(AUTODEPLOY);
		return (value != null) ? Boolean.valueOf(value) : true;
	}

	public void setAutoDeploy(boolean autoDeploy) {
		PropertiesComponent.getInstance().setValue(AUTODEPLOY, String.valueOf(autoDeploy));
	}
}
