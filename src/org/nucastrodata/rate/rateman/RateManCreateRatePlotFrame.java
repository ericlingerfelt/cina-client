package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import java.io.*;
import java.awt.print.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateman.RateManCreateRatePlotCanvas;
import org.nucastrodata.rate.rateman.RateManCreateRatePlotPrintable;


/**
 * The Class RateManCreateRatePlotFrame.
 */
public class RateManCreateRatePlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The rate man create rate plot canvas. */
	RateManCreateRatePlotCanvas rateManCreateRatePlotCanvas;
	
	/** The redraw button. */
	JButton printButton, saveButton, redrawButton;
	
	/** The label array. */
	JLabel[] labelArray = new JLabel[6];
	
	/** The string array. */
	String[] stringArray = {"log Temp min(T9): "
									, "log Temp max(T9): "
									, "log Rate min: "
									, "log Rate max: "
									, "Temperature: "
									, "Reaction Rate: "};
	
	/** The tmax combo box. */
	JComboBox rminComboBox, rmaxComboBox, tminComboBox, tmaxComboBox;
	
	/** The minor r box. */
	JCheckBox majorTBox, minorTBox, majorRBox, minorRBox;
	
	/** The grid panel. */
	JPanel formatPanel, buttonPanel, scalePanel, gridPanel;

	/** The toolkit. */
	Toolkit toolkit;
    
    /** The tmin combo box init. */
    int tminComboBoxInit;
    
    /** The tmax combo box init. */
    int tmaxComboBoxInit;
    
    /** The rmin combo box init. */
    int rminComboBoxInit;
    
    /** The rmax combo box init. */
    int rmaxComboBoxInit;
	
	/** The c. */
	Container c;
	
	/** The sp. */
	JScrollPane sp;

	/** The ds. */
	private RateManDataStructure ds;

	/**
	 * Instantiates a new rate man create rate plot frame.
	 *
	 * @param ds the ds
	 */
	public RateManCreateRatePlotFrame(RateManDataStructure ds){
	
		this.ds = ds;
	
		rminComboBoxInit = setInitialrminComboBoxInit();
		rmaxComboBoxInit = setInitialrmaxComboBoxInit();
	
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
	
		c = getContentPane();
	
		setSize(530, 691);
		
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
	
		if(ds.getCreateRateDataStructure().getDecay().equals("")){
	
			setTitle("Create New Rate Plot: " + ds.getCreateRateDataStructure().getReactionString());
	
		}else{
		
			setTitle("Create New Rate Plot: " 
						+ ds.getCreateRateDataStructure().getReactionString()
						+ " ["
						+ ds.getCreateRateDataStructure().getDecay()
						+ "]");
		
		}
	
		rminComboBoxInit = setInitialrminComboBoxInit();
		rmaxComboBoxInit = setInitialrmaxComboBoxInit();
	
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
	
		rminComboBox.setSelectedItem(String.valueOf(rminComboBoxInit));
        rmaxComboBox.setSelectedItem(String.valueOf(rmaxComboBoxInit));

        tminComboBox.setSelectedItem(String.valueOf(tminComboBoxInit));
        tmaxComboBox.setSelectedItem(String.valueOf(tmaxComboBoxInit));
        
        
	
	}
	
	//Window closing listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {  
		
		Cina.rateManFrame.rateManCreateRate3Panel.plotButton.setText("<html>Open Plotting<p>Interface</html>");
	
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
	
		rateManCreateRatePlotCanvas = new RateManCreateRatePlotCanvas(rminComboBoxInit 
																	, rmaxComboBoxInit
																	, tminComboBoxInit
																	, tmaxComboBoxInit
																	, ds);
		
		rateManCreateRatePlotCanvas.setPreferredSize(rateManCreateRatePlotCanvas.getSize());
		
		rateManCreateRatePlotCanvas.revalidate();
		
		sp = new JScrollPane(rateManCreateRatePlotCanvas);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateManCreateRatePlotCanvas);
		
		sp.setBackground(Color.white);
		
		sp.setViewport(vp);
		
		c.add(sp, BorderLayout.CENTER);
		
		setVisible(true);
		
		rateManCreateRatePlotCanvas.setPreferredSize(rateManCreateRatePlotCanvas.getSize());
		
		rateManCreateRatePlotCanvas.revalidate();
	
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
		
		scalePanel = new JPanel(new GridLayout(2, 4, 5, 2));	
		
		gridPanel = new JPanel(new GridLayout(2, 3, 5, 2));
		
		//Create Check boxes////////////////////////////////////////////CHECKBOXES////////////
		majorTBox = new JCheckBox("Major Gridlines", true);
		majorTBox.addItemListener(this);
		majorTBox.setFont(Fonts.textFont);
		
		minorTBox = new JCheckBox("Minor Gridlines", true);
		minorTBox.addItemListener(this);
		minorTBox.setFont(Fonts.textFont);
		
		majorRBox = new JCheckBox("Major Gridlines", true);
		majorRBox.addItemListener(this);
		majorRBox.setFont(Fonts.textFont);
		
		minorRBox = new JCheckBox("Minor Gridlines", false);
		minorRBox.addItemListener(this);
		minorRBox.setFont(Fonts.textFont);
		
		//Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
		rminComboBox = new JComboBox();
        rminComboBox.setFont(Fonts.textFont);
        for(int i=12; i>=-31; i--) { rminComboBox.addItem(Integer.toString(i));}
        rminComboBox.setSelectedItem(String.valueOf(rminComboBoxInit));
		rminComboBox.addItemListener(this);
		
        rmaxComboBox = new JComboBox();
        rmaxComboBox.setFont(Fonts.textFont);
        for(int i=13; i>=-30; i--) { rmaxComboBox.addItem(Integer.toString(i));}
        rmaxComboBox.setSelectedItem(String.valueOf(rmaxComboBoxInit));
		rmaxComboBox.addItemListener(this);
		
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
		
		redrawButton = new JButton("Redraw");
		redrawButton.setFont(Fonts.buttonFont);
		redrawButton.addActionListener(this);
	
		buttonPanel.add(printButton);
		buttonPanel.add(saveButton);
		//buttonPanel.add(redrawButton);
	
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
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		scalePanel.add(labelArray[2]);
		
		gbc.gridx = 1; 
		gbc.gridy = 1;
		
		scalePanel.add(rminComboBox);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		
		scalePanel.add(labelArray[3]);
		
		gbc.gridx = 3; 
		gbc.gridy = 1;
		
		scalePanel.add(rmaxComboBox);
		
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
		
		gridPanel.add(majorRBox);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		
		gridPanel.add(minorRBox);
	
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
	 * Sets the initialrmin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialrminComboBoxInit(){
	
		int min = 0;
		
		double currentrmin = 0.0;
		
		currentrmin = ds.getCreateRateDataStructure().getRatemin();

		min = (int)currentrmin;
				
		if(min < -31){
			
			min = -31;
		
		}
		
		return min;
	
	}
	
	/**
	 * Sets the initialrmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialrmaxComboBoxInit(){
	
		int max = 0;
		
		double currentrmax = 0.0;
	
		currentrmax = ds.getCreateRateDataStructure().getRatemax();
	
		max = (int)currentrmax;
		
		if(max > 13){
			
			max = 13;
		
		}
		
		return max;
	
	}
	
	/**
	 * Sets the initialtmin combo box init.
	 *
	 * @return the int
	 */
	public int setInitialtminComboBoxInit(){
	
		int min = 0;
		
		double currenttmin = 0.0;
		
		currenttmin = ds.getCreateRateDataStructure().getTempmin();

		min = (int)currenttmin;
	
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
	
		currenttmax = ds.getCreateRateDataStructure().getTempmax();

		max = (int)currenttmax;

		return max;
	
	}
		
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new RateManCreateRatePlotPrintable(), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(rateManCreateRatePlotCanvas, this);
			
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
		
		if(Integer.valueOf(rminComboBox.getSelectedItem().toString()).intValue() 
					>= Integer.valueOf(rmaxComboBox.getSelectedItem().toString()).intValue()){
			
				String string = "Rate minimum must be less than rate maximum.";
				
				Dialogs.createExceptionDialog(string, this);			
				
		}else if(Integer.valueOf(tminComboBox.getSelectedItem().toString()).intValue() 
					>= Integer.valueOf(tmaxComboBox.getSelectedItem().toString()).intValue()){
		
			String string = "Temperature minimum must be less than temperature maximum.";
			
			Dialogs.createExceptionDialog(string, this);
		
		}else{
		
			rateManCreateRatePlotCanvas.setPlotState();
			rateManCreateRatePlotCanvas.repaint();
		
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
	 * Gets the rmin.
	 *
	 * @return the rmin
	 */
	public double getRmin(){return Double.valueOf((String)rminComboBox.getSelectedItem()).doubleValue();}
	
	/**
	 * Gets the rmax.
	 *
	 * @return the rmax
	 */
	public double getRmax(){return Double.valueOf((String)rmaxComboBox.getSelectedItem()).doubleValue();}
	
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
	 * Gets the minor r.
	 *
	 * @return the minor r
	 */
	public boolean getMinorR(){return minorRBox.isSelected();} 
	
	/**
	 * Gets the major r.
	 *
	 * @return the major r
	 */
	public boolean getMajorR(){return majorRBox.isSelected();} 
	
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
	
		}else if(ie.getSource()==majorRBox){
		
			if(majorRBox.isSelected()){
				
				minorRBox.setEnabled(true);
				
			}else{
				minorRBox.setSelected(false);
				minorRBox.setEnabled(false);
			}
			
			redrawPlot();
			
		}else if(ie.getSource()==minorTBox || ie.getSource()==minorRBox){
			
			redrawPlot();
			
		}else if(ie.getSource()==rminComboBox || ie.getSource()==rmaxComboBox || ie.getSource()==tminComboBox || ie.getSource()==tmaxComboBox){
		
			if(Integer.valueOf(rminComboBox.getSelectedItem().toString()).doubleValue()
       				>= Integer.valueOf(rmaxComboBox.getSelectedItem().toString()).doubleValue()){
       		
       			String string = "Rate minimum must be less than rate maximum.";
       			
       			Dialogs.createExceptionDialog(string, this);
       			
       			rminComboBox.setSelectedItem("-31");
       		
   			}else if(Integer.valueOf(tminComboBox.getSelectedItem().toString()).doubleValue()
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

class RateManCreateRatePlotPrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		
		Cina.rateManFrame.rateManCreateRatePlotFrame.rateManCreateRatePlotCanvas.paintMe(g);
		
        return PAGE_EXISTS;
		
	}

}