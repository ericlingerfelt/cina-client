package org.nucastrodata;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JFrame;

import org.nucastrodata.Dialogs;


/**
 * The Class PlotPrinter.
 */
public class PlotPrinter{

	/**
	 * Prints the.
	 *
	 * @param printable the printable
	 * @param frame the frame
	 */
	public static void print(Printable printable, JFrame frame){
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Paper paper = new Paper();
		PageFormat pageFormat = new PageFormat();
		paper.setImageableArea(0, 0, 612, 792);	
		pageFormat.setPaper(paper);
		printerJob.setPrintable(printable, pageFormat);
		
		if(printerJob.printDialog()){
			try{
				printerJob.print();
			}catch(PrinterException pe){
				String string = "An error has occurred while attempting to print plot.";
				Dialogs.createExceptionDialog(string, frame);
			}	
		}
	}
}