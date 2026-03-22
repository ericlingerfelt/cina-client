//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateparam.RateParamReadInputPasteFrame;


/**
 * The Class RateParamReadInputPanel.
 */
public class RateParamReadInputPanel extends WizardPanel implements ActionListener{
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The input file panel. */
	JPanel mainPanel, mainMainPanel, inputTypePanel, inputFormatPanel, inputUnitsPanel, inputFilePanel; 
    
    /** The reaction label. */
    JLabel label1, label2, label3, label4, label5, label6, curveLabel, curveNameLabel, reactionLabel;

    /** The file dialog. */
    JFileChooser fileDialog;
    
    /** The notes text area. */
    JTextArea notesTextArea;
    
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
	
	/** The R list data. */
	String[] RListData = {"R, T", "T, R", "R, del R, T, del T", "T, del T, R, del R"};
	
	/** The sp. */
	JScrollPane sp;
	
	/** The filename array. */
	String[] filenameArray = {"iliadis/Cl31pg.rate"
								, "iliadis/Cl33pg.rate"
								, "iliadis/Cl34pg.rate"
								, "iliadis/Ar34pg.rate"
								, "iliadis/K35pg.rate"
								, "iliadis/K36pg.rate"
								, "iliadis/K37pg.rate"
								, "iliadis/K38pg.rate"
								, "iliadis/K39pg.rate"
								, "iliadis/K39pa.rate"};
	
	/** The sp1. */
	JScrollPane sp1;
	
