package org.geekuisine.omnom.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.domain.Quantity;
import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.RecipeRepository;
import org.joda.time.Duration;
import org.springframework.stereotype.Repository;

@Repository
/** First implementation of recipe repository - in memory.
 * Not for production use, only for dev purpose.
 * Should probably be implemented as singleton if it were to be of any real use. */
public class InMemoryRecipeRepository implements RecipeRepository {
	/** List of recipes, actually making the repository */
	List<Recipe> recipeRepository;
	/** ID of the next recipe added to the repository */
	int nextId;
	
	/** Creates */
	public InMemoryRecipeRepository(){
		recipeRepository = new ArrayList<Recipe>();
		nextId = 0;
		IngredientRepository ingredientRepository = new InMemoryIngredientRepository();
		nextId = 0;
		recipeRepository = new ArrayList<Recipe>();
		Recipe recipe = new Recipe();
		recipe.setRecipeId(getNextId());
		recipe.setName("Oven-baked chicken");
		recipe.setNumPersons(4);
		Quantity chickenQuantity = new Quantity("chicken", new BigDecimal(1));
		Map<Ingredient,Quantity> ingredients = new HashMap<Ingredient,Quantity>();
		ingredients.put(ingredientRepository.getIngredient("chicken"), chickenQuantity);
		Quantity butterQuantity = new Quantity("tablespoon", new BigDecimal(1));
		ingredients.put(ingredientRepository.getIngredient("butter"), butterQuantity);
		Quantity saltQuantity = new Quantity("dash", new BigDecimal(1), true);
		ingredients.put(ingredientRepository.getIngredient("salt"), saltQuantity);
		recipe.setIngredients(ingredients);
		Duration prepTime = Duration.standardMinutes(15);
		recipe.setPrepTime(prepTime);
		Duration cookTime = Duration.standardHours(1);
		recipe.setCookingTime(cookTime);
		Duration restTime = Duration.ZERO;
		recipe.setRestTime(restTime);
		List<String> steps = new ArrayList<String>();
		steps.add("Clean the chicken, add some butter and salt on it.");
		steps.add("Bake the chicken at 220°C for an hour.");
		steps.add("When the chicken is cooked through, cut it in pieces and eat it.");
		recipe.setSteps(steps);
		
		recipeRepository.add(recipe);
	}
	
	private synchronized int getNextId(){
		return nextId++;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		return recipeRepository;
	}
	
	public Recipe getRecipeById(int recipeId){
		for(Recipe recipe : recipeRepository){
			if(recipe.getRecipeId() == recipeId){
				return recipe;
			}
		}
		return null;
	}
	
	@Override
	public void addRecipe(Recipe recipe){
		recipe.setRecipeId(getNextId());
		recipeRepository.add(recipe);
	}
}
