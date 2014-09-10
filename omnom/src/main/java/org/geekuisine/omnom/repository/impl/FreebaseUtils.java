package org.geekuisine.omnom.repository.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.geekuisine.omnom.domain.Ingredient;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;


public class FreebaseUtils {
	private List<Ingredient> ingredients;
	private static final JsonFactory jsonFactory = new JacksonFactory();
	private static final HttpTransport httpTransport = new NetHttpTransport();
	private static String API_KEY;
	
	public FreebaseUtils(){
		API_KEY="AIzaSyDJtvtPhLj_YO2MCos9JiMXekVX88bP2Us";
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public static class IngredientList {
		@Key("result")
		List<JsonIngredientWithParent> result;
		
		public void setResult(List<JsonIngredientWithParent> result){
			this.result = result;
		}
		
		public List<JsonIngredientWithParent> getResult(){
			return result;
		}
	}
	
	public static class JsonIngredientWithParent extends JsonIngredient {
		@Key("output")
		Output output;
		public Output getOutput() {
			return output;
		}
		public void setOutput(Output output) {
			this.output = output;
		}	
		
	}
	
	public static class JsonIngredient {
		@Key("mid")
		String mid;
		@Key("name")
		String name;

		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	}
	
	public static class Output {
		@Key("/food/ingredient/more_general_ingredient")
		Output2 output;

		public Output2 getOutput() {
			return output;
		}

		public void setOutput(Output2 output) {
			this.output = output;
		}
		
	}
	
	public static class Output2{
		@Key("/food/ingredient/more_general_ingredient")
		List<JsonIngredient> parent;

		public List<JsonIngredient> getParent() {
			return parent;
		}

		public void setParent(List<JsonIngredient> parent) {
			this.parent = parent;
		}
		
		
		
	}
	
	public static class FreebaseUrl extends GenericUrl {
		@Key
		private final String key = API_KEY;
		
		@Key
		private int limit;
		
		public FreebaseUrl(String url){
			super(url);
		}
		
		public int getLimit(){
			return limit;
		}
		
		public FreebaseUrl setLimit(int maxResults){
			this.limit = maxResults;
			return this;
		}
		
		public static FreebaseUrl fetchIngredients(){
			return new FreebaseUrl("https://www.googleapis.com/freebase/v1/search?filter=(all type:/food/ingredient)&output=(/food/ingredient/more_general_ingredient)");
		}
		
	}

	public void fetchIngredients() throws GeneralSecurityException, IOException {
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				request.setParser(new JsonObjectParser(jsonFactory));
			}
		});
		FreebaseUrl url = FreebaseUrl.fetchIngredients().setLimit(20);
		HttpRequest request = requestFactory.buildGetRequest(url);
		parseResponse(request.execute());
		
	}

	public IngredientList parseResponse(HttpResponse response) throws IOException {
		IngredientList s = response.parseAs(IngredientList.class);
		return s;
	}
}
