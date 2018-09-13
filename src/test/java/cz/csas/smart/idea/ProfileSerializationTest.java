package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Profile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileSerializationTest {

    @Test
    public void testDefaultProfile() {
        Profile profile = new Profile(getClass().getResourceAsStream("/default.profile"));
        assertEquals("default", profile.getName());
        assertEquals("[attributes, createDate, createdBy, label, name, overview, revision, caseType, authorizedRoles, categorizationExpression, configurations, description, domain, emailConfigurations, expressions, header, importConfigurations, presentationSubject, requisition, serviceMocks, shredding, tags, tasks, transitions, uniqueHashAttributes, uniqueHashAttributesExpression, validationType]",
                profile.getCompletionsForPath("/").toString());
    }


}
