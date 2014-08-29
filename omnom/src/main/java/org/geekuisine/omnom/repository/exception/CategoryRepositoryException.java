package org.geekuisine.omnom.repository.exception;

public class CategoryRepositoryException extends RuntimeException {
	
	public CategoryRepositoryException(String string) {
		super(string);
	}
		
	public CategoryRepositoryException(Throwable ex) {
		super(ex);
	}

	private static final long serialVersionUID = -6418125495023369154L;

}
