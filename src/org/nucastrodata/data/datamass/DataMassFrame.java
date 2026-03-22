package org.nucastrodata.data.datamass;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.data.dataeval.DataEvalFrame;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;


import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.data.datamass.DataMassChartColorSettingsFrame;
import org.nucastrodata.data.datamass.DataMassChartFrame;
import org.nucastrodata.data.datamass.DataMassChartTableFrame;
import org.nucastrodata.data.datamass.DataMassHelpFrame;
import org.nucastrodata.data.datamass.DataMassIntroPanel;
import org.nucastrodata.data.datamass.DataMassPlotFrame;
import org.nucastrodata.data.datamass.DataMassRefModelPanel;
import org.nucastrodata.data.datamass.DataMassResultsPanel;
import org.nucastrodata.data.datamass.DataMassSelectIsotopesPanel;
import org.nucastrodata.data.datamass.DataMassSelectorFrame;
import org.nucastrodata.data.datamass.DataMassTableFrame;
import org.nucastrodata.data.datamass.DataMassTheoryModelPanel;


/**
 * The Class DataMassFrame.
 */
public class DataMassFrame extends JFrame implements WindowListener, ActionListener{
	
	/** The continue on button. */
	public JButton continueButton, backButton, endButton, continueOnButton;
	
	/** The choice button group. */
	ButtonGroup choiceButtonGroup;
	
	/** The do not close radio button. */
	JRadioButton saveAndCloseRadioButton, closeRadioButton, doNotCloseRadioButton;
	
	/** The submit button. */
	JButton submitButton;
	
	/** The end button panel. */
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	
	/** The save dialog. */
	JDialog saveDialog;
	
	/** The current panel index. */
	public static int currentPanelIndex;	
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The c. */
	public Container c;
	
	/** The data mass select isotopes panel. */
	public DataMassSelectIsotopesPanel dataMassSelectIsotopesPanel;
	
	/** The data mass theory model panel. */
	public DataMassTheoryModelPanel dataMassTheoryModelPanel;
	
	/** The data mass ref model panel. */
	public DataMassRefModelPanel dataMassRefModelPanel;
	
	/** The data mass results panel. */
	public DataMassResultsPanel dataMassResultsPanel;
	
	/** The data mass plot frame. */
	public DataMassPlotFrame dataMassPlotFrame;
	
	/** The data mass table frame. */
	public DataMassTableFrame dataMassTableFrame;
	
	/** The data mass chart frame. */
	public DataMassChartFrame dataMassChartFrame;
	
	/** The data mass chart table frame. */
	public DataMassChartTableFrame dataMassChartTableFrame;
	
	/** The data mass chart color settings frame. */
	public DataMassChartColorSettingsFrame dataMassChartColorSettingsFrame;

	/** The data mass selector frame. */
	public DataMassSelectorFrame dataMassSelectorFrame;

	/** The data mass help1 frame. */
	public DataMassHelpFrame dataMassHelp1Frame;
	
	/** The data mass help2 frame. */
	public DataMassHelpFrame dataMassHelp2Frame;
	
	/** The data mass intro panel. */
	public DataMassIntroPanel dataMassIntroPanel = new DataMassIntroPanel(); 

