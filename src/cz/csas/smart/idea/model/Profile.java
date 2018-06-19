package cz.csas.smart.idea.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

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
	private Map<String, List<Completion.Value>> completionMap;

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
		return completionMap.get(path);
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
		this.description = profile.getDescription();
		this.completionMap = profile.getCompletionMap();
	}

	public String toString() {
		return name + ((description != null) ? " (" + description + ")" : "");
	}


}
