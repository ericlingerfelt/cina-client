package org.nucastrodata.element.elementviz.scale;

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
import org.nucastrodata.element.elementviz.scale.ElementVizScaleChartPanel;


/**
 * The Class ElementVizScaleChartControlsPanel.
 */
public class ElementVizScaleChartControlsPanel extends WizardPanel implements ActionListener, ChangeListener{

    /** The chart panel. */
    private ElementVizScaleChartPanel chartPanel;
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The clear button. */
    private JButton clearButton;
    
    /** The right panel. */
    private JPanel botPanel, botPanelB, botPanelC, rightPanel;
    
    /** The element field. */
    public JTextField zmaxField, nmaxField, aField, zField, elementField;
    
    /** The nmax slider. */
    private JSlider zmaxSlider, nmaxSlider;
    
    /** The gbc. */
    private GridBagConstraints gbc;
    
    /** The bottom label. */
    private JLabel topLabel, bottomLabel;
    
    /** The denom nuc sim za map array. */
    private Point[] numNucSimZAMapArray, denomNucSimZAMapArray;
    
    /** The N ruler. */
    public IsotopeRuler ZRuler, NRuler;

	/** The ds. */
	private ElementVizDataStructure ds;

    /**
     * Instantiates a new element viz scale chart controls panel.
     *
     * @param ds the ds
     */
    public ElementVizScaleChartControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;

		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		addWizardPanel("Sensitivity Study Plotting Interface", "Select Isotopes", "1", "2");

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
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 85, chartPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(chartPanel.zmax));

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
        nmaxField.setText(Integer.toString(chartPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 193, chartPanel.nmax);
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

        chartPanel = new ElementVizScaleChartPanel(ds, this);
        
        chartPanel.width = (int)(chartPanel.boxWidth*(chartPanel.nmax+1));
        chartPanel.height = (int)(chartPanel.boxHeight*(chartPanel.zmax+1));
        
        chartPanel.setSize(chartPanel.width+2*chartPanel.xoffset,chartPanel.height+2*chartPanel.yoffset);
        
        chartPanel.xmax = (int)(chartPanel.xoffset + chartPanel.width);
        chartPanel.ymax = (int)(chartPanel.yoffset + chartPanel.height);

		chartPanel.setPreferredSize(chartPanel.getSize());
		
        sp = new JScrollPane(chartPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(chartPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)chartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)chartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(chartPanel.zmax
								, chartPanel.nmax
								, chartPanel.mouseX
								, chartPanel.mouseY
								, chartPanel.xoffset
								, chartPanel.yoffset
								, chartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(chartPanel.zmax
								, chartPanel.nmax
								, chartPanel.mouseX
								, chartPanel.mouseY
								, chartPanel.xoffset
								, chartPanel.yoffset
								, chartPanel.crosshairsOn);
	
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
           	
           	ds.setIsotopeViktorScale(new Vector());
           	
           	chartPanel.setCurrentState();
           	
           	chartPanel.repaint();
           	
           	setCurrentState();
           	
		}
    } 
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		chartPanel.initialize();
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		chartPanel.getCurrentState();
	}
	
    /**
     * Reset chart.
     */
    public void resetChart(){
        
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

        chartPanel.zmax = zmaxSlider.getValue();
        chartPanel.nmax = nmaxSlider.getValue();
        
        chartPanel.width = (int)(chartPanel.boxWidth*(chartPanel.nmax+1));
        chartPanel.height = (int)(chartPanel.boxHeight*(chartPanel.zmax+1));
        
        chartPanel.setSize(chartPanel.width+2*chartPanel.xoffset,chartPanel.height+2*chartPanel.yoffset);
        
        chartPanel.xmax = (int)(chartPanel.xoffset + chartPanel.width);
        chartPanel.ymax = (int)(chartPanel.yoffset + chartPanel.height);
        
        chartPanel.validate();
        
        chartPanel.setCurrentState();

		chartPanel.setPreferredSize(chartPanel.getSize());
		
		chartPanel.revalidate();
		
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(chartPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)chartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)chartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(chartPanel.zmax
								, chartPanel.nmax
								, chartPanel.mouseX
								, chartPanel.mouseY
								, chartPanel.xoffset
								, chartPanel.yoffset
								, chartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(chartPanel.zmax
								, chartPanel.nmax
								, chartPanel.mouseX
								, chartPanel.mouseY
								, chartPanel.xoffset
								, chartPanel.yoffset
								, chartPanel.crosshairsOn);
			
    }
   
} 


