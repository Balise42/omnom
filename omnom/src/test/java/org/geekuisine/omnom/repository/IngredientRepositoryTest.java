package org.geekuisine.omnom.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.impl.InMemoryIngredientRepository;

public class IngredientRepositoryTest {
	IngredientRepository rep;
	
	@Before
	public void init(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Category c = new Category();
		List<String> names = new ArrayList<String>();
		names.add("chicken");
		Ingredient ingredient = new Ingredient(0, names, c);
		ingredients.add(ingredient);
		rep = new InMemoryIngredientRepository(ingredients);
	}
	
	@Test 
	public void getAllIngredients_should_have_elements(){
		Assert.assertTrue(rep.getAllIngredients().size()>0);
	}
	
	@Test 
	public void getIngredient_should_return_chicken(){
		Ingredient i = rep.getIngredient("chicken");
		Assert.assertNotNull(i);
		Assert.assertEquals(i.getIngredientId(), 0);
	}
	
	@Test
	public void getIngredient_should_not_return_bepo(){
		Assert.assertNull(rep.getIngredient("bepo"));
	}
	
	@Test 
	public void addIngredient_insert_new_element_should_succeed_with_id_1(){
		List<String> names = new ArrayList<String>();
		names.add("ground beef");
		Category c = new Category();
		Ingredient i = rep.addIngredient(names, c);
		Assert.assertEquals(i.getIngredientId(), 1);
	}
	
	@Test 
	public void addIngredient_insert_existing_element_should_succeed_with_id_0(){
		List<String> names = new ArrayList<String>();
		names.add("abcd");
		names.add("chicken");
		Category c = new Category();
		Ingredient i = rep.addIngredient(names, c);
		Assert.assertEquals(i.getIngredientId(), 0);
	}
	
}
