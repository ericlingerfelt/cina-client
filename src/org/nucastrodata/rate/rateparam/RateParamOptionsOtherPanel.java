package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateParamOptionsOtherPanel.
 */
public class RateParamOptionsOtherPanel extends WizardPanel implements ItemListener{
	
	/** The max percent diff field. */
	private JTextField iterationsField, maxPercentDiffField; 
	
	/** The from label. */
	public JLabel reactionLabel, tempLabel, methodLabel, submittedLabel, modifiedLabel, fromLabel;
	
	/** The iterations combo box. */
	private JComboBox iterationsComboBox;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param options other panel.
	 *
	 * @param ds the ds
	 */
	public RateParamOptionsOtherPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(8);
		
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel eqnLabel = new JLabel("<html>Parameterize the Reaction Rate with the functional form <p>exp(a1+a2/t9+a3/t913+a4*t913+a5*t9+a6*t953+a7*ln(t9))</html>");
		
		JLabel iterationsLabel = new JLabel("<html>Select a maximum number of iterations: </html>");
		JLabel percentDiffLabel = new JLabel("<html>Enter a maximum percent difference: </html>");
		
		reactionLabel = new JLabel();
		reactionLabel.setFont(Fonts.textFont);
		
		fromLabel = new JLabel();
		fromLabel.setFont(Fonts.textFont);
		
		tempLabel = new JLabel();
		tempLabel.setFont(Fonts.textFont);
		
		methodLabel = new JLabel();
		methodLabel.setFont(Fonts.textFont);
		
		submittedLabel = new JLabel();
		submittedLabel.setFont(Fonts.textFont);

		modifiedLabel = new JLabel();
		modifiedLabel.setFont(Fonts.textFont);

		iterationsComboBox = new JComboBox();
		iterationsComboBox.addItem("quick (20,000)");
		iterationsComboBox.addItem("medium (50,000)");
		iterationsComboBox.addItem("long (150,000)");
		iterationsComboBox.addItem("custom");
		iterationsComboBox.setSelectedIndex(0);
		iterationsComboBox.addItemListener(this);
		iterationsComboBox.setFont(Fonts.textFont);
		
		iterationsField = new JTextField(8);
		iterationsField.setFont(Fonts.textFont);
		
		maxPercentDiffField = new JTextField(8);
		maxPercentDiffField.setFont(Fonts.textFont); 
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridBagLayout());
		JPanel optionsPanel = new JPanel(new GridBagLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(35, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
		labelPanel.add(eqnLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		labelPanel.add(reactionLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		labelPanel.add(fromLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		labelPanel.add(tempLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		labelPanel.add(methodLabel, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 5;
		labelPanel.add(submittedLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		labelPanel.add(modifiedLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		optionsPanel.add(percentDiffLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		optionsPanel.add(maxPercentDiffField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		optionsPanel.add(iterationsLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		optionsPanel.add(iterationsComboBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		optionsPanel.add(iterationsField, gbc);
		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
	
		addWizardPanel("Rate Parameterizer", "Set Other Options", "8", "12");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==iterationsComboBox){
		
			if(iterationsComboBox.getSelectedIndex()==3){
			
				iterationsField.setEditable(true);
			
			}else{
			
				iterationsField.setEditable(false);
			
			}
		
		}
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		String reactionName = ds.getRateDataStructure().getReactionString();
		
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			reactionName += " [" + ds.getRateDataStructure().getDecay() + "]";
		
		}
		
		reactionLabel.setText("Reaction: " + reactionName);
		tempLabel.setText("Temperature Range: (" + ds.getAllowedTempmin() + ", " + ds.getAllowedTempmax() + ")");

		if(Cina.cinaMainDataStructure.getUser().equals("guest") && !ds.getRateSubmitted()){
			fromLabel.setText("Rate data from guest file: " + ds.getInputFilename());
		}else if(ds.getRateSubmitted()){
			fromLabel.setText("Rate data submitted from Rate Generator");
		}else if(ds.getPastedData()){
			fromLabel.setText("Rate data pasted");
		}else if(ds.getUploadData()){
			fromLabel.setText("Rate data from uploaded file: " + ds.getInputFilename());
		}else{
			fromLabel.setText("Rate data (" + ds.getCurrentNucDataString() + ") from " + ds.getCurrentNucDataSetString() + " data set");
		}

		if(ds.getTechniqueParam().equals("MARQUARDT")){
			methodLabel.setText("Parameterization method: Customized (Marquardt)");
		}else if(ds.getTechniqueParam().equals("PARALLEL MARQUARDT")){
			methodLabel.setText("Parameterization method: Automated (Parallel Marquardt)");
		}
		
		if(ds.getTechniqueParam().equals("MARQUARDT")){
		
			if(ds.getStartingParameters()!=null){
			
				submittedLabel.setText("<html><i>Starting Parameters Submitted!</i></html>");
			
			}
		
		}else{
		
			submittedLabel.setText("");
		
		}
		
		if(ds.getRateModified()){
			modifiedLabel.setText("<html><i>Rate Points Modified!</i></html>");
		}else{
			modifiedLabel.setText("");
		}
		
		if(ds.getMaxNumberIterations()==20000){
			
			iterationsComboBox.setSelectedIndex(0);
			iterationsField.setEditable(false);
			
		}else if(ds.getMaxNumberIterations()==50000){
		
			iterationsComboBox.setSelectedIndex(1);
			iterationsField.setEditable(false);
			
		}else if(ds.getMaxNumberIterations()==150000){
		
			iterationsComboBox.setSelectedIndex(2);
			iterationsField.setEditable(false);
			
		}else{
		
			iterationsComboBox.setSelectedIndex(3);
			iterationsField.setEditable(true);
			iterationsField.setText(String.valueOf(ds.getMaxNumberIterations()));
		
		}

		maxPercentDiffField.setText(String.valueOf(ds.getMaxPercentDiffAllowed()));
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		switch(iterationsComboBox.getSelectedIndex()){
		
			case 0:
			
				ds.setMaxNumberIterations(20000);
				break;
			
			case 1:
			
				ds.setMaxNumberIterations(50000);
				break;
				
			case 2:
			
				ds.setMaxNumberIterations(150000);
				break;
				
			case 3:
			
				ds.setMaxNumberIterations((int)Double.valueOf(iterationsField.getText()).doubleValue());
				break;	
		
		}
		
		ds.setMaxPercentDiffAllowed(Double.valueOf(maxPercentDiffField.getText()).doubleValue());
	
	}

	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){
	
		boolean pass = false;
	
		try{
		
			if(Double.valueOf(maxPercentDiffField.getText()).doubleValue() > 100){
			
				String string = "Percent difference must be less than 100.";
			
				Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
				
				pass = false;
			
			}else if(iterationsComboBox.getSelectedIndex()==3){
			
				if(Double.valueOf(iterationsField.getText()).doubleValue() > 500000){
				
					String string = "Maximum number of iterations must be less than 500,000.";
				
					Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
					
					pass = false;
				
				}else{
				
					pass = true;
				
				}
			
			}else{
			
				pass = true;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Entries must be numeric values.";
			
			Dialogs.createExceptionDialog(string, Cina.rateParamFrame);
			
			pass = false;
		
		}
		
		return pass;
	
	}
	
}