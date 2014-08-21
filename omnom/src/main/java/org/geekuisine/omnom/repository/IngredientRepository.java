package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

public interface IngredientRepository {
	public void init();
	public List<Ingredient> getAllIngredients();
	public Ingredient addIngredient(String name);
	public Ingredient getIngredient(String name);
}
