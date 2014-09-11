package org.geekuisine.omnom.repository.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.freebase.FreebaseUtils;

/** Utils to empty and re-populate the DB repositories. 
 * Should not be used during normal operations. */
public class DBRepositoryUtils {
	private String connectionString;
	
	/** Sets the connection string for the database that is currently set up */
	public DBRepositoryUtils(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		connectionString = System.getProperty("omnom.db.connectionString");
	}
	
	/** Drops all the tables */
	public void dropAllTables(){
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("drop table if exists parent");
			s.executeUpdate("drop table if exists recipeingredients");
			s.executeUpdate("drop table if exists ingredient");
			s.executeUpdate("drop table if exists recipe");
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	/** Creates the DB structure and populates it with a few base elements */
	public void populate_with_dummy(){
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("CREATE TABLE ingredient(id integer primary key, name text)");
			s.executeUpdate("CREATE TABLE parent(idIngredient integer, idParent integer, primary key(idIngredient, idParent))");
			s.executeUpdate("CREATE TABLE recipe(id integer primary key, name text, numPersons integer, cookingTime integer, restingTime integer, prepTime integer,steps text)");
			s.executeUpdate("CREATE TABLE recipeIngredients(idRecipe integer, idIngredient integer,unit varchar(30),numberOfUnits real,fuzzy integer)");

			
			s.executeUpdate("INSERT INTO ingredient VALUES(0,'INGREDIENT')");
			s.executeUpdate("INSERT INTO ingredient VALUES(1,'CONDIMENT')");
			s.executeUpdate("INSERT INTO ingredient VALUES(2,'SALT')");
			s.executeUpdate("INSERT INTO ingredient VALUES(3,'DAIRY')");
			s.executeUpdate("INSERT INTO ingredient VALUES(4,'FAT')");
			s.executeUpdate("INSERT INTO ingredient VALUES(5,'MEAT')");
			s.executeUpdate("INSERT INTO ingredient VALUES(6,'POULTRY')");
			s.executeUpdate("INSERT INTO ingredient VALUES(7,'CHICKEN')");
			s.executeUpdate("INSERT INTO ingredient VALUES(8,'BUTTER')");

			s.executeUpdate("INSERT INTO parent VALUES(1,0)");
			s.executeUpdate("INSERT INTO parent VALUES(2,1)");
			s.executeUpdate("INSERT INTO parent VALUES(3,0)");
			s.executeUpdate("INSERT INTO parent VALUES(4,0)");
			s.executeUpdate("INSERT INTO parent VALUES(5,0)");
			s.executeUpdate("INSERT INTO parent VALUES(6,5)");
			s.executeUpdate("INSERT INTO parent VALUES(7,6)");
			s.executeUpdate("INSERT INTO parent VALUES(8,3)");
			s.executeUpdate("INSERT INTO parent VALUES(8,4)");
			s.executeUpdate("INSERT INTO parent VALUES(0,0)");
			
			s.executeUpdate("INSERT INTO recipe VALUES(0, 'Oven roasted chicken', 4, 60, 0, 10, 'Put some butter and salt on the chicken.\nPut it in the oven at 240°C.\nCarve and eat.')");
			s.executeUpdate("INSERT INTO recipeIngredients VALUES(0, 2, 'large pinch', 1, 1)");
			s.executeUpdate("INSERT INTO recipeIngredients VALUES(0, 7, 'chicken', 1, 0)");
			s.executeUpdate("INSERT INTO recipeIngredients VALUES(0, 8, 'tbsp', 1, 0)");
			
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	/** Fetches the ingredient data from Freebase, and populates the ingredient DB accordingly. 
	 * @throws IOException 
	 * @throws GeneralSecurityException */
	public void populate_with_freebase() throws GeneralSecurityException, IOException{
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("CREATE TABLE ingredient(id integer primary key, name text)");
			s.executeUpdate("CREATE TABLE parent(idIngredient integer, idParent integer, primary key(idIngredient, idParent))");
			s.executeUpdate("CREATE TABLE recipe(id integer primary key, name text, numPersons integer, cookingTime integer, restingTime integer, prepTime integer,steps text)");
			s.executeUpdate("CREATE TABLE recipeIngredients(idRecipe integer, idIngredient integer,unit varchar(30),numberOfUnits real,fuzzy integer)");
			
			
			FreebaseUtils freebase = new FreebaseUtils();
			freebase.fetchIngredients();
			freebase.jsonIngredientsToIngredients();
			List<Ingredient> ingredients = freebase.getIngredients();
			
			for(Ingredient ingredient : ingredients){
				PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient VALUES(?,?)");
				statement.setInt(1, ingredient.getIngredientId());
				statement.setString(2, ingredient.getName());
				statement.executeUpdate();
				for(int parent : ingredient.getParentIngredients()){
					statement = connection.prepareStatement("INSERT INTO parent VALUES (?,?)");
					statement.setInt(1, ingredient.getIngredientId());
					statement.setInt(2, parent);
					statement.executeUpdate();
				}
			}
			
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
