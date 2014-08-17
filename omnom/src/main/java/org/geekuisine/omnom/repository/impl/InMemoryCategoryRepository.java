package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {
	List<Category> categoryRepository;
	int nextId;

	public InMemoryCategoryRepository(){
		nextId = 0;
		categoryRepository = new ArrayList<Category>();
		Category ingredient = new Category(getNextId(), "Ingredient", -1);
		categoryRepository.add(ingredient);
		
		Category poultry = new Category(getNextId(), "Poultry", ingredient.getCategoryId());
		Category chicken = new Category(getNextId(), "Chicken", poultry.getCategoryId());
		Category duck = new Category(getNextId(), "Duck", poultry.getCategoryId());
		Category chickenLeg = new Category(getNextId(), "Chicken leg", chicken.getCategoryId());
		categoryRepository.add(poultry);
		categoryRepository.add(chicken);
		categoryRepository.add(duck);
		categoryRepository.add(chickenLeg);
		
	}
	
	public Category getCategory(String s){
		for(Category cat : categoryRepository){
			if(cat.getName().equalsIgnoreCase(s)){
				return cat;
			}
		}
		throw new IllegalArgumentException("No such category");
	}
	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository;
	}
	
	@Override
	public List<Category> getChildrenCategories(Category c){
		List<Category> children = new ArrayList<Category>();
		for(Category cat : categoryRepository){
			if(cat.getTopCategoryId() == c.getCategoryId()){
				children.add(cat);
			}
		}
		return children;
	}
	
	private synchronized int getNextId(){
		return nextId++;
	}
	

}
