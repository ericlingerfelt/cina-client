package org.nucastrodata.data.dataviewer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.awt.print.*;
import javax.swing.text.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.data.dataviewer.DataViewerInfoFrame;
import org.nucastrodata.data.dataviewer.DataViewerNucDataListPanel;
import org.nucastrodata.data.dataviewer.DataViewerPlotFramePrintable;
import org.nucastrodata.data.dataviewer.DataViewerPlotPanel;
import org.nucastrodata.data.dataviewer.DataViewerTableFrame;


/**
 * The Class DataViewerPlotFrame.
 */
public class DataViewerPlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener, ChangeListener{
    
    /** The toolkit. */
    Toolkit toolkit;

    /** The control panel. */
    JPanel panel4, panel5, controlPanel;

	/** The Emax spinner. */
	JSpinner CSminSpinner, CSmaxSpinner, SFminSpinner, SFmaxSpinner, EminSpinner, EmaxSpinner;

	/** The Emax model. */
	SpinnerNumberModel CSminModel, CSmaxModel, SFminModel, SFmaxModel, EminModel, EmaxModel;

    /** The data viewer nuc data list panel. */
    DataViewerNucDataListPanel dataViewerNucDataListPanel;

    /** The data viewer plot panel. */
    DataViewerPlotPanel dataViewerPlotPanel;
	
	/** The data viewer info frame. */
	DataViewerInfoFrame dataViewerInfoFrame;
    
    /** The data viewer table frame. */
    DataViewerTableFrame dataViewerTableFrame;
    
    /** The info button. */
    JButton printButton, saveButton, tableButton, applyButton, infoButton;

    /** The minor y check box. */
    JCheckBox majorXCheckBox, majorYCheckBox, minorXCheckBox, minorYCheckBox;
	
	/** The plot controls label. */
	JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel, xGridLabel, yGridLabel, plotControlsLabel;
	
	/** The SF radio button. */
	JRadioButton CSRadioButton, SFRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;
    
    /** The C smin spinner init. */
    int CSminSpinnerInit;
    
    /** The C smax spinner init. */
    int CSmaxSpinnerInit;

    /** The c. */
    Container c;
    
    /** The sp1. */
    JScrollPane sp, sp1;

    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The ds. */
    private DataViewerDataStructure ds;
    
    /**
     * Instantiates a new data viewer plot frame.
     *
     * @param ds the ds
     */
    public DataViewerPlotFrame(DataViewerDataStructure ds){
	
		this.ds = ds;
	
		if(ds.getNumberTotalCSNucData()>0){
			
			CSminSpinnerInit = setInitialCSminSpinnerInit();
			CSmaxSpinnerInit = setInitialCSmaxSpinnerInit();
		
		}
	
		c = getContentPane();
	
		setSize(805, 651);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		createFormatPanel();
		setFormatPanelState();
		createNucDataListPanel();
		setNucDataListPanel();
		createResultsPlot();
		
		
		
		validate();
		
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
	
		setTitle("Nuclear Data Plotting Interface");
	
		if(ds.getNumberTotalCSNucData()>0){
			
			CSminSpinnerInit = setInitialCSminSpinnerInit();
			CSmaxSpinnerInit = setInitialCSmaxSpinnerInit();
	
			CSminSpinner.removeChangeListener(this);
			CSmaxSpinner.removeChangeListener(this);
			
			CSminModel.setValue(new Integer(CSminSpinnerInit));
	        CSmaxModel.setValue(new Integer(CSmaxSpinnerInit));
	        
	        CSminSpinner.addChangeListener(this);
			CSmaxSpinner.addChangeListener(this);
	        
	        EminSpinner.removeChangeListener(this);
			EmaxSpinner.removeChangeListener(this);
			
			EminModel.setValue(new Double(ds.getEnergyCSmin()));
	        EmaxModel.setValue(new Double(ds.getEnergyCSmax()));
	        
	        EminSpinner.addChangeListener(this);
			EmaxSpinner.addChangeListener(this);
        
    	}else{
	        
	        EminSpinner.removeChangeListener(this);
			EmaxSpinner.removeChangeListener(this);
			
			EminModel.setValue(new Double(ds.getEnergySFmin()));
	        EmaxModel.setValue(new Double(ds.getEnergySFmax()));
	        
	        EminSpinner.addChangeListener(this);
			EmaxSpinner.addChangeListener(this);
	        
	        SFminSpinner.removeChangeListener(this);
			SFmaxSpinner.removeChangeListener(this);
			
			SFminModel.setValue(new Double(ds.getSFmin()));
	        SFmaxModel.setValue(new Double(ds.getSFmax()));
	        
	        SFminSpinner.addChangeListener(this);
			SFmaxSpinner.addChangeListener(this);
    	
    	}
        
        if(ds.getNumberTotalSFNucData()==0){
        
        	SFRadioButton.setEnabled(false);
        
        }else{
        
        	SFRadioButton.setEnabled(true);
        
        }
        
        if(ds.getNumberTotalCSNucData()==0){
        
        	CSRadioButton.setEnabled(false);
        
        }else{
        
        	CSRadioButton.setEnabled(true);
        
        }
        
        
	
	}
    
