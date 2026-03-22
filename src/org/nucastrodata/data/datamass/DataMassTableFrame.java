package org.nucastrodata.data.datamass;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class DataMassTableFrame.
 */
public class DataMassTableFrame extends JFrame implements ActionListener{
  
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The ds. */
    private DataMassDataStructure ds;
    
    /**
     * Instantiates a new data mass table frame.
     *
     * @param ds the ds
     */
    public DataMassTableFrame(DataMassDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
    	
        setSize(487, 400);
        setVisible(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
      
      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		 
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
      	tableTextArea = new JTextArea("", 100, 50);
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);
		
      	setTableText();
      	
      	tableTextArea.setCaretPosition(0);
      	
      	JScrollPane sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
      	
      	gbc.gridx = 0;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 5);
      	buttonPanel.add(saveButton, gbc);
      	gbc.gridx = 1;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		buttonPanel.add(copyButton, gbc);
      	gbc.insets = new Insets(0, 0, 0, 0);
	
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        
        validate();

    }
    
    /**
     * Sets the table text.
     */
    protected void setTableText(){
   
   		if(Cina.dataMassFrame.dataMassPlotFrame.type.equals("Diff")){
   
   			setTitle("Mass Difference vs. Isotope Table of Points");
							
	   		tableTextArea.setText("");
	
			tableTextArea.append(getTitle()
					+ " of \n" 
					+ ds.getTheoryModelDataStructure().getModelName()
					+ " and "
					+ ds.getExpModelDataStructure().getModelName()
					+ "\n\n");   
					
	
			tableTextArea.append("Mass Diff (MeV)    Z     N     A\n\n");
	
			for(int i=0; i<ds.getZArray().length; i++){
			
				tableTextArea.append(NumberFormats.getFormattedParameter(ds.getDiffArray()[i]));
	
				tableTextArea.append("      ");
				
				tableTextArea.append(NumberFormats.getFormattedMass(ds.getZArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(NumberFormats.getFormattedMass(ds.getNArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(NumberFormats.getFormattedMass(ds.getAArray()[i]) + "\n");
									
			}
   
   		}else if(Cina.dataMassFrame.dataMassPlotFrame.type.equals("RMS")){
   
			setTitle("RMS Value vs. Isotope Table of Points");
	
	   		tableTextArea.setText("");
	
			tableTextArea.append(getTitle()
					+ " of \n" 
					+ ds.getTheoryModelDataStructure().getModelName()
					+ " and "
					+ ds.getExpModelDataStructure().getModelName()
					+ "\n\n");   
					
			if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
	   
				tableTextArea.append("RMS Value (MeV)    Z\n\n");
	
				for(int i=0; i<ds.getZArrayRMS().length; i++){
				
					tableTextArea.append(NumberFormats.getFormattedParameter(ds.getRMSZArray()[i]));
	
					tableTextArea.append("      ");
				
					tableTextArea.append(NumberFormats.getFormattedMass(ds.getZArrayRMS()[i]) + "\n");
							
				}
							
			}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
	   
				tableTextArea.append("RMS Value (MeV)    N\n\n");
				
				for(int i=0; i<ds.getNArrayRMS().length; i++){
				
					tableTextArea.append(NumberFormats.getFormattedParameter(ds.getRMSNArray()[i]));
	
					tableTextArea.append("      ");
					
					tableTextArea.append(NumberFormats.getFormattedMass(ds.getNArrayRMS()[i]) + "\n");
							
				}
							
			}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
	   
				tableTextArea.append("RMS Value (MeV)    A\n\n");
				
				for(int i=0; i<ds.getAArrayRMS().length; i++){
				
					tableTextArea.append(NumberFormats.getFormattedParameter(ds.getRMSAArray()[i]));
	
					tableTextArea.append("      ");
					
					tableTextArea.append(NumberFormats.getFormattedMass(ds.getAArrayRMS()[i]) + "\n");
							
				}
								
			}
		
		}
									
    	tableTextArea.setCaretPosition(0);
		
		
		
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){

    	if(ae.getSource()==saveButton){
			TextSaver.saveText(tableTextArea.getText(), this);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(tableTextArea.getText());
    	}
   
    }
    
}

