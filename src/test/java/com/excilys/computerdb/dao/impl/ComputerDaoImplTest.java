package com.excilys.computerdb.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdb.dao.ComputerDao ;
import com.excilys.computerdb.dao.exception.CriticalDatabaseException;
import com.excilys.computerdb.models.Computer ;

import junit.framework.TestCase;

/**
 * Test of DAOs.
 * 
 * @author Steven Fougeron
 *
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-context.xml" })
public class ComputerDaoImplTest extends TestCase {
  @Autowired
  ComputerDao dao;

  /*
   * ClassPathXmlApplicationContext ctx;
   * 
   * @Before public void init() { ctx = new ClassPathXmlApplicationContext(new String[] {
   * "classpath:/test-context.xml" }); // Manually get Service bean dao = (ComputerDaoImpl)
   * ctx.getBean(ComputerDaoImpl.class);
   * 
   * }
   * 
   * @After public void close() { ctx.close(); }
   */
  @Configuration
  static class Config {

    // this bean will be injected into the OrderServiceTest class
    @Bean
    public ComputerDao orderService() {
      ComputerDao dao = new ComputerDaoImpl();
      // set properties, etc.
      return dao;
    }
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
