package org.nucastrodata.data.dataman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class DataManMoveDataPanel.
 */
public class DataManMoveDataPanel extends WizardPanel implements ActionListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The middle panel. */
	JPanel mainPanel, middlePanel; 
	
	/** The top label. */
	JLabel label1, label2, topLabel; 
	
	/** The nuc data set combo box. */
	JComboBox nucDataSetComboBox;
	
	/** The no button. */
	JButton moveNucDataSetButton, yesButton, noButton;
	
	/** The move nuc data set dialog. */
	JDialog cautionDialog, moveNucDataSetDialog;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	/**
	 * Instantiates a new data man move data panel.
	 *
	 * @param ds the ds
	 */
	public DataManMoveDataPanel(DataManDataStructure ds){
		
		this.ds = ds;
		
		Cina.dataManFrame.setCurrentFeatureIndex(4);
		Cina.dataManFrame.setCurrentPanelIndex(1);
		addWizardPanel("Nuclear Data Manager", "Move Data Set to Shared Folder", "1", "1");
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		middlePanel = new JPanel(new GridBagLayout());
		
		nucDataSetComboBox = new JComboBox();
		nucDataSetComboBox.setFont(Fonts.textFont);
		
		label1 = new JLabel("Nuclear data set to move to Shared folder: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("There are no nuclear data sets in your User folder to move.");
		label2.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>With the Move Data Set to Shared Folder tool, you can move nuclear "
							 + "data sets from<p>your User storage to the Shared nuclear data set folder. "
							 + "Nuclear data sets in the<p>Shared nuclear data sets folder can be accessed by all Users of the suite.<p><br>"
							 + "Contact coordinator@nucastrodata.org if you wish to remove or replace a nuclear<p>data set that you have moved into the Shared nuclear data set folder.</html>");
		
		moveNucDataSetButton = new JButton("Move Nuclear Data Set");
		moveNucDataSetButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 5, 20, 5);
		middlePanel.add(label1, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		middlePanel.add(nucDataSetComboBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		middlePanel.add(moveNucDataSetButton, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(topLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(middlePanel, gbc);

		add(mainPanel, BorderLayout.CENTER);
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==moveNucDataSetButton){
			String string = "You are about to move the nuclear data set " + (String)nucDataSetComboBox.getSelectedItem() 
								+ " from your User folder to the Shared nuclear data set folder."
								+ " "
								+ "Nuclear data sets in the Shared nuclear data set folder can be accessed by all Users of the suite.";
			createCautionDialog(string, Cina.dataManFrame);
		}else if(ae.getSource()==yesButton){
			
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			ds.setNucDataSetName((String)nucDataSetComboBox.getSelectedItem());
			
			if(Cina.cinaCGIComm.doCGICall("SHARE NUC DATA SET", Cina.dataManFrame)){
				if(nucDataSetComboBox.getItemCount()>1){
					nucDataSetComboBox.removeItem(nucDataSetComboBox.getSelectedItem());
				}else{
					middlePanel.remove(nucDataSetComboBox);
					middlePanel.remove(label1);
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.anchor = GridBagConstraints.WEST;
					gbc.insets = new Insets(20, 5, 20, 5);
					middlePanel.add(label2, gbc);
					moveNucDataSetButton.setEnabled(false);
				}
			
				String string = ds.getSharedNucDataSetReport();
				createMoveNucDataSetDialog(string, Cina.dataManFrame);
			}
		}else if(ae.getSource()==noButton){
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		}
		
		//Cina.setFrameColors(Cina.dataManFrame);
		validate();	
	} 
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		nucDataSetComboBox.removeAllItems();
		
		if(ds.getNumberUserNucDataSetDataStructures()!=0){
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
				nucDataSetComboBox.addItem(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
			}
			nucDataSetComboBox.setSelectedIndex(0);
		
			middlePanel.remove(label2);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(20, 5, 20, 5);
			middlePanel.add(label1, gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			middlePanel.add(nucDataSetComboBox, gbc);
			moveNucDataSetButton.setEnabled(true);
		}else{
			middlePanel.remove(nucDataSetComboBox);
			middlePanel.remove(label1);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(20, 5, 20, 5);
			middlePanel.add(label2, gbc);
			moveNucDataSetButton.setEnabled(false);
		}
		
		validate();
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Creates the move nuc data set dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createMoveNucDataSetDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	moveNucDataSetDialog = new JDialog(frame, "Nuclear Data Set Moved", true);
    	moveNucDataSetDialog.setSize(350, 210);
    	moveNucDataSetDialog.getContentPane().setLayout(new GridBagLayout());
		moveNucDataSetDialog.setLocationRelativeTo(frame);
		
		JTextArea moveNucDataSetTextArea = new JTextArea("", 5, 30);
		moveNucDataSetTextArea.setLineWrap(true);
		moveNucDataSetTextArea.setWrapStyleWord(true);
		moveNucDataSetTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(moveNucDataSetTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		moveNucDataSetTextArea.setText(string);
		moveNucDataSetTextArea.setCaretPosition(0);
		
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					moveNucDataSetDialog.setVisible(false);
					moveNucDataSetDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		moveNucDataSetDialog.getContentPane().add(sp, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.insets = new Insets(5, 3, 0, 3);
		moveNucDataSetDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(moveNucDataSetDialog);
		moveNucDataSetDialog.setVisible(true);
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
    	cautionDialog = new JDialog(frame, "Caution!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setFont(Fonts.textFont);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;
		cautionTextArea.setText(cautionString);
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 2;
		gbc1.insets = new Insets(0, 0, 0, 0);
		cautionButtonPanel.add(cautionLabel, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.insets = new Insets(3, 3, 0, 3);
		cautionButtonPanel.add(yesButton, gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		cautionButtonPanel.add(noButton, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		cautionDialog.getContentPane().add(sp, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);
		
		//Cina.setFrameColors(cautionDialog);
		cautionDialog.setVisible(true);
	}
}