    /**
     * Creates the nuc data list panel.
     */
    public void createNucDataListPanel(){
    
    	JPanel nucDataListPanel = new JPanel();
    	
        nucDataListPanel.setLayout(new GridBagLayout());
		
		dataViewerNucDataListPanel = new DataViewerNucDataListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        nucDataListPanel.add(dataViewerNucDataListPanel, gbc);   
          
        sp = new JScrollPane(nucDataListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    /**
     * Sets the nuc data list panel.
     */
    public void setNucDataListPanel(){

    	dataViewerNucDataListPanel.initialize();
    
    }
    
    /**
     * Creates the format panel.
     */
    public void createFormatPanel(){  
               
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   
		
		JPanel buttonPanel = new JPanel();
        
        buttonPanel.setLayout(new GridLayout(1, 5, 5, 5));

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
        
		applyButton = new JButton("Apply Energy Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);

		infoButton = new JButton("Nuclear Data Info");
        infoButton.setFont(Fonts.buttonFont);
        infoButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(infoButton);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
		
		CSminModel = new SpinnerNumberModel(0, -30, 30, 1);
        CSmaxModel = new SpinnerNumberModel(0, -30, 30, 1);
        
        CSminSpinner = new JSpinner(CSminModel);
        CSminSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(CSminSpinner.getEditor())).getTextField().setEditable(false);
        
        CSmaxSpinner = new JSpinner(CSmaxModel);
        CSmaxSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(CSmaxSpinner.getEditor())).getTextField().setEditable(false);
        
        SFminModel = new SpinnerNumberModel(new Double(ds.getSFmin())
    										, null
    										, null
    										, new Double(10.0));
    										
        SFmaxModel = new SpinnerNumberModel(new Double(ds.getSFmax())
    										, null
    										, null
    										, new Double(10.0));
        
        SFminSpinner = new JSpinner(SFminModel);
        SFminSpinner.addChangeListener(this);

        SFmaxSpinner = new JSpinner(SFmaxModel);
        SFmaxSpinner.addChangeListener(this);
   
   		if(ds.getNumberTotalCSNucData()>0){
   
	        EminModel = new SpinnerNumberModel(new Double(ds.getEnergyCSmin())
	    										, null
	    										, null
	    										, new Double(100.0));
	    										
	        EmaxModel = new SpinnerNumberModel(new Double(ds.getEnergyCSmax())
	    										, null
	    										, null
	    										, new Double(100.0));
	    										
	   	}else{
	   
	   		EminModel = new SpinnerNumberModel(new Double(ds.getEnergySFmin())
	    										, null
	    										, null
	    										, new Double(100.0));
	    										
	        EmaxModel = new SpinnerNumberModel(new Double(ds.getEnergySFmax())
	    										, null
	    										, null
	    										, new Double(100.0));
	   	
	   	}
        
        EminSpinner = new JSpinner(EminModel);
        EminSpinner.addChangeListener(this);

        EmaxSpinner = new JSpinner(EmaxModel);
        EmaxSpinner.addChangeListener(this);
        
        //CREATE RADIOBUTTONS/////////////////////////////////////////////////RADIOBUTTONS/////////////////
        if(ds.getNumberTotalCSNucData()>0){
        
	        CSRadioButton = new JRadioButton("Cross Section", true);
	        CSRadioButton.addItemListener(this);
	        
	        SFRadioButton = new JRadioButton("S Factor", false);
	        SFRadioButton.addItemListener(this);
	        
	    }else{
	    	
	    	CSRadioButton = new JRadioButton("Cross Section", false);
	        CSRadioButton.addItemListener(this);
	        
	        SFRadioButton = new JRadioButton("S Factor", true);
	        SFRadioButton.addItemListener(this);
	    
	    }
	        
        buttonGroup = new ButtonGroup();
        
        buttonGroup.add(CSRadioButton);
        buttonGroup.add(SFRadioButton);
        
        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Energy Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        if(ds.getNumberTotalCSNucData()>0){
        
	        yminLabel = new JLabel("log Cross Sec Min");
	        yminLabel.setFont(Fonts.textFont);
        
    	}else{
    	
    		yminLabel = new JLabel("S Factor Min");
	        yminLabel.setFont(Fonts.textFont);
	        
    	}
        
        plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
        
        //CHECKBOXES///////////////////////////////////////////////////////CHECKBOXES////////////////////
        majorXCheckBox = new JCheckBox("Major Gridlines", true);
        minorXCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorYCheckBox = new JCheckBox("Major Gridlines", true);
        minorYCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorXCheckBox.addItemListener(this);
        minorXCheckBox.addItemListener(this);
        majorYCheckBox.addItemListener(this);
        minorYCheckBox.addItemListener(this);

		majorXCheckBox.setFont(Fonts.textFont);
        minorXCheckBox.setFont(Fonts.textFont);
        majorYCheckBox.setFont(Fonts.textFont);
        minorYCheckBox.setFont(Fonts.textFont);

  		///////////////////////////////////////////////PUT IT ALL TOGETHER//////////////////////////////////////////////////////////////////
  		
  		gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.gridwidth = 9;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(CSRadioButton, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(SFRadioButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(yminLabel, gbc);
  		
  		if(ds.getNumberTotalCSNucData()>0){
  		
	  		gbc.gridx = 2;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(CSminSpinner, gbc);
	  		
	  	}else{
	  	
	  		gbc.gridx = 2;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(SFminSpinner, gbc);
	  	
	  	}
  
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(ymaxLabel, gbc);
  		
  		if(ds.getNumberTotalCSNucData()>0){
  		
	  		gbc.gridx = 4;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(CSmaxSpinner, gbc);
	  		
	  	}else{
	  		
	  		gbc.gridx = 4;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(SFmaxSpinner, gbc);
	  		
	  	}
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorYCheckBox, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYCheckBox, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(EminSpinner, gbc);
  
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(EmaxSpinner, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorXCheckBox, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXCheckBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 9;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;

        c.add(controlPanel, BorderLayout.SOUTH);
             
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    
    	if(ds.getNumberTotalCSNucData()>0){
    
	    	dataViewerPlotPanel = new DataViewerPlotPanel(CSminSpinnerInit 
															, CSmaxSpinnerInit
															, ds);
															
		}else{
		
			dataViewerPlotPanel = new DataViewerPlotPanel(0, 0, ds);
		
		}
		
		dataViewerPlotPanel.setPreferredSize(dataViewerPlotPanel.getSize());
		
		dataViewerPlotPanel.revalidate();
		
		sp1 = new JScrollPane(dataViewerPlotPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(dataViewerPlotPanel);
		
		sp1.setBackground(Color.white);
		
		sp1.setViewport(vp);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(550);
       	
       	c.add(jsp, BorderLayout.CENTER);
		
		setVisible(true);
		
		dataViewerPlotPanel.setPreferredSize(dataViewerPlotPanel.getSize());
		
		dataViewerPlotPanel.revalidate();
    	
    }
    
    /**
     * Sets the initial c smin spinner init.
     *
     * @return the int
     */
    public int setInitialCSminSpinnerInit(){
	
		int min = 0;

		min = ds.getCSmin();
		
		if(min < -30){
			
			min = -30;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initial c smax spinner init.
	 *
	 * @return the int
	 */
	public int setInitialCSmaxSpinnerInit(){
	
		int max = 0;
		
		max = ds.getCSmax();
		
		if(max > 30){
			
			max = 30;
		
		}
		
		return max;
	
	}
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	CSminModel.setMaximum(new Integer(CSmaxModel.getNumber().intValue()-1));
    	CSmaxModel.setMinimum(new Integer(CSminModel.getNumber().intValue()+1));
    
    	SFminModel.setMaximum(new Double(SFmaxModel.getNumber().doubleValue()-1));
    	SFmaxModel.setMinimum(new Double(SFminModel.getNumber().doubleValue()+1));
    	
    	EminModel.setMaximum(new Double(EmaxModel.getNumber().doubleValue()-1));
    	EmaxModel.setMinimum(new Double(EminModel.getNumber().doubleValue()+1));
    
    	redrawPlot();
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
        
      	if(ie.getSource()==majorXCheckBox){
      		
            if(majorXCheckBox.isSelected()){
            	
                minorXCheckBox.setEnabled(true);
                
            }else{
            	
                minorXCheckBox.setSelected(false);
                minorXCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorYCheckBox){
        	
            if(majorYCheckBox.isSelected()){
            	
                minorYCheckBox.setEnabled(true);
                
            }else{

                minorYCheckBox.setSelected(false);
                minorYCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorXCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorYCheckBox){
        	
            redrawPlot();
               
        }else if(ie.getSource()==CSRadioButton || ie.getSource()==SFRadioButton){
        
        	if(CSRadioButton.isSelected()){
        
        		yminLabel.setText("log Cross Sec Min");
        		ymaxLabel.setText("Max");

        		controlPanel.remove(SFminSpinner);
        		controlPanel.remove(SFmaxSpinner);
        		
        		gbc.gridx = 2;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.WEST;
		  		gbc.fill = GridBagConstraints.HORIZONTAL;
		  		controlPanel.add(CSminSpinner, gbc);

		  		gbc.gridx = 4;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.WEST;
		  		gbc.fill = GridBagConstraints.HORIZONTAL;
		  		controlPanel.add(CSmaxSpinner, gbc);
		  		
        		EminSpinner.removeChangeListener(this);
        		EmaxSpinner.removeChangeListener(this);
        		
        		EminSpinner.setValue(new Double(ds.getEnergyCSmin()));
		  		EmaxSpinner.setValue(new Double(ds.getEnergyCSmax()));
		  		
		  		EminSpinner.addChangeListener(this);
        		EmaxSpinner.addChangeListener(this);
        		
        		CSminSpinner.removeChangeListener(this);
        		CSmaxSpinner.removeChangeListener(this);
        		
        		CSminSpinner.setValue(new Integer(CSminSpinnerInit));
		  		CSmaxSpinner.setValue(new Integer(CSmaxSpinnerInit));
		  		
		  		CSminSpinner.addChangeListener(this);
        		CSmaxSpinner.addChangeListener(this);
		  		
		  		dataViewerNucDataListPanel.initialize();
        		
    		}else{

    			yminLabel.setText("S Factor Min");
        		ymaxLabel.setText("Max");

        		controlPanel.remove(CSminSpinner);
        		controlPanel.remove(CSmaxSpinner);
        		
        		gbc.gridx = 2;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.WEST;
		  		gbc.fill = GridBagConstraints.HORIZONTAL;
		  		controlPanel.add(SFminSpinner, gbc);

		  		gbc.gridx = 4;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.WEST;
		  		gbc.fill = GridBagConstraints.HORIZONTAL;
		  		controlPanel.add(SFmaxSpinner, gbc);
        		
		  		EminSpinner.removeChangeListener(this);
        		EmaxSpinner.removeChangeListener(this);
        		
        		EminSpinner.setValue(new Double(ds.getEnergySFmin()));
		  		EmaxSpinner.setValue(new Double(ds.getEnergySFmax()));
		  		
		  		EminSpinner.addChangeListener(this);
        		EmaxSpinner.addChangeListener(this);
        		
        		SFminSpinner.removeChangeListener(this);
        		SFmaxSpinner.removeChangeListener(this);
        		
        		SFminSpinner.setValue(new Double(ds.getSFmin()));
		  		SFmaxSpinner.setValue(new Double(ds.getSFmax()));
		  		
		  		SFminSpinner.addChangeListener(this);
        		SFmaxSpinner.addChangeListener(this);
		  		
		  		dataViewerNucDataListPanel.initialize();
		  		    		
    		}
    		
    		
    		
    		validate();
    		
        	redrawPlot();
        
        }

    }

	/**
	 * Gets the s fmin.
	 *
	 * @return the s fmin
	 */
	public double getSFmin(){return SFminModel.getNumber().doubleValue();} 
	
	/**
	 * Gets the s fmax.
	 *
	 * @return the s fmax
	 */
	public double getSFmax(){return SFmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the c smin.
	 *
	 * @return the c smin
	 */
	public double getCSmin(){return CSminModel.getNumber().doubleValue();} 
	
	/**
	 * Gets the c smax.
	 *
	 * @return the c smax
	 */
	public double getCSmax(){return CSmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the energymin.
	 *
	 * @return the energymin
	 */
	public double getEnergymin(){return EminModel.getNumber().doubleValue();} 
	
	/**
	 * Gets the energymax.
	 *
	 * @return the energymax
	 */
	public double getEnergymax(){return EmaxModel.getNumber().doubleValue();}

	/**
	 * Gets the minor x.
	 *
	 * @return the minor x
	 */
	public boolean getMinorX(){return minorXCheckBox.isSelected();} 
	
	/**
	 * Gets the major x.
	 *
	 * @return the major x
	 */
	public boolean getMajorX(){return majorXCheckBox.isSelected();} 
	
	/**
	 * Gets the minor y.
	 *
	 * @return the minor y
	 */
	public boolean getMinorY(){return minorYCheckBox.isSelected();} 
	
	/**
	 * Gets the major y.
	 *
	 * @return the major y
	 */
	public boolean getMajorY(){return majorYCheckBox.isSelected();} 
    
    /**
     * Redraw plot.
     */
    public void redrawPlot(){

		dataViewerPlotPanel.setPreferredSize(dataViewerPlotPanel.getSize());

		dataViewerPlotPanel.setPlotState();
   		
   		dataViewerPlotPanel.repaint();

    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	for(int i=0; i<Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray.length; i++){
    	
    		if(Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerNucDataListPanel.checkBoxArray[i].isSelected()){
    		
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
       	
	       		if(dataViewerTableFrame==null){
	       	
	       			dataViewerTableFrame = new DataViewerTableFrame(ds);
	       		
		       	}else{
		       	
		       		dataViewerTableFrame.setTableText();
		       		dataViewerTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{
	       	
	       		String string = "";
	       	
	       		if(SFRadioButton.isSelected()){
	       	
	       			string = "Please select S factor data for the Table of Points from the checkbox list."; 
	       		
	       		}else if(CSRadioButton.isSelected()){
	       		
	       			string = "Please select cross section data for the Table of Points from the checkbox list.";
	       		
	       		}
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
	 	}else if(ae.getSource()==infoButton){
	 		
	       	if(!noneChecked()){
       		
	       		if(dataViewerInfoFrame==null){
	       	
	       			dataViewerInfoFrame = new DataViewerInfoFrame(ds);
	       		
	       		}else{
	
					dataViewerInfoFrame.refreshData();
	       			dataViewerInfoFrame.setVisible(true);
	       		
	       		}
	       		
	       	}else{
	       	
	       		String string = "";
	       	
	       		if(SFRadioButton.isSelected()){
	       	
	       			string = "Please select S factor data for Nuclear Data Info from the checkbox list."; 
	       		
	       		}else if(CSRadioButton.isSelected()){
	       		
	       			string = "Please select cross section data for Nuclear Data Info from the checkbox list.";
	       		
	       		}
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       		
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new DataViewerPlotFramePrintable(), this);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(dataViewerPlotPanel, this);
       		
       	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we){
    	
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


class DataViewerPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.dataViewerFrame.dataViewerPlotFrame.dataViewerPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}