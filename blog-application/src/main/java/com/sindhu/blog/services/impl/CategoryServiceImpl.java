package com.sindhu.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sindhu.blog.entities.Category;
import com.sindhu.blog.exceptions.ResourceNotFoundException;
import com.sindhu.blog.payloads.CategoryDto;
import com.sindhu.blog.repositories.CategoryRepo;
import com.sindhu.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto catDto) {
		
		Category cat = this.dtoToCat(catDto);
		
		Category savedCat = this.catRepo.save(cat);
		
		return this.catToDto(savedCat);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catDto, Integer categoryId) {
		Category cat = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" , "id",categoryId));
		
		cat.setCategoryTitle(catDto.getCategoryTitle());
		cat.setCategoryDescription(catDto.getCategoryDescription());
		
		Category updatedCategory = this.catRepo.save(cat);
		return this.catToDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category cat = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" , "id",categoryId)); 
		return this.catToDto(cat);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.catRepo.findAll();
		
		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.catToDto(cat)).collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.catRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" , "id",categoryId));
		
		this.catRepo.delete(cat);

	}
	
	public Category dtoToCat(CategoryDto catDto) {
		
		Category cat = this.modelMapper.map(catDto, Category.class);
		
		return cat;
	}
	
	public CategoryDto catToDto(Category cat) {
		
		CategoryDto catDto = this.modelMapper.map(cat, CategoryDto.class);
		
		return catDto;
	}

}
