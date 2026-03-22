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

import java.text.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateParamResultsTableFrame.
 */
public class RateParamResultsTableFrame extends JFrame implements ActionListener{
	
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The ds. */
    private RateParamDataStructure ds;
    
    /**
     * Instantiates a new rate param results table frame.
     *
     * @param ds the ds
     */
    public RateParamResultsTableFrame(RateParamDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
    	
        setSize(349, 400);
        setVisible(true); 
        addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent we){
		        setVisible(false);
		        dispose();
		    }
        });
        
        GridBagConstraints gbc = new GridBagConstraints();
        
      	JPanel buttonPanel = new JPanel(new GridBagLayout());

		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

      	tableTextArea = new JTextArea("");
      	tableTextArea.setEditable(false);
		tableTextArea.setFont(Fonts.textFontFixedWidth);

      	JScrollPane sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));

        setTableText();
        
        tableTextArea.setCaretPosition(0);
        
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
    public void setTableText(){
   
   		tableTextArea.setText("");
   
   		String reactionString = ds.getRateDataStructure().getReactionString();
   		
   		if(!ds.getRateDataStructure().getDecay().equals("")){
   			reactionString += " [" + ds.getRateDataStructure().getDecay() + "]";	
   		}
   		
        String units = ds.getRateUnits();
        
        setTitle("Fit vs. Rate Table: " + reactionString);
   
   		double[] x = new double[ds.getTempParamDataArray().length];
        double[] y = new double[ds.getTempParamDataArray().length];
        
        String[] tempStringArray = new String[ds.getTempParamDataArray().length];
        	
        int[] tempSpacesArray = new int[ds.getTempParamDataArray().length];
        
        tableTextArea.append("Rate vs. Temperature Data over Parameterized Range for: " 
        							+ reactionString + "\n\n");   
       	tableTextArea.append("Temperature(T9)  " + "Reaction Rate(" + ds.getRateUnits() 
       								+ ") " + " \n\n" );
        
        for(int i=0; i<ds.getTempParamDataArray().length; i++){
        	
            x[i] = ds.getTempParamDataArray()[i];
            y[i] = ds.getRateParamDataArray()[i];
            
         	tempStringArray[i] = NumberFormats.getFormattedTemp2(x[i]);
	            
            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
            
            for(int j=0; j<tempSpacesArray[i]; j++){
			
				tableTextArea.append(" ");
			
			}
			
			tableTextArea.append(tempStringArray[i]);
			tableTextArea.append("          ");
			tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
 
		}
   
        x = new double[ds.getTempDataArrayOrig().length];
        y = new double[ds.getTempDataArrayOrig().length];
        
        tempStringArray = new String[ds.getTempDataArrayOrig().length];
        	
        tempSpacesArray = new int[ds.getTempDataArrayOrig().length];
        
        tableTextArea.append("\n\nReaction Rate vs. Temperature Data for: " + reactionString + "\n\n");   
       	tableTextArea.append("Temperature(T9)  " + "Reaction Rate(" + ds.getRateUnits() + ") " + " \n\n" );
        
        for(int i=0; i<ds.getTempDataArrayOrig().length; i++){
        	
            x[i] = ds.getTempDataArrayOrig()[i];
            y[i] = ds.getRateDataArrayOrig()[i];
            
         	tempStringArray[i] = NumberFormats.getFormattedTemp2(x[i]);
	            
            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
            
            for(int j=0; j<tempSpacesArray[i]; j++){
			
				tableTextArea.append(" ");
			
			}
			
			tableTextArea.append(tempStringArray[i]);
			tableTextArea.append("          ");
			tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
 
		}
		
		if(ds.getExtraPoints()){
		
			x = new double[ds.getTempDataArrayExtra().length];
	        y = new double[ds.getTempDataArrayExtra().length];
	        
	        tempStringArray = new String[ds.getTempDataArrayExtra().length];
	        	
	        tempSpacesArray = new int[ds.getTempDataArrayExtra().length];
	        
	        tableTextArea.append("\n\nAdded Rate Points Data for: " + reactionString + "\n\n");   
	       	tableTextArea.append("Temperature(T9)  " + "Reaction Rate(" + ds.getRateUnits() + ") " + " \n\n" );
	        
	        for(int i=0; i<ds.getTempDataArrayExtra().length; i++){
	        	
	            x[i] = ds.getTempDataArrayExtra()[i];
	            y[i] = ds.getRateDataArrayExtra()[i];
	            
	         	tempStringArray[i] = NumberFormats.getFormattedTemp2(x[i]);
		            
	            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
	            
	            for(int j=0; j<tempSpacesArray[i]; j++){
				
					tableTextArea.append(" ");
				
				}
				
				tableTextArea.append(tempStringArray[i]);
				tableTextArea.append("          ");
				tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
	 
			}
		
		}
		
		if(ds.getLowTempDataArray().length>0){
		
			x = new double[ds.getLowTempDataArray().length];
	        y = new double[ds.getLowTempDataArray().length];
	        
	        tempStringArray = new String[ds.getLowTempDataArray().length];
	        	
	        tempSpacesArray = new int[ds.getLowTempDataArray().length];
	        
	        tableTextArea.append("\n\nLow Temperature Extrapolation for: " 
	        									+ reactionString + "\n\n");   
	       	tableTextArea.append("Temperature(T9)  " + "Reaction Rate(" 
	       										+ ds.getRateUnits() + ") " + " \n\n" );
	        
	        for(int i=0; i<ds.getLowTempDataArray().length; i++){
	        	
	            x[i] = ds.getLowTempDataArray()[i];
	            y[i] = ds.getLowRateDataArray()[i];
	            
	         	tempStringArray[i] = NumberFormats.getFormattedTemp2(x[i]);
		            
	            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
	            
	            for(int j=0; j<tempSpacesArray[i]; j++){
				
					tableTextArea.append(" ");
				
				}
				
				tableTextArea.append(tempStringArray[i]);
				tableTextArea.append("          ");
				tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
	 
			}
		
		}
		
		if(ds.getHighTempDataArray().length>0){
		
			x = new double[ds.getHighTempDataArray().length];
	        y = new double[ds.getHighTempDataArray().length];
	        
	        tempStringArray = new String[ds.getHighTempDataArray().length];
	        	
	        tempSpacesArray = new int[ds.getHighTempDataArray().length];
	        
	        tableTextArea.append("\n\nHigh Temperature Extrapolation for: " 
	        										+ reactionString + "\n\n");   
	       	tableTextArea.append("Temperature(T9)  " + "Reaction Rate(" 
	       											+ ds.getRateUnits() + ") " + " \n\n" );
	        
	        for(int i=0; i<ds.getHighTempDataArray().length; i++){
	        	
	            x[i] = ds.getHighTempDataArray()[i];
	            y[i] = ds.getHighRateDataArray()[i];
	            
	         	tempStringArray[i] = NumberFormats.getFormattedTemp2(x[i]);
		            
	            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
	            
	            for(int j=0; j<tempSpacesArray[i]; j++){
				
					tableTextArea.append(" ");
					
				}
			
				tableTextArea.append(tempStringArray[i]);
				tableTextArea.append("          ");
				tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
	 
			}
		
		}
		
		
		tableTextArea.setCaretPosition(0);
		
		
    }
    
	/**
	 * Gets the temp spaces.
	 *
	 * @param string the string
	 * @return the temp spaces
	 */
	public int getTempSpaces(String string){

		int numSpaces = 0;	
		
		if(string.indexOf(".")==1){
			numSpaces = 3;
		}else{
			numSpaces = 3 - string.indexOf(".") + 1;
		}

		return numSpaces;
	
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

