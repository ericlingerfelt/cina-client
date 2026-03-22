//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rategen.RateGenResultsInfoFrame;
import org.nucastrodata.rate.rategen.RateGenResultsPlotFrame;
import org.nucastrodata.rate.rategen.RateGenResultsRateInfoFrame;
import org.nucastrodata.rate.rategen.RateGenResultsTableFrame;


/**
 * The Class RateGenResultsPanel.
 */
public class RateGenResultsPanel extends WizardPanel implements ActionListener, ItemListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The button panel. */
	JPanel mainPanel, RTSummaryPanel, buttonPanel; 
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[6];
	
	/** The string array. */
	String[] stringArray = {"Reaction Rate Summary"
								, "Number of Points: "
								, "Temp min (T9): "
								, "Temp max (T9): "
								, "Rate min: "
								, "Rate max: "};
	
	/** The info button. */
	JButton plotRateButton, tableButton, rateInfoButton, saveButton
				, okButton, cancelButton, yesButton, noButton, yesButton2, noButton2
				, infoButton;
	
	/** The nuc data modify dialog. */
	JDialog saveNucDataDialog, cautionDialog, nucDataExistsDialog, nucDataMergeDialog, nucDataModifyDialog;
	
	/** The nuc data set combo box. */
	JComboBox nucDataSetComboBox;
	
	/** The nuc data name field. */
	JTextField newNucDataSetField, nucDataNameField;
	
	/** The number points rt. */
	int numberPointsRT;
	
	/** The tempmin rt. */
	double tempminRT;
	
	/** The tempmax rt. */
	double tempmaxRT;
	
	/** The ratemin. */
	double ratemin;
	
	/** The ratemax. */
	double ratemax;
	
	/** The number reactants. */
	int[] numberReactants = {0, 1, 1, 1, 2, 2, 2, 2, 3};
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	/**
	 * Instantiates a new rate gen results panel.
	 *
	 * @param ds the ds
	 */
	public RateGenResultsPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateGenFrame.setCurrentPanelIndex(7);

		gbc = new GridBagConstraints();
		
		//Create buttons////////////////////////////////////////////////BUTTONS///////////////////
		plotRateButton = new JButton("Plot Rate");
		plotRateButton.setFont(Fonts.buttonFont);
		plotRateButton.addActionListener(this);
		
		tableButton = new JButton("Table of Rate vs. Temp");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);
		
		rateInfoButton = new JButton("Additional Information");
		rateInfoButton.setFont(Fonts.buttonFont);
		rateInfoButton.addActionListener(this);
		
		infoButton = new JButton("Session Information");
		infoButton.setFont(Fonts.buttonFont);
		infoButton.addActionListener(this);
		
		saveButton = new JButton("Save Rate");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			saveButton.setEnabled(false);
		}else{
			saveButton.setEnabled(true);
		}
		
		//Create labels////////////////////////////////////////////////LABELS/////////////////////
		for(int i=0; i<6; i++){
			
			labelArray[i] = new JLabel();
			
			if(i==0){
				labelArray[i].setFont(Fonts.titleFont);
			}else{
				labelArray[i].setFont(Fonts.textFont);	
			}
		
		}
		
		RTSummaryPanel = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		buttonPanel.add(plotRateButton);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		buttonPanel.add(tableButton);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		buttonPanel.add(infoButton);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		buttonPanel.add(rateInfoButton);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		buttonPanel.add(saveButton);
		
		mainPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 50);
		mainPanel.add(RTSummaryPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);
		mainPanel.add(buttonPanel, gbc);
		
		addWizardPanel("Rate Generator", "Rate Generation Results", "7", "7");
		
		add(mainPanel, BorderLayout.CENTER);
		
		setCurrentState();
		
		validate();
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		numberPointsRT = ds.getNumberPointsRT();
		
		tempminRT = ds.getTempminRT();
		tempmaxRT = ds.getTempmaxRT();
		
		ratemin = ds.getRatemin();
		ratemax = ds.getRatemax();
		
		labelArray[0].setText(stringArray[0]);
		
		labelArray[1].setText(stringArray[1] + String.valueOf(numberPointsRT));
		
		labelArray[2].setText(stringArray[2] + String.valueOf(tempminRT));
		labelArray[3].setText(stringArray[3] + String.valueOf(tempmaxRT));
		
		labelArray[4].setText(stringArray[4] + String.valueOf(ratemin) + " " + ds.getRateUnits());
		labelArray[5].setText(stringArray[5] + String.valueOf(ratemax) + " " + ds.getRateUnits());
		
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
		
		gbc.insets = new Insets(0, 0, 10, 0);
		
		for(int i=0;i<6;i++){
			
			gbc.gridx = 0;
			gbc.gridy = i;
			
			RTSummaryPanel.add(labelArray[i], gbc);
		
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==plotRateButton){
	
			if(Cina.rateGenFrame.rateGenResultsPlotFrame==null){
			
				Cina.rateGenFrame.rateGenResultsPlotFrame = new RateGenResultsPlotFrame(ds);				
			
			}else{
			
				Cina.rateGenFrame.rateGenResultsPlotFrame.setFormatPanelState();
				Cina.rateGenFrame.rateGenResultsPlotFrame.rateGenResultsPlotCanvas.setPlotState();
				Cina.rateGenFrame.rateGenResultsPlotFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==tableButton){
			
			if(Cina.rateGenFrame.rateGenResultsTableFrame==null){
			
				Cina.rateGenFrame.rateGenResultsTableFrame = new RateGenResultsTableFrame(ds);
			
			}else{
				
				Cina.rateGenFrame.rateGenResultsTableFrame.setTableText();
				Cina.rateGenFrame.rateGenResultsTableFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==rateInfoButton){
			
			if(Cina.rateGenFrame.rateGenResultsRateInfoFrame==null){
				
				Cina.rateGenFrame.rateGenResultsRateInfoFrame = new RateGenResultsRateInfoFrame(ds);
			
			}else{
				
				Cina.rateGenFrame.rateGenResultsRateInfoFrame.setLabelLayout();
				Cina.rateGenFrame.rateGenResultsRateInfoFrame.setVisible(true);
				
			
			}

		}else if(ae.getSource()==infoButton){
			
			if(Cina.rateGenFrame.rateGenResultsInfoFrame==null){
						
				Cina.rateGenFrame.rateGenResultsInfoFrame = new RateGenResultsInfoFrame(ds);
				
			}else{
				
				Cina.rateGenFrame.rateGenResultsInfoFrame.refreshData();
				Cina.rateGenFrame.rateGenResultsInfoFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==saveButton){
			
			if(getSourceNucDataSets()){
			
				createSaveNucDataDialog(Cina.rateGenFrame);
			
			}

		}else if(ae.getSource()==cancelButton){
			
			saveNucDataDialog.setVisible(false);
			saveNucDataDialog.dispose();
			
		}else if(ae.getSource()==okButton){
			
			if(nucDataNameField.getText().equals("")){
			
				String string = "Please enter a name for this tabulated rate.";
			
				Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
			
			}else if(ds.getNumberUserNucDataSetDataStructures()!=0){
			
				if(!checkNewNucDataSetField() && ((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
				
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
										
								createNucDataMergeDialog(string, Cina.rateGenFrame);
							
							}
						
						}else{
										
							String string = "You have specified the name of an existing nuclear data set. Do you want to overwrite this nuclear data set?";
					
							createCautionDialog(string, Cina.rateGenFrame);
						
						}
						
					}else{
					
						String string = "You have specified the name of a Public or a Shared nuclear data set. These can not overwritten.";
				
						Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
					
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
								createNucDataModifyDialog(string, Cina.rateGenFrame);
							
							}
							
						}else{
						
							String string = "This nuclear data already exists in the nuclear data set " + newNucDataSetField.getText() + ". Do you want to overwrite this nuclear data?";
						
							createNucDataExistsDialog(string, Cina.rateGenFrame);		
										
						}
						
					}
				
				}
			
			}else if(ds.getNumberUserNucDataSetDataStructures()==0){
			
				if(!checkNewNucDataSetField()){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
				
				}else{
	
					ds.setDestNucDataSetName(newNucDataSetField.getText());
					
					if(goodNucDataMerge()){
						
						saveNucDataDialog.setVisible(false);
						saveNucDataDialog.dispose();
						
						String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataReport();
										
						createNucDataMergeDialog(string, Cina.rateGenFrame);
					
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
										
				createNucDataMergeDialog(string, Cina.rateGenFrame);
			
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
				createNucDataModifyDialog(string, Cina.rateGenFrame);
			
			}
			
		}else if(ae.getSource()==noButton2){
		
			nucDataExistsDialog.setVisible(false);
			nucDataExistsDialog.dispose();
		
		}
	
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
	
	/**
	 * Gets the current nuc data id.
	 *
	 * @return the current nuc data id
	 */
	public String getCurrentNucDataID(){
	
		String string = "";
		
		string = "0"
					+ String.valueOf(ds.getRateDataStructure().getReactionType()) 
					+ getZAString()
					+ ds.getDestNucDataSetName()
					+ "\u0009"
					+ ds.getRateDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getRateDataStructure().getDecayType().equals("none")){
		
			string += ds.getRateDataStructure().getDecay();
		
		}
		
		string += "\u0009"
					+ "R(T)"
					+ "\u000b"
					+ nucDataNameField.getText();
		
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
	
		Point[] isoIn = new Point[numberReactants[ds.getRateDataStructure().getReactionType()]]; 
		
		Point biggestPoint = new Point(0, 0);
		
		for(int i=0; i<isoIn.length; i++){
		
			isoIn[i] = ds.getRateDataStructure().getIsoIn()[i];
		
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
		
		propertiesString += "Number of Points = " + String.valueOf(ds.getNumberPointsRT()) + "\u0009";
		propertiesString += "Nuc Data Notes = " + String.valueOf(ds.getRateDataStructure().getReactionNotes()) + "\u0009";
		propertiesString += "Reaction String = " + ds.getRateDataStructure().getReactionString() + "\u0009";
		propertiesString += "Nuc Data Name = " + nucDataNameField.getText() + "\u0009";//////////////////////////
		propertiesString += "Nuc Data Type = " + "R(T)" + "\u0009";
		
		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getRateDataStructure().getReactionType());
		
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getRateDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
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
		
		for(int i=0; i<ds.getNumberPointsRT(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getTempDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getTempDataArray()[i]);
			
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
		
		for(int i=0; i<ds.getNumberPointsRT(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getRateDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getRateDataArray()[i]);
			
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
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.rateGenFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceNucDataSets = true;
				
				}
				
			}
			
		}
		
		goodSourceNucDataSets = false;
		
		ds.setNucDataSetGroup("SHARED");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.rateGenFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceNucDataSets = true;
				
				}
				
			}
			
		}
		
		goodSourceNucDataSets = false;
		
		ds.setNucDataSetGroup("USER");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET NUC DATA SET LIST", Cina.rateGenFrame);
	
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
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA SET", Cina.rateGenFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodNucDataMerge = true;
					
				}
			}
		}
		
		flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA", Cina.rateGenFrame);
		
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
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA", Cina.rateGenFrame);
		
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
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("NUC DATA EXIST", Cina.rateGenFrame);
		
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
		
		JScrollPane sp = new JScrollPane(nucDataMergeTextArea
										, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
										, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		
		JScrollPane sp = new JScrollPane(nucDataModifyTextArea
										, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
										, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
    	saveNucDataDialog.setSize(600, 237);
    	saveNucDataDialog.getContentPane().setLayout(new GridBagLayout());
		saveNucDataDialog.setLocationRelativeTo(frame);

		nucDataSetComboBox = new JComboBox();
		nucDataSetComboBox.setFont(Fonts.textFont);
		
		newNucDataSetField = new JTextField(15);
		
		nucDataNameField = new JTextField(15);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		if(ds.getNumberUserNucDataSetDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
			
				nucDataSetComboBox.addItem(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
			
			}
		
			newNucDataSetField.setEditable(false);
		
			nucDataSetComboBox.addItem("new nuclear data set");
		
			nucDataSetComboBox.addItemListener(this);
		
			String reactionName = ds.getRateDataStructure().getReactionString();
		
			if(!ds.getRateDataStructure().getDecay().equals("")){
			
				reactionName += " ["
								+ ds.getRateDataStructure().getDecay()
								+ "]";
			
			}
		
			JLabel topLabel = new JLabel("Select existing or new nuclear data set to store " + reactionName + " tabulated rate.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			JLabel nucDataNameLabel = new JLabel("Enter the name of " + reactionName + " tabulated rate: ");
			nucDataNameLabel.setFont(Fonts.textFont);
		
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
			gbc1.anchor = GridBagConstraints.EAST;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.WEST;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);
		
			gbc1.gridx = 0;
			gbc1.gridy = 3;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.EAST;
			saveNucDataDialog.getContentPane().add(nucDataNameLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 3;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.WEST;
			saveNucDataDialog.getContentPane().add(nucDataNameField, gbc1);
		
			gbc1.anchor = GridBagConstraints.CENTER;
		
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
			gbc1.gridy = 4;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(buttonPanel, gbc1);
			
		}else{
		
			String reactionName = ds.getRateDataStructure().getReactionString();
		
			if(!ds.getRateDataStructure().getDecay().equals("")){
			
				reactionName += " ["
								+ ds.getRateDataStructure().getDecay()
								+ "]";
			
			}
		
			JLabel topLabel = new JLabel("Create a new nuclear data set to save " + reactionName + " tabulated reaction rate.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			JLabel nucDataNameLabel = new JLabel("Enter the name of " + reactionName + " tabulated rate: ");
			nucDataNameLabel.setFont(Fonts.textFont);
		
			newNucDataSetField.setEditable(true);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveNucDataDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.EAST;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.WEST;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);
		
			gbc1.gridx = 0;
			gbc1.gridy = 3;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.EAST;
			saveNucDataDialog.getContentPane().add(nucDataNameLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 3;
			gbc1.gridwidth = 1;
			gbc1.anchor = GridBagConstraints.WEST;
			saveNucDataDialog.getContentPane().add(nucDataNameField, gbc1);
		
			gbc1.anchor = GridBagConstraints.CENTER;
			
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
			gbc1.gridy = 4;
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
		
		JScrollPane sp = new JScrollPane(nucDataExistsTextArea
										, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
										, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		
		JScrollPane sp = new JScrollPane(cautionTextArea
										, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
										, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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