package org.geekuisine.omnom.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {
	int categoryId;
	String name;
	List<Integer> parentCategories;
	
	public Category(){
		parentCategories = new ArrayList<Integer>();
	}
	
	public Category(int categoryId, String name, int topCategoryId){
		this();
		this.categoryId = categoryId;
		this.name = name;
		parentCategories.add(topCategoryId);
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

	public List<Integer> getParentCategories() {
		return parentCategories;
	}

	public void setTopCategoryId(List<Integer> parentCategories) {
		this.parentCategories = parentCategories;
	}
	
	public void addParent(int parent){
		for(int cat : parentCategories){
			if(cat == parent){
				return;
			}
		}
		parentCategories.add(parent);
	}

}
