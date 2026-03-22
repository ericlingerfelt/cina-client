package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManModifyLib1Panel.
 */
public class RateLibManModifyLib1Panel extends WizardPanel{

	/** The source lib array. */
	String[] sourceLibArray;
	
	/** The source lib combo box. */
	JComboBox sourceLibComboBox;
	
	/** The save lib as field. */
	JTextField saveLibAsField;
	
	/** The source lib array counter. */
	int sourceLibArrayCounter;
	
	/** The add missing inv rates dialog. */
	JDialog addMissingInvRatesDialog;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man modify lib1 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManModifyLib1Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(2);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Library Manager", "Create and Modify Library", "1", "3");
		
		JLabel helpLabel = new JLabel("");
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			helpLabel.setText("<html>Welcome to the Create and Modify Rate Library tool. "
									+"<br><p>With this tool you can create a new reaction rate library or modify an existing one. "
									+"<p>In Step 1, select a base library below that "
									+"will be the foundation for your new <p>library and input a name for this new library. In Step 2, choose "
									+"rates to add to the base <p>library, overwriting any duplicates. In Step 3, view results of the library <p>modification and information about the new library.</html>");
			
		}else{
		
			helpLabel.setText("<html>Welcome to the Create and Modify Rate Library tool. "
									+"<br><p>With this tool you can create a new reaction rate library or modify an existing one. "
									+"<p>In Step 1, select a base library below that "
									+"will be the foundation for your new <p>library and input a name for this new library. In Step 2, choose "
									+"rates to add to the base <p>library, overwriting any duplicates. In Step 3, view results of the library <p>modification and information about the new library. <p><br>Guest users can not create new or modify existing libraries. <p>They can demo this tool and receive a representative report in Step 3.</html>");
			
		}
		
		JLabel sourceLibLabel = new JLabel("Choose base library: ");
		sourceLibLabel.setFont(Fonts.textFont);
		
		sourceLibComboBox = new JComboBox();
		sourceLibComboBox.setFont(Fonts.textFont);
		
		saveLibAsField = new JTextField(20);
		
		JLabel saveLibAsLabel = new JLabel("Save new library as: ");
		saveLibAsLabel.setFont(Fonts.textFont);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 4;
		mainPanel.add(helpLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(sourceLibLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(sourceLibComboBox, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(saveLibAsLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(saveLibAsField, gbc);

		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/**
	 * Check save as lib field.
	 *
	 * @return true, if successful
	 */
	public boolean checkSaveAsLibField(){
		if(saveLibAsField.getText().equals("")){
			return false;
		}
		return true;
	}
	
	/**
	 * Check user lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkUserLibName(){
		if(((String)sourceLibComboBox.getSelectedItem()).equals(saveLibAsField.getText())){
			return false;
		}
		return true;
	}
	
	/**
	 * Check public lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicLibName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equalsIgnoreCase(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equalsIgnoreCase(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check overwrite lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkOverwriteLibName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Good lib info inverse check.
	 *
	 * @return true, if successful
	 */
	public boolean goodLibInfoInverseCheck(){
		
		boolean goodLibInfoInverseCheck = false;
		
		ds.setLibraryName((String)sourceLibComboBox.getSelectedItem());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY INFO", Cina.rateLibManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodLibInfoInverseCheck = true;	
					
				}
			}
		}
		
		return goodLibInfoInverseCheck;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		sourceLibComboBox.removeAllItems();
		
		sourceLibArray = new String[ds.getNumberPublicLibraryDataStructures()
										+ ds.getNumberSharedLibraryDataStructures()
										+ ds.getNumberUserLibraryDataStructures()];
		
		sourceLibArrayCounter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			sourceLibComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
			
			sourceLibArray[sourceLibArrayCounter] = "Public";
			
			sourceLibArrayCounter++;
	
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			sourceLibComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
			
			sourceLibArray[sourceLibArrayCounter] = "Shared";
			
			sourceLibArrayCounter++;
	
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			sourceLibComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
	
			sourceLibArray[sourceLibArrayCounter] = "User";
			
			sourceLibArrayCounter++;
			
		}
		
		sourceLibComboBox.setSelectedItem(ds.getSourceLib());
		
		saveLibAsField.setText(ds.getDestLibName());
			
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(!((String)sourceLibComboBox.getSelectedItem()).equals(ds.getSourceLib())){
		
			ds.setSourceLib((String)sourceLibComboBox.getSelectedItem());
			
			ds.setRateIDVector(new Vector<Object>());
		
		}
		
		ds.setSourceLibGroup(sourceLibArray[sourceLibComboBox.getSelectedIndex()]);
		
		ds.setDestLibName(saveLibAsField.getText());
	
	}
	
	/**
	 * Creates the add missing inv rates dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createAddMissingInvRatesDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	addMissingInvRatesDialog = new JDialog(frame, "Inverse Rates Created and Added to Library", true);
    	addMissingInvRatesDialog.setSize(350, 210);
    	addMissingInvRatesDialog.getContentPane().setLayout(new GridBagLayout());
		addMissingInvRatesDialog.setLocationRelativeTo(frame);
		
		JTextArea addMissingInvRatesTextArea = new JTextArea("", 5, 30);
		addMissingInvRatesTextArea.setLineWrap(true);
		addMissingInvRatesTextArea.setWrapStyleWord(true);
		addMissingInvRatesTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(addMissingInvRatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		addMissingInvRatesTextArea.setText(string);
		addMissingInvRatesTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					addMissingInvRatesDialog.setVisible(false);
					addMissingInvRatesDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		addMissingInvRatesDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		addMissingInvRatesDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(addMissingInvRatesDialog);
		
		//Show the dialog
		addMissingInvRatesDialog.setVisible(true);
	
	}
}