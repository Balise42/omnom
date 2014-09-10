package org.geekuisine.omnom.repository;

import java.math.BigDecimal;

import org.geekuisine.omnom.domain.Quantity;
import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.impl.DBIngredientRepository;
import org.geekuisine.omnom.repository.impl.DBRecipeRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.repository.impl.InMemoryRecipeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/** Testing RecipeRepository objects. Does NOT autowire the object under test, it has
 * to be created explicitely. */
public class RecipeRepositoryTest {
	RecipeRepository recipeRepository;
	
	@Before
	/** Creates the RecipeRepository and populates it. */
	public void init() throws ClassNotFoundException{
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate_with_dummy();
		recipeRepository = new DBRecipeRepository();
		recipeRepository.setIngredientRepository(new DBIngredientRepository());
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
	
	@Test
	public void addRecipe_should_work() throws ClassNotFoundException{
		IngredientRepository ingRep = new DBIngredientRepository();
		Recipe recipe = new Recipe();
		recipe.addIngredient(ingRep.addIngredient("oil"), new Quantity("tbsp", new BigDecimal(2), false));
		recipe.addIngredient(ingRep.addIngredient("mustard"), new Quantity("tsp", new BigDecimal(1), false));
		recipe.addIngredient(ingRep.addIngredient("salt"), new Quantity("pinch", new BigDecimal(1), false));
		recipe.addIngredient(ingRep.addIngredient("vinegar"), new Quantity("tbsp", new BigDecimal(1), false));
		recipe.setCookingTime(0);
		recipe.setName("Vinaigrette");
		recipe.setNumPersons(4);
		recipe.setPrepTime(5);
		recipe.setRestTime(3);
		recipe.setSteps("Mix together the vinegar, salt and mustard.\nDrizzle the oil while mixing until you get a thick sauce.");
		int recipeId = recipeRepository.addRecipe(recipe);
		Assert.assertEquals(recipeId, 1);
		Recipe r = recipeRepository.getRecipeById(recipeId);
		Assert.assertEquals(r.getIngredients().size(),4);
		Assert.assertEquals(r.getSteps().size(), 2);
		Assert.assertEquals(r.getCookingTime().getStandardMinutes(), 0);
		Assert.assertEquals(r.getNumPersons(), 4);
		Assert.assertEquals(r.getPrepTime().getStandardMinutes(), 5);
		Assert.assertEquals(r.getRestTime().getStandardMinutes(), 3);
	}
}
