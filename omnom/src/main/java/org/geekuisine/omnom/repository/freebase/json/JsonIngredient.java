package org.geekuisine.omnom.repository.freebase.json;

import com.google.api.client.util.Key;

/** Object used to get the most basic info from JSON ingredient query */
public class JsonIngredient {
	@Key("mid")
	private String mid;
	@Key("name")
	private String name;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
