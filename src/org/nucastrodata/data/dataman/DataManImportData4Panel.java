//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.data.dataman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.dataman.DataManImportDataPlotFrame;
import org.nucastrodata.data.dataman.DataManImportDataTableFrame;


/**
 * The Class DataManImportData4Panel.
 */
public class DataManImportData4Panel extends WizardPanel implements ActionListener, ItemListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The button panel. */
	JPanel mainPanel, summaryPanel, buttonPanel; 
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[11];
	
	/** The string array. */
	String[] stringArray = {"Nuclear Data Summary"
								, "Reaction: "
								, "Data Type: "
								, "Data Source: "
								, "Number of Points: "
								, "Energy min: "
								, "Energy max: "
								, "Cross Section min: "
								, "Cross Section max: "
								, "S-factor min: "
								, "S-factor max: "};
	
	/** The no button2. */
	JButton plotDataButton, tableButton, saveDataButton, okButton, cancelButton, yesButton, noButton, yesButton2, noButton2;
	
	/** The nuc data modify dialog. */
	JDialog saveNucDataDialog, cautionDialog, nucDataExistsDialog, nucDataMergeDialog, nucDataModifyDialog;
	
	/** The nuc data set combo box. */
	JComboBox nucDataSetComboBox;
	
	/** The new nuc data set field. */
	JTextField newNucDataSetField;
	
	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {0, 1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {0, 1, 2, 3, 1, 2, 3, 4, 2};
	
	/** The ds. */
	private DataManDataStructure ds; 
	
	/**
	 * Instantiates a new data man import data4 panel.
	 *
	 * @param ds the ds
	 */
	public DataManImportData4Panel(DataManDataStructure ds){
		
		this.ds = ds;
		
		Cina.dataManFrame.setCurrentFeatureIndex(2);
		Cina.dataManFrame.setCurrentPanelIndex(4);

		gbc = new GridBagConstraints();
		
		//Create buttons////////////////////////////////////////////////BUTTONS///////////////////
		plotDataButton = new JButton("Plot Nuclear Data");
		plotDataButton.setFont(Fonts.buttonFont);
		plotDataButton.addActionListener(this);
		
		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);
		
		saveDataButton = new JButton("Save Nuclear Data");
		saveDataButton.setFont(Fonts.buttonFont);
		saveDataButton.addActionListener(this);
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			saveDataButton.setEnabled(false);
		
		}else{
		
			saveDataButton.setEnabled(true);
		
		}
		
		//Create labels////////////////////////////////////////////////LABELS/////////////////////
		for(int i=0; i<11; i++){
			
			labelArray[i] = new JLabel();
			
			if(i!=0){
				labelArray[i].setFont(Fonts.textFont);
			}
			
		}
		
		summaryPanel = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		buttonPanel.add(plotDataButton);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		buttonPanel.add(tableButton);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		buttonPanel.add(saveDataButton);
		
		mainPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 10, 50);
		mainPanel.add(summaryPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);
		mainPanel.add(buttonPanel, gbc);
		
		addWizardPanel("Nuclear Data Manager", "Import Nuclear Data", "4", "4");
		
		add(mainPanel, BorderLayout.CENTER);
		
		setCurrentState();
		
		validate();
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		labelArray[0].setText(stringArray[0]);
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			labelArray[1].setText(stringArray[1] + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
		
			labelArray[1].setText(stringArray[1] 
									+ ds.getImportNucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getImportNucDataDataStructure().getDecay()
									+ "]");
		
		}
		
		labelArray[2].setText(stringArray[2] + ds.getImportNucDataDataStructure().getNucDataType());
		labelArray[3].setText(stringArray[3] + ds.getInputFilename());
		
		labelArray[4].setText(stringArray[4] + String.valueOf(ds.getImportNucDataDataStructure().getNumberPoints()));
		
		labelArray[5].setText(stringArray[5] + String.valueOf(ds.getImportNucDataDataStructure().getXmin()) + " keV");
		labelArray[6].setText(stringArray[6] + String.valueOf(ds.getImportNucDataDataStructure().getXmax()) + " keV");
		
		labelArray[7].setText(stringArray[7] + String.valueOf(ds.getImportNucDataDataStructure().getYmin()) + " b");
		labelArray[8].setText(stringArray[8] + String.valueOf(ds.getImportNucDataDataStructure().getYmax()) + " b");
		
		labelArray[9].setText(stringArray[9] + String.valueOf(ds.getImportNucDataDataStructure().getYmin()) + " keV-b");
		labelArray[10].setText(stringArray[10] + String.valueOf(ds.getImportNucDataDataStructure().getYmax()) + " keV-b");
		
		setLabelLayout();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Sets the label layout.
	 */
	public void setLabelLayout(){
		
		gbc.anchor = GridBagConstraints.WEST;
		
		gbc.insets = new Insets(0, 0, 5, 0);
		
		for(int i=0; i<7; i++){
			
			gbc.gridx = 0;
			gbc.gridy = i;
			
			summaryPanel.add(labelArray[i], gbc);
		
		}
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			gbc.gridx = 0;
			gbc.gridy = 7;
			
			summaryPanel.add(labelArray[7], gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			
			summaryPanel.add(labelArray[8], gbc);
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			gbc.gridx = 0;
			gbc.gridy = 7;
			
			summaryPanel.add(labelArray[9], gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			
			summaryPanel.add(labelArray[10], gbc);
		
		}
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==nucDataSetComboBox){
		
			if(((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
			
				newNucDataSetField.setEditable(true);
			
			}else{
			
				newNucDataSetField.setEditable(false);
			
			}
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==plotDataButton){
	
			if(Cina.dataManFrame.dataManImportDataPlotFrame==null){
			
				Cina.dataManFrame.dataManImportDataPlotFrame = new DataManImportDataPlotFrame(ds);				
			
			}else{
			
				Cina.dataManFrame.dataManImportDataPlotFrame.setFormatPanelState();
				Cina.dataManFrame.dataManImportDataPlotFrame.dataManImportDataPlotCanvas.setPlotState();
				Cina.dataManFrame.dataManImportDataPlotFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==tableButton){
			
			if(Cina.dataManFrame.dataManImportDataTableFrame==null){
			
				Cina.dataManFrame.dataManImportDataTableFrame = new DataManImportDataTableFrame(ds);
			
			}else{
				
				Cina.dataManFrame.dataManImportDataTableFrame.setTableText();
				Cina.dataManFrame.dataManImportDataTableFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==saveDataButton){
			
			if(getSourceNucDataSets()){
			
				createSaveNucDataDialog(Cina.dataManFrame);
			
			}

		}else if(ae.getSource()==cancelButton){
			
			saveNucDataDialog.setVisible(false);
			saveNucDataDialog.dispose();
			
		}else if(ae.getSource()==okButton){
			
			if(ds.getNumberUserNucDataSetDataStructures()!=0){
			
				if(!checkNewNucDataSetField() && ((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					Dialogs.createExceptionDialog(string, Cina.dataManFrame);
				
				}else if(((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
					
					if(checkPublicNucDataSetName()){
					
						if(checkOverwriteNucDataSetName()){

							ds.setDestNucDataSetName(newNucDataSetField.getText());
							
							if(goodNucDataMerge()){
								
								saveNucDataDialog.setVisible(false);
								saveNucDataDialog.dispose();
								
								String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataReport();
										
								createNucDataMergeDialog(string, Cina.dataManFrame);
							
							}
						
						}else{
										
							String string = "You have specified the name of an existing nuclear data set. Do you want to overwrite this nuclear data set?";
					
							createCautionDialog(string, Cina.dataManFrame);
						
						}
						
					}else{
					
						String string = "You have specified the name of a Public or a Shared nuclear data set. These can not overwritten.";
				
						Dialogs.createExceptionDialog(string, Cina.dataManFrame);
					
					}
							
				}else if(!((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
				
					ds.setDestNucDataSetName((String)nucDataSetComboBox.getSelectedItem());
					
					ds.setNucData(getCurrentNucDataID());
					
					if(goodNucDataExists()){
					
						if(!ds.getCurrentNucDataExists()){
			
							if(goodNucDataModify()){
		
								saveNucDataDialog.setVisible(false);
								saveNucDataDialog.dispose();
		
								String string = ds.getModifyNucDataReport();
								createNucDataModifyDialog(string, Cina.dataManFrame);
							
							}
							
						}else{
						
							String string = "This nuclear data already exists in the nuclear data set " + newNucDataSetField.getText() + ". Do you want to overwrite this nuclear data?";
						
							createNucDataExistsDialog(string, Cina.dataManFrame);		
										
						}
						
					}
				
				}
			
			}else if(ds.getNumberUserNucDataSetDataStructures()==0){
			
				if(!checkNewNucDataSetField()){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					Dialogs.createExceptionDialog(string, Cina.dataManFrame);
				
				}else{
	
					ds.setDestNucDataSetName(newNucDataSetField.getText());
					
					if(goodNucDataMerge()){
						
						saveNucDataDialog.setVisible(false);
						saveNucDataDialog.dispose();
						
						String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataReport();
										
						createNucDataMergeDialog(string, Cina.dataManFrame);
					
					}
				
				}
			
			}
		
		}else if(ae.getSource()==yesButton){
		
			ds.setDestNucDataSetName(newNucDataSetField.getText());
						
			if(goodNucDataMerge()){
			
				saveNucDataDialog.setVisible(false);
				saveNucDataDialog.dispose();
				
				String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataSetReport();
										
				createNucDataMergeDialog(string, Cina.dataManFrame);
			
			}
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==yesButton2){

			nucDataExistsDialog.setVisible(false);
			nucDataExistsDialog.dispose();
		
			if(goodNucDataModify()){
							
				saveNucDataDialog.setVisible(false);
				saveNucDataDialog.dispose();

				String string = ds.getModifyNucDataReport();
				createNucDataModifyDialog(string, Cina.dataManFrame);
			
			}
			
		}else if(ae.getSource()==noButton2){
		
			nucDataExistsDialog.setVisible(false);
			nucDataExistsDialog.dispose();
		
		}
	
	}
	
	/**
	 * Gets the current nuc data id.
	 *
	 * @return the current nuc data id
	 */
	public String getCurrentNucDataID(){
	
		String string = "";
		
		string = "0"
					+ String.valueOf(ds.getImportNucDataDataStructure().getReactionType()) 
					+ getZAString()
					+ ds.getDestNucDataSetName()
					+ "\u0009"
					+ ds.getImportNucDataDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getImportNucDataDataStructure().getDecayType().equals("none")){
		
			string += ds.getImportNucDataDataStructure().getDecay();
		
		}
		
		string += "\u0009"
					+ ds.getImportNucDataDataStructure().getNucDataType()
					+ "\u000b"
					+ ds.getImportNucDataDataStructure().getNucDataName();
		
		return string;
	
	}
	
	/**
	 * Gets the zA string.
	 *
	 * @return the zA string
	 */
	public String getZAString(){
	
		String string = "";
		String ZString = "";
		String AString = "";
	
		Point[] isoIn = new Point[numberReactants[ds.getImportNucDataDataStructure().getReactionType()]]; 
		
		Point biggestPoint = new Point(0, 0);
		
		for(int i=0; i<isoIn.length; i++){
		
			isoIn[i] = ds.getImportNucDataDataStructure().getIsoIn()[i];
		
		}
		
		for(int i=0; i<isoIn.length; i++){
		
			if(biggestPoint.getX()<isoIn[i].getX()){
			
				biggestPoint = isoIn[i];
			
			}else if(biggestPoint.getX()==isoIn[i].getX()){
			
				if(biggestPoint.getY()<isoIn[i].getY()){
				
					biggestPoint = isoIn[i];
				
				}
			
			}
			
		}
	
		ZString = String.valueOf((int)(biggestPoint.getX()));
		AString = String.valueOf((int)(biggestPoint.getY()));
		
		if(ZString.length()==1){
		
			ZString = "00" + ZString;
		
		}else if(ZString.length()==2){
		
			ZString = "0" + ZString;
		
		}
		
		if(AString.length()==1){
		
			AString = "00" + AString;
		
		}else if(AString.length()==2){
		
			AString = "0" + AString;
		
		}
		
		string = ZString + AString;
		
		return string;
		
	}
	
	/**
	 * Gets the nuc data properties.
	 *
	 * @return the nuc data properties
	 */
	public String getNucDataProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Points = " + String.valueOf(ds.getImportNucDataDataStructure().getNumberPoints()) + "\u0009";
		propertiesString += "Nuc Data Notes = " + ds.getImportNucDataDataStructure().getNucDataNotes().replaceAll("\n", " ") + "\u0009";
		propertiesString += "Reaction String = " + ds.getImportNucDataDataStructure().getReactionString() + "\u0009";
		propertiesString += "Nuc Data Name = " + ds.getImportNucDataDataStructure().getNucDataName() + "\u0009";
		propertiesString += "Nuc Data Type = " + ds.getImportNucDataDataStructure().getNucDataType() + "\u0009";
		
		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getImportNucDataDataStructure().getReactionType());
		
		if(!ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getImportNucDataDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Reference Citation = " + ds.getImportNucDataDataStructure().getRefCitation().replaceAll("\n", " ") + "\u0009";
		propertiesString += "X points = " + getXDataPoints() + "\u0009";
		propertiesString += "Y points = " + getYDataPoints() + "\u0009";

		return propertiesString;
	
	}
	
	/**
	 * Gets the x data points.
	 *
	 * @return the x data points
	 */
	public String getXDataPoints(){
	
		String string = "";
		
		for(int i=0; i<ds.getImportNucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getImportNucDataDataStructure().getXDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getImportNucDataDataStructure().getXDataArray()[i]);
			
			}
		
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the y data points.
	 *
	 * @return the y data points
	 */
	public String getYDataPoints(){
	
		String string = "";
		
		for(int i=0; i<ds.getImportNucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getImportNucDataDataStructure().getYDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getImportNucDataDataStructure().getYDataArray()[i]);
			
			}
		
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the source nuc data sets.
	 *
	 * @return the source nuc data sets
	 */
	public boolean getSourceNucDataSets(){
	
		boolean goodSourceNucDataSets = false;
		
		ds.setNucDataSetGroup("PUBLIC");
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.dataManFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceNucDataSets = true;
				
				}
				
			}
			
		}
		
		goodSourceNucDataSets = false;
		
		ds.setNucDataSetGroup("SHARED");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.dataManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceNucDataSets = true;
				
				}
				
			}
			
		}
		
		goodSourceNucDataSets = false;
		
		ds.setNucDataSetGroup("USER");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.dataManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceNucDataSets = true;
				
				}
				
			}
			
		}
								
		return goodSourceNucDataSets;
	
	}
	
	/**
	 * Good nuc data merge.
	 *
	 * @return true, if successful
	 */
	public boolean goodNucDataMerge(){
		
		boolean goodNucDataMerge = false;
		
		ds.setSourceNucDataSet("");
		ds.setDestNucDataSetGroup("USER");
		ds.setNucData("");
		ds.setProperties(getNucDataProperties());
		ds.setDeleteSourceNucDataSet("NO");
		ds.setDeleteNucData("NO");
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA SET", Cina.dataManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodNucDataMerge = true;
					
				}
			}
		}
		
		flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA", Cina.dataManFrame);
		
		if(goodNucDataMerge){
		
			goodNucDataMerge = false;
		
			if(!flagArray[0]){
			
				if(!flagArray[2]){
				
					if(!flagArray[1]){
						
						goodNucDataMerge = true;
						
					}
				}
			}
		}

		return goodNucDataMerge;
	
	}
	
	/**
	 * Good nuc data modify.
	 *
	 * @return true, if successful
	 */
	public boolean goodNucDataModify(){
		
		boolean goodNucDataModify = false;

		ds.setNucData("");
		ds.setProperties(getNucDataProperties());
		ds.setDeleteNucData("NO");
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA", Cina.dataManFrame);
		
		if(!flagArray[0]){
			
			if(!flagArray[2]){
				
				if(!flagArray[1]){
						
					goodNucDataModify = true;
						
				}
			}
		}

		return goodNucDataModify;
	
	}
	
	/**
	 * Check new nuc data set field.
	 *
	 * @return true, if successful
	 */
	public boolean checkNewNucDataSetField(){
	
		boolean goodName = false;
	
		if(!newNucDataSetField.getText().equals("")){
		
			goodName = true;
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check public nuc data set name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicNucDataSetName(){
	
		boolean goodName = true;

		for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equalsIgnoreCase(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equalsIgnoreCase(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check overwrite nuc data set name.
	 *
	 * @return true, if successful
	 */
	public boolean checkOverwriteNucDataSetName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
		
		return goodName;
	
	}
	
	/**
	 * Good nuc data exists.
	 *
	 * @return true, if successful
	 */
	public boolean goodNucDataExists(){
		
		boolean goodNucDataExists = false;
		
		ds.setNucData(getCurrentNucDataID());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("NUC DATA EXIST", Cina.dataManFrame);
		
		if(!flagArray[0]){
			
			if(!flagArray[2]){
				
				if(!flagArray[1]){
						
					goodNucDataExists = true;
						
				}
			}
		}

		return goodNucDataExists;
	
	}
	
	/**
	 * Creates the nuc data merge dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataMergeDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	nucDataMergeDialog = new JDialog(frame, "Nuclear data saved", true);
    	nucDataMergeDialog.setSize(350, 210);
    	nucDataMergeDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataMergeDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataMergeTextArea = new JTextArea("", 5, 30);
		nucDataMergeTextArea.setLineWrap(true);
		nucDataMergeTextArea.setWrapStyleWord(true);
		nucDataMergeTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataMergeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		nucDataMergeTextArea.setText(string);
		nucDataMergeTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucDataMergeDialog.setVisible(false);
					nucDataMergeDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataMergeDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		nucDataMergeDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(nucDataMergeDialog);
		
		//Show the dialog
		nucDataMergeDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the nuc data modify dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataModifyDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	nucDataModifyDialog = new JDialog(frame, "Nuclear data saved", true);
    	nucDataModifyDialog.setSize(350, 210);
    	nucDataModifyDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataModifyDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataModifyTextArea = new JTextArea("", 5, 30);
		nucDataModifyTextArea.setLineWrap(true);
		nucDataModifyTextArea.setWrapStyleWord(true);
		nucDataModifyTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataModifyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		nucDataModifyTextArea.setText(string);
		nucDataModifyTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucDataModifyDialog.setVisible(false);
					nucDataModifyDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataModifyDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		nucDataModifyDialog.getContentPane().add(okButton, gbc1);

		//Cina.setFrameColors(nucDataModifyDialog);
		
		//Show the dialog
		nucDataModifyDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the save nuc data dialog.
	 *
	 * @param frame the frame
	 */
	public void createSaveNucDataDialog(JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	saveNucDataDialog = new JDialog(frame, "Save nuclear data to nuclear data set...", true);
    	saveNucDataDialog.setSize(588, 170);
    	saveNucDataDialog.getContentPane().setLayout(new GridBagLayout());
		saveNucDataDialog.setLocationRelativeTo(frame);

		nucDataSetComboBox = new JComboBox();
		nucDataSetComboBox.setFont(Fonts.textFont);
		
		newNucDataSetField = new JTextField(15);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		if(ds.getNumberUserNucDataSetDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
			
				nucDataSetComboBox.addItem(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
			
			}
		
			newNucDataSetField.setEditable(false);
		
			nucDataSetComboBox.addItem("new nuclear data set");
		
			nucDataSetComboBox.addItemListener(this);
		
			JLabel topLabel = new JLabel("Select a User nuclear data set or create a new nuclear data set to save current nuclear data.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveNucDataDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(nucDataSetComboBox, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);
		
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 3;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(buttonPanel, gbc1);
			
		}else{
		
			JLabel topLabel = new JLabel("There are no User nuclear data sets. Create a new nuclear data set to save current nuclear data.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			newNucDataSetField.setEditable(true);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveNucDataDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);	
			
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(buttonPanel, gbc1);
				
		}
		
		//Cina.setFrameColors(saveNucDataDialog);
		
		//Show the dialog
		saveNucDataDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the nuc data exists dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataExistsDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	nucDataExistsDialog = new JDialog(frame, "Caution!", true);
    	nucDataExistsDialog.setSize(320, 215);
    	nucDataExistsDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataExistsDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataExistsTextArea = new JTextArea("", 5, 30);
		nucDataExistsTextArea.setLineWrap(true);
		nucDataExistsTextArea.setWrapStyleWord(true);
		nucDataExistsTextArea.setFont(Fonts.textFont);
		nucDataExistsTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataExistsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String nucDataExistsString = string;

		nucDataExistsTextArea.setText(nucDataExistsString);
		
		nucDataExistsTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton2 = new JButton("Yes");
		yesButton2.setFont(Fonts.buttonFont);
		yesButton2.addActionListener(this);
		
		noButton2 = new JButton("No");
		noButton2.setFont(Fonts.buttonFont);
		noButton2.addActionListener(this);
		
		JPanel nucDataExistsButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataExistsButtonPanel.add(yesButton2, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		nucDataExistsButtonPanel.add(noButton2, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataExistsDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		nucDataExistsDialog.getContentPane().add(nucDataExistsButtonPanel, gbc1);
		
		//Cina.setFrameColors(nucDataExistsDialog);
		
		nucDataExistsDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
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
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
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