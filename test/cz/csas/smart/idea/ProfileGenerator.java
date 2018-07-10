package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.Profile;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ProfileGenerator {

    private Map<String, Object> fieldMap = new HashMap<>();

    private static final Set<Class> FIELD_TYPES = new HashSet<Class>() {{
        add(String.class);
        add(Integer.class);
        add(Boolean.class);
        add(Long.class);
        add(Date.class);
    }};

	private Comparator<Completion.Value> byFieldNameRequiredFirst = (field1, field2) -> {
		return !field1.required() && field2.required() ? 1 :
			field1.required() && !field2.required() ? -1 :
			field1.getText().compareTo(field2.getText());
	};

	public static void main(String[] args) throws ClassNotFoundException, IOException {
        ProfileGenerator pg = new ProfileGenerator();
        Profile profile = pg.generate(args[0], Class.forName(args[1]));
        new ProfilePrinter().print(profile);
    }

    private Profile generate(String name, Class baseClass) throws IOException {
        collectFields(baseClass, "");
        return new Profile(name, this.fieldMap.keySet().stream()
	        .sorted()
	        .map(path -> createCompletion(path, fieldMap.get(path)))
	        .collect(Collectors.toList()));
    }

    private void collectFields(Class aClass, String path) {
        System.out.println("Processing " + aClass + " as " + path);
	    if (FIELD_TYPES.contains(aClass)) {
		    return;
	    }

	    fieldMap.put(path, aClass);
	    String aPkg = aClass.getPackage().getName();

        // basic fields
        listFields(aClass).stream()
            .filter(f -> !"class".equals(f.getName()))
            .filter(f -> !"serialVersionUID".equals(f.getName()))
            .filter(f -> f.getType().isPrimitive() || FIELD_TYPES.contains(f.getType()))
            .map(this::logField)
            .forEach(f -> fieldMap.put(path + "/" + f.getName(), f));

	    // enums
	    listFields(aClass).stream()
		    .filter(f -> f.getType().isEnum())
		    .map(this::logField)
		    .forEach(f -> fieldMap.put(path + "/" + f.getName(), f));

	    // custom fields
        listFields(aClass).stream()
            .filter(f -> !"class".equals(f.getName()))
            .filter(f -> !f.getType().isPrimitive())
            .filter(f -> !f.getType().isEnum())
            .filter(f -> f.getType().getPackage() != null && f.getType().getPackage().getName().startsWith(aPkg))
            .map(this::logField)
            .forEach(f -> collectFields(f.getType(), path + "/" + f.getName()));

        // collections
        listFields(aClass).stream()
            .filter(f -> !"class".equals(f.getType().getName()))
            .filter(f -> !f.getType().isPrimitive())
            .filter(f -> !f.getType().isEnum())
            .filter(f -> f.getType().getPackage() != null && f.getType().getPackage().getName().startsWith("java.util"))
            .map(this::logField)
            .forEach(f -> collectFields(extractGenericType(f), path + "/" + f.getName()));
    }

    private List<Field> listFields(Class aClass) {
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
            aClass = aClass.getSuperclass();
        } while (aClass != null);

        return fields;
    }

    private Field logField(Field f) {
        System.out.println("- " + f.getName() + " " + f.getType());
        return f;
    }

    private Class extractGenericType(Field f) {
        Type type = f.getGenericType();
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        return (Class) type;
    }

    private Completion createCompletion(String path, Object object) {
        if (object instanceof Field) {
        	Field field = (Field) object;
        	if (field.getType().isEnum()) {
				return new Completion(path, Arrays.stream(field.getType().getEnumConstants())
					.map(e -> new Completion.Value(Completion.Value.ENUM, e.toString()))
					.collect(Collectors.toList()));
	        }
        } else if (object instanceof Class) {
	        return new Completion(path, listFields((Class) object).stream()
		        .filter(f -> !"class".equals(f.getName()))
		        .filter(f -> !"serialVersionUID".equals(f.getName()))
		        .map(f -> createValue(f))
		        .sorted(byFieldNameRequiredFirst)
		        .collect(Collectors.toList()));
        }

        return new Completion(path, Collections.emptyList());
    }

	private Completion.Value createValue(Field field) {
		Completion.Value value = new Completion.Value(createType(field.getType()), field.getName());
		value.setRequired((field.getAnnotation(NotNull.class) != null || field.getAnnotation(NotEmpty.class) != null) ? true : null);
		value.setDefaultValue(field.getAnnotation(Min.class) != null ? String.valueOf(field.getAnnotation(Min.class).value()) : null);
		return value;
	}

	private String createType(Class fieldType) {
		return String.class.equals(fieldType) ? Completion.Value.STRING :
			Boolean.class.equals(fieldType) ? Completion.Value.BOOLEAN :
			Long.class.equals(fieldType) ? Completion.Value.LONG :
			Integer.class.equals(fieldType) ? Completion.Value.INTEGER :
			Collection.class.isAssignableFrom(fieldType) ? Completion.Value.ARRAY :
			Completion.Value.OBJECT;
	}

}
