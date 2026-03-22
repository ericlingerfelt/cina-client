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
import org.nucastrodata.data.dataeval.DataEvalInterDataPlotPanel;
import org.nucastrodata.data.dataeval.DataEvalInterDataPlotPrintable;
import org.nucastrodata.data.dataeval.DataEvalInterDataTableFrame;


//This class creates the content for the second panel of the about rate sub feature of the rate man feature
/**
 * The Class DataEvalInterData3Panel.
 */
public class DataEvalInterData3Panel extends WizardPanel implements ActionListener, ItemListener, ChangeListener{

	/** The log10. */
	private final double log10 = 0.434294482;

	//Declare panels
	/** The right panel. */
	JPanel mainPanel, buttonPanel, formatPanel, scalePanel, setScalePanel, scaleTitlePanel, rightPanel;

	/** The data eval inter data plot panel. */
	DataEvalInterDataPlotPanel dataEvalInterDataPlotPanel;

	/** The no button2. */
	JButton saveButton, printButton, tableButton, fitButton, smoothButton, saveNucDataButton
			, applyButton, okButton, okButton2, cancelButton, yesButton, noButton, yesButton2, noButton2;
	
	/** The max thresh field. */
	JTextField EaField, maxThreshField;
    
    /** The energy fit combo box. */
    JComboBox curveComboBox, energyFitComboBox;
    
    /** The total curve box. */
    JCheckBox curve1Box, curve2Box, totalCurveBox;
    
    /** The sp. */
    JScrollPane sp;
    
	/** The plot controls label. */
	JLabel curve1Label, curve2Label, curve1NameLabel, curve2NameLabel, rateLabel, reactionLabel, 
			energyValueLabel, maxThresLabel, curvePlotLabel, adjustCurveLabel, energyFitLabel, sliderLabel, plotControlsLabel;

