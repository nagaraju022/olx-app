package com.olx.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.dto.Advertise;
import com.olx.service.AdvertisesService;

@WebMvcTest(AdvertisesController.class)
public class AdvertiseControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	AdvertisesService advertisesService;

	Advertise advertise = null;

	@Test
	public void testCreateAdvertises_success() throws Exception {

		advertise = new Advertise();
		advertise.setTitle("Sofa for sale");

		Advertise exptadvertise = new Advertise();
		exptadvertise.setTitle("Sofa for sale");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "D734");

		when(this.advertisesService.createAdvertises(advertise, "D734")).thenReturn(checkEqualsOrNot(exptadvertise));

		mockMvc.perform(post("/olx-advertises/advertise").contentType("application/json")
				.content(objectMapper.writeValueAsString(advertise)).headers(headers)).andExpect(status().isCreated())
				.andExpect(content().string(containsString("Sofa"))).andReturn();

	}

	public Advertise checkEqualsOrNot(Advertise exptadvertise) {
		if (exptadvertise.equals(advertise)) {
			return exptadvertise;
		}
		return null;

	}

	@Test
	public void testGetAllAdvertises_success() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "D734");

		when(this.advertisesService.getAllAdvertises("D734")).thenReturn(Arrays.asList(
				new Advertise("Mobile for sale", 1, 1, 20000, "available for in hyderabad", LocalDate.now(),
						LocalDate.now(), 1, "siva", "naga"),
				new Advertise("Car for sale", 1, 1, 60000, "available for in banglore", LocalDate.now(),
						LocalDate.now(), 1, "john", "naga"),
				new Advertise("Sofa for sale", 1, 1, 70000, "available for in Vizg", LocalDate.now(), LocalDate.now(),
						1, "tom", "naga")));

		this.mockMvc.perform(get("/olx-advertises/user/advertise").accept("application/json").headers(headers))
				.andExpect(status().isOk()).andReturn();

	}

	public void testGetAdvertisesById() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "D734");

		Advertise actualadv = new Advertise();
		actualadv.setId(1);
		actualadv.setTitle("car");

		Advertise exptadv = new Advertise();
		exptadv.setTitle("car");

		when(this.advertisesService.getAdvertisesById(actualadv.getId(), "D734")).thenReturn(exptadv);

		this.mockMvc.perform(
				get("/olx-advertises/user/advertise/1")
				.accept("application/json")
				.headers(headers))
		 
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("car")))
		.andReturn();

	}

	@Test
	public void testUpdateAdvertisesById_success() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "RT345");

		Advertise actualadv = new Advertise();
		actualadv.setId(1);
		actualadv.setTitle("car");

		Advertise exptadv = new Advertise();
		exptadv.setId(1);
		exptadv.setTitle("car");

		when(this.advertisesService.updateAdvertisesById(actualadv.getId(), actualadv, "RT345")).thenReturn(exptadv);

		this.mockMvc
				.perform(put("/olx-advertises/user/advertise/1").accept("application/json").contentType("application/json")
						.content(objectMapper.writeValueAsString(actualadv)).headers(headers))
				.andExpect(status().isOk()).andExpect(content().string(containsString("car"))).andReturn();

	}

	@Test
	public void testDeleteAdvertisesById_success() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "RT345");
		when(this.advertisesService.deleteAdvertisesById(1, "RT345")).thenReturn(true);

		this.mockMvc.perform(
				delete("/olx-advertises/user/advertise/1").accept("application/json").headers(headers))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testSearchAdvertisesByFilterCriteria() throws Exception {

		List<Advertise> advertises = new ArrayList<Advertise>();
		advertises.add(new Advertise());
		advertises.add(new Advertise());
		when(this.advertisesService.searchAdvertisesByFilterCriteria(null, 0, null, null, null, null, null, null, 0, 0))
				.thenReturn(advertises);

		MvcResult mvcResult = mockMvc.perform(get("/olx-advertises/advertise/search/filtercriteria").accept("application/json")
				.param("categoryId", "0").param("startIndex", "0").param("records", "0")).andExpect(status().isOk())
				.andReturn();
		String response = mvcResult.getResponse().getContentAsString();
		System.out.println("response: " + response);
		assertEquals(response.contains("title"), true);
	}

	@Test
	public void testGetAdvertisesBySearchText_success() throws Exception {

		Advertise adv1 = new Advertise();
		adv1.setTitle("car");

		Advertise adv2 = new Advertise();
		adv2.setTitle("car");

		when(this.advertisesService.getAdvertisesBySearchText(adv1.getTitle())).thenReturn(Arrays.asList(adv1, adv2));
		this.mockMvc.perform(get("/olx-advertises/advertise/search").accept("application/json").param("searchText", "car")).andExpect(status().isOk())
				.andReturn();

	}

}
