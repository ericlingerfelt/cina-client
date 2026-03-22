package org.nucastrodata.eval.wizard.reactionselector;

import javax.swing.*;
import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.awt.*;
import java.awt.event.*;
import info.clearthought.layout.*;


/**
 * The Class ReactionSelectorZA.
 */
public class ReactionSelectorZA extends JPanel implements ActionListener{

	/** The type. */
	private RateDataStructure.ReactionType type;
	
	/** The decay. */
	private RateDataStructure.DecayType decay;
	
	/** The decay box. */
	private JComboBox typeBox, decayBox;
	
	/** The decay model. */
	private DefaultComboBoxModel typeModel, decayModel;
	
	//Declare reactionString text fields
    /** The reaction string in z field array. */
	private JTextField[] reactionStringInZFieldArray = new JTextField[3];
    
    /** The reaction string in a field array. */
    private JTextField[] reactionStringInAFieldArray = new JTextField[3];
    
    /** The reaction string out z field array. */
    private JTextField[] reactionStringOutZFieldArray = new JTextField[4];
    
    /** The reaction string out a field array. */
    private JTextField[] reactionStringOutAFieldArray = new JTextField[4];
    
    //Declare reactionString labels
    /** The Z label array. */
    private JLabel[] ZLabelArray = new JLabel[6];
    
    /** The A label array. */
    private JLabel[] ALabelArray = new JLabel[6];
    
    /** The plus label array. */
    private JLabel[] plusLabelArray = new JLabel[4];
    
    /** The reaction ui panel. */
    private JPanel reactionUIPanel = new JPanel(new GridBagLayout());
    
	/**
	 * Instantiates a new reaction selector za.
	 */
	public ReactionSelectorZA(){
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL, gap};
		setLayout(new TableLayout(column, row));
		
		JPanel middlePanel = new JPanel();
		double[] columnMiddle = {TableLayoutConstants.FILL
				, 10, TableLayoutConstants.FILL
				, 10, TableLayoutConstants.FILL
				, 10, TableLayoutConstants.FILL};
		double[] rowMiddle = {TableLayoutConstants.PREFERRED};
		middlePanel.setLayout(new TableLayout(columnMiddle, rowMiddle));
		
		JLabel topLabel = new JLabel("<html>Please choose a reaction type and decay type from the dropdown menus.<p>Then enter the element and mass "
												+ "number for each reactant and product<p>in the fields below.Click <i>Continue</i> when you are finished.</html>");
		JLabel typeLabel = new JLabel("Reaction type: ");
		typeLabel.setFont(Fonts.textFont);
		JLabel decayLabel = new JLabel("Decay type: ");
		decayLabel.setFont(Fonts.textFont);
		
		typeModel = new DefaultComboBoxModel(RateDataStructure.ReactionType.values());
		typeBox = new JComboBox(typeModel);
		typeBox.addActionListener(this);
		typeBox.setFont(Fonts.textFont);
		
		decayModel = new DefaultComboBoxModel(RateDataStructure.DecayType.values());
		decayBox = new JComboBox(decayModel);
		decayBox.setFont(Fonts.textFont);
	
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
		
    	setReactionStringUI(RateDataStructure.ReactionType.A_B);
    	
    	middlePanel.add(typeLabel, "0, 0, r, c");
    	middlePanel.add(typeBox, "2, 0, l, c");
    	middlePanel.add(decayLabel, "4, 0, r, c");
    	middlePanel.add(decayBox, "6, 0, l, c");
    	
