package org.nucastrodata.file.filter;

import org.nucastrodata.file.util.*;

import org.nucastrodata.file.util.HTMLUtils;


//This class creates a file filter for savinf text files using a file chooser
/**
 * The Class HTMLFileFilter.
 */
public class HTMLFileFilter extends javax.swing.filechooser.FileFilter{

	//Method to filter which file types are shown through filter
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(java.io.File f){
		
		//If the File is a directory then show in file chooser
		if(f.isDirectory()){return true;}

		//Retrieve file extension from file and send to utils class
		String extension = HTMLUtils.getExtension(f);

		//If there is an extension
		if(extension!=null){

			//If extension equals utils variable then show in file chooser
			if(extension.equals(HTMLUtils.html)){

				return true;
			
			//If extension does not equal utils variable then do not show in file chooser
			}else{

				return false;

			}

		//If extension is null then do not show in file chooser
		}else{

			return false;

		}

	}
	
	//Method to return proper extension
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){
		
		return "*.html";

	}

}