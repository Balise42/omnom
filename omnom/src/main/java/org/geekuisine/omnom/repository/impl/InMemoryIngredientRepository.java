package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.exception.IngredientRepositoryException;
import org.springframework.stereotype.Repository;

//@Repository
/** First implementation of the category repository - stays in memory, no persistent storage.
 * Not for production use, only for dev purpose.
 * Should probably be implemented as singleton if it were to be used in any useful way. */
public class InMemoryIngredientRepository implements IngredientRepository {
	/** List of categories */
	List<Ingredient> categoryRepository;
	/** ID of the next category when added to the repository */
	int nextId;

	/** Default constructor: creates the repository and adds some data to it */
	public InMemoryIngredientRepository(){
		nextId = 0;
		categoryRepository = new ArrayList<Ingredient>();
		Ingredient ingredient = new Ingredient(getNextId(), "Ingredient");
		categoryRepository.add(ingredient);
		
		Ingredient poultry = new Ingredient(getNextId(), "Poultry", ingredient);
		Ingredient chicken = new Ingredient(getNextId(), "Chicken", poultry);
		Ingredient duck = new Ingredient(getNextId(), "Duck", poultry);
		Ingredient chickenLeg = new Ingredient(getNextId(), "Chicken leg", chicken);
		Ingredient meat = new Ingredient(getNextId(), "Meat",ingredient);
		Ingredient condiment = new Ingredient(getNextId(), "Condiment", ingredient);
		Ingredient salt = new Ingredient(getNextId(), "Salt", condiment);
		Ingredient fat = new Ingredient(getNextId(), "Fat", ingredient);
		Ingredient butter = new Ingredient(getNextId(), "Butter", fat);
		categoryRepository.add(poultry);
		categoryRepository.add(chicken);
		categoryRepository.add(duck);
		categoryRepository.add(chickenLeg);
		categoryRepository.add(meat);
		categoryRepository.add(condiment);
		categoryRepository.add(salt);
		categoryRepository.add(fat);
		categoryRepository.add(butter);
		
	}
	
	@Override
	public Ingredient getIngredient(String s){
		for(Ingredient cat : categoryRepository){
			if(cat.getName().equalsIgnoreCase(s)){
				return cat;
			}
		}
		return null;
	}
	
	@Override
	public List<Ingredient> getAllIngredients() {
		return categoryRepository;
	}
	
	@Override
	public List<Ingredient> getChildrenIngredients(Ingredient c){
		List<Ingredient> children = new ArrayList<Ingredient>();
		for(Ingredient cat : categoryRepository){
			if(cat.getParentIngredients().contains(c.getIngredientId())){
				children.add(cat);
			}
		}
		return children;
	}
	
	public synchronized int getNextId(){
		return nextId++;
	}

	@Override
	public Ingredient addIngredient(String s) {
		Ingredient c = getIngredient(s);
		if(c != null){
			return c;
		}
		c = new Ingredient(getNextId(), s);
		categoryRepository.add(c);
		return c;
	}

	@Override
	public Ingredient getIngredient(int i) {
		for(Ingredient cat : categoryRepository){
			if(cat.getIngredientId() == i){
				return cat;
			}
		}
		return null;
	}

	@Override
	public void updateIngredient(Ingredient c) throws IngredientRepositoryException {
		validate(c);
		for(int i = 0; i<categoryRepository.size(); i++){
			if(categoryRepository.get(i).getIngredientId() == c.getIngredientId()){
				categoryRepository.set(i,  c);
				return;
			}
		}
	}

	@Override
	public void deleteIngredient(int index) {
		for(int i = 0; i<categoryRepository.size(); i++){
			if(categoryRepository.get(i).getIngredientId() == index){
				categoryRepository.remove(i);
				return;
			}
		}
	}

	@Override
	public void validate(Ingredient category) throws IngredientRepositoryException {
		if(getIngredient(category.getIngredientId()) == null){
			throw new IngredientRepositoryException("Could not find category with id"+category.getIngredientId());
		}
		for(int parent : category.getParentIngredients()){
			Ingredient parentCategory = getIngredient(parent);
			if(parentCategory == null){
				throw new IngredientRepositoryException("Could not find parent category with id"+parent);
			}
			if(parentCategory.getIngredientId() != 0 && !category.getParentIngredients().containsAll(parentCategory.getParentIngredients())){
				throw new IngredientRepositoryException("Parent hierarchy is invalid.");
			}
		}
	}
}
