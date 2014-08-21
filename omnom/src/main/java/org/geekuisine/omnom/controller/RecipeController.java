package org.geekuisine.omnom.controller;

import org.geekuisine.omnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
	@Autowired
	RecipeService recipeService;
	
	@RequestMapping("/recipes")
	public String getAllRecipes(Model model){
		recipeService.init();
		model.addAttribute("recipes", recipeService.getAllRecipes());
		return "recipes";
	}
	
	@RequestMapping("/recipe/{id}")
	public String getRecipeById(Model model, @PathVariable("id") int id){
		recipeService.init();
		model.addAttribute("recipe", recipeService.getRecipeById(id));
		return "recipe";
	}
}
