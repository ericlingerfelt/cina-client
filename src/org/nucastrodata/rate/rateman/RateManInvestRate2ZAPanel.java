package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManInvestRate2ZAPanel.
 */
public class RateManInvestRate2ZAPanel extends WizardPanel implements ItemListener{

	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The combo box panel. */
	JPanel mainPanel, reactionUIPanel, reactionTypeComboBoxPanel, comboBoxPanel; 

	//Declare reaction ytpe drop down menu
    /** The decay type combo box. */
	JComboBox reactionTypeComboBox, reactionNameComboBox, decayTypeComboBox;
    
    //Declare reactionString text fields
    /** The reaction string in z field array. */
    JTextField[] reactionStringInZFieldArray = new JTextField[3];
    
    /** The reaction string in a field array. */
    JTextField[] reactionStringInAFieldArray = new JTextField[3];
    
    /** The reaction string out z field array. */
    JTextField[] reactionStringOutZFieldArray = new JTextField[4];
    
    /** The reaction string out a field array. */
    JTextField[] reactionStringOutAFieldArray = new JTextField[4];
    
    //Declare reactionString labels
    /** The Z label array. */
    JLabel[] ZLabelArray = new JLabel[6];
    
    /** The A label array. */
    JLabel[] ALabelArray = new JLabel[6];
    
    /** The plus label array. */
    JLabel[] plusLabelArray = new JLabel[4];

	/** The decay type label. */
	JLabel arrowLabel, plusParenLabel, parenLabel, reactionTypeLabel, reactionNameLabel, topLabel, decayTypeLabel;

	//Array holding total number of particles vs. reaction type
	/** The number particles array. */
	int[] numberParticlesArray = {2, 3, 4, 3, 4, 5, 6, 5};
	
	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {1, 2, 3, 1, 2, 3, 4, 2};

	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man invest rate2 za panel.
	 *
	 * @param ds the ds
	 */
	public RateManInvestRate2ZAPanel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(6);
		Cina.rateManFrame.setCurrentPanelIndex(2);

		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Rate Locator", "2", "3");
		
		//Instantiate and set up the reaction type choice box//////////////CHOICE//BOX//////////////////
		reactionTypeComboBox = new JComboBox();
    	reactionTypeComboBox.addItem("a-->b");
    	reactionTypeComboBox.addItem("a-->b+c");
    	reactionTypeComboBox.addItem("a-->b+c+d");
    	reactionTypeComboBox.addItem("a+b-->c");
    	reactionTypeComboBox.addItem("a+b-->c+d");
    	reactionTypeComboBox.addItem("a+b-->c+d+e");
    	reactionTypeComboBox.addItem("a+b-->c+d+e+f");
    	reactionTypeComboBox.addItem("a+b+c-->d(+e)");
			
		reactionTypeComboBox.setEnabled(true);
		reactionTypeComboBox.addItemListener(this);
		reactionTypeComboBox.setFont(Fonts.textFont);
		
		decayTypeComboBox = new JComboBox();
		decayTypeComboBox.addItem("none");
		decayTypeComboBox.addItem("electron capture");
		decayTypeComboBox.addItem("beta+");
		decayTypeComboBox.addItem("beta-");
		decayTypeComboBox.setFont(Fonts.textFont);
		
		for(int i=0; i<3; i++){
    		reactionStringInZFieldArray[i] = new JTextField(3);
    		reactionStringInZFieldArray[i].setFont(Fonts.textFont);
    		reactionStringInZFieldArray[i].setEditable(true);
    		
    		reactionStringInAFieldArray[i] = new JTextField(3);
    		reactionStringInAFieldArray[i].setFont(Fonts.textFont);
    		reactionStringInAFieldArray[i].setEditable(true);
    	}
    	
    	for(int i=0; i<4; i++){
    		reactionStringOutZFieldArray[i] = new JTextField(3);
    		reactionStringOutZFieldArray[i].setFont(Fonts.textFont);
    		reactionStringOutZFieldArray[i].setEditable(true);
    		
    		reactionStringOutAFieldArray[i] = new JTextField(3);
    		reactionStringOutAFieldArray[i].setFont(Fonts.textFont);
    		reactionStringOutAFieldArray[i].setEditable(true);
    	}

