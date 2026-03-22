//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.data.dataeval;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.data.dataman.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataEvalDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.data.dataeval.DataEvalExtraData1Panel;
import org.nucastrodata.data.dataeval.DataEvalExtraData2Panel;
import org.nucastrodata.data.dataeval.DataEvalExtraDataTableFrame;
import org.nucastrodata.data.dataeval.DataEvalInterData1Panel;
import org.nucastrodata.data.dataeval.DataEvalInterData2Panel;
import org.nucastrodata.data.dataeval.DataEvalInterData3Panel;
import org.nucastrodata.data.dataeval.DataEvalInterDataTableFrame;
import org.nucastrodata.data.dataeval.DataEvalTransData1Panel;
import org.nucastrodata.data.dataeval.DataEvalTransData2Panel;
import org.nucastrodata.data.dataeval.DataEvalTransDataTableFrame;
import org.nucastrodata.data.dataman.DataManFrame;


/**
 * The Class DataEvalFrame.
 */
public class DataEvalFrame extends JFrame implements WindowListener, ActionListener{
	
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
	
	/** The convert data radio button. */
	JRadioButton interDataRadioButton, transDataRadioButton, extraDataRadioButton
				, theoryDataRadioButton, convertDataRadioButton;
	
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
	Container c;
	
	/** The radio button panel. */
	JPanel radioButtonPanel;
	
	/** The label4. */
	JLabel label1, label2, label3, label4;
	
	/** The data eval inter data1 panel. */
	public DataEvalInterData1Panel dataEvalInterData1Panel;
	
	/** The data eval inter data2 panel. */
	public DataEvalInterData2Panel dataEvalInterData2Panel;
	
	/** The data eval inter data3 panel. */
	public DataEvalInterData3Panel dataEvalInterData3Panel;
	
	/** The data eval inter data table frame. */
	public DataEvalInterDataTableFrame dataEvalInterDataTableFrame;
	
	/** The data eval trans data1 panel. */
	public DataEvalTransData1Panel dataEvalTransData1Panel;
	
	/** The data eval trans data2 panel. */
	public DataEvalTransData2Panel dataEvalTransData2Panel;
	
	/** The data eval trans data table frame. */
	public DataEvalTransDataTableFrame dataEvalTransDataTableFrame;
	
	/** The data eval extra data1 panel. */
	public DataEvalExtraData1Panel dataEvalExtraData1Panel;
	
	/** The data eval extra data2 panel. */
	public DataEvalExtraData2Panel dataEvalExtraData2Panel;
	
	/** The data eval extra data table frame. */
	public DataEvalExtraDataTableFrame dataEvalExtraDataTableFrame;
	
	/** The confirm dialog. */
	JDialog confirmDialog;
	
	/** The ds. */
	private DataEvalDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new data eval frame.
	 *
	 * @param ds the ds
	 */
	public DataEvalFrame(DataEvalDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		radioButtonPanel = new JPanel(new GridBagLayout());
		
		interDataRadioButton = new JRadioButton("Cross Section Fuser", true);
		transDataRadioButton = new JRadioButton("Cross Section Normalizer", false);
		extraDataRadioButton = new JRadioButton("Cross Section Extrapolator", false);
		theoryDataRadioButton = new JRadioButton("Theoretical Cross Section Calculator", false);
		theoryDataRadioButton.setEnabled(false);
		convertDataRadioButton = new JRadioButton("S Factor Converter", false);
		convertDataRadioButton.setEnabled(false);
	
		buttonGroup = new ButtonGroup();
	
		buttonGroup.add(interDataRadioButton);
		buttonGroup.add(transDataRadioButton);
		buttonGroup.add(extraDataRadioButton);
		buttonGroup.add(theoryDataRadioButton);
		buttonGroup.add(convertDataRadioButton);
				
		addRadioButtonPanel();
				
		//Create buttons and set properties
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Nuclear Data Evaluator's Toolkit Home");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Nuclear Data Manager");
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
	
		label3 = new JLabel("Evaluator's");
		label3.setFont(Fonts.bigTitleFont);
	
		label4 = new JLabel("Toolkit");
		label4.setFont(Fonts.bigTitleFont);
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 0, 5, 100);
		
