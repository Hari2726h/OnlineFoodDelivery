package com.foodexpress.service;

import java.util.List;

import com.foodexpress.exception.CategoryException;
import com.foodexpress.model.Category;

public interface CategoryService {
	
    public Category addCategory(Category cat)throws CategoryException;
	
	public Category updateCategory(Category cat)throws CategoryException;
	
	public Category removeCategory(Integer categoryId)throws CategoryException;
	
	public Category viewCategory(Integer categoryId)throws CategoryException;
	
	public List<Category> viewAllCategory()throws CategoryException;
	
}
