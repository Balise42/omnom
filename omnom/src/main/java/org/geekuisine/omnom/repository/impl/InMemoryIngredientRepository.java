package org.geekuisine.omnom.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.springframework.stereotype.Repository;

@Repository
/** First implementation of the ingredient repository - stays in memory, no persistent storage.
 * Not for production use, only for dev purpose.
 * Should probably be implemented as singleton if were to be of any real use.
 *  */
public class InMemoryIngredientRepository implements IngredientRepository {
	
	/** List of ingredients that actually make the repository */
	private List<Ingredient> repository;
	/** Index of the next ingredient being added in the repository */
	private int nextIndex;
	/** Category repository used when adding a recipe (doubtful if this should be here or at a higher level...)*/
	private CategoryRepository categoryRepository;
	
	/** Default constructor: creates the repository and adds a few ingredients to it */
	public InMemoryIngredientRepository(){
		nextIndex = 0;
		repository = new ArrayList<Ingredient>();
		categoryRepository = new InMemoryCategoryRepository();
		nextIndex = 0;
		addIngredient("chicken");
		addIngredient("butter");
		addIngredient("salt");
		Ingredient chicken = new Ingredient(getNextIndex(), "chicken");
		repository = new ArrayList<Ingredient>();
		repository.add(chicken);
		Ingredient butter = new Ingredient(getNextIndex(), "butter");
		repository.add(butter);
		Ingredient salt = new Ingredient(getNextIndex(), "salt");
		repository.add(salt);
	}
	
	@Override
	public List<Ingredient> getAllIngredients(){
		return repository;
	}
	
	@Override
	public Ingredient addIngredient(String name){
		
		Ingredient i = getIngredient(name);
		if(i != null){
			return i;
		}
		categoryRepository.addCategory(name);
		i = new Ingredient(getNextIndex(), name);
		repository.add(i);
		return i;
	}
	
	@Override
	public Ingredient getIngredient(String name){
		for(Ingredient ing : repository){
			if(ing.isIngredient(name)){
				return ing;
			}
		}
		return null;
	}
	
	private synchronized int getNextIndex(){
		return nextIndex++;
	}

	@Override
	public int getNextAttributedId() {
		return nextIndex;
	}
}
