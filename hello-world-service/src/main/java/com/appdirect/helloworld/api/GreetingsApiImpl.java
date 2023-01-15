package com.appdirect.helloworld.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.configr.sdk.spring.bean.FeatureFlagContext;
import com.appdirect.configr.sdk.spring.service.FeatureFlagService;
import com.appdirect.helloworld.model.GreetingWsDTO;

@RestController
public class GreetingsApiImpl implements GreetingsApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(GreetingsApiImpl.class);


	@Autowired
	private FeatureFlagService featureFlagService;

	@Override
	public ResponseEntity<GreetingWsDTO> getHello(@PathVariable("userName") String userName) {

		if (getHelloworldFlag()) {
			return ResponseEntity.ok(new GreetingWsDTO().greeting("Hello flag on, " + userName + "!"));
		} else {
			return ResponseEntity.ok(new GreetingWsDTO().greeting("Hello flag off, " + userName + "!"));
		}
	}

	private boolean getHelloworldFlag() {

		try {
			return featureFlagService.isFlagOn("helloworld",false, FeatureFlagContext.builder().build());

		} catch (Exception flagException) {

			LOGGER.error(String.format("could not get value of flag: helloworld"), flagException);
			return false;

		}
	}
}
