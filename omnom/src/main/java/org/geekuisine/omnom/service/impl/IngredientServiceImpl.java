package org.geekuisine.omnom.service.impl;

import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/** Implementation of the IngredientService interface */
public class IngredientServiceImpl implements IngredientService {
	@Autowired
	/** Uses the current IngredientRepository */ 
	private IngredientRepository ingredientRepository;

	public void addIngredient(String s){
		ingredientRepository.addIngredient(s);
	}
	
	public List<Ingredient> getAllIngredients(){
		return ingredientRepository.getAllIngredients();
	}
}
