package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.impl.InMemoryCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class CategoryRepositoryTest {
	CategoryRepository rep;
	Category poultry;
	
	@Before
	public void init(){
		rep = new InMemoryCategoryRepository();
		poultry = rep.getCategory("poultry");
	}
	
	@Test 
	public void getCategory_should_get_chicken(){
		Category c = rep.getCategory("chicken");
		Assert.assertTrue(c.getName().equalsIgnoreCase("chicken"));
	}
	
	@Test
	public void getChildrenCategory_should_say_chicken_is_poultry(){
		List<Category> children = rep.getChildrenCategories(poultry);
		for(Category category : children){
			if(category.getName().equalsIgnoreCase("chicken")){
				return;
			}
		}
		Assert.fail();
	}
	
	@Test
	public void getCategory_should_fail(){
		Category c = rep.getCategory("BÉPOÈDLJAUIE,CTNSRTÀY.K'QGF");
		if(c != null){
			Assert.fail();
		}
	}
	
	@Test
	public void getAllCategories_should_return_stuff(){
		Assert.assertTrue(rep.getAllCategories().size()>0);
	}
	
}
