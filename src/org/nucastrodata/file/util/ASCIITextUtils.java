package org.nucastrodata.file.util;


/**
 * The Class ASCIITextUtils.
 */
public class ASCIITextUtils{

	//Declare and initialize extension string
	/** The Constant txt. */
	public final static String txt = "txt";

	/**
	 * Gets the extension.
	 *
	 * @param f the f
	 * @return the extension
	 */
	public static String getExtension(java.io.File f){

		//Initialize variable to null
		String ext = null;

		//Get file name from file
		String s = f.getName();

		//get index of last "." in file name
		int i = s.lastIndexOf('.');

		//If "." is not the first char of file name or the last char of file name
		if(i>0 && i<(s.length() - 1)){

			//Set extension to the last chars of string after "."
			ext = s.substring(i+1).toLowerCase();

		}

		//Return extension
		return ext;

	}

}