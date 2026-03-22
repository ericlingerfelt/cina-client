//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rategen.RateGenExtraFrame;


//This class creates the content for step 4 of 10 of the rate gen feature
/**
 * The Class RateGenInputWarningsPanel.
 */
public class RateGenInputWarningsPanel extends WizardPanel implements ActionListener{
	
	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The input warnings panel. */
	JPanel mainPanel, inputWarningsPanel; 
	
	//Create string array for labels
	/** The input warnings string array. */
	String[] inputWarningsStringArray = {"Reaction: "
										, "Data source: "
										, "Positive: "
										, "Single-valued: "
										, "Reasonable value ranges: "
										, "Continuity: "
										, "Unreasonable uncertainties: "
										, "Widest temperature range for rate generation (T9): "
										, "(as determined from Gamow Window calculation)"};
	
	//Create label array								
	/** The input warnings label array. */
	JLabel[] inputWarningsLabelArray = new JLabel[9];				
	
	//Declare label
	/** The extra label. */
	JLabel extraLabel;
	
	//Declare counter for label layout
	/** The counter. */
	int counter;
	
	//Declare vars for holding input filename and reaction name for presentation to the user
	/** The filename. */
	String filename;
	
	/** The reaction name. */
	String reactionName;
	
	//Create arrays to hold warnings and responses
	/** The input warnings array. */
	String[] inputWarningsArray = new String[5];
	
	/** The input warnings response array. */
	String[] inputWarningsResponseArray = new String[5];
	
	//Create vars to hold allowed temp min and max 
	/** The allowed tempmin. */
	double allowedTempmin = 0.0;
	
	/** The allowed tempmax. */
	double allowedTempmax = 0.0;
	
	/** The extra button. */
	JButton extraButton;
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate gen input warnings panel.
	 *
	 * @param ds the ds
	 */
	public RateGenInputWarningsPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		//Set current panel index to 4
		Cina.rateGenFrame.setCurrentPanelIndex(4);

		//Instantiate gbc
		gbc = new GridBagConstraints();
		
		//Loop over array
		for(int i=0; i<9; i++){
		
			//Create labels and set property
			inputWarningsLabelArray[i] = new JLabel();
			inputWarningsLabelArray[i].setFont(Fonts.textFont);
			
		}
		
		if(ds.getInputType().equals("CS(E)")){
		
			extraButton = new JButton("Cross Section Extrapolator");
			extraButton.setFont(Fonts.buttonFont);
			extraButton.addActionListener(this);
		
		}else if(ds.getInputType().equals("S(E)")){
		
			extraButton = new JButton("S Factor Extrapolator");
			extraButton.setFont(Fonts.buttonFont);
			extraButton.addActionListener(this);
			
		}

		if(ds.getInputType().equals("CS(E)")){

			extraLabel = new JLabel("<html>The widest temperature range for rate generation "
										+ "(as determined from a Gamow Window<p>calculation)"
										+ " has a minium greater than 0.01 T9 and/or a maximum less than 10.0 T9.<p>"
										+ " Click the Cross Section Extrapolator button to extend this range " 
										+ "or click Continue to go to Step 5.</html>");
										
		}else if(ds.getInputType().equals("S(E)")){
		
			extraLabel = new JLabel("<html>The widest temperature range for rate generation "
										+ "(as determined from a Gamow Window<p>calculation)"
										+ " has a minium greater than 0.01 T9 and/or a maximum less than 10.0 T9.<p>"
										+ " Click the S Factor Extrapolator button to extend this range " 
										+ "or click Continue to go to Step 5.</html>");
			
		}

		//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
		inputWarningsPanel = new JPanel(new GridBagLayout());
		
		mainPanel = new JPanel(new BorderLayout());
		
		addWizardPanel("Rate Generator", "Input Preprocessing Summary", "4", "7");
		
		//Add components to panels////////////////////////////////////////ADD//TO//PANELS/////////////
		add(mainPanel, BorderLayout.CENTER);
		
		setCurrentState();
		
		mainPanel.add(inputWarningsPanel, BorderLayout.CENTER);
		
