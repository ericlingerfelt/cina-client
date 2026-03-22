//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateGenOptionsPanel.
 */
public class RateGenOptionsPanel extends WizardPanel{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The temp panel. */
	JPanel mainPanel, contentPanel, outputLevelPanel, tempPanel; 

	/** The advanced button. */
	JButton advancedButton;
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[9];
	
	/** The string array. */
	String[] stringArray = {"Reaction: "
								, "Data source: "
								, "Number of data points: "
								, "S Factor Min: "
								, "S Factor Max: "
								, "Cross Section Min: "
								, "Cross Section Max: "
								, "Temperature range for rate generation (T9): "
								, "(allowed range: "
								, " - "
								, ")"
								, "Screen Output Level (4 = max; 1 = min): " 
								, "(4 = max; 1 = min)" };
	
	/** The reaction name. */
	String reactionName;
	
	/** The filename. */
	String filename; 
	
	/** The tempmax label. */
	JLabel tempminLabel, tempmaxLabel; 
	
	/** The bottom label. */
	JLabel topLabel, bottomLabel;
	
	/** The number points. */
	int numberPoints;
	
	/** The tempmax. */
	double crossSectionmin, crossSectionmax, SFactormin, SFactormax
			, allowedTempmin, allowedTempmax, tempmin, tempmax;
	
	/** The output level. */
	int outputLevel;
	
	/** The output level combo box. */
	JComboBox outputLevelComboBox;
	
	/** The tempmax field. */
	JTextField tempminField, tempmaxField;
	
	/** The ds. */
	private RateGenDataStructure ds;

	/**
	 * Instantiates a new rate gen options panel.
	 *
	 * @param ds the ds
	 */
	public RateGenOptionsPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateGenFrame.setCurrentPanelIndex(5);
		
		gbc = new GridBagConstraints();
	
		//Create button///////////////////////////////////////////////////BUTTON///////////////////
		advancedButton = new JButton("Advanced Options");
		advancedButton.setFont(Fonts.buttonFont);
		advancedButton.setEnabled(false);
		
		//Create labels///////////////////////////////////////////////////LABELS///////////////////
		for(int i=0; i<9; i++){
			labelArray[i] = new JLabel();
			labelArray[i].setFont(Fonts.textFont);
		}
		
		//Create textfields//////////////////////////////////////////////TEXTFIELDS//////////////////
		tempminField = new JTextField(5);
		tempminField.setFont(Fonts.textFont);
		
		tempmaxField = new JTextField(5);
		tempmaxField.setFont(Fonts.textFont);
		
		//Create choice//////////////////////////////////////////////////CHOICE/////////////////////
		outputLevelComboBox = new JComboBox();
		outputLevelComboBox.addItem("1");
		outputLevelComboBox.addItem("2");
		outputLevelComboBox.addItem("3");
		outputLevelComboBox.addItem("4");
		outputLevelComboBox.setEnabled(false);
		outputLevelComboBox.setFont(Fonts.textFont);
		
		//Create Panels//////////////////////////////////////////////////PANELS/////////////////////
		contentPanel = new JPanel(new GridBagLayout());
		
		tempPanel = new JPanel(new GridBagLayout());
		
		outputLevelPanel = new JPanel(new GridBagLayout());
		
		mainPanel = new JPanel(new BorderLayout());
		
		addWizardPanel("Rate Generator", "Rate Generation Options", "5", "7");
		
		//Add components to panels////////////////////////////////////////ADD//TO//PANELS/////////////
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		add(mainPanel, BorderLayout.CENTER);
		
		setCurrentState();
		
