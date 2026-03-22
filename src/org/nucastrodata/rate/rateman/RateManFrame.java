//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;


/**
 * The Class RateManFrame.
 */
public class RateManFrame extends JFrame implements WindowListener, ActionListener{
	
	/** The cancel button. */
	JButton continueButton, backButton, endButton, continueOnButton, yesButton, noButton, okButton, cancelButton;
	
	/** The choice button group. */
	ButtonGroup choiceButtonGroup;
	
	/** The save and close radio button. */
	JRadioButton saveAndCloseRadioButton;
	
	/** The close radio button. */
	JRadioButton closeRadioButton;

	/** The do not close radio button. */
	JRadioButton doNotCloseRadioButton;
	
	/** The submit button. */
	JButton submitButton;
		
	/** The end button panel. */
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	
	/** The invest rate radio button. */
	JRadioButton aboutRateRadioButton, createRateRadioButton, modifyRateRadioButton, rateErrorCheckRadioButton, rateGridRadioButton
					, investRateRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;
	
	/** The current panel index. */
	public static int currentPanelIndex;
	
	/** The current feature index. */
	public static int currentFeatureIndex;
		
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare dialog for closing rateGenFrame
	/** The caution dialog. */
	JDialog saveDialog, mockUpDialog, cautionDialog;

	/** The c. */
	public Container c;
	
	/** The radio button panel. */
	JPanel radioButtonPanel;
	
	/** The label3. */
	JLabel label1, label2, label3;
	
	/** The rate man about rate1 panel. */
	public RateManAboutRate1Panel rateManAboutRate1Panel;
	
	/** The rate man about rate2 panel. */
	public RateManAboutRate2Panel rateManAboutRate2Panel;
	
	/** The rate man create rate1 panel. */
	public RateManCreateRate1Panel rateManCreateRate1Panel;
	
	/** The rate man create rate2 panel. */
	public RateManCreateRate2Panel rateManCreateRate2Panel;
	
	/** The rate man create rate3 panel. */
	public RateManCreateRate3Panel rateManCreateRate3Panel;
	
	/** The rate man modify rate1 panel. */
	public RateManModifyRate1Panel rateManModifyRate1Panel;
	
	/** The rate man modify rate2 panel. */
	public RateManModifyRate2Panel rateManModifyRate2Panel;
	
	/** The rate man modify rate3 panel. */
	public RateManModifyRate3Panel rateManModifyRate3Panel;
	
	/** The rate man create rate plot frame. */
	public RateManCreateRatePlotFrame rateManCreateRatePlotFrame;
	
	/** The rate man modify rate plot frame. */
	public RateManModifyRatePlotFrame rateManModifyRatePlotFrame;
	
	/** The rate man create rate3 help frame. */
	public RateManCreateRate3HelpFrame rateManCreateRate3HelpFrame;
	
	/** The rate man rate grid1 panel. */
	public RateManRateGrid1Panel rateManRateGrid1Panel;
	
	/** The rate man rate grid2 panel. */
	public RateManRateGrid2Panel rateManRateGrid2Panel;
	
	/** The rate man rate grid3 panel. */
	public RateManRateGrid3Panel rateManRateGrid3Panel;
	
	/** The rate man custom grid frame. */
	public RateManCustomGridFrame rateManCustomGridFrame;
	
	/** The rate man rate error check1 panel. */
	public RateManRateErrorCheck1Panel rateManRateErrorCheck1Panel;
	
	/** The rate man rate error check2 panel. */
	public RateManRateErrorCheck2Panel rateManRateErrorCheck2Panel;
	
	/** The rate man invest rate1 panel. */
	public RateManInvestRate1Panel rateManInvestRate1Panel;
	
	/** The rate man invest rate2 za panel. */
	public RateManInvestRate2ZAPanel rateManInvestRate2ZAPanel;
	
	/** The rate man invest rate2 tree panel. */
	public RateManInvestRate2TreePanel rateManInvestRate2TreePanel;
	
	/** The rate man invest rate2 chart panel. */
	public RateManInvestRate2ChartPanel rateManInvestRate2ChartPanel;
	
	/** The rate man invest rate3 panel. */
	public RateManInvestRate3Panel rateManInvestRate3Panel;
	
	/** The rate man invest rate info frame. */
	public RateManInvestRateInfoFrame rateManInvestRateInfoFrame;
	
	/** The rate man invest rate plot frame. */
	public RateManInvestRatePlotFrame rateManInvestRatePlotFrame;
	
	/** The rate man invest rate table frame. */
	public RateManInvestRateTableFrame rateManInvestRateTableFrame;
	
	/** The rate man invest rate list frame. */
	public RateManInvestRateListFrame rateManInvestRateListFrame;
	
	/** The rate man invest rate view help frame. */
	public HelpFrame rateManInvestRateViewHelpFrame;
	
