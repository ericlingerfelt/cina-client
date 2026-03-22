package org.nucastrodata.dialogs;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class CautionDialog2 extends JDialog{
	
	public int selectedValue;
	public static final int YES = 1;
	public static final int NO = 0;
	
	/**
	 * Instantiates a new caution dialog.
	 *
	 * @param owner the owner
	 * @param string the string
	 * @param title the title
	 */
	public CautionDialog2(Window owner, String string, String title){
	
		super(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
		setSize(320, 215);
		setLocationRelativeTo(owner);

		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		JTextArea textArea = new JTextArea("", 5, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText(string);
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				selectedValue = 1;
				setVisible(false);
			}
		});
		
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				selectedValue = 0;
				setVisible(false);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		c.add(sp, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param owner the owner
	 * @param string the string
	 * @param title the title
	 * @return the int
	 */
	public static int createCautionDialog2(Window owner, String string, String title){
		CautionDialog2 dialog = new CautionDialog2(owner, string, title);
		dialog.setVisible(true);
		return dialog.selectedValue;
	}

}
