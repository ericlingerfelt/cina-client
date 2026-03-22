package org.nucastrodata.data.datamass;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.text.*;
import javax.net.ssl.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;
import org.nucastrodata.datastructure.util.MassModelDataStructure;
import org.nucastrodata.io.*;

import org.nucastrodata.io.FileGetter;

import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.data.datamass.DataMassChartColorSettingsFrame;
import org.nucastrodata.data.datamass.DataMassChartPanel;
import org.nucastrodata.data.datamass.DataMassChartPrintPanel;
import org.nucastrodata.data.datamass.DataMassChartTableFrame;
import org.nucastrodata.data.datamass.DataMassIsotopePanel;
import org.nucastrodata.data.datamass.DataMassRainbowPanel;


/**
 * The Class DataMassChartFrame.
 */
public class DataMassChartFrame extends JFrame implements WindowStateListener, ActionListener, ChangeListener, WindowListener, ItemListener{

    /** The sp3. */
    JScrollPane sp, sp2, sp3;
    
    /** The c. */
    Container c;
    
    /** The gbc. */
    GridBagConstraints gbc;

    /** The exp label. */
    JLabel massModelLabel
    				, valueLabel, maxLabel, minLabel
    				, zoomLabel, mouseLabel, typeLabel, quantityLabel
    				, theoryLabel, expLabel;
    				
   	/** The zoom field. */
	   JTextField valueField, maxField, minField, zoomField;
   					
   	/** The show r process check box. */
	   JCheckBox showLabelsCheckBox, showMagicCheckBox, showStableCheckBox, showRProcessCheckBox;
   	
   	/** The quantity combo box. */
	   JComboBox typeComboBox, quantityComboBox;
   	
   	/** The ok button. */
	   JButton saveButton, colorButton, tableButton, okButton;
   	
   	/** The zoom slider. */
	   JSlider zoomSlider;

    /** The data mass chart panel. */
    DataMassChartPanel dataMassChartPanel;
    
    /** The data mass rainbow panel. */
    DataMassRainbowPanel dataMassRainbowPanel;
    
    /** The data mass isotope panel. */
    DataMassIsotopePanel dataMassIsotopePanel;
    
    /** The log constant. */
    double logConstant;
    
    /** The log10. */
    double log10 = 0.434294482;
    
  	/** The x0 r. */
	  double x0R = 0.8;
    
    /** The x0 g. */
    double x0G = 0.6;
    
    /** The x0 b. */
    double x0B = 0.2;
    
    /** The a r. */
    double aR = 0.5;
    
    /** The a g. */
    double aG = 0.4;
    
    /** The a b. */
    double aB = 0.3; 
    
    /** The N ruler. */
    IsotopeRuler ZRuler, NRuler;
    
    /** The black text radio button. */
    JRadioButton whiteTextRadioButton, blackTextRadioButton;
    
    /** The value array. */
    double[] valueArray;
    
    /** The delay dialog. */
    JDialog delayDialog;
    
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The type. */
    int type;
    
    /** The quantity. */
    int quantity;
    
    /** The scheme. */
    String scheme = "";
    
    /** The ds. */
    private DataMassDataStructure ds;
    
