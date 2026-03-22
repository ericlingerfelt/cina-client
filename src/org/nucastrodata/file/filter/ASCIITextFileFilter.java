package org.nucastrodata.file.filter;
import org.nucastrodata.file.util.ASCIITextUtils;


/**
 * The Class ASCIITextFileFilter.
 */
public class ASCIITextFileFilter extends javax.swing.filechooser.FileFilter{

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(java.io.File f){
		
		//If the File is a directory then show in file chooser
		if(f.isDirectory()){return true;}

		//Retrieve file extension from file and send to utils class
		String extension = ASCIITextUtils.getExtension(f);

		//If there is an extension
		if(extension!=null){
			//If extension equals utils variable then show in file chooser
			if(extension.equals(ASCIITextUtils.txt)){
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
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){return "*.txt";}
}