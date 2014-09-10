package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.IngredientRepository;
import org.geekuisine.omnom.repository.exception.IngredientRepositoryException;
import org.springframework.stereotype.Repository;

@Repository
/** DB (sqlite) implementation of the Category repository */
public class DBIngredientRepository implements IngredientRepository {
	/** Connection string to the DB */
	private String connectionString;

	/** Default constructor - just inits the JDBC driver */
	public DBIngredientRepository() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		connectionString = System.getProperty("omnom.db.connectionString");
		if(connectionString == null){
			connectionString="jdbc:sqlite:omnom.db";
		}
	}

	@Override
	public List<Ingredient> getAllIngredients() {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			List<Ingredient> categories = new ArrayList<Ingredient>();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, idParent from ingredient, parent where ingredient.id = parent.idIngredient");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int parent = rs.getInt("idParent");
				boolean createNew = true;
				for (int i = 0; i < categories.size(); i++) {
					if (categories.get(i).getIngredientId() == id) {
						Ingredient category = categories.get(i);
						category.addParent(parent);
						categories.set(i, category);
						createNew = false;
					}
				}
				if (createNew) {
					Ingredient category = new Ingredient(id, name);
					category.addParent(parent);
					categories.add(category);
				}
			}
			connection.close();
			return categories;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Ingredient> getChildrenIngredients(Ingredient c) {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			List<Ingredient> children = new ArrayList<Ingredient>();
			PreparedStatement statement = connection.prepareStatement("select id,name from ingredient, parent where ingredient.id = parent.idIngredient and parent.idParent= ?");
			statement.setInt(1, c.getIngredientId());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Ingredient child = new Ingredient(id, name);
				PreparedStatement statement2 = connection.prepareStatement("select idParent from parent where idIngredient = ?");
				statement2.setInt(1, id);
				ResultSet rs2 = statement2.executeQuery();
				while (rs2.next()) {
					int idParent = rs2.getInt("idParent");
					child.addParent(idParent);
				}
				children.add(child);
			}
			connection.close();
			return children;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Ingredient> getAllChildren(Ingredient c){
		Queue<Integer> childrenQueue = new ArrayDeque<Integer>();
		Map<Integer,Boolean> visited = new HashMap<Integer,Boolean>();
		List<Ingredient> result = new ArrayList<Ingredient>();
		List<Ingredient> children = getChildrenIngredients(c);
		for(Ingredient child : children){
			childrenQueue.add(child.getIngredientId());
		}
		while(!childrenQueue.isEmpty()){
			int childId = childrenQueue.remove();
			if(!visited.containsKey(childId) || !visited.get(childId)){
				Ingredient ingredient = getIngredient(childId);
				List<Ingredient> childChildren = getChildrenIngredients(ingredient);
				for(Ingredient childChild : childChildren){
					childrenQueue.add(childChild.getIngredientId());
				}
				result.add(ingredient);
				visited.put(childId, true);
			}
		}
		return result;
	}

	@Override
	public Ingredient getIngredient(String s) {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			PreparedStatement statement = connection.prepareStatement("select id, name, idParent from ingredient, parent where name = ? and ingredient.id = parent.idIngredient");
			statement.setString(1, s.toUpperCase(Locale.ENGLISH));

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Ingredient c = new Ingredient(id, name);
				do {
					int idParent = rs.getInt("idParent");
					c.addParent(idParent);
				} while (rs.next());
				connection.close();
				return c;
			} else {
				connection.close();
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Ingredient addIngredient(String s) {
		Ingredient c = getIngredient(s);
		if (c != null) {
			return c;
		} else {
			try (Connection connection = DriverManager.getConnection(connectionString);) {
				int idNewCategory = getNextIdAndIncrement();
				PreparedStatement statement = connection.prepareStatement("insert into ingredient values (?, ?)");
				statement.setInt(1, idNewCategory);
				statement.setString(2, s.toUpperCase(Locale.ENGLISH));
				statement.executeUpdate();
				statement = connection.prepareStatement("insert into parent values (?, 0)");
				statement.setInt(1, idNewCategory);
				statement.execute();
				connection.close();
				return getIngredient(s);
			} catch (SQLException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	/** Gets the ID of the last category that has been added to the DB, returns the ID
	 * of the next category to be inserted in the DB. 
	 * TODO fix race condition as said ID is not reserved... */
	private synchronized int getNextIdAndIncrement() {
		int nextId = 0;
		try (Connection connection = DriverManager.getConnection(connectionString);){
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select max(id) as maxIng from ingredient");
			if (rs.next()) {
				nextId = rs.getInt("maxIng") + 1;
			} else {
				throw (new SQLException("Couldn't get next id"));
			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		return nextId;
	}

	@Override
	public Ingredient getIngredient(int i) {
		try(Connection connection = DriverManager.getConnection(connectionString)){	
			PreparedStatement statement = connection.prepareStatement("select id, name, idParent from ingredient, parent where id = ? and ingredient.id = parent.idIngredient");
			statement.setInt(1, i);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Ingredient c = new Ingredient(id, name);
				do {
					int idParent = rs.getInt("idParent");
					c.addParent(idParent);
				} while (rs.next());
				connection.close();
				return c;
			} else {
				connection.close();
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateIngredient(Ingredient c) throws IngredientRepositoryException {
		validate(c);
		try(Connection connection = DriverManager.getConnection(connectionString)){
			PreparedStatement statement = connection.prepareStatement("update ingredient set name = ? where id = ? ");
			statement.setString(1, c.getName().toUpperCase(Locale.ENGLISH));
			statement.setInt(2, c.getIngredientId());
			statement.executeUpdate();
			statement = connection.prepareStatement("delete from parent where idIngredient = ?");
			statement.setInt(1, c.getIngredientId());
			statement.executeUpdate();
			for(int parent : c.getParentIngredients()){
				statement = connection.prepareStatement("insert into parent values (?,?)");
				statement.setInt(1, c.getIngredientId());
				statement.setInt(2, parent);
				statement.executeUpdate();
			}
			connection.close();
		}
		catch(SQLException ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void deleteIngredient(int i) {
		Ingredient ingredient = getIngredient(i);
		for(int parent : ingredient.getParentIngredients()){
			for(Ingredient child : getChildrenIngredients(ingredient)){
				child.addParent(parent);
				updateIngredient(child);
			}
		}
		try(Connection connection = DriverManager.getConnection(connectionString)){
			PreparedStatement statement = connection.prepareStatement("delete from ingredient where id = ?");
			statement.setInt(1, i);
			statement.executeUpdate();
			statement = connection.prepareStatement("delete from parent where idParent = ? or idIngredient = ?");
			statement.setInt(1, i);
			statement.setInt(2, i);
			statement.executeUpdate();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void validate(Ingredient c) throws IngredientRepositoryException {
		Ingredient cat = getIngredient(c.getIngredientId());
		if(cat == null){
			throw new IngredientRepositoryException("Could not find ingredient with id"+c.getIngredientId());
		}
		for(int parent : c.getParentIngredients()){
			Ingredient parentCategory = getIngredient(parent);
			if(parentCategory == null){
				throw new IngredientRepositoryException("Could not find parent ingredient with id"+parent);
			}
		}
	}

}
