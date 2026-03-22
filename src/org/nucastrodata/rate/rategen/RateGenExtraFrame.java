package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rategen.RateGenExtraPlotPanel;
import org.nucastrodata.rate.rategen.RateGenExtraPlotPrintable;
import org.nucastrodata.rate.rategen.RateGenExtraTableFrame;


/**
 * The Class RateGenExtraFrame.
 */
public class RateGenExtraFrame extends JFrame implements ActionListener
															, ItemListener
															, ChangeListener{

	/** The log10. */
	private final double log10 = 0.434294482;

	/** The rate gen extra plot panel. */
	protected RateGenExtraPlotPanel rateGenExtraPlotPanel;
    
    /** The curve2 box. */
    protected JCheckBox curve1Box, curve2Box;
    
    /** The E range min field. */
    protected JTextField lowEField, highEField, numPointsHighEField
						, numPointsLowEField, maxThreshField, ERangeMaxField
						, ERangeMinField;
	
	/** The temp2. */
	protected NucDataDataStructure temp1, temp2;
	
	/** The Emax field. */
	protected JTextField EminField, EmaxField;
	
    /** The no button. */
    private JButton saveButton, printButton, tableButton, extraButton
						, submitNucDataButton, applyButton, applyDerButton
						, yesButton, noButton;
    
    /** The energy combo box. */
    private JComboBox energyComboBox;
    
    /** The temp range panel. */
    private JPanel rightPanel, tempRangePanel;
    
    /** The num points low e slider. */
    private JSlider highESlider, lowESlider, numPointsHighESlider
						, numPointsLowESlider;
	
	/** The dash label. */
	private JLabel rateLabel, reactionLabel, plotControlsLabel, energyLabel, lowELabel
				, highELabel, highESliderLabel, lowESliderLabel, curveBoxLabel
				, numPointsHighELabel, numPointsLowELabel
				, numPointsHighESliderLabel, numPointsLowESliderLabel
				, maxThresLabel, tempRangeLabel, dashLabel;
	
	/** The Y data array original. */
	private double[] XDataArrayOriginal, YDataArrayOriginal;
	
	/** The Ymax combo box. */
	private JComboBox YminComboBox, YmaxComboBox;
	
	/** The label array. */
	private JLabel[] labelArray = new JLabel[7];
	
	/** The string array. */
	private String[] stringArray = {"Autoscale E axis"
								,"Set Scale"
								, "Enter E min: " 
								, "Enter E max: "
								, "log Cross Sec min: "
								, "log Cross Sec max: "
								, "log S Factor min: "
								, "log S Factor max: "
								, "to max and min?"};
	
	/** The minor x box. */
	private JCheckBox majorYBox, majorXBox, minorYBox, minorXBox;
	
	/** The Ymin combo box init. */
	private int YminComboBoxInit = 0;
    
    /** The Ymax combo box init. */
    private int YmaxComboBoxInit = 0;
	
	/** The caution dialog. */
	private JDialog cautionDialog;
	
	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The ds. */
	private RateGenDataStructure ds;

	//Constructor					
	/**
	 * Instantiates a new rate gen extra frame.
	 *
	 * @param ds the ds
	 */
	public RateGenExtraFrame(RateGenDataStructure ds){
	
		this.ds = ds;
	
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout());
		gbc = new GridBagConstraints();
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {   
				if(submitNucDataButton.isEnabled()){
					String string = "Would you like to submit the extrapolated curve to the Rate Generator?";
					createCautionDialog(string, (Frame)we.getWindow());
				}else{
					setVisible(false);
					dispose();
				}	
		    } 
		});
		
		temp1 = new NucDataDataStructure();
		temp2 = new NucDataDataStructure();
		
		temp1.setXDataArray(ds.getEnergyDataArray());
		
		if(ds.getInputType().equals("CS(E)")){
		
			temp1.setYDataArray(ds.getCrossSectionDataArray());
		
		}else if(ds.getInputType().equals("S(E)")){
		
			temp1.setYDataArray(ds.getSFactorDataArray());
		
		}
		
		temp1.setNumberPoints(ds.getNumberPoints());
		
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
		
		if(ds.getInputType().equals("CS(E)")){
		
			submitNucDataButton = new JButton("Submit Cross Section");
		
		}else if(ds.getInputType().equals("S(E)")){
		
			submitNucDataButton = new JButton("Submit S Factor");
		
		}
		
		submitNucDataButton.setFont(Fonts.buttonFont);
		submitNucDataButton.addActionListener(this);
		submitNucDataButton.setEnabled(false);
		
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
        for(int i=19; i>=-20; i--) {YminComboBox.addItem(Integer.toString(i));}
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=20; i>=-19; i--) {YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.addItemListener(this);
		
		EminField = new JTextField(5);
		EmaxField = new JTextField(5);

		EminField.setText(String.valueOf(ds.getEmin() - 0.5*ds.getEmin()));
		EmaxField.setText(String.valueOf(ds.getEmax() + 0.5*ds.getEmax()));

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
		
		//LABELS////////////////////////////////////////////////////////////////////LABELS//////////////////////
		
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

		if(ds.getInputType().equals("CS(E)")){
		
			labelArray[4] = new JLabel(stringArray[4]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[5]);
			labelArray[5].setFont(Fonts.textFont);
			
		}else if(ds.getInputType().equals("S(E)")){
		
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
		
		tempRangeLabel = new JLabel("Widest Temperature Range (T9): ");
		tempRangeLabel.setFont(Fonts.textFont);
		
		dashLabel = new JLabel("-");
		dashLabel.setFont(Fonts.textFont);
		
		//SLIDERS////////////////////////////////////////////////////////////////////SLIDERS///////////////////////
		highESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		highESlider.addChangeListener(this);
		
		lowESlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		lowESlider.addChangeListener(this); 
		
		numPointsHighESlider = new JSlider(JSlider.HORIZONTAL
												, 2
												, temp1.getNumberPoints()-1
												, 2);
												
		numPointsHighESlider.addChangeListener(this);
		numPointsHighESlider.setSnapToTicks(true);
		
		numPointsLowESlider = new JSlider(JSlider.HORIZONTAL
												, 2
												, temp1.getNumberPoints()-1
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
		
		ERangeMaxField = new JTextField(3);
		ERangeMaxField.setEditable(false);
		ERangeMaxField.setText(String.valueOf(ds.getAllowedTempmax()));
		
		ERangeMinField = new JTextField(3);
		ERangeMinField.setEditable(false);
		ERangeMinField.setText(String.valueOf(ds.getAllowedTempmin()));
		
		//PANELS/////////////////////////////////////////////////////////////////////PANELS///////////////////////
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(extraButton);
		buttonPanel.add(submitNucDataButton);
		
		tempRangePanel = new JPanel(new FlowLayout());
		tempRangePanel.add(ERangeMinField);
		tempRangePanel.add(dashLabel);
		tempRangePanel.add(ERangeMaxField);
		
		//PLOT///////////////////////////////////////////////////////////////////////PLOT///////////////////////////
		rateGenExtraPlotPanel = new RateGenExtraPlotPanel(YminComboBoxInit
															, YmaxComboBoxInit
															, ds);
		rateGenExtraPlotPanel.setPreferredSize(rateGenExtraPlotPanel.getSize());
		rateGenExtraPlotPanel.revalidate();
		
		JScrollPane sp = new JScrollPane(rateGenExtraPlotPanel);
		JViewport vp = new JViewport();
		vp.setView(rateGenExtraPlotPanel);
		sp.setViewport(vp);

		rateGenExtraPlotPanel.setPreferredSize(rateGenExtraPlotPanel.getSize());
		rateGenExtraPlotPanel.revalidate();
		
		JPanel scaleTitlePanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		scaleTitlePanel.add(plotControlsLabel, gbc);
		
		JPanel setScalePanel = new JPanel(new GridBagLayout());
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
	
		JPanel formatPanel = new JPanel(new GridBagLayout());
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
		rightPanel.add(energyLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(energyComboBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(highELabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(highEField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(highESliderLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(highESlider, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(numPointsHighELabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(numPointsHighEField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(numPointsHighESliderLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(numPointsHighESlider, gbc);
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(maxThresLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(maxThreshField, gbc);
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(applyDerButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(tempRangeLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(tempRangePanel, gbc);
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
		c.add(mainPanel, BorderLayout.CENTER);
		
		
		validate();
		
	}

	/**
	 * Gets the high e slider value.
	 *
	 * @return the high e slider value
	 */
	private double getHighESliderValue(){
	
		double temp = 0.0;
		double min = 0.0;
		double max = 0.0;
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
		min = temp1.getXDataArray()[temp1.getNumberPoints()-1];
		temp = min + (((double)highESlider.getValue())/1000.0)*(max-min);

		return temp;
	
	}

	/**
	 * Gets the low e slider value.
	 *
	 * @return the low e slider value
	 */
	private double getLowESliderValue(){
	
		double temp = 0.0;
		double min = 0.0;
		double max = 0.0;
		
		max = temp1.getXDataArray()[0];
		min = Double.valueOf(EminField.getText()).doubleValue();
		temp = min + (((double)lowESlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}

	/**
	 * Sets the initial ymin combo box init.
	 *
	 * @return the int
	 */
	private int setInitialYminComboBoxInit(){
	
		int min = 0;
		double currentYmin = 0.0;
		
		if(ds.getInputType().equals("CS(E)")){
			currentYmin = ds.getCrossSectionmin();
		}else if(ds.getInputType().equals("S(E)")){
			currentYmin = ds.getSFactormin();
		}
	
		min = (int)Math.floor(0.434294482*Math.log(currentYmin));
	
		if(min < -20){min = -20;}
		
		return min;
	
	}
	
	/**
	 * Sets the initial ymax combo box init.
	 *
	 * @return the int
	 */
	private int setInitialYmaxComboBoxInit(){
	
		int max = 0;
		double currentYmax = 0.0;
		
		if(ds.getInputType().equals("CS(E)")){
			currentYmax = ds.getCrossSectionmax();
		}else if(ds.getInputType().equals("S(E)")){
			currentYmax = ds.getSFactormax();
		}
	
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 20){max = 20;}

		return max;
	
	}

	/**
	 * Gets the slope array.
	 *
	 * @return the slope array
	 */
	private double[] getSlopeArray(){
		
		double[] slopeArray = new double[temp1.getNumberPoints()-1];
	
		for(int i=0; i<slopeArray.length; i++){
		
			double lowXPoint = temp1.getXDataArray()[i];
			double highXPoint = temp1.getXDataArray()[i+1];

			double lowYPoint = log10*Math.log(temp1.getYDataArray()[i]);
			double highYPoint = log10*Math.log(temp1.getYDataArray()[i+1]);
				
			slopeArray[i] = (highYPoint - lowYPoint)/(highXPoint - lowXPoint);	
		
		}
	
		return slopeArray;
	
	}

	/**
	 * Gets the number high e points max.
	 *
	 * @return the number high e points max
	 */
	private int getNumberHighEPointsMax(){return Math.min(getMaxHighESlopePoints(), getMaxHighEDerPoints());}
	
	/**
	 * Gets the number low e points max.
	 *
	 * @return the number low e points max
	 */
	private int getNumberLowEPointsMax(){return Math.min(getMaxLowESlopePoints(), getMaxLowEDerPoints());}

	/**
	 * Gets the max high e slope points.
	 *
	 * @return the max high e slope points
	 */
	private int getMaxHighESlopePoints(){
	
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
	private int getMaxHighEDerPoints(){
	
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
	 * Gets the max low e slope points.
	 *
	 * @return the max low e slope points
	 */
	private int getMaxLowESlopePoints(){
	
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
	private int getMaxLowEDerPoints(){
	
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
       			
       			if(ds.getInputType().equals("CS(E)")){
       			
       				string = "Cross section minimum must be less than cross section maximum.";
       			
       			}else if(ds.getInputType().equals("S(E)")){
       			
       				string = "S factor minimum must be less than S factor maximum.";
       			
       			}
       				
       			Dialogs.createExceptionDialog(string, this);
       			
       			YminComboBox.removeItemListener(this);
       			YminComboBox.setSelectedItem("-10");
       			YminComboBox.addItemListener(this);
       			
       			redrawPlot();
       			
			}else{
			
				redrawPlot();
			
			}
			
		}else if(ie.getSource()==energyComboBox){
				
			setRightPanelFormat((String)energyComboBox.getSelectedItem());
			
			double EmaxValue = Double.valueOf(EmaxField.getText()).doubleValue();
			double EminValue = Double.valueOf(EminField.getText()).doubleValue();
			
			temp2.setNumberPoints(0);
			
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
			
				if(EmaxValue<=temp1.getXDataArray()[temp1.getNumberPoints()-1]){
						
					highESlider.setEnabled(false);
					highEField.setText("NOT IN DOMAIN");
				
					rateGenExtraPlotPanel.setHighE(false);
				
				}else if(EmaxValue>temp1.getXDataArray()[temp1.getNumberPoints()-1]){
				
					highESlider.setEnabled(true);
					highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
	
					rateGenExtraPlotPanel.setHighE(true);
	
				}
			
			}
			
			if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
			
				if(EminValue>=temp1.getXDataArray()[0]){
				
					lowESlider.setEnabled(false);
					lowEField.setText("NOT IN DOMAIN");
				
					rateGenExtraPlotPanel.setLowE(false);
				
				}else if(EminValue<temp1.getXDataArray()[0]){
				
					lowESlider.setEnabled(true);
					lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
	
					rateGenExtraPlotPanel.setLowE(true);
	
				}
			
			}
			
			curve2Box.setSelected(false);
			curve2Box.setEnabled(false);
			
			submitNucDataButton.setEnabled(false);
			
			
			
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
		}else if(ce.getSource()==lowESlider){
			lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
		}else if(ce.getSource()==numPointsHighESlider){
			numPointsHighEField.setText(String.valueOf(numPointsHighESlider.getValue()));
		}else if(ce.getSource()==numPointsLowESlider){
			numPointsLowEField.setText(String.valueOf(numPointsLowESlider.getValue()));
		}
		
		redrawPlot();
	
	}

	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButton){
		
			PlotSaver.savePlot(rateGenExtraPlotPanel, this);
		
		}else if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateGenExtraPlotPrintable(), this);
		
		}else if(ae.getSource()==tableButton){
		
			if(!noneChecked()){
       	
	       		if(Cina.rateGenFrame.rateGenExtraTableFrame==null){
	       	
	       			Cina.rateGenFrame.rateGenExtraTableFrame = new RateGenExtraTableFrame(ds);
	       		
		       	}else{
		       	
		       		Cina.rateGenFrame.rateGenExtraTableFrame.setTableText();
		       		Cina.rateGenFrame.rateGenExtraTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{

	       		String string = "Please select curves for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
		
		}else if(ae.getSource()==extraButton){
		
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
		
				if(goodHighEValue()){createHighEExtraCurve();}
		
			}else if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
		
				if(goodLowEValue()){createLowEExtraCurve();}
		
			}

			if(readFileSubmit()){
			
				if(preprocess()){
				
					ERangeMinField.setText(String.valueOf(ds.getAllowedTempmin()));
					ERangeMaxField.setText(String.valueOf(ds.getAllowedTempmax()));
					
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
		
		}else if(ae.getSource()==submitNucDataButton){
			
			Cina.rateGenFrame.rateGenInputWarningsPanel.setCurrentState();
			Cina.rateGenFrame.rateGenInputWarningsPanel.validate();
			Cina.rateGenFrame.validate();
			Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()-1));
			Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()+1));
			setVisible(false);
			dispose();
		
		}else if(ae.getSource()==applyButton){
			
			double EmaxValue = Double.valueOf(EmaxField.getText()).doubleValue();
			double EminValue = Double.valueOf(EminField.getText()).doubleValue();
			
			if(((String)energyComboBox.getSelectedItem()).equals("High Energy")){
			
				if(EmaxValue<=temp1.getXDataArray()[temp1.getNumberPoints()-1]){
						
					highESlider.setEnabled(false);
					highEField.setText("NOT IN DOMAIN");
				
					rateGenExtraPlotPanel.setHighE(false);
				
				}else if(EmaxValue>temp1.getXDataArray()[temp1.getNumberPoints()-1]){
				
					highESlider.setEnabled(true);
					highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
	
					rateGenExtraPlotPanel.setHighE(true);
	
				}
			
			}
			
			if(((String)energyComboBox.getSelectedItem()).equals("Low Energy")){
			
				if(EminValue>=temp1.getXDataArray()[0]){
				
					lowESlider.setEnabled(false);
					lowEField.setText("NOT IN DOMAIN");
				
					rateGenExtraPlotPanel.setLowE(false);
				
				}else if(EminValue<temp1.getXDataArray()[0]){
				
					lowESlider.setEnabled(true);
					lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));
	
					rateGenExtraPlotPanel.setLowE(true);
	
				}
			
			}
						
			redrawPlot();
			
		}else if(ae.getSource()==yesButton){
		
			Cina.rateGenFrame.rateGenInputWarningsPanel.setCurrentState();
			Cina.rateGenFrame.rateGenInputWarningsPanel.validate();
			Cina.rateGenFrame.validate();
			Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()-1));
			Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()+1));

			cautionDialog.setVisible(false);
			cautionDialog.dispose();

			setVisible(false);
			dispose();
		
		}else if(ae.getSource()==noButton){
		
			if(readFileOriginal()){
			
				if(preprocess()){
				
					Cina.rateGenFrame.rateGenInputWarningsPanel.setCurrentState();
					Cina.rateGenFrame.rateGenInputWarningsPanel.validate();
					Cina.rateGenFrame.validate();
					Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()-1));
					Cina.rateGenFrame.setSize(new Dimension((int)Cina.rateGenFrame.getSize().getWidth(), (int)Cina.rateGenFrame.getSize().getHeight()+1));
	
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
	
					setVisible(false);
					dispose();
					
				}
			
			}

		}

	}

	/**
	 * Gets the file string submit.
	 *
	 * @return the file string submit
	 */
	private String getFileStringSubmit(){
	
		String string = "";
		
		for(int i=0; i<temp2.getNumberPoints(); i++){
		
			if(i==0){
			
				string = NumberFormats.getFormattedValueLong(temp2.getXDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(temp2.getYDataArray()[i]);
			
			}else{
			
				string += "\n"
							+ NumberFormats.getFormattedValueLong(temp2.getXDataArray()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(temp2.getYDataArray()[i]);
			
			}
		
		}
	
		return string;
	
	}

	/**
	 * Gets the file string original.
	 *
	 * @return the file string original
	 */
	private String getFileStringOriginal(){
	
		String string = "";
		
		for(int i=0; i<getXDataArrayOriginal().length; i++){
		
			if(i==0){
			
				string = NumberFormats.getFormattedValueLong(getXDataArrayOriginal()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(getYDataArrayOriginal()[i]);
			
			}else{
			
				string += "\n"
							+ NumberFormats.getFormattedValueLong(getXDataArrayOriginal()[i])
							+ " "
							+ NumberFormats.getFormattedValueLong(getYDataArrayOriginal()[i]);
			
			}
		
		}

		return string;
	
	}

	/**
	 * Read file submit.
	 *
	 * @return true, if successful
	 */
	private boolean readFileSubmit(){
		
		ds.setXunits("KEV");
		
		if(ds.getInputType().equals("CS(E)")){
			ds.setYunits("B");
		}else if(ds.getInputType().equals("S(E)")){
			ds.setYunits("KEV-B");
		}

		ds.setFile(getFileStringSubmit());
		ds.setReaction_type(ds.getRateDataStructure().getDecay());
		
		return Cina.cinaCGIComm.doCGICall("READ INPUT", this);
	
	}

	/**
	 * Read file original.
	 *
	 * @return true, if successful
	 */
	private boolean readFileOriginal(){
		
		ds.setXunits("KEV");
		
		if(ds.getInputType().equals("CS(E)")){
			ds.setYunits("B");
		}else if(ds.getInputType().equals("S(E)")){
			ds.setYunits("KEV-B");
		}

		ds.setFile(getFileStringOriginal());
		ds.setReaction_type(ds.getRateDataStructure().getDecay());
		
		return Cina.cinaCGIComm.doCGICall("READ INPUT", this);
	
	}

	/**
	 * Sets the x data array original.
	 *
	 * @param newXDataArrayOriginal the new x data array original
	 */
	private void setXDataArrayOriginal(double[] newXDataArrayOriginal){XDataArrayOriginal = newXDataArrayOriginal;}
	
	/**
	 * Gets the x data array original.
	 *
	 * @return the x data array original
	 */
	private double[] getXDataArrayOriginal(){return XDataArrayOriginal;}

	/**
	 * Sets the y data array original.
	 *
	 * @param newYDataArrayOriginal the new y data array original
	 */
	private void setYDataArrayOriginal(double[] newYDataArrayOriginal){YDataArrayOriginal = newYDataArrayOriginal;}
	
	/**
	 * Gets the y data array original.
	 *
	 * @return the y data array original
	 */
	private double[] getYDataArrayOriginal(){return YDataArrayOriginal;}

	/**
	 * Preprocess.
	 *
	 * @return true, if successful
	 */
	private boolean preprocess(){
		return Cina.cinaCGIComm.doCGICall("PREPROCESSING", this);
	}

	/**
	 * Creates the high e extra curve.
	 */
	private void createHighEExtraCurve(){
	
		double highEPoint = Double.valueOf(highEField.getText()).doubleValue();
		double lowEPoint = temp1.getXDataArray()[temp1.getNumberPoints()-1];
		
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

		outputYDataArray[0] = log10*Math.log(temp1.getYDataArray()[temp1.getNumberPoints()-1]); 

		outputYDataArray[1] = -slopeAverage
								*(temp1.getXDataArray()[temp1.getNumberPoints()-1]
								- outputXDataArray[1]) 
								+ log10*Math.log(temp1.getYDataArray()[temp1.getNumberPoints()-1]); 
		
		double[] fullOutputXDataArray = new double[1 + temp1.getNumberPoints()];
		double[] fullOutputYDataArray = new double[1 + temp1.getNumberPoints()];
		
		for(int i=0; i<temp1.getNumberPoints(); i++){
		
			fullOutputXDataArray[i] = temp1.getXDataArray()[i];
			fullOutputYDataArray[i] = temp1.getYDataArray()[i];
		
		}
		
		fullOutputXDataArray[temp1.getNumberPoints()] = outputXDataArray[1];
		fullOutputYDataArray[temp1.getNumberPoints()] = Math.pow(10, outputYDataArray[1]);
		
		temp2.setXDataArray(fullOutputXDataArray);
		temp2.setYDataArray(fullOutputYDataArray);
		temp2.setNumberPoints(temp2.getXDataArray().length);
				
		submitNucDataButton.setEnabled(true);
		
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
	private void createLowEExtraCurve(){
	
		double highEPoint = temp1.getXDataArray()[0];
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

		outputYDataArray[1] = log10*Math.log(temp1.getYDataArray()[0]); 

		outputYDataArray[0] = -slopeAverage
								*(highEPoint
								- lowEPoint) 
								+ log10*Math.log(temp1.getYDataArray()[0]); 

		double[] fullOutputXDataArray = new double[1 + temp1.getNumberPoints()];
		double[] fullOutputYDataArray = new double[1 + temp1.getNumberPoints()];
		
		for(int i=1; i<temp1.getNumberPoints()+1; i++){
		
			fullOutputXDataArray[i] = temp1.getXDataArray()[i-1];
			fullOutputYDataArray[i] = temp1.getYDataArray()[i-1];
		
		}
		
		fullOutputXDataArray[0] = outputXDataArray[0];
		fullOutputYDataArray[0] = Math.pow(10, outputYDataArray[0]);
		
		temp2.setXDataArray(fullOutputXDataArray);
		temp2.setYDataArray(fullOutputYDataArray);
		temp2.setNumberPoints(temp2.getXDataArray().length);
		
		submitNucDataButton.setEnabled(true);

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
	private boolean goodHighEValue(){
	
		boolean goodHighEValue = false;
		
		try{
		
			double highE = Double.valueOf(highEField.getText()).doubleValue();
			
			if(highE<=Double.valueOf(EmaxField.getText()).doubleValue()
					&& highE>=temp1.getXDataArray()[temp1.getNumberPoints()-1]){
			
				goodHighEValue = true;
			
			}else{
			
				String string = "New high energy value must be less than " 
									+ EmaxField.getText()
									+ " and greater than "
									+ String.valueOf(temp1.getXDataArray()[temp1.getNumberPoints()-1])
									+ ". Increase the energy range of the plot to include new energy value.";
				
				Dialogs.createExceptionDialog(string, this);
			
				goodHighEValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "New high energy value must be a positive number.";
			
			Dialogs.createExceptionDialog(string, this);
			
			goodHighEValue = false;
		
		}

		return goodHighEValue;
	
	}

	/**
	 * Sets the high e slider value.
	 *
	 * @return the int
	 */
	private int setHighESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		double highE = Double.valueOf(highEField.getText()).doubleValue();
		
		max = Double.valueOf(EmaxField.getText()).doubleValue();
		min = temp1.getXDataArray()[temp1.getNumberPoints()-1];
		temp = (int)(1000*((highE - min)/(max - min)));
		
		return temp;
	
	}

	/**
	 * Good low e value.
	 *
	 * @return true, if successful
	 */
	private boolean goodLowEValue(){
	
		boolean goodLowEValue = false;
		
		try{
		
			double lowE = Double.valueOf(lowEField.getText()).doubleValue();

			if(lowE>=Double.valueOf(EminField.getText()).doubleValue()
					&& lowE<=temp1.getXDataArray()[0]){
				goodLowEValue = true;
			
			}else{
			
				String string = "New low energy value must be greater than " 
									+ EminField.getText()
									+ " and less than "
									+ String.valueOf(temp1.getXDataArray()[0])
									+ ". Increase the energy range of the plot to include new energy value.";
				
				Dialogs.createExceptionDialog(string, this);
			
				goodLowEValue = false;
			
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "New high energy value must be a positive number.";
			Dialogs.createExceptionDialog(string, this);
			
			goodLowEValue = false;
		
		}

		return goodLowEValue;
	
	}
	
	/**
	 * Sets the low e slider value.
	 *
	 * @return the int
	 */
	private int setLowESliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		double lowE = Double.valueOf(lowEField.getText()).doubleValue();
		
		max = temp1.getXDataArray()[0];
		min = Double.valueOf(EminField.getText()).doubleValue();
		temp = (int)(1000*((lowE - min)/(max - min)));
		
		return temp;
	
	}
	
	/**
	 * None checked.
	 *
	 * @return true, if successful
	 */
	private boolean noneChecked(){
    
    	if(curve1Box.isSelected() || curve2Box.isSelected()){
    		return false;
    	}else{
    		return true;
    	}

    }

	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	protected double getYmin(){return Double.valueOf((String)YminComboBox.getSelectedItem()).doubleValue();}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	protected double getYmax(){return Double.valueOf((String)YmaxComboBox.getSelectedItem()).doubleValue();}

	/**
	 * Redraw plot.
	 */
	private void redrawPlot(){
	
		String string = "";
				
		try{
	
			if((EminField.getText().equals("") || EmaxField.getText().equals(""))){

				string = "One or more fields for the energy range are empty. \nPlease enter numeric values.";

				Dialogs.createExceptionDialog(string, this);
				
			}else if((Double.valueOf(EminField.getText()).doubleValue() >= Double.valueOf(EmaxField.getText()).doubleValue())){

				string = "Energy minimum must be less than the energy maximum.";
				Dialogs.createExceptionDialog(string, this);
		
			}else{
				
				rateGenExtraPlotPanel.setXmin(Double.valueOf(EminField.getText()).doubleValue());
				rateGenExtraPlotPanel.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());

				rateGenExtraPlotPanel.setPlotState();

			}
			
		}catch(NumberFormatException nfe){
				
			string = "Values for Emin and Emax must be numeric values.";		
			Dialogs.createExceptionDialog(string, this);
				
		}

	}

	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	//Method to set the current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YminComboBox.removeItemListener(this);
		YmaxComboBox.removeItemListener(this);

		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

		YminComboBox.addItemListener(this);
		YmaxComboBox.addItemListener(this);

		EminField.setText(String.valueOf(ds.getEmin() - 0.5*ds.getEmin()));
		EmaxField.setText(String.valueOf(ds.getEmax() + 0.5*ds.getEmax()));

		if(ds.getRateDataStructure().getDecay().equals("")){
								
			reactionLabel.setText("Reaction: " + ds.getRateDataStructure().getReactionString());
		
		}else{
		
			reactionLabel.setText("Reaction: " 
									+ ds.getRateDataStructure().getReactionString()
									+ " ["
									+ ds.getRateDataStructure().getDecay()
									+ "]");
		
		}

		temp1 = new NucDataDataStructure();
		temp2 = new NucDataDataStructure();
		
		temp1.setXDataArray(ds.getEnergyDataArray());
		
		if(ds.getInputType().equals("CS(E)")){
		
			temp1.setYDataArray(ds.getCrossSectionDataArray());
		
		}else if(ds.getInputType().equals("S(E)")){
		
			temp1.setYDataArray(ds.getSFactorDataArray());
		
		}
		
		temp1.setNumberPoints(ds.getNumberPoints());
		
		setXDataArrayOriginal(temp1.getXDataArray());
		setYDataArrayOriginal(temp1.getYDataArray());
		
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

		curve2Box.setSelected(false);
		curve2Box.setEnabled(false);
		
		submitNucDataButton.setEnabled(false);

		highEField.setText(NumberFormats.getFormattedValueLong(getHighESliderValue()));
		lowEField.setText(NumberFormats.getFormattedValueLong(getLowESliderValue()));

		if(ds.getInputType().equals("CS(E)")){
		
			energyComboBox.setEnabled(false);
			
			//energyComboBox.removeItemListener(this);
			energyComboBox.setSelectedItem("High Energy");
			//energyComboBox.addItemListener(this);
			
			String string = "Extrapolation of cross sections to high energies is available. However, in order to extrapolate cross sections to low energies"
																+ ", please convert the cross section to an S factor by using the S Factor Converter tool "
																+ "of the Nuclear Data Evaluator's Toolkit. Then "
																+ "use the S Factor Converter tool to convert the S factor back to a cross section.";
			
			Dialogs.createExceptionDialog(string, Cina.rateGenFrame);
		
		}else{
		
			energyComboBox.setEnabled(true);
		
		}

		redrawPlot();
		
	}
	
	/**
	 * Sets the right panel format.
	 *
	 * @param string the new right panel format
	 */
	private void setRightPanelFormat(String string){
	
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
		rightPanel.add(energyLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(energyComboBox, gbc);
	
		if(string.equals("High Energy")){

			maxThresLabel.setText("<html>Maximum allowed change in the<p>derivative at high energy value :</html>");

			rateGenExtraPlotPanel.setLowE(false);
			rateGenExtraPlotPanel.setHighE(true);

			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(highELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(highEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(highESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(highESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsHighELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsHighEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsHighESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsHighESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 9;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThresLabel, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 9;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThreshField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(applyDerButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(tempRangeLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(tempRangePanel, gbc);
			
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
			
			rateGenExtraPlotPanel.setLowE(true);
			rateGenExtraPlotPanel.setHighE(false);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(lowELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(lowEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(lowESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(lowESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsLowELabel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 6;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(numPointsLowEField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsLowESliderLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(numPointsLowESlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 9;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThresLabel, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 9;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(maxThreshField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(applyDerButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(tempRangeLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(tempRangePanel, gbc);
			
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
		
		//Cina.setFrameColors(cautionDialog);
		
		cautionDialog.setVisible(true);

	}

}

class RateGenExtraPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.rateGenFrame.rateGenExtraFrame.rateGenExtraPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}