package org.nucastrodata.rate.rateman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManCreateRate2Panel.
 */
public class RateManCreateRate2Panel extends WizardPanel{
	
	/** The ref text area. */
	private JTextArea refTextArea;
	
	/** The temp max field. */
	private JTextField qValueField, chiSquaredField, maxPercentDiffField
				, biblioCodeField, tempMinField, tempMaxField;
	
	/** The top label2. */
	private JLabel topLabel2;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate man create rate2 panel.
	 *
	 * @param ds the ds
	 */
	public RateManCreateRate2Panel(RateManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(2);
		Cina.rateManFrame.setCurrentPanelIndex(2);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Create New Rate", "2", "3");
		
		String[] labelStringArray = {"Q-value: "
									, "Chisquared: "
									, "Max Percent Diff: "
									, "Biblio Code: "
									, "Valid Temp Range (T9): "
									, " to "};
									
		JLabel[] labelArray = new JLabel[6];
		
		for(int i=0; i<labelArray.length; i++){
		
			labelArray[i] = new JLabel(labelStringArray[i]);
			labelArray[i].setFont(Fonts.textFont);
		
		}
		
		JLabel topLabel = new JLabel("Reaction: ");
		
		topLabel2 = new JLabel(ds.getCreateRateDataStructure().getReactionString());
		topLabel2.setFont(Fonts.textFont);
		
		JLabel biblioLabel2 = new JLabel("(must be less than 5 characters)");
		biblioLabel2.setFont(Fonts.textFont);
		
		biblioCodeField = new JTextField(10);
		
		JLabel refCitLabel = new JLabel("Reference Citation: ");
		refCitLabel.setFont(Fonts.textFont);

		refTextArea = new JTextArea("", 3, 60);
		refTextArea.setLineWrap(true);
		refTextArea.setWrapStyleWord(true);
		refTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(refTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(470, 65));
		
		add(mainPanel, BorderLayout.CENTER);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(topLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(topLabel2, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(labelArray[3], gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(biblioCodeField, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(biblioLabel2, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		mainPanel.add(refCitLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(sp, gbc);
		
		validate();
		
	}
	
	/**
	 * Check data fields.
	 *
	 * @return true, if successful
	 */
	public boolean checkDataFields(){

		boolean pass = true;
	
		if(!biblioCodeField.getText().equals("") && biblioCodeField.getText().length()>4){
		
			pass = false;
			String string = "Biblio Code must be less than 5 characters.";
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
		
		}
		
		return pass;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		ds.getCreateRateDataStructure().setBiblioString(biblioCodeField.getText());
		ds.getCreateRateDataStructure().setRefCitation(refTextArea.getText());

	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		biblioCodeField.setText(ds.getCreateRateDataStructure().getBiblioString());
		refTextArea.setText(ds.getCreateRateDataStructure().getRefCitation());
		
		if(ds.getCreateRateDataStructure().getDecay().equals("")){
		
			topLabel2.setText(ds.getCreateRateDataStructure().getReactionString());
		
		}else{
			
			topLabel2.setText(ds.getCreateRateDataStructure().getReactionString()
								+ " ["
								+ ds.getCreateRateDataStructure().getDecay()
								+ "]");
		
		}
		
		validate();
		
	}
	
}