	/** The Ea slider. */
	JSlider EaSlider;

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
								, "to max and min?"};
	
	/** The E box. */
	JCheckBox EBox;
	
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

	/** The der. */
	double der = 0.0;

	/** The ds. */
	private DataEvalDataStructure ds;

	//Constructor					
	/**
	 * Instantiates a new data eval inter data3 panel.
	 *
	 * @param ds the ds
	 */
	public DataEvalInterData3Panel(DataEvalDataStructure ds){

		this.ds = ds;

		Cina.dataEvalFrame.setCurrentFeatureIndex(1);
		Cina.dataEvalFrame.setCurrentPanelIndex(3);
	
		setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		addWizardPanel("Nuclear Data Evaluator's Toolkit", "Cross Section Fuser", "3", "3");
		
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
		
		fitButton = new JButton("Match Curves");
		fitButton.setFont(Fonts.buttonFont);
		fitButton.addActionListener(this);
		
		smoothButton = new JButton("Smooth Curve");
		smoothButton.setFont(Fonts.buttonFont);
		smoothButton.addActionListener(this);
		smoothButton.setEnabled(false);
		
		saveNucDataButton = new JButton("Save Cross Section");
		saveNucDataButton.setFont(Fonts.buttonFont);
		saveNucDataButton.addActionListener(this);
		saveNucDataButton.setEnabled(false);
		
		applyButton = new JButton("Apply Energy Range");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		//COMBOBOXES///////////////////////////////////////////////////////////////COMBOBOXES//////////////////////
		curveComboBox = new JComboBox();
		curveComboBox.setFont(Fonts.textFont);
		curveComboBox.addItem("Curve 1");
		curveComboBox.addItem("Curve 2");
		curveComboBox.addItemListener(this);
		
		energyFitComboBox = new JComboBox();
		energyFitComboBox.setFont(Fonts.textFont);
		energyFitComboBox.addItem("High Energy");
		energyFitComboBox.addItem("Low Energy");
		
		YminComboBox = new JComboBox();
        YminComboBox.setFont(Fonts.textFont);
        for(int i=9; i>=-10; i--) { YminComboBox.addItem(Integer.toString(i));}
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=10; i>=-9; i--) { YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.addItemListener(this);
		
		
		//SLIDERS////////////////////////////////////////////////////////////////////SLIDERS////////////////////
		EaSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);
		EaSlider.addChangeListener(this);
		
		//FIELDS///////////////////////////////////////////////////////////////////FIELDS//////////////////////////
		EaField = new JTextField(10);
		EaField.setText(NumberFormats.getFormattedValueLong(getEaSliderValue()));
		
		maxThreshField = new JTextField(10);
		maxThreshField.setText("0.20");
		
		EminField = new JTextField(5);
		
		EmaxField = new JTextField(5);

		//CHECKBOXES///////////////////////////////////////////////////////////////CHECKBOXES/////////////////////
		curve1Box = new JCheckBox("Display Curve 1");
		curve1Box.setFont(Fonts.textFont);
		curve1Box.setSelected(true);
		curve1Box.addItemListener(this);
		
		curve2Box = new JCheckBox("Display Curve 2");
		curve2Box.setFont(Fonts.textFont);
		curve2Box.setSelected(true);
		curve2Box.addItemListener(this);
		
		totalCurveBox = new JCheckBox("Display Matched Curves");
		totalCurveBox.setFont(Fonts.textFont);
		totalCurveBox.addItemListener(this);
		totalCurveBox.setEnabled(false);
		
		EBox = new JCheckBox("", true);
		EBox.addItemListener(this);
		
		//LABELS////////////////////////////////////////////////////////////////////LABELS////////////////////////
		adjustCurveLabel = new JLabel("Choose stationary curve: ");
		adjustCurveLabel.setFont(Fonts.textFont);
		
		curve1Label = new JLabel("Curve 1: ");
		curve1Label.setFont(Fonts.textFont);
		
		curve2Label = new JLabel("Curve 2: ");
		curve2Label.setFont(Fonts.textFont);
		
		curve1NameLabel = new JLabel();
		curve1NameLabel.setFont(Fonts.textFont);
		
		curve2NameLabel = new JLabel();
		curve2NameLabel.setFont(Fonts.textFont);
		
		reactionLabel = new JLabel("Reaction: ");
		
		energyValueLabel = new JLabel("Energy value for curve matching :");
		energyValueLabel.setFont(Fonts.textFont);
		
		maxThresLabel = new JLabel("<html>Maximum allowed change in the<p>derivative at matching energy value :</html>");
		maxThresLabel.setFont(Fonts.textFont);
		
		curvePlotLabel = new JLabel("Choose curves for plot below :");
		curvePlotLabel.setFont(Fonts.textFont);
		
		energyFitLabel = new JLabel("Choose region from Curve 2 to scale :");
		energyFitLabel.setFont(Fonts.textFont);
		
		sliderLabel = new JLabel("Use slider to set matching energy value :");
		sliderLabel.setFont(Fonts.textFont);
		
		plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		labelArray[0] = new JLabel(stringArray[0] + " " + stringArray[6]);
		labelArray[0].setFont(Fonts.textFont);
		
		labelArray[1] = new JLabel(stringArray[1]);
		labelArray[1].setFont(Fonts.textFont);
		
		labelArray[2] = new JLabel(stringArray[2]);
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3] = new JLabel(stringArray[3]);
		labelArray[3].setFont(Fonts.textFont);

		labelArray[4] = new JLabel(stringArray[4]);
		labelArray[4].setFont(Fonts.textFont);
		
		labelArray[5] = new JLabel(stringArray[5]);
		labelArray[5].setFont(Fonts.textFont);
		
		//PANELS/////////////////////////////////////////////////////////////////////PANELS///////////////////////
		//buttonPanel = new JPanel(new GridLayout(1, 6, 2, 2));
		
		buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(saveButton);
		buttonPanel.add(printButton);
		buttonPanel.add(tableButton);
		buttonPanel.add(fitButton);
		buttonPanel.add(smoothButton);
		buttonPanel.add(saveNucDataButton);
		
		//PLOT///////////////////////////////////////////////////////////////////////PLOT///////////////////////////
		dataEvalInterDataPlotPanel = new DataEvalInterDataPlotPanel(YminComboBoxInit, YmaxComboBoxInit, ds);
		
		dataEvalInterDataPlotPanel.setPreferredSize(dataEvalInterDataPlotPanel.getSize());
		
		dataEvalInterDataPlotPanel.revalidate();
		
		sp = new JScrollPane(dataEvalInterDataPlotPanel);

		dataEvalInterDataPlotPanel.setPreferredSize(dataEvalInterDataPlotPanel.getSize());
		
		dataEvalInterDataPlotPanel.revalidate();
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		setScalePanel = new JPanel(new GridBagLayout());
		
		scaleTitlePanel = new JPanel(new GridBagLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		scaleTitlePanel.add(plotControlsLabel, gbc);
		/*scaleTitlePanel.add(labelArray[0], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		scaleTitlePanel.add(EBox, gbc);*/
		
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
		double[] col = {5, TableLayoutConstants.FILL, 5};
		double[] row = {5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED
						, 5, TableLayoutConstants.PREFERRED, 5};
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new TableLayout(col, row));
		
		rightPanel.add(reactionLabel,            "1, 1, l, c");
		rightPanel.add(curve1Label,              "1, 3, l, c");
		rightPanel.add(curve1NameLabel,          "1, 5, l, c");
		rightPanel.add(curve2Label,              "1, 7, l, c");
		rightPanel.add(curve2NameLabel,          "1, 9, l, c");
		rightPanel.add(adjustCurveLabel,         "1, 11, l, c");
		rightPanel.add(curveComboBox,            "1, 13, f, c");
		rightPanel.add(energyFitLabel,           "1, 15, l, c");
		rightPanel.add(energyFitComboBox,        "1, 17, f, c");
		rightPanel.add(energyValueLabel,         "1, 19, l, c");	
		rightPanel.add(EaField,                  "1, 21, f, c");
		rightPanel.add(sliderLabel,              "1, 23, l, c");
		rightPanel.add(EaSlider,                 "1, 25, f, c");
		rightPanel.add(maxThresLabel,            "1, 27, l, c");	
		rightPanel.add(maxThreshField,           "1, 29, f, c");
		rightPanel.add(curvePlotLabel,           "1, 31, l, c");
		rightPanel.add(curve1Box,                "1, 33, l, c");
		rightPanel.add(curve2Box,                "1, 35, l, c");
		rightPanel.add(totalCurveBox,            "1, 37, l, c");
		
		
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
		
		currentYmin = ds.getInterYmin();
	
		min = (int)Math.floor(0.434294482*Math.log(currentYmin));
	
		if(min < -10){
		
			min = -10;
		
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
		
		currentYmax = ds.getInterYmax();
	
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 10){
		
			max = 10;
		
		}

		return max;
	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==curve1Box
			|| ie.getSource()==curve2Box
			|| ie.getSource()==totalCurveBox){
		
			redrawPlot();
		
		}else if(ie.getSource()==EBox){
		
			if(EBox.isSelected()){
				
				EminField.setEditable(false);
				EmaxField.setEditable(false);
				
				EminField.setText(String.valueOf(ds.getInterXmin()));
				EmaxField.setText(String.valueOf(ds.getInterXmax()));

				applyButton.setEnabled(false);
				
			}else{
			
				EminField.setEditable(true);
				EmaxField.setEditable(true);
				
				applyButton.setEnabled(true);
			
			}
			
			redrawPlot();
			
		}else if(ie.getSource()==YminComboBox || ie.getSource()==YmaxComboBox){
		
			if(Integer.valueOf(YminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(YmaxComboBox.getSelectedItem().toString()).doubleValue()){
       			
       			String string = "";
       			
       			string = "Cross section minimum must be less than cross section maximum.";

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
		
		}else if(ie.getSource()==curveComboBox){
		
			EaField.setText(NumberFormats.getFormattedValueLong(getEaSliderValue()));
			
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
			
				energyFitLabel.setText("Choose region from Curve 2 to scale :");
				
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				energyFitLabel.setText("Choose region from Curve 1 to scale :");
			
			}
			
			redrawPlot();
			
			validate();
		
		}
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==EaSlider){
		
			EaField.setText(NumberFormats.getFormattedValueLong(getEaSliderValue()));

			redrawPlot();

		}
	
	}

	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButton){

			PlotSaver.savePlot(dataEvalInterDataPlotPanel, this);
		
		}else if(ae.getSource()==printButton){
		
			PlotPrinter.print(new DataEvalInterDataPlotPrintable(), Cina.dataEvalFrame);
		
		}else if(ae.getSource()==tableButton){
		
			if(!noneChecked()){
       	
	       		if(Cina.dataEvalFrame.dataEvalInterDataTableFrame==null){
	       	
	       			Cina.dataEvalFrame.dataEvalInterDataTableFrame = new DataEvalInterDataTableFrame(ds);
	       		
		       	}else{
		       	
		       		Cina.dataEvalFrame.dataEvalInterDataTableFrame.setTableText();
		       		Cina.dataEvalFrame.dataEvalInterDataTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{

	       		String string = "Please select curves for the Table of Points from the checkbox list."; 
	       		
	       		createExceptionDialog(string, Cina.dataEvalFrame);
	       		
	       	}
		
		}else if(ae.getSource()==fitButton){
		
			if(goodEaValue()){
		
				if(goodCurvesForMatching()){

					EaSlider.removeChangeListener(this);
					EaSlider.setValue(setEaSliderValue());
					EaSlider.addChangeListener(this);
			
					smoothButton.setEnabled(true);
					
					if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
					
						saveNucDataButton.setEnabled(true);
					
					}
					
					totalCurveBox.setEnabled(true);
					totalCurveBox.setSelected(true);
				
					createFittedCurve();
				
					if(!goodMaxThresh()){
					
						String string = "The change in the derivative at the matching energy value is "
												+ NumberFormats.getFormattedDer(der)
												+ " which is above the allowed maximum entered. Click the Smooth Curve button to decrease the change in the derivative at matching energy value.";
						
						createExceptionDialog(string, Cina.dataEvalFrame);
					
					}
			
				}else{
				
					if(((String)energyFitComboBox.getSelectedItem()).equals("High Energy")){
				
						if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
					
							String string = "Curves can not be matched. There are no points in Curve 2 with an energy value greater than the matching energy value entered.";
							
							createExceptionDialog(string, Cina.dataEvalFrame);
							
						}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
						
							String string = "Curves can not be matched. There are no points in Curve 1 with an energy value greater than the matching energy value entered.";
							
							createExceptionDialog(string, Cina.dataEvalFrame);
							
						}
					
					}else if(((String)energyFitComboBox.getSelectedItem()).equals("Low Energy")){
					
						if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
					
							String string = "Curves can not be matched. There are no points in Curve 2 with an energy value less than the matching energy value entered.";
							
							createExceptionDialog(string, Cina.dataEvalFrame);
							
						}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
						
							String string = "Curves can not be matched. There are no points in Curve 1 with an energy value less than the matching energy value entered.";
							
							createExceptionDialog(string, Cina.dataEvalFrame);
							
						}
						
					}
				
				}	
			
			}else{
			
				if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
			
					String string = "The matching energy value entered is not within the energy range of Curve 1.";
					
					createExceptionDialog(string, Cina.dataEvalFrame);
					
				}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
				
					String string = "The matching energy value entered is not within the energy range of Curve 2.";
					
					createExceptionDialog(string, Cina.dataEvalFrame);

				}
				
				EaSlider.setValue(500);
				
				EaField.setText(NumberFormats.getFormattedValueLong(getEaSliderValue()));
				
			}
		
		}else if(ae.getSource()==smoothButton){
		
			String string = "This feature is currently under development. When operational, the Smooth Curve function will implement a variety of computational "
								+ "techniques to decrease the change in the derivative at the matching energy value entered. These will include spline fitting algorithms as well as 3- or 4-point interpolations.";
			
			createExceptionDialog(string, Cina.dataEvalFrame);
		
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
					
					if(goodNucDataExists()){
					
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
	 * None checked.
	 *
	 * @return true, if successful
	 */
	public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	if(curve1Box.isSelected() || curve2Box.isSelected() || totalCurveBox.isSelected()){noneChecked = false;}
    	
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
					+ ds.getInter1NucDataDataStructure().getReactionString()
					+ "\u000b";
		
		if(!ds.getInter1NucDataDataStructure().getDecayType().equals("none")){
		
			string += ds.getInter1NucDataDataStructure().getDecay();
		
		}
		
		string += "\u0009"
					+ ds.getInter1NucDataDataStructure().getNucDataType()
					+ "\u000b"
					+ ds.getInter3NucDataDataStructure().getNucDataName();
		
		return string;
	
	}
	
	/**
	 * Gets the zA string.
	 *
	 * @return the zA string
	 */
	public String getZAString(){return ds.getInter1NucDataDataStructure().getNucDataID().substring(0, 8);}

	/**
	 * Gets the nuc data properties.
	 *
	 * @return the nuc data properties
	 */
	public String getNucDataProperties(){
	
		String propertiesString = "";
		
		propertiesString += "Number of Points = " + String.valueOf(ds.getInter3NucDataDataStructure().getNumberPoints()) + "\u0009";
		propertiesString += "Nuc Data Notes = " + String.valueOf(ds.getInter3NucDataDataStructure().getNucDataNotes()) + "\u0009";
		propertiesString += "Reaction String = " + ds.getInter1NucDataDataStructure().getReactionString() + "\u0009";
		propertiesString += "Nuc Data Name = " + ds.getInter3NucDataDataStructure().getNucDataName() + "\u0009";
		propertiesString += "Nuc Data Type = " + ds.getInter1NucDataDataStructure().getNucDataType() + "\u0009";
		
		String reactionTypeString = "Reaction Type = " + String.valueOf(ds.getInter1NucDataDataStructure().getReactionType());
		
		if(!ds.getInter1NucDataDataStructure().getDecay().equals("")){
		
			reactionTypeString += "," + ds.getInter1NucDataDataStructure().getDecay();
		
		}

		propertiesString += reactionTypeString + "\u0009"; 
		propertiesString += "Reference Citation = " + ds.getInter3NucDataDataStructure().getRefCitation() + "\u0009";
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
		
		for(int i=0; i<ds.getInter3NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getInter3NucDataDataStructure().getXDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getInter3NucDataDataStructure().getXDataArray()[i]);
			
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
		
		for(int i=0; i<ds.getInter3NucDataDataStructure().getNumberPoints(); i++){
		
			if(i==0){
		
				string += String.valueOf(ds.getInter3NucDataDataStructure().getYDataArray()[i]);
			
			}else{
			
				string += "," + String.valueOf(ds.getInter3NucDataDataStructure().getYDataArray()[i]);
			
			}
		
		
		}
		
		return string;
	
	}

	/**
	 * Good curves for matching.
	 *
	 * @return true, if successful
	 */
	public boolean goodCurvesForMatching(){
	
		boolean goodCurvesForMatching = false;
		
		double Ea = Double.valueOf(EaField.getText()).doubleValue();
		
		if(((String)energyFitComboBox.getSelectedItem()).equals("High Energy")){
		
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
				
				if(ds.getInter2NucDataDataStructure().getXDataArray()[ds.getInter2NucDataDataStructure().getXDataArray().length-1]>Ea){
				
					goodCurvesForMatching = true;
				
				}
				
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				if(ds.getInter1NucDataDataStructure().getXDataArray()[ds.getInter1NucDataDataStructure().getXDataArray().length-1]>Ea){
				
					goodCurvesForMatching = true;
				
				}
			
			}
			
		}else if(((String)energyFitComboBox.getSelectedItem()).equals("Low Energy")){
			
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
				
				if(ds.getInter2NucDataDataStructure().getXDataArray()[0]<Ea){
				
					goodCurvesForMatching = true;
				
				}
				
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				if(ds.getInter1NucDataDataStructure().getXDataArray()[0]<Ea){
				
					goodCurvesForMatching = true;
				
				}
			
			}
				
		}
		
		return goodCurvesForMatching;
	
	}

	/**
	 * Good ea value.
	 *
	 * @return true, if successful
	 */
	public boolean goodEaValue(){
	
		boolean goodEaValue = false;
		
		double Ea = Double.valueOf(EaField.getText()).doubleValue();
		
		if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
			
			if(Ea<=ds.getInter1NucDataDataStructure().getXDataArray()[ds.getInter1NucDataDataStructure().getXDataArray().length-1]
					&& Ea>=ds.getInter1NucDataDataStructure().getXDataArray()[0]){
			
				goodEaValue = true;
			
			}
	
		}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
			if(Ea<=ds.getInter2NucDataDataStructure().getXDataArray()[ds.getInter2NucDataDataStructure().getXDataArray().length-1]
					&& Ea>=ds.getInter2NucDataDataStructure().getXDataArray()[0]){
			
				goodEaValue = true;
			
			}
		
		}
		
		return goodEaValue;
	
	}

	/**
	 * Good nuc data merge.
	 *
	 * @return true, if successful
	 */
	public boolean goodNucDataMerge(){
		
		boolean goodNucDataMerge = false;
		
		ds.setSourceNucDataSet("");
		ds.setDestNucDataSetGroup("USER");
		ds.setNucData("");
		ds.setProperties(getNucDataProperties());
		ds.setDeleteSourceNucDataSet("NO");
		ds.setDeleteNucData("NO");
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA SET", Cina.dataEvalFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodNucDataMerge = true;
					
				}
			}
		}
		
		flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY NUC DATA", Cina.dataEvalFrame);
		
		if(goodNucDataMerge){
		
			goodNucDataMerge = false;
		
			if(!flagArray[0]){
			
				if(!flagArray[2]){
				
					if(!flagArray[1]){
						
						goodNucDataMerge = true;
						
					}
				}
			}
		}

		return goodNucDataMerge;
	
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
	 * Good nuc data exists.
	 *
	 * @return true, if successful
	 */
	public boolean goodNucDataExists(){
		
		boolean goodNucDataExists = false;
		
		ds.setNucData(getCurrentNucDataID());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("NUC DATA EXIST", Cina.dataEvalFrame);
		
		if(!flagArray[0]){
			
			if(!flagArray[2]){
				
				if(!flagArray[1]){
						
					goodNucDataExists = true;
						
				}
			}
		}

		return goodNucDataExists;
	
	}

	/**
	 * Good max thresh.
	 *
	 * @return true, if successful
	 */
	public boolean goodMaxThresh(){
	
		boolean goodMaxThresh = false;
	
		int indexLowPoint = 0;
		int indexHighPoint = 0;
		int indexEaPoint = 0;
		
		double lowPoint = 0.0;
		double highPoint = 0.0;
		double EaPoint = 0.0;
		
		for(int i=0; i<ds.getInter3NucDataDataStructure().getXDataArray().length; i++){
		
			if(ds.getInter3NucDataDataStructure().getXDataArray()[i]==Double.valueOf(EaField.getText()).doubleValue()){
			
				indexEaPoint = i;
				
				EaPoint = ds.getInter3NucDataDataStructure().getYDataArray()[i];
			
			}
		
		}
		
		indexLowPoint = indexEaPoint - 1;
		indexHighPoint = indexEaPoint + 1;
		
		lowPoint = ds.getInter3NucDataDataStructure().getYDataArray()[indexLowPoint];
		highPoint = ds.getInter3NucDataDataStructure().getYDataArray()[indexHighPoint];
		
		double lowDer = (EaPoint - lowPoint)/(ds.getInter3NucDataDataStructure().getXDataArray()[indexEaPoint] 
												- ds.getInter3NucDataDataStructure().getXDataArray()[indexLowPoint]);
		
		double highDer = (highPoint - EaPoint)/(ds.getInter3NucDataDataStructure().getXDataArray()[indexHighPoint] 
												- ds.getInter3NucDataDataStructure().getXDataArray()[indexEaPoint]);
		
		der = Math.abs((highDer - lowDer)/lowDer);
		
		if(der <= Double.valueOf(maxThreshField.getText()).doubleValue()){
		
			goodMaxThresh = true;
		
		}
	
		return goodMaxThresh;
		
	}

	/**
	 * Creates the fitted curve.
	 */
	public void createFittedCurve(){
	
		double CS1_Ea = 0.0;
		double CS2_Ea = 0.0;
		double CSRatio = 0.0;
	
		if(((String)energyFitComboBox.getSelectedItem()).equals("High Energy")){
	
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				CS1_Ea = getCS1_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CS2_Ea = getCS2_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CSRatio = CS1_Ea/CS2_Ea;
			
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				CS1_Ea = getCS1_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CS2_Ea = getCS2_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CSRatio = CS2_Ea/CS1_Ea;
			
			}
			
		}else if(((String)energyFitComboBox.getSelectedItem()).equals("Low Energy")){
		
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				CS1_Ea = getCS1_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CS2_Ea = getCS2_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CSRatio = CS1_Ea/CS2_Ea;
			
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				CS1_Ea = getCS1_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CS2_Ea = getCS2_Ea(Double.valueOf(EaField.getText()).doubleValue());
				CSRatio = CS2_Ea/CS1_Ea;
			
			}
			
		}
		
		ds.getInter3NucDataDataStructure().setNucDataName("Matched Cross Section for "
																									+ ds.getInter1NucDataDataStructure().getReactionString());
		
		ds.getInter3NucDataDataStructure().setXDataArray(getMatchedXDataArray());
		ds.getInter3NucDataDataStructure().setYDataArray(getMatchedYDataArray(CSRatio, CS1_Ea, CS2_Ea));
		ds.getInter3NucDataDataStructure().setNumberPoints(ds.getInter3NucDataDataStructure().getXDataArray().length);
		
		redrawPlot();

	}

	/**
	 * Gets the matched x data array.
	 *
	 * @return the matched x data array
	 */
	public double[] getMatchedXDataArray(){
	
		double[] outputArray = new double[1];
	
		if(((String)energyFitComboBox.getSelectedItem()).equals("High Energy")){
	
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter2NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getXDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				tempArray[counter] = Double.valueOf(EaField.getText()).doubleValue();
				
				counter++;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getXDataArray()[i];
		
						counter++;
						
					}
				
				}
				
				outputArray = tempArray;
			
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getXDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				tempArray[counter] = Double.valueOf(EaField.getText()).doubleValue();
				
				counter++;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getXDataArray()[i];
		
						counter++;
						
					}
				
				}
				
				outputArray = tempArray;
			
			}
			
		}else if(((String)energyFitComboBox.getSelectedItem()).equals("Low Energy")){
		
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter2NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getXDataArray()[i];
		
						counter++;
						
					}
				
				}
				
				tempArray[counter] = Double.valueOf(EaField.getText()).doubleValue();
				
				counter++;

				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getXDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				outputArray = tempArray;
			
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
			
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getXDataArray()[i];
		
						counter++;
						
					}
				
				}
				
				tempArray[counter] = Double.valueOf(EaField.getText()).doubleValue();
				
				counter++;

				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getXDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				outputArray = tempArray;
			
			}
			
		}
	
		return outputArray;
	
	}

	/**
	 * Gets the matched y data array.
	 *
	 * @param CSRatio the cS ratio
	 * @param CS1_Ea the c s1_ ea
	 * @param CS2_Ea the c s2_ ea
	 * @return the matched y data array
	 */
	public double[] getMatchedYDataArray(double CSRatio, double CS1_Ea, double CS2_Ea){
	
		double[] outputArray = new double[1];
	
		if(((String)energyFitComboBox.getSelectedItem()).equals("High Energy")){
	
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter2NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getYDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				tempArray[counter] = CS1_Ea;
				
				counter++;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = CSRatio*ds.getInter2NucDataDataStructure().getYDataArray()[i];
						
						counter++;
						
					}
				
				}
				
				outputArray = tempArray;
				
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
				
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getYDataArray()[i];

						counter++;
					
					}
				
				}
				
				tempArray[counter] = CS2_Ea;
			
				counter++;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = CSRatio*ds.getInter1NucDataDataStructure().getYDataArray()[i];
						
						counter++;
						
					}
				
				}
				
				outputArray = tempArray;
				
			}
			
		}else if(((String)energyFitComboBox.getSelectedItem()).equals("Low Energy")){
		
			if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter2NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = CSRatio*ds.getInter2NucDataDataStructure().getYDataArray()[i];
						
						counter++;
						
					}
				
				}
				
				tempArray[counter] = CS1_Ea;
				
				counter++;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter1NucDataDataStructure().getYDataArray()[i];
						
						counter++;
					
					}
				
				}
				
				outputArray = tempArray;
				
			}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
				
				int counter = 0;
			
				double Ea = Double.valueOf(EaField.getText()).doubleValue();
			
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						counter++;
					
					}
				
				}
				
				for(int i=ds.getInter1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
				
					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						counter++;
						
					}
				
				}
				
				counter++;
				
				double[] tempArray = new double[counter];
				
				counter = 0;
				
				for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){

					if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
					
						tempArray[counter] = CSRatio*ds.getInter1NucDataDataStructure().getYDataArray()[i];
						
						counter++;
						
					}
				
				}
				
				tempArray[counter] = CS2_Ea;
			
				counter++;
				
				for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
				
					if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
					
						tempArray[counter] = ds.getInter2NucDataDataStructure().getYDataArray()[i];

						counter++;
					
					}
				
				}
				
				outputArray = tempArray;
				
			}
			
		}
		
		return outputArray;
	
	}

	/**
	 * Gets the c s1_ ea.
	 *
	 * @param Ea the ea
	 * @return the c s1_ ea
	 */
	public double getCS1_Ea(double Ea){
	
		double lowYPoint = 0.0;
		double highYPoint = 0.0;
		
		double lowXPoint = 0.0;
		double highXPoint = 0.0;
		
		double matchedYPoint = 0.0;
		
		for(int i=0; i<ds.getInter1NucDataDataStructure().getNumberPoints(); i++){
		
			if(ds.getInter1NucDataDataStructure().getXDataArray()[i]<Ea){
			
				lowYPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getYDataArray()[i]);
				lowXPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getXDataArray()[i]);
			
			}
		
		}
		
		for(int i=ds.getInter1NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
		
			if(ds.getInter1NucDataDataStructure().getXDataArray()[i]>Ea){
			
				highYPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getYDataArray()[i]);
				highXPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getXDataArray()[i]);
				
			}
		
		}
		
		if(highXPoint==0.0){
		
			highXPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getXDataArray()[ds.getInter1NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(highYPoint==0.0){
		
			highYPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getYDataArray()[ds.getInter1NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(lowXPoint==0.0){
		
			lowXPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getXDataArray()[0]);
			
		}
		
		if(lowYPoint==0.0){
		
			lowYPoint = log10*Math.log(ds.getInter1NucDataDataStructure().getYDataArray()[0]);
			
		}
		
		double slope = (highYPoint - lowYPoint)/(highXPoint - lowXPoint);
		
		matchedYPoint = (-slope*(highXPoint - log10*Math.log(Ea))) + highYPoint;
		
		matchedYPoint = Math.pow(10, matchedYPoint);
		
		return matchedYPoint;
	
	}

	/**
	 * Gets the c s2_ ea.
	 *
	 * @param Ea the ea
	 * @return the c s2_ ea
	 */
	public double getCS2_Ea(double Ea){
	
		double lowYPoint = 0.0;
		double highYPoint = 0.0;
		
		double lowXPoint = 0.0;
		double highXPoint = 0.0;
		
		double matchedYPoint = 0.0;
		
		for(int i=0; i<ds.getInter2NucDataDataStructure().getNumberPoints(); i++){
		
			if(ds.getInter2NucDataDataStructure().getXDataArray()[i]<Ea){
			
				lowYPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getYDataArray()[i]);
				lowXPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getXDataArray()[i]);
			
			}
		
		}
		
		for(int i=ds.getInter2NucDataDataStructure().getNumberPoints()-1; i>=0; i--){
		
			if(ds.getInter2NucDataDataStructure().getXDataArray()[i]>Ea){
			
				highYPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getYDataArray()[i]);
				highXPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getXDataArray()[i]);
				
			}
		
		}
		
		if(highXPoint==0.0){
		
			highXPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getXDataArray()[ds.getInter2NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(highYPoint==0.0){
		
			highYPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getYDataArray()[ds.getInter2NucDataDataStructure().getNumberPoints()-1]);
			
		}
		
		if(lowXPoint==0.0){
		
			lowXPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getXDataArray()[0]);
			
		}
		
		if(lowYPoint==0.0){
		
			lowYPoint = log10*Math.log(ds.getInter2NucDataDataStructure().getYDataArray()[0]);
			
		}
		
		double slope = (highYPoint - lowYPoint)/(highXPoint - lowXPoint);
		
		matchedYPoint = (-slope*(highXPoint - log10*Math.log(Ea))) + highYPoint;
		
		matchedYPoint = Math.pow(10, matchedYPoint);
		
		return matchedYPoint;
		
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
				
				dataEvalInterDataPlotPanel.setXmin(Double.valueOf(EminField.getText()).doubleValue());
				dataEvalInterDataPlotPanel.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());

				dataEvalInterDataPlotPanel.setPlotState();

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
		
		ds.setInterXmax(setXmax());
		ds.setInterXmin(setXmin());
		
		ds.setInterYmax(setYmax());
		ds.setInterYmin(setYmin());
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YminComboBox.removeItemListener(this);
		YmaxComboBox.removeItemListener(this);

		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

		YminComboBox.addItemListener(this);
		YmaxComboBox.addItemListener(this);

		EminField.setText(String.valueOf(ds.getInterXmin()));
		EmaxField.setText(String.valueOf(ds.getInterXmax()));

		curve1NameLabel.setText(ds.getInter1NucDataDataStructure().getNucDataName());
									
		curve2NameLabel.setText(ds.getInter2NucDataDataStructure().getNucDataName());
									
		
		curve1Label.setText("Curve 1 from "
								+ ds.getInter1NucDataDataStructure().getNucDataSet()
								+ " Data Set: ");
								
		curve2Label.setText("Curve 2 from "
								+ ds.getInter2NucDataDataStructure().getNucDataSet()
								+ " Data Set: ");
		
		if(ds.getInter1NucDataDataStructure().getDecay().equals("")){
								
			reactionLabel.setText("Reaction: " + ds.getInter1NucDataDataStructure().getReactionString());
		
		}else{
		
			reactionLabel.setText("Reaction: " 
									+ ds.getInter1NucDataDataStructure().getReactionString()
									+ " ["
									+ ds.getInter1NucDataDataStructure().getDecay()
									+ "]");
		
		}
		
		EaField.setText(NumberFormats.getFormattedValueLong(getEaSliderValue()));
		
		redrawPlot();
		
	}
	
	/**
	 * Gets the ea slider value.
	 *
	 * @return the ea slider value
	 */
	public double getEaSliderValue(){
	
		double temp = 0.0;
		
		double min = 0.0;
		double max = 0.0;
		
		if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
			max = ds.getInter1NucDataDataStructure().getXDataArray()[ds.getInter1NucDataDataStructure().getNumberPoints()-1];
		
			min = ds.getInter1NucDataDataStructure().getXDataArray()[0];
		
		}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
		
			max = ds.getInter2NucDataDataStructure().getXDataArray()[ds.getInter2NucDataDataStructure().getNumberPoints()-1];
		
			min = ds.getInter2NucDataDataStructure().getXDataArray()[0];
		
		}
		
		temp = min + (((double)EaSlider.getValue())/1000.0)*(max-min);
		
		return temp;
	
	}
	
	/**
	 * Sets the ea slider value.
	 *
	 * @return the int
	 */
	public int setEaSliderValue(){
		
		int temp = 0;
		
		double min = 0.0;
		double max = 0.0;
		
		double Ea = Double.valueOf(EaField.getText()).doubleValue();
		
		if(((String)curveComboBox.getSelectedItem()).equals("Curve 1")){
		
			max = ds.getInter1NucDataDataStructure().getXDataArray()[ds.getInter1NucDataDataStructure().getNumberPoints()-1];
		
			min = ds.getInter1NucDataDataStructure().getXDataArray()[0];
		
		}else if(((String)curveComboBox.getSelectedItem()).equals("Curve 2")){
		
			max = ds.getInter2NucDataDataStructure().getXDataArray()[ds.getInter2NucDataDataStructure().getNumberPoints()-1];
		
			min = ds.getInter2NucDataDataStructure().getXDataArray()[0];
		
		}
		
		temp = (int)(1000*((Ea - min)/(max - min)));
		
		return temp;
	
	}
	
	/**
	 * Sets the xmin.
	 *
	 * @return the double
	 */
	public double setXmin(){
		
		double newXmin = 1e30;
		
		for(int i=0; i<ds.getInter1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmin = Math.min(newXmin, ds.getInter1NucDataDataStructure().getXDataArray()[i]);
		
		}
		
		for(int i=0; i<ds.getInter2NucDataDataStructure().getXDataArray().length; i++){
		
			newXmin = Math.min(newXmin, ds.getInter2NucDataDataStructure().getXDataArray()[i]);
		
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
		
		for(int i=0; i<ds.getInter1NucDataDataStructure().getXDataArray().length; i++){
		
			newXmax = Math.max(newXmax, ds.getInter1NucDataDataStructure().getXDataArray()[i]);
		
		}
		
		for(int i=0; i<ds.getInter2NucDataDataStructure().getXDataArray().length; i++){
		
			newXmax = Math.max(newXmax, ds.getInter2NucDataDataStructure().getXDataArray()[i]);
		
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
		
		for(int i=0; i<ds.getInter1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmin = Math.min(newYmin, ds.getInter1NucDataDataStructure().getYDataArray()[i]);
		
		}
		
		for(int i=0; i<ds.getInter2NucDataDataStructure().getYDataArray().length; i++){
		
			newYmin = Math.min(newYmin, ds.getInter2NucDataDataStructure().getYDataArray()[i]);
		
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
		
		for(int i=0; i<ds.getInter1NucDataDataStructure().getYDataArray().length; i++){
		
			newYmax = Math.max(newYmax, ds.getInter1NucDataDataStructure().getYDataArray()[i]);
		
		}
		
		for(int i=0; i<ds.getInter2NucDataDataStructure().getYDataArray().length; i++){
		
			newYmax = Math.max(newYmax, ds.getInter2NucDataDataStructure().getYDataArray()[i]);
		
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

class DataEvalInterDataPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.dataEvalFrame.dataEvalInterData3Panel.dataEvalInterDataPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}