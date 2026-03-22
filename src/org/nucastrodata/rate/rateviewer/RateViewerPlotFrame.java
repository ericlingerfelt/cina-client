package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.print.*;
import javax.swing.event.*;

import org.nucastrodata.datastructure.feature.RateViewerDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateviewer.RateViewerPlotFramePrintable;


/**
 * The Class RateViewerPlotFrame.
 */
public class RateViewerPlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener, ChangeListener{
    
    /** The units. */
    String[] units = {"", " reactions/sec", " reactions/sec", " reactions/sec", " cm^3/(mole*s)"
                                , " cm^3/(mole*s)", " cm^3/(mole*s)", " cm^3/(mole*s)"
                                , " cm^6/(mole^2 * s)"};
    
    /** The toolkit. */
    Toolkit toolkit;
    
    /** The panel5. */
    JPanel panel4, panel5;
    
    /** The tmax spinner. */
    JSpinner rminSpinner, rmaxSpinner, tminSpinner, tmaxSpinner;
    
    /** The tmax model. */
    SpinnerNumberModel rminModel, rmaxModel, tminModel, tmaxModel;  
    
    /** The button group. */
    ButtonGroup buttonGroup = new ButtonGroup();
    
    /** The temp radio button array. */
    JRadioButton[] tempRadioButtonArray = new JRadioButton[2];
    
    /** The submit button. */
    JButton redrawButton, printButton, saveButton, infoButton, tempRateButton, rateButton, submitButton;
    
    /** The subtitle field. */
    JTextField titleField, subtitleField;
    
    /** The minor temp check box. */
    JCheckBox majorRateCheckBox, majorTempCheckBox, minorRateCheckBox, minorTempCheckBox;
    
    /** The rmax spinner init. */
    int tminSpinnerInit, tmaxSpinnerInit, rminSpinnerInit, rmaxSpinnerInit;
    
    /** The c. */
    Container c;
    
    /** The sp1. */
    JScrollPane sp, sp1;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The rate viewer reaction list panel. */
    public RateViewerReactionListPanel rateViewerReactionListPanel;
    
    /** The rate viewer plot panel. */
    RateViewerPlotPanel rateViewerPlotPanel;
	
	/** The rate viewer info frame. */
	RateViewerInfoFrame rateViewerInfoFrame;
    
    /** The rate viewer table frame. */
    RateViewerTableFrame rateViewerTableFrame;
    
    /** The rate viewer rate frame. */
    RateViewerRateFrame rateViewerRateFrame;
    
    /** The legend box. */
    JCheckBox legendBox;
    
    /** The button panel. */
    private JPanel controlPanel, buttonPanel;
    
    /** The rate grid label. */
    private JLabel plotControlsLabel, tempLabel, rminLabel, rmaxLabel
    					, TminLabel, TmaxLabel, tempGridLabel, rateGridLabel;
    
    /** The compared rate vector. */
    Vector comparedRateVector;
    
    /** The found home vector. */
    Vector foundHomeVector;
    
    /** The ds. */
    private RateViewerDataStructure ds;
    
    /** The param. */
    public boolean param;
    
    /** The starting parameters. */
    double[] startingParameters;
    
