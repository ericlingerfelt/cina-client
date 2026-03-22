package org.nucastrodata.data.dataman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class DataManImportDataTableFrame.
 */
public class DataManImportDataTableFrame extends JFrame{
	
    /** The table text area. */
    private JTextArea tableTextArea;
   	
	   /** The ds. */
	   private DataManDataStructure ds; 
   
    /**
     * Instantiates a new data man import data table frame.
     *
     * @param ds the ds
     */
    public DataManImportDataTableFrame(DataManDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
    	
        setSize(349, 400);
        setVisible(true); 
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });

        GridBagConstraints gbc = new GridBagConstraints();

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		JButton saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){TextSaver.saveText(tableTextArea.getText()
			, Cina.dataManFrame.dataManImportDataTableFrame);}
		});
      
      	JButton copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){TextCopier.copyText(tableTextArea.getText());}
		});
      
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
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
    protected void setTableText(){
   
   		tableTextArea.setText("");
   
   		String reactionString = ds.getImportNucDataDataStructure().getReactionString();
   		
   		if(!ds.getImportNucDataDataStructure().getDecay().equals("")){
   		
   			reactionString += " [" + ds.getImportNucDataDataStructure().getDecay() + "]";
   		
   		}
   		
   		String dataName = ds.getImportNucDataDataStructure().getNucDataName();
        
        if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
        
        	setTitle("Energy vs. Cross Section Table: " 
        			+ dataName
        			+ " ("
        			+ reactionString
        			+ ")");
        
        	tableTextArea.append("Energy vs. Cross Section Data for: " + dataName
        			+ " ("
        			+ reactionString
        			+ ")" + "\n\n");  
        			
       		tableTextArea.append("Energy(keV)       " + "Cross Section(b) " + " \n\n" );
        
    	}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
    	
    		setTitle("Energy vs. S Factor Table: " 
        			+ dataName
        			+ " ("
        			+ reactionString
        			+ ")");
        			
        	tableTextArea.append("Energy vs. S Factor Data for: " + dataName
        			+ " ("
        			+ reactionString
        			+ ")" + "\n\n");  
        			
       		tableTextArea.append("Energy(keV)       " + "S Factor(keV-b) " + " \n\n" );
       		
    	}
   
        double[] x = new double[ds.getImportNucDataDataStructure().getNumberPoints()];
        double[] y = new double[ds.getImportNucDataDataStructure().getNumberPoints()];
        
        String[] energyStringArray = new String[ds.getImportNucDataDataStructure().getNumberPoints()];
        	
        int[] energySpacesArray = new int[ds.getImportNucDataDataStructure().getNumberPoints()];

        for(int i=0; i<ds.getImportNucDataDataStructure().getNumberPoints(); i++){
        	
            x[i] = ds.getImportNucDataDataStructure().getXDataArray()[i];
            y[i] = ds.getImportNucDataDataStructure().getYDataArray()[i];
			
			tableTextArea.append(NumberFormats.getFormattedValueLong(x[i]));
			
			tableTextArea.append("       ");
			
			tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]) + "\n");
 
		}
		
		tableTextArea.setCaretPosition(0);
		
		
		
    }

}

