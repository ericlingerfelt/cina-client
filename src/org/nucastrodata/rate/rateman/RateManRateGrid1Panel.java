package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.awt.datatransfer.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.rate.rateman.RateManCustomGridFrame;


/**
 * The Class RateManRateGrid1Panel.
 */
public class RateManRateGrid1Panel extends WizardPanel implements ActionListener, ItemListener{
   
    /** The choose button. */
    private JButton browseButton, pasteButton, clearButton, chooseButton;
    
    /** The grid text area. */
    protected JTextArea gridTextArea;
    
    /** The units combo box. */
    protected JComboBox unitsComboBox;
    
    /** The filename field. */
    protected JTextField filenameField;
    
    /** The ds. */
    private RateManDataStructure ds;
    
	/**
	 * Instantiates a new rate man rate grid1 panel.
	 *
	 * @param ds the ds
	 */
	public RateManRateGrid1Panel(RateManDataStructure ds){
	
		this.ds = ds;
	
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(4);
		Cina.rateManFrame.setCurrentPanelIndex(1);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 13, 13));
		JPanel tempGridPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Rate Manager", "Rates on a Grid", "1", "3");
		
		JLabel tempUnitsLabel = new JLabel("Temperature Units: ");
		tempUnitsLabel.setFont(Fonts.textFont);
		
		JLabel tempGridLabel = new JLabel("Temperature Grid: ");
		tempGridLabel.setFont(Fonts.textFont);
		
		JLabel filenameLabel = new JLabel("Data Source: ");
		filenameLabel.setFont(Fonts.textFont);
		
		unitsComboBox = new JComboBox();
		unitsComboBox.addItem("T8");
		unitsComboBox.addItem("T9");
		unitsComboBox.setSelectedItem("T9");
		unitsComboBox.addItemListener(this);
		unitsComboBox.setFont(Fonts.textFont);
		
		filenameField = new JTextField(10);
		filenameField.setFont(Fonts.textFont);
		filenameField.setEditable(false);

		//Create Buttons//////////////////////////////////////////////BUTTONS/////////////////////
		browseButton = new JButton("Browse...");
		browseButton.setFont(Fonts.buttonFont);
		browseButton.addActionListener(this);
		
		pasteButton = new JButton("Paste from Clipboard");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this);
		
		clearButton = new JButton("Clear Temperature Grid");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		chooseButton = new JButton("Choose Custom Temp Grid");
		chooseButton.setFont(Fonts.buttonFont);
		chooseButton.addActionListener(this);

		buttonPanel.add(filenameLabel);
		buttonPanel.add(filenameField);
		buttonPanel.add(browseButton);
		buttonPanel.add(pasteButton);
		buttonPanel.add(chooseButton);
		buttonPanel.add(clearButton);
		
		gridTextArea = new JTextArea("");
		gridTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(gridTextArea
								, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
								, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 150));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		tempGridPanel.add(tempUnitsLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		tempGridPanel.add(unitsComboBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		tempGridPanel.add(tempGridLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
		tempGridPanel.add(sp, gbc);
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		JLabel topLabel = new JLabel("<html>Click the <i>Browse</i> button to upload temperature grid file, "
								+ "click<p><i>Paste from Clipboard</i> to paste your grid data into the<p>Temperature Grid field, "
								+ " or click <i>Choose Custom Temp Grid</i><p>to generate a temperature grid.</html>");
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 20, 5);
		gbc.gridwidth = 3;
		mainPanel.add(topLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTH;
		mainPanel.add(buttonPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(tempGridPanel, gbc);
		
		gbc.gridwidth = 1;
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==browseButton){
		
			JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				filenameField.setText(file.getName());

				if(!uploadFile(file)){
				
					String string = "The file you have uploaded is empty. Please choose another.";
					
					Dialogs.createExceptionDialog(string, Cina.rateManFrame);
					
					filenameField.setText("");
				
				}else{
				
					gridTextArea.setText(ds.getTempGridFile());
				
				}
				
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==clearButton){
		
			gridTextArea.setText("");
		
			filenameField.setText("");
		
			ds.setTempGridFile("");
		
		}else if(ae.getSource()==chooseButton){
		
			unitsComboBox.setEnabled(false);
		
			if(Cina.rateManFrame.rateManCustomGridFrame==null){
				
				Cina.rateManFrame.rateManCustomGridFrame = new RateManCustomGridFrame(ds);
			
			}else{
				
				Cina.rateManFrame.rateManCustomGridFrame.setVisible(true);
			
			}
			
			Cina.rateManFrame.rateManCustomGridFrame.setLabelState();
		
		}else if(ae.getSource()==pasteButton){
		
			gridTextArea.setText("");
		
			gridTextArea.paste();
			
			filenameField.setText("Grid Pasted");
			
			ds.setTempGridFile("");
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==unitsComboBox){
			ds.setTempUnits((String)unitsComboBox.getSelectedItem());
		}
	
	}
	
	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	private boolean uploadFile(File file){
		
		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(stringBuffer);
			fileInputStream.close();
		
		}catch(Exception e){
		
			e.printStackTrace();
		
		}
		
		ds.setTempGridFile(new String(stringBuffer));
		
		if(new String(stringBuffer).equals("")){
			return false;
		}else{
			return true;
		}
	
	}
	
	/**
	 * Check temp grid.
	 *
	 * @return true, if successful
	 */
	protected boolean checkTempGrid(){
	
		boolean goodTempGrid = true;
		
		StringTokenizer st = new StringTokenizer(gridTextArea.getText());
		
		if(!gridTextArea.getText().equals("")){
		
			double[] tempArray = new double[st.countTokens()];
			
			try{
			
				badNumber:
			
				for(int i=0; i<tempArray.length; i++){
				
					tempArray[i] = Double.valueOf(st.nextToken()).doubleValue();
					
					if((tempArray[i]<0.01 || tempArray[i]>10.0) && ((String)unitsComboBox.getSelectedItem()).equals("T9")){
					
						String string = "All temperature grid points must be less than 0.01 T9 and greater than 10.0 T9.";
				
						Dialogs.createExceptionDialog(string, Cina.rateManFrame);
					
						goodTempGrid = false;
						
						break badNumber;	
					
					}else if((tempArray[i]<0.1 || tempArray[i]>100.0) && ((String)unitsComboBox.getSelectedItem()).equals("T8")){
					
						String string = "All temperature grid points must be less than 0.1 T8 and greater than 100.0 T8.";
				
						Dialogs.createExceptionDialog(string, Cina.rateManFrame);
					
						goodTempGrid = false;
						
						break badNumber;	
					
					}
				
				}
			
			}catch(NumberFormatException nfe){
				
				String string = "All temperature grid points must be numeric values.";
				
				Dialogs.createExceptionDialog(string, Cina.rateManFrame);
			
				goodTempGrid = false;
			
			}
		
		}else{
		
			String string = "The temperature grid you have submitted is blank.";
				
			Dialogs.createExceptionDialog(string, Cina.rateManFrame);
		
			goodTempGrid = false;
		
		}

		return goodTempGrid;	
	
	}
	
	/**
	 * Gets the temp grid.
	 *
	 * @return the temp grid
	 */
	private double[] getTempGrid(){
	
		StringTokenizer st = new StringTokenizer(gridTextArea.getText());
		double[] tempArray = new double[st.countTokens()];
		
		for(int i=0; i<tempArray.length; i++){
			tempArray[i] = Double.valueOf(st.nextToken()).doubleValue();
		}
				
		return tempArray;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setTempGridFile(gridTextArea.getText());
		ds.setDataSourceString(filenameField.getText());
		ds.setRateTempGrid(getTempGrid());
		ds.setTempUnits((String)unitsComboBox.getSelectedItem());
		
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		gridTextArea.setText(ds.getTempGridFile());
		filenameField.setText(ds.getDataSourceString());
		unitsComboBox.setSelectedItem(ds.getTempUnits());
		
	}
	
}

