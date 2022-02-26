package com.olx.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olx.dto.ADVStatus;
import com.olx.dto.Category;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OLXMasterdataDelegateServiceImpl implements OLXMasterdataDelegateService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	@CircuitBreaker(name = "MASTERDATA-CIRCUIT-BREAKER" ,fallbackMethod = "fallbackFindAllMasterdataCategory")
	public List<Category> findAllMasterdataCategory() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		ResponseEntity<Category[]> result = this.restTemplate.exchange("http://OLX-GATEWAY/olx-masterdata/advertises/categories", HttpMethod.GET,
				entity, Category[].class);
		
		List<Category> categories=new ArrayList<>(Arrays.asList(result.getBody()));
		return categories;
	}
	
	public List<Category> fallbackFindAllMasterdataCategory(Exception ex) {
		System.out.println("inside fallbackFindAllMasterdataCategory :"+ex);
		return null;
		
	}

	@Override
	@CircuitBreaker(name = "MASTERDATA-CIRCUIT-BREAKER" ,fallbackMethod = "fallbackFindAllMasterdataStuatus")
	public List<ADVStatus> findAllMasterdataStuatus() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		ResponseEntity<ADVStatus[]> result = this.restTemplate.exchange("http://OLX-GATEWAY/olx-masterdata/advertises/status",
				HttpMethod.GET, entity, ADVStatus[].class);
		return Arrays.asList(result.getBody());
	}
	
	public List<Category> fallbackFindAllMasterdataStuatus(Exception ex) {
		System.out.println("inside fallbackFindAllMasterdataStuatus :"+ex);
		return null;
		
	}


}
