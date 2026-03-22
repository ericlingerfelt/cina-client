package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;
import org.nucastrodata.file.filter.*;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateViewerTableFrame.
 */
public class RateViewerTableFrame extends JFrame implements WindowListener
															, ActionListener{
																
    /** The suffix. */
    String[] suffix = new String[4];
    
    /** The units index. */
    int[] unitsIndex;
    
    /** The table text area. */
    JTextArea tableTextArea;
    
    /** The button panel. */
    JPanel buttonPanel;
    
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
    
    /** The reaction choice. */
    JComboBox reactionChoice = new JComboBox();
   
   	/** The ds. */
	   private RateViewerDataStructure ds;
   
    /**
     * Instantiates a new rate viewer table frame.
     *
     * @param ds the ds
     */
    public RateViewerTableFrame(RateViewerDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
        setSize(349, 400);
        setVisible(true); 
        setTitle("Rate vs. Temperature Table of Points");
        gbc = new GridBagConstraints();
        addWindowListener(this);

      	buttonPanel = new JPanel(new GridBagLayout());

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
		
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()){
    					
						String[] tempStringArray = new String[ds.getTempGrid().length];
        				int[] tempSpacesArray = new int[ds.getTempGrid().length];
        				
						tableTextArea.append("Reaction Rate vs. Temperature Data for: " 
									+ Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].getText() 
									+ "\n\n");  
									 			
       					tableTextArea.append("Temperature(T9)  "
       								+ "Reaction Rate(" 
       								+ ds.getUnitsArray()[numberRates]
       								+ ")" 
       								+ "\n\n");

    					for(int k=0; k<ds.getTempGrid().length; k++){
							tempStringArray[k] = NumberFormats.getFormattedTemp2(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getTempDataArrayTotal()[k]);
				            tempSpacesArray[k] = getTempSpaces(tempStringArray[k]);
				            
				            for(int l=0; l<tempSpacesArray[k]; l++){tableTextArea.append(" ");}
							
							tableTextArea.append(tempStringArray[k]);
							tableTextArea.append("          ");
							tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayTotal()[k]) + "\n");		
						}
						
						tableTextArea.append("\n");
    				}
    		
    				numberRates++;
    		
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
	    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
							if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()){
								
								String[] tempStringArray = new String[ds.getTempGrid().length];
        						int[] tempSpacesArray = new int[ds.getTempGrid().length];
								
								tableTextArea.append("Reaction Rate vs. Temperature Data for: " 
											+ Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].getText() 
											+ "\n\n");   	
		       					
		       					tableTextArea.append("Temperature(T9)  " 
		       								+ "Reaction Rate(" 
		       								+ ds.getUnitsArray()[numberRates] 
		       								+ ")" 
		       								+ "\n\n");
		       											
    							for(int l=0; l<ds.getTempGrid().length; l++){
									tempStringArray[l] = NumberFormats.getFormattedTemp2(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getTempDataArrayComp()[k][l]);
						            tempSpacesArray[l] = getTempSpaces(tempStringArray[l]);
						            
						            for(int m=0; m<tempSpacesArray[l]; m++){tableTextArea.append(" ");}
									
									tableTextArea.append(tempStringArray[l]);
									tableTextArea.append("          ");
									tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getRateDataArrayComp()[k][l]) + "\n");		
								}
								
								tableTextArea.append("\n");
		    				}
		    		
		    				numberRates++;

						}	
					}
    			}	
    		}	
    	}

		if(ds.getRateAdded()){
		
			String[] tempStringArray = new String[ds.getAddTempArray().length];
			int[] tempSpacesArray = new int[ds.getAddTempArray().length];
			
			tableTextArea.append("Reaction Rate vs. Temperature Data for: " 
						+ ds.getAddRateName()
						+ "\n\n");  
						 			
			tableTextArea.append("Temperature(T9)  "
						+ "Reaction Rate"
						+ "\n\n");

			for(int i=0; i<ds.getAddTempArray().length; i++){
				tempStringArray[i] = NumberFormats.getFormattedTemp2(ds.getAddTempArray()[i]);
	            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
	            
	            for(int j=0; j<tempSpacesArray[i]; j++){tableTextArea.append(" ");}
				
				tableTextArea.append(tempStringArray[i]);
				tableTextArea.append("          ");
				tableTextArea.append(NumberFormats.getFormattedValueLong(ds.getAddRateArray()[i]) + "\n");		
			}
			
			tableTextArea.append("\n");

			numberRates++;
		
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

