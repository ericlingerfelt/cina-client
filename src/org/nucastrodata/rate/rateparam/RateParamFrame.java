package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;
import org.nucastrodata.rate.rateman.RateManFrame;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.rate.rateparam.RateParamChartFrame;
import org.nucastrodata.rate.rateparam.RateParamIDPanel;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPanel;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPlotFrame;
import org.nucastrodata.rate.rateparam.RateParamInputCheckTableFrame;
import org.nucastrodata.rate.rateparam.RateParamInputWarningsPanel;
import org.nucastrodata.rate.rateparam.RateParamIntroPanel;
import org.nucastrodata.rate.rateparam.RateParamOptionsModifyPanel;
import org.nucastrodata.rate.rateparam.RateParamOptionsOtherPanel;
import org.nucastrodata.rate.rateparam.RateParamOptionsStartPanel;
import org.nucastrodata.rate.rateparam.RateParamOptionsTechPanel;
import org.nucastrodata.rate.rateparam.RateParamPointFrame;
import org.nucastrodata.rate.rateparam.RateParamReadInputPanel;
import org.nucastrodata.rate.rateparam.RateParamReadInputPasteFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsEvalFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsFitFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsInfoFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsInverseFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsNotesFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsOtherPanel;
import org.nucastrodata.rate.rateparam.RateParamResultsOutputFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsPlotFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsSummaryPanel;
import org.nucastrodata.rate.rateparam.RateParamResultsTableFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsVizPanel;
import org.nucastrodata.rate.rateparam.RateParamStartFrame;
import org.nucastrodata.rate.rateparam.RateParamStartHelpFrame;
import org.nucastrodata.rate.rateparam.RateParamStatusPanel;


/**
 * The Class RateParamFrame.
 */
public class RateParamFrame extends JFrame implements WindowListener, ActionListener{
	
	/** The continue on button. */
	public JButton continueButton, backButton, endButton, continueOnButton;
	
	/** The choice button group. */
	ButtonGroup choiceButtonGroup;
	
	/** The do not close radio button. */
	JRadioButton saveAndCloseRadioButton, closeRadioButton, doNotCloseRadioButton;
	
	/** The cancel button paste. */
	JButton submitButton, okButton, cancelButton, okButtonPaste, cancelButtonPaste;
	
	/** The end button panel. */
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	
	/** The confirm dialog paste. */
	JDialog saveDialog, confirmDialog, confirmDialogPaste;
	
	/** The current panel index. */
	public static int currentPanelIndex;	
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The c. */
	public Container c;	
	
	/** The rate param intro panel. */
	public RateParamIntroPanel rateParamIntroPanel = new RateParamIntroPanel();
	
	/** The rate param id panel. */
	public RateParamIDPanel rateParamIDPanel;
	
	/** The rate param chart frame. */
	public RateParamChartFrame rateParamChartFrame;
	
	/** The rate param read input panel. */
	public RateParamReadInputPanel rateParamReadInputPanel;
	
	/** The rate param input check panel. */
	public RateParamInputCheckPanel rateParamInputCheckPanel;
	
	/** The rate param input warnings panel. */
	public RateParamInputWarningsPanel rateParamInputWarningsPanel;
	
	/** The rate param options tech panel. */
	public RateParamOptionsTechPanel rateParamOptionsTechPanel;
	
	/** The rate param options start panel. */
	public RateParamOptionsStartPanel rateParamOptionsStartPanel;
	
	/** The rate param options modify panel. */
	public RateParamOptionsModifyPanel rateParamOptionsModifyPanel;
	
	/** The rate param options other panel. */
	public RateParamOptionsOtherPanel rateParamOptionsOtherPanel;
	
	/** The rate param status panel. */
	public RateParamStatusPanel rateParamStatusPanel;
	
	/** The rate param results summary panel. */
	public RateParamResultsSummaryPanel rateParamResultsSummaryPanel;
	
	/** The rate param results viz panel. */
	public RateParamResultsVizPanel rateParamResultsVizPanel;
	
	/** The rate param results other panel. */
	public RateParamResultsOtherPanel rateParamResultsOtherPanel;
	
	/** The rate param input check table frame. */
	public RateParamInputCheckTableFrame rateParamInputCheckTableFrame;
	
	/** The rate param read input paste frame. */
	public RateParamReadInputPasteFrame rateParamReadInputPasteFrame;
	
	/** The rate param results plot frame. */
	public RateParamResultsPlotFrame rateParamResultsPlotFrame;
	
	/** The rate param results fit frame. */
	public RateParamResultsFitFrame rateParamResultsFitFrame;
	
	/** The rate param results output frame. */
	public RateParamResultsOutputFrame rateParamResultsOutputFrame;
	
	/** The rate param results info frame. */
	public RateParamResultsInfoFrame rateParamResultsInfoFrame;
	
	/** The rate param results inverse frame. */
	public RateParamResultsInverseFrame rateParamResultsInverseFrame;
	
	/** The rate param results eval frame. */
	public RateParamResultsEvalFrame rateParamResultsEvalFrame;
	
	/** The rate param results notes frame. */
	public RateParamResultsNotesFrame rateParamResultsNotesFrame;
	
	/** The rate param results table frame. */
	public RateParamResultsTableFrame rateParamResultsTableFrame;
	
	/** The rate param input check plot frame. */
	public RateParamInputCheckPlotFrame rateParamInputCheckPlotFrame;
	
