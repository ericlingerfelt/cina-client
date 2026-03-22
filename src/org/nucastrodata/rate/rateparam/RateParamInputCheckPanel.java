package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateParamInputCheckPanel.
 */
public class RateParamInputCheckPanel extends WizardPanel{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The input panel. */
	JPanel mainPanel, questionLabelPanel, inputSummaryPanel, checkInputPanel, inputPanel; 
	
	//Number of input points
	/** The number points. */
	int numberPoints = 0;
	
	/** The tempmin. */
	double tempmin = 0.0;
	
	/** The tempmax. */
	double tempmax = 0.0;
	
	/** The ratemin. */
	double ratemin = 0.0;
	
	/** The ratemax. */
	double ratemax = 0.0;
	
	/** The input summary field array. */
	JTextField[] inputSummaryFieldArray = new JTextField[5];
	
	/** The check input label. */
	JLabel questionLabel, inputSummaryLabel, checkInputLabel;
	
	/** The input summary label array. */
	JLabel[] inputSummaryLabelArray = new JLabel[7];
	
	/** The tool tip array. */
	String[] toolTipArray = {"<html>Verify that all input <p>is positive and definite.</html>"
								, "<html>Verify that all dependent values,<p> such as energy and temperature,<p> are unique. For example,<p> two points with the same energy<p> value would cause the test to fail.</html>"
								, "<html>Verify that all input has a <p>reasonable value for generating<p> reaction rates. Energies greater than<p> 20 MeV or temperatures less than<p> 1 million degrees would cause<p> this test to fail.</html>"
								, ""
								, "<html>Verify that each uncertainty<p> is smaller than the measured value.</html>"};
										
	/** The input summary string array. */
	String[] inputSummaryStringArray = {"Number of points: "
										, "Temp min: "
										, "Temp max: "
										, "Rate min: "
										, "Rate max: "};
	
	/** The check input box array. */
	JCheckBox[] checkInputBoxArray = new JCheckBox[5]; 
	
	/** The check input string array. */
	String[] checkInputStringArray = {"positive definite"
									, "single valued"
									, "reasonable value ranges"
									, "continuity"
									, "unreasonable uncertainties"};
	
	/** The check input array. */
	String[] checkInputArray= {"N", "N", "N", "N", "N"};
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param input check panel.
	 *
	 * @param ds the ds
	 */
	public RateParamInputCheckPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(3);

		gbc = new GridBagConstraints();
	
		//Create checkboxes/////////////////////////////////////////////////CHECKBOXES/////////////////
		for(int i=0; i<5; i++){
			
			if(i!=3){
			
				checkInputBoxArray[i] = new JCheckBox(checkInputStringArray[i]);
				checkInputBoxArray[i].setToolTipText(toolTipArray[i]);
			
			}else{
			
				checkInputBoxArray[i] = new JCheckBox(checkInputStringArray[i], false);
			
			}
			
			checkInputBoxArray[i].setFont(Fonts.textFont);
			
		}
		
		inputSummaryLabel = new JLabel("Input File Properties");
		inputSummaryLabel.setFont(Fonts.titleFont);
		
		checkInputLabel = new JLabel("<html>Preprocessing Tests to ensure input data will give a valid reaction rate and/or <p>parameterization (explanation of each test is given by moving your mouse <p>over the checkbox)");
		checkInputLabel.setFont(Fonts.titleFont);
		
		for(int i=0; i<7; i++){
			inputSummaryLabelArray[i] = new JLabel();
			inputSummaryLabelArray[i].setFont(Fonts.textFont);
		}
		
		//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
		mainPanel = new JPanel(new GridBagLayout());
		
		questionLabelPanel = new JPanel(new FlowLayout());
		
		inputSummaryPanel = new JPanel(new GridBagLayout());
		
		checkInputPanel = new JPanel(new GridBagLayout());
		
		inputPanel = new JPanel(new GridBagLayout());
		
		addWizardPanel("Rate Parameterizer", "Preprocess Input File", "3", "12");
		
		//Add components to panels////////////////////////////////////////ADD//TO//PANELS////////////
		
		gbc.insets = new Insets(0, 5, 15, 5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(inputSummaryLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(inputSummaryLabelArray[0], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(inputSummaryLabelArray[1], gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(inputSummaryLabelArray[2], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(inputSummaryLabelArray[3], gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(inputSummaryLabelArray[4], gbc);
		
		gbc.insets = new Insets(0, 0, 15, 0);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 3;
		mainPanel.add(checkInputLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(checkInputBoxArray[0], gbc);
			
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(checkInputBoxArray[1], gbc);		
	
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(checkInputBoxArray[2], gbc);
			
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(checkInputBoxArray[4], gbc);
		
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		numberPoints = ds.getNumberPoints();
		
		tempmin = ds.getTempminRT();
		tempmax = ds.getTempmaxRT();
		
		ratemin = ds.getRatemin();
		ratemax = ds.getRatemax();
		
		for(int i=0; i<5; i++){
		
			checkInputArray[i] = ds.getCheckInputArray()[i];
			
			if(checkInputArray[i].equals("Y")){
				
				checkInputBoxArray[i].setSelected(true);
				
			}else{
			
				checkInputBoxArray[i].setSelected(false);
			
			}
		}

		setNewSummary();

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		for(int i=0; i<5; i++){
			
			if(checkInputBoxArray[i].isSelected()){
				
				checkInputArray[i] = "Y";
			
			}else{
			
				checkInputArray[i] = "N";
				
			}
		}
		
		ds.setCheckInputArray(checkInputArray);
		
	}
	
	/**
	 * Sets the new summary.
	 */
	public void setNewSummary(){
		 
	 	inputSummaryLabelArray[0].setText(inputSummaryStringArray[0] + String.valueOf(numberPoints));
		
		inputSummaryLabelArray[1].setText(inputSummaryStringArray[1] + String.valueOf(tempmin) + " T9");
		inputSummaryLabelArray[2].setText(inputSummaryStringArray[2] + String.valueOf(tempmax) + " T9"); 

		inputSummaryLabelArray[3].setText(inputSummaryStringArray[3] + String.valueOf(ratemin) + " " 
											+ ds.getRateUnits());
	 	inputSummaryLabelArray[4].setText(inputSummaryStringArray[4] + String.valueOf(ratemax) + " " 
											+ ds.getRateUnits());								
	
	}
	
}