    	add(topLabel, "0, 1, c, f");
    	add(middlePanel, "0, 3, f, f");
    	add(reactionUIPanel, "0, 5, f, f");
    	
    	
    	
	}
	
	//Method setCurremtState sets the state of each component
	//in the panel by accessing the RateGenDataStructure object
	/**
	 * Sets the current state.
	 *
	 * @param rds the new current state
	 */
	public void setCurrentState(RateDataStructure rds){
		
		//Set the reaction type drop down menu to the saved reaction type
		typeBox.setSelectedItem(rds.getReactionTypeEnum());
		
		//Call method setReactionStringUI to set up dynamic Z,A input fields
		setReactionStringUI(rds.getReactionTypeEnum());
		
		decayBox.setSelectedItem(rds.getDecayType());
		
			//Loop over reactant textfields
		for(int i=0; i<rds.getReactionTypeEnum().getNumberReactants(); i++){
			
			//Set the text of each text field to the value in the data structure
			reactionStringInZFieldArray[i].setText(String.valueOf((int)rds.getIsoIn()[i].getX()));           
			
			//This is important. DO NOT CHANGE!
			//When creating a dynamic set of fields
			//always set columns to one. Otherwise, is the value of Z or A is 
			//larger than two digits, the field containing it
			//will change size, thereby, destroying the appropriate
			//layout configuration.  
			reactionStringInZFieldArray[i].setColumns(3);
			reactionStringInAFieldArray[i].setText(String.valueOf((int)rds.getIsoIn()[i].getY()));
			reactionStringInAFieldArray[i].setColumns(3);
			
		}
		
		if(rds.getUseSecondProduct()){
		
			//Loop over product text Z,A fields
			for(int i=0; i<rds.getReactionTypeEnum().getNumberProducts(); i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)rds.getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)rds.getIsoOut()[i].getY())); 
				reactionStringOutAFieldArray[i].setColumns(3);
			
			}
		
		}else{
		
			//Loop over product text Z,A fields
			for(int i=0; i<1; i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)rds.getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)rds.getIsoOut()[i].getY())); 
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
	 * @param rds the rds
	 * @return the current state
	 */
	public void getCurrentState(RateDataStructure rds){
		
		//Create to Point arrays to hold Z,A pairs 
		//for raectants and products
		Point[] isoIn = new Point[3];
		Point[] isoOut = new Point[4];

		//Set the reaction type index in the data structure to the one on the screen
		rds.setReactionTypeEnum((RateDataStructure.ReactionType)typeBox.getSelectedItem());
		
		rds.setDecayEnum((RateDataStructure.DecayType)decayBox.getSelectedItem());

		//Loop over reactant textfields
		for(int i=0; i<rds.getReactionTypeEnum().getNumberReactants(); i++){
			isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
		}
		
		//Update the isoIn array of the data structure
		//with the local one  
		rds.setIsoIn(isoIn); 

		if(rds.getUseSecondProduct()){
		
			for(int i=0; i<rds.getReactionTypeEnum().getNumberProducts(); i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
		
			rds.setNumberReactants(rds.getReactionTypeEnum().getNumberReactants());
			rds.setNumberProducts(rds.getReactionTypeEnum().getNumberProducts());
	
		}else{
		
			for(int i=0; i<1; i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
			
			rds.setNumberReactants(rds.getReactionTypeEnum().getNumberReactants());
			rds.setNumberProducts(1);
			
		}
		
		//Update the isoOut array of the data structure
		//with the local one
		rds.setIsoOut(isoOut);
		
	}
	
	/**
	 * Check data fields.
	 *
	 * @param rds the rds
	 * @return true, if successful
	 */
	public boolean checkDataFields(RateDataStructure rds){

		try{
		
			if(typeBox.getSelectedIndex()!=7 
				|| (typeBox.getSelectedIndex()==7 
						&&(!reactionStringOutZFieldArray[1].getText().equals("") && !reactionStringOutAFieldArray[1].getText().equals("")))
				|| (typeBox.getSelectedIndex()==7 
						&&(reactionStringOutZFieldArray[1].getText().equals("0") && reactionStringOutAFieldArray[1].getText().equals("0")))){
		
				Point[] isoIn = new Point[3];
				Point[] isoOut = new Point[4];
				
				//Loop over reactant textfields
				for(int i=0; i<((RateDataStructure.ReactionType)typeBox.getSelectedItem()).getNumberReactants(); i++){
					isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
				}
	
				for(int i=0; i<((RateDataStructure.ReactionType)typeBox.getSelectedItem()).getNumberProducts(); i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
			
				rds.setUseSecondProduct(true);
					
			}else{
				
				Point[] isoIn = new Point[3];
				Point[] isoOut = new Point[4];
				
				//Loop over reactant textfields
				for(int i=0; i<((RateDataStructure.ReactionType)typeBox.getSelectedItem()).getNumberReactants(); i++){
					isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
				}
	
				for(int i=0; i<1; i++){
					isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
				}
				
				rds.setUseSecondProduct(false);
				
			}
		
		}catch(NumberFormatException nfe){
			return false;
		}
		
		return true;
	
	}
	
	/**
	 * Creates the cgi comm reaction string.
	 *
	 * @param rds the rds
	 * @return the string
	 */
	public String createCGICommReactionString(RateDataStructure rds){
		
		Point[] in = rds.getIsoIn();
		Point[] out = rds.getIsoOut();
		int numIn = rds.getNumberReactants();
		int numOut = rds.getNumberProducts();
		
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
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==typeBox){
			setReactionStringUI((RateDataStructure.ReactionType)typeModel.getSelectedItem());
		}
	}
	
	//Method setReactionStringUI sets up the dynamic input interface
    //for the (Z,A) pairs
    //Argument: reaction type
    /**
	 * Sets the reaction string ui.
	 *
	 * @param type the new reaction string ui
	 */
	private void setReactionStringUI(RateDataStructure.ReactionType type){
    	
    	JLabel plusParenLabel = new JLabel("(+");
    	plusParenLabel.setFont(Fonts.textFont);
    	
    	JLabel parenLabel = new JLabel(")");
    	parenLabel.setFont(Fonts.textFont);
    	
    	JLabel arrowLabel = new JLabel("-->");
    	arrowLabel.setFont(Fonts.textFont);
    	
    	//remove all components before assigning new ones
  		reactionUIPanel.removeAll();
  		reactionUIPanel.validate();
  		
    	GridBagConstraints gbc = new GridBagConstraints();
    	
    	//Switch on type
		switch(type.ordinal()){
		
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
    	
    	
    	reactionUIPanel.setVisible(false);
    	reactionUIPanel.setVisible(true);
    	
    }
	
}
