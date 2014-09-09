package org.geekuisine.omnom.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Testing non-trivial behaviours of Category */
public class IngredientTest {
	Ingredient ingredient, fat, dairy, butter;
	
	@Before
	public void init(){
		ingredient = new Ingredient(0, "ingredient");
		fat = new Ingredient(1, "fat", ingredient);
		dairy = new Ingredient(2, "dairy", ingredient);
		butter = new Ingredient(3, "butter", fat);
	}
	
	@Test
	public void addParent_should_add_parent_category(){
		butter.addParent(dairy);
		Assert.assertEquals(butter.getParentIngredients().size(), 2);
	}

}
