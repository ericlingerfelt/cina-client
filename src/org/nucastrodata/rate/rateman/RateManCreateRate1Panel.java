package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManCreateRate1Panel.
 */
public class RateManCreateRate1Panel extends WizardPanel implements ItemListener{

	/** The notes text area. */
	private JTextArea notesTextArea;
    
    /** The decay type combo box. */
    private JComboBox reactionTypeComboBox, reactionNameComboBox, decayTypeComboBox;
    
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
    
	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	private int[] numberReactants = {1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	private int[] numberProducts = {1, 2, 3, 1, 2, 3, 4, 2};
	
	/** The reaction ui panel. */
	private JPanel reactionUIPanel = new JPanel(new GridBagLayout());

	/** The ds. */
	private RateManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate man create rate1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManCreateRate1Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(2);
		Cina.rateManFrame.setCurrentPanelIndex(1);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Create New Rate", "1", "3");
		
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
		
		JLabel reactionTypeLabel = new JLabel("Reaction type: ");
		reactionTypeLabel.setFont(Fonts.textFont);
		JLabel decayTypeLabel = new JLabel("Decay type: ");
		decayTypeLabel.setFont(Fonts.textFont);
		JLabel reactionNameLabel = new JLabel("Please choose a reaction: ");
		reactionNameLabel.setFont(Fonts.textFont);
		JLabel notesLabel = new JLabel("Notes: ");
		notesLabel.setFont(Fonts.textFont);
		JLabel topLabel = new JLabel("<html>Welcome to the Create New Rate tool.<p><br>With this tool you can create and save a new rate. "
								+ "In Step 1, choose a reaction<p>type from the dropdown menu, then enter the element and mass "
								+ "number for each <p>reactant and product in the fields below. Add notes for this reaction in "
								+ "the <p><i>Notes</i> field. In Step 2, enter additional rate information. In Step 3, input or upload "
								+ "rate <p>parameters, save the rate to an existing library or "
								+ "a new library, and view results of<p>the rate creation.</html>");
	    	
		//Instantiate text area//////////////////////////////////////////TEXT//AREA////////////////////
		notesTextArea = new JTextArea("", 3, 60);
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(400, 65));
	
		JPanel notesPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		notesPanel.add(notesLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		notesPanel.add(sp, gbc);
		
		JPanel comboBoxPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0; 
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
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
		gbc.gridx = 0; 
		gbc.gridy = 3;
		mainPanel.add(notesPanel, gbc);
		add(mainPanel, BorderLayout.CENTER);
		validate();
		
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
		reactionTypeComboBox.setSelectedIndex(ds.getCreateRateDataStructure().getReactionType()-1);
		
		//Call method setReactionStringUI to set up dynamic Z,A input fields
		setReactionStringUI(ds.getCreateRateDataStructure().getReactionType()-1);
		
		decayTypeComboBox.setSelectedItem(ds.getCreateRateDataStructure().getDecayType());
		
			//Loop over reactant textfields
		for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
			
			//Set the text of each text field to the value in the data structure
			reactionStringInZFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoIn()[i].getX()));           
			
			//This is important. DO NOT CHANGE!
			//When creating a dynamic set of fields
			//always set columns to one. Otherwise, is the value of Z or A is 
			//larger than two digits, the field containing it
			//will change size, thereby, destroying the appropriate
			//layout configuration.  
			reactionStringInZFieldArray[i].setColumns(3);
			reactionStringInAFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoIn()[i].getY()));
			reactionStringInAFieldArray[i].setColumns(3);
			
		}
		
		if(ds.getUseSecondProduct()){
		
			//Loop over product text Z,A fields
			for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoOut()[i].getY())); 
				reactionStringOutAFieldArray[i].setColumns(3);
			
			}
		
		}else{
		
			//Loop over product text Z,A fields
			for(int i=0; i<1; i++){
					
				reactionStringOutZFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoOut()[i].getX()));           
				reactionStringOutZFieldArray[i].setColumns(3);
				reactionStringOutAFieldArray[i].setText(String.valueOf((int)ds.getCreateRateDataStructure().getIsoOut()[i].getY())); 
				reactionStringOutAFieldArray[i].setColumns(3);
			
			}
		
		}
		
		//Set the textof the Notes area
		notesTextArea.setText(ds.getCreateRateDataStructure().getReactionNotes());
		
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
		ds.getCreateRateDataStructure().setReactionType(reactionTypeComboBox.getSelectedIndex()+1);
		
		ds.getCreateRateDataStructure().setDecayType((String)decayTypeComboBox.getSelectedItem());

		//Loop over reactant textfields
		for(int i=0; i<numberReactants[reactionTypeComboBox.getSelectedIndex()]; i++){
			isoIn[i] = new Point(Integer.valueOf(reactionStringInZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringInAFieldArray[i].getText()).intValue());
		}
		
		//Update the isoIn array of the data structure
		//with the local one  
		ds.getCreateRateDataStructure().setIsoIn(isoIn); 

		if(ds.getUseSecondProduct()){
		
			for(int i=0; i<numberProducts[reactionTypeComboBox.getSelectedIndex()]; i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
		
			ds.getCreateRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
			ds.getCreateRateDataStructure().setNumberProducts(numberProducts[reactionTypeComboBox.getSelectedIndex()]);
	
		}else{
		
			for(int i=0; i<1; i++){
				isoOut[i] = new Point(Integer.valueOf(reactionStringOutZFieldArray[i].getText()).intValue(), Integer.valueOf(reactionStringOutAFieldArray[i].getText()).intValue());
			}
			
			ds.getCreateRateDataStructure().setNumberReactants(numberReactants[reactionTypeComboBox.getSelectedIndex()]);
			ds.getCreateRateDataStructure().setNumberProducts(1);
			
		}
		
		//Update the isoOut array of the data structure
		//with the local one
		ds.getCreateRateDataStructure().setIsoOut(isoOut);
		
		//Update the data structure with the latest notes
		ds.getCreateRateDataStructure().setReactionNotes(notesTextArea.getText());
		
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
    	
    	JLabel plusParenLabel = new JLabel("(+");
    	plusParenLabel.setFont(Fonts.textFont);
    	
    	JLabel parenLabel = new JLabel(")");
    	parenLabel.setFont(Fonts.textFont);
    	
    	JLabel arrowLabel = new JLabel("-->");
    	arrowLabel.setFont(Fonts.textFont);
    	
    	//remove all components before assigning new ones
  		reactionUIPanel.removeAll();
    	
    	GridBagConstraints gbc = new GridBagConstraints();
    	
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
    	
    	
    	
    	reactionUIPanel.setVisible(false);
    	reactionUIPanel.setVisible(true);
    }
	
}