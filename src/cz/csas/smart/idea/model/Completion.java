package cz.csas.smart.idea.model;

import com.intellij.openapi.util.IconLoader;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

@XStreamAlias("completion")
public class Completion {

	@XStreamAlias("for")
	@XStreamAsAttribute
	private String key;

	@XStreamAlias("ref")
	@XStreamAsAttribute
	private String ref;

	@XStreamImplicit
	private List<Value> values;

	public Completion() {
	}

	public Completion(String path, List<Value> collect) {
		this.key = path;
		this.values = collect;
	}

	public String getKey() {
		return key;
	}

	public String getRef() {
		return ref;
	}

	public List<Value> getValues() {
		return values != null ? values : Collections.emptyList();
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

    @XStreamAlias("value")
	@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"text"})
	public static class Value {

		private String type;
		private Boolean required;
		private String defaultValue;
		private String notes;
		private String of;
		private String icon;
		private String text;

		public static final String STRING = "string";
		public static final String ENUM = "enum";
		public static final String BOOLEAN = "boolean";
		public static final String LONG = "long";
		public static final String INTEGER = "integer";
		public static final String OBJECT = "object";
		public static final String ARRAY = "array";

		public static final String ATTRIBUTE_NAME = "attributeName";
		public static final String ACTIVITY_NAME = "activityName";
		public static final String TASK_NAME = "taskName";
		public static final String USER_NAME = "userName";
		public static final String CURRENT_TIME = "currentTime";
		public static final String EDITOR_NAME = "editorName";
		public static final String EDITOR_PROPERTY_NAME = "editorPropertyName";

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

		public boolean isRequired() {
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

		public String getIcon() {
			return icon;
		}

		public String toString() {
			return text;
		}

		public Icon icon() {
			return icon != null && icon.length() > 0 ?
				IconLoader.getIcon("/" + icon + ".png") : null;
		}
	}
}
