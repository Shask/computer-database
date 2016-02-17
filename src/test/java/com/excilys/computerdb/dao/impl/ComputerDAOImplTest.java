package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDAO;
import com.excilys.computerdb.model.Computer;

import junit.framework.TestCase;
/**
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerDAOImplTest extends TestCase{

	public void testInsert()
	{
		Computer c =new Computer("SuperPC");
		ComputerDAO dao =ComputerDAOImpl.getInstance();
		dao.insertComputer(c);
		Computer c2 = dao.findById(c.getId());

		assertEquals(c,c2);
		dao.deleteComputer(c.getId());
	}
	public void testUpdate()
	{
		Computer c =new Computer("SuperPC");
		ComputerDAO dao = ComputerDAOImpl.getInstance();
		dao.insertComputer(c);
		c.setName("SupaPC");
		dao.updateComputer(c);
		assertEquals(dao.findById(c.getId()).getName(),"SupaPC");
		dao.deleteComputer(c.getId());
	}
	
}
