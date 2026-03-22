package org.nucastrodata.element.elementviz.intflux;

import java.awt.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizBinnedFluxPanel;
import org.nucastrodata.element.elementviz.ElementVizIsotopePanel;
import org.nucastrodata.element.elementviz.ElementVizReactionPanel;
import org.nucastrodata.element.elementviz.util.ElementVizRainbowPanel;
import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.element.elementviz.intflux.ElementVizAbundColorSettingsFrame;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxColorSettingsFrame;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxPanel;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxPrintPanel;


/**
 * The Class ElementVizIntFluxFrame.
 */
public class ElementVizIntFluxFrame extends JFrame implements ActionListener, ChangeListener, WindowListener, ItemListener{

    /** The isotope panel pane. */
    JScrollPane intFluxPanelPane, rainbowPaneFlux, rainbowPaneAbund, binnedFluxPane, reactionPanelPane, isotopePanelPane;
    
    /** The c. */
    Container c;
    
    /** The gbc. */
    GridBagConstraints gbc;

    /** The int label. */
    JLabel nucSimLabel, maxLabelFlux, minLabelFlux, valueLabel, maxLabelAbund, minLabelAbund, zoomLabel, warningLabel, minLabel, maxLabel, intLabel;
    				
   	/** The zoom field. */
	   JTextField valueField, maxFieldFlux, minFieldFlux, maxFieldAbund, minFieldAbund, minField, maxField, zoomField;
   					
   	/** The show final abund check box. */
	   JCheckBox showLabelsCheckBox, showStableCheckBox, showFinalAbundCheckBox;
   		
   	/** The ok button. */
	   JButton colorButtonFlux, colorButtonAbund, integrateButton, saveButton, okButton;
   	
   	/** The max slider. */
	   JSlider zoomSlider, minSlider, maxSlider;
    
    /** The nuc sim combo box. */
    JComboBox nucSimComboBox;

	/** The bottom panel. */
	JPanel rightPanel, bottomPanel;

	/** The black text radio button. */
	JRadioButton whiteTextRadioButton, blackTextRadioButton;

    /** The element viz int flux panel. */
    ElementVizIntFluxPanel elementVizIntFluxPanel;
    
    /** The element viz rainbow panel abund. */
    ElementVizRainbowPanel elementVizRainbowPanelAbund;
    
    /** The element viz rainbow panel flux. */
    ElementVizRainbowPanel elementVizRainbowPanelFlux;
    
    /** The element viz binned flux panel. */
    ElementVizBinnedFluxPanel elementVizBinnedFluxPanel;
    
    /** The element viz reaction panel. */
    ElementVizReactionPanel elementVizReactionPanel;
    
    /** The element viz isotope panel. */
    ElementVizIsotopePanel elementVizIsotopePanel;
    
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The element viz int flux color settings frame. */
    public ElementVizIntFluxColorSettingsFrame elementVizIntFluxColorSettingsFrame;
	
	/** The element viz abund color settings frame. */
	public ElementVizAbundColorSettingsFrame elementVizAbundColorSettingsFrame;

    /** The final abund min. */
    float finalAbundMax, finalAbundMin;
    
    /** The int flux min. */
    double intFluxMax, intFluxMin;
    
    /** The delay dialog. */
    JDialog delayDialog;
    
    /** The log10. */
    float log10 = 0.434294482f;
    
  	/** The x0 r_abund. */
	  double x0R_abund = 0.8;
    
    /** The x0 g_abund. */
    double x0G_abund = 0.6;
    
    /** The x0 b_abund. */
    double x0B_abund = 0.2;
    
    /** The a r_abund. */
    double aR_abund = 0.5;
    
    /** The a g_abund. */
    double aG_abund = 0.4;
    
    /** The a b_abund. */
    double aB_abund = 0.3; 
    
    /** The x0 r_flux. */
    double x0R_flux = 0.8;
    
    /** The x0 g_flux. */
    double x0G_flux = 0.6;
    
    /** The x0 b_flux. */
    double x0B_flux = 0.2;
    
    /** The a r_flux. */
    double aR_flux = 0.5;
    
    /** The a g_flux. */
    double aG_flux = 0.4;
    
    /** The a b_flux. */
    double aB_flux = 0.3;
    
    /** The N ruler. */
    IsotopeRuler ZRuler, NRuler;
    
    /** The scheme abund. */
    String schemeAbund = "";
    
    /** The scheme flux. */
    String schemeFlux = "";
    
    /** The simulation. */
    String simulation = "";
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /**
     * Instantiates a new element viz int flux frame.
     *
     * @param ds the ds
     */
    public ElementVizIntFluxFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        c.setLayout(new BorderLayout());

		gbc = new GridBagConstraints();

		setTitle("Integrated Flux Nuclide Chart");
		setSize(new Dimension(969, 719));
		
		//LABELS//////////////////////////////////////////////LABELS//////////////////////
		nucSimLabel = new JLabel("Choose Simulation: ");
		
		warningLabel = new JLabel("<html>Place the cursor over an isotope<p>or an arrow to read its value</html>");
    	
    	valueLabel = new JLabel("Value: ");
    	valueLabel.setFont(Fonts.textFont);
    	
    	maxLabelAbund = new JLabel("Final Abundance Max: ");
    	maxLabelAbund.setFont(Fonts.textFont);
    	
    	minLabelAbund = new JLabel("Final Abundance Min: ");
    	minLabelAbund.setFont(Fonts.textFont);
		
		maxLabelFlux = new JLabel("Integrated Flux Max: ");
    	maxLabelFlux.setFont(Fonts.textFont);
    	
    	minLabelFlux = new JLabel("Integrated Flux Min: ");
    	minLabelFlux.setFont(Fonts.textFont);
		
		maxLabel = new JLabel("Timestep Max: ");
    	maxLabel.setFont(Fonts.textFont);
    	
    	minLabel = new JLabel("Timestep Min: ");
    	minLabel.setFont(Fonts.textFont);
		
