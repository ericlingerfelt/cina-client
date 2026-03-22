package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.util.*;
import java.io.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateman.RateManCreateRate3HelpFrame;
import org.nucastrodata.rate.rateman.RateManCreateRatePlotFrame;


/**
 * The Class RateManCreateRate3Panel.
 */
public class RateManCreateRate3Panel extends WizardPanel implements ActionListener, ItemListener{

	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 

	/** The scale label. */
	JLabel numParamLabel, scaleLabel;
	
	/** The button group array. */
	ButtonGroup[] buttonGroupArray = new ButtonGroup[7];
	
	/** The radio button array. */
	JRadioButton[][] radioButtonArray = new JRadioButton[7][2];
	
	/** The param panel. */
	JPanel buttonPanel, paramPanel;
	
	/** The param tabbed pane. */
	JTabbedPane paramTabbedPane;
	
	/** The param panel array. */
	JPanel[] paramPanelArray = new JPanel[7];
	
	/** The param panel string array. */
	String[] paramPanelStringArray = {"1-7"
										, "8-14"
										, "15-21"
										, "22-28"
										, "29-35"
										, "36-42"
										, "43-49"};
										
	/** The param field array. */
	JTextField[][] paramFieldArray = new JTextField[7][7];
	
	/** The lib combo box. */
	JComboBox numParamComboBox, libComboBox;
	
	/** The scale field. */
	JTextField newLibField, scaleField;
	
	/** The scale button. */
	JButton importButton, helpButton, plotButton
			, saveButton, cancelButton, okButton
			, yesButton, noButton, yesButton2, noButton2
			, scaleButton;

	/** The file dialog. */
	JFileChooser fileDialog;

	/** The input file dir. */
	String inputFileDir;
    
    /** The input file. */
    String inputFile = "";

	/** The rate exists dialog. */
	JDialog saveRateDialog, cautionDialog, rateMergeDialog, rateModifyDialog, rateExistsDialog;

	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {0, 1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {0, 1, 2, 3, 1, 2, 3, 4, 2};
	
	/** The use comp array. */
	boolean[] useCompArray = new boolean[7];

	/** The current scale factor. */
	double currentScaleFactor = 1.0;

	/** The ds. */
	private RateManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate man create rate3 panel.
	 *
	 * @param ds the ds
	 */
	public RateManCreateRate3Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(2);
		Cina.rateManFrame.setCurrentPanelIndex(3);

		mainPanel = new JPanel(new BorderLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Create New Rate", "3", "3");
		
		numParamLabel = new JLabel("Number of Parameters: ");
		numParamLabel.setFont(Fonts.textFont);
		
		plotButton = new JButton("<html>Open Plotting<p>Interface</html>");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		importButton = new JButton("<html>Import Rate Parameters<p>from File</html>");
		importButton.setFont(Fonts.buttonFont);
		importButton.addActionListener(this);
		
		helpButton = new JButton("<html>Help on Parameter<p>File Format</html>");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this);
		
		saveButton = new JButton("<html>Save<p>Rate</html>");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			saveButton.setEnabled(false);
		
		}else{
		
			saveButton.setEnabled(true);
		
		}
		
		numParamComboBox = new JComboBox();
		
		buttonPanel = new JPanel(new GridBagLayout());
		
		paramPanel = new JPanel(new GridBagLayout());
		
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
		
		scaleLabel = new JLabel("Scale Factor: ");
		scaleLabel.setFont(Fonts.textFont);
		
		paramTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		
		gbc.insets = new Insets(7, 7, 7, 7);

