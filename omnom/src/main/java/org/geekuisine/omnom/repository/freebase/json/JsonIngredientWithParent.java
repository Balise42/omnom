package org.geekuisine.omnom.repository.freebase.json;

import java.util.List;

import com.google.api.client.util.Key;

/** JsonIngredient with additional parent property */
public class JsonIngredientWithParent extends JsonIngredient {
	@Key("output")
	private JsonIngredientParent parent;
	
	public JsonIngredientWithParent(){
		parent = new JsonIngredientParent();
	}
	
	public JsonIngredientParent getParent() {
		return parent;
	}
	public void setParent(JsonIngredientParent parent) {
		this.parent = parent;
	}
	
	public List<JsonIngredient> getParentIngredients(){
		if(parent == null){
			return null;
		}
		if(parent.getInternal() == null){
			return null;
		}
		return parent.getInternal().getParent();
	}
	
}