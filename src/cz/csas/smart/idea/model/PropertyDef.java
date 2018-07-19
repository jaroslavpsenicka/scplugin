package cz.csas.smart.idea.model;

public class PropertyDef {

    private String name;
    private String label;
    private Boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean required() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
