package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateparam.RateParamPointFrame;


/**
 * The Class RateParamOptionsModifyPanel.
 */
public class RateParamOptionsModifyPanel extends WizardPanel implements ActionListener{
	
	/** The from label. */
	public JLabel reactionLabel, tempLabel, methodLabel, submittedLabel, modifiedLabel, fromLabel;
	
	/** The original button. */
	private JButton modifyButton, originalButton;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param options modify panel.
	 *
	 * @param ds the ds
	 */
	public RateParamOptionsModifyPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(7);
		
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel eqnLabel = new JLabel("<html>Parameterize the Reaction Rate with the functional form <p>exp(a1+a2/t9+a3/t913+a4*t913+a5*t9+a6*t953+a7*ln(t9))</html>");
		
		JLabel modifyLabel = new JLabel("<html>Click <i>Modify Rate Points</i> to add, delete, or modify reaction rate points."
										+ "<p>Click <i>Use Original Rate Points</i> to revert to the unmodified set.</html>");
		
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
		
		modifyButton = new JButton("Modify Rate Points");
		modifyButton.addActionListener(this);
		modifyButton.setFont(Fonts.buttonFont);
		
		originalButton = new JButton("Use Original Rate Points");
		originalButton.addActionListener(this);
		originalButton.setFont(Fonts.buttonFont);
		
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
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		optionsPanel.add(modifyLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		optionsPanel.add(modifyButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		optionsPanel.add(originalButton, gbc);
		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
	
		addWizardPanel("Rate Parameterizer", "Modify Rate Points", "6", "12");
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==modifyButton){
		
			if(Cina.rateParamFrame.rateParamPointFrame==null){
			
				Cina.rateParamFrame.rateParamPointFrame = new RateParamPointFrame(ds);
				Cina.rateParamFrame.rateParamPointFrame.setCurrentState();
				Cina.rateParamFrame.rateParamPointFrame.setVisible(true);
			
			}else{
			
				Cina.rateParamFrame.rateParamPointFrame.setCurrentState();
				Cina.rateParamFrame.rateParamPointFrame.setVisible(true);
			
			}
		
		}else if(ae.getSource()==originalButton){
			
			ds.setRateDataArrayExtra(null);
			ds.setTempDataArrayExtra(null);
			ds.setExtraPoints(false);
			ds.setRateModified(false);
			
			double[] tempArray = new double[ds.getTempDataArrayOrig().length];
			double[] rateArray = new double[ds.getRateDataArrayOrig().length];
			
			for(int i=0; i<tempArray.length; i++){
				tempArray[i] = ds.getTempDataArrayOrig()[i];
				rateArray[i] = ds.getRateDataArrayOrig()[i];
			}
			
			ds.setTempDataArray(tempArray);
			ds.setRateDataArray(rateArray);
			
			setCurrentState();
		
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
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
	}
	
}