package com.excilys.computerdb.utils;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Parser {

	static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
	
	public static List<Integer> StringToIntList(String s)
	{
		List<Integer> listInt = new LinkedList<>();
		String charBuffer ="";
		char[] charArray = s.toCharArray();
		for(char c : charArray)
		{
			if(c == ',')
			{
				listInt.add(Integer.parseInt(charBuffer));
				charBuffer="";
			}else if(InputControl.testInt(c))
			{
				charBuffer += c;
				
			} 
		}
		listInt.add(Integer.parseInt(charBuffer));
		return listInt;
	}
}
