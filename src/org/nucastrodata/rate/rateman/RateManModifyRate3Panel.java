package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.util.*;
import java.io.*;
import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateman.RateManModifyRatePlotFrame;


/**
 * The Class RateManModifyRate3Panel.
 */
public class RateManModifyRate3Panel extends WizardPanel implements ActionListener, ItemListener{
	
	/** The radio button array. */
	private JRadioButton[][] radioButtonArray = new JRadioButton[7][2];
	
	/** The param panel array. */
	private JPanel[] paramPanelArray = new JPanel[7];
	
	/** The param panel string array. */
	private String[] paramPanelStringArray = {"1-7"
										, "8-14"
										, "15-21"
										, "22-28"
										, "29-35"
										, "36-42"
										, "43-49"};									
	
	/** The param field array. */
	private JTextField[][] paramFieldArray = new JTextField[7][7];
	
	/** The lib combo box. */
	private JComboBox numParamComboBox, libComboBox;
	
	/** The scale field. */
	private JTextField newLibField, scaleField;
	
	/** The scale button. */
	protected JButton plotButton, scaleButton;
	
	/** The no button2. */
	private JButton saveButton, cancelButton, okButton, yesButton
						, noButton, yesButton2, noButton2;
    
    /** The param tabbed pane. */
    private JTabbedPane paramTabbedPane;
	
	/** The rate exists dialog. */
	private JDialog saveRateDialog, cautionDialog, rateMergeDialog
						, rateModifyDialog, rateExistsDialog;

	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {1, 2, 3, 1, 2, 3, 4, 2};
	
	/** The use comp array. */
	boolean[] useCompArray = new boolean[7];

	/** The current scale factor. */
	double currentScaleFactor = 1.0;

	/** The ds. */
	private RateManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate man modify rate3 panel.
	 *
	 * @param ds the ds
	 */
	public RateManModifyRate3Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(3);
		Cina.rateManFrame.setCurrentPanelIndex(3);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JPanel paramPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Modify Existing Rate", "3", "3");
		
		JLabel numParamLabel = new JLabel("Number of Parameters: ");
		numParamLabel.setFont(Fonts.textFont);
		
