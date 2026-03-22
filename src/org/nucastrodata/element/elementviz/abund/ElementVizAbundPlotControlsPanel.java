package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import info.clearthought.layout.*;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundIsotopeListPanel;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotFramePrintable;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotPanel;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundTableFrame;
import org.nucastrodata.element.elementviz.abund.TimeDialog;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import javax.swing.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.dialogs.GeneralDialog;
import java.awt.event.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.WizardPanel;


/**
 * The Class ElementVizAbundPlotControlsPanel.
 */
public class ElementVizAbundPlotControlsPanel extends WizardPanel implements ItemListener, ActionListener{
    
	/** The Constant TIME_LOG. */
	public static final int TIME_LOG = 0;
	
	/** The Constant TIME_LIN. */
	public static final int TIME_LIN = 1;
	
    /** The toolkit. */
    Toolkit toolkit;

    /** The control panel. */
    JPanel abundPanel, ratioPanel, buttonPanel, controlPanel;
    
    /** The abundmax combo box. */
    JComboBox abundminComboBox, abundmaxComboBox;
    
    /** The timemax combo box. */
    JComboBox timeminComboBox, timemaxComboBox;
	
	/** The denom combo box. */
	public JComboBox numComboBox, denomComboBox;

    /** The element viz abund isotope list panel. */
    ElementVizAbundIsotopeListPanel elementVizAbundIsotopeListPanel;
    
    /** The element viz abund plot panel. */
    ElementVizAbundPlotPanel elementVizAbundPlotPanel;
    
    /** The element viz abund table frame. */
    public ElementVizAbundTableFrame elementVizAbundTableFrame;
    
    /** The change button. */
    JButton printButton, saveButton, tableButton, applyButton, changeButton;

    /** The minor ratio check box. */
    JCheckBox majorTimeCheckBox, majorAbundCheckBox, minorTimeCheckBox, minorAbundCheckBox
    			, majorMassCheckBox, majorRatioCheckBox, minorMassCheckBox, minorRatioCheckBox;
    			
	/** The time field. */
	JTextField timemaxField, timeminField, massminField, massmaxField, ratiominField, ratiomaxField, timeField;
	
	/** The time lin button. */
	public JRadioButton timeLogButton, timeLinButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;
	
	/** The time label. */
	JLabel timemaxLabel, timeminLabel, abundmaxLabel, abundminLabel
			, massmaxLabel, massminLabel, ratiomaxLabel, ratiominLabel
			, numLabel, denomLabel, timeLabel;
	
	/** The abundmin combo box init. */
	int abundminComboBoxInit;
    
    /** The abundmax combo box init. */
    int abundmaxComboBoxInit;
    
    /** The timemin combo box init. */
    int timeminComboBoxInit;
    
    /** The timemax combo box init. */
    int timemaxComboBoxInit;
    
    /** The legend box ratio. */
    JCheckBox legendBoxAbund, legendBoxRatio;
    
    /** The sp1. */
    JScrollPane sp, sp1;

    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /** The time. */
    public double time = Double.MIN_VALUE;
    
    /**
     * Instantiates a new element viz abund plot controls panel.
     *
     * @param ds the ds
     */
    public ElementVizAbundPlotControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;

		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Abundance Plotting Interface", "Plot Abundance or Ratio of Abundance", "2", "2");

		abundminComboBoxInit = setInitialAbundminComboBoxInit();
		abundmaxComboBoxInit = setInitialAbundmaxComboBoxInit();
		
		timeminComboBoxInit = 0;
		timemaxComboBoxInit = 3;
		
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
		
		time = Double.MIN_VALUE;
		
		abundminComboBoxInit = setInitialAbundminComboBoxInit();
		abundmaxComboBoxInit = setInitialAbundmaxComboBoxInit();

		abundminComboBox.removeItemListener(this);
		abundmaxComboBox.removeItemListener(this);
		
		abundminComboBox.setSelectedItem(String.valueOf(abundminComboBoxInit));
        abundmaxComboBox.setSelectedItem(String.valueOf(abundmaxComboBoxInit));
        
        abundminComboBox.addItemListener(this);
		abundmaxComboBox.addItemListener(this);
        
		//timeminComboBoxInit = setInitialTimeminComboBoxInit();
		//timemaxComboBoxInit = setInitialTimemaxComboBoxInit();
		
		timeminComboBoxInit = 0;
		timemaxComboBoxInit = 3;

		timeminComboBox.removeItemListener(this);
		timemaxComboBox.removeItemListener(this);
		
