package cz.csas.smart.idea;

import cz.csas.smart.idea.model.Profile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileSerializationTest {

	@Test
	public void testDefaultProfile() {
		Profile profile = new Profile(getClass().getResourceAsStream("/default.profile"));
		assertEquals("Default Profile", profile.getName());
		assertEquals("[name, label, description, revision, caseType, createdBy, createDate, " +
			"presentationSubject, emailConfigurations, configurations, header, overview, attributes, tasks]",
			profile.getCompletionsForPath("/").toString());
	}


}
