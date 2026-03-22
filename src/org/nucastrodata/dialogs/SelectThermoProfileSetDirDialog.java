package org.nucastrodata.dialogs;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.nucastrodata.datastructure.MainDataStructure;


/**
 * The Class SelectFolderDialog.
 *
 * @author Eric J. Lingerfelt
 */
public class SelectThermoProfileSetDirDialog extends JDialog implements ActionListener, TreeSelectionListener{
	
	private JButton selectButton;
	private DirectoryChooser chooser;
	private JTextField dirField;
	private Frame frame;
	private String selectedValue = "";
	private MainDataStructure mds;
	
	/**
	 * Creates the select folder dialog.
	 *
	 * @param owner the owner
	 * @return the string
	 */
	public static String createSelectFolderDialog(Frame owner, MainDataStructure mds){
		SelectThermoProfileSetDirDialog dialog = new SelectThermoProfileSetDirDialog(owner, mds);
		dialog.setVisible(true);
		return dialog.selectedValue;
	}
	
	/**
	 * Instantiates a new select folder dialog.
	 *
	 * @param frame the frame
	 */
	public SelectThermoProfileSetDirDialog(Frame frame, MainDataStructure mds){
		
		super(frame, "Select Thermo Profile Set Directory for Upload", Dialog.ModalityType.APPLICATION_MODAL);
		
		this.frame = frame;
		
		Container c = getContentPane();
		setSize(625, 470);
		setLocationRelativeTo(frame);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		c.setLayout(new TableLayout(column, row));
		
		chooser = new DirectoryChooser(new File(mds.getAbsolutePath()), this);
		chooser.addTreeSelectionListener(this);
		JScrollPane chooserPane = new JScrollPane(chooser);
		
		dirField = new JTextField(20);
		dirField.setEditable(false);
		dirField.setText(chooser.getSelectedDirectory().getAbsolutePath());
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				selectedValue = "";
				setVisible(false);
			}
		});
		
		selectButton = new JButton("Select Directory");
		selectButton.addActionListener(this);

		JLabel dirLabel = new JLabel("Upload Directory");
		
		c.add(chooserPane, "1, 1, 1, 11, f, f");
		c.add(dirLabel, "3, 1, f, c");
		c.add(dirField, "3, 3, f, c");
		c.add(selectButton, "3, 5, f, c");
		c.add(closeButton, "3, 9, f, c");	
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent tse){
		if(chooser.getSelectedDirectory()!=null){
			dirField.setText(chooser.getSelectedDirectory().getAbsolutePath());
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==selectButton){
			if(!dirField.getText().trim().equals("")){
				selectedValue = dirField.getText().trim();
				mds.setAbsolutePath(dirField.getText());
				setVisible(false);
			}else{
				String string = "Please select a directory from the tree.";
				MessageDialog.createMessageDialog(frame, string, "Attention!");
			}
		}
		
	}
}