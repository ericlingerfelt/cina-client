//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;



/**
 * The Class RateGenInputCheckPanel.
 */
public class RateGenInputCheckPanel extends WizardPanel{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The input panel. */
	JPanel mainPanel, questionLabelPanel, inputSummaryPanel, checkInputPanel, inputPanel; 
	
	//Number of input points
	/** The number points. */
	int numberPoints = 0;
	
	//Max and min of energy
	/** The Emin. */
	double Emin = 0.0;
	
	/** The Emax. */
	double Emax = 0.0;
	
	//max and min of cross section 
	/** The cross sectionmin. */
	double crossSectionmin = 0.0;
	
	/** The cross sectionmax. */
	double crossSectionmax = 0.0;
	
	//max and min of S Factor
	/** The S factormin. */
	double SFactormin = 0.0;
	
	/** The S factormax. */
	double SFactormax = 0.0;
	
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
	
	/** The input summary string array e. */
	String[] inputSummaryStringArrayE = {"Number of points: "
										, "E min: "
										, "E max: "
										, "S Factor min: "
										, "S Factor max: "
										, "Cross Section min: "
										, "Cross Section max: "};
	
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
	private RateGenDataStructure ds;
	
	/**
	 * Instantiates a new rate gen input check panel.
	 *
	 * @param ds the ds
	 */
	public RateGenInputCheckPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateGenFrame.setCurrentPanelIndex(3);

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
		
		addWizardPanel("Rate Generator", "Preprocess Input File", "3", "7");
		
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
		
		Emin = ds.getEmin();
		Emax = ds.getEmax();
		
		crossSectionmin = ds.getCrossSectionmin();
		crossSectionmax = ds.getCrossSectionmax();
		
		SFactormin = ds.getSFactormin();
		SFactormax = ds.getSFactormax();
		
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
		
		inputSummaryLabelArray[0].setText(inputSummaryStringArrayE[0] + String.valueOf(numberPoints));
		
		inputSummaryLabelArray[1].setText(inputSummaryStringArrayE[1] + String.valueOf(Emin) + " keV");
		inputSummaryLabelArray[2].setText(inputSummaryStringArrayE[2] + String.valueOf(Emax) + " keV");
		
		if(ds.getInputType().equals("CS(E)")){
			
			inputSummaryLabelArray[3].setText(inputSummaryStringArrayE[5] + String.valueOf(crossSectionmin) + " b");
	 		inputSummaryLabelArray[4].setText(inputSummaryStringArrayE[6] + String.valueOf(crossSectionmax) + " b"); 

	 	}else if(ds.getInputType().equals("S(E)")){
	 	
	 		inputSummaryLabelArray[3].setText(inputSummaryStringArrayE[3] + String.valueOf(SFactormin) + " keV-b");
	 		inputSummaryLabelArray[4].setText(inputSummaryStringArrayE[4] + String.valueOf(SFactormax) + " keV-b");
	 	
	 	}
	
	}
	
}