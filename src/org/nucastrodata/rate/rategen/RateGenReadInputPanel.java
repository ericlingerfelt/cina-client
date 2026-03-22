//This was written by Eric Lingerfelt
//09/19/2003
package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rategen.RateGenReadInputPasteFrame;


/**
 * The Class RateGenReadInputPanel.
 */
public class RateGenReadInputPanel extends WizardPanel implements ItemListener, ActionListener{
	
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
    public JTextField filenameField;
    
    /** The paste button. */
    JButton browseButton, pasteButton;
    
    /** The input file dir. */
    String inputFileDir;
    
    /** The input file. */
    String inputFile = "";

	/** The bottom label. */
	JLabel inputTypeLabel, inputFormatLabel, inputUnitsLabel, inputFileLabel, mainLabel, topLabel, bottomLabel;

	/** The input type button group. */
	ButtonGroup inputTypeButtonGroup;
	
	/** The nuc structure radio button. */
	JRadioButton crossSectionRadioButton, SFactorRadioButton, nucStructureRadioButton;
	
	/** The SF list data. */
	String[] SFListData = {"S, E", "E, S", "S, del S, E, del E", "E, del E, S, del S"};
	
	/** The CS list data. */
	String[] CSListData = {"CS, E", "E, CS", "CS, del CS, E, del E", "E, del E, CS, del CS"};
	
	/** The sp. */
	JScrollPane sp;
	
	/** The filename array sf. */
	String[] filenameArraySF = {"ddg.sf"
								, "ddp.sf"
								, "ddn.sf"
								, "dpg.sf"
								, "tag.sf"
								, "he3he3.sf"
								, "li6pa.sf"
								, "li7pa.sf"
								, "be9pa.sf"
								, "be9pg.sf"
								, "b10pg.sf"
								, "b11pa.sf"};
								
	/** The filename array cs. */
	String[] filenameArrayCS = {"ddg.cs"
								, "ddp.cs"
								, "ddn.cs"
								, "dpg.cs"
								, "tag.cs"
								, "he3he3.cs"
								, "li6pa.cs"
								, "li7pa.cs"
								, "be9pa.cs"
								, "be9pg.cs"
								, "b10pg.cs"
								, "b11pa.cs"};
								
	
	/** The sp1. */
	JScrollPane sp1;
	
