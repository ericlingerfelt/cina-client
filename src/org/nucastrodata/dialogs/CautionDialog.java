package org.nucastrodata.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.Fonts;

import info.clearthought.layout.*;


/**
 * The Class CautionDialog.
 */
public class CautionDialog extends JDialog{

	/** The no button. */
	private JButton yesButton, noButton;

	/**
	 * Instantiates a new caution dialog.
	 *
	 * @param owner the owner
	 * @param al the al
	 * @param string the string
	 * @param titleString the title string
	 */
	public CautionDialog(Frame owner, ActionListener al, String string, String titleString){
	
		super(owner, titleString, Dialog.ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(owner);
		setSize(320, 215);

		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		JTextArea textArea = new JTextArea("", 5, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText(string);
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(al);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(al);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		c.add(sp, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");

	}
	
	/**
	 * Gets the yes button.
	 *
	 * @return the yes button
	 */
	public JButton getYesButton(){return yesButton;}
	
	/**
	 * Gets the no button.
	 *
	 * @return the no button
	 */
	public JButton getNoButton(){return noButton;}

}
