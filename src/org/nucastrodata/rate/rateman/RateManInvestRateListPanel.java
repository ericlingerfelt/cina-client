package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.util.*;

import org.nucastrodata.Fonts;


/**
 * The Class RateManInvestRateListPanel.
 */
public class RateManInvestRateListPanel extends JPanel implements ItemListener{
	
	/** The gbc. */
	GridBagConstraints gbc;

	/** The check box vector array. */
	Vector[] checkBoxVectorArray;
	
	/** The master check box array. */
	JCheckBox[] masterCheckBoxArray;
	
	/** The rate array. */
	Vector[] rateArray;

	/** The ds. */
	private RateManDataStructure ds;

    /**
     * Instantiates a new rate man invest rate list panel.
     *
     * @param ds the ds
     */
    public RateManInvestRateListPanel(RateManDataStructure ds){
    	this.ds = ds;
    }
    
    /**
     * Initialize.
     */
    public void initialize(){
    	
    	removeAll();
	
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;

		setLayout(new GridBagLayout());
		
		rateArray = ds.getInvestRateVectorArray();

		masterCheckBoxArray = new JCheckBox[rateArray.length];
		
		checkBoxVectorArray = new Vector[rateArray.length];
		
		int counter = 0;
		
		for(int i=0; i<masterCheckBoxArray.length; i++){
		
			gbc.gridx = 0;
			gbc.gridy = counter;
			gbc.anchor = GridBagConstraints.WEST;
	
			masterCheckBoxArray[i] = new JCheckBox("Distinct Rate #" + String.valueOf(i+1), false);
			masterCheckBoxArray[i].addItemListener(this);

			gbc.insets = new Insets(5, 5, 5, 5);
			
			add(masterCheckBoxArray[i], gbc);
			
			counter++;
			
			checkBoxVectorArray[i] = new Vector();
			checkBoxVectorArray[i].trimToSize();
			
			for(int k=0; k<rateArray[i].size(); k++){
			
				String libName = ((RateDataStructure)rateArray[i].elementAt(k)).getReactionID().substring(8, ((RateDataStructure)rateArray[i].elementAt(k)).getReactionID().indexOf("\u0009"));
				
				JCheckBox tempBox = new JCheckBox(libName, false);
				tempBox.setFont(Fonts.textFont);
			
				checkBoxVectorArray[i].addElement(tempBox);
				
				gbc.gridx = 0;
				gbc.gridy = counter;
				gbc.insets = new Insets(5, 15, 5, 5);
			
				add((JCheckBox)checkBoxVectorArray[i].elementAt(k), gbc);
			
				counter++;
			
			}
		
		}
		
		
		
		validate();

    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    
    	for(int i=0; i<masterCheckBoxArray.length; i++){
    	
    		if(ie.getSource()==masterCheckBoxArray[i]){
    		
    			if(masterCheckBoxArray[i].isSelected()){
    			
    				for(int k=0; k<rateArray[i].size(); k++){
    				
    					((JCheckBox)(checkBoxVectorArray[i].elementAt(k))).setEnabled(false);
    					((JCheckBox)(checkBoxVectorArray[i].elementAt(k))).setSelected(true);
    				
    				}
    			
    			}else{
    			
    				for(int k=0; k<rateArray[i].size(); k++){
    				
    					((JCheckBox)(checkBoxVectorArray[i].elementAt(k))).setEnabled(true);
    				
    				}
    		
    			}
    		
    		}
    	
    	}
    
    }
    
}    