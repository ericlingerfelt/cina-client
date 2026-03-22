package org.nucastrodata.dialogs;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.nucastrodata.Fonts;
import org.nucastrodata.dialogs.worker.OpenDialogWorker;

public class PleaseWaitDialog extends JDialog{

	private Frame owner;
	
	public PleaseWaitDialog(Frame owner, String string){
		
		super(owner, "Please Wait...", Dialog.ModalityType.APPLICATION_MODAL);

		this.owner = owner;
		
		setSize(340, 200);
		
    		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		setLocationRelativeTo(owner);
		
		JTextArea textArea = new JTextArea(string);
		textArea.setFont(Fonts.textFont);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		
		c.add(sp, "1, 1, f, f");
		
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
