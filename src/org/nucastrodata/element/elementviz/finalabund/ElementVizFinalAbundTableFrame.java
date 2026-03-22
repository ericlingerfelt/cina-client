package org.nucastrodata.element.elementviz.finalabund;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;

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
public class ElementVizFinalAbundTableFrame extends JFrame implements WindowListener, ActionListener{
    
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
    public ElementVizFinalAbundTableFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true);
          
        setTitle("Final Weighted Abundance Table of Points");
        
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

		NucSimSetDataStructure nssds = ds.getFinalNucSimSetDataStructure();
		
		
		tableTextArea.append("Final Weighted Abundance Table of Points for: " 
				+ Cina.elementVizFrame.elementVizFinalAbundFrame.nucSimComboBox.getSelectedItem().toString() 
				+ "\n\n");
		tableTextArea.append("Isotope\tZ\tA\tAbundance\n\n");
		
   		for(int i=0; i<nssds.getIndexArray().length; i++){
			
			int index = nssds.getIndexArray()[i];
			double abund = nssds.getFinalAbundArray()[i];
			
			int z = getZ(nssds, index);
			int a = getA(nssds, index);
			String isotope = getIsotope(z, a);
			
			String abundString = NumberFormats.getFormattedValueLong(abund);
			tableTextArea.append(isotope + "\t" + z + "\t" + a + "\t" + abundString);
			tableTextArea.append("\n");

		}
		tableTextArea.setCaretPosition(0);
		
	}
    
    private int getZ(NucSimSetDataStructure nssds, int index){
    		return (int) nssds.getZAMapArray()[index].getX();
    }
    
    private int getA(NucSimSetDataStructure nssds, int index){
    		return (int) nssds.getZAMapArray()[index].getY();
    }
    
    private String getIsotope(int z, int a){
    		return a + MainDataStructure.getElementSymbol(z);
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

