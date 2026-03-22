package org.nucastrodata.dialogs;

import info.clearthought.layout.*;
import java.awt.*;
import java.io.*;
import java.text.*;
import javax.swing.*;

import org.nucastrodata.dialogs.worker.OpenDialogWorker;

public class DownloadFileDialog extends JDialog {
	
	private JProgressBar bar;
	private Frame owner;
	private File file;
	private DecimalFormat format;
	private int filesize;
	private int counter = 0;

	public DownloadFileDialog(Frame owner, File file, int filesize){
		
		super(owner, "", Dialog.ModalityType.APPLICATION_MODAL);
		this.owner = owner;
		this.file = file;
		this.filesize = filesize;
		format = new DecimalFormat("########.0");
		setSize(600, 90);
		setTitle("Downloading " + file.getName() 
					+ ": 0.0 kB out of " 
					+ format.format(filesize/1024.0) + "kB");
		
		bar = new JProgressBar();
		bar.setStringPainted(true);
		bar.setBorderPainted(true);
		bar.setMaximum(filesize);
		bar.setMinimum(0);
		
		Container c = this.getContentPane();
		double[] col = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL, 10};
		c.setLayout(new TableLayout(col, row));
		c.add(bar, "1, 1, f, c");
	}

	public void setBytesRead(int bytesRead){
		if(counter==25){
			bar.setValue(bytesRead);
			setTitle("Downloading " + file.getName() + ": " 
						+ format.format(bytesRead/1024.0) + " kB out of " 
						+ format.format(filesize/1024.0) + "kB");
			validate();
			repaint();
			counter = 0;
		}else{
			counter++;
		}
	}

	public void open(){
		OpenDialogWorker worker = new OpenDialogWorker(this, owner);
		worker.execute();
	}

	public void close(){
		setVisible(false);
		dispose();
	}
	
}