package org.nucastrodata.file.util;


/**
 * The Class SVGUtils.
 */
public class SVGUtils{

	//Initialize var to hold ps extension
	/** The Constant svg. */
	public final static String svg = "svg";

	/**
	 * Gets the extension.
	 *
	 * @param f the f
	 * @return the extension
	 */
	public static String getExtension(java.io.File f){
		
		//Initialize extension String var
		String ext = null;

		//Get filename from file
		String s = f.getName();

		//get index of last "." in file name
		int i = s.lastIndexOf('.');

		//If the last appearence of "." in the filename is not the first char or the last char
		if(i>0 && i<(s.length() - 1)){

			//Extract extension
			ext = s.substring(i+1).toLowerCase();

		}

		//Return extension
		return ext;

	}

}