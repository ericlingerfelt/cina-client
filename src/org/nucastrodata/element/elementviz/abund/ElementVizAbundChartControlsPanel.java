package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;


import java.awt.event.*;

import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundChartPanel;


/**
 * The Class ElementVizAbundChartControlsPanel.
 */
public class ElementVizAbundChartControlsPanel extends WizardPanel implements ActionListener, ChangeListener{

    /** The element viz abund chart panel. */
    static ElementVizAbundChartPanel elementVizAbundChartPanel;

    /** The sp. */
    JScrollPane sp;
    
    /** The clear button. */
    JButton clearButton;
    
    /** The right panel. */
    JPanel botPanel, botPanelB, botPanelC, rightPanel;
    
    /** The element field. */
    JTextField zmaxField, nmaxField, aField, zField, elementField;
    
    /** The nmax slider. */
    JSlider zmaxSlider, nmaxSlider;
   
    /** The gbc. */
    GridBagConstraints gbc;
    
    /** The bottom label. */
    JLabel topLabel, bottomLabel;
    
    /** The denom nuc sim za map array. */
    Point[] numNucSimZAMapArray, denomNucSimZAMapArray;

	/** The N ruler. */
	IsotopeRuler ZRuler, NRuler;

	/** The ds. */
	private ElementVizDataStructure ds;

    /**
     * Instantiates a new element viz abund chart controls panel.
     *
     * @param ds the ds
     */
    public ElementVizAbundChartControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;

		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		addWizardPanel("Abundance Plotting Interface", "Select Isotopes", "1", "2");

		rightPanel = new JPanel(new GridBagLayout());

		clearButton = new JButton("<html>Clear All<p>Selections</html>");
        clearButton.setFont(Fonts.buttonFont);
        clearButton.addActionListener(this);
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		//rightPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		rightPanel.add(clearButton, gbc);
		
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
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 85, ElementVizAbundChartPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(ElementVizAbundChartPanel.zmax));

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
        nmaxField.setText(Integer.toString(ElementVizAbundChartPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 193, ElementVizAbundChartPanel.nmax);
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

        elementVizAbundChartPanel = new ElementVizAbundChartPanel(ds);
        
        elementVizAbundChartPanel.width = (int)(elementVizAbundChartPanel.boxWidth*(elementVizAbundChartPanel.nmax+1));
        elementVizAbundChartPanel.height = (int)(elementVizAbundChartPanel.boxHeight*(elementVizAbundChartPanel.zmax+1));
        
        elementVizAbundChartPanel.setSize(elementVizAbundChartPanel.width+2*elementVizAbundChartPanel.xoffset,elementVizAbundChartPanel.height+2*elementVizAbundChartPanel.yoffset);
        
        elementVizAbundChartPanel.xmax = (int)(elementVizAbundChartPanel.xoffset + elementVizAbundChartPanel.width);
        elementVizAbundChartPanel.ymax = (int)(elementVizAbundChartPanel.yoffset + elementVizAbundChartPanel.height);

		elementVizAbundChartPanel.setPreferredSize(elementVizAbundChartPanel.getSize());
		
        sp = new JScrollPane(elementVizAbundChartPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(elementVizAbundChartPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)elementVizAbundChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAbundChartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(elementVizAbundChartPanel.zmax
								, elementVizAbundChartPanel.nmax
								, elementVizAbundChartPanel.mouseX
								, elementVizAbundChartPanel.mouseY
								, elementVizAbundChartPanel.xoffset
								, elementVizAbundChartPanel.yoffset
								, elementVizAbundChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAbundChartPanel.zmax
								, elementVizAbundChartPanel.nmax
								, elementVizAbundChartPanel.mouseX
								, elementVizAbundChartPanel.mouseY
								, elementVizAbundChartPanel.xoffset
								, elementVizAbundChartPanel.yoffset
								, elementVizAbundChartPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
		
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		add(sp, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		add(botPanel, BorderLayout.SOUTH);
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		validate();
		
		
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
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
        
		if(ae.getSource()==clearButton){
           	
           	ds.setIsotopeViktorAbund(new Vector());
       
           	elementVizAbundChartPanel.setCurrentState();
           	
           	elementVizAbundChartPanel.repaint();
           	
           	setCurrentState();
           	
		}
    } 
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		elementVizAbundChartPanel.initialize();
	
	}
	
    /**
     * Reset chart.
     */
    public void resetChart(){
        
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

        elementVizAbundChartPanel.zmax = zmaxSlider.getValue();
        elementVizAbundChartPanel.nmax = nmaxSlider.getValue();
        
        elementVizAbundChartPanel.width = (int)(elementVizAbundChartPanel.boxWidth*(elementVizAbundChartPanel.nmax+1));
        elementVizAbundChartPanel.height = (int)(elementVizAbundChartPanel.boxHeight*(elementVizAbundChartPanel.zmax+1));
        
        elementVizAbundChartPanel.setSize(elementVizAbundChartPanel.width+2*elementVizAbundChartPanel.xoffset,elementVizAbundChartPanel.height+2*elementVizAbundChartPanel.yoffset);
        
        elementVizAbundChartPanel.xmax = (int)(elementVizAbundChartPanel.xoffset + elementVizAbundChartPanel.width);
        elementVizAbundChartPanel.ymax = (int)(elementVizAbundChartPanel.yoffset + elementVizAbundChartPanel.height);
        
        elementVizAbundChartPanel.validate();
        
        elementVizAbundChartPanel.setCurrentState();

		elementVizAbundChartPanel.setPreferredSize(elementVizAbundChartPanel.getSize());
		
		elementVizAbundChartPanel.revalidate();
		
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(elementVizAbundChartPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)elementVizAbundChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAbundChartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(elementVizAbundChartPanel.zmax
								, elementVizAbundChartPanel.nmax
								, elementVizAbundChartPanel.mouseX
								, elementVizAbundChartPanel.mouseY
								, elementVizAbundChartPanel.xoffset
								, elementVizAbundChartPanel.yoffset
								, elementVizAbundChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAbundChartPanel.zmax
								, elementVizAbundChartPanel.nmax
								, elementVizAbundChartPanel.mouseX
								, elementVizAbundChartPanel.mouseY
								, elementVizAbundChartPanel.xoffset
								, elementVizAbundChartPanel.yoffset
								, elementVizAbundChartPanel.crosshairsOn);
			
    }
   
} 

