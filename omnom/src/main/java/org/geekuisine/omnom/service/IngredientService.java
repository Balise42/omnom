package org.geekuisine.omnom.service;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

/** Interface to be implemented by IngredientService implementations*/
public interface IngredientService {
	/** add an ingredient */
	public void addIngredient(String s);
	/** get a list of all ingredients */
	public List<Ingredient> getAllIngredients();
}
