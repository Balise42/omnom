package org.geekuisine.omnom.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class IngredientTest {
	Ingredient ingredient;
	
	@Before
	public void init(){
		Category c = new Category();
		List<String> names = new ArrayList<String>();
		names.add("abCD");
		names.add("efgh");
		ingredient = new Ingredient(0, names, c);
	}
	
	@Test
	public void isIngredient_should_say_ingredient_is_ABcd(){
		Assert.assertTrue(ingredient.isIngredient("ABcd"));
	}
	
	@Test
	public void isIngredient_should_say_ingredient_is_not_BEPO(){
		Assert.assertFalse(ingredient.isIngredient("BEPO"));
	}
}
