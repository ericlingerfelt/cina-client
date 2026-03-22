package org.nucastrodata.rate.rategen;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;
import org.nucastrodata.rate.rateparam.RateParamFrame;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.rate.rategen.RateGenChartFrame;
import org.nucastrodata.rate.rategen.RateGenExtraFrame;
import org.nucastrodata.rate.rategen.RateGenExtraTableFrame;
import org.nucastrodata.rate.rategen.RateGenIDPanel;
import org.nucastrodata.rate.rategen.RateGenInputCheckPanel;
import org.nucastrodata.rate.rategen.RateGenInputCheckPlotFrame;
import org.nucastrodata.rate.rategen.RateGenInputCheckTableFrame;
import org.nucastrodata.rate.rategen.RateGenInputWarningsPanel;
import org.nucastrodata.rate.rategen.RateGenIntroPanel;
import org.nucastrodata.rate.rategen.RateGenOptionsPanel;
import org.nucastrodata.rate.rategen.RateGenReadInputPanel;
import org.nucastrodata.rate.rategen.RateGenReadInputPasteFrame;
import org.nucastrodata.rate.rategen.RateGenResultsInfoFrame;
import org.nucastrodata.rate.rategen.RateGenResultsPanel;
import org.nucastrodata.rate.rategen.RateGenResultsPlotFrame;
import org.nucastrodata.rate.rategen.RateGenResultsRateInfoFrame;
import org.nucastrodata.rate.rategen.RateGenResultsTableFrame;
import org.nucastrodata.rate.rategen.RateGenStatusPanel;


/**
 * The Class RateGenFrame.
 */
public class RateGenFrame extends JFrame implements WindowListener, ActionListener{
	
	/** The continue on button. */
	public JButton continueButton, backButton, endButton, continueOnButton;
	
	/** The param button group. */
	ButtonGroup choiceButtonGroup, paramButtonGroup;
	
	/** The cancel radio button. */
	JRadioButton saveAndCloseRadioButton, closeRadioButton, doNotCloseRadioButton
				, yesRadioButton, noRadioButton, cancelRadioButton;
	
	/** The submit button param. */
	JButton submitButton, okButton, cancelButton, okButtonPaste, cancelButtonPaste
			, submitButtonParam;
	
	/** The end button panel. */
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	
	/** The rate param dialog. */
	JDialog saveDialog, confirmDialog, confirmDialogPaste, rateParamDialog;
	
	/** The current panel index. */
	public static int currentPanelIndex;	
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The c. */
	public Container c;	
	
	/** The rate gen intro panel. */
	public RateGenIntroPanel rateGenIntroPanel = new RateGenIntroPanel();
	
	/** The rate gen id panel. */
	public RateGenIDPanel rateGenIDPanel;
	
	/** The rate gen chart frame. */
	public RateGenChartFrame rateGenChartFrame;
	
	/** The rate gen read input panel. */
	public RateGenReadInputPanel rateGenReadInputPanel;
	
	/** The rate gen input check panel. */
	public RateGenInputCheckPanel rateGenInputCheckPanel;
	
	/** The rate gen input warnings panel. */
	public RateGenInputWarningsPanel rateGenInputWarningsPanel;
	
	/** The rate gen options panel. */
	public RateGenOptionsPanel rateGenOptionsPanel;
	
	/** The rate gen status panel. */
	public RateGenStatusPanel rateGenStatusPanel;
	
	/** The rate gen results panel. */
	public RateGenResultsPanel rateGenResultsPanel;
	
	/** The rate gen results info frame. */
	public RateGenResultsInfoFrame rateGenResultsInfoFrame;
	
	/** The rate gen results plot frame. */
	public RateGenResultsPlotFrame rateGenResultsPlotFrame;
	
	/** The rate gen results table frame. */
	public RateGenResultsTableFrame rateGenResultsTableFrame;
	
	/** The rate gen results rate info frame. */
	public RateGenResultsRateInfoFrame rateGenResultsRateInfoFrame;
	
	/** The rate gen input check table frame. */
	public RateGenInputCheckTableFrame rateGenInputCheckTableFrame;
	
	/** The rate gen read input paste frame. */
	public RateGenReadInputPasteFrame rateGenReadInputPasteFrame;
	
	/** The rate gen input check plot frame. */
	public RateGenInputCheckPlotFrame rateGenInputCheckPlotFrame;
	
	/** The rate gen extra frame. */
	public RateGenExtraFrame rateGenExtraFrame;
	
	/** The rate gen extra table frame. */
	public RateGenExtraTableFrame rateGenExtraTableFrame;

