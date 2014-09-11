package org.geekuisine.omnom.repository.freebase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geekuisine.omnom.domain.Ingredient;
import org.geekuisine.omnom.repository.freebase.json.JsonIngredient;
import org.geekuisine.omnom.repository.freebase.json.JsonIngredientWithParent;
import org.geekuisine.omnom.repository.freebase.json.JsonIngredientWithParentList;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

/** Manages the import of ingredients from Freebase */
public class FreebaseUtils {
	/** List of ingredients that are retrieved from Freebase */
	private JsonIngredientWithParentList jsonIngredients;
	/** List of ingredients converted from jsonIngredients */
	private List<Ingredient> ingredients;
	/** JsonFactory to parse JSON responses */
	private static final JsonFactory jsonFactory = new JacksonFactory();
	private int nextId = 0;
	
	/** generates ids */
	private int getNextIdAndIncrement(){
		return nextId++;
	}

	/** Return the list of ingredients */ 
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	/** Transform a list of JsonIngredients into "real" ingredients
	 * TODO: how to guarantee IDs when I have recipes depending on IDs??? right now IDs depend on the order of the JSON query response, which is incredibly bad! */
	public void jsonIngredientsToIngredients(){
		if (jsonIngredients == null){
			ingredients = null;
			return;
		}
		ingredients = new ArrayList<Ingredient>();
		Map<String, Integer> ingredientIds = new HashMap<String,Integer>();
		/* first add the ingredients, keeping track of the IDs */
		for(JsonIngredientWithParent jsonIngredient : jsonIngredients.getIngredientList()){
			int id = getNextIdAndIncrement();
			String name = jsonIngredient.getName();
			Ingredient i = new Ingredient(id, name);
			ingredients.add(i);
			ingredientIds.put(name, id);
		}
		
		/* then add the parents, using the fact that the generated ID is the index in the list... 
		 * TODO: fix this, it's ugly! */
		for(int i = 0; i<ingredients.size(); i++){
			for(JsonIngredient parent : jsonIngredients.getIngredientList().get(i).getParentIngredients()){
				String name = parent.getName();
				if(ingredientIds.containsKey(name)){
					int id = ingredientIds.get(name);
					ingredients.get(i).addParent(id);
				}
			}
		}
	}

	/** Construct the HTTP request, sends it to Freebase and stores the response into ingredients */
	public void fetchIngredients() throws GeneralSecurityException, IOException {
		
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(jsonFactory));
			}
		});
		FreebaseUrl url = FreebaseUrl.fetchIngredients().setLimit(40);
		HttpRequest request = requestFactory.buildGetRequest(url);
		jsonIngredients = request.execute().parseAs(JsonIngredientWithParentList.class);
	}
	
}
