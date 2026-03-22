//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.data.dataman;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.data.dataviewer.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.data.dataman.DataManAboutData1Panel;
import org.nucastrodata.data.dataman.DataManAboutData2Panel;
import org.nucastrodata.data.dataman.DataManEraseDataPanel;
import org.nucastrodata.data.dataman.DataManImportData1Panel;
import org.nucastrodata.data.dataman.DataManImportData2Panel;
import org.nucastrodata.data.dataman.DataManImportData3Panel;
import org.nucastrodata.data.dataman.DataManImportData4Panel;
import org.nucastrodata.data.dataman.DataManImportDataPasteFrame;
import org.nucastrodata.data.dataman.DataManImportDataPlotFrame;
import org.nucastrodata.data.dataman.DataManImportDataTableFrame;
import org.nucastrodata.data.dataman.DataManMoveDataPanel;
import org.nucastrodata.data.dataviewer.DataViewerFrame;


/**
 * The Class DataManFrame.
 */
public class DataManFrame extends JFrame implements WindowListener, ActionListener{
	
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
	
	/** The move data radio button. */
	JRadioButton aboutDataRadioButton, importDataRadioButton, eraseDataRadioButton, moveDataRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;
	
	/** The current panel index. */
	public static int currentPanelIndex;
	
	/** The current feature index. */
	public static int currentFeatureIndex;
		
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The caution dialog. */
	JDialog saveDialog, mockUpDialog, cautionDialog;

	/** The c. */
	public Container c;
	
	/** The radio button panel. */
	JPanel radioButtonPanel;
	
	/** The label3. */
	JLabel label1, label2, label3;
	
	/** The data man about data1 panel. */
	public DataManAboutData1Panel dataManAboutData1Panel;
	
	/** The data man about data2 panel. */
	public DataManAboutData2Panel dataManAboutData2Panel;
	
	/** The data man import data1 panel. */
	public DataManImportData1Panel dataManImportData1Panel;
	
	/** The data man import data2 panel. */
	public DataManImportData2Panel dataManImportData2Panel;
	
	/** The data man import data3 panel. */
	public DataManImportData3Panel dataManImportData3Panel;
	
	/** The data man import data4 panel. */
	public DataManImportData4Panel dataManImportData4Panel;
	
	/** The data man import data paste frame. */
	public DataManImportDataPasteFrame dataManImportDataPasteFrame;
	
	/** The data man import data plot frame. */
	public DataManImportDataPlotFrame dataManImportDataPlotFrame;
	
	/** The data man import data table frame. */
	public DataManImportDataTableFrame dataManImportDataTableFrame;
	
	/** The data man erase data panel. */
	public DataManEraseDataPanel dataManEraseDataPanel;
	
	/** The data man move data panel. */
	public DataManMoveDataPanel dataManMoveDataPanel;
	
	/** The confirm dialog. */
	JDialog confirmDialog;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new data man frame.
	 *
	 * @param ds the ds
	 */
	public DataManFrame(DataManDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		radioButtonPanel = new JPanel(new GridBagLayout());
		
		aboutDataRadioButton = new JRadioButton("Nuclear Data Info", true);
		importDataRadioButton = new JRadioButton("Import Nuclear Data", false);
		eraseDataRadioButton = new JRadioButton("Erase Nuclear Data", false);
		moveDataRadioButton = new JRadioButton("Move Data Set to Shared Folder", false);
	
		buttonGroup = new ButtonGroup();
	
		buttonGroup.add(aboutDataRadioButton);
		buttonGroup.add(importDataRadioButton);
		buttonGroup.add(eraseDataRadioButton);
		buttonGroup.add(moveDataRadioButton);
				
		addRadioButtonPanel();
				
		//Create buttons and set properties
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Nuclear Data Manager Home");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Nuclear Data Viewer");
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
	
		label1 = new JLabel("Nuclear");
		label1.setFont(Fonts.bigTitleFont);
	
		label2 = new JLabel("Data");
		label2.setFont(Fonts.bigTitleFont);
	
