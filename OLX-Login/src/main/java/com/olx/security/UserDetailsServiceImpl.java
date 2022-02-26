package com.olx.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserEntity> userEntities = userRepository.findByUsername(username);

		if (userEntities.isEmpty() || userEntities == null) {
			throw new UsernameNotFoundException(username);
		}

		UserEntity userEntity = userEntities.get(0);

		List<GrantedAuthority> authority = new ArrayList<>();
		String roles = userEntity.getRoles();
		authority.add(new SimpleGrantedAuthority(roles));

		UserDetails userDetails = new User(username, passwordEncoder.encode(userEntity.getPassword()), authority);

		return userDetails;
	}


}
