package org.nucastrodata.dialogs;

import java.awt.*;
import javax.swing.*;
import info.clearthought.layout.*;

public class FileDownloadDialog extends JDialog{

	private JProgressBar bar;
	private JTextArea textArea;
	
	public FileDownloadDialog(Frame frame){
		super(frame, "Downloading Files", Dialog.ModalityType.APPLICATION_MODAL);
		
		setLocationRelativeTo(frame);
		
		Container c = getContentPane();
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED, gap};
		c.setLayout(new TableLayout(column, row));
		
		setSize(500, 250);
		
		textArea = new JTextArea();
		JScrollPane sp = new JScrollPane(textArea);
		sp.setPreferredSize(new Dimension(150, 100));
		
		bar = new JProgressBar();
		bar.setValue(0);
		bar.setStringPainted(true);
		
		c.add(sp, "1, 1, f, f");
		c.add(bar, "1, 3, f, c");
		
		
		validate();
		
	}
	
	public void appendText(String string){
		textArea.append(string);
	}
	
	public void setProgressBarMaxValue(int value){
		bar.setMaximum(value);
	}

	public void setProgressBarCurrentValue(int value){
		bar.setValue(value);
	}
	
}
