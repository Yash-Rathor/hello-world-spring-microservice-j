package com.appdirect.helloworld.api

import com.appdirect.helloworld.api.GreetingsApiImpl
import spock.lang.Specification

class GreetingsApiImplUnitTest extends Specification {

	def greetingsApi = new GreetingsApiImpl()

	def 'Get Greeting Should Return Hello, Tom!'() {
		expect: 'Should return the correct message'
		greetingsApi.getHello("Tom").getBody().getGreeting().contains('Tom!')
	}
}

