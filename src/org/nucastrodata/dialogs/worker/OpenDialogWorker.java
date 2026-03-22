package org.nucastrodata.dialogs.worker;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.SwingWorker;

public class OpenDialogWorker extends SwingWorker<Void, Void>{
	
	private JDialog dialog;
	private Frame owner;
	
	public OpenDialogWorker(JDialog dialog, Frame owner){
		this.dialog = dialog;
		this.owner = owner;
	}
	
	protected Void doInBackground(){
		dialog.setLocationRelativeTo(owner);
		dialog.setVisible(true);
		dialog.validate();
		dialog.repaint();
		return null;
	}

}