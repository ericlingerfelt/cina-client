package org.nucastrodata.rate.rateparam;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.text.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;


/**
 * The Class RateParamResultsInverseFrame.
 */
public class RateParamResultsInverseFrame extends JFrame implements ActionListener{
	
	/** The param text area. */
	private JTextArea paramTextArea;
    
    /** The save button. */
    private JButton copyButton, saveButton;
   	
	   /** The ds. */
	   private RateParamDataStructure ds;
   
	/**
	 * Instantiates a new rate param results inverse frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsInverseFrame(RateParamDataStructure ds){
		
		this.ds = ds;
		
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setSize(379, 398);
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Parameters for the Inverse of the " + ds.getRateDataStructure().getReactionString() + " Rate");
		
		}else{
			
			setTitle("Parameters for the Inverse of the "
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "] Rate");
		
		}
		
		paramTextArea = new JTextArea("");
		paramTextArea.setFont(Fonts.textFontFixedWidth);
		paramTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(paramTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(300, 300));
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 20));
		
		buttonPanel.add(saveButton);
		buttonPanel.add(copyButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		c.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		c.add(buttonPanel, gbc);
		
		setParamText();
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		
		
		
		setVisible(true);
	
	}
	
	/**
	 * Refresh data.
	 */
	protected void refreshData(){
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Parameters for the Inverse of the " + ds.getRateDataStructure().getReactionString() + " Rate");
		
		}else{
			
			setTitle("Parameters for the Inverse of the "
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "] Rate");
		
		}
		
		setParamText();
		
	}
	
	/**
	 * Sets the param text.
	 */
	private void setParamText(){
	
		paramTextArea.setText("");
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Parameters for the Inverse of the " + ds.getRateDataStructure().getReactionString() + " Rate");
		
		}else{
			
			setTitle("Parameters for the Inverse of the "
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "] Rate\n\n");
		
		}
        
        String[] paramString = {"a(1) = ", "a(2) = ", "a(3) = ", "a(4) = ", "a(5) = ", "a(6) = ", "a(7) = "
									, "a(8) = ", "a(9) = ", "a(10)= ", "a(11)= ", "a(12)= ", "a(13)= ", "a(14)= "
									, "a(15)= ", "a(16)= ", "a(17)= ", "a(18)= ", "a(19)= ", "a(20)= ", "a(21)= "
									, "a(22)= ", "a(23)= ", "a(24)= ", "a(25)= ", "a(26)= ", "a(27)= ", "a(28)= "
									, "a(29)= ", "a(30)= ", "a(31)= ", "a(32)= ", "a(33)= ", "a(34)= ", "a(35)= "
									, "a(36)= ", "a(37)= ", "a(38)= ", "a(39)= ", "a(40)= ", "a(41)= ", "a(42)= "
									, "a(43)= ", "a(44)= ", "a(45)= ", "a(46)= ", "a(47)= ", "a(48)= ", "a(49)= "};
         					
        for(int i=0; i<ds.getRateDataStructure().getNumberParameters(); i++){
        
        	paramTextArea.append(paramString[i]);
        	
        	paramTextArea.append(NumberFormats.getFormattedParameter(ds.getInverseParamArray()[i]) + "\n");
        	
        }
        
        paramTextArea.setCaretPosition(0);
        
        
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==saveButton){
			TextSaver.saveText(paramTextArea.getText(), this);
		}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(paramTextArea.getText());
    	}
	}
}