package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManCustomLibCreation2Panel.
 */
public class RateLibManCustomLibCreation2Panel extends WizardPanel{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 
	
	/** The top label. */
	JLabel label1, label2, topLabel; 
	
	/** The area2. */
	JTextArea area1, area2;
	
	/** The sp2. */
	JScrollPane sp1, sp2;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man custom lib creation2 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManCustomLibCreation2Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(3);
		Cina.rateLibManFrame.setCurrentPanelIndex(2);
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		topLabel = new JLabel("<html>Registered users will obtain a Results report and a Library Descriptor such as <p>the following generated from the prioritized merge of the Iliadis, NACRE, <p>and REACLIB rate libraries :</html>");
		
		area1 = new JTextArea("", 10, 80);
		area1.setFont(Fonts.textFont);
		area1.setEditable(false);
		area1.setLineWrap(true);
		area1.setWrapStyleWord(true);
	
		if(Cina.cinaMainDataStructure.getUser().equals("guest")){
	
			sp1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp1.setPreferredSize(new Dimension(500, 80));
			
			label1 = new JLabel("THIS IS A MOCK-UP MERGE RESULTS REPORT FOR GUESTS: ");
		
			label2 = new JLabel("THIS IS A MOCK-UP LIBRARY DESCRIPTOR FOR GUESTS: ");

			area2 = new JTextArea("", 10, 40);
			area2.setFont(Fonts.textFont);
			area2.setEditable(false);
			area2.setLineWrap(true);
			area2.setWrapStyleWord(true);
			
			sp2 = new JScrollPane(area2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp2.setPreferredSize(new Dimension(500, 80));
		
		}else{
		
			sp1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp1.setPreferredSize(new Dimension(500, 100));
			
			label1 = new JLabel("Merge Results: ");
			label1.setFont(Fonts.textFont);
		
			label2 = new JLabel("Library Descriptor: ");
			label2.setFont(Fonts.textFont);
			
			area2 = new JTextArea("", 10, 40);
			area2.setFont(Fonts.textFont);
			area2.setEditable(false);
			area2.setLineWrap(true);
			area2.setWrapStyleWord(true);
			
			sp2 = new JScrollPane(area2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp2.setPreferredSize(new Dimension(500, 100));
		
		}
		
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
			mainPanel.add(label1, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp1, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(label2, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp2, gbc);
		
		}else{
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(label1, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp1, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			mainPanel.add(label2, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(sp2, gbc);
		
		}
		
		addWizardPanel("Rate Library Manager", "Merge Existing Libraries", "2", "2");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
	
			area1.setText(ds.getModifyLibReport());
			
			area2.setText(getLibraryDescriptor(ds.getCurrentLibraryDataStructure()));
		
		}else{
		
			area1.setText("The following libraries were merged from highest to lowest priority to create myNewLibrary\n\n"
								+ "1 Iliadis 30 rates copied\n"
								+ "2 NACRE 20 rates copied\n"
								+ "3 REACLIB 61222 rates copied\n\n"
								+ "myNewLibrary now has 61272 rates");
			
			area2.setText("Library name: myNewLibrary\n\n"
								+ "Library notes: Reaction rate library created with the astrodata computational infrastructure suite\n"
								+ "Creation date: 03/25/2004 14:45:23\n"
								+ "Library recipe:   (1) Iliadis  (2) NACRE  (3) REACLIB\n");
								
		}
		
		area1.setCaretPosition(0);
		area2.setCaretPosition(0);
		
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