		//Instantiate labels//////////////////////////////////////////LABELS////////////////////////////
		for(int i=0; i<6; i++){
    		ZLabelArray[i] = new JLabel("Z");
    		ZLabelArray[i].setFont(Fonts.textFont);
    		
    		ALabelArray[i] = new JLabel("A");
    		ALabelArray[i].setFont(Fonts.textFont);
    	}
    	
    	for(int i=0; i<4; i++){
    		plusLabelArray[i] = new JLabel("+");
    		plusLabelArray[i].setFont(Fonts.textFont);
    	
    	}
		
		plusParenLabel = new JLabel("(+");
    	plusParenLabel.setFont(Fonts.textFont);
    	
    	parenLabel = new JLabel(")");
    	parenLabel.setFont(Fonts.textFont);
    	
    	arrowLabel = new JLabel("-->");
    	arrowLabel.setFont(Fonts.textFont);
		
		reactionTypeLabel = new JLabel("Reaction type: ");
		reactionTypeLabel.setFont(Fonts.textFont);
		
		decayTypeLabel = new JLabel("Decay type: ");
		decayTypeLabel.setFont(Fonts.textFont);
		
		reactionNameLabel = new JLabel("Please choose a reaction: ");
		reactionNameLabel.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>Select reaction type and decay type from the dropdown menus below.<p>Then enter the Z and A values for the reaction of interest in the text fields.</html>");
		
		reactionUIPanel = new JPanel(new GridBagLayout());
		
		comboBoxPanel = new JPanel(new GridBagLayout());
			
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(15, 5, 15, 5);
		gbc.anchor = GridBagConstraints.EAST;
		comboBoxPanel.add(reactionTypeLabel, gbc);
		
		gbc.gridx = 1; 
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		comboBoxPanel.add(reactionTypeComboBox, gbc);
		
		gbc.gridx = 2; 
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		comboBoxPanel.add(decayTypeLabel, gbc);
		
		gbc.gridx = 3; 
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		comboBoxPanel.add(decayTypeComboBox, gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 7, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		mainPanel.add(comboBoxPanel, gbc);
		
		gbc.gridx = 0; 
		gbc.gridy = 2;
		
		mainPanel.add(reactionUIPanel, gbc);

		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	//Method to get the rateID for current reaction
	/**
	 * Gets the current rate id.
	 *
	 * @return the current rate id
	 */
	public String getCurrentRateID(){
	
		//Create local string
		String string = "";
		
		//Construct string for rate ID
		string = "0"
					+ String.valueOf(ds.getInvestRateDataStructure().getReactionType()) 
					+ getZAString()
					+ "ReaclibV2.2"
					+ "\u0009"
					+ ds.getInvestRateDataStructure().getReactionString()
					+ "\u000b";
		
		//If it is a decay			
		if(!ds.getInvestRateDataStructure().getDecayType().equals("none")){
		
			//Add decay string to rate id
			string += ds.getInvestRateDataStructure().getDecay();
		
		}
		
		//Return string		
		return string;
	
	}
	
	//Method to construct part of the rate id identifying heaviest reactant
	/**
	 * Gets the zA string.
	 *
	 * @return the zA string
	 */
	public String getZAString(){
	
		//Create local strings
		String string = "";
		String ZString = "";
		String AString = "";
	
		//Make a point array to contain all reactants for this reaction
		Point[] isoIn = new Point[numberReactants[ds.getInvestRateDataStructure().getReactionType()]]; 
		
		//Create a point to assign largest reactant
		Point biggestPoint = new Point(0, 0);
		
		//Loop over iso in
		for(int i=0; i<isoIn.length; i++){
		
			//Assign reactants to isoIn
			isoIn[i] = ds.getInvestRateDataStructure().getIsoIn()[i];
		
		}
		
		//Loop over reactants to find largest reactant
		for(int i=0; i<isoIn.length; i++){
			
			//If this Z is less than current Z
			if(biggestPoint.getX()<isoIn[i].getX()){
			
				//This is the biggest point
				biggestPoint = isoIn[i];
			
			//If this Z is equal to current Z
			}else if(biggestPoint.getX()==isoIn[i].getX()){
			
				//If this A is less than current A
				if(biggestPoint.getY()<isoIn[i].getY()){
				
					//this is the biggest point
					biggestPoint = isoIn[i];
				
				}
			
			}
			
		}
	
		//Create strings for Z and A
		//They require leading zeroes for CGI
		ZString = String.valueOf((int)(biggestPoint.getX()));
		AString = String.valueOf((int)(biggestPoint.getY()));
		
		//Add leading zeroes
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
		
		//Put string together
		string = ZString + AString;
		
		//Return string
		return string;
	
	}
	
	/**
	 * Gets the good rate id list.
	 *
	 * @return the good rate id list
	 */
	public String getGoodRateIDList(){
	
		String string = "";
		
		Vector rateIDVector = new Vector();
		
		String[] tempArray = ds.getInvestRateIDs();
		
		for(int i=0; i<tempArray.length; i++){
		
			StringTokenizer st09 = new StringTokenizer(tempArray[i], "\u0009");
		
			String firstPart = st09.nextToken();
			String secondPart = st09.nextToken();
		
			if(st09.nextToken().equals("EXIST=YES")){
			
				rateIDVector.addElement(firstPart + "\u0009" + secondPart);	
			
			}
		
		}

		rateIDVector.trimToSize();

		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i==0){
			
				string += (String)rateIDVector.elementAt(i);
			
			}else{
				
				string += "\n" + (String)rateIDVector.elementAt(i);
			
			}
		
		}

