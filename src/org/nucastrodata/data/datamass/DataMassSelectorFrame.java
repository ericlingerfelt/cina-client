package org.nucastrodata.data.datamass;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.data.datamass.DataMassSelectorPanel;


/**
 * The Class DataMassSelectorFrame.
 */
public class DataMassSelectorFrame extends JFrame implements ActionListener, ChangeListener{

	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 
	
    /** The data mass selector panel. */
    static DataMassSelectorPanel dataMassSelectorPanel;

    /** The sp. */
    JScrollPane sp;
  
    /** The submit button. */
    JButton clearButton, submitButton;
    
    /** The button panel. */
    JPanel botPanel, botPanelB, botPanelC, rightPanel, buttonPanel;
    
    /** The rate field. */
    JTextField zmaxField, nmaxField, aField, zField, elementField, rateField;
    
    /** The nmax slider. */
    JSlider zmaxSlider, nmaxSlider;
    
    /** The c. */
    Container c;
    
    /** The choice button group. */
    ButtonGroup choiceButtonGroup;
    
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The rate label. */
    JLabel topLabel, rateLabel;

	/** The N ruler. */
	IsotopeRuler ZRuler, NRuler;

	/** The ds. */
	private DataMassDataStructure ds;

    /**
     * Instantiates a new data mass selector frame.
     *
     * @param ds the ds
     */
    public DataMassSelectorFrame(DataMassDataStructure ds){
    
    	this.ds = ds;
    
    	c = getContentPane();
    
		gbc = new GridBagConstraints();
		
		setTitle("Mass Model Evaluator Isotope Selector");
		
		mainPanel = new JPanel(new BorderLayout());

		rightPanel = new JPanel(new GridBagLayout());

		topLabel = new JLabel("Available Rates :");
		topLabel.setFont(Fonts.textFont);

		clearButton = new JButton("Clear All Selections");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);

		submitButton = new JButton("Submit Isotope Selection");
        submitButton.setFont(Fonts.buttonFont);
        submitButton.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		//rightPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		
		rightPanel.add(clearButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		
		rightPanel.add(submitButton, gbc);
		
        botPanel = new JPanel();
        botPanel.setLayout(new GridBagLayout());

        botPanelB = new JPanel();
        JLabel zmaxLabel = new JLabel("Zmax",JLabel.LEFT);
        zmaxLabel.setFont(Fonts.textFont);
        botPanelB.add(zmaxLabel);

		JLabel elementLabel = new JLabel("Element: ");
		elementLabel.setFont(Fonts.textFont);

		JLabel zLabel = new JLabel("Z: ");
		zLabel.setFont(Fonts.textFont);
		
		JLabel aLabel = new JLabel("A: ");
		aLabel.setFont(Fonts.textFont);

		elementField = new JTextField();
		elementField.setColumns(3);
		elementField.setEditable(false);
		
		zField = new JTextField();
		zField.setColumns(3);
		zField.setEditable(false);
		
		aField = new JTextField();
		aField.setColumns(3);
		aField.setEditable(false);

        zmaxField = new JTextField();
        zmaxField.setColumns(3);
        zmaxField.setFont(Fonts.textFont);
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 175, dataMassSelectorPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(dataMassSelectorPanel.zmax));

		zmaxField.setEditable(false);

		botPanelB.add(zmaxSlider);
		botPanelB.add(zmaxField);

        botPanelC = new JPanel();
        JLabel nmaxLabel = new JLabel("Nmax",JLabel.LEFT);
        nmaxLabel.setFont(Fonts.textFont);
        botPanelC.add(nmaxLabel);

