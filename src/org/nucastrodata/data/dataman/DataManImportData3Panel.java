package org.nucastrodata.data.dataman;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.dataman.DataManImportDataPasteFrame;


/**
 * The Class DataManImportData3Panel.
 */
public class DataManImportData3Panel extends WizardPanel implements ItemListener, ActionListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The input file panel. */
	JPanel mainPanel, mainMainPanel, inputTypePanel, inputFormatPanel, inputUnitsPanel, inputFilePanel; 
    
    /** The label6. */
    JLabel label1, label2, label3, label4, label5, label6;

    /** The file dialog. */
    JFileChooser fileDialog;
    
    /** The input format list. */
    JList inputFormatList;
    
    /** The y units combo box. */
    JComboBox yUnitsComboBox;
    
    /** The x units combo box. */
    JComboBox xUnitsComboBox;
    
    /** The filename field. */
    JTextField filenameField;
    
    /** The paste button. */
    JButton browseButton, pasteButton;
    
    /** The input file dir. */
    String inputFileDir;
    
    /** The input file. */
    String inputFile = "";
	
	/** The bottom label. */
	JLabel inputTypeLabel, inputFormatLabel, inputUnitsLabel, inputFileLabel, mainLabel, topLabel, bottomLabel;
	
	/** The reaction label. */
	JLabel reactionLabel;
	
	/** The input type button group. */
	ButtonGroup inputTypeButtonGroup;
	
	/** The S factor radio button. */
	JRadioButton crossSectionRadioButton, SFactorRadioButton;
	
	/** The SF list data. */
	String[] SFListData = {"S, E", "E, S", "S, del S, E, del E", "E, del E, S, del S"};
	
	/** The CS list data. */
	String[] CSListData = {"CS, E", "E, CS", "CS, del CS, E, del E", "E, del E, CS, del CS"};
	
	/** The sp. */
	JScrollPane sp;

	/** The sp1. */
	JScrollPane sp1;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	/**
	 * Instantiates a new data man import data3 panel.
	 *
	 * @param ds the ds
	 */
	public DataManImportData3Panel(DataManDataStructure ds){
		
		this.ds = ds;
		
		Cina.dataManFrame.setCurrentFeatureIndex(2);
		Cina.dataManFrame.setCurrentPanelIndex(3);

		gbc = new GridBagConstraints();
		
		//Create textfields////////////////////////////////////////////TEXTFIELDS///////////////////
		filenameField = new JTextField(10);
		filenameField.setFont(Fonts.textFont);
		filenameField.setEditable(false);

		//Create Buttons//////////////////////////////////////////////BUTTONS/////////////////////
		browseButton = new JButton("Browse...");
		browseButton.setFont(Fonts.buttonFont);
		browseButton.setEnabled(true);
		browseButton.addActionListener(this);
		
		pasteButton = new JButton("<html>Cut and<p>Paste Data</html>");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.setEnabled(true);
		pasteButton.addActionListener(this);
		
		//Create lists///////////////////////////////////////////////////////LISTS//////////////////////
		
		if(Cina.rateGenDataStructure.getInputType().equals("S(E)")){
		
			inputFormatList = new JList(SFListData);
		
		}else if(Cina.rateGenDataStructure.getInputType().equals("CS(E)")){
		
			inputFormatList = new JList(CSListData);
		
		}
		
		inputFormatList.setEnabled(false);
		inputFormatList.setFont(Fonts.textFont);
		inputFormatList.setSelectedIndex(1);
			
		sp = new JScrollPane(inputFormatList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(120, 120));
		
		//Create labels/////////////////////////////////////////////////////LABELS//////////////
		inputTypeLabel = new JLabel("Input Type");
		inputTypeLabel.setFont(Fonts.titleFont);
		
		inputFormatLabel = new JLabel("Input Format"); 
		inputFormatLabel.setFont(Fonts.titleFont);
		
		inputUnitsLabel = new JLabel("Input Units");
		inputUnitsLabel.setFont(Fonts.titleFont);
		
		inputFileLabel = new JLabel("Input File");
		inputFileLabel.setFont(Fonts.titleFont);
		
		mainLabel = new JLabel("");
		
		reactionLabel = new JLabel("");
		
		//Create ComboBoxs////////////////////////////////////////////////CHOICES/////////////////////
		
		if(Cina.rateGenDataStructure.getInputType().equals("S(E)")){
			
			xUnitsComboBox = new JComboBox();
			xUnitsComboBox.addItem("eV");
			xUnitsComboBox.addItem("keV");
			xUnitsComboBox.addItem("MeV");
			xUnitsComboBox.addItem("GeV");
			xUnitsComboBox.setSelectedIndex(1);
			xUnitsComboBox.setFont(Fonts.textFont);
							
			yUnitsComboBox = new JComboBox();
			yUnitsComboBox.addItem("eV-b");
			yUnitsComboBox.addItem("keV-b");
			yUnitsComboBox.addItem("MeV-b");
			yUnitsComboBox.addItem("GeV-b");
			yUnitsComboBox.setSelectedIndex(1);
			yUnitsComboBox.setFont(Fonts.textFont);
							
		}else if(Cina.rateGenDataStructure.getInputType().equals("CS(E)")){
		
			xUnitsComboBox = new JComboBox();
			xUnitsComboBox.addItem("eV");
			xUnitsComboBox.addItem("keV");
			xUnitsComboBox.addItem("MeV");
			xUnitsComboBox.addItem("GeV");
			xUnitsComboBox.setSelectedIndex(1);
			xUnitsComboBox.setFont(Fonts.textFont);
			
			yUnitsComboBox = new JComboBox();
			yUnitsComboBox.addItem("b");
			yUnitsComboBox.addItem("mb");
			yUnitsComboBox.addItem("ub");
			yUnitsComboBox.addItem("nb");
			yUnitsComboBox.addItem("pb");
			yUnitsComboBox.setSelectedIndex(0);
			yUnitsComboBox.setFont(Fonts.textFont);
			
		}
		
		//Create cb group////////////////////////////////////////////////CHECKBOX//GROUPS////////////////
		inputTypeButtonGroup = new ButtonGroup();
		
		//Create checkboxes for input type///////////////////////////////CHECKBOXES//////////////////
		crossSectionRadioButton = new JRadioButton("Cross Section (E)", false);
		crossSectionRadioButton.addItemListener(this);
		crossSectionRadioButton.setFont(Fonts.textFont);
		
		SFactorRadioButton = new JRadioButton("S factor (E)", true);
		SFactorRadioButton.addItemListener(this);
		SFactorRadioButton.setFont(Fonts.textFont);
		
		inputTypeButtonGroup.add(crossSectionRadioButton);
		inputTypeButtonGroup.add(SFactorRadioButton);
		
		//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
		mainPanel = new JPanel(new GridBagLayout());
		
		mainMainPanel = new JPanel(new GridBagLayout());
		
		inputTypePanel = new JPanel(new GridBagLayout());
		
		inputFormatPanel = new JPanel(new GridBagLayout());
		
		inputUnitsPanel = new JPanel(new GridBagLayout());
		
		inputFilePanel = new JPanel(new GridBagLayout());
		
		addWizardPanel("Nuclear Data Manager", "Import Nuclear Data", "3", "4");
		
		//Add components to panels//////////////////////////////////////ADD//TO//PANELS/////////////////
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 0, 10, 10);
		inputTypePanel.add(inputTypeLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		inputTypePanel.add(crossSectionRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		inputTypePanel.add(SFactorRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(5, 5, 5, 5);
		mainPanel.add(inputTypePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		inputFormatPanel.add(inputFormatLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		inputFormatPanel.add(sp, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(inputFormatPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		inputUnitsPanel.add(inputUnitsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		inputUnitsPanel.add(xUnitsComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		inputUnitsPanel.add(yUnitsComboBox, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(inputUnitsPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		inputFilePanel.add(inputFileLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		inputFilePanel.add(filenameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		inputFilePanel.add(browseButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		inputFilePanel.add(pasteButton, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(inputFilePanel, gbc);
		
		gbc.insets = new Insets(0, 0, 10, 0);
		
		//Add Panels to this panel///////////////////////////////////////ADD//PANELS/////////////////////
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		mainMainPanel.add(mainLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainMainPanel.add(reactionLabel, gbc);
					
		gbc.insets = new Insets(0, 0, 0, 0);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainMainPanel.add(mainPanel, gbc);
		
		add(mainMainPanel, BorderLayout.CENTER);	
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==browseButton){
		
			fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				filenameField.setText(file.getName());
				
				if(!uploadFile(file)){
				
					String string = "The file you have uploaded is empty. Please choose another.";
					
					Dialogs.createExceptionDialog(string, Cina.dataManFrame);
					
					filenameField.setText("");
				
				}
			
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==pasteButton){
		
			if(Cina.dataManFrame.dataManImportDataPasteFrame==null){
				
				Cina.dataManFrame.dataManImportDataPasteFrame = new DataManImportDataPasteFrame(ds);
				Cina.dataManFrame.dataManImportDataPasteFrame.refreshData();
			
			}else{
				
				Cina.dataManFrame.dataManImportDataPasteFrame.refreshData();
				Cina.dataManFrame.dataManImportDataPasteFrame.setVisible(true);
			
			}
		
		}
	
	}
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	public boolean uploadFile(File file){
	
		boolean goodFile = true;

		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(stringBuffer);
			
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		
		String string = new String(stringBuffer);
		
		inputFile = string;

		ds.setInputFile(inputFile);
		ds.setPastedData(false);
		
		if(inputFile.equals("")){
		
			goodFile = false;
		
		}
		
		return goodFile;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		filenameField.setText(ds.getFilename());
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
			
			crossSectionRadioButton.setSelected(true);
			SFactorRadioButton.setSelected(false);
			
			inputFormatList.setListData(CSListData);
			
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
			
			crossSectionRadioButton.setSelected(false);
			SFactorRadioButton.setSelected(true);
			
			inputFormatList.setListData(SFListData);
			
		}
		
		if(ds.getImportNucDataDataStructure().getDataFormat().equals("2,0,1,0")){
			inputFormatList.setSelectedIndex(0);
		}else if(ds.getImportNucDataDataStructure().getDataFormat().equals("1,0,2,0")){
			inputFormatList.setSelectedIndex(1);
		}else if(ds.getImportNucDataDataStructure().getDataFormat().equals("3,4,1,2")){
			inputFormatList.setSelectedIndex(2);
		}else if(ds.getImportNucDataDataStructure().getDataFormat().equals("1,2,3,4")){
			inputFormatList.setSelectedIndex(3);
		}
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			reactionLabel.setText("Reaction: " + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
			
			reactionLabel.setText("Reaction: " 
									+ ds.getImportNucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getImportNucDataDataStructure().getDecay()
									+ "]");
		
		}
		
		filenameField.setText(ds.getInputFilename());

		mainLabel.setText("<html>Select input type below, then choose the units for your data from the dropdown menus.<p>The units of your data will be converted to the standard units employed by the suite.<p>Click <i>Browse</i> to choose a datafile from your harddisc, or click <i>Cut and Paste Data</i> to<p>enter data via textfield. In this version, the <i>Input Format</i> option is disabled.</html>");
			
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			xUnitsComboBox.removeAllItems();
			xUnitsComboBox.addItem("eV");
			xUnitsComboBox.addItem("keV");
			xUnitsComboBox.addItem("MeV");
			xUnitsComboBox.addItem("GeV");
			xUnitsComboBox.setSelectedItem(ds.getXUnits());
		
			yUnitsComboBox.removeAllItems();
			yUnitsComboBox.addItem("b");
			yUnitsComboBox.addItem("mb");
			yUnitsComboBox.addItem("ub");
			yUnitsComboBox.addItem("nb");
			yUnitsComboBox.addItem("pb");
			yUnitsComboBox.setSelectedItem(ds.getYUnits());
			yUnitsComboBox.setEnabled(true);
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			xUnitsComboBox.removeAllItems();
			xUnitsComboBox.addItem("eV");
			xUnitsComboBox.addItem("keV");
			xUnitsComboBox.addItem("MeV");
			xUnitsComboBox.addItem("GeV");
			xUnitsComboBox.setSelectedItem(ds.getXUnits());
		
			yUnitsComboBox.removeAllItems();
			yUnitsComboBox.addItem("eV-b");
			yUnitsComboBox.addItem("keV-b");
			yUnitsComboBox.addItem("MeV-b");
			yUnitsComboBox.addItem("GeV-b");
			yUnitsComboBox.setSelectedItem(ds.getYUnits());
			yUnitsComboBox.setEnabled(true);
		
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		if(crossSectionRadioButton.isSelected()){
			ds.getImportNucDataDataStructure().setNucDataType("CS(E)");
		}else if(SFactorRadioButton.isSelected()){
			ds.getImportNucDataDataStructure().setNucDataType("S(E)");
		}
		
		if(inputFormatList.getSelectedIndex()==0){
			ds.getImportNucDataDataStructure().setDataFormat("2,0,1,0");
		}else if(inputFormatList.getSelectedIndex()==1){
			ds.getImportNucDataDataStructure().setDataFormat("1,0,2,0");
		}else if(inputFormatList.getSelectedIndex()==2){
			ds.getImportNucDataDataStructure().setDataFormat("3,4,1,2");
		}else if(inputFormatList.getSelectedIndex()==3){
			ds.getImportNucDataDataStructure().setDataFormat("1,2,3,4");
		}

		ds.setType(ds.getImportNucDataDataStructure().getNucDataType());
		ds.setFormat(ds.getImportNucDataDataStructure().getDataFormat());
		ds.setXUnits((String)xUnitsComboBox.getSelectedItem());
		ds.setYUnits((String)yUnitsComboBox.getSelectedItem());
		ds.setInputFilename(filenameField.getText());
			
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		if(ie.getSource()==crossSectionRadioButton 
			|| ie.getSource()==SFactorRadioButton){
			
			if(crossSectionRadioButton.isSelected()){
				
				inputFormatList.setListData(CSListData);
				
				xUnitsComboBox.removeAllItems();
				xUnitsComboBox.addItem("eV");
				xUnitsComboBox.addItem("keV");
				xUnitsComboBox.addItem("MeV");
				xUnitsComboBox.addItem("GeV");
				xUnitsComboBox.setSelectedIndex(1);
				
				yUnitsComboBox.removeAllItems();
				yUnitsComboBox.addItem("b");
				yUnitsComboBox.addItem("mb");
				yUnitsComboBox.addItem("ub");
				yUnitsComboBox.addItem("nb");
				yUnitsComboBox.addItem("pb");
				yUnitsComboBox.setSelectedItem("b");
				yUnitsComboBox.setEnabled(true);
				ds.setYUnits("b");
				
			}else if(SFactorRadioButton.isSelected()){
			
				inputFormatList.setListData(SFListData);
				
				xUnitsComboBox.removeAllItems();
				xUnitsComboBox.addItem("eV");
				xUnitsComboBox.addItem("keV");
				xUnitsComboBox.addItem("MeV");
				xUnitsComboBox.addItem("GeV");
				xUnitsComboBox.setSelectedIndex(1);
				
				yUnitsComboBox.removeAllItems();
				yUnitsComboBox.addItem("eV-b");
				yUnitsComboBox.addItem("keV-b");
				yUnitsComboBox.addItem("MeV-b");
				yUnitsComboBox.addItem("GeV-b");
				yUnitsComboBox.setSelectedItem("keV-b");
				yUnitsComboBox.setEnabled(true);
				ds.setYUnits("keV-b");
				
			}
		
			inputFormatList.setSelectedIndex(1);
			
		}

	}
	
}