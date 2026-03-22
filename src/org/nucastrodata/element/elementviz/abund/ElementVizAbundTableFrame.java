package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class ElementVizAbundTableFrame.
 */
public class ElementVizAbundTableFrame extends JFrame implements WindowListener, ActionListener{
    
    /** The table text area. */
    JTextArea tableTextArea;
    
    /** The top panel. */
    JPanel buttonPanel, topPanel;
    
    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The y. */
    double[] x, y;
    
    /** The units. */
    String units;
	
	/** The index. */
	int index;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The c. */
    Container c;    
    
    /** The sp. */
    JScrollPane sp;

	/** The ds. */
	private ElementVizDataStructure ds;

    /**
     * Instantiates a new element viz abund table frame.
     *
     * @param ds the ds
     */
    public ElementVizAbundTableFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        setTitle("Abundance vs. Time Table of Points");
        
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
   
   		if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.abundRadioButton.isSelected()){
   		
   			setTitle("Abundance vs. Time Table of Points");
   		
   		}else if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.ratioRadioButton.isSelected()){
   		
   			setTitle("Abundance Ratio vs. Mass Number Table of Points");
   		
   		}
   
   		tableTextArea.setText("");

		if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.abundRadioButton.isSelected()){
		
			for(int i=0; i<ds.getNumberIsotopeRunsAbund(); i++){
			
				if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.checkBoxArray[i].isSelected()){
	
					tableTextArea.append("Abundance vs. Time Data for: " 
											+ Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.checkBoxArray[i].getText() 
											+ "\n\n");
					
					tableTextArea.append("Time(sec)   Abundance\n\n");
					
					for(int j=0; j<ds.getTimeDataArrayAbund()[i].length; j++){
					
						if((ds.getTimeDataArrayAbund()[i][j]<=Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getTimemax() 
								&& ds.getTimeDataArrayAbund()[i][j]>=Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getTimemin()) 
								&& Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.timeLinButton.isSelected()){
					
							int timeSpaces = getTimeSpaces(NumberFormats.getFormattedTime(ds.getTimeDataArrayAbund()[i][j]));
					
							for(int k=0; k<timeSpaces; k++){
					
								tableTextArea.append(" ");
					
							}		
					
							tableTextArea.append(NumberFormats.getFormattedTime(ds.getTimeDataArrayAbund()[i][j])
													+ "     "
													+ NumberFormats.getFormattedValueLong(ds.getAbundDataArray()[i][j])
													+ "\n");
												
						}else if((ds.getTimeDataArrayAbund()[i][j]<=Math.pow(10, Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getTimemaxLog()) 
								&& ds.getTimeDataArrayAbund()[i][j]>=Math.pow(10, Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getTimeminLog())) 
								&& Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.timeLogButton.isSelected()){
							
							int timeSpaces = getTimeSpaces(NumberFormats.getFormattedTime(ds.getTimeDataArrayAbund()[i][j]));
							
							for(int k=0; k<timeSpaces; k++){
					
								tableTextArea.append(" ");
					
							}		
					
							tableTextArea.append(NumberFormats.getFormattedTime(ds.getTimeDataArrayAbund()[i][j])
													+ "     "
													+ NumberFormats.getFormattedValueLong(ds.getAbundDataArray()[i][j])
													+ "\n");
							
						}
					
					}
					
					tableTextArea.append("\n\n");
					
				}

			}
			
		}else if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.ratioRadioButton.isSelected()){
   		
   			for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
			
				if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.checkBoxArray[i].isSelected()){
	
					tableTextArea.append("Abundance Ratio vs. Mass Number Data for: " 
											+ Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.checkBoxArray[i].getText() 
											+ "\n\n");
					
					tableTextArea.append("Mass Number   Abundance Ratio\n\n");
				
					for(int j=0; j<ds.getMassDataArrayAbund()[i].length; j++){
					
						if(ds.getMassDataArrayAbund()[i][j]<=Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getMassmaxAbund() 
								&& ds.getMassDataArrayAbund()[i][j]>=Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.getMassminAbund()){
					
							int timeSpaces = getTimeSpaces(NumberFormats.getFormattedMass(ds.getMassDataArrayAbund()[i][j]));
					
							for(int k=0; k<timeSpaces; k++){
					
								tableTextArea.append(" ");
					
							}		
					
							tableTextArea.append(NumberFormats.getFormattedMass(ds.getMassDataArrayAbund()[i][j])
													+ "     "
													+ NumberFormats.getFormattedValueLong(ds.getRatioDataArrayAbund()[i][j])
													+ "\n");
												
						}
					
					}
					
					tableTextArea.append("\n\n");
					
				}

			}
   		
   		}

    	tableTextArea.setCaretPosition(0);
		
		
		
	}
    
    /**
     * Gets the time spaces.
     *
     * @param string the string
     * @return the time spaces
     */
    public int getTimeSpaces(String string){

		int numSpaces = 0;	
		
		int decimalIndex = string.indexOf(".");
		
		numSpaces = 5 - decimalIndex;
		
		return numSpaces;
	
	}
    
    /**
     * Gets the mass spaces.
     *
     * @param string the string
     * @return the mass spaces
     */
    public int getMassSpaces(String string){

		int numSpaces = 0;	
		
		int decimalIndex = string.indexOf(".");
		
		numSpaces = 3 - decimalIndex;
		
		return numSpaces;
	
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
    	String string = "";
    
    	if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.abundRadioButton.isSelected()){
   		
   			string += "Abundance vs. Time Table of Points\n\n";
   		
   		}else if(Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundIsotopeListPanel.ratioRadioButton.isSelected()){
   		
   			string += "Abundance Ratio vs. Mass Number Table of Points\n\n";
   		
   		}
   		
        string += tableTextArea.getText();
    
    	if(ae.getSource()==saveButton){
    	
			TextSaver.saveText(string, this);	
    	
    	}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(string);
    	
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

