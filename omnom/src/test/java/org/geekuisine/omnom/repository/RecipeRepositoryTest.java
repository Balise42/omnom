package org.geekuisine.omnom.repository;

import org.geekuisine.omnom.repository.impl.InMemoryRecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecipeRepositoryTest {
	RecipeRepository recipeRepository;
	
	@Before
	public void init(){
		recipeRepository = new InMemoryRecipeRepository();
	}
	
	@Test
	public void getAllRecipes_should_return_stuff(){
		Assert.assertTrue(recipeRepository.getAllRecipes().size()> 0);
	}
	
	@Test
	public void getRecipeById_with_valid_id_should_return_recipe(){
		Assert.assertNotNull(recipeRepository.getRecipeById(0));
	}

	@Test
	public void getRecipeById_with_invalid_id_should_return_null(){
		Assert.assertNull(recipeRepository.getRecipeById(-1));
	}
}
