package cz.csas.smart.idea.model;

public class NameType {

    private String name;
    private String type;

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

	public boolean isType(String type) {
		return type == null || type.equals(this.type);
	}
}
