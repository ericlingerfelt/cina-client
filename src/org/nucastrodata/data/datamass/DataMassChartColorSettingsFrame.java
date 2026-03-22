package org.nucastrodata.data.datamass;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.text.*;
import javax.swing.table.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.HelpFrame;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.data.datamass.DataMassChartColorSettingsTable;
import org.nucastrodata.data.datamass.DataMassRainbowPanel;
import org.nucastrodata.data.datamass.RowData;


/**
 * The Class DataMassChartColorSettingsFrame.
 */
public class DataMassChartColorSettingsFrame extends JFrame implements ItemListener, ActionListener, ChangeListener, WindowListener{

	/** The c. */
	Container c;

	/** The gbc. */
	GridBagConstraints gbc;

	/** The help button. */
	JButton defaultButton, applyButton, applyRangeButton, helpButton;
	
	/** The max field. */
	JTextField minField, maxField;
	
	/** The max slider. */
	JSlider minSlider, maxSlider;
	
	/** The num bin label. */
	JLabel topLabel, minLabel, maxLabel, colorSchemeLabelCont, colorSchemeLabelBinned, schemeLabel, numBinLabel;
	
	/** The color scheme combo box binned. */
	JComboBox colorSchemeComboBoxCont, schemeComboBox, colorSchemeComboBoxBinned; 

	/** The table pane. */
	JScrollPane sp, tablePane;

	/** The num bin spinner. */
	JSpinner numBinSpinner;
	
	/** The num bin model. */
	SpinnerNumberModel numBinModel;

	/** The data mass rainbow panel. */
	DataMassRainbowPanel dataMassRainbowPanel;
	
	/** The table. */
	DataMassChartColorSettingsTable table;

	/** The data mass chart color settings help frame. */
	public static HelpFrame dataMassChartColorSettingsHelpFrame;

	/** The num bin panel. */
	JPanel valuePanel, colorPanel, buttonPanel, sliderPanel, schemePanel, contentPanelCont, contentPanelBin, numBinPanel;

	/** The a b slider. */
	JSlider x0RSlider, x0GSlider, x0BSlider, aRSlider, aGSlider, aBSlider;
	
	/** The blue label. */
	JLabel x0RLabel, x0GLabel, x0BLabel, aRLabel, aGLabel, aBLabel, redLabel, greenLabel, blueLabel;

	/** The a b field. */
	JTextField x0RField, x0GField, x0BField, aRField, aGField, aBField;

	/** The delay dialog. */
	JDialog delayDialog;

	/** The exclude radio button. */
	JRadioButton includeRadioButton, excludeRadioButton;

	/** The button group. */
	ButtonGroup buttonGroup;

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
	private DataMassDataStructure ds;
	
	/**
	 * Instantiates a new data mass chart color settings frame.
	 *
	 * @param ds the ds
	 */
	public DataMassChartColorSettingsFrame(DataMassDataStructure ds){
	
		this.ds = ds;
	
		setTitle("Mass Model Nuclide Chart Color Scale Settings");
	
		c = getContentPane();
	
		c.setLayout(new BorderLayout());
		
		gbc = new GridBagConstraints();
		
		defaultButton = new JButton("Default Settings");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		applyButton = new JButton("Apply Settings");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		applyRangeButton = new JButton("Enter Max/Min Range");
		applyRangeButton.setFont(Fonts.buttonFont);
		applyRangeButton.addActionListener(this);
		
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
		redLabel.setFont(Fonts.textFont);

		greenLabel = new JLabel("Green");
		greenLabel.setFont(Fonts.textFont);
		
		blueLabel = new JLabel("Blue");
		blueLabel.setFont(Fonts.textFont);

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
		
		maxField = new JTextField(10);
		
		minSlider = new JSlider(JSlider.HORIZONTAL);
		minSlider.addChangeListener(this);
		
		maxSlider = new JSlider(JSlider.HORIZONTAL);
		maxSlider.addChangeListener(this);
		
		topLabel = new JLabel("<html>With this tool, you can set the floor and ceiling of the mass model nuclide chart color scale<p>and select a new color scheme for the Mass Model Evaluator by using the sliders below.</html>");
		
		schemeLabel = new JLabel("Select type of color scale: ");
		
		numBinLabel = new JLabel("Select number of bins: ");
		numBinLabel.setFont(Fonts.textFont);
		
		numBinModel = new SpinnerNumberModel(5, 1, 10, 1);

		numBinSpinner = new JSpinner(numBinModel);
		numBinSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(numBinSpinner.getEditor())).getTextField().setEditable(false);
		
