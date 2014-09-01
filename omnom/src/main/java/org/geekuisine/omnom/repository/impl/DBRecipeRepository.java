package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.domain.Quantity;
import org.geekuisine.omnom.domain.Recipe;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
/** DB (sqlite) repository implementation for recipes */
public class DBRecipeRepository implements RecipeRepository {
	@Autowired
	/** Ingredient repository to fetch ingredients */
	private IngredientRepository ingredientRepository;
	
	/** Connection string to the DB */
	private String connectionString;

	/** Default constructor - just inits the JDBC driver */
	public DBRecipeRepository() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		connectionString = System.getProperty("omnom.db.connectionString");
		if(connectionString == null){
			connectionString="jdbc:sqlite:omnom.db";
		}
	}
	
	@Override
	public List<Recipe> getAllRecipes() {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			List<Recipe> recipes = new ArrayList<Recipe>();
			Statement statement = connection.createStatement();
			/* TODO: check the impact of fetching everything in one query */
			ResultSet rs = statement.executeQuery("select id from recipe");
			List<Integer> recipeIds = new ArrayList<Integer>();
			while(rs.next()){
				recipeIds.add(rs.getInt("id"));
			}
			connection.close();
			for(int i : recipeIds){
				recipes.add(getRecipeById(i));
			}
			return recipes;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Recipe getRecipeById(int recipeId) {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			Recipe recipe = new Recipe();
			/* TODO: check the impact of fetching everything in one query */
			PreparedStatement statement = connection.prepareStatement("select id, name, numPersons, cookingTime, restingTime, prepTime, steps from recipe where id = ?");
			statement.setInt(1, recipeId);
			ResultSet rs = statement.executeQuery();
			if(!rs.next()){
				return null;
			}
			recipe.setRecipeId(rs.getInt("id"));
			recipe.setName(rs.getString("name"));
			recipe.setNumPersons(rs.getInt("numPersons"));
			recipe.setCookingTime(rs.getInt("cookingTime"));
			recipe.setPrepTime(rs.getInt("prepTime"));
			recipe.setRestTime(rs.getInt("restingTime"));
			recipe.setSteps(rs.getString("steps"));
			statement = connection.prepareStatement("select ingredientId, unit, numberOfUnits, fuzzy from recipeIngredients where idRecipe = ?");
			statement.setInt(1, recipeId);
			rs = statement.executeQuery();
			while(rs.next()){
				Quantity q = new Quantity();
				q.setFuzzy(rs.getBoolean("fuzzy"));
				q.setNumberOfUnits(rs.getBigDecimal("numberOfUnits"));
				q.setUnit(rs.getString("unit"));
				Ingredient i = ingredientRepository.getIngredient(rs.getInt("ingredientId"));
				recipe.addIngredient(i, q);
			}
			connection.close();
			return recipe;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void addRecipe(Recipe r) {
		// TODO Auto-generated method stub
		
	}

}