		plotButton = new JButton("<html>Open Plotting<p>Interface</html>");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		saveButton = new JButton("<html>Save<p>Rate</html>");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			saveButton.setEnabled(false);
		}else{
			saveButton.setEnabled(true);
		}
		
		numParamComboBox = new JComboBox();
		
		for(int i=0; i<7; i++){
			numParamComboBox.addItem(String.valueOf((i*7)+7));
		}
		
		numParamComboBox.setSelectedIndex(0);
		numParamComboBox.setFont(Fonts.textFont);
		
		scaleField = new JTextField(10);
		scaleField.setText("1.0");
		scaleButton = new JButton("Apply");
		scaleButton.addActionListener(this);
		scaleButton.setFont(Fonts.buttonFont);
		
		JLabel scaleLabel = new JLabel("Scale Factor: ");
		scaleLabel.setFont(Fonts.textFont);
		
		paramTabbedPane = new JTabbedPane(JTabbedPane.TOP
											, JTabbedPane.SCROLL_TAB_LAYOUT);

		ButtonGroup[] buttonGroupArray = new ButtonGroup[7];

		for(int i=0; i<7; i++){
	
			radioButtonArray[i][0] = new JRadioButton("Nonresonant", true);
			radioButtonArray[i][1] = new JRadioButton("Resonant", false);
			
			radioButtonArray[i][0].setFont(Fonts.textFont);
			radioButtonArray[i][1].setFont(Fonts.textFont);
			
			buttonGroupArray[i] = new ButtonGroup();
			
			buttonGroupArray[i].add(radioButtonArray[i][0]);
			buttonGroupArray[i].add(radioButtonArray[i][1]);
		
		}

		gbc.insets = new Insets(7, 7, 7, 7);

		for(int i=0; i<7; i++){
		
			paramPanelArray[i] = new JPanel(new GridBagLayout());
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			
			paramPanelArray[i].add(radioButtonArray[i][0], gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.WEST;
			
			paramPanelArray[i].add(radioButtonArray[i][1], gbc);
			
			for(int j=0; j<7; j++){
			
				paramFieldArray[i][j] = new JTextField(10);
				paramFieldArray[i][j].setText("0.0");
				
				gbc.gridx = 1;
				gbc.gridy = j;
				gbc.anchor = GridBagConstraints.EAST;
				
				if(j>3){
					
					gbc.gridx = 3;
					gbc.gridy = j-4;
					
				}
				
				JLabel label = new JLabel("a" + ((i*7)+(j+1)) + ": ");
				label.setFont(Fonts.textFont);
				
				paramPanelArray[i].add(label, gbc);
				
				gbc.gridx = 2;
				gbc.gridy = j;
				gbc.anchor = GridBagConstraints.WEST;
				
				if(j>3){
					gbc.gridx = 4;
					gbc.gridy = j-4;
				}
				
				paramPanelArray[i].add(paramFieldArray[i][j], gbc);
			
			}
			
		}

		paramTabbedPane.addTab(paramPanelStringArray[0], paramPanelArray[0]);
		numParamComboBox.addItemListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		paramPanel.add(numParamLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		paramPanel.add(numParamComboBox, gbc);
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		paramPanel.add(scaleLabel, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		paramPanel.add(scaleField, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		paramPanel.add(scaleButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		buttonPanel.add(plotButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(saveButton, gbc);

		mainPanel.add(paramPanel, BorderLayout.NORTH);
		mainPanel.add(paramTabbedPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}

	/**
	 * Good field value.
	 *
	 * @return true, if successful
	 */
	private boolean goodFieldValue(){
		if(scaleField.getText().trim().equals("")){
			return false;
		}
		try{
			Double.valueOf(scaleField.getText()).doubleValue();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * Check zero parameters.
	 *
	 * @return true, if successful
	 */
	private boolean checkZeroParameters(){
	
		boolean zeroParameters = true;
		
		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
			for(int j=0; j<7; j++){
				if(!(Double.valueOf(paramFieldArray[i][j].getText()).doubleValue()==0.0)){
					zeroParameters = false;
				}
			}
		}
		
		return zeroParameters; 
	
	}
	
	/**
	 * Sets the rate data.
	 *
	 * @return true, if successful
	 */
	private boolean setRateData(){

		boolean parametersSkipped = false;

		int counter = 0;

		for(int i=0; i<useCompArray.length; i++){
		
			useCompArray[i] = false;
		
		}

		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){

			for(int j=0; j<7; j++){
			
				if(Double.valueOf(paramFieldArray[i][j].getText()).doubleValue()!=0){
				
					useCompArray[i] = true;	
				
				}
			
			}
		
		}
	
		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
		
			if(useCompArray[i]){
			
				counter = counter + 7;
			
			}else{
			
				parametersSkipped = true;
			
			}
		
		}
		
		ds.getModifyRateDataStructure().setNumberParameters(counter);
		
		int[] useFieldArray = new int[(int)(counter/7)];
		
		counter = 0;
		
		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
		
			if(useCompArray[i]){
			
				useFieldArray[counter] = i;
				
				counter++;
			
			}
		
		}
		
		double[] outputArray = new double[ds.getModifyRateDataStructure().getNumberParameters()];
		
		double[][] outputCompArray = new double[(int)(outputArray.length/7)][7];
		
		counter = 0;
		
		for(int i=0; i<(int)(outputArray.length/7); i++){
		
			for(int j=0; j<7; j++){
			
				outputArray[counter] = Double.valueOf(paramFieldArray[useFieldArray[i]][j].getText()).doubleValue();
				
				outputCompArray[i][j] = outputArray[counter];
			
				counter++;
			
			}
		
		}
		
		ds.getModifyRateDataStructure().setParameters(outputArray);
		ds.getModifyRateDataStructure().setParameterCompArray(outputCompArray);
		ds.getModifyRateDataStructure().setResonantInfo(getResonantInfo(useCompArray));
		
		return parametersSkipped;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==numParamComboBox){

			paramTabbedPane.removeAll();
			
			for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
			
				paramTabbedPane.addTab(paramPanelStringArray[i], paramPanelArray[i]);
				
				for(int j=0; j<7; j++){
				
					if(paramFieldArray[i][j].getText().equals("")){
					
						paramFieldArray[i][j].setText("0.0");
					
					}
				
				}
			
			}
		
			setRateData();
		
			
		
			validate();
			
		}else if(ie.getSource()==libComboBox){
		
			if(((String)libComboBox.getSelectedItem()).equals("new library")){
			
				newLibField.setEditable(true);
			
			}else{
			
				newLibField.setEditable(false);	
			
			}
		
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==scaleButton){
			
			if(goodFieldValue()){
			
				for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
					
					double param = Double.valueOf(paramFieldArray[i][0].getText()).doubleValue() - Math.log(currentScaleFactor);
					
					param = param + Math.log(Double.valueOf(scaleField.getText()).doubleValue());
	
					paramFieldArray[i][0].setText(NumberFormats.getFormattedParameter2(param));
					
				}
				
				currentScaleFactor = Double.valueOf(scaleField.getText()).doubleValue();
			
			}else{
				
				String string = "Please enter a number between 10^-9 and 10^9 for the scale factor.";
				
				Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
			}
		
		}else if(ae.getSource()==plotButton){

			if(checkDataFields()){
				
				if(!checkZeroParameters()){
				
					if(setRateData()){
						
						String string = "One or more parameter sets are equal to zero. These parameter sets will not be plotted.";
						
						Dialogs.createExceptionDialog(string, Cina.rateManFrame);
						
					}
					
					setDataArrays();
				
					ds.getModifyRateDataStructure().setTempmin(getTempmin());
					ds.getModifyRateDataStructure().setTempmax(getTempmax());
		
					ds.getModifyRateDataStructure().setRatemin(getRatemin());
					ds.getModifyRateDataStructure().setRatemax(getRatemax());
		
					if(Cina.rateManFrame.rateManModifyRatePlotFrame==null){
						
						Cina.rateManFrame.rateManModifyRatePlotFrame = new RateManModifyRatePlotFrame(ds);
						
					}else{
		
						Cina.rateManFrame.rateManModifyRatePlotFrame.setFormatPanelState();
						Cina.rateManFrame.rateManModifyRatePlotFrame.rateManModifyRatePlotCanvas.setPlotState();
						Cina.rateManFrame.rateManModifyRatePlotFrame.setVisible(true);
					}
				
					plotButton.setText("<html>Refresh Plotting<p>Interface</html>");
				
				}else{
				
					String string = "All parameters are zero.";
				
					Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
				}
			
			}else{
			
				String string = "One or more parameter fields are empty.";
			
				Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			
			}
		
		}else if(ae.getSource()==saveButton){
			
			if(checkDataFields()){
			
				if(!checkZeroParameters()){
			
					if(getSourceLibraries()){
					
						createSaveRateDialog(Cina.rateManFrame);
					
					}
					
				}else{
				
					String string = "All parameters are zero.";
				
					Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
				}
			
			}else{
			
				String string = "One or more parameter fields are empty.";
			
				Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
			}
			
		}else if(ae.getSource()==cancelButton){
			
			saveRateDialog.setVisible(false);
			saveRateDialog.dispose();
			
		}else if(ae.getSource()==okButton){
			
			if(ds.getNumberUserLibraryDataStructures()!=0){
			
				if(!checkNewLibField() && ((String)libComboBox.getSelectedItem()).equals("new library")){
				
					String string = "Please enter a name for the new library.";
					
					Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
				}else if(((String)libComboBox.getSelectedItem()).equals("new library")){
					
					if(checkPublicLibName()){
					
						if(checkOverwriteLibName()){

							ds.setDestLibName(newLibField.getText());
							
							if(goodRateMerge()){
								
								saveRateDialog.setVisible(false);
								saveRateDialog.dispose();
								
								String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport();
										
								createRateMergeDialog(string, Cina.rateManFrame);
							
							}
						
						}else{
										
							String string = "You have specified the name of an existing library. Do you want to overwrite this library?";
					
							createCautionDialog(string, Cina.rateManFrame);
						
						}
						
					}else{
					
						String string = "You have specified the name of a Public or a Shared library. These can not overwritten.";
				
						Dialogs.createExceptionDialog(string, Cina.rateManFrame);
					
					}
							
				}else if(!((String)libComboBox.getSelectedItem()).equals("new library")){
				
					ds.setDestLibName((String)libComboBox.getSelectedItem());
					
					ds.setRates(getCurrentRateID());
					
					if(goodRateExists()){
					
						if(!ds.getCurrentRateExists()){
			
							if(goodRateModify()){
		
								saveRateDialog.setVisible(false);
								saveRateDialog.dispose();
		
								String string = ds.getModifyRatesReport();
								createRateModifyDialog(string, Cina.rateManFrame);
							
							}
							
						}else{
						
							String string = "This rate already exists in the library " + newLibField.getText() + ". Do you want to overwrite this rate?";
						
							createRateExistsDialog(string, Cina.rateManFrame);		
										
						}
						
					}
				
				}
			
			}else if(ds.getNumberUserLibraryDataStructures()==0){
			
				if(!checkNewLibField()){
				
					String string = "Please enter a name for the new library.";
					
					Dialogs.createExceptionDialog(string, Cina.rateManFrame);
				
				}else{
	
					ds.setDestLibName(newLibField.getText());
					
					if(goodRateMerge()){
						
						saveRateDialog.setVisible(false);
						saveRateDialog.dispose();
						
						String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport();
										
						createRateMergeDialog(string, Cina.rateManFrame);
					
					}
				
				}
			
			}
		
		}else if(ae.getSource()==yesButton){
		
			ds.setDestLibName(newLibField.getText());
						
			if(goodRateMerge()){
			
				saveRateDialog.setVisible(false);
				saveRateDialog.dispose();
				
				String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport();
										
				createRateMergeDialog(string, Cina.rateManFrame);
			
			}
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==yesButton2){

			rateExistsDialog.setVisible(false);
			rateExistsDialog.dispose();
		
			if(goodRateModify()){
							
				saveRateDialog.setVisible(false);
				saveRateDialog.dispose();

				String string = ds.getModifyRatesReport();
				createRateModifyDialog(string, Cina.rateManFrame);
			
			}
			
		}else if(ae.getSource()==noButton2){
		
			rateExistsDialog.setVisible(false);
			rateExistsDialog.dispose();
		
		}
	
	}
	
	/**
	 * Check new lib field.
	 *
	 * @return true, if successful
	 */
	private boolean checkNewLibField(){
	
		if(!newLibField.getText().equals("")){
			return true;
		}
		return false;
	
	}
	
	/**
	 * Check public lib name.
	 *
	 * @return true, if successful
	 */
	private boolean checkPublicLibName(){
	
		boolean goodName = true;

		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			if(newLibField.getText().equalsIgnoreCase(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			if(newLibField.getText().equalsIgnoreCase(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
			
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
	private boolean checkOverwriteLibName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			if(newLibField.getText().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
		
		return goodName;
	
	}
	
	/**
	 * Gets the rate properties.
	 *
	 * @return the rate properties
	 */
	private String getRateProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Parameters = " + String.valueOf(ds.getModifyRateDataStructure().getNumberParameters()) + "\u0009";
		propertiesString += "Number of Products = " + String.valueOf(numberProducts[ds.getModifyRateDataStructure().getReactionType()-1]) + "\u0009";
		propertiesString += "Number of Reactants = " + String.valueOf(numberReactants[ds.getModifyRateDataStructure().getReactionType()-1]) + "\u0009";
		propertiesString += "Parameters = " + getParameters() + "\u0009";
		
		if(!ds.getModifyRateDataStructure().getReactionNotes().equals("")){
		
			propertiesString += "Reaction Notes = " 
									+ ds.getModifyRateDataStructure().getReactionNotes() 
									+ " "
									+ ds.getAppendedNotes()
									+ "\u0009";
								
		}else{
		
			propertiesString += "Reaction Notes = " 
									+ ds.getAppendedNotes()
									+ "\u0009";
		
		}
								
		propertiesString += "Reaction String = " + ds.getModifyRateDataStructure().getReactionString() + "\u0009";

		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getModifyRateDataStructure().getReactionType());

		if(!ds.getModifyRateDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getModifyRateDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Resonant Components = " + getResonantInfoString() + "\u0009";
		propertiesString += "Biblio Code = " + ds.getModifyRateDataStructure().getBiblioString() + "\u0009";
		propertiesString += "Reference Citation = " + ds.getModifyRateDataStructure().getRefCitation() + "\u0009";
		propertiesString += "Q-value = " + String.valueOf(ds.getModifyRateDataStructure().getQValue()) + "\u0009";

		if(!compareParameters()){

			propertiesString += "Chisquared = " + "" + "\u0009";
			propertiesString += "Max Percent Difference = " + "" + "\u0009";
			propertiesString += "Valid Temp Range = " + "";

		}else{		

			propertiesString += "Chisquared = " + String.valueOf(ds.getModifyRateDataStructure().getChisquared()) + "\u0009";
			propertiesString += "Max Percent Difference = " + String.valueOf(ds.getModifyRateDataStructure().getMaxPercentDiff()) + "\u0009";
			propertiesString += "Valid Temp Range = " + ds.getModifyRateDataStructure().getValidTempRange();
			
		}
		
		return propertiesString;
	
	}
	
	/**
	 * Compare parameters.
	 *
	 * @return true, if successful
	 */
	private boolean compareParameters(){
	
		boolean tempBoolean = true;
	
		if(ds.getModifyRateDataStructure().getParameters().length==ds.getModifyRateDataStructureCompare().getParameters().length){
	
			for(int i=0; i<ds.getModifyRateDataStructure().getParameters().length; i++){
			
				if(ds.getModifyRateDataStructure().getParameters()[i]!=ds.getModifyRateDataStructureCompare().getParameters()[i]){
				
					tempBoolean = false;
				
				}
			
			}
			
		}else{
		
			tempBoolean = false;
		
		}
		
		return tempBoolean;
	
	} 
	
	/**
	 * Gets the resonant info.
	 *
	 * @param booleanArray the boolean array
	 * @return the resonant info
	 */
	private String[] getResonantInfo(boolean[] booleanArray){
	
		String[] stringArray = new String[(ds.getModifyRateDataStructure().getNumberParameters()/7)];
		
		int counter = 0;
		
		for(int i=0; i<7; i++){
		
			if(booleanArray[i]){
		
				if(radioButtonArray[i][0].isSelected()){
					
					stringArray[counter] = "nr";
					
				}else if(radioButtonArray[i][1].isSelected()){
				
					stringArray[counter] = "r";
				
				}
				
				counter++;
				
			}
	
		}
		
		return stringArray;
	
	}
	
	/**
	 * Gets the resonant info string.
	 *
	 * @return the resonant info string
	 */
	private String getResonantInfoString(){
	
		String string = "";
		
		for(int i=0; i<ds.getModifyRateDataStructure().getResonantInfo().length; i++){
		
			if(i==0){
				string = ds.getModifyRateDataStructure().getResonantInfo()[i];
			}else{
				string = string + "," + ds.getModifyRateDataStructure().getResonantInfo()[i];
			}
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	private String getParameters(){
	
		String string = "";
		
		for(int i=0; i<ds.getModifyRateDataStructure().getNumberParameters(); i++){
		
			if(i!=0){
				string = string + "," + String.valueOf(ds.getModifyRateDataStructure().getParameters()[i]);
			}else{
				string = String.valueOf(ds.getModifyRateDataStructure().getParameters()[i]);
			}
		
		}
		
		return string;
	
	}
	
	
	/**
	 * Good rate merge.
	 *
	 * @return true, if successful
	 */
	private boolean goodRateMerge(){
		
		boolean goodRateMerge = false;
		
		ds.setSourceLib("");
		ds.setDestLibGroup("USER");
		
		if(setRateData()){
				
			String string = "One or more parameter sets are equal to zero. These parameter sets will not be saved.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			
		}
		
		ds.setRates("");
		ds.setProperties(getRateProperties());
		ds.setDeleteSourceLib("NO");
		ds.setMake_inverse("YES");
		
		ds.setCHK_TEMP_BEHAVIOR("NO");
		ds.setCHK_OVERFLOW("NO");
		ds.setCHK_INVERSE("NO");
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATE LIBRARY", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodRateMerge = true;
					
				}
			}
		}
		
		flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATES", Cina.rateManFrame);
		
		if(goodRateMerge){
		
			goodRateMerge = false;
		
			if(!flagArray[0]){
			
				if(!flagArray[2]){
				
					if(!flagArray[1]){
						
						goodRateMerge = true;
						
					}
				}
			}
		}

		return goodRateMerge;
	
	}
	
	/**
	 * Good rate modify.
	 *
	 * @return true, if successful
	 */
	private boolean goodRateModify(){
		
		if(setRateData()){
				
			String string = "One or more parameter sets are equal to zero. These parameter sets will not be saved.";
			
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			
		}
		
		ds.setRates("");
		ds.setProperties(getRateProperties());
		ds.setMake_inverse("YES");
		
		ds.setCHK_TEMP_BEHAVIOR("NO");
		ds.setCHK_OVERFLOW("NO");
		ds.setCHK_INVERSE("NO");
		
		return Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateManFrame);
	
	}
	
	/**
	 * Gets the current rate id.
	 *
	 * @return the current rate id
	 */
	private String getCurrentRateID(){
	
		String string = "";
		
		string = getTypeZAString()
					+ ds.getDestLibName()
					+ "\u0009"
					+ ds.getModifyRateDataStructure().getReactionString()
					+ "\u000b";
					
		if(!ds.getModifyRateDataStructure().getDecay().equals("")){
		
			string += ds.getModifyRateDataStructure().getDecay();
		
		}

		return string;
	
	}
	
	/**
	 * Gets the type za string.
	 *
	 * @return the type za string
	 */
	private String getTypeZAString(){
		return ds.getModifyRateDataStructure().getReactionID().substring(0, 8);
	}
	
	/**
	 * Good rate exists.
	 *
	 * @return true, if successful
	 */
	private boolean goodRateExists(){
		ds.setRates(getCurrentRateID());
		return Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateManFrame);
	}
	
	/**
	 * Gets the source libraries.
	 *
	 * @return the source libraries
	 */
	private boolean getSourceLibraries(){
	
		boolean goodSourceLibraries = false;
		
		ds.setLibGroup("PUBLIC");
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY LIST", Cina.rateManFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceLibraries = true;
				
				}
				
			}
			
		}
		
		goodSourceLibraries = false;
		
		ds.setLibGroup("SHARED");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY LIST", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceLibraries = true;
				
				}
				
			}
			
		}
		
		goodSourceLibraries = false;
		
		ds.setLibGroup("USER");
	
		flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY LIST", Cina.rateManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodSourceLibraries = true;
				
				}
				
			}
			
		}
								
		return goodSourceLibraries;
	
	}
	
	/**
	 * Gets the tempmin.
	 *
	 * @return the tempmin
	 */
	private int getTempmin(){return -2;}
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	private int getTempmax(){return 1;}
	
	/**
	 * Gets the ratemin.
	 *
	 * @return the ratemin
	 */
	private int getRatemin(){
		
		double newRatemin = 1e30;
		
		int rateMin = 0;
		
		for(int k=0; k<ds.getModifyRateDataStructure().getRateDataArrayTotal().length; k++){

			newRatemin = Math.min(newRatemin, ds.getModifyRateDataStructure().getRateDataArrayTotal()[k]);

		}
    		

		rateMin = (int)Math.floor(0.434294482*Math.log(newRatemin));

		return rateMin;
	
	}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	private int getRatemax(){
		
		double newRatemax = 0.0;
		
		int rateMax = 0;
		
		for(int k=0; k<ds.getModifyRateDataStructure().getRateDataArrayTotal().length; k++){
		
			newRatemax = Math.max(newRatemax, ds.getModifyRateDataStructure().getRateDataArrayTotal()[k]);

		}
				
		rateMax = (int)Math.ceil(0.434294482*Math.log(newRatemax));

		return rateMax;
	
	}
	
	/**
	 * Sets the data arrays.
	 */
	private void setDataArrays(){

		ds.getModifyRateDataStructure().setTempDataArrayTotal(ds.getTempGrid());

		double[][] outputArray = new double[ds.getModifyRateDataStructure().getResonantInfo().length][ds.getTempGrid().length];

		for(int k=0; k<ds.getModifyRateDataStructure().getResonantInfo().length; k++){
		
			outputArray[k] = ds.getTempGrid();
		
		}
		
		ds.getModifyRateDataStructure().setTempDataArrayComp(outputArray);
			
		double[] tempArray = ds.getModifyRateDataStructure().getTempDataArrayTotal();

		int numberParameters = ds.getModifyRateDataStructure().getNumberParameters();
		
		double[] parameters = ds.getModifyRateDataStructure().getParameters();

		double[] rateArray = new double[tempArray.length];
		
		for(int k=0; k<rateArray.length; k++){
		
			rateArray[k] = Cina.cinaMainDataStructure.calcRate(tempArray[k], parameters, numberParameters);

			if(rateArray[k]<1e-100 || rateArray[k]==0){
			
				rateArray[k] = 1e-100;
			
			}
			
			if(rateArray[k]>1e100 || rateArray[k]==0){
			
				rateArray[k] = 1e100;
			
			}

		}
		
		ds.getModifyRateDataStructure().setRateDataArrayTotal(rateArray);

		outputArray = new double[ds.getModifyRateDataStructure().getResonantInfo().length][Cina.rateViewerDataStructure.getTempGrid().length];

		double[][] parameterCompArray = ds.getModifyRateDataStructure().getParameterCompArray();
 				
		if(ds.getModifyRateDataStructure().getResonantInfo().length>1){

			for(int k=0; k<ds.getModifyRateDataStructure().getResonantInfo().length; k++){

				for(int l=0; l<ds.getTempGrid().length; l++){

					outputArray[k][l] = Cina.cinaMainDataStructure.calcRate(ds.getModifyRateDataStructure().getTempDataArrayComp()[k][l], parameterCompArray[k], 7);
				
					if(outputArray[k][l]<1e-100 || outputArray[k][l]==0){
			
						outputArray[k][l] = 1e-100;
					
					}
					
					if(outputArray[k][l]>1e100 || outputArray[k][l]==0){
			
						outputArray[k][l] = 1e100;
					
					}
				
				}
			
			}
			
			ds.getModifyRateDataStructure().setRateDataArrayComp(outputArray);
			
		}
    		
	}
	
	/**
	 * Upload file.
	 *
	 * @param dir the dir
	 * @param filename the filename
	 */
	private void uploadFile(String dir, String filename){
	
		File file = new File(dir, filename);
		int i = (int)file.length();
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(stringBuffer);
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}

		ds.setInputFile(new String(stringBuffer));
	
	}
	
	/**
	 * Fill param fields.
	 */
	private void fillParamFields(){
	
		int length = (ds.getModifyRateDataStructure().getParameters().length/7);
	
		paramTabbedPane.removeAll();
	
		numParamComboBox.removeItemListener(this);
		numParamComboBox.setSelectedIndex(length-1);
		numParamComboBox.addItemListener(this);
	
		for(int i=0; i<length; i++){
		
			paramTabbedPane.addTab(paramPanelStringArray[i], paramPanelArray[i]);
		
			for(int j=0; j<7; j++){

				paramFieldArray[i][j].setText(String.valueOf(ds.getModifyRateDataStructure().getParameterCompArray()[i][j]));
			
			}
		}
		
		
		
		validate();
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	private boolean checkDataFields(){
	
		boolean pass = true;
		
		double[][] dummyArray = new double[numParamComboBox.getSelectedIndex()+1][7];
		
		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
		
			for(int j=0; j<7; j++){
			
				if(paramFieldArray[i][j].getText().equals("")){
				
					pass = false;
				
				}
			
			}
		
		}
		
		try{
		
			for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
			
				for(int j=0; j<7; j++){
				
					dummyArray[i][j] = Double.valueOf(paramFieldArray[i][j].getText()).doubleValue();
				
				}
			
			}
		
		}catch(NumberFormatException nfe){
		
			pass = false;
			String string = "Please enter numeric values for all parameters.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
		
		}
		
		return pass;
	
	}
	
	/**
	 * Assign parameters.
	 */
	private void assignParameters(){
	
		String inputString = "";
		
		inputString = ds.getInputFile();
		
		StringTokenizer st = new StringTokenizer(inputString);

		if(st.countTokens()==7
			|| st.countTokens()==14
			|| st.countTokens()==21
			|| st.countTokens()==28
			|| st.countTokens()==35
			|| st.countTokens()==42
			|| st.countTokens()==49){
		
			double[] outputArray = new double[st.countTokens()];
			
			double[][] outputCompArray = new double[(outputArray.length/7)][7];
			
			for(int i=0; i<outputArray.length; i++){
			
				outputArray[i] = Double.valueOf(st.nextToken()).doubleValue();
			
			}
			
			int counter = 0;
			
			for(int i=0; i<(outputArray.length/7); i++){
			
				for(int j=0; j<7; j++){
					
					outputCompArray[i][j] = outputArray[counter];
					
					counter++;
					
				}
			
			}
			
			ds.getModifyRateDataStructure().setParameters(outputArray);
			ds.getModifyRateDataStructure().setParameterCompArray(outputCompArray);
			ds.getModifyRateDataStructure().setNumberParameters(outputArray.length);
			
			String[] outputResonantArray = new String[(outputArray.length/7)];
			
			for(int i=0; i<outputResonantArray.length; i++){
			
				if(i!=0){
				
					outputResonantArray[i] = "r";
				
				}else{
				
					outputResonantArray[i] = "nr";
				
				}
			
			}
			
			ds.getModifyRateDataStructure().setResonantInfo(outputResonantArray);
			
			fillParamFields();
		
		}else{
		
			String string = "The parameter file you have just uploaded does not contain exactly 7, 14, 21, 28, 35, 42, or 49 parameters.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
		
		}
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		if(ds.getModifyRateDataStructure().getNumberParameters()!=0){
		
			numParamComboBox.removeItemListener(this);
			numParamComboBox.setSelectedItem(String.valueOf(ds.getModifyRateDataStructure().getNumberParameters()));
			numParamComboBox.addItemListener(this);
			
		}else{
		
			numParamComboBox.removeItemListener(this);
			numParamComboBox.setSelectedItem("7");
			numParamComboBox.addItemListener(this);
			
			ds.getModifyRateDataStructure().setNumberParameters(7);
		
		}

		int length = ds.getModifyRateDataStructure().getNumberParameters()/7;
	
		paramTabbedPane.removeAll();
	
		numParamComboBox.removeItemListener(this);
		numParamComboBox.setSelectedIndex(length-1);
		numParamComboBox.addItemListener(this);
	
		for(int i=0; i<length; i++){
		
			paramTabbedPane.addTab(paramPanelStringArray[i], paramPanelArray[i]);
		
			useCompArray[i] = true;
		
			for(int j=0; j<7; j++){

				if(ds.getModifyRateDataStructure().getParameters().length!=0){

					paramFieldArray[i][j].setText(String.valueOf(ds.getModifyRateDataStructure().getParameterCompArray()[i][j]));
			
				}else{
				
					paramFieldArray[i][j].setText("0.0");
				
				}
			}
		}

		for(int i=0; i<length; i++){
		
			if(ds.getModifyRateDataStructure().getResonantInfo().length!=0){
		
				if(ds.getModifyRateDataStructure().getResonantInfo()[i].equals("nr")){
				
					radioButtonArray[i][0].setSelected(true);
					radioButtonArray[i][1].setSelected(false);
				
				}else if(ds.getModifyRateDataStructure().getResonantInfo()[i].equals("r")){
	
					radioButtonArray[i][0].setSelected(false);
					radioButtonArray[i][1].setSelected(true);
				
				}
			
			}else{
			
				radioButtonArray[i][0].setSelected(true);
				radioButtonArray[i][1].setSelected(false);
			
			}
		
		}
		
		
		
		validate();

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		ds.getModifyRateDataStructure().setNumberParameters(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue());
	
		double[] outputArray = new double[Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue()];
		
		double[][] outputCompArray = new double[(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7][7];
		
		int counter = 0;
		
		for(int i=0; i<(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7; i++){
		
			for(int j=0; j<7; j++){
			
				outputArray[counter] = Double.valueOf(paramFieldArray[i][j].getText()).doubleValue();
				
				outputCompArray[i][j] = Double.valueOf(paramFieldArray[i][j].getText()).doubleValue();
				
			}

		}
		
		ds.getModifyRateDataStructure().setParameters(outputArray);
		
		ds.getModifyRateDataStructure().setParameterCompArray(outputCompArray);
		
		String[] outputStringArray = new String[(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7];
		
		for(int i=0; i<(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7; i++){
		
			if(radioButtonArray[0][i].isSelected()){
			
				outputStringArray[i] = "nr";
			
			}else{
			
				outputStringArray[i] = "r";
			
			}
		
		}
		
		ds.getModifyRateDataStructure().setResonantInfo(outputStringArray);
	
	}
	
	/**
	 * Creates the rate merge dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	private void createRateMergeDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	rateMergeDialog = new JDialog(frame, "Rate saved", true);
    	rateMergeDialog.setSize(350, 210);
    	rateMergeDialog.getContentPane().setLayout(new GridBagLayout());
		rateMergeDialog.setLocationRelativeTo(frame);
		
		JTextArea rateMergeTextArea = new JTextArea("", 5, 30);
		rateMergeTextArea.setLineWrap(true);
		rateMergeTextArea.setWrapStyleWord(true);
		rateMergeTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateMergeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		rateMergeTextArea.setText(string);
		rateMergeTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					rateMergeDialog.setVisible(false);
					rateMergeDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateMergeDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		rateMergeDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(rateMergeDialog);
		
		//Show the dialog
		rateMergeDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the rate modify dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	private void createRateModifyDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	rateModifyDialog = new JDialog(frame, "Rate saved", true);
    	rateModifyDialog.setSize(350, 210);
    	rateModifyDialog.getContentPane().setLayout(new GridBagLayout());
		rateModifyDialog.setLocationRelativeTo(frame);
		
		JTextArea rateModifyTextArea = new JTextArea("", 5, 30);
		rateModifyTextArea.setLineWrap(true);
		rateModifyTextArea.setWrapStyleWord(true);
		rateModifyTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateModifyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		rateModifyTextArea.setText(string);
		rateModifyTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					rateModifyDialog.setVisible(false);
					rateModifyDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateModifyDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		rateModifyDialog.getContentPane().add(okButton, gbc1);

		//Cina.setFrameColors(rateModifyDialog);
		
		//Show the dialog
		rateModifyDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the save rate dialog.
	 *
	 * @param frame the frame
	 */
	private void createSaveRateDialog(JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	saveRateDialog = new JDialog(frame, "Save rate to library...", true);
    	saveRateDialog.setSize(480, 170);
    	saveRateDialog.getContentPane().setLayout(new GridBagLayout());
		saveRateDialog.setLocationRelativeTo(frame);

		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		newLibField = new JTextField(15);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		if(ds.getNumberUserLibraryDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			
				libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
			
			}
		
			newLibField.setEditable(false);
		
			libComboBox.addItem("new library");
		
			libComboBox.addItemListener(this);
		
			JLabel topLabel = new JLabel("Select a User library or create a new library to save current rate.");
			topLabel.setFont(Fonts.textFont);
			
			JLabel newLibLabel = new JLabel("Enter the name of the new library: ");
			newLibLabel.setFont(Fonts.textFont);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveRateDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 2;
			saveRateDialog.getContentPane().add(libComboBox, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibField, gbc1);
		
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
			saveRateDialog.getContentPane().add(buttonPanel, gbc1);
			
		}else{
		
			JLabel topLabel = new JLabel("There are no User libraries. Create a new library to save current rate.");
			topLabel.setFont(Fonts.textFont);
			
			JLabel newLibLabel = new JLabel("Enter the name of the new library: ");
			newLibLabel.setFont(Fonts.textFont);
		
			newLibField.setEditable(true);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveRateDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibField, gbc1);	
			
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
			saveRateDialog.getContentPane().add(buttonPanel, gbc1);
				
		}
		
		//Cina.setFrameColors(saveRateDialog);
		
		//Show the dialog
		saveRateDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the rate exists dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	private void createRateExistsDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	rateExistsDialog = new JDialog(frame, "Caution!", true);
    	rateExistsDialog.setSize(320, 215);
    	rateExistsDialog.getContentPane().setLayout(new GridBagLayout());
		rateExistsDialog.setLocationRelativeTo(frame);
		
		JTextArea rateExistsTextArea = new JTextArea("", 5, 30);
		rateExistsTextArea.setLineWrap(true);
		rateExistsTextArea.setWrapStyleWord(true);
		rateExistsTextArea.setFont(Fonts.textFont);
		rateExistsTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateExistsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String rateExistsString = string;

		rateExistsTextArea.setText(rateExistsString);
		
		rateExistsTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton2 = new JButton("Yes");
		yesButton2.setFont(Fonts.buttonFont);
		yesButton2.addActionListener(this);
		
		noButton2 = new JButton("No");
		noButton2.setFont(Fonts.buttonFont);
		noButton2.addActionListener(this);
		
		JPanel rateExistsButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateExistsButtonPanel.add(yesButton2, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		rateExistsButtonPanel.add(noButton2, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateExistsDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		rateExistsDialog.getContentPane().add(rateExistsButtonPanel, gbc1);
		
		//Cina.setFrameColors(rateExistsDialog);
		
		rateExistsDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	private void createCautionDialog(String string, Frame frame){
	
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