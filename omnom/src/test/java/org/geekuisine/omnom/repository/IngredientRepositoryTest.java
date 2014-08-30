package org.geekuisine.omnom.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.impl.InMemoryIngredientRepository;

/** Testing IngredientRepository objects. Does NOT autowire the object under test, it has
 * to be created explicitely. */
public class IngredientRepositoryTest {
	IngredientRepository rep;
	
	@Before
	/** Creates the repository and populates it*/
	public void init(){
		rep = new InMemoryIngredientRepository();
	}
	
	@Test 
	public void getAllIngredients_should_have_elements(){
		Assert.assertTrue(rep.getAllIngredients().size()>0);
	}
	
	@Test 
	public void getIngredient_should_return_chicken(){
		Ingredient i = rep.getIngredient("chicken");
		Assert.assertNotNull(i);
	}
	
	@Test
	public void getIngredient_should_not_return_bepo(){
		Assert.assertNull(rep.getIngredient("bepo"));
	}
	
	@Test 
	public void addIngredient_insert_new_element_should_succeed_with_next_id(){
		int expectedId = rep.getNextAttributedId();
		Ingredient i = rep.addIngredient("ground beef");
		Assert.assertEquals(i.getIngredientId(), expectedId);
	}
	
}
