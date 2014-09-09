package org.geekuisine.omnom.service;

import org.geekuisine.omnom.domain.Ingredient;
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
public class IngredientServiceTest {
	
	@Autowired
	IngredientService ingredientService;
	
	public IngredientServiceTest(){
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
	}
	
	@Before
	/** Creates a new test repository and populates it*/
	public void init(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
	}
	
	@Test
	public void create_ingredient_should_work(){
		Ingredient cat = new Ingredient(1000, "newItem");
		Assert.assertNotNull(ingredientService.create(cat));
	}
	
	@Test
	public void read_ingredient_from_id_should_work(){
		Assert.assertNotNull(ingredientService.read(3));
	}
	
	@Test
	public void read_ingredient_from_name_should_work(){
		Assert.assertNotNull(ingredientService.read("chicken"));
	}
	
	@Test
	public void update_ingredient_should_work(){
		Ingredient cat = ingredientService.read("chicken");
		cat.addParent(5);
		ingredientService.update(cat);
		Assert.assertTrue(ingredientService.read("chicken").getParentIngredients().contains(5));
	}
	
	@Test
	public void delete_ingredient_should_work(){
		ingredientService.delete(3);
		Assert.assertNull(ingredientService.read(3));
	}
}
