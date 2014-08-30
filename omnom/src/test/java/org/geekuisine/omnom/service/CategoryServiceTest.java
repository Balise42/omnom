package org.geekuisine.omnom.service;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-DispatcherServlet-context.xml")
@WebAppConfiguration
public class CategoryServiceTest {
	
	@Autowired
	CategoryService categoryService;
	
	public CategoryServiceTest(){
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test.db");
	}
	
	@Before
	public void init(){
		DBRepositoryUtils dbutils = new DBRepositoryUtils();
		dbutils.truncateAll();
		dbutils.populate();
	}
	
	@Test
	public void create_category_should_work(){
		Category cat = new Category(1000, "newItem");
		Assert.assertNotNull(categoryService.create(cat));
	}
	
	@Test
	public void read_category_from_id_should_work(){
		Assert.assertNotNull(categoryService.read(3));
	}
	
	@Test
	public void read_category_from_name_should_work(){
		Assert.assertNotNull(categoryService.read("chicken"));
	}
	
	@Test
	public void update_category_should_work(){
		Category cat = categoryService.read("chicken");
		cat.addParentWithoutGrandparents(5);
		categoryService.update(cat);
		Assert.assertTrue(categoryService.read("chicken").getParentCategories().contains(5));
	}
	
	@Test
	public void delete_category_should_work(){
		categoryService.delete(3);
		Assert.assertNull(categoryService.read(3));
	}
}
