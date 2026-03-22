package org.nucastrodata.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;

import info.clearthought.layout.*;


/**
 * The Class FeatureClosingDialog.
 */
public class FeatureClosingDialog extends JDialog{

	/** The do not close radio button. */
	private JRadioButton saveAndCloseRadioButton
							, closeRadioButton
							, doNotCloseRadioButton;
	
	/** The submit button. */
	private JButton submitButton;
	
	/**
	 * Instantiates a new feature closing dialog.
	 *
	 * @param owner the owner
	 * @param al the al
	 * @param featureString the feature string
	 */
	public FeatureClosingDialog(Frame owner, ActionListener al, String featureString){
	
		super(owner, featureString + " Exit", Dialog.ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(owner);
		setSize(500, 170);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		double[] colPanel = {TableLayoutConstants.FILL};
		double[] rowPanel = {TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED};
		
		JPanel panel = new JPanel(new TableLayout(colPanel, rowPanel));
    	
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain " + featureString + " session input.", true);
		saveAndCloseRadioButton.setFont(Fonts.textFont);

    	closeRadioButton = new JRadioButton("Exit and erase " + featureString + " session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the " + featureString + ".", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(saveAndCloseRadioButton);
		buttonGroup.add(closeRadioButton);
		buttonGroup.add(doNotCloseRadioButton);

		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(al);
	
		panel.add(saveAndCloseRadioButton, "0, 0, l, c");
		panel.add(closeRadioButton, "0, 2, l, c");
		panel.add(doNotCloseRadioButton, "0, 4, l, c");
		
		c.add(panel, "1, 1, c, c");
		c.add(submitButton, "1, 3, c, c");
		
		
		
	}
	
	/**
	 * Gets the submit button.
	 *
	 * @return the submit button
	 */
	public JButton getSubmitButton(){return submitButton;}
	
	/**
	 * Gets the save and close radio button.
	 *
	 * @return the save and close radio button
	 */
	public JRadioButton getSaveAndCloseRadioButton(){return saveAndCloseRadioButton;}
	
	/**
	 * Gets the close radio button.
	 *
	 * @return the close radio button
	 */
	public JRadioButton getCloseRadioButton(){return closeRadioButton;}
	
	/**
	 * Gets the do not close radio button.
	 *
	 * @return the do not close radio button
	 */
	public JRadioButton getDoNotCloseRadioButton(){return doNotCloseRadioButton;}
}
