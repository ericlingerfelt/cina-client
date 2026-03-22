package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.awt.print.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.rate.rateman.RateManInvestRatePlotFramePrintable;
import org.nucastrodata.rate.rateman.RateManInvestRatePlotListPanel;
import org.nucastrodata.rate.rateman.RateManInvestRatePlotPanel;
import org.nucastrodata.rate.rateman.RateManInvestRateTableFrame;


/**
 * The Class RateManInvestRatePlotFrame.
 */
public class RateManInvestRatePlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener{
    
    /** The units. */
    String[] units = {"", " reactions/sec", " reactions/sec", " reactions/sec", " cm^3/(mole*s)"
                                , " cm^3/(mole*s)", " cm^3/(mole*s)", " cm^3/(mole*s)"
                                , " cm^6/(mole^2 * s)"};
   
    /** The toolkit. */
    Toolkit toolkit;

    /** The panel5. */
    JPanel panel4, panel5;
    
    /** The tmax combo box. */
    JComboBox rminComboBox, rmaxComboBox, tminComboBox, tmaxComboBox;
    
    /** The button group. */
    ButtonGroup buttonGroup = new ButtonGroup();
    
    /** The temp radio button array. */
    JRadioButton[] tempRadioButtonArray = new JRadioButton[2];

    /** The rate man invest rate plot list panel. */
    RateManInvestRatePlotListPanel rateManInvestRatePlotListPanel;

    /** The rate man invest rate plot panel. */
    RateManInvestRatePlotPanel rateManInvestRatePlotPanel;
    
    /** The temp rate button. */
    JButton printButton, saveButton, tempRateButton;
    
    /** The subtitle field. */
    JTextField titleField, subtitleField;
    
    /** The minor temp check box. */
    JCheckBox majorRateCheckBox, majorTempCheckBox, minorRateCheckBox, minorTempCheckBox;

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
    
    /** The sp1. */
    JScrollPane sp, sp1;

    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The ds. */
    private RateManDataStructure ds;
    
    /**
     * Instantiates a new rate man invest rate plot frame.
     *
     * @param ds the ds
     */
    public RateManInvestRatePlotFrame(RateManDataStructure ds){
	
		this.ds = ds;
	
		rminComboBoxInit = setInitialrminComboBoxInit();
		rmaxComboBoxInit = setInitialrmaxComboBoxInit();
	
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
	
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
	
		rminComboBoxInit = setInitialrminComboBoxInit();
		rmaxComboBoxInit = setInitialrmaxComboBoxInit();
	
		tminComboBoxInit = setInitialtminComboBoxInit();
		tmaxComboBoxInit = setInitialtmaxComboBoxInit();
	
		rminComboBox.removeItemListener(this);
		rmaxComboBox.removeItemListener(this);
		
		tminComboBox.removeItemListener(this);
		tmaxComboBox.removeItemListener(this);
		
		rminComboBox.setSelectedItem(String.valueOf(rminComboBoxInit));
        rmaxComboBox.setSelectedItem(String.valueOf(rmaxComboBoxInit));

        tminComboBox.setSelectedItem(String.valueOf(tminComboBoxInit));
        tmaxComboBox.setSelectedItem(String.valueOf(tmaxComboBoxInit));
        
        rminComboBox.addItemListener(this);
		rmaxComboBox.addItemListener(this);
		
		tminComboBox.addItemListener(this);
		tmaxComboBox.addItemListener(this);
        
        
	
	}
    
    /**
     * Creates the reaction list panel.
     */
    public void createReactionListPanel(){
    
    	JPanel reactionListPanel = new JPanel();
        reactionListPanel.setLayout(new GridBagLayout());
		
		rateManInvestRatePlotListPanel = new RateManInvestRatePlotListPanel(ds);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);  
        
        reactionListPanel.add(rateManInvestRatePlotListPanel, gbc);   
          
