package org.nucastrodata.data.dataviewer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class DataViewerInfoFrame.
 */
public class DataViewerInfoFrame extends JFrame implements WindowListener, ActionListener, ItemListener{
	
	/** The copy button. */
	JButton saveButton, copyButton;
	
	/** The ref citation box. */
	JCheckBox pointsBox
				, orgCodeBox
				, peopleCodeBox
				, nucDataNotesBox
				, creationDateBox
				, refCitationBox;
	
	/** The title label. */
	JLabel titleLabel;
	
	/** The text area string. */
	String textAreaString;
	
	/** The output text area. */
	JTextArea outputTextArea;

	/** The gbc. */
	GridBagConstraints gbc;

	/** The cb panel. */
	JPanel mainPanel, buttonPanel, cbPanel;
    
    
    /** The c. */
    Container c;
        
    /** The sp. */
    JScrollPane sp;
    
  
    
    /** The type. */
    String type = "";
    
    /** The ds. */
    private DataViewerDataStructure ds;
    
	/**
	 * Instantiates a new data viewer info frame.
	 *
	 * @param ds the ds
	 */
	public DataViewerInfoFrame(DataViewerDataStructure ds){
	
		this.ds = ds;
	
		c = getContentPane();
	
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
	
		setSize(700, 500);
		setVisible(true);
		setTitle("Nuclear Data Information");
		
		addWindowListener(this);
		
		titleLabel = new JLabel("<html>Select Nuclear Data<p>Information for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);
		
		//Checkboxes////////////////////////////////////////////////////////////////////////////
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
		
		//Create text area//////////////////////////////////////////////TEXTAREAS///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		mainPanel = new JPanel(new GridBagLayout());
		
		buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		buttonPanel.add(saveButton, gbc);
		
		gbc.gridx = 1;
		
		buttonPanel.add(copyButton, gbc);
		
		cbPanel = new JPanel(new GridBagLayout());
		
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

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		c.add(cbPanel, BorderLayout.WEST);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 20, 0, 0);
		c.add(sp, BorderLayout.CENTER);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		c.add(buttonPanel, BorderLayout.SOUTH);
		
		
		
		validate();
	}
	
	/**
	 * Refresh data.
	 */
	public void refreshData(){
		
		//Set text area empty
		outputTextArea.setText("");
		
		//Initialize text area string
		textAreaString = "";
	
		//Create a counter
		int numberNucData = 0;
		
		type = "";
		
		if(Cina.dataViewerFrame.dataViewerPlotFrame.SFRadioButton.isSelected()){
	        	
			type = "S(E)";
				
		}else if(Cina.dataViewerFrame.dataViewerPlotFrame.CSRadioButton.isSelected()){
		
			type = "CS(E)";
		
		}
		
		//Loop over rate data strcuture array
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    		
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals(type)){
    		
	    				if(Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray[numberNucData].isSelected()){
	
							//Create text area string for each piece of info selected
							textAreaString = textAreaString + getReactionStringText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n\n";
											
							if(orgCodeBox.isSelected()){textAreaString = textAreaString + getOrgCodeText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}
						
							if(peopleCodeBox.isSelected()){textAreaString = textAreaString + getPeopleCodeText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}
				
							if(nucDataNotesBox.isSelected()){textAreaString = textAreaString + getNucDataNotesText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}
				
							if(refCitationBox.isSelected()){textAreaString = textAreaString + getRefCitationText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}
				
							if(creationDateBox.isSelected()){textAreaString = textAreaString + getCreationDateText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}
				
							if(pointsBox.isSelected()){textAreaString = textAreaString + "\n" + getPointsText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j]) + "\n";}			
				
							textAreaString = textAreaString + "\n" 
												+ "__________________________________________________________________________________________________"
												+ "\n";
												
						}
    		
    				numberNucData++;
    				
    				}
    			}	
    		}	
    	}
	
		//If nothing is selected
		if(!pointsBox.isSelected()
			&& !orgCodeBox.isSelected()
			&& !peopleCodeBox.isSelected()
			&& !nucDataNotesBox.isSelected()
			&& !refCitationBox.isSelected()
			&& !creationDateBox.isSelected()){
		
			//Set text area string empty
			textAreaString = "";
		
		}
	
		//Set text of text area
		outputTextArea.setText(textAreaString);
		
		//Set caret position to the top
		outputTextArea.setCaretPosition(0);
		
		//Set color formatting
		
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		refreshData();
		
	}

	//Method to construct the reaction string property
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

	//Method to construct the parameters property
	/**
	 * Gets the points text.
	 *
	 * @param appndds the appndds
	 * @return the points text
	 */
	public String getPointsText(NucDataDataStructure appndds){
	
		String string = "";

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
	
	//Method to construct the biblio code property
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
	
	//Method to construct the biblio code property
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
	
	//Method to construct the notes property
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

	//Method to construct the creation date property
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
	
	//Method to construct the reference citation property
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
		
			TextSaver.saveText(outputTextArea.getText(), this);
		
		}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(outputTextArea.getText());
    	
    	}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {
        setVisible(false);
        dispose();
    } 
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}

}