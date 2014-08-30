package org.geekuisine.omnom.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Testing non-trivial behaviours of Category */
public class CategoryTest {
	Category ingredient, fat, dairy, butter;
	
	@Before
	public void init(){
		ingredient = new Category(0, "ingredient");
		fat = new Category(1, "fat", ingredient);
		dairy = new Category(2, "dairy", ingredient);
		butter = new Category(3, "butter", fat);
	}
	
	@Test
	public void addParent_should_add_parent_category(){
		butter.addParent(dairy);
		Assert.assertEquals(butter.getParentCategories().size(), 3);
	}
	
	@Test
	public void addParent_should_add_all_parents(){
		butter.addParent(dairy);
		Category saltbutter = new Category(4, "salted butter");
		saltbutter.addParent(butter);
		Assert.assertEquals(saltbutter.getParentCategories().size(), 4);
	}

}
