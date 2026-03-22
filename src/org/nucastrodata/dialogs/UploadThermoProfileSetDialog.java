package org.nucastrodata.dialogs;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.text.DecimalFormat;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

import org.nucastrodata.dialogs.worker.OpenDialogWorker;
import org.nucastrodata.io.BytesWrittenListener;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class UploadThermoProfileSetDialog extends JDialog implements BytesWrittenListener{
	
	private JProgressBar bar;
	private Frame owner;
	private String setName;
	private DecimalFormat format;
	private long contentLength;
	private int counter = 0;
	
	/**
	 * Instantiates a new download file dialog.
	 *
	 * @param owner the owner
	 * @param file the file
	 * @param customFile the custom file
	 */
	public UploadThermoProfileSetDialog(Frame owner, String setName){
		
		super(owner, "", Dialog.ModalityType.APPLICATION_MODAL);
		
		this.owner = owner;
		this.setName = setName;
		
		format = new DecimalFormat("#######0.0");
		setSize(750, 125);

		bar = new JProgressBar();
		bar.setStringPainted(true);
		bar.setBorderPainted(true);
		bar.setMaximum(100);
		bar.setMinimum(0);
		
		Container c = this.getContentPane();
		double[] col = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.FILL, 10};
		c.setLayout(new TableLayout(col, row));
		c.add(bar, "1, 1, f, c");
	}
	
	/* (non-Javadoc)
	 * @see org.bellerophon.io.BytesReadListener#setBytesRead(int)
	 */
	public void setBytesWritten(int bytesWritten){
		if(counter==200){
			bar.setValue((int) Math.ceil((((double)bytesWritten/(double)contentLength)*100)));
			setTitle("Uploading Thermo Profile Set " + setName + ": " 
					+ format.format(bytesWritten/1E6) + " MB out of " 
					+ format.format(contentLength/1E6) + " MB");
			validate();
			repaint();
			counter = 0;
		}else{
			counter++;
		}
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
		setTitle("Uploading Thermo Profile Set " + setName
				+ ": 0.0 MB out of " 
				+ format.format(contentLength/(long)1E6) + " MB");
		
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