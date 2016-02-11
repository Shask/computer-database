package com.excilys.computerdb.model;

import java.time.LocalDateTime;

import junit.framework.TestCase;

public class ComputerTest extends TestCase{

	public void testComputerEquality()
	{
		Computer c1 = new Computer(0,"Macbook");
		Computer c2 = new Computer(0,"Macbook");
		assertEquals(c2, c1);
	}
	public void testComputerDate()
	{
		Computer c1 = new Computer(0,"PC");
		Computer c2 = new Computer(0,"PC");
		c1.setDiscontinued(null);
		assertEquals(c1,c2);
		c1.setDiscontinued(LocalDateTime.now());
		assertEquals(c1,c2);
		c1.setIntroduced(LocalDateTime.now());
		assertTrue(!c1.equals(c2));
	}
}
