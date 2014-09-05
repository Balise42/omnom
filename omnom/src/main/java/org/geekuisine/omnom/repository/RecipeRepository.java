package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.impl.DBIngredientRepository;

/** Interface that should be implemented by recipe repositories */
public interface RecipeRepository {
	/** Returns a list of all recipes */
	public List<Recipe> getAllRecipes();
	/** Gets a recipe by its ID */
	public Recipe getRecipeById(int recipeId);
	/** Adds a recipe to the repository. Ingredients 
	 * should already exist when adding the recipe. 
	 * @return */
	public int addRecipe(Recipe r);
	public void setIngredientRepository(IngredientRepository ingredientRepository);
}
