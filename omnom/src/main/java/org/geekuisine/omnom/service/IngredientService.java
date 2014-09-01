package org.geekuisine.omnom.service;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

/** Interface that has to be implemented by ingredientService implementations */
public interface IngredientService {
	/** Create a ingredient. The ID of the ingredient is not supposed to be correct
	 * (it is attributed by the repository). The ingredient should have at least 
	 * ingredient 0 (ingredient) as parent (should be added by the service). */
	public Ingredient create(Ingredient ingredient);
	/** Get an ingredient by its ID or null if doesn't exist */
	public Ingredient read(int id);
	/** Get an ingredient by its name or null if it doesn't exist */
	public Ingredient read(String name);
	/** Update the ingredient pointed by the parameter ingredient ID so that it corresponds
	 * to the parameter ingredient. ingredient should be valid. (checked downstream) */
	public void update(Ingredient ingredient);
	/** Delete an ingredient */
	public void delete(int id);
	/** Initialize the repository - flushes it and adds a few initial ingredients.
	 * Should not be used in normal operation. */
	public void initRepository();
	/** List all ingredients */
	public List<Ingredient> list();
}
