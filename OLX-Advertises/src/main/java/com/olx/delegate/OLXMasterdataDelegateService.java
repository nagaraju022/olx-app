package com.olx.delegate;

import java.util.List;

import com.olx.dto.ADVStatus;
import com.olx.dto.Category;

public interface OLXMasterdataDelegateService {
	
	List<Category> findAllMasterdataCategory();
	List<ADVStatus> findAllMasterdataStuatus();

}