		zoomLabel = new JLabel("Zoom (%): ");
		zoomLabel.setFont(Fonts.textFont);
		
		intLabel = new JLabel("Integration Interval: ");
		//intLabel.setFont(CinaFonts.textFont);
		
		//COMBOBOX//////////////////////////////////////////////COMBOBOX/////////////////////
		nucSimComboBox = new JComboBox();
		nucSimComboBox.setFont(Fonts.textFont);
		nucSimComboBox.addItemListener(this);
		
		//FIELD/////////////////////////////////////////////////FIELDS///////////////////////
   		
   		valueField = new JTextField(10);
   		valueField.setEditable(false);
   		
   		maxFieldFlux = new JTextField(10);
   		maxFieldFlux.setEditable(false);
   		
   		minFieldFlux = new JTextField(10);
   		minFieldFlux.setEditable(false);
   		
   		maxFieldAbund = new JTextField(10);
   		maxFieldAbund.setEditable(false);
   		
   		minFieldAbund = new JTextField(10);
   		minFieldAbund.setEditable(false);
   		
   		maxField = new JTextField(3);
   		//maxField.setEditable(false);
   		
   		minField = new JTextField(3);
   		//minField.setEditable(false);
   		
		zoomField = new JTextField(3);
		zoomField.setEditable(false);
		
		//CHECKBOX//////////////////////////////////////////////CHECKBOX////////////////////
		showLabelsCheckBox = new JCheckBox("Isotope Labels", false);
		showLabelsCheckBox.setFont(Fonts.textFont);
		showLabelsCheckBox.addItemListener(this);
		
		showStableCheckBox = new JCheckBox("Stable Isotopes", false);
		showStableCheckBox.setFont(Fonts.textFont);
		showStableCheckBox.addItemListener(this);
		
		showFinalAbundCheckBox = new JCheckBox("Final Abundances", false);
		showFinalAbundCheckBox.setFont(Fonts.textFont);
		showFinalAbundCheckBox.addItemListener(this);
		
		//SLIDERS///////////////////////////////////////////////SLIDERS/////////////////////
		minSlider = new JSlider(JSlider.HORIZONTAL);
		minSlider.addChangeListener(this);
		
		maxSlider = new JSlider(JSlider.HORIZONTAL);
		maxSlider.addChangeListener(this);
		
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		zoomSlider.addChangeListener(this);
		
		//BUTTONS//////////////////////////////////////////////BUTTONS////////////////////////
		
		colorButtonAbund = new JButton("Abundance Color Scale Settings");
		colorButtonAbund.setFont(Fonts.buttonFont);
		colorButtonAbund.addActionListener(this);

		colorButtonFlux = new JButton("Flux Color Scale Settings");
		colorButtonFlux.setFont(Fonts.buttonFont);
		colorButtonFlux.addActionListener(this);
		
		integrateButton = new JButton("Apply Interval");
		integrateButton.setFont(Fonts.buttonFont);
		integrateButton.addActionListener(this);

