package org.nucastrodata.element.elementviz.weight;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightIsotopeListPanel;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotFramePrintable;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotPanel;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightTableFrame;


/**
 * The Class ElementVizWeightPlotControlsPanel.
 */
public class ElementVizWeightPlotControlsPanel extends WizardPanel implements ItemListener, ActionListener{
    
    /** The toolkit. */
    Toolkit toolkit;

    /** The control panel. */
    JPanel weightPanel, ratioPanel, buttonPanel, controlPanel;
    
    /** The weightmax combo box. */
    JComboBox weightminComboBox, weightmaxComboBox;

	/** The denom combo box. */
	public JComboBox numComboBox, denomComboBox;

    /** The element viz weight isotope list panel. */
    ElementVizWeightIsotopeListPanel elementVizWeightIsotopeListPanel;

    /** The element viz weight plot panel. */
    ElementVizWeightPlotPanel elementVizWeightPlotPanel;

    /** The element viz weight table frame. */
    public ElementVizWeightTableFrame elementVizWeightTableFrame;
    
    /** The apply button. */
    JButton printButton, saveButton, tableButton, applyButton;

    /** The minor ratio check box. */
    JCheckBox majorZoneCheckBox, majorWeightCheckBox, minorZoneCheckBox, minorWeightCheckBox
    			, majorMassCheckBox, majorRatioCheckBox, minorMassCheckBox, minorRatioCheckBox;
    
    /** The legend box ratio. */
    JCheckBox legendBoxWeight, legendBoxRatio;
    
	/** The ratiomax field. */
	JTextField zonemaxField, zoneminField, massminField, massmaxField, ratiominField, ratiomaxField;
	
	/** The denom label. */
	JLabel zonemaxLabel, zoneminLabel, weightmaxLabel, weightminLabel
			, massmaxLabel, massminLabel, ratiomaxLabel, ratiominLabel
			, numLabel, denomLabel;
	
	/** The weightmin combo box init. */
	int weightminComboBoxInit;
    
    /** The weightmax combo box init. */
    int weightmaxComboBoxInit;
    
    /** The sp1. */
    JScrollPane sp, sp1;

    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /**
     * Instantiates a new element viz weight plot controls panel.
     *
     * @param ds the ds
     */
    public ElementVizWeightPlotControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;

		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Final Weighted Abundance Plotting Interface", "Plot Final Weighted Abundance or Ratio of Summed Final Weighted Abundance", "2", "2");

		weightminComboBoxInit = setInitialWeightminComboBoxInit();
		weightmaxComboBoxInit = setInitialWeightmaxComboBoxInit();

		createIsotopeListPanel();
		setIsotopeListPanel();
		createFormatPanel();
		setFormatPanelState();
		createResultsPlot();
		
		
		
		validate();
		
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
	
		weightminComboBoxInit = setInitialWeightminComboBoxInit();
		weightmaxComboBoxInit = setInitialWeightmaxComboBoxInit();

		weightminComboBox.removeItemListener(this);
		weightmaxComboBox.removeItemListener(this);
		
		weightminComboBox.setSelectedItem(String.valueOf(weightminComboBoxInit));
        weightmaxComboBox.setSelectedItem(String.valueOf(weightmaxComboBoxInit));
        
        weightminComboBox.addItemListener(this);
		weightmaxComboBox.addItemListener(this);
        
        zoneminField.setText(String.valueOf(ds.getZonemin()));
        zonemaxField.setText(String.valueOf(ds.getZonemax()));
        
        massminField.setText(String.valueOf(ds.getMassminWeight()));
        massmaxField.setText(String.valueOf(ds.getMassmaxWeight()));
        
        ratiominField.setText(String.valueOf(ds.getRatiominWeight()));
        ratiomaxField.setText(String.valueOf(ds.getRatiomaxWeight()));
        
        if(ds.getNumberNucSimSetDataStructures()==1){
        
        	elementVizWeightIsotopeListPanel.ratioRadioButton.setEnabled(false);
        
        }else{
        
        	elementVizWeightIsotopeListPanel.ratioRadioButton.setEnabled(true);
        
        }
        
