package org.geekuisine.omnom.repository.freebase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/** URL builder for Freebase query */
public class FreebaseUrl extends GenericUrl {
	/** Google API key*/
	@Key
	private String key;
	
	/** number of items retrieved at a time*/
	@Key
	private int limit;
	
	/** Constructor: fetches the API key from property file */
	public FreebaseUrl(String url){
		super(url);
		Properties props = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("secrets.properties");
		try{
			props.load(inputStream);
			key = props.getProperty("omnom.freebase.apikey");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public int getLimit(){
		return limit;
	}
	
	public FreebaseUrl setLimit(int maxResults){
		this.limit = maxResults;
		return this;
	}
	
	/** Creates the url to fetch ingredients */
	public static FreebaseUrl fetchIngredients(){
		return new FreebaseUrl("https://www.googleapis.com/freebase/v1/search?filter=(all type:/food/ingredient)&output=(/food/ingredient/more_general_ingredient)");
	}
	
}