		validate();
	}
	
	//method to set current state of this panel 
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		//Set reaction name and filename
		reactionName = ds.getRateDataStructure().getReactionString();
		
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			reactionName += " ["
							+ ds.getRateDataStructure().getDecay()
							+ "]";
		
		}
		
		filename = ds.getInputFilename();
		
		//Loop over tests
		for(int i=0; i<5; i++){
			inputWarningsArray[i] = ds.getCheckInputArray()[i];
		}
		
		//Loop over responses
		for(int i=0; i<5; i++){
			inputWarningsResponseArray[i] = ds.getInputWarningsResponseArray()[i];
		}
		
		//Set allowed temp min and max
		allowedTempmin = ds.getAllowedTempmin();
		allowedTempmax = ds.getAllowedTempmax();
		
		//Call set labels to set these changes to the panel
		setLabels();
	}
	
	//Method to get current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	//Method to set panel labels
	/**
	 * Sets the labels.
	 */
	public void setLabels(){
		
		//Remove all compoenents from panel
		inputWarningsPanel.removeAll();
		
		//If input data is cross section or s-factor
		if(allowedTempmin<=0.01
			&&  allowedTempmax>=10.0){
		
			//Initialize counter
			counter = 0;
		
		}else if(allowedTempmin>0.01
					||  allowedTempmax<10.0){
		
			//Initialize counter
			counter = 1;
			
			//Add rate label
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
		
			gbc.insets = new Insets(0, 0, 9, 0);
			
			inputWarningsPanel.add(extraLabel, gbc);
		
		}
		
		//Add the reaction name and filename labels
		gbc.gridx = 0;
		gbc.gridy = counter;
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.insets = new Insets(0, 0, 9, 0);
		
		inputWarningsLabelArray[0].setText(inputWarningsStringArray[0] + reactionName);
	
		inputWarningsLabelArray[1].setText(inputWarningsStringArray[1] + filename);
		
		counter++;
		
		gbc.gridx = 0;
		gbc.gridy = counter;
		gbc.anchor = GridBagConstraints.WEST;
		inputWarningsPanel.add(inputWarningsLabelArray[0], gbc);
		
		counter++;
		
		gbc.gridx = 0;
		gbc.gridy = counter;

		inputWarningsPanel.add(inputWarningsLabelArray[1], gbc);
		
		counter++;
		
		//If this test was scheduled
		if(inputWarningsArray[0].equals("Y")){
			
			//If test passed
			if(inputWarningsResponseArray[0].equals("PASSED")){
		 
		 		inputWarningsLabelArray[2].setText(inputWarningsStringArray[2] + "Passed");
		 	
		 	//If test failed
		 	}else{
		 		
		 		inputWarningsLabelArray[2].setText(inputWarningsStringArray[2] + "Failed");
		 		
		 	}	
		 	
		 	gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[2], gbc);	
		 		 
		 	counter++;
		
		//If this test was not scheduled	
		}else{
			
			//Test skipped
			inputWarningsLabelArray[2].setText(inputWarningsStringArray[2] + "Skipped");
			
			gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[2], gbc);	
		 		 
		 	counter++;
		
		}
		
		//If this test was scheduled
		if(inputWarningsArray[1].equals("Y")){
			
			//If test passed
			if(inputWarningsResponseArray[1].equals("PASSED")){
		 
		 		inputWarningsLabelArray[3].setText(inputWarningsStringArray[3] + "Passed");
		 	
		 	//If test failed
		 	}else{
		 		
		 		inputWarningsLabelArray[3].setText(inputWarningsStringArray[3] + "Failed");
		 		
		 	}
		 	
		 	gbc.gridx = 0;
			gbc.gridy = counter;	
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[3], gbc);
		 	
		 	counter++;	
		
		//If this test was not scheduled
		}else{
		
			//Test skipped
			inputWarningsLabelArray[3].setText(inputWarningsStringArray[3] + "Skipped");
		
			gbc.gridx = 0;
			gbc.gridy = counter;	
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[3], gbc);
		 	
		 	counter++;
		
		}
		
		//If this test was scheduled
		if(inputWarningsArray[2].equals("Y")){
			
			//If test passed			
			if(inputWarningsResponseArray[2].equals("PASSED")){
		 
		 		inputWarningsLabelArray[4].setText(inputWarningsStringArray[4] + "Passed");
		 	
		 	//If test failed
		 	}else{
		 		
		 		inputWarningsLabelArray[4].setText(inputWarningsStringArray[4] + "Failed");
		 		
		 	}	
		 	
		 	gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[4], gbc);
		 	
		 	counter++;	
		
		//If this test was not scheduled
		}else{
			
			//Test skipped
			inputWarningsLabelArray[4].setText(inputWarningsStringArray[4] + "Skipped");
		
			gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[4], gbc);
		 	
		 	counter++;
		
		}
		
		//If this test was scheduled
		if(inputWarningsArray[4].equals("Y")){
			
			//If test passed
			if(inputWarningsResponseArray[4].equals("PASSED")){
		 
		 		inputWarningsLabelArray[6].setText(inputWarningsStringArray[6] + "Passed");
		 	
		 	//If test failed
		 	}else{
		 		
		 		inputWarningsLabelArray[6].setText(inputWarningsStringArray[6] + "Failed");
		 		
		 	}	
		 	
		 	gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[6], gbc);
		 	
		 	counter++;
		
		//If this test was not scheduled
		}else{
			
			//Test skipped
			inputWarningsLabelArray[6].setText(inputWarningsStringArray[6] + "Skipped");
		
			gbc.gridx = 0;
			gbc.gridy = counter;
		 	
		 	inputWarningsPanel.add(inputWarningsLabelArray[6], gbc);
		 	
		 	counter++;
		
		}
		
		inputWarningsLabelArray[7].setText(inputWarningsStringArray[7] + " " 
											+ String.valueOf(allowedTempmin) + " - " 
											+ String.valueOf(allowedTempmax) + " ");
		
		inputWarningsLabelArray[8].setText(inputWarningsStringArray[8]);
			
		gbc.gridx = 0;
		gbc.gridy = counter;
		
		inputWarningsPanel.add(inputWarningsLabelArray[7], gbc);
		
		counter++;
		
		gbc.gridx = 0;
		gbc.gridy = counter;
		
		//Add final label
		inputWarningsPanel.add(inputWarningsLabelArray[8], gbc);
		
		counter++;
		
		gbc.gridx = 0;
		gbc.gridy = counter;
		
		if(allowedTempmin>0.01
					||  allowedTempmax<10.0){
			
			gbc.anchor = GridBagConstraints.CENTER;
						
			inputWarningsPanel.add(extraButton, gbc);
		
		}
		
		validate();
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==extraButton){
		
			if(Cina.rateGenFrame.rateGenExtraFrame==null){

				//Instantiate Lib Eval
				Cina.rateGenFrame.rateGenExtraFrame = new RateGenExtraFrame(ds);
				
				Cina.rateGenFrame.rateGenExtraFrame.setCurrentState();
				
				//Set Lib Eval properties
				Cina.rateGenFrame.rateGenExtraFrame.setResizable(true);
				Cina.rateGenFrame.rateGenExtraFrame.setSize(797, 664);
				Cina.rateGenFrame.rateGenExtraFrame.setVisible(true);
				
				//Set location slightly offset from cina
				Cina.rateGenFrame.rateGenExtraFrame.setLocation((int)(Cina.rateGenFrame.rateGenExtraFrame.getLocation().getX()) + 25
																				, (int)(Cina.rateGenFrame.rateGenExtraFrame.getLocation().getY()) + 25);
				
				if(ds.getInputType().equals("CS(E)")){
				
					Cina.rateGenFrame.rateGenExtraFrame.setTitle("Cross Section Extrapolator");
				
				}else if(ds.getInputType().equals("S(E)")){
				
					Cina.rateGenFrame.rateGenExtraFrame.setTitle("S Factor Extrapolator");
				
				}
			
			//If Lib Man has been created
			}else{
				
				//Set Lib Man visible
				Cina.rateGenFrame.rateGenExtraFrame.setCurrentState();
				
				Cina.rateGenFrame.rateGenExtraFrame.setVisible(true);

			}
		
		}
	
	}
	
}