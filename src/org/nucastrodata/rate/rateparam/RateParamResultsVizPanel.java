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
import org.nucastrodata.ColorFormat;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateparam.RateParamResultsEvalFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsFitFrame;


//This class generates the content for the Rate Gen step 10 of 10
/**
 * The Class RateParamResultsVizPanel.
 */
public class RateParamResultsVizPanel extends WizardPanel implements ActionListener{
	
	//Declare buttons
	/** The cancel button. */
	JButton rateFitButton, evalRateButton, redoParamButton, submitButton, cancelButton;
	
	//Declare dialogs
	/** The redo param dialog. */
	JDialog redoParamDialog;
	
	/** The reaction label. */
	JLabel numParamLabel, tempminLabel, tempmaxLabel, maxLabel, chiLabel, reactionLabel; 
	
	/** The status radio button. */
	JRadioButton techRadioButton, startRadioButton, modifyRadioButton, otherRadioButton, statusRadioButton; 
	
	/** The desc text area. */
	JTextArea descTextArea;
	
	//String to be added to end of report when inverse code is in
	/** The inverse string. */
	String inverseString = "Click on the Inverse Parameters to view the inverse parameters.";
	
	/** The default desc string. */
	final String defaultDescString = "Roll your mouse over a button to get a description of each tools capabilities.";
	
	/** The rate fit string. */
	final String rateFitString = "- Create a plot of the percent difference between fit and reaction rate vs. tempereature.";
	
	/** The eval rate string. */
	final String evalRateString = "- Calculate a reaction rate value using the parameterization by entering a temperature value.";
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate param results viz panel.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsVizPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		addWizardPanel("Rate Parameterizer", "Visualize Results", "11", "12");
		
		//Set panel index to 10 
		Cina.rateParamFrame.setCurrentPanelIndex(11);

		//Instantiate gbc
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel summaryLabel = new JLabel("Parameter Summary");
		JLabel descLabel = new JLabel("Tool Description");

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
		
		JLabel topLabel = new JLabel("<html>Click continue if rate plot extrapolation, maximum percent difference,<p>"
										+ "and chi-squared are acceptable, otherwise click <i>Redo Parameterization</i>.<p>"
										+ "Extrapolation problems may be fixed by redoing parameterization with<p>added "
										+ "data points. Place your mouse over each button to get a description of<p>"
										+ "each button's function.</html>");
		
		descTextArea = new JTextArea("");
		descTextArea.setFont(Fonts.textFontFixedWidth);
		descTextArea.setEditable(false);
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		
		JScrollPane sp = new JScrollPane(descTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(170, 140));
		
		//Create Buttons////////////////////////////////////////////////////BUTTONS///////////////////
		rateFitButton = new JButton("Plot % Difference");
		rateFitButton.setFont(Fonts.buttonFont);
		rateFitButton.addActionListener(this);
		rateFitButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(rateFitString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(rateFitButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
					
		evalRateButton = new JButton("Calculate R(Temp Value)");
		evalRateButton.setFont(Fonts.buttonFont);	
		evalRateButton.addActionListener(this);
		evalRateButton.addMouseListener(new MouseListener(){
		
			public void mouseEntered(MouseEvent me){
				descTextArea.setText(evalRateString);
				descTextArea.setCaretPosition(0);
				setButtonForegrounds(evalRateButton);
			}
			public void mouseExited(MouseEvent me){}
			public void mousePressed(MouseEvent me){}
			public void mouseClicked(MouseEvent me){}
			public void mouseReleased(MouseEvent me){}
		
		});
		
		redoParamButton = new JButton("Redo Parameterization");
		redoParamButton.setFont(Fonts.buttonFont);	
		redoParamButton.addActionListener(this);
	
		JPanel summaryPanel = new JPanel(new GridBagLayout());
		JPanel descPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
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
		gbc.insets = new Insets(5, 5, 5, 5);
		descPanel.add(descLabel, gbc);

		gbc.gridy = 1;
		descPanel.add(sp, gbc);

		buttonPanel.add(rateFitButton);
		buttonPanel.add(evalRateButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(summaryPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(descPanel, gbc);
				
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(buttonPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(redoParamButton, gbc);
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
	}
	
	/**
	 * Sets the button foregrounds.
	 *
	 * @param button the new button foregrounds
	 */
	public void setButtonForegrounds(JButton button){
	
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.indexOf("mac")!=-1){
        	rateFitButton.setForeground(ColorFormat.backColor);
    		evalRateButton.setForeground(ColorFormat.backColor);
        }else{
        	rateFitButton.setForeground(ColorFormat.frontColor);
    		evalRateButton.setForeground(ColorFormat.frontColor);
        }
		button.setForeground(Color.red);
	
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
		
		descTextArea.setText(defaultDescString);
		
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
		
		//If source is rate fit button
		}else if(ae.getSource()==rateFitButton){
			
			//Open rate fit interface
			if(Cina.rateParamFrame.rateParamResultsFitFrame==null){
						
				Cina.rateParamFrame.rateParamResultsFitFrame = new RateParamResultsFitFrame(ds);

			}else{
			
				Cina.rateParamFrame.rateParamResultsFitFrame.setFormatPanelState();
				Cina.rateParamFrame.rateParamResultsFitFrame.rateParamResultsFitCanvas.setPlotState();
				Cina.rateParamFrame.rateParamResultsFitFrame.setVisible(true);

			}
			
			if(ds.getExtraPoints()){
			
				String string = "Percent difference plot does not include any added or subtracted points.";
				Dialogs.createExceptionDialog(string, Cina.rateParamFrame.rateParamResultsFitFrame);
			
			}
			
		//If source is evaluate rate button
		}else if(ae.getSource()==evalRateButton){
			
			//Open evaluate rate interface
			if(Cina.rateParamFrame.rateParamResultsEvalFrame==null){
						
				Cina.rateParamFrame.rateParamResultsEvalFrame = new RateParamResultsEvalFrame(ds);
				
			}else{
				
				Cina.rateParamFrame.rateParamResultsEvalFrame.refreshData();
				Cina.rateParamFrame.rateParamResultsEvalFrame.setVisible(true);
				
			}
			
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
		
		statusRadioButton = new JRadioButton("Reparameterize with current parameters", false);
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