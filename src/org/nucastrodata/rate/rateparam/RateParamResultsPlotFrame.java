package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.io.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateparam.RateParamResultsListPanel;
import org.nucastrodata.rate.rateparam.RateParamResultsPlotCanvas;
import org.nucastrodata.rate.rateparam.RateParamResultsPlotPrintable;
import org.nucastrodata.rate.rateparam.RateParamResultsTableFrame;


/**
 * The Class RateParamResultsPlotFrame.
 */
public class RateParamResultsPlotFrame extends JFrame implements ActionListener
																		, ItemListener
																		, ChangeListener{

	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The rate param results plot canvas. */
	protected RateParamResultsPlotCanvas rateParamResultsPlotCanvas;
	
	/** The table button. */
	private JButton printButton, saveButton, tableButton;
	
	/** The c. */
	private Container c;
	
	/** The sp1. */
	private JScrollPane sp, sp1;
	
	/** The tmax spinner. */
	private JSpinner rminSpinner, rmaxSpinner, tminSpinner, tmaxSpinner;
    
    /** The tmax model. */
    private SpinnerNumberModel rminModel, rmaxModel, tminModel, tmaxModel;
	
	/** The rmax spinner init. */
	private int tminSpinnerInit, tmaxSpinnerInit, rminSpinnerInit, rmaxSpinnerInit;
	
	/** The minor temp check box. */
	private JCheckBox majorRateCheckBox, majorTempCheckBox, minorRateCheckBox, minorTempCheckBox;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/** The rate param results list panel. */
	protected RateParamResultsListPanel rateParamResultsListPanel;
		
	/**
	 * Instantiates a new rate param results plot frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsPlotFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		c = getContentPane();
		setSize(761, 684);
		setVisible(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose(); 
		    } 
		});
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
	
		createFormatPanel();
		setFormatPanelState();
		createListPanel();
		setListPanel();
		createResultsPlot();
		
		
		
		validate();
	}
	
	/**
	 * Creates the list panel.
	 */
	public void createListPanel(){
    
    	JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
		rateParamResultsListPanel = new RateParamResultsListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        listPanel.add(rateParamResultsListPanel, gbc);   
          
        sp = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    }
    
    /**
     * Sets the list panel.
     */
    public void setListPanel(){rateParamResultsListPanel.initialize();}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
	
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Parameterization Plot: " + ds.getRateDataStructure().getReactionString());

		}else{
		
			setTitle("Parameterization Plot: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
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
        
        
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);

		gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.insets = new Insets(5,5,5,5); 

        buttonPanel.add(printButton, gbc);
        
        gbc.gridx = 1;                      
        gbc.gridy = 0;
        
        buttonPanel.add(saveButton, gbc);
        
        gbc.gridx = 2;                      
        gbc.gridy = 0;
        
		buttonPanel.add(tableButton, gbc);

		JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        
        JLabel rminLabel = new JLabel("log Rate min");
        rminLabel.setFont(Fonts.textFont);
        JLabel rmaxLabel = new JLabel("max");
        rmaxLabel.setFont(Fonts.textFont);
        JLabel TminLabel = new JLabel("log Temp min");
        TminLabel.setFont(Fonts.textFont);
        JLabel TmaxLabel = new JLabel("max");
        TmaxLabel.setFont(Fonts.textFont);

		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");

		rminModel = new SpinnerNumberModel(0, -50, 50, 1);
        rmaxModel = new SpinnerNumberModel(0, -50, 50, 1);
        tminModel = new SpinnerNumberModel(0, -3, 3, 1);
        tmaxModel = new SpinnerNumberModel(0, -3, 3, 1);
        
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
        
        gbc.insets = new Insets(3, 3, 3, 3);
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(plotControlsLabel, gbc);		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(rminLabel, gbc);
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(rminSpinner, gbc);
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(rmaxLabel, gbc);
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(rmaxSpinner, gbc);
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorRateCheckBox, gbc);
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorRateCheckBox, gbc);
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(TminLabel, gbc);
  		gbc.gridx = 1;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(tminSpinner, gbc);
  		gbc.gridx = 2;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(TmaxLabel, gbc);
  		gbc.gridx = 3;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(tmaxSpinner, gbc);
  		gbc.gridx = 4;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorTempCheckBox, gbc);
  		gbc.gridx = 5;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorTempCheckBox, gbc);
  		gbc.gridx = 0;
  		gbc.gridy = 4;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.fill = GridBagConstraints.NONE;
  		gbc.gridwidth = 7;
  		controlPanel.add(buttonPanel, gbc);

        c.add(controlPanel, BorderLayout.SOUTH); 

		
	}
	
	/**
	 * Creates the results plot.
	 */
	public void createResultsPlot(){
		
		rateParamResultsPlotCanvas = new RateParamResultsPlotCanvas(rminSpinnerInit 
																		, rmaxSpinnerInit
																		, tminSpinnerInit
																		, tmaxSpinnerInit
																		, ds);
		
		rateParamResultsPlotCanvas.setPreferredSize(rateParamResultsPlotCanvas.getSize());
		rateParamResultsPlotCanvas.revalidate();
		
		sp1 = new JScrollPane(rateParamResultsPlotCanvas);
		JViewport vp = new JViewport();
		vp.setView(rateParamResultsPlotCanvas);
		sp1.setBackground(Color.white);
		sp1.setViewport(vp);
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	jsp.setDividerLocation(520);
       	c.add(jsp, BorderLayout.CENTER);
		setVisible(true);
		
		rateParamResultsPlotCanvas.setPreferredSize(rateParamResultsPlotCanvas.getSize());
		rateParamResultsPlotCanvas.revalidate();
	
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
    	rateParamResultsPlotCanvas.setPreferredSize(rateParamResultsPlotCanvas.getSize());
		rateParamResultsPlotCanvas.setPlotState();
   		rateParamResultsPlotCanvas.repaint();
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
	
	/**
	 * Sets the initialrmin spinner init.
	 *
	 * @return the int
	 */
	public int setInitialrminSpinnerInit(){
		
		double newRmin = 1e40;
		
		if(ds.getLowTemp()){
		
			for(int i=0; i<ds.getLowRateDataArray().length; i++){
				
				newRmin = Math.min(newRmin, ds.getLowRateDataArray()[i]);
				
			}
		
		}
		
		for(int i=0; i<ds.getRateParamDataArray().length; i++){
			
			newRmin = Math.min(newRmin, ds.getRateParamDataArray()[i]);
			
		}
		
		if(ds.getExtraPoints()){
		
			for(int i=0; i<ds.getRateDataArrayExtra().length; i++){
				
				newRmin = Math.min(newRmin, ds.getRateDataArrayExtra()[i]);
				
			}
		
		}
		
		for(int i=0; i<ds.getRateDataArray().length; i++){
			
			newRmin = Math.min(newRmin, ds.getRateDataArray()[i]);
			
		}
		
		if(ds.getHighTemp()){
		
			for(int i=0; i<ds.getHighRateDataArray().length; i++){
				
				newRmin = Math.min(newRmin, ds.getHighRateDataArray()[i]);
				
			}
		
		}
		
		if(newRmin < 1e-40){
			
			newRmin = 1e-40;
		
		}
		
		return (int)Math.floor(0.434294482*Math.log(newRmin));
	
	}
	
	/**
	 * Sets the initialrmax spinner init.
	 *
	 * @return the int
	 */
	public int setInitialrmaxSpinnerInit(){
		
		double newRmax = 0.0;
		
		if(ds.getHighTemp()){
		
			for(int i=0; i<ds.getHighRateDataArray().length; i++){
				
				newRmax = Math.max(newRmax, ds.getHighRateDataArray()[i]);
				
			}
		
		}
		
		for(int i=0; i<ds.getRateParamDataArray().length; i++){
			
			newRmax = Math.max(newRmax, ds.getRateParamDataArray()[i]);
			
		}
		
		if(ds.getExtraPoints()){
		
			for(int i=0; i<ds.getRateDataArrayExtra().length; i++){
				
				newRmax = Math.max(newRmax, ds.getRateDataArrayExtra()[i]);
				
			}
		
		}
	
		for(int i=0; i<ds.getRateDataArray().length; i++){
			
			newRmax = Math.max(newRmax, ds.getRateDataArray()[i]);
			
		}
		
		if(ds.getLowTemp()){
		
			for(int i=0; i<ds.getLowRateDataArray().length; i++){
				
				newRmax = Math.max(newRmax, ds.getLowRateDataArray()[i]);
				
			}
		
		}
		
		if(newRmax > 1e40){
			
			newRmax = 1e40;
			
		}
		
		return (int)Math.ceil(0.434294482*Math.log(newRmax));
	
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
        }
    }
	
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
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateParamResultsPlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(rateParamResultsPlotCanvas, this);
			
		}else if(ae.getSource()==tableButton){
		
			if(Cina.rateParamFrame.rateParamResultsTableFrame==null){
				Cina.rateParamFrame.rateParamResultsTableFrame = new RateParamResultsTableFrame(ds);
				Cina.rateParamFrame.rateParamResultsTableFrame.setVisible(true);
			}else{
				Cina.rateParamFrame.rateParamResultsTableFrame.setTableText();
				Cina.rateParamFrame.rateParamResultsTableFrame.setVisible(true);
			}
			
		}
	}
}

class RateParamResultsPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		
		Cina.rateParamFrame.rateParamResultsPlotFrame.rateParamResultsPlotCanvas.paintMe(g);
		
        return PAGE_EXISTS;
		
	}

}