package com.excilys.computerdb.dao.impl;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.model.Computer;

import junit.framework.TestCase ;

/**
 * Test of DAOs.
 * @author Steven Fougeron
 *
 */
public class ComputerDaoImplTest extends TestCase {

  /**
   * test inserting in DB.
   * @throws CriticalDatabaseException if fails
   */
  public void testInsert() throws CriticalDatabaseException {
    Computer ccomputer = new Computer("SuperPC");
    ComputerDao dao = ComputerDaoImpl.getInstance();
    dao.insertComputer(ccomputer);
    Computer c2 = dao.findById(ccomputer.getId());

    assertEquals(ccomputer, c2);
    dao.deleteComputer(ccomputer.getId());
  }

  /**
   * test update in DB.
   * @throws CriticalDatabaseException if fails
   */
  public void testUpdate() throws CriticalDatabaseException {
    Computer computer = new Computer("SuperPC");
    ComputerDao dao = ComputerDaoImpl.getInstance();
    dao.insertComputer(computer);
    computer.setName("SupaPC");
    dao.updateComputer(computer);
    assertEquals(dao.findById(computer.getId()).getName(), "SupaPC");
    dao.deleteComputer(computer.getId());
  }

}
