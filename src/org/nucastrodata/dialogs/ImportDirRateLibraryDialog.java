package org.nucastrodata.dialogs;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.nucastrodata.dialogs.worker.OpenDialogWorker;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ImportDirRateLibraryDialog extends JDialog{
	
	private JProgressBar bar;
	private Frame owner;
	private String libDirName;
	private int numLibs;
	
	public ImportDirRateLibraryDialog(Frame owner, String libDirName, int numLibs, String firstFile){
		
		super(owner, "Importing Rate Library Number 1 out of " + numLibs +  " | " + firstFile + " to " + libDirName, Dialog.ModalityType.APPLICATION_MODAL);
		
		this.owner = owner;
		this.libDirName = libDirName;
		this.numLibs = numLibs;
		
		setSize(750, 125);

		bar = new JProgressBar();
		bar.setStringPainted(true);
		bar.setBorderPainted(true);
		bar.setMaximum(numLibs);
		bar.setMinimum(0);
		
		JPanel panel = new JPanel();
		
		double[] colPanel = {TableLayoutConstants.FILL};
		double[] rowPanel = {TableLayoutConstants.FILL};
		panel.setLayout(new TableLayout(colPanel, rowPanel));
		panel.add(bar, "0, 0, f, f");
		
		Container c = getContentPane();
		double[] col = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL, 10};
		c.setLayout(new TableLayout(col, row));
		c.add(panel, "1, 1, f, f");
		
	}
	
	public void update(int currentLibNum, String libName){
		bar.setValue(currentLibNum);
		setTitle("Importing Rate Library Number " + (currentLibNum + 1) + " out of " + numLibs +  " | " + libName + " to " + libDirName);
		validate();
		repaint();
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