package org.nucastrodata.export.print;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.format.FormattedHTMLEditorPane;

import java.awt.print.*;


/**
 * The Class PrintableEditorPane.
 */
public class PrintableEditorPane extends FormattedHTMLEditorPane implements Printable{
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print (Graphics g, PageFormat pf, int pageIndex) throws PrinterException{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor (Color.black);
		RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		Dimension d = this.getSize();
		double panelWidth = d.width;
		double panelHeight = d.height;
		double pageWidth = pf.getImageableWidth();
		double pageHeight = pf.getImageableHeight();
		double scale = pageWidth / panelWidth;
		int totalNumPages = (int)Math.ceil(scale*panelHeight/pageHeight);

		// Check for empty pages
		if(pageIndex>=totalNumPages){
			return Printable.NO_SUCH_PAGE;
		}

		g2.translate(pf.getImageableX(), pf.getImageableY());
		g2.translate(0f, -pageIndex * pageHeight);
		g2.scale(scale, scale);
		this.paint(g2);
		
		return Printable.PAGE_EXISTS;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.text.JTextComponent#print()
	 */
	public boolean print(){
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if(job.printDialog()){
			try{
				job.print();
				return true;
			}catch(Exception ex){
				System.out.println(ex);
				return false;
			}
		}
		
		return true;
	}
	
}