    /**
     * Instantiates a new data mass chart frame.
     *
     * @param ds the ds
     */
    public DataMassChartFrame(DataMassDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        c.setLayout(new BorderLayout());

		gbc = new GridBagConstraints();

		setTitle("Interactive Nuclide Chart for Mass Model Evaluations");
		
		setSize(850, 700);
		
		//LABELS//////////////////////////////////////////////LABELS//////////////////////);
		
		mouseLabel = new JLabel("<html>Place the cursor over an isotope<p>to read its value</html>");
		
		typeLabel = new JLabel("Select chart type below: ");
		
		quantityLabel = new JLabel("Select quantity below: ");
		
		theoryLabel = new JLabel("Theoretical Mass Model: ");
		theoryLabel.setFont(Fonts.textFont);
		
		expLabel = new JLabel("Reference Mass Model: ");
		expLabel.setFont(Fonts.textFont);
    	
    	valueLabel = new JLabel("Value: ");
    	valueLabel.setFont(Fonts.textFont);
    	
    	maxLabel = new JLabel("Quantity Max: ");
    	maxLabel.setFont(Fonts.textFont);
    	
    	minLabel = new JLabel("Quantity Min: ");
    	minLabel.setFont(Fonts.textFont);
		
		zoomLabel = new JLabel("Zoom (%): ");
		zoomLabel.setFont(Fonts.textFont);
		
		//COMBOBOX//////////////////////////////////////////////COMBOBOX/////////////////////
		typeComboBox = new JComboBox();
		typeComboBox.addItem("Theoretical Data");
		typeComboBox.addItem("Reference Data");
		typeComboBox.addItem("Difference of Data");
		typeComboBox.addItem("Abs Value of Difference");
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.setSelectedIndex(2);
		typeComboBox.addItemListener(this);
		
		quantityComboBox = new JComboBox();
		quantityComboBox.addItem("Mass Excess");
		quantityComboBox.addItem("S_n");
		quantityComboBox.addItem("S_2n");
		quantityComboBox.addItem("S_p");
		quantityComboBox.addItem("S_2p");
		quantityComboBox.addItem("S_alpha");
		quantityComboBox.addItem("Q_(alpha, n)");
		quantityComboBox.addItem("Q_(alpha, p)");
		quantityComboBox.addItem("Q_(p, n)");
		quantityComboBox.setFont(Fonts.textFont);
		quantityComboBox.addItemListener(this);
		
		//FIELD/////////////////////////////////////////////////FIELDS//////////////////////
   		valueField = new JTextField(10);
   		valueField.setEditable(false);
   		
   		maxField = new JTextField(10);
   		maxField.setEditable(false);
   		
   		minField = new JTextField(10);
   		minField.setEditable(false);
		
		zoomField = new JTextField(3);
		zoomField.setEditable(false);
		
		//CHECKBOX//////////////////////////////////////////////CHECKBOX///////////////////
		showLabelsCheckBox = new JCheckBox("Isotope Labels", false);
		showLabelsCheckBox.setFont(Fonts.textFont);
		showLabelsCheckBox.addItemListener(this);
		
		showMagicCheckBox = new JCheckBox("Magic Numbers", false);
		showMagicCheckBox.setFont(Fonts.textFont);
		showMagicCheckBox.addItemListener(this);
		
		showStableCheckBox = new JCheckBox("Stable Isotopes", false);
		showStableCheckBox.setFont(Fonts.textFont);
		showStableCheckBox.addItemListener(this);
		
		showRProcessCheckBox = new JCheckBox("r-Process Path", false);
		showRProcessCheckBox.setFont(Fonts.textFont);
		showRProcessCheckBox.addItemListener(this);
		
		//SLIDERS///////////////////////////////////////////////SLIDERS////////////////////
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		zoomSlider.addChangeListener(this);
		
		//BUTTONS//////////////////////////////////////////////BUTTONS///////////////////////
		
		saveButton = new JButton("Save Chart as Image");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);
		
		colorButton = new JButton("Set Color Scale Settings");
		colorButton.setFont(Fonts.buttonFont);
		colorButton.addActionListener(this);
		
		dataMassRainbowPanel = new DataMassRainbowPanel(ds);
		sp2 = new JScrollPane(dataMassRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(50, 100));
		
