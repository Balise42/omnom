package org.geekuisine.omnom.repository.freebase.json;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

/** List of JsonIngredientWithParents, used for JSON parsing */
public class JsonIngredientWithParentList {
	@Key("result")
	private List<JsonIngredientWithParent> ingredientList;
	
	public JsonIngredientWithParentList(){
		ingredientList = new ArrayList<JsonIngredientWithParent>();
	}
	
	public void setIngredientList(List<JsonIngredientWithParent> result){
		this.ingredientList = result;
	}
	
	public List<JsonIngredientWithParent> getIngredientList(){
		return ingredientList;
	}
}