		label3 = new JLabel("Manager");
		label3.setFont(Fonts.bigTitleFont);
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.insets = new Insets(5, 0, 5, 170);
		
		radioButtonPanel.add(label1, gbc);
		
		gbc.gridy = 1;
		
		radioButtonPanel.add(label2, gbc);
	
		gbc.gridy = 2;
		
		radioButtonPanel.add(label3, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 30);
		gbc.anchor = GridBagConstraints.WEST;
		radioButtonPanel.add(aboutDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		radioButtonPanel.add(importDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		radioButtonPanel.add(eraseDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		
		radioButtonPanel.add(moveDataRadioButton, gbc);
		
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
									
					if(aboutDataRadioButton.isSelected()){
						
						dataManAboutData1Panel = new DataManAboutData1Panel(ds);
						
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataManFrame);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataManFrame);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataManFrame);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
							dataManAboutData1Panel.createSetGroupNodes();
								
							removeIntroButtonPanel();
						
							dataManAboutData1Panel.setCurrentState();
						
							c.add(dataManAboutData1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}else if(importDataRadioButton.isSelected()){
						
						dataManImportData1Panel = new DataManImportData1Panel(ds);
						
						removeIntroButtonPanel();
						
						dataManImportData1Panel.setCurrentState();
							
						c.add(dataManImportData1Panel, BorderLayout.CENTER);
						
						addFullButtonPanel();
					
					}else if(eraseDataRadioButton.isSelected()){
						
						dataManEraseDataPanel = new DataManEraseDataPanel(ds);
						
						ds.setNucDataSetGroup("USER");
				
						if(Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataManFrame)){
							
							dataManEraseDataPanel.createSetGroupNodes();
								
							removeIntroButtonPanel();
						
							dataManEraseDataPanel.setCurrentState();
						
							c.add(dataManEraseDataPanel, BorderLayout.CENTER);
	
							addEndButtonPanel();
						
						}
					
					}else if(moveDataRadioButton.isSelected()){
						
						dataManMoveDataPanel = new DataManMoveDataPanel(ds);
					
						ds.setNucDataSetGroup("USER");
					
						if(Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataManFrame)){
					
							removeIntroButtonPanel();
								
							dataManMoveDataPanel.setCurrentState();
								
							c.add(dataManMoveDataPanel, BorderLayout.CENTER);
							
							addEndButtonPanel();
							
						}
					
					}
					
					break;
				
				case 1:
				
					switch(currentPanelIndex){
					
						case 1:
					
							dataManAboutData1Panel.getCurrentState();
							
							if(!dataManAboutData1Panel.emptyNucData()){
							
								dataManAboutData1Panel.createNucDataDataStructureArray();
								
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
								
								if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", Cina.dataManFrame)){
								
									removeFullButtonPanel();
								
									setSize(800, 600);
																	
									c.remove(dataManAboutData1Panel);

									dataManAboutData2Panel = new DataManAboutData2Panel(ds);
									
									dataManAboutData2Panel.setCurrentState();

									c.add(dataManAboutData2Panel, BorderLayout.CENTER);
									
									validate();

									addEndButtonPanel();
								
								}
							
							}else{
							
								String string = "Please select at least one nuclear data file.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
					
							break;
						
					}
				
					break;
				
				case 2:
				
					switch(currentPanelIndex){
							
						case 1:
						
							if(dataManImportData1Panel.checkDataFields()){
								
								dataManImportData1Panel.getCurrentState();
								
								String cgiCommReactionString = Cina.dataManFrame.dataManImportData1Panel.createCGICommReactionString(ds.getImportNucDataDataStructure().getIsoIn()
																	, ds.getImportNucDataDataStructure().getIsoOut()
																	, ds.getImportNucDataDataStructure().getNumberReactants()
																	, ds.getImportNucDataDataStructure().getNumberProducts());
		
								ds.setReaction(cgiCommReactionString);
								
								ds.setReaction_type(ds.getImportNucDataDataStructure().getDecay());
								
								if(Cina.cinaCGIComm.doCGICall("CHECK REACTION", Cina.dataManFrame)){
									
									createConfirmDialog(this);
								
								}
							
							}
										
							break;
							
						case 2:

							dataManImportData2Panel.getCurrentState();
						
							c.remove(dataManImportData2Panel);
						
							dataManImportData3Panel = new DataManImportData3Panel(ds);
							
							dataManImportData3Panel.setCurrentState();
		
							c.add(dataManImportData3Panel, BorderLayout.CENTER);
								
							break;
							
						case 3: 
						
							dataManImportData3Panel.getCurrentState();
							
							if(!dataManImportData3Panel.filenameField.getText().equals("")){
							
								ds.setReaction_type(ds.getImportNucDataDataStructure().getDecay());
							
								if(Cina.cinaCGIComm.doCGICall("PARSE NUC DATA FILE", Cina.dataManFrame)){
								
									removeFullButtonPanel();
									
									c.remove(dataManImportData3Panel);
									
									addEndButtonPanel();
								
									dataManImportData4Panel = new DataManImportData4Panel(ds);
									
									dataManImportData4Panel.setCurrentState();
									
									c.add(dataManImportData4Panel, BorderLayout.CENTER);
																
								}
							
							}else{
							
								String string = "Please choose a data source.";
								
								Dialogs.createExceptionDialog(string, this);
							
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
						
							dataManAboutData1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(dataManAboutData1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							removeEndButtonPanel();
							
							c.remove(dataManAboutData2Panel);
							
							addFullButtonPanel();
							
							dataManAboutData1Panel = new DataManAboutData1Panel(ds);
							
							dataManAboutData1Panel.createSetGroupNodes();
							
							dataManAboutData1Panel.setCurrentState();
							
							setSize(578, 418);
							
							c.add(dataManAboutData1Panel, BorderLayout.CENTER);
							
							validate();
							
							setCurrentPanelIndex(1);
						
							break;
							
					}
					
					break;
					
				case 2:
				
					switch(currentPanelIndex){
					
						case 1:
						
							dataManImportData1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(dataManImportData1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							c.remove(dataManImportData2Panel);
							
							dataManImportData1Panel = new DataManImportData1Panel(ds);
							
							dataManImportData1Panel.setCurrentState();
							
							c.add(dataManImportData1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);
						
							break;
							
						case 3:
							
							c.remove(dataManImportData3Panel);
							
							dataManImportData2Panel = new DataManImportData2Panel(ds);
							
							dataManImportData2Panel.setCurrentState();
							
							c.add(dataManImportData2Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(2);
						
							break;
							
						case 4:
							
							c.remove(dataManImportData4Panel);
							
							removeEndButtonPanel();
							
							dataManImportData3Panel = new DataManImportData3Panel(ds);
							
							dataManImportData3Panel.setCurrentState();
							
							c.add(dataManImportData3Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(3);
						
							addFullButtonPanel();
						
							break;
							
					
					}	
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
						
							dataManEraseDataPanel.setVisible(false);
						
							removeEndButtonPanel();
							
							c.remove(dataManEraseDataPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
				
				case 4:
				
					switch(currentPanelIndex){
					
						case 1:
						
							dataManMoveDataPanel.setVisible(false);
						
							removeEndButtonPanel();
							
							c.remove(dataManMoveDataPanel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
					}
					
					break;
									
			}
		
			c.validate();
	
		}else if(ae.getSource()==endButton){
			
			removeEndButtonPanel();
			
			switch(currentFeatureIndex){
			
				case 1:
				
					dataManAboutData2Panel.setVisible(false);
					
					c.remove(dataManAboutData2Panel);
					
					break;
					
				case 2:
				
					dataManImportData4Panel.setVisible(false);
					
					c.remove(dataManImportData4Panel);
					
					break;
					
				case 3:
				
					dataManEraseDataPanel.setVisible(false);
					
					c.remove(dataManEraseDataPanel);
					
					break;
					
				case 4:
				
					dataManMoveDataPanel.setVisible(false);
					
					c.remove(dataManMoveDataPanel);
					
					break;	
			
			}

			setCurrentFeatureIndex(0);
			
			addRadioButtonPanel();
			
			addIntroButtonPanel();
        	
        	setSize(578, 418);
        	
        	validate();
        
    	}else if(ae.getSource()==continueOnButton){
    		
			//If Rate Viewer has not been created
			if(Cina.dataViewerFrame==null){

				//Instantiate Rate Viewer
				Cina.dataViewerFrame = new DataViewerFrame(Cina.dataViewerDataStructure);
				
				Cina.dataViewerDataStructure.setNucDataSetGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataViewerFrame);
				
				Cina.dataViewerDataStructure.setNucDataSetGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataViewerFrame);
				
				Cina.dataViewerDataStructure.setNucDataSetGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataViewerFrame);
				
				//Call method to populate library dropdown menu in Rate Viewer with all source libraries
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					
					//Call setCurrentState
					Cina.dataViewerFrame.setCurrentState();
				
					//Set temp lib data structure for CGI Call
			    	Cina.dataViewerDataStructure.setCurrentNucDataSetDataStructure(Cina.dataViewerDataStructure.getNucDataSetStructureArray()[Cina.dataViewerDataStructure.getNucDataSetIndex()]);
					
					//Set library name for CGI Call		
					Cina.dataViewerDataStructure.setNucDataSetName(Cina.dataViewerDataStructure.getCurrentNucDataSetDataStructure().getNucDataSetName());
					
					//Call method to get list of isotopes for each source library
					if(Cina.cinaCGIComm.doCGICall("GET NUC DATA SET ISOTOPES", Cina.dataViewerFrame)){
					
				
						//Set Rate Viewer properties
						Cina.dataViewerFrame.setSize(825,600);
						Cina.dataViewerFrame.setResizable(true);
	        			Cina.dataViewerFrame.setTitle("Nuclear Data Viewer");
	        			Cina.dataViewerFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
	        			
	        			//Set Rate Viewer visible
	        			Cina.dataViewerFrame.setVisible(true);
							
					}
				
				}
			
			//If Rate Viewer has been created	
			}else{
				
				//Set rate Viewer visiable
				Cina.dataViewerFrame.setVisible(true);
				
			}
			
       	}else if(ae.getSource()==submitButton){

			//If "Save info and close Rate Gen" is chosen
			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				switch(currentFeatureIndex){
			
					case 1:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataManAboutData1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								dataManAboutData2Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 2:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataManImportData1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								dataManImportData2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								dataManImportData3Panel.getCurrentState();
							
								break;
								
							case 4:
							
								dataManImportData4Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 3:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataManEraseDataPanel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 4:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataManMoveDataPanel.getCurrentState();
							
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
        		
        		Cina.dataManFrame = null;

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
        		
        		Cina.dataManFrame = null;
			
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
						
			c.remove(dataManImportData1Panel);
		
			dataManImportData2Panel = new DataManImportData2Panel(ds);
			
			dataManImportData2Panel.setCurrentState();

			c.add(dataManImportData2Panel, BorderLayout.CENTER);
					
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

		if(dataManImportDataPasteFrame!=null){
			dataManImportDataPasteFrame.setVisible(false);
			dataManImportDataPasteFrame.dispose();
		}
		
		if(dataManImportDataPlotFrame!=null){
			dataManImportDataPlotFrame.setVisible(false);
			dataManImportDataPlotFrame.dispose();
		}
		
		if(dataManImportDataTableFrame!=null){
			dataManImportDataTableFrame.setVisible(false);
			dataManImportDataTableFrame.dispose();
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
        
    //Method createSaveDialog
    //Args JPanel index
    /**
     * Creates the save dialog.
     *
     * @param index the index
     */
    public void createSaveDialog(int index){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Nuclear Data Manager Exit", true);
    	saveDialog.setSize(525, 199);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Nuclear Data Manager session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Nuclear Data Manager session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Nuclear Data Manager.", false);
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
									+ ds.getImportNucDataDataStructure().getReactionString()
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