	/** The units. */
	String[] units = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                                , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                                , "cm^6/(mole^2*s)"};
	
	/** The file. */
	File file;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param read input panel.
	 *
	 * @param ds the ds
	 */
	public RateParamReadInputPanel(RateParamDataStructure ds){
		
		this.ds = ds;
		Cina.rateParamFrame.setCurrentPanelIndex(2);

		gbc = new GridBagConstraints();
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")
				&& ds.getUploadData()){
		
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
			
			inputFormatList = new JList(RListData);
			
			inputFormatList.setEnabled(false);
			inputFormatList.setFont(Fonts.textFont);
				
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

			xUnitsComboBox = new JComboBox();
			xUnitsComboBox.addItem("T0");
			xUnitsComboBox.addItem("T3");
			xUnitsComboBox.addItem("T6");
			xUnitsComboBox.addItem("T9");
			xUnitsComboBox.setSelectedIndex(3);
			xUnitsComboBox.setFont(Fonts.textFont);
			
			yUnitsComboBox = new JComboBox();
			yUnitsComboBox.addItem("reactions/sec");
			yUnitsComboBox.addItem("cm^3/(mole*s)");
			yUnitsComboBox.addItem("cm^6/(mole^2*s)");
			yUnitsComboBox.setSelectedItem(ds.getRateUnits());
			yUnitsComboBox.setEnabled(false);
			yUnitsComboBox.setFont(Fonts.textFont);

			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			mainMainPanel = new JPanel(new GridBagLayout());
			
			inputFormatPanel = new JPanel(new GridBagLayout());
			
			inputUnitsPanel = new JPanel(new GridBagLayout());
			
			inputFilePanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Parameterizer", "Read Input File", "2", "12");
			
			//Add components to panels//////////////////////////////////////ADD//TO//PANELS/////////////////
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
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
			
		}else if(Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			//Create textfields////////////////////////////////////////////TEXTFIELDS///////////////////
			filenameField = new JTextField(10);
			filenameField.setFont(Fonts.textFont);
			filenameField.setEditable(false);
			
			//Create Buttons//////////////////////////////////////////////BUTTONS/////////////////////
			browseButton = new JButton("Browse...");
			browseButton.setFont(Fonts.buttonFont);
			browseButton.setEnabled(false);
			
			//Create lists///////////////////////////////////////////////////////LISTS//////////////////////
			inputFormatList = new JList(RListData);
			inputFormatList.setEnabled(false);
		
			sp = new JScrollPane(inputFormatList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			sp.setPreferredSize(new Dimension(120, 120));
			
			//Create labels/////////////////////////////////////////////////////LABELS/////////////
			inputFormatLabel = new JLabel("Input Format"); 
			inputFormatLabel.setFont(Fonts.titleFont);
			
			inputUnitsLabel = new JLabel("Input Units");
			inputUnitsLabel.setFont(Fonts.titleFont);
			
			inputFileLabel = new JLabel("Input File");
			inputFileLabel.setFont(Fonts.titleFont);
			
			mainLabel = new JLabel("");
			
			//Create ComboBoxs////////////////////////////////////////////////CHOICES////////////////////
			xUnitsComboBox = new JComboBox();
			xUnitsComboBox.addItem("T0");
			xUnitsComboBox.addItem("T3");
			xUnitsComboBox.addItem("T6");
			xUnitsComboBox.addItem("T9");
			xUnitsComboBox.setSelectedIndex(3);
			xUnitsComboBox.setEnabled(false);
		
			yUnitsComboBox = new JComboBox();
			yUnitsComboBox.addItem("reactions/sec");
			yUnitsComboBox.addItem("cm^3/(mole*s)");
			yUnitsComboBox.addItem("cm^6/(mole^2*s)");
			yUnitsComboBox.setSelectedItem(ds.getRateUnits());
			yUnitsComboBox.setEnabled(false);
			
			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Parameterizer", "File Summary", "2", "12");

			add(mainPanel, BorderLayout.CENTER);
		
		}else if(!Cina.cinaMainDataStructure.getUser().equals("guest")
				&& !ds.getUploadData()){
					
			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Parameterizer", "File Summary", "2", "12");
			
			JLabel notesLabel = new JLabel("Notes: ");
			notesLabel.setFont(Fonts.textFont);
			
			notesTextArea = new JTextArea("", 4, 60);
			notesTextArea.setLineWrap(true);
			notesTextArea.setWrapStyleWord(true);
			notesTextArea.setFont(Fonts.textFont);
			
			sp1 = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			sp1.setPreferredSize(new Dimension(400, 125));
			
			curveLabel = new JLabel("Curve: ");
			curveLabel.setFont(Fonts.textFont);
			
			curveNameLabel = new JLabel();
			curveNameLabel.setFont(Fonts.textFont);
			
			reactionLabel = new JLabel("Reaction: ");
			
			topLabel = new JLabel("<html>Please verify the information below. Enter notes for this reaction in the<p><i>Notes</i> text area below</html>");
			
			gbc.insets = new Insets(5, 5, 5, 5);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
			
			mainPanel.add(topLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.WEST;
			
			mainPanel.add(reactionLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			
			mainPanel.add(curveLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.WEST;
			
			mainPanel.add(curveNameLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.anchor = GridBagConstraints.WEST;
			
			mainPanel.add(notesLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.anchor = GridBagConstraints.CENTER;
			
			mainPanel.add(sp1, gbc);
			
			//Add Panels to this panel///////////////////////////////////////ADD//PANELS/////////////////////
			add(mainPanel, BorderLayout.CENTER);
		
		}

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==browseButton){
		
			fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				ds.setFileInit(file);

				filenameField.setText(file.getName());
			
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==pasteButton){
		
			if(Cina.rateParamFrame.rateParamReadInputPasteFrame==null){
				
				Cina.rateParamFrame.rateParamReadInputPasteFrame = new RateParamReadInputPasteFrame(ds);
				Cina.rateParamFrame.rateParamReadInputPasteFrame.refreshData();
			
			}else{
				
				Cina.rateParamFrame.rateParamReadInputPasteFrame.refreshData();
				Cina.rateParamFrame.rateParamReadInputPasteFrame.setVisible(true);
			
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
	 * Sets the label layout demo.
	 */
	public void setLabelLayoutDemo(){
	
		gbc.insets = new Insets(0, 0, 5, 0);
	
		label1 = new JLabel("Reaction: " + ds.getRateDataStructure().getReactionString());
		label1.setFont(Fonts.textFont);
		
		label3 = new JLabel("Filename: " + filenameField.getText());
		label3.setFont(Fonts.textFont);
		
		label2 = new JLabel("Input type: Rate vs. Temperature");
		label2.setFont(Fonts.textFont);
	
		label4 = new JLabel("Reaction Rate units: " + ds.getRateUnits());
		
		label5 = new JLabel("Temperature units: T9");
		
		label4.setFont(Fonts.textFont);
		label5.setFont(Fonts.textFont);
		
		label6 = new JLabel("Notes: ");
		label6.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea("NACRE Charged Particle-Induced Reaction Rate Library 1999 - Angulo et al., NACRE Collab. [ULB] ", 4, 60);
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		
		sp1 = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp1.setPreferredSize(new Dimension(400, 75));
		
		topLabel = new JLabel("Functional Path and File Properties");
		
		bottomLabel = new JLabel("Click Continue to upload the input file.");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 0, 0, 0);
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label3, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label4, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label5, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label6, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(sp1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.CENTER;
		
		mainPanel.add(bottomLabel, gbc);
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		if(ds.getUploadData()){
		
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
				
				filenameField.setText(filenameArray[ds.getDemoComboBox()]);

			}else{
			
				filenameField.setText(ds.getFilename());
			
			}
			
			inputFormatList.setListData(RListData);
				
			if(ds.getInputFormat().equals("2,0,1,0")){
				inputFormatList.setSelectedIndex(0);
			}else if(ds.getInputFormat().equals("1,0,2,0")){
				inputFormatList.setSelectedIndex(1);
			}else if(ds.getInputFormat().equals("3,4,1,2")){
				inputFormatList.setSelectedIndex(2);
			}else if(ds.getInputFormat().equals("1,2,3,4")){
				inputFormatList.setSelectedIndex(3);
			}
			
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
	
				setLabelLayoutDemo();
				
				setNotes();
			
			}else{
			
				if(ds.getRateDataStructure().getDecay().equals("")){
			
					reactionLabel.setText("Reaction: " + ds.getRateDataStructure().getReactionString());
				
				}else{
				
					reactionLabel.setText("Reaction: " 
											+ ds.getRateDataStructure().getReactionString()
											+ " ["
											+ ds.getRateDataStructure().getDecay()
											+ "]");
				
				}
			
			}
			
			if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				filenameField.setText(ds.getInputFilename());
			
			}
			
			if(ds.getPossibleTypes().equals("BOTH")){
			
				mainLabel.setText("<html>Choose the units for your rate from the dropdown menus.<p>The units of your rate will be converted to the standard units employed by the suite.<p>Click <i>Browse</i> to choose a rate file from your harddisc, or click <i>Cut and Paste Data</i> to<p>enter rate points via textfield. In this version, the <i>Input Format</i> option is disabled.</html>");
					
				xUnitsComboBox.removeAllItems();
				xUnitsComboBox.addItem("T0");
				xUnitsComboBox.addItem("T3");
				xUnitsComboBox.addItem("T6");
				xUnitsComboBox.addItem("T9");
				xUnitsComboBox.setSelectedItem(ds.getXUnits());
			
				yUnitsComboBox.removeAllItems();
				yUnitsComboBox.addItem("reactions/sec");
				yUnitsComboBox.addItem("cm^3/(mole*s)");
				yUnitsComboBox.addItem("cm^6/(mole^2*s)");
				yUnitsComboBox.setSelectedItem(ds.getRateUnits());
				yUnitsComboBox.setEnabled(false);
					
			}
		
		}else{
				
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
			
				setNotes();
				
			}else{
				
				notesTextArea.setText(ds.getNotes());
			
			}
			
			curveNameLabel.setText(ds.getNucDataDataStructure().getNucDataName());
									
			curveLabel.setText("Curve from "
									+ ds.getNucDataDataStructure().getNucDataSet()
									+ " Data Set: ");
			
			if(ds.getNucDataDataStructure().getDecay().equals("")){
									
				reactionLabel.setText("Reaction: " + ds.getNucDataDataStructure().getReactionString());
			
			}else{
			
				reactionLabel.setText("Reaction: " 
										+ ds.getNucDataDataStructure().getReactionString()
										+ " ["
										+ ds.getNucDataDataStructure().getDecay()
										+ "]");
			
			}
		
		}
		
	}
	
	/**
	 * Sets the notes.
	 */
	public void setNotes(){
		
		notesTextArea.setText("Iliadis et al., Astrophys. J. Suppl. 134, 151 (2001) http://www.tunl.duke.edu/~astro/RATES/webdata.dat");
		ds.getRateDataStructure().setReactionNotes(notesTextArea.getText());
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(ds.getUploadData()){

			ds.setInputType("R(T)");
			
			if(inputFormatList.getSelectedIndex()==0){
				ds.setInputFormat("2,0,1,0");
			}else if(inputFormatList.getSelectedIndex()==1){
				ds.setInputFormat("1,0,2,0");
			}else if(inputFormatList.getSelectedIndex()==2){
				ds.setInputFormat("3,4,1,2");
			}else if(inputFormatList.getSelectedIndex()==3){
				ds.setInputFormat("1,2,3,4");
			}
			
			if(Cina.cinaMainDataStructure.getUser().equals("guest")){
				
				ds.setInputFilename(filenameField.getText());
				ds.setInputFile("");
				ds.setXUnits("T9");
				ds.setYUnits(ds.getRateUnits());
			
			}else{
			
				ds.setXUnits((String)xUnitsComboBox.getSelectedItem());
				ds.setYUnits((String)yUnitsComboBox.getSelectedItem());
				ds.setInputFilename(filenameField.getText());
			
			}
		
		}else{
		
			ds.getRateDataStructure().setReactionString(ds.getNucDataDataStructure().getReactionString());
			ds.getRateDataStructure().setReactionType(ds.getNucDataDataStructure().getReactionType());
			ds.getRateDataStructure().setDecayType(ds.getNucDataDataStructure().getDecayType());
			ds.getRateDataStructure().setDecay(ds.getNucDataDataStructure().getDecay());
			
			ds.setReaction(ds.getRateDataStructure().getReactionString());
			ds.setReaction_type(ds.getRateDataStructure().getDecay());
			
			ds.getRateDataStructure().setReactionNotes(notesTextArea.getText());
			ds.setNotes(notesTextArea.getText());
		
			ds.setInputType(ds.getNucDataDataStructure().getNucDataType());
		
			ds.setInputFormat("1,0,2,0");
			
			ds.setInputFilename("");
			ds.setInputFile(getDataPoints());
			
			ds.setRateUnits(units[ds.getRateDataStructure().getReactionType()]);

			ds.setRateDataArrayOrig(null);
			ds.setTempDataArrayOrig(null);

		}

	}
	
	/**
	 * Gets the data points.
	 *
	 * @return the data points
	 */
	public String getDataPoints(){
	
		String string = "";
		
		for(int i=0; i<ds.getNucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
			
				string = NumberFormats.getFormattedValueLong(ds.getNucDataDataStructure().getXDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(ds.getNucDataDataStructure().getYDataArray()[i]);
			
			}else{
			
				string += "\n"
							+ NumberFormats.getFormattedValueLong(ds.getNucDataDataStructure().getXDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(ds.getNucDataDataStructure().getYDataArray()[i]);
			
			}
		
		}
	
		return string;
	
	}
	
}