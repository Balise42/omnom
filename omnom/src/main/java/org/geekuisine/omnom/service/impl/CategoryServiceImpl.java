package org.geekuisine.omnom.service.impl;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository repository;
	
	@Override
	public Category create(Category category) {
		Category newCategory = repository.addCategory(category.getName());
		newCategory.setParentCategories(category.getParentCategories());
		update(newCategory);
		return read(category.getName());
	}

	@Override
	public Category read(int id) {
		return repository.getCategory(id);
	}

	@Override
	public Category read(String name) {
		return repository.getCategory(name);
	}

	@Override
	public void update(Category category) {
		repository.updateCategory(category);
	}

	@Override
	public void delete(int id) {
		repository.deleteCategory(id);
	}
	
	public void initRepository(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.truncateAll();
		dbutils.populate();
	}

}
