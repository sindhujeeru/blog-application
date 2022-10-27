package com.sindhu.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sindhu.blog.payloads.CategoryDto;


public interface CategoryService {

	CategoryDto createCategory(CategoryDto catDto);
	
	CategoryDto updateCategory(CategoryDto catDto,Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	
	List<CategoryDto> getAllCategories();
	
	void deleteCategory(Integer categoryId);
}
