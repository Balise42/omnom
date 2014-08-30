package org.geekuisine.omnom.service;

import org.geekuisine.omnom.domain.Category;

public interface CategoryService {
	public Category create(Category category);
	public Category read(int id);
	public Category read(String name);
	public void update(Category category);
	public void delete(int id);
	public void initRepository();
}
