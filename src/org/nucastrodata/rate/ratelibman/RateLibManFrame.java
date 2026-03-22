package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDirectoryDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.rate.ratelibman.listener.RateLibManGetLibDirsListener;
import org.nucastrodata.rate.ratelibman.worker.RateLibManGetLibDirsWorker;
import org.nucastrodata.rate.rateviewer.RateViewerFrame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;

public class RateLibManFrame extends JFrame implements WindowListener, ActionListener, RateLibManGetLibDirsListener{
	
	JButton continueButton, backButton, endButton, continueOnButton, yesButton, noButton, yesButtonBaseLib, noButtonBaseLib;
	ButtonGroup choiceButtonGroup;
	JRadioButton saveAndCloseRadioButton;
	JRadioButton closeRadioButton;
	JRadioButton doNotCloseRadioButton;
	JButton submitButton;
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	JRadioButton aboutLibRadioButton, modifyLibRadioButton, customLibCreationRadioButton, 
						libErrorCheckRadioButton, sharedLibRadioButton, eraseLibRadioButton, 
						exportLibRadioButton, importLibRadioButton, eraseLibDirRadioButton, 
						aboutLibDirRadioButton;
	
	ButtonGroup buttonGroup;
	String mergeResultsString = "";
	String libDescriptorString = "";
	static int currentPanelIndex;
	static int currentFeatureIndex;
	GridBagConstraints gbc;
	JDialog saveDialog, mockUpDialog, cautionDialogBaseLib, cautionDialog, delayDialog;
	Container c;
	JPanel radioButtonPanel;
	JLabel label1, label2, label3;
	public RateLibManAboutLibPanel rateLibManAboutLibPanel;
	public RateLibManModifyLib1Panel rateLibManModifyLib1Panel;
	public RateLibManModifyLib2Panel rateLibManModifyLib2Panel;
	public RateLibManModifyLib3Panel rateLibManModifyLib3Panel;
	public RateLibManCustomLibCreation1Panel rateLibManCustomLibCreation1Panel;
	public RateLibManCustomLibCreation2Panel rateLibManCustomLibCreation2Panel;
	public RateLibManLibErrorCheck1Panel rateLibManLibErrorCheck1Panel;
	public RateLibManLibErrorCheck2Panel rateLibManLibErrorCheck2Panel;
	public RateLibManEraseLibPanel rateLibManEraseLibPanel;
	public RateLibManSharedLibPanel rateLibManSharedLibPanel;
	public RateLibManExportLibPanel rateLibManExportLibPanel;
	public RateLibManImportLib1Panel rateLibManImportLib1Panel;
	public RateLibManImportLib2Panel rateLibManImportLib2Panel;
	public RateLibManEraseLibDirPanel rateLibManEraseLibDirPanel;
	public RateLibManAboutLibDirPanel rateLibManAboutLibDirPanel;
	private RateLibManDataStructure ds;

	public RateLibManFrame(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		radioButtonPanel = new JPanel(new GridBagLayout());
		
		aboutLibRadioButton = new JRadioButton("Library Info", true);
		modifyLibRadioButton = new JRadioButton("Create and Modify Library", false);
		customLibCreationRadioButton = new JRadioButton("Merge Existing Libraries", false);
		libErrorCheckRadioButton = new JRadioButton("Library Error Check", false);
		sharedLibRadioButton = new JRadioButton("Move Library to Shared Folder", false);
		eraseLibRadioButton = new JRadioButton("Erase Library", false);
		eraseLibDirRadioButton = new JRadioButton("Erase Library Directory", false);
		exportLibRadioButton = new JRadioButton("Export Library", false);
		importLibRadioButton = new JRadioButton("Import One or More Libraries", false);
		aboutLibDirRadioButton = new JRadioButton("Library Directory Info", false);
	
		buttonGroup = new ButtonGroup();
	
		buttonGroup.add(aboutLibRadioButton);
		buttonGroup.add(modifyLibRadioButton);
		buttonGroup.add(customLibCreationRadioButton);
		buttonGroup.add(libErrorCheckRadioButton);
		buttonGroup.add(sharedLibRadioButton);
		buttonGroup.add(eraseLibRadioButton);
		buttonGroup.add(exportLibRadioButton);
		if(Cina.cinaMainDataStructure.isMasterUser()){
			buttonGroup.add(importLibRadioButton);
			buttonGroup.add(eraseLibDirRadioButton);
			buttonGroup.add(aboutLibDirRadioButton);
		}
				
		addRadioButtonPanel();
				
		//Create buttons and set properties
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Rate Library Manager Home");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Rate Viewer");
		continueOnButton.addActionListener(this);
		continueOnButton.setFont(Fonts.buttonFont);
		
