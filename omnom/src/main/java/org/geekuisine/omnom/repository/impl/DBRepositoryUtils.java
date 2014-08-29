package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBRepositoryUtils {
	private String connectionString;
	
	public DBRepositoryUtils(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		connectionString = System.getProperty("omnom.db.connectionString");
	}
	
	public void truncateAll(){
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("drop table parent");
			s.executeUpdate("drop table category");
			s.executeUpdate("drop table recipeingredients");
			s.executeUpdate("drop table ingredient");
			s.executeUpdate("drop table recipe");
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void populate(){
		try{
			Connection connection = DriverManager.getConnection(connectionString);
			Statement s = connection.createStatement();
			s.executeUpdate("CREATE TABLE category(id integer primary key,name text)");
			s.executeUpdate("CREATE TABLE ingredient(id integer primary key, name text)");
			s.executeUpdate("CREATE TABLE parent(idCategory integer, idParent integer, primary key(idCategory, idParent))");
			s.executeUpdate("CREATE TABLE recipe(id integer primary key, name text, numPersons integer, cookingTime integer, restingTime integer, prepTime integer,steps text)");
			s.executeUpdate("CREATE TABLE recipeIngredients(idRecipe integer,idIngredient integer,unit varchar(30),numberOfUnits real,fuzzy integer)");

			
			s.executeUpdate("INSERT INTO category VALUES(0,'ingredient')");
			s.executeUpdate("INSERT INTO category VALUES(1,'condiment')");
			s.executeUpdate("INSERT INTO category VALUES(2,'salt')");
			s.executeUpdate("INSERT INTO category VALUES(3,'dairy')");
			s.executeUpdate("INSERT INTO category VALUES(4,'fat')");
			s.executeUpdate("INSERT INTO category VALUES(5,'meat')");
			s.executeUpdate("INSERT INTO category VALUES(6,'poultry')");
			s.executeUpdate("INSERT INTO category VALUES(7,'chicken')");
			s.executeUpdate("INSERT INTO category VALUES(8,'butter')");
			
			s.executeUpdate("INSERT INTO ingredient VALUES(0,'chicken')");
			s.executeUpdate("INSERT INTO ingredient VALUES(1,'salt')");
			s.executeUpdate("INSERT INTO ingredient VALUES(2,'butter')");

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
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