		RateDataStructure[] apprdsa = new RateDataStructure[rateIDVector.size()];

		for(int i=0; i<rateIDVector.size(); i++){
		
			apprdsa[i] = new RateDataStructure();
			apprdsa[i].setReactionID((String)rateIDVector.elementAt(i));
		
		}

		ds.setInvestRateDataStructureArray(apprdsa);

		return string;
	
	}
	
	/**
	 * Gets the rate id list.
	 *
	 * @return the rate id list
	 */
	public String getRateIDList(){
	
		ds.getInvestRateDataStructure().setReactionID(getCurrentRateID());
	
		String string = "";
	
		String currentRateID = ds.getInvestRateDataStructure().getReactionID();
		
		String firstPart = currentRateID.substring(0,8);

		String secondPart = currentRateID.substring(currentRateID.indexOf("\u0009"));

		String[] libraryNameArray = new String[ds.getNumberPublicLibraryDataStructures()
												+ ds.getNumberSharedLibraryDataStructures()
												+ ds.getNumberUserLibraryDataStructures()];
		int counter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getPublicLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}	
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getSharedLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}									
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
			libraryNameArray[counter] = ds.getUserLibraryDataStructureArray()[i].getLibName();
			
			counter++;
		
		}
		
		for(int i=0; i<libraryNameArray.length; i++){
			
			if(i==0){
			
				string += firstPart + libraryNameArray[i] + secondPart;
			
			}else{
			
				string += "\n" + firstPart + libraryNameArray[i] + secondPart;
			
			}
			
		}
	
		return string;
		
	}
	
	/**
	 * Creates the cgi comm reaction string.
	 *
	 * @param in the in
	 * @param out the out
	 * @param numIn the num in
	 * @param numOut the num out
	 * @return the string
	 */
	public String createCGICommReactionString(Point[] in, Point[] out, int numIn, int numOut){
	
		String string = "";

		for(int i=0; i<numIn; i++){
		
			if(i!=(numIn-1)){
			
				string = string + String.valueOf((int)in[i].getX()) + "," 
							+ String.valueOf((int)in[i].getY()) + "+";
			
			}else{
			
				string = string + String.valueOf((int)in[i].getX()) + "," 
							+ String.valueOf((int)in[i].getY()) + "-->";
			
			}
		
		}
		
		for(int i=0; i<numOut; i++){
		
			if(i!=(numOut-1)){
			
				string = string + String.valueOf((int)out[i].getX()) + "," 
							+ String.valueOf((int)out[i].getY()) + "+";
			
			}else{
			
				string = string + String.valueOf((int)out[i].getX()) + "," 
							+ String.valueOf((int)out[i].getY());
			
			}
		
		}

		return string;
		
	}
	
	//Method setCurremtState sets the state of each component
	//in the panel by accessing the RateGenDataStructure object
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		//Set the reaction type drop down menu to the saved reaction type
		reactionTypeComboBox.setSelectedIndex(ds.getInvestRateDataStructure().getReactionType()-1);
		
		//Call method setReactionStringUI to set up dynamic Z,A input fields
		setReactionStringUI(ds.getInvestRateDataStructure().getReactionType()-1);
		
		decayTypeComboBox.setSelectedItem(ds.getInvestRateDataStructure().getDecayType());

		//Loop over reactant textfields
		for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
			
			//Set the text of each text field to the value in the data structure
			reactionStringInZFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoIn()[i].getX()));           
			
			//This is important. DO NOT CHANGE!
			//When creating a dynamic set of fields
			//always set columns to one. Otherwise, is the value of Z or A is 
			//larger than two digits, the field containing it
			//will change size, thereby, destroying the appropriate
			//layout configuration.  
			reactionStringInZFieldArray[i].setColumns(3);
			reactionStringInAFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoIn()[i].getY()));
			reactionStringInAFieldArray[i].setColumns(3);
			
		}
		
		if(ds.getUseSecondProduct()){
		
			//Loop over product text Z,A fields
			for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoOut()[i].getY())); 
				reactionStringOutAFieldArray[i].setColumns(3);
			
			}
		
		}else{
		
			//Loop over product text Z,A fields
			for(int i=0; i<1; i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getInvestRateDataStructure().getIsoOut()[i].getY())); 
				reactionStringOutAFieldArray[i].setColumns(3);
			
			}
		
		}

		validate();
		
	}
	
	//Method getCurrentState updates the current data structure
	//with the information currently showing in the JPanel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		//Create to Point arrays to hold Z,A pairs 
		//for raectants and products
		Point[] isoIn = new Point[3];
		Point[] isoOut = new Point[4];

		//Set the reaction type index in the data structure to the one on the screen
		ds.getInvestRateDataStructure().setReactionType(reactionTypeComboBox.getSelectedIndex()+1);
		
		ds.getInvestRateDataStructure().setDecayType((String)decayTypeComboBox.getSelectedItem());

		//Loop over reactant textfields
		for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
			isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
		}
		
		//Update the isoIn array of the data structure
		//with the local one  
		ds.getInvestRateDataStructure().setIsoIn(isoIn); 

		if(ds.getUseSecondProduct()){
		
			for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
		
			ds.getInvestRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
			ds.getInvestRateDataStructure().setNumberProducts(numberProducts[reactionTypeComboBox.getSelectedIndex()]);
	
		}else{
		
			for(int i=0; i<1; i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
			
			ds.getInvestRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
			ds.getInvestRateDataStructure().setNumberProducts(1);
			
		}
		
		//Update the isoOut array of the data structure
		//with the local one
		ds.getInvestRateDataStructure().setIsoOut(isoOut);
		
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){

		boolean pass = true;
	
		try{
		
			if(reactionTypeComboBox.getSelectedIndex()!=7 
				|| (reactionTypeComboBox.getSelectedIndex()==7 
						&&(!reactionStringOutZFieldArray[1].getText().equals("") && !reactionStringOutAFieldArray[1].getText().equals("")))
				|| (reactionTypeComboBox.getSelectedIndex()==7 
						&&(reactionStringOutZFieldArray[1].getText().equals("0") && reactionStringOutAFieldArray[1].getText().equals("0")))){
		
				Point[] isoIn = new Point[3];
				Point[] isoOut = new Point[4];
				
				//Loop over reactant textfields
				for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
					isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
				}
	
				for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
			
				ds.setUseSecondProduct(true);
					
			}else{
				
				Point[] isoIn = new Point[3];
				Point[] isoOut = new Point[4];
				
				//Loop over reactant textfields
				for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
					isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
				}
	
				for(int i=0; i<1; i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
				
				ds.setUseSecondProduct(false);
				
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Z and A entries must be integer values.";
			
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			
			pass = false;
		
		}
		
		return pass;
	
	}
	
	//Method itemStateChanged for the reaction type drop down menu
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		if(ie.getSource()==reactionTypeComboBox){
		
			setReactionStringUI(reactionTypeComboBox.getSelectedIndex());
		
		}
		
		validate();
		
	}
	
	//Method setReactionStringUI sets up the dynamic input interface
    //for the (Z,A) pairs
    //Argument: reaction type
    /**
	 * Sets the reaction string ui.
	 *
	 * @param type the new reaction string ui
	 */
	public void setReactionStringUI(int type){
    	
    	//remove all components before assigning new ones
  		reactionUIPanel.removeAll();
    	
    	//Switch on type
		switch(type){
		
			case 0:
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
				
				break;
		
			case 1:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				break;
				
			case 2:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);	
				
				break;
		
			case 3:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				break;
				
			case 4:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);

				break;
		
			case 5:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[2], gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
	
				break;
				
			case 6:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[2], gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[2], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[2], gbc);
    			
    			gbc.gridx = 14;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[3], gbc);
    			
    			gbc.gridx = 15;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[3], gbc);
    			
    			gbc.gridx = 16;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[3], gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
				
				gbc.gridx = 15;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[5], gbc);
				
				gbc.gridx = 16;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[5], gbc);

				break;
		
			case 7:
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(0, 0, 2, 0);
				reactionUIPanel.add(reactionStringInZFieldArray[0], gbc);
				
				gbc.gridx = 1;
    			gbc.gridy = 0;
				
				reactionUIPanel.add(reactionStringInAFieldArray[0], gbc);
				
				gbc.gridx = 2;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[0], gbc);
    			
    			gbc.gridx = 3;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[1], gbc);
    			
    			gbc.gridx = 4;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[1], gbc);
    			
    			gbc.gridx = 5;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusLabelArray[1], gbc);
    			
    			gbc.gridx = 6;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringInZFieldArray[2], gbc);
    			
    			gbc.gridx = 7;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringInAFieldArray[2], gbc);
    			
    			gbc.gridx = 8;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(arrowLabel, gbc);
    			
    			gbc.gridx = 9;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[0], gbc);
    			
    			gbc.gridx = 10;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[0], gbc);
    			
    			gbc.gridx = 11;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(plusParenLabel, gbc);
    			
    			gbc.gridx = 12;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.WEST;
    			gbc.insets = new Insets(0, 0, 2, 0);
    			reactionUIPanel.add(reactionStringOutZFieldArray[1], gbc);
    			
    			gbc.gridx = 13;
    			gbc.gridy = 0;
    			
    			reactionUIPanel.add(reactionStringOutAFieldArray[1], gbc);
    			
    			gbc.gridx = 14;
    			gbc.gridy = 0;
    			gbc.anchor = GridBagConstraints.CENTER;
    			gbc.insets = new Insets(0, 0, 0, 0);
    			reactionUIPanel.add(parenLabel, gbc);
    			
    			gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.insets = new Insets(0, 0, 0, 0);
				reactionUIPanel.add(ZLabelArray[0], gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[0], gbc);
				
				gbc.gridx = 3;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[1], gbc);
				
				gbc.gridx = 4;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[1], gbc);
					
				gbc.gridx = 6;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[2], gbc);
				
				gbc.gridx = 7;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[2], gbc);	
				
				gbc.gridx = 9;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[3], gbc);
				
				gbc.gridx = 10;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[3], gbc);
				
				gbc.gridx = 12;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ZLabelArray[4], gbc);
				
				gbc.gridx = 13;
				gbc.gridy = 1;
				
				reactionUIPanel.add(ALabelArray[4], gbc);
				
				break;
				
		}
		
		
		//Reset gbc variables for other components
		gbc.anchor = GridBagConstraints.WEST;
    	gbc.insets = new Insets(0, 0, 0, 0);
    	
    	//Call validate method to layout components
    	//reactionUIPanel.invalidate();
    	
    	
    	
    	reactionUIPanel.setVisible(false);
    	reactionUIPanel.setVisible(true);
    }
	
}