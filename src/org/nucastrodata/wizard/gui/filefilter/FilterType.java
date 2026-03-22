package org.nucastrodata.wizard.gui.filefilter;

import org.nucastrodata.wizard.gui.filefilter.FilterUtils;


/**
 * The Enum FilterType.
 */
public enum FilterType {
	
	/** The IMAGE. */
	IMAGE("Image File Types", FilterUtils.ImageFileType.values())
	, 
 
	/** The IMAGE. */
	MOVIE("Movie File Types", FilterUtils.MovieFileType.values())
	, 
	
 /** The TEXT. */
 TEXT("Text File Types", FilterUtils.TextFileType.values())
	, 
 
 /** The OPEN. */
 OPEN("Open Office File Types", FilterUtils.OpenOfficeFileType.values())
	, 
 
 /** The MICROSOFT. */
 MICROSOFT("Microsoft Office File Types", FilterUtils.MicrosoftOfficeFileType.values())
	, 
 
 /** The OTHER. */
 OTHER("Other Document File Types", FilterUtils.OtherDocumentFileType.values())
	, 
 
 /** The WEB. */
 WEB("Web File Types", FilterUtils.WebFileType.values())
	, 
 
 /** The COMPRESSED. */
 COMPRESSED("Compressed File Types", FilterUtils.CompressedFileType.values())
	, 
 
 /** The ALL. */
 ALL("All Acceptable File Types", FilterUtils.AllFileType.values());
	
	/** The string. */
	private String string;
	
	/** The values. */
	private Object[] values;
	
	/**
	 * Instantiates a new filter type.
	 *
	 * @param string the string
	 * @param values the values
	 */
	FilterType(String string, Object[] values){
		this.string = string;
		this.values = values;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		String str = string;
		if(!this.name().equals("ALL")){
			str += " (";
			for(int i=0; i<this.values.length; i++){
				str += "*." + this.values[i].toString();
				if(i!=this.values.length-1){
					str += ",";
				}
			}
			str += ")";
		}
		return str;
	}
	
	/**
	 * Contains value.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean containsValue(String value){
		for(int i=0; i<values.length; i++){
			if(values[i].toString().equals(value)){
				return true;
			}
		}
		return false;
	}
}