	/** The ds. */
	private RateGenDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate gen frame.
	 *
	 * @param ds the ds
	 */
	public RateGenFrame(RateGenDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		//Create buttons and set properties
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Close Rate Generator");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Rate Parameterizer");
		continueOnButton.addActionListener(this);
		continueOnButton.setFont(Fonts.buttonFont);
		
		//Set the current panel index to zero
		setCurrentPanelIndex(0);
		addIntroPanel();

		//add the introButton JPanel
		addIntroButtonPanel();
		
		//set window listener for rateGenFrame
		addWindowListener(this);

			

	}
	
	//Method addIntroPanel adds radio buttons
	/**
	 * Adds the intro panel.
	 */
	public void addIntroPanel(){
		c.add(rateGenIntroPanel, BorderLayout.CENTER);
		
		rateGenIntroPanel.setCurrentState(ds);
		
		//Call validate to reset Component layout
		c.validate();
	}
	
	//Method setCurrentPanelIndex takes one arg
	//which is the new index (integer)
	/**
	 * Sets the current panel index.
	 *
	 * @param index the new current panel index
	 */
	public void setCurrentPanelIndex(int index){
		currentPanelIndex = index;
	}
	
	//Methdo actionPerformed
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		//If the continue button is pressed
		if(ae.getSource()==continueButton){
			
			//Switch on the panel index
			switch(currentPanelIndex){
			
				//If cinaIntroPanel is showing
				case 0:
					
					//and remove the inttro button panel
					//just the back button
					removeIntroButtonPanel();
					
					rateGenIntroPanel.getCurrentState(ds);
					
					c.remove(rateGenIntroPanel);
					
					//Create panel index = 1
					//which is the rate identification panel
					rateGenIDPanel = new RateGenIDPanel(ds);
					
					if(!ds.getUploadData()){
					
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
						
							rateGenIDPanel.createSetGroupNodes();
						
						}
					
					}
					
					//Add the new panel			
					c.add(rateGenIDPanel, BorderLayout.CENTER);
					
					//Set its current state as held in the data structure
					rateGenIDPanel.setCurrentState();
					
					//Add the full button panel (forward and back)
					addFullButtonPanel();
					
					//Break out of the switch/case
					break; 
			
				//If the rate ID panel is showing
				case 1:
					
					if(rateGenIDPanel.checkDataFields()){
					
						//Save the current state of the rate ID panel
						rateGenIDPanel.getCurrentState();
						
						if(ds.getUploadData()){
						
							String cgiCommReactionString = Cina.rateGenFrame.rateGenIDPanel.createCGICommReactionString(ds.getRateDataStructure().getIsoIn()
																	, ds.getRateDataStructure().getIsoOut()
																	, ds.getRateDataStructure().getNumberReactants()
																	, ds.getRateDataStructure().getNumberProducts());
		
							ds.setReaction(cgiCommReactionString);
							
							ds.setReaction_type(ds.getRateDataStructure().getDecay());
						
							//If the reaction string is acceptable
							if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", Cina.rateGenFrame)){
								
								//Remove the rate ID JPanel from frame
								c.remove(rateGenIDPanel);
								
								//Create read input panel
								rateGenReadInputPanel = new RateGenReadInputPanel(ds);
								
								//Add it to the frame
								c.add(rateGenReadInputPanel, BorderLayout.CENTER);
								
								//Set the read inptu panel's current state
								rateGenReadInputPanel.setCurrentState();
								
							}
						
						}else{
						
							ds.setNucData(ds.getNucDataDataStructure().getNucDataID());
		
							ds.setNucDataProperties("Reaction String"
														+ "\u0009Number of Points"
														+ "\u0009X points"
														+ "\u0009Y points"
														+ "\u0009Reaction Type"
														+ "\u0009Organization Code"
														+ "\u0009People Code"
														+ "\u0009Nuc Data Notes"
														+ "\u0009Creation Date"
														+ "\u0009Reference Citation");
						
							//If the reaction string is acceptable
							if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", Cina.rateGenFrame)){
								
								//Remove the rate ID JPanel from frame
								c.remove(rateGenIDPanel);
								
								//Create read input panel
								rateGenReadInputPanel = new RateGenReadInputPanel(ds);
								
								//Add it to the frame
								c.add(rateGenReadInputPanel, BorderLayout.CENTER);
								
								//Set the read inptu panel's current state
								rateGenReadInputPanel.setCurrentState();
								
							}
						
						}
					
					}
					
					//Break out of the switch case
					break; 
				
				//If Read input panel is currently showing
				case 2:
				
					//Record the current state of the read input panel
					rateGenReadInputPanel.getCurrentState();
					
					if(ds.getUploadData()){
					
						if(ds.getPastedData()){
						
							createConfirmDialogPaste(this);
						
						}else if(!ds.getFilename().equals("")
							&& !Cina.cinaMainDataStructure.getUser().equals("guest")
							&& !ds.getPastedData()){
						
							createConfirmDialog(this);
						
						}else if(ds.getFilename().equals("")
							&& !Cina.cinaMainDataStructure.getUser().equals("guest")
							&& !ds.getPastedData()){
						
							String string = "Please choose a data source.";
						
							Dialogs.createExceptionDialog(string, this);
						
						}else if(!ds.getFilename().equals("")
							&& Cina.cinaMainDataStructure.getUser().equals("guest")){
						
							if(ds.getUploadData()){
		
								ds.setReaction_type(ds.getRateDataStructure().getDecay());
							
							}else{
							
								ds.setReaction_type(ds.getNucDataDataStructure().getDecay());
							
							}
							
							//read in the chosen test file for SFactor, E
							if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateGenFrame)){
								
								//Remove it from the frame
								c.remove(rateGenReadInputPanel);
								
								//Create the input check panel
								rateGenInputCheckPanel = new RateGenInputCheckPanel(ds);
								
								//Add it to the frame
								c.add(rateGenInputCheckPanel, BorderLayout.CENTER);
								
								//Create output plot to echo info back to user before
								//implementing preprocessing
								if(rateGenInputCheckPlotFrame==null){
									
									rateGenInputCheckPlotFrame = new RateGenInputCheckPlotFrame(ds);
									rateGenInputCheckPlotFrame.setFormatPanelState();
								
								}else{
									
									rateGenInputCheckPlotFrame.reinitialize();
									rateGenInputCheckPlotFrame.setFormatPanelState();
									rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.setPlotState();
									rateGenInputCheckPlotFrame.setVisible(true);
								
								}
							
								//Set its current state relative to the data structure
								rateGenInputCheckPanel.setCurrentState();
					
							}
						
						}
					
					}else{
					
						if(ds.getUploadData()){
		
							ds.setReaction_type(ds.getRateDataStructure().getDecay());
						
						}else{
						
							ds.setReaction_type(ds.getNucDataDataStructure().getDecay());
						
						}
						
						//read in the chosen test file for SFactor, E
						if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateGenFrame)){
							
							//Remove it from the frame
							c.remove(rateGenReadInputPanel);
							
							//Create the input check panel
							rateGenInputCheckPanel = new RateGenInputCheckPanel(ds);
							
							//Add it to the frame
							c.add(rateGenInputCheckPanel, BorderLayout.CENTER);
							
							//Create output plot to echo info back to user before
							//implementing preprocessing
							if(rateGenInputCheckPlotFrame==null){
								
								rateGenInputCheckPlotFrame = new RateGenInputCheckPlotFrame(ds);
								rateGenInputCheckPlotFrame.setFormatPanelState();
							
							}else{
								
								rateGenInputCheckPlotFrame.reinitialize();
								rateGenInputCheckPlotFrame.setFormatPanelState();
								rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.setPlotState();
								rateGenInputCheckPlotFrame.setVisible(true);
							
							}
						
							//Set its current state relative to the data structure
							rateGenInputCheckPanel.setCurrentState();
				
						}
					
					}
				
					//Break out of the switch/case
					break;
				
				//If the input check panel is showing
				case 3:
				
					//get the current state of the input check
					rateGenInputCheckPanel.getCurrentState();

					if(Cina.cinaCGIComm.doCGICall("PREPROCESSING", Cina.rateGenFrame)){
					
						//and remove it
						c.remove(rateGenInputCheckPanel);
						
						//Create the input warnings panel
						rateGenInputWarningsPanel = new RateGenInputWarningsPanel(ds);
						
						//add it to the frame			
						c.add(rateGenInputWarningsPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateGenInputWarningsPanel.setCurrentState();
					
					}
					
					//Break out of the switch/case loop
					break;
					
				//If the input warnings panel is showing
				case 4:
				
					//get the current state of the input warnings panel
					rateGenInputWarningsPanel.getCurrentState();
					
					//and remove it
					c.remove(rateGenInputWarningsPanel);
					
					//Create the program options panel
					rateGenOptionsPanel = new RateGenOptionsPanel(ds);
					
					//add it to the frame			
					c.add(rateGenOptionsPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateGenOptionsPanel.setCurrentState();

					//Break out of the switch/case loop
					break;
					
				//If the program options panel is showing
				case 5:
				
					if(rateGenOptionsPanel.checkDataFields()){
					
						rateGenOptionsPanel.getCurrentState();
					
						if(Cina.cinaCGIComm.doCGICall("GENERATE RATE", Cina.rateGenFrame)){
							
							//and remove it
							c.remove(rateGenOptionsPanel);
							
							//Create the program options panel
							rateGenStatusPanel = new RateGenStatusPanel(ds);
							
							rateGenStatusPanel.beginRateGenUpdateTask(500);
							
							rateGenStatusPanel.abortButton.setEnabled(true);
							
							//add it to the frame			
							c.add(rateGenStatusPanel, BorderLayout.CENTER);
							
							//Set its current state 
							rateGenStatusPanel.setCurrentState();
							
							continueButton.setEnabled(false);
						
						}
					}
					
					//Break out of the switch/case loop
					break;
					
				//If the program status panel is showing
				case 6:
				
					//get the current state of the program status panel
					rateGenStatusPanel.getCurrentState();
					
					if(rateGenStatusPanel.readRateGenerationOutput()){
						
						removeFullButtonPanel();
						
						addEndButtonPanel();
						
						//and remove it
						c.remove(rateGenStatusPanel);
						
						//Create the program options panel
						rateGenResultsPanel = new RateGenResultsPanel(ds);
						
						//add it to the frame			
						c.add(rateGenResultsPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateGenResultsPanel.setCurrentState();
					
					}
					
					//Break out of the switch/case loop
					break;	
					
			}
		
			//the magic word: validate
			c.validate();
		
		//Else if back button hit	
		}else if(ae.getSource()==backButton){
			
			//Switch on the panel index
			switch(currentPanelIndex){
			
				//If the ID panel is showing 
				case 1:
					
					rateGenIDPanel.setVisible(false);
					
					//remove the ID panel
					c.remove(rateGenIDPanel);
					
					//remove forward/back combo buttons
					removeFullButtonPanel();
					
					//set the panel index
					setCurrentPanelIndex(0);
					
					addIntroPanel();
					
					//add the continue only button panel
					addIntroButtonPanel();
					
					//Break out of the loop
					break; 
				
				//If the read input panel is showing
				case 2:
					
					//remove it
					c.remove(rateGenReadInputPanel);
					
					rateGenIDPanel = new RateGenIDPanel(ds);
					
					if(!ds.getUploadData()){
					
						rateGenIDPanel.createSetGroupNodes();
					
					}
					
					//add the Rate ID JPanel
					c.add(rateGenIDPanel, BorderLayout.CENTER);
					
					//set the current state
					rateGenIDPanel.setCurrentState();
			
					//set the panel index
					setCurrentPanelIndex(1);
					
					//Break out of the loop
					break; 
				
				//If input check panel is showing
				case 3:
				
					//remove the input check panel
					c.remove(rateGenInputCheckPanel);
					
					rateGenReadInputPanel = new RateGenReadInputPanel(ds);
					
					//Add the read input panel
					c.add(rateGenReadInputPanel, BorderLayout.CENTER);
					
					//set the current state of the panel
					rateGenReadInputPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(2);
					
					//Break out of the loop
					break;
				
				//If the input warnings panel is showing
				case 4:
				
					//remove it
					c.remove(rateGenInputWarningsPanel);
					
					rateGenInputCheckPanel = new RateGenInputCheckPanel(ds);
					
					//add the inptu check panel
					c.add(rateGenInputCheckPanel, BorderLayout.CENTER);
					
					//set the current state of the input check panel
					rateGenInputCheckPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(3);
					
					//break out of the loop
					break;	
					
				//If the program options panel is showing
				case 5:
				
					//remove it
					c.remove(rateGenOptionsPanel);
					
					rateGenInputWarningsPanel = new RateGenInputWarningsPanel(ds);
					
					//add the input warnings panel
					c.add(rateGenInputWarningsPanel, BorderLayout.CENTER);
					
					//set the current state of the input warnings panel
					rateGenInputWarningsPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(4);
					
					//break out of the loop
					break;
					
				//If the program status panel is showing
				case 6:
				
					if(!rateGenStatusPanel.statusLabel.getText().equals("Status Report: Program Running")){
						
						rateGenStatusPanel.statusTextArea.setText("");
						
						ds.setStatusText("");
						
						//remove it
						c.remove(rateGenStatusPanel);
						
						rateGenOptionsPanel = new RateGenOptionsPanel(ds);
						
						//add the program options panel
						c.add(rateGenOptionsPanel, BorderLayout.CENTER);
						
						//set the current state of the program options panel
						rateGenOptionsPanel.setCurrentState();
						
						//set the panel index
						setCurrentPanelIndex(5);
						
						continueButton.setEnabled(true);
						
					}else{
						
						String string = "You must abort program before going back.";
						Dialogs.createExceptionDialog(string, this);
					
					}
					
					//break out of the loop
					break;
					
				//If the results panel is showing
				case 7:
				
					//remove it
					c.remove(rateGenResultsPanel);
					
					removeEndButtonPanel();
					addFullButtonPanel();
					
					rateGenStatusPanel = new RateGenStatusPanel(ds);
					
					//add the program options panel
					c.add(rateGenStatusPanel, BorderLayout.CENTER);
					
					//set the current state of the program status panel
					rateGenStatusPanel.setCurrentState();
					
					rateGenStatusPanel.statusLabel.setText("Status Report: Program Complete");
					
					//set the panel index
					setCurrentPanelIndex(6);
					
					//break out of the loop
					break;
				
			}
			
			c.validate();
		
		}else if(ae.getSource()==endButton){
				
			//Call createSaveDialog with current panel index
        	createSaveDialog(currentPanelIndex);
	
		}else if(ae.getSource()==continueOnButton){

			String string = "Do you want to parameterize this reaction rate?";
			createRateParamDialog(string, this);
		
		}else if(ae.getSource()==submitButtonParam){
		
			if(yesRadioButton.isSelected()){
			
				rateParamDialog.setVisible(false);
				rateParamDialog.dispose();
			
				rateGenResultsPanel.getCurrentState();
			
				if(Cina.rateParamFrame==null){
					Cina.rateParamFrame = new RateParamFrame(Cina.rateParamDataStructure);
					Cina.rateParamFrame.setSubmitRate(Cina.rateGenDataStructure);
					Cina.rateParamFrame.setResizable(true);
					Cina.rateParamFrame.setSize(635, 425);
					Cina.rateParamFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
					Cina.rateParamFrame.setVisible(true);
					Cina.rateParamFrame.setTitle("Rate Parameterizer");
				}else{
					Cina.rateParamFrame.setSubmitRate(Cina.rateGenDataStructure);
					Cina.rateParamFrame.setVisible(true);
				}
			
			}else if(noRadioButton.isSelected()){
			
				rateParamDialog.setVisible(false);
				rateParamDialog.dispose();
				
				rateGenResultsPanel.getCurrentState();
	
				if(Cina.rateParamFrame==null){
					
					Cina.rateParamFrame = new RateParamFrame(Cina.rateParamDataStructure);
					Cina.rateParamDataStructure.setRateSubmitted(false);
					Cina.rateParamFrame.setResizable(true);
					Cina.rateParamFrame.setSize(635, 425);
					Cina.rateParamFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
					Cina.rateParamFrame.setVisible(true);
					Cina.rateParamFrame.setTitle("Rate Parameterizer");
					
				}else{
					
					if(Cina.rateParamDataStructure.getRateSubmitted()){
					
						Cina.rateParamFrame.c.removeAll();
						Cina.rateParamFrame.setCurrentPanelIndex(0);
						Cina.rateParamFrame.addIntroPanel();
						Cina.rateParamFrame.addIntroButtonPanel();
						Cina.rateParamDataStructure.setUploadData(true);
						Cina.rateParamDataStructure.setRateSubmitted(false);
						Cina.rateParamFrame.rateParamIntroPanel.setCurrentState(Cina.rateParamDataStructure);
						//Cina.setFrameColors(Cina.rateParamFrame);
						Cina.rateParamFrame.setVisible(true);
					
					}else{
						
						Cina.rateParamDataStructure.setUploadData(true);
						Cina.rateParamDataStructure.setRateSubmitted(false);
						Cina.rateParamFrame.rateParamIntroPanel.setCurrentState(Cina.rateParamDataStructure);
						//Cina.setFrameColors(Cina.rateParamFrame);
						Cina.rateParamFrame.setVisible(true);
					
					}
					
				}
			
			}else if(cancelRadioButton.isSelected()){
			
				rateParamDialog.setVisible(false);
				rateParamDialog.dispose();
			
			}
		
		//Else if the submit button of the save dialog is pressed
		}else if(ae.getSource()==submitButton){

			//If "Save info and close Rate Gen" is chosen
			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				//then
				//switch on panel index
				switch(currentPanelIndex){
					
					//If rate ID panel is showing
					case 1:
					
						//Get its state and store it
						rateGenIDPanel.getCurrentState();
						
						//Break out of switch/case
						break;
					
					//If read input panel is showing
					case 2:
					
						//Get its state
						rateGenReadInputPanel.getCurrentState();
						
						//Break out of switch/case
						break;
				
					//If input check panel is showing
					case 3:
					
						//Store state of input check panel
						rateGenInputCheckPanel.getCurrentState();
						
						//Break out of switch/case
						break;
					
					//If input warnings panel is showing 
					case 4:
					
						//Get the state of it
						rateGenInputWarningsPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					//If program options panel is showing 
					case 5:
					
						//Get the state of it
						rateGenOptionsPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					//If program status panel is showing 
					case 6:
					
						//Get the state of it
						rateGenStatusPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					//If results panel is showing 
					case 7:
					
						//Get the state of it
						rateGenResultsPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
				}
				
				//Close the dialog
				saveDialog.setVisible(false);
				saveDialog.dispose();
			
				//Set index to zero then when RateGen is reopened
				//it will start with the intro panel
				setCurrentPanelIndex(0);
				
				//Make the rate gen invisible 
         		setVisible(false);
         		
         		//Remove all compoenents from RateGenFrame
        		c.removeAll();
				
				//add the intro button panel
				addIntroButtonPanel();
        		
        		closeAllFrames();
        		
        		//Free system resources from frame
        		dispose();
        		
        		Cina.rateGenFrame = null;

			//Else if only close is chosen
			}else if(closeRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				ds.initialize();
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
				//Set index to zero 
				setCurrentPanelIndex(0);
				
				//Hide the frame
         		setVisible(false);
         		
         		//Remove all Components
        		c.removeAll();
				
				//Add intro button panel
				addIntroButtonPanel();
        
        		closeAllFrames();
        
        		//Free system resources 
        		dispose();
        		
        		Cina.rateGenFrame = null;
        		
			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
			}
			
		}else if(ae.getSource()==okButton){
		
			confirmDialog.setVisible(false);
			confirmDialog.dispose();
			
			if(ds.getUploadData()){
		
				ds.setReaction_type(ds.getRateDataStructure().getDecay());
			
			}else{
			
				ds.setReaction_type(ds.getNucDataDataStructure().getDecay());
			
			}
			
			//read in the chosen test file for SFactor, E
			if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateGenFrame)){
				
				//Remove it from the frame
				c.remove(rateGenReadInputPanel);
				
				//Create the input check panel
				rateGenInputCheckPanel = new RateGenInputCheckPanel(ds);
				
				//Add it to the frame
				c.add(rateGenInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(rateGenInputCheckPlotFrame==null){
					
					rateGenInputCheckPlotFrame = new RateGenInputCheckPlotFrame(ds);
					rateGenInputCheckPlotFrame.setFormatPanelState();
				
				}else{
					
					rateGenInputCheckPlotFrame.reinitialize();
					rateGenInputCheckPlotFrame.setFormatPanelState();
					rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.setPlotState();
					rateGenInputCheckPlotFrame.setVisible(true);
				
				}
				
				//Set its current state relative to the data structure
				rateGenInputCheckPanel.setCurrentState();
				
			}

		}else if(ae.getSource()==cancelButton){
		
			confirmDialog.setVisible(false);
			confirmDialog.dispose();
		
		}else if(ae.getSource()==okButtonPaste){
		
			confirmDialogPaste.setVisible(false);
			confirmDialogPaste.dispose();
		
			if(ds.getUploadData()){
		
				ds.setReaction_type(ds.getRateDataStructure().getDecay());
			
			}else{
			
				ds.setReaction_type(ds.getNucDataDataStructure().getDecay());
			
			}
			
			//read in the chosen test file for SFactor, E
			if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateGenFrame)){
				
				//Remove it from the frame
				c.remove(rateGenReadInputPanel);
				
				//Create the input check panel
				rateGenInputCheckPanel = new RateGenInputCheckPanel(ds);
				
				//Add it to the frame
				c.add(rateGenInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(rateGenInputCheckPlotFrame==null){
					
					rateGenInputCheckPlotFrame = new RateGenInputCheckPlotFrame(ds);
					rateGenInputCheckPlotFrame.setFormatPanelState();
				
				}else{
					
					rateGenInputCheckPlotFrame.reinitialize();
					rateGenInputCheckPlotFrame.setFormatPanelState();
					rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.setPlotState();
					rateGenInputCheckPlotFrame.setVisible(true);
				
				}
				
				//Set its current state relative to the data structure
				rateGenInputCheckPanel.setCurrentState();
				
			}

		}else if(ae.getSource()==cancelButtonPaste){
		
			confirmDialogPaste.setVisible(false);
			confirmDialogPaste.dispose();
		
		}
				
		
		
		//Redo layout of frame
		c.validate();
		
	}
	
	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){
	
		if(rateGenChartFrame!=null){
			rateGenChartFrame.setVisible(false);
			rateGenChartFrame.dispose();
		}
	
		if(rateGenReadInputPasteFrame!=null){
			rateGenReadInputPasteFrame.setVisible(false);
			rateGenReadInputPasteFrame.dispose();
		}
	
		if(rateGenInputCheckPlotFrame!=null){
			rateGenInputCheckPlotFrame.setVisible(false);
			rateGenInputCheckPlotFrame.dispose();
		}
		
		if(rateGenInputCheckTableFrame!=null){
			rateGenInputCheckTableFrame.setVisible(false);
			rateGenInputCheckTableFrame.dispose();
		}
		
		if(rateGenResultsPlotFrame!=null){
			rateGenResultsPlotFrame.setVisible(false);
			rateGenResultsPlotFrame.dispose();
		}
		
		if(rateGenResultsTableFrame!=null){
			rateGenResultsTableFrame.setVisible(false);
			rateGenResultsTableFrame.dispose();
		}
		
		if(rateGenResultsRateInfoFrame!=null){
			rateGenResultsRateInfoFrame.setVisible(false);
			rateGenResultsRateInfoFrame.dispose();
		}
		
		if(rateGenResultsInfoFrame!=null){
			rateGenResultsInfoFrame.setVisible(false);
			rateGenResultsInfoFrame.dispose();
		}
		
		if(rateGenExtraFrame!=null){
			rateGenExtraFrame.setVisible(false);
			rateGenExtraFrame.dispose();
			
		}
		
		if(rateGenExtraTableFrame!=null){
			rateGenExtraTableFrame.setVisible(false);
			rateGenExtraTableFrame.dispose();
			
		}
		
	}
	
	//Method addIntroButtonPanel creates and 
	//adds the continue only panel
	/**
	 * Adds the intro button panel.
	 */
	public void addIntroButtonPanel(){
		introButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		introButtonPanel.add(continueButton, gbc);

		c.add(introButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		
	}
	
	//Method removes the intro button panel
	/**
	 * Removes the intro button panel.
	 */
	public void removeIntroButtonPanel(){
	
		c.remove(introButtonPanel);
	}
	
	//Method adds full button panel
	//Continue and back
	/**
	 * Adds the full button panel.
	 */
	public void addFullButtonPanel(){
		fullButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		fullButtonPanel.add(backButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		fullButtonPanel.add(continueButton, gbc);

		c.add(fullButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
	}
	
	//Remove the full button panel
	/**
	 * Removes the full button panel.
	 */
	public void removeFullButtonPanel(){
		
		c.remove(fullButtonPanel);
	}
	
	//Method addIntroButtonPanel creates and 
	//adds the continue only panel
	/**
	 * Adds the end button panel.
	 */
	public void addEndButtonPanel(){
		endButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		endButtonPanel.add(backButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		endButtonPanel.add(endButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		endButtonPanel.add(continueOnButton, gbc);

		c.add(endButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		
	}
	
	//Method removes the intro button panel
	/**
	 * Removes the end button panel.
	 */
	public void removeEndButtonPanel(){
	
		c.remove(endButtonPanel);
		
	}
	
	//Window closing listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {   
	
		//If we are closing the Rate Gen JFrame
		if(we.getSource()==this){  
		   
			//Call createSaveDialog with current panel index
        	createSaveDialog(currentPanelIndex);
        
        //If we are closing the save dialog
    	}else if(we.getSource()==saveDialog){
    		
    		//Close the dialog
    		saveDialog.setVisible(false);
    		saveDialog.dispose();
    	}
    } 
    
    //Remainder of windowListener methods
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}
    
    /**
     * Creates the confirm dialog.
     *
     * @param frame the frame
     */
    public void createConfirmDialog(Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	confirmDialog = new JDialog(frame, "Attention!", true);
    	confirmDialog.setSize(350, 210);
    	confirmDialog.getContentPane().setLayout(new GridBagLayout());
		confirmDialog.setLocationRelativeTo(frame);

		JLabel label = new JLabel();

		if(ds.getRateDataStructure().getDecay().equals("")){

			label.setText("<html>You have chosen the reaction <br>" 
									+ ds.getRateDataStructure().getReactionString()
									+ " <p>and the file <br>"
									+ ds.getFilename()
									+ "<br>Is this correct?</html>");
									
		}else{
		
			label.setText("<html>You have chosen the reaction <br>" 
									+ ds.getRateDataStructure().getReactionString()
									+ " ["
									+ ds.getRateDataStructure().getDecay()
									+ "]"
									+ " <p>and the file <br>"
									+ ds.getFilename()
									+ "<br>Is this correct?</html>");
		
		}
									
		okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		gbc1.insets = new Insets(5, 5, 5, 5);
		confirmDialog.getContentPane().add(label, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		
		buttonPanel.add(okButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		buttonPanel.add(cancelButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		confirmDialog.getContentPane().add(buttonPanel, gbc1);
		
		//Cina.setFrameColors(confirmDialog);
		
		//Show the dialog
		confirmDialog.setVisible(true);	
	
	}
    
    /**
     * Creates the confirm dialog paste.
     *
     * @param frame the frame
     */
    public void createConfirmDialogPaste(Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	confirmDialogPaste = new JDialog(frame, "Attention!", true);
    	confirmDialogPaste.setSize(320, 178);
    	confirmDialogPaste.getContentPane().setLayout(new GridBagLayout());
		confirmDialogPaste.setLocationRelativeTo(frame);

		JLabel label = new JLabel();

		if(ds.getRateDataStructure().getDecay().equals("")){

			label.setText("<html>You have chosen the reaction <br>" 
										+ ds.getRateDataStructure().getReactionString()
										+ " <p>using pasted data."
										+ "<br>Is this correct?</html>");
									
		}else{
		
			label.setText("<html>You have chosen the reaction <br>" 
										+ ds.getRateDataStructure().getReactionString()
										+ " ["
										+ ds.getRateDataStructure().getDecay()
										+ "]"
										+ " <p>using pasted data."
										+ "<br>Is this correct?</html>");
		
		}
									
		okButtonPaste = new JButton("Ok");
		okButtonPaste.setFont(Fonts.buttonFont);
		okButtonPaste.addActionListener(this);
		
		cancelButtonPaste = new JButton("Cancel");
		cancelButtonPaste.setFont(Fonts.buttonFont);
		cancelButtonPaste.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		gbc1.insets = new Insets(5, 5, 5, 5);
		confirmDialogPaste.getContentPane().add(label, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		
		buttonPanel.add(okButtonPaste, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		buttonPanel.add(cancelButtonPaste, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		confirmDialogPaste.getContentPane().add(buttonPanel, gbc1);
		
		//Cina.setFrameColors(confirmDialogPaste);
		
		//Show the dialog
		confirmDialogPaste.setVisible(true);	
	
	}
    
    /**
     * Creates the rate param dialog.
     *
     * @param string the string
     * @param frame the frame
     */
    public void createRateParamDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	rateParamDialog = new JDialog(frame, "Attention!", true);
    	rateParamDialog.setSize(382, 300);
    	rateParamDialog.getContentPane().setLayout(new GridBagLayout());
		rateParamDialog.setLocationRelativeTo(frame);
		
		JTextArea rateParamTextArea = new JTextArea("", 5, 30);
		rateParamTextArea.setLineWrap(true);
		rateParamTextArea.setWrapStyleWord(true);
		rateParamTextArea.setFont(Fonts.textFont);
		rateParamTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(rateParamTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String rateParamString = string;

		rateParamTextArea.setText(rateParamString);
		rateParamTextArea.setCaretPosition(0);	
		
		yesRadioButton = new JRadioButton("Yes. I want to parameterize this rate.", true);
		yesRadioButton.setFont(Fonts.textFont);
		
		noRadioButton = new JRadioButton("No. I want to parameterize another rate.", false);
		noRadioButton.setFont(Fonts.textFont);
		
		cancelRadioButton = new JRadioButton("Cancel this action.", false);
		cancelRadioButton.setFont(Fonts.textFont);
		
		paramButtonGroup = new ButtonGroup();
		paramButtonGroup.add(yesRadioButton);
		paramButtonGroup.add(noRadioButton);
		paramButtonGroup.add(cancelRadioButton);
		
		submitButtonParam = new JButton("Submit");
		submitButtonParam.setFont(Fonts.buttonFont);
		submitButtonParam.addActionListener(this);
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 3, 3);
		gbc1.anchor = GridBagConstraints.WEST;
		
		panel.add(yesRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		panel.add(noRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		
		panel.add(cancelRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 3;
		gbc1.anchor = GridBagConstraints.CENTER;
		
		panel.add(submitButtonParam, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		rateParamDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		rateParamDialog.getContentPane().add(panel, gbc1);
		
		//Cina.setFrameColors(rateParamDialog);
		
		rateParamDialog.setVisible(true);

	}
    
    //Method createSaveDialog
    //Args JPanel index
    /**
     * Creates the save dialog.
     *
     * @param index the index
     */
    public void createSaveDialog(int index){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Rate Generator Exit", true);
    	saveDialog.setSize(457, 180);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Rate Generator session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Rate Generator session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Rate Generator.", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		
		choiceButtonGroup.add(saveAndCloseRadioButton);
		choiceButtonGroup.add(closeRadioButton);
		choiceButtonGroup.add(doNotCloseRadioButton);
		
		//Create submit button and its properties
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		//Layout the components
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		saveDialog.getContentPane().add(saveAndCloseRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		saveDialog.getContentPane().add(closeRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		saveDialog.getContentPane().add(doNotCloseRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		saveDialog.getContentPane().add(submitButton, gbc);
		
		//Add window listener
		//saveDialog.addWindowListener(this);
		
		//Cina.setFrameColors(saveDialog);
				
		//Show the dialog
		saveDialog.setVisible(true);

    }
    
}