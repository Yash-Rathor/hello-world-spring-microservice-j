package com.appdirect.helloworld.contracttest;

import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.server.LocalServerPort;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

@Provider("hello-world-spring-microservice")
@Category({ ContractTest.class })
@IgnoreNoPactsToVerify
public class HelloWorldGreetingsContractTest extends ContractTest {

    @LocalServerPort
    public int port;


  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void testTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @BeforeEach
  public void setup(PactVerificationContext context) {
    System.setProperty("pact.verifier.publishResults", "true");
    context.setTarget(new HttpTestTarget("localhost", port, "/"));
  }

  @State("A request for a Greeting message")
    public void testArequestForGreetingMessage() {

  }

}
