package org.geekuisine.omnom.domain;

public class Category {
	int categoryId;
	String name;
	int topCategoryId;
	
	public Category(){
		super();
	}
	
	public Category(int categoryId, String name, int topCategoryId){
		this.categoryId = categoryId;
		this.name = name;
		this.topCategoryId = topCategoryId;
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

	public int getTopCategoryId() {
		return topCategoryId;
	}

	public void setTopCategoryId(int topCategoryId) {
		this.topCategoryId = topCategoryId;
	}

}
