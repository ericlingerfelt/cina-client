package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import java.net.*;
import java.awt.datatransfer.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;


/**
 * The Class RateManCustomGridFrame.
 */
public class RateManCustomGridFrame extends JFrame implements WindowListener
													, ActionListener
													, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
    
    /** The c. */
    Container c;

    /** The grid points label. */
    JLabel gridTypeLabel, gridRangeLabel, tempMaxLabel, tempMinLabel, gridPointsLabel;
    
    /** The submit button. */
    JButton resetButton, submitButton;
    
    /** The per range radio button. */
    JRadioButton logRadioButton, linRadioButton, perDecadeRadioButton, perRangeRadioButton;
    
    /** The button group2. */
    ButtonGroup buttonGroup1, buttonGroup2;
    
    /** The per range field. */
    JTextField tempMaxField, tempMinField, perDecadeField, perRangeField;
    
    /** The button panel. */
    JPanel gridTypePanel, gridRangePanel, gridPointsPanel, buttonPanel;

    /** The log10. */
    double log10 = 0.434294482;

	/** The decade array t9. */
	double[][] decadeArrayT9 = {{0.01, 0.1}
					, {0.1, 1.0}
					, {1.0, 10.0}}; 
					
	/** The decade array t8. */
	double[][] decadeArrayT8 = {{0.1, 1.0}
							, {1.0, 10.0}
							, {10.0, 100.0}}; 

	/** The ds. */
	private RateManDataStructure ds;

	/**
	 * Instantiates a new rate man custom grid frame.
	 *
	 * @param ds the ds
	 */
	public RateManCustomGridFrame(RateManDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		c.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
	
		setSize(586, 294);
		setVisible(true);
		setTitle("Choose Custom Temperature Grid");
		
		gridTypeLabel = new JLabel("Grid Type");
		//gridTypeLabel.setFont(CinaFonts.textFont);
		
		gridRangeLabel = new JLabel("Grid Range");
		//gridRangeLabel.setFont(CinaFonts.textFont);
		
		if(ds.getTempUnits().equals("T9")){
		
			tempMaxLabel = new JLabel("Temp max (<=10.0 T9): ");
			tempMaxLabel.setFont(Fonts.textFont);
			
			tempMinLabel = new JLabel("Temp min (>=0.01 T9): ");
			tempMinLabel.setFont(Fonts.textFont);
		
		}else if(ds.getTempUnits().equals("T8")){
		
			tempMaxLabel = new JLabel("Temp max (<=100.0 T8): ");
			tempMaxLabel.setFont(Fonts.textFont);
			
			tempMinLabel = new JLabel("Temp min (>=0.1 T8): ");
			tempMinLabel.setFont(Fonts.textFont);
		
		}
		
		gridPointsLabel = new JLabel("Grid Points");
		//gridIncrementsLabel.setFont(CinaFonts.textFont);
		
		logRadioButton = new JRadioButton("log", true);
		logRadioButton.setFont(Fonts.textFont);
		
		linRadioButton = new JRadioButton("lin", false);
		linRadioButton.setFont(Fonts.textFont);
		
		buttonGroup1 = new ButtonGroup();
		
		buttonGroup1.add(logRadioButton);
		buttonGroup1.add(linRadioButton);
		
		perDecadeRadioButton = new JRadioButton("Number of points per decade: ", true);
		perDecadeRadioButton.setFont(Fonts.textFont);
		perDecadeRadioButton.addItemListener(this);
		
		perRangeRadioButton = new JRadioButton("Number of points in full range: ", false);
		perRangeRadioButton.setFont(Fonts.textFont);
		perRangeRadioButton.addItemListener(this);
		
		buttonGroup2 = new ButtonGroup();
		
		buttonGroup2.add(perDecadeRadioButton);
		buttonGroup2.add(perRangeRadioButton);
		
		tempMaxField = new JTextField(5);
		
		tempMinField = new JTextField(5);
		
		perDecadeField = new JTextField(5);
		
		perRangeField = new JTextField(5);
		perRangeField.setEditable(false);
		
		resetButton = new JButton("Reset");
		resetButton.setFont(Fonts.buttonFont);
		resetButton.addActionListener(this);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		gridTypePanel = new JPanel(new GridBagLayout());
		gridRangePanel = new JPanel(new GridBagLayout());
		gridPointsPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gridTypePanel.add(gridTypeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gridTypePanel.add(logRadioButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gridTypePanel.add(linRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gridRangePanel.add(gridRangeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gridRangePanel.add(tempMinLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gridRangePanel.add(tempMinField, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		
		gridRangePanel.add(tempMaxLabel, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		
		gridRangePanel.add(tempMaxField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gridPointsPanel.add(gridPointsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gridPointsPanel.add(perDecadeRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		gridPointsPanel.add(perDecadeField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gridPointsPanel.add(perRangeRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		gridPointsPanel.add(perRangeField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		buttonPanel.add(resetButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		buttonPanel.add(submitButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		c.add(gridTypePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		c.add(gridRangePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		c.add(gridPointsPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		c.add(buttonPanel, gbc);
		
		addWindowListener(this);
		
		
		
		c.validate();
	
	}
	
	/**
	 * Sets the label state.
	 */
	public void setLabelState(){
	
		if(ds.getTempUnits().equals("T9")){
		
			tempMaxLabel.setText("Temp max (<=10.0 T9): ");
			tempMinLabel.setText("Temp min (>=0.01 T9): ");
		
		}else if(ds.getTempUnits().equals("T8")){
		
			tempMaxLabel.setText("Temp max (<=100.0 T8): ");
			tempMinLabel.setText("Temp min (>=0.1 T8): ");
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==resetButton){
			
			logRadioButton.setSelected(true);
			linRadioButton.setSelected(false);
			
			tempMinField.setText("");
			tempMaxField.setText("");
			
			perDecadeRadioButton.setSelected(true);
			perDecadeField.setEditable(true);
			perDecadeField.setText("");
		
			perRangeRadioButton.setSelected(false);
			perRangeField.setEditable(false);
			perRangeField.setText("");
		
		}else if(ae.getSource()==submitButton){
		
			try{
		
				double tempMin = Double.valueOf(tempMinField.getText()).doubleValue();
				double tempMax = Double.valueOf(tempMaxField.getText()).doubleValue();
				
				if(perDecadeRadioButton.isSelected()){
			
					int numPoints = Integer.valueOf(perDecadeField.getText()).intValue();
					
				}else if(perRangeRadioButton.isSelected()){
				
					int numPoints = Integer.valueOf(perRangeField.getText()).intValue();
				
				}
				
				if(ds.getTempUnits().equals("T9")){
			
					if(tempMin<0.01){
						
						String string = "Temperature minimum must be greater than 0.01 T9.";
					
						Dialogs.createExceptionDialog(string, this);
						
					}else if(tempMax>10.0){
						
						String string = "Temperature maximum must be less than 10.0 T9.";
					
						Dialogs.createExceptionDialog(string, this);
						
					}else if(tempMin>=tempMax){
				
						String string = "Temperature minimum must be greater than temperature maximum.";
					
						Dialogs.createExceptionDialog(string, this);
				
					}else{
				
						Cina.rateManFrame.rateManRateGrid1Panel.gridTextArea.setText(calculateTempGrid());
						
						Cina.rateManFrame.rateManRateGrid1Panel.filenameField.setText("Custom Grid");
						
						Cina.rateManFrame.rateManRateGrid1Panel.unitsComboBox.setEnabled(true);
			
						setVisible(false);
						
						dispose();
					
					}
				
				}else if(ds.getTempUnits().equals("T8")){
			
					if(tempMin<0.1){
						
						String string = "Temperature minimum must be greater than 0.1 T8.";
					
						Dialogs.createExceptionDialog(string, this);
						
					}else if(tempMax>100.0){
						
						String string = "Temperature maximum must be less than 100.0 T8.";
					
						Dialogs.createExceptionDialog(string, this);
						
					}else if(tempMin>=tempMax){
				
						String string = "Temperature minimum must be greater than temperature maximum.";
					
						Dialogs.createExceptionDialog(string, this);
				
					}else{
				
						Cina.rateManFrame.rateManRateGrid1Panel.gridTextArea.setText(calculateTempGrid());
						
						Cina.rateManFrame.rateManRateGrid1Panel.filenameField.setText("Custom Grid");
						
						Cina.rateManFrame.rateManRateGrid1Panel.unitsComboBox.setEnabled(true);
			
						setVisible(false);
						
						dispose();
					
					}
				
				}
			
			}catch(NumberFormatException nfe){
			
				String string = "All fields must contain numeric values.";
				
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}
	
	}
	
	/**
	 * Calculate temp grid.
	 *
	 * @return the string
	 */
	public String calculateTempGrid(){
	
		String string = "";
	
		double tempMin = Double.valueOf(tempMinField.getText()).doubleValue();
		double tempMax = Double.valueOf(tempMaxField.getText()).doubleValue();
		
		if(logRadioButton.isSelected()){
		
			if(perDecadeRadioButton.isSelected()){
			
				int numPoints = Integer.valueOf(perDecadeField.getText()).intValue();
			
				double[][] decadesArray = getDecades(tempMin, tempMax);
				
				int numDecades = decadesArray.length;

				double[][] tempGrid = new double[numDecades][numPoints + 1];

				for(int i=0; i<numDecades; i++){
				
					double decadeTempMin = decadesArray[i][0];
					double decadeTempMax = decadesArray[i][1];
				
					double logIncrement = (log10*Math.log(decadeTempMax) - log10*Math.log(decadeTempMin))/(numPoints-1);

					for(int j=1; j<=numPoints; j++){
					
						double power = (log10*Math.log(decadeTempMin)) + ((j-1)*logIncrement);
					
						tempGrid[i][j] = Math.pow(10, power);
					
					}
				
				}
				
				for(int i=0; i<numDecades; i++){
				
					for(int j=1; j<=numPoints; j++){
					
						if(numDecades>1 && j==numPoints && i!=numDecades-1){
					
							//do nothing
						
						}else if(tempGrid[i][j]>=tempMin && tempGrid[i][j]<=tempMax){
						
							string = string + NumberFormats.getFormattedNumber(tempGrid[i][j]) + "\n";
						
						}
					
					}
				
				}
				
				boolean addTempMin = false;
				boolean addTempMax = false;
				
				if(tempGrid[0][1]!=tempMin){
				
					addTempMin = true;
				
				}
				
				if(tempGrid[numDecades-1][numPoints]!=tempMax){
				
					addTempMax = true;
				
				}
				
				if(addTempMin && !addTempMax){
				
					string = NumberFormats.getFormattedNumber(tempMin) + "\n" + string;
				
				}else if(!addTempMin && addTempMax){
				
					string = string + NumberFormats.getFormattedNumber(tempMax);
				
				}else if(addTempMin && addTempMax){
				
					string = NumberFormats.getFormattedNumber(tempMin) + "\n" + string + NumberFormats.getFormattedNumber(tempMax);
				
				}
				
			}else if(perRangeRadioButton.isSelected()){
			
				int numPoints = Integer.valueOf(perRangeField.getText()).intValue();
				
				double[] tempGrid = new double[numPoints + 1];
				
				double logIncrement = (log10*Math.log(tempMax) - log10*Math.log(tempMin))/(numPoints-1);
				
				for(int i=1; i<=numPoints; i++){
		
					double power = (log10*Math.log(tempMin)) + ((i-1)*logIncrement);
				
					tempGrid[i] = Math.pow(10, power);
					
					string = string + NumberFormats.getFormattedNumber(tempGrid[i]) + "\n";
				
				}
		
			}
			
		}else if(linRadioButton.isSelected()){
		
			if(perDecadeRadioButton.isSelected()){
			
				int numPoints = Integer.valueOf(perDecadeField.getText()).intValue();
			
				double[][] decadesArray = getDecades(tempMin, tempMax);
				
				int numDecades = decadesArray.length;

				double[][] tempGrid = new double[numDecades][numPoints + 1];

				for(int i=0; i<numDecades; i++){
				
					double decadeTempMin = decadesArray[i][0];
					double decadeTempMax = decadesArray[i][1];
				
					double linIncrement = (decadeTempMax - decadeTempMin)/(numPoints-1);
				
					for(int j=1; j<=numPoints; j++){
					
						tempGrid[i][j] = decadeTempMin + (j-1)*linIncrement;
					
					}
				
				}
				
				for(int i=0; i<numDecades; i++){
				
					for(int j=1; j<=numPoints; j++){
					
						if(numDecades>1 && j==numPoints && i!=numDecades-1){
					
							//do nothing
						
						}else if(tempGrid[i][j]>=tempMin && tempGrid[i][j]<=tempMax){
						
							string = string + NumberFormats.getFormattedNumber(tempGrid[i][j]) + "\n";
						
						}
					
					}
				
				}
				
				boolean addTempMin = false;
				boolean addTempMax = false;
				
				if(tempGrid[0][1]!=tempMin){
				
					addTempMin = true;
				
				}
				
				if(tempGrid[numDecades-1][numPoints]!=tempMax){
				
					addTempMax = true;
				
				}
				
				if(addTempMin && !addTempMax){
				
					string = NumberFormats.getFormattedNumber(tempMin) + "\n" + string;
				
				}else if(!addTempMin && addTempMax){
				
					string = string + NumberFormats.getFormattedNumber(tempMax);
				
				}else if(addTempMin && addTempMax){
				
					string = NumberFormats.getFormattedNumber(tempMin) + "\n" + string + NumberFormats.getFormattedNumber(tempMax);
				
				}

			}else if(perRangeRadioButton.isSelected()){
			
				int numPoints = Integer.valueOf(perRangeField.getText()).intValue();
				
				double[] tempGrid = new double[numPoints + 1];
				
				double linIncrement = (tempMax - tempMin)/(numPoints-1);
				
				for(int i=1; i<=numPoints; i++){

					tempGrid[i] = tempMin + (i-1)*linIncrement;
					
					string = string + NumberFormats.getFormattedNumber(tempGrid[i]) + "\n";
				
				}

			}
			
		}
	
		return string;
	
	}
	
	/**
	 * Gets the decades.
	 *
	 * @param tempMin the temp min
	 * @param tempMax the temp max
	 * @return the decades
	 */
	public double[][] getDecades(double tempMin, double tempMax){
	
		int numDecades = 0;

		double[][] decadeArray = new double[3][2];

		if(ds.getTempUnits().equals("T9")){
		
			decadeArray = decadeArrayT9; 
							
		}else if(ds.getTempUnits().equals("T8")){
		
			decadeArray = decadeArrayT8; 
							
		} 
									
		int startingDecadeIndex = 0;
		int endingDecadeIndex = 0;

		for(int i=0; i<3; i++){
		
			if((tempMin>decadeArray[i][0] && tempMin<decadeArray[i][1])
					|| tempMin==decadeArray[i][0]){
			
				startingDecadeIndex = i;

			}
		}
		
		for(int i=0; i<3; i++){
		
			if((tempMax>decadeArray[i][0] && tempMax<decadeArray[i][1])
					|| tempMax==decadeArray[i][1]){
			
				endingDecadeIndex = i;

			}
		
		}
		
		
		numDecades = endingDecadeIndex - startingDecadeIndex + 1;
		
		double[][] newDecadeArray = new double[numDecades][2];
		
		for(int i=0; i<numDecades; i++){
		
			newDecadeArray[i] = decadeArray[startingDecadeIndex + i];			
		
		}
		
		return newDecadeArray;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(perDecadeRadioButton.isSelected()){
		
			perDecadeField.setEditable(true);	
			perRangeField.setEditable(false);
			
		}else if(perRangeRadioButton.isSelected()){
		
			perDecadeField.setEditable(false);	
			perRangeField.setEditable(true);
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {
		
		Cina.rateManFrame.rateManRateGrid1Panel.unitsComboBox.setEnabled(true);
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