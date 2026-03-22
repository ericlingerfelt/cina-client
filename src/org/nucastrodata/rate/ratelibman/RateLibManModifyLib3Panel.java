package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManModifyLib3Panel.
 */
public class RateLibManModifyLib3Panel extends WizardPanel{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 

	/** The des text area. */
	JTextArea resultsTextArea, desTextArea;
	
	/** The top label. */
	JLabel resultsLabel, desLabel, topLabel;
		
	/** The sp1. */
	JScrollPane sp, sp1;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man modify lib3 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManModifyLib3Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(2);
		Cina.rateLibManFrame.setCurrentPanelIndex(3);

		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
	
		topLabel = new JLabel("<html>Registered users will obtain a Results report and a Library Descriptor such as <p>the following generated from the addition of 3 NACRE rates to the REACLIB library :</html>");

		resultsTextArea = new JTextArea("", 10, 80);
		resultsTextArea.setFont(Fonts.textFont);
		resultsTextArea.setEditable(false);
		resultsTextArea.setLineWrap(true);
		resultsTextArea.setWrapStyleWord(true);
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			sp = new JScrollPane(resultsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp.setPreferredSize(new Dimension(500, 80));
			
			resultsLabel = new JLabel("THIS IS A MOCK-UP RESULTS REPORT FOR GUESTS: ");
			
			desLabel = new JLabel("THIS IS A MOCK-UP LIBRARY DESCRIPTOR FOR GUESTS: ");
					
			desTextArea = new JTextArea("", 10, 80);
			desTextArea.setFont(Fonts.textFont);
			desTextArea.setEditable(false);
			desTextArea.setLineWrap(true);
			desTextArea.setWrapStyleWord(true);
			
			sp1 = new JScrollPane(desTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp1.setPreferredSize(new Dimension(500, 80));
		
		}else{
		
			sp = new JScrollPane(resultsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp.setPreferredSize(new Dimension(500, 100));
			
			resultsLabel = new JLabel("Results: ");
			resultsLabel.setFont(Fonts.textFont);
			
			desLabel = new JLabel("Library Descriptor: ");
			desLabel.setFont(Fonts.textFont);
					
			desTextArea = new JTextArea("", 10, 80);
			desTextArea.setFont(Fonts.textFont);
			desTextArea.setEditable(false);
			desTextArea.setLineWrap(true);
			desTextArea.setWrapStyleWord(true);
			
			sp1 = new JScrollPane(desTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp1.setPreferredSize(new Dimension(500, 100));
		
		}
		
		addWizardPanel("Rate Library Manager", "Create and Modify Library", "3", "3");
		
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(topLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(resultsLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(desLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp1, gbc);
		
		}else{
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(resultsLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(desLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp1, gbc);
		
		}

		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
	
			resultsTextArea.setText(ds.getModifyLibReport()
										+ "\n\n"
										+ ds.getModifyRatesReport());
										
			desTextArea.setText(getLibraryDescriptor(ds.getCurrentLibraryDataStructure()));
	
		}else{
		
			resultsTextArea.setText("Rate library \"REACLIB\" copied to \"myNewLibrary\"\n"
							+ "These libraries have 61272 rates\n\n"
							+ "p + 13N --> 14O in myNewLibrary replaced with rate from NACRE\n"
							+ "p + 22Ne --> 23Na in myNewLibrary replaced with rate from NACRE\n"
							+ "p + 27Al --> 28Si in myNewLibrary replaced with rate from NACRE\n");
			
										
			desTextArea.setText("Library name: myNewLibrary\n"
							+ "Library notes: Reaction rate library created with the astrodata computational infrastructure suite\n"
							+ "Creation date: 03/25/2004 14:32:30\n"
							+ "Library recipe:   (1) REACLIB, added p + 13N --> 14O (NACRE), added p + 22Ne --> 23Na (NACRE), added p + 27Al --> 28Si (NACRE)\n");
					
		}
		
		resultsTextArea.setCaretPosition(0);
		desTextArea.setCaretPosition(0);
		
	}
	
	/**
	 * Gets the library descriptor.
	 *
	 * @param applds the applds
	 * @return the library descriptor
	 */
	public String getLibraryDescriptor(LibraryDataStructure applds){
	
		String string = "";
		
		string = "Library name: " + applds.getLibName() + "\n";
		
		string = string + "Library notes: " + applds.getLibNotes() + "\n";
		
		string = string + "Creation date: " + applds.getCreationDate() + "\n";
		
		string = string + "Library recipe: " + applds.getLibraryRecipe() + "\n";
		
		return string;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
}