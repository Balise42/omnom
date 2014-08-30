package org.geekuisine.omnom.service.impl;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

/** Implementation of the CategoryService interface */
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	/** Uses the current CategoryRepository */
	CategoryRepository repository;
	
	@Override
	public Category create(Category category) {
		Category newCategory = repository.addCategory(category.getName());
		newCategory.setParentCategories(category.getParentCategories());
		newCategory.addParentWithoutGrandparents(0);
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
	
	@Override
	public void initRepository(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
	}

}
