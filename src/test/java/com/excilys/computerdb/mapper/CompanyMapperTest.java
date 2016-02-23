package com.excilys.computerdb.mapper;

import static org.mockito.Mockito.mock;

import com.excilys.computerdb.dao.mapper.CompanyMapperDao;
import com.excilys.computerdb.model.Company;

import junit.framework.TestCase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for CompanyMapper.
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyMapperTest extends TestCase {
  /**
   * test if empty ResultSet.
   */
  public void testListEmptyResultSet() {
    ResultSet mock = mock(ResultSet.class);
    List<Company> listComp = CompanyMapperDao.toModelList(mock);
    assertEquals(listComp, new ArrayList<>());
  }

  public void testListnullResultSet() {
    List<Company> listComp = CompanyMapperDao.toModelList(null);
    assertEquals(listComp, new ArrayList<>());
  }

  /**
   * test one empty resultatset.
   * 
   */
  public void testOneEmptyResultSet() {
    ResultSet mock = mock(ResultSet.class);
    Company comp = CompanyMapperDao.toModel(mock);
    assertEquals(comp, null);
  }

  /**
   * test null resultSet.
   */
  public void testOnenullResultSet() {
    Company comp = CompanyMapperDao.toModel(null);
    assertEquals(comp, null);
  }

}
