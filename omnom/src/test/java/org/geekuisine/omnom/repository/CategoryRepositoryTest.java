package org.geekuisine.omnom.repository;

import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.exception.CategoryRepositoryException;
import org.geekuisine.omnom.repository.impl.DBCategoryRepository;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.geekuisine.omnom.repository.impl.InMemoryCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

/** Testing CategoryRepository objects. Does NOT autowire the object under test, it has
 * to be created explicitely. */
public class CategoryRepositoryTest {
	CategoryRepository rep;
	Category poultry;
	
	@Before
	/** Creates a test CategoryRepository and populates it. */
	public void init() throws ClassNotFoundException{
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
		rep = new DBCategoryRepository();
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.dropAllTables();
		dbutils.populate();
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
	public void getCategory_by_existing_id_should_succeed(){
		Category c = rep.getCategory(0);
		Assert.assertNotNull(c);
		Assert.assertTrue(c.getName().equalsIgnoreCase("ingredient"));
	}
	
	@Test
	public void getCategory_by_nonexistent_id_should_fail(){
		Category c = rep.getCategory(1024);
		Assert.assertNull(c);
	}
	
	@Test
	public void getCategory_should_fail(){
		Category c = rep.getCategory("BÉPOÈDLJAUIE,CTNS'RTÀY.KQGF");
		if(c != null){
			Assert.fail();
		}
	}
	
	@Test
	public void getAllCategories_should_return_stuff(){
		Assert.assertTrue(rep.getAllCategories().size()>0);
	}
	
	@Test
	public void addCategory_should_work(){
		rep.addCategory("olive oil");
		Category c = rep.getCategory("olive oil");
		if(c==null){
			Assert.fail(); 
		}
	}
	
	@Test
	public void updateCategory_should_work(){
		Category c = rep.getCategory("chicken");
		c.addParentWithoutGrandparents(5);
		rep.updateCategory(c);
		Assert.assertTrue(rep.getCategory("chicken").getParentCategories().contains(5));
	}
	
	@Test(expected=CategoryRepositoryException.class)
	public void updateCategory_with_invalid_id_should_throw(){
		Category c = new Category();
		c.setCategoryId(1299);
		c.setName("failCat");
		rep.updateCategory(c);
		Assert.fail();
	}
	
	@Test(expected=CategoryRepositoryException.class)
	public void updateCategory_with_invalid_parent_should_throw(){
		Category c = rep.getCategory("chicken");
		c.addParentWithoutGrandparents(90);
		rep.updateCategory(c);
		Assert.fail();
	}
	
	@Test
	public void deleteCategory_should_work(){
		rep.deleteCategory(8);
		Category c = rep.getCategory("butter");
		Assert.assertNull(c);
	}
	
	@Test
	public void deleteCategory_should_delete_in_children(){
		rep.deleteCategory(6);
		Category c = rep.getCategory("chicken");
		Assert.assertFalse(c.getParentCategories().contains(6));
	}
	
}
