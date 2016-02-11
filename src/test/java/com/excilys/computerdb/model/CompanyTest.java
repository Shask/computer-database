package com.excilys.computerdb.model;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {
	
	public void testEqualityCompany()
	{
		Company c1= new Company(0, "test");
		Company c2 = new Company(0,"test");
		assertEquals(c1,c2);
	}
}
