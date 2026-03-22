package org.nucastrodata;
import java.awt.*;
import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.file.filter.ASCIITextFileFilter;

import java.io.File;
import java.io.FileOutputStream;


/**
 * The Class TextSaver.
 */
public class TextSaver{

	/**
	 * Save text.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public static void saveText(String string, Component frame){
		JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());
		fileDialog.addChoosableFileFilter(new ASCIITextFileFilter());
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			String saveFilename = fileDialog.getSelectedFile().getName();
			StringBuffer sb = new StringBuffer();
        
        	try{
            	sb.append(string);
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	        String s = sb.toString();
	        byte[] buffer = s.getBytes();
	        
	        try{
	        	FileOutputStream fos;
	            if(saveFilename.endsWith(".txt")){
		        	fos = new FileOutputStream(file);
		        }else{
		        	fos = new FileOutputStream(file.getAbsolutePath() + ".txt");
		        }
		        
		        fos.write(buffer);
		        fos.close();       
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	       
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}
	}
}