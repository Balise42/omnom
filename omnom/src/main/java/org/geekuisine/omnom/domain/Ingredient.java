package org.geekuisine.omnom.domain;

/** Represents an ingredient. A category with the name of the ingredient should also exist. */
public class Ingredient {
	/** ID of the ingredient. Typically attributed by the repository. */
	int ingredientId;
	/** Name of the ingredient */
	String name;
	
	public Ingredient(){
	}
	
	public Ingredient(int ingredientId, String name){
		this.ingredientId = ingredientId;
		this.name = name;
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
	
	/** Case insensitive comparison on the ingredient name */
	public boolean isIngredient(String s){
		if(name.equalsIgnoreCase(s)){
			return true;
		}
		return false;
	}
}