        nmaxField = new JTextField();
        nmaxField.setColumns(3);
        nmaxField.setFont(Fonts.textFont);
        nmaxField.setText(Integer.toString(dataMassSelectorPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 360, dataMassSelectorPanel.nmax);
        nmaxSlider.addChangeListener(this);
        
        botPanelC.add(nmaxSlider);
        
        botPanelC.add(nmaxField);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(1, 1, 1, 1);
        botPanel.add(botPanelB, gbc);
        
        gbc.gridx = 2;
        botPanel.add(botPanelC, gbc);
        
        gbc.gridx = 3;
        botPanel.add(elementLabel, gbc);
        
        gbc.gridx = 4;
        botPanel.add(elementField, gbc);
        
        gbc.gridx = 5;
        botPanel.add(zLabel, gbc);
        
        gbc.gridx = 6;
        botPanel.add(zField, gbc);
        
        gbc.gridx = 7;
        botPanel.add(aLabel, gbc);
        
        gbc.gridx = 8;
        botPanel.add(aField, gbc);

		gbc.fill = GridBagConstraints.NONE;

        dataMassSelectorPanel = new DataMassSelectorPanel(ds);
        
        dataMassSelectorPanel.width = (int)(dataMassSelectorPanel.boxWidth*(dataMassSelectorPanel.nmax+1));
        dataMassSelectorPanel.height = (int)(dataMassSelectorPanel.boxHeight*(dataMassSelectorPanel.zmax+1));
        
        dataMassSelectorPanel.setSize(dataMassSelectorPanel.width+2*dataMassSelectorPanel.xoffset
        										,dataMassSelectorPanel.height+2*dataMassSelectorPanel.yoffset);
        
        dataMassSelectorPanel.xmax = (int)(dataMassSelectorPanel.xoffset + dataMassSelectorPanel.width);
        dataMassSelectorPanel.ymax = (int)(dataMassSelectorPanel.yoffset + dataMassSelectorPanel.height);

		dataMassSelectorPanel.setPreferredSize(dataMassSelectorPanel.getSize());
		
        sp = new JScrollPane(dataMassSelectorPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(dataMassSelectorPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)dataMassSelectorPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataMassSelectorPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(dataMassSelectorPanel.zmax
								, dataMassSelectorPanel.nmax
								, dataMassSelectorPanel.mouseX
								, dataMassSelectorPanel.mouseY
								, dataMassSelectorPanel.xoffset
								, dataMassSelectorPanel.yoffset
								, dataMassSelectorPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataMassSelectorPanel.zmax
								, dataMassSelectorPanel.nmax
								, dataMassSelectorPanel.mouseX
								, dataMassSelectorPanel.mouseY
								, dataMassSelectorPanel.xoffset
								, dataMassSelectorPanel.yoffset
								, dataMassSelectorPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
		
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		mainPanel.add(botPanel, BorderLayout.SOUTH);
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());

        c.add(mainPanel, BorderLayout.CENTER);

		
		
		c.validate();
		
    } 
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==zmaxSlider){
    	
    		zmaxField.setText(Integer.toString(zmaxSlider.getValue()));
    		
    		resetChart();
    		
    	}else if(ce.getSource()==nmaxSlider){
    	
    		nmaxField.setText(Integer.toString(nmaxSlider.getValue()));
    		
   			resetChart();
    		
    	}
    	
    	sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
        if(ae.getSource()==clearButton){
           	
           	ds.setIsotopeViktor(new Vector());
           	
           	dataMassSelectorPanel.setCurrentState();
           	
           	dataMassSelectorPanel.repaint();
           	
           	setCurrentState();
           	
		}else if(ae.getSource()==submitButton){
		
			ds.setMassesSubmitted(true);
			
			Cina.dataMassFrame.dataMassSelectIsotopesPanel.setCurrentState();
		
			dataMassSelectorPanel.isotopeViktor.trimToSize();
		
			ds.setIsotopeViktor(dataMassSelectorPanel.isotopeViktor);
		
			setVisible(false);
			dispose();
		
		}
		
    } 
	
    /**
     * Reset chart.
     */
    public void resetChart(){
        
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

        dataMassSelectorPanel.zmax = zmaxSlider.getValue();
        dataMassSelectorPanel.nmax = nmaxSlider.getValue();
        
        dataMassSelectorPanel.width = (int)(dataMassSelectorPanel.boxWidth*(dataMassSelectorPanel.nmax+1));
        dataMassSelectorPanel.height = (int)(dataMassSelectorPanel.boxHeight*(dataMassSelectorPanel.zmax+1));
        
        dataMassSelectorPanel.setSize(dataMassSelectorPanel.width+2*dataMassSelectorPanel.xoffset
        										,dataMassSelectorPanel.height+2*dataMassSelectorPanel.yoffset);
        
        dataMassSelectorPanel.xmax = (int)(dataMassSelectorPanel.xoffset + dataMassSelectorPanel.width);
        dataMassSelectorPanel.ymax = (int)(dataMassSelectorPanel.yoffset + dataMassSelectorPanel.height);
        
        dataMassSelectorPanel.setCurrentState();

		dataMassSelectorPanel.setPreferredSize(dataMassSelectorPanel.getSize());
		
		dataMassSelectorPanel.revalidate();
        
        ZRuler.setPreferredWidth((int)dataMassSelectorPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)dataMassSelectorPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(dataMassSelectorPanel.zmax
								, dataMassSelectorPanel.nmax
								, dataMassSelectorPanel.mouseX
								, dataMassSelectorPanel.mouseY
								, dataMassSelectorPanel.xoffset
								, dataMassSelectorPanel.yoffset
								, dataMassSelectorPanel.crosshairsOn);
								
    	NRuler.setCurrentState(dataMassSelectorPanel.zmax
								, dataMassSelectorPanel.nmax
								, dataMassSelectorPanel.mouseX
								, dataMassSelectorPanel.mouseY
								, dataMassSelectorPanel.xoffset
								, dataMassSelectorPanel.yoffset
								, dataMassSelectorPanel.crosshairsOn);
			
	}
    
    /**
     * Gets the current state.
     *
     * @return the current state
     */
    public void getCurrentState(){
    
    }
    
    /**
     * Sets the current state.
     */
    public void setCurrentState(){
    	
    	dataMassSelectorPanel.initialize();
    
    }
    
} 