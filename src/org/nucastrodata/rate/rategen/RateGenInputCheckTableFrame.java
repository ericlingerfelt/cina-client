package org.nucastrodata.rate.rategen;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateGenInputCheckTableFrame.
 */
public class RateGenInputCheckTableFrame extends JFrame implements WindowListener, ActionListener{

   
    /** The table text area. */
    JTextArea tableTextArea;

    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The reaction string. */
    String reactionString;
    
    /** The y. */
    double[] x, y;

 	/** The ds. */
	 private RateGenDataStructure ds;
 
    /**
     * Instantiates a new rate gen input check table frame.
     *
     * @param ds the ds
     */
    public RateGenInputCheckTableFrame(RateGenDataStructure ds){
    	
    	this.ds = ds;
    	
    	Container c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true); 
        
        c.setLayout(new BorderLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(this);

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
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

      	JScrollPane sp = new JScrollPane(tableTextArea
      							, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
      							, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
   		
   		reactionString = ds.getRateDataStructure().getReactionString();
        
        if(!ds.getRateDataStructure().getDecay().equals("")){
        
        	reactionString += " ["
        						+ ds.getRateDataStructure().getDecay()
        						+ "]";
        
        }
        
        if(ds.getInputType().equals("S(E)")){
        
        	setTitle("S Factor vs. Energy Table: " + reactionString);
   
   			x = new double[ds.getSFactorDataArray().length];
        	y = new double[ds.getSFactorDataArray().length];
   			
   			tableTextArea.append("S Factor vs. Energy Data for: " + reactionString + "\n\n");   
       		tableTextArea.append("Energy(keV)  S Factor(keV-b)\n\n");
   			
   			for(int i=0; i<ds.getSFactorDataArray().length; i++){
        	
	            x[i] = ds.getSFactorDataArray()[i];
	            y[i] = ds.getEnergyDataArray()[i];
	            
	            tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]));
	            tableTextArea.append("   " + NumberFormats.getFormattedValueLong(x[i]) + "\n");
 
			}
			
	   	}else if(ds.getInputType().equals("CS(E)")){
	   	
	   		setTitle("Cross Section vs. Energy Table: " + reactionString);
	   	
	   		x = new double[ds.getCrossSectionDataArray().length];
        	y = new double[ds.getCrossSectionDataArray().length];
        	
        	tableTextArea.append("Cross Section vs. Energy Data for: " + reactionString + "\n\n");   
       		tableTextArea.append("Energy(keV)  Cross Section(b)\n\n");
   			
		   	for(int i=0; i<ds.getCrossSectionDataArray().length; i++){
	        	
	            x[i] = ds.getCrossSectionDataArray()[i];
	            y[i] = ds.getEnergyDataArray()[i];
	            
	            tableTextArea.append(NumberFormats.getFormattedValueLong(y[i]));
	            tableTextArea.append("    " + NumberFormats.getFormattedValueLong(x[i]) + "\n");
	            
	       }
			
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