	/**
	 * Instantiates a new rate viewer plot frame.
	 *
	 * @param ds the ds
	 * @param param the param
	 */
    public RateViewerPlotFrame(RateViewerDataStructure ds, boolean param){
		
		this.param = param;
		this.ds = ds;
		
		rminSpinnerInit = setInitialrminSpinnerInit();
		rmaxSpinnerInit = setInitialrmaxSpinnerInit();
		tminSpinnerInit = setInitialtminSpinnerInit();
		tmaxSpinnerInit = setInitialtmaxSpinnerInit();
	
		c = getContentPane();
		setSize(799, 657);
		setVisible(true);
		addWindowListener(this);
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		createFormatPanel();
		setFormatPanelState();
		createReactionListPanel();
		setReactionListPanel();
		createResultsPlot();
		
		
		validate();
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
	
		setTitle("Reaction Rate Plotting Interface");
		
		rminSpinnerInit = setInitialrminSpinnerInit();
		rmaxSpinnerInit = setInitialrmaxSpinnerInit();
		tminSpinnerInit = setInitialtminSpinnerInit();
		tmaxSpinnerInit = setInitialtmaxSpinnerInit();
		
		rminSpinner.removeChangeListener(this);
		rmaxSpinner.removeChangeListener(this);
		tminSpinner.removeChangeListener(this);
		tmaxSpinner.removeChangeListener(this);
		
		rminModel.setValue(new Integer(rminSpinnerInit));
        rmaxModel.setValue(new Integer(rmaxSpinnerInit));
        tminModel.setValue(new Integer(tminSpinnerInit));
        tmaxModel.setValue(new Integer(tmaxSpinnerInit));
        
        rminSpinner.addChangeListener(this);
		rmaxSpinner.addChangeListener(this);
		tminSpinner.addChangeListener(this);
		tmaxSpinner.addChangeListener(this);
        
        controlPanel.removeAll();
        
        if(param){
		
			gbc.insets = new Insets(3, 3, 3, 3);
	  		gbc.gridx = 0;
	  		gbc.gridy = 0;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.gridwidth = 9;
	  		controlPanel.add(submitButton, gbc);
	  		gbc.gridx = 0;
	  		gbc.gridy = 1;
	  		gbc.gridwidth = 8;
	  		controlPanel.add(plotControlsLabel, gbc);		
	  		gbc.gridx = 8;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.gridwidth = 1;
	  		controlPanel.add(legendBox, gbc);			
	  		gbc.gridx = 0;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.gridwidth = 2;
	  		controlPanel.add(tempLabel, gbc);
	  		gbc.gridx = 0;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.gridwidth = 1;
	  		controlPanel.add(tempRadioButtonArray[0], gbc);
	  		gbc.gridx = 1;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(tempRadioButtonArray[1], gbc);
	  		gbc.gridx = 6;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(tempGridLabel, gbc);
	  		gbc.gridx = 7;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(majorTempCheckBox, gbc);
	  		gbc.gridx = 8;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(minorTempCheckBox, gbc);
	  		gbc.gridx = 2;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(rminLabel, gbc);
	  		gbc.gridx = 3;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(rminSpinner, gbc);
	  		gbc.gridx = 4;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(rmaxLabel, gbc);
	  		gbc.gridx = 5;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(rmaxSpinner, gbc);
	  		gbc.gridx = 6;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(rateGridLabel, gbc);
	  		gbc.gridx = 7;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(majorRateCheckBox, gbc);
	  		gbc.gridx = 8;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(minorRateCheckBox, gbc);
	  		gbc.gridx = 2;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(TminLabel, gbc);
	  		gbc.gridx = 3;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(tminSpinner, gbc);
	  		gbc.gridx = 4;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(TmaxLabel, gbc);
	  		gbc.gridx = 5;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(tmaxSpinner, gbc);
	  		gbc.gridx = 0;
	  		gbc.gridy = 4;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.fill = GridBagConstraints.NONE;
	  		gbc.gridwidth = 9;
	  		controlPanel.add(buttonPanel, gbc);
		
		}else{

	  		gbc.insets = new Insets(3, 3, 3, 3);
	  		gbc.gridx = 0;
	  		gbc.gridy = 0;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.gridwidth = 8;
	  		controlPanel.add(plotControlsLabel, gbc);		
	  		gbc.gridx = 8;
	  		gbc.gridy = 0;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.gridwidth = 1;
	  		controlPanel.add(legendBox, gbc);	
	  		gbc.gridx = 0;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.gridwidth = 2;
	  		controlPanel.add(tempLabel, gbc);
	  		gbc.gridx = 0;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.gridwidth = 1;
	  		controlPanel.add(tempRadioButtonArray[0], gbc);
	  		gbc.gridx = 1;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(tempRadioButtonArray[1], gbc);
	  		gbc.gridx = 6;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(tempGridLabel, gbc);
	  		gbc.gridx = 7;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(majorTempCheckBox, gbc);
	  		gbc.gridx = 8;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(minorTempCheckBox, gbc);
	  		gbc.gridx = 2;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(rminLabel, gbc);
	  		gbc.gridx = 3;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(rminSpinner, gbc);
	  		gbc.gridx = 4;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(rmaxLabel, gbc);
	  		gbc.gridx = 5;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(rmaxSpinner, gbc);
	  		gbc.gridx = 6;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(rateGridLabel, gbc);
	  		gbc.gridx = 7;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(majorRateCheckBox, gbc);
	  		gbc.gridx = 8;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(minorRateCheckBox, gbc);
	  		gbc.gridx = 2;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		controlPanel.add(TminLabel, gbc);
	  		gbc.gridx = 3;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(tminSpinner, gbc);
	  		gbc.gridx = 4;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.WEST;
	  		gbc.fill = GridBagConstraints.NONE;
	  		controlPanel.add(TmaxLabel, gbc);
	  		gbc.gridx = 5;
	  		gbc.gridy = 2;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		gbc.fill = GridBagConstraints.HORIZONTAL;
	  		controlPanel.add(tmaxSpinner, gbc);
	  		gbc.gridx = 0;
	  		gbc.gridy = 3;
	  		gbc.anchor = GridBagConstraints.CENTER;
	  		gbc.fill = GridBagConstraints.NONE;
	  		gbc.gridwidth = 9;
	  		controlPanel.add(buttonPanel, gbc);

		}
        
        c.validate();
        
        
	}
    
