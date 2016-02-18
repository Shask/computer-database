package com.excilys.computerdb.mapper;

import static org.mockito.Mockito.mock;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdb.dao.mapper.ComputerMapperDAO;
import com.excilys.computerdb.model.Computer;

import junit.framework.TestCase;
/**
 * Test class for ComputerMapper
 * @author Steven Fougeron
 *
 */
public class ComputerMapperTest extends TestCase {

	public void testListEmptyResultSet() {
		ResultSet mock = mock(ResultSet.class);
		List<Computer> listComp = ComputerMapperDAO.toModelList(mock);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testListnullResultSet() {
		List<Computer> listComp = ComputerMapperDAO.toModelList(null);
		assertEquals(listComp, new ArrayList<>());
	}

	public void testOneEmptyResultSet() {
		ResultSet mock = mock(ResultSet.class);
		Computer Comp = ComputerMapperDAO.toModel(mock);
		assertEquals(Comp, null);
	}

	public void testOnenullResultSet() {
		Computer Comp = ComputerMapperDAO.toModel(null);
		assertEquals(Comp, null);
	}

}
