package org.geekuisine.omnom.service.impl;

import java.util.List;

import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.RecipeRepository;
import org.geekuisine.omnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	private RecipeRepository recipeRepository;

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
