package org.geekuisine.omnom.service.impl;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/** Implementation of the CategoryService interface */
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	/** Uses the current CategoryRepository */
	IngredientRepository repository;
	
	@Override
	public Ingredient create(Ingredient category) {
		Ingredient newCategory = repository.addIngredient(category.getName());
		newCategory.setParentCategories(category.getParentIngredients());
		newCategory.addParentWithoutGrandparents(0);
		update(newCategory);
		return read(category.getName());
	}

	@Override
	public Ingredient read(int id) {
		return repository.getIngredient(id);
	}

	@Override
	public Ingredient read(String name) {
		return repository.getIngredient(name);
	}

	@Override
	public void update(Ingredient category) {
		repository.updateIngredient(category);
	}

	@Override
	public void delete(int id) {
		repository.deleteIngredient(id);
	}
	
	@Override
	public void initRepository(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
	}

	@Override
	public List<Ingredient> list() {
		return repository.getAllIngredients();
	}

}