        sp = new JScrollPane(rateManInvestRatePlotListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    /**
     * Sets the reaction list panel.
     */
    public void setReactionListPanel(){

    	rateManInvestRatePlotListPanel.initialize();
    
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

        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tempRateButton = new JButton("Table of Points");
        tempRateButton.setFont(Fonts.buttonFont);
        tempRateButton.addActionListener(this);
        tempRateButton.setEnabled(true);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(tempRateButton);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        
        JLabel tempLabel = new JLabel("Temperature");
        tempLabel.setFont(Fonts.textFont);
        JLabel rminLabel = new JLabel("log Rate min");
        rminLabel.setFont(Fonts.textFont);
        JLabel rmaxLabel = new JLabel("log Rate max");
        rmaxLabel.setFont(Fonts.textFont);
        JLabel TminLabel = new JLabel("log Temp min");
        TminLabel.setFont(Fonts.textFont);
        JLabel TmaxLabel = new JLabel("log Temp max");
        TmaxLabel.setFont(Fonts.textFont);

		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");

        tempRadioButtonArray[0] = new JRadioButton("log");
        tempRadioButtonArray[0].setFont(Fonts.textFont);
        tempRadioButtonArray[1] = new JRadioButton("lin");
		tempRadioButtonArray[1].setFont(Fonts.textFont);	
			
		buttonGroup.add(tempRadioButtonArray[0]);
		buttonGroup.add(tempRadioButtonArray[1]);

        tempRadioButtonArray[0].setSelected(true);

        tempRadioButtonArray[0].addItemListener(this);
        tempRadioButtonArray[1].addItemListener(this);

        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
		rminComboBox = new JComboBox();
        rminComboBox.setFont(Fonts.textFont);
        for(int i=12; i>=-31; i--) {rminComboBox.addItem(Integer.toString(i));}
        rminComboBox.setSelectedItem(String.valueOf(rminComboBoxInit));
		rminComboBox.addItemListener(this);
		
        rmaxComboBox = new JComboBox();
        rmaxComboBox.setFont(Fonts.textFont);
        for(int i=13; i>=-30; i--) {rmaxComboBox.addItem(Integer.toString(i));}
        rmaxComboBox.setSelectedItem(String.valueOf(rmaxComboBoxInit));
		rmaxComboBox.addItemListener(this);
		
        tminComboBox = new JComboBox();
        tminComboBox.setFont(Fonts.textFont);
        for(int i=-2; i<=0; i++) {tminComboBox.addItem(Integer.toString(i));}
        tminComboBox.setSelectedItem(String.valueOf(tminComboBoxInit));
		tminComboBox.addItemListener(this);

        tmaxComboBox = new JComboBox();
        tmaxComboBox.setFont(Fonts.textFont);
        for(int i=-1; i<=1; i++) {tmaxComboBox.addItem(Integer.toString(i));}
        tmaxComboBox.setSelectedItem(String.valueOf(tmaxComboBoxInit));
		tmaxComboBox.addItemListener(this);
        
        JLabel tempGridLabel = new JLabel("Temperature");
        tempGridLabel.setFont(Fonts.textFont);
        
        JLabel rateGridLabel = new JLabel("Rate");
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

  		///////////////////////////////////////////////PUT IT ALL TOGETHER//////////////////////////////////////////////////////////////////
  		
  		gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 9;
  		controlPanel.add(plotControlsLabel, gbc);	
  			
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
  		controlPanel.add(rminComboBox, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(rmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(rmaxComboBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
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
  		controlPanel.add(tminComboBox, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(TmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(tmaxComboBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 9;
  		controlPanel.add(buttonPanel, gbc);

        c.add(controlPanel, BorderLayout.SOUTH);
             
    }
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    
    	rateManInvestRatePlotPanel = new RateManInvestRatePlotPanel(rminComboBoxInit 
													, rmaxComboBoxInit
													, tminComboBoxInit
													, tmaxComboBoxInit
													, ds);
		
		rateManInvestRatePlotPanel.setPreferredSize(rateManInvestRatePlotPanel.getSize());
		
		rateManInvestRatePlotPanel.revalidate();
		
		sp1 = new JScrollPane(rateManInvestRatePlotPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(rateManInvestRatePlotPanel);
		
		sp1.setBackground(Color.white);
		
		sp1.setViewport(vp);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	
       	jsp.setDividerLocation(500);
       	
       	c.add(jsp, BorderLayout.CENTER);
		
		setVisible(true);
		
		rateManInvestRatePlotPanel.setPreferredSize(rateManInvestRatePlotPanel.getSize());
		
		rateManInvestRatePlotPanel.revalidate();
    	
    }
    
    /**
     * Sets the initialrmin combo box init.
     *
     * @return the int
     */
    public int setInitialrminComboBoxInit(){
	
		int min = 0;

		min = ds.getRatemin();
		
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
		
		max = ds.getRatemax();
		
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
		
		//double currenttmin = 0.0;
		
		//currenttmin = Cina.rateViewerDataStructure.getTempmin();
		
		//min = (int)Math.floor(0.434294482*Math.log(currenttmin));
		
		return -2;
	
	}
	
	/**
	 * Sets the initialtmax combo box init.
	 *
	 * @return the int
	 */
	public int setInitialtmaxComboBoxInit(){
	
		int max = 0;
		
		//double currenttmax = 0.0;
	
		//currenttmax = Cina.rateViewerDataStructure.getTempmax();
		
		//max = (int)Math.ceil(0.434294482*Math.log(currenttmax));
		
		return 1;
	
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
   			
				tminComboBox.setSelectedItem("7");
   			
   			}else{
   			
   				redrawPlot();
   			
   			}

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
    
    	rateManInvestRatePlotPanel.setPreferredSize(rateManInvestRatePlotPanel.getSize());
		
		rateManInvestRatePlotPanel.setPlotState();
   		
   		rateManInvestRatePlotPanel.repaint();

    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    
    	boolean noneChecked = true;
    	
    	for(int i=0; i<Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotListPanel.checkBoxArray.length; i++){
    	
    		if(Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotListPanel.checkBoxArray[i].isSelected()){
    		
    			noneChecked = false;
    		
    		}
    	
    	}
    	
    	return noneChecked;
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
       if(ae.getSource()==tempRateButton){
       	
       		if(!noneChecked()){
       	
	       		if(Cina.rateManFrame.rateManInvestRateTableFrame==null){
	       	
	       			Cina.rateManFrame.rateManInvestRateTableFrame = new RateManInvestRateTableFrame(ds);
	       		
		       	}else{
		       	
		       		Cina.rateManFrame.rateManInvestRateTableFrame.setTableText();
		       		Cina.rateManFrame.rateManInvestRateTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{
	       	
	       		String string = "Please select reaction rates for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new RateManInvestRatePlotFramePrintable(), this);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(rateManInvestRatePlotPanel, this);
       			
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


class RateManInvestRatePlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.rateManFrame.rateManInvestRatePlotFrame.rateManInvestRatePlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}