		timeminComboBox.setSelectedItem(String.valueOf(timeminComboBoxInit));
        timemaxComboBox.setSelectedItem(String.valueOf(timemaxComboBoxInit));
        
        timeminComboBox.addItemListener(this);
		timemaxComboBox.addItemListener(this);
		
        timeminField.setText(String.valueOf(ds.getTimeminAbund()));
        timemaxField.setText(String.valueOf(ds.getTimemaxAbund()));
        
        massminField.setText(String.valueOf(ds.getMassminAbund()));
        massmaxField.setText(String.valueOf(ds.getMassmaxAbund()));
        
        ratiominField.setText(String.valueOf(ds.getRatiominAbund()));
        ratiomaxField.setText(String.valueOf(ds.getRatiomaxAbund()));
        
        if(ds.getNucSimVector().size()==1){
        	elementVizAbundIsotopeListPanel.ratioRadioButton.setEnabled(false);
        }else{
        	elementVizAbundIsotopeListPanel.ratioRadioButton.setEnabled(true);
        }
        
        numComboBox.removeItemListener(this);
        numComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			numComboBox.addItem(ds.getNucSimDataStructureArray()[i].getNucSimName());
		}
		
		if(ds.getNumberNucSimDataStructures()!=1){
			numComboBox.setSelectedItem(ds.getNucSimDataStructureArray()[0].getNucSimName());
		}
		
        numComboBox.addItemListener(this);
        
        denomComboBox.removeItemListener(this);
        denomComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			denomComboBox.addItem(ds.getNucSimDataStructureArray()[i].getNucSimName());
		}
		
		if(ds.getNumberNucSimDataStructures()!=1){
			denomComboBox.setSelectedItem(ds.getNucSimDataStructureArray()[1].getNucSimName());
		}
		
        denomComboBox.addItemListener(this);

	}
    
    /**
     * Creates the isotope list panel.
     */
    public void createIsotopeListPanel(){
    
    	JPanel isotopeListPanel = new JPanel();
    	
        isotopeListPanel.setLayout(new GridBagLayout());
		
		elementVizAbundIsotopeListPanel = new ElementVizAbundIsotopeListPanel(ds, this);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        isotopeListPanel.add(elementVizAbundIsotopeListPanel, gbc);   
          
        sp = new JScrollPane(isotopeListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    /**
     * Sets the isotope list panel.
     */
    public void setIsotopeListPanel(){
    	elementVizAbundIsotopeListPanel.initialize();
    }
    
    /**
     * Creates the format panel.
     */
    public void createFormatPanel(){   
		
		buttonPanel = new JPanel();

		abundPanel = new JPanel(new GridBagLayout());
		
		ratioPanel = new JPanel(new GridBagLayout());
		
		controlPanel = new JPanel(new GridBagLayout());
		
		legendBoxAbund = new JCheckBox("Show Legend?", true);
	    legendBoxAbund.setFont(Fonts.textFont);
	    legendBoxAbund.addActionListener(this);
		
	    legendBoxRatio = new JCheckBox("Show Legend?", true);
	    legendBoxRatio.setFont(Fonts.textFont);
	    legendBoxRatio.addActionListener(this);
	    
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
        
		applyButton = new JButton("Apply Time Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);
        
        changeButton = new JButton("Set Ratio Time");
        changeButton.setFont(Fonts.buttonFont);
        changeButton.addActionListener(this);
        changeButton.setEnabled(false);
        
        timeField = new JTextField(5);
        
        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(changeButton);

        //RADIO BUTTONS////////////////////////////////////////////////////////////////////
        timeLogButton = new JRadioButton("log", false);
        timeLogButton.setFont(Fonts.textFont);
        timeLogButton.addItemListener(this);
        
        timeLinButton = new JRadioButton("lin", true);
        timeLinButton.setFont(Fonts.textFont);
        timeLinButton.addItemListener(this);
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(timeLogButton);
        buttonGroup.add(timeLinButton);
        
        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        abundminComboBox = new JComboBox();
        abundminComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-30; i--) {abundminComboBox.addItem(Integer.toString(i));}
        abundminComboBox.setSelectedItem(String.valueOf(abundminComboBoxInit));
		abundminComboBox.addItemListener(this);

        abundmaxComboBox = new JComboBox();
        abundmaxComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-29; i--) {abundmaxComboBox.addItem(Integer.toString(i));}
        abundmaxComboBox.setSelectedItem(String.valueOf(abundmaxComboBoxInit));
		abundmaxComboBox.addItemListener(this);
		
		timeminComboBox = new JComboBox();
        timeminComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-3; i--) {timeminComboBox.addItem(Integer.toString(i));}
        timeminComboBox.setSelectedItem(String.valueOf(timeminComboBoxInit));
		timeminComboBox.addItemListener(this);

        timemaxComboBox = new JComboBox();
        timemaxComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-2; i--) {timemaxComboBox.addItem(Integer.toString(i));}
        timemaxComboBox.setSelectedItem(String.valueOf(timemaxComboBoxInit));
		timemaxComboBox.addItemListener(this);
		
		numComboBox = new JComboBox();
		numComboBox.setFont(Fonts.textFont);
		
		numComboBox.removeItemListener(this);
        numComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			numComboBox.addItem(ds.getNucSimDataStructureArray()[i].getNucSimName());
		}
		
		if(ds.getNumberNucSimDataStructures()!=1){
			numComboBox.setSelectedItem(ds.getNucSimDataStructureArray()[0].getNucSimName());
		}
		
        numComboBox.addItemListener(this);
        
        denomComboBox = new JComboBox();
		denomComboBox.setFont(Fonts.textFont);
        
        denomComboBox.removeItemListener(this);
        denomComboBox.removeAllItems();
        for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			denomComboBox.addItem(ds.getNucSimDataStructureArray()[i].getNucSimName());
		}
		
		if(ds.getNumberNucSimDataStructures()!=1){
			denomComboBox.setSelectedItem(ds.getNucSimDataStructureArray()[1].getNucSimName());
		}
		
        denomComboBox.addItemListener(this);
        
        //CREATE FIELDS/////////////////////////////////////////////////////////FIELDS/////////////////////
        
        timemaxField = new JTextField(7);
        timeminField = new JTextField(7);
        
        massmaxField = new JTextField(5);
        massminField = new JTextField(5);
        
        ratiomaxField = new JTextField(5);
        ratiominField = new JTextField(5);
        
        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        timeLabel = new JLabel("Time");
        timeLabel.setFont(Fonts.textFont);
        
        timemaxLabel = new JLabel("Max");
        timemaxLabel.setFont(Fonts.textFont);
        
        timeminLabel = new JLabel("Time Min");
        timeminLabel.setFont(Fonts.textFont);
        
        abundmaxLabel = new JLabel("log Max");
        abundmaxLabel.setFont(Fonts.textFont);
        
        abundminLabel = new JLabel("log Abund Min");
        abundminLabel.setFont(Fonts.textFont);
        
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
        majorTimeCheckBox = new JCheckBox("Major Gridlines", true);
        minorTimeCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorAbundCheckBox = new JCheckBox("Major Gridlines", true);
        minorAbundCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorMassCheckBox = new JCheckBox("Major Gridlines", true);
        minorMassCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorRatioCheckBox = new JCheckBox("Major Gridlines", true);
        minorRatioCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorTimeCheckBox.addItemListener(this);
        minorTimeCheckBox.addItemListener(this);
        majorAbundCheckBox.addItemListener(this);
        minorAbundCheckBox.addItemListener(this);

		majorMassCheckBox.addItemListener(this);
        minorMassCheckBox.addItemListener(this);
        majorRatioCheckBox.addItemListener(this);
        minorRatioCheckBox.addItemListener(this);

		majorTimeCheckBox.setFont(Fonts.textFont);
        minorTimeCheckBox.setFont(Fonts.textFont);
        majorAbundCheckBox.setFont(Fonts.textFont);
        minorAbundCheckBox.setFont(Fonts.textFont);

		majorMassCheckBox.setFont(Fonts.textFont);
        minorMassCheckBox.setFont(Fonts.textFont);
        majorRatioCheckBox.setFont(Fonts.textFont);
        minorRatioCheckBox.setFont(Fonts.textFont);
        
        gbc.insets = new Insets(3, 15, 3, -13);

        gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(timeLabel, gbc);
  		
  		gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(abundminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(abundminComboBox, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(abundmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(abundmaxComboBox, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(majorAbundCheckBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(minorAbundCheckBox, gbc);
  		
  		gbc.gridx = 8;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(legendBoxAbund, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(timeLogButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(timeLinButton, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(timeminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(timeminField, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		abundPanel.add(timemaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(timemaxField, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(majorTimeCheckBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		abundPanel.add(minorTimeCheckBox, gbc);
  		
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
  		controlPanel.add(abundPanel, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		controlPanel.add(buttonPanel, gbc);
  		
  		add(controlPanel, BorderLayout.SOUTH);
        
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    
    	elementVizAbundPlotPanel = new ElementVizAbundPlotPanel(ds, this, elementVizAbundIsotopeListPanel);
		
		elementVizAbundPlotPanel.setSize(442, 546);
		elementVizAbundPlotPanel.setPreferredSize(elementVizAbundPlotPanel.getSize());
		elementVizAbundPlotPanel.revalidate();
		
		sp1 = new JScrollPane(elementVizAbundPlotPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		sp1.validate();
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(550);
       	
       	add(jsp, BorderLayout.CENTER);
		
    }
    
    /**
     * Sets the initial abundmin combo box init.
     *
     * @return the int
     */
    public int setInitialAbundminComboBoxInit(){
	
		int min = 0;

		min = ds.getAbundmin();
		
		if(min < -30){
			
			min = -30;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initial abundmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialAbundmaxComboBoxInit(){
	
		int max = 0;
		
		max = ds.getAbundmax();
		
		if(max > 30){
			
			max = 30;
		
		}
		
		return max;
	
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
        
        if(ie.getSource()==majorTimeCheckBox){
      		
            if(majorTimeCheckBox.isSelected()){
            	
                minorTimeCheckBox.setEnabled(true);
                
            }else{
            	
                minorTimeCheckBox.setSelected(false);
                minorTimeCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorAbundCheckBox){
        	
            if(majorAbundCheckBox.isSelected()){
            	
                minorAbundCheckBox.setEnabled(true);
                
            }else{

                minorAbundCheckBox.setSelected(false);
                minorAbundCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorTimeCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorAbundCheckBox){
        	
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
               
        }else if(ie.getSource()==abundminComboBox || ie.getSource()==abundmaxComboBox){

       		redrawPlot();

        }else if(ie.getSource()==timeminComboBox || ie.getSource()==timemaxComboBox){

       		redrawPlot();

        }else if(ie.getSource()==numComboBox || ie.getSource()==denomComboBox){
        
        	reloadRatioDataAtDifferentTime(time);
			
        	/*if(!goodStopTime() && time==Double.MIN_VALUE){
	  			String string = "The simulations chosen for abundance ratios do not have the same stoptime.";
	  			CinaDialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
	  		}*/
        	
        	redrawPlot();
        
        }else if(ie.getSource()==timeLogButton || ie.getSource()==timeLinButton){
        	if(timeLogButton.isSelected()){
        		setInterface(TIME_LOG);
        	}else if(timeLinButton.isSelected()){
        		setInterface(TIME_LIN);
        	}
        	
        	redrawPlot();
        	
        }
  
    }
    
    /**
     * Sets the interface.
     *
     * @param type the new interface
     */
    private void setInterface(int type){
    	
    	if(type==TIME_LOG){

    		timeminLabel.setText("log Time Min");
    		timemaxLabel.setText("log Max");
    		
    		abundPanel.remove(timeminField);
    		abundPanel.remove(timemaxField);
    		
    		gbc.insets = new Insets(3, 3, 3, 3);
    		
    		gbc.gridx = 3;
      		gbc.gridy = 1;
      		gbc.anchor = GridBagConstraints.EAST;
      		abundPanel.add(timeminComboBox, gbc);
      		
      		gbc.gridx = 5;
      		gbc.gridy = 1;
      		gbc.anchor = GridBagConstraints.EAST;
      		abundPanel.add(timemaxComboBox, gbc);
    		
    	}else if(type==TIME_LIN){
    		
    		timeminLabel.setText("Time Min");
    		timemaxLabel.setText("Max");
    		
    		abundPanel.remove(timeminComboBox);
    		abundPanel.remove(timemaxComboBox);
    		
    		gbc.insets = new Insets(3, 3, 3, 3);
    		
    		gbc.gridx = 3;
      		gbc.gridy = 1;
      		gbc.anchor = GridBagConstraints.EAST;
      		abundPanel.add(timeminField, gbc);
      		
      		gbc.gridx = 5;
      		gbc.gridy = 1;
      		gbc.anchor = GridBagConstraints.EAST;
      		abundPanel.add(timemaxField, gbc);
      		
    	}
    	
    	
    	validate();
    }
    
    /**
     * Good stop time.
     *
     * @return true, if successful
     */
    public boolean goodStopTime(){
    	
    	boolean goodStopTime = true;

    	double time1 = 0.0;
    	double time2 = 0.0;
    	
    	timesFound:
		for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){

			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals((String)numComboBox.getSelectedItem())){
			
				time1 = ds.getNucSimDataStructureArray()[i].getTimeArray()[ds.getNucSimDataStructureArray()[i].getTimeArray().length-1];
			
			}
			
			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals((String)denomComboBox.getSelectedItem())){
			
				time2 = ds.getNucSimDataStructureArray()[i].getTimeArray()[ds.getNucSimDataStructureArray()[i].getTimeArray().length-1];
			
			}

		}
		
		if(time1!=time2){
		
			goodStopTime = false;
		
		}
    	
    	return goodStopTime;
    
    }
    
    /**
     * Gets the abundmin.
     *
     * @return the abundmin
     */
    public double getAbundmin(){return Double.valueOf((String)abundminComboBox.getSelectedItem()).doubleValue();} 
	
	/**
	 * Gets the abundmax.
	 *
	 * @return the abundmax
	 */
	public double getAbundmax(){return Double.valueOf((String)abundmaxComboBox.getSelectedItem()).doubleValue();}

	/**
	 * Gets the timemin.
	 *
	 * @return the timemin
	 */
	public double getTimemin(){return Double.valueOf(timeminField.getText()).doubleValue();} 
	
	/**
	 * Gets the timemax.
	 *
	 * @return the timemax
	 */
	public double getTimemax(){return Double.valueOf(timemaxField.getText()).doubleValue();}

	/**
	 * Gets the timemin log.
	 *
	 * @return the timemin log
	 */
	public double getTimeminLog(){return Double.valueOf(timeminComboBox.getSelectedItem().toString()).doubleValue();} 
	
	/**
	 * Gets the timemax log.
	 *
	 * @return the timemax log
	 */
	public double getTimemaxLog(){return Double.valueOf(timemaxComboBox.getSelectedItem().toString()).doubleValue();}
	
	/**
	 * Gets the ratiomin abund.
	 *
	 * @return the ratiomin abund
	 */
	public double getRatiominAbund(){return Double.valueOf(ratiominField.getText()).doubleValue();} 
	
	/**
	 * Gets the ratiomax abund.
	 *
	 * @return the ratiomax abund
	 */
	public double getRatiomaxAbund(){return Double.valueOf(ratiomaxField.getText()).doubleValue();}

	/**
	 * Gets the massmin abund.
	 *
	 * @return the massmin abund
	 */
	public double getMassminAbund(){return Double.valueOf(massminField.getText()).doubleValue();} 
	
	/**
	 * Gets the massmax abund.
	 *
	 * @return the massmax abund
	 */
	public double getMassmaxAbund(){return Double.valueOf(massmaxField.getText()).doubleValue();}

	/**
	 * Gets the minor time.
	 *
	 * @return the minor time
	 */
	public boolean getMinorTime(){return minorTimeCheckBox.isSelected();} 
	
	/**
	 * Gets the major time.
	 *
	 * @return the major time
	 */
	public boolean getMajorTime(){return majorTimeCheckBox.isSelected();} 
	
	/**
	 * Gets the minor abund.
	 *
	 * @return the minor abund
	 */
	public boolean getMinorAbund(){return minorAbundCheckBox.isSelected();} 
	
	/**
	 * Gets the major abund.
	 *
	 * @return the major abund
	 */
	public boolean getMajorAbund(){return majorAbundCheckBox.isSelected();} 
    
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
    
    	if(elementVizAbundIsotopeListPanel.ratioRadioButton.isSelected()){
	       	
       		try{
       		
       			if(Double.valueOf(ratiominField.getText()).doubleValue() == Double.valueOf(ratiomaxField.getText()).doubleValue()){
       				ratiomaxField.setText(String.valueOf(Double.valueOf(ratiominField.getText()).doubleValue()+1));
       			}
       			
       			if(Integer.valueOf(massminField.getText()).intValue() == Integer.valueOf(massmaxField.getText()).intValue()){
       				massmaxField.setText(String.valueOf(Integer.valueOf(massminField.getText()).intValue()+1));
       			}
       			
       			if(Double.valueOf(ratiominField.getText()).doubleValue() >= Double.valueOf(ratiomaxField.getText()).doubleValue()){
       			
       				String string = "Ratio minimum must be less than ratio maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       			
       			}else if(Integer.valueOf(massminField.getText()).intValue() >= Integer.valueOf(massmaxField.getText()).intValue()){
       			
       				String string = "Mass minimum must be less than mass maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       				
       			}else if(Integer.valueOf(massminField.getText()).intValue()<0){
       			
       				String string = "Mass minimum must be greater than or equal to zero.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       			
       			}else{
       			
       				elementVizAbundPlotPanel.setPreferredSize(elementVizAbundPlotPanel.getSize());
		
					elementVizAbundPlotPanel.setPlotState();
			   		
			   		elementVizAbundPlotPanel.repaint();
       			
       			}
       		
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for mass minimum and maximum must be integer values. Values for ratio minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       	
       		}

       	}else if(elementVizAbundIsotopeListPanel.abundRadioButton.isSelected()){
       	
       		try{
       		
       			if(Integer.valueOf(abundminComboBox.getSelectedItem().toString()).intValue()
       				>= Integer.valueOf(abundmaxComboBox.getSelectedItem().toString()).intValue()){
       		
		   			String string = "Abundance minimum must be less than abundance maximum.";
		   			
		   			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
		   			
       			}else if(timeLogButton.isSelected() && (Integer.valueOf(timeminComboBox.getSelectedItem().toString()).intValue()
           				>= Integer.valueOf(timemaxComboBox.getSelectedItem().toString()).intValue())){
       			
       				String string = "Time minimum must be less than abundance maximum.";
		   			
		   			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       				
       			}else if(timeLinButton.isSelected() && (Double.valueOf(timeminField.getText()).doubleValue() >= Double.valueOf(timemaxField.getText()).doubleValue())){
       			
       				String string = "Time minimum must be less than time maximum.";
       				
       				Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       				
       			}else{
       			
       				elementVizAbundPlotPanel.setPreferredSize(elementVizAbundPlotPanel.getSize());
		
					elementVizAbundPlotPanel.setPlotState();
			   		
			   		elementVizAbundPlotPanel.repaint();
       			
       			}
       			
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for time minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
       	
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
    	
    	for(int i=0; i<elementVizAbundIsotopeListPanel.checkBoxArray.length; i++){
    	
    		if(elementVizAbundIsotopeListPanel.checkBoxArray[i].isSelected()){
    		
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
       	
	       		if(elementVizAbundTableFrame==null){
	       	
	       			elementVizAbundTableFrame = new ElementVizAbundTableFrame(ds);
	       		
		       	}else{
		       	
		       		elementVizAbundTableFrame.setTableText();
		       		elementVizAbundTableFrame.setVisible(true);
		       	
		       	}
	       	
	       	}else{
	       	
	       		String string = "Please select isotopes for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
	       		
	       	}
	       	
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new ElementVizAbundPlotFramePrintable(), Cina.elementVizFrame.elementVizAbundPlotFrame);
       	
       	}else if(ae.getSource()==saveButton){
       		PlotSaver.savePlot(elementVizAbundPlotPanel, Cina.elementVizFrame.elementVizAbundPlotFrame);
       	}else if(ae.getSource()==legendBoxAbund || ae.getSource()==legendBoxRatio){
       		redrawPlot();
       	}else if(ae.getSource()==changeButton){
	       	TimeDialog dialog = new TimeDialog(Cina.elementVizFrame.elementVizAbundPlotFrame);
	       	dialog.setLocation((int)(getLocation().getX() + getWidth()/2.0 - dialog.getWidth()/2)
					, (int)(getLocation().getY() + getHeight()/2.0 - dialog.getHeight()/2));
	       	dialog.setCurrentState(time);
	       	dialog.setVisible(true);
       			
       	}
       
    }
    
    /**
     * Reload ratio data at different time.
     *
     * @param time the time
     */
    public void reloadRatioDataAtDifferentTime(double time){
    
	    ds.setMassDataArrayAbund(getMassDataArrayAbund());
		ds.setRatioDataArrayAbund(getRatioDataArrayAbund(time));
		
		ds.setMassmaxAbund(getMassmaxAbundIntValue());
		ds.setMassminAbund(getMassminAbundIntValue());
		
		ds.setRatiomaxAbund(getRatiomaxAbundDoubleValue());
		ds.setRatiominAbund(getRatiominAbundDoubleValue());

		ratiomaxField.setText(String.valueOf(ds.getRatiomaxAbund()));
		ratiominField.setText(String.valueOf(ds.getRatiominAbund()));
		
    }
    
    private int getMassminAbundIntValue(){
        
    	double[][] tempArray = ds.getMassDataArrayAbund();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)min;
    
    }
    
    private int getMassmaxAbundIntValue(){
    
    	double[][] tempArray = ds.getMassDataArrayAbund();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	return (int)max;
    
    }
    
	private double getRatiominAbundDoubleValue(){
    
    	double[][] tempArray = ds.getRatioDataArrayAbund();
    
    	double min = 1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			min = Math.min(min, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	//min = Double.valueOf(CinaNumberFormats.getFormattedRatio(min)).doubleValue();
    	
    	min = Math.floor(min);
    	
    	return min;
    
    }
    
    private double getRatiomaxAbundDoubleValue(){
    
    	double[][] tempArray = ds.getRatioDataArrayAbund();
    
    	double max = -1E100;
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		for(int j=0; j<tempArray[i].length; j++){
    		
    			max = Math.max(max, tempArray[i][j]);
    			
    		}
    	
    	}
    	
    	//max = Double.valueOf(CinaNumberFormats.getFormattedRatio(max)).doubleValue();
    	
    	max = Math.ceil(max);
    	
    	return max;
    
    }
    
    private double[][] getMassDataArrayAbund(){

  		double[][] tempArray = new double[ds.getIsotopeViktorAbund().size()][1];
     
       	for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
       	
       		tempArray[i][0] = ((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX()
       							+ ((Point)ds.getIsotopeViktorAbund().elementAt(i)).getY();

       	}	
       		
    	return tempArray;
       
	}
    
    /**
     * Gets the ratio data array abund.
     *
     * @param time the time
     * @return the ratio data array abund
     */
    public double[][] getRatioDataArrayAbund(double time){
    	  
  		int index1 = 0;

  		double[][] tempArray = new double[ds.getIsotopeViktorAbund().size()][1];
     
     	String numNucSim = "";
     	String denomNucSim = "";

		Point[] numNucSimZAMapArray = new Point[0];
		Point[] denomNucSimZAMapArray = new Point[0];

     	
     	numNucSim = (String)Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.numComboBox.getSelectedItem();
     	denomNucSim = (String)Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.denomComboBox.getSelectedItem();

 		AbundTimestepDataStructure appatdsNum = new AbundTimestepDataStructure();
 		AbundTimestepDataStructure appatdsDenom = new AbundTimestepDataStructure();

 		for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
 		
 			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals(numNucSim)){
 				
 				numNucSimZAMapArray = ds.getNucSimDataStructureArray()[i].getZAMapArray();
 			
 				if(time!=Double.MIN_VALUE){
 				
	 				int timestep = getTimeStepForTime(ds.getTimeDataArrayAbund()[i], time);
	 				
	 				appatdsNum.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[timestep].getAbundArray());
	 				appatdsNum.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[timestep].getIndexArray());
	 			
 				}else{
 					appatdsNum.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getAbundArray());
	 				appatdsNum.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getIndexArray());
	 			
	 			}
 			}
 			
 			if(ds.getNucSimDataStructureArray()[i].getNucSimName().equals(denomNucSim)){
 			
 				denomNucSimZAMapArray = ds.getNucSimDataStructureArray()[i].getZAMapArray();
 			
 				if(time!=Double.MIN_VALUE){
 				
	 				int timestep = getTimeStepForTime(ds.getTimeDataArrayAbund()[i], time);
	 				
	 				appatdsDenom.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[timestep].getAbundArray());
	 				appatdsDenom.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[timestep].getIndexArray());
 				
 				}else{
 					
 					appatdsDenom.setAbundArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getAbundArray());
	 				appatdsDenom.setIndexArray(ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray()[ds.getNucSimDataStructureArray()[i].getAbundTimestepDataStructureArray().length-1].getIndexArray());
 				
 					
 				}
 			}
 		
 		}
 		
 		for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
 			
 			int currentZ = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX();
 			
 			int currentA = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX()
 							+ (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getY();

 			boolean inNumPoint = false;
 			boolean inDenomPoint = false;
 			
 			double numAbund = 0.0;
 			double denomAbund = 0.0;

 			for(int j=0; j<appatdsNum.getIndexArray().length; j++){
 			
 				int currentZNum = -1;
 				int currentANum = -1;
 				
 				if(appatdsNum.getIndexArray()[j]!=-1){
 			
	 				currentZNum = (int)(numNucSimZAMapArray[appatdsNum.getIndexArray()[j]].getX());
	 				currentANum = (int)(numNucSimZAMapArray[appatdsNum.getIndexArray()[j]].getY());
 				
 				}
 				
 				if((currentZNum==currentZ) && (currentANum==currentA)){
 				
 					inNumPoint = true;
 					numAbund = appatdsNum.getAbundArray()[j];
 				
 				}
 			
 			}
 			
 			for(int j=0; j<appatdsDenom.getIndexArray().length; j++){
 			
 				int currentZDenom = -1;
 				int currentADenom = -1;
 			
 				if(appatdsDenom.getIndexArray()[j]!=-1){
 			
 					currentZDenom = (int)(denomNucSimZAMapArray[appatdsDenom.getIndexArray()[j]].getX());
 					currentADenom = (int)(denomNucSimZAMapArray[appatdsDenom.getIndexArray()[j]].getY());
 				
 				}
 				
 				if((currentZDenom==currentZ) && (currentADenom==currentA)){
 				
 					inDenomPoint = true;
 					denomAbund = appatdsDenom.getAbundArray()[j];
 				
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
    
    /**
     * Gets the time step for time.
     *
     * @param timeArray the time array
     * @param time the time
     * @return the time step for time
     */
    private int getTimeStepForTime(double[] timeArray, double time){
    	int timestep = 0;
    	for(int i=0; i<timeArray.length-1; i++){
    		if(timeArray[i]<time && timeArray[i+1]>time){
    			double left = Math.abs(timeArray[i]-time);
    			double right = Math.abs(timeArray[i+1]-time);
    			if(left<right){
    				return i;
    			}
				return i+1;
    		}
    	}
    	
    	return timestep;
    }
    
}  

class TimeDialog extends JDialog implements ActionListener{
	
	private JTextField timeField;
	private JRadioButton timeButton, finalButton;
	private JButton submitButton;
	
	public TimeDialog(JFrame frame){
		super(frame, "Set Ratio Time...", Dialog.ModalityType.APPLICATION_MODAL);
		setSize(450, 250);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		Container c = getContentPane();
		c.setLayout(new TableLayout(column, row));

		timeField = new JTextField(10);
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>To select a new time (sec) used to calculate the abundance ratio, "
							+"select that option, enter that time in the field below, and click <i>Submit</i>. "
							+ "To select the final timestep, select that option and click <i>Submit</i>.</html>");
		
		timeButton = new JRadioButton("Select new ratio time (sec)");
		timeButton.setFont(Fonts.textFont);
		timeButton.addActionListener(this);
		
		finalButton = new JRadioButton("Select final timestep (default)");
		finalButton.setFont(Fonts.textFont);
		finalButton.addActionListener(this);
		
		submitButton = new JButton("Submit Selection");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(timeButton);
		bg.add(finalButton);
		
		c.add(topLabel, "1, 1, 3, 1, f, c");
		c.add(timeButton, "1, 3, l, c");
		c.add(timeField, "3, 3, f, c");
		c.add(finalButton, "1, 5, l, c");
		c.add(submitButton, "1, 7, 3, 7, c, c");
	}
	
	public void setCurrentState(double time){
		if(time==Double.MIN_VALUE){
			timeButton.setSelected(false);
			timeField.setEditable(false);
			timeField.setText("");
			finalButton.setSelected(true);
		}else{
			timeButton.setSelected(true);
			timeField.setEditable(true);
			timeField.setText(String.valueOf(time));
			finalButton.setSelected(false);
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==submitButton){
			if(finalButton.isSelected()){
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.time = Double.MIN_VALUE;
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.reloadRatioDataAtDifferentTime(Double.MIN_VALUE);
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.redrawPlot();
				this.setVisible(false);
				this.dispose();
			}else if (goodData() && timeButton.isSelected()){
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.time = Double.valueOf(timeField.getText()).doubleValue();
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.reloadRatioDataAtDifferentTime(Double.valueOf(timeField.getText()).doubleValue());
				Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.redrawPlot();
				this.setVisible(false);
				this.dispose();
			}else{
				String string = "Please enter a numeric value of the time (sec) or select the Select final timestep (default) option.";
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
			}
		}else if(ae.getSource()==timeButton || ae.getSource()==finalButton){
			if(timeButton.isSelected()){
				timeField.setEditable(true);
			}else if(finalButton.isSelected()){
				timeField.setEditable(false);
			}
		}
	}
	
	private boolean goodData(){
		try{
			Double.valueOf(timeField.getText()).doubleValue();
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
}

class ElementVizAbundPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.elementVizFrame.elementVizAbundPlotFrame.elementVizAbundPlotControlsPanel.elementVizAbundPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}