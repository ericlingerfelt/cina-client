package org.nucastrodata.data.datamass;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.io.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.data.datamass.DataMassPlotPanel;
import org.nucastrodata.data.datamass.DataMassPlotPrintable;
import org.nucastrodata.data.datamass.DataMassTableFrame;


/**
 * The Class DataMassPlotFrame.
 */
public class DataMassPlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener, ChangeListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The data mass plot panel. */
	DataMassPlotPanel dataMassPlotPanel;
	
	/** The table button. */
	JButton printButton, saveButton, applyButton, tableButton;
	
	/** The Ymax spinner. */
	JSpinner XmaxSpinner, XminSpinner, YminSpinner, YmaxSpinner;
	
	/** The Ymax model. */
	SpinnerNumberModel XminModel, XmaxModel, YminModel, YmaxModel;
	
	/** The minor y box. */
	JCheckBox majorXBox, minorXBox, majorYBox, minorYBox;
	
	/** The plot controls label. */
	JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel, xGridLabel, yGridLabel, plotControlsLabel;
	
	/** The X combo box. */
	JComboBox XComboBox;
	
	/** The control panel. */
	JPanel formatPanel, buttonPanel, controlPanel;
	
	/** The c. */
	Container c;
	
	/** The sp. */
	JScrollPane sp;

	/** The Diff radio button. */
	JRadioButton RMSRadioButton, DiffRadioButton;

	/** The button group. */
	ButtonGroup buttonGroup;

	/** The type. */
	String type;

	/** The ds. */
	private DataMassDataStructure ds;

	/**
	 * Instantiates a new data mass plot frame.
	 *
	 * @param ds the ds
	 */
	public DataMassPlotFrame(DataMassDataStructure ds){
	
		this.ds = ds;
	
		c = getContentPane();
	
		setSize(756, 670);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		type = "Diff";
		
		createFormatPanel();
		setFormatPanelState();
		createResultsPlot();
		
		
		
		validate();
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
	
		if(type.equals("Diff")){
	
			setTitle("Mass Model Evaluator Plotting Interface: Mass Differences of "
						+ ds.getTheoryModelDataStructure().getModelName()
						+ " and "
						+ ds.getExpModelDataStructure().getModelName());
						
		}else if(type.equals("RMS")){
	
			setTitle("Mass Model Evaluator Plotting Interface: RMS Difference of "
						+ ds.getTheoryModelDataStructure().getModelName()
						+ " and "
						+ ds.getExpModelDataStructure().getModelName());
						
		}
        
        XminSpinner.removeChangeListener(this);
		XmaxSpinner.removeChangeListener(this);
		
		YminSpinner.removeChangeListener(this);
		YmaxSpinner.removeChangeListener(this);
		
		if(type.equals("Diff")){
	
			if(XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
		
				XminModel.setMinimum(new Integer(0));
				XminModel.setValue(new Integer(ds.getZmin()));
		        XminModel.setMaximum(new Integer(ds.getZmax()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getZmin()+1));
				XmaxModel.setValue(new Integer(ds.getZmax()));
	        
	        	xminLabel.setText("Z Min");
	        
	        	YminModel.setValue(new Double(ds.getDiffmin()));
		        YminModel.setMaximum(new Double(ds.getDiffmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getDiffmin()+1));
				YmaxModel.setValue(new Double(ds.getDiffmax()));
		    
		    	yminLabel.setText("Mass Diff Min");
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
				XminModel.setMinimum(new Integer(0));
				XminModel.setValue(new Integer(ds.getNmin()));
		        XminModel.setMaximum(new Integer(ds.getNmax()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getNmin()+1));
				XmaxModel.setValue(new Integer(ds.getNmax()));
	       
	        	xminLabel.setText("N Min");
	        
	        	YminModel.setValue(new Double(ds.getDiffmin()));
		        YminModel.setMaximum(new Double(ds.getDiffmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getDiffmin()+1));
				YmaxModel.setValue(new Double(ds.getDiffmax()));
		    
		    	yminLabel.setText("Mass Diff Min");
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("A = Z + N")){
			
				XminModel.setMinimum(new Integer(1));
				XminModel.setValue(new Integer(ds.getAmin()));
		        XminModel.setMaximum(new Integer(ds.getAmax()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getAmin()+1));
				XmaxModel.setValue(new Integer(ds.getAmax()));
	        
	        	xminLabel.setText("A Min");
	        	
	        	YminModel.setValue(new Double(ds.getDiffmin()));
		        YminModel.setMaximum(new Double(ds.getDiffmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getDiffmin()+1));
				YmaxModel.setValue(new Double(ds.getDiffmax()));
		    
		    	yminLabel.setText("Mass Diff Min");
	        	
	    	}
						
		}else if(type.equals("RMS")){
	
			if(XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
		
				XminModel.setMinimum(new Integer(0));
				//XminModel.setValue(new Integer(ds.getZminRMS()));
				XminModel.setValue(new Integer(0));
		        XminModel.setMaximum(new Integer(ds.getZmaxRMS()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getZminRMS()+1));
				XmaxModel.setValue(new Integer(ds.getZmaxRMS()));
	        
	        	xminLabel.setText("Z Min");
	        	yminLabel.setText("RMS Min");
	        	
	        	YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(ds.getRMSZmin()));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(ds.getRMSZmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getRMSZmin()+1));
				YmaxModel.setValue(new Double(ds.getRMSZmax()));
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
				XminModel.setMinimum(new Integer(0));
				//XminModel.setValue(new Integer(ds.getNminRMS()));
				XminModel.setValue(new Integer(0));
		        XminModel.setMaximum(new Integer(ds.getNmaxRMS()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getNminRMS()+1));
				XmaxModel.setValue(new Integer(ds.getNmaxRMS()));
		        
		        xminLabel.setText("N Min");
		        yminLabel.setText("RMS Min");
		        
		        YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(ds.getRMSNmin()));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(ds.getRMSNmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getRMSNmin()+1));
				YmaxModel.setValue(new Double(ds.getRMSNmax()));
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("A = Z + N")){
			
				XminModel.setMinimum(new Integer(1));
				//XminModel.setValue(new Integer(ds.getAminRMS()));
				XminModel.setValue(new Integer(1));
		        XminModel.setMaximum(new Integer(ds.getAmaxRMS()-1));
			
		       	XmaxModel.setMinimum(new Integer(ds.getAminRMS()+1));
				XmaxModel.setValue(new Integer(ds.getAmaxRMS()));
		        
		        xminLabel.setText("A Min");
		        yminLabel.setText("RMS Min");
		        
		        YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(ds.getRMSAmin()));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(ds.getRMSAmax()-1));
			
		       	YmaxModel.setMinimum(new Double(ds.getRMSAmin()+1));
				YmaxModel.setValue(new Double(ds.getRMSAmax()));
	 
	    	}
						
		}
        
        XminSpinner.addChangeListener(this);
		XmaxSpinner.addChangeListener(this);

        YminSpinner.addChangeListener(this);
		YmaxSpinner.addChangeListener(this);
        
        
	
	}
	
	/**
	 * Creates the results plot.
	 */
	public void createResultsPlot(){
	
		dataMassPlotPanel = new DataMassPlotPanel(ds);
		
		dataMassPlotPanel.setPreferredSize(dataMassPlotPanel.getSize());
		
		dataMassPlotPanel.revalidate();
		
		sp = new JScrollPane(dataMassPlotPanel);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		dataMassPlotPanel.setPreferredSize(dataMassPlotPanel.getSize());
		
		dataMassPlotPanel.revalidate();
	
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		
		gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   
        
        XComboBox = new JComboBox();
        XComboBox.addItem("Z (proton number)");
        XComboBox.addItem("N (neutron number)");
        XComboBox.addItem("A = Z + N");
        XComboBox.setSelectedItem("Z (proton number)");
        XComboBox.setFont(Fonts.textFont);
        XComboBox.addItemListener(this);

		//Create Radio Buttons//////////////////////////////////////////RAIODBUTTONS//////////
		RMSRadioButton = new JRadioButton("RMS of Difference", false);
		RMSRadioButton.addItemListener(this);
		
		DiffRadioButton = new JRadioButton("Mass Differences", true);
		DiffRadioButton.addItemListener(this);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(RMSRadioButton);
		buttonGroup.add(DiffRadioButton);
		
		//Create Check boxes////////////////////////////////////////////CHECKBOXES////////////
		majorXBox = new JCheckBox("Major Gridlines", true);
		majorXBox.addItemListener(this);
		majorXBox.setFont(Fonts.textFont);
		
		minorXBox = new JCheckBox("Minor Gridlines", true);
		minorXBox.addItemListener(this);
		minorXBox.setFont(Fonts.textFont);
		
		majorYBox = new JCheckBox("Major Gridlines", true);
		majorYBox.addItemListener(this);
		majorYBox.setFont(Fonts.textFont);
		
		minorYBox = new JCheckBox("Minor Gridlines", false);
		minorYBox.addItemListener(this);
		minorYBox.setFont(Fonts.textFont);
		
		controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
		
		XminModel = new SpinnerNumberModel(0, 0, 150, 1);
        XmaxModel = new SpinnerNumberModel(0, 0, 999, 1);
		
        XminSpinner = new JSpinner(XminModel);
        XminSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(XminSpinner.getEditor())).getTextField().setEditable(false);
        
        XmaxSpinner = new JSpinner(XmaxModel);
        XmaxSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(XmaxSpinner.getEditor())).getTextField().setEditable(false);
        
		YminModel = new SpinnerNumberModel(new Double(ds.getDiffmin())
    										, null
    										, null
    										, new Double(1.0));
    										
        YmaxModel = new SpinnerNumberModel(new Double(ds.getDiffmax())
    										, null
    										, null
    										, new Double(1.0));
        
        YminSpinner = new JSpinner(YminModel);
        YminSpinner.addChangeListener(this);

        YmaxSpinner = new JSpinner(YmaxModel);
        YmaxSpinner.addChangeListener(this);
		
		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		//Create buttons////////////////////////////////////////////////BUTTONS//////////
		printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
        
		applyButton = new JButton("Apply Entered Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);
	
		buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);
	
		xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Z Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        yminLabel = new JLabel("Mass Diff Min");
        yminLabel.setFont(Fonts.textFont);
    	
    	gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.gridwidth = 8;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(DiffRadioButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(new JLabel("Select Z, N, or A: "), gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(ymaxLabel, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YmaxSpinner, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorYBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(RMSRadioButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(XComboBox, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XmaxSpinner, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorXBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 8;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;
    	
		c.add(controlPanel, BorderLayout.SOUTH);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
    
    	XminModel.setMaximum(new Integer(XmaxModel.getNumber().intValue()-1));
    	XmaxModel.setMinimum(new Integer(XminModel.getNumber().intValue()+1));
    
    	YminModel.setMaximum(new Double(YmaxModel.getNumber().doubleValue()-1));
    	YmaxModel.setMinimum(new Double(YminModel.getNumber().doubleValue()+1));
    
    	redrawPlot();
    
    }
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new DataMassPlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(dataMassPlotPanel, this);
			
		}else if(ae.getSource()==applyButton){
		
			redrawPlot();
		
		}else if(ae.getSource()==tableButton){
		
			if(Cina.dataMassFrame.dataMassTableFrame==null){
	       	
       			Cina.dataMassFrame.dataMassTableFrame = new DataMassTableFrame(ds);
       		
	       	}else{
	       	
	       		Cina.dataMassFrame.dataMassTableFrame.setTableText();
	       		Cina.dataMassFrame.dataMassTableFrame.setVisible(true);
	       	
	       	}
		
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
		
		dataMassPlotPanel.setPlotState();
		dataMassPlotPanel.repaint();

	}
	
	/**
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){return XminModel.getNumber().intValue();} 
	
	/**
	 * Gets the xmax.
	 *
	 * @return the xmax
	 */
	public double getXmax(){return XmaxModel.getNumber().intValue();}
	
	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){return YminModel.getNumber().doubleValue();}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){return YmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the minor x.
	 *
	 * @return the minor x
	 */
	public boolean getMinorX(){return minorXBox.isSelected();} 
	
	/**
	 * Gets the major x.
	 *
	 * @return the major x
	 */
	public boolean getMajorX(){return majorXBox.isSelected();} 
	
	/**
	 * Gets the minor y.
	 *
	 * @return the minor y
	 */
	public boolean getMinorY(){return minorYBox.isSelected();} 
	
	/**
	 * Gets the major y.
	 *
	 * @return the major y
	 */
	public boolean getMajorY(){return majorYBox.isSelected();} 
	
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
        
      	if(ie.getSource()==majorXBox){
      		
            if(majorXBox.isSelected()){
            	
                minorXBox.setEnabled(true);
                
            }else{
            	
                minorXBox.setSelected(false);
                minorXBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorYBox){
        	
            if(majorYBox.isSelected()){
            	
                minorYBox.setEnabled(true);
                
            }else{

                minorYBox.setSelected(false);
                minorYBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if((ie.getSource()==minorXBox) 
        		|| (ie.getSource()==minorYBox)){
        	
            redrawPlot();
            
        }else if(ie.getSource()==XComboBox){

        	setFormatPanelState();
    				
    		
    		
    		validate();
    		
        	redrawPlot();
        
        }else if(ie.getSource()==RMSRadioButton || ie.getSource()==DiffRadioButton){
        
        	if(RMSRadioButton.isSelected()){
        	
        		type = "RMS";
        	
        	}else if(DiffRadioButton.isSelected()){
        	
        		type = "Diff";
        	
        	}
        
        	setFormatPanelState();
    				
    		
    		
    		validate();
    		
        	redrawPlot();
        
        }

    }
	
	//Window closing listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {  
		setVisible(false);
		dispose(); 
    } 
    
    //Remainder of windowListener methods
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

class DataMassPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		
		Cina.dataMassFrame.dataMassPlotFrame.dataMassPlotPanel.paintMe(g);
		
        return PAGE_EXISTS;
		
	}

}