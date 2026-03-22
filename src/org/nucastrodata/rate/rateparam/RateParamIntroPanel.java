package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;


/**
 * The Class RateParamIntroPanel.
 */
public class RateParamIntroPanel extends JPanel{

	/** The nuc data radio button. */
	private JRadioButton uploadRadioButton, nucDataRadioButton;

	//Constructor
	/**
	 * Instantiates a new rate param intro panel.
	 */
	public RateParamIntroPanel(){

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel label1 = new JLabel("Rate");
		label1.setFont(Fonts.bigTitleFont);

		JLabel label2 = new JLabel("Parameterizer");
		label2.setFont(Fonts.bigTitleFont);

		JLabel dummyLabel = new JLabel("<html>Welcome to the Rate Parameterizer. " 
											+ "<p><br>This application will enable you to: "
  											+ "<p><br>- Determine rate parameters in a common format <p>(of the REACLIB rate library) from an input file of <p>Reaction Rate vs. Temperature"
  											+ "</html>");

  		JPanel titlePanel = new JPanel(new GridBagLayout());
		JLabel dataLabel = new JLabel("Please select a path below :");

		uploadRadioButton = new JRadioButton("Upload or paste a reaction rate", true);
		nucDataRadioButton = new JRadioButton("Choose a previously saved tabulated reaction rate", false);

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
	public void getCurrentState(RateParamDataStructure ds){
		ds.setUploadData(uploadRadioButton.isSelected());
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param ds the new current state
	 */
	public void setCurrentState(RateParamDataStructure ds){
	
		if(ds.getUploadData()){
		
			uploadRadioButton.setSelected(true);
			nucDataRadioButton.setSelected(false);
		
		}else{
		
			uploadRadioButton.setSelected(false);
			nucDataRadioButton.setSelected(true);
		
		}
	
	}

}