package org.nucastrodata.data.dataman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class DataManImportDataPasteFrame.
 */
public class DataManImportDataPasteFrame extends JFrame implements ActionListener{
	
    /** The data text area. */
    private JTextArea dataTextArea;
    
    /** The submit button. */
    private JButton clearButton, pasteButton, submitButton;
	
	/** The ds. */
	private DataManDataStructure ds;

	/**
	 * Instantiates a new data man import data paste frame.
	 *
	 * @param ds the ds
	 */
	public DataManImportDataPasteFrame(DataManDataStructure ds){
		
		this.ds = ds;
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setSize(520, 540);
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			setTitle("Cut and Paste Data: " + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
		
			setTitle("Cut and Paste Data: " 
						+ ds.getImportNucDataDataStructure().getReactionString()
						+ " ["
						+ ds.getImportNucDataDataStructure().getDecay()
						+ "]");
		
		}
		
		dataTextArea = new JTextArea("");
		dataTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(dataTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

		addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });
		
		
		setVisible(true);
	
	}

	/**
	 * Refresh data.
	 */
	public void refreshData(){
	
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			setTitle("Cut and Paste Data: " + ds.getImportNucDataDataStructure().getReactionString());
		
		}else{
		
			setTitle("Cut and Paste Data: " 
						+ ds.getImportNucDataDataStructure().getReactionString()
						+ " ["
						+ ds.getImportNucDataDataStructure().getDecay()
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
				Cina.dataManFrame.dataManImportData3Panel.filenameField.setText("Data Pasted");
			
				dispose();
				setVisible(false);
			
			}else{
			
				String string = "The data field is empty.";
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}

	}	
   
}