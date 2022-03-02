package com.vora.analytics;

import com.vora.analytics.controller.FibonacciController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AnalyticsApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private FibonacciController fibController;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(fibController).isNotNull();
	}

	@Test
	void fibSequenceFirstTerm() throws Exception{
		assertThat(this.restTemplate
				.getForObject("http://localhost:"+port+"/api/v1/fibonacci/1",String.class))
				.contains("\"value\":1");
	}

	@Test
	void fibSequenceSecondTerm() throws Exception{
		assertThat(this.restTemplate
				.getForObject("http://localhost:"+port+"/api/v1/fibonacci/2",String.class))
				.contains("\"value\":1");
	}

	@Test
	void fibSequenceFifthTerm() throws Exception{
		assertThat(this.restTemplate
				.getForObject("http://localhost:"+port+"/api/v1/fibonacci/5",String.class))
				.contains("\"value\":5");
	}

}