	/** The rate man invest rate3 help frame. */
	public HelpFrame rateManInvestRate3HelpFrame;
	
	/** The rate man about rate help frame. */
	public HelpFrame rateManAboutRateHelpFrame;
	
	/** The confirm dialog. */
	JDialog confirmDialog;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man frame.
	 *
	 * @param ds the ds
	 */
	public RateManFrame(RateManDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		radioButtonPanel = new JPanel(new GridBagLayout());
		
		aboutRateRadioButton = new JRadioButton("Rate Info", true);
		createRateRadioButton = new JRadioButton("Create New Rate", false);
		modifyRateRadioButton = new JRadioButton("Modify Existing Rate", false);
		rateGridRadioButton = new JRadioButton("Rates on a Grid", false);
		rateErrorCheckRadioButton = new JRadioButton("Rate Error Check", false);
		investRateRadioButton = new JRadioButton("Rate Locator", false);
	
		buttonGroup = new ButtonGroup();
	
		buttonGroup.add(aboutRateRadioButton);
		buttonGroup.add(createRateRadioButton);
		buttonGroup.add(modifyRateRadioButton);
		buttonGroup.add(rateGridRadioButton);
		buttonGroup.add(rateErrorCheckRadioButton);
		buttonGroup.add(investRateRadioButton);
				
		addRadioButtonPanel();
				
		//Create buttons and set properties
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Rate Manager Home");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Rate Commentor");
		continueOnButton.addActionListener(this);
		continueOnButton.setFont(Fonts.buttonFont);
		
		setCurrentPanelIndex(0);
		setCurrentFeatureIndex(0);

		//add the introButton JPanel
		addIntroButtonPanel();
		
