package org.geekuisine.omnom.controller;

import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/** Welcome page controller */
public class HomeController {
	@RequestMapping("/")
	public String welcome(Model model){
		model.addAttribute("greeting", "Welcome to Omnom");
		model.addAttribute("tagline", "A nice recipe repository");
		
		return "welcome";
	}
	
	@RequestMapping("/reinit")
	public String reinit(Model model){
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom.db");
		DBRepositoryUtils utils = new DBRepositoryUtils();
		utils.dropAllTables();
		utils.populate();
		return "welcome";
	}
}
