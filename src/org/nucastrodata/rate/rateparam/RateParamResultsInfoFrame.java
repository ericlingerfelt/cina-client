package org.nucastrodata.rate.rateparam;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateParamResultsInfoFrame.
 */
public class RateParamResultsInfoFrame extends JFrame implements ActionListener, ItemListener{
	
	/** The copy button. */
	private JButton saveButton, copyButton;
	
	/** The rate param box. */
	private JCheckBox inputCheckBox, inputPreprocBox, rateParamBox;
	
	/** The title label. */
	private JLabel titleLabel;
	
	/** The text area string. */
	private String textAreaString;
	
	/** The output text area. */
	private JTextArea outputTextArea;
    
    /** The ds. */
    private RateParamDataStructure ds;
    
	/**
	 * Instantiates a new rate param results info frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsInfoFrame(RateParamDataStructure ds){
		
		this.ds = ds;
	
		Container c = getContentPane();
	
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
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
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		titleLabel = new JLabel("<html>Select Sessions <p>for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);
		
		//Create text area//////////////////////////////////////////////TEXTAREAS///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFont);
		outputTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create checkboxes////////////////////////////////////////////CHECKBOXES///////////////////
		inputCheckBox = new JCheckBox("Input Check", false);
		inputCheckBox.addItemListener(this);
		inputCheckBox.setFont(Fonts.textFont);
		
		inputPreprocBox = new JCheckBox("Input Preprocessing", false);
		inputPreprocBox.addItemListener(this);
		inputPreprocBox.setFont(Fonts.textFont);
		
		rateParamBox = new JCheckBox("Rate Parameterization", false);
		rateParamBox.addItemListener(this);
		rateParamBox.setFont(Fonts.textFont);
		
		//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
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
		cbPanel.add(inputCheckBox, gbc);	
		gbc.gridx = 0;
		gbc.gridy = 2;
		cbPanel.add(inputPreprocBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		cbPanel.add(rateParamBox, gbc);
		
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
	protected void refreshData(){
	
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
		
		if(inputCheckBox.isSelected()){textAreaString += addInputCheckInfo();}
		if(inputPreprocBox.isSelected()){textAreaString += addInputPreprocInfo();}
		if(rateParamBox.isSelected()){textAreaString += addRateParamInfo();}
	
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
				|| rateParamBox.isSelected()){
					
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
		
		if(inputCheckBox.isSelected()){textAreaString += addInputCheckInfo();}
		if(inputPreprocBox.isSelected()){textAreaString += addInputPreprocInfo();}
		if(rateParamBox.isSelected()){textAreaString += addRateParamInfo();}
	
		outputTextArea.setText(textAreaString);
		outputTextArea.setCaretPosition(0);
	}

	/**
	 * Adds the input check info.
	 *
	 * @return the string
	 */
	private String addInputCheckInfo(){
		
		String string = "Input Check Information" + "\n\n";
		
		string = string 
					+ "Notes: " 
					+ ds.getRateDataStructure().getReactionNotes()
					+ "\n";
		
		string = string + "Input Type: Rate vs. Temperature \n"; 
		
		if(ds.getInputFormat().equals("2,0,1,0")){
		
			string = string + "Input Format: Rate, Temperature \n"; 
			
		}else if(ds.getInputFormat().equals("1,0,2,0")){
	
			string = string + "Input Format: Temperature, Rate \n";
	
		}else if(ds.getInputFormat().equals("3,4,1,2")){
	
			string = string + "Input Format: Rate, Rate Uncertainty, Temperature, Temperature Uncertainty \n";
	
		}else if(ds.getInputFormat().equals("1,0,2,0")){
	
			string = string + "Input Format: Temperature, Temperature Uncertainty, Rate, Rate Uncertainty \n";
	
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
			
		string = string 
				+ "Temperature min: " 
				+ String.valueOf(ds.getTempminRT())
				+ "\n";	
				
		string = string 
				+ "Temperature max: " 
				+ String.valueOf(ds.getTempmaxRT())
				+ "\n";
		
		string = string 
				+ "Rate min: " 
				+ String.valueOf(ds.getRatemin())
				+ "\n";	
				
		string = string 
				+ "Rate max: " 
				+ String.valueOf(ds.getRatemax())
				+ "\n";
				
		string = string + "\n";
		
		return string;
		
	}
	
	/**
	 * Adds the input preproc info.
	 *
	 * @return the string
	 */
	private String addInputPreprocInfo(){
		
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
	 * Adds the rate param info.
	 *
	 * @return the string
	 */
	private String addRateParamInfo(){
		
		String string = "Rate Parameterization Information" + "\n\n";
		
		string = string 
					+ "Allowed temperature range (T9): (" 
					+ String.valueOf(ds.getAllowedTempmin())
					+ ", "
					+ String.valueOf(ds.getAllowedTempmax())
					+ ")"
					+ "\n";
					
		string = string 
					+ "Temperature min: " 
					+ String.valueOf(ds.getTempminParam())
					+ "\n";	
					
		string = string 
					+ "Temperature max: " 
					+ String.valueOf(ds.getTempmaxParam())
					+ "\n";
					
		string = string 
					+"Number of Parameters: "
					+ String.valueOf(ds.getRateDataStructure().getNumberParameters())
					+ "\n";
		
		string = string 
					+"Maximum Percent Difference: "
					+ String.valueOf(ds.getRateDataStructure().getMaxPercentDiff())
					+ "\n";
		
		string = string 
					+"Chi-squared Value: "
					+ String.valueOf(ds.getRateDataStructure().getChisquared())
					+ "\n\n";
					
		string = string +"Parameters \n";
		
		for(int i=0; i<ds.getRateDataStructure().getNumberParameters(); i++){
		
			string = string 
						+ "a" 
						+ (i+1) 
						+ ": " 
						+ ds.getParamStringArray()[i]
						+ "\n";
		
		}
		
		string = string + "\n";
		
		if(ds.getExtraPoints()){
	
			string += "Added Rate Points \n";
	
			for(int i=0; i<ds.getTempDataArrayExtra().length; i++){
			
				string += ds.getTempDataArrayExtra()[i]
							+ "    " + ds.getRateDataArrayExtra()[i] + "\n";
			
			}
		
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
}