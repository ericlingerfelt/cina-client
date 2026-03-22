package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPlotCanvas;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPlotPrintable;
import org.nucastrodata.rate.rateparam.RateParamInputCheckTableFrame;


/**
 * The Class RateParamInputCheckPlotFrame.
 */
public class RateParamInputCheckPlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The rate param input check plot canvas. */
	public RateParamInputCheckPlotCanvas rateParamInputCheckPlotCanvas;
	
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
	String[] stringArray = {"Autoscale Temp axis"
								,"Set Scale"
								, "log Temp min(T9): " 
								, "log Temp max(T9): "
								, "log Rate min: "
								, "log Rate max: "
								, "log Cross Sec min: "
								, "log Cross Sec max: "
								, "to max and min?"};
	
	/** The Ymin combo box init. */
	int YminComboBoxInit = 0;
    
    /** The Ymax combo box init. */
    int YmaxComboBoxInit = 0;
	
	/** The Xmin combo box init. */
	int XminComboBoxInit = 0;
    
    /** The Xmax combo box init. */
    int XmaxComboBoxInit = 0;
	
	/** The c. */
	Container c;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The exception dialog. */
	JDialog exceptionDialog;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param input check plot frame.
	 *
	 * @param ds the ds
	 */
	public RateParamInputCheckPlotFrame(RateParamDataStructure ds){
	
		this.ds = ds;
	
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
	
		XminComboBoxInit = setInitialXminComboBoxInit();
		XmaxComboBoxInit = setInitialXmaxComboBoxInit();
	
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
		
		if(ds.getRateDataStructure().getDecay().equals("")){
	
			setTitle("Rate vs. Temperature Plot: " + ds.getRateDataStructure().getReactionString());
			
		}else{
		
			setTitle("Rate vs. Temperature Plot: " 
						+ ds.getRateDataStructure().getReactionString()
						+ " ["
						+ ds.getRateDataStructure().getDecay()
						+ "]");
		
		}
		
		labelArray[4].setText(stringArray[4]);
		labelArray[5].setText(stringArray[5]);
		
		YminComboBoxInit = setInitialYminComboBoxInit();
		YmaxComboBoxInit = setInitialYmaxComboBoxInit();
		
		YmaxComboBox.removeItemListener(this);
		YminComboBox.removeItemListener(this);
		
		YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
        YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));

        YmaxComboBox.addItemListener(this);
		YminComboBox.addItemListener(this);
		
		XminComboBoxInit = setInitialXminComboBoxInit();
		XmaxComboBoxInit = setInitialXmaxComboBoxInit();
		
		XmaxComboBox.removeItemListener(this);
		XminComboBox.removeItemListener(this);
		
		XminComboBox.setSelectedItem(String.valueOf(XminComboBoxInit));
        XmaxComboBox.setSelectedItem(String.valueOf(XmaxComboBoxInit));

        XmaxComboBox.addItemListener(this);
		XminComboBox.addItemListener(this);
		
		
	
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
	
		rateParamInputCheckPlotCanvas = new RateParamInputCheckPlotCanvas(YminComboBoxInit
																		, YmaxComboBoxInit
																		, XminComboBoxInit
																		, XmaxComboBoxInit
																		, ds);
		
		rateParamInputCheckPlotCanvas.setPreferredSize(rateParamInputCheckPlotCanvas.getSize());
		
		rateParamInputCheckPlotCanvas.revalidate();
		
		sp = new JScrollPane(rateParamInputCheckPlotCanvas);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateParamInputCheckPlotCanvas);
		
		sp.setViewport(vp);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		rateParamInputCheckPlotCanvas.setPreferredSize(rateParamInputCheckPlotCanvas.getSize());
		
		rateParamInputCheckPlotCanvas.revalidate();
	
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
        for(int i=12; i>=-31; i--) { YminComboBox.addItem(Integer.toString(i));}
        YminComboBox.setSelectedItem(String.valueOf(YminComboBoxInit));
		YminComboBox.addItemListener(this);
		
        YmaxComboBox = new JComboBox();
        YmaxComboBox.setFont(Fonts.textFont);
        for(int i=13; i>=-30; i--) { YmaxComboBox.addItem(Integer.toString(i));}
        YmaxComboBox.setSelectedItem(String.valueOf(YmaxComboBoxInit));
        YmaxComboBox.addItemListener(this);
        
        XminComboBox = new JComboBox();
        XminComboBox.setFont(Fonts.textFont);
        for(int i=-5; i<=1; i++) { XminComboBox.addItem(Integer.toString(i));}
        XminComboBox.setSelectedItem(String.valueOf(XminComboBoxInit));
		XminComboBox.addItemListener(this);
		
        XmaxComboBox = new JComboBox();
        XmaxComboBox.setFont(Fonts.textFont);
        for(int i=-4; i<=2; i++) { XmaxComboBox.addItem(Integer.toString(i));}
        XmaxComboBox.setSelectedItem(String.valueOf(XmaxComboBoxInit));
		XmaxComboBox.addItemListener(this);
		
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
		labelArray[0].setFont(Fonts.titleFont);
		
		labelArray[1] = new JLabel(stringArray[1]);
		labelArray[1].setFont(Fonts.titleFont);
		
		labelArray[2] = new JLabel(stringArray[2]);
		labelArray[2].setFont(Fonts.textFont);
		
		labelArray[3] = new JLabel(stringArray[3]);
		labelArray[3].setFont(Fonts.textFont);

		labelArray[4] = new JLabel(stringArray[4]);
		labelArray[4].setFont(Fonts.textFont);
		
		labelArray[5] = new JLabel(stringArray[5]);
		labelArray[5].setFont(Fonts.textFont);
		
		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");

		buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
		
		setScalePanel = new JPanel(new GridBagLayout());
		
		scaleTitlePanel = new JPanel(new GridBagLayout());
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(2, 0, 2, 0);
		setScalePanel.add(labelArray[2], gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(XminComboBox, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		setScalePanel.add(labelArray[3], gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		setScalePanel.add(XmaxComboBox, gbc);

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
		formatPanel.add(plotControlsLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		formatPanel.add(scaleTitlePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
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
		
		currentYmin = ds.getRatemin();
		
		min = (int)Math.floor(0.434294482*Math.log(currentYmin));
	
		if(min < -31){
		
			min = -31;
		
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
		
		currentYmax = ds.getRatemax();
		
		max = (int)Math.ceil(0.434294482*Math.log(currentYmax));
	
		if(max > 13){
		
			max = 13;
		
		}

		return max;
	
	}
	
	/**
	 * Sets the initial xmin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialXminComboBoxInit(){
	
		int min = 0;
		
		double currentXmin = 0.0;
		
		currentXmin = ds.getTempminRT();
		
		min = (int)Math.floor(0.434294482*Math.log(currentXmin));
		
		return min;
	
	}
	
	/**
	 * Sets the initial xmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialXmaxComboBoxInit(){
	
		int max = 0;
		
		double currentXmax = 0.0;
	
		currentXmax = ds.getTempmaxRT();
		
		max = (int)Math.ceil(0.434294482*Math.log(currentXmax));
		
		return max;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateParamInputCheckPlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(rateParamInputCheckPlotCanvas, this);
			
		}else if(ae.getSource()==applyButton){
			
			redrawPlot();
			
		}else if(ae.getSource()==tableButton){
			
			if(Cina.rateParamFrame.rateParamInputCheckTableFrame==null){
			
				Cina.rateParamFrame.rateParamInputCheckTableFrame = new RateParamInputCheckTableFrame(ds);
			
			}else{
				
				Cina.rateParamFrame.rateParamInputCheckTableFrame.setTableText();
				Cina.rateParamFrame.rateParamInputCheckTableFrame.setVisible(true);
			
			}
			
		}else if(ae.getSource()==okButton){
		
			exceptionDialog.setVisible(false);
			exceptionDialog.dispose();
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
		
		rateParamInputCheckPlotCanvas.setPlotState();

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		
		if(ie.getSource()==YminComboBox || ie.getSource()==YmaxComboBox || ie.getSource()==XminComboBox || ie.getSource()==XmaxComboBox){
	
			if(Integer.valueOf(YminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(YmaxComboBox.getSelectedItem().toString()).doubleValue()){
       		
       			String string = "Rate minimum must be less than rate maximum.";
       			
       			createExceptionDialog(string, this);
       			
       			YminComboBox.removeItemListener(this);
       			YminComboBox.setSelectedItem("-31");
       			YminComboBox.addItemListener(this);
       		
   			}else if(Integer.valueOf(XminComboBox.getSelectedItem().toString()).doubleValue()
   						>= Integer.valueOf(XmaxComboBox.getSelectedItem().toString()).doubleValue()){
   		
   				String string = "Temperature minimum must be less than temperature maximum.";
   				
   				createExceptionDialog(string, this);
   			
   				XminComboBox.removeItemListener(this);
				XminComboBox.setSelectedItem("-5");
   				XminComboBox.addItemListener(this);
   			
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

class RateParamInputCheckPlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.rateParamFrame.rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.paintMe(g);

        return PAGE_EXISTS;
		
	}

}