package org.geekuisine.omnom.service;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;

/** Interface to be implemented by all RecipeService implementations */
public interface RecipeService {
	/** get a recipe by its ID */
	public Recipe getRecipeById(int recipeId);
	/** get all the recipes */
	public List<Recipe> getAllRecipes();
	/** add a recipe to the system */
	public void addRecipe(Recipe recipe);
}
