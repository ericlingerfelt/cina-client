package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.text.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateManInvestRateListFrame.
 */
public class RateManInvestRateListFrame extends JFrame implements WindowListener, ActionListener{
    
    /** The table text area. */
    JTextArea tableTextArea;
    
    /** The button panel. */
    JPanel buttonPanel;
    
    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The c. */
    Container c;    
    
    /** The sp. */
    JScrollPane sp;
    
    /** The ds. */
    private RateManDataStructure ds;
    
    /**
     * Instantiates a new rate man invest rate list frame.
     *
     * @param ds the ds
     */
    public RateManInvestRateListFrame(RateManDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true); 
        
        c.setLayout(new BorderLayout());
        
        gbc = new GridBagConstraints();
        
        addWindowListener(this);

      	buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
      
      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
      
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
      	tableTextArea = new JTextArea("");
      	tableTextArea.setEditable(false);
		tableTextArea.setFont(Fonts.textFontFixedWidth);

      	sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        
        String reactionString = "";
        
        if(ds.getInvestRateDataStructure().getDecay().equals("")){
        	
        	reactionString = ds.getInvestRateDataStructure().getReactionString();
        
        }else{
        
        	reactionString = ds.getInvestRateDataStructure().getReactionString()
        						+ " ["
        						+ ds.getInvestRateDataStructure().getDecay()
        						+ "]";
        
        }
        
        setTitle("List of Distinct Rates for " + reactionString);
		
		tableTextArea.append(reactionString + "\n\n");
		
		Vector[] rateArray = ds.getInvestRateVectorArray();
		
		for(int i=0; i<ds.getInvestRateVectorArray().length; i++){
		
			tableTextArea.append("Distinct Rate #" + (i+1) + "\n");
		
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){
			
				String libName = ((RateDataStructure)rateArray[i].elementAt(j)).getReactionID().substring(8, ((RateDataStructure)rateArray[i].elementAt(j)).getReactionID().indexOf("\u0009"));
			
				tableTextArea.append(libName + "\n");
			
			}
			
			tableTextArea.append("\n");
		
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

