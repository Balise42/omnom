package org.geekuisine.omnom.domain;

public class Category {
	int categoryId;
	String name;
	Category topCategory;
	
	public Category(){
		super();
	}
	
	public Category(int categoryId, String name, Category topCategory){
		this.categoryId = categoryId;
		this.name = name;
		this.topCategory = topCategory;
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

	public Category getTopCategory() {
		return topCategory;
	}

	public void setTopCategory(Category topCategory) {
		this.topCategory = topCategory;
	}

}
