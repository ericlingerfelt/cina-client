package org.nucastrodata.file.filter;

import org.nucastrodata.file.util.*;

import org.nucastrodata.file.util.PNGUtils;


//This class filters out which files are shown in the file chooser
//When saving a postscript image file from within the suite
/**
 * The Class PNGFileFilter.
 */
public class PNGFileFilter extends javax.swing.filechooser.FileFilter{
	
	//Method to see if a file will be shown in the file chooser
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(java.io.File f){

		//If the file is a directory, show it in the file chooser
		if(f.isDirectory()){return true;}

		//Get the extension of the file using the ps utils class
		String extension = PNGUtils.getExtension(f);

		//If the extension exists
		if(extension!=null){

			//If the extension is ".ps", then show it
			if(extension.equals(PNGUtils.png)){

				return true;

			//If the extension is not ".ps", then don't show it
			}else{

				return false;

			}

		//If there is no extension, then don't show it
		}else{

			return false;

		}

	}

	//Method to return proper extension
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){

		return "*.png (Portable Network Graphics)";

	}

}