package cz.csas.smart.idea.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@XStreamAlias("environments")
public class EnvironmentList {

    @XStreamImplicit
    private List<Environment> environments = new ArrayList<>();

    public EnvironmentList(InputStream stream) {
        XStream xstream = new XStream();
        xstream.setClassLoader(EnvironmentList.class.getClassLoader());
        xstream.processAnnotations(EnvironmentList.class);
        xstream.processAnnotations(Environment.class);

        EnvironmentList environmentList = (EnvironmentList) xstream.fromXML(stream);
        this.environments = environmentList.getEnvironments();
    }

    public List<Environment> getEnvironments() {
        return environments;
    }
}
