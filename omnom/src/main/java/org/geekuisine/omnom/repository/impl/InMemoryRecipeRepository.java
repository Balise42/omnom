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
public class InMemoryRecipeRepository implements RecipeRepository {
	List<Recipe> recipeRepository;
	int nextId;
	
	IngredientRepository ingredientRepository;
	
	public InMemoryRecipeRepository(){
		recipeRepository = new ArrayList<Recipe>();
		nextId = 0;
		ingredientRepository = new InMemoryIngredientRepository();
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
