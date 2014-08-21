package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryIngredientRepository implements IngredientRepository {
	private List<Ingredient> repository;
	private int nextIndex;
	private CategoryRepository categoryRepository;
	
	public InMemoryIngredientRepository(){
		nextIndex = 0;
		repository = new ArrayList<Ingredient>();
		categoryRepository = new InMemoryCategoryRepository();
		nextIndex = 0;
		addIngredient("chicken");
		addIngredient("butter");
		addIngredient("salt");
		Ingredient chicken = new Ingredient(getNextIndex(), "chicken");
		repository = new ArrayList<Ingredient>();
		repository.add(chicken);
		Ingredient butter = new Ingredient(getNextIndex(), "butter");
		repository.add(butter);
		Ingredient salt = new Ingredient(getNextIndex(), "salt");
		repository.add(salt);
	}
	
	public List<Ingredient> getAllIngredients(){
		return repository;
	}
	
	public Ingredient addIngredient(String name){
		
		Ingredient i = getIngredient(name);
		if(i != null){
			return i;
		}
		categoryRepository.addCategory(name);
		i = new Ingredient(getNextIndex(), name);
		repository.add(i);
		return i;
	}
	
	public Ingredient getIngredient(String name){
		for(Ingredient ing : repository){
			if(ing.isIngredient(name)){
				return ing;
			}
		}
		return null;
	}
	
	private synchronized int getNextIndex(){
		return nextIndex++;
	}

	@Override
	public int getNextAttributedId() {
		return nextIndex;
	}
}
