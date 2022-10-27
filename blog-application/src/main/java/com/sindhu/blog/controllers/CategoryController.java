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
import com.sindhu.blog.payloads.CategoryDto;
import com.sindhu.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService catService;
	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCat(@Valid @RequestBody CategoryDto catDto){
		
		CategoryDto createCatDto = this.catService.createCategory(catDto);
		
		return new ResponseEntity<>(createCatDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{catId}")
	public ResponseEntity<CategoryDto> updateCat(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId){
		CategoryDto updateCatDto = this.catService.updateCategory(catDto, catId);
		
		return new ResponseEntity<>(updateCatDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{catId}")
	public ResponseEntity<ApiResponse> deleteCat(@PathVariable Integer catId){
		this.catService.deleteCategory(catId);
		
		return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);
		
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCat(@PathVariable Integer catId){
		CategoryDto catDto = this.catService.getCategoryById(catId);
		
		return new ResponseEntity<>(catDto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> catDtos = this.catService.getAllCategories();
		return new ResponseEntity<>(catDtos,HttpStatus.OK);
	}
}
