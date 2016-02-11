package com.excilys.computerdb.service;

public class Page {
	private int currentPage = 1;
	private int pageSize = 25;
	
	public Page(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	public Page()
	{
		super();
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
	
	public void resetPage()
	{
		currentPage=1;
	}
	public void incPage() {
		currentPage++;		
	}
	public void decPage(){
		if(currentPage>1)
		{
			currentPage--;
		}
	}

}
