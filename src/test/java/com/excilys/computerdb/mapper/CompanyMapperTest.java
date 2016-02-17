package com.excilys.computerdb.mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
		List<Company> listComp = CompanyMapper.mapList(mock);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testListnullResultSet() {
		List<Company> listComp = CompanyMapper.mapList(null);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testOneEmptyResultSet() {
		ResultSet mock = mock(ResultSet.class);
		Company Comp = CompanyMapper.mapOne(mock);
		assertEquals(Comp, null);
	}

	public void testOnenullResultSet() {
		Company Comp = CompanyMapper.mapOne(null);
		assertEquals(Comp, null);
	}

}
