package org.nucastrodata.data.dataman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class DataManImportData2Panel.
 */
public class DataManImportData2Panel extends WizardPanel{

	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 

	/** The ref text area. */
	JTextArea refTextArea;
	
	/** The name field. */
	JTextField nameField;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The name label. */
	JLabel refCitLabel, topLabel, nameLabel;

	/** The exception dialog. */
	JDialog exceptionDialog;

	/** The ds. */
	private DataManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new data man import data2 panel.
	 *
	 * @param ds the ds
	 */
	public DataManImportData2Panel(DataManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.dataManFrame.setCurrentFeatureIndex(2);
		Cina.dataManFrame.setCurrentPanelIndex(2);

		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Nuclear Data Manager", "Import Nuclear Data", "2", "4");
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			topLabel = new JLabel("Reaction: " + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
		
			topLabel = new JLabel("Reaction: " 
									+ ds.getImportNucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getImportNucDataDataStructure().getDecay()
									+ "]");
		
		}
		
		nameLabel = new JLabel("Name for Nuclear Data: ");
		nameLabel.setFont(Fonts.textFont);
		
		refCitLabel = new JLabel("Reference Citation: ");
		refCitLabel.setFont(Fonts.textFont);

		refTextArea = new JTextArea("", 3, 60);
		refTextArea.setLineWrap(true);
		refTextArea.setWrapStyleWord(true);
		refTextArea.setFont(Fonts.textFont);
		
		nameField = new JTextField(35);
		
		sp = new JScrollPane(refTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(470, 65));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 5, 10, 5);
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(nameLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 5, 10, 5);
		
		mainPanel.add(nameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(refCitLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		mainPanel.add(sp, gbc);

		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		ds.getImportNucDataDataStructure().setNucDataName(nameField.getText());

		ds.getImportNucDataDataStructure().setRefCitation(refTextArea.getText());
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		nameField.setText(ds.getImportNucDataDataStructure().getNucDataName());
		
		refTextArea.setText(ds.getImportNucDataDataStructure().getRefCitation());
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			topLabel.setText("Reaction: " + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
		
			topLabel.setText("Reaction: " 
									+ ds.getImportNucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getImportNucDataDataStructure().getDecay()
									+ "]");
		
		}
		
		validate();
		
	}

}