		saveButton = new JButton("Save Chart as Image");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		elementVizRainbowPanelAbund = new ElementVizRainbowPanel(ds);
		rainbowPaneAbund = new JScrollPane(elementVizRainbowPanelAbund, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rainbowPaneAbund.setPreferredSize(new Dimension(50, 100));
		
		elementVizRainbowPanelFlux = new ElementVizRainbowPanel(ds);
		rainbowPaneFlux = new JScrollPane(elementVizRainbowPanelFlux, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rainbowPaneFlux.setPreferredSize(new Dimension(50, 100));
		
		elementVizBinnedFluxPanel = new ElementVizBinnedFluxPanel(new String("Integrated Flux Legend"));
		binnedFluxPane = new JScrollPane(elementVizBinnedFluxPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		binnedFluxPane.setPreferredSize(new Dimension(200, 100));
	
		elementVizReactionPanel = new ElementVizReactionPanel();
		reactionPanelPane = new JScrollPane(elementVizReactionPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		reactionPanelPane.setPreferredSize(new Dimension(200, 30));
	
		elementVizIsotopePanel = new ElementVizIsotopePanel();
		isotopePanelPane = new JScrollPane(elementVizIsotopePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		isotopePanelPane.setPreferredSize(new Dimension(200, 30));
	
		//PANELS/////////////////////////////////////////////////////////////////////PANELS//////////////////////////
		rightPanel = new JPanel(new GridBagLayout());
		bottomPanel = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);

		bottomPanel.add(intLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;

		bottomPanel.add(minLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		bottomPanel.add(minSlider, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		
		bottomPanel.add(minField, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;

		bottomPanel.add(maxLabel, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 0;
		
		bottomPanel.add(maxSlider, gbc);
		
		gbc.gridx = 6;
		gbc.gridy = 0;
		
		bottomPanel.add(maxField, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 0;
		
		bottomPanel.add(integrateButton, gbc);

    	ds.setIntFluxNucSimDataStructure(ds.getNucSimDataStructureArrayIntFlux()[0]);
		
		elementVizIntFluxPanel = new ElementVizIntFluxPanel(ds);
		elementVizIntFluxPanel.setCurrentState("Binned", "Continuous");
		//elementVizIntFluxPanel.setPreferredSize(elementVizIntFluxPanel.getSize());
		
        intFluxPanelPane = new JScrollPane(elementVizIntFluxPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		JViewport vp = new JViewport();
		
		vp.setView(elementVizIntFluxPanel);
		
		intFluxPanelPane.setViewport(vp);
	
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)elementVizIntFluxPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizIntFluxPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(elementVizIntFluxPanel.zmax
								, elementVizIntFluxPanel.nmax
								, elementVizIntFluxPanel.mouseX
								, elementVizIntFluxPanel.mouseY
								, elementVizIntFluxPanel.xoffset
								, elementVizIntFluxPanel.yoffset
								, elementVizIntFluxPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizIntFluxPanel.zmax
								, elementVizIntFluxPanel.nmax
								, elementVizIntFluxPanel.mouseX
								, elementVizIntFluxPanel.mouseY
								, elementVizIntFluxPanel.xoffset
								, elementVizIntFluxPanel.yoffset
								, elementVizIntFluxPanel.crosshairsOn);
	
		intFluxPanelPane.setColumnHeaderView(ZRuler);
        intFluxPanelPane.setRowHeaderView(NRuler);
	
		intFluxPanelPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        intFluxPanelPane.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        intFluxPanelPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		intFluxPanelPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		intFluxPanelPane.getHorizontalScrollBar().setValue(intFluxPanelPane.getHorizontalScrollBar().getMinimum());
		intFluxPanelPane.getVerticalScrollBar().setValue(intFluxPanelPane.getVerticalScrollBar().getMaximum());

        addWindowListener(this);
    } 
    
    /**
     * Close all frames.
     */
    public void closeAllFrames(){
    	if(elementVizIntFluxColorSettingsFrame!=null){
    		elementVizIntFluxColorSettingsFrame.closeAllFrames();
			elementVizIntFluxColorSettingsFrame.setVisible(false);
			elementVizIntFluxColorSettingsFrame.dispose();
		}
		
		if(elementVizAbundColorSettingsFrame!=null){
			elementVizAbundColorSettingsFrame.closeAllFrames();
			elementVizAbundColorSettingsFrame.setVisible(false);
			elementVizAbundColorSettingsFrame.dispose();
		}
    }
    
    /**
     * Sets the format layout.
     *
     * @param schemeFlux the new format layout
     */
    public void setFormatLayout(String schemeFlux){
    
    	c.removeAll();

		rightPanel.removeAll();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(nucSimLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		rightPanel.add(nucSimComboBox, gbc);
		
		if(schemeFlux.equals("Continuous")){
		
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridheight = 4;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(rainbowPaneFlux, gbc);
			
			gbc.gridheight = 1;
			
			gbc.gridx = 2;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxLabelFlux, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 3;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxFieldFlux, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minLabelFlux, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 5;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minFieldFlux, gbc);

			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridheight = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(rainbowPaneAbund, gbc);
			
			gbc.gridheight = 1;
			
			gbc.gridx = 2;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxLabelAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxFieldAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 8;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minLabelAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 9;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minFieldAbund, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(warningLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(reactionPanelPane, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.EAST;
			rightPanel.add(valueLabel, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(valueField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 13;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showFinalAbundCheckBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showLabelsCheckBox, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showStableCheckBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.EAST;
			rightPanel.add(zoomLabel, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(zoomField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 16;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(zoomSlider, gbc);
		
			gbc.gridx = 0;
			gbc.gridy = 17;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(colorButtonFlux, gbc);
			gbc.fill = GridBagConstraints.NONE;
		
			gbc.gridx = 0;
			gbc.gridy = 18;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(colorButtonAbund, gbc);
			gbc.fill = GridBagConstraints.NONE;
		
			gbc.gridx = 0;
			gbc.gridy = 19;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(saveButton, gbc);
			gbc.fill = GridBagConstraints.NONE;
			
		}else if(schemeFlux.equals("Binned")){
		
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridheight = 4;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(binnedFluxPane, gbc);
			
			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridheight = 4;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(rainbowPaneAbund, gbc);
			
			gbc.gridheight = 1;
			
			gbc.gridx = 2;
			gbc.gridy = 6;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxLabelAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 7;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(maxFieldAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 8;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minLabelAbund, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 9;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(minFieldAbund, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 10;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(warningLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 11;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(reactionPanelPane, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.EAST;
			rightPanel.add(valueLabel, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 12;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(valueField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 13;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showFinalAbundCheckBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showLabelsCheckBox, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 14;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(showStableCheckBox, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.EAST;
			rightPanel.add(zoomLabel, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 15;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			rightPanel.add(zoomField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 16;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			rightPanel.add(zoomSlider, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 17;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(colorButtonFlux, gbc);
			gbc.fill = GridBagConstraints.NONE;
		
			gbc.gridx = 0;
			gbc.gridy = 18;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(colorButtonAbund, gbc);
			gbc.fill = GridBagConstraints.NONE;
		
			gbc.gridx = 0;
			gbc.gridy = 19;
			gbc.gridwidth = 4;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rightPanel.add(saveButton, gbc);
			gbc.fill = GridBagConstraints.NONE;
	
		}

    	rightPanel.setSize(rightPanel.getPreferredSize());
    	rightPanel.validate();
    	
    	c.add(intFluxPanelPane, BorderLayout.CENTER);
		c.add(bottomPanel, BorderLayout.SOUTH);
    	c.add(rightPanel, BorderLayout.EAST);
    	
    	

    	validate();
  
    }

    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==nucSimComboBox){
    	
    		if(delayDialog==null){
    	
    			// openDelayDialog(this);

	    		simulation = nucSimComboBox.getSelectedItem().toString();
	    	
	    		nucSimComboBox.removeItemListener(this);
	    	
	    		//final SwingWorker worker = new SwingWorker(){
			
				//	public Object construct(){
					
				initializeIntFlux(simulation
											, ds.getFinalAbundScheme()
											, ds.getIntFluxScheme());		
				//		return new Object();
						
				//	}
					
				//	public void finished(){
						
				//		closeDelayDialog();
						
						nucSimComboBox.addItemListener(Cina.elementVizFrame.elementVizIntFluxFrame);
						
				//	}
					
				//};
				
				//worker.start();
				
				if(elementVizIntFluxColorSettingsFrame!=null){
					
					elementVizIntFluxColorSettingsFrame.setCurrentState(ds.getIntFluxScheme());
					elementVizIntFluxColorSettingsFrame.setSize(elementVizIntFluxColorSettingsFrame.getPreferredSize());
					elementVizIntFluxColorSettingsFrame.validate();
	
	        	}
				
				if(elementVizAbundColorSettingsFrame!=null){
					
					elementVizAbundColorSettingsFrame.setCurrentState(ds.getFinalAbundScheme());
					elementVizAbundColorSettingsFrame.setSize(elementVizIntFluxColorSettingsFrame.getPreferredSize());
					elementVizAbundColorSettingsFrame.validate();
	
	        	}
	        	
	        }
			
    	}else if(ie.getSource()==showLabelsCheckBox){
    	
    		elementVizIntFluxPanel.repaint();
    	
    	}else if(ie.getSource()==showStableCheckBox){
    	
    		elementVizIntFluxPanel.repaint();
    	
    	}else if(ie.getSource()==showFinalAbundCheckBox){
    	
    		elementVizIntFluxPanel.repaint();
    	
    	}
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
	    
	    if(ae.getSource()==colorButtonFlux){
	    	
    		if(elementVizIntFluxColorSettingsFrame!=null){
				
				elementVizIntFluxColorSettingsFrame.setSize(862, 400);
				elementVizIntFluxColorSettingsFrame.setCurrentState(ds.getIntFluxScheme());
        		elementVizIntFluxColorSettingsFrame.setVisible(true);
        		elementVizIntFluxColorSettingsFrame.validate();

        	}else{
        	
        		elementVizIntFluxColorSettingsFrame = new ElementVizIntFluxColorSettingsFrame(ds);
        		elementVizIntFluxColorSettingsFrame.setSize(862, 400);
        		elementVizIntFluxColorSettingsFrame.setCurrentState(ds.getIntFluxScheme());
        		elementVizIntFluxColorSettingsFrame.setVisible(true);
        		elementVizIntFluxColorSettingsFrame.validate();
        			        	
        	}
        	
	    }else if(ae.getSource()==colorButtonAbund){
	    	
    		if(elementVizAbundColorSettingsFrame!=null){
				
				elementVizAbundColorSettingsFrame.setSize(862, 400);
				elementVizAbundColorSettingsFrame.setCurrentState(ds.getFinalAbundScheme());
        		elementVizAbundColorSettingsFrame.setVisible(true);
        		elementVizAbundColorSettingsFrame.validate();

        	}else{
        	
        		elementVizAbundColorSettingsFrame = new ElementVizAbundColorSettingsFrame(ds);
        		elementVizAbundColorSettingsFrame.setSize(862, 400);
        		elementVizAbundColorSettingsFrame.setCurrentState(ds.getFinalAbundScheme());
        		elementVizAbundColorSettingsFrame.setVisible(true);
        		elementVizAbundColorSettingsFrame.validate();
        			        	
        	}
	    
	    }else if(ae.getSource()==saveButton){
	    
	    	createSaveDialog();
	    
	    }else if(ae.getSource()==okButton){
	    	
	    	saveDialog.setVisible(false);
	    	saveDialog.dispose();
	    
	    	PlotSaver.savePlot(new ElementVizIntFluxPrintPanel(whiteTextRadioButton.isSelected(), ds), this);
	    	
	    }else if(ae.getSource()==integrateButton){
	    
	    	if(checkFields()){
	    
		    	if(goodIntLimits()){
    	
    				if(delayDialog==null){
    	
						openDelayDialog(this);

		    			final SwingWorker worker = new SwingWorker(){
		
							public Object construct(){
							
								minSlider.removeChangeListener(Cina.elementVizFrame.elementVizIntFluxFrame);
					    		maxSlider.removeChangeListener(Cina.elementVizFrame.elementVizIntFluxFrame);
					    	
					    		minSlider.setValue(Integer.valueOf(minField.getText()).intValue());
					    		maxSlider.setValue(Integer.valueOf(maxField.getText()).intValue());
					    	
					    		minSlider.addChangeListener(Cina.elementVizFrame.elementVizIntFluxFrame);
					    		maxSlider.addChangeListener(Cina.elementVizFrame.elementVizIntFluxFrame);
					    	
					    		int min = Integer.valueOf(minField.getText()).intValue();
				    			int max = Integer.valueOf(maxField.getText()).intValue();
					    	
					    		ds.getIntFluxNucSimDataStructure().setIntFluxArray(getIntFluxArray(ds.getIntFluxNucSimDataStructure(), min, max));
					    		
					    		if(ds.getIntFluxScheme().equals("Continuous")){
						
									intFluxMax = getIntFluxMax();
							    	intFluxMin = getIntFluxMin();
							    	
							    	setMinIntFlux(intFluxMin);
							    	setMaxIntFlux(intFluxMax);
						    	
						    		x0R_flux = ds.getIntFluxColorValues()[0];
								    x0G_flux = ds.getIntFluxColorValues()[1];
								   	x0B_flux = ds.getIntFluxColorValues()[2];
								    aR_flux = ds.getIntFluxColorValues()[3];
								    aG_flux = ds.getIntFluxColorValues()[4];
								    aB_flux = ds.getIntFluxColorValues()[5];
						    		
						    		elementVizRainbowPanelFlux.setRGB(ds.getIntFluxColorValues());
								
								}else if(ds.getIntFluxScheme().equals("Binned")){
								
									Vector dataVector = ds.getIntFluxBinData();
							    	
							    	intFluxMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
									intFluxMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
									
									setMinIntFlux(intFluxMin);
						    		setMaxIntFlux(intFluxMax);							
						    		
						    		elementVizBinnedFluxPanel.setDataVector(dataVector);
						    		elementVizBinnedFluxPanel.setSize(200, (dataVector.size()-1)*15+50);
									elementVizBinnedFluxPanel.validate();
									elementVizBinnedFluxPanel.setPreferredSize(elementVizBinnedFluxPanel.getSize());
									elementVizBinnedFluxPanel.revalidate();
								
								}
						
								setChartColors(ds.getIntFluxScheme(), ds.getFinalAbundScheme());
					    	
					    		elementVizIntFluxPanel.repaint();		
								return new Object();
								
							}
							
							public void finished(){
								
								closeDelayDialog();
								
							}
							
						};
			
						worker.start();
						
					}
		    	
		    	}else{
		    		
		    		String string = "Please select a minimum integration timestep value less than the maximum integration timestep value.";
		    		Dialogs.createExceptionDialog(string, this);
		    	
		    	}
	    	
	    	}else{
		    		
	    		String string = "Please enter integer values for timestep minimum and maximum for integration.";
	    		Dialogs.createExceptionDialog(string, this);
	    	
	    	}
	   
	 	}
    
    }
    
    /**
     * Gets the int flux array.
     *
     * @param appnsds the appnsds
     * @param min the min
     * @param max the max
     * @return the int flux array
     */
    public double[] getIntFluxArray(NucSimDataStructure appnsds, int min, int max){
    
		double[] intFluxArray = new double[appnsds.getReactionMapArray().length];
		
		for(int i=min+1; i<max+1; i++){
		
			double delTime = appnsds.getTimeArray()[i] - appnsds.getTimeArray()[i-1];
		
			for(int j=0; j<appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
			
				if(appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray()[j]!=-1){
								
					intFluxArray[appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray()[j]] += delTime*appnsds.getFluxTimestepDataStructureArray()[i].getFluxArray()[j];
			
				}
			
			}
		
		}
		
		return intFluxArray;
    
    }
    
    /**
     * Check fields.
     *
     * @return true, if successful
     */
    public boolean checkFields(){
    
    	boolean checkFields = true;
    
    	try{
    
	    	int min = Integer.valueOf(minField.getText()).intValue();
	    	int max = Integer.valueOf(maxField.getText()).intValue();
    	
    	}catch(NumberFormatException nfe){
    	
    		checkFields = false;
    	
    	}
    	
    	return checkFields;
    	
    }
    
    /**
     * Good int limits.
     *
     * @return true, if successful
     */
    public boolean goodIntLimits(){
    
    	boolean goodIntLimits = true;

    	int min = Integer.valueOf(minField.getText()).intValue();
    	int max = Integer.valueOf(maxField.getText()).intValue();
    	
    	if(min>=max){
    	
    		goodIntLimits = false;
    	
    	}
    	
    	return goodIntLimits;
    	
    }
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==zoomSlider){
    		
    		if(zoomSlider.getValue()==100){
    		
    			intFluxPanelPane.setColumnHeaderView(ZRuler);
        		intFluxPanelPane.setRowHeaderView(NRuler);
    		
    		}else{
    		
    			intFluxPanelPane.setColumnHeaderView(null);
        		intFluxPanelPane.setRowHeaderView(null);
    		
    		}
    		
    		double scale = ((double)zoomSlider.getValue())/100.0;
			elementVizIntFluxPanel.boxHeight = (int)(29.0*scale);
			elementVizIntFluxPanel.boxWidth = (int)(29.0*scale);
	        
        	elementVizIntFluxPanel.width = (int)(elementVizIntFluxPanel.boxWidth*(elementVizIntFluxPanel.nmax+1));
	        elementVizIntFluxPanel.height = (int)(elementVizIntFluxPanel.boxHeight*(elementVizIntFluxPanel.zmax+1));
	        
	        elementVizIntFluxPanel.xmax = (int)(elementVizIntFluxPanel.xoffset + elementVizIntFluxPanel.width);
	        elementVizIntFluxPanel.ymax = (int)(elementVizIntFluxPanel.yoffset + elementVizIntFluxPanel.height);

        	elementVizIntFluxPanel.setSize(elementVizIntFluxPanel.xmax+elementVizIntFluxPanel.xoffset, elementVizIntFluxPanel.ymax+elementVizIntFluxPanel.yoffset);
        			
			elementVizIntFluxPanel.setPreferredSize(elementVizIntFluxPanel.getSize());
    		
    		elementVizIntFluxPanel.repaint();

			zoomField.setText(String.valueOf(zoomSlider.getValue()));
    		
    		if(!zoomSlider.getValueIsAdjusting()){
    		
	    		intFluxPanelPane.getHorizontalScrollBar().setValue(intFluxPanelPane.getHorizontalScrollBar().getMinimum());
				intFluxPanelPane.getVerticalScrollBar().setValue(intFluxPanelPane.getVerticalScrollBar().getMaximum());
			
			}
    		
    		intFluxPanelPane.validate();
    		
    	}else if(ce.getSource()==minSlider){
    		
    		minField.setText(String.valueOf(minSlider.getValue()));
    	
    	}else if(ce.getSource()==maxSlider){
    		
    		maxField.setText(String.valueOf(maxSlider.getValue()));
    	
    	}

    }
    
    /**
     * Initialize.
     */
    public void initialize(){
    	
    	nucSimComboBox.removeItemListener(this);
    	nucSimComboBox.removeAllItems();
    		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			if(ds.getNucSimDataStructureArrayIntFlux()[i].getHasFluxes()){
	    		
	    		nucSimComboBox.addItem(ds.getNucSimDataStructureArrayIntFlux()[i].getNucSimName());
	    		
	    	}
	    
	  	}
    	
    	nucSimComboBox.setSelectedIndex(0);
    	nucSimComboBox.addItemListener(this);
    	
    	simulation = (String)nucSimComboBox.getSelectedItem();
    	
    	schemeAbund = ds.getFinalAbundScheme();
    	schemeFlux = ds.getIntFluxScheme();

    	initializeIntFlux(simulation, schemeAbund, schemeFlux);
    
	}
    
    /**
     * Initialize int flux.
     *
     * @param nucSim the nuc sim
     * @param schemeAbund the scheme abund
     * @param schemeFlux the scheme flux
     */
    public void initializeIntFlux(String nucSim, String schemeAbund, String schemeFlux){
    
    	zoomField.setText(String.valueOf(zoomSlider.getValue()));
    	
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

	        if(ds.getNucSimDataStructureArrayIntFlux()[i].getNucSimName().equals(nucSim)){

    			ds.setIntFluxNucSimDataStructure(ds.getNucSimDataStructureArrayIntFlux()[i]);
    	
    		}
    	
    	}

		minField.setText("0");
		maxField.setText(String.valueOf(ds.getIntFluxNucSimDataStructure().getTimeArray().length-1));

		minSlider.removeChangeListener(this);
		maxSlider.removeChangeListener(this);

		minSlider.setMinimum(0);
		minSlider.setMaximum(ds.getIntFluxNucSimDataStructure().getTimeArray().length-1);

		minSlider.setValue(0);

		maxSlider.setMinimum(0);
		maxSlider.setMaximum(ds.getIntFluxNucSimDataStructure().getTimeArray().length-1);

		maxSlider.setValue(ds.getIntFluxNucSimDataStructure().getTimeArray().length-1);

		minSlider.addChangeListener(this);
		maxSlider.addChangeListener(this);

		int min = Integer.valueOf(minField.getText()).intValue();
			    			int max = Integer.valueOf(maxField.getText()).intValue();
				    	
		ds.getIntFluxNucSimDataStructure().setIntFluxArray(getIntFluxArray(ds.getIntFluxNucSimDataStructure()
																												, min, max));
		
		if(schemeAbund.equals("Continuous")){
		
			finalAbundMax = getFinalAbundMax();
	    	finalAbundMin = getFinalAbundMin();
	    	
	    	setMinFinalAbund(finalAbundMin);
	    	setMaxFinalAbund(finalAbundMax);
    		
    		x0R_abund = ds.getFinalAbundColorValues()[0];
		    x0G_abund = ds.getFinalAbundColorValues()[1];
		   	x0B_abund = ds.getFinalAbundColorValues()[2];
		    aR_abund = ds.getFinalAbundColorValues()[3];
		    aG_abund = ds.getFinalAbundColorValues()[4];
		    aB_abund = ds.getFinalAbundColorValues()[5];
    		
    		elementVizRainbowPanelAbund.setRGB(ds.getFinalAbundColorValues());
		
		}else if(schemeAbund.equals("Binned")){
		
			Vector dataVector = ds.getFinalAbundBinData();
	    	
	    	finalAbundMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
			finalAbundMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
			
			setMinFinalAbund(finalAbundMin);
    		setMaxFinalAbund(finalAbundMax);
		
		}

		if(schemeFlux.equals("Continuous")){
		
			intFluxMax = getIntFluxMax();
	    	intFluxMin = getIntFluxMin();
	    	
	    	setMinIntFlux(intFluxMin);
	    	setMaxIntFlux(intFluxMax);
    	
    		x0R_flux = ds.getIntFluxColorValues()[0];
		    x0G_flux = ds.getIntFluxColorValues()[1];
		   	x0B_flux = ds.getIntFluxColorValues()[2];
		    aR_flux = ds.getIntFluxColorValues()[3];
		    aG_flux = ds.getIntFluxColorValues()[4];
		    aB_flux = ds.getIntFluxColorValues()[5];
    		
    		elementVizRainbowPanelFlux.setRGB(ds.getIntFluxColorValues());
		
		}else if(schemeFlux.equals("Binned")){
		
			Vector dataVector = ds.getIntFluxBinData();
	    	
	    	intFluxMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
			intFluxMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
			
			setMinIntFlux(intFluxMin);
    		setMaxIntFlux(intFluxMax);							
    		
    		elementVizBinnedFluxPanel.setDataVector(dataVector);
    		elementVizBinnedFluxPanel.setSize(200, (dataVector.size()-1)*15+50);
			elementVizBinnedFluxPanel.validate();
			elementVizBinnedFluxPanel.setPreferredSize(elementVizBinnedFluxPanel.getSize());
			elementVizBinnedFluxPanel.revalidate();
		
		}

		setChartColors(schemeFlux, schemeAbund);

		elementVizRainbowPanelAbund.setType("Abundance", schemeAbund);
		elementVizRainbowPanelAbund.repaint();

		elementVizRainbowPanelFlux.setType("Reaction Flux", schemeFlux);
		elementVizRainbowPanelFlux.repaint();

		elementVizIntFluxPanel.setCurrentState(schemeAbund, schemeFlux);
		elementVizIntFluxPanel.revalidate();
	
		intFluxPanelPane.getHorizontalScrollBar().setMinimum(0);
		intFluxPanelPane.getVerticalScrollBar().setMaximum(elementVizIntFluxPanel.getHeight());
	
		intFluxPanelPane.getHorizontalScrollBar().setValue(intFluxPanelPane.getHorizontalScrollBar().getMinimum());
		intFluxPanelPane.getVerticalScrollBar().setValue(intFluxPanelPane.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)elementVizIntFluxPanel.getPreferredSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizIntFluxPanel.getPreferredSize().getHeight());
	
		ZRuler.setCurrentState(elementVizIntFluxPanel.zmax
								, elementVizIntFluxPanel.nmax
								, elementVizIntFluxPanel.mouseX
								, elementVizIntFluxPanel.mouseY
								, elementVizIntFluxPanel.xoffset
								, elementVizIntFluxPanel.yoffset
								, elementVizIntFluxPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizIntFluxPanel.zmax
								, elementVizIntFluxPanel.nmax
								, elementVizIntFluxPanel.mouseX
								, elementVizIntFluxPanel.mouseY
								, elementVizIntFluxPanel.xoffset
								, elementVizIntFluxPanel.yoffset
								, elementVizIntFluxPanel.crosshairsOn);
								
		ZRuler.validate();
		NRuler.validate();
		
		setFormatLayout(schemeFlux);
	
    }
    
    /**
     * Sets the chart colors.
     *
     * @param schemeFlux the scheme flux
     * @param schemeAbund the scheme abund
     */
    public void setChartColors(String schemeFlux, String schemeAbund){

		Color color = Color.black;

		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getZAMapArray().length; i++){
			Point p = ds.getIntFluxNucSimDataStructure().getZAMapArray()[i];
			if(p.getX()==13.0 && p.getY()==26.0){
				for(int j=0; j<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; j++){
					if(ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray()[j]==i){
						list.add(j);
					}
				}
			}
		}
		
		if(schemeAbund.equals("Continuous")){

			x0R_abund = ds.getFinalAbundColorValues()[0];
		    x0G_abund = ds.getFinalAbundColorValues()[1];
		   	x0B_abund = ds.getFinalAbundColorValues()[2];
		    aR_abund = ds.getFinalAbundColorValues()[3];
		    aG_abund = ds.getFinalAbundColorValues()[4];
		    aB_abund = ds.getFinalAbundColorValues()[5];
				
			short[] redArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
			short[] greenArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
			short[] blueArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
		
			for(int i=0; i<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length; i++){
			
				double abund = 0.0;
				
				if(list.contains(i)){
					for(Integer index: list){
						abund += ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[index];
					}
				}else{
					abund = ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i];
				}
				
				color = getLogColor(abund
									, finalAbundMax
									, finalAbundMin
									, "Final Abundance");

				redArray[i] = (short)color.getRed();
				greenArray[i] = (short)color.getGreen();
				blueArray[i] = (short)color.getBlue();
				
			}	

			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setRedArray(redArray);
			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setGreenArray(greenArray);
			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setBlueArray(blueArray);
    	
	    }else if(schemeAbund.equals("Binned")){
	    
			short[] redArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
			short[] greenArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
			short[] blueArray = new short[ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length];
		
			for(int i=0; i<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray().length; i++){
	
				color = null;
    
		    	Vector binDataVector = ds.getFinalAbundBinData();
				
		    	double value = 0.0;
				
				if(list.contains(i)){
					for(Integer index: list){
						value += ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[index];
					}
				}else{
					value = ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i];
				}
				
				binFound:
				for(int j=0; j<binDataVector.size(); j++){
				
					int minMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(0)).intValue();
					int maxMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(1)).intValue();
				
					float min = (float)Math.pow(10, minMag);
					float max = (float)Math.pow(10, maxMag);
					
					if(value>=min && value<max){
					
						if(((Boolean)((Vector)binDataVector.elementAt(j)).elementAt(2)).booleanValue()){
						
							color = (Color)((Vector)binDataVector.elementAt(j)).elementAt(3);
							break binFound;
						
						}else{
							
							color = null;
						
						}	
					
					}
				
				}  
				
				if(color!=null){
					
					redArray[i] = (short)color.getRed();
					greenArray[i] = (short)color.getGreen();
					blueArray[i] = (short)color.getBlue();
				
				}else{
					
					redArray[i] = -1;
					greenArray[i] = -1;
					blueArray[i] = -1;
				
				}
			
			}	

			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setRedArray(redArray);
			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setGreenArray(greenArray);
			ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].setBlueArray(blueArray);

	    }
	    
	    if(schemeFlux.equals("Continuous")){
	    
	    	x0R_flux = ds.getIntFluxColorValues()[0];
		    x0G_flux = ds.getIntFluxColorValues()[1];
		   	x0B_flux = ds.getIntFluxColorValues()[2];
		    aR_flux = ds.getIntFluxColorValues()[3];
		    aG_flux = ds.getIntFluxColorValues()[4];
		    aB_flux = ds.getIntFluxColorValues()[5];

			short[] redArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			short[] greenArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			short[] blueArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			byte[] linestyleArray = new byte[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			byte[] linewidthArray = new byte[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			
			for(int i=0; i<ds.getIntFluxNucSimDataStructure().getIntFluxArray().length; i++){
	
				color = getLogColor(Math.abs(ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i])
										, intFluxMax
										, intFluxMin
										, "Integrated Flux");
				
				redArray[i] = (short)color.getRed();
				greenArray[i] = (short)color.getGreen();
				blueArray[i] = (short)color.getBlue();
				linestyleArray[i] = 0;
				linewidthArray[i] = 1;
					
			}	

			ds.getIntFluxNucSimDataStructure().setIntFluxRedArray(redArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxGreenArray(greenArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxBlueArray(blueArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxLinestyleArray(linestyleArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxLinewidthArray(linewidthArray);
    
	    }else if(schemeFlux.equals("Binned")){
	    
	    	Vector binDataVector = ds.getIntFluxBinData();
    			
			short[] redArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			short[] greenArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			short[] blueArray = new short[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			byte[] linestyleArray = new byte[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
			byte[] linewidthArray = new byte[ds.getIntFluxNucSimDataStructure().getIntFluxArray().length];
		
			for(int i=0; i<ds.getIntFluxNucSimDataStructure().getIntFluxArray().length; i++){

				double value = ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i];

				binFound:
				for(int j=0; j<binDataVector.size(); j++){
				
					int minMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(0)).intValue();
					int maxMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(1)).intValue();
				
					double min = (float)Math.pow(10, minMag);
					double max = (float)Math.pow(10, maxMag);
					
					if(value>=min && value<max){
					
						if(((Boolean)((Vector)binDataVector.elementAt(j)).elementAt(2)).booleanValue()){
						
							color = (Color)((Vector)binDataVector.elementAt(j)).elementAt(3);
							redArray[i] = (short)color.getRed();
							greenArray[i] = (short)color.getGreen();
							blueArray[i] = (short)color.getBlue();	
							linestyleArray[i] = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(4)).byteValue();
							linewidthArray[i] = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(5)).byteValue();
							
							break binFound;
							
						}
				
					}else{
							
						redArray[i] = -1;
						greenArray[i] = -1;
						blueArray[i] = -1;
						linestyleArray[i] = 0;
						linewidthArray[i] = 1;
						
					}
				
				}  
				
			}	

			ds.getIntFluxNucSimDataStructure().setIntFluxRedArray(redArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxGreenArray(greenArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxBlueArray(blueArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxLinestyleArray(linestyleArray);
			ds.getIntFluxNucSimDataStructure().setIntFluxLinewidthArray(linewidthArray);

	    }

    }
    
    /**
     * Gets the final abund max.
     *
     * @return the final abund max
     */
    public float getFinalAbundMax(){
    
    	float max = 0.0f;
    	
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; i++){
				
			max = Math.max(max, ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i]);
		
		}
    	
    	return max;
    
    }
    
    /**
     * Gets the final abund min.
     *
     * @return the final abund min
     */
    public float getFinalAbundMin(){
    
    	float min = 1E38f;
    	
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; i++){
			
			if(Math.min(min, ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i])!=0.0){
				
				min = Math.min(min, ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i]);
		
			}
		
		}

    	return min;
    
    }
    
    /**
     * Gets the int flux max.
     *
     * @return the int flux max
     */
    public double getIntFluxMax(){
    
    	double max = 0.0;
    	
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getIntFluxArray().length; i++){
				
			max = Math.max(max, Math.abs(ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i]));
		
		}
    	
    	return max;
    
    }
    
    /**
     * Gets the int flux min.
     *
     * @return the int flux min
     */
    public double getIntFluxMin(){
    
    	double min = 1E100;
    	
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getIntFluxArray().length; i++){
			
			if(Math.min(min, ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i])!=0.0){
				
				min = Math.min(min, Math.abs(ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i]));
		
			}
		
		}
    		
    	return min;
    
    }
    
    /**
     * Gets the log color.
     *
     * @param value the value
     * @param max the max
     * @param min the min
     * @param type the type
     * @return the log color
     */
    public Color getLogColor(double value, double max, double min, String type){
    	
        double normValue = 0.0;
        
        double logConstant = 1.0/(log10*Math.log(max) - log10*Math.log(min));  
        
        boolean includeValues = false;
        
        if(type.equals("Final Abundance")){
        
        	includeValues = ds.getFinalAbundIncludeValues();
        
        }else if(type.equals("Integrated Flux")){
        
        	includeValues = ds.getIntFluxIncludeValues();
        
        }
          
        if(includeValues){ 
         
	        if(value!=0.0){
	
				normValue = logConstant*log10*Math.log(value) - logConstant*log10*Math.log(min);
	
	        }
	
	        return getRGB(normValue, type); 
        
    	}else{
    	
    		if(value<=max && value>=min){
    		
    			normValue = logConstant*log10*Math.log(value) - logConstant*log10*Math.log(min);
		
		        return getRGB(normValue, type); 
    		
    		}else{
    		
    			return Color.black;
    		
    		}
    	
    	}
        
    }
    
    /**
     * Gets the rGB.
     *
     * @param x the x
     * @param type the type
     * @return the rGB
     */
    public Color getRGB(double x, String type){
    	
    	if(x>=1.0){x = 1.0;}
    	if(x<=0.0){x = 0.0;}
    	
    	int red = 0;
        int green = 0;
        int blue = 0;
    	
    	if(type.equals("Final Abundance")){
    	
	        red = (int)(255*Math.exp(-(x-x0R_abund)*(x-x0R_abund)/aR_abund/aR_abund));
	        green = (int)(255*Math.exp(-(x-x0G_abund)*(x-x0G_abund)/aG_abund/aG_abund));
	        blue = (int)(255*Math.exp(-(x-x0B_abund)*(x-x0B_abund)/aB_abund/aB_abund));
        
    	}else if(type.equals("Integrated Flux")){
    		
    	    red = (int)(255*Math.exp(-(x-x0R_flux)*(x-x0R_flux)/aR_flux/aR_flux));
	        green = (int)(255*Math.exp(-(x-x0G_flux)*(x-x0G_flux)/aG_flux/aG_flux));
	        blue = (int)(255*Math.exp(-(x-x0B_flux)*(x-x0B_flux)/aB_flux/aB_flux));	
	        
    	}
        
        return new Color(red,green,blue);   
    }

    /**
     * Sets the min final abund.
     *
     * @param minFinalAbund the new min final abund
     */
    public void setMinFinalAbund(float minFinalAbund){minFieldAbund.setText(NumberFormats.getFormattedValueLong((double)minFinalAbund));}
    
    /**
     * Gets the min final abund.
     *
     * @return the min final abund
     */
    public float getMinFinalAbund(){return Float.valueOf(minFieldAbund.getText()).floatValue();}
    
    /**
     * Sets the max final abund.
     *
     * @param maxFinalAbund the new max final abund
     */
    public void setMaxFinalAbund(float maxFinalAbund){maxFieldAbund.setText(NumberFormats.getFormattedValueLong((double)maxFinalAbund));}
    
    /**
     * Gets the max final abund.
     *
     * @return the max final abund
     */
    public float getMaxFinalAbund(){return Float.valueOf(maxFieldAbund.getText()).floatValue();}
 
    /**
     * Sets the min int flux.
     *
     * @param minIntFlux the new min int flux
     */
    public void setMinIntFlux(double minIntFlux){minFieldFlux.setText(NumberFormats.getFormattedValueLong(minIntFlux));}
    
    /**
     * Gets the min int flux.
     *
     * @return the min int flux
     */
    public double getMinIntFlux(){return Double.valueOf(minFieldFlux.getText()).doubleValue();}
    
    /**
     * Sets the max int flux.
     *
     * @param maxIntFlux the new max int flux
     */
    public void setMaxIntFlux(double maxIntFlux){maxFieldFlux.setText(NumberFormats.getFormattedValueLong(maxIntFlux));}
    
    /**
     * Gets the max int flux.
     *
     * @return the max int flux
     */
    public double getMaxIntFlux(){return Double.valueOf(maxFieldFlux.getText()).doubleValue();}
    
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

		String delayString = "Please be patient while data is loaded. DO NOT operate the Integrated Flux Nuclide Chart at this time!";
		
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
    
    