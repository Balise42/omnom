package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.domain.Ingredient;

public interface IngredientRepository {
	public void init();
	public List<Ingredient> getAllIngredients();
	public Ingredient addIngredient(List<String> names, Category category);
	public Ingredient getIngredient(String name);
}