        numComboBox.removeItemListener(this);
        numComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
			numComboBox.addItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
		if(ds.getNumberNucSimSetDataStructures()!=1){
			String string = ds.getZoneNucSimSetDataStructureArray()[0].getPath();
			numComboBox.setSelectedItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
        numComboBox.addItemListener(this);
        
        denomComboBox.removeItemListener(this);
        denomComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
			denomComboBox.addItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
		if(ds.getNumberNucSimSetDataStructures()!=1){
			String string = ds.getZoneNucSimSetDataStructureArray()[1].getPath();
			denomComboBox.setSelectedItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
        denomComboBox.addItemListener(this);
        
        
	
	}
    
    /**
     * Creates the isotope list panel.
     */
    public void createIsotopeListPanel(){
    
    	JPanel isotopeListPanel = new JPanel();
    	
        isotopeListPanel.setLayout(new GridBagLayout());
		
		elementVizWeightIsotopeListPanel = new ElementVizWeightIsotopeListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        isotopeListPanel.add(elementVizWeightIsotopeListPanel, gbc);   
          
        sp = new JScrollPane(isotopeListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    /**
     * Sets the isotope list panel.
     */
    public void setIsotopeListPanel(){

    	elementVizWeightIsotopeListPanel.initialize();
    
    }
    
    /**
     * Creates the format panel.
     */
    public void createFormatPanel(){   
		
		buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		weightPanel = new JPanel(new GridBagLayout());
		ratioPanel = new JPanel(new GridBagLayout());
		controlPanel = new JPanel(new GridBagLayout());
		
		/////BUTTONS/////////////////////////////////////////////////////BUTTONS////////////////////
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
        
		applyButton = new JButton("Apply Zone Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);

        legendBoxWeight = new JCheckBox("Show Legend?", true);
    	legendBoxWeight.setFont(Fonts.textFont);
    	legendBoxWeight.addActionListener(this);
        
        legendBoxRatio = new JCheckBox("Show Legend?", true);
    	legendBoxRatio.setFont(Fonts.textFont);
    	legendBoxRatio.addActionListener(this);
        
        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        weightminComboBox = new JComboBox();
        weightminComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-30; i--) {weightminComboBox.addItem(Integer.toString(i));}
        weightminComboBox.setSelectedItem(String.valueOf(weightminComboBoxInit));
		weightminComboBox.addItemListener(this);

        weightmaxComboBox = new JComboBox();
        weightmaxComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-29; i--) {weightmaxComboBox.addItem(Integer.toString(i));}
        weightmaxComboBox.setSelectedItem(String.valueOf(weightmaxComboBoxInit));
		weightmaxComboBox.addItemListener(this);
		
		numComboBox = new JComboBox();
		numComboBox.setFont(Fonts.textFont);
		
		numComboBox.removeItemListener(this);
        numComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
			numComboBox.addItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
		if(ds.getNumberNucSimSetDataStructures()!=1){
			String string = ds.getZoneNucSimSetDataStructureArray()[0].getPath();
			numComboBox.setSelectedItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
        numComboBox.addItemListener(this);
        
        denomComboBox = new JComboBox();
		denomComboBox.setFont(Fonts.textFont);
        
        denomComboBox.removeItemListener(this);
        denomComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
			denomComboBox.addItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
		if(ds.getNumberNucSimSetDataStructures()!=1){
			String string = ds.getZoneNucSimSetDataStructureArray()[1].getPath();
			denomComboBox.setSelectedItem(string.substring(string.lastIndexOf("/")+1));
		
		}
		
        denomComboBox.addItemListener(this);
        
        //CREATE FIELDS/////////////////////////////////////////////////////////FIELDS/////////////////////
        
        zonemaxField = new JTextField(7);
        zoneminField = new JTextField(7);
        
        massmaxField = new JTextField(5);
        massminField = new JTextField(5);
        
        ratiomaxField = new JTextField(5);
        ratiominField = new JTextField(5);
        
        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        zonemaxLabel = new JLabel("Max");
        zonemaxLabel.setFont(Fonts.textFont);
        
        zoneminLabel = new JLabel("Zone Min");
        zoneminLabel.setFont(Fonts.textFont);
        
        weightmaxLabel = new JLabel("log Max");
        weightmaxLabel.setFont(Fonts.textFont);
        
        weightminLabel = new JLabel("log Weight Min");
        weightminLabel.setFont(Fonts.textFont);
        
        massmaxLabel = new JLabel("Max");
        massmaxLabel.setFont(Fonts.textFont);
        
        massminLabel = new JLabel("Mass Min");
        massminLabel.setFont(Fonts.textFont);
        
        ratiomaxLabel = new JLabel("Max");
        ratiomaxLabel.setFont(Fonts.textFont);
        
        ratiominLabel = new JLabel("Ratio Min");
        ratiominLabel.setFont(Fonts.textFont);
        
        numLabel = new JLabel("Num: ");
        numLabel.setFont(Fonts.textFont);
        
        denomLabel = new JLabel("Denom: ");
        denomLabel.setFont(Fonts.textFont);
        
        JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
        
        //CHECKBOXES///////////////////////////////////////////////////////CHECKBOXES////////////////////
        majorZoneCheckBox = new JCheckBox("Major Gridlines", true);
        minorZoneCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorWeightCheckBox = new JCheckBox("Major Gridlines", true);
        minorWeightCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorMassCheckBox = new JCheckBox("Major Gridlines", true);
        minorMassCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorRatioCheckBox = new JCheckBox("Major Gridlines", true);
        minorRatioCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorZoneCheckBox.addItemListener(this);
        minorZoneCheckBox.addItemListener(this);
        majorWeightCheckBox.addItemListener(this);
        minorWeightCheckBox.addItemListener(this);

		majorMassCheckBox.addItemListener(this);
        minorMassCheckBox.addItemListener(this);
        majorRatioCheckBox.addItemListener(this);
        minorRatioCheckBox.addItemListener(this);

		majorZoneCheckBox.setFont(Fonts.textFont);
        minorZoneCheckBox.setFont(Fonts.textFont);
        majorWeightCheckBox.setFont(Fonts.textFont);
        minorWeightCheckBox.setFont(Fonts.textFont);

		majorMassCheckBox.setFont(Fonts.textFont);
        minorMassCheckBox.setFont(Fonts.textFont);
        majorRatioCheckBox.setFont(Fonts.textFont);
        minorRatioCheckBox.setFont(Fonts.textFont);
        
        gbc.insets = new Insets(3, 3, 3, 3);

  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		weightPanel.add(weightminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(weightminComboBox, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		weightPanel.add(weightmaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(weightmaxComboBox, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(majorWeightCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(minorWeightCheckBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(legendBoxWeight, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		weightPanel.add(zoneminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(zoneminField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		weightPanel.add(zonemaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(zonemaxField, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(majorZoneCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		weightPanel.add(minorZoneCheckBox, gbc);
  		
  		//RATIO PANEL///////////////////////////////////////RATIO PANEL//////////////////////////////////////
  		gbc.insets = new Insets(3, 3, 3, 3);

  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		ratioPanel.add(ratiominLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(ratiominField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		ratioPanel.add(ratiomaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(ratiomaxField, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(majorRatioCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(minorRatioCheckBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(numLabel, gbc);
  		
  		gbc.gridx = 7;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(numComboBox, gbc);
  		
  		gbc.gridx = 8;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(legendBoxRatio, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		ratioPanel.add(massminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(massminField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		ratioPanel.add(massmaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(massmaxField, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(majorMassCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(minorMassCheckBox, gbc);

		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(denomLabel, gbc);

		gbc.gridx = 7;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		ratioPanel.add(denomComboBox, gbc);

		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(plotControlsLabel, gbc);

  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(weightPanel, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		controlPanel.add(buttonPanel, gbc);
  		
  		add(controlPanel, BorderLayout.SOUTH);
        
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    
    	elementVizWeightPlotPanel = new ElementVizWeightPlotPanel(weightminComboBoxInit 
													, weightmaxComboBoxInit
													, ds);
		
		elementVizWeightPlotPanel.setSize(442, 546);
		elementVizWeightPlotPanel.setPreferredSize(elementVizWeightPlotPanel.getSize());
		elementVizWeightPlotPanel.revalidate();
		
		sp1 = new JScrollPane(elementVizWeightPlotPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		sp1.validate();
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(550);
       	
       	add(jsp, BorderLayout.CENTER);
    	
    }
    
    /**
     * Sets the initial weightmin combo box init.
     *
     * @return the int
     */
    public int setInitialWeightminComboBoxInit(){
	
		int min = 0;

		min = ds.getWeightmin();
		
		if(min < -30){
			
			min = -30;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initial weightmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialWeightmaxComboBoxInit(){
	
		int max = 0;
		
		max = ds.getWeightmax();
		
		if(max > 30){
			
			max = 30;
		
		}
		
		return max;
	
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
        
        if(ie.getSource()==majorZoneCheckBox){
      		
            if(majorZoneCheckBox.isSelected()){
            	
                minorZoneCheckBox.setEnabled(true);
                
            }else{
            	
                minorZoneCheckBox.setSelected(false);
                minorZoneCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorWeightCheckBox){
        	
            if(majorWeightCheckBox.isSelected()){
            	
                minorWeightCheckBox.setEnabled(true);
                
            }else{

                minorWeightCheckBox.setSelected(false);
                minorWeightCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorZoneCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorWeightCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==majorMassCheckBox){
      		
            if(majorMassCheckBox.isSelected()){
            	
                minorMassCheckBox.setEnabled(true);
                
            }else{
            	
                minorMassCheckBox.setSelected(false);
                minorMassCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorRatioCheckBox){
        	
            if(majorRatioCheckBox.isSelected()){
            	
                minorRatioCheckBox.setEnabled(true);
                
            }else{

                minorRatioCheckBox.setSelected(false);
                minorRatioCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorMassCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorRatioCheckBox){
        	
            redrawPlot();
               
        }else if(ie.getSource()==weightminComboBox || ie.getSource()==weightmaxComboBox){

       		redrawPlot();

        }else if(ie.getSource()==numComboBox || ie.getSource()==denomComboBox){
        
        	ds.setMassDataArrayWeight(getMassDataArrayWeight());
			ds.setRatioDataArrayWeight(getRatioDataArrayWeight());
			
			ds.setMassmaxWeight(getMassmaxWeightDoubleValue());
			ds.setMassminWeight(getMassminWeightDoubleValue());
			
        	ds.setRatiomaxWeight(getRatiominWeightDoubleValue());
			ds.setRatiominWeight(getRatiomaxWeightDoubleValue());
        	
			ratiominField.setText(String.valueOf(ds.getRatiominWeight()));
			ratiomaxField.setText(String.valueOf(ds.getRatiomaxWeight()));
			
        	redrawPlot();
        
        }
  
    }
    
    private double[][] getMassDataArrayWeight(){

  		double[][] tempArray = new double[ds.getIsotopeViktorWeight().size()][1];
     
       	for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
       	
       		tempArray[i][0] = ((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX()
       							+ ((Point)ds.getIsotopeViktorWeight().elementAt(i)).getY();

       	}	
       		
    	return tempArray;
       
	}
    
    private double[][] getRatioDataArrayWeight(){

  		double[][] tempArray = new double[ds.getIsotopeViktorWeight().size()][1];
     
     	String numNucSimSet = "";
     	String denomNucSimSet = "";

		Point[] numNucSimZAMapArray = new Point[0];
		Point[] denomNucSimZAMapArray = new Point[0];

		int[] numIndexArray = new int[0];
		int[] denomIndexArray = new int[0];
	
		double[] numFinalAbundArray = new double[0];
		double[] denomFinalAbundArray = new double[0];
	
     	if(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel!=null){
     	
     		numNucSimSet = (String)Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.numComboBox.getSelectedItem();
     		denomNucSimSet = (String)Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.denomComboBox.getSelectedItem();
     	
     	}else{
     		
     		String stringNum = ds.getZoneNucSimSetDataStructureArray()[0].getPath();
     		String stringDenom = ds.getZoneNucSimSetDataStructureArray()[1].getPath();
     		
     		numNucSimSet = stringNum.substring(stringNum.lastIndexOf("/")+1);
     		denomNucSimSet = stringDenom.substring(stringDenom.lastIndexOf("/")+1);
     		
     	}

 		for(int i=0; i<ds.getZoneNucSimSetDataStructureArray().length; i++){
 		
 			String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
 		
 			if(string.substring(string.lastIndexOf("/")+1).equals(numNucSimSet)){
 				
 				numNucSimZAMapArray = ds.getZoneNucSimSetDataStructureArray()[i].getZAMapArray();
 				numIndexArray = ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray();
 				numFinalAbundArray = ds.getZoneNucSimSetDataStructureArray()[i].getFinalAbundArray();
 				
 			}
 			
 			string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
 		
 			if(string.substring(string.lastIndexOf("/")+1).equals(denomNucSimSet)){
 				
 				denomNucSimZAMapArray = ds.getZoneNucSimSetDataStructureArray()[i].getZAMapArray();
 				denomIndexArray = ds.getZoneNucSimSetDataStructureArray()[i].getIndexArray();
				denomFinalAbundArray = ds.getZoneNucSimSetDataStructureArray()[i].getFinalAbundArray();
				
 			}
 			
 		}
 		
 		for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
 			
 			int currentZ = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX();
 			
 			int currentA = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX()
 							+ (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getY();

 			boolean inNumPoint = false;
 			boolean inDenomPoint = false;
 			
 			double numAbund = 0.0;
 			double denomAbund = 0.0;

 			for(int j=0; j<numIndexArray.length; j++){
 			
 				int currentZNum = (int)(numNucSimZAMapArray[numIndexArray[j]].getX());
 				int currentANum = (int)(numNucSimZAMapArray[numIndexArray[j]].getY());

 				if((currentZNum==currentZ) && (currentANum==currentA)){
 				
 					inNumPoint = true;
 					numAbund += numFinalAbundArray[j];
 				}
 			
 			}
 			
 			for(int j=0; j<denomIndexArray.length; j++){
 			
				int currentZDenom = (int)(denomNucSimZAMapArray[denomIndexArray[j]].getX());
				int currentADenom = (int)(denomNucSimZAMapArray[denomIndexArray[j]].getY());

 				if((currentZDenom==currentZ) && (currentADenom==currentA)){
 				
 					inDenomPoint = true;
 					denomAbund += denomFinalAbundArray[j];
 		
 				}
 			
 			}

 			if(inNumPoint && inDenomPoint && numAbund!=0.0 && denomAbund!=0.0){
 			
 				tempArray[i][0] = numAbund/denomAbund;

 			}else{
 			
 				tempArray[i][0] = 0.0;
 			
 			}

 		}

    	return tempArray;
       
	}
    
    private int getMassminWeightDoubleValue(){
	    
    	double[][] tempArray = ds.getMassDataArrayWeight();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)min;
    
    }
    
	private int getMassmaxWeightDoubleValue(){
    
    	double[][] tempArray = ds.getMassDataArrayWeight();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)max;
    
    }
    
    private double getRatiominWeightDoubleValue(){
        
    	double[][] tempArray = ds.getRatioDataArrayWeight();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	min = Math.floor(min);
    	
    	return min;
    
    }

    private double getRatiomaxWeightDoubleValue(){
    
    	double[][] tempArray = ds.getRatioDataArrayWeight();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	max = Math.ceil(max);
    	
    	return max;
    
    }
    
    /**
     * Gets the weightmin.
     *
     * @return the weightmin
     */
    public double getWeightmin(){return Double.valueOf((String)weightminComboBox.getSelectedItem()).doubleValue();} 
	
	/**
	 * Gets the weightmax.
	 *
	 * @return the weightmax
	 */
	public double getWeightmax(){return Double.valueOf((String)weightmaxComboBox.getSelectedItem()).doubleValue();}

	/**
	 * Gets the zonemin.
	 *
	 * @return the zonemin
	 */
	public double getZonemin(){return Double.valueOf(zoneminField.getText()).doubleValue();} 
	
	/**
	 * Gets the zonemax.
	 *
	 * @return the zonemax
	 */
	public double getZonemax(){return Double.valueOf(zonemaxField.getText()).doubleValue();}

	/**
	 * Gets the ratiomin weight.
	 *
	 * @return the ratiomin weight
	 */
	public double getRatiominWeight(){return Double.valueOf(ratiominField.getText()).doubleValue();} 
	
	/**
	 * Gets the ratiomax weight.
	 *
	 * @return the ratiomax weight
	 */
	public double getRatiomaxWeight(){return Double.valueOf(ratiomaxField.getText()).doubleValue();}

	/**
	 * Gets the massmin weight.
	 *
	 * @return the massmin weight
	 */
	public double getMassminWeight(){return Double.valueOf(massminField.getText()).doubleValue();} 
	
	/**
	 * Gets the massmax weight.
	 *
	 * @return the massmax weight
	 */
	public double getMassmaxWeight(){return Double.valueOf(massmaxField.getText()).doubleValue();}

	/**
	 * Gets the minor zone.
	 *
	 * @return the minor zone
	 */
	public boolean getMinorZone(){return minorZoneCheckBox.isSelected();} 
	
	/**
	 * Gets the major zone.
	 *
	 * @return the major zone
	 */
	public boolean getMajorZone(){return majorZoneCheckBox.isSelected();} 
	
	/**
	 * Gets the minor weight.
	 *
	 * @return the minor weight
	 */
	public boolean getMinorWeight(){return minorWeightCheckBox.isSelected();} 
	
	/**
	 * Gets the major weight.
	 *
	 * @return the major weight
	 */
	public boolean getMajorWeight(){return majorWeightCheckBox.isSelected();} 
    
    /**
     * Gets the minor mass.
     *
     * @return the minor mass
     */
    public boolean getMinorMass(){return minorMassCheckBox.isSelected();} 
	
	/**
	 * Gets the major mass.
	 *
	 * @return the major mass
	 */
	public boolean getMajorMass(){return majorMassCheckBox.isSelected();} 
	
	/**
	 * Gets the minor ratio.
	 *
	 * @return the minor ratio
	 */
	public boolean getMinorRatio(){return minorRatioCheckBox.isSelected();} 
	
	/**
	 * Gets the major ratio.
	 *
	 * @return the major ratio
	 */
	public boolean getMajorRatio(){return majorRatioCheckBox.isSelected();}
    
    /**
     * Redraw plot.
     */
    public void redrawPlot(){
    
    	if(elementVizWeightIsotopeListPanel.ratioRadioButton.isSelected()){
	       	
       		try{
       		
       			if(Double.valueOf(ratiominField.getText()).doubleValue() == Double.valueOf(ratiomaxField.getText()).doubleValue()){
       				ratiomaxField.setText(String.valueOf(Double.valueOf(ratiominField.getText()).doubleValue()+1));
       			}
       			
       			if(Integer.valueOf(massminField.getText()).intValue() == Integer.valueOf(massmaxField.getText()).intValue()){
       				massmaxField.setText(String.valueOf(Integer.valueOf(massminField.getText()).intValue()+1));
       			}
       			
       			if(Double.valueOf(ratiominField.getText()).doubleValue() >= Double.valueOf(ratiomaxField.getText()).doubleValue()){
       			
       				String string = "Ratio minimum must be less than ratio maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       			
       			}else if(Integer.valueOf(massminField.getText()).intValue() >= Integer.valueOf(massmaxField.getText()).intValue()){
       			
       				String string = "Mass minimum must be less than mass maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       				
       			}else if(Integer.valueOf(massminField.getText()).intValue()<0){
       			
       				String string = "Mass minimum must be greater than or equal to zero.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       			
       			}else{
       			
       				elementVizWeightPlotPanel.setPreferredSize(elementVizWeightPlotPanel.getSize());
		
					elementVizWeightPlotPanel.setPlotState();
			   		
			   		elementVizWeightPlotPanel.repaint();
       			
       			}
       		
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for mass minimum and maximum must be integer values. Values for ratio minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       	
       		}

       	}else if(elementVizWeightIsotopeListPanel.weightRadioButton.isSelected()){
       	
       		try{
       			
       			if(Integer.valueOf(weightminComboBox.getSelectedItem().toString()).intValue()
       				>= Integer.valueOf(weightmaxComboBox.getSelectedItem().toString()).intValue()){
       		
		   			String string = "Final weighted abundance minimum must be less than final weighted abundance maximum.";
		   			
		   			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       			
       			}else if(Double.valueOf(zoneminField.getText()).doubleValue() >= Double.valueOf(zonemaxField.getText()).doubleValue()){
       			
       				String string = "Zone minimum must be less than zone maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       				
       			}else{
       			
       				elementVizWeightPlotPanel.setPreferredSize(elementVizWeightPlotPanel.getSize());
		
					elementVizWeightPlotPanel.setPlotState();
			   		
			   		elementVizWeightPlotPanel.repaint();
       			
       			}
       			
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for zone minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
       	
       		}
       	
       	}

    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	for(int i=0; i<elementVizWeightIsotopeListPanel.checkBoxArray.length; i++){
    	
    		if(elementVizWeightIsotopeListPanel.checkBoxArray[i].isSelected()){
    		
    			noneChecked = false;
    		
    		}
    	
    	}
    	
    	return noneChecked;
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
       if(ae.getSource()==tableButton){
       	
       		if(!noneChecked()){
       	
	       		if(elementVizWeightTableFrame==null){
	       	
	       			elementVizWeightTableFrame = new ElementVizWeightTableFrame(ds);
	       		
		       	}else{
		       	
		       		elementVizWeightTableFrame.setTableText();
		       		elementVizWeightTableFrame.setVisible(true);
		       	
		       	}
	       	
	       	}else{
	       	
	       		String string = "Please select isotopes for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
	       		
	       	}
	       	
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new ElementVizWeightPlotFramePrintable(), Cina.elementVizFrame.elementVizWeightPlotFrame);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(elementVizWeightPlotPanel, Cina.elementVizFrame.elementVizWeightPlotFrame);
       		
       	}else if(ae.getSource()==legendBoxWeight || ae.getSource()==legendBoxRatio){
       		
       		redrawPlot();
       		
       	}
    }
    
}  


class ElementVizWeightPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.elementVizWeightPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}