    /**
     * Creates the reaction list panel.
     */
    public void createReactionListPanel(){
    
    	JPanel reactionListPanel = new JPanel();
        reactionListPanel.setLayout(new GridBagLayout());
		rateViewerReactionListPanel = new RateViewerReactionListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        reactionListPanel.add(rateViewerReactionListPanel, gbc);   
          
        sp = new JScrollPane(reactionListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    }
    
    /**
     * Sets the reaction list panel.
     */
    public void setReactionListPanel(){rateViewerReactionListPanel.initialize();}
    
    /**
     * Creates the format panel.
     */
    public void createFormatPanel(){    
		
    	legendBox = new JCheckBox("Show Legend?", true);
    	legendBox.setFont(Fonts.textFont);
    	legendBox.addActionListener(this);
    	
		buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

		submitButton = new JButton("Submit Selected Starting Parameters");
        submitButton.addActionListener(this);
        submitButton.setFont(Fonts.buttonFont);

        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        infoButton = new JButton("Rate Info");
        infoButton.addActionListener(this);
        infoButton.setFont(Fonts.buttonFont);
        infoButton.setEnabled(true);

        tempRateButton = new JButton("Table of Points");
        tempRateButton.setFont(Fonts.buttonFont);
        tempRateButton.addActionListener(this);
        tempRateButton.setEnabled(true);

		rateButton = new JButton("Upload or Paste Rate");
        rateButton.setFont(Fonts.buttonFont);
        rateButton.addActionListener(this);
        rateButton.setEnabled(true);

		gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.insets = new Insets(5,5,5,5); 

        buttonPanel.add(printButton, gbc);
        
        gbc.gridx = 1;                      
        gbc.gridy = 0;
        
        buttonPanel.add(saveButton, gbc);
        
        gbc.gridx = 2;                      
        gbc.gridy = 0;
        
		buttonPanel.add(infoButton, gbc);
		
		gbc.gridx = 3;                      
        gbc.gridy = 0;
        
        buttonPanel.add(tempRateButton, gbc);

		gbc.gridx = 4;                      
        gbc.gridy = 0;
        
        buttonPanel.add(rateButton, gbc);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        
        tempLabel = new JLabel("Temperature");
        tempLabel.setFont(Fonts.textFont);
        rminLabel = new JLabel("log Rate min");
        rminLabel.setFont(Fonts.textFont);
        rmaxLabel = new JLabel("log Rate max");
        rmaxLabel.setFont(Fonts.textFont);
        TminLabel = new JLabel("log Temp min");
        TminLabel.setFont(Fonts.textFont);
        TmaxLabel = new JLabel("log Temp max");
        TmaxLabel.setFont(Fonts.textFont);

		plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");

        tempRadioButtonArray[0] = new JRadioButton("log");
        tempRadioButtonArray[0].setFont(Fonts.textFont);
        tempRadioButtonArray[1] = new JRadioButton("lin");
		tempRadioButtonArray[1].setFont(Fonts.textFont);	
			
		buttonGroup.add(tempRadioButtonArray[0]);
		buttonGroup.add(tempRadioButtonArray[1]);

        tempRadioButtonArray[0].setSelected(true);
        tempRadioButtonArray[0].addItemListener(this);
        tempRadioButtonArray[1].addItemListener(this);
        
        rminModel = new SpinnerNumberModel(0, -30, 30, 1);
        rmaxModel = new SpinnerNumberModel(0, -30, 30, 1);
        tminModel = new SpinnerNumberModel(0, -2, 1, 1);
        tmaxModel = new SpinnerNumberModel(0, -2, 1, 1);
        
        rminSpinner = new JSpinner(rminModel);
        rminSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(rminSpinner.getEditor())).getTextField().setEditable(false);
        rmaxSpinner = new JSpinner(rmaxModel);
        rmaxSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(rmaxSpinner.getEditor())).getTextField().setEditable(false);
        tminSpinner = new JSpinner(tminModel);
        tminSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(tminSpinner.getEditor())).getTextField().setEditable(false);
        tmaxSpinner = new JSpinner(tmaxModel);
        tmaxSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(tmaxSpinner.getEditor())).getTextField().setEditable(false);
        
        tempGridLabel = new JLabel("Temperature");
        tempGridLabel.setFont(Fonts.textFont);
        rateGridLabel = new JLabel("Rate");
        rateGridLabel.setFont(Fonts.textFont);
        
        majorTempCheckBox = new JCheckBox("Major Gridlines", true);
        minorTempCheckBox = new JCheckBox("Minor Gridlines", true);
        majorRateCheckBox = new JCheckBox("Major Gridlines", true);
        minorRateCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorTempCheckBox.addItemListener(this);
        minorTempCheckBox.addItemListener(this);
        majorRateCheckBox.addItemListener(this);
        minorRateCheckBox.addItemListener(this);

		majorTempCheckBox.setFont(Fonts.textFont);
        minorTempCheckBox.setFont(Fonts.textFont);
        majorRateCheckBox.setFont(Fonts.textFont);
        minorRateCheckBox.setFont(Fonts.textFont);

        c.add(controlPanel, BorderLayout.SOUTH);      
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
		rateViewerPlotPanel = new RateViewerPlotPanel(rminSpinnerInit 
													, rmaxSpinnerInit
													, tminSpinnerInit
													, tmaxSpinnerInit
													, ds);
		rateViewerPlotPanel.setPreferredSize(rateViewerPlotPanel.getSize());
		rateViewerPlotPanel.revalidate();
		
		sp1 = new JScrollPane(rateViewerPlotPanel);
		JViewport vp = new JViewport();
		vp.setView(rateViewerPlotPanel);
		sp1.setBackground(Color.white);
		sp1.setViewport(vp);
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	jsp.setDividerLocation(500);
       	c.add(jsp, BorderLayout.CENTER);
		setVisible(true);
		
		rateViewerPlotPanel.setPreferredSize(rateViewerPlotPanel.getSize());
		rateViewerPlotPanel.revalidate();
    }
    
    /**
     * Sets the initialrmin spinner init.
     *
     * @return the int
     */
    public int setInitialrminSpinnerInit(){
		int min = 0;
		min = ds.getRatemin();
		if(min<-30){min = -30;}
		return min;
	}
	
	/**
	 * Sets the initialrmax spinner init.
	 *
	 * @return the int
	 */
	public int setInitialrmaxSpinnerInit(){
		int max = 0;
		max = ds.getRatemax();
		if(max > 30){max = 30;}
		return max;
	}
	
	/**
	 * Sets the initialtmin spinner init.
	 *
	 * @return the int
	 */
	public int setInitialtminSpinnerInit(){return -2;}
	
	/**
	 * Sets the initialtmax spinner init.
	 *
	 * @return the int
	 */
	public int setInitialtmaxSpinnerInit(){return 1;}
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    	rminModel.setMaximum(new Integer(rmaxModel.getNumber().intValue()-1));
    	rmaxModel.setMinimum(new Integer(rminModel.getNumber().intValue()+1));
    	tminModel.setMaximum(new Integer(tmaxModel.getNumber().intValue()-1));
    	tmaxModel.setMinimum(new Integer(tminModel.getNumber().intValue()+1));
    
    	redrawPlot();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
      	if(ie.getSource()==majorTempCheckBox){
            if(majorTempCheckBox.isSelected()){
            	minorTempCheckBox.setEnabled(true); 
            }else{
                minorTempCheckBox.setSelected(false);
                minorTempCheckBox.setEnabled(false);
            }
            redrawPlot();
        }else if(ie.getSource()==majorRateCheckBox){
            if(majorRateCheckBox.isSelected()){
            	minorRateCheckBox.setEnabled(true); 
            }else{
                minorRateCheckBox.setSelected(false);
                minorRateCheckBox.setEnabled(false);   
            }
            redrawPlot(); 
        }else if(ie.getSource()==minorTempCheckBox){
        	redrawPlot();
        }else if(ie.getSource()==minorRateCheckBox){
        	redrawPlot();   
        }else if(ie.getSource()==tempRadioButtonArray[0] || ie.getSource()==tempRadioButtonArray[1]){
        	redrawPlot();
        }
    }
    
    /**
     * Gets the plot mode.
     *
     * @return the plot mode
     */
    public int getPlotMode(){
    	int plotMode = 2;
    	if(tempRadioButtonArray[0].isSelected()){
    		plotMode = 2;
    	}else if(tempRadioButtonArray[1].isSelected()){
    		plotMode = 1;
    	}
    	return plotMode;
    }
	
	/**
	 * Gets the tmin.
	 *
	 * @return the tmin
	 */
	public double getTmin(){return tminModel.getNumber().doubleValue();} 
	
	/**
	 * Gets the tmax.
	 *
	 * @return the tmax
	 */
	public double getTmax(){return tmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the rmin.
	 *
	 * @return the rmin
	 */
	public double getRmin(){return rminModel.getNumber().doubleValue();}
	
	/**
	 * Gets the rmax.
	 *
	 * @return the rmax
	 */
	public double getRmax(){return rmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the minor t.
	 *
	 * @return the minor t
	 */
	public boolean getMinorT(){return minorTempCheckBox.isSelected();} 
	
	/**
	 * Gets the major t.
	 *
	 * @return the major t
	 */
	public boolean getMajorT(){return majorTempCheckBox.isSelected();} 
	
	/**
	 * Gets the minor r.
	 *
	 * @return the minor r
	 */
	public boolean getMinorR(){return minorRateCheckBox.isSelected();} 
	
	/**
	 * Gets the major r.
	 *
	 * @return the major r
	 */
	public boolean getMajorR(){return majorRateCheckBox.isSelected();} 
    
    /**
     * Redraw plot.
     */
    public void redrawPlot(){
    	rateViewerPlotPanel.setPreferredSize(rateViewerPlotPanel.getSize());
		rateViewerPlotPanel.setPlotState();
   		rateViewerPlotPanel.repaint();
    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	if(!ds.getRateAdded()){
    	
	    	for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length; i++){
	    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){noneChecked = false;}
	    	}
    	
    	}else{
    	
    		for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length-1; i++){
	    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){noneChecked = false;}
	    	}
    		
    	}
    	
    	return noneChecked;
    }
    
    /**
     * None checked table.
     *
     * @return true, if successful
     */
    public boolean noneCheckedTable(){
    
    	boolean noneChecked = true;
    	
    	for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length; i++){
    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){noneChecked = false;}
    	}
    	
    	return noneChecked;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
       if(ae.getSource()==rateButton){
    
    		if(rateViewerRateFrame==null){
    			rateViewerRateFrame = new RateViewerRateFrame(ds);
    			rateViewerRateFrame.setCurrentState();
    			rateViewerRateFrame.setVisible(true);
    		}else{
    			rateViewerRateFrame.setCurrentState();
    			rateViewerRateFrame.setVisible(true);
    		}
    	
       }else if(ae.getSource()==infoButton){
       		if(!noneChecked()){
	       		if(rateViewerInfoFrame==null){
	       			rateViewerInfoFrame = new RateViewerInfoFrame(ds);
	       		}else{
					rateViewerInfoFrame.refreshData();
	       			rateViewerInfoFrame.setVisible(true);
	       		}
	       	}else{
	       		String string = "Please select (non-added) reaction rates for Rate Info from the checkbox list."; 
	       		Dialogs.createExceptionDialog(string, this);
	       	}
       	
       	}else if(ae.getSource()==tempRateButton){
       		if(!noneCheckedTable()){
	       		if(rateViewerTableFrame==null){
	       			rateViewerTableFrame = new RateViewerTableFrame(ds);
		       	}else{
		       		rateViewerTableFrame.setTableText();
		       		rateViewerTableFrame.setVisible(true);
		       	}
		 	}else{
	       		String string = "Please select reaction rates for the Table of Points from the checkbox list."; 
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
       	}else if(ae.getSource()==printButton){
       		PlotPrinter.print(new RateViewerPlotFramePrintable(), this);
       	}else if(ae.getSource()==saveButton){
       		PlotSaver.savePlot(rateViewerPlotPanel, this);
    	}else if(ae.getSource()==submitButton){
    	
    		if(!noneChecked()){
    	
	    		if(uniqRateChecked()){
	    	
		    		if(Cina.rateParamFrame!=null){
		    		
		    			if(Cina.rateParamFrame.rateParamStartFrame!=null){
		    				
		    				Cina.rateParamFrame.rateParamStartFrame.setParameters(startingParameters);
		    				Cina.rateParamDataStructure.setStartFromRate(true);
		    				Cina.rateParamDataStructure.setStartingParametersLib(startingParameters);
		    				Cina.rateParamDataStructure.setStartLib(getLibName());
							Cina.rateParamDataStructure.setStartRate(getRateName());
							Cina.rateParamFrame.rateParamOptionsStartPanel.setCurrentState();

		    			}
		    			
		    		}
	    		
	    		}else{
	    		
	    			String string = "Please select only one total rate or at least one rate component from the same rate for parameter submission.";
    				Dialogs.createExceptionDialog(string, this);
	    		
	    		}
    		
    		}else{
    		
    			String string = "Please select only one total rate or at least one rate component from the same rate for parameter submission.";
    			Dialogs.createExceptionDialog(string, this);
    		
    		}
    	
    	}else if(ae.getSource()==legendBox){
    		redrawPlot();
    	}
    		
    }
    
    /**
     * Gets the lib name.
     *
     * @return the lib name
     */
    public String getLibName(){
    
    	String string = "";
    
    	for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length; i++){
    
    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){
    		
    			string = rateViewerReactionListPanel.checkBoxArray[i].getText();
    			
    			if(string.indexOf("nr;")==-1 && string.indexOf("r;")==-1){
    			
    				string = string.substring(string.indexOf("(")+1, string.indexOf(")"));
    			
    			}else{
    			
    				string = string.substring(string.indexOf(";")+2, string.indexOf(")"));
    			
    			}
    		
    		}
    	
    	}
    
    	return string;
    
    }
    
    /**
     * Gets the rate name.
     *
     * @return the rate name
     */
    public String getRateName(){
    
    	String string = "";
    
    	for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length; i++){
    
    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){
    		
    			string = rateViewerReactionListPanel.checkBoxArray[i].getText();
    			string = string.substring(0, string.indexOf("(")-1);
    		
    		}
    	
    	}
    	
    	return string;
    
    }
    
    /**
     * Gets the rate.
     *
     * @param rateVector the rate vector
     * @param indexVector the index vector
     * @return the rate
     */
    public double[] getRate(Vector rateVector, Vector indexVector){
    
    	int totalRateIndex = -1;
    	
    	Vector paramVector = new Vector();
    	
    	totalRateFound:
    	for(int i=0; i<rateVector.size(); i++){
    	
    		String rate = rateVector.elementAt(i).toString();
    	
    		if(rate.indexOf("nr;")==-1 && rate.indexOf("r;")==-1){
    		
    			paramVector.removeAllElements();
    		
    			paramVector.addElement(getTotalParameters(((Integer)indexVector.elementAt(i)).intValue()));
    		
    			break totalRateFound;
    		
    		}else{
    			
    			totalRateIndex = 0;
    			
    			paramVector.addElement(getCompParameters(((Integer)indexVector.elementAt(i)).intValue()));
    		
    		}
    		
    	}
    	
    	paramVector.trimToSize();
    	
    	double[] parameters;
    	
    	if(totalRateIndex!=-1){

	    	parameters = new double[paramVector.size()*7];
	    	
	    	int counter = 0;
	    	
	    	for(int i=0; i<paramVector.size(); i++){

	    		for(int j=0; j<((Double[])paramVector.elementAt(i)).length; j++){
	    		
	    			parameters[counter] = ((Double[])paramVector.elementAt(i))[j].doubleValue();
	    			
	    			counter++;
	    			
	    		}
	    		
	    	}
	    	
	    	return parameters;
    	
    	}else{
    	
    		parameters = new double[((Double[])paramVector.elementAt(0)).length];
    		
    		for(int i=0; i<((Double[])paramVector.elementAt(0)).length; i++){
    		
    			parameters[i] = ((Double[])paramVector.elementAt(0))[i].doubleValue();

    		}
    		
    		return parameters;
    	
    	}
    
    }
    
    /**
     * Gets the total parameters.
     *
     * @param searchIndex the search index
     * @return the total parameters
     */
    public Double[] getTotalParameters(int searchIndex){
    
    	int index = 0;
		
		double[] parameters = new double[0];
		
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
			if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
				for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
					
					if(index==searchIndex){
						parameters = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getParameters();
					}
					
					index++;
				
					if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
						for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
							index++;
						}
					}
				}
			}
		}
		
		Double[] parametersDouble = new Double[parameters.length];
		
		for(int i=0; i<parameters.length; i++){
		
			parametersDouble[i] = new Double(parameters[i]);	
		
		}
    
    	return parametersDouble;
    
    }
    
    /**
     * Gets the comp parameters.
     *
     * @param searchIndex the search index
     * @return the comp parameters
     */
    public Double[] getCompParameters(int searchIndex){
    
    	int index = 0;
		
		double[] parameters = new double[0];

		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
			if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
				for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
					
					index++;
				
					if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
						for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
							
							if(index==searchIndex){
							 	parameters = ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getParameterCompArray()[k];	
							}
							
							index++;
						}
					}
				}
			}
		}
		
		Double[] parametersDouble = new Double[parameters.length];
		
		for(int i=0; i<parameters.length; i++){
		
			parametersDouble[i] = new Double(parameters[i]);

		}
    
    	return parametersDouble;
    
    }
    
    /**
     * Uniq rate checked.
     *
     * @return true, if successful
     */
    public boolean uniqRateChecked(){
    	
    	boolean uniqRateChecked = true;
    	
    	Vector rateVector = new Vector();
    	Vector indexVector = new Vector();

    	if(!ds.getRateAdded()){
    	
	    	for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length; i++){
	    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){
	    			rateVector.addElement(rateViewerReactionListPanel.checkBoxArray[i].getText());
	    			indexVector.addElement(new Integer(i));
	    		}
	    	}
    	
    	}else{
    	
    		for(int i=0; i<rateViewerReactionListPanel.checkBoxArray.length-1; i++){
	    		if(rateViewerReactionListPanel.checkBoxArray[i].isSelected()){
	    			rateVector.addElement(rateViewerReactionListPanel.checkBoxArray[i].getText());
	    			indexVector.addElement(new Integer(i));
	    		}
	    	}
    		
    	}
    	
    	if(sameRate(rateVector)){
    	
    		if(sameLib(rateVector)){
    		
    			startingParameters = getRate(rateVector, indexVector);
    		
    		}else{
    		
    			uniqRateChecked = false;
    		
    		}
    	
    	}else{
    		
    		uniqRateChecked = false;
    	
    	}
    	
    	
    	return uniqRateChecked;
    
    }
    
    /**
     * Same rate.
     *
     * @param vector the vector
     * @return true, if successful
     */
    public boolean sameRate(Vector vector){
    
    	boolean sameRate = true;
   
    	String currentRate = vector.elementAt(0).toString().substring(0, vector.elementAt(0).toString().indexOf("(") - 1);
    
    	Iterator itr = vector.iterator();
    
    	notSameRate:
    	while(itr.hasNext()){
    		String string = itr.next().toString();
    		if(!string.substring(0, string.indexOf("(")-1).equals(currentRate)){
    			sameRate = false;
    			break notSameRate;
    		}
    	}
    
    	return sameRate;
    
    }
    
    /**
     * Same lib.
     *
     * @param vector the vector
     * @return true, if successful
     */
    public boolean sameLib(Vector vector){
    
    	boolean sameLib = true;
    
    	String currentLib = "";
    
    	if(vector.elementAt(0).toString().indexOf("nr;")==-1 && vector.elementAt(0).toString().indexOf("r;")==-1){
    		
    		currentLib = vector.elementAt(0).toString().substring(vector.elementAt(0).toString().indexOf("(")+1, vector.elementAt(0).toString().indexOf(")"));
    		
    	}else{
    	
    		currentLib = vector.elementAt(0).toString().substring(vector.elementAt(0).toString().indexOf(";")+2, vector.elementAt(0).toString().indexOf(")"));
    		
    	}

    	Iterator itr = vector.iterator();
    
    	notSameLib:
    	while(itr.hasNext()){
    		String string = itr.next().toString();
    		
    		if(string.indexOf("nr;")==-1 && string.indexOf("r;")==-1){
    		
	    		string = string.substring(string.indexOf("(")+1, string.indexOf(")"));
	    		
	    	}else{
	    	
	    		string = string.substring(string.indexOf(";")+2, string.indexOf(")"));
	    		
	    	}

    		if(!string.equals(currentLib)){
    			sameLib = false;
    			break notSameLib;
    		}
    	}
    
    	return sameLib;
    
    }
    
    /**
     * Gets the checked rate id array.
     *
     * @return the checked rate id array
     */
    public String[] getCheckedRateIDArray(){
    
    	Vector tempVector = new Vector();
    
    	int numberRates = 0;
		
		for(int i=0; i<ds.getNumberLibraryStructures(); i++){
			
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				
    				if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()){
    					
    					tempVector.addElement(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionID());
    					
    				}
    				
    				numberRates++;
    		
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
    					
	    				for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
	
							if(Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerReactionListPanel.checkBoxArray[numberRates].isSelected()){
    					
		    					tempVector.addElement(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionID());
		    					
		    				}
	
							numberRates++;
							
						}
						
					}

    			}
    			
    		}
    		
    	}
    	
    	tempVector.trimToSize();
    	
    	String[] idArray = new String[tempVector.size()];
    	
    	for(int i=0; i<idArray.length; i++){
    	
    		idArray[i] = tempVector.elementAt(i).toString();
    	
    	}
    	
    	return idArray;
    
	}
    
    /**
     * Gets the rate id list.
     *
     * @param rateIDArray the rate id array
     * @return the rate id list
     */
    public String getRateIDList(String[] rateIDArray){
	
		String string = "";
	
		Vector partVector = new Vector();

		for(int i=0; i<rateIDArray.length; i++){
		
			String currentRateID = rateIDArray[i];
		
			String currentFirstPart = currentRateID.substring(0,8);
			String currentSecondPart = currentRateID.substring(currentRateID.indexOf("\u0009"));
			
			String currentPartString = currentFirstPart + currentSecondPart;
			
			partVector.trimToSize();
			
			if(!partVector.contains(currentPartString)){
			
				partVector.addElement(currentPartString);
			
			}
			
		}

		partVector.trimToSize();

		for(int i=0; i<partVector.size(); i++){

			String firstPart = partVector.elementAt(i).toString().substring(0, partVector.elementAt(i).toString().indexOf("\u0009"));
	
			String secondPart = partVector.elementAt(i).toString().substring(partVector.elementAt(i).toString().indexOf("\u0009"));
	
			String[] libraryNameArray = new String[ds.getNumberPublicLibraryDataStructures()
													+ ds.getNumberSharedLibraryDataStructures()
													+ ds.getNumberUserLibraryDataStructures()];

			int counter = 0;
			
			for(int j=0; j<ds.getNumberPublicLibraryDataStructures(); j++){

				libraryNameArray[counter] = ds.getPublicLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}	
			
			for(int j=0; j<ds.getNumberSharedLibraryDataStructures(); j++){
			
				libraryNameArray[counter] = ds.getSharedLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}									
			
			for(int j=0; j<ds.getNumberUserLibraryDataStructures(); j++){
			
				libraryNameArray[counter] = ds.getUserLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}
			
			for(int j=0; j<libraryNameArray.length; j++){
				
				if(j==0 && i==0){
				
					string += firstPart + libraryNameArray[j] + secondPart;
				
				}else{
				
					string += "\n" + firstPart + libraryNameArray[j] + secondPart;
				
				}
				
			}
		
		}

		return string;
		
	}
	
	/**
	 * Gets the rate vector array.
	 *
	 * @param tempArray the temp array
	 * @param apprds the apprds
	 * @param vectorArrayIndex the vector array index
	 * @return the rate vector array
	 */
	public Vector[] getRateVectorArray(Vector[] tempArray, RateDataStructure apprds, int vectorArrayIndex){
	
		tempArray[0].addElement(apprds);
		
		foundHomeVector = new Vector();
		foundHomeVector.trimToSize();
		foundHomeVector.addElement(apprds.getReactionID());
		
		for(int i=0; i<ds.getRateDataStructureVectorArray()[vectorArrayIndex].size(); i++){

			RateDataStructure apprdsTemp = ((RateDataStructure)(ds.getRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i)));
	
			boolean alreadyThere = false;
			
			for(int j=0; j<tempArray.length; j++){

				tempArray[j].trimToSize();

				int size = tempArray[j].size();
			
				if(foundHomeVector.contains(apprdsTemp.getReactionID())){
				
					alreadyThere = true;
					
				}
			
			}
			
			if(!alreadyThere){
			
				tempArray = findRateHome(apprdsTemp, tempArray);
			
			}
		
		}
	
		int blankCounter = 0;
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i].trimToSize();
		
			if(tempArray[i].size()!=0){
			
				blankCounter++;
			
			}
		
		}
	
		Vector[] tempArrayNew = new Vector[blankCounter];
	
		for(int i=0; i<tempArrayNew.length; i++){
		
			tempArrayNew[i] = tempArray[i];
		
		}
	
		return tempArrayNew;
	
	}

	/**
	 * Find rate home.
	 *
	 * @param apprds the apprds
	 * @param tempArray the temp array
	 * @return the vector[]
	 */
	public Vector[] findRateHome(RateDataStructure apprds, Vector[] tempArray){
	
		boolean foundHome = false;

		for(int i=0; i<tempArray.length; i++){
			
			tempArray[i].trimToSize();
		
			int size = tempArray[i].size();
		
			for(int j=0; j<size; j++){
			
				if(((RateDataStructure)tempArray[i].elementAt(j)).getNumberParameters()
					== apprds.getNumberParameters()){
				
					boolean sameRate = true;
				
					for(int k=0; k<apprds.getNumberParameters(); k++){
					
						if(((RateDataStructure)tempArray[i].elementAt(j)).getParameters()[k]!=apprds.getParameters()[k]){
						
							sameRate = false;
						
						}
					
					}
					
					if(sameRate && !foundHomeVector.contains(apprds.getReactionID())){
	
						foundHomeVector.addElement(apprds.getReactionID());
	
						tempArray[i].addElement(apprds);
						
						foundHome = true;
					
					}
				
				}
			
			}
		
		}
	
		breakOut:
		if(!foundHome){
		
			for(int i=0; i<tempArray.length; i++){
			
				tempArray[i].trimToSize();
		
				int size = tempArray[i].size();
			
				if(size==0){
					
					tempArray[i].addElement(apprds);
					break breakOut;
				
				}
				
			}
		
		}
	
		return tempArray;
	
	}
	
	/**
	 * Check for unique rate.
	 *
	 * @param apprds the apprds
	 * @param numberRates the number rates
	 * @param vectorArrayIndex the vector array index
	 * @return true, if successful
	 */
	public boolean checkForUniqueRate(RateDataStructure apprds, int numberRates, int vectorArrayIndex){
	
		boolean unique = false;
	
		for(int i=0; i<numberRates; i++){
		
			if((apprds.getNumberParameters()==((RateDataStructure)(ds.getRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i))).getNumberParameters())
					&& !comparedRateVector.contains(apprds.getReactionID())){
			
				unique = compareParameters(apprds
							, ((RateDataStructure)(ds.getRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i))));
				
				comparedRateVector.addElement(apprds.getReactionID());
				
			}
		
		}
		
		return unique;
	
	}
	
	/**
	 * Compare parameters.
	 *
	 * @param apprds1 the apprds1
	 * @param apprds2 the apprds2
	 * @return true, if successful
	 */
	public boolean compareParameters(RateDataStructure apprds1, RateDataStructure apprds2){
	
		boolean diffParameters = false;
		
		foundAnswer:
		for(int i=0; i<apprds1.getNumberParameters(); i++){
		
			if(apprds1.getParameters()[i]!=apprds2.getParameters()[i]){
			
				diffParameters = true;
				
				break foundAnswer;
			
			}
		
		}
	
		return diffParameters;
	
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

/**
 *RateViewerPlotFramePrintable
 *
 *This class creates a Printable object to be passed to the plot printing class 
 */
class RateViewerPlotFramePrintable implements Printable{
	
	/**
	 *prints this printable class
	 *
	 *@param g
	 *@param pf
	 *@param pageIndex
	 *
	 *@return
	 */
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		Cina.rateViewerFrame.rateViewerPlotFrame.rateViewerPlotPanel.paintMe(g);
        return PAGE_EXISTS;	
	}
}