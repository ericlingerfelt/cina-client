package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManRateGrid3Panel.
 */
public class RateManRateGrid3Panel extends WizardPanel implements ActionListener{

	/** The copy button. */
	private JButton saveButton, copyButton;
	
	/** The output text area. */
	private JTextArea outputTextArea;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	/**
	 * Instantiates a new rate man rate grid3 panel.
	 *
	 * @param ds the ds
	 */
	public RateManRateGrid3Panel(RateManDataStructure ds){
	
		this.ds = ds;
	
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(4);
		Cina.rateManFrame.setCurrentPanelIndex(3);
		
		//Set the panel's layout
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Rates on a Grid", "3", "3");
				
		//Create text area//////////////////////////////////////////////TEXTAREAS///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(saveButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(copyButton, gbc);
		
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==saveButton){
			TextSaver.saveText(outputTextArea.getText(), this);
		}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(outputTextArea.getText());
    	}
	}
	
	/**
	 * Gets the rate temp points.
	 *
	 * @return the rate temp points
	 */
	private double[][] getRateTempPoints(){

		double[] tempGrid = ds.getRateTempGrid();
		double[][] ratePoints = new double[ds.getCurrentRateDataStructureArray().length][tempGrid.length];
		
		for(int i=0; i<ds.getCurrentRateDataStructureArray().length; i++){

			for(int j=0; j<tempGrid.length; j++){
			
				if(ds.getTempUnits().equals("T9")){
			
					ratePoints[i][j] = Cina.cinaMainDataStructure.calcRate(tempGrid[j]
																							, ds.getCurrentRateDataStructureArray()[i].getParameters()
																							, ds.getCurrentRateDataStructureArray()[i].getNumberParameters());
																							
				}else if(ds.getTempUnits().equals("T8")){
			
					ratePoints[i][j] = Cina.cinaMainDataStructure.calcRate(tempGrid[j]/10
																							, ds.getCurrentRateDataStructureArray()[i].getParameters()
																							, ds.getCurrentRateDataStructureArray()[i].getNumberParameters());
																							
				}
					
			}
		
		}
		
		return ratePoints;
	
	}
	
	/**
	 * Sets the output text.
	 *
	 * @param ratePoints the new output text
	 */
	private void setOutputText(double[][] ratePoints){

		double[] tempGrid = ds.getRateTempGrid();
		String[] tempStringArray = new String[tempGrid.length];
		int[] tempSpacesArray = new int[tempGrid.length];
		String[][] rateStringArray = new String[ds.getCurrentRateDataStructureArray().length][tempGrid.length];
		
		for(int i=0; i<tempGrid.length; i++){
		
			tempStringArray[i] = NumberFormats.getFormattedTemp3(tempGrid[i]);
			tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);

		}

		for(int i=0; i<ds.getCurrentRateDataStructureArray().length; i++){
			
			for(int j=0; j<tempGrid.length; j++){
			
				rateStringArray[i][j] = NumberFormats.getFormattedRate2(ratePoints[i][j]);
				
			}
			
		} 
		
		for(int i=0; i<ds.getCurrentRateDataStructureArray().length; i++){
			
			String reactionString = ds.getCurrentRateDataStructureArray()[i].getReactionString();
			
			if(!ds.getCurrentRateDataStructureArray()[i].getDecay().trim().equals("")){
				reactionString += " [" + ds.getCurrentRateDataStructureArray()[i].getDecay() + "]";
			}
			
			outputTextArea.append("#\n");
			outputTextArea.append("#       " 
										+ ds.getTempUnits() 
										//+ reactionString
										+ "\n");
										
			outputTextArea.append("#\n");
			
			for(int j=0; j<tempGrid.length; j++){
				
				for(int k=0; k<tempSpacesArray[j]; k++){
					outputTextArea.append(" ");
				}
				
				outputTextArea.append(tempStringArray[j]);
				outputTextArea.append("  ");
				outputTextArea.append(rateStringArray[i][j] + "\n");
	
			}
			
		}
	
	}
	
	/**
	 * Gets the temp spaces.
	 *
	 * @param string the string
	 * @return the temp spaces
	 */
	private int getTempSpaces(String string){

		int numSpaces = 0;	
		
		if(string.indexOf(".")==0){
			numSpaces = 6;
		}else{
			numSpaces = 6 - string.indexOf(".");
		}

		return numSpaces;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		setOutputText(getRateTempPoints());
	}

}