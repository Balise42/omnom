package org.geekuisine.omnom.service;

import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-DispatcherServlet-context.xml")
@WebAppConfiguration
/** Integration testing for IngredientService (everything is autowired) */
public class RecipeServiceTest {
	@Autowired
	private RecipeService recipeService;
	
	public RecipeServiceTest(){
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
	}
	
	@Before
	/** Creates a new repository and inits it */
	public void init(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
	}
	
	@Test
	public void list_should_return_stuff(){
		Assert.assertTrue(recipeService.list().size() > 0);
	}
	
	@Test
	public void read_should_get_recipe(){
		Assert.assertNotNull(recipeService.read(0));
	}
	
	@Test
	public void create_should_work(){
		Recipe r = new Recipe();
		Assert.assertTrue(recipeService.create(r) > 0);
	}
	
}
