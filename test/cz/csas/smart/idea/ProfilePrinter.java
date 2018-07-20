package cz.csas.smart.idea;

import com.thoughtworks.xstream.XStream;
import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.CompletionList;
import cz.csas.smart.idea.model.Profile;

import java.io.PrintWriter;

public class ProfilePrinter {

	private final XStream xstream;

	public ProfilePrinter() {
		xstream = new XStream();
		xstream.setClassLoader(Profile.class.getClassLoader());
		xstream.processAnnotations(Profile.class);
		xstream.processAnnotations(CompletionList.class);
		xstream.processAnnotations(Completion.class);
		xstream.processAnnotations(Completion.Value.class);
	}

    public void print(Profile profile) {
		xstream.toXML(profile, new PrintWriter(System.out));
    }
}
