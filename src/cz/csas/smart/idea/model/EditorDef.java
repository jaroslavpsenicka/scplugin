package cz.csas.smart.idea.model;

import cz.csas.smart.idea.SmartCaseAPIClient;

import java.util.List;

public class EditorDef {

    private String name;
    private List<PropertyDef> properties;
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

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
