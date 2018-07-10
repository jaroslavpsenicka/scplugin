package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.Profile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.reflect.ParameterizedType;

public class ProfileGenerator {

    private Map<String, Object> fieldMap = new HashMap<>();

    private static final Set<Class> FIELD_TYPES = new HashSet<Class>() {{
        add(String.class);
        add(Integer.class);
        add(Boolean.class);
        add(Long.class);
    }};

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ProfileGenerator pg = new ProfileGenerator();
        Profile profile = pg.generate(args[0], Class.forName(args[1]));
        new ProfilePrinter().print(profile);
    }

    private Profile generate(String name, Class baseClass) throws IOException {
        collectFields(baseClass, "");
        return new Profile(name, createCompletions());
    }

    private void collectFields(Class aClass, String path) {
        System.out.println("Processing " + aClass + " as " + path);
        fieldMap.put(path + "/", aClass);

        if (FIELD_TYPES.contains(aClass)) {
            return;
        }

        String aPkg = aClass.getPackage().getName();

        // basic fields
        listFields(aClass).stream()
            .filter(f -> !"class".equals(f.getName()))
            .filter(f -> !"serialVersionUID".equals(f.getName()))
            .filter(f -> f.getType().isPrimitive() || FIELD_TYPES.contains(f.getType()))
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

    private List<Completion> createCompletions() {
        return this.fieldMap.keySet().stream()
            .map(path -> createCompletion(path, fieldMap.get(path)))
            .collect(Collectors.toList());
    }

    private Completion createCompletion(String path, Object object) {
        if (object instanceof Field) {

        } else if (object instanceof Class) {

        }

        return null;
    }

}
