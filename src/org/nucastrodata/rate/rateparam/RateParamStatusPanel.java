//This was written by Eric Lingerfelt
//09/22/2003
package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateparam.RateParamUpdateTask;


/**
 * The Class RateParamStatusPanel.
 */
public class RateParamStatusPanel extends WizardPanel implements ActionListener, ItemListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The print level panel. */
	JPanel mainPanel, printLevelPanel; 
	
	/** The print level label. */
	public JLabel statusLabel, timerLabel, printLevelLabel;
	
	/** The status text area. */
	public JTextArea statusTextArea;
		
	/** The abort button. */
	public JButton abortButton;
	
	/** The print level combo box. */
	JComboBox timerComboBox, printLevelComboBox;

    /** The save button. */
    JButton saveButton;

	/** The timer. */
	public static java.util.Timer timer;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param status panel.
	 *
	 * @param ds the ds
	 */
	public RateParamStatusPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(9);

		gbc = new GridBagConstraints();
	
		//Create button/////////////////////////////////////////////////////BUTTONS//////////////////////
		abortButton = new JButton("Abort Program");
		abortButton.setFont(Fonts.buttonFont);
		abortButton.addActionListener(this);
		
		saveButton = new JButton("Save Full Report");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		//Create Labels/////////////////////////////////////////////////////LABELS////////////////////
		statusLabel = new JLabel("Status Report :");
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
		
		addWizardPanel("Rate Parameterizer", "Parameterization Status", "9", "12");
		
		add(mainPanel, BorderLayout.CENTER);
		
		setCurrentState();
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==timerComboBox){
		
			if(timer!=null){
		
				timer.cancel();
			
			}
			
			beginRateParamUpdateTask((int)Double.valueOf((String)timerComboBox.getSelectedItem()).doubleValue());
		
		}
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		statusTextArea.setText(ds.getStatusText());
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setStatusText(statusTextArea.getText());
			
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==abortButton){
		
			if(abortRateParameterization()){
			
				if(!statusLabel.getText().equals("Status Report: Program Complete")){
					
					statusLabel.setText("Status Report: Program Aborted");
					abortButton.setEnabled(false);
					
				}
				
			}
			
		}
	
	}
	
	/**
	 * Abort rate parameterization.
	 *
	 * @return true, if successful
	 */
	public boolean abortRateParameterization(){
	
		if(timer!=null){
			
			timer.cancel();
		
		}
		
		boolean goodAbortRateParam = false;
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("ABORT RATE PARAMETERIZATION", Cina.rateParamFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodAbortRateParam = true;
						
				
				}
				
			}
			
		}
								
		return goodAbortRateParam;
	
	}
	
	/**
	 * Begin rate param update task.
	 *
	 * @param millisec the millisec
	 */
	public void beginRateParamUpdateTask(int millisec){
	
		timer = new java.util.Timer();
		
		timer.schedule(new RateParamUpdateTask(), 0, millisec);
	
	}
	
	/**
	 * Read rate parameterization output.
	 *
	 * @return true, if successful
	 */
	public boolean readRateParameterizationOutput(){
	
		boolean goodReadRateParam = false;
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("RATE PARAMETERIZATION OUTPUT", Cina.rateParamFrame);
		
		if(!flagArray[0]){
		
			//if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodReadRateParam = true;
					
					if(timer!=null){
						
						timer.cancel();
					
					}
				
				}
				
			//}
			
		}
								
		return goodReadRateParam;
	
	}

}