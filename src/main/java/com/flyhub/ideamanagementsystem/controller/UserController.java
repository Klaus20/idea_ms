package com.flyhub.ideamanagementsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flyhub.ideamanagementsystem.entity.User;
import com.flyhub.ideamanagementsystem.exception.ResourceNotFoundException;
import com.flyhub.ideamanagementsystem.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	//get users
	
	@GetMapping("/users")
	public List<User> getAllUser(){
		return this.userRepository.findAll();
	}
	//get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
		throws ResourceNotFoundException {
	User user = userRepository.findById(userId)	
			.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
			return ResponseEntity.ok().body(user);
		
	}
	
	//save user
	@PostMapping("/users")
	public User createUser ( @RequestBody User user) {
		return userRepository.save(user);
	}
	
	//update user
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
			 @RequestBody User userDetails) throws ResourceNotFoundException{
	
		User user = userRepository.findById(userId)	
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		
		user.setEmail(userDetails.getEmail());
		user.setFirst_Name(userDetails.getFirst_Name());
		user.setLast_Name(userDetails.getLast_Name());
		user.setPassword(userDetails.getPassword());
		
		return ResponseEntity.ok(this.userRepository.save(user));
	}
	
	//delete user
	@DeleteMapping("users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
		User user = userRepository.findById(userId)	
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		
		this.userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
		
	}
	
	
}