	/** The ds. */
	private DataMassDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new data mass frame.
	 *
	 * @param ds the ds
	 */
	public DataMassFrame(DataMassDataStructure ds){
		
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
		
		endButton = new JButton("Close Mass Model Evaluator");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		continueOnButton = new JButton("Nuclear Data Evaluator's Toolkit");
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
		c.add(dataMassIntroPanel, BorderLayout.CENTER);
		
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
	
					c.remove(dataMassIntroPanel);
					
					dataMassSelectIsotopesPanel = new DataMassSelectIsotopesPanel(ds);
					
					dataMassSelectIsotopesPanel.setCurrentState();
				
					c.add(dataMassSelectIsotopesPanel, BorderLayout.CENTER);

					addFullButtonPanel();
					
					break; 
						
				case 1:
				
					dataMassSelectIsotopesPanel.getCurrentState();
					
					if(ds.getSelectMassesFlag()==1){
					
						c.remove(dataMassSelectIsotopesPanel);
						
						dataMassTheoryModelPanel = new DataMassTheoryModelPanel(ds);
					
						dataMassTheoryModelPanel.setCurrentState();
						
						c.add(dataMassTheoryModelPanel, BorderLayout.CENTER);
					
					}else{
					
						if(ds.getMassesSubmitted()){
						
							c.remove(dataMassSelectIsotopesPanel);
						
							dataMassTheoryModelPanel = new DataMassTheoryModelPanel(ds);
						
							dataMassTheoryModelPanel.setCurrentState();
							
							c.add(dataMassTheoryModelPanel, BorderLayout.CENTER);
						
						}else{
							
							String string = "When evaluating masses by entering a range of isotopes or by selecting particular isotopes, "
												+ "click the Open Isotope Selector button. Click the Submit Isotopes button of the Isotope Selector when you "
												+ "have selected the isotopes of interest.";
												
							Dialogs.createExceptionDialog(string, this);
						
						}
					
					}
				
					break;
					
				case 2:
				
					dataMassTheoryModelPanel.getCurrentState();
					
					if(dataMassTheoryModelPanel.theorySelectRadioButton.isSelected()){
					
						if(dataMassTheoryModelPanel.delayDialog==null){
					
							dataMassTheoryModelPanel.openDelayDialog(this);
							dataMassTheoryModelPanel.goodTheoryDataDownload();
						
						}
					
					}else{
					
						if(ds.getTheoryFile()!=null){
					
							if(dataMassTheoryModelPanel.goodTheoryDataUpload()){

								c.remove(dataMassTheoryModelPanel);
																	
								dataMassRefModelPanel = new DataMassRefModelPanel(ds);
					
								dataMassRefModelPanel.setCurrentState();
							
								c.add(dataMassRefModelPanel, BorderLayout.CENTER);
								
							}else{
								
								String string = "The file you have uploaded is empty.";
								Dialogs.createExceptionDialog(string, this);
							
							}
						
						}else{
							
							String string = "Please upload a mass model file or select one from the dropdown menu.";
							Dialogs.createExceptionDialog(string, this);
						
						}
					
					}
				
					break;	
					
				case 3:
				
					dataMassRefModelPanel.getCurrentState();
				
					if(dataMassRefModelPanel.expSelectRadioButton.isSelected()){
					
						if(dataMassRefModelPanel.goodDataSets()){
					
							if(dataMassRefModelPanel.delayDialog==null){
					
								dataMassRefModelPanel.openDelayDialog(this);
								dataMassRefModelPanel.goodExpDataDownload();
							
							}
						
						}else{
								
							String string = "Please select different mass models for analysis.";
							Dialogs.createExceptionDialog(string, this);
						
						}
					
					}else{
						
						if(ds.getExpFile()!=null){
				
							if(dataMassRefModelPanel.goodDataSets()){
				
								if(dataMassRefModelPanel.goodExpDataUpload()){
								
									dataMassRefModelPanel.parseModelDataStructures();
									
									removeFullButtonPanel();
									
									c.remove(dataMassRefModelPanel);
																	
									dataMassResultsPanel = new DataMassResultsPanel(ds);
						
									dataMassResultsPanel.setCurrentState();
								
									addEndButtonPanel();
								
									c.add(dataMassResultsPanel, BorderLayout.CENTER);
								
								}else{
								
									String string = "The file you have uploaded is empty.";
									Dialogs.createExceptionDialog(string, this);
								
								}
							
							}else{
								
								String string = "Please select different mass models for analysis.";
								Dialogs.createExceptionDialog(string, this);
							
							}
					
						}else{
						
							String string = "Please upload a mass model file or select one from the dropdown menu.";
							Dialogs.createExceptionDialog(string, this);
					
						}
					
					}
				
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
						
					removeFullButtonPanel();
					
					c.remove(dataMassSelectIsotopesPanel);
					
					addIntroPanel();
					
					addIntroButtonPanel();

					setCurrentPanelIndex(0);
					
					break; 
						
				case 2:
				
					dataMassTheoryModelPanel.getCurrentState();
				
					dataMassTheoryModelPanel.setVisible(false);
				
					c.remove(dataMassTheoryModelPanel);
				
					dataMassSelectIsotopesPanel = new DataMassSelectIsotopesPanel(ds);
			
					dataMassSelectIsotopesPanel.setCurrentState();
					
					c.add(dataMassSelectIsotopesPanel, BorderLayout.CENTER);
		
					setCurrentPanelIndex(1);

					validate();
				
					break;
					
				case 3:
				
					dataMassRefModelPanel.getCurrentState();
				
					dataMassRefModelPanel.setVisible(false);
				
					c.remove(dataMassRefModelPanel);
				
					dataMassTheoryModelPanel = new DataMassTheoryModelPanel(ds);
			
					dataMassTheoryModelPanel.setCurrentState();
					
					c.add(dataMassTheoryModelPanel, BorderLayout.CENTER);

					setCurrentPanelIndex(2);

					validate();
				
					break;
					
				case 4:
				
					dataMassResultsPanel.getCurrentState();
			
					dataMassResultsPanel.setVisible(false);
			
					c.remove(dataMassResultsPanel);
			
					removeEndButtonPanel();
			
					dataMassRefModelPanel = new DataMassRefModelPanel(ds);
			
					dataMassRefModelPanel.setCurrentState();
					
					c.add(dataMassRefModelPanel, BorderLayout.CENTER);

					addFullButtonPanel();
		
					setCurrentPanelIndex(3);

					validate();

					break;
					
			}
			
