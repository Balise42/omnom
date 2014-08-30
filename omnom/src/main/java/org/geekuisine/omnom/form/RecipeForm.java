package org.geekuisine.omnom.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.domain.Quantity;
import org.geekuisine.omnom.domain.Recipe;
import org.joda.time.Duration;

/** Recipe bean usable in a JSP form */
public class RecipeForm {
	private String name;
	private List<Ingredient> ingredientsList;
	private List<Quantity> quantities;
	private int numPersons;
	private int cookingTime;
	private int prepTime;
	private int restTime;
	private String steps;
	
	/** Default constructor. IngredientList and quantities are declared as LazyList to allow dynamic forms
	 * where the exact number of fields is unknown. */
	public RecipeForm(){
		Factory ingredientFactory = new Factory() {
			public Object create() {
				return new Ingredient();
		    }
		};
		Factory quantityFactory = new Factory(){
			public Object create(){
				return new Quantity();
			}
		};
		
		ingredientsList = LazyList.decorate(new ArrayList<Ingredient>(), ingredientFactory);
		quantities = LazyList.decorate(new ArrayList<Quantity>(), quantityFactory);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ingredient> getIngredientsList() {
		return ingredientsList;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredientsList = ingredients;
	}
	public List<Quantity> getQuantities() {
		return quantities;
	}
	public void setQuantities(List<Quantity> quantities) {
		this.quantities = quantities;
	}
	public int getNumPersons() {
		return numPersons;
	}
	public void setNumPersons(int numPersons) {
		this.numPersons = numPersons;
	}
	public int getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}
	public int getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}
	public int getRestTime() {
		return restTime;
	}
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	
	/** Creates a Recipe object from the form data */
	public Recipe getRecipe(){
		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setNumPersons(numPersons);
		recipe.setPrepTime(Duration.standardMinutes(prepTime));
		recipe.setRestTime(Duration.standardMinutes(restTime));
		recipe.setCookingTime(Duration.standardMinutes(cookingTime));
		HashMap<Ingredient, Quantity> ingredients = new HashMap<Ingredient,Quantity>();
		for(int i = 0; i<ingredientsList.size(); i++){
			ingredients.put(ingredientsList.get(i), quantities.get(i));
		}
		recipe.setIngredients(ingredients);
		recipe.setSteps(Arrays.asList(steps.split("\n")));
		return recipe;
	}
}
