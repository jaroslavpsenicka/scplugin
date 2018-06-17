package cz.csas.smart.idea.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XStreamAlias("profile")
public class Profile {

	private String name;
	private List<Completion> completions;
	private Map<String, List<Completion.Value>> completionMap;

	public Profile(InputStream stream) {
		readStream(stream);
	}

	public Profile(String path) throws IOException {
		try (InputStream stream = new FileInputStream(new File(path))) {
			readStream(stream);
		}
	}

	public Profile(VirtualFile file) {
		try {
			readStream(file.getInputStream());
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public String getName() {
		return name;
	}

	public List<Completion> getCompletions() {
		return completions;
	}

	public List<Completion.Value> getCompletionsForPath(String path) {
		return completionMap.get(path);
	}

	private Map<String, List<Completion.Value>> getCompletionMap() {
		return completions.stream().collect(Collectors.toMap(Completion::getKey, Completion::getValue));
	}

	private void readStream(InputStream stream) {
		XStream xstream = new XStream();
		xstream.setClassLoader(Profile.class.getClassLoader());
		xstream.processAnnotations(Profile.class);
		xstream.processAnnotations(CompletionList.class);
		xstream.processAnnotations(Completion.class);
		xstream.processAnnotations(Completion.Value.class);

		Profile profile = (Profile) xstream.fromXML(stream);
		this.name = profile.getName();
		this.completions = profile.getCompletions();
		this.completionMap = profile.getCompletionMap();
	}

	public String toString() {
		return name;
	}


}
