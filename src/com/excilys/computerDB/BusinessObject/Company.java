package com.excilys.computerDB.BusinessObject;

public class Company {

	private int id;
	private String name;
	
	public Company(int id, String name)
	{
		this.name=name;
		this.id=id;
	}

	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
