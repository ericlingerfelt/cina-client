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

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class DataMassChartTableFrame.
 */
public class DataMassChartTableFrame extends JFrame implements ActionListener{
  
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The ds. */
    private DataMassDataStructure ds;
    
    /**
     * Instantiates a new data mass chart table frame.
     *
     * @param ds the ds
     */
    public DataMassChartTableFrame(DataMassDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
        setSize(487, 400);
        setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent we){
        		setVisible(false);
        		dispose();
        	}	
        });

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 

      	tableTextArea = new JTextArea("", 100, 50);
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);
      	setTableText();
      	
      	JScrollPane sp = new JScrollPane(tableTextArea
  								, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
  								, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
    public void setTableText(){
   
   		/*int quantity = Cina.dataMassFrame.dataMassChartFrame.typeComboBox.getSelectedIndex();
   
   		switch(quantity){
				
					//EXCESS
					case 0:
					
						
						
						break;
					
					//N	
					case 1:
					
						
						break;
					
					//2N	
					case 2:
					
						
						break;
					
					//P	
					case 3:
					
						
						break;
						
					//2P	
					case 4:
					
						
						break;
					
					//ALPHA	
					case 5:
					
						
						break;
					
					//ALPHA,N
					case 6:
					
						
						break;
					
					//ALPHA,P	
					case 7:
					
		
					
						break;
					
					//P,N	
					case 8:
					
					
						break;
				
				}
   
   		if(type==2 || type==3){

			colorArray = new Color[ds.getPointVector().size()];
		
		}else if(type==0){
		
			colorArray = new Color[ds.getTheoryModelDataStructure().getZNArray().length];
		
		}else if(type==1){
		
			colorArray = new Color[ds.getExpModelDataStructure().getZNArray().length];

		}

		for(int i=0; i<colorArray.length; i++){
		
			colorArray[i] = getColor(valueArray[i]
										, Double.valueOf(minField.getText()).doubleValue()
										, Double.valueOf(maxField.getText()).doubleValue());
		
		}
   
   		switch(type){
			
		
   
   		/*if(Cina.dataMassFrame.dataMassChartFrame.typeComboBox.getSelectedItem().toString().equals("Difference of Data")){
   		
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
			
				tableTextArea.append(CinaNumberFormats.getFormattedParameter(ds.getDiffArray()[i]));
	
				tableTextArea.append("      ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getZArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getNArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getAArray()[i]) + "\n");
									
			}
		
		}else{
		
		
		setTitle("Absolute Value of Difference vs. Isotope Table of Points");
								
	   		tableTextArea.setText("");
	
			tableTextArea.append(getTitle()
					+ " of \n" 
					+ ds.getTheoryModelDataStructure().getModelName()
					+ " and "
					+ ds.getExpModelDataStructure().getModelName()
					+ "\n\n");   
					
	
			tableTextArea.append("Mass Diff (MeV)    Z     N     A\n\n");
	
			for(int i=0; i<ds.getZArray().length; i++){
			
				tableTextArea.append(CinaNumberFormats.getFormattedParameter(ds.getAbsDiffArray()[i]));
	
				tableTextArea.append("      ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getZArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getNArray()[i]));
				
				tableTextArea.append("     ");
				
				tableTextArea.append(CinaNumberFormats.getFormattedMass(ds.getAArray()[i]) + "\n");
									
			}
		
		
		}*/
													
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

