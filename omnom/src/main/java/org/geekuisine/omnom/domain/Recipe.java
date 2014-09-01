package org.geekuisine.omnom.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Duration;

/** Represent a recipe: metadata, steps and ingredients */
public class Recipe {
	/** ID of the recipe. Typically attributed by the repository.*/
	int recipeId;
	/** Name of the recipe */
	String name;
	/** Ingredients of the recipe: each Ingredient is associated to a Quantity for the recipe (total quantity used) */
	Map<Ingredient, Quantity> ingredients;
	/** Number of persons for which the recipe is written */
	int numPersons;
	/** Cooking time */
	Duration cookingTime;
	/** Preparation time */
	Duration prepTime;
	/** Resting time (also include refrigerating, rising, and such) */
	Duration restTime;
	/** Steps of the recipe (one string in the list = one step in the recipe) */
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

	public Duration getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Duration cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Duration getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Duration prepTime) {
		this.prepTime = prepTime;
	}

	public Duration getRestTime() {
		return restTime;
	}

	public void setRestTime(Duration restTime) {
		this.restTime = restTime;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	/** Sets the cooking time to the duration in minutes*/
	public void setCookingTime(int minutes) {
		setCookingTime(Duration.standardMinutes(minutes));
	}
	
	/** Sets the resting time to the duration in minutes */
	public void setRestTime(int minutes){
		setRestTime(Duration.standardMinutes(minutes));
	}
	
	/** Sets the prep time to the duration in minutes */
	public void setPrepTime(int minutes){
		setPrepTime(Duration.standardMinutes(minutes));
	}
	
	/** Splits the string according to newlines, sets the step string 
	 * as split string. */
	public void setSteps(String steps){
		setSteps(Arrays.asList(steps.split("\n")));
	}
	
	/** Add an ingredient to the ingredient list */
	public void addIngredient(Ingredient i, Quantity q){
		ingredients.put(i, q);
	}
}
