package cz.csas.smart.idea.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import java.util.List;

@XStreamAlias("completion")
public class Completion {

	@XStreamAlias("for")
	@XStreamAsAttribute
	private String key;

	@XStreamImplicit
	private List<Value> value;

	public String getKey() {
		return key;
	}

	public List<Value> getValue() {
		return value;
	}

	public void addValue(Value value) {
		this.value.add(value);
	}

	@XStreamAlias("value")
	@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"text"})
	public static class Value {

		private String type;
		private String defaultValue;
		private String text;

		public static final String STRING = "string";
		public static final String LONG = "long";
		public static final String INTEGER = "integer";
		public static final String OBJECT = "object";
		public static final String ARRAY = "array";

		public String getType() {
			return type;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public String getText() {
			return text;
		}

		public String toString() {
			return text;
		}
	}
}
