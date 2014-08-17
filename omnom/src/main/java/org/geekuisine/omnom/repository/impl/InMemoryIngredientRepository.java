package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;

public class InMemoryIngredientRepository implements IngredientRepository {
	private List<Ingredient> repository;
	private int nextIndex;
	
	public InMemoryIngredientRepository(){
		repository = new ArrayList<Ingredient>();
		nextIndex = 0;
	}
	
	/* Ingredients should be ID'd from 0 to ingredients.size()-1 */
	public InMemoryIngredientRepository(List<Ingredient> ingredients){
		repository = ingredients;
		nextIndex = ingredients.size();
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
