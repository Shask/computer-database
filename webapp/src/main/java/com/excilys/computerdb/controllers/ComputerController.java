package com.excilys.computerdb.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.dao.utils.Page;
import com.excilys.computerdb.dao.utils.Page.OrderBy;
import com.excilys.computerdb.dto.CompanyDto;
import com.excilys.computerdb.dto.ComputerDto;
import com.excilys.computerdb.dto.mapper.ComputerMapperDto;
import com.excilys.computerdb.dto.validation.ComputerDtoValidator;
import com.excilys.computerdb.dto.validation.ValidatorDto;
import com.excilys.computerdb.mapper.exception.MappingException;
import com.excilys.computerdb.models.Computer;
import com.excilys.computerdb.models.mappers.CompanyMapperModel;
import com.excilys.computerdb.models.mappers.ComputerMapperModel;
import com.excilys.computerdb.services.CompanyServices;
import com.excilys.computerdb.services.ComputerServices;
import com.excilys.computerdb.utils.Parser;
import com.excilys.computerdb.utils.exception.ValidationException;

@Controller
public class ComputerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);
	@Autowired
	private ComputerServices computerServices;
	@Autowired
	private CompanyServices companyServices;
	@Autowired
	private ComputerMapperDto computerMapperDto;
	@Autowired
	private ComputerDtoValidator dtoValidator;

	@RequestMapping(value = "/computer/list", method = RequestMethod.GET)
	public String list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer nbElements,
			@RequestParam(required = false) String search, @RequestParam(required = false) String order,
			ModelMap model) {
		Page pageObject = computerServices.getPage();

		List<ComputerDto> listComp;

		// Get number of the page and check is it is not null and <1
		if (page == null || page < 1) {
			page = 1;
		}
		// Get number of elements per pages and set it for the Service
		if (nbElements != null) {
			switch (nbElements) {
			case 10:
				pageObject.setPageSize(10);
				break;
			case 50:
				pageObject.setPageSize(50);
				break;
			case 100:
				pageObject.setPageSize(100);
				break;
			default:
			}

		}
		pageObject.setCurrentPage(page);

		orderBy(order);

		// Search by name
		long nbTotalComputer;
		if (search == null || "".equals(search)) {
			listComp = ComputerMapperModel.toDtoList(computerServices.findAllComputer());
			nbTotalComputer = computerServices.getCountComputer();
		} else {
			listComp = ComputerMapperModel.toDtoList(computerServices.findComputerByName(search));
			nbTotalComputer = listComp.size();
		}

		model.addAttribute("nbComputer", nbTotalComputer);
		model.addAttribute("currentpage", page);
		model.addAttribute("computers", listComp);
		return "dashboard";
	}
	private void orderBy(String order) {
	    // Order by
	    if ( order != null && !"".equals(order) ) {
	      switch ( order ) {
	        case "name" :
	          computerServices.getPage().setOrder(OrderBy.NAME);
	          break;
	        case "introduced" :
	          computerServices.getPage().setOrder(OrderBy.INTRODUCED);
	          break;
	        case "discontinued" :
	          computerServices.getPage().setOrder(OrderBy.DISCONTINUED);
	          break;
	        case "company_name" :
	          computerServices.getPage().setOrder(OrderBy.COMPANY_NAME);
	          break;
	        default :
	      }
	    }
	  }

	@RequestMapping(value = "/computer/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(required = false) String selection, ModelMap model) {
		if (selection != null || !"".equals(selection)) {
			List<Integer> listToDelete = Parser.stringToIntList(selection);
			LOGGER.debug("deleting " + listToDelete.toString());
			computerServices.deleteComputer(listToDelete);
		}
		return "dashboard";
	}

	@RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable int id, ModelMap model) {
		if (id > 0) {
			try {
				LOGGER.info("Retreiving computer with id : " + id);
				fillFields(id, model);

			} catch (MappingException e) {
				LOGGER.error("Error during mapping, redirection to dashboard...");
				return "redirect:/dashboard";
			}
		} else {
			// If there is something wrong with the id in parameters
			LOGGER.error("there is something wrong with the id in parameters, redirection to dashboard...");
			return "redirect:/dashboard";
		}
		model.addAttribute("id", id);
		return "editcomputer";
	}

	@RequestMapping(value = "/computer/edit/{id}", method = RequestMethod.POST)
	public String edit(@ModelAttribute("computerdto") ComputerDto computerdto, ModelMap model, BindingResult result) {
		dtoValidator.validate(computerdto, result);

		System.err.println(result);
		if (result.hasErrors()) {
			System.err.println("error");
			model.addAttribute("id", computerdto.getId());
			List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());

			model.addAttribute("companies", companies);
			return "editcomputer";
		}
		Computer computer;
		try {
			ValidatorDto.validate(computerdto);
			computer = computerMapperDto.toModel(computerdto);
			computerServices.updateComputer(computer);
		} catch (ValidationException | NullPointerException e) {
			LOGGER.debug("Error during update : Computer was not added");
			e.printStackTrace();
			model.addAttribute("id", computerdto.getId());
			return "editcomputer";
		}
		return "redirect:/dashboard";
	}

	/**
	 * Edit Computer utils : Fill the fields of the form
	 * 
	 * @param id
	 * @param model
	 */
	public void fillFields(Integer id, ModelMap model) {
		ComputerDto dto = ComputerMapperModel.toDto(computerServices.findComputerById(id));
		List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
		model.addAttribute("computerdto", dto);
		model.addAttribute("companies", companies);
	}

	@RequestMapping(value = "/computer/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		List<CompanyDto> listCompanies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
		model.addAttribute("companies", listCompanies);
		model.addAttribute("computerdto", new ComputerDto());
		return "addcomputer";
	}

	@RequestMapping(value = "/computer/add" ,method = RequestMethod.POST)
	public String add(@ModelAttribute("computerdto") ComputerDto computerdto, ModelMap model, BindingResult result) {

		dtoValidator.validate(computerdto, result);

		if (result.hasErrors()) {
			List<CompanyDto> companies = CompanyMapperModel.toDtoList(companyServices.findAllCompany());
			model.addAttribute("companies", companies);
			return "addcomputer";
		}

		Computer computer;
		try {
			ValidatorDto.validate(computerdto);
			computer = computerMapperDto.toModel(computerdto);
			computerServices.insertComputer(computer);
		} catch (ValidationException e) {
			LOGGER.debug("Error during validation or insertion : Computer was not added");
			e.printStackTrace();
			return "addcomputer";
		}

		return "redirect:/dashboard";
	}

}