		for(int i=0; i<7; i++){
	
			radioButtonArray[i][0] = new JRadioButton("Nonresonant", true);
			radioButtonArray[i][1] = new JRadioButton("Resonant", false);
			
			radioButtonArray[i][0].setFont(Fonts.textFont);
			radioButtonArray[i][1].setFont(Fonts.textFont);
			
			buttonGroupArray[i] = new ButtonGroup();
			
			buttonGroupArray[i].add(radioButtonArray[i][0]);
			buttonGroupArray[i].add(radioButtonArray[i][1]);
		
		}

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
		buttonPanel.add(importButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		buttonPanel.add(helpButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		buttonPanel.add(plotButton, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		
		buttonPanel.add(saveButton, gbc);

		mainPanel.add(paramPanel, BorderLayout.NORTH);

		mainPanel.add(paramTabbedPane, BorderLayout.CENTER);
		
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		add(mainPanel, BorderLayout.CENTER);
		
		
		
		validate();
		
	}
	
	
	/**
	 * Check zero parameters.
	 *
	 * @return true, if successful
	 */
	public boolean checkZeroParameters(){
	
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
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){
	
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
	 * Sets the rate data.
	 *
	 * @return true, if successful
	 */
	public boolean setRateData(){

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
		
		ds.getCreateRateDataStructure().setNumberParameters(counter);
		
		int[] useFieldArray = new int[(int)(counter/7)];
		
		counter = 0;
		
		for(int i=0; i<numParamComboBox.getSelectedIndex()+1; i++){
		
			if(useCompArray[i]){
			
				useFieldArray[counter] = i;
				
				counter++;
			
			}
		
		}
		
		double[] outputArray = new double[ds.getCreateRateDataStructure().getNumberParameters()];
		
		double[][] outputCompArray = new double[(int)(outputArray.length/7)][7];
		
		counter = 0;
		
		for(int i=0; i<(int)(outputArray.length/7); i++){
		
			for(int j=0; j<7; j++){
			
				outputArray[counter] = Double.valueOf(paramFieldArray[useFieldArray[i]][j].getText()).doubleValue();
				
				outputCompArray[i][j] = outputArray[counter];
			
				counter++;
			
			}
		
		}
		
		ds.getCreateRateDataStructure().setParameters(outputArray);
		ds.getCreateRateDataStructure().setParameterCompArray(outputCompArray);
		ds.getCreateRateDataStructure().setResonantInfo(getResonantInfo(useCompArray));
		
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
				
					ds.getCreateRateDataStructure().setTempmin(getTempmin());
					ds.getCreateRateDataStructure().setTempmax(getTempmax());
		
					ds.getCreateRateDataStructure().setRatemin(getRatemin());
					ds.getCreateRateDataStructure().setRatemax(getRatemax());
		
					if(Cina.rateManFrame.rateManCreateRatePlotFrame==null){
						
						Cina.rateManFrame.rateManCreateRatePlotFrame = new RateManCreateRatePlotFrame(ds);
						
					}else{
		
						Cina.rateManFrame.rateManCreateRatePlotFrame.setFormatPanelState();
						Cina.rateManFrame.rateManCreateRatePlotFrame.rateManCreateRatePlotCanvas.setPlotState();
						Cina.rateManFrame.rateManCreateRatePlotFrame.setVisible(true);
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
		
		}else if(ae.getSource()==importButton){
		
			fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				uploadFile(file);
		
				assignParameters();
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
		
		}else if(ae.getSource()==helpButton){
		
			if(Cina.rateManFrame.rateManCreateRate3HelpFrame==null){

				Cina.rateManFrame.rateManCreateRate3HelpFrame = new RateManCreateRate3HelpFrame();

			}else{
				
				Cina.rateManFrame.rateManCreateRate3HelpFrame.setVisible(true);
				
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
	public boolean checkNewLibField(){
	
		boolean goodName = false;
	
		if(!newLibField.getText().equals("")){
		
			goodName = true;
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check public lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicLibName(){
	
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
	public boolean checkOverwriteLibName(){
	
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
	public String getRateProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Parameters = " + String.valueOf(ds.getCreateRateDataStructure().getNumberParameters()) + "\u0009";
		propertiesString += "Number of Products = " + String.valueOf(ds.getCreateRateDataStructure().getNumberProducts()) + "\u0009";
		propertiesString += "Number of Reactants = " + String.valueOf(ds.getCreateRateDataStructure().getNumberReactants()) + "\u0009";
		propertiesString += "Parameters = " + getParameters() + "\u0009";
		propertiesString += "Reaction Notes = " + ds.getCreateRateDataStructure().getReactionNotes() + "\u0009";
		propertiesString += "Reaction String = " + ds.getCreateRateDataStructure().getReactionString() + "\u0009";

		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getCreateRateDataStructure().getReactionType());
		
		if(!ds.getCreateRateDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getCreateRateDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Resonant Components = " + getResonantInfoString() + "\u0009";
		propertiesString += "Chisquared = " + "" + "\u0009";
		propertiesString += "Max Percent Difference = " + "" + "\u0009";
		propertiesString += "Valid Temp Range = " + "" + "\u0009";
		propertiesString += "Q-value = " + "" + "\u0009";
		propertiesString += "Biblio Code = " + ds.getCreateRateDataStructure().getBiblioString() + "\u0009";
		propertiesString += "Reference Citation = " + ds.getCreateRateDataStructure().getRefCitation();

		return propertiesString;
	
	}
	
	/**
	 * Gets the resonant info.
	 *
	 * @param booleanArray the boolean array
	 * @return the resonant info
	 */
	public String[] getResonantInfo(boolean[] booleanArray){
	
		String[] stringArray = new String[(int)(ds.getCreateRateDataStructure().getNumberParameters()/7)];
		
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
	public String getResonantInfoString(){
	
		String string = "";
		
		for(int i=0; i<ds.getCreateRateDataStructure().getResonantInfo().length; i++){
		
			if(i==0){
			
				string = ds.getCreateRateDataStructure().getResonantInfo()[i];
			
			}else{
			
				string = string + "," + ds.getCreateRateDataStructure().getResonantInfo()[i];
			 
			}
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public String getParameters(){
	
		String string = "";
		
		for(int i=0; i<ds.getCreateRateDataStructure().getNumberParameters(); i++){
		
			if(i!=0){
		
				string = string + "," + String.valueOf(ds.getCreateRateDataStructure().getParameters()[i]);
			
			}else{
				
				string = String.valueOf(ds.getCreateRateDataStructure().getParameters()[i]);
			
			}
		
		}
		
		return string;
	
	}
	
	
	/**
	 * Good rate merge.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateMerge(){
		
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
	public boolean goodRateModify(){
		
		boolean goodRateModify = false;
		
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
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATES", Cina.rateManFrame);
		
		if(!flagArray[0]){
			
			if(!flagArray[2]){
				
				if(!flagArray[1]){
						
					goodRateModify = true;
						
				}
			}
		}

		return goodRateModify;
	
	}
	
	/**
	 * Gets the current rate id.
	 *
	 * @return the current rate id
	 */
	public String getCurrentRateID(){
	
		String string = "";
		
		string = "0"
					+ String.valueOf(ds.getCreateRateDataStructure().getReactionType()) 
					+ getZAString()
					+ ds.getDestLibName()
					+ "\u0009"
					+ ds.getCreateRateDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getCreateRateDataStructure().getDecayType().equals("none")){
		
			string += ds.getCreateRateDataStructure().getDecay();
		
		}
		
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
	
		Point[] isoIn = new Point[numberReactants[ds.getCreateRateDataStructure().getReactionType()]]; 
		
		Point biggestPoint = new Point(0, 0);
		
		for(int i=0; i<isoIn.length; i++){
		
			isoIn[i] = ds.getCreateRateDataStructure().getIsoIn()[i];
		
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
	 * Good rate exists.
	 *
	 * @return true, if successful
	 */
	public boolean goodRateExists(){
		
		boolean goodRateExists = false;
		
		ds.setRates(getCurrentRateID());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("RATES EXIST", Cina.rateManFrame);
		
		if(!flagArray[0]){
			
			if(!flagArray[2]){
				
				if(!flagArray[1]){
						
					goodRateExists = true;
						
				}
			}
		}

		return goodRateExists;
	
	}
	
	/**
	 * Gets the source libraries.
	 *
	 * @return the source libraries
	 */
	public boolean getSourceLibraries(){
	
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
	public int getTempmin(){	
		
		/*double newTempmin = 1e30;
		
		int tempMin = 0;
		
		for(int k=0; k<ds.getCreateRateDataStructure().getTempDataArrayTotal().length; k++){

			newTempmin = Math.min(newTempmin, ds.getCreateRateDataStructure().getTempDataArrayTotal()[k]);

		}
    		

		tempMin = (int)Math.floor(0.434294482*Math.log(newTempmin));

		return tempMin;*/
		
		return -2;
		
	}
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	public int getTempmax(){	
		
		/*double newTempmax = 0.0;
		
		int tempMax = 0;
		
		for(int k=0; k<ds.getCreateRateDataStructure().getTempDataArrayTotal().length; k++){
		
			newTempmax = Math.max(newTempmax, ds.getCreateRateDataStructure().getTempDataArrayTotal()[k]);

		}
				
		tempMax = (int)Math.ceil(0.434294482*Math.log(newTempmax));

		return tempMax;*/
		
		return 1;
		
	}
	
	/**
	 * Gets the ratemin.
	 *
	 * @return the ratemin
	 */
	public int getRatemin(){
		
		double newRatemin = 1e30;
		
		int rateMin = 0;
		
		for(int k=0; k<ds.getCreateRateDataStructure().getRateDataArrayTotal().length; k++){

			newRatemin = Math.min(newRatemin, ds.getCreateRateDataStructure().getRateDataArrayTotal()[k]);

		}
    		

		rateMin = (int)Math.floor(0.434294482*Math.log(newRatemin));

		return rateMin;
	
	}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	public int getRatemax(){
		
		double newRatemax = 0.0;
		
		int rateMax = 0;
		
		for(int k=0; k<ds.getCreateRateDataStructure().getRateDataArrayTotal().length; k++){
		
			newRatemax = Math.max(newRatemax, ds.getCreateRateDataStructure().getRateDataArrayTotal()[k]);

		}
				
		rateMax = (int)Math.ceil(0.434294482*Math.log(newRatemax));

		return rateMax;
	
	}
	
	/**
	 * Sets the data arrays.
	 */
	public void setDataArrays(){

		ds.getCreateRateDataStructure().setTempDataArrayTotal(ds.getTempGrid());

		double[][] outputArray = new double[ds.getCreateRateDataStructure().getResonantInfo().length][ds.getTempGrid().length];

		for(int k=0; k<ds.getCreateRateDataStructure().getResonantInfo().length; k++){
		
			outputArray[k] = ds.getTempGrid();
		
		}
		
		ds.getCreateRateDataStructure().setTempDataArrayComp(outputArray);
			
		double[] tempArray = ds.getCreateRateDataStructure().getTempDataArrayTotal();

		int numberParameters = ds.getCreateRateDataStructure().getNumberParameters();
		
		double[] parameters = ds.getCreateRateDataStructure().getParameters();

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
		
		ds.getCreateRateDataStructure().setRateDataArrayTotal(rateArray);

		outputArray = new double[ds.getCreateRateDataStructure().getResonantInfo().length][Cina.rateViewerDataStructure.getTempGrid().length];

		double[][] parameterCompArray = ds.getCreateRateDataStructure().getParameterCompArray();
 				
		if(ds.getCreateRateDataStructure().getResonantInfo().length>1){

			for(int k=0; k<ds.getCreateRateDataStructure().getResonantInfo().length; k++){

				for(int l=0; l<ds.getTempGrid().length; l++){

					outputArray[k][l] = Cina.cinaMainDataStructure.calcRate(ds.getCreateRateDataStructure().getTempDataArrayComp()[k][l], parameterCompArray[k], 7);
				
					if(outputArray[k][l]<1e-100 || outputArray[k][l]==0){
			
						outputArray[k][l] = 1e-100;
					
					}
					
					if(outputArray[k][l]>1e100 || outputArray[k][l]==0){
			
						outputArray[k][l] = 1e100;
					
					}
				
				}
			
			}
			
			ds.getCreateRateDataStructure().setRateDataArrayComp(outputArray);
			
		}
    		
	}
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 */
	public void uploadFile(File file){

		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(stringBuffer);
			
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		
		String string = new String(stringBuffer);
		
		String inputFile = string;

		ds.setInputFile(inputFile);
	
	}
	
	/**
	 * Fill param fields.
	 */
	public void fillParamFields(){
	
		int length = (int)(ds.getCreateRateDataStructure().getParameters().length/7);
	
		paramTabbedPane.removeAll();
	
		numParamComboBox.removeItemListener(this);
		numParamComboBox.setSelectedIndex(length-1);
		numParamComboBox.addItemListener(this);
	
		for(int i=0; i<length; i++){
		
			paramTabbedPane.addTab(paramPanelStringArray[i], paramPanelArray[i]);
		
			for(int j=0; j<7; j++){

				paramFieldArray[i][j].setText(String.valueOf(ds.getCreateRateDataStructure().getParameterCompArray()[i][j]));
			
			}
		}
		
		
		
		validate();
	}
	
	/**
	 * Assign parameters.
	 */
	public void assignParameters(){
	
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
			
			double[][] outputCompArray = new double[(int)(outputArray.length/7)][7];
			
			for(int i=0; i<outputArray.length; i++){
			
				outputArray[i] = Double.valueOf(st.nextToken()).doubleValue();
			
			}
			
			int counter = 0;
			
			for(int i=0; i<(int)(outputArray.length/7); i++){
			
				for(int j=0; j<7; j++){
					
					outputCompArray[i][j] = outputArray[counter];
					
					counter++;
					
				}
			
			}
			
			ds.getCreateRateDataStructure().setParameters(outputArray);
			ds.getCreateRateDataStructure().setParameterCompArray(outputCompArray);
			ds.getCreateRateDataStructure().setNumberParameters(outputArray.length);
			
			String[] outputResonantArray = new String[(int)(outputArray.length/7)];
			
			for(int i=0; i<outputResonantArray.length; i++){
			
				if(i!=0){
				
					outputResonantArray[i] = "r";
				
				}else{
				
					outputResonantArray[i] = "nr";
				
				}
			
			}
			
			ds.getCreateRateDataStructure().setResonantInfo(outputResonantArray);
			
			fillParamFields();
		
		}else{
		
			String string = "The parameter file you have just uploaded does not contain exactly 7, 14, 21, 28, 35, 42, or 49 parameters.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
		
		}
		
		currentScaleFactor = 1.0;
		
		scaleField.setText("1.0");
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		if(ds.getCreateRateDataStructure().getNumberParameters()!=0){
		
			numParamComboBox.removeItemListener(this);
			numParamComboBox.setSelectedItem(String.valueOf(ds.getCreateRateDataStructure().getNumberParameters()));
			numParamComboBox.addItemListener(this);
			
		}else{
		
			numParamComboBox.removeItemListener(this);
			numParamComboBox.setSelectedItem("7");
			numParamComboBox.addItemListener(this);
			
			ds.getCreateRateDataStructure().setNumberParameters(7);
		
		}

		int length = (int)(ds.getCreateRateDataStructure().getNumberParameters()/7);
	
		paramTabbedPane.removeAll();
	
		numParamComboBox.removeItemListener(this);
		numParamComboBox.setSelectedIndex(length-1);
		numParamComboBox.addItemListener(this);

		for(int i=0; i<length; i++){
		
			paramTabbedPane.addTab(paramPanelStringArray[i], paramPanelArray[i]);
		
			useCompArray[i] = true;
		
			for(int j=0; j<7; j++){

				if(ds.getCreateRateDataStructure().getParameters().length!=0){

					paramFieldArray[i][j].setText(String.valueOf(ds.getCreateRateDataStructure().getParameterCompArray()[i][j]));
			
				}else{
				
					paramFieldArray[i][j].setText("0.0");
				
				}
			}
		}

		for(int i=0; i<length; i++){
		
			if(ds.getCreateRateDataStructure().getResonantInfo().length!=0){
		
				if(ds.getCreateRateDataStructure().getResonantInfo()[i].equals("nr")){
				
					radioButtonArray[i][0].setSelected(true);
					radioButtonArray[i][1].setSelected(false);
				
				}else if(ds.getCreateRateDataStructure().getResonantInfo()[i].equals("r")){
	
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
	
		ds.getCreateRateDataStructure().setNumberParameters(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue());
	
		double[] outputArray = new double[Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue()];
		
		double[][] outputCompArray = new double[(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7][7];
		
		int counter = 0;
		
		for(int i=0; i<(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7; i++){
		
			for(int j=0; j<7; j++){
			
				outputArray[counter] = Double.valueOf(paramFieldArray[i][j].getText()).doubleValue();
				
				outputCompArray[i][j] = Double.valueOf(paramFieldArray[i][j].getText()).doubleValue();
				
			}

		}
		
		ds.getCreateRateDataStructure().setParameters(outputArray);
		
		ds.getCreateRateDataStructure().setParameterCompArray(outputCompArray);
		
		String[] outputStringArray = new String[(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7];
		
		for(int i=0; i<(Integer.valueOf((String)numParamComboBox.getSelectedItem()).intValue())/7; i++){
		
			if(radioButtonArray[i][0].isSelected()){
			
				outputStringArray[i] = "nr";
			
			}else{
			
				outputStringArray[i] = "r";
			
			}
		
		}
		
		ds.getCreateRateDataStructure().setResonantInfo(outputStringArray);

	}
	
	/**
	 * Creates the rate merge dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createRateMergeDialog(String string, JFrame frame){
    	
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
	public void createRateModifyDialog(String string, JFrame frame){
    	
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
	public void createSaveRateDialog(JFrame frame){
    	
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
	public void createRateExistsDialog(String string, Frame frame){
	
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