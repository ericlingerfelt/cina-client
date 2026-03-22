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


//Class CinaIntroPanel creates the panel for the 
//cina class
/**
 * The Class RateGenIntroPanel.
 */
public class RateGenIntroPanel extends JPanel{

	/** The nuc data radio button. */
	private JRadioButton uploadRadioButton, nucDataRadioButton;

	//Constructor
	/**
	 * Instantiates a new rate gen intro panel.
	 */
	public RateGenIntroPanel(){

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel label1 = new JLabel("Rate");
		label1.setFont(Fonts.bigTitleFont);

		JLabel label2 = new JLabel("Generator");
		label2.setFont(Fonts.bigTitleFont);

		JLabel dummyLabel = new JLabel("<html>Welcome to the Rate Generator. " 
											+ "<p><br>This application will enable you to: "
  											+ "<p><br>- Calculate a Thermonuclear Reaction Rate as a function of <p>Temperature from an input file of Cross Section vs. Energy <p>or S-factor vs. Energy" 
  											+ "</html>");

  		JPanel titlePanel = new JPanel(new GridBagLayout());
		JLabel dataLabel = new JLabel("Please select a path below :");

		uploadRadioButton = new JRadioButton("Upload or paste a cross section or S factor", true);
		nucDataRadioButton = new JRadioButton("Choose a cross section or S factor file from a Nuclear Data Set", false);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(uploadRadioButton);
		buttonGroup.add(nucDataRadioButton); 

		if(Cina.cinaMainDataStructure.getUser().equals("guest")){

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			titlePanel.add(label1, gbc);
	
			gbc.gridy = 1;
			gbc.insets = new Insets(10, 0, 0, 0);
			titlePanel.add(label2, gbc);
			JPanel htmlPanel = new JPanel(new FlowLayout());
	
			gbc.gridx = 0;
			gbc.gridy = 0;
			htmlPanel.add(dummyLabel);
	
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.insets = new Insets(0, 0, 165, 0);
			add(titlePanel, gbc);
	
			gbc.gridx = 2;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(0, 20, 60, 0);
			gbc.anchor = GridBagConstraints.EAST;
			add(htmlPanel, gbc);
		
		}else{
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			titlePanel.add(label1, gbc);
	
			gbc.gridy = 1;
			gbc.insets = new Insets(10, 0, 0, 0);
			titlePanel.add(label2, gbc);
			JPanel htmlPanel = new JPanel(new GridBagLayout());
	
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			htmlPanel.add(dummyLabel, gbc);
	
			gbc.gridx = 0;
			gbc.gridy = 1;
			htmlPanel.add(dataLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			htmlPanel.add(uploadRadioButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			htmlPanel.add(nucDataRadioButton, gbc);
	
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.insets = new Insets(0, 0, 165, 0);
			add(titlePanel, gbc);
	
			gbc.gridx = 2;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(0, 60, 30, 0);
			gbc.anchor = GridBagConstraints.EAST;
			add(htmlPanel, gbc);
		
		}

	}
	
	/**
	 * Gets the current state.
	 *
	 * @param ds the ds
	 * @return the current state
	 */
	public void getCurrentState(RateGenDataStructure ds){
		ds.setUploadData(uploadRadioButton.isSelected());
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param ds the new current state
	 */
	public void setCurrentState(RateGenDataStructure ds){
	
		if(ds.getUploadData()){
		
			uploadRadioButton.setSelected(true);
			nucDataRadioButton.setSelected(false);
		
		}else{
		
			uploadRadioButton.setSelected(false);
			nucDataRadioButton.setSelected(true);
		
		}
	
	}

}