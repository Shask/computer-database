package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdb.dao.mapper.CompanyMapperDAO;
import com.excilys.computerdb.model.Company;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 * Test class for CompanyMapper
 * 
 * @author Steven Fougeron
 *
 */
public class CompanyMapperTest extends TestCase {

	public void testListEmptyResultSet() {
		ResultSet mock = mock(ResultSet.class);
		List<Company> listComp = CompanyMapperDAO.toModelList(mock);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testListnullResultSet() {
		List<Company> listComp = CompanyMapperDAO.toModelList(null);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testOneEmptyResultSet() {
		ResultSet mock = mock(ResultSet.class);
		Company Comp = CompanyMapperDAO.toModel(mock);
		assertEquals(Comp, null);
	}

	public void testOnenullResultSet() {
		Company Comp = CompanyMapperDAO.toModel(null);
		assertEquals(Comp, null);
	}

}
