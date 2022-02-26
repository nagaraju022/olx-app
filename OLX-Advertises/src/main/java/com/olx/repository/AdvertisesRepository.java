package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.olx.entity.AdvertiseEntity;

@Repository
public interface AdvertisesRepository extends  JpaRepository<AdvertiseEntity, Integer> {
	
	
	
	List<AdvertiseEntity> findByTitleContaining(String searchText);
	List<AdvertiseEntity> findByDescriptionContaining(String searchText);
	
	
	
	

}
