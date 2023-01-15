package com.appdirect.helloworld.api

import org.springframework.boot.web.server.LocalServerPort

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration

import com.appdirect.helloworld.HelloWorldApplication
import spock.lang.Specification
import spock.lang.Stepwise

@ContextConfiguration(classes = HelloWorldApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise
class GreetingsApiImplIntegrationTest extends Specification {

	@LocalServerPort
	private int port;

	def 'Get Greeting Should Return Hello, Tom!'() {
		expect: 'Should return the correct message'
		given().port(port).contentType(APPLICATION_JSON_VALUE)
				.when()
				.get("/greetings/Tom")
				.then()
				.assertThat()
				.body("greeting", equalTo("Hello flag off, Tom!"))
	}

	@TestConfiguration
	static class MockConfig {

	}
}

