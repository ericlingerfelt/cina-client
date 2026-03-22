package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RateParamReadInputPasteFrame.
 */
public class RateParamReadInputPasteFrame extends JFrame implements WindowListener, ActionListener{

    /** The data text area. */
    private JTextArea dataTextArea;
    
    /** The submit button. */
    private JButton clearButton, pasteButton, submitButton;
    
    /** The ds. */
    private RateParamDataStructure ds;
    
	/**
	 * Instantiates a new rate param read input paste frame.
	 *
	 * @param ds the ds
	 */
	public RateParamReadInputPasteFrame(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		setSize(480, 520);
		
		if(ds.getRateDataStructure().getDecay().equals("")){
		
			setTitle("Cut and Paste Data: " + ds.getRateDataStructure().getReactionString());
		
		}else{
			
			setTitle("Cut and Paste Data: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		dataTextArea = new JTextArea("");
		dataTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(dataTextArea
												, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
												, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300, 300));
		
		clearButton = new JButton("Clear Data Field");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		pasteButton = new JButton("Paste from Clipboard");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this); 
		
		submitButton = new JButton("Submit Data");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this); 
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		buttonPanel.add(clearButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		buttonPanel.add(pasteButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		
		buttonPanel.add(submitButton, gbc);

		c.add(sp, BorderLayout.CENTER);

		c.add(buttonPanel, BorderLayout.SOUTH);

		addWindowListener(this);
		
		
		
		setVisible(true);
	
	}

	/**
	 * Refresh data.
	 */
	public void refreshData(){
	
		if(ds.getRateDataStructure().getDecay().equals("")){
		
			setTitle("Cut and Paste Data: " + ds.getRateDataStructure().getReactionString());
		
		}else{
			
			setTitle("Cut and Paste Data: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==pasteButton){
		
			dataTextArea.paste();
		
		}else if(ae.getSource()==clearButton){
		
			dataTextArea.setText("");
		
		}else if(ae.getSource()==submitButton){
		
			if(!dataTextArea.getText().equals("")){
		
				ds.setInputFile(dataTextArea.getText());
				ds.setInputFilename("");
				ds.setInputFileDir("");
				ds.setPastedData(true);
				Cina.rateParamFrame.rateParamReadInputPanel.filenameField.setText("Data Pasted");
			
				dispose();
				setVisible(false);
			
			}else{
			
				String string = "The data field is empty.";
				Dialogs.createExceptionDialog(string, this);
			
			}
		
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