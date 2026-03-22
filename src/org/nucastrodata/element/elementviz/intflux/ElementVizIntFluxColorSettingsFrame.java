package org.nucastrodata.element.elementviz.intflux;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.*;

import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.util.ElementVizRainbowPanel;
import org.nucastrodata.element.elementviz.util.FluxRowData;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxColorSettingsTable;


/**
 * The Class ElementVizIntFluxColorSettingsFrame.
 */
public class ElementVizIntFluxColorSettingsFrame extends JFrame implements ItemListener, ActionListener, ChangeListener, WindowListener{

	/** The c. */
	Container c;

	/** The gbc. */
	GridBagConstraints gbc;

	/** The help button. */
	JButton defaultButton, applyButton, helpButton;
	
	/** The max field. */
	JTextField minField, maxField;
	
	/** The max slider. */
	JSlider minSlider, maxSlider;
	
	/** The num bin label. */
	JLabel topLabel, minLabel, maxLabel, colorSchemeLabelCont, colorSchemeLabelBinned, redLabel, greenLabel, blueLabel, schemeLabel, numBinLabel;
	
	/** The color scheme combo box binned. */
	JComboBox colorSchemeComboBoxCont, schemeComboBox, colorSchemeComboBoxBinned; 

	/** The table pane. */
	JScrollPane sp, tablePane;
	
	/** The num bin spinner. */
	JSpinner numBinSpinner;
	
	/** The num bin model. */
	SpinnerNumberModel numBinModel;

	/** The element viz rainbow panel. */
	ElementVizRainbowPanel elementVizRainbowPanel;
	
	/** The table. */
	ElementVizIntFluxColorSettingsTable table;
	
	/** The element viz int flux color settings help frame. */
	public HelpFrame elementVizIntFluxColorSettingsHelpFrame;
	
	/** The num bin panel. */
	JPanel valuePanel, colorPanel, buttonPanel, sliderPanel, schemePanel, contentPanelCont, contentPanelBin, numBinPanel;

	/** The a b slider. */
	JSlider x0RSlider, x0GSlider, x0BSlider, aRSlider, aGSlider, aBSlider;
	
	/** The a b label. */
	JLabel x0RLabel, x0GLabel, x0BLabel, aRLabel, aGLabel, aBLabel;

	/** The a b field. */
	JTextField x0RField, x0GField, x0BField, aRField, aGField, aBField;

	/** The delay dialog. */
	JDialog delayDialog;

	/** The exclude radio button. */
	JRadioButton includeRadioButton, excludeRadioButton;

	/** The button group. */
	ButtonGroup buttonGroup;

	/** The log10. */
	double log10 = 0.434294482;

	/** The x0 r. */
	int x0R = 80;
    
    /** The x0 g. */
    int x0G = 60;
    
    /** The x0 b. */
    int x0B = 20;
    
    /** The a r. */
    int aR = 50;
    
    /** The a g. */
    int aG = 40;
    
    /** The a b. */
    int aB = 30;
	
	/** The scheme. */
	String scheme;
	
	/** The ds. */
	private ElementVizDataStructure ds;
	
	/**
	 * Instantiates a new element viz int flux color settings frame.
	 *
	 * @param ds the ds
	 */
	public ElementVizIntFluxColorSettingsFrame(ElementVizDataStructure ds){

		this.ds = ds;

		setTitle("Integrated Flux Color Scale Settings");

		c = getContentPane();
	
		c.setLayout(new BorderLayout());
		
		gbc = new GridBagConstraints();
		
		defaultButton = new JButton("Default Settings");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		applyButton = new JButton("Apply Settings");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		helpButton = new JButton("Help on This Interface");
        helpButton.setFont(Fonts.buttonFont);
        helpButton.addActionListener(this);
		
		x0RSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0R);
		x0RSlider.addChangeListener(this);
		x0RSlider.setPreferredSize(new Dimension(20, 130));
		
