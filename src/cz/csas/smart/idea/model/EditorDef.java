package cz.csas.smart.idea.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditorDef {

    private String name;
    private List<PropertyDef> properties;
    private Map<String, ModelDef> model = new HashMap<>();
    private String application;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyDef> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDef> properties) {
        this.properties = properties;
    }

    public Map<String, ModelDef> getModel() {
        return model;
    }

    public void setModel(Map<String, ModelDef> model) {
        this.model = model;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
