package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;
import java.text.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateParamInputCheckTableFrame.
 */
public class RateParamInputCheckTableFrame extends JFrame implements WindowListener, ActionListener{

   
    /** The table text area. */
    JTextArea tableTextArea;

    /** The copy button. */
    JButton saveButton, copyButton;
    
    /** The reaction string. */
    String reactionString;
    
    /** The y. */
    double[] x, y;

 	/** The ds. */
	 private RateParamDataStructure ds;
 
    /**
     * Instantiates a new rate param input check table frame.
     *
     * @param ds the ds
     */
    public RateParamInputCheckTableFrame(RateParamDataStructure ds){
    	
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
	   	
   		setTitle("Rate vs. Temperature Table: " + reactionString);
   	
   		x = new double[ds.getRateDataArray().length];
    	y = new double[ds.getRateDataArray().length];
    	
    	String[] tempStringArray = new String[ds.getRateDataArray().length];
    	
    	int[] tempSpacesArray = new int[ds.getRateDataArray().length];
    	
    	tableTextArea.append("Rate vs. Temperature Data for: " + reactionString + "\n\n");   
   		tableTextArea.append("Temperature(" 
   								+ ds.getXUnits() 
   								+ ")  Rate(" 
   								+ ds.getRateUnits() 
   								+ ")\n\n");
		
	   	for(int i=0; i<ds.getRateDataArray().length; i++){
        	
            x[i] = ds.getRateDataArray()[i];
            y[i] = ds.getTempDataArray()[i];
            
            tempStringArray[i] = NumberFormats.getFormattedTemp2(y[i]);
            
            tempSpacesArray[i] = getTempSpaces(tempStringArray[i]);
            
            for(int j=0; j<tempSpacesArray[i]; j++){
			
				tableTextArea.append(" ");
			
			}
			
			tableTextArea.append(tempStringArray[i]);
			
			tableTextArea.append("          ");
			
			tableTextArea.append(NumberFormats.getFormattedValueLong(x[i]) + "\n");
            
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

