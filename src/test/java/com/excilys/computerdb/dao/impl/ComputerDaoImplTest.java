package com.excilys.computerdb.dao.impl;

import org.junit.BeforeClass ;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext ;
import org.springframework.context.support.ClassPathXmlApplicationContext ;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.dao.JdbcConnection ;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.models.Computer;

import junit.framework.TestCase;

/**
 * Test of DAOs.
 * 
 * @author Steven Fougeron
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-context.xml" })
public class ComputerDaoImplTest extends TestCase {
 
  static ComputerDao dao;

  @BeforeClass
  public static void test() {
    @SuppressWarnings("resource")
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:/test-context.xml");
    @SuppressWarnings("unused")
    JdbcConnection jdbcConnection = applicationContext.getBean(JdbcConnection.class);
    dao = applicationContext.getBean(ComputerDaoImpl.class);
  }
  

  /**
   * test inserting in DB.
   * 
   * @throws CriticalDatabaseException
   *           if fails
   */

  @Test
  public void testInsert() throws CriticalDatabaseException {
    Computer ccomputer = new Computer("SuperPC");
    System.out.println(ccomputer);
    dao.insertComputer(ccomputer);
    Computer c2 = dao.findById(ccomputer.getId());

    assertEquals(ccomputer, c2);
    dao.deleteComputer(ccomputer.getId());
  }

  /**
   * test update in DB.
   * 
   * @throws CriticalDatabaseException
   *           if fails
   */

  @Test
  public void testUpdate() throws CriticalDatabaseException {
    Computer computer = new Computer("SuperPC");
    dao.insertComputer(computer);
    computer.setName("SupaPC");
    dao.updateComputer(computer);
    assertEquals(dao.findById(computer.getId()).getName(), "SupaPC");
    dao.deleteComputer(computer.getId());
  }

}
