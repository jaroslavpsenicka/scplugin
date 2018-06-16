package cz.csas.smart.idea.model;

import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Profile {

	private String name;

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

	private void readStream(InputStream stream) {
		XStream xstream = new XStream();
		xstream.setClassLoader(Profile.class.getClassLoader());
		xstream.alias("profile", Profile.class);
		Profile profile = (Profile) xstream.fromXML(stream);
		this.name = profile.getName();
	}


	public String toString() {
		return name;
	}
}
