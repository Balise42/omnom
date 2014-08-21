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
		Category ingredient = new Category(getNextId(), "Ingredient");
		categoryRepository.add(ingredient);
		
		Category poultry = new Category(getNextId(), "Poultry", ingredient);
		Category chicken = new Category(getNextId(), "Chicken", poultry);
		Category duck = new Category(getNextId(), "Duck", poultry);
		Category chickenLeg = new Category(getNextId(), "Chicken leg", chicken);
		Category condiment = new Category(getNextId(), "Condiment", ingredient);
		Category salt = new Category(getNextId(), "Salt", condiment);
		Category fat = new Category(getNextId(), "Fat", ingredient);
		Category butter = new Category(getNextId(), "Butter", fat);
		categoryRepository.add(poultry);
		categoryRepository.add(chicken);
		categoryRepository.add(duck);
		categoryRepository.add(chickenLeg);
		categoryRepository.add(condiment);
		categoryRepository.add(salt);
		categoryRepository.add(fat);
		categoryRepository.add(butter);
		
	}
	
	public Category getCategory(String s){
		for(Category cat : categoryRepository){
			if(cat.getName().equalsIgnoreCase(s)){
				return cat;
			}
		}
		return null;
	}
	
	@Override
	public List<Category> getAllCategories() {
		return categoryRepository;
	}
	
	@Override
	public List<Category> getChildrenCategories(Category c){
		List<Category> children = new ArrayList<Category>();
		for(Category cat : categoryRepository){
			if(cat.getParentCategories().contains(c.getCategoryId())){
				children.add(cat);
			}
		}
		return children;
	}
	
	public synchronized int getNextId(){
		return nextId++;
	}

	@Override
	public Category addCategory(String s) {
		Category c = getCategory(s);
		if(c != null){
			return c;
		}
		c = new Category(getNextId(), s);
		categoryRepository.add(c);
		return c;
	}
	

}
