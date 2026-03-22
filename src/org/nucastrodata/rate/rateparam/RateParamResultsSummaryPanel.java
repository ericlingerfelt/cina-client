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
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.WizardPanel;


//This class generates the content for the Rate Gen step 10 of 10
/**
 * The Class RateParamResultsSummaryPanel.
 */
public class RateParamResultsSummaryPanel extends WizardPanel implements ActionListener{

	//Declare buttons
	/** The cancel button. */
	JButton redoParamButton, submitButton, cancelButton;
	
	//Declare labels
	/** The reaction label. */
	JLabel summaryLabel, paramLabel, numParamLabel
			, tempminLabel, tempmaxLabel, maxLabel, chiLabel, reactionLabel;
	
	//Declare dialogs
	/** The redo param dialog. */
	JDialog redoParamDialog;
	
	//Declare text area for central parameter list
	/** The param text area. */
	JTextArea paramTextArea;
	
	//Create String array for parameter list
	/** The param string array. */
	String[] paramStringArray = {"a(1) = ", "a(2) = ", "a(3) = ", "a(4) = ", "a(5) = ", "a(6) = ", "a(7) = "
									, "a(8) = ", "a(9) = ", "a(10)= ", "a(11)= ", "a(12)= ", "a(13)= ", "a(14)= "
									, "a(15)= ", "a(16)= ", "a(17)= ", "a(18)= ", "a(19)= ", "a(20)= ", "a(21)= "
									, "a(22)= ", "a(23)= ", "a(24)= ", "a(25)= ", "a(26)= ", "a(27)= ", "a(28)= "
									, "a(29)= ", "a(30)= ", "a(31)= ", "a(32)= ", "a(33)= ", "a(34)= ", "a(35)= "
									, "a(36)= ", "a(37)= ", "a(38)= ", "a(39)= ", "a(40)= ", "a(41)= ", "a(42)= "
									, "a(43)= ", "a(44)= ", "a(45)= ", "a(46)= ", "a(47)= ", "a(48)= ", "a(49)= "};

	/** The status radio button. */
	JRadioButton techRadioButton, startRadioButton, modifyRadioButton, otherRadioButton, statusRadioButton; 

	/** The ds. */
	private RateParamDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate param results summary panel.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsSummaryPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		addWizardPanel("Rate Parameterizer", "Results Summary", "10", "12");
		
		//Set panel index to 10 
		Cina.rateParamFrame.setCurrentPanelIndex(10);

		//Instantiate gbc
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Create textareas/////////////////////////////////////////////////TEXTAREAS////////////////
		paramTextArea = new JTextArea("");
		paramTextArea.setFont(Fonts.textFontFixedWidth);
		paramTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(paramTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(170, 140));
		
		paramTextArea.setCaretPosition(0);
		
		//Create Buttons////////////////////////////////////////////////////BUTTONS///////////////////
		redoParamButton = new JButton("Redo Parameterization");
		redoParamButton.setFont(Fonts.buttonFont);	
		redoParamButton.addActionListener(this);
		
		//Create labels////////////////////////////////////////////////////LABELS////////////////////
		JLabel summaryLabel = new JLabel("Parameter Summary");
		JLabel paramLabel = new JLabel("Parameters");
		
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
		
		JLabel topLabel = new JLabel("<html>Below is a summary of the parameterization. Click continue if<p>"
											+ "chi-squared and maximum percent difference are acceptable,<p>"
											+ "otherwise click <i>Redo Parameterization</i></html>");

		//Create panels////////////////////////////////////////////////////PANELS///////////////////
		JPanel summaryPanel = new JPanel(new GridBagLayout());
		JPanel paramPanel = new JPanel(new GridBagLayout());
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
		paramPanel.add(paramLabel, gbc);
		
		gbc.gridy = 1;
		paramPanel.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(summaryPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		mainPanel.add(paramPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(redoParamButton, gbc);
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
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

		double[] paramArray = ds.getRateDataStructure().getParameters();
		
		//Set parameter list to blank
		paramTextArea.setText("");
		
		for(int i=0; i<ds.getRateDataStructure().getNumberParameters(); i++){
			
			paramTextArea.append(paramStringArray[i] + NumberFormats.getFormattedParameter(paramArray[i]) + "\n");
		
		}
		
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
		
			redoParamDialog.setVisible(false);
			redoParamDialog.dispose();
		
			if(techRadioButton.isSelected()){
			
				Cina.rateParamFrame.continueFitAtTech();
			
			}else if(startRadioButton.isSelected()){
			
				Cina.rateParamFrame.continueFitAtStart();
			
			}else if(modifyRadioButton.isSelected()){
			
				Cina.rateParamFrame.continueFitAtModify();
			
			}else if(otherRadioButton.isSelected()){
				
				Cina.rateParamFrame.continueFitAtOther();
			
			}else if(statusRadioButton.isSelected()){
			
				ds.setStartingParameters(ds.getRateDataStructure().getParameters());
				ds.setNumberStartingParams(ds.getRateDataStructure().getParameters().length);
				Cina.rateParamFrame.continueFitAtStatus();
			
			}
		
		}else if(ae.getSource()==cancelButton){
		
			redoParamDialog.setVisible(false);
			redoParamDialog.dispose();
		
		}else if(ae.getSource()==redoParamButton){
		
			createRedoParamDialog(Cina.rateParamFrame);
		
		}

	}

	/**
	 * Creates the redo param dialog.
	 *
	 * @param frame the frame
	 */
	public void createRedoParamDialog(Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	redoParamDialog = new JDialog(frame, "Redo Parameterization", true);
    	redoParamDialog.setSize(564, 300);
    	redoParamDialog.getContentPane().setLayout(new GridBagLayout());
		redoParamDialog.setLocationRelativeTo(frame);
		
		JLabel topLabel = new JLabel("<html>Please select an option below and click <i>Submit</i>.</html>");
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(this);
		
		techRadioButton = new JRadioButton("Select new parameterization method", true);
		techRadioButton.setFont(Fonts.textFont);
		
		startRadioButton = new JRadioButton("Select new starting parameters", false);
		startRadioButton.setFont(Fonts.textFont);
		
		if(ds.getTechniqueParam().equals("MARQUARDT")){
			startRadioButton.setEnabled(true);
		}else{
			startRadioButton.setEnabled(false);
		}
		
		modifyRadioButton = new JRadioButton("Modify rate points", false);
		modifyRadioButton.setFont(Fonts.textFont);
		
		otherRadioButton = new JRadioButton("Select or enter new maximum number of iterations or maximum percent difference", false);
		otherRadioButton.setFont(Fonts.textFont);
		
		statusRadioButton = new JRadioButton("Try more iterations with current parameters", false);
		statusRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(techRadioButton);
		buttonGroup.add(startRadioButton);
		buttonGroup.add(modifyRadioButton);
		buttonGroup.add(otherRadioButton);
		buttonGroup.add(statusRadioButton);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(5, 5, 5, 5);
		mainPanel.add(topLabel, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.WEST;
		mainPanel.add(techRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		mainPanel.add(startRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 3;
		mainPanel.add(modifyRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 4;
		mainPanel.add(otherRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 5;
		mainPanel.add(statusRadioButton, gbc1);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.anchor = GridBagConstraints.CENTER;
		buttonPanel.add(submitButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		buttonPanel.add(cancelButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		
		redoParamDialog.getContentPane().add(mainPanel, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		redoParamDialog.getContentPane().add(buttonPanel, gbc1);
		
		//Cina.setFrameColors(redoParamDialog);
		
		redoParamDialog.setVisible(true);
	
	}
	
}