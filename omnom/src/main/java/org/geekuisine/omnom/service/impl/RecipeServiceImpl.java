package org.geekuisine.omnom.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.domain.Quantity;
import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.RecipeRepository;
import org.geekuisine.omnom.service.IngredientService;
import org.geekuisine.omnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/** Implementation of the RecipeService interface */
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	/** Uses the current RecipeRepository */
	private RecipeRepository recipeRepository;
	@Autowired
	/** Uses the current IngredientService */
	private IngredientService ingredientService;

	@Override
	public Recipe read(int recipeId) {
		return recipeRepository.getRecipeById(recipeId);
	}

	@Override
	public List<Recipe> list() {
		return recipeRepository.getAllRecipes();
	} 
	
	@Override
	public int create(Recipe recipe){
		Map<Ingredient,Quantity> ingredients = new HashMap<Ingredient,Quantity>();
		for(Ingredient i : recipe.getIngredients().keySet()){
			Ingredient ingredient = ingredientService.create(i);
			ingredients.put(ingredient, recipe.getIngredients().get(i));
		}
		recipe.setIngredients(ingredients);
		return recipeRepository.addRecipe(recipe);
	}
}
