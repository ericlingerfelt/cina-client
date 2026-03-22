package org.nucastrodata.wizard.gui;

import java.io.File;


/**
 * The Class CustomFileFilter.
 */
public class CustomFileFilter extends javax.swing.filechooser.FileFilter{
	
	/** The description. */
	private String extension, description;
	
	/**
	 * Instantiates a new custom file filter.
	 *
	 * @param extension the extension
	 * @param description the description
	 */
	public CustomFileFilter(String extension, String description){
		this.extension = extension;
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File file){
		if(file.isDirectory()){return true;}
		String fileExtension = getFileExtension(file);
		if(fileExtension!=null){
			return fileExtension.equals(extension);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @param file the file
	 * @return the file extension
	 */
	public String getFileExtension(File file){
		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');
		if(i>0 && i<(s.length() - 1)){
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
	
}