		setCurrentPanelIndex(0);
		setCurrentFeatureIndex(0);

		addIntroButtonPanel();
		addWindowListener(this);	

	}
	
	public void removeRadioButtonPanel(){c.remove(radioButtonPanel);}
	
	public void addRadioButtonPanel(){		
	
		label3 = new JLabel("Rate");
		label3.setFont(Fonts.bigTitleFont);
	
		label1 = new JLabel("Library");
		label1.setFont(Fonts.bigTitleFont);
		
		label2 = new JLabel("Manager");
		label2.setFont(Fonts.bigTitleFont);
	
		if(Cina.cinaMainDataStructure.isMasterUser()){
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.insets = new Insets(5, 0, 5, 170);
			
			radioButtonPanel.add(label3, gbc);
			
			gbc.gridy = 1;
			
			radioButtonPanel.add(label1, gbc);
		
			gbc.gridy = 2;
			
			radioButtonPanel.add(label2, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 0, 5, 30);
			gbc.anchor = GridBagConstraints.WEST;
			radioButtonPanel.add(aboutLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			
			radioButtonPanel.add(modifyLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 2;
			
			radioButtonPanel.add(customLibCreationRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			
			radioButtonPanel.add(eraseLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 4;
			
			radioButtonPanel.add(sharedLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			
			radioButtonPanel.add(exportLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			
			radioButtonPanel.add(libErrorCheckRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 7;
			
			radioButtonPanel.add(aboutLibDirRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 8;
			
			radioButtonPanel.add(importLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 9;
			
			radioButtonPanel.add(eraseLibDirRadioButton, gbc);
			
		}else{
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.insets = new Insets(5, 0, 5, 170);
			
			radioButtonPanel.add(label3, gbc);
			
			gbc.gridy = 1;
			
			radioButtonPanel.add(label1, gbc);
		
			gbc.gridy = 2;
			
			radioButtonPanel.add(label2, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 0, 5, 30);
			gbc.anchor = GridBagConstraints.WEST;
			radioButtonPanel.add(aboutLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			
			radioButtonPanel.add(modifyLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 2;
			
			radioButtonPanel.add(customLibCreationRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			
			radioButtonPanel.add(eraseLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 4;
			
			radioButtonPanel.add(sharedLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			
			radioButtonPanel.add(exportLibRadioButton, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			radioButtonPanel.add(libErrorCheckRadioButton, gbc);
			
		}

		c.add(radioButtonPanel, BorderLayout.EAST);
		
	}

	public void setCurrentPanelIndex(int index){
		currentPanelIndex = index;
	}

	public void setCurrentFeatureIndex(int index){
		currentFeatureIndex = index;
	}
	
	public void actionPerformed(ActionEvent ae){
		
		//If the continue button is pressed
		if(ae.getSource()==continueButton){
			
			//Switch on the panel index
			switch(currentFeatureIndex){
			
				case 0:
					
					removeRadioButtonPanel();
									
					if(aboutLibRadioButton.isSelected()){
					
						rateLibManAboutLibPanel = new RateLibManAboutLibPanel(ds);
	
						ds.setLibGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
							removeIntroButtonPanel();
						
							rateLibManAboutLibPanel.setCurrentState();
						
							c.add(rateLibManAboutLibPanel, BorderLayout.CENTER);
	
							addEndButtonPanel();
						
						}
					
					}else if(modifyLibRadioButton.isSelected()){
						
							rateLibManModifyLib1Panel = new RateLibManModifyLib1Panel(ds);
							
							ds.setLibGroup("PUBLIC");
							boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
							
							ds.setLibGroup("SHARED");
							boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
							
							ds.setLibGroup("USER");
							boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
							
							if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
								removeIntroButtonPanel();
								
								rateLibManModifyLib1Panel.setCurrentState();
									
								c.add(rateLibManModifyLib1Panel, BorderLayout.CENTER);
								
								addFullButtonPanel();
							
							}
					
					}else if(customLibCreationRadioButton.isSelected()){
					
						rateLibManCustomLibCreation1Panel = new RateLibManCustomLibCreation1Panel(ds);
					
						ds.setLibGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					
							removeIntroButtonPanel();
							
							rateLibManCustomLibCreation1Panel.setCurrentState();
							
							c.add(rateLibManCustomLibCreation1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
						}
					
					}else if(libErrorCheckRadioButton.isSelected()){
					
						rateLibManLibErrorCheck1Panel = new RateLibManLibErrorCheck1Panel(ds);
					
						ds.setLibGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					
							removeIntroButtonPanel();
								
							rateLibManLibErrorCheck1Panel.setCurrentState();
								
							c.add(rateLibManLibErrorCheck1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
						}
					
					}else if(eraseLibRadioButton.isSelected()){
					
						rateLibManEraseLibPanel = new RateLibManEraseLibPanel(ds);
			
						ds.setLibGroup("USER");
					
						if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this)){
					
							removeIntroButtonPanel();
								
							rateLibManEraseLibPanel.setCurrentState();
								
							c.add(rateLibManEraseLibPanel, BorderLayout.CENTER);
							
							addEndButtonPanel();
							
						}
						
					}else if(eraseLibDirRadioButton.isSelected()){
							
						PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
						dialog.open();
						RateLibManGetLibDirsWorker worker = new RateLibManGetLibDirsWorker(ds, this, dialog, this);
						worker.execute();
						break;
							
					}else if(aboutLibDirRadioButton.isSelected()){
							
						PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
						dialog.open();
						RateLibManGetLibDirsAboutWorker worker = new RateLibManGetLibDirsAboutWorker(ds, this, dialog);
						worker.execute();
						break;
					
					}else if(sharedLibRadioButton.isSelected()){
					
						rateLibManSharedLibPanel = new RateLibManSharedLibPanel(ds);
					
						ds.setLibGroup("USER");
					
						if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this)){
					
							removeIntroButtonPanel();
								
							rateLibManSharedLibPanel.setCurrentState();
								
							c.add(rateLibManSharedLibPanel, BorderLayout.CENTER);
							
							addEndButtonPanel();
							
						}
					
					}else if(exportLibRadioButton.isSelected()){
					
						rateLibManExportLibPanel = new RateLibManExportLibPanel(ds);
			
						removeIntroButtonPanel();
							
						rateLibManExportLibPanel.setCurrentState();
							
						c.add(rateLibManExportLibPanel, BorderLayout.CENTER);
						
						addEndButtonPanel();
					
					}else if(importLibRadioButton.isSelected()){
					
						rateLibManImportLib1Panel = new RateLibManImportLib1Panel(this, ds, Cina.cinaMainDataStructure);
						
						ds.setLibGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						ds.setLibGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					
							removeIntroButtonPanel();
						
							rateLibManImportLib1Panel.setCurrentState();
							
							c.add(rateLibManImportLib1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
						
						}
					
					}
					
					break;
					
				case 2:
				
					switch(currentPanelIndex){
							
						case 1:
						
							rateLibManModifyLib1Panel.getCurrentState();
						
							if(rateLibManModifyLib1Panel.checkSaveAsLibField()){
						
								if(rateLibManModifyLib1Panel.checkPublicLibName()){
						
									if(rateLibManModifyLib1Panel.checkUserLibName()){
						
										if(rateLibManModifyLib1Panel.checkOverwriteLibName()){
								
											if(rateLibManModifyLib1Panel.goodLibInfoInverseCheck()){

												if(ds.getCurrentLibraryDataStructure().getLibType().equals("USER")){
								
													if(ds.getCurrentLibraryDataStructure().getAllInversesPresent()){
									
														c.remove(rateLibManModifyLib1Panel);
													
														rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
														
														rateLibManModifyLib2Panel.setCurrentState();
									
														c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
														
													}else{
							
														String string = "The base library you have selected does not have a complete set of "
																			+ "reaction rates as determined by detailed balance (inverse rates)."
																			+ " This is a requirement for a base library. Do you want to calculate these reactions and add them to this library?";
																			
														createCautionDialogBaseLib(string, this);					
													
													}
												
												}else{
												
													c.remove(rateLibManModifyLib1Panel);
													
													rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
													
													rateLibManModifyLib2Panel.setCurrentState();
								
													c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
												
												}
											
											}
											
										
										}else{
										
											String string = "You have specified the name of an existing library. Do you want to overwrite this library?";
									
											createCautionDialog(string, this);
										
										}
										
									}else{
										
										String string = "You have specified the name of the chosen base library. Please choose another library name.";
								
										Dialogs.createExceptionDialog(string, this);
									
									}
									
								}else{
								
									String string = "You have specified the name of a public or shared reaction rate library. These can not overwritten.";
							
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}else{
							
								String string = "You must specify a library name.";
							
								Dialogs.createExceptionDialog(string, this);
							
							}

							break;
							
						case 2:
					
							rateLibManModifyLib2Panel.getCurrentState();
							
							if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
								
								if(rateLibManModifyLib2Panel.delayDialog==null){
								
									rateLibManModifyLib2Panel.openDelayDialog(this);
	
									rateLibManModifyLib2Panel.goodRateMerge();
								
								}
								
							}else{
								
								removeFullButtonPanel();
									
								c.remove(rateLibManModifyLib2Panel);
							
								rateLibManModifyLib3Panel = new RateLibManModifyLib3Panel(ds);
			
								rateLibManModifyLib3Panel.setCurrentState();
			
								c.add(rateLibManModifyLib3Panel, BorderLayout.CENTER);
								
								addEndButtonPanel();
								
							}
									
							break;
												
					}	
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
							
							rateLibManCustomLibCreation1Panel.getCurrentState();
							
							if(rateLibManCustomLibCreation1Panel.checkSaveAsLibField()){
						
								if(rateLibManCustomLibCreation1Panel.checkBaseLibs()){
						
									if(rateLibManCustomLibCreation1Panel.checkPublicLibName()){
							
										if(rateLibManCustomLibCreation1Panel.checkOverwriteLibName()){
											
											if(rateLibManCustomLibCreation1Panel.ratesSelected()){
											
												if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
											
													if(rateLibManCustomLibCreation1Panel.delayDialog==null){
											
														rateLibManCustomLibCreation1Panel.openDelayDialog(this);
	
														rateLibManCustomLibCreation1Panel.goodLibMerge();
													
													}
												
												}else{
												
													removeFullButtonPanel();
													
													c.remove(rateLibManCustomLibCreation1Panel);
												
													rateLibManCustomLibCreation2Panel = new RateLibManCustomLibCreation2Panel(ds);
												
													rateLibManCustomLibCreation2Panel.setCurrentState();
								
													c.add(rateLibManCustomLibCreation2Panel, BorderLayout.CENTER);
													
													addEndButtonPanel();
												
												}
												
											}else{
											
												String string = "Please choose at least two libraries to merge.";
								
												Dialogs.createExceptionDialog(string, this);
											
											}
											
										}else{
										
											String string = "You have specified the name of an existing library. Do you want to overwrite this library?";
									
											createCautionDialog(string, this);
										
										}
										
									}else{
									
										String string = "You have specified the name of a public or shared reaction rate library. These can not be overwritten.";
								
										Dialogs.createExceptionDialog(string, this);
									
									}
									
								}else{
								
									String string = "You have chosen a name for your new library that is the same as a base library. Please choose another name.";
								
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}else{
							
								String string = "You must specify a library name.";
							
								Dialogs.createExceptionDialog(string, this);
							
							}
							
							break;
							
					}	
				
					break;
					
				case 4:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateLibManLibErrorCheck1Panel.getCurrentState();
							
							if(rateLibManLibErrorCheck1Panel.delayDialog==null){
							
								rateLibManLibErrorCheck1Panel.openDelayDialog(this);
								
								rateLibManLibErrorCheck1Panel.goodErrorCheckLib();
							
							}
						
							break;
					
					}
					
					break;
					
				case 8:
					
					switch(currentPanelIndex){
					
						case 1:
						
							rateLibManImportLib1Panel.getCurrentState();
						
							if(ds.getImportDir()){
							
								PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
								dialog.open();
								RateLibManGetLibDirsWorker worker = new RateLibManGetLibDirsWorker(ds, this, dialog, this);
								worker.execute();
							
							}else{
							
								ds.setLibGroup("PUBLIC");
								boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
								
								ds.setLibGroup("SHARED");
								boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
								
								ds.setLibGroup("USER");
								boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
								
								if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
								
									removeFullButtonPanel();
									
									c.remove(rateLibManImportLib1Panel);
								
									rateLibManImportLib2Panel = new RateLibManImportLib2Panel(this, ds, Cina.cinaMainDataStructure);
									
									rateLibManImportLib2Panel.setCurrentState();
										
									c.add(rateLibManImportLib2Panel, BorderLayout.CENTER);
									
									addEndButtonPanel();
								
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
					
					rateLibManAboutLibPanel.setVisible(false);
					
					removeEndButtonPanel();
					
					c.remove(rateLibManAboutLibPanel);
					
					addRadioButtonPanel();
					
					addIntroButtonPanel();
					
					setCurrentFeatureIndex(0);
					
					setCurrentPanelIndex(0);
					
					break;
					
				case 2:
				
					switch(currentPanelIndex){
					
						case 1:
						
							rateLibManModifyLib1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(rateLibManModifyLib1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							c.remove(rateLibManModifyLib2Panel);
							
							rateLibManModifyLib1Panel = new RateLibManModifyLib1Panel(ds);
							
							rateLibManModifyLib1Panel.setCurrentState();
							
							c.add(rateLibManModifyLib1Panel, BorderLayout.CENTER);
							
							continueButton.setEnabled(true);
							
							setCurrentPanelIndex(1);
						
							break;
							
						case 3:
						
							removeEndButtonPanel();
							
							c.remove(rateLibManModifyLib3Panel);
							
							rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
							
							rateLibManModifyLib2Panel.setCurrentState();
							
							c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							setCurrentPanelIndex(2);
						
							break;
					
					}	
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
							
							removeFullButtonPanel();
							
							rateLibManCustomLibCreation1Panel.setVisible(false);
							
							c.remove(rateLibManCustomLibCreation1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
						
							removeEndButtonPanel();
							
							c.remove(rateLibManCustomLibCreation2Panel);
							
							rateLibManCustomLibCreation1Panel = new RateLibManCustomLibCreation1Panel(ds);
							
							rateLibManCustomLibCreation1Panel.setCurrentState();
							
							c.add(rateLibManCustomLibCreation1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							setCurrentPanelIndex(1);
						
							break;
							
					
					}	
				
					break;
					
				case 4:
				
					switch(currentPanelIndex){
					
						case 1:
						
							removeFullButtonPanel();
							
							rateLibManLibErrorCheck1Panel.setVisible(false);
							
							c.remove(rateLibManLibErrorCheck1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							removeEndButtonPanel();
							
							c.remove(rateLibManLibErrorCheck2Panel);
							
							rateLibManLibErrorCheck1Panel = new RateLibManLibErrorCheck1Panel(ds);
							
							rateLibManLibErrorCheck1Panel.setCurrentState();
							
							c.add(rateLibManLibErrorCheck1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							setCurrentPanelIndex(1);
						
							break;
					
					}
					
					break;
					
				case 5:
				
					switch(currentPanelIndex){
					
						case 1:
						
							removeEndButtonPanel();
							
							rateLibManSharedLibPanel.setVisible(false);
							
							c.remove(rateLibManSharedLibPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
				
				case 6:
				
					switch(currentPanelIndex){
					
						case 1:
						
							removeEndButtonPanel();
							
							rateLibManEraseLibPanel.setVisible(false);
							
							c.remove(rateLibManEraseLibPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
					
				case 7:
				
					switch(currentPanelIndex){
					
						case 1:
						
							removeEndButtonPanel();
							
							rateLibManExportLibPanel.setVisible(false);
							
							c.remove(rateLibManExportLibPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
					
				case 8:
					
					switch(currentPanelIndex){
					
						case 1:
						
							removeFullButtonPanel();
							
							rateLibManImportLib1Panel.setVisible(false);
							
							c.remove(rateLibManImportLib1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							removeEndButtonPanel();
							
							rateLibManImportLib2Panel.setVisible(false);
							
							c.remove(rateLibManImportLib2Panel);
							
							rateLibManImportLib1Panel = new RateLibManImportLib1Panel(this, ds, Cina.cinaMainDataStructure);
							
							rateLibManImportLib1Panel.setCurrentState();
							
							c.add(rateLibManImportLib1Panel, BorderLayout.CENTER);
					
							addFullButtonPanel();
							
							setCurrentFeatureIndex(8);
					
							setCurrentPanelIndex(1);
						
							break;
							
					}
					
					break;
					
				case 9:
					
					switch(currentPanelIndex){
					
						case 1:
						
							removeEndButtonPanel();
							
							rateLibManEraseLibDirPanel.setVisible(false);
							
							c.remove(rateLibManEraseLibDirPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
					
				case 10:
					
					rateLibManAboutLibDirPanel.setVisible(false);
					
					removeEndButtonPanel();
					
					c.remove(rateLibManAboutLibDirPanel);
					
					addRadioButtonPanel();
					
					addIntroButtonPanel();
					
					setCurrentFeatureIndex(0);
					
					setCurrentPanelIndex(0);
					
					break;
					
				
			}
		
			c.validate();
	
		}else if(ae.getSource()==endButton){
			
			removeEndButtonPanel();
			
			switch(currentFeatureIndex){
			
				case 1:
				
					rateLibManAboutLibPanel.setVisible(false);
					
					c.remove(rateLibManAboutLibPanel);
					
					break;
					
				case 2:
				
					rateLibManModifyLib3Panel.setVisible(false);
				
					c.remove(rateLibManModifyLib3Panel);
				
					break;
					
				case 3:
				
					rateLibManCustomLibCreation2Panel.setVisible(false);
							
					c.remove(rateLibManCustomLibCreation2Panel);
				
					break;
				
				case 4:
				
					rateLibManLibErrorCheck2Panel.setVisible(false);
							
					c.remove(rateLibManLibErrorCheck2Panel);
				
					break;
					
				case 5:
				
					rateLibManSharedLibPanel.setVisible(false);
							
					c.remove(rateLibManSharedLibPanel);
				
					break;
					
				case 6:
				
					rateLibManEraseLibPanel.setVisible(false);
							
					c.remove(rateLibManEraseLibPanel);
				
					break;
				
				case 7:
				
					rateLibManExportLibPanel.setVisible(false);
							
					c.remove(rateLibManExportLibPanel);
				
					break;
					
				case 8:
					
					rateLibManImportLib2Panel.setVisible(false);
							
					c.remove(rateLibManImportLib2Panel);
				
					break;
					
				case 9:
					
					rateLibManEraseLibDirPanel.setVisible(false);
							
					c.remove(rateLibManEraseLibDirPanel);
				
					break;
					
				case 10:
					
					rateLibManAboutLibDirPanel.setVisible(false);
							
					c.remove(rateLibManAboutLibDirPanel);
				
					break;
			
			}
			
			setCurrentFeatureIndex(0);
			
			addRadioButtonPanel();
			
			addIntroButtonPanel();
        	
        	c.validate();
        
    	}else if(ae.getSource()==continueOnButton){
    		
			if(Cina.rateViewerFrame==null){
					
				//and set frame properties
				Cina.rateViewerFrame = new RateViewerFrame(Cina.rateViewerDataStructure, false);
				
				Cina.rateViewerDataStructure.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);
				
				Cina.rateViewerDataStructure.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);
				
				Cina.rateViewerDataStructure.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateViewerFrame);
				
				//Call method to populate library dropdown menu in Rate Viewer with all source libraries
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					
					//Call setCurrentState
					Cina.rateViewerFrame.setCurrentState();
				
					//Set temp lib data structure for CGI Call
					Cina.rateViewerDataStructure.setCurrentLibraryDataStructure(Cina.rateViewerDataStructure.getLibraryStructureArray()[Cina.rateViewerDataStructure.getLibIndex()]);
					
					//Set library name for CGI Call		
					Cina.rateViewerDataStructure.setLibraryName(Cina.rateViewerDataStructure.getCurrentLibraryDataStructure().getLibName());
					
					//Call method to get list of isotopes for each source library
					if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateViewerFrame)){
				
						Cina.rateViewerFrame.setSize(825,600);
						Cina.rateViewerFrame.setResizable(true);
	        				Cina.rateViewerFrame.setTitle("Rate Viewer");
	        				Cina.rateViewerFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
	        			
	        				Cina.rateViewerFrame.setVisible(true);
							
					}
				
				}
				
			}else{
				
				Cina.rateViewerFrame.param = false;
				if(Cina.rateViewerFrame.rateViewerPlotFrame!=null){
					Cina.rateViewerFrame.rateViewerPlotFrame.param = false;
					Cina.rateViewerDataStructure.setRateAdded(false);
					Cina.rateViewerFrame.rateViewerPlotFrame.setFormatPanelState();
					Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.initialize();
					Cina.rateViewerFrame.rateViewerPlotFrame.redrawPlot();
				}
				Cina.rateViewerFrame.setVisible(true);
				
			}
			
       	}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			if(currentPanelIndex==1 && currentFeatureIndex==2){
			
				if(rateLibManModifyLib1Panel.goodLibInfoInverseCheck()){
					
					if(ds.getCurrentLibraryDataStructure().getLibType().equals("USER")){
					
						if(ds.getCurrentLibraryDataStructure().getAllInversesPresent()){
		
							c.remove(rateLibManModifyLib1Panel);
						
							rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
							
							rateLibManModifyLib2Panel.setCurrentState();
		
							c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
							
						}else{
	
							String string = "The base library you have selected does not have a complete set of "
												+ "reaction rates as determined by detailed balance (inverse rates)."
												+ " This is a requirement for a base library. Do you want to calculate these reactions and add them to this library?";
												
							createCautionDialogBaseLib(string, this);					
						
						}
					
					}else{
					
						c.remove(rateLibManModifyLib1Panel);
						
						rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
						
						rateLibManModifyLib2Panel.setCurrentState();
	
						c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
					
					}
				
				}
			
			}else if(currentPanelIndex==1 && currentFeatureIndex==3){
				
				if(rateLibManCustomLibCreation1Panel.ratesSelected()){
					
					if(rateLibManCustomLibCreation1Panel==null){
					
						rateLibManCustomLibCreation1Panel.openDelayDialog(this);
						
						if(rateLibManCustomLibCreation1Panel.goodLibMerge()){
							
							ds.setLibraryName(ds.getDestCustomLibName());
	
							if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY INFO", this)){
								
								removeFullButtonPanel();
							
								c.remove(rateLibManCustomLibCreation1Panel);
							
								rateLibManCustomLibCreation2Panel = new RateLibManCustomLibCreation2Panel(ds);
							
								rateLibManCustomLibCreation2Panel.setCurrentState();
			
								c.add(rateLibManCustomLibCreation2Panel, BorderLayout.CENTER);
								
								addEndButtonPanel();
								
							}
							
						}
						
					}
				
				}
			
			}
			
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
		}else if(ae.getSource()==yesButtonBaseLib){
			
			cautionDialogBaseLib.setVisible(false);
			cautionDialogBaseLib.dispose();
		
			openDelayDialog(this);
		
			ds.setLibraryName((String)rateLibManModifyLib1Panel.sourceLibComboBox.getSelectedItem());
        		boolean goodInverseRates = Cina.cinaCGIComm.doCGICall("ADD MISSING INV RATES", this);
  			
  			closeDelayDialog();
  			
  			if(goodInverseRates){
  				
  				String string = ds.getAddMissingInvRatesReport();

				rateLibManModifyLib1Panel.createAddMissingInvRatesDialog(string, this);
				
				c.remove(rateLibManModifyLib1Panel);
											
				rateLibManModifyLib2Panel = new RateLibManModifyLib2Panel(ds);
				
				rateLibManModifyLib2Panel.setCurrentState();
	
				c.add(rateLibManModifyLib2Panel, BorderLayout.CENTER);
  				
  			}
			
		}else if(ae.getSource()==noButtonBaseLib){
			
			cautionDialogBaseLib.setVisible(false);
			cautionDialogBaseLib.dispose();
			
		}else if(ae.getSource()==submitButton){

			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				switch(currentFeatureIndex){
			
					case 2:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateLibManModifyLib1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateLibManModifyLib2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								rateLibManModifyLib3Panel.getCurrentState();
							
								break;
					
						}
					
						break;
					
					case 3:
					
						switch(currentPanelIndex){
					
							case 1:
							
								rateLibManCustomLibCreation1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								rateLibManCustomLibCreation2Panel.getCurrentState();
							
								break;
					
						}
					
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

    			//Free system resources from frame
    			dispose();
    			
    			Cina.rateLibManFrame = null;
			
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

    			//Free system resources 
    			dispose();
    			
    			Cina.rateLibManFrame = null;
		
			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
			}
			
		}
	
	}
	
	public void gotoImportLib2Panel(){
		removeFullButtonPanel();
		c.remove(rateLibManImportLib1Panel);
		rateLibManImportLib2Panel = new RateLibManImportLib2Panel(this, ds, Cina.cinaMainDataStructure);
		rateLibManImportLib2Panel.setCurrentState();
		c.add(rateLibManImportLib2Panel, BorderLayout.CENTER);
		addEndButtonPanel();
		validate();
		repaint();
	}
	
	public void gotoEraseLibDirPanel(){
		rateLibManEraseLibDirPanel = new RateLibManEraseLibDirPanel(ds);
		rateLibManEraseLibDirPanel.setCurrentState();	
		removeIntroButtonPanel();
		c.add(rateLibManEraseLibDirPanel, BorderLayout.CENTER);
		addEndButtonPanel();
		validate();
		repaint();
	}
	
	public void gotoAboutLibDirPanel(){
		rateLibManAboutLibDirPanel = new RateLibManAboutLibDirPanel(ds);
		rateLibManAboutLibDirPanel.setCurrentState();	
		removeIntroButtonPanel();
		c.add(rateLibManAboutLibDirPanel, BorderLayout.CENTER);
		addEndButtonPanel();
		validate();
		repaint();
	}
	
	public void libCreation1GoodLibMerge(){

		if(rateLibManCustomLibCreation1Panel.goodLibMerge){
									
			ds.setLibraryName(ds.getDestCustomLibName());

			if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY INFO", this)){

				removeFullButtonPanel();
			
				c.remove(rateLibManCustomLibCreation1Panel);
			
				rateLibManCustomLibCreation2Panel = new RateLibManCustomLibCreation2Panel(ds);
			
				rateLibManCustomLibCreation2Panel.setCurrentState();

				c.add(rateLibManCustomLibCreation2Panel, BorderLayout.CENTER);
				
				addEndButtonPanel();
			
			}
			
		}
	
	}
	
	public void modifyLib2GoodRateMerge(){
	
		if(rateLibManModifyLib2Panel.goodRateMerge){
			
			ds.setLibraryName(ds.getDestLibName());
							
			if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY INFO", this)){

				removeFullButtonPanel();
			
				c.remove(rateLibManModifyLib2Panel);
			
				rateLibManModifyLib3Panel = new RateLibManModifyLib3Panel(ds);

				rateLibManModifyLib3Panel.setCurrentState();

				c.add(rateLibManModifyLib3Panel, BorderLayout.CENTER);
				
				addEndButtonPanel();
			
			}
		
		}
	
	}
	
	public void libErrorCheck1ErrorCheckLib(){
	
		if(rateLibManLibErrorCheck1Panel.goodErrorCheckLib){
						
			removeFullButtonPanel();
		
			c.remove(rateLibManLibErrorCheck1Panel);
		
			rateLibManLibErrorCheck2Panel = new RateLibManLibErrorCheck2Panel(ds);

			rateLibManLibErrorCheck2Panel.setCurrentState();

			c.add(rateLibManLibErrorCheck2Panel, BorderLayout.CENTER);
			
			addEndButtonPanel();
		
		}
	
	}
	
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

	public void removeIntroButtonPanel(){
	
		c.remove(introButtonPanel);
		
	}
	
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
	
	public void removeFullButtonPanel(){
		
		c.remove(fullButtonPanel);
	}
	
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
	
	public void removeEndButtonPanel(){
	
		c.remove(endButtonPanel);
		
	}
	
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
    
    public void windowActivated(WindowEvent we){}
    public void windowClosed(WindowEvent we){}
    public void windowDeactivated(WindowEvent we){}
    public void windowDeiconified(WindowEvent we){}
    public void windowIconified(WindowEvent we){}
    public void windowOpened(WindowEvent we){}
    
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
		cautionDialog.setVisible(true);
	
	}
        
    public void createSaveDialog(int index){
    	
		//Create a new JDialog object
		saveDialog = new JDialog(this, "Rate Library Manager Exit", true);
		saveDialog.setSize(525, 199);
		saveDialog.getContentPane().setLayout(new GridBagLayout());
		saveDialog.setLocationRelativeTo(this);
	
		//Check box group for radio buttons
		choiceButtonGroup = new ButtonGroup();
	
		//Create checkboxes and set state and cbGroup
		saveAndCloseRadioButton = new JRadioButton("Exit and retain Rate Library Manager session input.", true);
		saveAndCloseRadioButton.setFont(Fonts.textFont);
	
		closeRadioButton = new JRadioButton("Exit and erase Rate Library Manager session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
   		doNotCloseRadioButton = new JRadioButton("Return to the Rate Library Manager.", false);
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
		saveDialog.setVisible(true);

    }
    
    public void createCautionDialogBaseLib(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
    	cautionDialogBaseLib = new JDialog(frame, "Caution!", true);
    	cautionDialogBaseLib.setSize(320, 215);
    	cautionDialogBaseLib.getContentPane().setLayout(new GridBagLayout());
		cautionDialogBaseLib.setLocationRelativeTo(frame);
		
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
		
		yesButtonBaseLib = new JButton("Yes");
		yesButtonBaseLib.setFont(Fonts.buttonFont);
		yesButtonBaseLib.addActionListener(this);
		
		noButtonBaseLib = new JButton("No");
		noButtonBaseLib.setFont(Fonts.buttonFont);
		noButtonBaseLib.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButtonBaseLib, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButtonBaseLib, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialogBaseLib.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialogBaseLib.getContentPane().add(cautionButtonPanel, gbc1);
		cautionDialogBaseLib.setVisible(true);

	}
	
	public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait", false);
		delayDialog.setSize(340, 200);
    	delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while inverse rates are added. DO NOT operate the Rate Library Manager at this time!";
		delayTextArea.setText(delayString);
		delayTextArea.setCaretPosition(0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		delayDialog.validate();
		delayDialog.setVisible(true);
		
	}
	
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	
	}

	public void updateAfterGetLibDirs(){
		if(eraseLibDirRadioButton.isSelected()){
			gotoEraseLibDirPanel();
		}else{
			gotoImportLib2Panel();
		}
	}
	
}

class RateLibManGetLibDirsAboutWorker extends SwingWorker<Void, Void>{

	private RateLibManDataStructure ds;
	private RateLibManFrame parent;
	private PleaseWaitDialog dialog;

	public RateLibManGetLibDirsAboutWorker(RateLibManDataStructure ds
											, RateLibManFrame parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_LIB_DIRS
												, parent);
			
			if(ds.getLibDirList().size()>0){
			
				LibraryDirectoryDataStructure ldds = new LibraryDirectoryDataStructure();
			
				ds.setLibDir(ds.getLibDirList().get(0));
				ds.setLibDirDS(ldds);
				
				Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
													, ds
													, CGICom.GET_LIB_DIR_INFO
													, parent);
												
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		dialog.close();
		parent.gotoAboutLibDirPanel();
	}
	
}