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

	public Completion() {
	}

	public Completion(String path, List<Value> collect) {
		this.key = path;
		this.value = collect;
	}

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
		private Boolean required;
		private String defaultValue;
		private String notes;
		private String of;
		private String text;

		public static final String STRING = "string";
		public static final String ENUM = "enum";
		public static final String BOOLEAN = "boolean";
		public static final String LONG = "long";
		public static final String INTEGER = "integer";
		public static final String OBJECT = "object";
		public static final String ARRAY = "array";

		public static final String ATTRIBUTE_NAME = "attributeName";

		public Value() {}

		public Value(String type, String text) {
			this.type = type;
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public boolean required() {
			return required == Boolean.TRUE;
		}

		public Boolean isRequired() {
			return required;
		}

		public void setRequired(Boolean required) {
			this.required = required;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getOf() {
			return of;
		}

		public String getText() {
			return text;
		}

		public String getNotes() {
			return notes;
		}

		public String toString() {
			return text;
		}
	}
}
