package org.nucastrodata.data.dataeval;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

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

import java.text.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.dataeval.DataEvalTransDataPlotPanel;
import org.nucastrodata.data.dataeval.DataEvalTransDataPlotPrintable;
import org.nucastrodata.data.dataeval.DataEvalTransDataTableFrame;


/**
 * The Class DataEvalTransData2Panel.
 */
public class DataEvalTransData2Panel extends WizardPanel implements ActionListener, ItemListener, ChangeListener{

	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;

	/** The log10. */
	double log10 = 0.434294482;

	//Declare panels
	/** The right panel. */
	JPanel mainPanel, buttonPanel, formatPanel, scalePanel, setScalePanel, scaleTitlePanel, rightPanel;

	/** The data eval trans data plot panel. */
	DataEvalTransDataPlotPanel dataEvalTransDataPlotPanel;

	/** The default button. */
	JButton saveButton, printButton, tableButton, scaleButton, saveNucDataButton
			, applyButton, okButton, okButton2, cancelButton, yesButton, noButton, yesButton2, noButton2, defaultButton;
    
    /** The scale combo box. */
    JComboBox scaleComboBox;
    
    /** The curve2 box. */
    JCheckBox curve1Box, curve2Box;
    
    /** The sp. */
    JScrollPane sp;
    
	/** The curve box label. */
	JLabel curveLabel, curveNameLabel, rateLabel, reactionLabel, plotControlsLabel, scaleLabel, originalELabel, finalELabel,
				originalESliderLabel, finalESliderLabel, scaleELabel, originalYLabel, finalYLabel,
				originalYSliderLabel, finalYSliderLabel, scaleYLabel, originalYLabel2, curveBoxLabel;

	/** The scale y point radio button. */
	JRadioButton scaleEFactorRadioButton, scaleEPointRadioButton, scaleYFactorRadioButton, scaleYPointRadioButton;

	/** The Y button group. */
	ButtonGroup EButtonGroup, YButtonGroup;

	/** The original y field2. */
	JTextField scaleEFactorField, originalEField, finalEField, scaleYFactorField, originalYField, finalYField, originalYField2;

	/** The final y slider. */
	JSlider originalESlider, finalESlider, originalYSlider, finalYSlider;

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
	 * Instantiates a new data eval trans data2 panel.
	 *
	 * @param ds the ds
	 */
	public DataEvalTransData2Panel(DataEvalDataStructure ds){
	
		this.ds = ds;
	
		//Set the current panel index to 1 in the parent frame
		Cina.dataEvalFrame.setCurrentFeatureIndex(2);
		Cina.dataEvalFrame.setCurrentPanelIndex(2);

		//Create main panel
		mainPanel = new JPanel(new BorderLayout());
		
		addWizardPanel("Nuclear Data Evaluator's Toolkit", "Cross Section Normalizer", "2", "2");
		
		//Instantiate gbc
		gbc = new GridBagConstraints();
		
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
		
		scaleButton = new JButton("Normalize Curve");
		scaleButton.setFont(Fonts.buttonFont);
		scaleButton.addActionListener(this);
		
		defaultButton = new JButton("Return to Default Settings");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			saveNucDataButton = new JButton("Save Cross Section");
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			saveNucDataButton = new JButton("Save S Factor");
		
		}
		
		saveNucDataButton.setFont(Fonts.buttonFont);
		saveNucDataButton.addActionListener(this);
		saveNucDataButton.setEnabled(false);
		
		applyButton = new JButton("Apply Energy Range");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		//COMBOBOXES///////////////////////////////////////////////////////////////COMBOBOXES//////////////////////
		scaleComboBox = new JComboBox();
		scaleComboBox.setFont(Fonts.textFont);
		scaleComboBox.addItem("Energy");
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
			
			scaleComboBox.addItem("Cross Section");
			scaleComboBox.setSelectedItem("Cross Section");
			
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
			
