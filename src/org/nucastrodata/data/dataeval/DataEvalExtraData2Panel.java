package org.nucastrodata.data.dataeval;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.print.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataEvalDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.dataeval.DataEvalExtraDataPlotPanel;
import org.nucastrodata.data.dataeval.DataEvalExtraDataPlotPrintable;
import org.nucastrodata.data.dataeval.DataEvalExtraDataTableFrame;


//This class creates the content for the second panel of the about rate sub feature of the rate man feature
/**
 * The Class DataEvalExtraData2Panel.
 */
public class DataEvalExtraData2Panel extends WizardPanel implements ActionListener, ItemListener, ChangeListener{

	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;

	/** The log10. */
	double log10 = 0.434294482;

	//Declare panels
	/** The right panel. */
	JPanel mainPanel, buttonPanel, formatPanel, scalePanel, setScalePanel, scaleTitlePanel, rightPanel;

	/** The data eval extra data plot panel. */
	DataEvalExtraDataPlotPanel dataEvalExtraDataPlotPanel;

	/** The apply der button. */
	JButton saveButton, printButton, tableButton, extraButton, saveNucDataButton
			, applyButton, okButton, okButton2, cancelButton, yesButton, noButton, yesButton2, noButton2
			, applyDerButton;
    
    /** The energy combo box. */
    JComboBox energyComboBox;
    
    /** The curve2 box. */
    JCheckBox curve1Box, curve2Box;
    
    /** The sp. */
    JScrollPane sp;
    
	/** The max thres label. */
	JLabel curveLabel, curveNameLabel, rateLabel, reactionLabel, plotControlsLabel, energyLabel, lowELabel, highELabel
				, highESliderLabel, lowESliderLabel, curveBoxLabel, numPointsHighELabel, numPointsLowELabel, numPointsHighESliderLabel
				, numPointsLowESliderLabel, maxThresLabel;

	/** The num points low e slider. */
	JSlider highESlider, lowESlider, numPointsHighESlider, numPointsLowESlider;

	/** The max thresh field. */
	JTextField lowEField, highEField, numPointsHighEField, numPointsLowEField, maxThreshField;

	////PLOTTER CONTROLS//////////////////////////////////////////////////////////////////////////
	/** The Ymax combo box. */
	JComboBox YminComboBox, YmaxComboBox;

	/** The label array. */
	JLabel[] labelArray = new JLabel[7];
	
	/** The string array. */
	String[] stringArray = {"Autoscale E axis"
								,"Set Scale"
								, "Enter E min: " 
								, "Enter E max: "
								, "log Cross Sec min: "
								, "log Cross Sec max: "
								, "log S Factor min: "
								, "log S Factor max: "
								, "to max and min?"};

	/** The minor x box. */
	JCheckBox majorYBox, majorXBox, minorYBox, minorXBox;
	
	/** The Emax field. */
	JTextField EminField, EmaxField;
	
	/** The Ymin combo box init. */
	int YminComboBoxInit = 0;
    
    /** The Ymax combo box init. */
    int YmaxComboBoxInit = 0;

	/** The nuc data modify dialog. */
	JDialog saveNucDataDialog, exceptionDialog, cautionDialog, nucDataExistsDialog, nucDataMergeDialog, nucDataModifyDialog;

	/** The nuc data set combo box. */
	JComboBox nucDataSetComboBox;
	
	/** The new nuc data set field. */
	JTextField newNucDataSetField;
	
	//Array holding total number of reactants (input particles) vs. reaction type
	/** The number reactants. */
	int[] numberReactants = {0, 1, 1, 1, 2, 2, 2, 2, 3};
	
	//Array holding total number of products (output particles) vs. reaction type
	/** The number products. */
	int[] numberProducts = {0, 1, 2, 3, 1, 2, 3, 4, 2};

	/** The ds. */
	private DataEvalDataStructure ds;

	//Constructor					
	/**
	 * Instantiates a new data eval extra data2 panel.
	 *
	 * @param ds the ds
	 */
	public DataEvalExtraData2Panel(DataEvalDataStructure ds){
	
		this.ds = ds;
	
		//Set the current panel index to 1 in the parent frame
		Cina.dataEvalFrame.setCurrentFeatureIndex(3);
		Cina.dataEvalFrame.setCurrentPanelIndex(2);
		
		//Create main panel
		mainPanel = new JPanel(new BorderLayout());
		
		//Instantiate gbc
		gbc = new GridBagConstraints();
		
		addWizardPanel("Nuclear Data Evaluator's Toolkit", "Cross Section Extrapolator", "2", "2");
		
		//BUTTONS/////////////////////////////////////////////////////////////////BUTTONS////////////////////////
		saveButton = new JButton("Save Plot");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);
		
		extraButton = new JButton("Extrapolate Curve");
		extraButton.setFont(Fonts.buttonFont);
		extraButton.addActionListener(this);
		
