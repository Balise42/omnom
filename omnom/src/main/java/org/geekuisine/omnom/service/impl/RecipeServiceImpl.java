package org.geekuisine.omnom.service.impl;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.RecipeRepository;
import org.geekuisine.omnom.service.IngredientService;
import org.geekuisine.omnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/** Implementation of the IngredientService interface */
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	/** Uses the current RecipeRepository */
	private RecipeRepository recipeRepository;
	@Autowired
	/** Uses the current IngredientService */
	private IngredientService ingredientService;

	@Override
	public Recipe getRecipeById(int recipeId) {
		return recipeRepository.getRecipeById(recipeId);
	}

	@Override
	public List<Recipe> getAllRecipes() {
		return recipeRepository.getAllRecipes();
	} 
	
	@Override
	public void addRecipe(Recipe recipe){
		recipeRepository.addRecipe(recipe);
	}
}
