package cz.csas.smart.idea.model;

public class NameType {

    private String name;
    private String type;
    private boolean important;

    public NameType(String name, String tail) {
        this.name = name;
        this.type = tail;
    }

    public NameType(String name, String tail, boolean important) {
        this(name, tail);
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean important() {
        return important;
    }

    public NameType typeOf(String type) {
        this.important = type == null || type.equals(this.type);
        return this;
    }

    public String toString() {
        return name + " " + important;
    }
}