			scaleComboBox.addItem("S Factor");
			scaleComboBox.setSelectedItem("S Factor");
				
		}
		
		scaleComboBox.addItemListener(this);
		
		YminComboBox = new JComboBox();
        YminComboBox.setFont(Fonts.textFont);
        for(int i=9; i>=-10; i--) {YminComboBox.addItem(Integer.toString(i));}
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=10; i>=-9; i--) {YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.addItemListener(this);
		
		EminField = new JTextField(5);
		EmaxField = new JTextField(5);
		
		ds.setTransXmax(setXmax());
		ds.setTransXmin(setXmin());
		
		ds.setTransYmax(setYmax());
		ds.setTransYmin(setYmin());
		
		EminField.setText(String.valueOf(ds.getTransXmin()));
		EmaxField.setText(String.valueOf(ds.getTransXmax()));
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YminComboBox.removeItemListener(this);
		YmaxComboBox.removeItemListener(this);

		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

		YminComboBox.addItemListener(this);
		YmaxComboBox.addItemListener(this);
		
		//CHECKBOXES///////////////////////////////////////////////////////////////CHECKBOXES/////////////////////
		curve1Box = new JCheckBox("Display Original Curve");
		curve1Box.setFont(Fonts.textFont);
		curve1Box.setSelected(true);
		curve1Box.addItemListener(this);
		
		curve2Box = new JCheckBox("Display Normalized Curve");
		curve2Box.setFont(Fonts.textFont);
		curve2Box.setSelected(true);
		curve2Box.addItemListener(this);
		
		//RADIOBUTTONS///////////////////////////////////////////////////////////////RADIOBUTTONS////////////////
		scaleEFactorRadioButton = new JRadioButton("Enter Energy Scale Factor", true);
		scaleEFactorRadioButton.setFont(Fonts.textFont);
		scaleEFactorRadioButton.addItemListener(this);
		
		scaleEPointRadioButton = new JRadioButton("Enter Original and Final Energy Point");
		scaleEPointRadioButton.setFont(Fonts.textFont);
		scaleEPointRadioButton.addItemListener(this);
		
		EButtonGroup = new ButtonGroup();
		EButtonGroup.add(scaleEFactorRadioButton);
		EButtonGroup.add(scaleEPointRadioButton);
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			scaleYFactorRadioButton = new JRadioButton("Enter Cross Sec Scale Factor", true);
			scaleYFactorRadioButton.setFont(Fonts.textFont);
			scaleYFactorRadioButton.addItemListener(this);
			
			scaleYPointRadioButton = new JRadioButton("Enter Original and Final Cross Sec Point");
			scaleYPointRadioButton.setFont(Fonts.textFont);
			scaleYPointRadioButton.addItemListener(this);
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			scaleYFactorRadioButton = new JRadioButton("Enter S Factor Scale Factor", true);
			scaleYFactorRadioButton.setFont(Fonts.textFont);
			scaleYFactorRadioButton.addItemListener(this);
			
			scaleYPointRadioButton = new JRadioButton("Enter Original and Final S Factor Point");
			scaleYPointRadioButton.setFont(Fonts.textFont);
			scaleYPointRadioButton.addItemListener(this);
		
		}
		
		YButtonGroup = new ButtonGroup();
		YButtonGroup.add(scaleYFactorRadioButton);
		YButtonGroup.add(scaleYPointRadioButton);
		
		//CHECKBOXES////////////////////////////////////////////////////////////////CHECKBOXES//////////////////
		curve1Box = new JCheckBox("Display Original Curve", true);
		curve1Box.addItemListener(this);
		curve1Box.setFont(Fonts.textFont);
		
		curve2Box = new JCheckBox("Display Normalized Curve", false);
		curve2Box.setEnabled(false);
		curve2Box.addItemListener(this);
		curve2Box.setFont(Fonts.textFont);
		
		//SLIDERS///////////////////////////////////////////////////////////////////SLIDERS//////////////////////
		originalESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		originalESlider.addChangeListener(this);
		originalESlider.setEnabled(false);
		
		finalESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		finalESlider.addChangeListener(this);
		finalESlider.setEnabled(false);
		
		originalYSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		originalYSlider.addChangeListener(this);
		originalYSlider.setEnabled(false);
		
		finalYSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		finalYSlider.addChangeListener(this);
		finalYSlider.setEnabled(false);
		
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

		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			labelArray[4] = new JLabel(stringArray[4]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[5]);
			labelArray[5].setFont(Fonts.textFont);
			
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			labelArray[4] = new JLabel(stringArray[6]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[7]);
			labelArray[5].setFont(Fonts.textFont);
			
		}
		
		scaleLabel = new JLabel("Choose quantity to scale :");
		scaleLabel.setFont(Fonts.textFont);
		
		originalELabel = new JLabel("Original Energy Value :");
		originalELabel.setFont(Fonts.textFont);
		
		finalELabel = new JLabel("New Energy Value :");
		finalELabel.setFont(Fonts.textFont);
		
		originalESliderLabel = new JLabel("Use slider to set original energy value :");
		originalESliderLabel.setFont(Fonts.textFont);
		
		finalESliderLabel = new JLabel("Use slider to set new energy value :");
		finalESliderLabel.setFont(Fonts.textFont);
		
		scaleELabel = new JLabel("Energy Scale Factor :");
		scaleELabel.setFont(Fonts.textFont);
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			originalYLabel = new JLabel("Original Energy Value :");
			originalYLabel.setFont(Fonts.textFont);
			
			originalYLabel2 = new JLabel("Original Cross Sec Value :");
			originalYLabel2.setFont(Fonts.textFont);
			
			finalYLabel = new JLabel("New Cross Sec Value :");
			finalYLabel.setFont(Fonts.textFont);
			
			originalYSliderLabel = new JLabel("Use slider to set original energy value :");
			originalYSliderLabel.setFont(Fonts.textFont);
			
			finalYSliderLabel = new JLabel("Use slider to set new cross sec value :");
			finalYSliderLabel.setFont(Fonts.textFont);
			
			scaleYLabel = new JLabel("Cross Sec Scale Factor :");
			scaleYLabel.setFont(Fonts.textFont);
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			originalYLabel = new JLabel("Original Energy Value :");
			originalYLabel.setFont(Fonts.textFont);
			
			originalYLabel2 = new JLabel("Original S Factor Value :");
			originalYLabel2.setFont(Fonts.textFont);
			
			finalYLabel = new JLabel("New S Factor Value :");
			finalYLabel.setFont(Fonts.textFont);
			
			originalYSliderLabel = new JLabel("Use slider to set original energy value :");
			originalYSliderLabel.setFont(Fonts.textFont);
			
			finalYSliderLabel = new JLabel("Use slider to set new S factor value :");
			finalYSliderLabel.setFont(Fonts.textFont);
			
			scaleYLabel = new JLabel("S Factor Scale Factor :");
			scaleYLabel.setFont(Fonts.textFont);	
		
		}
		
		curveBoxLabel = new JLabel("Choose curves for plot below :");
		curveBoxLabel.setFont(Fonts.textFont);
		
		//TEXTFIELDS////////////////////////////////////////////////////////////////////TEXTFIELDS////////////////
		scaleEFactorField = new JTextField(8);
		scaleEFactorField.setText("1.00");
		
		originalEField = new JTextField(8);
		originalEField.setEditable(false);
		originalEField.setText(NumberFormats.getFormattedValueLong(getOriginalESliderValue()));
		
		finalEField = new JTextField(8);
		finalEField.setEditable(false);
		finalEField.setText(NumberFormats.getFormattedValueLong(getFinalESliderValue()));
		
		scaleYFactorField = new JTextField(8);
		scaleYFactorField.setText("1.00");
		
		originalYField = new JTextField(8);
		originalYField.setEditable(false);
		originalYField.setText(NumberFormats.getFormattedValueLong(getOriginalYSliderValue()));
		
		finalYField = new JTextField(8);
		finalYField.setEditable(false);
		finalYField.setText(NumberFormats.getFormattedValueLong(Math.pow(10, getFinalYSliderValue())));
		
		originalYField2 = new JTextField(8);
		originalYField2.setEditable(false);
		originalYField2.setText(NumberFormats.getFormattedValueLong(getYValue(getOriginalYSliderValue())));
		
		//PANELS/////////////////////////////////////////////////////////////////////PANELS///////////////////////
		buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(scaleButton);
		buttonPanel.add(saveNucDataButton);
		
		//PLOT///////////////////////////////////////////////////////////////////////PLOT///////////////////////////
		dataEvalTransDataPlotPanel = new DataEvalTransDataPlotPanel(YminComboBoxInit, YmaxComboBoxInit, ds);
		
		dataEvalTransDataPlotPanel.setPreferredSize(dataEvalTransDataPlotPanel.getSize());
		
		dataEvalTransDataPlotPanel.revalidate();
		
		sp = new JScrollPane(dataEvalTransDataPlotPanel/*, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED*/);

		dataEvalTransDataPlotPanel.setPreferredSize(dataEvalTransDataPlotPanel.getSize());
		
		dataEvalTransDataPlotPanel.revalidate();
		
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
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			setRightPanelFormat("Cross Section");
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			setRightPanelFormat("S Factor");
		
		}
		
		mainPanel.add(sp, BorderLayout.CENTER);
		
		mainPanel.add(rightPanel, BorderLayout.EAST);
	
		mainPanel.add(formatPanel, BorderLayout.SOUTH);
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}

	/**
	 * Sets the initial ymin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialYminComboBoxInit(){
	
		int min = 0;
		
		double currentYmin = 0.0;
		
		currentYmin = ds.getTransYmin();
	
		min = (int)Math.floor(0.434294482*Math.log(currentYmin));
	
		if(min < -10){min = -10;}
		
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
		
		currentYmax = ds.getTransYmax();
	
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 10){max = 10;}

		return max;
	
	}

	/**
	 * Gets the original e slider value.
	 *
	 * @return the original e slider value
	 */
	public double getOriginalESliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = ds.getTrans1NucDataDataStructure().getXDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1];
	
		min = ds.getTrans1NucDataDataStructure().getXDataArray()[0];
		
		temp = min + (((double)originalESlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Gets the final e slider value.
	 *
	 * @return the final e slider value
	 */
	public double getFinalESliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
		
		min = Double.valueOf(EminField.getText()).doubleValue();
		
		temp = min + (((double)finalESlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Gets the original y slider value.
	 *
	 * @return the original y slider value
	 */
	public double getOriginalYSliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = ds.getTrans1NucDataDataStructure().getXDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1];
	
		min = ds.getTrans1NucDataDataStructure().getXDataArray()[0];
		
		temp = min + (((double)originalYSlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Gets the final y slider value.
	 *
	 * @return the final y slider value
	 */
	public double getFinalYSliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		max = Double.valueOf((String)YmaxComboBox.getSelectedItem()).doubleValue();
		min = Double.valueOf((String)YminComboBox.getSelectedItem()).doubleValue();
		
		temp = min + (((double)finalYSlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Gets the y value.
	 *
	 * @param E the e
	 * @return the y value
	 */
	public double getYValue(double E){
	
		double lowYPoint = 0.0;
		double highYPoint = 0.0;
		
		double lowXPoint = 0.0;
		double highXPoint = 0.0;
		
		double interYPoint = 0.0;
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
		
			if(ds.getTrans1NucDataDataStructure().getXDataArray()[i]<E){
			
				lowYPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getYDataArray()[i]);
				lowXPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getXDataArray()[i]);
			
			}
		
		}
		
		for(int i=ds.getTrans1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
		
			if(ds.getTrans1NucDataDataStructure().getXDataArray()[i]>E){
			
				highYPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getYDataArray()[i]);
				highXPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getXDataArray()[i]);
				
			}
		
		}

		if(highXPoint==0.0){
		
			highXPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getXDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(highYPoint==0.0){
		
			highYPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getYDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(lowXPoint==0.0){
		
			lowXPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getXDataArray()[0]);
			
		}
		
		if(lowYPoint==0.0){
		
			lowYPoint = log10*Math.log(ds.getTrans1NucDataDataStructure().getYDataArray()[0]);
			
		}
				
		double slope = (highYPoint - lowYPoint)/(highXPoint - lowXPoint);
		
		interYPoint = (-slope*(highXPoint - log10*Math.log(E))) + highYPoint;
		
		interYPoint = Math.pow(10, interYPoint);
		
		return interYPoint;
	
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
       			
       			if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
       			
       				string = "Cross section minimum must be less than cross section maximum.";
       			
       			}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
       			
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
			
			finalYField.setText(NumberFormats.getFormattedValueLong(getFinalYSliderValue()));
			
		}else if(ie.getSource()==nucDataSetComboBox){
		
			if(((String)nucDataSetComboBox.getSelectedItem()).equals("new nuclear data set")){
			
				newNucDataSetField.setEditable(true);
			
			}else{
			
				newNucDataSetField.setEditable(false);
			
			}
		
		}else if(ie.getSource()==scaleComboBox){
			
			rightPanel.removeAll();
			
			setRightPanelFormat((String)scaleComboBox.getSelectedItem());
			
			if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& scaleEPointRadioButton.isSelected()){
			
				curve2Box.setText("Display Gain Normalized Curve");
			
				dataEvalTransDataPlotPanel.setOriginalE(true);
				dataEvalTransDataPlotPanel.setFinalE(true);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& !scaleEPointRadioButton.isSelected()){
			
				curve2Box.setText("Display Gain Normalized Curve");
							
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& scaleYPointRadioButton.isSelected()){
			
				curve2Box.setText("Display Normalized Curve");
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(true);
				dataEvalTransDataPlotPanel.setOriginalY2(true);
				dataEvalTransDataPlotPanel.setFinalY(true);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& !scaleYPointRadioButton.isSelected()){
			
				curve2Box.setText("Display Normalized Curve");
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}
			
			redrawPlot();
			
			
			
			validate();
		
		}else if(ie.getSource()==scaleEFactorRadioButton
					|| ie.getSource()==scaleEPointRadioButton){
		
			if(scaleEFactorRadioButton.isSelected()){
			
				scaleEFactorField.setEditable(true);
				
				originalEField.setEditable(false);
				finalEField.setEditable(false);
				
				originalESlider.setEnabled(false);
				finalESlider.setEnabled(false);
			
			}else if(scaleEPointRadioButton.isSelected()){
			
				scaleEFactorField.setEditable(false);
				
				originalEField.setEditable(true);
				finalEField.setEditable(true);
				
				originalESlider.setEnabled(true);
				finalESlider.setEnabled(true);
			
			}
		
			if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& scaleEPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(true);
				dataEvalTransDataPlotPanel.setFinalE(true);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& !scaleEPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& scaleYPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(true);
				dataEvalTransDataPlotPanel.setOriginalY2(true);
				dataEvalTransDataPlotPanel.setFinalY(true);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& !scaleYPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}
		
			redrawPlot();
		
		}else if(ie.getSource()==scaleYFactorRadioButton
					|| ie.getSource()==scaleYPointRadioButton){
		
			if(scaleYFactorRadioButton.isSelected()){
			
				scaleYFactorField.setEditable(true);
				
				originalYField.setEditable(false);
				finalYField.setEditable(false);
				
				originalYSlider.setEnabled(false);
				finalYSlider.setEnabled(false);
			
			}else if(scaleYPointRadioButton.isSelected()){
			
				scaleYFactorField.setEditable(false);
				
				originalYField.setEditable(true);
				finalYField.setEditable(true);
				
				originalYSlider.setEnabled(true);
				finalYSlider.setEnabled(true);
			
			}
			
			if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& scaleEPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(true);
				dataEvalTransDataPlotPanel.setFinalE(true);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& !scaleEPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& scaleYPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(true);
				dataEvalTransDataPlotPanel.setOriginalY2(true);
				dataEvalTransDataPlotPanel.setFinalY(true);
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& !scaleYPointRadioButton.isSelected()){
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
				dataEvalTransDataPlotPanel.setFinalE(false);
				
				dataEvalTransDataPlotPanel.setOriginalY(false);
				dataEvalTransDataPlotPanel.setOriginalY2(false);
				dataEvalTransDataPlotPanel.setFinalY(false);
			
			}
			
			redrawPlot();
		
		}
		
	}

	/**
	 * Sets the right panel format.
	 *
	 * @param string the new right panel format
	 */
	public void setRightPanelFormat(String string){
	
		if(string.equals("Energy")){
		
			
			double[] col = {5, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL, 5};
			double[] row = {5, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED, 5};

			rightPanel.setLayout(new TableLayout(col, row));
			
			rightPanel.add(reactionLabel,           "1, 1, 3, 1, l, c");
			rightPanel.add(curveLabel,              "1, 3, 3, 3, l, c");
			rightPanel.add(curveNameLabel,          "1, 5, 3, 5, l, c");
			rightPanel.add(scaleLabel,              "1, 7, 3, 7, l, c");
			rightPanel.add(scaleComboBox,           "1, 9, 3, 9, f, c");
			rightPanel.add(scaleEFactorRadioButton, "1, 11, 3, 11, l, c");
			rightPanel.add(scaleELabel,             "1, 13, r, c");
			rightPanel.add(scaleEFactorField,       "3, 13, f, c");
			rightPanel.add(scaleEPointRadioButton,  "1, 15, 3, 15, l, c");
			rightPanel.add(originalELabel,          "1, 17, r, c");	
			rightPanel.add(originalEField,          "3, 17, f, c");
			rightPanel.add(originalESliderLabel,    "1, 19, 3, 19, f, c");
			rightPanel.add(originalESlider,         "1, 21, 3, 21, f, c");
			rightPanel.add(finalELabel,             "1, 23, r, c");	
			rightPanel.add(finalEField,             "3, 23, f, c");
			rightPanel.add(finalESliderLabel,       "1, 25, 3, 25, f, c");
			rightPanel.add(finalESlider,            "1, 27, 3, 27, f, c");
			rightPanel.add(curveBoxLabel,           "1, 29, 3, 29, l, c");
			rightPanel.add(curve1Box,               "1, 31, 3, 31, l, c");
			rightPanel.add(curve2Box,               "1, 33, 3, 33, l, c");
		
		}else if(string.equals("Cross Section")
					|| string.equals("S Factor")){
		
			double[] col = {5, TableLayoutConstants.FILL
					, 10, TableLayoutConstants.FILL, 5};
			double[] row = {5, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED, 5};
			
			rightPanel.setLayout(new TableLayout(col, row));
			
			rightPanel.add(reactionLabel,           "1, 1, 3, 1, l, c");
			rightPanel.add(curveLabel,              "1, 3, 3, 3, l, c");
			rightPanel.add(curveNameLabel,          "1, 5, 3, 5, l, c");
			rightPanel.add(scaleLabel,              "1, 7, 3, 7, l, c");
			rightPanel.add(scaleComboBox,           "1, 9, 3, 9, f, c");
			rightPanel.add(scaleYFactorRadioButton, "1, 11, 3, 11, l, c");
			rightPanel.add(scaleYLabel,             "1, 13, r, c");
			rightPanel.add(scaleYFactorField,       "3, 13, f, c");
			rightPanel.add(scaleYPointRadioButton,  "1, 15, 3, 15, l, c");
			rightPanel.add(originalYLabel,          "1, 17, r, c");	
			rightPanel.add(originalYField,          "3, 17, f, c");
			rightPanel.add(originalYLabel2,         "1, 19, r, c");	
			rightPanel.add(originalYField2,         "3, 19, f, c");
			rightPanel.add(originalYSliderLabel,    "1, 21, 3, 21, f, c");
			rightPanel.add(originalYSlider,         "1, 23, 3, 23, f, c");
			rightPanel.add(finalYLabel,             "1, 25, r, c");	
			rightPanel.add(finalYField,             "3, 25, f, c");
			rightPanel.add(finalYSliderLabel,       "1, 27, 3, 27, f, c");
			rightPanel.add(finalYSlider,            "1, 29, 3, 29, f, c");
			rightPanel.add(curveBoxLabel,           "1, 31, 3, 31, l, c");
			rightPanel.add(curve1Box,               "1, 33, 3, 33, l, c");
			rightPanel.add(curve2Box,               "1, 35, 3, 35, l, c");
			
		}
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==originalESlider){
		
			originalEField.setText(NumberFormats.getFormattedValueLong(getOriginalESliderValue()));
	
			if(Double.valueOf(originalEField.getText()).doubleValue()<=Double.valueOf(EmaxField.getText()).doubleValue()
				&& Double.valueOf(originalEField.getText()).doubleValue()>=Double.valueOf(EminField.getText()).doubleValue()){
			
				dataEvalTransDataPlotPanel.setOriginalE(true);
			
			}else{
			
				dataEvalTransDataPlotPanel.setOriginalE(false);
			
			}
			
			redrawPlot();

		}else if(ce.getSource()==finalESlider){
		
			finalEField.setText(NumberFormats.getFormattedValueLong(getFinalESliderValue()));

			redrawPlot();

		}else if(ce.getSource()==originalYSlider){
		
			originalYField.setText(NumberFormats.getFormattedValueLong(getOriginalYSliderValue()));
			originalYField2.setText(NumberFormats.getFormattedValueLong(getYValue(getOriginalYSliderValue())));
			
			if(Double.valueOf(originalYField2.getText()).doubleValue()<=Math.pow(10, Double.valueOf((String)YmaxComboBox.getSelectedItem()).doubleValue())
				&& Double.valueOf(originalYField2.getText()).doubleValue()>=Math.pow(10, Double.valueOf((String)YminComboBox.getSelectedItem()).doubleValue())){
			
				dataEvalTransDataPlotPanel.setOriginalY2(true);
			
			}else{
			
				dataEvalTransDataPlotPanel.setOriginalY2(false);
			
			}
			
			redrawPlot();

		}else if(ce.getSource()==finalYSlider){
		
			finalYField.setText(NumberFormats.getFormattedValueLong(Math.pow(10, getFinalYSliderValue())));

			redrawPlot();
			
		}
	}

	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButton){
		
			PlotSaver.savePlot(dataEvalTransDataPlotPanel, this);
		
		}else if(ae.getSource()==printButton){
		
			PlotPrinter.print(new DataEvalTransDataPlotPrintable(), Cina.dataEvalFrame);
		
		}else if(ae.getSource()==tableButton){
		
			if(!noneChecked()){
       	
	       		if(Cina.dataEvalFrame.dataEvalTransDataTableFrame==null){
	       	
	       			Cina.dataEvalFrame.dataEvalTransDataTableFrame = new DataEvalTransDataTableFrame(ds);
	       		
		       	}else{
		       	
		       		Cina.dataEvalFrame.dataEvalTransDataTableFrame.setTableText();
		       		Cina.dataEvalFrame.dataEvalTransDataTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{

	       		String string = "Please select curves for the Table of Points from the checkbox list."; 
	       		
	       		createExceptionDialog(string, Cina.dataEvalFrame);
	       		
	       	}
		
		}else if(ae.getSource()==scaleButton){
		
			if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& scaleEPointRadioButton.isSelected()){
			
				if(goodOriginalEPointValue() && goodFinalEPointValue()){
					
					if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
					
						saveNucDataButton.setEnabled(true);
					
					}
					
					curve2Box.setEnabled(true);
					curve2Box.setSelected(true);
					
					originalESlider.removeChangeListener(this);
					originalESlider.setValue(setOriginalESliderValue());
					originalESlider.addChangeListener(this);
					
					finalESlider.removeChangeListener(this);
					finalESlider.setValue(setFinalESliderValue());
					finalESlider.addChangeListener(this);
					
					createEPointScaledCurve();
					
				}
			
			}else if(((String)scaleComboBox.getSelectedItem()).equals("Energy")
				&& !scaleEPointRadioButton.isSelected()){

				if(goodEFactorValue()){
					
					if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
					
						saveNucDataButton.setEnabled(true);
					
					}
					
					curve2Box.setEnabled(true);
					curve2Box.setSelected(true);
					
					createEFactorScaledCurve();
					
				}

			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& scaleYPointRadioButton.isSelected()){
			
				if(goodOriginalYPointValue() && goodFinalYPointValue()){
					
					if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
					
						saveNucDataButton.setEnabled(true);
					
					}
					
					curve2Box.setEnabled(true);
					curve2Box.setSelected(true);
					
					originalYSlider.removeChangeListener(this);
					originalYSlider.setValue(setOriginalYSliderValue());
					originalYSlider.addChangeListener(this);
				
					finalYSlider.removeChangeListener(this);
					finalYSlider.setValue(setFinalYSliderValue());
					finalYSlider.addChangeListener(this);
					
					originalYField2.setText(NumberFormats.getFormattedValueLong(getYValue(getOriginalYSliderValue())));
					
					createYPointScaledCurve();
					
				}
			
			}else if((((String)scaleComboBox.getSelectedItem()).equals("Cross Section")
						||((String)scaleComboBox.getSelectedItem()).equals("S Factor"))
				&& !scaleYPointRadioButton.isSelected()){
					
				if(goodYFactorValue()){
					
					if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
					
						saveNucDataButton.setEnabled(true);
					
					}
					
					curve2Box.setEnabled(true);
					curve2Box.setSelected(true);
					
					createYFactorScaledCurve();
					
				}	
			
			}
		
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
			
			finalEField.setText(NumberFormats.getFormattedValueLong(getFinalESliderValue()));
			
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
	 * Creates the e factor scaled curve.
	 */
	public void createEFactorScaledCurve(){
	
		double ERatio = Double.valueOf(scaleEFactorField.getText()).doubleValue();
		
		double[] outputXDataArray = new double[ds.getTrans1NucDataDataStructure().getNumberPoints()];
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
		
			outputXDataArray[i] = ERatio*ds.getTrans1NucDataDataStructure().getXDataArray()[i];
		
		}
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized Cross Section for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized S Factor for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}
		
		ds.getTrans2NucDataDataStructure().setXDataArray(outputXDataArray);
		ds.getTrans2NucDataDataStructure().setYDataArray(ds.getTrans1NucDataDataStructure().getYDataArray());
		ds.getTrans2NucDataDataStructure().setNumberPoints(ds.getTrans1NucDataDataStructure().getNumberPoints());
		
		redrawPlot();
	
	}

	/**
	 * Creates the y factor scaled curve.
	 */
	public void createYFactorScaledCurve(){
	
		double YRatio = Double.valueOf(scaleYFactorField.getText()).doubleValue();
		
		double[] outputYDataArray = new double[ds.getTrans1NucDataDataStructure().getNumberPoints()];
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
		
			outputYDataArray[i] = YRatio*ds.getTrans1NucDataDataStructure().getYDataArray()[i];
		
		}
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized Cross Section for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized S Factor for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}
		ds.getTrans2NucDataDataStructure().setXDataArray(ds.getTrans1NucDataDataStructure().getXDataArray());
		ds.getTrans2NucDataDataStructure().setYDataArray(outputYDataArray);
		ds.getTrans2NucDataDataStructure().setNumberPoints(ds.getTrans1NucDataDataStructure().getNumberPoints());
		
		redrawPlot();
	
	}

	/**
	 * Creates the e point scaled curve.
	 */
	public void createEPointScaledCurve(){
	
		double ERatio = Double.valueOf(this.finalEField.getText()).doubleValue()/Double.valueOf(originalEField.getText()).doubleValue();
		
		double[] outputXDataArray = new double[ds.getTrans1NucDataDataStructure().getNumberPoints()];
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
		
			outputXDataArray[i] = ERatio*ds.getTrans1NucDataDataStructure().getXDataArray()[i];
		
		}
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized Cross Section for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized S Factor for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}
		
		ds.getTrans2NucDataDataStructure().setXDataArray(outputXDataArray);
		ds.getTrans2NucDataDataStructure().setYDataArray(ds.getTrans1NucDataDataStructure().getYDataArray());
		ds.getTrans2NucDataDataStructure().setNumberPoints(ds.getTrans1NucDataDataStructure().getNumberPoints());
		
		redrawPlot();
	
	}

	/**
	 * Creates the y point scaled curve.
	 */
	public void createYPointScaledCurve(){
	
		double YRatio = Double.valueOf(this.finalYField.getText()).doubleValue()/Double.valueOf(originalYField2.getText()).doubleValue();
		
		double[] outputYDataArray = new double[ds.getTrans1NucDataDataStructure().getNumberPoints()];
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getNumberPoints(); i++){
		
			outputYDataArray[i] = YRatio*ds.getTrans1NucDataDataStructure().getYDataArray()[i];
		
		}
		
		if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized Cross Section for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
		
			ds.getTrans2NucDataDataStructure().setNucDataName("Normalized S Factor for "
																									+ ds.getTrans1NucDataDataStructure().getReactionString());
		
		}
		
		ds.getTrans2NucDataDataStructure().setXDataArray(ds.getTrans1NucDataDataStructure().getXDataArray());
		ds.getTrans2NucDataDataStructure().setYDataArray(outputYDataArray);
		ds.getTrans2NucDataDataStructure().setNumberPoints(ds.getTrans1NucDataDataStructure().getNumberPoints());
		
		redrawPlot();
	
	}

	/**
	 * Good e factor value.
	 *
	 * @return true, if successful
	 */
	public boolean goodEFactorValue(){
	
		boolean goodEFactorValue = true;
		
		try{
		
			if(Double.valueOf(scaleEFactorField.getText()).doubleValue()<=0.0){
			
				String string = "Energy Scale Factor must be a positive number greater than zero.";
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodEFactorValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Energy Scale Factor must be a positive number greater than zero.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodEFactorValue = false;
		
		}
		
		return goodEFactorValue;
	
	}

	/**
	 * Good y factor value.
	 *
	 * @return true, if successful
	 */
	public boolean goodYFactorValue(){
	
		boolean goodYFactorValue = true;
		
		try{
		
			if(Double.valueOf(scaleYFactorField.getText()).doubleValue()<=0.0){
			
				String string = "";
			
				if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
			
					string = "Cross Section Scale Factor must be a positive number greater than zero.";
				
				}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
					
					string = "S Factor Scale Factor must be a positive number greater than zero.";
					
				}
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodYFactorValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "";
		
			if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
			
				string = "Cross Section Scale Factor must be a positive number greater than zero.";
			
			}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
				
				string = "S Factor Scale Factor must be a positive number greater than zero.";
				
			}
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodYFactorValue = false;
		
		}
		
		return goodYFactorValue;
	
	}

	/**
	 * Good original e point value.
	 *
	 * @return true, if successful
	 */
	public boolean goodOriginalEPointValue(){
	
		boolean goodOriginalEPointValue = true;
		
		try{
		
			if(Double.valueOf(originalEField.getText()).doubleValue()<ds.getTransXmin()
					|| Double.valueOf(originalEField.getText()).doubleValue()>ds.getTransXmax()){
			
				String string = "Original Energy Value must be a number within the energy range of the original curve.";
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodOriginalEPointValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Original Energy Value must be a number.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodOriginalEPointValue = false;
		
		}
		
		return goodOriginalEPointValue;
	
	}

	/**
	 * Good final e point value.
	 *
	 * @return true, if successful
	 */
	public boolean goodFinalEPointValue(){
	
		boolean goodFinalEPointValue = true;
		
		try{
		
			if(Double.valueOf(finalEField.getText()).doubleValue()<Double.valueOf(EminField.getText()).doubleValue()
					|| Double.valueOf(finalEField.getText()).doubleValue()>Double.valueOf(EmaxField.getText()).doubleValue()){
			
				String string = "New Energy Value must be a number within the energy range of the plot. "
									+ "To increase the energy range of the plot, enter a new minimum and/or maximum "
									+ "energy in the E min and/or E max fields below the plot. Then click the Apply Energy Range Button.";
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodFinalEPointValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "New Energy Value must be a number.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodFinalEPointValue = false;
		
		}
		
		return goodFinalEPointValue;
	
	}

	/**
	 * Good original y point value.
	 *
	 * @return true, if successful
	 */
	public boolean goodOriginalYPointValue(){
	
		boolean goodOriginalYPointValue = true;
		
		try{
		
			if(Double.valueOf(originalYField.getText()).doubleValue()<ds.getTransXmin()
					|| Double.valueOf(originalYField.getText()).doubleValue()>ds.getTransXmax()){
			
				String string = "Original Energy Value must be a number within the energy range of the original curve.";
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodOriginalYPointValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Original Energy Value must be a number.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodOriginalYPointValue = false;
		
		}
		
		return goodOriginalYPointValue;
	
	}

	/**
	 * Good final y point value.
	 *
	 * @return true, if successful
	 */
	public boolean goodFinalYPointValue(){
	
		boolean goodFinalYPointValue = true;
		
		double Ymax = Math.pow(10, Integer.valueOf((String)YmaxComboBox.getSelectedItem()).intValue());
		double Ymin = Math.pow(10, Integer.valueOf((String)YminComboBox.getSelectedItem()).intValue());
		
		try{
		
			if(Double.valueOf(finalYField.getText()).doubleValue()<Ymin
					|| Double.valueOf(finalYField.getText()).doubleValue()>Ymax){
			
				String string = "";
			
				if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
			
					string = "New Cross Section Value must be a number within the cross section range of the plot. "
										+ "To increase the cross section range of the plot, use the dropdown menus "
										+ "below the plot to select a new maximum and/or minimum magnitude for the cross section.";
									
				}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
				
					string = "New S Factor Value must be a number within the S factor range of the plot. "
										+ "To increase the S factor range of the plot, use the dropdown menus "
										+ "below the plot to select a new maximum and/or minimum magnitude for the S factor.";
				
				}
			
				createExceptionDialog(string, Cina.dataEvalFrame);
				
				goodFinalYPointValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "";
			
			if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("CS(E)")){
		
				string = "New Cross Section Value must be a number.";
				
			}else if(ds.getTrans1NucDataDataStructure().getNucDataType().equals("S(E)")){
				
				string = "New S Factor Value must be a number.";
			
			}	
			
			createExceptionDialog(string, Cina.dataEvalFrame);
			
			goodFinalYPointValue = false;
		
		}
		
		return goodFinalYPointValue;
	
	}

	/**
	 * Sets the original e slider value.
	 *
	 * @return the int
	 */
	public int setOriginalESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double originalE = Double.valueOf(originalEField.getText()).doubleValue();
		
		max = ds.getTrans1NucDataDataStructure().getXDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1];
	
		min = ds.getTrans1NucDataDataStructure().getXDataArray()[0];
	
		temp = (int)(1000*((originalE - min)/(max - min)));
		
		return temp;
	
	}

	/**
	 * Sets the final e slider value.
	 *
	 * @return the int
	 */
	public int setFinalESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double finalE = Double.valueOf(finalEField.getText()).doubleValue();
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
		
		min = Double.valueOf(EminField.getText()).doubleValue();
		
		temp = (int)(1000*((finalE - min)/(max - min)));
		
		return temp;
	
	}

	/**
	 * Sets the original y slider value.
	 *
	 * @return the int
	 */
	public int setOriginalYSliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double originalY = Double.valueOf(originalYField.getText()).doubleValue();
		
		max = ds.getTrans1NucDataDataStructure().getXDataArray()[ds.getTrans1NucDataDataStructure().getNumberPoints()-1];
	
		min = ds.getTrans1NucDataDataStructure().getXDataArray()[0];
	
		temp = (int)(1000*((originalY - min)/(max - min)));
		
		return temp;
	
	}

	/**
	 * Sets the final y slider value.
	 *
	 * @return the int
	 */
	public int setFinalYSliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double finalY = log10*Math.log(Double.valueOf(finalYField.getText()).doubleValue());
		
		max = Double.valueOf((String)YmaxComboBox.getSelectedItem()).doubleValue();
		
		min = Double.valueOf((String)YminComboBox.getSelectedItem()).doubleValue();
		
		temp = (int)(1000*((finalY - min)/(max - min)));
		
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
					+ ds.getTrans1NucDataDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getTrans1NucDataDataStructure().getDecayType().equals("none")){
		
			string += ds.getTrans1NucDataDataStructure().getDecay();
		
		}
		
		string += "\u0009"
					+ ds.getTrans1NucDataDataStructure().getNucDataType()
					+ "\u000b"
					+ ds.getTrans2NucDataDataStructure().getNucDataName();
		
		return string;
	
	}
	
	/**
	 * Gets the zA string.
	 *
	 * @return the zA string
	 */
	public String getZAString(){
	
		String string = "";
		
		string = ds.getTrans1NucDataDataStructure().getNucDataID().substring(0, 8);

		return string;
	
	}

	/**
	 * Gets the nuc data properties.
	 *
	 * @return the nuc data properties
	 */
	public String getNucDataProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Points = " + String.valueOf(ds.getTrans2NucDataDataStructure().getNumberPoints()) + "\u0009";
		propertiesString += "Nuc Data Notes = " + String.valueOf(ds.getTrans2NucDataDataStructure().getNucDataNotes()) + "\u0009";
		propertiesString += "Reaction String = " + ds.getTrans1NucDataDataStructure().getReactionString() + "\u0009";
		propertiesString += "Nuc Data Name = " + ds.getTrans2NucDataDataStructure().getNucDataName() + "\u0009";
		propertiesString += "Nuc Data Type = " + ds.getTrans1NucDataDataStructure().getNucDataType() + "\u0009";
		
		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getTrans1NucDataDataStructure().getReactionType());
		
		if(!ds.getTrans1NucDataDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getTrans1NucDataDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Reference Citation = " + ds.getTrans2NucDataDataStructure().getRefCitation() + "\u0009";
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
		
		for(int i=0; i<ds.getTrans2NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){string += String.valueOf(ds.getTrans2NucDataDataStructure().getXDataArray()[i]);
			
			}else{string += "," + String.valueOf(ds.getTrans2NucDataDataStructure().getXDataArray()[i]);}
		
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
		
		for(int i=0; i<ds.getTrans2NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){string += String.valueOf(ds.getTrans2NucDataDataStructure().getYDataArray()[i]);
			
			}else{string += "," + String.valueOf(ds.getTrans2NucDataDataStructure().getYDataArray()[i]);}
		
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
	
		if(!newNucDataSetField.getText().equals("")){
		
			goodName = true;
		
		}
	
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
				
				dataEvalTransDataPlotPanel.setXmin(Double.valueOf(EminField.getText()).doubleValue());
				dataEvalTransDataPlotPanel.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());

				dataEvalTransDataPlotPanel.setPlotState();

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
		
		ds.setTransXmax(setXmax());
		ds.setTransXmin(setXmin());
		
		ds.setTransYmax(setYmax());
		ds.setTransYmin(setYmin());
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YminComboBox.removeItemListener(this);
		YmaxComboBox.removeItemListener(this);

		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

		YminComboBox.addItemListener(this);
		YmaxComboBox.addItemListener(this);

		EminField.setText(String.valueOf(ds.getTransXmin()));
		EmaxField.setText(String.valueOf(ds.getTransXmax()));

		curveNameLabel.setText(ds.getTrans1NucDataDataStructure().getNucDataName());
									
		curveLabel.setText("Curve from "
								+ ds.getTrans1NucDataDataStructure().getNucDataSet()
								+ " Data Set: ");
		
		if(ds.getTrans1NucDataDataStructure().getDecay().equals("")){
								
			reactionLabel.setText("Reaction: " + ds.getTrans1NucDataDataStructure().getReactionString());
		
		}else{
		
			reactionLabel.setText("Reaction: " 
									+ ds.getTrans1NucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getTrans1NucDataDataStructure().getDecay()
									+ "]");
		
		}

		redrawPlot();
		
	}
	
	/**
	 * Sets the xmin.
	 *
	 * @return the double
	 */
	public double setXmin(){
		
		double newXmin = 1e30;
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmin = Math.min(newXmin, ds.getTrans1NucDataDataStructure().getXDataArray()[i]);
		
		}
		
		return newXmin;
	
	}
	
	/**
	 * Sets the xmax.
	 *
	 * @return the double
	 */
	public double setXmax(){
		
		double newXmax = 0.0;
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmax = Math.max(newXmax, ds.getTrans1NucDataDataStructure().getXDataArray()[i]);
		
		}
	
		return newXmax;
	
	}	
	
	/**
	 * Sets the ymin.
	 *
	 * @return the double
	 */
	public double setYmin(){
		
		double newYmin = 1e30;
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmin = Math.min(newYmin, ds.getTrans1NucDataDataStructure().getYDataArray()[i]);
		
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
		
		for(int i=0; i<ds.getTrans1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmax = Math.max(newYmax, ds.getTrans1NucDataDataStructure().getYDataArray()[i]);
		
		}
		
		return newYmax;
	
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

class DataEvalTransDataPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.dataEvalFrame.dataEvalTransData2Panel.dataEvalTransDataPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}