		minLabel = new JLabel("Value min: ");
		minLabel.setFont(Fonts.textFont);
		
		maxLabel = new JLabel("Value max: ");
		maxLabel.setFont(Fonts.textFont);
		
		colorSchemeLabelCont = new JLabel("<html>Choose a<p>color scheme :</html>");
		colorSchemeLabelCont.setFont(Fonts.textFont);
		
		colorSchemeComboBoxCont = new JComboBox();
		colorSchemeComboBoxCont.setFont(Fonts.textFont);
		colorSchemeComboBoxCont.addItem("Rainbow 1");
		colorSchemeComboBoxCont.addItem("Rainbow 2");
		colorSchemeComboBoxCont.addItem("Fire");
		colorSchemeComboBoxCont.addItem("Purple Haze");
		colorSchemeComboBoxCont.addItem("Greyscale");
		colorSchemeComboBoxCont.addItemListener(this);
		
		colorSchemeLabelBinned = new JLabel("Choose a color scheme: ");
		colorSchemeLabelBinned.setFont(Fonts.textFont);
		
		colorSchemeComboBoxBinned = new JComboBox();
		colorSchemeComboBoxBinned.setFont(Fonts.textFont);
		colorSchemeComboBoxBinned.addItem("Color Scheme 1");
		colorSchemeComboBoxBinned.addItem("Color Scheme 2");
		colorSchemeComboBoxBinned.addItem("Color Scheme 3");
		
		dataMassRainbowPanel = new DataMassRainbowPanel(ds);
		sp = new JScrollPane(dataMassRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(50, 100));
		
		schemeComboBox = new JComboBox();
		schemeComboBox.setFont(Fonts.textFont);
		schemeComboBox.addItem("Continuous");
		schemeComboBox.addItem("Binned");
		
