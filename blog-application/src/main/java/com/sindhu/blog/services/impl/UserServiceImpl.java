package com.sindhu.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sindhu.blog.entities.User;
import com.sindhu.blog.exceptions.ResourceNotFoundException;
import com.sindhu.blog.payloads.UserDto;
import com.sindhu.blog.repositories.UserRepo;
import com.sindhu.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper; // to convert one class object to another
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User"," id ",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User"," id ",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User"," id ",userId));
		
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		/*
			User user = new User();
			user.setId(userDto.getId());
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setAbout(userDto.getAbout());
			user.setPassword(userDto.getPassword());
		*/
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto dto = this.modelMapper.map(user, UserDto.class);
		/*
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setName(user.getName());
			dto.setEmail(user.getEmail());
			dto.setAbout(user.getAbout());
			dto.setPassword(user.getPassword());
		*/
		
		return dto;
	}

}







