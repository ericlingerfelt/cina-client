package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.io.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateparam.RateParamResultsFitCanvas;
import org.nucastrodata.rate.rateparam.RateParamResultsFitPrintable;


/**
 * The Class RateParamResultsFitFrame.
 */
public class RateParamResultsFitFrame extends JFrame implements ActionListener, WindowListener, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The rate param results fit canvas. */
	RateParamResultsFitCanvas rateParamResultsFitCanvas;
	
	/** The apply button. */
	JButton printButton, saveButton, redrawButton, applyButton;
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[6];
	
	/** The string array. */
	String[] stringArray = {"log Tmin (T9): "
									, "log Tmax (T9): "
									, "% Diff min: "
									, "% Diff max: "
									, "Temperature: "
									, "% Difference: "};
	
	/** The tmax combo box. */
	JComboBox tminComboBox, tmaxComboBox;
	
	/** The percent diffmax field. */
	JTextField percentDiffminField, percentDiffmaxField;
	
	/** The minor percent diff box. */
	JCheckBox majorTBox, minorTBox, majorPercentDiffBox, minorPercentDiffBox;
	
	/** The toolkit. */
	Toolkit toolkit;
	
	/** The grid panel. */
	JPanel formatPanel, buttonPanel, scalePanel, gridPanel;

    /** The tmin combo box init. */
    int tminComboBoxInit;
    
    /** The tmax combo box init. */
    int tmaxComboBoxInit;
    
    /** The percent diffmin init. */
    double percentDiffminInit;
    
    /** The percent diffmax init. */
    double percentDiffmaxInit;
	
	/** The c. */
	Container c;
	
	/** The sp. */
	JScrollPane sp;

	/** The ds. */
	private RateParamDataStructure ds;

	/**
	 * Instantiates a new rate param results fit frame.
	 *
	 * @param ds the ds
	 */
	public RateParamResultsFitFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
	
		percentDiffminInit = setInitialpercentDiffminInit();
		percentDiffmaxInit = setInitialpercentDiffmaxInit();
	
		c = getContentPane();
	
		setSize(530, 685);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		createFormatPanel();
		setFormatPanelState();
		createResultsPlot();
		
		
				
		validate();
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Percent Difference Plot: " + ds.getRateDataStructure().getReactionString());
		
		}else{
			
			setTitle("Percent Difference Plot: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
		
        tminComboBox.setSelectedItem(String.valueOf(tminComboBoxInit));
        tmaxComboBox.setSelectedItem(String.valueOf(tmaxComboBoxInit));
        
        percentDiffminInit = setInitialpercentDiffminInit();
		percentDiffmaxInit = setInitialpercentDiffmaxInit();
		
		String tmpString = String.valueOf(Math.round(percentDiffminInit*100));
		
		percentDiffminField.setText(String.valueOf(Double.valueOf(tmpString).doubleValue()/100));
		
		tmpString = String.valueOf(Math.round(percentDiffmaxInit*100));
		
		percentDiffmaxField.setText(String.valueOf(Double.valueOf(tmpString).doubleValue()/100));
		
		
	
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

	
	/**
	 * Creates the results plot.
	 */
	public void createResultsPlot(){
	
		rateParamResultsFitCanvas = new RateParamResultsFitCanvas(tminComboBoxInit
																	, tmaxComboBoxInit
																	, percentDiffminInit
																	, percentDiffmaxInit
																	, ds);
		
		rateParamResultsFitCanvas.setPreferredSize(rateParamResultsFitCanvas.getSize());
		
		rateParamResultsFitCanvas.revalidate();
		
		sp = new JScrollPane(rateParamResultsFitCanvas);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateParamResultsFitCanvas);
		
		sp.setViewport(vp);
		
		sp.setBackground(Color.white);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		rateParamResultsFitCanvas.setPreferredSize(rateParamResultsFitCanvas.getSize());
		
		rateParamResultsFitCanvas.revalidate();
	
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
		
		scalePanel = new JPanel(new GridLayout(2, 5, 5, 2));	
		
		gridPanel = new JPanel(new GridLayout(2, 3, 5, 2));
		
		//Create Check boxes////////////////////////////////////////////CHECKBOXES////////////
		majorTBox = new JCheckBox("Major Gridlines", true);
		majorTBox.addItemListener(this);
		majorTBox.setFont(Fonts.textFont);
		
		minorTBox = new JCheckBox("Minor Gridlines", true);
		minorTBox.addItemListener(this);
		minorTBox.setFont(Fonts.textFont);
		
		majorPercentDiffBox = new JCheckBox("Major Gridlines", true);
		majorPercentDiffBox.addItemListener(this);
		majorPercentDiffBox.setFont(Fonts.textFont);
		
		minorPercentDiffBox = new JCheckBox("Minor Gridlines", false);
		minorPercentDiffBox.addItemListener(this);
		minorPercentDiffBox.setFont(Fonts.textFont);
		
		//Create text Fields///////////////////////////////////////////FIELDS////////////
		percentDiffminField = new JTextField(5);
		percentDiffminField.setFont(Fonts.textFont);
		percentDiffminField.setText(String.valueOf(percentDiffminInit));
		
		percentDiffmaxField = new JTextField(5);
		percentDiffmaxField.setFont(Fonts.textFont);
		percentDiffmaxField.setText(String.valueOf(percentDiffmaxInit));
		
		//Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        tminComboBox = new JComboBox();
        tminComboBox.setFont(Fonts.textFont);
        for(int i=-5; i<=1; i++) { tminComboBox.addItem(Integer.toString(i));}
        tminComboBox.setSelectedItem(String.valueOf(tminComboBoxInit));
        tminComboBox.addItemListener(this);

        tmaxComboBox = new JComboBox();
        tmaxComboBox.setFont(Fonts.textFont);
        for(int i=-4; i<=2; i++) { tmaxComboBox.addItem(Integer.toString(i));}
        tmaxComboBox.setSelectedItem(String.valueOf(tmaxComboBoxInit));
        tmaxComboBox.addItemListener(this);
		
		//Create Labels/////////////////////////////////////////////////LABELS////////////
		for(int i=0; i<6; i++){
			
			labelArray[i] = new JLabel(stringArray[i]);
			labelArray[i].setFont(Fonts.textFont);
		
		}
		
		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		//Create buttons////////////////////////////////////////////////BUTTONS//////////
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
	
		applyButton = new JButton("Apply");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
	
		buttonPanel.add(printButton);
		buttonPanel.add(saveButton);
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		scalePanel.add(labelArray[0]);
		
		gbc.gridx = 1; 
		gbc.gridy = 0;
		
		scalePanel.add(tminComboBox);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		scalePanel.add(labelArray[1]);
		
		gbc.gridx = 3; 
		gbc.gridy = 0;
		
		scalePanel.add(tmaxComboBox);
		
		scalePanel.add(new JLabel());
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		scalePanel.add(labelArray[2]);
		
		gbc.gridx = 1; 
		gbc.gridy = 1;
		
		scalePanel.add(percentDiffminField);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		
		scalePanel.add(labelArray[3]);
		
		gbc.gridx = 3; 
		gbc.gridy = 1;
		
		scalePanel.add(percentDiffmaxField);
		
		gbc.gridx = 4; 
		gbc.gridy = 1;
		
		scalePanel.add(applyButton);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gridPanel.add(labelArray[4]);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gridPanel.add(majorTBox);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gridPanel.add(minorTBox);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gridPanel.add(labelArray[5]);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		gridPanel.add(majorPercentDiffBox);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		
		gridPanel.add(minorPercentDiffBox);
	
		formatPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(2, 0, 2, 0);
		
		formatPanel.add(plotControlsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 0, 2, 0);
		
		formatPanel.add(scalePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		formatPanel.add(gridPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		formatPanel.add(buttonPanel, gbc);
		
		
		
		c.add(formatPanel, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Sets the initialtmin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialtminComboBoxInit(){
	
		int min = 0;
		
		double currenttmin = 0.0;
		
		currenttmin = ds.getTempminRT();
		
		min = (int)Math.floor(0.434294482*Math.log(currenttmin));
		
		return min;
	
	}
	
	/**
	 * Sets the initialtmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialtmaxComboBoxInit(){
	
		int max = 0;
		
		double currenttmax = 0.0;
	
		currenttmax = ds.getTempmaxRT();
		
		max = (int)Math.ceil(0.434294482*Math.log(currenttmax));
		
		return max;
	
	}
	
	/**
	 * Sets the initialpercent diffmin init.
	 *
	 * @return the double
	 */
	public double setInitialpercentDiffminInit(){
	
		double min = 0.0;
		
		double currentpmin = 0.0;
		
		min = getpercentDiffmin();
		
		return min;
	
	}
	
	/**
	 * Sets the initialpercent diffmax init.
	 *
	 * @return the double
	 */
	public double setInitialpercentDiffmaxInit(){
	
		double max = 0.0;
		
		double currentpmax = 0.0;
	
		max = getpercentDiffmax();
		
		return max;
	
	}
	
	/**
	 * Gets the percent diffmin.
	 *
	 * @return the percent diffmin
	 */
	public double getpercentDiffmin(){
		
		double min = 1e50;
		
		for(int i=0; i<ds.getPercentDiffDataArray().length; i++){
		
			min = Math.min(min, ds.getPercentDiffDataArray()[i]);
		
		}
		
		min = (Math.round(min*100.0))/100.0;
		
		return min;
	
	}
	
	/**
	 * Gets the percent diffmax.
	 *
	 * @return the percent diffmax
	 */
	public double getpercentDiffmax(){
		
		double max = -1e50;
		
		for(int i=0; i<ds.getPercentDiffDataArray().length; i++){
		
			max = Math.max(max, ds.getPercentDiffDataArray()[i]);
		
		}

		max = (Math.round(max*100.0))/100.0;
		
		return max;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateParamResultsFitPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(rateParamResultsFitCanvas, this);
			
		}else if(ae.getSource()==applyButton){
		
			redrawPlot();
		
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
	
		try{
			
			if((percentDiffminField.getText().equals("") || percentDiffmaxField.getText().equals(""))){
			
				String string = "One or more fields for the percent difference are empty. \nPlease enter numeric values.";
			
				Dialogs.createExceptionDialog(string, this);
			
			}else if((Double.valueOf(percentDiffminField.getText()).doubleValue() >= Double.valueOf(percentDiffmaxField.getText()).doubleValue())){
				
				String string = "Percent difference minimum must be less than the percent difference maximum.";
			
				Dialogs.createExceptionDialog(string, this);
			
			}else if(Double.valueOf(percentDiffminField.getText()).doubleValue() < -100){
				
				String string = "Percent difference minimum must be more than -100";
			
				Dialogs.createExceptionDialog(string, this);
			
			}else if(Double.valueOf(percentDiffmaxField.getText()).doubleValue() > 100){
				
				String string = "Percent difference maximum must be less than 100";
			
				Dialogs.createExceptionDialog(string, this);
			
			}else{
			
				rateParamResultsFitCanvas.setPlotState();
				rateParamResultsFitCanvas.repaint();
							
			}
		
		}catch(NumberFormatException nfe){
		
			String string = "Values for the percent diffence minimum and maximum must be numeric values.";
			
			Dialogs.createExceptionDialog(string, this);
		
		}	
	
	}
	
	/**
	 * Gets the tmin.
	 *
	 * @return the tmin
	 */
	public double getTmin(){return Double.valueOf((String)tminComboBox.getSelectedItem()).doubleValue();} 
	
	/**
	 * Gets the tmax.
	 *
	 * @return the tmax
	 */
	public double getTmax(){return Double.valueOf((String)tmaxComboBox.getSelectedItem()).doubleValue();}
	
	/**
	 * Gets the percent diffmin.
	 *
	 * @return the percent diffmin
	 */
	public double getPercentDiffmin(){return Double.valueOf(percentDiffminField.getText()).doubleValue();}
	
	/**
	 * Gets the percent diffmax.
	 *
	 * @return the percent diffmax
	 */
	public double getPercentDiffmax(){return Double.valueOf(percentDiffmaxField.getText()).doubleValue();}
	
	/**
	 * Gets the minor t.
	 *
	 * @return the minor t
	 */
	public boolean getMinorT(){return minorTBox.isSelected();} 
	
	/**
	 * Gets the major t.
	 *
	 * @return the major t
	 */
	public boolean getMajorT(){return majorTBox.isSelected();} 
	
	/**
	 * Gets the minor percent diff.
	 *
	 * @return the minor percent diff
	 */
	public boolean getMinorPercentDiff(){return minorPercentDiffBox.isSelected();} 
	
	/**
	 * Gets the major percent diff.
	 *
	 * @return the major percent diff
	 */
	public boolean getMajorPercentDiff(){return majorPercentDiffBox.isSelected();} 

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==majorTBox){
		
			if(majorTBox.isSelected()){
				
				minorTBox.setEnabled(true);
				
			}else{
				minorTBox.setSelected(false);
				minorTBox.setEnabled(false);
			}
	
			redrawPlot();
	
		}else if(ie.getSource()==majorPercentDiffBox){
		
			if(majorPercentDiffBox.isSelected()){
				
				minorPercentDiffBox.setEnabled(true);
				
			}else{
				minorPercentDiffBox.setSelected(false);
				minorPercentDiffBox.setEnabled(false);
			}
			
			redrawPlot();
			
		}else if(ie.getSource()==minorTBox || ie.getSource()==minorPercentDiffBox){
			
			redrawPlot();
				
		}else if(ie.getSource()==tminComboBox || ie.getSource()==tmaxComboBox){
		
			if(Integer.valueOf(tminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(tmaxComboBox.getSelectedItem().toString()).doubleValue()){
       		
       			String string = "Temperature minimum must be less than temperature maximum.";
       			
       			Dialogs.createExceptionDialog(string, this);
       			
       			tminComboBox.setSelectedItem("-5");
       		
   			}else{
   			
   				redrawPlot();
   			
   			}
		}
	}
}

class RateParamResultsFitPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.rateParamFrame.rateParamResultsFitFrame.rateParamResultsFitCanvas.paintMe(g);

        return PAGE_EXISTS;
		
	}

}