		table = new DataMassChartColorSettingsTable(ds);
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
		//numBinPanel.add(colorSchemeLabelBinned, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		//numBinPanel.add(colorSchemeComboBoxBinned, gbc);
		
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
		buttonPanel.add(applyRangeButton);
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
		gbc.anchor = GridBagConstraints.EAST;
		valuePanel.add(maxLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.CENTER;
		valuePanel.add(maxField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;	
		valuePanel.add(maxSlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.EAST;
		valuePanel.add(minLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.CENTER;
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
	 * Sets the format layout.
	 *
	 * @param scheme the new format layout
	 */
	public void setFormatLayout(String scheme){
	
		if(scheme.equals("Continuous")){
	
			c.remove(contentPanelBin);
			
			minLabel.setText("Value min: ");
			maxLabel.setText("Value max: ");
			
			topLabel.setText("<html>With this tool, you can set the floor and ceiling of the mass model nuclide chart color scale<p>and select a new color scheme for the Mass Model Evaluator by using the sliders below.</html>");
		
			contentPanelCont.add(valuePanel, "1, 1, c, c");
			contentPanelCont.add(colorPanel, "3, 1, c, c");
			contentPanelCont.add(sliderPanel, "5, 1, f, c");
			
			c.add(contentPanelCont, BorderLayout.CENTER);
			
			gbc.gridwidth = 1;
		
			gbc.gridx = 2;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			buttonPanel.add(applyRangeButton, gbc);
		
		}else if(scheme.equals("Binned")){
			
			buttonPanel.remove(applyRangeButton);
			
			c.remove(contentPanelCont);
			
			contentPanelBin.add(numBinPanel, BorderLayout.NORTH);
			contentPanelBin.add(tablePane, BorderLayout.CENTER);
			
			c.add(contentPanelBin, BorderLayout.CENTER);

			topLabel.setText("<html>With this tool, you can select the number of bins, edit the interval min and max,<p>and choose the bin color.</html>");
			
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
		
			dataMassRainbowPanel.setScheme(scheme);
			dataMassRainbowPanel.setRGB(ds.getColorValues());	
			dataMassRainbowPanel.repaint();
		
			setFormatLayout(scheme);
			setSize(862, 400);
			
			minField.setText(Cina.dataMassFrame.dataMassChartFrame.minField.getText());
			maxField.setText(Cina.dataMassFrame.dataMassChartFrame.maxField.getText());
			
			minSlider.setMinimum(getIntegerValue(Cina.dataMassFrame.dataMassChartFrame.getMinValue()));
			minSlider.setMaximum(getIntegerValue(Cina.dataMassFrame.dataMassChartFrame.getMaxValue()));
			minSlider.setValue(getIntegerValue(Double.valueOf(Cina.dataMassFrame.dataMassChartFrame.minField.getText()).doubleValue()));
			
			maxSlider.setMinimum(getIntegerValue(Cina.dataMassFrame.dataMassChartFrame.getMinValue()));
			maxSlider.setMaximum(getIntegerValue(Cina.dataMassFrame.dataMassChartFrame.getMaxValue()));
			maxSlider.setValue(getIntegerValue(Double.valueOf(Cina.dataMassFrame.dataMassChartFrame.maxField.getText()).doubleValue()));

			if(ds.getIncludeValues()){
			
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}else{
			
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}
			
		}else if(scheme.equals("Binned")){
			
			setFormatLayout(scheme);
			
			int numBin = 0;
			
			numBin = ds.getBinData().size();
		
			numBinModel.setValue(new Integer(numBin));
			table.setCurrentState(numBin);
			
			setSize(getPreferredSize());
		
		}
	
		
				
		validate();
	
	}
	
	/**
	 * Gets the integer value.
	 *
	 * @param inputValue the input value
	 * @return the integer value
	 */
	public int getIntegerValue(double inputValue){
	
		int value = 0;
		
		double normValue = 0.0;
		
		normValue = setScale(inputValue
								, Cina.dataMassFrame.dataMassChartFrame.getMaxValue()
								, Cina.dataMassFrame.dataMassChartFrame.getMinValue());
								
		value = (int)Math.round(normValue*1000);

		return value;
	
	}
    
    /**
     * Sets the scale.
     *
     * @param value the value
     * @param max the max
     * @param min the min
     * @return the double
     */
    public double setScale(double value, double max, double min){
    	
        double normValue = (1/(max-min))*(value-min);
        
        return normValue;
            
 
    }
   
	/**
	 * Gets the double value.
	 *
	 * @param sliderValue the slider value
	 * @return the double value
	 */
	public double getDoubleValue(int sliderValue){
	
		double value = 0.0;
		
		double normValue = ((double)sliderValue)/1000.0;
		
		value = Cina.dataMassFrame.dataMassChartFrame.getMinValue()
					+ (normValue*(Cina.dataMassFrame.dataMassChartFrame.getMaxValue() 
					- Cina.dataMassFrame.dataMassChartFrame.getMinValue()));
		
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
			
			/*if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 1")){
				
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
		
			table.repaint();*/
			
		}else if(ie.getSource()==schemeComboBox){
		
			scheme = schemeComboBox.getSelectedItem().toString();
		
			if(scheme.equals("Continuous")){

				setCurrentState("Continuous");
			
			}else if(scheme.equals("Binned")){
				
				setCurrentState("Binned");
				
				int numBin = ds.getBinData().size();
					
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
				
				Vector columnData = new Vector();
				columnData.addElement(new RowData(1, 2, true, new Color (255,0,0)));
				columnData.addElement(new RowData(0, 1, true, new Color(195,0,0)));
				columnData.addElement(new RowData(-1, 0, true, new Color(0,0,195)));
				columnData.addElement(new RowData(-2, -1, true, new Color(0,0,255)));
			
				ds.setBinData(columnData);
				
				numBinModel.setValue(new Integer(4));
				table.setCurrentState(4);
				
			}
			
		}else if(ae.getSource()==applyButton){
		
			if(delayDialog==null){
		
				openDelayDialog(this);

				final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
						
						if(scheme.equals("Continuous")){
						
							if(goodContInterval()){
						
								ds.setScheme("Continuous");
							
								double[] tempArray = new double[6];
				
								tempArray[0] = dataMassRainbowPanel.getX0R();
								tempArray[1] = dataMassRainbowPanel.getX0G();
								tempArray[2] = dataMassRainbowPanel.getX0B();
								tempArray[3] = dataMassRainbowPanel.getAR();
								tempArray[4] = dataMassRainbowPanel.getAG();
								tempArray[5] = dataMassRainbowPanel.getAB();
							
								ds.setColorValues(tempArray);
								
								Cina.dataMassFrame.dataMassChartFrame.dataMassRainbowPanel.setScheme(scheme);
								Cina.dataMassFrame.dataMassChartFrame.dataMassRainbowPanel.setRGB(tempArray);
									
								Cina.dataMassFrame.dataMassChartFrame.minField.setText(minField.getText());
					    		Cina.dataMassFrame.dataMassChartFrame.maxField.setText(maxField.getText());
					
								ds.setIncludeValues(includeRadioButton.isSelected());
								ds.setChartColorArray(Cina.dataMassFrame.dataMassChartFrame.getChartColorArray(Cina.dataMassFrame.dataMassChartFrame.typeComboBox.getSelectedIndex(), "Continuous"));
		
								Cina.dataMassFrame.dataMassChartFrame.dataMassRainbowPanel.repaint();
								Cina.dataMassFrame.dataMassChartFrame.dataMassChartPanel.repaint();
							
							}else{
							
								closeDelayDialog();
							
								String string = "Value minimum must be less than value maximum.";
								Dialogs.createExceptionDialog(string, Cina.dataMassFrame.dataMassChartColorSettingsFrame);
							
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
							
								ds.setScheme("Binned");
								ds.setBinData(table.tableModel.getDataVector());
								
								Cina.dataMassFrame.dataMassChartFrame.dataMassRainbowPanel.setScheme(scheme);
								
								Cina.dataMassFrame.dataMassChartFrame.maxField.setText(NumberFormats.getFormattedDer(((Double)(table.tableModel.getValueAt(0, 1))).doubleValue()));
								Cina.dataMassFrame.dataMassChartFrame.minField.setText(NumberFormats.getFormattedDer(((Double)((Vector)(table.tableModel.getDataVector().elementAt(table.tableModel.getDataVector().size()-1))).elementAt(0)).doubleValue()));
								
								ds.setChartColorArray(Cina.dataMassFrame.dataMassChartFrame.getChartColorArray(Cina.dataMassFrame.dataMassChartFrame.typeComboBox.getSelectedIndex(), "Binned"));
	
								Cina.dataMassFrame.dataMassChartFrame.dataMassRainbowPanel.repaint();
								Cina.dataMassFrame.dataMassChartFrame.dataMassChartPanel.repaint();
									
							
							}else{
							
								closeDelayDialog();
							
								String string = "Bin intervals are not entered correctly. Please choose a maximum greater than the minimum indicated for each bin.";
								Dialogs.createExceptionDialog(string, Cina.dataMassFrame.dataMassChartColorSettingsFrame);
							
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
					
		}else if(ae.getSource()==applyRangeButton){
		
			try{
		
				if(Double.valueOf(minField.getText()).doubleValue()<Double.valueOf(maxField.getText()).doubleValue()){
		
					minSlider.removeChangeListener(this);
					maxSlider.removeChangeListener(this);		
					minSlider.setValue(getIntegerValue(Double.valueOf(minField.getText()).doubleValue()));
					maxSlider.setValue(getIntegerValue(Double.valueOf(maxField.getText()).doubleValue()));
					minSlider.addChangeListener(this);
					maxSlider.addChangeListener(this);
					
				}else{
				
					String string = "Quantity minimum must be less than quantity maximum.";
				
					Dialogs.createExceptionDialog(string, this);
					
				}
			
			}catch(NumberFormatException nfe){
			
				String string = "Please enter numeric values for quantity maximum and minimum.";
				
				Dialogs.createExceptionDialog(string, this);
				
				nfe.printStackTrace();
			
			}
		
		}else if(ae.getSource()==helpButton){
				
			dataMassChartColorSettingsHelpFrame = new HelpFrame("Help on Mass Model Evaluator Nuclide Chart Color Scale Settings", "DataMassColorSettings");
			dataMassChartColorSettingsHelpFrame.setVisible(true);
			
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
			
			double min = ((Double)((Vector)dataVector.elementAt(i)).elementAt(0)).doubleValue();
			double max = ((Double)((Vector)dataVector.elementAt(i)).elementAt(1)).doubleValue();
		
			if(max<=min){goodBinIntervals = false;}

		}
		
		return goodBinIntervals;
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==minSlider){
		
			minField.setText(NumberFormats.getFormattedDer(getDoubleValue(minSlider.getValue())));
		
		}else if(ce.getSource()==maxSlider){
		
			maxField.setText(NumberFormats.getFormattedDer(getDoubleValue(maxSlider.getValue())));
		
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
			
			dataMassRainbowPanel.setRGB(x0RSlider.getValue()/100.0
											, x0GSlider.getValue()/100.0
											, x0BSlider.getValue()/100.0
											, aRSlider.getValue()/100.0
											, aGSlider.getValue()/100.0
											, aBSlider.getValue()/100.0);
			
			dataMassRainbowPanel.repaint();
		
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

		String delayString = "Please be patient while applying new settings. DO NOT operate the Mass Model Evaluator Nuclide Chart Color Scale Settings interface at this time!";
		
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