package com.olx.service;

import java.time.LocalDate;
import java.util.List;

import com.olx.dto.Advertise;

public interface AdvertisesService {

	Advertise createAdvertises(Advertise advertises ,String authToken );

	Advertise updateAdvertisesById(int advertiseId,Advertise advertises  ,String authToken );
	List<Advertise> getAllAdvertises( String authToken );
	Advertise getAdvertisesById(int advertiseId ,String authToken );
	boolean deleteAdvertisesById(int advertiseId ,String authToken );
	List<Advertise> getAdvertisesBySearchText(String searchText) ;
	Advertise getAdvertiseById(int id);

	List<Advertise> searchAdvertisesByFilterCriteria(String searchText, int categoryId, String postedBy,
			String dateCondition, LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortedBy,
			int startIndex, int records);

}