		validate();
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		reactionName = ds.getRateDataStructure().getReactionString();
		
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			reactionName += " ["
							+ ds.getRateDataStructure().getDecay()
							+ "]";
		
		}
		
		filename = ds.getInputFilename();
		
		numberPoints = ds.getNumberPoints();
		
		SFactormin = ds.getSFactormin();
		SFactormax = ds.getSFactormax();
		
		crossSectionmin = ds.getCrossSectionmin();
		crossSectionmax = ds.getCrossSectionmax();
		
		allowedTempmin = ds.getAllowedTempmin();
		allowedTempmax = ds.getAllowedTempmax();
		
		tempmin = ds.getTempmin();
		tempmax = ds.getTempmax();
		
		outputLevelComboBox.setSelectedIndex(ds.getOutputLevel() - 1);
		
		tempminField.setText(String.valueOf(allowedTempmin));
		tempmaxField.setText(String.valueOf(allowedTempmax));
		
		setComponentLayout();
		
	}
	
	/**
	 * Sets the component layout.
	 */
	public void setComponentLayout(){
	
		topLabel = new JLabel("Choose Temperature range for Reaction Rate and detail of output.");
	
		bottomLabel = new JLabel("<html>Click Continue to numerically integrate input file and <p>generate Reaction Rate vs. Temperature.<html>");
	
		//Initialize labels 
		labelArray[0].setText(stringArray[0] + reactionName);
		labelArray[1].setText(stringArray[1] + filename);
		labelArray[2].setText(stringArray[2] + String.valueOf(numberPoints));
		
		if(ds.getInputType().equals("CS(E)")){
			
			labelArray[3].setText(stringArray[5] 
									+ String.valueOf(ds.getCrossSectionmin()) + " b");
											
			labelArray[4].setText(stringArray[6] 
									+ String.valueOf(ds.getCrossSectionmax()) + " b");
			
		}else if(ds.getInputType().equals("S(E)")){
			
			labelArray[3].setText(stringArray[3] 
									+ String.valueOf(ds.getSFactormin()) + " keV-b");
			labelArray[4].setText(stringArray[4] 
									+ String.valueOf(ds.getSFactormax()) + " keV-b");
		
		}
		
		
		labelArray[5].setText(stringArray[7]);
		
		labelArray[6].setText(stringArray[8] 
								+ String.valueOf(ds.getAllowedTempmin())
								+ stringArray[9] 
								+ String.valueOf(ds.getAllowedTempmax())
								+ stringArray[10]);
						
		labelArray[7].setText(stringArray[11]);
		
		labelArray[8].setText(stringArray[12]);
		
		gbc.insets = new Insets(0, 0, 10, 0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		contentPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		contentPanel.add(labelArray[0], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		contentPanel.add(labelArray[1], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		contentPanel.add(labelArray[2], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		contentPanel.add(labelArray[3], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		
		contentPanel.add(labelArray[4], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		
		contentPanel.add(labelArray[5], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 10, 0, 0);
		tempPanel.add(new JLabel(""), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 10);
		tempPanel.add(tempminField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		tempPanel.add(new JLabel(" - "), gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		
		tempPanel.add(tempmaxField, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		
		tempPanel.add(new JLabel(""), gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		
		contentPanel.add(tempPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.WEST;
		contentPanel.add(labelArray[6], gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		outputLevelPanel.add(labelArray[7], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		outputLevelPanel.add(outputLevelComboBox, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		
		contentPanel.add(outputLevelPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.CENTER;
		contentPanel.add(advancedButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 0, 0, 0);
		contentPanel.add(bottomLabel, gbc);
		
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){
	
		boolean pass = false;
	
		try{
		
			if(Double.valueOf(tempminField.getText()).doubleValue() < allowedTempmin
					|| Double.valueOf(tempmaxField.getText()).doubleValue() > allowedTempmax){
			
				String string = "Temperature entries must be within allowed range.";
			
				Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
				
				pass = false;
			
			}else if(Double.valueOf(tempminField.getText()).doubleValue() 
						>= Double.valueOf(tempmaxField.getText()).doubleValue()){
			
				String string = "Temperature minimum must be less than temperature maximum.";
			
				Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
				
				pass = false;
			
			}else{
			
				pass = true;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Temperature entries must be numeric values.";
			
			Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
			
			pass = false;
		
		}
		
		return pass;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		ds.setTempmin(Double.valueOf(tempminField.getText()).doubleValue());	
		ds.setTempmax(Double.valueOf(tempmaxField.getText()).doubleValue());
		
		ds.setOutputLevel(outputLevelComboBox.getSelectedIndex() + 1);
		
	}
	
}