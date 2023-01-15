package com.appdirect.helloworld.contracttest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import au.com.dius.pact.provider.junit.loader.PactBroker;
import com.appdirect.helloworld.HelloWorldApplication;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HelloWorldApplication.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactBroker(tags = "${pactbroker.tags}")
public abstract class ContractTest {

}
