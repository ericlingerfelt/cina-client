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
 * The Class DataEvalTransDataTableFrame.
 */
public class DataEvalTransDataTableFrame extends JFrame implements ActionListener{
	
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
   	
	   /** The ds. */
	   private DataEvalDataStructure ds;
   
    /**
     * Instantiates a new data eval trans data table frame.
     *
     * @param ds the ds
     */
    public DataEvalTransDataTableFrame(DataEvalDataStructure ds){
    	
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
   
   		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
   
			setTitle("Cross Section vs. Energy Table of Points");

		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			setTitle("S Factor vs. Energy Table of Points");
		
		}

   		tableTextArea.setText("");
		
		if(Cina.dataEvalFrame.dataEvalTransData2Panel.curve1Box.isSelected()){

			if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){

				tableTextArea.append("Cross Section vs. Energy Data for: \n" 
						+ "Original Curve: " + ds.getTrans1NucDataDataStructure().getNucDataName()
						+ "\n\n");   
						
				tableTextArea.append("Energy (keV)     " 
						+ "Cross Section (b)" 
						+ "\n\n");
			
			}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
				
				tableTextArea.append("S Factor vs. Energy Data for: \n" 
						+ "Original Curve: " + ds.getTrans1NucDataDataStructure().getNucDataName()
						+ "\n\n");   
						
				tableTextArea.append("Energy (keV)     " 
						+ "S Factor (keV-b)" 
						+ "\n\n");
				
			}
			
			for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getTrans1NucDataDataStructure().getXDataArray()[i]));
				
				tableTextArea.append("        ");
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getTrans1NucDataDataStructure().getYDataArray()[i]) + "\n");
						
			}
			
			tableTextArea.append("\n");
			
		}
		
		if(Cina.dataEvalFrame.dataEvalTransData2Panel.curve2Box.isSelected()){

			if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){

				tableTextArea.append("Cross Section vs. Energy Data for: \n" 
						+ "Normalized Curve: " + ds.getTrans2NucDataDataStructure().getNucDataName()
						+ "\n\n");   
						
				tableTextArea.append("Energy (keV)     " 
						+ "Cross Section (b)" 
						+ "\n\n");
			
			}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
				
				tableTextArea.append("S Factor vs. Energy Data for: \n" 
						+ "Normalized Curve: " + ds.getTrans2NucDataDataStructure().getNucDataName()
						+ "\n\n");   
						
				tableTextArea.append("Energy (keV)     " 
						+ "S Factor (keV-b)" 
						+ "\n\n");
				
			}
			
			for(int i=0; i<ds.getTrans2NucDataDataStructure().getNumberPoints(); i++){
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getTrans2NucDataDataStructure().getXDataArray()[i]));
				
				tableTextArea.append("        ");
				
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getTrans2NucDataDataStructure().getYDataArray()[i]) + "\n");
						
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
    	
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
			string += "Cross Section vs. Energy Table of Points\n\n";
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
			string += "S Factor vs. Energy Table of Points\n\n";
		}

        string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(string, this);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(string);
    	}
   
	}
    
}