		addWindowListener(this);
		
			

	}
	
	/**
	 * Removes the radio button panel.
	 */
	public void removeRadioButtonPanel(){c.remove(radioButtonPanel);}
	
	/**
	 * Adds the radio button panel.
	 */
	public void addRadioButtonPanel(){		
	
		label3 = new JLabel("Rate");
		label3.setFont(Fonts.bigTitleFont);
		
		label2 = new JLabel("Manager");
		label2.setFont(Fonts.bigTitleFont);
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 0, 5, 170);
		
		radioButtonPanel.add(label3, gbc);
		
		gbc.gridy = 1;
		
		radioButtonPanel.add(label2, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 120);
		gbc.anchor = GridBagConstraints.WEST;
		radioButtonPanel.add(aboutRateRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		radioButtonPanel.add(investRateRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		radioButtonPanel.add(createRateRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		
		radioButtonPanel.add(modifyRateRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		
		radioButtonPanel.add(rateGridRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		radioButtonPanel.add(rateErrorCheckRadioButton, gbc);
		
		c.add(radioButtonPanel, BorderLayout.EAST);
		
	}

	/**
	 * Sets the current panel index.
	 *
	 * @param index the new current panel index
	 */
	public void setCurrentPanelIndex(int index){
		currentPanelIndex = index;
	}
	
	/**
	 * Sets the current feature index.
	 *
	 * @param index the new current feature index
	 */
	public void setCurrentFeatureIndex(int index){
		currentFeatureIndex = index;
	}
	
	//Methdo actionPerformed
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		//If the continue button is pressed
		if(ae.getSource()==continueButton){
			
			//Switch on the panel index
			switch(currentFeatureIndex){
			
				case 0:
					
					removeRadioButtonPanel();
									
					if(aboutRateRadioButton.isSelected()){
						
						rateManAboutRate1Panel = new RateManAboutRate1Panel(ds);
						
						ds.setLibGroup("PUBLIC");
						boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("USER");
						boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
							rateManAboutRate1Panel.createLibGroupNodes();
								
							removeIntroButtonPanel();
						
							rateManAboutRate1Panel.setCurrentState();
						
							c.add(rateManAboutRate1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}else if(createRateRadioButton.isSelected()){
						
						rateManCreateRate1Panel = new RateManCreateRate1Panel(ds);
						
						removeIntroButtonPanel();
						
						rateManCreateRate1Panel.setCurrentState();
							
						c.add(rateManCreateRate1Panel, BorderLayout.CENTER);
						
						addFullButtonPanel();
					
					}else if(modifyRateRadioButton.isSelected()){
						
						rateManModifyRate1Panel = new RateManModifyRate1Panel(ds);
						
						ds.setLibGroup("PUBLIC");
						boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("USER");
						boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
							rateManModifyRate1Panel.createLibGroupNodes();
								
							removeIntroButtonPanel();
						
							rateManModifyRate1Panel.setCurrentState();
						
							c.add(rateManModifyRate1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}else if(rateGridRadioButton.isSelected()){
						
						rateManRateGrid1Panel = new RateManRateGrid1Panel(ds);
						
						removeIntroButtonPanel();
					
						rateManRateGrid1Panel.setCurrentState();
					
						c.add(rateManRateGrid1Panel, BorderLayout.CENTER);

						addFullButtonPanel();
					
					}else if(rateErrorCheckRadioButton.isSelected()){
						
						rateManRateErrorCheck1Panel = new RateManRateErrorCheck1Panel(ds);
						
						ds.setLibGroup("PUBLIC");
						boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						ds.setLibGroup("USER");
						boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
						
						if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
							rateManRateErrorCheck1Panel.createLibGroupNodes();
								
							removeIntroButtonPanel();
						
							rateManRateErrorCheck1Panel.setCurrentState();
						
							c.add(rateManRateErrorCheck1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}else if(investRateRadioButton.isSelected()){
					
						rateManInvestRate1Panel = new RateManInvestRate1Panel(ds);
						
						removeIntroButtonPanel();
						
						rateManInvestRate1Panel.setCurrentState();
					
						c.add(rateManInvestRate1Panel, BorderLayout.CENTER);

						addFullButtonPanel();
						
					}
					
					break;
				
				case 1:
				
					switch(currentPanelIndex){
					
						case 1:
					
							rateManAboutRate1Panel.getCurrentState();
							
							if(!rateManAboutRate1Panel.emptyRates()){
							
								rateManAboutRate1Panel.createRateDataStructureArray();
								
								ds.setRateProperties("Reaction String"
        																+ "\u0009Number of Parameters"
        																+ "\u0009Parameters"
        																+ "\u0009Resonant Components"
        																+ "\u0009Reaction Type"
        																+ "\u0009Biblio Code"
        																+ "\u0009Reaction Notes"
        																+ "\u0009Q-value"
        																+ "\u0009Chisquared"
        																+ "\u0009Creation Date"
        																+ "\u0009Max Percent Difference"
        																+ "\u0009Reference Citation"
        																+ "\u0009Valid Temp Range");
								
								if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
								
									removeFullButtonPanel();
								
									setSize(800, 600);
																	
									c.remove(rateManAboutRate1Panel);

									rateManAboutRate2Panel = new RateManAboutRate2Panel(ds);
									
									rateManAboutRate2Panel.setCurrentState();

									c.add(rateManAboutRate2Panel, BorderLayout.CENTER);
									
									validate();

									addEndButtonPanel();
								
								}
							
							}else{
							
								String string = "Please select at least one rate.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
					
							break;
						
					}
				
					break;
				
				case 2:
				
					switch(currentPanelIndex){
							
						case 1:
						
							if(rateManCreateRate1Panel.checkDataFields()){
								
								rateManCreateRate1Panel.getCurrentState();
								
								String cgiCommReactionString = rateManCreateRate1Panel.createCGICommReactionString(ds.getCreateRateDataStructure().getIsoIn()
																	, ds.getCreateRateDataStructure().getIsoOut()
																	, ds.getCreateRateDataStructure().getNumberReactants()
																	, ds.getCreateRateDataStructure().getNumberProducts());
		
								ds.setReaction(cgiCommReactionString);
								ds.setReaction_type(ds.getCreateRateDataStructure().getDecay());
								
								if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", Cina.rateManFrame)){
									
									createConfirmDialog(this);
								
								}
							
							}
										
							break;
							
						case 2:
					
							if(rateManCreateRate2Panel.checkDataFields()){
					
								rateManCreateRate2Panel.getCurrentState();
							
								removeFullButtonPanel();
							
								c.remove(rateManCreateRate2Panel);
							
								rateManCreateRate3Panel = new RateManCreateRate3Panel(ds);
								
								rateManCreateRate3Panel.setCurrentState();
			
								c.add(rateManCreateRate3Panel, BorderLayout.CENTER);
									
								addEndButtonPanel();	
							
							}
								
							break;
												
					}	
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
							
						case 1:
						
							if(rateManModifyRate1Panel.checkRateField()){
						
								rateManModifyRate1Panel.getCurrentState();
						
								ds.setRates(ds.getModifyRateDataStructure().getReactionID());

								ds.setRateProperties("Reaction String"
															+ "\u0009Number of Parameters"
															+ "\u0009Parameters"
															+ "\u0009Resonant Components"
															+ "\u0009Reaction Type"
															+ "\u0009Biblio Code"
															+ "\u0009Reaction Notes"
															+ "\u0009Q-value"
															+ "\u0009Chisquared"
															+ "\u0009Creation Date"
															+ "\u0009Max Percent Difference"
															+ "\u0009Reference Citation"
															+ "\u0009Valid Temp Range");
						
								if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
							
									c.remove(rateManModifyRate1Panel);
								
									rateManModifyRate2Panel = new RateManModifyRate2Panel(ds);
									
									rateManModifyRate2Panel.setCurrentState();
				
									c.add(rateManModifyRate2Panel, BorderLayout.CENTER);
								
								}
							
							}else{
							
								String string = "Please select a reaction rate.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
										
							break;
							
						case 2:
					
							if(rateManModifyRate2Panel.checkDataFields()){
					
								rateManModifyRate2Panel.getCurrentState();
							
								removeFullButtonPanel();
							
								c.remove(rateManModifyRate2Panel);
							
								rateManModifyRate3Panel = new RateManModifyRate3Panel(ds);
								
								rateManModifyRate3Panel.setCurrentState();
			
								c.add(rateManModifyRate3Panel, BorderLayout.CENTER);
									
								addEndButtonPanel();
							
							}	
								
							break;
												
					}	
				
					break;
				
				case 4:
				
					switch(currentPanelIndex){
					
						case 1:
					
							if(rateManRateGrid1Panel.checkTempGrid()){
					
								rateManRateGrid1Panel.getCurrentState();
								
								rateManRateGrid2Panel = new RateManRateGrid2Panel(ds);
								
								ds.setLibGroup("PUBLIC");
								boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("SHARED");
								boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("USER");
								boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
											
									c.remove(rateManRateGrid1Panel);
		
									rateManRateGrid2Panel.setCurrentState();
	
									rateManRateGrid2Panel.createLibGroupNodes();
	
									c.add(rateManRateGrid2Panel, BorderLayout.CENTER);
									
									validate();
									
								}
							
							}
						
							break;
							
						case 2:
							
							if(!rateManRateGrid2Panel.emptyRates()){
							
								rateManRateGrid2Panel.getCurrentState();
							
								rateManRateGrid2Panel.createRateDataStructureArray();
								
								ds.setRateProperties("Reaction String"
        																+ "\u0009Number of Parameters"
        																+ "\u0009Parameters"
        																+ "\u0009Resonant Components"
        																+ "\u0009Reaction Type"
        																+ "\u0009Biblio Code"
        																+ "\u0009Reaction Notes"
        																+ "\u0009Q-value"
        																+ "\u0009Chisquared"
        																+ "\u0009Creation Date"
        																+ "\u0009Max Percent Difference"
        																+ "\u0009Reference Citation"
        																+ "\u0009Valid Temp Range");
								
								if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
								
									removeFullButtonPanel();
									
									c.remove(rateManRateGrid2Panel);

									rateManRateGrid3Panel = new RateManRateGrid3Panel(ds);
									
									rateManRateGrid3Panel.setCurrentState();

									c.add(rateManRateGrid3Panel, BorderLayout.CENTER);
									
									validate();

									addEndButtonPanel();
								
								}
							
							}else{
							
								String string = "Please select at least one rate.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
							
							break;
						
					}
				
					break;
										
				case 5:
				
					switch(currentPanelIndex){
							
						case 1:
						
							rateManRateErrorCheck1Panel.getCurrentState();
						
							if(rateManRateErrorCheck1Panel.checkRateField()){
								
								ds.setRates(ds.getErrorCheckRateDataStructure().getReactionID());
								ds.setDEST_LIB("");
								ds.setProperties("");
								ds.setMake_inverse("NO");
								
								if(Cina.cinaCGIComm.doCGICall("MODIFY RATES", Cina.rateManFrame)){
						
									removeFullButtonPanel();
							
									c.remove(rateManRateErrorCheck1Panel);
								
									rateManRateErrorCheck2Panel = new RateManRateErrorCheck2Panel(ds);
									
									rateManRateErrorCheck2Panel.setCurrentState();
				
									addEndButtonPanel();
				
									c.add(rateManRateErrorCheck2Panel, BorderLayout.CENTER);
								
								}
							
							}else{
							
								String string = "Please select a reaction rate.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
										
							break;
												
					}	
				
					break;
					
				case 6:
				
					switch(currentPanelIndex){
							
						case 1:
						
							rateManInvestRate1Panel.getCurrentState();
						
							String selector = ds.getInvestRateSelector();
						
							if(selector.equals("ZAFIELDS")){
								
								rateManInvestRate2ZAPanel = new RateManInvestRate2ZAPanel(ds);
							
								c.remove(rateManInvestRate1Panel);
								
								rateManInvestRate2ZAPanel.setCurrentState();
			
								c.add(rateManInvestRate2ZAPanel, BorderLayout.CENTER);
								
							}else if(selector.equals("TREE")){
							
								rateManInvestRate2TreePanel = new RateManInvestRate2TreePanel(ds);
							
								ds.setLibGroup("PUBLIC");
								boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("SHARED");
								boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("USER");
								boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
									c.remove(rateManInvestRate1Panel);
	
									rateManInvestRate2TreePanel.createZNodes();
									
									rateManInvestRate2TreePanel.setCurrentState();
				
									c.add(rateManInvestRate2TreePanel, BorderLayout.CENTER);
								
								}	
							
							}else if(selector.equals("CHART")){
							
								setSize(825, 625);

								validate();

								rateManInvestRate2ChartPanel = new RateManInvestRate2ChartPanel(ds);
							
								ds.setLibGroup("PUBLIC");
								boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("SHARED");
								boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("USER");
								boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
									c.remove(rateManInvestRate1Panel);
									
									rateManInvestRate2ChartPanel.setCurrentState();
		
									c.add(rateManInvestRate2ChartPanel, BorderLayout.CENTER);
								
								}
							
							}
										
							break;
							
						case 2:
						
							selector = ds.getInvestRateSelector();
						
							if(selector.equals("ZAFIELDS")){
								
								if(rateManInvestRate2ZAPanel.checkDataFields()){
							
									rateManInvestRate2ZAPanel.getCurrentState();
							
									String cgiCommReactionString = rateManInvestRate2ZAPanel.createCGICommReactionString(ds.getInvestRateDataStructure().getIsoIn()
																	, ds.getInvestRateDataStructure().getIsoOut()
																	, ds.getInvestRateDataStructure().getNumberReactants()
																	, ds.getInvestRateDataStructure().getNumberProducts());
		
									ds.setReaction(cgiCommReactionString);
									ds.setReaction_type(ds.getInvestRateDataStructure().getDecay());
									
									if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", Cina.rateManFrame)){
										
										ds.setLibGroup("PUBLIC");
										boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
										
										ds.setLibGroup("SHARED");
										boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
										
										ds.setLibGroup("USER");
										boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
										
										if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
							
											ds.setRates(rateManInvestRate2ZAPanel.getRateIDList());
							
											if(Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateManFrame)){
										
												ds.setRates(rateManInvestRate2ZAPanel.getGoodRateIDList());

												ds.setRateProperties("Reaction String"
										        																+ "\u0009Number of Parameters"
										        																+ "\u0009Parameters"
										        																+ "\u0009Resonant Components"
										        																+ "\u0009Reaction Type"
										        																+ "\u0009Biblio Code"
										        																+ "\u0009Reaction Notes"
										        																+ "\u0009Q-value"
										        																+ "\u0009Chisquared"
										        																+ "\u0009Creation Date"
										        																+ "\u0009Max Percent Difference"
										        																+ "\u0009Reference Citation"
										        																+ "\u0009Valid Temp Range");
										
												if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
										
													rateManInvestRate2ZAPanel.getCurrentState();
												
													rateManInvestRate3Panel = new RateManInvestRate3Panel(ds);
												
													c.remove(rateManInvestRate2ZAPanel);
													
													rateManInvestRate3Panel.setInvestRateVectorArray();
													
													rateManInvestRate3Panel.setCurrentState();
								
													removeFullButtonPanel();
													
													addEndButtonPanel();
								
													c.add(rateManInvestRate3Panel, BorderLayout.CENTER);
												
												}
											
											}
										
										}
									
									}	
									
								}
								
							}else if(selector.equals("TREE")){
							
								if(rateManInvestRate2TreePanel.checkRateField()){
							
									ds.setRates(rateManInvestRate2TreePanel.getRateIDList());
							
									if(Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateManFrame)){
								
										ds.setRates(rateManInvestRate2TreePanel.getGoodRateIDList());

										ds.setRateProperties("Reaction String"
								        																+ "\u0009Number of Parameters"
								        																+ "\u0009Parameters"
								        																+ "\u0009Resonant Components"
								        																+ "\u0009Reaction Type"
								        																+ "\u0009Biblio Code"
								        																+ "\u0009Reaction Notes"
								        																+ "\u0009Q-value"
								        																+ "\u0009Chisquared"
								        																+ "\u0009Creation Date"
								        																+ "\u0009Max Percent Difference"
								        																+ "\u0009Reference Citation"
								        																+ "\u0009Valid Temp Range");
								
										if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
								
											rateManInvestRate2TreePanel.getCurrentState();
										
											rateManInvestRate3Panel = new RateManInvestRate3Panel(ds);
										
											c.remove(rateManInvestRate2TreePanel);
											
											rateManInvestRate3Panel.setInvestRateVectorArray();
											
											rateManInvestRate3Panel.setCurrentState();
						
											removeFullButtonPanel();
											
											addEndButtonPanel();
						
											c.add(rateManInvestRate3Panel, BorderLayout.CENTER);
										
										}
									
									}	
									
								}else{
								
									String string = "Please select a reaction rate.";
									
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}else if(selector.equals("CHART")){
							
								if(rateManInvestRate2ChartPanel.checkRateField()){
							
									ds.setRates(rateManInvestRate2ChartPanel.getRateIDList());
							
									if(Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateManFrame)){
								
										ds.setRates(rateManInvestRate2ChartPanel.getGoodRateIDList());

										ds.setRateProperties("Reaction String"
								        																+ "\u0009Number of Parameters"
								        																+ "\u0009Parameters"
								        																+ "\u0009Resonant Components"
								        																+ "\u0009Reaction Type"
								        																+ "\u0009Biblio Code"
								        																+ "\u0009Reaction Notes"
								        																+ "\u0009Q-value"
								        																+ "\u0009Chisquared"
								        																+ "\u0009Creation Date"
								        																+ "\u0009Max Percent Difference"
								        																+ "\u0009Reference Citation"
								        																+ "\u0009Valid Temp Range");
								
										if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){
								
											setSize(610, 435);
								
											validate();
								
											rateManInvestRate2ChartPanel.getCurrentState();
										
											rateManInvestRate3Panel = new RateManInvestRate3Panel(ds);
										
											c.remove(rateManInvestRate2ChartPanel);
											
											rateManInvestRate3Panel.setInvestRateVectorArray();
											
											rateManInvestRate3Panel.setCurrentState();
						
											removeFullButtonPanel();
											
											addEndButtonPanel();
						
											c.add(rateManInvestRate3Panel, BorderLayout.CENTER);
										
										}
									
									}	
									
								}else{
								
									String string = "Please select a reaction rate.";
									
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}
										
							break;
												
					}	
				
					break;
				
			}
			
			c.validate();
			
		//Else if back button hit	
		}else if(ae.getSource()==backButton){

				//Switch on the panel index
			switch(currentFeatureIndex){
			
				case 1:
					
					switch(currentPanelIndex){
					
						case 1:
						
							rateManAboutRate1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManAboutRate1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							removeEndButtonPanel();
							
							c.remove(rateManAboutRate2Panel);
							
							addFullButtonPanel();
							
							rateManAboutRate1Panel = new RateManAboutRate1Panel(ds);
							
							rateManAboutRate1Panel.createLibGroupNodes();
							
							rateManAboutRate1Panel.setCurrentState();
							
							setSize(578, 418);
							
							c.add(rateManAboutRate1Panel, BorderLayout.CENTER);
							
							validate();
							
							setCurrentPanelIndex(1);
						
							break;
							
					}
					
					break;
					
				case 2:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateManCreateRate1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManCreateRate1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							c.remove(rateManCreateRate2Panel);
							
							rateManCreateRate1Panel = new RateManCreateRate1Panel(ds);
							
							rateManCreateRate1Panel.setCurrentState();
							
							c.add(rateManCreateRate1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);
						
							break;
							
						case 3:
							
							c.remove(rateManCreateRate3Panel);
							
							removeEndButtonPanel();
							
							rateManCreateRate2Panel = new RateManCreateRate2Panel(ds);
							
							rateManCreateRate2Panel.setCurrentState();
							
							c.add(rateManCreateRate2Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							setCurrentPanelIndex(2);
						
							break;
							
					
					}	
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateManModifyRate1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManModifyRate1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							c.remove(rateManModifyRate2Panel);
							
							rateManModifyRate1Panel = new RateManModifyRate1Panel(ds);
							
							rateManModifyRate1Panel.createLibGroupNodes();
							
							rateManModifyRate1Panel.setCurrentState();
							
							c.add(rateManModifyRate1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);
						
							break;
							
						case 3:
							
							c.remove(rateManModifyRate3Panel);
							
							removeEndButtonPanel();
							
							rateManModifyRate2Panel = new RateManModifyRate2Panel(ds);

							rateManModifyRate2Panel.setCurrentState();
							
							addFullButtonPanel();
							
							c.add(rateManModifyRate2Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(2);
						
							break;
							
					}
					
					break;
				
				case 4:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateManRateGrid1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManRateGrid1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:

							c.remove(rateManRateGrid2Panel);
							
							rateManRateGrid1Panel = new RateManRateGrid1Panel(ds);

							rateManRateGrid1Panel.setCurrentState();
							
							c.add(rateManRateGrid1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);

							break;
							
						case 3:

							removeEndButtonPanel();

							c.remove(rateManRateGrid3Panel);
							
							rateManRateGrid2Panel = new RateManRateGrid2Panel(ds);

							rateManRateGrid2Panel.createLibGroupNodes();

							rateManRateGrid2Panel.setCurrentState();
							
							c.add(rateManRateGrid2Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							setCurrentPanelIndex(2);

							break;
							
					}
					
					break;	
				
				case 5:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateManRateErrorCheck1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManRateErrorCheck1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							c.remove(rateManRateErrorCheck2Panel);
							
							removeEndButtonPanel();
							
							rateManRateErrorCheck1Panel = new RateManRateErrorCheck1Panel(ds);
							
							rateManRateErrorCheck1Panel.createLibGroupNodes();
							
							rateManRateErrorCheck1Panel.setCurrentState();
							
							addFullButtonPanel();
							
							c.add(rateManRateErrorCheck1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);
						
							break;
							
					}
					
					break;	
					
				case 6:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateManInvestRate1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateManInvestRate1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							String selector = ds.getInvestRateSelector();
						
							if(selector.equals("ZAFIELDS")){
								
								c.remove(rateManInvestRate2ZAPanel);
								
								rateManInvestRate1Panel = new RateManInvestRate1Panel(ds);
								
								rateManInvestRate1Panel.setCurrentState();
								
								c.add(rateManInvestRate1Panel, BorderLayout.CENTER);
								
								setCurrentPanelIndex(1);
								
							}else if(selector.equals("TREE")){
							
								c.remove(rateManInvestRate2TreePanel);
								
								rateManInvestRate1Panel = new RateManInvestRate1Panel(ds);
								
								rateManInvestRate1Panel.setCurrentState();
								
								c.add(rateManInvestRate1Panel, BorderLayout.CENTER);
								
								setCurrentPanelIndex(1);	
							
							}else if(selector.equals("CHART")){
							
								c.remove(rateManInvestRate2ChartPanel);
								
								setSize(610, 435);
								
								validate();
								
								rateManInvestRate1Panel = new RateManInvestRate1Panel(ds);
								
								rateManInvestRate1Panel.setCurrentState();
								
								c.add(rateManInvestRate1Panel, BorderLayout.CENTER);
								
								setCurrentPanelIndex(1);
							
							}
							
							break;
							
						case 3:
							
							selector = ds.getInvestRateSelector();
							
							if(selector.equals("ZAFIELDS")){
								
								rateManInvestRate2ZAPanel = new RateManInvestRate2ZAPanel(ds);
							
								c.remove(rateManInvestRate3Panel);

								removeEndButtonPanel();
								
								rateManInvestRate2ZAPanel.setCurrentState();
			
								addFullButtonPanel();
			
								c.add(rateManInvestRate2ZAPanel, BorderLayout.CENTER);
															
								setCurrentPanelIndex(2);
								
							}else if(selector.equals("TREE")){
	
								rateManInvestRate2TreePanel = new RateManInvestRate2TreePanel(ds);
							
								ds.setLibGroup("PUBLIC");
								boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("SHARED");
								boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								ds.setLibGroup("USER");
								boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
								
								if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
	
									c.remove(rateManInvestRate3Panel);
	
									removeEndButtonPanel();
	
									rateManInvestRate2TreePanel.createZNodes();
									
									rateManInvestRate2TreePanel.setCurrentState();
				
									addFullButtonPanel();
				
									c.add(rateManInvestRate2TreePanel, BorderLayout.CENTER);
								
								}	
								
								setCurrentPanelIndex(2);	
							
							}else if(selector.equals("CHART")){
							
								setSize(825, 625);

								validate();
							
								rateManInvestRate2ChartPanel = new RateManInvestRate2ChartPanel(ds);
	
								c.remove(rateManInvestRate3Panel);

								removeEndButtonPanel();
								
								rateManInvestRate2ChartPanel.setCurrentState();
			
								addFullButtonPanel();
			
								c.add(rateManInvestRate2ChartPanel, BorderLayout.CENTER);
								
								setCurrentPanelIndex(2);
							
							}
							
							break;
							
					}
					
					break;	
					
			}
		
			c.validate();
	
		}else if(ae.getSource()==endButton){
			
			removeEndButtonPanel();
			
			switch(currentFeatureIndex){
			
				case 1:
				
					rateManAboutRate2Panel.setVisible(false);
					
					c.remove(rateManAboutRate2Panel);
					
					break;
					
				case 2:
				
					rateManCreateRate3Panel.setVisible(false);
					
					c.remove(rateManCreateRate3Panel);
					
					break;
					
				case 3:
				
					rateManModifyRate3Panel.setVisible(false);
					
					c.remove(rateManModifyRate3Panel);
					
					break;
					
				case 4:
				
					rateManRateGrid3Panel.setVisible(false);
					
					c.remove(rateManRateGrid3Panel);
					
					break;	
				
				case 5:
				
					rateManRateErrorCheck2Panel.setVisible(false);
					
					c.remove(rateManRateErrorCheck2Panel);
					
					break;
					
				case 6:
				
					rateManInvestRate3Panel.setVisible(false);
					
					c.remove(rateManInvestRate3Panel);
					
					break;
			
			}

			setCurrentFeatureIndex(0);
			
			addRadioButtonPanel();
			
			addIntroButtonPanel();
        	
        	setSize(578, 418);
        	
        	validate();
        
    	}else if(ae.getSource()==continueOnButton){
    		
			//got to rate com
			
       	}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
		}else if(ae.getSource()==submitButton){

			//If "Save info and close Rate Gen" is chosen
			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				switch(currentFeatureIndex){
			
					case 1:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManAboutRate1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateManAboutRate2Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 2:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManCreateRate1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateManCreateRate2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								rateManCreateRate3Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 3:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManModifyRate1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateManModifyRate2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								rateManModifyRate3Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 4:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManRateGrid1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateManRateGrid2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								rateManRateGrid3Panel.getCurrentState();
							
								break;
								
						}
					
						break;
					
					case 5:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManRateErrorCheck1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateManRateErrorCheck2Panel.getCurrentState();
							
								break;
								
						}
					
						break;
											
					case 6:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateManInvestRate1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								String selector = ds.getInvestRateSelector();
							
								if(selector.equals("ZAFIELDS")){
									
									rateManInvestRate2ZAPanel.getCurrentState();
									
								}else if(selector.equals("TREE")){
		
									rateManInvestRate2TreePanel.getCurrentState();
								
								}else if(selector.equals("CHART")){
								
									rateManInvestRate2ChartPanel.getCurrentState();
								
								}
							
								break;
								
							case 3:
							
								rateManInvestRate3Panel.getCurrentState();
							
								break;
								
						}
					
						break;
				
				}
				
				closeAllFrames();
				
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

        		//Free system resources from frame
        		dispose();
        		
        		Cina.rateManFrame = null;

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
        		
        		Cina.rateManFrame = null;
        	
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
						
			c.remove(rateManCreateRate1Panel);
		
			rateManCreateRate2Panel = new RateManCreateRate2Panel(ds);
			
			rateManCreateRate2Panel.setCurrentState();

			c.add(rateManCreateRate2Panel, BorderLayout.CENTER);
					
		}else if(ae.getSource()==cancelButton){
		
			confirmDialog.setVisible(false);
			confirmDialog.dispose();
		
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
	
	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){

		if(rateManCreateRatePlotFrame!=null){
			rateManCreateRatePlotFrame.setVisible(false);
			rateManCreateRatePlotFrame.dispose();
		}
		
		if(rateManCreateRate3HelpFrame!=null){
			rateManCreateRate3HelpFrame.setVisible(false);
			rateManCreateRate3HelpFrame.dispose();
		}
		
		if(rateManModifyRatePlotFrame!=null){
			rateManModifyRatePlotFrame.setVisible(false);
			rateManModifyRatePlotFrame.dispose();
		}
		
		if(rateManCustomGridFrame!=null){
			rateManCustomGridFrame.setVisible(false);
			rateManCustomGridFrame.dispose();
		}
		
		if(rateManInvestRateInfoFrame!=null){
			rateManInvestRateInfoFrame.setVisible(false);
			rateManInvestRateInfoFrame.dispose();
		}
		
		if(rateManInvestRateListFrame!=null){
			rateManInvestRateListFrame.setVisible(false);
			rateManInvestRateListFrame.dispose();
		}
		
		if(rateManInvestRate3HelpFrame!=null){
			rateManInvestRate3HelpFrame.setVisible(false);
			rateManInvestRate3HelpFrame.dispose();
		}
		
		if(rateManInvestRatePlotFrame!=null){
			
			if(rateManInvestRateTableFrame!=null){
				rateManInvestRateTableFrame.setVisible(false);
				rateManInvestRateTableFrame.dispose();
			}
			
			rateManInvestRatePlotFrame.setVisible(false);
			rateManInvestRatePlotFrame.dispose();
			
		}

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
    
    /**
     * Creates the mock up dialog.
     *
     * @param string the string
     * @param frame the frame
     */
    public void createMockUpDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	mockUpDialog = new JDialog(frame, "Attention!", true);
    	mockUpDialog.setSize(350, 210);
    	mockUpDialog.getContentPane().setLayout(new GridBagLayout());
		mockUpDialog.setLocationRelativeTo(frame);
		
		JTextArea mockUpTextArea = new JTextArea("", 5, 30);
		mockUpTextArea.setLineWrap(true);
		mockUpTextArea.setWrapStyleWord(true);
		mockUpTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(mockUpTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		mockUpTextArea.setText(string);
		mockUpTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					mockUpDialog.setVisible(false);
					mockUpDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		mockUpDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		mockUpDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(mockUpDialog);
		
		//Show the dialog
		mockUpDialog.setVisible(true);
	
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
    	saveDialog = new JDialog(this, "Rate Manager Exit", true);
    	saveDialog.setSize(525, 199);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Rate Manager session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Rate Manager session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Rate Manager.", false);
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

		JLabel label = new JLabel("<html>You have chosen the reaction <br>" 
									+ ds.getCreateRateDataStructure().getReactionString()
									+ "<br>Is this correct?</html>");
									
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
}