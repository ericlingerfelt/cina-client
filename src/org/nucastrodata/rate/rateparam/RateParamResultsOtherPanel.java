//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateparam.RateParamResultsInfoFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsInverseFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsNotesFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsOutputFrame;


//This class generates the content for the Rate Gen step 10 of 10
/**
 * The Class RateParamResultsOtherPanel.
 */
public class RateParamResultsOtherPanel extends WizardPanel implements ActionListener, ItemListener{
	
	//Declare buttons
	/** The notes button. */
	JButton paramListButton, outputButton, inverseParamButton, saveButton, okButton, cancelButton
				, yesButton, noButton, yesButton2, noButton2, notesButton;
	
	//Declare library combo box
	/** The lib combo box. */
	JComboBox libComboBox;
	
	//Declare library field
	/** The new lib field. */
	JTextField newLibField;
	
	//Declare scrollpane for parameter list
	/** The scrollpane. */
	ScrollPane scrollpane;
	
	//Declare dialogs
	/** The continue dialog. */
	JDialog saveRateDialog, cautionDialog, rateModifyDialog, rateMergeDialog
				, rateExistsDialog, continueDialog;
	
	//Declare more labels
	/** The top label. */
	JLabel newLibLabel, topLabel;

	/** The desc text area. */
	JTextArea descTextArea;

	/** The reaction label. */
	JLabel numParamLabel, tempminLabel, tempmaxLabel, maxLabel, chiLabel, reactionLabel; 

	/** The status radio button. */
	JRadioButton techRadioButton, startRadioButton, modifyRadioButton, otherRadioButton, statusRadioButton; 
	
	/** The default desc string. */
	final String defaultDescString = "Roll your mouse over a button to get a description of each tools capabilities.";
	
	/** The param list string. */
	final String paramListString = "- Create a report detailing this parameterization.";
	
	/** The output string. */
	final String outputString = "- Generate parameters in a variety of formats.";
	
	/** The inverse param string. */
	final String inverseParamString = "- View the inverse paremeters for this rate as calculated by detailed balance.";
	
	/** The notes string. */
	final String notesString = "- Edit your notes for this rate before saving."; 
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate param results other panel.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsOtherPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		//Set panel index to 10 
		Cina.rateParamFrame.setCurrentPanelIndex(12);

		addWizardPanel("Rate Parameterizer", "Save Rate and Other Options", "12", "12");

