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
import org.nucastrodata.rate.rateparam.RateParamStartFrame;


/**
 * The Class RateParamOptionsStartPanel.
 */
public class RateParamOptionsStartPanel extends WizardPanel implements ActionListener{
				
	/** The starting params combo box. */
	public JComboBox startingParamsComboBox;
	
	/** The param button. */
	private JButton paramButton; 
	
	/** The from label. */
	public JLabel reactionLabel, tempLabel, methodLabel, submittedLabel, fromLabel;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param options start panel.
	 *
	 * @param ds the ds
	 */
	public RateParamOptionsStartPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(6);
		
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel eqnLabel = new JLabel("<html>Parameterize the Reaction Rate with the functional form <p>exp(a1+a2/t9+a3/t913+a4*t913+a5*t9+a6*t953+a7*ln(t9))</html>");
		
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
		
		JLabel startLabel = new JLabel("<html>Select number of starting parameters below or click <i>Select Starting Parameters</i></html>");
		
		JLabel paramLabel = new JLabel("Number of starting parameters: ");
		paramLabel.setFont(Fonts.textFont);
		
		startingParamsComboBox = new JComboBox();
		startingParamsComboBox.addItem("7");
		startingParamsComboBox.addItem("14");
		startingParamsComboBox.addItem("21");
		startingParamsComboBox.addItem("28");
		startingParamsComboBox.setSelectedIndex(0);
		startingParamsComboBox.setFont(Fonts.textFont);
		
		paramButton = new JButton("Select Starting Parameters");
		paramButton.addActionListener(this);
		paramButton.setFont(Fonts.buttonFont);

		JRadioButton startRadioButton = new JRadioButton("Set Starting Parameters (recommended)", true);
		startRadioButton.setFont(Fonts.textFont);
		
		JRadioButton basisRadioButton = new JRadioButton("Use Basis Parameter Set", false);
		basisRadioButton.setFont(Fonts.textFont);
		basisRadioButton.setEnabled(false);
		
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
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		optionsPanel.add(startLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		optionsPanel.add(startRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		optionsPanel.add(basisRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		optionsPanel.add(paramButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		optionsPanel.add(paramLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		optionsPanel.add(startingParamsComboBox, gbc);
		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
	
		addWizardPanel("Rate Parameterizer", "Set Starting Parameters", "6", "12");
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==paramButton){
		
			ds.setLibGroup("PUBLIC");
			boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			ds.setLibGroup("SHARED");
			boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			ds.setLibGroup("USER");
			boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateParamFrame);
			
			if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
				
				if(Cina.rateParamFrame.rateParamStartFrame == null){
				
					Cina.rateParamFrame.rateParamStartFrame = new RateParamStartFrame(ds);
					Cina.rateParamFrame.rateParamStartFrame.createLibGroupNodes();
					Cina.rateParamFrame.rateParamStartFrame.setCurrentState();
					Cina.rateParamFrame.rateParamStartFrame.setVisible(true);
				
				}else{
					
					Cina.rateParamFrame.rateParamStartFrame.dispose();
					Cina.rateParamFrame.rateParamStartFrame.setVisible(false);
					Cina.rateParamFrame.rateParamStartFrame = null;
					
					Cina.rateParamFrame.rateParamStartFrame = new RateParamStartFrame(ds);
					Cina.rateParamFrame.rateParamStartFrame.createLibGroupNodes();
					Cina.rateParamFrame.rateParamStartFrame.setCurrentState();
					Cina.rateParamFrame.rateParamStartFrame.setVisible(true);
					
				}
			
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
		
		startingParamsComboBox.setSelectedItem(String.valueOf(ds.getNumberStartingParams()));
		
		if(ds.getStartingParameters()!=null && !ds.getStartFromRate()){
		
			submittedLabel.setText("<html><i>Starting Parameters Submitted!</i></html>");
		
		}else if(ds.getStartingParameters()!=null && ds.getStartFromRate()){
			
			submittedLabel.setText("<html><i>Starting Parameters Submitted from " + ds.getStartRate() + " (" + ds.getStartLib() + ")</i></html>");
		
		}
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		ds.setNumberStartingParams((int)Double.valueOf((String)startingParamsComboBox.getSelectedItem()).doubleValue());
	
	}
	
}