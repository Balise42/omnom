package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;

public interface RecipeRepository {
	public List<Recipe> getAllRecipes();
	public Recipe getRecipeById(int recipeId);
}
