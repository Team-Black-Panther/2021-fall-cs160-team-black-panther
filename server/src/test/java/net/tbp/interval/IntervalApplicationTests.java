package net.tbp.interval;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import net.tbp.interval.database.MainController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntervalApplicationTests {

	/*
	@Autowired
	private MainController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	 */
}
