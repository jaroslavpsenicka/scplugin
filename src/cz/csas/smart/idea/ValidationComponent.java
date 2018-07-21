package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import cz.csas.smart.idea.model.EditorDef;
import cz.csas.smart.idea.model.Violation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationComponent {

	private Map<String, Violation> currentViolations = new HashMap<>();

	private static ValidationComponent instance = new ValidationComponent();
	public static final String AUTOVALIDATE = "cz.csas.smart.idea.autoValidate";

	public static final ValidationComponent getInstance() {
		return instance;
	}

	public ValidationComponent() {
	}

	public Map<String, Violation> getCurrentViolations() {
		return currentViolations;
	}

	public void setCurrentViolations(List<Violation> violations) {
		this.currentViolations = violations.stream()
			.collect(Collectors.toMap(Violation::getPath, v -> v));
	}

	public boolean isAutoValidate() {
		String value = PropertiesComponent.getInstance().getValue(AUTOVALIDATE);
		return (value != null) ? Boolean.valueOf(value) : true;
	}

	public void setAutoValidate(boolean autoValidate) {
		PropertiesComponent.getInstance().setValue(AUTOVALIDATE, String.valueOf(autoValidate));
	}
}
