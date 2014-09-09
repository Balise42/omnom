package org.geekuisine.omnom.domain;

import java.util.HashSet;
import java.util.Set;

/** A class for ingredients (e.g. "chicken", "butter", "meat", "fat", "condiment", etc). 
 * Allows a DAG structure by storing the parent ingredients. No validation whatsoever
 * on the hierarchy is done at this level, this is purely for storage purpose. */

public class Ingredient {
	/** ID of the ingredient. Typically attributed by the repository. */
	int ingredientId;
	/** Name of the ingredient */
	String name;
	/** IDs of the parent ingredient */
	Set<Integer> parentIngredients;
	
	/** Default constructor. Just creates the underlying structure. */
	public Ingredient(){
		parentIngredients = new HashSet<Integer>();
	}
	
	/** Constructor with ID and name, no parent (uses "0" as a parent)*/
	public Ingredient(int ingredientId, String name){
		this(ingredientId, name, 0);
	}
	
	/** Constructor with name, no parent (uses "0" as a parent)*/
	public Ingredient(String name){
		this(-1, name, 0);
	}
	
	/** Constructor with id, name, and parent */
	public Ingredient(int ingredientId, String name, Ingredient parent){
		this(ingredientId, name, parent.getIngredientId());
	}
	
	/** Constructor with id, name and parent ID */
	public Ingredient(int ingredientId, String name, int parentId){
		this();
		this.name = name;
		this.ingredientId = ingredientId;
		this.parentIngredients.add(parentId);
	}

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Integer> getParentIngredients() {
		return parentIngredients;
	}
	
	public void setParentCategories(Set<Integer> parents){
		parentIngredients = parents;
	}

	
	/** Add a parent ID to the list of parents. */
	public void addParent(int parentId){
		parentIngredients.add(parentId);
	}
	
	/** Add a parent to the list of parents (just add the ID) */
	public void addParent(Ingredient parent){
		parentIngredients.add(parent.getIngredientId());
	}

}
