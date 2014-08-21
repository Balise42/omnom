package org.geekuisine.omnom.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
	Category ingredient, fat, dairy, butter;
	
	@Before
	public void init(){
		ingredient = new Category(0, "ingredient", -1);
		fat = new Category(1, "fat", 0);
		dairy = new Category(2, "dairy", 0);
		butter = new Category(3, "butter", 1);
	}
	
	@Test
	public void addCategory_should_add_parent_category(){
		butter.addParent(2);
		Assert.assertEquals(butter.getParentCategories().size(), 2);
	}
	
	

}
