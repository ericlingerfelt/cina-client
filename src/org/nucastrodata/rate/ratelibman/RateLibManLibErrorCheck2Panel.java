package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManLibErrorCheck2Panel.
 */
public class RateLibManLibErrorCheck2Panel extends WizardPanel{
	

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The main panel. */
	JPanel mainPanel; 
	
	/** The top label. */
	JLabel label1, topLabel; 
	
	/** The area1. */
	JTextArea area1;
	
	/** The sp1. */
	JScrollPane sp1;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man lib error check2 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManLibErrorCheck2Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(4);
		Cina.rateLibManFrame.setCurrentPanelIndex(2);
		
		topLabel = new JLabel("<html>The Results report shown below is a representative report. <p>The Library Error Check tool does not currently function.</html>");
		
		label1 = new JLabel("Results: ");
		label1.setFont(Fonts.textFont);
		
		area1 = new JTextArea("", 10, 80);
		
		sp1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp1.setPreferredSize(new Dimension(500, 200));
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(label1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(sp1, gbc);
		
		addWizardPanel("Rate Library Manager", "Library Error Check", "2", "2");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		area1.setText(ds.getModifyLibReport());
		area1.setCaretPosition(0);
	
	}
	
}