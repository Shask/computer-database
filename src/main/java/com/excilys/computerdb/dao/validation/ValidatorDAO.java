package com.excilys.computerdb.dao.validation;

import java.sql.ResultSet;
/**
 * Class with a validator method allowing you to know if the Resultset is valid
 * @author Steven Fougeron
 *
 */
public class ValidatorDAO {

	public static boolean  validator(ResultSet rs)
	{
		if (rs == null) {
			return false;
		}
		return true;
	}
}
