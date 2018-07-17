package cz.csas.smart.idea.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XStreamAlias("profile")
public class Profile {

	private String path;

	private String name;
	private String description;
	private List<Completion> completions = new ArrayList<>();
	private Map<String, Completion> completionMap;

	public Profile(String name, List<Completion> completions) {
		this.name = name;
		this.completions = completions;
	}

	public Profile(InputStream stream) {
		readStream(stream);
	}

	public Profile(String path) throws IOException {
		this.path = path;
		try (InputStream stream = new FileInputStream(new File(path))) {
			readStream(stream);
		}
	}

	public Profile(VirtualFile file) {
		this.path = file.getPath();
		try {
			readStream(file.getInputStream());
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPath() {
		return path;
	}

	public List<Completion> getCompletions() {
		return completions;
	}

	public List<Completion.Value> getCompletionsForPath(String path) {
		return completionMap.containsKey(path) ? completionMap.get(path).getValues() : null;
	}

	public boolean canReload() {
		return path != null;
	}

	public void reload() {
		try (InputStream stream = new FileInputStream(new File(path))) {
			readStream(stream);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Problem reloading the profile (" + ex.getMessage() +
				"), make sure the file exists and is readable.");
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name + ((description != null) ? " (" + description + ")" : "");
	}

	private void processCompletions() {
		this.completionMap = completions.stream().collect(Collectors.toMap(Completion::getKey, c -> c));
		this.completions.stream()
			.filter(c -> StringUtils.isNotEmpty(c.getRef()))
			.filter(c -> this.completionMap.containsKey(c.getRef()))
			.forEach(c -> c.setValues(this.completionMap.get(c.getRef()).getValues()));
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
		this.description = profile.getDescription();
		this.completions = profile.getCompletions();
		this.processCompletions();
	}

}
