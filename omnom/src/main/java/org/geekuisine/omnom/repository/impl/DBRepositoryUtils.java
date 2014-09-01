package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	public void populate(){
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("CREATE TABLE ingredient(id integer primary key, name text)");
			s.executeUpdate("CREATE TABLE parent(ingredientId integer, parentId integer, primary key(ingredientId, parentId))");
			s.executeUpdate("CREATE TABLE recipe(id integer primary key, name text, numPersons integer, cookingTime integer, restingTime integer, prepTime integer,steps text)");
			s.executeUpdate("CREATE TABLE recipeIngredients(idRecipe integer,ingredientId integer,unit varchar(30),numberOfUnits real,fuzzy integer)");

			
			s.executeUpdate("INSERT INTO ingredient VALUES(0,'ingredient')");
			s.executeUpdate("INSERT INTO ingredient VALUES(1,'condiment')");
			s.executeUpdate("INSERT INTO ingredient VALUES(2,'salt')");
			s.executeUpdate("INSERT INTO ingredient VALUES(3,'dairy')");
			s.executeUpdate("INSERT INTO ingredient VALUES(4,'fat')");
			s.executeUpdate("INSERT INTO ingredient VALUES(5,'meat')");
			s.executeUpdate("INSERT INTO ingredient VALUES(6,'poultry')");
			s.executeUpdate("INSERT INTO ingredient VALUES(7,'chicken')");
			s.executeUpdate("INSERT INTO ingredient VALUES(8,'butter')");

			s.executeUpdate("INSERT INTO parent VALUES(1,0)");
			s.executeUpdate("INSERT INTO parent VALUES(2,0)");
			s.executeUpdate("INSERT INTO parent VALUES(2,1)");
			s.executeUpdate("INSERT INTO parent VALUES(3,0)");
			s.executeUpdate("INSERT INTO parent VALUES(4,0)");
			s.executeUpdate("INSERT INTO parent VALUES(5,0)");
			s.executeUpdate("INSERT INTO parent VALUES(6,0)");
			s.executeUpdate("INSERT INTO parent VALUES(6,5)");
			s.executeUpdate("INSERT INTO parent VALUES(7,0)");
			s.executeUpdate("INSERT INTO parent VALUES(7,6)");
			s.executeUpdate("INSERT INTO parent VALUES(8,0)");
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
}
