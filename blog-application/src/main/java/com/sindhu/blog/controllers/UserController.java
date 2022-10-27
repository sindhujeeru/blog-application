package com.sindhu.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sindhu.blog.payloads.ApiResponse;
import com.sindhu.blog.payloads.UserDto;
import com.sindhu.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST -create user
	@PostMapping("/create")
	public ResponseEntity<UserDto>  cerateUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);   
	}
	//PUT - update user
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id){
		
		UserDto updatedUserDto = this.userService.updateUser(userDto, id); 
		
		return new ResponseEntity<>(updatedUserDto,HttpStatus.CREATED);
	}
	
	//DELETE delete user
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
		//UserDto deleteUserDto = this.userService.getUserById(id);
		
		this.userService.deleteUser(id);
		//return new ResponseEntity(Map.of("Message", "User Deleted successfully"), HttpStatus.OK);
		
		return new ResponseEntity<>( new ApiResponse("UserDeletedSuccesfully",true),   HttpStatus.OK);
	}
	
	//GET get user
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserbyId(@PathVariable Integer id){		
		return ResponseEntity.ok(this.userService.getUserById(id));
	}
	
	//GET ALL USERS
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
}
