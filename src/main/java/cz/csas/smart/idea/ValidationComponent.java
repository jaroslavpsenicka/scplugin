package cz.csas.smart.idea;

import com.intellij.ide.util.PropertiesComponent;
import cz.csas.smart.idea.model.Violation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationComponent {

    public static final String AUTOVALIDATE = "cz.csas.smart.idea.autoValidate";
    private static ValidationComponent instance = new ValidationComponent();
    private Map<String, Map<String, Violation>> currentViolations = new HashMap<>();

    public ValidationComponent() {
    }

    public static final ValidationComponent getInstance() {
        return instance;
    }

    public Map<String, Violation> getViolations(String file) {
        return currentViolations.get(file);
    }

    public void setViolations(String file, List<Violation> violations) {
        this.currentViolations.put(file, violations.stream()
                .collect(Collectors.toMap(Violation::getPath, v -> v)));
    }

    public boolean isAutoValidate() {
        String value = PropertiesComponent.getInstance().getValue(AUTOVALIDATE);
        return (value != null) ? Boolean.valueOf(value) : true;
    }

    public void setAutoValidate(boolean autoValidate) {
        PropertiesComponent.getInstance().setValue(AUTOVALIDATE, String.valueOf(autoValidate));
    }
}