		//Instantiate gbc
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Create Buttons////////////////////////////////////////////////////BUTTONS///////////////////
		paramListButton = new JButton("Session Info");
		paramListButton.setFont(Fonts.buttonFont);
		paramListButton.addActionListener(this);
		paramListButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(paramListString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(paramListButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		outputButton = new JButton("Formatted Output");
		outputButton.setFont(Fonts.buttonFont);	
		outputButton.addActionListener(this);
		outputButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(outputString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(outputButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		inverseParamButton = new JButton("Inverse Parameters");
		inverseParamButton.setFont(Fonts.buttonFont);	
		inverseParamButton.addActionListener(this);
		inverseParamButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(inverseParamString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(inverseParamButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		saveButton = new JButton("Save Rate");
		saveButton.setFont(Fonts.buttonFont);	
		saveButton.addActionListener(this);
		
		notesButton = new JButton("Edit Notes");
		notesButton.setFont(Fonts.buttonFont);	
		notesButton.addActionListener(this);
		notesButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(notesString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(notesButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		//If user is not a guest enable save rate button
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			saveButton.setEnabled(true);
			notesButton.setEnabled(true);
		
		}else{
		
			saveButton.setEnabled(false);
			notesButton.setEnabled(false);
		
		}
		
		descTextArea = new JTextArea("");
		descTextArea.setFont(Fonts.textFontFixedWidth);
		descTextArea.setEditable(false);
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		
		JScrollPane sp = new JScrollPane(descTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(170, 140));
		
		reactionLabel = new JLabel();
		reactionLabel.setFont(Fonts.textFont);
		
		numParamLabel = new JLabel();
		numParamLabel.setFont(Fonts.textFont);
		
		tempminLabel = new JLabel();
		tempminLabel.setFont(Fonts.textFont);
		
		tempmaxLabel = new JLabel();
		tempmaxLabel.setFont(Fonts.textFont);
		
		maxLabel = new JLabel();
		maxLabel.setFont(Fonts.textFont);
		
		chiLabel = new JLabel();
		chiLabel.setFont(Fonts.textFont);
		
		JLabel topLabel2 = new JLabel("<html>Click <i>Save Rate</i> to save this rate in a previouly created<p>"
										+ "or new rate library. Place your mouse over each button to get a<p>"
										+ "description of the button's function.</html>");
		
		//Create labels////////////////////////////////////////////////////LABELS////////////////////
		JLabel summaryLabel = new JLabel("Parameter Summary");
		JLabel descLabel = new JLabel("Tool Description");

		//Create panels////////////////////////////////////////////////////PANELS///////////////////
		JPanel summaryPanel = new JPanel(new GridBagLayout());
		JPanel descPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		summaryPanel.add(summaryLabel, gbc);
		
		gbc.gridy = 1;
		summaryPanel.add(reactionLabel, gbc);
		
		gbc.gridy = 2;
		summaryPanel.add(numParamLabel, gbc);
		
		gbc.gridy = 3;
		summaryPanel.add(tempminLabel, gbc);
		
		gbc.gridy = 4;
		summaryPanel.add(tempmaxLabel, gbc);
		
		gbc.gridy = 5;
		summaryPanel.add(maxLabel, gbc);
		
		gbc.gridy = 6;
		summaryPanel.add(chiLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		descPanel.add(descLabel, gbc);

		gbc.gridy = 1;
		descPanel.add(sp, gbc);
		
		buttonPanel.add(paramListButton);
		buttonPanel.add(outputButton);
		buttonPanel.add(inverseParamButton);
		buttonPanel.add(notesButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(topLabel2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(summaryPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(descPanel, gbc);
				
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(buttonPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(saveButton, gbc);
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
	}
	
	/**
	 * Sets the button foregrounds.
	 *
	 * @param button the new button foregrounds
	 */
	public void setButtonForegrounds(JButton button){
	
		paramListButton.setForeground(Color.white);
		inverseParamButton.setForeground(Color.white);
		outputButton.setForeground(Color.white);
		notesButton.setForeground(Color.white);
		
		button.setForeground(Color.red);
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
	}
	
	//Method to set current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		descTextArea.setText(defaultDescString);
		
		String reactionName = ds.getRateDataStructure().getReactionString();
		
		if(!ds.getRateDataStructure().getDecay().equals("")){
			reactionName += " [" + ds.getRateDataStructure().getDecay() + "]";
		}
		
		reactionLabel.setText("Reaction: " + reactionName);
		numParamLabel.setText("Number of Parameters: " + ds.getRateDataStructure().getNumberParameters());
		tempminLabel.setText("Temp min (T9): " + ds.getTempminRT());
		tempmaxLabel.setText("Temp max (T9): " + ds.getTempmaxRT());
		maxLabel.setText("Max percent difference: " + ds.getRateDataStructure().getMaxPercentDiff());
		chiLabel.setText("Chi-squared: " + ds.getRateDataStructure().getChisquared());
		
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==outputButton){
			
			//Open formatted output interface
			if(Cina.rateParamFrame.rateParamResultsOutputFrame==null){
						
				Cina.rateParamFrame.rateParamResultsOutputFrame = new RateParamResultsOutputFrame(ds);
				
			}else{
				
				Cina.rateParamFrame.rateParamResultsOutputFrame.refreshData();
				Cina.rateParamFrame.rateParamResultsOutputFrame.setVisible(true);
			
			}
			
		//If source is results info button
		}else if(ae.getSource()==paramListButton){
			
			//Open results report interface
			if(Cina.rateParamFrame.rateParamResultsInfoFrame==null){
						
				Cina.rateParamFrame.rateParamResultsInfoFrame = new RateParamResultsInfoFrame(ds);
				
			}else{
				
				Cina.rateParamFrame.rateParamResultsInfoFrame.refreshData();
				Cina.rateParamFrame.rateParamResultsInfoFrame.setVisible(true);
			
			}
			
		//If source is inverse parameters button
		}else if(ae.getSource()==inverseParamButton){
			
			//If CGI call for inverse parameters is successful
			if(Cina.cinaCGIComm.doCGICall("INVERSE PARAMETERS", Cina.rateParamFrame)){
			
				//Open inverese parameters interface
				if(Cina.rateParamFrame.rateParamResultsInverseFrame==null){
							
					Cina.rateParamFrame.rateParamResultsInverseFrame = new RateParamResultsInverseFrame(ds);
					
				}else{
					
					Cina.rateParamFrame.rateParamResultsInverseFrame.refreshData();
					Cina.rateParamFrame.rateParamResultsInverseFrame.setVisible(true);
				
				}
			}

		}else if(ae.getSource()==notesButton){
			
			//Open evaluate rate interface
			if(Cina.rateParamFrame.rateParamResultsNotesFrame==null){
	
				Cina.rateParamFrame.rateParamResultsNotesFrame = new RateParamResultsNotesFrame(ds);
				Cina.rateParamFrame.rateParamResultsNotesFrame.setCurrentState();
				Cina.rateParamFrame.rateParamResultsNotesFrame.setVisible(true);
				
			}else{

				Cina.rateParamFrame.rateParamResultsNotesFrame.setCurrentState();
				Cina.rateParamFrame.rateParamResultsNotesFrame.setVisible(true);
				
			}
			
		}else if(ae.getSource()==saveButton){
			
			//If CGI call to get source libraries
			//Use this call to populate library combo box 
			//for save rate dialog
			ds.setLibGroup("PUBLIC");
			boolean goodPublicSourceLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			ds.setLibGroup("SHARED");
			boolean goodSharedSourceLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			ds.setLibGroup("USER");
			boolean goodUserSourceLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			if(goodPublicSourceLibraries && goodSharedSourceLibraries && goodUserSourceLibraries){
			
				//Create save rate dialog
				createSaveRateDialog(Cina.rateParamFrame);
			
			}
		
		//If source is cancel button of save rate dialog
		}else if(ae.getSource()==cancelButton){
			
			//Close save rate dialog 
			saveRateDialog.setVisible(false);
			saveRateDialog.dispose();
		
		//If source is ok button of save rate dialog
		}else if(ae.getSource()==okButton){
			
			//If there is at least one user library available to save this rate
			if(ds.getNumberUserLibraryDataStructures()!=0){
			
				//If library field is blank and the user has selected new library from the library combo box
				if(!checkNewLibField() && ((String)libComboBox.getSelectedItem()).equals("new library")){
				
					//Open attention dialog
					String string = "Please enter a name for the new library.";
					
					Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
				
				//If library field is NOT blank and the user has selected new library from the library combo box 
				}else if(((String)libComboBox.getSelectedItem()).equals("new library")){
					
					//Check library name to see if library chosen has the same name as a public library
					if(checkPublicLibName()){
					
						//Check to see if the library entered is the same as a current user library
						if(checkOverwriteLibName()){
						
							//Set CGI var for destination library
							ds.setDestLibName(newLibField.getText());
							
							//Set CGI vars for call
							ds.setSourceLib("");
							ds.setDestLibGroup("USER");
							ds.setProperties(getRateProperties());
							ds.setRates("");
							ds.setDeleteSourceLib("NO");
							ds.setMake_inverse("YES");
							
							ds.setCHK_TEMP_BEHAVIOR("NO");
							ds.setCHK_OVERFLOW("NO");
							ds.setCHK_INVERSE("NO");
							
							//If CGI call to create new library and place this rate in it
							//is successful
							if(Cina.cinaCGIComm.doCGICall("MODIFY RATE LIBRARY", Cina.rateParamFrame)
								&& Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateParamFrame)){
								
								//Close save rate dialog
								saveRateDialog.setVisible(false);
								saveRateDialog.dispose();
								
								//Set string for save rave report dialog
								String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport()
										+ "\n";
										//+ inverseString;
								
								//Create the save rate report dialog		
								createRateMergeDialog(string, Cina.rateParamFrame);
							
							}
						
						}else{
							
							//Create attention dialog	
							String string = "You have specified the name of an existing library. Do you want to overwrite this library?";
					
							createCautionDialog(string, Cina.rateParamFrame);
						
						}
						
					}else{
					
						//Create attention dialog
						String string = "You have specified the name of a Public or a Shared library. These can not overwritten.";
				
						Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
					
					}
				
				//If the user has not chosen a new library		
				}else if(!((String)libComboBox.getSelectedItem()).equals("new library")){
				
					//Set CGI var destination library to the one selected
					ds.setDestLibName((String)libComboBox.getSelectedItem());
			
					//Set CGI var to current rate id 
					ds.setRates(getCurrentRateID());
					
					//Make CGI call to see if this rate exists
					//CGI parses to var called currentRateExists
					//in Rate Gen Data Structure
					if(Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateParamFrame)){
					
						//If current rate does not exist
						if(!ds.getCurrentRateExists()){
	
							//Set CGI vars
							ds.setProperties(getRateProperties());
							ds.setRates("");
							ds.setMake_inverse("YES");
							
							ds.setCHK_TEMP_BEHAVIOR("NO");
							ds.setCHK_OVERFLOW("NO");
							ds.setCHK_INVERSE("NO");
	
							//Make CGI call to add this rate to the chosen user library
							if(Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateParamFrame)){
							
								//Close save rate dialog
								saveRateDialog.setVisible(false);
								saveRateDialog.dispose();
			
								//Create string for report
								String string = ds.getModifyRatesReport();
												/*+ "\n"
												+ inverseString;*/
								
								//Create report dialog				
								createRateModifyDialog(string, Cina.rateParamFrame);
							
							}
						
						//Tell user that this rate exists and give them the choice to overwrite	
						}else{
						
							String string = "This rate already exists in the library " + newLibField.getText() + ". Do you want to overwrite this rate?";
						
							createRateExistsDialog(string, Cina.rateParamFrame);		
										
						}
						
					}
				
				}
			
			//If there are no user libraries to write the rate
			}else if(ds.getNumberUserLibraryDataStructures()==0){
			
				//Check to see if library name field is empty
				if(!checkNewLibField()){
				
					String string = "Please enter a name for the new library.";
					
					Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
					
				//If not empty
				}else{
	
					//Set CGO var for destination library
					ds.setDestLibName(newLibField.getText());
	
					//Set CGI vars for call
					ds.setSourceLib("");
					ds.setDestLibGroup("USER");
					ds.setProperties(getRateProperties());
					ds.setRates("");
					ds.setDeleteSourceLib("NO");
					ds.setMake_inverse("YES");
					
					ds.setCHK_TEMP_BEHAVIOR("NO");
					ds.setCHK_OVERFLOW("NO");
					ds.setCHK_INVERSE("NO");
	
					//If library is created and rate added successfully
					if(Cina.cinaCGIComm.doCGICall("MODIFY RATE LIBRARY", Cina.rateParamFrame)
							&& Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateParamFrame)){
						
						//Close save rate dialog
						saveRateDialog.setVisible(false);
						saveRateDialog.dispose();
						
						//Create string for report
						String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport();
										/*+ "\n"
										+ inverseString;*/
						
						//Show report to user				
						createRateMergeDialog(string, Cina.rateParamFrame);
					
					}
				
				}
			
			}
		
		//If yes button is clicked to over write user library
		}else if(ae.getSource()==yesButton){
			
			//Set CGI var for destination library
			ds.setDestLibName(newLibField.getText());
			
			//Set CGI vars for call
			ds.setSourceLib("");
			ds.setDestLibGroup("USER");
			ds.setProperties(getRateProperties());
			ds.setRates("");
			ds.setDeleteSourceLib("NO");
			ds.setMake_inverse("YES");
			
			ds.setCHK_TEMP_BEHAVIOR("NO");
			ds.setCHK_OVERFLOW("NO");
			ds.setCHK_INVERSE("NO");
			
			//If library overwritten and rate added successfully		
			if(Cina.cinaCGIComm.doCGICall("MODIFY RATE LIBRARY", Cina.rateParamFrame)
					&& Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateParamFrame)){
			
				//Close save rate dialog
				saveRateDialog.setVisible(false);
				saveRateDialog.dispose();
				
				//Create string for report
				String string = ds.getModifyLibReport()
										+ "\n"
										+ ds.getModifyRatesReport();
										/*+ "\n"
										+ inverseString;*/
				
				//Show report to the user						
				createRateMergeDialog(string, Cina.rateParamFrame);
			
			}
		
			//Close overwrite lib caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		//If the iser decides not to overwrite lib
		}else if(ae.getSource()==noButton){
		
			//Close overwrite lib caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
		//If user decides to overwrite rate
		}else if(ae.getSource()==yesButton2){

			//Close overwrite rate caution dialog 
			rateExistsDialog.setVisible(false);
			rateExistsDialog.dispose();
			
			//Set CGI vars
			ds.setProperties(getRateProperties());
			ds.setRates("");
			ds.setMake_inverse("YES");
			
			ds.setCHK_TEMP_BEHAVIOR("NO");
			ds.setCHK_OVERFLOW("NO");
			ds.setCHK_INVERSE("NO");
			
			//If CGI call to overwrite existing rate with this new rate in the chosen library
			if(Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateParamFrame)){
				
				//Close save rate dialog		
				saveRateDialog.setVisible(false);
				saveRateDialog.dispose();

				//Create string for report
				String string = ds.getModifyRatesReport();
								/*+ "\n"
								+ inverseString;*/
				
				//Show report to user				
				createRateModifyDialog(string, Cina.rateParamFrame);
			
			}
		
		//If user decides NOT to overwrite rate	
		}else if(ae.getSource()==noButton2){
		
			//Close overwrite rate caution dialog 
			rateExistsDialog.setVisible(false);
			rateExistsDialog.dispose();
		
		}
		
	}
	
	//Method to check library field to see if it is blank
	/**
	 * Check new lib field.
	 *
	 * @return true, if successful
	 */
	public boolean checkNewLibField(){
	
		//Create local boolean var
		boolean goodName = false;
	
		//If field is NOT blank
		if(!newLibField.getText().equals("")){
		
			//Set boolean to true
			goodName = true;
		
		}
	
		//Return boolean
		return goodName;
	
	}
	
	//Method to check to see if entered library is a public or shared library
	/**
	 * Check public lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicLibName(){
	
		//Create local boolean
		boolean goodName = true;

		//Loop over public library structures
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			//If field name equals public lib name ignoring case
			if(newLibField.getText().equalsIgnoreCase(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
			
				//Set boolean to false
				goodName = false;
			
			}
		
		}
	
		//Loop over shared library structures
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			//If field name equals shared lib name ignoring case
			if(newLibField.getText().equalsIgnoreCase(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
			
				//Set boolean to false
				goodName = false;
			
			}
		
		}
	
		//Return boolean
		return goodName;
	
	}
	
	//Method to check if the user is about to overwrite a user library
	/**
	 * Check overwrite lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkOverwriteLibName(){
	
		//Create local boolean
		boolean goodName = true;
	
		//Loop over user libraries
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			//If name entered is the name of an existing user library
			if(newLibField.getText().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
			
				//Set boolean to false
				goodName = false;
			
			}
		
		}
		
		//Return boolean
		return goodName;
	
	}
	
	//Method to create string to set rate properties to send to CGI when saving rate
	/**
	 * Gets the rate properties.
	 *
	 * @return the rate properties
	 */
	public String getRateProperties(){
	
		//Create local string for properties
		String propertiesString = "";
		
		propertiesString += "Number of Parameters = " + String.valueOf(ds.getRateDataStructure().getNumberParameters()) + "\u0009";
		propertiesString += "Number of Products = " + String.valueOf(ds.getRateDataStructure().getNumberProducts()) + "\u0009";
		propertiesString += "Number of Reactants = " + String.valueOf(ds.getRateDataStructure().getNumberReactants()) + "\u0009";
		propertiesString += "Parameters = " + getParameters() + "\u0009";
		propertiesString += "Reaction Notes = " + ds.getRateDataStructure().getReactionNotes() + "\u0009";
		propertiesString += "Reaction String = " + ds.getRateDataStructure().getReactionString() + "\u0009";

		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getRateDataStructure().getReactionType());
		
		if(!ds.getRateDataStructure().getDecayType().equals("none")){
		
			reactionTypeString += "," + ds.getRateDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		
		propertiesString += "Resonant Components = " + getResonantInfo() + "\u0009";
		propertiesString += "Chisquared = " + String.valueOf(ds.getRateDataStructure().getChisquared()) + "\u0009";
		propertiesString += "Max Percent Difference = " + String.valueOf(ds.getRateDataStructure().getMaxPercentDiff()) + "\u0009";
		propertiesString += "Valid Temp Range = " + getTempRange() + "\u0009";
		propertiesString += "Q-value = " + "" + "\u0009";
		propertiesString += "Reference Citation = " + "" + "\u0009";
		propertiesString += "Biblio Code = " + getBiblioCode();
		
		return propertiesString;
	
	}
	
	//Method to get resonant info property
	/**
	 * Gets the resonant info.
	 *
	 * @return the resonant info
	 */
	public String getResonantInfo(){
	
		String string = "";
		
		for(int i=0; i<(int)(ds.getRateDataStructure().getNumberParameters()/7); i++){
		
			if(i==0){string = "nr";
			}else{string = string + ",r";}
		
		}
		
		return string;
	
	}
	
	//Method to get Valid Temp Range property
	/**
	 * Gets the temp range.
	 *
	 * @return the temp range
	 */
	public String getTempRange(){
	
		String string = "";
		
		string = string.valueOf(ds.getAllowedTempmin()) 
					+ ","
					+ string.valueOf(ds.getAllowedTempmax());
		
		return string;
	
	}
	
	//Method to get Biblio Code property
	/**
	 * Gets the biblio code.
	 *
	 * @return the biblio code
	 */
	public String getBiblioCode(){
		
		String string = "";
		
		if(Cina.cinaMainDataStructure.getUser().length()>3){
		
			string = Cina.cinaMainDataStructure.getUser().substring(0, 4);
		
		}else{
		
			string = Cina.cinaMainDataStructure.getUser();
		
		}
		
		return string;
		
	}
	
	//method to get Parameters property
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public String getParameters(){
	
		String string = "";
		
		for(int i=0; i<ds.getRateDataStructure().getNumberParameters(); i++){
		
			if(i!=0){
		
				string = string + "," + String.valueOf(ds.getRateDataStructure().getParameters()[i]);
			
			}else{
				
				string = String.valueOf(ds.getRateDataStructure().getParameters()[i]);
			
			}
		
		}
		
		return string;
	
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
					+ String.valueOf(ds.getRateDataStructure().getReactionType()) 
					+ getZAString()
					+ ds.getDestLibName()
					+ "\u0009"
					+ ds.getRateDataStructure().getReactionString()
					+ "\u000b";
		
		//If it is a decay			
		if(!ds.getRateDataStructure().getDecayType().equals("none")){
		
			//Add decay string to rate id
			string += ds.getRateDataStructure().getDecay();
		
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
		Point[] isoIn = new Point[ds.getRateDataStructure().getNumberReactants()]; 
		
		//Create a point to assign largest reactant
		Point biggestPoint = new Point(0, 0);
		
		//Loop over iso in
		for(int i=0; i<isoIn.length; i++){
		
			//Assign reactants to isoIn
			isoIn[i] = ds.getRateDataStructure().getIsoIn()[i];
		
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
	
	//Method for Item Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		//If source is the library combo box of the rate save dialog
		if(ie.getSource()==libComboBox){
		
			//If library is set to new library
			if(((String)libComboBox.getSelectedItem()).equals("new library")){
			
				//Set field editable
				newLibField.setEditable(true);
			
			//If library is NOT set to new library
			}else{
			
				//Set field uneditable
				newLibField.setEditable(false);	
			
			}
		
		}
	
	}
	
	//Method to create rate save report
	/**
	 * Creates the rate merge dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createRateMergeDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	rateMergeDialog = new JDialog(frame, "Rate saved", true);
    	rateMergeDialog.setSize(350, 210);
    	rateMergeDialog.getContentPane().setLayout(new GridBagLayout());
		rateMergeDialog.setLocationRelativeTo(frame);
		
		JTextArea rateMergeTextArea = new JTextArea("", 5, 30);
		rateMergeTextArea.setLineWrap(true);
		rateMergeTextArea.setWrapStyleWord(true);
		rateMergeTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateMergeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		rateMergeTextArea.setText(string);
		rateMergeTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					rateMergeDialog.setVisible(false);
					rateMergeDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateMergeDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		rateMergeDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(rateMergeDialog);
		
		//Show the dialog
		rateMergeDialog.setVisible(true);
	
	}
	
	//Method to create rate save report
	/**
	 * Creates the rate modify dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createRateModifyDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	rateModifyDialog = new JDialog(frame, "Rate saved", true);
    	rateModifyDialog.setSize(350, 210);
    	rateModifyDialog.getContentPane().setLayout(new GridBagLayout());
		rateModifyDialog.setLocationRelativeTo(frame);
		
		JTextArea rateModifyTextArea = new JTextArea("", 5, 30);
		rateModifyTextArea.setLineWrap(true);
		rateModifyTextArea.setWrapStyleWord(true);
		rateModifyTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateModifyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		rateModifyTextArea.setText(string);
		rateModifyTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					rateModifyDialog.setVisible(false);
					rateModifyDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateModifyDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		rateModifyDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(rateModifyDialog);
		
		//Show the dialog
		rateModifyDialog.setVisible(true);
	
	}
	
	//Method to create save rate dialog
	/**
	 * Creates the save rate dialog.
	 *
	 * @param frame the frame
	 */
	public void createSaveRateDialog(JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	saveRateDialog = new JDialog(frame, "Save rate to library...", true);
    	saveRateDialog.setSize(480, 170);
    	saveRateDialog.getContentPane().setLayout(new GridBagLayout());
		saveRateDialog.setLocationRelativeTo(frame);

		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		newLibField = new JTextField(15);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		if(ds.getNumberUserLibraryDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			
				libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
			
			}
		
			newLibField.setEditable(false);
		
			libComboBox.addItem("new library");
		
			libComboBox.addItemListener(this);
		
			topLabel = new JLabel("Select a User library or create a new library to save current rate.");
			topLabel.setFont(Fonts.textFont);
		
			newLibLabel = new JLabel("Enter the name of the new library: ");
			newLibLabel.setFont(Fonts.textFont);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveRateDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 2;
			saveRateDialog.getContentPane().add(libComboBox, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibField, gbc1);
		
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 3;
			gbc1.gridwidth = 2;
			saveRateDialog.getContentPane().add(buttonPanel, gbc1);
			
		}else{
		
			topLabel = new JLabel("There are no User libraries. Create a new library to save current rate.");
			topLabel.setFont(Fonts.textFont);
		
			newLibLabel = new JLabel("Enter the name of the new library: ");
			newLibLabel.setFont(Fonts.textFont);
		
			newLibField.setEditable(true);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveRateDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveRateDialog.getContentPane().add(newLibField, gbc1);	
			
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 2;
			saveRateDialog.getContentPane().add(buttonPanel, gbc1);
				
		}
		
		//Cina.setFrameColors(saveRateDialog);
		
		//Show the dialog
		saveRateDialog.setVisible(true);
	
	}
	
	//Method to create caution dialogs
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	cautionDialog = new JDialog(frame, "Caution!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setFont(Fonts.textFont);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;

		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);
		
		//Cina.setFrameColors(cautionDialog);
		
		cautionDialog.setVisible(true);
	
	}
	
	//Method to create rate exists caution dialog
	/**
	 * Creates the rate exists dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createRateExistsDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	rateExistsDialog = new JDialog(frame, "Caution!", true);
    	rateExistsDialog.setSize(320, 215);
    	rateExistsDialog.getContentPane().setLayout(new GridBagLayout());
		rateExistsDialog.setLocationRelativeTo(frame);
		
		JTextArea rateExistsTextArea = new JTextArea("", 5, 30);
		rateExistsTextArea.setLineWrap(true);
		rateExistsTextArea.setWrapStyleWord(true);
		//rateExistsTextArea.setFont(CinaFonts.textFont);
		rateExistsTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateExistsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String rateExistsString = string;

		rateExistsTextArea.setText(rateExistsString);
		
		rateExistsTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton2 = new JButton("Yes");
		yesButton2.setFont(Fonts.buttonFont);
		yesButton2.addActionListener(this);
		
		noButton2 = new JButton("No");
		noButton2.setFont(Fonts.buttonFont);
		noButton2.addActionListener(this);
		
		JPanel rateExistsButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateExistsButtonPanel.add(yesButton2, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		rateExistsButtonPanel.add(noButton2, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateExistsDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		rateExistsDialog.getContentPane().add(rateExistsButtonPanel, gbc1);
		
		//Cina.setFrameColors(rateExistsDialog);
		
		rateExistsDialog.setVisible(true);
	
	}
	
}