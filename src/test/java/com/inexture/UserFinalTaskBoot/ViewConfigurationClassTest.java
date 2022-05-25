package com.inexture.UserFinalTaskBoot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles({"profile1","dev"})
@ContextConfiguration(classes = ViewConfigurationClass.class)
@WebMvcTest
class ViewConfigurationClassTest {

	@Test
	void test() {
		
	}

}
