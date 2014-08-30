package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

/** Interface that should be implemented by ingredient repositories */
public interface IngredientRepository {
	/** Returns the list of all ingredients */
	public List<Ingredient> getAllIngredients();
	/** Add an ingredient to the ingredient repository, as well as the corresponding category */
	public Ingredient addIngredient(String name);
	/** Gets an ingredient by its name */
	public Ingredient getIngredient(String name);
	/** Returns the ID that will be attributed to the next ingredient */
	public int getNextAttributedId();
}
