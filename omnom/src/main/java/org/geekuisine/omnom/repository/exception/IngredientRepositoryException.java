package org.geekuisine.omnom.repository.exception;

/** Exception in the category repository */
public class IngredientRepositoryException extends RuntimeException {
	
	public IngredientRepositoryException(String string) {
		super(string);
	}
		
	public IngredientRepositoryException(Throwable ex) {
		super(ex);
	}

	private static final long serialVersionUID = -6418125495023369154L;

}
