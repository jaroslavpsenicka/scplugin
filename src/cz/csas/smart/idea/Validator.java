package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.Violation;

import java.util.Arrays;
import java.util.List;

public class Validator {

    public List<Violation> validate(Environment env) {
        System.out.println("Validating...");
        return Arrays.asList(
            new Violation("/attributes", "Stupid attributes")
        );
    }
}
