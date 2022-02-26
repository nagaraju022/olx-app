package com.olx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.olx.entity.ADVStatusEntity;
import com.olx.entity.CategoryEntity;
import com.olx.repository.ADVStatusRepository;
import com.olx.repository.CategoryRepository;

@Service
public class MyRunner implements CommandLineRunner {
	

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ADVStatusRepository advStatusRepository;


	@Override
	public void run(String... args) throws Exception {

		/*
		 * List<CategoryEntity> categoriesEntities=getAllCategories();
		 * categoryRepository.saveAll(categoriesEntities);
		 * 
		 * List<ADVStatusEntity> adList = getAllADVStatus();
		 * advStatusRepository.saveAll(adList);
		 */
	}

	private List<CategoryEntity> getAllCategories() {

		List<CategoryEntity> caList = new ArrayList<CategoryEntity>(
				Arrays.asList(new CategoryEntity("Furniture", "Furniture purchage"),
						new CategoryEntity("Mobile", "Mobile purchage"), new CategoryEntity("Car", "Car purchage")));
		return caList;
	}
	
	private List<ADVStatusEntity> getAllADVStatus() {
		List<ADVStatusEntity> advStatusEntities = new ArrayList<>(
				Arrays.asList(new ADVStatusEntity("OPEN"), new ADVStatusEntity("CLOSED"), new ADVStatusEntity("OPEN")));

		return advStatusEntities;
	}


}
