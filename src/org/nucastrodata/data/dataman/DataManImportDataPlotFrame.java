package org.nucastrodata.data.dataman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.print.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.data.dataman.DataManImportDataPlotCanvas;
import org.nucastrodata.data.dataman.DataManImportDataPlotPrintable;


/**
 * The Class DataManImportDataPlotFrame.
 */
public class DataManImportDataPlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The data man import data plot canvas. */
	DataManImportDataPlotCanvas dataManImportDataPlotCanvas;
	
	/** The ok button. */
	JButton printButton, saveButton, redrawButton, applyButton, tableButton, okButton;
	
	/** The button panel. */
	JPanel formatPanel, scalePanel, setScalePanel, scaleTitlePanel, buttonPanel;
	
	/** The Ymax combo box. */
	JComboBox YminComboBox, YmaxComboBox;
	
	/** The Xmax combo box. */
	JComboBox XminComboBox, XmaxComboBox;
	
	/** The toolkit. */
	Toolkit toolkit;
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[7];
	
	/** The string array. */
	String[] stringArray = {"Autoscale E axis"
								,"Set Scale"
								, "Enter E min: " 
								, "Enter E max: "
								, "Enter S Factor min: "
								, "Enter S Factor max: "
								, "log Cross Sec min: "
								, "log Cross Sec max: "
								, "to max and min?"};
	
	/** The E box. */
	JCheckBox EBox;
	
	/** The Emax field. */
	JTextField EminField, EmaxField;
	
	/** The Ymax field. */
	JTextField YminField, YmaxField;
	
	/** The file dialog. */
	JFileChooser fileDialog;
	
	/** The save file dir. */
	String saveFilename, saveFileDir;
	
	/** The Ymin combo box init. */
	int YminComboBoxInit = 0;
    
    /** The Ymax combo box init. */
    int YmaxComboBoxInit = 0;
	
	/** The c. */
	Container c;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The exception dialog. */
	JDialog exceptionDialog;
	
	/** The ds. */
	private DataManDataStructure ds;
	
	/**
	 * Instantiates a new data man import data plot frame.
	 *
	 * @param ds the ds
	 */
	public DataManImportDataPlotFrame(DataManDataStructure ds){
	
		this.ds = ds;
	
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
	
		c = getContentPane();
	
		setSize(530, 671);
	
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		createFormatPanel();
		createCheckPlot();
		setFormatPanelState();
		
		
		
		validate();
	}

	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
				setTitle("Cross Section vs. Energy Plot: " 
							+ ds.getImportNucDataDataStructure().getNucDataName()
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ ")");
						
			}else{
			
				setTitle("Cross Section vs. Energy Plot: " 
							+ ds.getImportNucDataDataStructure().getNucDataName()
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ " ["
							+ ds.getImportNucDataDataStructure().getDecay()
							+ "]"
							+ ")");	
			
			}
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
				setTitle("S Factor vs. Energy Plot: " + ds.getImportNucDataDataStructure().getNucDataName()
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ ")");
						
			}else{
			
				setTitle("S Factor vs. Energy Plot: " + ds.getImportNucDataDataStructure().getNucDataName()
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ " ["
							+ ds.getImportNucDataDataStructure().getDecay()
							+ "]"
							+ ")");
			
			}
		
		}
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			labelArray[4].setText(stringArray[6]);
			labelArray[5].setText(stringArray[7]);	
		
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			labelArray[4].setText(stringArray[4]);
			labelArray[5].setText(stringArray[5]);
			
		}
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			YminComboBoxInit = setInitialYminComboBoxInit();
			YmaxComboBoxInit = setInitialYmaxComboBoxInit();
			
			YmaxComboBox.removeItemListener(this);
			YminComboBox.removeItemListener(this);
			
			YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
	        YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));
	
	        YmaxComboBox.addItemListener(this);
			YminComboBox.addItemListener(this);
			
		}
		
		setScalePanel.removeAll();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[2], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EminField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[3], gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EmaxField, gbc);

		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 5, 2, 0);
		setScalePanel.add(applyButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[4], gbc);
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YminComboBox, gbc);
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YminField, gbc);
		
		}
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[5], gbc);
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			gbc.gridx = 3;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YmaxComboBox, gbc);
	
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			gbc.gridx = 3;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YmaxField, gbc);
	
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

	
	/**
	 * Creates the check plot.
	 */
	public void createCheckPlot(){
	
		dataManImportDataPlotCanvas = new DataManImportDataPlotCanvas(YminComboBoxInit, YmaxComboBoxInit, ds);
		
		dataManImportDataPlotCanvas.setPreferredSize(dataManImportDataPlotCanvas.getSize());
		
		dataManImportDataPlotCanvas.revalidate();
		
		sp = new JScrollPane(dataManImportDataPlotCanvas);
		
		JViewport vp = new JViewport();
		
		vp.setView(dataManImportDataPlotCanvas);
		
		sp.setViewport(vp);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		dataManImportDataPlotCanvas.setPreferredSize(dataManImportDataPlotCanvas.getSize());
		
		dataManImportDataPlotCanvas.revalidate();
	
	}
	
	/**
	 * Reinitialize.
	 */
	public void reinitialize(){
	
		c.removeAll();
		
		createFormatPanel();
		createCheckPlot();
		
		validate();
	
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		
		//Create choices////////////////////////////////////////////////CHOICES/////////
		YminComboBox = new JComboBox();
        YminComboBox.setFont(Fonts.textFont);
        for(int i=9; i>=-10; i--) { YminComboBox.addItem(Integer.toString(i));}
        YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=10; i>=-9; i--) { YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));
        YmaxComboBox.addItemListener(this);

		//Create buttons///////////////////////////////////////////////BUTTONS//////////
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		applyButton = new JButton("Apply");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
				
		//Create Labels/////////////////////////////////////////////////LABELS////////
		labelArray[0] = new JLabel(stringArray[0] + " " + stringArray[8]);
		labelArray[0].setFont(Fonts.textFont);
		
		labelArray[1] = new JLabel(stringArray[1]);
		labelArray[1].setFont(Fonts.textFont);
		
		labelArray[2] = new JLabel(stringArray[2]);
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3] = new JLabel(stringArray[3]);
		labelArray[3].setFont(Fonts.textFont);

		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			labelArray[4] = new JLabel(stringArray[6]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[7]);
			labelArray[5].setFont(Fonts.textFont);	
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			labelArray[4] = new JLabel(stringArray[4]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[5]);
			labelArray[5].setFont(Fonts.textFont);
		
		}
		
		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		//Create text fields///////////////////////////////////////TEXTFIELDS/////////////////
		EminField = new JTextField(5);
		EminField.setFont(Fonts.textFont);
		EminField.setEditable(false);
		EminField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmin()));
		
		EmaxField = new JTextField(5);
		EmaxField.setFont(Fonts.textFont);
		EmaxField.setEditable(false);
		EmaxField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmax()));
		
		YminField = new JTextField(5);
		YminField.setFont(Fonts.textFont);
		YminField.setText(String.valueOf(ds.getImportNucDataDataStructure().getYmin()));
		
		YmaxField = new JTextField(5);
		YmaxField.setFont(Fonts.textFont);
		YmaxField.setText(String.valueOf(ds.getImportNucDataDataStructure().getYmax()));
		
		//Create check boxes/////////////////////////////////////CHECKBOXES////////////////////
		EBox = new JCheckBox("", true);
		EBox.addItemListener(this);
		
		buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
		
		setScalePanel = new JPanel(new GridBagLayout());
		
		scaleTitlePanel = new JPanel(new GridBagLayout());
	
		JPanel tempPanel = new JPanel(new FlowLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		scaleTitlePanel.add(plotControlsLabel, gbc);
		
		tempPanel.add(labelArray[0]);
		tempPanel.add(EBox);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[2], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EminField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[3], gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(EmaxField, gbc);

		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 5, 2, 0);
		setScalePanel.add(applyButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[4], gbc);
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YminComboBox, gbc);
		
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YminField, gbc);
		
		}
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[5], gbc);
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
		
			gbc.gridx = 3;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YmaxComboBox, gbc);
	
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			gbc.gridx = 3;
			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.WEST;
			setScalePanel.add(YmaxField, gbc);
	
		}
	
		buttonPanel.add(printButton);
		buttonPanel.add(saveButton);
	
		formatPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		formatPanel.add(scaleTitlePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		formatPanel.add(tempPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 0, 2, 0);
		formatPanel.add(setScalePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		formatPanel.add(buttonPanel, gbc);
			
		c.add(formatPanel, BorderLayout.SOUTH);
		
		
		
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
		
		currentYmin = ds.getImportNucDataDataStructure().getYmin();
	
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
		
		currentYmax = ds.getImportNucDataDataStructure().getYmax();
	
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 10){
		
			max = 10;
		
		}

		return max;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new DataManImportDataPlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(dataManImportDataPlotCanvas, this);
			
		}else if(ae.getSource()==okButton){
		
			exceptionDialog.setVisible(false);
			exceptionDialog.dispose();
					
			EminField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmin()));
			EmaxField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmax()));
			
			YminField.setText(String.valueOf(ds.getImportNucDataDataStructure().getYmin()));
			YmaxField.setText(String.valueOf(ds.getImportNucDataDataStructure().getYmax()));
			
			redrawPlot();

		}else if(ae.getSource()==applyButton){
			
			redrawPlot();
			
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
	
		String string = "";
		
		if(!EBox.isSelected()){
					
			try{
		
				if((EminField.getText().equals("") || EmaxField.getText().equals(""))){

					string = "One or more fields for the energy range are empty. \nPlease enter numeric values.";

					createExceptionDialog(string, this);
					
				}else if((Double.valueOf(EminField.getText()).doubleValue() >= Double.valueOf(EmaxField.getText()).doubleValue())){

					string = "Energy minimum must be less than the energy maximum.";
					createExceptionDialog(string, this);
			
				}else{
					
					dataManImportDataPlotCanvas.setXmin(Double.valueOf(EminField.getText()).doubleValue());
					dataManImportDataPlotCanvas.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());
				
					dataManImportDataPlotCanvas.setDoScalex(0);
					
					dataManImportDataPlotCanvas.setPlotState();

				}
				
			}catch(NumberFormatException nfe){
					
				string = "Values for Emin and Emax must be numeric values.";		
				createExceptionDialog(string, this);
					
			}
				
		}else{
		
			dataManImportDataPlotCanvas.setDoScalex(1);		
			dataManImportDataPlotCanvas.setPlotState();
			
		}
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			try{
		
				if((YminField.getText().equals("") || YmaxField.getText().equals(""))){

					string = "One or more fields for the S-factor range are empty. \nPlease enter numeric values.";

					createExceptionDialog(string, this);
					
				}else if((Double.valueOf(YminField.getText()).doubleValue() >= Double.valueOf(YmaxField.getText()).doubleValue())){

					string = "S-factor minimum must be less than the S-factor maximum.";
					createExceptionDialog(string, this);
			
				}else{
					
					dataManImportDataPlotCanvas.setYmin(Double.valueOf(YminField.getText()).doubleValue());
					dataManImportDataPlotCanvas.setYmax(Double.valueOf(YmaxField.getText()).doubleValue());
					
					dataManImportDataPlotCanvas.setPlotState();

				}
				
			}catch(NumberFormatException nfe){
					
				string = "Values for S-factor minimum and S-factor maximum must be numeric values.";		
				createExceptionDialog(string, this);
					
			}
			
		
		}
		

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){

		if(ie.getSource()==EBox){
		
			if(EBox.isSelected()){
				
				EminField.setEditable(false);
				EmaxField.setEditable(false);
				
				EminField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmin()));
				EmaxField.setText(String.valueOf(ds.getImportNucDataDataStructure().getXmax()));
				
			}else{
			
				EminField.setEditable(true);
				EmaxField.setEditable(true);
			
			}
			
			redrawPlot();
			
		}else if(ie.getSource()==YminComboBox || ie.getSource()==YmaxComboBox){
		
			if(Integer.valueOf(YminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(YmaxComboBox.getSelectedItem().toString()).doubleValue()){
       			
       			String string = "";
       			
       			if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
       			
       				string = "Cross section minimum must be less than cross section maximum.";
       			
       			}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
       			
       				string = "S factor minimum must be less than S factor maximum.";
       			
       			}
       			
       			createExceptionDialog(string, this);
       			
       			YminComboBox.removeItemListener(this);
       			YminComboBox.setSelectedItem("-10");
       			YminComboBox.addItemListener(this);
       			
       			redrawPlot();
       			
			}else{
			
				redrawPlot();
			
			}
		}
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
		okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		exceptionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		exceptionDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(exceptionDialog);
		
		//Show the dialog
		exceptionDialog.setVisible(true);
	
	}
}

class DataManImportDataPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.dataManFrame.dataManImportDataPlotFrame.dataManImportDataPlotCanvas.paintMe(g);

        return PAGE_EXISTS;
		
	}

}