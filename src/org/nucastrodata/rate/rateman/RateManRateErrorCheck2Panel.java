package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateManRateErrorCheck2Panel.
 */
public class RateManRateErrorCheck2Panel extends WizardPanel{
	
	/** The area1. */
	private JTextArea area1;
	
	/** The ds. */
	private RateManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate man rate error check2 panel.
	 *
	 * @param ds the ds
	 */
	public RateManRateErrorCheck2Panel(RateManDataStructure ds){
		
		this.ds = ds;		
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(5);
		Cina.rateManFrame.setCurrentPanelIndex(2);

		JLabel topLabel = new JLabel("<html>The Results report shown below is a representative report. <p>The Rate Error Check tool does not currently function.</html>");
		
		JLabel label1 = new JLabel("Results: ");
		label1.setFont(Fonts.textFont);
		
		area1 = new JTextArea("", 10, 80);
		
		JScrollPane sp1 = new JScrollPane(area1
								, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
								, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(500, 200));
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(label1, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(sp1, gbc);
		
		addWizardPanel("Rate Manager", "Rate Error Check", "2", "2");
		add(mainPanel, BorderLayout.CENTER);
		validate();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		area1.setText(ds.getModifyRatesReport());
		area1.setCaretPosition(0);
	}
	
}