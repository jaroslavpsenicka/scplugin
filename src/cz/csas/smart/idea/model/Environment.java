package cz.csas.smart.idea.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("environment")
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"url"})
public class Environment {

	@XStreamAsAttribute
	private String name;

	private String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return name;
	}
}