	/** The rate param start frame. */
	public RateParamStartFrame rateParamStartFrame;
	
	/** The rate param start help frame. */
	public RateParamStartHelpFrame rateParamStartHelpFrame;
	
	/** The rate param point frame. */
	public RateParamPointFrame rateParamPointFrame;

	/** The temp grid. */
	private double[] tempGrid = {0.01, 0.011, 0.012, 0.013, 0.014, 0.015, 0.016, 0.017, 0.018, 0.019
									, 0.02, 0.0225, 0.025, 0.0275
									, 0.03, 0.0325, 0.035, 0.0375
									, 0.04, 0.0425, 0.045, 0.0475
									, 0.05, 0.0525, 0.055, 0.0575
									, 0.06, 0.0625, 0.065, 0.0675
									, 0.07, 0.0725, 0.075, 0.0775
									, 0.08, 0.0825, 0.085, 0.0875
									, 0.09, 0.0925, 0.095, 0.0975
									, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95
									, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10};

	/** The units. */
	String[] units = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                                , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                                , "cm^6/(mole^2*s)"};

	/** The ds. */
	private RateParamDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate param frame.
	 *
	 * @param ds the ds
	 */
	public RateParamFrame(RateParamDataStructure ds){
		
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
		
		endButton = new JButton("Close Rate Parameterizer");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Rate Manager");
		continueOnButton.addActionListener(this);
		continueOnButton.setFont(Fonts.buttonFont);
		
		//Set the current panel index to zero
		setCurrentPanelIndex(0);
		addIntroPanel();

		//add the introButton JPanel
		addIntroButtonPanel();
		
		//set window listener for rateParamFrame
		addWindowListener(this);

			

	}
	
	/**
	 * Sets the submit rate.
	 *
	 * @param rgds the new submit rate
	 */
	public void setSubmitRate(RateGenDataStructure rgds){
		
		ds.setUploadData(false);
		ds.setRateSubmitted(true);
		
		ds.setReaction(rgds.getRateDataStructure().getReactionString());
		ds.setReaction_type(rgds.getRateDataStructure().getDecay());
		ds.setNotes(rgds.getRateDataStructure().getReactionNotes());
		ds.setInputType("R(T)");
		ds.setInputFormat("1,0,2,0");
		ds.setInputFilename("");
		ds.setInputFile(getDataPoints(rgds));
		ds.setRateUnits(units[rgds.getRateDataStructure().getReactionType()]);
		ds.setXUnits("T9");
		ds.setRateDataArrayOrig(null);
		ds.setTempDataArrayOrig(null);

		ds.getRateDataStructure().setReactionString(rgds.getRateDataStructure().getReactionString());
		ds.getRateDataStructure().setReactionType(rgds.getRateDataStructure().getReactionType());
		ds.getRateDataStructure().setDecayType(rgds.getRateDataStructure().getDecayType());
		ds.getRateDataStructure().setDecay(rgds.getRateDataStructure().getDecay());
		ds.getRateDataStructure().setReactionNotes(rgds.getRateDataStructure().getReactionNotes());
		
		ds.getNucDataDataStructure().setReactionString(rgds.getRateDataStructure().getReactionString());
		ds.getNucDataDataStructure().setDecay(rgds.getRateDataStructure().getDecay());
		
		setCurrentPanelIndex(2);
				
		if(Cina.cinaCGIComm.doCGICall("READ INPUT", this)){
			
			//Remove it from the frame
			//c.remove(rateParamIntroPanel);
			
			c.removeAll();
			
			addIntroButtonPanel();
			
			//Create the input check panel
			rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);
			
			//Add it to the frame
			c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
			
			//Create output plot to echo info back to user before
			//implementing preprocessing
			if(rateParamInputCheckPlotFrame==null){
				
				rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(ds);
				rateParamInputCheckPlotFrame.setFormatPanelState();
			
			}else{
				
				rateParamInputCheckPlotFrame.setFormatPanelState();
				rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
				rateParamInputCheckPlotFrame.setVisible(true);
			
			}
		
			//Set its current state relative to the data structure
			rateParamInputCheckPanel.setCurrentState();

			
			c.validate();

		}
	
	}
	
	/**
	 * Gets the data points.
	 *
	 * @param rgds the rgds
	 * @return the data points
	 */
	public String getDataPoints(RateGenDataStructure rgds){
	
		String string = "";
		
		for(int i=0; i<rgds.getNumberPointsRT(); i++){
		
			if(i==0){
			
				string = NumberFormats.getFormattedValueLong(rgds.getTempDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(rgds.getRateDataArray()[i]);
			
			}else{
			
				string += "\n"
							+ NumberFormats.getFormattedValueLong(rgds.getTempDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(rgds.getRateDataArray()[i]);
			
			}
		
		}
	
		return string;
	
	}
	
	/**
	 * Continue fit at status.
	 */
	public void continueFitAtStatus(){
		
		if(Cina.cinaCGIComm.doCGICall("PARAMETERIZE RATE", this)){
					
			//and remove it
			c.remove(rateParamResultsSummaryPanel);
			
			if(rateParamResultsVizPanel!=null){	
				c.remove(rateParamResultsVizPanel);
			}
				
			//Create the program options panel
			rateParamStatusPanel = new RateParamStatusPanel(ds);
			rateParamStatusPanel.beginRateParamUpdateTask(500);
			rateParamStatusPanel.abortButton.setEnabled(true);
			
			//add it to the frame			
			c.add(rateParamStatusPanel, BorderLayout.CENTER);
			
			//Set its current state 
			rateParamStatusPanel.setCurrentState();
			
			continueButton.setEnabled(false);
		
		}
		
			
		c.validate();
	
	}
	
	/**
	 * Continue fit at tech.
	 */
	public void continueFitAtTech(){

		c.remove(rateParamResultsSummaryPanel);
		if(rateParamResultsVizPanel!=null){	
			c.remove(rateParamResultsVizPanel);
		}
	
		rateParamOptionsTechPanel = new RateParamOptionsTechPanel(ds);
		
		//add it to the frame			
		c.add(rateParamOptionsTechPanel, BorderLayout.CENTER);
		
		//Set its current state 
		rateParamOptionsTechPanel.setCurrentState();
	
			
		c.validate();
	
	}
	
	/**
	 * Continue fit at start.
	 */
	public void continueFitAtStart(){
	
		c.remove(rateParamResultsSummaryPanel);
		if(rateParamResultsVizPanel!=null){	
			c.remove(rateParamResultsVizPanel);
		}
	
		rateParamOptionsStartPanel = new RateParamOptionsStartPanel(ds);
						
		//add it to the frame			
		c.add(rateParamOptionsStartPanel, BorderLayout.CENTER);
		
		//Set its current state 
		rateParamOptionsStartPanel.setCurrentState();
	
			
		c.validate();
	
	}
	
	/**
	 * Continue fit at modify.
	 */
	public void continueFitAtModify(){
	
		c.remove(rateParamResultsSummaryPanel);
		if(rateParamResultsVizPanel!=null){	
			c.remove(rateParamResultsVizPanel);
		}
	
		rateParamOptionsModifyPanel = new RateParamOptionsModifyPanel(ds);
					
		//add it to the frame			
		c.add(rateParamOptionsModifyPanel, BorderLayout.CENTER);
		
		//Set its current state 
		rateParamOptionsModifyPanel.setCurrentState();
	
			
		c.validate();
	
	}
	
	/**
	 * Continue fit at other.
	 */
	public void continueFitAtOther(){
		
		c.remove(rateParamResultsSummaryPanel);
		if(rateParamResultsVizPanel!=null){	
			c.remove(rateParamResultsVizPanel);
		}
	
		rateParamOptionsOtherPanel = new RateParamOptionsOtherPanel(ds);
					
		//add it to the frame			
		c.add(rateParamOptionsOtherPanel, BorderLayout.CENTER);
		
		//Set its current state 
		rateParamOptionsOtherPanel.setCurrentState();
	
			
		c.validate();
	
	}
	
	//Method addIntroPanel adds radio buttons
	/**
	 * Adds the intro panel.
	 */
	public void addIntroPanel(){
		c.add(rateParamIntroPanel, BorderLayout.CENTER);
		
		rateParamIntroPanel.setCurrentState(ds);
		
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
					
					rateParamIntroPanel.getCurrentState(ds);
					
					c.remove(rateParamIntroPanel);
					
					//Create panel index = 1
					//which is the rate identification panel
					rateParamIDPanel = new RateParamIDPanel(ds);
					
					if(Cina.cinaMainDataStructure.getUser().equals("guest")){
						ds.setUploadData(true);
					}
					
					if(!ds.getUploadData()){
					
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
						
							rateParamIDPanel.createSetGroupNodes();
						
						}
					
					}
					
					//Add the new panel			
					c.add(rateParamIDPanel, BorderLayout.CENTER);
					
					//Set its current state as held in the data structure
					rateParamIDPanel.setCurrentState();
					
					//Add the full button panel (forward and back)
					addFullButtonPanel();
					
					//Break out of the switch/case
					break; 
			
				//If the rate ID panel is showing
				case 1:
					
					if(rateParamIDPanel.checkDataFields()){
					
						//Save the current state of the rate ID panel
						rateParamIDPanel.getCurrentState();
						
						if(ds.getUploadData()){
						
							String cgiCommReactionString = Cina.rateParamFrame.rateParamIDPanel.createCGICommReactionString(ds.getRateDataStructure().getIsoIn()
																	, ds.getRateDataStructure().getIsoOut()
																	, ds.getRateDataStructure().getNumberReactants()
																	, ds.getRateDataStructure().getNumberProducts());
		
							ds.setReaction(cgiCommReactionString);
							
							ds.setReaction_type(ds.getRateDataStructure().getDecay());
						
							//If the reaction string is acceptable
							if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", this)){
								
								//Remove the rate ID JPanel from frame
								c.remove(rateParamIDPanel);
								
								//Create read input panel
								rateParamReadInputPanel = new RateParamReadInputPanel(ds);
								
								//Add it to the frame
								c.add(rateParamReadInputPanel, BorderLayout.CENTER);
								
								//Set the read inptu panel's current state
								rateParamReadInputPanel.setCurrentState();
								
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
							if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", this)){
								
								//Remove the rate ID JPanel from frame
								c.remove(rateParamIDPanel);
								
								//Create read input panel
								rateParamReadInputPanel = new RateParamReadInputPanel(ds);
								
								//Add it to the frame
								c.add(rateParamReadInputPanel, BorderLayout.CENTER);
								
								//Set the read inptu panel's current state
								rateParamReadInputPanel.setCurrentState();
								
							}
						
						}
					
					}
					
					//Break out of the switch case
					break; 
				
				//If Read input panel is currently showing
				case 2:
				
					//Record the current state of the read input panel
					rateParamReadInputPanel.getCurrentState();
					
					if(ds.getUploadData()){
					
						if(ds.getPastedData()){
						
							createConfirmDialogPaste(this);
						
						}else if(!ds.getFilename().equals("")
							&& !Cina.cinaMainDataStructure.getUser().equals("guest")
							&& !ds.getPastedData()){
						
							if(rateParamReadInputPanel.uploadFile(ds.getFileInit())){
						
								createConfirmDialog(this);
							
							}else{
							
								String string = "The file you have uploaded is empty. Please choose another.";
								Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
								
								rateParamReadInputPanel.filenameField.setText("");	
							
							}
						
						
						}else if(ds.getFilename().equals("")
							&& !Cina.cinaMainDataStructure.getUser().equals("guest")
							&& !ds.getPastedData()){
						
							String string = "Please choose a data source.";
						
							Dialogs.createExceptionDialog(string, this);
						
						}else if(!ds.getFilename().equals("")
							&& Cina.cinaMainDataStructure.getUser().equals("guest")){
						
							ds.setReaction_type(ds.getRateDataStructure().getDecay());
							
							//read in the chosen test file for SFactor, E
							if(Cina.cinaCGIComm.doCGICall("READ INPUT", this)){

								//Remove it from the frame
								c.remove(rateParamReadInputPanel);
								
								//Create the input check panel
								rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);

								//Add it to the frame
								c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
								
								//Create output plot to echo info back to user before
								//implementing preprocessing
								if(rateParamInputCheckPlotFrame==null){
									
									rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(ds);
									rateParamInputCheckPlotFrame.setFormatPanelState();
	
								}else{

									rateParamInputCheckPlotFrame.setFormatPanelState();
									rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
									rateParamInputCheckPlotFrame.setVisible(true);
								
								}
							
								//Set its current state relative to the data structure
								rateParamInputCheckPanel.setCurrentState();
					
							}
						
						}
					
					}else{
						
						ds.setReaction_type(ds.getNucDataDataStructure().getDecay());
		
						//read in the chosen test file for SFactor, E
						if(Cina.cinaCGIComm.doCGICall("READ INPUT", this)){
							
							//Remove it from the frame
							c.remove(rateParamReadInputPanel);
							
							//Create the input check panel
							rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);
							
							//Add it to the frame
							c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
							
							//Create output plot to echo info back to user before
							//implementing preprocessing
							if(rateParamInputCheckPlotFrame==null){
								
								rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(ds);
								rateParamInputCheckPlotFrame.setFormatPanelState();
							
							}else{
								
								rateParamInputCheckPlotFrame.setFormatPanelState();
								rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
								rateParamInputCheckPlotFrame.setVisible(true);
							
							}
						
							//Set its current state relative to the data structure
							rateParamInputCheckPanel.setCurrentState();
				
						}
					
					}
				
					//Break out of the switch/case
					break;
				
				//If the input check panel is showing
				case 3:
				
					//get the current state of the input check
					rateParamInputCheckPanel.getCurrentState();

					if(Cina.cinaCGIComm.doCGICall("PREPROCESSING", this)){
					
						//and remove it
						c.remove(rateParamInputCheckPanel);
						
						//Create the input warnings panel
						rateParamInputWarningsPanel = new RateParamInputWarningsPanel(ds);
						
						if(ds.getRateSubmitted()){
						
							removeIntroButtonPanel();
							addFullButtonPanel();
						
						}
						
						//add it to the frame			
						c.add(rateParamInputWarningsPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateParamInputWarningsPanel.setCurrentState();
					
					}
					
					//Break out of the switch/case loop
					break;
					
				//If the input warnings panel is showing
				case 4:
				
					//get the current state of the input warnings panel
					rateParamInputWarningsPanel.getCurrentState();
					
					//and remove it
					c.remove(rateParamInputWarningsPanel);
				
					//Create the param options panel
					rateParamOptionsTechPanel = new RateParamOptionsTechPanel(ds);
					
					//add it to the frame			
					c.add(rateParamOptionsTechPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateParamOptionsTechPanel.setCurrentState();
					
					//Break out of the switch/case loop
					break;
					
				//If the param options panel is showing
				case 5:
				
					//get the current state of the input warnings panel
					rateParamOptionsTechPanel.getCurrentState();
					
					//and remove it
					c.remove(rateParamOptionsTechPanel);
				
					if(ds.getTechniqueParam().equals("MARQUARDT")){
				
						//Create the param options panel
						rateParamOptionsStartPanel = new RateParamOptionsStartPanel(ds);
						
						//add it to the frame			
						c.add(rateParamOptionsStartPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateParamOptionsStartPanel.setCurrentState();
					
					}else{
					
						//Create the param options panel
						rateParamOptionsModifyPanel = new RateParamOptionsModifyPanel(ds);
						
						//add it to the frame			
						c.add(rateParamOptionsModifyPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateParamOptionsModifyPanel.setCurrentState();
					
					}
					
					//Break out of the switch/case loop
					break;	
				
				case 6:
				
					//get the current state of the input warnings panel
					rateParamOptionsStartPanel.getCurrentState();
					
					//and remove it
					c.remove(rateParamOptionsStartPanel);
				
					//Create the param options panel
					rateParamOptionsModifyPanel = new RateParamOptionsModifyPanel(ds);
					
					//add it to the frame			
					c.add(rateParamOptionsModifyPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateParamOptionsModifyPanel.setCurrentState();
					
					//Break out of the switch/case loop
					break;
				
				case 7:
				
					//get the current state of the input warnings panel
					rateParamOptionsModifyPanel.getCurrentState();
					
					//and remove it
					c.remove(rateParamOptionsModifyPanel);
				
					//Create the param options panel
					rateParamOptionsOtherPanel = new RateParamOptionsOtherPanel(ds);
					
					//add it to the frame			
					c.add(rateParamOptionsOtherPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateParamOptionsOtherPanel.setCurrentState();
					
					//Break out of the switch/case loop
					break;
					
				case 8:
				
					if(rateParamOptionsOtherPanel.checkDataFields()){
					
						rateParamOptionsOtherPanel.getCurrentState();
					
						if(Cina.cinaCGIComm.doCGICall("PARAMETERIZE RATE", this)){
							
							//and remove it
							c.remove(rateParamOptionsOtherPanel);
							
							//Create the program options panel
							rateParamStatusPanel = new RateParamStatusPanel(ds);
							
							rateParamStatusPanel.beginRateParamUpdateTask(500);
							
							rateParamStatusPanel.abortButton.setEnabled(true);
							
							//add it to the frame			
							c.add(rateParamStatusPanel, BorderLayout.CENTER);
							
							//Set its current state 
							rateParamStatusPanel.setCurrentState();
							
							continueButton.setEnabled(false);
						
						}
					}
					
					//Break out of the switch/case loop
					break;
				
				//If the param status panel is showing
				case 9:
				
					//get the current state of the param status panel
					rateParamStatusPanel.getCurrentState();
					
					if(rateParamStatusPanel.readRateParameterizationOutput()){
						
						//and remove it
						c.remove(rateParamStatusPanel);
						
						//Create the program options panel
						rateParamResultsSummaryPanel = new RateParamResultsSummaryPanel(ds);
						
						//add it to the frame			
						c.add(rateParamResultsSummaryPanel, BorderLayout.CENTER);
						
						//Set its current state 
						rateParamResultsSummaryPanel.setCurrentState();
					
					}
					
					//Break out of the switch/case loop
					break;	

				case 10:
				
					//get the current state of the param status panel
					rateParamResultsSummaryPanel.getCurrentState();
				
					//and remove it
					c.remove(rateParamResultsSummaryPanel);
					
					//Create the program options panel
					rateParamResultsVizPanel = new RateParamResultsVizPanel(ds);
					
					//add it to the frame			
					c.add(rateParamResultsVizPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateParamResultsVizPanel.setCurrentState();

					double[] array = new double[ds.getTempDataArray().length];
		
					for(int i=0; i<ds.getTempDataArray().length; i++){
						
						array[i] = Cina.cinaMainDataStructure.calcRate(ds.getTempDataArray()[i]
																									, ds.getRateDataStructure().getParameters()
																									, ds.getRateDataStructure().getNumberParameters());
						
					}
					
					ds.setTempParamDataArray(ds.getTempDataArray());
					ds.setRateParamDataArray(array);
					ds.setLowTempDataArray(getLowTempDataArray());
					ds.setHighTempDataArray(getHighTempDataArray());
					ds.setLowRateDataArray(getLowRateDataArray());
					ds.setHighRateDataArray(getHighRateDataArray());
					
					if(ds.getLowTempDataArray().length==1){
						ds.setLowTemp(false);
					}else{
						ds.setLowTemp(true);
					}

					if(ds.getHighTempDataArray().length==1){
						ds.setHighTemp(false);
					}else{
						ds.setHighTemp(true);
					}

					if(rateParamResultsPlotFrame==null){
									
						rateParamResultsPlotFrame = new RateParamResultsPlotFrame(ds);
						rateParamResultsPlotFrame.rateParamResultsPlotCanvas.setPlotState();
						rateParamResultsPlotFrame.setVisible(true);

					}else{

						rateParamResultsPlotFrame.setFormatPanelState();
						rateParamResultsPlotFrame.rateParamResultsListPanel.initialize();
						rateParamResultsPlotFrame.rateParamResultsPlotCanvas.setPlotState();
						rateParamResultsPlotFrame.setVisible(true);
					
					}
					
					//Break out of the switch/case loop
					break;	
					
				case 11:
				
					//get the current state of the param status panel
					rateParamResultsVizPanel.getCurrentState();
				
					//and remove it
					c.remove(rateParamResultsVizPanel);
					
					removeFullButtonPanel();
					addEndButtonPanel();
					
					//Create the program options panel
					rateParamResultsOtherPanel = new RateParamResultsOtherPanel(ds);
					
					//add it to the frame			
					c.add(rateParamResultsOtherPanel, BorderLayout.CENTER);
					
					//Set its current state 
					rateParamResultsOtherPanel.setCurrentState();
					
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
					
					rateParamIDPanel.setVisible(false);
					
					//remove the ID panel
					c.remove(rateParamIDPanel);
					
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
					c.remove(rateParamReadInputPanel);
					
					rateParamIDPanel = new RateParamIDPanel(ds);
					
					if(!ds.getUploadData()){
					
						rateParamIDPanel.createSetGroupNodes();
					
					}
					
					//add the Rate ID JPanel
					c.add(rateParamIDPanel, BorderLayout.CENTER);
					
					//set the current state
					rateParamIDPanel.setCurrentState();
			
					//set the panel index
					setCurrentPanelIndex(1);
					
					//Break out of the loop
					break; 
				
				//If input check panel is showing
				case 3:
				
					//remove the input check panel
					c.remove(rateParamInputCheckPanel);
					
					rateParamReadInputPanel = new RateParamReadInputPanel(ds);
					
					//Add the read input panel
					c.add(rateParamReadInputPanel, BorderLayout.CENTER);
					
					//set the current state of the panel
					rateParamReadInputPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(2);
					
					//Break out of the loop
					break;
				
				//If the input warnings panel is showing
				case 4:
				
					//remove it
					c.remove(rateParamInputWarningsPanel);
					
					rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);
					
					if(ds.getRateSubmitted()){
						
						removeFullButtonPanel();
						addIntroButtonPanel();
					
					}
					
					//add the inptu check panel
					c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
					
					//set the current state of the input check panel
					rateParamInputCheckPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(3);
					
					//break out of the loop
					break;	
					
				//If the program options panel is showing
				case 5:
				
					//remove it
					c.remove(rateParamOptionsTechPanel);
					
					rateParamInputWarningsPanel = new RateParamInputWarningsPanel(ds);
					
					//add the input warnings panel
					c.add(rateParamInputWarningsPanel, BorderLayout.CENTER);
					
					//set the current state of the input warnings panel
					rateParamInputWarningsPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(4);
					
					//break out of the loop
					break;

					
				case 6:
					
					//remove it
					c.remove(rateParamOptionsStartPanel);
					
					rateParamOptionsTechPanel = new RateParamOptionsTechPanel(ds);
					
					//add the input warnings panel
					c.add(rateParamOptionsTechPanel, BorderLayout.CENTER);
					
					//set the current state of the input warnings panel
					rateParamOptionsTechPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(5);
					
					//break out of the loop
					break;
					
				case 7:
					
					//remove it
					c.remove(rateParamOptionsModifyPanel);
					
					if(ds.getTechniqueParam().equals("MARQUARDT")){
					
						rateParamOptionsStartPanel = new RateParamOptionsStartPanel(ds);
					
						//add the input warnings panel
						c.add(rateParamOptionsStartPanel, BorderLayout.CENTER);
						
						//set the current state of the input warnings panel
						rateParamOptionsStartPanel.setCurrentState();
						
						//set the panel index
						setCurrentPanelIndex(6);
					
					}else{
					
						rateParamOptionsTechPanel = new RateParamOptionsTechPanel(ds);
					
						//add the input warnings panel
						c.add(rateParamOptionsTechPanel, BorderLayout.CENTER);
						
						//set the current state of the input warnings panel
						rateParamOptionsTechPanel.setCurrentState();
						
						//set the panel index
						setCurrentPanelIndex(5);
					
					}
					
					//break out of the loop
					break;	
				
				case 8:
					
					//remove it
					c.remove(rateParamOptionsOtherPanel);
					
					rateParamOptionsModifyPanel = new RateParamOptionsModifyPanel(ds);
					
					//add the input warnings panel
					c.add(rateParamOptionsModifyPanel, BorderLayout.CENTER);
					
					//set the current state of the input warnings panel
					rateParamOptionsModifyPanel.setCurrentState();
					
					//set the panel index
					setCurrentPanelIndex(7);
					
					//break out of the loop
					break;	
				
				case 9:
				
					if(!rateParamStatusPanel.statusLabel.getText().equals("Status Report: Program Running")){
						
						rateParamStatusPanel.statusTextArea.setText("");
						
						ds.setStatusText("");
						
						//remove it
						c.remove(rateParamStatusPanel);
						
						rateParamOptionsOtherPanel = new RateParamOptionsOtherPanel(ds);
						
						//add the program options panel
						c.add(rateParamOptionsOtherPanel, BorderLayout.CENTER);
						
						//set the current state of the program options panel
						rateParamOptionsOtherPanel.setCurrentState();
						
						//set the panel index
						setCurrentPanelIndex(8);
						
						continueButton.setEnabled(true);
						
					}else{
						
						String string = "You must abort program before going back.";
						Dialogs.createExceptionDialog(string, this);
					
					}
					
					//break out of the loop
					break;
				
				//If the param results panel is showing
				case 10:
				
					//remove it
					c.remove(rateParamResultsSummaryPanel);
					
					rateParamStatusPanel = new RateParamStatusPanel(ds);
					
					//add the param status panel
					c.add(rateParamStatusPanel, BorderLayout.CENTER);
					
					//set the current state of the param status panel
					rateParamStatusPanel.setCurrentState();
					
					rateParamStatusPanel.statusLabel.setText("Status Report: Program Complete");
					
					//set the panel index
					setCurrentPanelIndex(9);

					//break out of the loop
					break;
					
				//If the param results panel is showing
				case 11:
				
					//remove it
					c.remove(rateParamResultsVizPanel);
					
					rateParamResultsSummaryPanel = new RateParamResultsSummaryPanel(ds);
					
					//add the param status panel
					c.add(rateParamResultsSummaryPanel, BorderLayout.CENTER);
					
					//set the current state of the param status panel
					rateParamResultsSummaryPanel.setCurrentState();

					//set the panel index
					setCurrentPanelIndex(10);

					//break out of the loop
					break;
					
				//If the param results panel is showing
				case 12:
				
					//remove it
					c.remove(rateParamResultsOtherPanel);
					
					removeEndButtonPanel();
					addFullButtonPanel();
					
					rateParamResultsVizPanel = new RateParamResultsVizPanel(ds);
					
					//add the param status panel
					c.add(rateParamResultsVizPanel, BorderLayout.CENTER);
					
					//set the current state of the param status panel
					rateParamResultsVizPanel.setCurrentState();

					//set the panel index
					setCurrentPanelIndex(11);

					//break out of the loop
					break;
				
			}
			
			c.validate();
		
		}else if(ae.getSource()==endButton){
				
			//Call createSaveDialog with current panel index
        	createSaveDialog(currentPanelIndex);
	
		}else if(ae.getSource()==continueOnButton){

			//rateParamResultsPanel.getCurrentState();

			//If rateParamFrame has NOT been created
				if(Cina.rateManFrame==null){
					
					//and set frame properties
					Cina.rateManFrame = new RateManFrame(Cina.rateManDataStructure);
					Cina.rateManFrame.setResizable(true);
					Cina.rateManFrame.setSize(610, 435);
					Cina.rateManFrame.setVisible(true);
					Cina.rateManFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
					Cina.rateManFrame.setTitle("Rate Manager");

				//Else rateParamFrame has been created 
				}else{
					
					//then show it
					Cina.rateManFrame.setVisible(true);
					
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
						rateParamIDPanel.getCurrentState();
						
						//Break out of switch/case
						break;
					
					//If read input panel is showing
					case 2:
					
						//Get its state
						rateParamReadInputPanel.getCurrentState();
						
						//Break out of switch/case
						break;
				
					//If input check panel is showing
					case 3:
					
						//Store state of input check panel
						rateParamInputCheckPanel.getCurrentState();
						
						//Break out of switch/case
						break;
					
					//If input warnings panel is showing 
					case 4:
					
						//Get the state of it
						rateParamInputWarningsPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					//If param options panel is showing 
					case 5:
					
						//Get the state of it
						rateParamOptionsTechPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					//If param status panel is showing 
					case 6:
					
						//Get the state of it
						rateParamOptionsStartPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					case 7:
					
						//Get the state of it
						rateParamOptionsModifyPanel.getCurrentState();
						
						//Break out of switch/case
						break;	
						
					case 8:
					
						//Get the state of it
						rateParamOptionsOtherPanel.getCurrentState();
						
						//Break out of switch/case
						break;	
						
					case 9:
					
						//Get the state of it
						rateParamStatusPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					case 10:
					
						//Get the state of it
						rateParamResultsSummaryPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					case 11:
					
						//Get the state of it
						rateParamResultsVizPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
					case 12:
					
						//Get the state of it
						rateParamResultsOtherPanel.getCurrentState();
						
						//Break out of switch/case
						break;
						
				}
				
				//Close the dialog
				saveDialog.setVisible(false);
				saveDialog.dispose();
			
				//Set index to zero then when RateParam is reopened
				//it will start with the intro panel
				setCurrentPanelIndex(0);
				
				//Make the rate gen invisible 
         		setVisible(false);
         		
         		//Remove all compoenents from RateParamFrame
        		c.removeAll();
				
				//add the intro button panel
				addIntroButtonPanel();
        		
        		closeAllFrames();
        		
        		//Free system resources from frame
        		dispose();
        		
        		Cina.rateParamFrame = null;

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
        		
        		Cina.rateParamFrame = null;
        		
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
			if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateParamFrame)){
				
				//Remove it from the frame
				c.remove(rateParamReadInputPanel);
				
				//Create the input check panel
				rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);
				
				//Add it to the frame
				c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(rateParamInputCheckPlotFrame==null){
					
					rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(ds);
					rateParamInputCheckPlotFrame.setFormatPanelState();
				
				}else{
					
					rateParamInputCheckPlotFrame.setFormatPanelState();
					rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
					rateParamInputCheckPlotFrame.setVisible(true);
				
				}
				
				//Set its current state relative to the data structure
				rateParamInputCheckPanel.setCurrentState();
				
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
			if(Cina.cinaCGIComm.doCGICall("READ INPUT", Cina.rateParamFrame)){
				
				//Remove it from the frame
				c.remove(rateParamReadInputPanel);
				
				//Create the input check panel
				rateParamInputCheckPanel = new RateParamInputCheckPanel(ds);
				
				//Add it to the frame
				c.add(rateParamInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(rateParamInputCheckPlotFrame==null){
					
					rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(ds);
					rateParamInputCheckPlotFrame.setFormatPanelState();
				
				}else{
					
					rateParamInputCheckPlotFrame.setFormatPanelState();
					rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
					rateParamInputCheckPlotFrame.setVisible(true);
				
				}
				
				//Set its current state relative to the data structure
				rateParamInputCheckPanel.setCurrentState();
				
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
	
		if(rateParamChartFrame!=null){
			rateParamChartFrame.setVisible(false);
			rateParamChartFrame.dispose();
		}
	
		if(rateParamReadInputPasteFrame!=null){
			rateParamReadInputPasteFrame.setVisible(false);
			rateParamReadInputPasteFrame.dispose();
		}
	
		if(rateParamInputCheckPlotFrame!=null){
			rateParamInputCheckPlotFrame.setVisible(false);
			rateParamInputCheckPlotFrame.dispose();
		}
		
		if(rateParamInputCheckTableFrame!=null){
			rateParamInputCheckTableFrame.setVisible(false);
			rateParamInputCheckTableFrame.dispose();
		}
		
		if(rateParamResultsPlotFrame!=null){
			rateParamResultsPlotFrame.setVisible(false);
			rateParamResultsPlotFrame.dispose();
		}
		
		if(rateParamResultsFitFrame!=null){
			rateParamResultsFitFrame.setVisible(false);
			rateParamResultsFitFrame.dispose();
		}
		
		if(rateParamResultsPlotFrame!=null){
			rateParamResultsPlotFrame.setVisible(false);
			rateParamResultsPlotFrame.dispose();
			
			if(rateParamResultsTableFrame!=null){
				rateParamResultsTableFrame.setVisible(false);
				rateParamResultsTableFrame.dispose();
			}
			
		}
		
		if(rateParamResultsInfoFrame!=null){
			rateParamResultsInfoFrame.setVisible(false);
			rateParamResultsInfoFrame.dispose();	
		}
		
		if(rateParamResultsOutputFrame!=null){
			rateParamResultsOutputFrame.setVisible(false);
			rateParamResultsOutputFrame.dispose();
			
		}
		
		if(rateParamResultsTableFrame!=null){
			rateParamResultsTableFrame.setVisible(false);
			rateParamResultsTableFrame.dispose();
		}
		
		if(rateParamResultsEvalFrame!=null){
			rateParamResultsEvalFrame.setVisible(false);
			rateParamResultsEvalFrame.dispose();
			
		}
		
		if(rateParamResultsNotesFrame!=null){
			rateParamResultsNotesFrame.setVisible(false);
			rateParamResultsNotesFrame.dispose();
			
		}
		
		if(rateParamStartFrame!=null){
			rateParamStartFrame.setVisible(false);
			rateParamStartFrame.dispose();
			
			if(rateParamStartHelpFrame!=null){
				rateParamStartHelpFrame.setVisible(false);
				rateParamStartHelpFrame.dispose();
			}
			
		}
		
		if(rateParamPointFrame!=null){
			rateParamPointFrame.setVisible(false);
			rateParamPointFrame.dispose();
			
		}
		
	}
	
	/**
	 * Gets the low temp data array.
	 *
	 * @return the low temp data array
	 */
	public double[] getLowTempDataArray(){
	
		double currentTemp = tempGrid[0];
		
		int i = 0;
		
		while(currentTemp<ds.getTempminRT()){
	
			i++;
			
			currentTemp = tempGrid[i];		
		
		}
		
		double[] lowTempDataArray = new double[i+1];
		
		i = 0;
		
		currentTemp = tempGrid[0];
		
		while(currentTemp<ds.getTempminRT()){

			lowTempDataArray[i] = currentTemp;
			
			i++;
			
			currentTemp = tempGrid[i];			
		
		}
		
		lowTempDataArray[i] = ds.getTempminRT();
		
		return lowTempDataArray;

	}
	
	/**
	 * Gets the high temp data array.
	 *
	 * @return the high temp data array
	 */
	public double[] getHighTempDataArray(){
	
		double currentTemp = tempGrid[tempGrid.length - 1];
		
		int i = 0;
		
		while(currentTemp>ds.getTempmaxRT()){
	
			i++;
			
			currentTemp = tempGrid[tempGrid.length - 1 - i];
			
		}
		
		double[] highTempDataArray = new double[i+1];
				
		currentTemp = tempGrid[tempGrid.length - 1];
		
		i = 0;
		
		while(currentTemp>ds.getTempmaxRT()){
		
			highTempDataArray[i] = currentTemp;
			
			i++;
			
			currentTemp = tempGrid[tempGrid.length - 1 - i];
		
		}
		
		highTempDataArray[i] = ds.getTempmaxRT();
		
		return highTempDataArray;

	}
	
	/**
	 * Gets the low rate data array.
	 *
	 * @return the low rate data array
	 */
	public double[] getLowRateDataArray(){
	
		double[] lowRateDataArray = new double[ds.getLowTempDataArray().length];
	
		for(int i=0; i<ds.getLowTempDataArray().length; i++){
		
			lowRateDataArray[i] = Cina.cinaMainDataStructure.calcRate(ds.getLowTempDataArray()[i]
																					, ds.getRateDataStructure().getParameters()
																					, ds.getRateDataStructure().getNumberParameters());
																					
			if(String.valueOf(lowRateDataArray[i]).equals("Infinity")){
			
				lowRateDataArray[i] = Double.MAX_VALUE;
			
			}
			
			if(lowRateDataArray[i]==0.0){
			
				lowRateDataArray[i] = 1.0E-100;
			
			}

		}
		
		return lowRateDataArray;
	
	}
	
	/**
	 * Gets the high rate data array.
	 *
	 * @return the high rate data array
	 */
	public double[] getHighRateDataArray(){
	
		double[] highRateDataArray = new double[ds.getHighTempDataArray().length];
	
		for(int i=0; i<ds.getHighTempDataArray().length; i++){
		
			highRateDataArray[i] = Cina.cinaMainDataStructure.calcRate(ds.getHighTempDataArray()[i]
																					, ds.getRateDataStructure().getParameters()
																					, ds.getRateDataStructure().getNumberParameters());
																					
			if(String.valueOf(highRateDataArray[i]).equals("Infinity")){
			
				highRateDataArray[i] = Double.MAX_VALUE;
			
			}
			
			if(highRateDataArray[i]==0.0){
			
				highRateDataArray[i] = 1.0E-100;
			
			}
		
		}
		
		return highRateDataArray;
	
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
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Rate Parameterizer session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Rate Parameterizer session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Rate Parameterizer.", false);
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