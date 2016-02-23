package com.excilys.computerdb.model;

import junit.framework.TestCase;

/**
 * Model Test.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyTest extends TestCase {
  /**
   * Test if 2 company are equals.
   */
  public void testEqualityCompany() {
    Company c1 = new Company(0, "test");
    Company c2 = new Company(0, "test");
    assertEquals(c1, c2);
  }
}