			c.validate();
			
		}else if(ae.getSource()==endButton){
				
			//Call createSaveDialog with current panel index
        	createSaveDialog(currentPanelIndex);

		}else if(ae.getSource()==continueOnButton){

			if(Cina.dataEvalFrame==null){
				Cina.dataEvalFrame = new DataEvalFrame(Cina.dataEvalDataStructure);
				Cina.dataEvalFrame.setResizable(true);
				Cina.dataEvalFrame.setSize(580, 420);
				Cina.dataEvalFrame.setVisible(true);
				Cina.dataEvalFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
				Cina.dataEvalFrame.setTitle("Nuclear Data Evaluator's Toolkit");
			}else{
				Cina.dataEvalFrame.setVisible(true);
			}
			
		//Else if the submit button of the save dialog is pressed
		}else if(ae.getSource()==submitButton){

			//If "Save info and close Rate Gen" is chosen
			if(saveAndCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
				
				switch(currentPanelIndex){
					
					case 1:
					
						dataMassSelectIsotopesPanel.getCurrentState();
					
						break;
					
					case 2:
					
						dataMassTheoryModelPanel.getCurrentState();
					
						break;
						
					case 3:
					
						dataMassRefModelPanel.getCurrentState();
					
						break;
						
					case 4:
					
						dataMassResultsPanel.getCurrentState();
						
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
        		
        		Cina.dataMassFrame = null;

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
        		
        		Cina.dataMassFrame = null;
        		
			//Else if do not close chosen
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
				
				//Close dialog box
				saveDialog.setVisible(false);
				saveDialog.dispose();
				
			}
			
		}
				
		
		
		//Redo layout of frame
		c.validate();
		
	}
	
	/**
	 * Mass model3 good exp data download.
	 */
	public void massModel3GoodExpDataDownload(){

		if(dataMassRefModelPanel.goodExpDataDownload){
			
			if(dataMassRefModelPanel.goodDataSets()){
				
				dataMassRefModelPanel.parseModelDataStructures();
				
				dataMassRefModelPanel.closeDelayDialog();
				
				removeFullButtonPanel();
				
				c.remove(dataMassRefModelPanel);
												
				dataMassResultsPanel = new DataMassResultsPanel(ds);
	
				dataMassResultsPanel.setCurrentState();
			
				addEndButtonPanel();
			
				c.add(dataMassResultsPanel, BorderLayout.CENTER);
			
			}else{
			
				String string = "Please select different mass models for analysis.";
				Dialogs.createExceptionDialog(string, this);
			
			}
			
		}
	
	}
	
	/**
	 * Mass model2 good theory data download.
	 */
	public void massModel2GoodTheoryDataDownload(){

		if(dataMassTheoryModelPanel.goodTheoryDataDownload){
										
			c.remove(dataMassTheoryModelPanel);
												
			dataMassRefModelPanel = new DataMassRefModelPanel(ds);

			dataMassRefModelPanel.setCurrentState();
		
			c.add(dataMassRefModelPanel, BorderLayout.CENTER);
			
		}
	
	}
	
	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){
	
		if(dataMassPlotFrame!=null){
			dataMassPlotFrame.setVisible(false);
			dataMassPlotFrame.dispose();
		}

		if(dataMassChartColorSettingsFrame!=null){
			dataMassChartColorSettingsFrame.setVisible(false);
			dataMassChartColorSettingsFrame.dispose();
		}

		if(dataMassChartFrame!=null){
			dataMassChartFrame.setVisible(false);
			dataMassChartFrame.dispose();
		}

		if(dataMassHelp1Frame!=null){
			dataMassHelp1Frame.setVisible(false);
			dataMassHelp1Frame.dispose();
		}

		if(dataMassSelectorFrame!=null){
			dataMassSelectorFrame.setVisible(false);
			dataMassSelectorFrame.dispose();
		}

		if(dataMassHelp2Frame!=null){
			dataMassHelp2Frame.setVisible(false);
			dataMassHelp2Frame.dispose();
		}

		if(dataMassTableFrame!=null){
			dataMassTableFrame.setVisible(false);
			dataMassTableFrame.dispose();
		}

		if(dataMassChartTableFrame!=null){
			dataMassChartTableFrame.setVisible(false);
			dataMassChartTableFrame.dispose();
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
    
    //Method createSaveDialog
    //Args JPanel index
    /**
     * Creates the save dialog.
     *
     * @param index the index
     */
    public void createSaveDialog(int index){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Mass Model Evaluator Exit", true);
    	saveDialog.setSize(457, 180);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	//Check box group for radio buttons
    	choiceButtonGroup = new ButtonGroup();
    	
    	//Create checkboxes and set state and cbGroup
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Mass Model Evaluator session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Mass Model Evaluator session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Mass Model Evaluator.", false);
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
		saveDialog.addWindowListener(this);
		
		//Cina.setFrameColors(saveDialog);
				
		//Show the dialog
		saveDialog.setVisible(true);

    }
    
}