package com.excilys.computerdb.dto;

import java.util.List;

public class PageDTO {

	List<ComputerDto> computers;
	private int currentPage = 1;
	private int pageSize = 50;
	private String order = "id";
	private String search = "";


	public List<ComputerDto> getComputers() {
		return computers;
	}

	public void setComputers(List<ComputerDto> computers) {
		this.computers = computers;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
