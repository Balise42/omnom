package org.geekuisine.omnom.service;

import org.geekuisine.omnom.domain.Category;

/** Interface that has to be implemented by CategoryService implementations */
public interface CategoryService {
	/** Create a category. The ID of the category is not supposed to be correct
	 * (it is attributed by the repository). The category should have at least 
	 * category 0 (ingredient) as parent (should be added by the service). */
	public Category create(Category category);
	/** Get a category by its ID or null if doesn't exist */
	public Category read(int id);
	/** Get a category by its name or null if it doesn't exist */
	public Category read(String name);
	/** Update the category pointed by the parameter category ID so that it corresponds
	 * to the parameter category. Category should be valid. (checked downstream) */
	public void update(Category category);
	/** Delete a category */
	public void delete(int id);
	/** Initialize the repository - flushes it and adds a few initial categories.
	 * Should not be used in normal operation. */
	public void initRepository();
}
