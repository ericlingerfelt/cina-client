package org.nucastrodata.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;

import info.clearthought.layout.*;


/**
 * The Class RegUserDialog.
 */
public class RegUserDialog extends JDialog implements ActionListener{

	/** The reg button. */
	private JButton closeButton, regButton;

	/**
	 * Instantiates a new reg user dialog.
	 *
	 * @param owner the owner
	 * @param string the string
	 * @param titleString the title string
	 */
	public RegUserDialog(Frame owner, String string, String titleString){
		
    	super(owner, titleString, Dialog.ModalityType.APPLICATION_MODAL);
    	setSize(355, 200);

    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, 10, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 10, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		setLocationRelativeTo(owner);
		
		//Create submit button and its properties
		closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		regButton = new JButton("REGISTER NOW!");
		regButton.setFont(Fonts.buttonFont);
		regButton.addActionListener(this);
		
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
		
		c.add(sp, "1, 1, 3, 1, f, f");
		c.add(regButton, "1, 3, f, c");
		c.add(closeButton, "3, 3, f, c");
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==regButton){
			Cina.openRegister();
			setVisible(false);
			dispose();
		}
	}
}

