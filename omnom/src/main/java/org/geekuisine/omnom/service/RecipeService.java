package org.geekuisine.omnom.service;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;

public interface RecipeService {
	public Recipe getRecipeById(int recipeId);
	public List<Recipe> getAllRecipes();
	public void init();

}
