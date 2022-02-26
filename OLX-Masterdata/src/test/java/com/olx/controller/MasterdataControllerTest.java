package com.olx.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.dto.ADVStatus;
import com.olx.dto.Category;
import com.olx.service.MasterService;

@WebMvcTest(MasterdataController.class)
public class MasterdataControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	MasterService masterService;
	
	@Test
	public void testGetAllCategories_success() throws Exception{
		List<Category> categories=new ArrayList<Category>(
				Arrays.asList(new Category("car","marthi car"),new Category("bike","suzki bike")));
		when(this.masterService.getAllCategories()).thenReturn(categories);
		MvcResult mvcResult = mockMvc.perform(get("/olx-masterdata/advertises/categories")
				.accept("application/json")
				)
		.andExpect(status().isOk())
		.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println("response: " + response);
		assertEquals(response.contains("description"), true);

		
	}
	@Test
	public void testGetAllStatus_success() throws Exception{
		List<ADVStatus> advStatus=new ArrayList<ADVStatus>(
				Arrays.asList(new ADVStatus("open"),new ADVStatus("closed")));
		when(this.masterService.getAllStatus()).thenReturn(advStatus);
		MvcResult mvcResult = mockMvc.perform(get("/olx-masterdata/advertises/status")
				.accept("application/json")
				)
				.andExpect(status().isOk())
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println("response: " + response);
		assertEquals(response.contains("status"), true);
		
		
	}
}
