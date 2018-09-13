package cz.csas.smart.idea;

import cz.csas.smart.idea.model.EnvironmentList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvironmentSerializationTest {

    @Test
    public void testDefaultEnvironments() {
        EnvironmentList envs = new EnvironmentList(getClass().getResourceAsStream("/default.environments"));
        assertEquals("INT (almost ready)", envs.getEnvironments().get(0).getName());
    }


}
