package org.nucastrodata.export.save;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.file.filter.ASCIITextFileFilter;
import org.nucastrodata.file.filter.HTMLFileFilter;

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
	 * @param mds the mds
	 */
	public static void saveText(String string, Component frame, MainDataStructure mds){
		JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
		fileDialog.addChoosableFileFilter(new ASCIITextFileFilter());
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.setFileFilter(new ASCIITextFileFilter());
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
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
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}
	}
	
	/**
	 * Save text html.
	 *
	 * @param string the string
	 * @param frame the frame
	 * @param mds the mds
	 */
	public static void saveTextHTML(String string, Component frame, MainDataStructure mds){
		JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
		fileDialog.addChoosableFileFilter(new HTMLFileFilter());
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.setFileFilter(new HTMLFileFilter());
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
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
	            if(saveFilename.endsWith(".html")){
		        	fos = new FileOutputStream(file);
		        }else{
		        	fos = new FileOutputStream(file.getAbsolutePath() + ".html");
		        }
		        
		        fos.write(buffer);
		        fos.close();       
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	       
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}
	}
}

