package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.exception.CategoryRepositoryException;

public interface CategoryRepository {
	public List<Category> getAllCategories();
	public List<Category> getChildrenCategories(Category c);
	public Category getCategory(String s);
	public Category addCategory(String s);
	public Category getCategory(int i);
	public void updateCategory(Category c) throws CategoryRepositoryException;
	public void deleteCategory(int i);	
	public void validate(Category c) throws CategoryRepositoryException;
}