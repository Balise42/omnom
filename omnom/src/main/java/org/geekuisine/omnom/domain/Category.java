package org.geekuisine.omnom.domain;

import java.util.HashSet;
import java.util.Set;

public class Category {
	int categoryId;
	String name;
	Set<Integer> parentCategories;
	
	public Category(){
		parentCategories = new HashSet<Integer>();
	}
	
	public Category(int categoryId, String name){
		this();
		if(categoryId != 0){
			parentCategories.add(0);
		}
		this.categoryId = categoryId;
		this.name = name;
	}
	
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
	
	public void addParent(Category parent){
		parentCategories.add(parent.categoryId);
		if(parent.categoryId != 0){
			parentCategories.addAll(parent.getParentCategories());
		}
	}
	
	public void addParentWithoutGrandparents(int parent){
		parentCategories.add(parent);
	}

}
