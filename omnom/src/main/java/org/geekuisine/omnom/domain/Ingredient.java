package org.geekuisine.omnom.domain;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
	int ingredientId;
	List<String> names;
	Category category;
	
	Ingredient(){
		super();
		names = new ArrayList<String>();
	}
	
	public Ingredient(int ingredientId, List<String> names, Category category){
		this();
		this.ingredientId = ingredientId;
		this.names = names;
		this.category = category;
	}

	public int getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public boolean isIngredient(String s){
		for(String name : names){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
}
