package org.geekuisine.omnom.service;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

public interface IngredientService {
	public void addIngredient(String s);
	public List<Ingredient> getAllIngredients();
}
