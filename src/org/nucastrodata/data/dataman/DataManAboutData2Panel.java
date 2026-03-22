package org.nucastrodata.data.dataman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.WizardPanel;


/**
 * The Class DataManAboutData2Panel.
 */
public class DataManAboutData2Panel extends WizardPanel implements ActionListener, ItemListener{
	
	/** The copy button. */
	private JButton saveButton, copyButton;
	
	/** The ref citation box. */
	private JCheckBox pointsBox
						, orgCodeBox
						, peopleCodeBox
						, nucDataNotesBox
						, creationDateBox
						, refCitationBox;
	
	/** The text area string. */
	private String textAreaString;
	
	/** The output text area. */
	private JTextArea outputTextArea;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	/**
	 * Instantiates a new data man about data2 panel.
	 *
	 * @param ds the ds
	 */					
	public DataManAboutData2Panel(DataManDataStructure ds){
		
		this.ds = ds;
		
		Cina.dataManFrame.setCurrentFeatureIndex(1);
		Cina.dataManFrame.setCurrentPanelIndex(2);
		addWizardPanel("Nuclear Data Manager", "Nuclear Data Info", "2", "2");

		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel titleLabel = new JLabel("<html>Select Nuclear Data<p>Information for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);

		pointsBox = new JCheckBox("Data Points", false);
		pointsBox.addItemListener(this);
		pointsBox.setFont(Fonts.textFont);
		
		orgCodeBox = new JCheckBox("Organization Code", false);
		orgCodeBox.addItemListener(this);
		orgCodeBox.setFont(Fonts.textFont);
		
		peopleCodeBox = new JCheckBox("Scientist Code", false);
		peopleCodeBox.addItemListener(this);
		peopleCodeBox.setFont(Fonts.textFont);
		
		nucDataNotesBox = new JCheckBox("Notes", false);
		nucDataNotesBox.addItemListener(this);
		nucDataNotesBox.setFont(Fonts.textFont);
		
		creationDateBox = new JCheckBox("Creation Date", false);
		creationDateBox.addItemListener(this);
		creationDateBox.setFont(Fonts.textFont);
		
		refCitationBox = new JCheckBox("Reference Citation", false);
		refCitationBox.addItemListener(this);
		refCitationBox.setFont(Fonts.textFont);
				
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(saveButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(copyButton, gbc);
		
		JPanel cbPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 10, 0);
		cbPanel.add(titleLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		cbPanel.add(pointsBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		cbPanel.add(orgCodeBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		cbPanel.add(peopleCodeBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		cbPanel.add(nucDataNotesBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		cbPanel.add(refCitationBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		cbPanel.add(creationDateBox, gbc);
		
		mainPanel.add(cbPanel, BorderLayout.WEST);
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Refresh data.
	 */
	public void refreshData(){

		outputTextArea.setText("");
		textAreaString = "";
		int numberNucData = 0;
		
		for(int i=0; i<ds.getCurrentNucDataDataStructureArray().length; i++){
			textAreaString = textAreaString + getReactionStringText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n\n";			
			if(orgCodeBox.isSelected()){textAreaString = textAreaString + getOrgCodeText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}
			if(peopleCodeBox.isSelected()){textAreaString = textAreaString + getPeopleCodeText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}
			if(nucDataNotesBox.isSelected()){textAreaString = textAreaString + getNucDataNotesText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}
			if(refCitationBox.isSelected()){textAreaString = textAreaString + getRefCitationText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}
			if(creationDateBox.isSelected()){textAreaString = textAreaString + getCreationDateText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}
			if(pointsBox.isSelected()){textAreaString = textAreaString + "\n" + getPointsText(ds.getCurrentNucDataDataStructureArray()[i]) + "\n";}			
			textAreaString = textAreaString + "\n" 
								+ "__________________________________________________________________________________________________"
								+ "\n";

			numberNucData++;	
    	}
	
		if(!pointsBox.isSelected()
			&& !orgCodeBox.isSelected()
			&& !peopleCodeBox.isSelected()
			&& !nucDataNotesBox.isSelected()
			&& !refCitationBox.isSelected()
			&& !creationDateBox.isSelected()){
			textAreaString = "";
		}
	
		outputTextArea.setText(textAreaString);
		outputTextArea.setCaretPosition(0);
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){refreshData();}

	/**
	 * Gets the reaction string text.
	 *
	 * @param appndds the appndds
	 * @return the reaction string text
	 */
	public String getReactionStringText(NucDataDataStructure appndds){
		String string = "";
		if(appndds.getDecay().equals("")){
			string = "Nuclear Data Information for "
						+ appndds.getNucDataName()
						+ " ("
						+ appndds.getReactionString()
						+ ", "
						+ appndds.getNucDataSet()
						+ ")";
		}else{
			string = "Nuclear Data Information for "
						+ appndds.getNucDataName()
						+ " ("
						+ appndds.getReactionString()
						+ " ["
						+ appndds.getDecay()
						+ "]"
						+ ", "
						+ appndds.getNucDataSet()
						+ ")";
		}
		return string;
	}

	/**
	 * Gets the points text.
	 *
	 * @param appndds the appndds
	 * @return the points text
	 */
	public String getPointsText(NucDataDataStructure appndds){
		String string = "";
		String type = "";
		type = appndds.getNucDataType();

		if(type.equals("CS(E)")){ 			
			string += "Energy (keV)   " 
						+ "Cross Section (b)" 
						+ "\n\n";
		}else if(type.equals("S(E)")){
			string += "Energy (keV)   " 
						+ "S Factor (keV-b)" 
						+ "\n\n";
		}

		double[] XDataArray = appndds.getXDataArray();
		double[] YDataArray = appndds.getYDataArray();
		
		for(int i=0; i<appndds.getNumberPoints(); i++){
			string += NumberFormats.getFormattedValueLong(XDataArray[i]) 
						+ "    " 
						+ NumberFormats.getFormattedValueLong(YDataArray[i])
						+ "\n";
		}
		return string;
	}
	
	/**
	 * Gets the org code text.
	 *
	 * @param appndds the appndds
	 * @return the org code text
	 */
	public String getOrgCodeText(NucDataDataStructure appndds){
		String string = "";
		if(appndds.getOrgCode().equals("")){
			string = "Organization Code: Not Entered";
		}else{
			string = "Organization Code: " + appndds.getOrgCode();
		}
		return string;
	}
	
	/**
	 * Gets the people code text.
	 *
	 * @param appndds the appndds
	 * @return the people code text
	 */
	public String getPeopleCodeText(NucDataDataStructure appndds){
		String string = "";
		if(appndds.getPeopleCode().equals("")){
			string = "Scientist Code: Not Entered";
		}else{
			string = "Scientist Code: " + appndds.getPeopleCode();
		}
		return string;
	}
	
	/**
	 * Gets the nuc data notes text.
	 *
	 * @param appndds the appndds
	 * @return the nuc data notes text
	 */
	public String getNucDataNotesText(NucDataDataStructure appndds){
		String string = "";
		if(appndds.getNucDataNotes().equals("")){
			string = "Nuclear Data Notes: Not Entered";
		}else{
			string = "Nuclear Data Notes: " + appndds.getNucDataNotes();
		}
		return string;
	}

	/**
	 * Gets the creation date text.
	 *
	 * @param appndds the appndds
	 * @return the creation date text
	 */
	public String getCreationDateText(NucDataDataStructure appndds){
		String string = "";
		if(String.valueOf(appndds.getCreationDate()).equals("")){
			string = "Creation Date: Not Entered";
		}else{
			string = "Creation Date: " + String.valueOf(appndds.getCreationDate());
		}
		return string;
	}
	
	/**
	 * Gets the ref citation text.
	 *
	 * @param appndds the appndds
	 * @return the ref citation text
	 */
	public String getRefCitationText(NucDataDataStructure appndds){
		String string = "";
		if(appndds.getRefCitation().equals("")){
			string = "Reference Citation: Not Entered";
		}else{
			string = "Reference Citation: " + String.valueOf(appndds.getRefCitation());
		}
		return string;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButton){
			TextSaver.saveText(outputTextArea.getText(), Cina.dataManFrame);
		}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(outputTextArea.getText());
    	}
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
	public void setCurrentState(){}

}