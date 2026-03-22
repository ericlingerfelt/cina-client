package org.nucastrodata.rate.rategen;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateGenResultsInfoFrame.
 */
public class RateGenResultsInfoFrame extends JFrame implements WindowListener, ActionListener, ItemListener{
	
	/** The copy button. */
	JButton saveButton, copyButton;
	
	/** The rate calc box. */
	JCheckBox inputCheckBox, inputPreprocBox, rateCalcBox;
	
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

	/** The ds. */
	private RateGenDataStructure ds;

	/**
	 * Instantiates a new rate gen results info frame.
	 *
	 * @param ds the ds
	 */
	public RateGenResultsInfoFrame(RateGenDataStructure ds){
	
		this.ds = ds;
	
		c = getContentPane();
	
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
	
		setSize(700, 257);
		setVisible(true);
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Session Information: " + ds.getRateDataStructure().getReactionString());
	
		}else{
		
			setTitle("Session Information: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		addWindowListener(this);
		
		titleLabel = new JLabel("<html>Select Sessions <p>for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);
		
		//Create text area//////////////////////////////////////////////TEXTAREAS///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFont);
		outputTextArea.setEditable(false);
		
		sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create checkboxes////////////////////////////////////////////CHECKBOXES///////////////////
		inputCheckBox = new JCheckBox("Input Check", false);
		inputCheckBox.addItemListener(this);
		inputCheckBox.setFont(Fonts.textFont);
		
		inputPreprocBox = new JCheckBox("Input Preprocessing", false);
		inputPreprocBox.addItemListener(this);
		inputPreprocBox.setFont(Fonts.textFont);
		
		rateCalcBox = new JCheckBox("Rate Calculation", false);
		rateCalcBox.addItemListener(this);
		rateCalcBox.setFont(Fonts.textFont);
		
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
		
		cbPanel.add(inputCheckBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		cbPanel.add(inputPreprocBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		cbPanel.add(rateCalcBox, gbc);
		
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
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Session Information: " + ds.getRateDataStructure().getReactionString());
	
		}else{
		
			setTitle("Session Information: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
	
		outputTextArea.setText("");
		
		textAreaString = "";
		
		if(ds.getRateDataStructure().getDecay().equals("")){
		
			textAreaString = "Reaction Name: " 
								+ ds.getRateDataStructure().getReactionString() 
								+ "\n" + "\n";
		
		}else{
			
			textAreaString = "Reaction Name: " 
								+ ds.getRateDataStructure().getReactionString() 
								+ " ["
								+ ds.getRateDataStructure().getDecay()
								+ "]"
								+ "\n" + "\n";
		
		}
		
		if(inputCheckBox.isSelected()){
		
			textAreaString = textAreaString + addInputCheckInfo();

		}
		
		if(inputPreprocBox.isSelected()){
			
			textAreaString = textAreaString + addInputPreprocInfo();
			
		}
		
		if(rateCalcBox.isSelected()){
		
			textAreaString = textAreaString + addRateCalcInfo();
		
		}
	
		outputTextArea.setText(textAreaString);
		
		outputTextArea.setCaretPosition(0);
		
		
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		outputTextArea.setText("");
		
		textAreaString = "";
		
		if(inputCheckBox.isSelected()
				|| inputPreprocBox.isSelected()
				|| rateCalcBox.isSelected()){
					
			if(ds.getRateDataStructure().getDecay().equals("")){
		
				textAreaString = "Reaction Name: " 
									+ ds.getRateDataStructure().getReactionString() 
									+ "\n" + "\n";
			
			}else{
				
				textAreaString = "Reaction Name: " 
									+ ds.getRateDataStructure().getReactionString() 
									+ " ["
									+ ds.getRateDataStructure().getDecay()
									+ "]"
									+ "\n" + "\n";
			
			}
		
		}
		
		if(inputCheckBox.isSelected()){
		
			textAreaString = textAreaString + addInputCheckInfo();

		}
		
		if(inputPreprocBox.isSelected()){
			
			textAreaString = textAreaString + addInputPreprocInfo();
			
		}
		
		if(rateCalcBox.isSelected()){
		
			textAreaString = textAreaString + addRateCalcInfo();
		
		}
	
		outputTextArea.setText(textAreaString);
		
		outputTextArea.setCaretPosition(0);
	}

	/**
	 * Adds the input check info.
	 *
	 * @return the string
	 */
	public String addInputCheckInfo(){
		
		String string = "Input Check Information" + "\n\n";
		
		string = string 
					+ "Notes: " 
					+ ds.getRateDataStructure().getReactionNotes()
					+ "\n";
		
		if(ds.getInputType().equals("CS(E)")){
			
			string = string + "Input Type: Cross Section vs. Energy \n"; 
			
			if(ds.getInputFormat().equals("2,0,1,0")){
			
				string = string + "Input Format: Cross Section, Energy \n"; 
				
			}else if(ds.getInputFormat().equals("1,0,2,0")){
		
				string = string + "Input Format: Energy, Cross Section \n";
		
			}else if(ds.getInputFormat().equals("3,4,1,2")){
		
				string = string + "Input Format: Cross Section, Cross Section Uncertainty, Energy, Energy Uncertainty \n";
		
			}else if(ds.getInputFormat().equals("1,0,2,0")){
		
				string = string + "Input Format: Energy, Energy Uncertainty, Cross Section, Cross Section Uncertainty \n";
		
			}
				
		}else if(ds.getInputType().equals("S(E)")){
		
			string = string + "Input Type: S Factor vs. Energy \n"; 
			
			if(ds.getInputFormat().equals("2,0,1,0")){
			
				string = string + "Input Format: S Factor, Energy \n"; 
				
			}else if(ds.getInputFormat().equals("1,0,2,0")){
		
				string = string + "Input Format: Energy, S Factor \n";
		
			}else if(ds.getInputFormat().equals("3,4,1,2")){
		
				string = string + "Input Format: S Factor, S Factor Uncertainty, Energy, Energy Uncertainty \n";
		
			}else if(ds.getInputFormat().equals("1,0,2,0")){
		
				string = string + "Input Format: Energy, Energy Uncertainty, S Factor, S Factor Uncertainty \n";
		
			}
		
		}
		
		if(!ds.getPastedData()){
		
			string = string 
						+ "Data Source: " 
						+ ds.getInputFilename()
						+ "\n";
					
		}else{
		
			string = string + "Data Source: Data Pasted \n";

		}
					
		string = string 
					+ "Number of Points: " 
					+ String.valueOf(ds.getNumberPoints())
					+ "\n";	
					
		if(ds.getInputType().equals("CS(E)")){
			
			string = string 
					+ "Energy min: " 
					+ String.valueOf(ds.getEmin())
					+ "\n";	
					
			string = string 
					+ "Energy max: " 
					+ String.valueOf(ds.getEmax())
					+ "\n";
			
			string = string 
					+ "Cross Section min: " 
					+ String.valueOf(ds.getCrossSectionmin())
					+ "\n";	
					
			string = string 
					+ "Cross Section max: " 
					+ String.valueOf(ds.getCrossSectionmax())
					+ "\n";
			
		}else if(ds.getInputType().equals("S(E)")){
			
			string = string 
					+ "Energy min: " 
					+ String.valueOf(ds.getEmin())
					+ "\n";	
					
			string = string 
					+ "Energy max: " 
					+ String.valueOf(ds.getEmax())
					+ "\n";
			
			string = string 
					+ "S Factor min: " 
					+ String.valueOf(ds.getSFactormin())
					+ "\n";	
					
			string = string 
					+ "S Factor max: " 
					+ String.valueOf(ds.getSFactormax())
					+ "\n";
			
		}
				
		string = string + "\n";
		
		return string;
		
	}
	
	/**
	 * Adds the input preproc info.
	 *
	 * @return the string
	 */
	public String addInputPreprocInfo(){
		
		String string = "Input Preprocessing Information" + "\n\n";
		
		if(ds.getCheckInputArray()[0].equals("Y")){
		
			if(ds.getInputWarningsResponseArray()[0].equals("PASSED")){
			
				string = string + "Positive: Passed \n";
								
			}else{
			
				string = string + "Positive: Failed \n";
				
			}
		}else{
		
			string = string + "Positive: Skipped \n";
		
		}
				
		if(ds.getCheckInputArray()[1].equals("Y")){
			
			if(ds.getInputWarningsResponseArray()[1].equals("PASSED")){
			
				string = string + "Single-valued: Passed \n";
				
			}else{
			
				string = string + "Single-valued: Failed \n";
				
			}
		}else{
		
			string = string + "Single-valued: Skipped \n";
			
		}
		
		if(ds.getCheckInputArray()[2].equals("Y")){
			
			if(ds.getInputWarningsResponseArray()[2].equals("PASSED")){
			
				string = string + "Reasonable energy range: Passed \n";
				
			}else{
			
				string = string + "Reasonable energy range: Failed \n";
				
			}
		}else{
		
			string = string + "Reasonable energy range: Skipped \n";
		
		}
		
		if(ds.getCheckInputArray()[4].equals("Y")){
			
			if(ds.getInputWarningsResponseArray()[4].equals("PASSED")){
			
				string = string + "Large uncertainties: Passed \n";
				
			}else{
			
				string = string + "Large uncertainties: Failed \n";
				
			}
		}else{
		
			string = string + "Large uncertainties: Skipped \n";
		
		}
		
		string = string 
				+ "Temperature that rate can be calculated (T9): (" 
				+ String.valueOf(ds.getAllowedTempmin())
				+ ", "
				+ String.valueOf(ds.getAllowedTempmax())
				+ ")"
				+ "\n";
	
		string = string + "\n";
	
		return string;
	}
	
	/**
	 * Adds the rate calc info.
	 *
	 * @return the string
	 */
	public String addRateCalcInfo(){
		
		String string = "Rate Calculation Information" + "\n\n";
		
		string = string 
					+ "Temperature range for rate (T9): (" 
					+ String.valueOf(ds.getTempmin())
					+ ", "
					+ String.valueOf(ds.getTempmax())
					+ ")"
					+ "\n";
					
		string = string
					+ "Output Level: "
					+ String.valueOf(ds.getOutputLevel())
					+ "\n";
					
		string = string 
					+"Number of Points: "
					+ String.valueOf(ds.getNumberPointsRT())
					+ "\n";
					
		string = string 
					+ "Temperature min: " 
					+ String.valueOf(ds.getTempminRT())
					+ "\n";	
					
		string = string 
					+ "Temperature max: " 
					+ String.valueOf(ds.getTempmaxRT())
					+ "\n";
					
		string = string 
					+ "Reaction Rate min: " 
					+ String.valueOf(ds.getRatemin())
					+ "\n";	
					
		string = string 
					+ "Reaction Rate max: " 
					+ String.valueOf(ds.getRatemax())
					+ "\n";
					
		string = string 
					+ "dR/dT min: " 
					+ String.valueOf(ds.getRateDerivmin())
					+ "\n";	
					
		string = string 
					+ "dR/dT max: " 
					+ String.valueOf(ds.getRateDerivmax())
					+ "\n";
					
		if(ds.getInputType().equals("CS(E)")){
			
			string = string 
					+ "Number of interpolated Cross Section points: " 
					+ String.valueOf(ds.getNumberInterpolatedPointsRT())
					+ "\n";
			
		}else if(ds.getInputType().equals("S(E)")){
			
			string = string 
					+ "Number of interpolated S Factor points: " 
					+ String.valueOf(ds.getNumberInterpolatedPointsRT())
					+ "\n";
			
		}
		
		string = string 
					+"Technique for Integration: "
					+ ds.getTechniqueRT()
					+ "\n";
		
		string = string + "\n";
		
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