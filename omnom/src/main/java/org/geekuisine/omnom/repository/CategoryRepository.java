package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.exception.CategoryRepositoryException;

/** Interface that should be implemented by category repositories */
public interface CategoryRepository {
	/** Get a list of all categories */
	public List<Category> getAllCategories();
	/** Get a list of the children categories of a given category */
	public List<Category> getChildrenCategories(Category c);
	/** Returns a (full) Category object corresponding to a name, or null if it doesn't exist */
	public Category getCategory(String s);
	/** Adds a category for a new category name, or returns the existing category if it already exists. 
	 * TODO: currently doesn't handle the case!!
	 * */
	public Category addCategory(String s);
	/** Returns a (full) Category object corresponding to an ID, or null if it doesn't exist */
	public Category getCategory(int i);
	/** Update the category pointed by the categoryId of the parameter, with the name and parents of the parameter object. */
	public void updateCategory(Category c) throws CategoryRepositoryException;
	/** Delete a category and all the parent/category relationships */
	public void deleteCategory(int i);	
	/** Validates a category: checks that its ID exists and that the parent hierarchy is valid */
	public void validate(Category c) throws CategoryRepositoryException;
}