package cz.csas.smart.idea.model;

public class NameType {

    private String name;
    private String type;
    private boolean important;

    public NameType() {}

    public NameType(String name, String tail) {
        this.name = name;
        this.type = tail;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isImportant() {
        return important;
    }

    public boolean isType(String type) {
		return type == null || type.equals(this.type);
	}

    public NameType typeOf(String ofType) {
        this.important = this.isType(ofType);
        return this;
    }
}
