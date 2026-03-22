package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManInvestRate1Panel.
 */
public class RateManInvestRate1Panel extends WizardPanel{
	
	/** The chart radio button. */
	private JRadioButton ZARadioButton, treeRadioButton, chartRadioButton; 
	
	/** The ds. */
	private RateManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate man invest rate1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManInvestRate1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(6);
		Cina.rateManFrame.setCurrentPanelIndex(1);

		//Create main panel to hold all other inner panels and components
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		//Instantiate gbc
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel topLabel = new JLabel("<html>Welcome to the Rate Investigator. With this tool you can plot,<p>"
									+ " and access information for all instances<p>"
									+ "of a chosen rate for all libraries accessible to the user.<br><br>To select the rate of interest, select a method "
									+ "below and<p>click <i>Continue</i>.</html>");
		
		ZARadioButton = new JRadioButton("Enter Z and A values for each reactant and product", false);
		ZARadioButton.setFont(Fonts.textFont);
		treeRadioButton = new JRadioButton("Select reaction from a tree", true);
		treeRadioButton.setFont(Fonts.textFont);
		chartRadioButton = new JRadioButton("Select reaction from the Chart of the Nuclides", false);
		chartRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(ZARadioButton);
		buttonGroup.add(treeRadioButton);
		buttonGroup.add(chartRadioButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		mainPanel.add(topLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(chartRadioButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(treeRadioButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(ZARadioButton, gbc);
		
		addWizardPanel("Rate Manager", "Rate Locator", "1", "3");
		add(mainPanel, BorderLayout.CENTER);
		validate();
		
	}
	
	//Method to get the state of this object
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(ZARadioButton.isSelected()){
			ds.setInvestRateSelector("ZAFIELDS");
		}else if(treeRadioButton.isSelected()){
			ds.setInvestRateSelector("TREE");
		}else if(chartRadioButton.isSelected()){
			ds.setInvestRateSelector("CHART");
		}
		
	}
	
	//Method to set the state of this object
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		String string = ds.getInvestRateSelector();
		
		if(string.equals("ZAFIELDS")){
		
			ZARadioButton.setSelected(true);
			treeRadioButton.setSelected(false);
			chartRadioButton.setSelected(false);
		
		}else if(string.equals("TREE")){
		
			ZARadioButton.setSelected(false);
			treeRadioButton.setSelected(true);
			chartRadioButton.setSelected(false);
		
		}else if(string.equals("CHART")){
		
			ZARadioButton.setSelected(false);
			treeRadioButton.setSelected(false);
			chartRadioButton.setSelected(true);
		
		}
	
	}

}