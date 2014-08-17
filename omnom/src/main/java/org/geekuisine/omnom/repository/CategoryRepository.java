package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;

public interface CategoryRepository {
	public List<Category> getAllCategories();
	public List<Category> getChildrenCategories(Category c);
	public Category getCategory(String s);
}
