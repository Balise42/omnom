package org.geekuisine.omnom.domain;

import java.util.HashSet;
import java.util.Set;

/** A class for ingredient categories (e.g. "meat", "fat", "condiment", etc). 
 * Allows a DAG structure by storing the parent categories. */

public class Category {
	/** ID of the category. Typically attributed by the repository. */
	int categoryId;
	/** Name of the category */
	String name;
	/** IDs of the parent categories */
	Set<Integer> parentCategories;
	
	/** Default constructor. Every category is derived from category "0" which represents "ingredient". */
	public Category(){
		parentCategories = new HashSet<Integer>();
		parentCategories.add(0);
	}
	
	/** Constructor with ID and name, no parent (will add "0" as a parent)*/
	public Category(int categoryId, String name){
		this(name);
		this.categoryId = categoryId;
	}
	
	/** Constructor with name, no parent (will add "0" as a parent)*/
	public Category(String name){
		this();
		this.name = name;
	}
	
	/** Constructor with name, id and one parent */
	public Category(int categoryId, String name, Category parent){
		this(categoryId, name);
		addParent(parent);
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Integer> getParentCategories() {
		return parentCategories;
	}
	
	public void setParentCategories(Set<Integer> parents){
		parentCategories = parents;
	}
	
	/** Add a category as a parent. Also adds the parents of that category as parents.
	 Parent category is supposed to be valid. */
	public void addParent(Category parent){
		parentCategories.add(parent.categoryId);
		if(parent.categoryId != 0){
			parentCategories.addAll(parent.getParentCategories());
		}
	}
	
	/** Add a parent ID to the list of parents. Doesn't do any check! */
	public void addParentWithoutGrandparents(int parent){
		parentCategories.add(parent);
	}

}
