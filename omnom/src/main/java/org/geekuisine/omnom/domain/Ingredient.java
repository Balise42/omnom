package org.geekuisine.omnom.domain;

import java.util.HashSet;
import java.util.Set;

/** A class for ingredients (e.g. "chicken", "butter", "meat", "fat", "condiment", etc). 
 * Allows a DAG structure by storing the parent ingredients. */

public class Ingredient {
	/** ID of the ingredient. Typically attributed by the repository. */
	int ingredientId;
	/** Name of the ingredient */
	String name;
	/** IDs of the parent ingredient */
	Set<Integer> parentIngredients;
	
	/** Default constructor. Every category is derived from ingredient "0" which represents "ingredient". */
	public Ingredient(){
		parentIngredients = new HashSet<Integer>();
		parentIngredients.add(0);
	}
	
	/** Constructor with ID and name, no parent (will add "0" as a parent)*/
	public Ingredient(int ingredientId, String name){
		this(name);
		this.ingredientId = ingredientId;
	}
	
	/** Constructor with name, no parent (will add "0" as a parent)*/
	public Ingredient(String name){
		this();
		this.name = name;
	}
	
	/** Constructor with name, id and one parent */
	public Ingredient(int ingredientId, String name, Ingredient parent){
		this(ingredientId, name);
		addParent(parent);
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
	
	/** Add an ingredient  as a parent. Also adds the parents of that ingredient as parents.
	 Parent ingredient is supposed to be valid. */
	public void addParent(Ingredient parent){
		parentIngredients.add(parent.ingredientId);
		if(parent.ingredientId != 0){
			parentIngredients.addAll(parent.getParentIngredients());
		}
	}
	
	/** Add a parent ID to the list of parents. Doesn't do any check! */
	public void addParentWithoutGrandparents(int parent){
		parentIngredients.add(parent);
	}

}