		radioButtonPanel.add(label1, gbc);
		
		gbc.gridy = 1;
		
		radioButtonPanel.add(label2, gbc);
	
		gbc.gridy = 2;
		
		radioButtonPanel.add(label3, gbc);
	
		gbc.gridy = 3;
		
		radioButtonPanel.add(label4, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 30);
		gbc.anchor = GridBagConstraints.WEST;
		radioButtonPanel.add(interDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		radioButtonPanel.add(transDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		radioButtonPanel.add(extraDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		
		radioButtonPanel.add(theoryDataRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		
		radioButtonPanel.add(convertDataRadioButton, gbc);
		
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
									
					if(interDataRadioButton.isSelected()){
						
						dataEvalInterData1Panel = new DataEvalInterData1Panel(ds);
						
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
							dataEvalInterData1Panel.createSetGroupNodes();
								
							removeIntroButtonPanel();
						
							dataEvalInterData1Panel.setCurrentState();
						
							c.add(dataEvalInterData1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}
					
					if(transDataRadioButton.isSelected()){
						
						dataEvalTransData1Panel = new DataEvalTransData1Panel(ds);
						
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);

						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
							dataEvalTransData1Panel.createSetGroupNodes();
								
							removeIntroButtonPanel();
						
							dataEvalTransData1Panel.setCurrentState();
						
							c.add(dataEvalTransData1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}
					
					if(extraDataRadioButton.isSelected()){
						
						dataEvalExtraData1Panel = new DataEvalExtraData1Panel(ds);
						
						ds.setNucDataSetGroup("PUBLIC");
						boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("SHARED");
						boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						ds.setNucDataSetGroup("USER");
						boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
						
						if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
							
							dataEvalExtraData1Panel.createSetGroupNodes();
								
							removeIntroButtonPanel();
						
							dataEvalExtraData1Panel.setCurrentState();
						
							c.add(dataEvalExtraData1Panel, BorderLayout.CENTER);
	
							addFullButtonPanel();
						
						}
					
					}
					
					break;
				
				case 1:
				
					switch(currentPanelIndex){
					
						case 1:
						
							if(dataEvalInterData1Panel.checkNucDataField()){
						
								dataEvalInterData1Panel.getCurrentState();
						
								ds.setNucData(ds.getInter1NucDataDataStructure().getNucDataID());
		
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
						
								if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", this)){
							
									c.remove(dataEvalInterData1Panel);
								
									dataEvalInterData2Panel = new DataEvalInterData2Panel(ds);
									
									ds.setNucDataSetGroup("PUBLIC");
									boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
									
									ds.setNucDataSetGroup("SHARED");
									boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
									
									ds.setNucDataSetGroup("USER");
									boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
									
									if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
								
										dataEvalInterData2Panel.createSetGroupNodes();
									
										dataEvalInterData2Panel.setCurrentState();
									
										c.add(dataEvalInterData2Panel, BorderLayout.CENTER);
									
									}
								
								}
							
							}else{
							
								String string = "Please select nuclear data for cross section one.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
										
							break;
							
						case 2:
						
							if(dataEvalInterData2Panel.checkNucDataField()){
						
								dataEvalInterData2Panel.getCurrentState();
						
								if(dataEvalInterData2Panel.goodReactionMatch()){
						
									ds.setNucData(ds.getInter2NucDataDataStructure().getNucDataID());
		
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
						
									if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", this)){
								
										setSize(766, 713);
								
										c.remove(dataEvalInterData2Panel);
									
										removeFullButtonPanel();
										
										addEndButtonPanel();
									
										dataEvalInterData3Panel = new DataEvalInterData3Panel(ds);
	
										dataEvalInterData3Panel.setCurrentState();
									
										validate();
									
										c.add(dataEvalInterData3Panel, BorderLayout.CENTER);
											
									}
								
								}else{
								
									String string = "The cross section files chosen are not for the same reaction.";
								
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}else{
							
								String string = "Please select nuclear data for cross section two.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
										
							break;	
						
						
					}
				
					break;
					
				case 2:
				
					switch(currentPanelIndex){
					
						case 1:
						
							if(dataEvalTransData1Panel.checkNucDataField()){
						
								dataEvalTransData1Panel.getCurrentState();
						
									ds.setNucData(ds.getTrans1NucDataDataStructure().getNucDataID());
		
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
								
									if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", this)){
								
										setSize(915, 790);
								
										c.remove(dataEvalTransData1Panel);
									
										removeFullButtonPanel();
										
										addEndButtonPanel();
									
										dataEvalTransData2Panel = new DataEvalTransData2Panel(ds);
	
										dataEvalTransData2Panel.setCurrentState();
									
										validate();
									
										c.add(dataEvalTransData2Panel, BorderLayout.CENTER);
											
									}
							
							}else{
							
								String string = "Please select a cross section or a S factor.";
								
								Dialogs.createExceptionDialog(string, this);
							
							}
										
							break;		
						
					}
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
						
							if(dataEvalExtraData1Panel.checkNucDataField()){
						
								dataEvalExtraData1Panel.getCurrentState();
						
								ds.setNucData(ds.getExtra1NucDataDataStructure().getNucDataID());
	
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
												
								if(Cina.cinaCGIComm.doCGICall("GET NUC DATA INFO", this)){
							
									if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){

										String string = "Extrapolation of cross sections to high energies is available. However, in order to extrapolate cross sections to low energies"
															+ ", please convert the cross section to an S factor by using the S Factor Converter tool "
															+ "of the Nuclear Data Evaluator's Toolkit. Then "
															+ "use the S Factor Converter tool to convert the S factor back to a cross section.";
										
										Dialogs.createExceptionDialog(string, this);
									
									}
							
									setSize(800, 723);
							
									c.remove(dataEvalExtraData1Panel);
								
									removeFullButtonPanel();
									
									addEndButtonPanel();
								
									dataEvalExtraData2Panel = new DataEvalExtraData2Panel(ds);

									dataEvalExtraData2Panel.setCurrentState();
								
									validate();
								
									c.add(dataEvalExtraData2Panel, BorderLayout.CENTER);
										
								}
							
							}else{
							
								String string = "Please select a cross section or a S factor.";
								
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
						
							dataEvalInterData1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(dataEvalInterData1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							dataEvalInterData2Panel.setVisible(false);
							
							c.remove(dataEvalInterData2Panel);
							
							dataEvalInterData1Panel = new DataEvalInterData1Panel(ds);
							
							dataEvalInterData1Panel.createSetGroupNodes();
							
							dataEvalInterData1Panel.setCurrentState();
							
							c.add(dataEvalInterData1Panel, BorderLayout.CENTER);
							
							setCurrentPanelIndex(1);
						
							break;
							
						case 3:
							
							dataEvalInterData3Panel.setVisible(false);
							
							c.remove(dataEvalInterData3Panel);
							
							removeEndButtonPanel();
							
							dataEvalInterData2Panel = new DataEvalInterData2Panel(ds);
							
							dataEvalInterData2Panel.createSetGroupNodes();
							
							dataEvalInterData2Panel.setCurrentState();
							
							setSize(578, 418);
							
							c.add(dataEvalInterData2Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							validate();
							
							setCurrentPanelIndex(2);
						
							break;
														
					}
					
					break;
					
				case 2:
				
					switch(currentPanelIndex){
					
						case 1:
						
							dataEvalTransData1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(dataEvalTransData1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							dataEvalTransData2Panel.setVisible(false);
							
							c.remove(dataEvalTransData2Panel);
							
							removeEndButtonPanel();
							
							dataEvalTransData1Panel = new DataEvalTransData1Panel(ds);
							
							dataEvalTransData1Panel.createSetGroupNodes();
							
							dataEvalTransData1Panel.setCurrentState();
							
							setSize(578, 418);
							
							c.add(dataEvalTransData1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							validate();
							
							setCurrentPanelIndex(1);
						
							break;
							
					}
				
					break;
					
				case 3:
				
					switch(currentPanelIndex){
					
						case 1:
						
							dataEvalExtraData1Panel.setVisible(false);
						
							removeFullButtonPanel();
							
							c.remove(dataEvalExtraData1Panel);
							
							addRadioButtonPanel();
					
							addIntroButtonPanel();
							
							setCurrentFeatureIndex(0);
					
							setCurrentPanelIndex(0);
						
							break;
							
						case 2:
							
							dataEvalExtraData2Panel.setVisible(false);
							
							c.remove(dataEvalExtraData2Panel);
							
							removeEndButtonPanel();
							
							dataEvalExtraData1Panel = new DataEvalExtraData1Panel(ds);
							
							dataEvalExtraData1Panel.createSetGroupNodes();
							
							dataEvalExtraData1Panel.setCurrentState();
							
							setSize(578, 418);
							
							c.add(dataEvalExtraData1Panel, BorderLayout.CENTER);
							
							addFullButtonPanel();
							
							validate();
							
							setCurrentPanelIndex(1);
						
							break;
							
					}
				
					break;
									
			}
		
			c.validate();
	
		}else if(ae.getSource()==endButton){
			
			removeEndButtonPanel();
			
			switch(currentFeatureIndex){
			
				case 1:
				
					dataEvalInterData3Panel.setVisible(false);
					
					c.remove(dataEvalInterData3Panel);
					
					break;
					
				case 2:
				
					dataEvalTransData2Panel.setVisible(false);
					
					c.remove(dataEvalTransData2Panel);
					
					break;
				
				case 3:
				
					dataEvalExtraData2Panel.setVisible(false);
					
					c.remove(dataEvalExtraData2Panel);
					
					break;
					
			}
			
        	setCurrentFeatureIndex(0);
        	
        	setCurrentPanelIndex(0);
			
			addRadioButtonPanel();
			
			addIntroButtonPanel();
			
			setSize(578, 418);
        	
        	c.validate();
			
    	}else if(ae.getSource()==continueOnButton){
    		
			//If Lib Man has not been created
			if(Cina.dataManFrame==null){

				//Instantiate Lib Man
				Cina.dataManFrame = new DataManFrame(Cina.dataManDataStructure);
				
				//Set Lib Man properties
				Cina.dataManFrame.setResizable(true);
				Cina.dataManFrame.setSize(580, 420);
				Cina.dataManFrame.setVisible(true);
				
				//Set location slightly offset from cina
				Cina.dataManFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
				Cina.dataManFrame.setTitle("Nuclear Data Manager");
			
			//If Lib Man has been created
			}else{
				
				//Set Lib Man visible
				Cina.dataManFrame.setVisible(true);

			}
			
       	}else if(ae.getSource()==submitButton){

			//If "Save info and close Rate Gen" is chosen
			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				switch(currentFeatureIndex){
			
					case 1:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataEvalInterData1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								dataEvalInterData2Panel.getCurrentState();
							
								break;
								
							case 3:
							
								dataEvalInterData3Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 2:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataEvalTransData1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								dataEvalTransData2Panel.getCurrentState();
							
								break;
								
						}
					
						break;
						
					case 3:
					
						switch(currentPanelIndex){
					
							case 1:
							
								dataEvalExtraData1Panel.getCurrentState();
							
								break;
								
							case 2:
							
								dataEvalExtraData2Panel.getCurrentState();
							
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
        		
        		Cina.dataEvalFrame = null;

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
        		
        		Cina.dataEvalFrame = null;
			
			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
			}
			
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

		if(dataEvalInterDataTableFrame!=null){
			dataEvalInterDataTableFrame.setVisible(false);
			dataEvalInterDataTableFrame.dispose();
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
    	saveDialog = new JDialog(this, "Nuclear Data Evaluator's Toolkit Exit", true);
    	saveDialog.setSize(525, 199);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Nuclear Data Evaluator's Toolkit session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Nuclear Data Evaluator's Toolkit session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Nuclear Data Evaluator's Toolkit.", false);
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
		
		//Show the dialog
		saveDialog.setVisible(true);

    }
    
}