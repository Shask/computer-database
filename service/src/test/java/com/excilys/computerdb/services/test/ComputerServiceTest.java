package com.excilys.computerdb.services.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdb.models.Company;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.services.ComputerServices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/service-context.xml" })
public class ComputerServiceTest {

	@Autowired
	ComputerServices computerService;

	@Test
	public void findallTest()
	{
		List<Computer> cpList = computerService.findAllComputer();
		assertNotNull(cpList);
		assertFalse(cpList.isEmpty());
	}
	
	@Test
	public void findByNameTest()
	{
		List<Computer> cpList = computerService.findComputerByName("Apple");
		cpList.stream().forEach((computer) -> {
			assertTrue(((Computer)computer).getName().contains("Apple"));
		});
	}
	
	@Test
	public void addComputerTest()
	{
		Computer c = new Computer("Apple3000Test");
		computerService.insertComputer(c);
		assertNotNull(computerService.findComputerById(c.getId()));
	}
	
	@Test
	public void addWrongDateComputerTest()
	{
		Computer c = new Computer("Apple3000Test",LocalDate.now(),LocalDate.now().minusDays(3),new Company(1,"Apple"));
		computerService.insertComputer(c);
		assertNull(computerService.findComputerById(c.getId()).getIntroduced());
		assertNull(computerService.findComputerById(c.getId()).getDiscontinued());
	}
	
	@Test
	public void addEmptyComputerTest()
	{
		Computer c = new Computer();
		computerService.insertComputer(c);
		assertNull(computerService.findComputerById(c.getId()));
	}
	
	@Test
	public void updateComputertoEmptyTest()
	{
		Computer c = new Computer();
		c.setId(1);
		computerService.updateComputer(c);
		assertNotNull(computerService.findComputerById(c.getId()).getName());
	}
	
	@Test
	public void updateWrongDateComputerTest()
	{
		Computer c = new Computer("Apple3000Test",LocalDate.now(),LocalDate.now().minusDays(3),new Company(1,"Apple"));
		c.setId(1);
		computerService.updateComputer(c);
		assertNull(computerService.findComputerById(c.getId()).getIntroduced());
		assertNull(computerService.findComputerById(c.getId()).getDiscontinued());
	}
	
	@Test
	public void updateNullComputerTest()
	{
		Computer c = new Computer();
		c.setId(1);
		computerService.updateComputer(c);
		assertNotNull(computerService.findComputerById(c.getId()).getName());
	}
	
/*	@Test
	public void deleteComputerTest()
	{
		Computer c = new Computer("Apple3000TestToDelete");
		computerService.insertComputer(c);
		assertNotNull(computerService.findComputerById(c.getId()));
		computerService.deleteComputer(c.getId());
		assertNull(computerService.findComputerById(c.getId()));
	}*/
	
	
}
