package com.olx.service;

import java.util.List;

import com.olx.dto.ADVStatus;
import com.olx.dto.Category;

public interface MasterService {
	List<Category> getAllCategories();
	List<ADVStatus> getAllStatus();
}
