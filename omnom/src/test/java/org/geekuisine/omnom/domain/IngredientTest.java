package org.geekuisine.omnom.domain;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

/** Testing non-trivial behaviours of Ingredient */
public class IngredientTest {
	Ingredient ingredient;
	
	@Before
	public void init(){
		ingredient = new Ingredient(0, "abCD");
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
