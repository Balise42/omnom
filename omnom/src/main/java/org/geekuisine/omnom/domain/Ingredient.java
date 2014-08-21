package org.geekuisine.omnom.domain;

public class Ingredient {
	int ingredientId;
	String name;
	
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
	
	public boolean isIngredient(String s){
		if(name.equalsIgnoreCase(s)){
			return true;
		}
		return false;
	}
}
