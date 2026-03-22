package org.nucastrodata.dialogs;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;


/**
 * The Class CreateFolderDialog.
 */
class CreateFolderDialog extends JDialog{

	/** The create button. */
	private JButton createButton;
	
	/** The field. */
	private JTextField field;
	
	/**
	 * Instantiates a new creates the folder dialog.
	 *
	 * @param parent the parent
	 * @param al the al
	 */
	public CreateFolderDialog(JDialog parent, ActionListener al){
		
		super(parent, "Create New Folder", Dialog.ModalityType.APPLICATION_MODAL);
		
		Container c = getContentPane();
		setSize(280, 150);
		setLocationRelativeTo(parent);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED, gap};
		c.setLayout(new TableLayout(column, row));
		
		JLabel topLabel = new JLabel("Please enter new folder name below.");
		
		createButton = new JButton("Create New Folder");
		createButton.setFont(Fonts.buttonFont);
		createButton.addActionListener(al);
		
		field = new JTextField();
		
		c.add(topLabel, "1, 1, f, c");
		c.add(field, "1, 3, f, c");
		c.add(createButton, "1, 5, c, c");
		
		
		
	}
	
	/**
	 * Gets the creates the button.
	 *
	 * @return the creates the button
	 */
	public JButton getCreateButton(){
		return createButton;
	}
	
	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public JTextField getField(){
		return field;
	}
	
}