package org.geekuisine.omnom.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {
	int recipeId;
	String name;
	Map<Ingredient, Quantity> ingredients;
	int numPersons;
	Quantity cookingTime;
	Quantity prepTime;
	Quantity restTime;
	List<String> steps;
	
	public Recipe(){
		super();
		ingredients = new HashMap<Ingredient,Quantity>();
		steps = new ArrayList<String>();
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Ingredient, Quantity> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<Ingredient, Quantity> ingredients) {
		this.ingredients = ingredients;
	}

	public int getNumPersons() {
		return numPersons;
	}

	public void setNumPersons(int numPersons) {
		this.numPersons = numPersons;
	}

	public Quantity getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Quantity cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Quantity getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Quantity prepTime) {
		this.prepTime = prepTime;
	}

	public Quantity getRestTime() {
		return restTime;
	}

	public void setRestTime(Quantity restTime) {
		this.restTime = restTime;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}
}
