package com.excilys.computerdb.webservices;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
import com.excilys.computerdb.services.ComputerServices;

@Path("/computer")
public class ComputerWebService{
	
	@Autowired
	private ComputerServices computerservices;
	
	@Autowired
	ComputerMapperDto dtoMapper;
	
	@POST
	@Path("add")
	public void insertComputer(ComputerDto cdto)
	{
		//TODO VALiDATE DTO
		Computer c = dtoMapper.toModel(cdto);
		computerservices.insertComputer(c);
		cdto = ComputerMapperModel.toDto(c);
	}
	
	@GET
	@Path("/list")
	
	public List<Computer> listall()
	{
		return computerservices.findAllComputer();
	}
	
}
