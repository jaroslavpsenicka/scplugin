package cz.csas.smart.idea.model;

import org.apache.commons.lang.StringUtils;

public class Transition {

    private String name;
    private String source;
    private String target;

    public Transition(String name, String source, String target) {
        this.name = StringUtils.strip(name, "\"");
        this.source = StringUtils.strip(source, "\"");
        this.target = StringUtils.strip(target, "\"");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
