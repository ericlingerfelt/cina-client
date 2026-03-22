package org.nucastrodata.element.elementviz.abundtable;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;

import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.WizardPanel;


/**
 * The Class ElementVizAbundChartControlsPanel.
 */
public class ElementVizAbundTableChartControlsPanel extends WizardPanel implements ActionListener, ChangeListener{

    /** The element viz abund chart panel. */
    static ElementVizAbundTableChartPanel elementVizAbundTableChartPanel;

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
    public ElementVizAbundTableChartControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;

		setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		addWizardPanel("Final Abundance Table Interface", "Select Isotopes", "1", "2");

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
        
        zmaxSlider = new JSlider(JSlider.HORIZONTAL, 0, 85, ElementVizAbundTableChartPanel.zmax);
		zmaxSlider.addChangeListener(this);
		zmaxField.setText(Integer.toString(ElementVizAbundTableChartPanel.zmax));

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
        nmaxField.setText(Integer.toString(ElementVizAbundTableChartPanel.nmax));
        
        nmaxField.setEditable(false);
        
        nmaxSlider = new JSlider(JSlider.HORIZONTAL, 1, 193, ElementVizAbundTableChartPanel.nmax);
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

		elementVizAbundTableChartPanel = new ElementVizAbundTableChartPanel(ds);
        
		elementVizAbundTableChartPanel.width = (int)(elementVizAbundTableChartPanel.boxWidth*(elementVizAbundTableChartPanel.nmax+1));
		elementVizAbundTableChartPanel.height = (int)(elementVizAbundTableChartPanel.boxHeight*(elementVizAbundTableChartPanel.zmax+1));
        
		elementVizAbundTableChartPanel.setSize(elementVizAbundTableChartPanel.width+2*elementVizAbundTableChartPanel.xoffset,elementVizAbundTableChartPanel.height+2*elementVizAbundTableChartPanel.yoffset);
        
		elementVizAbundTableChartPanel.xmax = (int)(elementVizAbundTableChartPanel.xoffset + elementVizAbundTableChartPanel.width);
		elementVizAbundTableChartPanel.ymax = (int)(elementVizAbundTableChartPanel.yoffset + elementVizAbundTableChartPanel.height);

		elementVizAbundTableChartPanel.setPreferredSize(elementVizAbundTableChartPanel.getSize());
		
        sp = new JScrollPane(elementVizAbundTableChartPanel);
		
		JViewport vp = new JViewport();
		
		vp.setView(elementVizAbundTableChartPanel);
		
		sp.setViewport(vp);
		
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)elementVizAbundTableChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAbundTableChartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(elementVizAbundTableChartPanel.zmax
								, elementVizAbundTableChartPanel.nmax
								, elementVizAbundTableChartPanel.mouseX
								, elementVizAbundTableChartPanel.mouseY
								, elementVizAbundTableChartPanel.xoffset
								, elementVizAbundTableChartPanel.yoffset
								, elementVizAbundTableChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAbundTableChartPanel.zmax
								, elementVizAbundTableChartPanel.nmax
								, elementVizAbundTableChartPanel.mouseX
								, elementVizAbundTableChartPanel.mouseY
								, elementVizAbundTableChartPanel.xoffset
								, elementVizAbundTableChartPanel.yoffset
								, elementVizAbundTableChartPanel.crosshairsOn);
	
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
           	
           	ds.setIsotopeViktorAbundTable(new Vector());
       
           	elementVizAbundTableChartPanel.setCurrentState();
           	
           	elementVizAbundTableChartPanel.repaint();
           	
           	setCurrentState();
           	
		}
    } 
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		elementVizAbundTableChartPanel.initialize();
	
	}
	
    /**
     * Reset chart.
     */
    public void resetChart(){
        
        double s1 = zmaxSlider.getValue();
        double s2 = nmaxSlider.getValue();

        elementVizAbundTableChartPanel.zmax = zmaxSlider.getValue();
        elementVizAbundTableChartPanel.nmax = nmaxSlider.getValue();
        
        elementVizAbundTableChartPanel.width = (int)(elementVizAbundTableChartPanel.boxWidth*(elementVizAbundTableChartPanel.nmax+1));
        elementVizAbundTableChartPanel.height = (int)(elementVizAbundTableChartPanel.boxHeight*(elementVizAbundTableChartPanel.zmax+1));
        
        elementVizAbundTableChartPanel.setSize(elementVizAbundTableChartPanel.width+2*elementVizAbundTableChartPanel.xoffset,elementVizAbundTableChartPanel.height+2*elementVizAbundTableChartPanel.yoffset);
        
        elementVizAbundTableChartPanel.xmax = (int)(elementVizAbundTableChartPanel.xoffset + elementVizAbundTableChartPanel.width);
        elementVizAbundTableChartPanel.ymax = (int)(elementVizAbundTableChartPanel.yoffset + elementVizAbundTableChartPanel.height);
        
        elementVizAbundTableChartPanel.validate();
        
        elementVizAbundTableChartPanel.setCurrentState();

		elementVizAbundTableChartPanel.setPreferredSize(elementVizAbundTableChartPanel.getSize());
		
		elementVizAbundTableChartPanel.revalidate();
		
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(elementVizAbundTableChartPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)elementVizAbundTableChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAbundTableChartPanel.getSize().getHeight());
       	
       	ZRuler.setCurrentState(elementVizAbundTableChartPanel.zmax
								, elementVizAbundTableChartPanel.nmax
								, elementVizAbundTableChartPanel.mouseX
								, elementVizAbundTableChartPanel.mouseY
								, elementVizAbundTableChartPanel.xoffset
								, elementVizAbundTableChartPanel.yoffset
								, elementVizAbundTableChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAbundTableChartPanel.zmax
								, elementVizAbundTableChartPanel.nmax
								, elementVizAbundTableChartPanel.mouseX
								, elementVizAbundTableChartPanel.mouseY
								, elementVizAbundTableChartPanel.xoffset
								, elementVizAbundTableChartPanel.yoffset
								, elementVizAbundTableChartPanel.crosshairsOn);
			
    }
   
} 

