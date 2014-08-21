package org.geekuisine.omnom.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.geekuisine.omnom.domain.Ingredient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-DispatcherServlet-context.xml")
@WebAppConfiguration
public class IngredientRepositoryTest {
	@Autowired
	IngredientRepository rep;
	
	@Before
	public void init(){
		rep.init();
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
