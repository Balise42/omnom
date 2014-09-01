package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;

/** Interface that should be implemented by recipe repositories */
public interface RecipeRepository {
	/** Returns a list of all recipes */
	public List<Recipe> getAllRecipes();
	/** Gets a recipe by its ID */
	public Recipe getRecipeById(int recipeId);
	/** Adds a recipe to the repository. Ingredients 
	 * should already exist when adding the recipe. */
	public void addRecipe(Recipe r);
}
