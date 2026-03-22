package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;


/**
 * The Class ErrorResult is the main data structure for reporting web service errors.
 *
 * @author Eric J. Lingerfelt
 */
public class ErrorResultDataStructure implements DataStructure{
	
	private boolean error;
	private String string;
	
	/**
	 * The Constructor.
	 */
	public ErrorResultDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.bellerophon.data.Data#initialize()
	 */
	public void initialize(){
		error = false;
		string = "";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ErrorResultDataStructure clone(){
		ErrorResultDataStructure r = new ErrorResultDataStructure();
		r.error = error;
		r.string = string;
		return r;
	}
	
	/**
	 * Checks if is error.
	 *
	 * @return true, if is error
	 */
	public boolean isError(){return error;}
	
	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(boolean error){this.error = error;}
	
	/**
	 * Gets the string.
	 *
	 * @return the string
	 */
	public String getString(){return string;}
	
	/**
	 * Sets the string.
	 *
	 * @param string the new string
	 */
	public void setString(String string){this.string = string;}
	
}
