package com.olx.actuator;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.olx.dto.Advertise;
import com.olx.entity.AdvertiseEntity;
import com.olx.repository.AdvertisesRepository;

@Component
@Endpoint(id ="orders" )
public class CustomAdvertiseActuator {
	@Autowired
	AdvertisesRepository advertisesRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@ReadOperation
	public ArrayList<Advertise> getAllAdvertises(){
		ArrayList<Advertise> adList=new ArrayList<Advertise>();
		ArrayList<AdvertiseEntity> advertisesEntities = (ArrayList<AdvertiseEntity>) advertisesRepository.findAll();
		for (AdvertiseEntity advertisesEntity : advertisesEntities) {
			Advertise advertises=convertAdvertisesEntityToDto(advertisesEntity);
			adList.add(advertises);
		}
		return adList;
		
	}
	private Advertise convertAdvertisesEntityToDto(AdvertiseEntity advertisesEntity) {
		Advertise advertises=this.modelMapper.map(advertisesEntity, Advertise.class);
		return advertises;
	}
}
