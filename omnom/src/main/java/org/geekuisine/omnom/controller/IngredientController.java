package org.geekuisine.omnom.controller;

import org.geekuisine.omnom.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/** Ingredient management controller */
public class IngredientController {
	@Autowired
	IngredientService ingredientService;
	
	@RequestMapping("/ingredient/all")
	/** List of all ingredients */
	public String listAllIngredients(Model m){
		m.addAttribute("ingredients", ingredientService.list());
		return("ingredients");
	}
}
