package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryIngredientRepository implements IngredientRepository {
	private List<Ingredient> repository;
	private int nextIndex;
	@Autowired
	private CategoryRepository categoryRepository;
	
	public InMemoryIngredientRepository(){
		super();
		nextIndex = 0;
		repository = new ArrayList<Ingredient>();
	}
	
	public void init(){
		nextIndex = 0;
		List<String> chickenNames = new ArrayList<String>();
		chickenNames.add("chicken");
		Ingredient chicken = new Ingredient(getNextIndex(), chickenNames, categoryRepository.getCategory("chicken"));
		repository = new ArrayList<Ingredient>();
		repository.add(chicken);
	}
	
	public List<Ingredient> getAllIngredients(){
		return repository;
	}
	
	public Ingredient addIngredient(List<String> names, Category category){
		for(String name : names){
			Ingredient i = getIngredient(name);
			if(i != null){
				return i;
			}
		}
		Ingredient i = new Ingredient(getNextIndex(), names, category);
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
}
