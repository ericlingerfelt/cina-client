package org.nucastrodata.data.datamass;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;

import java.awt.event.*;


/**
 * The Class DataMassHelpFrame.
 */
public class DataMassHelpFrame extends JFrame{

    /**
     * Instantiates a new data mass help frame.
     */
    public DataMassHelpFrame(){
    	
    	Container c = getContentPane();
    	setTitle("Help with Mass Model File Format");
        setSize(349, 400);
        setVisible(true); 
        c.setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });
        
        JTextArea helpTextArea = new JTextArea(getAboutString());
      	helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		
      	JScrollPane sp = new JScrollPane(helpTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
        helpTextArea.setCaretPosition(0);
		c.add(sp, BorderLayout.CENTER);
		
		
        c.validate();
    }
    
    /**
     * Gets the about string.
     *
     * @return the about string
     */
    private String getAboutString(){
    	String string = "";
    	string = "When uploading a mass model to the Mass Model Evaluator, "
			    	+ "the file must be in the following format. The data must be in three "
			    	+ "columns of Z, N, and Mass Excess in units of MeV. The columns must be" 
			    	+ "separated by a space or tab and there can be no column headers, only "
			    	+ "the numbers. Also, the columns must be ordered from smallest to "
			    	+ "largest Z. \n\nExample:";
    	string += "\n\n8   8   -4.84"
					   + "\n8   9   -0.17"
					   + "\n8  10   -2.62"
					   + "\n8  11    2.56"
					   + "\n8  12    2.57"
					   + "\n8  13    8.20"
					   + "\n8  14   10.41"
					   + "\n8  15   17.72"
					   + "\n8  16   22.14"
					   + "\n8  17   30.93"
					   + "\n8  18   36.75";
    	
    	return string;	
    }
}

