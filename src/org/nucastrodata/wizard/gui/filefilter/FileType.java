package org.nucastrodata.wizard.gui.filefilter;


/**
 * The Enum FileType.
 */
public enum FileType {
	
	/** The HTML. */
	HTML("HTML Document (*.html)")
	, 
 
 /** The TXT. */
 TXT("Text (Tab Delimited) (*.txt)")
	, 
 
 /** The RTF. */
 RTF("Rich Text Format (*.rtf)")
	, 
 
 /** The PDF. */
 PDF("Portable Document Format (*.pdf)")
	, 
 
 /** The PS. */
 PS("PostScript (*.ps)")
	, 
 
 /** The DOC. */
 DOC("Microsoft Word Document (*.doc)")
	, 
 
 /** The PPT. */
 PPT("Microsoft PowerPoint Presentation (*.ppt)")
	, 
 
 /** The XLS. */
 XLS("Microsoft Excel Workbook (*.xls)")
	, 
 
 /** The PNG. */
 PNG("Portable Network Graphics (*.png)")
	, 
 
 /** The GIF. */
 GIF("Graphics Interchange Format (*.gif)")
	, 
 
 /** The JPG. */
 JPG("Joint Photographic Experts Group (*.jpg)")
	, 
 
 /** The WMV. */
 WMV("Windows Media Video (*.wmv)")
	, 
 
 /** The MOV. */
 MOV("QuickTime Movie (*.mov)")
	, 
 
 /** The MPG. */
 MPG("Moving Pictures Expert Group (*.mpg)")
	, 
 
 /** The AVI. */
 AVI("Audio Video Interleave (*.avi)")
	, 
 
 /** The ZIP. */
 ZIP("Zip File (*.zip)")
	, 
 
 /** The TGZ. */
 TGZ("GZipped Tar File (*.tgz)")
	, 
 
 /** The H5. */
 H5("HDF5 File (*.h5)");
	
	/** The string. */
	private String string;
	
	/**
	 * Instantiates a new file type.
	 *
	 * @param string the string
	 */
	FileType(String string){
		this.string = string;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){return string;}
}
