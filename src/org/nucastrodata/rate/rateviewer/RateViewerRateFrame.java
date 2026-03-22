package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;

import java.io.*;
import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RateViewerRateFrame.
 */
public class RateViewerRateFrame extends JFrame implements ActionListener{

	/** The paste button. */
	private JButton submitButton, importButton, pasteButton;
	
	/** The text area. */
	private JTextArea textArea;
	
	/** The rate field. */
	private JTextField filenameField, rateField;
	
	/** The ds. */
	private RateViewerDataStructure ds;

	/**
	 * Instantiates a new rate viewer rate frame.
	 *
	 * @param ds the ds
	 */
	public RateViewerRateFrame(RateViewerDataStructure ds){
	
		this.ds = ds;
	
		setSize(500, 600);
		setTitle("Upload or Paste Rate");
	
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		JLabel topLabel = new JLabel("<html>With this tool, you can add a rate for plotting by:<p>"
											+ " - importing a rate by clicking <i>Import</i>.<p>"
											+ " - typing the rate points into the field below.<p>"
											+ " - pasting the rate by clicking <i>Paste from Clipboard</i><p>" 
											+ "Click <i>Submit Rate</i> when finished.<p>"
											+ "<i>Enter points as a temp column then a rate column.</i></html>");
		
		JLabel rateLabel = new JLabel("Reaction Name: ");
		rateLabel.setFont(Fonts.textFont);
		
		rateField = new JTextField(22);
		rateField.setFont(Fonts.textFont);
	
		JPanel ratePanel = new JPanel(new GridBagLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		ratePanel.add(rateLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		ratePanel.add(rateField, gbc);
	
		gbc.anchor = GridBagConstraints.CENTER;
	
		filenameField = new JTextField(10);
		filenameField.setEditable(false);
		filenameField.setFont(Fonts.textFont);
	
		submitButton = new JButton("Submit Rate");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
	
		importButton = new JButton("Import");
		importButton.setFont(Fonts.buttonFont);
		importButton.addActionListener(this);
	
		pasteButton = new JButton("Paste from Clipboard");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this);
		
		textArea = new JTextArea();
		
		JScrollPane sp = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(355, 300));
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 3;
		c.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		c.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		c.add(ratePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		c.add(pasteButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		c.add(importButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		c.add(filenameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		c.add(submitButton, gbc);
		
		gbc.gridwidth = 1;
		
		
		validate();
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		if(ds.getRateAdded()){
		
			textArea.setText(getRateTempString());
			textArea.setCaretPosition(0);
			rateField.setText(ds.getAddRateName());
		
		}else{
			
			textArea.setText("");
			textArea.setCaretPosition(0);
		
		}
	
	}
	
	/**
	 * Gets the rate temp string.
	 *
	 * @return the rate temp string
	 */
	public String getRateTempString(){
		
		String string = "";
		
		double[] tempArray = ds.getAddTempArray();
		double[] rateArray = ds.getAddRateArray();
		
		for(int i=0; i<tempArray.length; i++){
		
			string += tempArray[i] + "   " + rateArray[i] + "\n";
		
		}
		
		return string;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==submitButton){
		
			if(textArea.getText().trim().equals("")){
			
				dispose();
				setVisible(false);
				
				String string = "Rate was not added. No rate points entered.";
				Dialogs.createExceptionDialog(string, Cina.rateViewerFrame.rateViewerPlotFrame);
			
			}else if(!rateField.getText().trim().equals("")){
			
				try{
				
					addRate();
					Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.initialize();
					Cina.rateViewerFrame.rateViewerPlotFrame.redrawPlot();
					Cina.rateViewerFrame.rateViewerPlotFrame.validate();
					
					dispose();
					setVisible(false);
				
				}catch(NumberFormatException nfe){
				
					String string = "Rate and temperature values must be numbers";
					Dialogs.createExceptionDialog(string, this);
								
				}catch(ArrayIndexOutOfBoundsException aioobe){
				
					String string = "A rate point was not an ordered pair.";
					Dialogs.createExceptionDialog(string, this);
				
				}
			
			}else{
				
				String string = "Please enter a name for this rate.";
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}else if(ae.getSource()==importButton){
		
			JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				uploadFile(file);
				
				filenameField.setText(file.getName());
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
		
		}else if(ae.getSource()==pasteButton){
		
			textArea.paste();
			filenameField.setText("Rate Pasted");
		
		}
	
	}
	
	/**
	 * Adds the rate.
	 *
	 * @throws NumberFormatException the number format exception
	 * @throws ArrayIndexOutOfBoundsException the array index out of bounds exception
	 */
	public void addRate() throws NumberFormatException, ArrayIndexOutOfBoundsException{
	
		StringTokenizer st = new StringTokenizer(textArea.getText());
		int length = (int)(st.countTokens()/2.0);
		
		double[] tempArray = new double[length];
		double[] rateArray = new double[length];
		
		for(int i=0; i<length; i++){
			
			tempArray[i] = Double.valueOf(st.nextToken()).doubleValue();
			rateArray[i] = Double.valueOf(st.nextToken()).doubleValue();
			
		}
		
		ds.setAddTempArray(tempArray);
		ds.setAddRateArray(rateArray);
		ds.setAddRateName(rateField.getText().trim());
		ds.setRateAdded(true);
	
	}
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 */
	public void uploadFile(File file){

		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(stringBuffer);
			
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		
		String string = new String(stringBuffer);
		
		textArea.setText(string);
	
	}

}