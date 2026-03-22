package org.nucastrodata.wizard.gui.filefilter;

import java.io.*;



public class FilterUtils {

	public enum MovieFileType{


 wmv, 
 
 
 mov, 
 

 mpg, 
 
 

 avi};
	
	//Image File Types
	/**
	 * The Enum ImageFileType.
	 */
	public enum ImageFileType{

/** The jpeg. */
jpeg, 
 
 /** The jpg. */
 jpg, 
 
 /** The gif. */
 gif, 
 
 /** The tiff. */
 tiff, 
 
 /** The tif. */
 tif, 
 
 /** The png. */
 png, 
 
 /** The bmp. */
 bmp};
    
    //Text Files Types
	/**
     * The Enum TextFileType.
     */
    public enum TextFileType{

/** The dat. */
dat, 
 
 /** The rtf. */
 rtf, 
 
 /** The txt. */
 txt}
    
    //Open Office File Types
	/**
     * The Enum OpenOfficeFileType.
     */
    public enum OpenOfficeFileType{

/** The sxc. */
sxc, 
 
 /** The sxd. */
 sxd, 
 
 /** The sxm. */
 sxm, 
 
 /** The sxw. */
 sxw}
    
    //Microsoft File Types
	/**
     * The Enum MicrosoftOfficeFileType.
     */
    public enum MicrosoftOfficeFileType{

/** The xls. */
xls, 
 
 /** The ppt. */
 ppt, 
 
 /** The doc. */
 doc, 
 
 /** The xlsx. */
 xlsx, 
 
 /** The pptx. */
 pptx, 
 
 /** The docx. */
 docx}
    
    //Other Document Types
	/**
     * The Enum OtherDocumentFileType.
     */
    public enum OtherDocumentFileType{

/** The ps. */
ps, 
 
 /** The pdf. */
 pdf, 
 
 /** The eps. */
 eps, 
 
 /** The wpd. */
 wpd, 
 
 /** The tex. */
 tex}
    
    //Web File Formats
	/**
     * The Enum WebFileType.
     */
    public enum WebFileType{

/** The htm. */
htm, 
 
 /** The html. */
 html, 
 
 /** The xml. */
 xml}
    
    //Compressed File Types
	/**
     * The Enum CompressedFileType.
     */
    public enum CompressedFileType{

/** The tar. */
tar, 
 
 /** The z. */
 z, 
 
 /** The gz. */
 gz, 
 
 /** The zip. */
 zip, 
 
 /** The tgz. */
 tgz}
	
	//All File Types
	/**
	 * The Enum AllFileType.
	 */
	public enum AllFileType{
		
		 wmv, 
		 
		 
		 mov, 
		 

		 mpg, 

		 

		 avi,

/** The jpeg. */
jpeg, 
 
 /** The jpg. */
 jpg, 
 
 /** The gif. */
 gif, 
 
 /** The tiff. */
 tiff, 
 
 /** The tif. */
 tif, 
 
 /** The png. */
 png, 
 
 /** The bmp. */
 bmp
								, 
 
 /** The dat. */
 dat, 
 
 /** The rtf. */
 rtf, 
 
 /** The txt. */
 txt
								, 
 
 /** The sxc. */
 sxc, 
 
 /** The sxd. */
 sxd, 
 
 /** The sxm. */
 sxm, 
 
 /** The sxw. */
 sxw
								, 
 
 /** The xls. */
 xls, 
 
 /** The ppt. */
 ppt, 
 
 /** The doc. */
 doc, 
 
 /** The xlsx. */
 xlsx, 
 
 /** The pptx. */
 pptx, 
 
 /** The docx. */
 docx
								, 
 
 /** The ps. */
 ps, 
 
 /** The pdf. */
 pdf, 
 
 /** The eps. */
 eps, 
 
 /** The wpd. */
 wpd, 
 
 /** The tex. */
 tex
								, 
 
 /** The htm. */
 htm, 
 
 /** The html. */
 html, 
 
 /** The xml. */
 xml
								, 
 
 /** The tar. */
 tar, 
 
 /** The z. */
 z, 
 
 /** The gz. */
 gz, 
 
 /** The zip. */
 zip, 
 
 /** The tgz. */
 tgz}
    
    /**
     * Gets the extension.
     *
     * @param f the f
     * @return the extension
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
}
