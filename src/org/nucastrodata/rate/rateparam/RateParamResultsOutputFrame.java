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

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateParamResultsOutputFrame.
 */
public class RateParamResultsOutputFrame extends JFrame implements ActionListener, ItemListener{

	/** The output text area. */
	private JTextArea outputTextArea;
	
	/** The copy button. */
	private JButton saveButton, copyButton;
	
	/** The title label. */
	private JLabel titleLabel;
	
	/** The fullnetsu box. */
	private JCheckBox asciiBox, reaclibBox, fortranBox, htmlBox
						, netsuBox, fullnetsuBox;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param results output frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsOutputFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		Container c = getContentPane();
	
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		setSize(714, 306);
		setVisible(true);
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Formatted Output: " + ds.getRateDataStructure().getReactionString());
		
		}else{
		
			setTitle("Formatted Output: " 
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
		
		//Create text area//////////////////////////////////////////////TEXTAREA///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		//Create checkboxes/////////////////////////////////////////CHECKBOXES////////////////////
		asciiBox = new JCheckBox("ASCII List", false);
		asciiBox.addItemListener(this);
		asciiBox.setFont(Fonts.textFont);
		
		reaclibBox = new JCheckBox("REACLIB", false);
		reaclibBox.addItemListener(this);
		reaclibBox.setFont(Fonts.textFont);
		
		fortranBox = new JCheckBox("Fortran", false);
		fortranBox.addItemListener(this);
		fortranBox.setFont(Fonts.textFont);
		
		htmlBox = new JCheckBox("HTML code", false);
		htmlBox.addItemListener(this);
		htmlBox.setFont(Fonts.textFont);
		
		netsuBox = new JCheckBox("netsu", false);
		netsuBox.addItemListener(this);
		netsuBox.setFont(Fonts.textFont);
		
		fullnetsuBox = new JCheckBox("full netsu", false);
		fullnetsuBox.addItemListener(this);	
		fullnetsuBox.setFont(Fonts.textFont);
		
		//Create labels//////////////////////////////////////////////LABELS/////////////////////
		titleLabel = new JLabel("Available formats: ");
		titleLabel.setFont(Fonts.titleFont);
		
		//Create Panels//////////////////////////////////////////////PANELS/////////////////////
		JPanel mainPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JPanel cbPanel = new JPanel(new GridBagLayout());
		
		//Add components to panels//////////////////////////////////ADD//TO//PANELS////////////
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 10, 0);
		cbPanel.add(titleLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 5, 10, 0);
		cbPanel.add(asciiBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		cbPanel.add(reaclibBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		cbPanel.add(fortranBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		cbPanel.add(htmlBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		cbPanel.add(netsuBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		cbPanel.add(fullnetsuBox, gbc);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.gridx = 0;
		buttonPanel.add(saveButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(copyButton, gbc);
		
		c.add(cbPanel, BorderLayout.WEST);
		c.add(sp, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
		
		
		validate();
	}
	
	/**
	 * Sets the output text area string.
	 *
	 * @param string the new output text area string
	 */
	public void setOutputTextAreaString(String string){outputTextArea.setText(string);}

	/**
	 * Refresh data.
	 */
	protected void refreshData(){
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Formatted Output: " + ds.getRateDataStructure().getReactionString());
		
		}else{
		
			setTitle("Formatted Output: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		outputTextArea.setText("");
		
		
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		outputTextArea.setText("");	
		genFormatOutputBodyString();
		Cina.cinaCGIComm.doCGICall("GENERATE PARAMETER FORMAT", this);
		outputTextArea.setCaretPosition(0);
		
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		String string = "";
	
		if(ds.getRateDataStructure().getDecay().equals("")){
            	
           	string += "Output Format: " 
            			+ ds.getRateDataStructure().getReactionString() 
            			+ "\n\n";
        			
     	}else{
     	
     		string += "Output Format: " 
            			+ ds.getRateDataStructure().getReactionString() 
            			+ " ["
            			+ ds.getRateDataStructure().getDecay()
            			+ "]"
            			+ "\n\n";
     	
     	}
       
        string += outputTextArea.getText();
	
		if(ae.getSource()==saveButton){
		
			TextSaver.saveText(string, this);
		
		}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(string);
    	
    	}
	
	}
	
	/**
	 * Gen format output body string.
	 */
	private void genFormatOutputBodyString(){
	
		String string = "";
		
		if(asciiBox.isSelected()){string += "ASCII,";}
		if(reaclibBox.isSelected()){string += "REACLIB,";}
		if(fortranBox.isSelected()){string += "FORTRAN,";}
		if(htmlBox.isSelected()){string += "HTML,";}
		if(netsuBox.isSelected()){string += "NETSU,";}
		if(fullnetsuBox.isSelected()){string += "FULL_NETSU,";}
		
		ds.setBody(string);
	
	}

}