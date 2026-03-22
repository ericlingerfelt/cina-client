package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateManInvestRateTableFrame.
 */
public class RateManInvestRateTableFrame extends JFrame implements WindowListener, ActionListener{

    /** The suffix. */
    String[] suffix = new String[4];
    
    /** The units index. */
    int[] unitsIndex;
    
    /** The table text area. */
    JTextArea tableTextArea;

    /** The top panel. */
    JPanel buttonPanel, topPanel;
    
    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The reaction string. */
    String reactionString;
    
    /** The y. */
    double[] x, y;
    
    /** The units. */
    String units;
    
	/** The index. */
	int index;

    /** The units array. */
    String[] unitsArray = {"", "reactions/sec", "reactions/sec", "reactions/sec", "cm^3/(mole*s)"
                            , "cm^3/(mole*s)", "cm^3/(mole*s)", "cm^3/(mole*s)"
                            , "cm^6/(mole^2 * s)"};
    
    /** The gbc. */
    GridBagConstraints gbc;
  
    /** The c. */
    Container c;    
    
    /** The sp. */
    JScrollPane sp;

	/** The ds. */
	private RateManDataStructure ds;

    /**
     * Instantiates a new rate man invest rate table frame.
     *
     * @param ds the ds
     */
    public RateManInvestRateTableFrame(RateManDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        setTitle("Rate vs. Temperature Table of Points");
        
        gbc = new GridBagConstraints();
        
        addWindowListener(this);

		topPanel = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;

      	buttonPanel = new JPanel(new GridBagLayout());
      	
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
      	
      	sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
    public void setTableText(){
   
   		tableTextArea.setText("");

		int numberRates = 0;

		String titleString = "";
		
		if(((RateDataStructure)ds.getInvestRateVectorArray()[0].elementAt(0)).getDecay().equals("")){
		
			titleString = ((RateDataStructure)ds.getInvestRateVectorArray()[0].elementAt(0)).getReactionString();
		
		}else{
		
			titleString = ((RateDataStructure)ds.getInvestRateVectorArray()[0].elementAt(0)).getReactionString()
							+ " ["
							+ ((RateDataStructure)ds.getInvestRateVectorArray()[0].elementAt(0)).getDecay()
							+ "]";
		
		}

		tableTextArea.append("Reaction Rate vs. Temperature Data for: " 
							+ titleString 
							+ "\n\n"); 

		for(int i=0; i<Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotListPanel.checkBoxArray.length; i++){
			
			if(Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotListPanel.checkBoxArray[i].isSelected()){

				tableTextArea.append(Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotListPanel.checkBoxArray[i].getText() + "\n\n");  
							
				tableTextArea.append("Temperature(T9)  " 
							+ "Reaction Rate(" 
							+ unitsArray[((RateDataStructure)ds.getInvestRateVectorArray()[0].elementAt(0)).getReactionType()]
							+ ")" 
							+ "\n\n");

				String[] tempStringArray = new String[ds.getTempGrid().length];
    	
    			int[] tempSpacesArray = new int[ds.getTempGrid().length];

				for(int j=0; j<ds.getTempGrid().length; j++){
					
					tempStringArray[j] = NumberFormats.getFormattedTemp2(ds.getTempGrid()[j]);
				
		            tempSpacesArray[j] = getTempSpaces(tempStringArray[j]);
					            
		            for(int k=0; k<tempSpacesArray[j]; k++){
					
						tableTextArea.append(" ");
					
					}
							
					tableTextArea.append(tempStringArray[j]);

					tableTextArea.append("          ");
					
					tableTextArea.append(NumberFormats.getFormattedValueLong(Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotPanel.y[i][j]) + "\n");
							
				}
				
				tableTextArea.append("\n");
			
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
    	
			TextSaver.saveText("Rate vs. Temperature Table of Points\n\n" + tableTextArea.getText(), this);	
    	
    	}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText("Rate vs. Temperature Table of Points\n\n" + tableTextArea.getText());
    	
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

