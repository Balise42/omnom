package org.geekuisine.omnom.controller;

import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.form.RecipeForm;
import org.geekuisine.omnom.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RecipeController {
	@Autowired
	RecipeService recipeService;
	
	@RequestMapping("/recipes")
	public String getAllRecipes(Model model){
		model.addAttribute("recipes", recipeService.getAllRecipes());
		return "recipes";
	}
	
	@RequestMapping("/recipe/{id}")
	public String getRecipeById(Model model, @PathVariable("id") int id){
		model.addAttribute("recipe", recipeService.getRecipeById(id));
		return "recipe";
	}
	
	@RequestMapping(value="/recipes/add", method = RequestMethod.GET)
	public String addRecipe(Model model){
		model.addAttribute("newRecipe", new RecipeForm());
		return "addRecipe";
	}
	
	@RequestMapping(value="/recipes/add", method = RequestMethod.POST)
	public String processRecipe(Model model, @ModelAttribute("newRecipe") RecipeForm recipeForm){
		Recipe r = recipeForm.getRecipe();
		recipeService.addRecipe(r);
		return "redirect:/recipes";
	}
}
