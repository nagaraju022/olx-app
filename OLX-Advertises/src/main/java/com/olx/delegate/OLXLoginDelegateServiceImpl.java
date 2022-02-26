package com.olx.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OLXLoginDelegateServiceImpl implements OLXLoginDelegateService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	@CircuitBreaker(name = "VALIDATE-CIRCUIT-BREAKER", fallbackMethod = "fallbackIsTokenValid")
	public boolean isTokenValid(String authToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<Boolean> result = this.restTemplate
				.exchange("http://OLX-GATEWAY/olx-login/user/token/validate", HttpMethod.GET, entity, Boolean.class);
		return result.getBody();
	}
	
	
	public boolean fallbackIsTokenValid(String authToken,Exception ex) {
		System.out.println("inside fallbackIsTokenValid :"+ex);
		return false;
		
	}

}
