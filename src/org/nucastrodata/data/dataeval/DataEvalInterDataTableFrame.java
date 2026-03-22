package org.nucastrodata.data.dataeval;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataEvalDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class DataEvalInterDataTableFrame.
 */
public class DataEvalInterDataTableFrame extends JFrame implements ActionListener{
    
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
	
	/** The ds. */
	private DataEvalDataStructure ds;

    /**
     * Instantiates a new data eval inter data table frame.
     *
     * @param ds the ds
     */
    public DataEvalInterDataTableFrame(DataEvalDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
    	
        setSize(349, 400);
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
		
		//c.add(topPanel, BorderLayout.NORTH);
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        
        
        validate();

    }
    
    /**
     * Sets the table text.
     */
    protected void setTableText(){
   
		setTitle("Cross Section vs. Energy Table of Points");

   		tableTextArea.setText("");
		
		if(Cina.dataEvalFrame.dataEvalInterData3Panel.curve1Box.isSelected()){

			tableTextArea.append("Cross Section vs. Energy Data for: \n" 
					+ "Curve 1: " + ds.getInter1NucDataDataStructure().getNucDataName()
					+ "\n\n");   
					
			tableTextArea.append("Energy (keV)     " 
					+ "Cross Section (b)" 
					+ "\n\n");

			for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter1NucDataDataStructure().getXDataArray()[i]));
				
				tableTextArea.append("        ");
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter1NucDataDataStructure().getYDataArray()[i]) + "\n");
						
			}
			
			tableTextArea.append("\n");
			
		}
		
		
		
		if(Cina.dataEvalFrame.dataEvalInterData3Panel.curve2Box.isSelected()){

			tableTextArea.append("Cross Section vs. Energy Data for: \n" 
					+ "Curve 2: " + ds.getInter2NucDataDataStructure().getNucDataName()
					+ "\n\n");   
					
			tableTextArea.append("Energy (keV)     " 
					+ "Cross Section (b)" 
					+ "\n\n");

			for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter2NucDataDataStructure().getXDataArray()[i]));
				
				tableTextArea.append("        ");
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter2NucDataDataStructure().getYDataArray()[i]) + "\n");
						
			}
			
			tableTextArea.append("\n");
			
		}
		
		if(Cina.dataEvalFrame.dataEvalInterData3Panel.totalCurveBox.isSelected()){

			tableTextArea.append("Cross Section vs. Energy Data for: \n" 
					+ ds.getInter3NucDataDataStructure().getNucDataName()
					+ "\n\n");   
					
			tableTextArea.append("Energy (keV)     " 
					+ "Cross Section (b)" 
					+ "\n\n");

			for(int i=0; i<ds.getInter3NucDataDataStructure().getNumberPoints(); i++){
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter3NucDataDataStructure().getXDataArray()[i]));
				
				tableTextArea.append("        ");
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getInter3NucDataDataStructure().getYDataArray()[i]) + "\n");
						
			}
			
			tableTextArea.append("\n");
			
		}
											
    	tableTextArea.setCaretPosition(0);
		
		
		
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
    	String string = "";
    
    	string += "Cross Section vs. Energy Table of Points\n\n";
    	string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(string, this);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(string);
    	}
    }
    
}