		x0GSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0G);
		x0GSlider.addChangeListener(this);
		x0GSlider.setPreferredSize(new Dimension(20, 130));
		
		x0BSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0B);
		x0BSlider.addChangeListener(this);
		x0BSlider.setPreferredSize(new Dimension(20, 130));
		
		aRSlider = new JSlider(JSlider.VERTICAL, 0, 100, aR);
		aRSlider.addChangeListener(this);
		aRSlider.setPreferredSize(new Dimension(20, 130));
		
		aGSlider = new JSlider(JSlider.VERTICAL, 0, 100, aG);
		aGSlider.addChangeListener(this);
		aGSlider.setPreferredSize(new Dimension(20, 130));
		
		aBSlider = new JSlider(JSlider.VERTICAL, 0, 100, aB);
		aBSlider.addChangeListener(this);
		aBSlider.setPreferredSize(new Dimension(20, 130));
		
		redLabel = new JLabel("Red");

		greenLabel = new JLabel("Green");
		
		blueLabel = new JLabel("Blue");

		x0RLabel = new JLabel("Position: ");
		x0RLabel.setFont(Fonts.textFont);
		
		x0GLabel = new JLabel("Position: ");
		x0GLabel.setFont(Fonts.textFont);
		
		x0BLabel = new JLabel("Position: ");
		x0BLabel.setFont(Fonts.textFont);
		
		aRLabel = new JLabel("Amount: ");
		aRLabel.setFont(Fonts.textFont);
		
		aGLabel = new JLabel("Amount: ");
		aGLabel.setFont(Fonts.textFont);
		
		aBLabel = new JLabel("Amount: ");
		aBLabel.setFont(Fonts.textFont);

		x0RField = new JTextField(5);
		x0RField.setText(String.valueOf(x0R/100.0));
		x0RField.setEditable(false);
		
		x0GField = new JTextField(5);
		x0GField.setText(String.valueOf(x0G/100.0));
		x0GField.setEditable(false);
		
		x0BField = new JTextField(5);
		x0BField.setText(String.valueOf(x0B/100.0));
		x0BField.setEditable(false);
		
		aRField = new JTextField(5);
		aRField.setText(String.valueOf(aR/100.0));
		aRField.setEditable(false);
		
		aGField = new JTextField(5);
		aGField.setText(String.valueOf(aG/100.0));
		aGField.setEditable(false);
		
		aBField = new JTextField(5);
		aBField.setText(String.valueOf(aB/100.0));
		aBField.setEditable(false);
		
		includeRadioButton = new JRadioButton("Map values outside of range to max/min color", true);
		includeRadioButton.setFont(Fonts.textFont);

		excludeRadioButton = new JRadioButton("Show only values within this range", false);
		excludeRadioButton.setFont(Fonts.textFont);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(includeRadioButton);
		buttonGroup.add(excludeRadioButton);
		
		minField = new JTextField(10);
		minField.setEditable(false);
		
		maxField = new JTextField(10);
		maxField.setEditable(false);
		
		minSlider = new JSlider(JSlider.HORIZONTAL);
		minSlider.addChangeListener(this);
		
		maxSlider = new JSlider(JSlider.HORIZONTAL);
		maxSlider.addChangeListener(this);
		
		topLabel = new JLabel("<html>With this tool, you can set the floor and ceiling of the integrated flux color scale<p>and select a new color scheme for intagrated flux by using the sliders below.</html>");
		
		schemeLabel = new JLabel("Select type of color scale: ");
		
		numBinLabel = new JLabel("Select number of bins: ");
		numBinLabel.setFont(Fonts.textFont);
		
		numBinModel = new SpinnerNumberModel(5, 1, 10, 1);

		numBinSpinner = new JSpinner(numBinModel);
		numBinSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(numBinSpinner.getEditor())).getTextField().setEditable(false);
		
		minLabel = new JLabel("Integrated Flux min: ");
		minLabel.setFont(Fonts.textFont);
		
		maxLabel = new JLabel("Integrated Flux max: ");
		maxLabel.setFont(Fonts.textFont);
		
		colorSchemeLabelCont = new JLabel("<html>Choose a<p>color scheme :</html>");
		colorSchemeLabelCont.setFont(Fonts.textFont);
		
		colorSchemeComboBoxCont = new JComboBox();
		colorSchemeComboBoxCont.addItem("Rainbow 1");
		colorSchemeComboBoxCont.addItem("Rainbow 2");
		colorSchemeComboBoxCont.addItem("Fire");
		colorSchemeComboBoxCont.addItem("Purple Haze");
		colorSchemeComboBoxCont.addItem("Greyscale");
		colorSchemeComboBoxCont.addItemListener(this);
		colorSchemeComboBoxCont.setFont(Fonts.textFont);
		
		colorSchemeLabelBinned = new JLabel("Choose a color scheme: ");
		colorSchemeLabelBinned.setFont(Fonts.textFont);
		
		colorSchemeComboBoxBinned = new JComboBox();
		colorSchemeComboBoxBinned.setFont(Fonts.textFont);
		colorSchemeComboBoxBinned.addItem("Color Scheme 1");
		colorSchemeComboBoxBinned.addItem("Color Scheme 2");
		colorSchemeComboBoxBinned.addItem("Color Scheme 3");
		
		elementVizRainbowPanel = new ElementVizRainbowPanel(ds);
		sp = new JScrollPane(elementVizRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(50, 100));
		
		schemeComboBox = new JComboBox();
		schemeComboBox.setFont(Fonts.textFont);
		schemeComboBox.addItem("Continuous");
		schemeComboBox.addItem("Binned");
		
		table = new ElementVizIntFluxColorSettingsTable(ds);
		table.validate();
		
		tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePane.setPreferredSize(new Dimension(600, 125));
		
		numBinPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		numBinPanel.add(numBinLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		numBinPanel.add(numBinSpinner, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		numBinPanel.add(colorSchemeLabelBinned, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		numBinPanel.add(colorSchemeComboBoxBinned, gbc);
		
		schemePanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		schemePanel.add(schemeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		schemePanel.add(schemeComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		schemePanel.add(topLabel, gbc);
		
		gbc.gridwidth = 1;
		
		contentPanelCont = new JPanel();
		double[] colCont = {10, TableLayoutConstants.PREFERRED
							 ,10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, 10};
		double[] rowCont = {10, TableLayoutConstants.PREFERRED, 10};
		contentPanelCont.setLayout(new TableLayout(colCont, rowCont));
		
		contentPanelBin = new JPanel(new BorderLayout());
		
		colorPanel = new JPanel();
		
		double[] colColor = {TableLayoutConstants.FILL};
		double[] rowColor = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		colorPanel.setLayout(new TableLayout(colColor, rowColor));
		colorPanel.add(colorSchemeLabelCont,    "0, 0, f, c");
		colorPanel.add(colorSchemeComboBoxCont, "0, 2, f, c");
		colorPanel.add(sp,                      "0, 4, c, f");
		
		buttonPanel = new JPanel();
		buttonPanel.add(defaultButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(helpButton);
		
		sliderPanel = new JPanel();
		double[] colSlider = {TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.FILL};
		double[] rowSlider = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL};
		sliderPanel.setLayout(new TableLayout(colSlider, rowSlider));
		
		sliderPanel.add(redLabel,     "0, 0, 2, 0, c, c");
		sliderPanel.add(greenLabel,   "4, 0, 6, 0, c, c");
		sliderPanel.add(blueLabel,    "8, 0, 10, 0, c, c");
		
		sliderPanel.add(x0RLabel,     "0, 2, c, c");
		sliderPanel.add(x0RField,     "0, 4, f, c");
		sliderPanel.add(x0RSlider,    "0, 6, f, f");
		
		sliderPanel.add(aRLabel,      "2, 2, c, c");
		sliderPanel.add(aRField,      "2, 4, f, c");
		sliderPanel.add(aRSlider,     "2, 6, f, f");
		
		sliderPanel.add(x0GLabel,     "4, 2, c, c");
		sliderPanel.add(x0GField,     "4, 4, f, c");
		sliderPanel.add(x0GSlider,    "4, 6, f, f");
		
		sliderPanel.add(aGLabel,      "6, 2, c, c");
		sliderPanel.add(aGField,      "6, 4, f, c");
		sliderPanel.add(aGSlider,     "6, 6, f, f");
		
		sliderPanel.add(x0BLabel,     "8, 2, c, c");
		sliderPanel.add(x0BField,     "8, 4, f, c");
		sliderPanel.add(x0BSlider,    "8, 6, f, f");
		
		sliderPanel.add(aBLabel,      "10, 2, c, c");
		sliderPanel.add(aBField,      "10, 4, f, c");
		sliderPanel.add(aBSlider,     "10, 6, f, f");
		
		valuePanel = new JPanel(new GridBagLayout());
			
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;	
		gbc.insets = new Insets(5, 5, 5, 5);
		valuePanel.add(maxLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;	
		valuePanel.add(maxField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;	
		valuePanel.add(maxSlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		valuePanel.add(minLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		valuePanel.add(minField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;	
		valuePanel.add(minSlider, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;	
		gbc.anchor = GridBagConstraints.WEST;
		valuePanel.add(includeRadioButton, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;	
		valuePanel.add(excludeRadioButton, gbc);
	
		gbc.anchor = GridBagConstraints.CENTER;
	
		gbc.insets = new Insets(5, 5, 5, 5);
	
		c.add(buttonPanel, BorderLayout.SOUTH);
		c.add(schemePanel, BorderLayout.NORTH);
	
	}
	
	/**
	 * Close all frames.
	 */
	public void closeAllFrames(){
		if(elementVizIntFluxColorSettingsHelpFrame!=null){
			elementVizIntFluxColorSettingsHelpFrame.setVisible(false);
			elementVizIntFluxColorSettingsHelpFrame.dispose();
		}
	}
	
	/**
	 * Sets the format layout.
	 *
	 * @param scheme the new format layout
	 */
	public void setFormatLayout(String scheme){
	
		if(scheme.equals("Continuous")){
	
			c.remove(contentPanelBin);
		
			contentPanelCont.add(valuePanel, "1, 1, c, c");
			contentPanelCont.add(colorPanel, "3, 1, c, c");
			contentPanelCont.add(sliderPanel, "5, 1, f, c");
			
			c.add(contentPanelCont, BorderLayout.CENTER);
			
			gbc.gridwidth = 1;
		
		}else if(scheme.equals("Binned")){
			
			c.remove(contentPanelCont);
			
			contentPanelBin.add(numBinPanel, BorderLayout.NORTH);
			contentPanelBin.add(tablePane, BorderLayout.CENTER);
			
			c.add(contentPanelBin, BorderLayout.CENTER);
			
			topLabel.setText("<html>With this tool, you can select the number of bins, edit the interval min and max,<p>choose the bin color, and choose the flux arrow line style and line widths.</html>");

			colorSchemeComboBoxBinned.addItemListener(this);
		
		}
		
		
				
		validate();

	}
	
	/**
	 * Sets the current state.
	 *
	 * @param scheme the new current state
	 */
	public void setCurrentState(String scheme){
	
		this.scheme = scheme;
		
		schemeComboBox.removeItemListener(this);
		schemeComboBox.setSelectedItem(scheme);
		schemeComboBox.addItemListener(this);
		
		if(scheme.equals("Continuous")){
		
			double min = 0.0;
			double max = 0.0;
		
			minSlider.removeChangeListener(this);
			maxSlider.removeChangeListener(this);
		
			elementVizRainbowPanel.setType("Integrated Flux", scheme);
			
			elementVizRainbowPanel.x0R = ds.getIntFluxColorValues()[0];
			elementVizRainbowPanel.x0G = ds.getIntFluxColorValues()[1];
			elementVizRainbowPanel.x0B = ds.getIntFluxColorValues()[2];
			elementVizRainbowPanel.aR = ds.getIntFluxColorValues()[3];
			elementVizRainbowPanel.aG = ds.getIntFluxColorValues()[4];
			elementVizRainbowPanel.aB = ds.getIntFluxColorValues()[5];
			
			min = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMin();
			max = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMax();

			elementVizRainbowPanel.repaint();
			
			setFormatLayout(scheme);
			
			minField.setText(Cina.elementVizFrame.elementVizIntFluxFrame.minFieldFlux.getText());
			maxField.setText(Cina.elementVizFrame.elementVizIntFluxFrame.maxFieldFlux.getText());
			
			minSlider.setMinimum(getIntegerValue(min, min, max));											
			minSlider.setMaximum(getIntegerValue(max, min, max));
													
			minSlider.setValue(getIntegerValue(Double.valueOf(Cina.elementVizFrame.elementVizIntFluxFrame.minFieldFlux.getText()).doubleValue()
													, min, max));
			
			maxSlider.setMinimum(getIntegerValue(min, min, max));
			maxSlider.setMaximum(getIntegerValue(max, min, max));
			
			maxSlider.setValue(getIntegerValue(Double.valueOf(Cina.elementVizFrame.elementVizIntFluxFrame.maxFieldFlux.getText()).doubleValue()
													, min, max));

			if(ds.getIntFluxIncludeValues()){
		
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}else{
			
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}

			minSlider.addChangeListener(this);
			maxSlider.addChangeListener(this);

			setSize(862, 400);
		
		}else if(scheme.equals("Binned")){
		
			setFormatLayout(scheme);
		
			int numBin = 0;
				
			numBin = ds.getIntFluxBinData().size();

			numBinModel.setValue(new Integer(numBin));
			table.setCurrentState(numBin);
			
			setSize(getPreferredSize());
		
		}
		
		
				
		validate();

	}
	
	/**
	 * Gets the integer value.
	 *
	 * @param value the value
	 * @param min the min
	 * @param max the max
	 * @return the integer value
	 */
	public int getIntegerValue(double value, double min, double max){
	
		int intValue = 0;
		
		double normValue = 0.0;
		
		double C = 1.0/(log10*Math.log(max/min));

		if(value!=0.0){
        	
            normValue = C*log10*Math.log(value) - C*log10*Math.log(min);
            
        }
		
		intValue = (int)Math.round(normValue*1000);
		
		return intValue;
	
	}
	
	/**
	 * Gets the double value.
	 *
	 * @param sliderValue the slider value
	 * @param min the min
	 * @param max the max
	 * @return the double value
	 */
	public double getDoubleValue(int sliderValue, double min, double max){
	
		double value = 0.0;
		
		double normValue = ((double)sliderValue)/1000.0;
		
		double constant = log10*Math.log(max/min);
		
		double exponent = constant*normValue + log10*Math.log(min);
		
		value = Math.pow(10, exponent);
		
		return value;
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){

		if(ie.getSource()==colorSchemeComboBoxCont){
		
			if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Greyscale")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(100);
				x0BSlider.setValue(100);
				aRSlider.setValue(100);
				aGSlider.setValue(100);
				aBSlider.setValue(100);
			
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Rainbow 1")){
			
				x0RSlider.setValue(80);
				x0GSlider.setValue(60);
				x0BSlider.setValue(20);
				aRSlider.setValue(50);
				aGSlider.setValue(40);
				aBSlider.setValue(30);
			
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Rainbow 2")){
				
				x0RSlider.setValue(44);
				x0GSlider.setValue(2);
				x0BSlider.setValue(100);
				aRSlider.setValue(25);
				aGSlider.setValue(32);
				aBSlider.setValue(37);
				
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Purple Haze")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(0);
				x0BSlider.setValue(100);
				aRSlider.setValue(84);
				aGSlider.setValue(0);
				aBSlider.setValue(84);
			
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Fire")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(97);
				x0BSlider.setValue(0);
				aRSlider.setValue(71);
				aGSlider.setValue(33);
				aBSlider.setValue(0);
			
			}
		
		}else if(ie.getSource()==colorSchemeComboBoxBinned){
		
			if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 1")){
			
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(255-(i*40),0,0), i, 3);
				
				}
			
			}else if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 2")){
			
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(0,255-(i*40),0), i, 3);
				
				}
			
			}else if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 3")){
			
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(0, 0, 255-(i*40)), i, 3);
				
				}
			
			}
			
			table.repaint();
		
		}else if(ie.getSource()==schemeComboBox){
		
			scheme = schemeComboBox.getSelectedItem().toString();
		
			if(scheme.equals("Continuous")){

				setCurrentState("Continuous");
			
			}else if(scheme.equals("Binned")){
				
				setCurrentState("Binned");
				
				int numBin = ds.getFluxBinData().size();
				
				numBinModel.setValue(new Integer(numBin));

				table.setCurrentState(numBinModel.getNumber().intValue());
			
			}
		
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==defaultButton){
		
			if(scheme.equals("Continuous")){
		
				minSlider.setValue(0);
				maxSlider.setValue(1000);
				
				x0RSlider.setValue(80);
				x0GSlider.setValue(60);
				x0BSlider.setValue(20);
				aRSlider.setValue(50);
				aGSlider.setValue(40);
				aBSlider.setValue(30);
				
				colorSchemeComboBoxCont.removeItemListener(this);
				colorSchemeComboBoxCont.setSelectedItem("Rainbow");
				colorSchemeComboBoxCont.addItemListener(this);
			
			}else if(scheme.equals("Binned")){
			
				colorSchemeComboBoxBinned.removeItemListener(this);
				colorSchemeComboBoxBinned.setSelectedItem("Color Scheme 1");
				colorSchemeComboBoxBinned.addItemListener(this);
				
				Vector fluxColumnData = new Vector();
				fluxColumnData.addElement(new FluxRowData(-1, 0, true, new Color (255,0,0), 0, 1));
				fluxColumnData.addElement(new FluxRowData(-2, -1, true, new Color(215,0,0), 0, 1));
				fluxColumnData.addElement(new FluxRowData(-3, -2, true, new Color(175,0,0), 0, 1));
				fluxColumnData.addElement(new FluxRowData(-4, -3, true, new Color(135,0,0), 0, 1));
				fluxColumnData.addElement(new FluxRowData(-5, -4, true, new Color(95,0,0), 0, 1));
				
				ds.setFluxBinData(fluxColumnData);
							
				numBinModel.setValue(new Integer(5));
				table.setCurrentState(5);
			
			}
			
		}else if(ae.getSource()==applyButton){

			if(delayDialog==null){

				openDelayDialog(this);
				
				final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
						
						if(scheme.equals("Continuous")){
	
							if(goodContInterval()){
						
								ds.setIntFluxScheme("Continuous");
						
								double[] tempArray = new double[6];
			
								tempArray[0] = elementVizRainbowPanel.x0R;
								tempArray[1] = elementVizRainbowPanel.x0G;
								tempArray[2] = elementVizRainbowPanel.x0B;
								tempArray[3] = elementVizRainbowPanel.aR;
								tempArray[4] = elementVizRainbowPanel.aG;
								tempArray[5] = elementVizRainbowPanel.aB;
							
								ds.setIntFluxColorValues(tempArray);
								
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizRainbowPanelFlux.setType("Integrated Flux", scheme);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizRainbowPanelFlux.setRGB(tempArray);
								Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMin = Float.valueOf(minField.getText()).floatValue();
								Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMax = Float.valueOf(maxField.getText()).floatValue();
								Cina.elementVizFrame.elementVizIntFluxFrame.setMinIntFlux(Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMin);
					    		Cina.elementVizFrame.elementVizIntFluxFrame.setMaxIntFlux(Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMax);
								
								ds.setIntFluxIncludeValues(includeRadioButton.isSelected());
								
								Cina.elementVizFrame.elementVizIntFluxFrame.setChartColors(scheme, ds.getFinalAbundScheme());
	
								Cina.elementVizFrame.elementVizIntFluxFrame.setFormatLayout(scheme);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxPanel.setCurrentState(ds.getFinalAbundScheme(), scheme);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxPanel.repaint();
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizRainbowPanelFlux.repaint();
				
							}else{
						
								closeDelayDialog();
							
								String string = "Integrated flux minimum must be less than integrated flux maximum.";
								Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxColorSettingsFrame);
							
							}
					
						}else if(scheme.equals("Binned")){
							
							if(table.isEditing()){
							
								for(int i=0; i<table.getRowCount(); i++){
			
									for(int j=0; j<table.getColumnCount(); j++){
										
										table.getCellEditor(i, j).stopCellEditing();
										
									}
									
								}
							
							}
							
							if(goodBinIntervals(table.tableModel.getDataVector())){
							
								ds.setIntFluxScheme(scheme);
								ds.setIntFluxBinData(table.tableModel.getDataVector());
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.setDataVector(ds.getIntFluxBinData());
								Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMax = (float)Math.pow(10,((Integer)(table.tableModel.getValueAt(0, 1))).intValue());
								Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMin = (float)Math.pow(10,((Integer)((Vector)(table.tableModel.getDataVector().elementAt(table.tableModel.getDataVector().size()-1))).elementAt(0)).intValue());
								Cina.elementVizFrame.elementVizIntFluxFrame.setMinIntFlux(Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMin);
					    		Cina.elementVizFrame.elementVizIntFluxFrame.setMaxIntFlux(Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMax);							
								
								Cina.elementVizFrame.elementVizIntFluxFrame.setChartColors(scheme, ds.getFinalAbundScheme());
	
								Cina.elementVizFrame.elementVizIntFluxFrame.setFormatLayout(scheme);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxPanel.setCurrentState(ds.getFinalAbundScheme(), scheme);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxPanel.repaint();
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.setSize(200, (table.tableModel.getDataVector().size()-1)*15+50);
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.validate();
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.setPreferredSize(Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.getSize());
								Cina.elementVizFrame.elementVizIntFluxFrame.elementVizBinnedFluxPanel.revalidate();
							
							}else{
							
								closeDelayDialog();
							
								String string = "Bin intervals are not entered correctly. Please choose a maximum greater than the minimum indicated for each bin.";
								Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIntFluxColorSettingsFrame);
							
							}
						
						}
										
						return new Object();
						
					}
					
					public void finished(){
						
						closeDelayDialog();
							
					}
					
				};
				
				worker.start();
				
			}
		
		}else if(ae.getSource()==helpButton){
				
			elementVizIntFluxColorSettingsHelpFrame = new HelpFrame("Help on Integrated Flux Color Scale Settings", "ElementVizFluxColorSettings");
			elementVizIntFluxColorSettingsHelpFrame.setVisible(true);
			
		}
	
	}

	/**
	 * Good cont interval.
	 *
	 * @return true, if successful
	 */
	public boolean goodContInterval(){
	
		boolean goodContInterval = true;
		
		double min = Double.valueOf(minField.getText()).doubleValue();
		double max = Double.valueOf(maxField.getText()).doubleValue();
		
		if(min>=max){goodContInterval = false;}
		
		return goodContInterval;
	
	}

	/**
	 * Good bin intervals.
	 *
	 * @param dataVector the data vector
	 * @return true, if successful
	 */
	public boolean goodBinIntervals(Vector dataVector){
	
		boolean goodBinIntervals = true;
		
		for(int i=0; i<dataVector.size(); i++){
			
			int min = ((Integer)((Vector)dataVector.elementAt(i)).elementAt(0)).intValue();
			int max = ((Integer)((Vector)dataVector.elementAt(i)).elementAt(1)).intValue();
		
			if(max<=min){goodBinIntervals = false;}

		}
		
		return goodBinIntervals;
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==minSlider){
		
			double min = 0.0;
			double max = 0.0;

			min = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMin();
			max = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMax();

			elementVizRainbowPanel.setType("Integrated Flux", scheme);
			elementVizRainbowPanel.repaint();
		
			minField.setText(NumberFormats.getFormattedValueLong(getDoubleValue(minSlider.getValue(), min, max)));

		}else if(ce.getSource()==maxSlider){
		
			double min = 0.0;
			double max = 0.0;
			
			min = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMin();
			max = Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMax();

			maxField.setText(NumberFormats.getFormattedValueLong(getDoubleValue(maxSlider.getValue(), min, max)));

			elementVizRainbowPanel.setType("Integrated Flux", scheme);
			elementVizRainbowPanel.repaint();

		}else if(ce.getSource()==x0RSlider
					|| ce.getSource()==x0GSlider
					|| ce.getSource()==x0BSlider
					|| ce.getSource()==aRSlider
					|| ce.getSource()==aGSlider
					|| ce.getSource()==aBSlider){
		
			x0RField.setText(String.valueOf(x0RSlider.getValue()/100.0));
			x0GField.setText(String.valueOf(x0GSlider.getValue()/100.0));
			x0BField.setText(String.valueOf(x0BSlider.getValue()/100.0));
			aRField.setText(String.valueOf(aRSlider.getValue()/100.0));
			aGField.setText(String.valueOf(aGSlider.getValue()/100.0));
			aBField.setText(String.valueOf(aBSlider.getValue()/100.0));
			
			elementVizRainbowPanel.x0R = x0RSlider.getValue()/100.0;
			elementVizRainbowPanel.x0G = x0GSlider.getValue()/100.0;
			elementVizRainbowPanel.x0B = x0BSlider.getValue()/100.0;
			elementVizRainbowPanel.aR = aRSlider.getValue()/100.0;
			elementVizRainbowPanel.aG = aGSlider.getValue()/100.0;
			elementVizRainbowPanel.aB = aBSlider.getValue()/100.0;
			
			elementVizRainbowPanel.repaint();
		
		}else if(ce.getSource()==numBinSpinner){

			table.setCurrentState(numBinModel.getNumber().intValue());
		
		}
	
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

		String delayString = "Please be patient while applying new settings. DO NOT operate the Integrated Flux Color Scale Settings interface at this time!";
		
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