package com.olx.actuator;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.olx.dto.User;
import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;

@Component
@Endpoint(id = "users")
public class CustomUserActuator {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@ReadOperation
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		List<UserEntity> userEntities = userRepository.findAll();
		for (UserEntity userEntity : userEntities) {
			User user = convertUserEntityToDto(userEntity);
			users.add(user);
		}
		return users;

	}

	private User convertUserEntityToDto(UserEntity userEntity) {
		TypeMap<UserEntity, User> typeMap = this.modelMapper.typeMap(UserEntity.class, User.class);
		User user = this.modelMapper.map(userEntity, User.class);
		return user;
	}

}
