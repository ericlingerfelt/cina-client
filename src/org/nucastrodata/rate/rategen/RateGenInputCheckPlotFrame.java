package org.nucastrodata.rate.rategen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateGenDataStructure;

import java.awt.print.Printable;
import java.awt.print.PageFormat;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rategen.RateGenInputCheckPlotCanvas;
import org.nucastrodata.rate.rategen.RateGenInputCheckPlotPrintable;
import org.nucastrodata.rate.rategen.RateGenInputCheckTableFrame;


/**
 * The Class RateGenInputCheckPlotFrame.
 */
public class RateGenInputCheckPlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The rate gen input check plot canvas. */
	public RateGenInputCheckPlotCanvas rateGenInputCheckPlotCanvas;
	
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
								, "log S Factor min: "
								, "log S Factor max: "
								, "log Cross Sec min: "
								, "log Cross Sec max: "
								, "to max and min?"};
	
	/** The E box. */
	JCheckBox EBox;
	
	/** The Emax field. */
	JTextField EminField, EmaxField;
	
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
	private RateGenDataStructure ds;
	
	/**
	 * Instantiates a new rate gen input check plot frame.
	 *
	 * @param ds the ds
	 */
	public RateGenInputCheckPlotFrame(RateGenDataStructure ds){
	
		this.ds = ds;
	
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
	
		c = getContentPane();
	
		setSize(530, 673);
	
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
		
		if(ds.getInputType().equals("CS(E)")){
		
			if(ds.getRateDataStructure().getDecay().equals("")){
		
				setTitle("Cross Section vs. Energy Plot: " + ds.getRateDataStructure().getReactionString());
			
			}else{
			
				setTitle("Cross Section vs. Energy Plot: " 
							+ ds.getRateDataStructure().getReactionString()
							+ " ["
							+ ds.getRateDataStructure().getDecay()
							+ "]");
			
			}
		
		}else if(ds.getInputType().equals("S(E)")){
			
			if(ds.getRateDataStructure().getDecay().equals("")){
		
				setTitle("S Factor vs. Energy Plot: " + ds.getRateDataStructure().getReactionString());
				
			}else{
				
				setTitle("S Factor vs. Energy Plot: " 
							+ ds.getRateDataStructure().getReactionString()
							+ " ["
							+ ds.getRateDataStructure().getDecay()
							+ "]");
				
			}
		
		}
		
		if(ds.getInputType().equals("CS(E)")){
		
			labelArray[4].setText(stringArray[6]);
			labelArray[5].setText(stringArray[7]);	
		
		}else if(ds.getInputType().equals("S(E)")){
		
			labelArray[4].setText(stringArray[4]);
			labelArray[5].setText(stringArray[5]);
			
		}
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YmaxComboBox.removeItemListener(this);
		YminComboBox.removeItemListener(this);
		
		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
        YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

        YmaxComboBox.addItemListener(this);
		YminComboBox.addItemListener(this);
		
		
	
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
	
		rateGenInputCheckPlotCanvas = new RateGenInputCheckPlotCanvas(YminComboBoxInit
																		, YmaxComboBoxInit
																		, ds);
		
		rateGenInputCheckPlotCanvas.setPreferredSize(rateGenInputCheckPlotCanvas.getSize());
		
		rateGenInputCheckPlotCanvas.revalidate();
		
		sp = new JScrollPane(rateGenInputCheckPlotCanvas);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateGenInputCheckPlotCanvas);
		
		sp.setViewport(vp);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		rateGenInputCheckPlotCanvas.setPreferredSize(rateGenInputCheckPlotCanvas.getSize());
		
		rateGenInputCheckPlotCanvas.revalidate();
	
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
		
		redrawButton = new JButton("Redraw");
		redrawButton.setFont(Fonts.buttonFont);
		redrawButton.addActionListener(this);
		
		applyButton = new JButton("Apply");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		applyButton.setEnabled(false);
		
		tableButton = new JButton("Table");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);
				
		//Create Labels/////////////////////////////////////////////////LABELS////////
		labelArray[0] = new JLabel(stringArray[0] + " " + stringArray[8]);
		labelArray[0].setFont(Fonts.textFont);
		
		labelArray[1] = new JLabel(stringArray[1]);
		labelArray[1].setFont(Fonts.textFont);
		
		labelArray[2] = new JLabel(stringArray[2]);
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3] = new JLabel(stringArray[3]);
		labelArray[3].setFont(Fonts.textFont);
		
		if(ds.getInputType().equals("CS(E)")){
		
			labelArray[4] = new JLabel(stringArray[6]);
			labelArray[4].setFont(Fonts.textFont);
			
			labelArray[5] = new JLabel(stringArray[7]);
			labelArray[5].setFont(Fonts.textFont);	
		
		}else if(ds.getInputType().equals("S(E)")){
		
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
		EminField.setText(String.valueOf(ds.getEmin()));
		
		EmaxField = new JTextField(5);
		EmaxField.setFont(Fonts.textFont);
		EmaxField.setEditable(false);
		EmaxField.setText(String.valueOf(ds.getEmax()));
		
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
	
		buttonPanel.add(printButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(tableButton);
	
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
		
		if(ds.getInputType().equals("S(E)")){
		
			currentYmin = ds.getSFactormin();
		
			min = (int)Math.floor(0.434294482*Math.log(currentYmin));
		
			if(min < -10){
			
				min = -10;
			
			}
		
		}else if(ds.getInputType().equals("CS(E)")){
		
			currentYmin = ds.getCrossSectionmin();
		
			min = (int)Math.floor(0.434294482*Math.log(currentYmin));
		
			if(min < -10){
			
				min = -10;
			
			}
		
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
		
		if(ds.getInputType().equals("S(E)")){
		
			currentYmax = ds.getSFactormax();
		
			max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
		
			if(max > 10){
			
				max = 10;
			
			}
		
		}else if(ds.getInputType().equals("CS(E)")){
		
			currentYmax = ds.getCrossSectionmax();
		
			max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
		
			if(max > 10){
			
				max = 10;
			
			}
		
		}

		return max;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateGenInputCheckPlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(rateGenInputCheckPlotCanvas, this);
			
		}else if(ae.getSource()==applyButton){
			
			redrawPlot();
			
		}else if(ae.getSource()==tableButton){
			
			if(Cina.rateGenFrame.rateGenInputCheckTableFrame==null){
			
				Cina.rateGenFrame.rateGenInputCheckTableFrame = new RateGenInputCheckTableFrame(ds);
			
			}else{
				
				Cina.rateGenFrame.rateGenInputCheckTableFrame.setTableText();
				Cina.rateGenFrame.rateGenInputCheckTableFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==okButton){
		
			exceptionDialog.setVisible(false);
			exceptionDialog.dispose();
				
			EminField.setText(String.valueOf(ds.getEmin()));
			EmaxField.setText(String.valueOf(ds.getEmax()));
			
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
					
					rateGenInputCheckPlotCanvas.setXmin(Double.valueOf(EminField.getText()).doubleValue());
					rateGenInputCheckPlotCanvas.setXmax(Double.valueOf(EmaxField.getText()).doubleValue());
				
					rateGenInputCheckPlotCanvas.setDoScalex(0);
					
					rateGenInputCheckPlotCanvas.setPlotState();

				}
				
			}catch(NumberFormatException nfe){
					
				string = "Values for Emin and Emax must be numeric values.";		
				createExceptionDialog(string, this);
					
			}
				
		}else{
		
			rateGenInputCheckPlotCanvas.setDoScalex(1);		
			rateGenInputCheckPlotCanvas.setPlotState();
			
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
				
				EminField.setText(String.valueOf(ds.getEmin()));
				EmaxField.setText(String.valueOf(ds.getEmax()));

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
       			
       			if(ds.getInputType().equals("CS(E)")){
       			
       				string = "Cross section minimum must be less than cross section maximum.";
       			
       			}else if(ds.getInputType().equals("S(E)")){
       			
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
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){return Double.valueOf((String)XminComboBox.getSelectedItem()).doubleValue();} 
	
	/**
	 * Gets the xmax.
	 *
	 * @return the xmax
	 */
	public double getXmax(){return Double.valueOf((String)XmaxComboBox.getSelectedItem()).doubleValue();}
	
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

class RateGenInputCheckPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.rateGenFrame.rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.paintMe(g);

        return PAGE_EXISTS;
		
	}

}