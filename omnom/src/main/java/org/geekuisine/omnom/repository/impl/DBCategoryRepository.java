package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DBCategoryRepository implements CategoryRepository {
	private int nextId;
	
	public DBCategoryRepository() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		try{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:omnom.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select max(id) as maxCat from category");
			if(rs.next()){
				nextId = rs.getInt("maxCat")+1;
			}
			connection.close();
		}
		catch(SQLException ex){
			System.err.println(ex);
			System.exit(-1);
		}
		finally{
		}
	}

	@Override
	public List<Category> getAllCategories() {
		try{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:omnom.db");
			List<Category> categories = new ArrayList<Category>();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, idParent from category, parent where category.id = parent.idCategory");
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int parent = rs.getInt("idParent");
				boolean createNew = true;
				for(int i = 0; i<categories.size(); i++){
					if(categories.get(i).getCategoryId() == id){
						Category category = categories.get(i);
						category.addParentWithoutGrandparents(parent);
						categories.set(i, category);
						createNew = false;
					}
				}
				if(createNew){
					Category category = new Category(id, name);
					category.addParentWithoutGrandparents(parent);
					categories.add(category);
				}
			}
			connection.close();
			return categories;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Category> getChildrenCategories(Category c) {
		try{
			List<Category> children = new ArrayList<Category>();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:omnom.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select id,name from category, parent where category.id = parent.idCategory and parent.idParent="+c.getCategoryId());
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category child = new Category(id, name);
				ResultSet rs2 = statement.executeQuery("select idParent from parent where idCategory ="+id);
				while(rs2.next()){
					int idParent = rs.getInt("idParent");
					child.addParentWithoutGrandparents(idParent);
				}
				children.add(child);
			}
			connection.close();
			return children;
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Category getCategory(String s) {
		try{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:omnom.db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, idParent from category, parent where name ='"+s+"' and category.id = parent.idCategory");
			if(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category c = new Category(id, name);
				do{
					int idParent = rs.getInt("idParent");
					c.addParentWithoutGrandparents(idParent);
				} while(rs.next());
				connection.close();
				return c;
			}
			else{
				connection.close();
				return null;
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Category addCategory(String s) {
		try{
			Category c = getCategory(s);
			if(c != null){
				return c;
			}
			else{
				Connection connection = DriverManager.getConnection("jdbc:sqlite:omnom.db");
				Statement statement = connection.createStatement();
				int idNewCategory = getNextIdAndIncrement();
				statement.executeUpdate("insert into category values("+idNewCategory+", '"+s+"')");
				statement.executeUpdate("insert into parent values("+idNewCategory+", 0)");
				connection.close();
				return getCategory(s);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	private synchronized int getNextIdAndIncrement(){
		return nextId++;
	}

}
