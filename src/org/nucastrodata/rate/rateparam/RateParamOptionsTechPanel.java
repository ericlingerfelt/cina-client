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


/**
 * The Class RateParamOptionsTechPanel.
 */
public class RateParamOptionsTechPanel extends WizardPanel implements ItemListener{
				
	/** The method text area. */
	private JTextArea methodTextArea;
	
	/** The method combo box. */
	private JComboBox methodComboBox;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/** The from label. */
	private JLabel reactionLabel, tempLabel, fromLabel;
	
	/** The parallel radio button. */
	private JRadioButton singleRadioButton, parallelRadioButton;
	
	/**
	 * Instantiates a new rate param options tech panel.
	 *
	 * @param ds the ds
	 */
	public RateParamOptionsTechPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Cina.rateParamFrame.setCurrentPanelIndex(5);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel eqnLabel = new JLabel("<html>Parameterize the Reaction Rate with the functional form <p>exp(a1+a2/t9+a3/t913+a4*t913+a5*t9+a6*t953+a7*ln(t9))</html>");
		
		JLabel techLabel = new JLabel("Select a parameterization method below: ");
		
		reactionLabel = new JLabel();
		reactionLabel.setFont(Fonts.textFont);
		
		fromLabel = new JLabel();
		fromLabel.setFont(Fonts.textFont);
		
		tempLabel = new JLabel();
		tempLabel.setFont(Fonts.textFont);
		
		singleRadioButton = new JRadioButton("Customized (Marquardt)", false);
		singleRadioButton.setFont(Fonts.textFont);
		singleRadioButton.addItemListener(this);
		
		parallelRadioButton = new JRadioButton("Automated (Parallel Marquardt)", true);
		parallelRadioButton.setFont(Fonts.textFont);
		parallelRadioButton.addItemListener(this);	
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(singleRadioButton); 
		buttonGroup.add(parallelRadioButton);

		methodTextArea = new JTextArea("Levenberg-Marquardt method for nonlinear parameter estimation from W.H. Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.", 4, 30);
		methodTextArea.setLineWrap(true);
		methodTextArea.setWrapStyleWord(true);
		methodTextArea.setFont(Fonts.textFont);
		methodTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(methodTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 70));
		
		methodTextArea.setCaretPosition(0);
		
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
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		optionsPanel.add(techLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 1;
		optionsPanel.add(singleRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		optionsPanel.add(parallelRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridheight = 2;
		optionsPanel.add(sp, gbc);
		
		gbc.gridheight = 1;
	
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(optionsPanel, BorderLayout.CENTER);
	
		addWizardPanel("Rate Parameterizer", "Select Parameterization Method", "5", "12");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(singleRadioButton.isSelected()){
	
			methodTextArea.setText("Levenberg-Marquardt method for nonlinear parameter estimation from W.H. Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.");
			
		}else if(parallelRadioButton.isSelected()){
			
			methodTextArea.setText("Parallel invocation of Levenberg-Marquardt method with automatic interval and starting parameters selection. Marquardt methods from W.H. Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.");
			
		}
			
		methodTextArea.setCaretPosition(0);
		
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
			methodTextArea.setText("Levenberg-Marquardt method for nonlinear parameter estimation from W.H. Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.");
			singleRadioButton.setSelected(true);
			parallelRadioButton.setSelected(false);
		}else if(ds.getTechniqueParam().equals("PARALLEL MARQUARDT")){
			methodTextArea.setText("Parallel invocation of Levenberg-Marquardt method with automatic interval and starting parameters selection. Marquardt methods from W.H. Press et al. 'Numerical Recipes in FORTRAN', 2nd edition, 1992.");
			singleRadioButton.setSelected(false);
			parallelRadioButton.setSelected(true);
		}
		
		methodTextArea.setCaretPosition(0);
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(singleRadioButton.isSelected()){
			ds.setTechniqueParam("MARQUARDT");
		}else if(parallelRadioButton.isSelected()){
			ds.setTechniqueParam("PARALLEL MARQUARDT");
		}

	}
	
}