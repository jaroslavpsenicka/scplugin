package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Completion;
import cz.csas.smart.idea.model.Profile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ProfileGenerator {

    private Map<String, Object> fieldMap = new HashMap<>();

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ProfileGenerator pg = new ProfileGenerator();
        Profile profile = pg.generate(args[0], Class.forName(args[1]));
        new ProfilePrinter().print(profile);
    }

    private Profile generate(String name, Class baseClass) throws IOException {
        collectFields(baseClass, "");
        return new Profile(name, generateCompletions());
    }

    private void collectFields(Class aClass, String path) {
        fieldMap.put(path + "/", aClass);

        // basic fields
        Arrays.stream(aClass.getFields())
            .filter(f -> !"class".equals(f.getType().getName()))
            .filter(f -> f.getType().isPrimitive() || f.getType().getPackage().getName().startsWith("java.lang"))
            .forEach(f -> fieldMap.put(path + "/" + f.getName(), f));

        // custom fields
        Arrays.stream(aClass.getFields())
            .filter(f -> !"class".equals(f.getType().getName()))
            .filter(f -> !f.getType().isPrimitive() && !f.getType().getPackage().getName().startsWith("java.lang"))
            .forEach(f -> collectFields(f.getType(), path + "/" + f.getName()));

        // collections
        Arrays.stream(aClass.getFields())
            .filter(f -> !"class".equals(f.getType().getName()))
            .filter(f -> !f.getType().isPrimitive() && f.getType().getPackage().getName().startsWith("java.util"))
            .forEach(f -> collectFields(f.getType().getGenericInterfaces()[0].getClass(), path + "/" + f.getName()));
    }

    private List<Completion> generateCompletions() {
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
