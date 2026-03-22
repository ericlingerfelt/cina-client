package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;

import java.awt.event.*;

import org.nucastrodata.Fonts;


/**
 * The Class RateParamStartHelpFrame.
 */
public class RateParamStartHelpFrame extends JFrame implements WindowListener{
    
    /** The help text area. */
    JTextArea helpTextArea;
    
    /** The sp. */
    JScrollPane sp;
    
    /** The c. */
    Container c;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /**
     * Instantiates a new rate param start help frame.
     */
    public RateParamStartHelpFrame(){
    	
    	c = getContentPane();
    	
        setSize(349, 400);
        setVisible(true); 
         
        setTitle("Help on Parameter Set Format");
         
        c.setLayout(new BorderLayout());
        
        gbc = new GridBagConstraints();
        
        addWindowListener(this);
        
        helpTextArea = new JTextArea(getAboutString());
      	helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setFont(Fonts.textFont);
		
      	sp = new JScrollPane(helpTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
    public String getAboutString(){
    
    	String string = "";

    	string = "The parameter set or file to be imported must be an ASCII text file containing ONLY the parameters. ";
    	string += "The parameters must be separated by the space character, the tab character, the newline character";
    	string += ", the carriage-return character, or the form-feed character. ";
    	string += "Below is an example of a suitable parameter input set.\n\n";
    	string += "A set with 14 parameters:\n\n";
    	string += "4.27026\n-5.69067E-13\n7.48144E-11\n-2.47239E-10\n2.48052E-11\n-2.07736E-12\n8.43048E-11\n20.1798\n-2.12961\n16.8052\n-30.138\n1.14711\n-0.0220312\n13.7452\n\n";
    	
    	return string;
    	
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

