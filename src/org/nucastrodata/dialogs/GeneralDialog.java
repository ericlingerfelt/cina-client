package org.nucastrodata.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;

import info.clearthought.layout.*;


/**
 * The Class GeneralDialog.
 */
public class GeneralDialog extends JDialog{

	/** The ok button. */
	private JButton okButton;

	/**
	 * Instantiates a new general dialog.
	 *
	 * @param owner the owner
	 * @param string the string
	 * @param titleString the title string
	 */
	public GeneralDialog(Frame owner, String string, String titleString){
		
    	super(owner, titleString, Dialog.ModalityType.APPLICATION_MODAL);
    	setSize(355, 200);

    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
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
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
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
		c.add(okButton, "1, 3, c, c");
		
		
		
	}
	
	/**
	 * Instantiates a new general dialog.
	 *
	 * @param owner the owner
	 * @param string the string
	 * @param titleString the title string
	 */
	public GeneralDialog(Dialog owner, String string, String titleString){
		
    	super(owner, titleString, Dialog.ModalityType.APPLICATION_MODAL);
    	setSize(355, 200);

    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
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
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
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
		c.add(okButton, "1, 3, c, c");
		
		
		
	}
	
}