		if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			saveNucDataButton = new JButton("Save Cross Section");
		
		}else if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			saveNucDataButton = new JButton("Save S Factor");
		
		}
		
		saveNucDataButton.setFont(Fonts.buttonFont);
		saveNucDataButton.addActionListener(this);
		saveNucDataButton.setEnabled(false);
		
		applyButton = new JButton("Apply Energy Range");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		applyDerButton = new JButton("Apply Maximum Allowed Change");
		applyDerButton.setFont(Fonts.buttonFont);
		applyDerButton.addActionListener(this);
		
		//COMBOBOXES///////////////////////////////////////////////////////////////COMBOBOXES//////////////////////
		energyComboBox = new JComboBox();
		energyComboBox.setFont(Fonts.textFont);
		energyComboBox.addItem("High Energy");
		energyComboBox.addItem("Low Energy");
		energyComboBox.addItemListener(this);
		
		YminComboBox = new JComboBox();
        YminComboBox.setFont(Fonts.textFont);
        for(int i=19; i>=-20; i--) { YminComboBox.addItem(Integer.toString(i));}
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=20; i>=-19; i--) { YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.addItemListener(this);
		
		EminField = new JTextField(5);
		EmaxField = new JTextField(5);

		ds.setExtraXmax(setXmax());
		ds.setExtraXmin(setXmin());

		EminField.setText(String.valueOf(ds.getExtraXmin()));
		EmaxField.setText(String.valueOf(ds.getExtraXmax()));

		//CHECKBOXES///////////////////////////////////////////////////////////////CHECKBOXES/////////////////////
		curve1Box = new JCheckBox("Display Original Curve");
		curve1Box.setFont(Fonts.textFont);
		curve1Box.setSelected(true);
		curve1Box.addItemListener(this);
		
		curve2Box = new JCheckBox("Display Extrapolated Curve");
		curve2Box.setFont(Fonts.textFont);
		curve2Box.setSelected(false);
		curve2Box.setEnabled(false);
		curve2Box.addItemListener(this);
		
		//LABELS////////////////////////////////////////////////////////////////////LABELS///////////////////////
		curveLabel = new JLabel("Curve: ");
		curveLabel.setFont(Fonts.textFont);
		
		curveNameLabel = new JLabel();
		curveNameLabel.setFont(Fonts.textFont);
		
		reactionLabel = new JLabel("Reaction: ");
		
		plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		labelArray[0] = new JLabel(stringArray[0] + " " + stringArray[6]);
		labelArray[0].setFont(Fonts.textFont);
		
		labelArray[1] = new JLabel(stringArray[1]);
		labelArray[1].setFont(Fonts.textFont);
		
		labelArray[2] = new JLabel(stringArray[2]);
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3] = new JLabel(stringArray[3]);
		labelArray[3].setFont(Fonts.textFont);

		if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			labelArray[4] = new JLabel(stringArray[4]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[5]);
			labelArray[5].setFont(Fonts.textFont);
			
		}else if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			labelArray[4] = new JLabel(stringArray[6]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[7]);
			labelArray[5].setFont(Fonts.textFont);
			
		}
		
		energyLabel = new JLabel("Choose Extrapolation Type :");
		energyLabel.setFont(Fonts.textFont);
		
		highELabel = new JLabel("New High Energy Value :");
		highELabel.setFont(Fonts.textFont);
		
		lowELabel = new JLabel("New Low Energy Value :");
		lowELabel.setFont(Fonts.textFont);
		
		highESliderLabel = new JLabel("Use slider to set new high energy value :");
		highESliderLabel.setFont(Fonts.textFont);
		
		lowESliderLabel = new JLabel("Use slider to set new low energy value :");
		lowESliderLabel.setFont(Fonts.textFont);
		
		curveBoxLabel = new JLabel("Choose curves for plot below :");
		curveBoxLabel.setFont(Fonts.textFont);
		
		numPointsHighELabel = new JLabel("<html>Number of points determining<p>high energy extrapolation :</html>");
		numPointsHighELabel.setFont(Fonts.textFont);
		
		numPointsLowELabel = new JLabel("<html>Number of points determining<p>low energy extrapolation :</html>");
		numPointsLowELabel.setFont(Fonts.textFont);
		
		numPointsHighESliderLabel = new JLabel("Use slider to set number of points :");
		numPointsHighESliderLabel.setFont(Fonts.textFont);
		
		numPointsLowESliderLabel = new JLabel("<html>Use slider to set number of points :</html>");
		numPointsLowESliderLabel.setFont(Fonts.textFont);
		
		maxThresLabel = new JLabel("<html>Maximum allowed change in the<p>derivative at high energy value :</html>");
		maxThresLabel.setFont(Fonts.textFont);
		
		//SLIDERS////////////////////////////////////////////////////////////////////SLIDERS///////////////////////
		highESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		highESlider.addChangeListener(this);
		
		lowESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		lowESlider.addChangeListener(this);
		
		numPointsHighESlider = new JSlider(JSlider.HORIZONTAL
												, 2
												, ds.getExtra1NucDataDataStructure().getNumberPoints()-1
												, 2);
												
		numPointsHighESlider.addChangeListener(this);
		numPointsHighESlider.setSnapToTicks(true);
		
		numPointsLowESlider = new JSlider(JSlider.HORIZONTAL
												, 2
												, ds.getExtra1NucDataDataStructure().getNumberPoints()-1
												, 2);
												
		numPointsLowESlider.addChangeListener(this);
		numPointsLowESlider.setSnapToTicks(true);
		
		//FIELDS/////////////////////////////////////////////////////////////////////FIELDS///////////////////////
		highEField = new JTextField(8);
		highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
		
		lowEField = new JTextField(8);
		lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
		
		numPointsHighEField  = new JTextField(3);
		numPointsHighEField.setEditable(false);
		numPointsHighEField.setText("1");
		
		numPointsLowEField  = new JTextField(3);
		numPointsLowEField.setEditable(false);
		numPointsLowEField.setText("1");
		
		maxThreshField = new JTextField(5);
		maxThreshField.setText("0.20");
		
		//PANELS/////////////////////////////////////////////////////////////////////PANELS///////////////////////
		buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(extraButton);
		buttonPanel.add(saveNucDataButton);
		
		//PLOT///////////////////////////////////////////////////////////////////////PLOT///////////////////////////
		dataEvalExtraDataPlotPanel = new DataEvalExtraDataPlotPanel(YminComboBoxInit, YmaxComboBoxInit, ds);
		
		dataEvalExtraDataPlotPanel.setPreferredSize(dataEvalExtraDataPlotPanel.getSize());
		
		dataEvalExtraDataPlotPanel.revalidate();
		
		sp = new JScrollPane(dataEvalExtraDataPlotPanel);

		dataEvalExtraDataPlotPanel.setPreferredSize(dataEvalExtraDataPlotPanel.getSize());
		
		dataEvalExtraDataPlotPanel.revalidate();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		setScalePanel = new JPanel(new GridBagLayout());
		
		scaleTitlePanel = new JPanel(new GridBagLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		scaleTitlePanel.add(plotControlsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[2], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EminField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[3], gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EmaxField, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 5, 2, 0);
		setScalePanel.add(applyButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[4], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(YminComboBox, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[5], gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(YmaxComboBox, gbc);
	
		formatPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		formatPanel.add(scaleTitlePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		formatPanel.add(setScalePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		formatPanel.add(buttonPanel, gbc);
	
		//RIGHT PANEL/////////////////////////////////////////////////////////////////////RIGHT PANEL//////////////////
		rightPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		rightPanel.add(reactionLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(curveLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(curveNameLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(energyLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(energyComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(highELabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(highEField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(highESliderLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(highESlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(numPointsHighELabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(numPointsHighEField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(numPointsHighESliderLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(numPointsHighESlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(maxThresLabel, gbc);
	
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(maxThreshField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(applyDerButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 13;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(curveBoxLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.gridwidth = 2;
		rightPanel.add(curve1Box, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 15;
		gbc.gridwidth = 2;
		rightPanel.add(curve2Box, gbc);
		
		mainPanel.add(sp, BorderLayout.CENTER);
		
		mainPanel.add(rightPanel, BorderLayout.EAST);
	
		mainPanel.add(formatPanel, BorderLayout.SOUTH);
	
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}

	/**
	 * Gets the high e slider value.
	 *
	 * @return the high e slider value
	 */
	public double getHighESliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
	
		min = ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1];
		
		temp = min + (((double)highESlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Gets the low e slider value.
	 *
	 * @return the low e slider value
	 */
	public double getLowESliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = ds.getExtra1NucDataDataStructure().getXDataArray()[0];
	
		min = Double.valueOf(EminField.getText()).doubleValue();
		
		temp = min + (((double)lowESlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Sets the initial ymin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialYminComboBoxInit(){
	
		int min = 0;
		
		double currentYmin = 0.0;
		
		currentYmin = ds.getExtraYmin();
	
		min = (int)Math.floor(0.434294482*Math.log(currentYmin));
	
		if(min < -20){
		
			min = -20;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initial ymax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialYmaxComboBoxInit(){
	
		int max = 0;
		
		double currentYmax = 0.0;
		
		currentYmax = ds.getExtraYmax();
	
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 20){
		
			max = 20;
		
		}

		return max;
	
	}

	/**
	 * Gets the slope array.
	 *
	 * @return the slope array
	 */
	public double[] getSlopeArray(){
		
		double[] slopeArray = new double[ds.getExtra1NucDataDataStructure().getNumberPoints()-1];
	
		for(int i=0; i<slopeArray.length; i++){
		
			double lowXPoint = ds.getExtra1NucDataDataStructure().getXDataArray()[i];
			double highXPoint = ds.getExtra1NucDataDataStructure().getXDataArray()[i+1];
		
			double lowYPoint = log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[i]);
			double highYPoint = log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[i+1]);
			
			slopeArray[i] = (highYPoint - lowYPoint)/(highXPoint - lowXPoint);	
		
		}
	
		return slopeArray;
	
	}

	/**
	 * Gets the number high e points max.
	 *
	 * @return the number high e points max
	 */
	public int getNumberHighEPointsMax(){
	
		int maxPoints = 0;
		
		maxPoints = Math.min(getMaxHighESlopePoints(), getMaxHighEDerPoints());
		
		return maxPoints;
	
	}

	/**
	 * Gets the max high e slope points.
	 *
	 * @return the max high e slope points
	 */
	public int getMaxHighESlopePoints(){
	
		int maxPoints = 2;
		
		String slopeSign = "";
		
		if(ds.getSlopeArray()[ds.getSlopeArray().length-1]>0){
		
			slopeSign = "+";
		
		}else{
		
			slopeSign = "-";
		
		}
		
		foundMaxPoints:
		for(int i=ds.getSlopeArray().length-1; i>=0; i--){
		
			String tempSlopeSign = "";
		
			if(ds.getSlopeArray()[i]>0){
			
				tempSlopeSign = "+";
			
			}else{
			
				tempSlopeSign = "-";
			
			}
			
			if(tempSlopeSign.equals(slopeSign)){
			
				maxPoints++;
			
			}else{
			
				break foundMaxPoints;
			
			}
		
		}
		
		return maxPoints;
	
	}

	/**
	 * Gets the max high e der points.
	 *
	 * @return the max high e der points
	 */
	public int getMaxHighEDerPoints(){
	
		int maxPoints = 2;
		
		foundMaxPoints:
		for(int i=ds.getSlopeArray().length-1; i>0; i--){
		
			double delDer = (ds.getSlopeArray()[ds.getSlopeArray().length-1] 
								- ds.getSlopeArray()[i-1])
								/ds.getSlopeArray()[ds.getSlopeArray().length-1];
			
			if(Math.abs(delDer)<=Double.valueOf(maxThreshField.getText()).doubleValue()){
			
				maxPoints++;
			
			}else{
			
				break foundMaxPoints;
			
			}
			
		}
		
		return maxPoints;
	
	}

	/**
	 * Gets the number low e points max.
	 *
	 * @return the number low e points max
	 */
	public int getNumberLowEPointsMax(){
	
		int maxPoints = 0;
		
		maxPoints = Math.min(getMaxLowESlopePoints(), getMaxLowEDerPoints());
		
		return maxPoints;
	
	}

	/**
	 * Gets the max low e slope points.
	 *
	 * @return the max low e slope points
	 */
	public int getMaxLowESlopePoints(){
	
		int maxPoints = 2;
		
		String slopeSign = "";
		
		if(ds.getSlopeArray()[0]>0){
		
			slopeSign = "+";
		
		}else{
		
			slopeSign = "-";
		
		}
		
		foundMaxPoints:
		for(int i=0; i<ds.getSlopeArray().length; i++){
		
			String tempSlopeSign = "";
		
			if(ds.getSlopeArray()[i]>0){
			
				tempSlopeSign = "+";
			
			}else{
			
				tempSlopeSign = "-";
			
			}
			
			if(tempSlopeSign.equals(slopeSign)){
			
				maxPoints++;
			
			}else{
			
				break foundMaxPoints;
			
			}
		
		}
		
		return maxPoints;
	
	}

	/**
	 * Gets the max low e der points.
	 *
	 * @return the max low e der points
	 */
	public int getMaxLowEDerPoints(){
	
		int maxPoints = 2;
		
		foundMaxPoints:
		for(int i=0; i<ds.getSlopeArray().length-1; i++){
		
			double delDer = (ds.getSlopeArray()[0] 
								- ds.getSlopeArray()[i+1])
								/ds.getSlopeArray()[0];
			
			if(Math.abs(delDer)<=Double.valueOf(maxThreshField.getText()).doubleValue()){
			
				maxPoints++;
			
			}else{
			
				break foundMaxPoints;
			
			}
			
		}
		
		return maxPoints;
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==curve1Box
			|| ie.getSource()==curve2Box){
		
			redrawPlot();
		
		}else if(ie.getSource()==YminComboBox || ie.getSource()==YmaxComboBox){
		
			if(Integer.valueOf(YminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(YmaxComboBox.getSelectedItem().toString()).doubleValue()){
       			
       			String string = "";
       			
       			if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){
       			
       				string = "Cross section minimum must be less than cross section maximum.";
       			
       			}else if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("S(E)")){
       			
       				string = "S factor minimum must be less than S factor maximum.";
       			
       			}
       				
       			createExceptionDialog(string, Cina.dataEvalFrame);
       			
       			YminComboBox.removeItemListener(this);
       			YminComboBox.setSelectedItem("-10");
       			YminComboBox.addItemListener(this);
       			
       			redrawPlot();
       			
			}else{
			
				redrawPlot();
			
			}
			
		}else if(ie.getSource()==nucDataSetComboBox){
		
			if(((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
			
				newNucDataSetField.setEditable(true);
			
			}else{
			
				newNucDataSetField.setEditable(false);
			
			}
		
		}else if(ie.getSource()==energyComboBox){
				
			setRightPanelFormat((String)energyComboBox.getSelectedItem());
			
			double EmaxValue = Double.valueOf(EmaxField.getText()).doubleValue();
			double EminValue = Double.valueOf(EminField.getText()).doubleValue();
			
			ds.getExtra2NucDataDataStructure().setNumberPoints(0);
			
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
			
				if(EmaxValue<=ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]){
						
					highESlider.setEnabled(false);
					highEField.setText("NOT IN DOMAIN");
				
					dataEvalExtraDataPlotPanel.setHighE(false);
				
				}else if(EmaxValue>ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]){
				
					highESlider.setEnabled(true);
					highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
	
					dataEvalExtraDataPlotPanel.setHighE(true);
	
				}
			
			}
			
			if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
			
				if(EminValue>=ds.getExtra1NucDataDataStructure().getXDataArray()[0]){
				
					lowESlider.setEnabled(false);
					lowEField.setText("NOT IN DOMAIN");
				
					dataEvalExtraDataPlotPanel.setLowE(false);
				
				}else if(EminValue<ds.getExtra1NucDataDataStructure().getXDataArray()[0]){
				
					lowESlider.setEnabled(true);
					lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
	
					dataEvalExtraDataPlotPanel.setLowE(true);
	
				}
			
			}
			
			curve2Box.setSelected(false);
			curve2Box.setEnabled(false);
			
			saveNucDataButton.setEnabled(false);
			
			
			
			redrawPlot();
			
			validate();
		
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==highESlider){

			highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));

			redrawPlot();

		}else if(ce.getSource()==lowESlider){
		
			lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
		
			redrawPlot();
		
		}else if(ce.getSource()==numPointsHighESlider){
		
			numPointsHighEField.setText(String.valueOf(numPointsHighESlider.getValue()));
		
			redrawPlot();
		
		}else if(ce.getSource()==numPointsLowESlider){
		
			numPointsLowEField.setText(String.valueOf(numPointsLowESlider.getValue()));
			
			redrawPlot();
			
		}
	
	}

	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButton){
		
			PlotSaver.savePlot(dataEvalExtraDataPlotPanel, this);
		
		}else if(ae.getSource()==printButton){
		
			PlotPrinter.print(new DataEvalExtraDataPlotPrintable(), Cina.dataEvalFrame);
		
		}else if(ae.getSource()==tableButton){
		
			if(!noneChecked()){
       	
	       		if(Cina.dataEvalFrame.dataEvalExtraDataTableFrame==null){
	       	
	       			Cina.dataEvalFrame.dataEvalExtraDataTableFrame = new DataEvalExtraDataTableFrame(ds);
	       		
		       	}else{
		       	
		       		Cina.dataEvalFrame.dataEvalExtraDataTableFrame.setTableText();
		       		Cina.dataEvalFrame.dataEvalExtraDataTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{

	       		String string = "Please select curves for the Table of Points from the checkbox list."; 
	       		
	       		createExceptionDialog(string, Cina.dataEvalFrame);
	       		
	       	}
		
		}else if(ae.getSource()==extraButton){
		
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
		
				if(goodHighEValue()){
					
					createHighEExtraCurve();
				
				}
		
			}else if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
		
				if(goodLowEValue()){
					
					createLowEExtraCurve();
					
				}
		
			}
		
		}else if(ae.getSource()==applyDerButton){
		
			ds.setNumberHighEPointsMax(getNumberHighEPointsMax());
			ds.setNumberLowEPointsMax(getNumberLowEPointsMax());
	
			numPointsHighESlider.setMaximum(ds.getNumberHighEPointsMax());
			numPointsHighESlider.setMinimum(2);
	
			numPointsLowESlider.setMaximum(ds.getNumberLowEPointsMax());
			numPointsLowESlider.setMinimum(2);
			
			if(numPointsHighESlider.getMaximum()==2){
		
				numPointsHighESlider.setEnabled(false);
		
			}else{
			
				numPointsHighESlider.setEnabled(true);
			
			}
	
			if(numPointsLowESlider.getMaximum()==2){
			
				numPointsLowESlider.setEnabled(false);
			
			}else{
			
				numPointsLowESlider.setEnabled(true);
			
			}
			
			redrawPlot();
		
		}else if(ae.getSource()==saveNucDataButton){
		
			ds.setNucDataSetGroup("PUBLIC");
			boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
			
			ds.setNucDataSetGroup("SHARED");
			boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
			
			ds.setNucDataSetGroup("USER");
			boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET NUC DATA SET LIST", Cina.dataEvalFrame);
			
			if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
			
				createSaveNucDataDialog(Cina.dataEvalFrame);
			
			}
		
		}else if(ae.getSource()==okButton2){
		
			exceptionDialog.setVisible(false);
			exceptionDialog.dispose();

		}else if(ae.getSource()==applyButton){
			
			double EmaxValue = Double.valueOf(EmaxField.getText()).doubleValue();
			double EminValue = Double.valueOf(EminField.getText()).doubleValue();
			
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
			
				if(EmaxValue<=ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]){
						
					highESlider.setEnabled(false);
					highEField.setText("NOT IN DOMAIN");
				
					dataEvalExtraDataPlotPanel.setHighE(false);
				
				}else if(EmaxValue>ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]){
				
					highESlider.setEnabled(true);
					highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
	
					dataEvalExtraDataPlotPanel.setHighE(true);
	
				}
			
			}
			
			if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
			
				if(EminValue>=ds.getExtra1NucDataDataStructure().getXDataArray()[0]){
				
					lowESlider.setEnabled(false);
					lowEField.setText("NOT IN DOMAIN");
				
					dataEvalExtraDataPlotPanel.setLowE(false);
				
				}else if(EminValue<ds.getExtra1NucDataDataStructure().getXDataArray()[0]){
				
					lowESlider.setEnabled(true);
					lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
	
					dataEvalExtraDataPlotPanel.setLowE(true);
	
				}
			
			}
						
			redrawPlot();
			
		}else if(ae.getSource()==cancelButton){
			
			saveNucDataDialog.setVisible(false);
			saveNucDataDialog.dispose();
			
		}else if(ae.getSource()==okButton){
			
			if(ds.getNumberUserNucDataSetDataStructures()!=0){
			
				if(!checkNewNucDataSetField() && ((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					createExceptionDialog(string, Cina.dataEvalFrame);
				
				}else if(((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
					
					if(checkPublicNucDataSetName()){
					
						if(checkOverwriteNucDataSetName()){

							ds.setDestNucDataSetName(newNucDataSetField.getText());
							ds.setSourceNucDataSet("");
							ds.setDestNucDataSetGroup("USER");
							ds.setNucData("");
							ds.setProperties(getNucDataProperties());
							ds.setDeleteSourceNucDataSet("NO");
							ds.setDeleteNucData("NO");
							
							if(Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA SET", Cina.dataEvalFrame)
									&& Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA", Cina.dataEvalFrame)){
								
								saveNucDataDialog.setVisible(false);
								saveNucDataDialog.dispose();
								
								String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataReport();
										
								createNucDataMergeDialog(string, Cina.dataEvalFrame);
							
							}
						
						}else{
										
							String string = "You have specified the name of an existing nuclear data set. Do you want to overwrite this nuclear data set?";
					
							createCautionDialog(string, Cina.dataEvalFrame);
						
						}
						
					}else{
					
						String string = "You have specified the name of a Public or a Shared nuclear data set. These can not overwritten.";
				
						createExceptionDialog(string, Cina.dataEvalFrame);
					
					}
							
				}else if(!((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
				
					ds.setDestNucDataSetName((String)nucDataSetComboBox.getSelectedItem());
					ds.setNucData(getCurrentNucDataID());

					if(Cina.cinaCGIComm.doCGICall("NUC DATA EXIST", Cina.dataEvalFrame)){
					
						if(!ds.getCurrentNucDataExists()){
			
							ds.setNucData("");
							ds.setProperties(getNucDataProperties());
							ds.setDeleteNucData("NO");
						
							if(Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA", Cina.dataEvalFrame)){
		
								saveNucDataDialog.setVisible(false);
								saveNucDataDialog.dispose();
		
								String string = ds.getModifyNucDataReport();
								createNucDataModifyDialog(string, Cina.dataEvalFrame);
							
							}
							
						}else{
						
							String string = "This nuclear data already exists in the nuclear data set " + newNucDataSetField.getText() + ". Do you want to overwrite this nuclear data?";
						
							createNucDataExistsDialog(string, Cina.dataEvalFrame);		
										
						}
						
					}
				
				}
			
			}else if(ds.getNumberUserNucDataSetDataStructures()==0){
			
				if(!checkNewNucDataSetField()){
				
					String string = "Please enter a name for the new nuclear data set.";
					
					createExceptionDialog(string, Cina.dataEvalFrame);
				
				}else{
	
					ds.setDestNucDataSetName(newNucDataSetField.getText());
					ds.setSourceNucDataSet("");
					ds.setDestNucDataSetGroup("USER");
					ds.setNucData("");
					ds.setProperties(getNucDataProperties());
					ds.setDeleteSourceNucDataSet("NO");
					ds.setDeleteNucData("NO");
					
					if(Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA SET", Cina.dataEvalFrame)
							&& Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA", Cina.dataEvalFrame)){
						
						saveNucDataDialog.setVisible(false);
						saveNucDataDialog.dispose();
						
						String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataReport();
										
						createNucDataMergeDialog(string, Cina.dataEvalFrame);
					
					}
				
				}
			
			}
		
		}else if(ae.getSource()==yesButton){
		
			ds.setDestNucDataSetName(newNucDataSetField.getText());
			ds.setSourceNucDataSet("");
			ds.setDestNucDataSetGroup("USER");
			ds.setNucData("");
			ds.setProperties(getNucDataProperties());
			ds.setDeleteSourceNucDataSet("NO");
			ds.setDeleteNucData("NO");
								
			if(Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA SET", Cina.dataEvalFrame)
					&& Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA", Cina.dataEvalFrame)){
			
				saveNucDataDialog.setVisible(false);
				saveNucDataDialog.dispose();
				
				String string = ds.getModifyNucDataSetReport()
										+ "\n"
										+ ds.getModifyNucDataSetReport();
										
				createNucDataMergeDialog(string, Cina.dataEvalFrame);
			
			}
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==yesButton2){

			nucDataExistsDialog.setVisible(false);
			nucDataExistsDialog.dispose();
		
			ds.setNucData("");
			ds.setProperties(getNucDataProperties());
			ds.setDeleteNucData("NO");
		
			if(Cina.cinaCGIComm.doCGICall("MODIFY NUC DATA", Cina.dataEvalFrame)){
							
				saveNucDataDialog.setVisible(false);
				saveNucDataDialog.dispose();

				String string = ds.getModifyNucDataReport();
				createNucDataModifyDialog(string, Cina.dataEvalFrame);
			
			}
			
		}else if(ae.getSource()==noButton2){
		
			nucDataExistsDialog.setVisible(false);
			nucDataExistsDialog.dispose();
		
		}

	}

	/**
	 * Creates the high e extra curve.
	 */
	public void createHighEExtraCurve(){
	
		double highEPoint = Double.valueOf(highEField.getText()).doubleValue();
		double lowEPoint = ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1];
		
		double[] outputXDataArray = new double[2];
		double[] outputYDataArray = new double[2];
		
		outputXDataArray[0] = lowEPoint;
		outputXDataArray[1] = highEPoint;
		
		double totalSlope = 0.0;
		
		double slopeAverage = 0.0;
		
		int numberPoints = Integer.valueOf(numPointsHighEField.getText()).intValue()-2;
		
		for(int i=ds.getSlopeArray().length-1; i>=(ds.getSlopeArray().length-1-numberPoints); i--){
		
			totalSlope += ds.getSlopeArray()[i];
		
		}
		
		slopeAverage = totalSlope/(Integer.valueOf(numPointsHighEField.getText()).intValue()-1);

		outputYDataArray[0] = log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]); 

		outputYDataArray[1] = -slopeAverage
								*(ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]
								- outputXDataArray[1]) 
								+ log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]); 

		if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){

			ds.getExtra2NucDataDataStructure().setNucDataName("Extrapolated Cross Section for "
																									+ ds.getExtra1NucDataDataStructure().getReactionString());
		
		}else if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getExtra2NucDataDataStructure().setNucDataName("Extrapolated S Factor for "
																									+ ds.getExtra1NucDataDataStructure().getReactionString());
		
			
		}
		
		double[] fullOutputXDataArray = new double[1 + ds.getExtra1NucDataDataStructure().getNumberPoints()];
		double[] fullOutputYDataArray = new double[1 + ds.getExtra1NucDataDataStructure().getNumberPoints()];
		
		for(int i=0; i<ds.getExtra1NucDataDataStructure().getNumberPoints(); i++){
		
			fullOutputXDataArray[i] = ds.getExtra1NucDataDataStructure().getXDataArray()[i];
			fullOutputYDataArray[i] = ds.getExtra1NucDataDataStructure().getYDataArray()[i];
		
		}
		
		fullOutputXDataArray[ds.getExtra1NucDataDataStructure().getNumberPoints()] = outputXDataArray[1];
		fullOutputYDataArray[ds.getExtra1NucDataDataStructure().getNumberPoints()] = Math.pow(10, outputYDataArray[1]);
		
		ds.getExtra2NucDataDataStructure().setXDataArray(fullOutputXDataArray);
		ds.getExtra2NucDataDataStructure().setYDataArray(fullOutputYDataArray);
		ds.getExtra2NucDataDataStructure().setNumberPoints(ds.getExtra2NucDataDataStructure().getXDataArray().length);
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
				
			saveNucDataButton.setEnabled(true);
		
		}
		
		curve2Box.setEnabled(true);
		curve2Box.setSelected(true);
		
		lowESlider.removeChangeListener(this);
		lowESlider.setValue(setLowESliderValue());
		lowESlider.addChangeListener(this);

		redrawPlot();
			
	}

	/**
	 * Creates the low e extra curve.
	 */
	public void createLowEExtraCurve(){
	
		double highEPoint = ds.getExtra1NucDataDataStructure().getXDataArray()[0];
		double lowEPoint = Double.valueOf(lowEField.getText()).doubleValue();
		
		double[] outputXDataArray = new double[2];
		double[] outputYDataArray = new double[2];
		
		outputXDataArray[0] = lowEPoint;
		outputXDataArray[1] = highEPoint;
		
		double totalSlope = 0.0;
		
		double slopeAverage = 0.0;
		
		int numberPoints = Integer.valueOf(numPointsLowEField.getText()).intValue()-2;
		
		for(int i=0; i<(numberPoints+1); i++){
		
			totalSlope += ds.getSlopeArray()[i];
		
		}
		
		slopeAverage = totalSlope/(Integer.valueOf(numPointsLowEField.getText()).intValue()-1);

		outputYDataArray[1] = log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[0]); 

		outputYDataArray[0] = -slopeAverage
								*(highEPoint
								- lowEPoint) 
								+ log10*Math.log(ds.getExtra1NucDataDataStructure().getYDataArray()[0]); 


		if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){

			ds.getExtra2NucDataDataStructure().setNucDataName("Extrapolated Cross Section for "
																									+ ds.getExtra1NucDataDataStructure().getReactionString());
		
		}else if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getExtra2NucDataDataStructure().setNucDataName("Extrapolated S Factor for "
																									+ ds.getExtra1NucDataDataStructure().getReactionString());
		
			
		}
		
		double[] fullOutputXDataArray = new double[1 + ds.getExtra1NucDataDataStructure().getNumberPoints()];
		double[] fullOutputYDataArray = new double[1 + ds.getExtra1NucDataDataStructure().getNumberPoints()];
		
		for(int i=1; i<ds.getExtra1NucDataDataStructure().getNumberPoints()+1; i++){
		
			fullOutputXDataArray[i] = ds.getExtra1NucDataDataStructure().getXDataArray()[i-1];
			fullOutputYDataArray[i] = ds.getExtra1NucDataDataStructure().getYDataArray()[i-1];
		
		}
		
		fullOutputXDataArray[0] = outputXDataArray[0];
		fullOutputYDataArray[0] = Math.pow(10, outputYDataArray[0]);
		
		ds.getExtra2NucDataDataStructure().setXDataArray(fullOutputXDataArray);
		ds.getExtra2NucDataDataStructure().setYDataArray(fullOutputYDataArray);
		ds.getExtra2NucDataDataStructure().setNumberPoints(ds.getExtra2NucDataDataStructure().getXDataArray().length);
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
				
			saveNucDataButton.setEnabled(true);
		
		}
		
		curve2Box.setEnabled(true);
		curve2Box.setSelected(true);
		
		lowESlider.removeChangeListener(this);
		lowESlider.setValue(setLowESliderValue());
		lowESlider.addChangeListener(this);

		redrawPlot();

	}

	/**
	 * Good high e value.
	 *
	 * @return true, if successful
	 */
	public boolean goodHighEValue(){
	
		boolean goodHighEValue = false;
		
		try{
		
			double highE = Double.valueOf(highEField.getText()).doubleValue();
			
			if(highE<=Double.valueOf(EmaxField.getText()).doubleValue()
					&& highE>=ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1]){
			
				goodHighEValue = true;
			
			}else{
			
				String string = "New high energy value must be less than " 
									+ EmaxField.getText()
									+ " and greater than "
									+ String.valueOf(ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1])
									+ ". Increase the energy range of the plot to include new energy value.";
				
				createExceptionDialog(string, Cina.dataEvalFrame);
			
				goodHighEValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "New high energy value must be a positive number.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodHighEValue = false;
		
		}

		return goodHighEValue;
	
	}

	/**
	 * Sets the high e slider value.
	 *
	 * @return the int
	 */
	public int setHighESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double highE = Double.valueOf(highEField.getText()).doubleValue();
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
		
		min = ds.getExtra1NucDataDataStructure().getXDataArray()[ds.getExtra1NucDataDataStructure().getNumberPoints()-1];
	
		temp = (int)(1000*((highE - min)/(max - min)));
		
		return temp;
	
	}

	/**
	 * Good low e value.
	 *
	 * @return true, if successful
	 */
	public boolean goodLowEValue(){
	
		boolean goodLowEValue = false;
		
		try{
		
			double lowE = Double.valueOf(lowEField.getText()).doubleValue();

			if(lowE>=Double.valueOf(EminField.getText()).doubleValue()
					&& lowE<=ds.getExtra1NucDataDataStructure().getXDataArray()[0]){
			
				goodLowEValue = true;
			
			}else{
			
				String string = "New low energy value must be greater than " 
									+ EminField.getText()
									+ " and less than "
									+ String.valueOf(ds.getExtra1NucDataDataStructure().getXDataArray()[0])
									+ ". Increase the energy range of the plot to include new energy value.";
				
				createExceptionDialog(string, Cina.dataEvalFrame);
			
				goodLowEValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "New high energy value must be a positive number.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodLowEValue = false;
		
		}

		return goodLowEValue;
	
	}
	
	/**
	 * Sets the low e slider value.
	 *
	 * @return the int
	 */
	public int setLowESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double lowE = Double.valueOf(lowEField.getText()).doubleValue();
		
		max = ds.getExtra1NucDataDataStructure().getXDataArray()[0];

		min = Double.valueOf(EminField.getText()).doubleValue();
		
		temp = (int)(1000*((lowE - min)/(max - min)));
		
		return temp;
	
	}
	
	/**
	 * None checked.
	 *
	 * @return true, if successful
	 */
	public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	if(curve1Box.isSelected() || curve2Box.isSelected()){noneChecked = false;}
    	
    	return noneChecked;
    
    }

	/**
	 * Gets the current nuc data id.
	 *
	 * @return the current nuc data id
	 */
	public String getCurrentNucDataID(){
	
		String string = "";
		
		string = getZAString()
					+ ds.getDestNucDataSetName()
					+ "\u0009"
					+ ds.getExtra1NucDataDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getExtra1NucDataDataStructure().getDecayType().equals("none")){
		
			string += ds.getExtra1NucDataDataStructure().getDecay();
		
		}
		
		string += "\u0009"
					+ ds.getExtra1NucDataDataStructure().getNucDataType()
					+ "\u000b"
					+ ds.getExtra2NucDataDataStructure().getNucDataName();
		
		return string;
	
	}
	
	/**
	 * Gets the zA string.
	 *
	 * @return the zA string
	 */
	public String getZAString(){
	
		String string = "";
		
		string = ds.getExtra1NucDataDataStructure().getNucDataID().substring(0, 8);

		return string;
	
	}

	/**
	 * Gets the nuc data properties.
	 *
	 * @return the nuc data properties
	 */
	public String getNucDataProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Points = " + String.valueOf(ds.getExtra2NucDataDataStructure().getNumberPoints()) + "\u0009";
		propertiesString += "Nuc Data Notes = " + String.valueOf(ds.getExtra2NucDataDataStructure().getNucDataNotes()) + "\u0009";
		propertiesString += "Reaction String = " + ds.getExtra1NucDataDataStructure().getReactionString() + "\u0009";
		propertiesString += "Nuc Data Name = " + ds.getExtra2NucDataDataStructure().getNucDataName() + "\u0009";
		propertiesString += "Nuc Data Type = " + ds.getExtra1NucDataDataStructure().getNucDataType() + "\u0009";
		
		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getExtra1NucDataDataStructure().getReactionType());
		
		if(!ds.getExtra1NucDataDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getExtra1NucDataDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Reference Citation = " + ds.getExtra2NucDataDataStructure().getRefCitation() + "\u0009";
		propertiesString += "X points = " + getXDataPoints() + "\u0009";
		propertiesString += "Y points = " + getYDataPoints() + "\u0009";

		return propertiesString;
	
	}

	/**
	 * Gets the x data points.
	 *
	 * @return the x data points
	 */
	public String getXDataPoints(){
	
		String string = "";
		
		for(int i=0; i<ds.getExtra2NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getExtra2NucDataDataStructure().getXDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getExtra2NucDataDataStructure().getXDataArray()[i]);
			
			}
		
		
		}
		
		return string;
	
	}
	
	/**
	 * Gets the y data points.
	 *
	 * @return the y data points
	 */
	public String getYDataPoints(){
	
		String string = "";
		
		for(int i=0; i<ds.getExtra2NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getExtra2NucDataDataStructure().getYDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getExtra2NucDataDataStructure().getYDataArray()[i]);
			
			}
		
		
		}
		
		return string;
	
	}
	
	/**
	 * Check new nuc data set field.
	 *
	 * @return true, if successful
	 */
	public boolean checkNewNucDataSetField(){
		
		boolean goodName = false;
		
		if(!newNucDataSetField.getText().equals("")){goodName = true;}
		
		return goodName;
		
	}
	
	/**
	 * Check public nuc data set name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicNucDataSetName(){
	
		boolean goodName = true;

		for(int i=0; i<ds.getNumberPublicNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equalsIgnoreCase(ds.getPublicNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equalsIgnoreCase(ds.getSharedNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check overwrite nuc data set name.
	 *
	 * @return true, if successful
	 */
	public boolean checkOverwriteNucDataSetName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
	
			if(newNucDataSetField.getText().equals(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName())){
			
				goodName = false;
			
			}
		
		}
		
		return goodName;
	
	}

	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){return Double.valueOf((String)YminComboBox.getSelectedItem()).doubleValue();}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){return Double.valueOf((String)YmaxComboBox.getSelectedItem()).doubleValue();}

	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
	
		String string = "";
				
		try{
	
			if((EminField.getText().equals("") || EmaxField.getText().equals(""))){

				string = "One or more fields for the energy range are empty. \nPlease enter numeric values.";

				createExceptionDialog(string, Cina.dataEvalFrame);
				
			}else if((Double.valueOf(EminField.getText()).doubleValue() >= Double.valueOf(EmaxField.getText()).doubleValue())){

				string = "Energy minimum must be less than the energy maximum.";
				createExceptionDialog(string, Cina.dataEvalFrame);
		
			}else{
				
				dataEvalExtraDataPlotPanel.setXmin(Double.valueOf(EminField.getText()).doubleValue());
				dataEvalExtraDataPlotPanel.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());

				dataEvalExtraDataPlotPanel.setPlotState();

			}
			
		}catch(NumberFormatException nfe){
				
			string = "Values for Emin and Emax must be numeric values.";		
			createExceptionDialog(string, Cina.dataEvalFrame);
				
		}

	}

	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
	//Method to set the current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		ds.setExtraXmax(setXmax());
		ds.setExtraXmin(setXmin());
		
		ds.setExtraYmax(setYmax());
		ds.setExtraYmin(setYmin());
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YminComboBox.removeItemListener(this);
		YmaxComboBox.removeItemListener(this);

		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

		YminComboBox.addItemListener(this);
		YmaxComboBox.addItemListener(this);

		EminField.setText(String.valueOf(ds.getExtraXmin()));
		EmaxField.setText(String.valueOf(ds.getExtraXmax()));

		curveNameLabel.setText(ds.getExtra1NucDataDataStructure().getNucDataName());
									
		curveLabel.setText("Curve from "
								+ ds.getExtra1NucDataDataStructure().getNucDataSet()
								+ " Data Set: ");
		
		if(ds.getExtra1NucDataDataStructure().getDecay().equals("")){
								
			reactionLabel.setText("Reaction: " + ds.getExtra1NucDataDataStructure().getReactionString());
		
		}else{
		
			reactionLabel.setText("Reaction: " 
									+ ds.getExtra1NucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getExtra1NucDataDataStructure().getDecay()
									+ "]");
		
		}

		ds.setSlopeArray(getSlopeArray());

		ds.setNumberHighEPointsMax(getNumberHighEPointsMax());
		ds.setNumberLowEPointsMax(getNumberLowEPointsMax());

		numPointsHighESlider.setMaximum(ds.getNumberHighEPointsMax());
		numPointsHighESlider.setMinimum(2);

		numPointsLowESlider.setMaximum(ds.getNumberLowEPointsMax());
		numPointsLowESlider.setMinimum(2);

		if(numPointsHighESlider.getMaximum()==2){
		
			numPointsHighESlider.setEnabled(false);
		
		}else{
		
			numPointsHighESlider.setEnabled(true);
		
		}

		if(numPointsLowESlider.getMaximum()==2){
		
			numPointsLowESlider.setEnabled(false);
		
		}else{
		
			numPointsLowESlider.setEnabled(true);
		
		}

		if(ds.getExtra1NucDataDataStructure().getNucDataType().equals("CS(E)")){

			energyComboBox.setEnabled(false);
		
			energyComboBox.removeItemListener(this);
			energyComboBox.setSelectedItem("High Energy");
			energyComboBox.addItemListener(this);
		
		}else{
			
			energyComboBox.setEnabled(true);
		
		}
		
		redrawPlot();
		sp.revalidate();
		
	}
	
	/**
	 * Sets the xmin.
	 *
	 * @return the double
	 */
	public double setXmin(){
		
		double newXmin = 1e30;
		
		for(int i=0; i<ds.getExtra1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmin = Math.min(newXmin, ds.getExtra1NucDataDataStructure().getXDataArray()[i]);
		
		}
		
		newXmin = 0;
		
		return newXmin;
	
	}
	
	/**
	 * Sets the xmax.
	 *
	 * @return the double
	 */
	public double setXmax(){
		
		double newXmax = 0.0;
		
		for(int i=0; i<ds.getExtra1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmax = Math.max(newXmax, ds.getExtra1NucDataDataStructure().getXDataArray()[i]);
		
		}
	
		newXmax = newXmax + 0.5*newXmax;
	
		return newXmax;
	
	}	
	
	/**
	 * Sets the ymin.
	 *
	 * @return the double
	 */
	public double setYmin(){
		
		double newYmin = 1e30;
		
		for(int i=0; i<ds.getExtra1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmin = Math.min(newYmin, ds.getExtra1NucDataDataStructure().getYDataArray()[i]);
		
		}
		
		return newYmin;
	
	}
	
	/**
	 * Sets the ymax.
	 *
	 * @return the double
	 */
	public double setYmax(){
		
		double newYmax = 0.0;
		
		for(int i=0; i<ds.getExtra1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmax = Math.max(newYmax, ds.getExtra1NucDataDataStructure().getYDataArray()[i]);
		
		}
		
		return newYmax;
	
	}	
	
	/**
	 * Sets the right panel format.
	 *
	 * @param string the new right panel format
	 */
	public void setRightPanelFormat(String string){
	
		rightPanel.removeAll();
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = 2;
		rightPanel.add(reactionLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(curveLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(curveNameLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(energyLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(energyComboBox, gbc);
	
		if(string.equals("High Energy")){

			maxThresLabel.setText("<html>Maximum allowed change in the<p>derivative at high energy value :</html>");

			dataEvalExtraDataPlotPanel.setLowE(false);
			dataEvalExtraDataPlotPanel.setHighE(true);

			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(highELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(highEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(highESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(highESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsHighELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 8;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsHighEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 9;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsHighESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsHighESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThresLabel, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 11;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThreshField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(applyDerButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 13;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(curveBoxLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			rightPanel.add(curve1Box, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			rightPanel.add(curve2Box, gbc);
		
			
			highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
			
		}else if(string.equals("Low Energy")){
			
			maxThresLabel.setText("<html>Maximum allowed change in the<p>derivative at low energy value :</html>");
			
			dataEvalExtraDataPlotPanel.setLowE(true);
			dataEvalExtraDataPlotPanel.setHighE(false);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(lowELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 5;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(lowEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(lowESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(lowESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsLowELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 8;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsLowEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 9;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsLowESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsLowESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThresLabel, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 11;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThreshField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(applyDerButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 13;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(curveBoxLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			rightPanel.add(curve1Box, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			rightPanel.add(curve2Box, gbc);
		
			lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
						
		}
	
	}
	
	/**
	 * Creates the nuc data merge dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataMergeDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	nucDataMergeDialog = new JDialog(frame, "Nuclear data saved", true);
    	nucDataMergeDialog.setSize(350, 210);
    	nucDataMergeDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataMergeDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataMergeTextArea = new JTextArea("", 5, 30);
		nucDataMergeTextArea.setLineWrap(true);
		nucDataMergeTextArea.setWrapStyleWord(true);
		nucDataMergeTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataMergeTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		nucDataMergeTextArea.setText(string);
		nucDataMergeTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucDataMergeDialog.setVisible(false);
					nucDataMergeDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataMergeDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		nucDataMergeDialog.getContentPane().add(okButton, gbc1);
		
		//Show the dialog
		nucDataMergeDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the nuc data modify dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataModifyDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();

    	nucDataModifyDialog = new JDialog(frame, "Nuclear data saved", true);
    	nucDataModifyDialog.setSize(350, 210);
    	nucDataModifyDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataModifyDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataModifyTextArea = new JTextArea("", 5, 30);
		nucDataModifyTextArea.setLineWrap(true);
		nucDataModifyTextArea.setWrapStyleWord(true);
		nucDataModifyTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataModifyTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		nucDataModifyTextArea.setText(string);
		nucDataModifyTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucDataModifyDialog.setVisible(false);
					nucDataModifyDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataModifyDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		nucDataModifyDialog.getContentPane().add(okButton, gbc1);
		
		//Show the dialog
		nucDataModifyDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the save nuc data dialog.
	 *
	 * @param frame the frame
	 */
	public void createSaveNucDataDialog(JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	saveNucDataDialog = new JDialog(frame, "Save nuclear data to nuclear data set...", true);
    	saveNucDataDialog.setSize(588, 170);
    	saveNucDataDialog.getContentPane().setLayout(new GridBagLayout());
		saveNucDataDialog.setLocationRelativeTo(frame);

		nucDataSetComboBox = new JComboBox();
		nucDataSetComboBox.setFont(Fonts.textFont);
		
		newNucDataSetField = new JTextField(15);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		if(ds.getNumberUserNucDataSetDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserNucDataSetDataStructures(); i++){
			
				nucDataSetComboBox.addItem(ds.getUserNucDataSetDataStructureArray()[i].getNucDataSetName());
			
			}
		
			newNucDataSetField.setEditable(false);
		
			nucDataSetComboBox.addItem("new nuclear data set");
		
			nucDataSetComboBox.addItemListener(this);
		
			JLabel topLabel = new JLabel("Select a User nuclear data set or create a new nuclear data set to save current nuclear data.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveNucDataDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(nucDataSetComboBox, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 2;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);
		
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 3;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(buttonPanel, gbc1);
			
		}else{
		
			JLabel topLabel = new JLabel("There are no User nuclear data sets. Create a new nuclear data set to save current nuclear data.");
			topLabel.setFont(Fonts.textFont);
		
			JLabel newNucDataSetLabel = new JLabel("Enter the name of the new nuclear data set: ");
			newNucDataSetLabel.setFont(Fonts.textFont);
		
			newNucDataSetField.setEditable(true);
		
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			gbc1.gridwidth = 2;
			gbc1.insets = new Insets(5, 5, 5, 5);
			saveNucDataDialog.getContentPane().add(topLabel, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetLabel, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 1;
			gbc1.gridwidth = 1;
			saveNucDataDialog.getContentPane().add(newNucDataSetField, gbc1);	
			
			okButton = new JButton("Ok");
			okButton.setFont(Fonts.buttonFont);
			okButton.addActionListener(this);
			
			cancelButton = new JButton("Cancel");
			cancelButton.setFont(Fonts.buttonFont);
			cancelButton.addActionListener(this);
			
			gbc1.gridx = 0;
			gbc1.gridy = 0;
			buttonPanel.add(okButton, gbc1);
			
			gbc1.gridx = 1;
			gbc1.gridy = 0;
			buttonPanel.add(cancelButton, gbc1);
			
			gbc1.gridx = 0;
			gbc1.gridy = 2;
			gbc1.gridwidth = 2;
			saveNucDataDialog.getContentPane().add(buttonPanel, gbc1);
				
		}

		//Show the dialog
		saveNucDataDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the nuc data exists dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createNucDataExistsDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	nucDataExistsDialog = new JDialog(frame, "Caution!", true);
    	nucDataExistsDialog.setSize(320, 215);
    	nucDataExistsDialog.getContentPane().setLayout(new GridBagLayout());
		nucDataExistsDialog.setLocationRelativeTo(frame);
		
		JTextArea nucDataExistsTextArea = new JTextArea("", 5, 30);
		nucDataExistsTextArea.setLineWrap(true);
		nucDataExistsTextArea.setWrapStyleWord(true);
		nucDataExistsTextArea.setFont(Fonts.textFont);
		nucDataExistsTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucDataExistsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String nucDataExistsString = string;

		nucDataExistsTextArea.setText(nucDataExistsString);
		
		nucDataExistsTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton2 = new JButton("Yes");
		yesButton2.setFont(Fonts.buttonFont);
		yesButton2.addActionListener(this);
		
		noButton2 = new JButton("No");
		noButton2.setFont(Fonts.buttonFont);
		noButton2.addActionListener(this);
		
		JPanel nucDataExistsButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataExistsButtonPanel.add(yesButton2, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		nucDataExistsButtonPanel.add(noButton2, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		nucDataExistsDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;

		nucDataExistsDialog.getContentPane().add(nucDataExistsButtonPanel, gbc1);

		nucDataExistsDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	cautionDialog = new JDialog(frame, "Caution!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setFont(Fonts.textFont);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;

		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);
		
		cautionDialog.setVisible(true);

	}
	
	/**
	 * Creates the exception dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createExceptionDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	exceptionDialog = new JDialog(frame, "Attention!", true);
    	exceptionDialog.setSize(350, 210);
    	exceptionDialog.getContentPane().setLayout(new GridBagLayout());
		exceptionDialog.setLocationRelativeTo(frame);
		
		JTextArea exceptionTextArea = new JTextArea("", 5, 30);
		exceptionTextArea.setLineWrap(true);
		exceptionTextArea.setWrapStyleWord(true);
		exceptionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(exceptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		exceptionTextArea.setText(string);
		exceptionTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		okButton2 = new JButton("Ok");
		okButton2.setFont(Fonts.buttonFont);
		okButton2.addActionListener(this);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		exceptionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		exceptionDialog.getContentPane().add(okButton2, gbc1);
	
		//Show the dialog
		exceptionDialog.setVisible(true);
	
	}

}

class DataEvalExtraDataPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.dataEvalFrame.dataEvalExtraData2Panel.dataEvalExtraDataPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}