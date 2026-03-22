package org.nucastrodata.element.elementviz.thermo;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import java.awt.print.*;
import java.text.DecimalFormat;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.element.elementviz.thermo.ElementVizNucSimListPanel;
import org.nucastrodata.element.elementviz.thermo.ElementVizThermoPlotFramePrintable;
import org.nucastrodata.element.elementviz.thermo.ElementVizThermoPlotPanel;
import org.nucastrodata.element.elementviz.thermo.ElementVizThermoTableFrame;


public class ElementVizThermoPlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener{
    
    Toolkit toolkit;
    JPanel panel4, panel5, controlPanel;
    JComboBox densityminComboBox, densitymaxComboBox;
    ElementVizNucSimListPanel elementVizNucSimListPanel;
    ElementVizThermoPlotPanel elementVizThermoPlotPanel;
    public ElementVizThermoTableFrame elementVizThermoTableFrame;
    JButton printButton, saveButton, tableButton, applyButton;
    JCheckBox majorXCheckBox, majorYCheckBox, minorXCheckBox, minorYCheckBox;
	JTextField timemaxField, timeminField, tempminField, tempmaxField;
	JCheckBox legendBox;
	JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel, xGridLabel, yGridLabel;
	int densityminComboBoxInit;
    int densitymaxComboBoxInit;
    Container c;
    JScrollPane sp, sp1;
    GridBagConstraints gbc;
    private ElementVizDataStructure ds;
    
    public ElementVizThermoPlotFrame(ElementVizDataStructure ds){
	
		this.ds = ds;
	
		densityminComboBoxInit = setInitialDensityminComboBoxInit();
		densitymaxComboBoxInit = setInitialDensitymaxComboBoxInit();
	
		c = getContentPane();
	
		setSize(834, 665);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		createNucSimListPanel();
		setNucSimListPanel();
		createFormatPanel();
		setFormatPanelState();
		createResultsPlot();
		validate();
		
	}
	
    public void closeAllFrames(){
    	if(elementVizThermoTableFrame!=null){
			elementVizThermoTableFrame.setVisible(false);
			elementVizThermoTableFrame.dispose();
		}
    }
	public void setFormatPanelState(){
	
		setTitle("Thermodynamic Profile Plotting Interface");
	
		densityminComboBoxInit = setInitialDensityminComboBoxInit();
		densitymaxComboBoxInit = setInitialDensitymaxComboBoxInit();

		densityminComboBox.removeItemListener(this);
		densitymaxComboBox.removeItemListener(this);
		
		densityminComboBox.setSelectedItem(String.valueOf(densityminComboBoxInit));
        densitymaxComboBox.setSelectedItem(String.valueOf(densitymaxComboBoxInit));
        
        densityminComboBox.addItemListener(this);
		densitymaxComboBox.addItemListener(this);
        
        timeminField.setText(String.valueOf(ds.getTimeminThermo()));
        timemaxField.setText(String.valueOf(ds.getTimemaxThermo()));
        
        DecimalFormat df = new DecimalFormat("0.000E00");
        tempminField.setText(df.format(ds.getTempmin()));
        tempmaxField.setText(df.format(ds.getTempmax()));
	
	}
	
