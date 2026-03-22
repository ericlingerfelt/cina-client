package org.nucastrodata;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Properties;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;

import org.nucastrodata.Cina;

import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.graphicsio.gif.GIFGraphics2D;
import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.graphicsio.png.PNGEncoder;
import org.nucastrodata.Dialogs;
import org.nucastrodata.file.filter.EMFFileFilter;
import org.nucastrodata.file.filter.GIFFileFilter;
import org.nucastrodata.file.filter.PDFFileFilter;
import org.nucastrodata.file.filter.PNGFileFilter;
import org.nucastrodata.file.filter.PSFileFilter;
import org.nucastrodata.file.filter.SVGFileFilter;


/**
 * The Class PlotSaver.
 */
public class PlotSaver{
	
	/** The file dialog. */
	static JFileChooser fileDialog;
	
	/** The save filename. */
	static String saveFilename;

	/**
	 * Save plot.
	 *
	 * @param plot the plot
	 * @param frame the frame
	 */
	public static void savePlot(JComponent plot, Component frame){
	
		fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());
			
		fileDialog.addChoosableFileFilter(new PNGFileFilter());
		fileDialog.addChoosableFileFilter(new PDFFileFilter());
		fileDialog.addChoosableFileFilter(new PSFileFilter());
		fileDialog.addChoosableFileFilter(new GIFFileFilter());
		fileDialog.addChoosableFileFilter(new SVGFileFilter());
		//fileDialog.addChoosableFileFilter(new EMFFileFilter());
		
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.setFileFilter(new PNGFileFilter());
		
		saveFilename = "";
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			saveFilename = fileDialog.getSelectedFile().getName();
			Properties p = new Properties();
			p.setProperty("", "");
			VectorGraphics g;
		
			try{
				if(fileDialog.getFileFilter() instanceof PSFileFilter){
					if(saveFilename.endsWith(".ps")){
						g = new PSGraphics2D(file, plot.getSize());		
					}else{
						g = new PSGraphics2D(new File(file.getAbsolutePath() + ".ps"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();	
				}else if(fileDialog.getFileFilter() instanceof PDFFileFilter){
					if(saveFilename.endsWith(".pdf")){
						g = new PDFGraphics2D(file, plot.getSize());
					}else{
						g = new PDFGraphics2D(new File(file.getAbsolutePath() + ".pdf"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter() instanceof PNGFileFilter){
					BufferedImage myImage = new BufferedImage((int)plot.getSize().getWidth(), (int)plot.getSize().getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = myImage.createGraphics();
					plot.print(g2);
					PNGEncoder encoder = new PNGEncoder((Image)myImage);
					encoder.setCompressionLevel(1);
					
					byte[] myArray = encoder.pngEncode();
					
					
					FileOutputStream fos;
					if(saveFilename.endsWith(".png")){
						fos = new FileOutputStream(file);
					}else{
						fos = new FileOutputStream(new File(file.getAbsolutePath() + ".png"));
					}
						
					fos.write(myArray); 
					 		
				    fos.close();
				}else if(fileDialog.getFileFilter() instanceof GIFFileFilter){
					if(saveFilename.endsWith(".gif")){
						g = new GIFGraphics2D(file, plot.getSize());
					}else{
						g = new GIFGraphics2D(new File(file.getAbsolutePath() + ".gif"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter() instanceof EMFFileFilter){
					if(saveFilename.endsWith(".emf")){
						g = new EMFGraphics2D(file, plot.getSize());
					}else{
						g = new EMFGraphics2D(new File(file.getAbsolutePath() + ".emf"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter() instanceof SVGFileFilter){
					if(saveFilename.endsWith(".svg")){
						g = new SVGGraphics2D(file, plot.getSize());
					}else{
						g = new SVGGraphics2D(new File(file.getAbsolutePath() + ".svg"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();
				}
			}catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
			
				String string = fnfe.getMessage();
				Dialogs.createExceptionDialog(string, (JFrame)frame);
				
			}catch(Exception e){
				
				e.printStackTrace();
				
				String string = "An error has occured while trying to save this plot.";
				Dialogs.createExceptionDialog(string, (JFrame)frame);
				
			}
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}	
	}
}