		dataMassIsotopePanel = new DataMassIsotopePanel();
		sp3 = new JScrollPane(dataMassIsotopePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp3.setPreferredSize(new Dimension(200, 30));
			
		//PANELS/////////////////////////////////////////////////////////////////////PANELS//////////////////////////
		JPanel rightPanel = new JPanel(new GridBagLayout());

		//RIGHTPANEL/////////////////////////////////////////////////////////////////RIGHTPANEL//////////////////////
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		rightPanel.add(theoryLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		rightPanel.add(expLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		rightPanel.add(typeLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		rightPanel.add(typeComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		rightPanel.add(quantityLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		rightPanel.add(quantityComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridheight = 4;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(sp2, gbc);
		
		gbc.gridheight = 1;
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(maxLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		rightPanel.add(maxField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		rightPanel.add(minLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		rightPanel.add(minField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(colorButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(mouseLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(sp3, gbc);
			
		gbc.gridx = 0;
		gbc.gridy = 13;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		rightPanel.add(valueLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 13;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(valueField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(showRProcessCheckBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(showLabelsCheckBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 15;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(showMagicCheckBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 15;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(showStableCheckBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 16;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		rightPanel.add(zoomLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 16;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(zoomField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 17;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(zoomSlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 18;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(saveButton, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		
		dataMassChartPanel = new DataMassChartPanel(ds);
		dataMassChartPanel.setCurrentState(2, 0);
		
        sp = new JScrollPane(dataMassChartPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
		JViewport vp = new JViewport();
		
		vp.setView(dataMassChartPanel);
		
		sp.setViewport(vp);
	
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)dataMassChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataMassChartPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(dataMassChartPanel.zmax
								, dataMassChartPanel.nmax
								, dataMassChartPanel.mouseX
								, dataMassChartPanel.mouseY
								, dataMassChartPanel.xoffset
								, dataMassChartPanel.yoffset
								, dataMassChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataMassChartPanel.zmax
								, dataMassChartPanel.nmax
								, dataMassChartPanel.mouseX
								, dataMassChartPanel.mouseY
								, dataMassChartPanel.xoffset
								, dataMassChartPanel.yoffset
								, dataMassChartPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
	
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		c.add(sp, BorderLayout.CENTER);
		c.add(rightPanel, BorderLayout.EAST);

		downloadStableIsotopes();
		downloadRProcessIsotopes();

        addWindowListener(this);
	
		addWindowStateListener(this);

    } 
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==typeComboBox){
    	
    		if(delayDialog==null){
    	
    			openDelayDialog(this);
    		
	  			type = typeComboBox.getSelectedIndex();
	    		quantity = quantityComboBox.getSelectedIndex();
	  
	  			typeComboBox.removeItemListener(this);
	 		 	
	    		final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
					
						initialize(type, quantity);
													
						return new Object();
						
					}
					
					public void finished(){
						
						closeDelayDialog();
						
						typeComboBox.addItemListener(Cina.dataMassFrame.dataMassChartFrame);
					
					}
					
				};
				
				worker.start();
				
			}

    	}else if(ie.getSource()==quantityComboBox){
   
    		if(delayDialog==null){
    	
    			openDelayDialog(this);
  
	  			type = typeComboBox.getSelectedIndex();
	    		quantity = quantityComboBox.getSelectedIndex();
	  
	 		 	quantityComboBox.removeItemListener(this);
	 		 	
	    		final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
					
						initialize(type, quantity);
													
						return new Object();
						
					}
					
					public void finished(){
						
						closeDelayDialog();
						
	 		 			quantityComboBox.addItemListener(Cina.dataMassFrame.dataMassChartFrame);
					
					}
					
				};
				
				worker.start();
				
			}
			
    	}else if(ie.getSource()==showLabelsCheckBox
    				|| ie.getSource()==showMagicCheckBox
    				|| ie.getSource()==showStableCheckBox
    				|| ie.getSource()==showRProcessCheckBox){
    	
    		dataMassChartPanel.repaint();
    	
    	}
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
		if(ae.getSource()==saveButton){
	    
	    	createSaveDialog();
	    
	    }else if(ae.getSource()==okButton){
	    	
	    	saveDialog.setVisible(false);
	    	saveDialog.dispose();
	    
	    	PlotSaver.savePlot(new DataMassChartPrintPanel(whiteTextRadioButton.isSelected(), ds), this);
	    	
	    }else if(ae.getSource()==tableButton){
		
			if(Cina.dataMassFrame.dataMassChartTableFrame==null){
	       	
       			Cina.dataMassFrame.dataMassChartTableFrame = new DataMassChartTableFrame(ds);
       		
	       	}else{
	       	
	       		Cina.dataMassFrame.dataMassChartTableFrame.setTableText();
	       		Cina.dataMassFrame.dataMassChartTableFrame.setVisible(true);
	       	
	       	}
		
		}else if(ae.getSource()==colorButton){
	    
    		if(Cina.dataMassFrame.dataMassChartColorSettingsFrame!=null){
				
				Cina.dataMassFrame.dataMassChartColorSettingsFrame.setCurrentState(scheme);
				Cina.dataMassFrame.dataMassChartColorSettingsFrame.setSize(862, 400);
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame.setVisible(true);

        	}else{
        	
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame = new DataMassChartColorSettingsFrame(ds);
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame.setCurrentState(scheme);
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame.setSize(862, 400);
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame.setVisible(true);
        		Cina.dataMassFrame.dataMassChartColorSettingsFrame.validate();
        		
        			        	
        	}
        	
	    }
    
	}
   
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==zoomSlider){
    		redoChartSize();
    	}

    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowStateListener#windowStateChanged(java.awt.event.WindowEvent)
     */
    public void windowStateChanged(WindowEvent we){
    	redoChartSize();
    }
    
    /**
     * Redo chart size.
     */
    public void redoChartSize(){
    	if(zoomSlider.getValue()==100){
    		
			sp.setColumnHeaderView(ZRuler);
    		sp.setRowHeaderView(NRuler);
		
		}else{
		
			sp.setColumnHeaderView(null);
    		sp.setRowHeaderView(null);
		
		}
		
		double scale = ((double)zoomSlider.getValue())/100.0;
		dataMassChartPanel.boxHeight = (int)(29.0*scale);
		dataMassChartPanel.boxWidth = (int)(29.0*scale);
        
    	dataMassChartPanel.width = (int)(dataMassChartPanel.boxWidth*(dataMassChartPanel.nmax+1));
        dataMassChartPanel.height = (int)(dataMassChartPanel.boxHeight*(dataMassChartPanel.zmax+1));
        
        dataMassChartPanel.xmax = (int)(dataMassChartPanel.xoffset + dataMassChartPanel.width);
        dataMassChartPanel.ymax = (int)(dataMassChartPanel.yoffset + dataMassChartPanel.height);

        if((dataMassChartPanel.xmax+dataMassChartPanel.xoffset)<sp.getSize().width
        		|| (dataMassChartPanel.ymax+dataMassChartPanel.yoffset)<sp.getSize().height){
        	
        	dataMassChartPanel.setSize(sp.getSize().width, sp.getSize().height);
        	
        	
        }else{
        	dataMassChartPanel.setSize(dataMassChartPanel.xmax+dataMassChartPanel.xoffset, dataMassChartPanel.ymax+dataMassChartPanel.yoffset);
        	
        }
        
    			
		dataMassChartPanel.setPreferredSize(dataMassChartPanel.getSize());
		
		dataMassChartPanel.repaint();

		zoomField.setText(String.valueOf(zoomSlider.getValue()));
		
		if(!zoomSlider.getValueIsAdjusting()){
		
    		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		}
		
		sp.setBackground(Color.black);
		sp.validate();

    }
    
    
    /**
     * Initialize.
     *
     * @param type the type
     * @param quantity the quantity
     */
    public void initialize(int type, int quantity){
    
    	zoomField.setText(String.valueOf(zoomSlider.getValue()));

		double[] valueArrayTheory = new double[0];
		double[] valueArrayExp = new double[0];
		double[] valueArrayDiff = new double[0];

		switch(type){
			
			//THEORETICAL
			case 0:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = ds.getTheoryModelDataStructure().getMassArray();

						break;
					
					//N	
					case 1:
					
						valueArray = getValueArray(1, ds.getTheoryModelDataStructure());
					
						break;
					
					//2N	
					case 2:
					
						valueArray = getValueArray(2, ds.getTheoryModelDataStructure());
					
						break;
					
					//P	
					case 3:
					
						valueArray = getValueArray(3, ds.getTheoryModelDataStructure());
					
						break;
						
					//2P	
					case 4:
					
						valueArray = getValueArray(4, ds.getTheoryModelDataStructure());
					
						break;
					
					//ALPHA	
					case 5:
					
						valueArray = getValueArray(5, ds.getTheoryModelDataStructure());
					
						break;
					
					//ALPHA,N
					case 6:
					
						valueArray = getValueArray(6, ds.getTheoryModelDataStructure());
					
						break;
					
					//ALPHA,P	
					case 7:
					
						valueArray = getValueArray(7, ds.getTheoryModelDataStructure());
					
						break;
					
					//P,N	
					case 8:
					
						valueArray = getValueArray(8, ds.getTheoryModelDataStructure());
					
						break;
				
				}
				
				break;
		
			//REFERENCE
			case 1:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = ds.getExpModelDataStructure().getMassArray();
						
						break;
					
					//N	
					case 1:
					
						valueArray = getValueArray(1, ds.getExpModelDataStructure());
					
						break;
					
					//2N	
					case 2:
					
						valueArray = getValueArray(2, ds.getExpModelDataStructure());
					
						break;
					
					//P	
					case 3:
					
						valueArray = getValueArray(3, ds.getExpModelDataStructure());
					
						break;
						
					//2P	
					case 4:
					
						valueArray = getValueArray(4, ds.getExpModelDataStructure());
					
						break;
					
					//ALPHA	
					case 5:
					
						valueArray = getValueArray(5, ds.getExpModelDataStructure());
					
						break;
					
					//ALPHA,N
					case 6:
					
						valueArray = getValueArray(6, ds.getExpModelDataStructure());
					
						break;
					
					//ALPHA,P	
					case 7:
					
						valueArray = getValueArray(7, ds.getExpModelDataStructure());
					
						break;
					
					//P,N	
					case 8:
					
						valueArray = getValueArray(8, ds.getExpModelDataStructure());
					
						break;
				
				}
				
				break;
			
			//DIFFERENCE	
			case 2:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = ds.getDiffArray();
					
						break;
					
					//N	
					case 1:
					
						valueArrayTheory = getValueArray(1, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(1, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
					
					//2N	
					case 2:
					
						valueArrayTheory = getValueArray(2, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(2, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						
						break;
					
					//P	
					case 3:
					
						valueArrayTheory = getValueArray(3, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(3, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
						
					//2P	
					case 4:
					
						valueArrayTheory = getValueArray(4, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(4, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
					
					//ALPHA	
					case 5:
					
						valueArrayTheory = getValueArray(5, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(5, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
					
					//ALPHA,N
					case 6:
					
						valueArrayTheory = getValueArray(6, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(6, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
					
					//ALPHA,P	
					case 7:
					
						valueArrayTheory = getValueArray(7, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(7, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
					
					//P,N	
					case 8:
					
						valueArrayTheory = getValueArray(8, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(8, ds.getExpModelDataStructure());
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
					
						break;
				
				}
				
				break;
			
			//ABS VALUE DIFFERENCE	
			case 3:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = ds.getAbsDiffArray();
						
						break;
					
					//N	
					case 1:
					
						valueArrayTheory = getValueArray(1, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(1, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//2N	
					case 2:
					
						valueArrayTheory = getValueArray(2, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(2, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//P	
					case 3:
					
						valueArrayTheory = getValueArray(3, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(3, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
						
					//2P	
					case 4:
					
						valueArrayTheory = getValueArray(4, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(4, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA	
					case 5:
					
						valueArrayTheory = getValueArray(5, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(5, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA,N
					case 6:
					
						valueArrayTheory = getValueArray(6, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(6, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA,P	
					case 7:
					
						valueArrayTheory = getValueArray(7, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(7, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//P,N	
					case 8:
					
						valueArrayTheory = getValueArray(8, ds.getTheoryModelDataStructure());
						valueArrayExp = getValueArray(8, ds.getExpModelDataStructure());
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, ds.getPointVector());
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
				
				}
				
				break;

		}

		minField.setText(NumberFormats.getFormattedDer(getMinValue()));
		maxField.setText(NumberFormats.getFormattedDer(getMaxValue()));

		theoryLabel.setText("Theoretical Mass Model: "
								+ ds.getTheoryModelDataStructure().getModelName());
								
		expLabel.setText("Reference Mass Model: "
								+ ds.getExpModelDataStructure().getModelName());						

		x0R = ds.getColorValues()[0];
	    x0G = ds.getColorValues()[1];
	   	x0B = ds.getColorValues()[2];
	    aR = ds.getColorValues()[3];
	    aG = ds.getColorValues()[4];
	    aB = ds.getColorValues()[5];
		
		dataMassRainbowPanel.setRGB(ds.getColorValues());

		scheme = ds.getScheme();

		ds.setChartColorArray(getChartColorArray(type, scheme));
		
		dataMassChartPanel.setCurrentState(type, quantity);
		
		dataMassRainbowPanel.setScheme(scheme);
		dataMassRainbowPanel.repaint();
		
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(dataMassChartPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		sp.validate();
		
		ZRuler.setPreferredWidth((int)dataMassChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataMassChartPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(dataMassChartPanel.zmax
								, dataMassChartPanel.nmax
								, dataMassChartPanel.mouseX
								, dataMassChartPanel.mouseY
								, dataMassChartPanel.xoffset
								, dataMassChartPanel.yoffset
								, dataMassChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataMassChartPanel.zmax
								, dataMassChartPanel.nmax
								, dataMassChartPanel.mouseX
								, dataMassChartPanel.mouseY
								, dataMassChartPanel.xoffset
								, dataMassChartPanel.yoffset
								, dataMassChartPanel.crosshairsOn);
	
    }
    
    /**
     * Gets the value array.
     *
     * @param quantity the quantity
     * @param appmmds the appmmds
     * @return the value array
     */
    public double[] getValueArray(int quantity, MassModelDataStructure appmmds){
    
    	double[] outputArray = new double[appmmds.getZNArray().length];
    	
    	Vector ZNVector = new Vector();
    	
    	for(int i=0; i<appmmds.getZNArray().length; i++){
    	
    		ZNVector.addElement(appmmds.getZNArray()[i]);
    	
    	}
    	
    	ZNVector.trimToSize();
    	
    	double M_n = 8.071;
    	double M_H = 7.289;
    	double M_He = 2.425;
    	double M_ZN = 0;
    	int currentZ = 0;
    	int currentN = 0;
    	int index = 0;
    	
    	for(int i=0; i<outputArray.length; i++){
    	
	    	switch(quantity){
	    		
		    	//N	
				case 1:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ, currentN-1));
					double Sn = 1E100;
					
					if(index!=-1){
					
						Sn = appmmds.getMassArray()[index] - M_ZN + M_n;
					
					}
				
					outputArray[i] = Sn;
					
					break;
				
				//2N	
				case 2:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ, currentN-2));
					double S2n = 1E100;
					
					if(index!=-1){
					
						S2n = appmmds.getMassArray()[index] - M_ZN + 2*M_n;
					
					}
				
					outputArray[i] = S2n;
					
					break;
				
				//P	
				case 3:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-1, currentN));
					double Sp = 1E100;
					
					if(index!=-1){
					
						Sp = appmmds.getMassArray()[index] - M_ZN + M_H;
					
					}
				
					outputArray[i] = Sp;
					
					break;
					
				//2P	
				case 4:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-2, currentN));
					double S2p = 1E100;
					
					if(index!=-1){
					
						S2p = appmmds.getMassArray()[index] - M_ZN + 2*M_H;
					
					}
				
					outputArray[i] = S2p;
					
					break;
				
				//ALPHA	
				case 5:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-2, currentN-2));
					double SAlpha = 1E100;
					
					if(index!=-1){
					
						SAlpha = appmmds.getMassArray()[index] - M_ZN + M_He;
					
					}
				
					outputArray[i] = SAlpha;
					
					break;
				
				//ALPHA,N
				case 6:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+2, currentN+1));
					double SAlphaN = 1E100;
					
					if(index!=-1){
					
						SAlphaN = M_ZN - appmmds.getMassArray()[index] + M_He - M_n;
					
					}
				
					outputArray[i] = SAlphaN;
					
					break;
				
				//ALPHA,P	
				case 7:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+1, currentN+2));
					double SAlphaP = 1E100;
					
					if(index!=-1){
					
						SAlphaP = M_ZN - appmmds.getMassArray()[index] + M_He - M_H;
					
					}
				
					outputArray[i] = SAlphaP;
					
					break;
				
				//P,N	
				case 8:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+1, currentN-1));
					double Snp = 1E100;
					
					if(index!=-1){
					
						Snp = M_ZN - appmmds.getMassArray()[index] + M_H - M_n;
					
					}
				
					outputArray[i] = Snp;
					
					break;
				
			}
		
		}
		
		return outputArray;
    
    }
    
    /**
     * Gets the diff value array.
     *
     * @param valueArrayTheory the value array theory
     * @param valueArrayExp the value array exp
     * @param pointVector the point vector
     * @return the diff value array
     */
    public double[] getDiffValueArray(double[] valueArrayTheory, double[] valueArrayExp, Vector pointVector){
    
    	double[] outputArray = new double[pointVector.size()];
    
    	int counter = 0;
    
    	for(int i=0; i<ds.getTheoryModelDataStructure().getZNArray().length; i++){
    	
    		int theoryZ = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getX();
    		int theoryN = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getY();
    		
    		for(int j=0; j<ds.getExpModelDataStructure().getZNArray().length; j++){
    		
    			int expZ = (int)ds.getExpModelDataStructure().getZNArray()[j].getX();
    			int expN = (int)ds.getExpModelDataStructure().getZNArray()[j].getY();
    			
    			if((theoryZ==expZ) && (theoryN==expN) && pointVector.contains(new Point(theoryZ, theoryN))){
    			
    				double value = 1E100;
    			
    				if(valueArrayTheory[i]!=1E100 && valueArrayExp[j]!=1E100){
    				
    					value = valueArrayTheory[i] - valueArrayExp[j];
    				
    				}
    			
    				outputArray[counter] = value;
    				counter++;
    			
    			}
    		
    		}
    	
    	}
    
    	return outputArray;
    
    }
    
    /**
     * Gets the abs diff array.
     *
     * @param diffArray the diff array
     * @return the abs diff array
     */
    public double[] getAbsDiffArray(double[] diffArray){
    
    	double[] outputArray = new double[diffArray.length];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = Math.abs(diffArray[i]);
		
		}
		
		return outputArray;
    
    }
    
    /**
     * Gets the min value.
     *
     * @return the min value
     */
    public double getMinValue(){
    
    	double min = 1E100;
    	
    	for(int i=0; i<valueArray.length; i++){
    	
    		if(valueArray[i]!=1E100){
    	
    			min = Math.min(min, valueArray[i]);
    		
    		}
    	
    	}
    	
    	return min;
    
    }
    
    /**
     * Gets the max value.
     *
     * @return the max value
     */
    public double getMaxValue(){
    
    	double max = -1E100;
    	
    	for(int i=0; i<valueArray.length; i++){
    	
    		if(valueArray[i]!=1E100){
    	
    			max = Math.max(max, valueArray[i]);
    		
    		}
    	
    	}
    	
    	return max;
    
    }
    
    /**
     * Download r process isotopes.
     */
    public void downloadRProcessIsotopes(){
		String dataString = new String(FileGetter.getFileByName("MassModelData/rpSmall.txt"));
		parseRProcessIsotopesString(dataString);

    }
    
    /**
     * Parses the r process isotopes string.
     *
     * @param string the string
     */
    public void parseRProcessIsotopesString(String string){
    
    	StringTokenizer st = new StringTokenizer(string);
    
    	int numberTokens = st.countTokens();
    	
    	Vector tempVector = new Vector();
 	
    	for(int i=0; i<(int)(numberTokens/2); i++){
    	
    		tempVector.addElement(new Point((int)(Double.valueOf(st.nextToken()).doubleValue())
    									, (int)(Double.valueOf(st.nextToken()).doubleValue())));
    	
    	}
    
    	ds.setRProcessArray(tempVector);
    
    }
    
    /**
     * Download stable isotopes.
     */
    public void downloadStableIsotopes(){
    

			String dataString = new String(FileGetter.getFileByName("MassModelData/Expstablenuclides"));
			parseStableIsotopesString(dataString);
    }
    
    /**
     * Parses the stable isotopes string.
     *
     * @param string the string
     */
    public void parseStableIsotopesString(String string){
    
    	StringTokenizer st = new StringTokenizer(string);
    
    	int numberTokens = st.countTokens();
    	
    	Vector tempVector = new Vector();
 	
    	for(int i=0; i<(int)(numberTokens/2); i++){
    	
    		tempVector.addElement(new Point(Integer.valueOf(st.nextToken()).intValue()
    									, Integer.valueOf(st.nextToken()).intValue()));
    	
    	}
    
    	ds.setStableArray(tempVector);
    
    }
    
    /**
     * Gets the chart color array.
     *
     * @param type the type
     * @param scheme the scheme
     * @return the chart color array
     */
    public Color[] getChartColorArray(int type, String scheme){
    
	    x0R = ds.getColorValues()[0];
	    x0G = ds.getColorValues()[1];
	    x0B = ds.getColorValues()[2];
	    aR = ds.getColorValues()[3];
	    aG = ds.getColorValues()[4];
	    aB = ds.getColorValues()[5]; 
    
    	Color[] colorArray = new Color[0];
    
    	if(type==2 || type==3){

			colorArray = new Color[ds.getPointVector().size()];
		
		}else if(type==0){
		
			colorArray = new Color[ds.getTheoryModelDataStructure().getZNArray().length];
		
		}else if(type==1){
		
			colorArray = new Color[ds.getExpModelDataStructure().getZNArray().length];

		}

		if(scheme.equals("Continuous")){

			for(int i=0; i<colorArray.length; i++){
			
				if(valueArray[i]!=1E100){
			
					colorArray[i] = getColor(valueArray[i]
													, Double.valueOf(minField.getText()).doubleValue()
													, Double.valueOf(maxField.getText()).doubleValue());
											
				}else{
				
					colorArray[i] = null;
				
				}
			
			}
		
		}else if(scheme.equals("Binned")){
		
			for(int i=0; i<colorArray.length; i++){
		
				colorArray[i] = getBinnedColor(valueArray[i]);
			
			}
		
		}

    	return colorArray;
    	
    }
    
    /**
     * Gets the color.
     *
     * @param value the value
     * @param min the min
     * @param max the max
     * @return the color
     */
    public Color getColor(double value, double min, double max){
    	
        double normValue;
        
        Color color;
        
        if(ds.getIncludeValues()){
        
	        normValue = (1/(max-min))*(value-min);
	
	        color = getRGB(normValue); 
        
    	}else{
    	
    		if(value<=max && value>=min){
    		
    			normValue = (1/(max-min))*(value-min);
	
	        	color = getRGB(normValue);
    		
    		}else{
    		
    			color = null;
    		
    		}
    	
    	}
        
        return color;
    }
    
    /**
     * Gets the binned color.
     *
     * @param value the value
     * @return the binned color
     */
    public Color getBinnedColor(double value){
    
    	Color tempColor = null;
    
    	Vector binDataVector = ds.getBinData();
		
		binFound:
		for(int i=0; i<binDataVector.size(); i++){
		
			double min = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(0)).doubleValue();
			double max = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(1)).doubleValue();
			
			if(value>=min && value<max){
			
				if(((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue()){
				
					tempColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(3);
					break binFound;
				
				}
			
			}else{

				tempColor = null;
			
			}	
		
		}   
    	
    	return tempColor;
    
    }
    
    /**
     * Gets the rGB.
     *
     * @param x the x
     * @return the rGB
     */
    public Color getRGB(double x){
    	
    	if(x>=1.0){
    	
    		x = 1.0;
    	
    	}
    	
    	if(x<=0.0){
    	
    		x = 0.0;
    	
    	}
    	
        int red = (int)(255*Math.exp(-(x-x0R)*(x-x0R)/aR/aR));
        int green = (int)(255*Math.exp(-(x-x0G)*(x-x0G)/aG/aG));
        int blue = (int)(255*Math.exp(-(x-x0B)*(x-x0B)/aB/aB));
        
        return new Color(red,green,blue);   
    }
    
    //Window closing listener
	/* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  

		   setVisible(false);
		   dispose();
		   
    	}
    	
    } 
    
    //Remainder of windowListener methods
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){redoChartSize();}
    
    /**
     * Creates the save dialog.
     */
    public void createSaveDialog(){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Select Image Type", true);
    	saveDialog.setSize(355, 158);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	JPanel boxPanel = new JPanel(new GridBagLayout());
    	
    	//Check box group for radio buttons
    	ButtonGroup choiceButtonGroup = new ButtonGroup();
    	
		blackTextRadioButton = new JRadioButton("Black text / White background", false);
		blackTextRadioButton.setFont(Fonts.textFont);
		
		whiteTextRadioButton = new JRadioButton("White text / Black background", true);
		whiteTextRadioButton.setFont(Fonts.textFont);
		
		choiceButtonGroup.add(blackTextRadioButton);
		choiceButtonGroup.add(whiteTextRadioButton);
		
		//Create submit button and its properties
		okButton = new JButton("Submit");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		JLabel label = new JLabel("Please select simulation data types for animation.");
		label.setFont(Fonts.textFont);
		
		//Layout the components
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.anchor = GridBagConstraints.CENTER;

		saveDialog.getContentPane().add(label, gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(whiteTextRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(blackTextRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.gridwidth = 2;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(okButton, gbc1);
		
		gbc1.gridwidth = 1;
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		saveDialog.getContentPane().add(boxPanel, gbc1);

		//Cina.setFrameColors(saveDialog);
		
		//Show the dialog
		saveDialog.setVisible(true);

    }
    
    /**
     * Open delay dialog.
     *
     * @param frame the frame
     */
    public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait", false);
    	delayDialog.setSize(340, 200);
    	delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while data is loaded. DO NOT operate the Interactive Nuclide Chart for Mass Model Evaluations at this time!";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		
		//Cina.setFrameColors(delayDialog);	
		
		delayDialog.validate();
		
		delayDialog.setVisible(true);
		
	}
	
	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	
	}
	
}
    
    