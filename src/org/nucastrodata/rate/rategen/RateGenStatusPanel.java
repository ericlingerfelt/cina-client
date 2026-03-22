//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rategen.RateGenUpdateTask;


//This class creates the content for the rate generation update screen
//It is step 6 of 10 for the rate gen feature
/**
 * The Class RateGenStatusPanel.
 */
public class RateGenStatusPanel extends WizardPanel implements ActionListener, ItemListener{
	
	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The print level panel. */
	JPanel mainPanel, printLevelPanel; 
	
	//Declare components
	/** The timer label. */
	public JLabel statusLabel, printLevelLabel, timerLabel;
	
	/** The status text area. */
	public JTextArea statusTextArea;
	
	/** The print level combo box. */
	JComboBox printLevelComboBox;
	
	/** The timer combo box. */
	JComboBox timerComboBox;	
	
	/** The save button. */
	public JButton abortButton, saveButton;
	
	//Declare timer object for updates
	/** The timer. */
	public static java.util.Timer timer;
	
	//Declare scrollpane for status text area
	/** The sp. */
	JScrollPane sp;
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate gen status panel.
	 *
	 * @param ds the ds
	 */
	public RateGenStatusPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		
		//Set current panel index to 6
		Cina.rateGenFrame.setCurrentPanelIndex(6);

		//Instantiate gbc
		gbc = new GridBagConstraints();
	
		//Create button/////////////////////////////////////////////////////BUTTONS//////////////////////
		abortButton = new JButton("Abort Program");
		abortButton.setFont(Fonts.buttonFont);
		abortButton.addActionListener(this);
		
		saveButton = new JButton("Save Full Report");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		//Create Labels/////////////////////////////////////////////////////LABELS////////////////////
		statusLabel = new JLabel("Status of Numerical Integration :");
		statusLabel.setFont(Fonts.textFont);
		
		printLevelLabel = new JLabel("Print Level :");
		printLevelLabel.setFont(Fonts.textFont);
		
		timerLabel = new JLabel("Status update (in millisec) :");
		timerLabel.setFont(Fonts.textFont);
		
		//Create choice/////////////////////////////////////////////////////CHOICE////////////////////
		printLevelComboBox = new JComboBox();
		printLevelComboBox.addItem("1");
		printLevelComboBox.addItem("2");
		printLevelComboBox.addItem("3");
		printLevelComboBox.addItem("4");
		
		timerComboBox = new JComboBox();
		timerComboBox.addItem("500");
		timerComboBox.addItem("1000");
		timerComboBox.addItem("2000");
		timerComboBox.addItem("5000");
		timerComboBox.setSelectedIndex(2);
		timerComboBox.addItemListener(this);
		timerComboBox.setFont(Fonts.textFont);
		
		//Create text areas/////////////////////////////////////////////////TEXTAREAS//////////////////////
		statusTextArea = new JTextArea("", 10, 80);
		statusTextArea.setFont(Fonts.textFont);
		statusTextArea.setEditable(false);
		
		sp = new JScrollPane(statusTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(500, 200));
		
		printLevelPanel = new JPanel(new GridBagLayout());
		
		mainPanel = new JPanel(new GridBagLayout());
		
		//Set gbc and put it all together
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(statusLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		mainPanel.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 0, 0, 5);
		printLevelPanel.add(abortButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//printLevelPanel.add(saveButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 150, 0, 0);
		gbc.anchor = GridBagConstraints.EAST;
		printLevelPanel.add(timerLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		printLevelPanel.add(timerComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(printLevelPanel, gbc);
		
		addWizardPanel("Rate Generator", "Rate Generation Status", "6", "7");
		
		add(mainPanel, BorderLayout.CENTER);
		
		//Set current state of this panel
		setCurrentState();
		
		validate();
	}
	
	//Method for Item Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		//If source is timer combo box
		if(ie.getSource()==timerComboBox){
		
			//If timer exists
			if(timer!=null){
		
				//Cancel timer
				timer.cancel();
			
			}
			
			//Start new timer for status updates
			beginRateGenUpdateTask((int)Double.valueOf((String)timerComboBox.getSelectedItem()).doubleValue());
		
		}
	
	}
	
	//Method to set current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		//Set text of text areas
		statusTextArea.setText(ds.getStatusText());
		
	}
	
	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		//Assign status text to rate gen data structure
		ds.setStatusText(statusTextArea.getText());
			
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		//If source is abort button
		if(ae.getSource()==abortButton){
		
			//If CGI call for abort rate gen calc is successful
			if(abortRateGeneration()){
			
				//If program is not already finished
				if(!statusLabel.getText().equals("Status Report: Program Complete")){
					
					//Set text opf status label
					statusLabel.setText("Status Report: Program Aborted");
					
					//Set abort button disabled
					abortButton.setEnabled(false);
					
				}
				
			}
		
		}
		
	}
	
	//Method to make CGI call to abort rate gen calc
	/**
	 * Abort rate generation.
	 *
	 * @return true, if successful
	 */
	public boolean abortRateGeneration(){
	
//		If timer exists
		if(timer!=null){
			
			//Cancel timer
			timer.cancel();
		
		}
		
		//Create local boolean
		boolean goodAbortRateGen = false;
		
		//Make CGI call for ABORT RATE GENERATION action
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("ABORT RATE GENERATION", Cina.rateGenFrame);
		
		//If no errors
		if(!flagArray[0]){
		
			//If no reasons
			if(!flagArray[2]){
			
				//If no cautions
				if(!flagArray[1]){
				
					//Set boolean to true
					goodAbortRateGen = true;
					
				}
				
			}
			
		}
		
		//Return boolean						
		return goodAbortRateGen;
	
	}
	
	//Method to begin status update tasks
	/**
	 * Begin rate gen update task.
	 *
	 * @param millisec the millisec
	 */
	public void beginRateGenUpdateTask(int millisec){
	
		//Create new timer
		timer = new java.util.Timer();
		
		//Use schedule method of timer class to begin updates 
		timer.schedule(new RateGenUpdateTask(), 0, millisec);
	
	}
	
	//Method to get rate gen output form CGI 
	/**
	 * Read rate generation output.
	 *
	 * @return true, if successful
	 */
	public boolean readRateGenerationOutput(){
	
		//Create local boolean
		boolean goodReadRateGen = false;
		
		//Make CGI call for RATE GENERATION OUTPUT action
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("RATE GENERATION OUTPUT", Cina.rateGenFrame);
		
		//If no errors
		if(!flagArray[0]){
		
			//If no reasons
			if(!flagArray[2]){
			
				//If no cautions
				if(!flagArray[1]){
				
					//Set boolean to true
					goodReadRateGen = true;
					
					//If timer exists
					if(timer!=null){
					
						//Cancel timer
						timer.cancel();
					
					}
				
				}
				
			}
			
		}
		
		//Return boolean					
		return goodReadRateGen;
	
	}
	
}