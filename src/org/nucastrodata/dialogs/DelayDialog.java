package org.nucastrodata.dialogs;

import info.clearthought.layout.*;
import java.awt.*;
import javax.swing.*;

import org.nucastrodata.dialogs.worker.OpenDialogWorker;
import org.nucastrodata.wizard.gui.WordWrapLabel;

public class DelayDialog extends JDialog{

	private Frame owner;

	public DelayDialog(Frame owner, String string, String titleString){
	
		super(owner, titleString, Dialog.ModalityType.APPLICATION_MODAL);
		this.owner = owner;
		
		setSize(320, 175);
		setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
				, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));

		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		WordWrapLabel textArea = new WordWrapLabel();
		textArea.setText(string);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		sp.setBorder(null);

		c.add(sp, "1, 1, f, f");
	}
	
	public void openDelayDialog(){
		OpenDialogWorker worker = new OpenDialogWorker(this, owner);
		worker.execute();
	}
	
	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		setVisible(false);
		dispose();
	}
}