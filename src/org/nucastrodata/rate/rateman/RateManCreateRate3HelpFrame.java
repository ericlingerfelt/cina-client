package org.nucastrodata.rate.rateman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;

import java.awt.event.*;

import org.nucastrodata.Fonts;


/**
 * The Class RateManCreateRate3HelpFrame.
 */
public class RateManCreateRate3HelpFrame extends JFrame{

    /**
     * Instantiates a new rate man create rate3 help frame.
     */
    public RateManCreateRate3HelpFrame(){
    	
    	Container c = getContentPane();
    	c.setLayout(new BorderLayout());
        setSize(349, 400);
        setVisible(true); 
        setTitle("Help of Parameter File Format");

        addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent we) {
		        setVisible(false);
		        dispose();
		    } 
        });
        
    	String string = "The parameter file must be an ASCII text file containing ONLY the parameters. ";
    	string += "The parameters must be separated by the space character, the tab character, the newline character";
    	string += ", the carriage-return character, or the form-feed character. ";
    	string += "If the rate is classified as a decay reaction, only one parameter is required. ";
    	string += "Below are examples of suitable parameter input files.\n\n";
    	string += "A file with 14 parameters:\n\n";
    	string += "4.27026\n-5.69067E-13\n7.48144E-11\n-2.47239E-10\n2.48052E-11\n-2.07736E-12\n8.43048E-11\n20.1798\n-2.12961\n16.8052\n-30.138\n1.14711\n-0.0220312\n13.7452\n\n";
        
        JTextArea helpTextArea = new JTextArea(string);
      	helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setFont(Fonts.textFont);
		
      	JScrollPane sp = new JScrollPane(helpTextArea
      								, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
      								, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
        
        helpTextArea.setCaretPosition(0);
        
		c.add(sp, BorderLayout.CENTER);
		
		

        c.validate();

    }
}

