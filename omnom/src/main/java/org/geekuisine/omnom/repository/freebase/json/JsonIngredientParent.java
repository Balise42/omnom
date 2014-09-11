package org.geekuisine.omnom.repository.freebase.json;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

/** Basic info from JSON parent ingredients, including managing weird JSON structure */
public class JsonIngredientParent {
	/** needed by JSON structure */
	public static class InternalParent{
		@Key("/food/ingredient/more_general_ingredient")
		private List<JsonIngredient> parent;
		
		public InternalParent(){
			parent = new ArrayList<JsonIngredient>();
		}

		public List<JsonIngredient> getParent() {
			return parent;
		}

		public void setParent(List<JsonIngredient> parent) {
			this.parent = parent;
		}
	}
	
	@Key("/food/ingredient/more_general_ingredient")
	private InternalParent internal;
	
	public JsonIngredientParent(){
		internal = new InternalParent();
	}

	public InternalParent getInternal() {
		return internal;
	}

	public void setInternal(InternalParent internal) {
		this.internal = internal;
	}
	
}