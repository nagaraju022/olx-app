package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.ADVStatus;
import com.olx.dto.Category;
import com.olx.entity.ADVStatusEntity;
import com.olx.entity.CategoryEntity;
import com.olx.repository.ADVStatusRepository;
import com.olx.repository.CategoryRepository;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ADVStatusRepository advStatusRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Category> getAllCategories() {

		List<CategoryEntity> categoriesEntities = categoryRepository.findAll();
		List<Category> catList = new ArrayList<Category>();

		for (CategoryEntity categoriesEntity : categoriesEntities) {
			Category categories = convertCategoryEntityToDto(categoriesEntity);
			catList.add(categories);
		}

		return catList;
	}

	private Category convertCategoryEntityToDto(CategoryEntity categoriesEntities) {
		Category categories = this.modelMapper.map(categoriesEntities, Category.class);
		return categories;
	}

	@Override
	public List<ADVStatus> getAllStatus() {
		List<ADVStatusEntity> advList = advStatusRepository.findAll();

		List<ADVStatus> advstList = new ArrayList<ADVStatus>();
		for (ADVStatusEntity advStatusEntity : advList) {
			ADVStatus advStatus = convertAdvStatusEntityToDto(advStatusEntity);
			advstList.add(advStatus);
		}

		return advstList;
	}

	private ADVStatus convertAdvStatusEntityToDto(ADVStatusEntity advStatusEntity) {
		ADVStatus advStatus = this.modelMapper.map(advStatusEntity, ADVStatus.class);
		return advStatus;
	}

}
