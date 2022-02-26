package com.olx.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.olx.dto.AuthenticateRequest;
import com.olx.dto.User;
import com.olx.entity.BlackListedToken;
import com.olx.entity.UserEntity;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.exception.InvalidUserNameORPasswordException;
import com.olx.repository.UserLogoutMongoRepository;
import com.olx.repository.UserRepository;
import com.olx.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	UserLogoutMongoRepository userLogoutMongoRepository;

	@Override
	public String authenticate(AuthenticateRequest authenticateRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticateRequest.getUsername(), authenticateRequest.getPassword()));

		} catch (AuthenticationException ex) {
			throw new InvalidUserNameORPasswordException("" + authenticateRequest.getUsername());
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getUsername());
		String jwtToken = jwtUtil.generateToken(userDetails);
		return jwtToken;

		// String authToken="SNK002";
		// return authToken;
	}

	@Override
	public boolean validateUserToken(String authToken) {
		String token = authToken.substring(7);

		LocalDateTime currentDateTime = LocalDateTime.now();
		BlackListedToken blackListedToken = userLogoutMongoRepository.findByToken(token);
		if (blackListedToken!=null &&(currentDateTime.equals(blackListedToken.getExpirydate())
				|| currentDateTime.isAfter(blackListedToken.getExpirydate()))) {
			throw new InvalidAuthTokenException("" + authToken);
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
		return jwtUtil.validateToken(token, userDetails);

	}

	@Override
	public boolean userLogout(String authToken) {
		boolean validateToken = validateUserToken(authToken);

		if(validateToken==false) {
			throw new InvalidAuthTokenException("" + authToken);
		}
		String token = authToken.substring(7);
		Date extractExpiration = jwtUtil.extractExpiration(token);
		LocalDateTime expiryDateTime = extractExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		BlackListedToken balBlackListedToken = new BlackListedToken(1, token, LocalDateTime.now(), expiryDateTime);
		userLogoutMongoRepository.save(balBlackListedToken);

		return true;
	}

	@Override
	public User createUser(User user) {
		UserEntity userEntity = convertUserDtoToEntity(user);
		userEntity = userRepository.save(userEntity);
		user = convertUserEntityToDto(userEntity);
		return user;
	}

	private User convertUserEntityToDto(UserEntity userEntity) {
		TypeMap<UserEntity, User> typeMap = this.modelMapper.typeMap(UserEntity.class, User.class);
		User user = this.modelMapper.map(userEntity, User.class);
		return user;
	}

	private UserEntity convertUserDtoToEntity(User user) {
		TypeMap<User, UserEntity> typeMap = this.modelMapper.typeMap(User.class, UserEntity.class);
		UserEntity userEntity = this.modelMapper.map(user, UserEntity.class);
		return userEntity;
	}

	@Override
	public User getUser(String authToken) {
		boolean userToken = validateUserToken(authToken);
		if (userToken == false) {
			throw new InvalidAuthTokenException("" + authToken);
		}
		
		Optional<UserEntity> userEntity = userRepository.findById(1);

		if (userEntity.isPresent()) {
			UserEntity usEntity = userEntity.get();
			User user = convertUserEntityToDto(usEntity);
			return user;

		}
		return null;
	}

}
