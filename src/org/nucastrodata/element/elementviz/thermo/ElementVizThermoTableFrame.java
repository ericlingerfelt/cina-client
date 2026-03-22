package org.nucastrodata.element.elementviz.thermo;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;

public class ElementVizThermoTableFrame extends JFrame implements WindowListener, ActionListener{
    
    JTextArea tableTextArea;
    JPanel buttonPanel, topPanel;
    JButton saveButton, copyButton;
    double[] x, y;
    String units;
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
     * Instantiates a new element viz thermo table frame.
     *
     * @param ds the ds
     */
    public ElementVizThermoTableFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        setTitle("Temperature vs. Time Table of Points");
        
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
   
   		if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.tempRadioButton.isSelected()){
   		
   			setTitle("Temperature vs. Time Table of Points");
   		
   		}else if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.densityRadioButton.isSelected()){
   		
   			setTitle("Density vs. Time Table of Points");
   		
   		}
   
   		tableTextArea.setText("");

		if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.tempRadioButton.isSelected()){
		
			for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
			
				if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].isSelected()){
	
					tableTextArea.append("Temperature vs. Time Data for: " 
											+ Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].getText() 
											+ "\n\n");
					
					tableTextArea.append("Time(sec)   Temperature(T9)\n\n");
				
					for(int j=0; j<ds.getTimeDataArrayThermo()[i].length; j++){
					
						if(ds.getTimeDataArrayThermo()[i][j]<=Cina.elementVizFrame.elementVizThermoPlotFrame.getTimemax() 
								&& ds.getTimeDataArrayThermo()[i][j]>=Cina.elementVizFrame.elementVizThermoPlotFrame.getTimemin()){
					
							int timeSpaces = getTimeSpaces(NumberFormats.getFormattedTime(ds.getTimeDataArrayThermo()[i][j]));
					
							for(int k=0; k<timeSpaces; k++){
					
								tableTextArea.append(" ");
					
							}		
					
							tableTextArea.append(NumberFormats.getFormattedTime(ds.getTimeDataArrayThermo()[i][j])
													+ "     "
													+ NumberFormats.getFormattedValueLong(ds.getTempDataArray()[i][j])
													+ "\n");
												
						}
					
					}
					
					tableTextArea.append("\n\n");
					
				}

			}
			
		}else if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.densityRadioButton.isSelected()){
   		
   			for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
			
				if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].isSelected()){
	
					tableTextArea.append("Density vs. Time Data for: " 
											+ Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].getText() 
											+ "\n\n");
					
					tableTextArea.append("Time(sec)   Density(g/cm^3)\n\n");
				
					for(int j=0; j<ds.getTimeDataArrayThermo()[i].length; j++){
					
						if(ds.getTimeDataArrayThermo()[i][j]<=Cina.elementVizFrame.elementVizThermoPlotFrame.getTimemax() 
								&& ds.getTimeDataArrayThermo()[i][j]>=Cina.elementVizFrame.elementVizThermoPlotFrame.getTimemin()){
					
							int timeSpaces = getTimeSpaces(NumberFormats.getFormattedTime(ds.getTimeDataArrayThermo()[i][j]));
					
							for(int k=0; k<timeSpaces; k++){
					
								tableTextArea.append(" ");
					
							}		
					
							tableTextArea.append(NumberFormats.getFormattedTime(ds.getTimeDataArrayThermo()[i][j])
													+ "     "
													+ NumberFormats.getFormattedValueLong(ds.getDensityDataArray()[i][j])
													+ "\n");
												
						}
					
					}
					
				}
				
				tableTextArea.append("\n\n");

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
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
    	String string = "";
    	
    	if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.tempRadioButton.isSelected()){
   		
   			string += "Temperature vs. Time Table of Points\n\n";
   		
   		}else if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.densityRadioButton.isSelected()){
   		
   			string += "Density vs. Time Table of Points\n\n";
   		
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

