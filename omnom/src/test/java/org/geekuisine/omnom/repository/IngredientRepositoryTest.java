package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.exception.IngredientRepositoryException;
import org.geekuisine.omnom.repository.impl.DBIngredientRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.repository.impl.InMemoryIngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/** Testing ingredientRepository objects. Does NOT autowire the object under test, it has
 * to be created explicitely. */
public class IngredientRepositoryTest {
	IngredientRepository rep;
	Ingredient poultry;
	
	@Before
	/** Creates a test IngredientRepository and populates it. */
	public void init() throws ClassNotFoundException{
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
		rep = new DBIngredientRepository();
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
		poultry = rep.getIngredient("poultry");
	}
	
	@Test 
	public void getIngredient_should_get_chicken(){
		Ingredient c = rep.getIngredient("chicken");
		Assert.assertTrue(c.getName().equalsIgnoreCase("chicken"));
	}
	
	@Test
	public void getChildrenIngredients_should_say_chicken_is_poultry(){
		List<Ingredient> children = rep.getChildrenIngredients(poultry);
		for(Ingredient ingredient : children){
			if(ingredient.getName().equalsIgnoreCase("chicken")){
				return;
			}
		}
		Assert.fail();
	}
	
	@Test
	public void getIngredient_by_existing_id_should_succeed(){
		Ingredient c = rep.getIngredient(0);
		Assert.assertNotNull(c);
		Assert.assertTrue(c.getName().equalsIgnoreCase("ingredient"));
	}
	
	@Test
	public void getIngredient_by_nonexistent_id_should_fail(){
		Ingredient c = rep.getIngredient(1024);
		Assert.assertNull(c);
	}
	
	@Test
	public void getIngredient_by_nonexistent_name_should_fail(){
		Ingredient c = rep.getIngredient("BÉPOÈDLJAUIE,CTNS'RTÀY.KQGF");
		if(c != null){
			Assert.fail();
		}
	}
	
	@Test
	public void getAllIngredients_should_return_stuff(){
		Assert.assertTrue(rep.getAllIngredients().size()>0);
	}
	
	@Test
	public void addIngredient_should_work(){
		rep.addIngredient("olive oil");
		Ingredient c = rep.getIngredient("olive oil");
		if(c==null){
			Assert.fail(); 
		}
	}
	
	@Test
	public void updateIngredient_should_work(){
		Ingredient c = rep.getIngredient("chicken");
		c.addParentWithoutGrandparents(5);
		rep.updateIngredient(c);
		Assert.assertTrue(rep.getIngredient("chicken").getParentIngredients().contains(5));
	}
	
	@Test(expected=IngredientRepositoryException.class)
	public void updateIngredient_with_invalid_id_should_throw(){
		Ingredient c = new Ingredient();
		c.setIngredientId(1299);
		c.setName("failCat");
		rep.updateIngredient(c);
		Assert.fail();
	}
	
	@Test(expected=IngredientRepositoryException.class)
	public void updateIngredient_with_invalid_parent_should_throw(){
		Ingredient c = rep.getIngredient("chicken");
		c.addParentWithoutGrandparents(90);
		rep.updateIngredient(c);
		Assert.fail();
	}
	
	@Test
	public void deleteIngredient_should_work(){
		rep.deleteIngredient(8);
		Ingredient c = rep.getIngredient("butter");
		Assert.assertNull(c);
	}
	
	@Test
	public void deleteIngredient_should_delete_in_children(){
		rep.deleteIngredient(6);
		Ingredient c = rep.getIngredient("chicken");
		Assert.assertFalse(c.getParentIngredients().contains(6));
	}
	
}
