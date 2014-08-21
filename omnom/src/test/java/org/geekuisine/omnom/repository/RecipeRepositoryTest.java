package org.geekuisine.omnom.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecipeRepositoryTest {
	@Autowired 
	RecipeRepository recipeRepository;
	
	@Before
	public void init(){
		recipeRepository.init();
	}
	
	@Test
	public void getAllRecipes_should_return_stuff(){
		Assert.assertTrue(recipeRepository.getAllRecipes().size()> 0);
	}

}
