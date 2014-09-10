package org.geekuisine.omnom.repository;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.geekuisine.omnom.repository.impl.DBRepositoryUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class DBUtilsTest {
	DBRepositoryUtils dbUtils;
	
	@Before
	public void init(){
		System.setProperty("omnom.db.connectionString", "jdbc:sqlite:omnom-test-utils.db");
		dbUtils = new DBRepositoryUtils();
		dbUtils.dropAllTables();
	}
	
	@Test
	public void populateWithFreebase_works() throws GeneralSecurityException, IOException{
		dbUtils.populate_with_freebase();
	}
}
