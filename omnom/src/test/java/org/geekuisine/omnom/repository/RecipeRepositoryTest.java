package org.geekuisine.omnom.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-DispatcherServlet-context.xml")
@WebAppConfiguration
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
	
	@Test
	public void getRecipeById_with_valid_id_should_return_recipe(){
		Assert.assertNotNull(recipeRepository.getRecipeById(0));
	}

	@Test
	public void getRecipeById_with_invalid_id_should_return_null(){
		Assert.assertNull(recipeRepository.getRecipeById(-1));
	}
}
