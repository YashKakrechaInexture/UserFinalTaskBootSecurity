package com.inexture.UserFinalTaskBoot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles({"profile2","dev"})
@ContextConfiguration(classes = ViewConfigurationClass2.class)
@WebMvcTest
class ViewConfigurationClass2Test {

	@Test
	void test() {
	}

}