	/** The units. */
	String[] units = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                            , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                            , "cm^6/(mole^2*s)"};
	
	/** The ds. */
	private RateGenDataStructure ds;
	
	/**
	 * Instantiates a new rate gen read input panel.
	 *
	 * @param ds the ds
	 */
	public RateGenReadInputPanel(RateGenDataStructure ds){
		
		this.ds = ds;
		Cina.rateGenFrame.setCurrentPanelIndex(2);

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
			
			if(ds.getInputType().equals("S(E)")){
			
				inputFormatList = new JList(SFListData);
			
			}else if(ds.getInputType().equals("CS(E)")){
			
				inputFormatList = new JList(CSListData);
			
			}
			
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
			
			if(ds.getInputType().equals("S(E)")){
				
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
								
			}else if(ds.getInputType().equals("CS(E)")){
			
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
			
			nucStructureRadioButton = new JRadioButton("<html>Nuclear <p>Structure Info</html>", false);
			nucStructureRadioButton.setEnabled(false);
			nucStructureRadioButton.setFont(Fonts.textFont);
			
			inputTypeButtonGroup.add(crossSectionRadioButton);
			inputTypeButtonGroup.add(SFactorRadioButton);
			inputTypeButtonGroup.add(nucStructureRadioButton);
			
			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			mainMainPanel = new JPanel(new GridBagLayout());
			
			inputTypePanel = new JPanel(new GridBagLayout());
			
			inputFormatPanel = new JPanel(new GridBagLayout());
			
			inputUnitsPanel = new JPanel(new GridBagLayout());
			
			inputFilePanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Generator", "Read Input File", "2", "7");
			
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
			gbc.gridy = 3;
			gbc.insets = new Insets(0, 0, 0, 0);
			inputTypePanel.add(nucStructureRadioButton, gbc);
			
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
			if(ds.getInputType().equals("S(E)")){
			
				inputFormatList = new JList(SFListData);
			
			}else if(ds.getInputType().equals("CS(E)")){
			
				inputFormatList = new JList(CSListData);
			
			}
			
			inputFormatList.setEnabled(false);
		
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
			
			//Create ComboBoxs////////////////////////////////////////////////CHOICES/////////////////////
			if(ds.getInputType().equals("S(E)")){
				
				xUnitsComboBox = new JComboBox();
				xUnitsComboBox.addItem("eV");
				xUnitsComboBox.addItem("keV");
				xUnitsComboBox.addItem("MeV");
				xUnitsComboBox.addItem("GeV");
				xUnitsComboBox.setSelectedIndex(1);
				xUnitsComboBox.setEnabled(false);
				
				yUnitsComboBox = new JComboBox();
				yUnitsComboBox.addItem("eV-b");
				yUnitsComboBox.addItem("keV-b");
				yUnitsComboBox.addItem("MeV-b");
				yUnitsComboBox.addItem("GeV-b");
				yUnitsComboBox.setSelectedIndex(1);
				yUnitsComboBox.setEnabled(false);
				
			}else if(ds.getInputType().equals("CS(E)")){
			
				xUnitsComboBox = new JComboBox();
				xUnitsComboBox.addItem("eV");
				xUnitsComboBox.addItem("keV");
				xUnitsComboBox.addItem("MeV");
				xUnitsComboBox.addItem("GeV");
				xUnitsComboBox.setSelectedIndex(1);
				xUnitsComboBox.setEnabled(false);
			
				yUnitsComboBox = new JComboBox();
				yUnitsComboBox.addItem("b");
				yUnitsComboBox.addItem("mb");
				yUnitsComboBox.addItem("ub");
				yUnitsComboBox.addItem("nb");
				yUnitsComboBox.addItem("pb");
				yUnitsComboBox.setSelectedIndex(0);
				yUnitsComboBox.setEnabled(false);
				
			}
			
			//Create cb group////////////////////////////////////////////////CHECKBOX//GROUPS////////////////
			inputTypeButtonGroup = new ButtonGroup();
			
			//Create checkboxes for input type///////////////////////////////CHECKBOXES//////////////////
			crossSectionRadioButton = new JRadioButton("Cross Section (E)", false);
			crossSectionRadioButton.addItemListener(this);
			
			SFactorRadioButton = new JRadioButton("S factor (E)", true);
			SFactorRadioButton.addItemListener(this);
			
			nucStructureRadioButton = new JRadioButton("<html>Nuclear <p>Structure Info</html>", false);
			nucStructureRadioButton.setEnabled(false);
			
			inputTypeButtonGroup.add(crossSectionRadioButton);
			inputTypeButtonGroup.add(SFactorRadioButton);
			inputTypeButtonGroup.add(nucStructureRadioButton);
			
			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Generator", "File Summary", "2", "7");

			add(mainPanel, BorderLayout.CENTER);
		
		}else if(!Cina.cinaMainDataStructure.getUser().equals("guest")
				&& !ds.getUploadData()){
					
			//Create Panels////////////////////////////////////////////////////PANELS/////////////////////
			mainPanel = new JPanel(new GridBagLayout());
			
			addWizardPanel("Rate Generator", "File Summary", "2", "7");
			
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
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				filenameField.setText(file.getName());
				
				if(!uploadFile(file)){
				
					String string = "The file you have uploaded is empty. Please choose another.";
					
					Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
					
					filenameField.setText("");
				
				}
			
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==pasteButton){
		
			if(Cina.rateGenFrame.rateGenReadInputPasteFrame==null){
				
				Cina.rateGenFrame.rateGenReadInputPasteFrame = new RateGenReadInputPasteFrame(ds);
				Cina.rateGenFrame.rateGenReadInputPasteFrame.refreshData();
			
			}else{
				
				Cina.rateGenFrame.rateGenReadInputPasteFrame.refreshData();
				Cina.rateGenFrame.rateGenReadInputPasteFrame.setVisible(true);
			
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
		
		if(ds.getInputType().equals("CS(E)")){
		
			label2 = new JLabel("Input type: Cross Section vs. Energy");
			label2.setFont(Fonts.textFont);
		
			label4 = new JLabel("Cross Section units: b");
			
			label5 = new JLabel("Energy units: keV");
		
		}else if(ds.getInputType().equals("S(E)")){
			
			label2 = new JLabel("Input type: S-factor vs. Energy");
			label2.setFont(Fonts.textFont);
		
			label4 = new JLabel("S-factor units: keV-b");
			
			label5 = new JLabel("Energy units: keV");
		
		
		}
		
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
			
				if(ds.getInputType().equals("S(E)")){
				
					filenameField.setText(filenameArraySF[ds.getDemoComboBox()]);
				
				}else if(ds.getInputType().equals("CS(E)")){
				
					filenameField.setText(filenameArrayCS[ds.getDemoComboBox()]);
				
				}
			
			}else{
			
				filenameField.setText(ds.getFilename());
			
			}
			
			crossSectionRadioButton.removeItemListener(this);
			SFactorRadioButton.removeItemListener(this);
		
			if(ds.getInputType().equals("CS(E)")){
				
				crossSectionRadioButton.setSelected(true);
				SFactorRadioButton.setSelected(false);
				
				inputFormatList.setListData(CSListData);
				
			}else if(ds.getInputType().equals("S(E)")){
				
				crossSectionRadioButton.setSelected(false);
				SFactorRadioButton.setSelected(true);
				
				inputFormatList.setListData(SFListData);
				
			}
			
			crossSectionRadioButton.addItemListener(this);
			SFactorRadioButton.addItemListener(this);
			
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

				mainLabel.setText("<html>Select input type below, then choose the units for your data from the dropdown menus.<p>The units of your data will be converted to the standard units employed by the suite.<p>Click <i>Browse</i> to choose a datafile from your harddisc, or click <i>Cut and Paste Data</i> to<p>enter data via textfield. In this version, the <i>Input Format</i> option is disabled.</html>");
				
				if(ds.getInputType().equals("CS(E)")){
				
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
					yUnitsComboBox.setSelectedItem(ds.getYUnitsCS());
					yUnitsComboBox.setEnabled(true);
				
				}else if(ds.getInputType().equals("S(E)")){
				
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
					yUnitsComboBox.setSelectedItem(ds.getYUnitsS());
					yUnitsComboBox.setEnabled(true);
				
				}
				
			}
		
		}else{
		
			notesTextArea.setText(ds.getNotes());
			
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
	
		notesTextArea.setText("NACRE Charged Particle-Induced Reaction Rate Library 1999 - Angulo et al., NACRE Collab. [ULB] ");
		ds.getRateDataStructure().setReactionNotes(notesTextArea.getText());
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(ds.getUploadData()){
		
			if(crossSectionRadioButton.isSelected()){
				ds.setInputType("CS(E)");
			}else if(SFactorRadioButton.isSelected()){
				ds.setInputType("S(E)");
			}
			
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
				
				if(ds.getInputType().equals("CS(E)")){
				
					ds.setXUnits("keV");
					ds.setYUnitsCS("b");
				
				}else if(ds.getInputType().equals("S(E)")){
					
					ds.setXUnits("keV");
					ds.setYUnitsS("keV-b");
				
				}
	
			}else{
			
				ds.setXUnits((String)xUnitsComboBox.getSelectedItem());
				if(crossSectionRadioButton.isSelected()){
					ds.setYUnitsCS((String)yUnitsComboBox.getSelectedItem());
				}else if(SFactorRadioButton.isSelected()){
					ds.setYUnitsS((String)yUnitsComboBox.getSelectedItem());
				}
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
			
			if(ds.getInputType().equals("CS(E)")){
					
				ds.setXUnits("keV");
				ds.setYUnitsCS("b");
			
			}else if(ds.getInputType().equals("S(E)")){
				
				ds.setXUnits("keV");
				ds.setYUnitsS("keV-b");
			
			}
			
			ds.setInputFilename("");
			ds.setInputFile(getDataPoints());
			
			ds.setRateUnits(units[ds.getRateDataStructure().getReactionType()]);

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
				xUnitsComboBox.setSelectedItem(ds.getXUnits());
				
				yUnitsComboBox.removeAllItems();
				yUnitsComboBox.addItem("b");
				yUnitsComboBox.addItem("mb");
				yUnitsComboBox.addItem("ub");
				yUnitsComboBox.addItem("nb");
				yUnitsComboBox.addItem("pb");
				yUnitsComboBox.setSelectedItem(ds.getYUnitsCS());
				
				if(Cina.cinaMainDataStructure.getUser().equals("guest")){
				
					filenameField.setText(filenameArrayCS[ds.getDemoComboBox()]);
					
					ds.setFile(filenameField.getText());
				
				}
				
			}else if(SFactorRadioButton.isSelected()){
			
				inputFormatList.setListData(SFListData);
				
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
				yUnitsComboBox.setSelectedItem(ds.getYUnitsS());
				
				if(Cina.cinaMainDataStructure.getUser().equals("guest")){
				
					filenameField.setText(filenameArraySF[ds.getDemoComboBox()]);
					
					ds.setFile(filenameField.getText());
				
				}
				
			}
		
			inputFormatList.setSelectedIndex(1);
			
		}

	}
	
}