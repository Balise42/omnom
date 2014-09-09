package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.exception.IngredientRepositoryException;
import org.springframework.stereotype.Repository;

//@Repository
/** First implementation of the category repository - stays in memory, no persistent storage.
 * Not for production use, only for dev purpose.
 * Should probably be implemented as singleton if it were to be used in any useful way. 
 * @deprecated feature compatibility with DB repository not guaranteed since Sep. 8th 2014 */
public class InMemoryIngredientRepository implements IngredientRepository {
	/** List of categories */
	List<Ingredient> ingredientRepository;
	/** ID of the next category when added to the repository */
	int nextId;

	/** Default constructor: creates the repository and adds some data to it */
	public InMemoryIngredientRepository(){
		nextId = 0;
		ingredientRepository = new ArrayList<Ingredient>();
		Ingredient ingredient = new Ingredient(getNextId(), "Ingredient");
		ingredientRepository.add(ingredient);
		
		Ingredient poultry = new Ingredient(getNextId(), "POULTRY");
		Ingredient chicken = new Ingredient(getNextId(), "CHICKEN");
		Ingredient chickenLeg = new Ingredient(getNextId(), "CHICKEN LEG");
		Ingredient meat = new Ingredient(getNextId(), "MEAT");
		Ingredient condiment = new Ingredient(getNextId(), "CONDIMENT");
		Ingredient salt = new Ingredient(getNextId(), "SALT");
		Ingredient fat = new Ingredient(getNextId(), "FAT");
		Ingredient butter = new Ingredient(getNextId(), "BUTTER");
		ingredientRepository.add(poultry);
		
		ingredientRepository.add(chicken);
		ingredientRepository.add(chickenLeg);
		ingredientRepository.add(meat);
		ingredientRepository.add(condiment);
		ingredientRepository.add(salt);
		ingredientRepository.add(fat);
		ingredientRepository.add(butter);
		
	}
	
	public void setParent(Ingredient ingredient, Ingredient parent){
		
	}
	
	private void addParentRelationship(){
		
	}
	
	@Override
	public Ingredient getIngredient(String s){
		for(Ingredient cat : ingredientRepository){
			if(cat.getName().equalsIgnoreCase(s)){
				return cat;
			}
		}
		return null;
	}
	
	@Override
	public List<Ingredient> getAllIngredients() {
		return ingredientRepository;
	}
	
	@Override
	public List<Ingredient> getChildrenIngredients(Ingredient c){
		List<Ingredient> children = new ArrayList<Ingredient>();
		for(Ingredient cat : ingredientRepository){
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
		c = new Ingredient(getNextId(), s.toUpperCase(Locale.ENGLISH));
		ingredientRepository.add(c);
		return c;
	}

	@Override
	public Ingredient getIngredient(int i) {
		for(Ingredient cat : ingredientRepository){
			if(cat.getIngredientId() == i){
				return cat;
			}
		}
		return null;
	}

	@Override
	public void updateIngredient(Ingredient c) throws IngredientRepositoryException {
		validate(c);
		for(int i = 0; i<ingredientRepository.size(); i++){
			if(ingredientRepository.get(i).getIngredientId() == c.getIngredientId()){
				c.setName(c.getName().toUpperCase());
				ingredientRepository.set(i,  c);
				return;
			}
		}
	}

	@Override
	public void deleteIngredient(int index) {
		for(int i = 0; i<ingredientRepository.size(); i++){
			if(ingredientRepository.get(i).getIngredientId() == index){
				ingredientRepository.remove(i);
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
