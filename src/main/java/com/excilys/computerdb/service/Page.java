package com.excilys.computerdb.service;
/**
 * 
 * @author Steven Fougeron
 *
 */
public class Page {
	private int currentPage = 1;
	private int pageSize = 25;
	private DIRECTION ASC = DIRECTION.ASC;
	private ORDER_BY order = ORDER_BY.ID;
	
	public enum DIRECTION
	{
		DESC,
		ASC
	}
	public enum ORDER_BY
	{
		ID,
		NAME,
		INTRODUCED,
		DISCONTINUED,
		COMPANY_NAME
		
	}
	
	
	public ORDER_BY getOrder() {
		return order;
	}
	public void setOrder(ORDER_BY order) {
		this.order = order;
	}
	

	
	public DIRECTION getASC() {
		return ASC;
	}
	public void setASC(DIRECTION aSC) {
		ASC = aSC;
	}
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