    public void createNucSimListPanel(){
    
    	JPanel nucSimListPanel = new JPanel();
    	
        nucSimListPanel.setLayout(new GridBagLayout());
		
		elementVizNucSimListPanel = new ElementVizNucSimListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        nucSimListPanel.add(elementVizNucSimListPanel, gbc);   
          
        sp = new JScrollPane(nucSimListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    public void setNucSimListPanel(){

    	elementVizNucSimListPanel.initialize();
    }
    
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
        
		applyButton = new JButton("Apply Time/Temp Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);

        legendBox = new JCheckBox("Show Legend?", true);
    	legendBox.setFont(Fonts.textFont);
    	legendBox.addActionListener(this);
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        densityminComboBox = new JComboBox();
        densityminComboBox.setFont(Fonts.textFont);
        for(int i=9; i>=-10; i--) {densityminComboBox.addItem(Integer.toString(i));}
        densityminComboBox.setSelectedItem(String.valueOf(densityminComboBoxInit));
		densityminComboBox.addItemListener(this);

        densitymaxComboBox = new JComboBox();
        densitymaxComboBox.setFont(Fonts.textFont);
        for(int i=10; i>=-9; i--) {densitymaxComboBox.addItem(Integer.toString(i));}
        densitymaxComboBox.setSelectedItem(String.valueOf(densitymaxComboBoxInit));
		densitymaxComboBox.addItemListener(this);
        
        
        //CREATE FIELDS/////////////////////////////////////////////////////////FIELDS/////////////////////
        
        timemaxField = new JTextField(10);
        
        timeminField = new JTextField(10);
        
        tempminField = new JTextField(10);
        
        tempmaxField = new JTextField(10);
        
        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Time Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        yminLabel = new JLabel("Temp Min");
        yminLabel.setFont(Fonts.textFont);
        
        JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
        
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
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridwidth = 1;
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(tempminField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(ymaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(tempmaxField, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorYCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYCheckBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(legendBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(timeminField, gbc);
  
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(timemaxField, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorXCheckBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXCheckBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;

        c.add(controlPanel, BorderLayout.SOUTH);
             
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    
    	elementVizThermoPlotPanel = new ElementVizThermoPlotPanel(densityminComboBoxInit 
													, densitymaxComboBoxInit
													, ds);
		
		elementVizThermoPlotPanel.setPreferredSize(elementVizThermoPlotPanel.getSize());
		
		elementVizThermoPlotPanel.revalidate();
		
		sp1 = new JScrollPane(elementVizThermoPlotPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(elementVizThermoPlotPanel);
		
		sp1.setBackground(Color.white);
		
		sp1.setViewport(vp);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(550);
       	
       	c.add(jsp, BorderLayout.CENTER);
		
		setVisible(true);
		
		elementVizThermoPlotPanel.setPreferredSize(elementVizThermoPlotPanel.getSize());
		
		elementVizThermoPlotPanel.revalidate();
    	
    }
    
    /**
     * Sets the initial densitymin combo box init.
     *
     * @return the int
     */
    public int setInitialDensityminComboBoxInit(){
	
		int min = 0;

		min = ds.getDensitymin();
		
		if(min < -10){
			
			min = -10;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initial densitymax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialDensitymaxComboBoxInit(){
	
		int max = 0;
		
		max = ds.getDensitymax();
		
		if(max > 10){
			
			max = 10;
		
		}
		
		return max;
	
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
               
        }else if(ie.getSource()==densityminComboBox || ie.getSource()==densitymaxComboBox){
	
       		redrawPlot();

        }

    }
    
    /**
     * Gets the densitymin.
     *
     * @return the densitymin
     */
    public double getDensitymin(){return Double.valueOf((String)densityminComboBox.getSelectedItem()).doubleValue();} 
	
	/**
	 * Gets the densitymax.
	 *
	 * @return the densitymax
	 */
	public double getDensitymax(){return Double.valueOf((String)densitymaxComboBox.getSelectedItem()).doubleValue();}
	
	/**
	 * Gets the tempmin.
	 *
	 * @return the tempmin
	 */
	public double getTempmin(){return Double.valueOf(tempminField.getText()).doubleValue();} 
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	public double getTempmax(){return Double.valueOf(tempmaxField.getText()).doubleValue();}
	
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
    
    	if(elementVizNucSimListPanel.tempRadioButton.isSelected()){
	       	
       		try{
       		
       			if(Double.valueOf(tempminField.getText()).doubleValue() >= Double.valueOf(tempmaxField.getText()).doubleValue()){
       			
       				String string = "Temperature minimum must be less than temperature maximum.";
       				
       				Dialogs.createExceptionDialog(string, this);
       			
       			}else if(Double.valueOf(timeminField.getText()).doubleValue() >= Double.valueOf(timemaxField.getText()).doubleValue()){
       			
       				String string = "Time minimum must be less than time maximum.";
       				
       				Dialogs.createExceptionDialog(string, this);
       			
       			}else{
       			
       				elementVizThermoPlotPanel.setPreferredSize(elementVizThermoPlotPanel.getSize());
		
					elementVizThermoPlotPanel.setPlotState();
			   		
			   		elementVizThermoPlotPanel.repaint();
       			
       			}
       		
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for temperature/time minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, this);
       	
       		}

       	}else if(elementVizNucSimListPanel.densityRadioButton.isSelected()){
       	
       		try{
       		
       			if(Integer.valueOf(densityminComboBox.getSelectedItem().toString()).intValue()
       				>= Integer.valueOf(densitymaxComboBox.getSelectedItem().toString()).intValue()){
       		
		   			String string = "Density minimum must be less than density maximum.";
		   			
		   			Dialogs.createExceptionDialog(string, this);
       			
       			}else if(Double.valueOf(timeminField.getText()).doubleValue() >= Double.valueOf(timemaxField.getText()).doubleValue()){
       			
       				String string = "Time minimum must be less than time maximum.";
       				
       				Dialogs.createExceptionDialog(string, this);
       				
       			}else{
       			
       				elementVizThermoPlotPanel.setPreferredSize(elementVizThermoPlotPanel.getSize());
		
					elementVizThermoPlotPanel.setPlotState();
			   		
			   		elementVizThermoPlotPanel.repaint();
       			
       			}
       			
       		}catch(NumberFormatException nfe){
       			
       			String string = "Values for time minimum and maximum must be numeric values.";
       	
       			Dialogs.createExceptionDialog(string, this);
       	
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
    	
    	for(int i=0; i<Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray.length; i++){
    	
    		if(Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].isSelected()){
    		
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
       	
	       		if(elementVizThermoTableFrame==null){
	       	
	       			elementVizThermoTableFrame = new ElementVizThermoTableFrame(ds);
	       		
		       	}else{
		       	
		       		elementVizThermoTableFrame.setTableText();
		       		elementVizThermoTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{
	       	
	       		String string = "Please select simulations for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new ElementVizThermoPlotFramePrintable(), this);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(elementVizThermoPlotPanel, this);
       		
       	}else if(ae.getSource()==legendBox){
       		redrawPlot();
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

class ElementVizThermoPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.elementVizFrame.elementVizThermoPlotFrame.elementVizThermoPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}