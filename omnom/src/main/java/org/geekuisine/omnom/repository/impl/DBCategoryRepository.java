package org.geekuisine.omnom.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.geekuisine.omnom.domain.Category;
import org.geekuisine.omnom.repository.CategoryRepository;
import org.geekuisine.omnom.repository.exception.CategoryRepositoryException;
import org.springframework.stereotype.Repository;

@Repository
/** DB (sqlite) implementation of the Category repository */
public class DBCategoryRepository implements CategoryRepository {
	/** Connection string to the DB */
	private String connectionString;

	/** Default constructor - just inits the JDBC driver */
	public DBCategoryRepository() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		connectionString = System.getProperty("omnom.db.connectionString");
	}

	@Override
	public List<Category> getAllCategories() {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			List<Category> categories = new ArrayList<Category>();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select id, name, idParent from category, parent where category.id = parent.idCategory");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int parent = rs.getInt("idParent");
				boolean createNew = true;
				for (int i = 0; i < categories.size(); i++) {
					if (categories.get(i).getCategoryId() == id) {
						Category category = categories.get(i);
						category.addParentWithoutGrandparents(parent);
						categories.set(i, category);
						createNew = false;
					}
				}
				if (createNew) {
					Category category = new Category(id, name);
					category.addParentWithoutGrandparents(parent);
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
	public List<Category> getChildrenCategories(Category c) {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			List<Category> children = new ArrayList<Category>();
			PreparedStatement statement = connection.prepareStatement("select id,name from category, parent where category.id = parent.idCategory and parent.idParent= ?");
			statement.setInt(1, c.getCategoryId());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category child = new Category(id, name);
				PreparedStatement statement2 = connection.prepareStatement("select idParent from parent where idCategory= ?");
				statement2.setInt(1, id);
				ResultSet rs2 = statement2.executeQuery();
				while (rs2.next()) {
					int idParent = rs2.getInt("idParent");
					child.addParentWithoutGrandparents(idParent);
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

	@Override
	public Category getCategory(String s) {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			PreparedStatement statement = connection.prepareStatement("select id, name, idParent from category, parent where name = ? and category.id = parent.idCategory");
			statement.setString(1, s);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category c = new Category(id, name);
				do {
					int idParent = rs.getInt("idParent");
					c.addParentWithoutGrandparents(idParent);
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
	public Category addCategory(String s) {
		Category c = getCategory(s);
		if (c != null) {
			return c;
		} else {
			try (Connection connection = DriverManager.getConnection(connectionString);) {
				int idNewCategory = getNextIdAndIncrement();
				PreparedStatement statement = connection.prepareStatement("insert into category values (?, ?)");
				statement.setInt(1, idNewCategory);
				statement.setString(2, s);
				statement.executeUpdate();
				statement = connection.prepareStatement("insert into parent values (?, 0)");
				statement.setInt(1, idNewCategory);
				statement.execute();
				connection.close();
				return getCategory(s);
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
			ResultSet rs = statement.executeQuery("select max(id) as maxCat from category");
			if (rs.next()) {
				nextId = rs.getInt("maxCat") + 1;
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
	public Category getCategory(int i) {
		try(Connection connection = DriverManager.getConnection(connectionString)){	
			PreparedStatement statement = connection.prepareStatement("select id, name, idParent from category, parent where id = ? and category.id = parent.idCategory");
			statement.setInt(1, i);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category c = new Category(id, name);
				do {
					int idParent = rs.getInt("idParent");
					c.addParentWithoutGrandparents(idParent);
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
	public void updateCategory(Category c) throws CategoryRepositoryException {
		validate(c);
		try(Connection connection = DriverManager.getConnection(connectionString)){
			PreparedStatement statement = connection.prepareStatement("update category set name = ? where id = ? ");
			statement.setString(1, c.getName());
			statement.setInt(2, c.getCategoryId());
			statement.executeUpdate();
			statement = connection.prepareStatement("delete from parent where idCategory = ?");
			statement.setInt(1, c.getCategoryId());
			statement.executeUpdate();
			for(int parent : c.getParentCategories()){
				statement = connection.prepareStatement("insert into parent values (?,?)");
				statement.setInt(1, c.getCategoryId());
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
	public void deleteCategory(int i) {
		try(Connection connection = DriverManager.getConnection(connectionString)){
			PreparedStatement statement = connection.prepareStatement("delete from category where id = ?");
			statement.setInt(1, i);
			statement.executeUpdate();
			statement = connection.prepareStatement("delete from parent where idParent = ? or idCategory = ?");
			statement.setInt(1, i);
			statement.setInt(2, i);
			statement.executeUpdate();
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void validate(Category c) throws CategoryRepositoryException {
		Category cat = getCategory(c.getCategoryId());
		if(cat == null){
			throw new CategoryRepositoryException("Could not find category with id"+c.getCategoryId());
		}
		for(int parent : c.getParentCategories()){
			Category parentCategory = getCategory(parent);
			if(parentCategory == null){
				throw new CategoryRepositoryException("Could not find parent category with id"+parent);
			}
			if(parentCategory.getCategoryId() != 0 && !c.getParentCategories().containsAll(parentCategory.getParentCategories())){
				throw new CategoryRepositoryException("Parent hierarchy is invalid.");
			}
		}
	}

}
