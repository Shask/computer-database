package com.excilys.computerdb.mapper;

import static org.mockito.Mockito.mock;

import com.excilys.computerdb.dao.mapper.ComputerMapperDao;
import com.excilys.computerdb.model.Computer;

import junit.framework.TestCase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for ComputerMapper.
 * 
 * @author Steven Fougeron
 *
 */
public class ComputerMapperTest extends TestCase {

  /**
  * Test an empty list ( mapping ).
  */
  public void testListEmptyResultSet() {
    ResultSet mock = mock(ResultSet.class);
    List<Computer> listComp = ComputerMapperDao.toModelList(mock);
    assertEquals(listComp, new ArrayList<>());
  }

  public void testListnullResultSet() {
    List<Computer> listComp = ComputerMapperDao.toModelList(null);
    assertEquals(listComp, new ArrayList<>());
  }

  /**
   * test to map a empty resultset.
   */
  public void testOneEmptyResultSet() {
    ResultSet mock = mock(ResultSet.class);
    Computer comp = ComputerMapperDao.toModel(mock);
    assertEquals(comp, null);
  }

  public void testOnenullResultSet() {
    Computer comp = ComputerMapperDao.toModel(null);
    assertEquals(comp, null);
  }

}
