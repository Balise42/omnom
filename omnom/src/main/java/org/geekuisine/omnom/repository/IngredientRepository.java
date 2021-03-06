package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.exception.IngredientRepositoryException;

/** Interface that should be implemented by ingredient repositories */
public interface IngredientRepository {
	/** Get a list of all ingredients */
	public List<Ingredient> getAllIngredients();
	/** Get a list of the children ingredients of a given ingredient */
	public List<Ingredient> getChildrenIngredients(Ingredient c);
	/** Returns a (full) ingredient object corresponding to a name, or null if it doesn't exist */
	public Ingredient getIngredient(String s);
	/** Adds a ingredient for a new ingredient name, or returns the existing ingredient if it already exists. 
	 * */
	public Ingredient addIngredient(String s);
	/** Returns a (full) ingredient object corresponding to an ID, or null if it doesn't exist */
	public Ingredient getIngredient(int i);
	/** Update the ingredient pointed by the ingredientId of the parameter, with the name and parents of the parameter object. */
	public void updateIngredient(Ingredient c) throws IngredientRepositoryException;
	/** Delete a ingredient and all the parent/ingredient relationships */
	public void deleteIngredient(int i);	
	/** Validates a ingredient: checks that its ID exists and that the parent hierarchy is valid */
	public void validate(Ingredient c) throws IngredientRepositoryException;
	/** Returns the list of all children, grandchildren, grandgrandchildren... of the hierarchy */
	public List<Ingredient> getAllChildren(Ingredient c);
	
}