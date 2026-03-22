package org.nucastrodata.element.elementviz.animator;

import info.clearthought.layout.*;
import java.awt.*;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.datastructure.feature.SumDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.element.elementviz.ElementVizBinnedFluxPanel;
import org.nucastrodata.element.elementviz.ElementVizChartRangeDialog;
import org.nucastrodata.element.elementviz.ElementVizIsotopePanel;
import org.nucastrodata.element.elementviz.ElementVizPointFrame;
import org.nucastrodata.element.elementviz.ElementVizReactionPanel;
import org.nucastrodata.element.elementviz.util.ElementVizRainbowPanel;
import java.awt.geom.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.TextSaver;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorColorSettingsFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorExportFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorPanel;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorPrintPanel;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorUpdateTask;
import org.nucastrodata.element.elementviz.animator.WaitingColorPanel;
import org.nucastrodata.element.elementviz.animator.bottle.BottleFrame;
import org.nucastrodata.element.elementviz.animator.flux.FluxFrame;
import org.nucastrodata.element.elementviz.animator.sum.SumFrame;
import org.nucastrodata.element.elementviz.animator.wait.WaitingFrame;
import org.nucastrodata.format.SizedComboBox;
import org.nucastrodata.wizard.gui.WordWrapLabel;


/**
 * The Class ElementVizAnimatorFrame.
 */
public class ElementVizAnimatorFrame extends JFrame implements ActionListener, ChangeListener, WindowListener, ItemListener{

    /** The sp5. */
    JScrollPane sp, sp2, sp3, sp4, sp5;
    
    /** The c. */
    Container c;
    
    int zmin, zmax;

    /** The zoom control label. */
    JLabel nucSimLabel, currentLabel, tempLabel, densityLabel, timeLabel, timeStepLabel
    				, valueLabel, maxLabel, minLabel, fpsLabel, timeStepSliderLabel, timeStepminLabel, timeStepmaxLabel
    				, gotoTimeStepLabel, zoomLabel, typeLabel, zoomControlLabel;
    				
   	/** The zoom field. */
	JTextField tempField, densityField, timeField, timeStepField
   					, valueField, maxField, minField, fpsField
   					, timeStepminField, timeStepmaxField, gotoTimeStepField, zoomField, sumField;
   					
   	/** The bottle major box. */
	JCheckBox loopCheckBox, showLabelsCheckBox, showStableCheckBox
   				, showOutlineCheckBox, waitingYesBox, waitingMaybeBox
   				, bottleMinorBox, bottleMajorBox;
   		
   	/** The bottle color major button. */
	JButton playButton, stopButton, applyButton, exportButton, stepForwardButton
   				, stepBackButton, defaultButton, colorButton, saveButton, okButton, pointButton
   				, waitingButton, waitingYesButton, waitingMaybeButton
   				, bottleButton, bottleColorMinorButton, bottleColorMajorButton
   				, sumButton, fluxButton, abundButton, rangeButton;
   	
   	/** The zoom slider. */
	JSlider timeStepSlider, zoomSlider;
    
   	/** The type combo box. */
	SizedComboBox nucSimComboBox, typeComboBox;

	/** The bottom panel2. */
	JPanel rightPanel, bottomPanel, buttonPanel, waitingPanel, bottlePanel, bottomPanel2, sumPanel;

	/** The bottle color major panel. */
	WaitingColorPanel yesPanel, maybePanel, bottleColorMinorPanel, bottleColorMajorPanel;
	
    /** The element viz animator panel. */
    ElementVizAnimatorPanel elementVizAnimatorPanel;
    
    /** The element viz rainbow panel. */
    ElementVizRainbowPanel elementVizRainbowPanel;
    
    /** The element viz binned flux panel. */
    ElementVizBinnedFluxPanel elementVizBinnedFluxPanel;
    
    /** The element viz reaction panel. */
    ElementVizReactionPanel elementVizReactionPanel;
    
    /** The element viz isotope panel. */
    ElementVizIsotopePanel elementVizIsotopePanel;
    
    /** The point frame. */
    ElementVizPointFrame pointFrame;
    
    /** The waiting frame. */
    public WaitingFrame waitingFrame;
    
    /** The bottle frame. */
    public BottleFrame bottleFrame;
    
    public SumFrame sumFrame;
    
    public FluxFrame fluxFrame;
    
    /** The element viz animator export frame. */
    public ElementVizAnimatorExportFrame elementVizAnimatorExportFrame;
    
    /** The element viz animator color settings frame. */
    public ElementVizAnimatorColorSettingsFrame elementVizAnimatorColorSettingsFrame;

    /** The timer. */
    public java.util.Timer timer;
    
    /** The flux min. */
    float abundMax, abundMin, derAbundMax, derAbundMin, fluxMax, fluxMin;
    
    /** The black text radio button. */
    JRadioButton whiteTextRadioButton, blackTextRadioButton;
    
    /** The der abund mag. */
    int derAbundMag = -30;
    
    /** The normal time. */
    public double normalTime;
    
    /** The save dialog. */
    JDialog delayDialog, saveDialog;
    
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
    
    /** The x0 r_der. */
    double x0R_der = 1.0;
    
    /** The x0 g_der. */
    double x0G_der = 0.0;
    
    /** The x0 b_der. */
    double x0B_der = 0.0;
    
    /** The a r_der. */
    double aR_der = 0.5;
    
    /** The a g_der. */
    double aG_der = 0.0;
    
    /** The a b_der. */
    double aB_der = 0.5; 
    
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
    
    /** The color vector_abund. */
    Vector colorVector_abund = new Vector();
    
    /** The color vector_der. */
    Vector colorVector_der = new Vector();
    
    /** The color vector_flux. */
    Vector colorVector_flux = new Vector();
    
    /** The mag vector_abund. */
    Vector magVector_abund = new Vector();
    
    /** The mag vector_der. */
    Vector magVector_der = new Vector();
    
    /** The mag vector_flux. */
    Vector magVector_flux = new Vector();
    
    /** The N ruler. */
    IsotopeRuler ZRuler, NRuler;
     
    /** The type. */
    String type = "";
    
    /** The scheme. */
    String scheme = "";
    
    /** The simulation. */
    String simulation = "";
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /** The warning label. */
    private WordWrapLabel warningLabel;
    
    /**
     * Instantiates a new element viz animator frame.
     *
     * @param ds the ds
     */
    public ElementVizAnimatorFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
    	double gap = 5;
    	double[] columnMain = {gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED, gap};

    	double[] rowMain = {gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED, gap};
    	
        c.setLayout(new TableLayout(columnMain, rowMain));

		setTitle("Element Synthesis Animator");
		setSize(new Dimension(1110, 730));
		
		//WAITING////////////////////////////////////////////////////////////////////////
		
		double[] columnWaiting = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		double[] rowWaiting = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		double[] columnBottle = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};

		double[] rowBottle = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		double[] columnSum = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL};
		
		double[] rowSum = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		
		waitingPanel = new JPanel(new TableLayout(columnWaiting, rowWaiting));
		bottlePanel = new JPanel(new TableLayout(columnBottle, rowBottle));
		sumPanel = new JPanel(new TableLayout(columnSum, rowSum));
		
		JLabel waitingLabel = new JLabel("Waiting Points");
		JLabel bottleLabel = new JLabel("Bottleneck Reactions");
		JLabel sumLabel = new JLabel("Abundance Sum");
		JLabel sumOutputLabel = new JLabel("Abundance Sum:");
		
		bottleColorMinorPanel = new WaitingColorPanel();
		bottleColorMinorPanel.setColor(Color.GRAY);
		JScrollPane bottleMinorPane = new JScrollPane(bottleColorMinorPanel);
		
		bottleColorMajorPanel = new WaitingColorPanel();
		bottleColorMajorPanel.setColor(Color.GRAY);
		JScrollPane bottleMajorPane = new JScrollPane(bottleColorMajorPanel);
		
		maybePanel = new WaitingColorPanel();
		maybePanel.setColor(Color.GRAY);
		JScrollPane maybePane = new JScrollPane(maybePanel);
		
		yesPanel = new WaitingColorPanel();
		yesPanel.setColor(Color.GRAY);
		JScrollPane yesPane = new JScrollPane(yesPanel);
		
		bottleMajorBox = new JCheckBox("Show Major Bottlenecks", false);
		bottleMajorBox.setEnabled(false);
		bottleMajorBox.addActionListener(this);
		bottleMajorBox.setFont(Fonts.textFont);
		
		bottleMinorBox = new JCheckBox("Show Minor Bottlenecks", false);
		bottleMinorBox.setEnabled(false);
		bottleMinorBox.addActionListener(this);
		bottleMinorBox.setFont(Fonts.textFont);
		
		waitingYesBox = new JCheckBox("Show Waiting Points", false);
		waitingYesBox.setEnabled(false);
		waitingYesBox.addActionListener(this);
		waitingYesBox.setFont(Fonts.textFont);
		
		waitingMaybeBox = new JCheckBox("Ambiguous", false);
		waitingMaybeBox.setEnabled(false);
		waitingMaybeBox.addActionListener(this);
		waitingMaybeBox.setFont(Fonts.textFont);
		
		waitingButton = new JButton("Waiting Point Finder");
		waitingButton.setFont(Fonts.buttonFont);
		waitingButton.addActionListener(this);
		
		bottleButton = new JButton("Bottleneck Reaction Finder");
		bottleButton.setFont(Fonts.buttonFont);
		bottleButton.addActionListener(this);
		
		sumButton = new JButton("Abundance Summer");
		sumButton.setFont(Fonts.buttonFont);
		sumButton.addActionListener(this);
		
		rangeButton = new JButton("Set Chart Range");
		rangeButton.setFont(Fonts.buttonFont);
		rangeButton.addActionListener(this);
		
		pointButton = new JButton("Point File Formatter");
		pointButton.setFont(Fonts.buttonFont);
		pointButton.addActionListener(this);
		
		fluxButton = new JButton("Table of Reaction Flux Values");
		fluxButton.setFont(Fonts.buttonFont);
		fluxButton.addActionListener(this);
		
		bottleColorMinorButton = new JButton("Set Color");
		bottleColorMinorButton.setFont(Fonts.buttonFont);
		bottleColorMinorButton.addActionListener(this);
		bottleColorMinorButton.setEnabled(false);
		
		bottleColorMajorButton = new JButton("Set Color");
		bottleColorMajorButton.setFont(Fonts.buttonFont);
		bottleColorMajorButton.addActionListener(this);
		bottleColorMajorButton.setEnabled(false);
		
		waitingYesButton = new JButton("Set Color");
		waitingYesButton.setFont(Fonts.buttonFont);
		waitingYesButton.addActionListener(this);
		waitingYesButton.setEnabled(false);
		
		waitingMaybeButton = new JButton("Ambiguous Color");
		waitingMaybeButton.setFont(Fonts.buttonFont);
		waitingMaybeButton.addActionListener(this);
		waitingMaybeButton.setEnabled(false);
		
		waitingPanel.add(waitingYesBox, "0, 0");
		waitingPanel.add(yesPane, "2, 0");
		waitingPanel.add(waitingYesButton, "4, 0");
		waitingPanel.add(waitingButton, "0, 2, 4, 2, c, c");
		
		bottlePanel.add(bottleMajorBox, "0, 0");
		bottlePanel.add(bottleMajorPane, "2, 0");
		bottlePanel.add(bottleColorMajorButton, "4, 0");
		bottlePanel.add(bottleMinorBox, "0, 2");
		bottlePanel.add(bottleMinorPane, "2, 2");
		bottlePanel.add(bottleColorMinorButton, "4, 2");
		bottlePanel.add(bottleButton, "0, 4, 4, 4, c, c");
		
		sumField = new JTextField(20);
		sumField.setEditable(false);
		
		sumPanel.add(sumOutputLabel, "0, 0, r, c");
		sumPanel.add(sumField,       "2, 0, f, c");
		sumPanel.add(sumButton,       "0, 2, 2, 2, c, c");
		
		abundButton = new JButton("Export All Abundances");
		abundButton.addActionListener(this);
		
		//LABELS//////////////////////////////////////////////LABELS//////////////////////
		nucSimLabel = new JLabel("Choose Simulation: ");
		
		typeLabel = new JLabel("Choose Quantity: ");
		
		currentLabel = new JLabel("Current Conditions");
		
		timeStepLabel = new JLabel("Timestep: ");
		timeStepLabel.setFont(Fonts.textFont);
		
		tempLabel = new JLabel("Temp: ");
		tempLabel.setFont(Fonts.textFont);
		
		densityLabel = new JLabel("Density: ");
		densityLabel.setFont(Fonts.textFont);
		
		timeLabel = new JLabel("Time: ");
		timeLabel.setFont(Fonts.textFont);
		
		warningLabel = new WordWrapLabel(false);
		warningLabel.setText("Selected Isotope: ");
    	
    	valueLabel = new JLabel("Selected Abundance: ");
    	valueLabel.setFont(Fonts.textFont);
    	
    	maxLabel = new JLabel("Abundance Max: ");
    	maxLabel.setFont(Fonts.textFont);
    	
    	minLabel = new JLabel("Abundance Min: ");
    	minLabel.setFont(Fonts.textFont);
    	
    	fpsLabel = new JLabel("Timesteps per Second: ");
		fpsLabel.setFont(Fonts.textFont);
		
		timeStepSliderLabel = new JLabel("Timestep Control: ");
		timeStepSliderLabel.setFont(Fonts.textFont);
		
		timeStepminLabel = new JLabel("Timestep min: ");
		timeStepminLabel.setFont(Fonts.textFont);
		
		timeStepmaxLabel = new JLabel("Timestep max: ");
		timeStepmaxLabel.setFont(Fonts.textFont);
    	
    	gotoTimeStepLabel = new JLabel("Goto Timestep: ");
		gotoTimeStepLabel.setFont(Fonts.textFont);
		
		zoomLabel = new JLabel("Zoom (%): ");
		zoomLabel.setFont(Fonts.textFont);
		
		zoomControlLabel = new JLabel("Zoom (%): ");
		zoomControlLabel.setFont(Fonts.textFont);
		
		//COMBOBOX//////////////////////////////////////////////COMBOBOX/////////////////////
		nucSimComboBox = new SizedComboBox();
		nucSimComboBox.setFont(Fonts.textFont);
		nucSimComboBox.addItemListener(this);
		
		typeComboBox = new SizedComboBox();
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.addItemListener(this);
		
		//FIELD/////////////////////////////////////////////////FIELDS///////////////////////
		timeStepField = new JTextField(8);
		timeStepField.setEditable(false);
		
		tempField = new JTextField(8);
		tempField.setEditable(false);
		
		densityField = new JTextField(8);
		densityField.setEditable(false);
		
		timeField = new JTextField(8);
		timeField.setEditable(false);
   		
   		valueField = new JTextField(8);
   		valueField.setEditable(false);
   		
   		maxField = new JTextField(8);
   		maxField.setEditable(false);
   		
   		minField = new JTextField(8);
   		minField.setEditable(false);
   		
   		fpsField = new JTextField(5);
		
		timeStepminField = new JTextField(5);
		
		timeStepmaxField = new JTextField(5);
		
		gotoTimeStepField = new JTextField(5);
		
		zoomField = new JTextField(3);
		zoomField.setEditable(false);
		
		//CHECKBOX//////////////////////////////////////////////CHECKBOX////////////////////
		loopCheckBox = new JCheckBox("Loop Playback", false);
		loopCheckBox.setFont(Fonts.textFont);
		
		showLabelsCheckBox = new JCheckBox("Isotope Labels", false);
		showLabelsCheckBox.setFont(Fonts.textFont);
		showLabelsCheckBox.addItemListener(this);
		
		showStableCheckBox = new JCheckBox("Stable Isotopes", false);
		showStableCheckBox.setFont(Fonts.textFont);
		showStableCheckBox.addItemListener(this);
		
		showOutlineCheckBox = new JCheckBox("Isotope Box Outline", true);
		showOutlineCheckBox.setFont(Fonts.textFont);
		showOutlineCheckBox.addItemListener(this);
		
		//SLIDERS///////////////////////////////////////////////SLIDERS/////////////////////
		timeStepSlider = new JSlider(JSlider.HORIZONTAL);
		timeStepSlider.addChangeListener(this);
		
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		zoomSlider.addChangeListener(this);
		
		//BUTTONS//////////////////////////////////////////////BUTTONS////////////////////////
		playButton = new JButton("Play");
		playButton.setFont(Fonts.buttonFont);
		playButton.addActionListener(this);
		
		saveButton = new JButton("Save as Image");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		stopButton = new JButton("Stop");
		stopButton.setFont(Fonts.buttonFont);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		
		stepForwardButton = new JButton("Step Forward");
		stepForwardButton.setFont(Fonts.buttonFont);
		stepForwardButton.addActionListener(this);
		
		stepBackButton = new JButton("Step Backward");
		stepBackButton.setFont(Fonts.buttonFont);
		stepBackButton.addActionListener(this);
		
		applyButton = new JButton("Apply Timestep Settings");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		defaultButton = new JButton("Default Settings");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		exportButton = new JButton("Movie Maker");
		exportButton.setFont(Fonts.buttonFont);
		exportButton.addActionListener(this);
		
		colorButton = new JButton("Set Color Scale Settings");
		colorButton.setFont(Fonts.buttonFont);
		colorButton.addActionListener(this);
		
		
		elementVizRainbowPanel = new ElementVizRainbowPanel(ds);
		sp2 = new JScrollPane(elementVizRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(50, 100));
		
		elementVizBinnedFluxPanel = new ElementVizBinnedFluxPanel(new String("Normalized Flux Legend"));
		sp3 = new JScrollPane(elementVizBinnedFluxPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp3.setPreferredSize(new Dimension(200, 100));
	
		elementVizReactionPanel = new ElementVizReactionPanel();
		sp4 = new JScrollPane(elementVizReactionPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp4.setPreferredSize(new Dimension(200, 30));
	
		elementVizIsotopePanel = new ElementVizIsotopePanel();
		sp5 = new JScrollPane(elementVizIsotopePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp5.setPreferredSize(new Dimension(200, 30));
	
		//PANELS/////////////////////////////////////////////////////////////////////PANELS//////////////////////////
		gap = 5;
		double[] columnRight = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		
		double[] rowRight = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED, gap};
		rightPanel = new JPanel(new TableLayout(columnRight, rowRight));
		
		buttonPanel = new JPanel(new GridLayout(1, 7, 5, 5));
		
		//BUTTONPANEL////////////////////////////////////////////////////////////////BUTTONPANEL////////////////////
		buttonPanel.add(defaultButton);
		buttonPanel.add(stepBackButton);
		buttonPanel.add(playButton);
		buttonPanel.add(stepForwardButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(exportButton);
		buttonPanel.add(saveButton);
		
		//BOTTOMPANEL////////////////////////////////////////////////////////BOTTOMPANEL////////////////////////////
		double[] columnBottom = {gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.FILL
									, gap, TableLayoutConstants.FILL
									, gap, TableLayoutConstants.FILL
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED, gap};
		double[] rowBottom = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		bottomPanel = new JPanel(new TableLayout(columnBottom, rowBottom));
		
		bottomPanel.add(zoomControlLabel, "1, 1, r, c");
		bottomPanel.add(zoomSlider, "3, 1, 7, 1, f, c");
		bottomPanel.add(zoomLabel, "9, 1, r, c");
		bottomPanel.add(zoomField, "11, 1, f, c");
		bottomPanel.add(showLabelsCheckBox, "13, 1, l, c");
		bottomPanel.add(showStableCheckBox, "15, 1, l, c");
		bottomPanel.add(timeStepSliderLabel, "1, 3, r, c");
		bottomPanel.add(timeStepSlider, "3, 3, 7, 3, f, c");
		bottomPanel.add(fpsLabel, "9, 3, r, c");
		bottomPanel.add(fpsField, "11, 3, f, c");
		bottomPanel.add(loopCheckBox, "13, 3, l, c");
		bottomPanel.add(showOutlineCheckBox, "15, 3, l, c");
		bottomPanel.add(timeStepminLabel, "1, 5, r, c");
		bottomPanel.add(timeStepminField, "3, 5, f, c");
		bottomPanel.add(timeStepmaxLabel, "5, 5, r, c");
		bottomPanel.add(timeStepmaxField, "7, 5, f, c");
		bottomPanel.add(gotoTimeStepLabel, "9, 5, r, c");
		bottomPanel.add(gotoTimeStepField, "11, 5, f, c");
		bottomPanel.add(applyButton, "13, 5, 15, 5, f, c");
		
		double[] columnBottom2 = {gap, TableLayoutConstants.PREFERRED, gap};
		double[] rowBottom2 = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		bottomPanel2 = new JPanel(new TableLayout(columnBottom2, rowBottom2));
		bottomPanel2.add(bottomPanel, "1, 1, c, c");
		bottomPanel2.add(buttonPanel, "1, 3, c, c");
		
    	ds.setAnimatorNucSimDataStructure(ds.getNucSimDataStructureArrayAnimator()[0]);

    	Point[] ZAMapArray = ds.getAnimatorNucSimDataStructure().getZAMapArray();
		zmin = getZmin(ZAMapArray);
		zmax = getZmax(ZAMapArray);
    	
		elementVizAnimatorPanel = new ElementVizAnimatorPanel(ds);
		elementVizAnimatorPanel.setCurrentState((String)typeComboBox.getSelectedItem(), "Continuous", zmin, zmax);

        sp = new JScrollPane(elementVizAnimatorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		JViewport vp = new JViewport();
		
		vp.setView(elementVizAnimatorPanel);
		
		sp.setViewport(vp);
	
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)elementVizAnimatorPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAnimatorPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(elementVizAnimatorPanel.zmin
								, elementVizAnimatorPanel.zmax
								, elementVizAnimatorPanel.nmin
								, elementVizAnimatorPanel.nmax
								, elementVizAnimatorPanel.mouseX
								, elementVizAnimatorPanel.mouseY
								, elementVizAnimatorPanel.xoffset
								, elementVizAnimatorPanel.yoffset
								, elementVizAnimatorPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAnimatorPanel.zmin
								, elementVizAnimatorPanel.zmax
								, elementVizAnimatorPanel.nmin
								, elementVizAnimatorPanel.nmax
								, elementVizAnimatorPanel.mouseX
								, elementVizAnimatorPanel.mouseY
								, elementVizAnimatorPanel.xoffset
								, elementVizAnimatorPanel.yoffset
								, elementVizAnimatorPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
	
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());

        addWindowListener(this);
	
    } 
    
    /**
     * Close all frames.
     */
    public void closeAllFrames(){
    	
    	if(elementVizAnimatorColorSettingsFrame!=null){
    		elementVizAnimatorColorSettingsFrame.closeAllFrames();
			elementVizAnimatorColorSettingsFrame.setVisible(false);
			elementVizAnimatorColorSettingsFrame.dispose();
    	}
		
		if(waitingFrame!=null){
			waitingFrame.closeAllFrames();
			waitingFrame.setVisible(false);
			waitingFrame.dispose();
		}
		
		if(bottleFrame!=null){
			bottleFrame.closeAllFrames();
			bottleFrame.setVisible(false);
			bottleFrame.dispose();
		}
		
		if(sumFrame!=null){
			sumFrame.closeAllFrames();
			sumFrame.setVisible(false);
			sumFrame.dispose();
		}
		
		if(elementVizAnimatorExportFrame!=null){
			elementVizAnimatorExportFrame.closeAllFrames();
			elementVizAnimatorExportFrame.setVisible(false);
			elementVizAnimatorExportFrame.dispose();
		}
    }
    
    /**
     * Sets the format layout.
     *
     * @param type the type
     * @param scheme the scheme
     */
    public void setFormatLayout(String type, String scheme){
    
    	c.removeAll();
    	
    	rightPanel.removeAll();
		
		rightPanel.add(nucSimLabel, "1, 1, 3, 1, r, c");
		rightPanel.add(nucSimComboBox, "5, 1, 7, 1, l, c");
		rightPanel.add(typeLabel, "1, 3, 3, 3, r, c");
		rightPanel.add(typeComboBox, "5, 3, 7, 3, l, c");
		rightPanel.add(timeStepLabel, "1, 5, r, c");
		rightPanel.add(timeStepField, "3, 5, l, c");
		rightPanel.add(timeLabel, "5, 5, r, c");
		rightPanel.add(timeField, "7, 5, l, c");
		rightPanel.add(tempLabel, "1, 7, r, c");
		rightPanel.add(tempField, "3, 7, l, c");
		rightPanel.add(densityLabel, "5, 7, r, c");
		rightPanel.add(densityField, "7, 7, l, c");
    	
    	if(type.equals("Abundance") || type.equals("Derivative")){
    	
			rightPanel.add(sp2, "1, 11, 3, 15, r, c");
			rightPanel.add(minLabel, "1, 9, r, c");
			rightPanel.add(minField, "3, 9, l, c");
			rightPanel.add(maxLabel, "5, 9, r, c");
			rightPanel.add(maxField, "7, 9, l, c");
			rightPanel.add(valueLabel, "5, 11, r, c");
			rightPanel.add(valueField, "7, 11, l, c");
			rightPanel.add(sp5, "5, 13, 7, 13, c, c");
			rightPanel.add(colorButton, "5, 15, 7, 15, f, c");
			
    	}else if(type.equals("Reaction Flux")){
    	
			if(scheme.equals("Continuous")){
			
				rightPanel.add(sp2, "1, 11, 3, 15, r, c");
				rightPanel.add(minLabel, "1, 9, r, c");
				rightPanel.add(minField, "3, 9, l, c");
				rightPanel.add(maxLabel, "5, 9, r, c");
				rightPanel.add(maxField, "7, 9, l, c");
				rightPanel.add(valueLabel, "5, 11, r, c");
				rightPanel.add(valueField, "7, 11, l, c");
			
			}else if(scheme.equals("Binned")){
			
				rightPanel.add(valueLabel, "1, 9, r, c");
				rightPanel.add(valueField, "3, 9, l, c");
				rightPanel.add(sp3, "1, 11, 7, 11, f, f");
			
			}
			
			rightPanel.add(sp4, "5, 13, 7, 13, c, c");
			rightPanel.add(colorButton, "5, 15, 7, 15, f, c");
    	}
    	
		rightPanel.add(waitingPanel, "1, 17, 7, 17, c, c");
		rightPanel.add(bottlePanel, "1, 19, 7, 19, c, c");
		
		if(type.equals("Abundance") || type.equals("Derivative")){
			rightPanel.add(sumPanel, "1, 21, 7, 21, c, c");
			//rightPanel.add(abundButton, "1, 23, 7, 23, c, c");
		}else if(type.equals("Reaction Flux")){
			rightPanel.add(fluxButton, "1, 21, 7, 21, c, t");
		}
    	
		rightPanel.add(rangeButton, "1, 23, 7, 23, c, c");
		
    	c.add(sp, "1, 1, f, f");
    	c.add(rightPanel, "3, 1, c, f");
		c.add(bottomPanel2, "1, 3, 3, 3, c, c");
		
		repaint();
    	validate();
  
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==nucSimComboBox){
    	
    		if(timer!=null){
    	
    			timer.cancel();
    		
    		}
    		
    		stopButton.setEnabled(false);
	    	playButton.setEnabled(true);
    	
    		if(delayDialog==null){
    	
    			//openDelayDialog(this);
    		
	    		type = typeComboBox.getSelectedItem().toString();
	    		simulation = nucSimComboBox.getSelectedItem().toString();
	    	
	    		nucSimComboBox.removeItemListener(this);
	    	
	    		//final SwingWorker worker = new SwingWorker(){
			
					//public Object construct(){
					
						initializeAnimator(simulation, type, scheme);
													
						//return new Object();
						
					//}
					
					//public void finished(){
						
						//closeDelayDialog();
						
						nucSimComboBox.addItemListener(ElementVizAnimatorFrame.this);
						
					//}
					
				//};
				
				//worker.start();
				
				if(elementVizAnimatorColorSettingsFrame!=null){
					
					elementVizAnimatorColorSettingsFrame.setCurrentState(type, scheme);
					elementVizAnimatorColorSettingsFrame.setSize(elementVizAnimatorColorSettingsFrame.getPreferredSize());
					elementVizAnimatorColorSettingsFrame.validate();
	
	        	}
	        	
	        }
			
    	}else if(ie.getSource()==typeComboBox){
    	
    		if(timer!=null){
    	
    			timer.cancel();
    		
    		}
    		
    		stopButton.setEnabled(false);
	    	playButton.setEnabled(true);
    	
    		if(delayDialog==null){
    	
    			// openDelayDialog(this);
    
    			type = typeComboBox.getSelectedItem().toString();
	    		simulation = nucSimComboBox.getSelectedItem().toString();
    
	    		if(type.equals("Abundance")){
	    		
	    			scheme = ds.getAbundScheme();
	    			showOutlineCheckBox.setEnabled(true);
	    		
	    		}else if(type.equals("Derivative")){
	    		
	    			scheme = ds.getDerScheme();
	    			showOutlineCheckBox.setEnabled(true);
	    		
	    		}if(type.equals("Reaction Flux")){
	    		
	    			scheme = ds.getFluxScheme();
	    			
	    			showOutlineCheckBox.setEnabled(false);
	    			showOutlineCheckBox.setSelected(true);
	    		
	    		}

	    		typeComboBox.removeItemListener(this);
	    	
	    		//final SwingWorker worker = new SwingWorker(){
		
				//	public Object construct(){
					
						initializeAnimator(simulation, type, scheme);
						
				//		return new Object();
					
					//}
					
					//public void finished(){
						
						//closeDelayDialog();
						
						typeComboBox.addItemListener(ElementVizAnimatorFrame.this);
						
					//}
					
				//};
				
				//worker.start();
	    	
	    		if(elementVizAnimatorColorSettingsFrame!=null){
					
					elementVizAnimatorColorSettingsFrame.setCurrentState(type, scheme);
					elementVizAnimatorColorSettingsFrame.setSize(elementVizAnimatorColorSettingsFrame.getPreferredSize());
					elementVizAnimatorColorSettingsFrame.validate();
	
	        	}
	        	
	        }
    	
    	}else if(ie.getSource()==showLabelsCheckBox){
    	
    		elementVizAnimatorPanel.repaint();
    	
    	}else if(ie.getSource()==showStableCheckBox){
    	
    		elementVizAnimatorPanel.repaint();
    	
    	}else if(ie.getSource()==showOutlineCheckBox){
    	
    		elementVizAnimatorPanel.repaint();
    	
    	}
    	
    }
    
    /**
     * Open point frame.
     */
    public void openPointFrame(){
    	ds.setGroup("PUBLIC");
		boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
		ds.setGroup("SHARED");
		boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
		ds.setGroup("USER");
		boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
		
		if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
    		if(pointFrame==null){
    			pointFrame = new ElementVizPointFrame(ds
    												, Cina.cinaMainDataStructure
    												, Cina.cinaCGIComm);
    		}
    		pointFrame.setCurrentState(Integer.valueOf(timeStepminField.getText())
    									, Integer.valueOf(timeStepmaxField.getText()));
    		pointFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0)
    									, (int)(getLocation().getY() + getHeight()/2.0));
    		pointFrame.setVisible(true);
		}
    }
    
    public void exportAllAbundances(){
    	
    	StringBuilder sb = new StringBuilder();
    	
    	TreeMap<Integer, TreeSet<Integer>> isotopeMapFull = new TreeMap<Integer, TreeSet<Integer>>();
		
    	int alminusIndex = -1;
    	int alstarIndex = -1;
    	
		for(int i=0; i<ds.getAnimatorNucSimDataStructure().getZAMapArray().length; i++){
			
			int z = Double.valueOf(ds.getAnimatorNucSimDataStructure().getZAMapArray()[i].getX()).intValue();
			int a = Double.valueOf(ds.getAnimatorNucSimDataStructure().getZAMapArray()[i].getY()).intValue();
			
			if(!isotopeMapFull.containsKey(z)){
				isotopeMapFull.put(z, new TreeSet<Integer>());
			}
			
			isotopeMapFull.get(z).add(a);
			
			if(z==13 && a==26){
				if(alminusIndex==-1){
					alminusIndex = i;
				}else{
					alstarIndex = i;
				}
			}
			
		}
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray().length; i++){
    		
			AbundTimestepDataStructure atsds = ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i];
			int timeStep = i;
			sb.append(timeStep + "\n");
			double time = ds.getAnimatorNucSimDataStructure().getTimeArray()[i] - normalTime;
			sb.append(String.format("%6.7E", time) + "\n");

			TreeMap<Integer, TreeMap<Integer, Double>> isotopeMap = new TreeMap<Integer, TreeMap<Integer, Double>>();
			
			int[] indexArray = atsds.getIndexArray();
			
			double alminusAbund = 0.0;
			double alstarAbund = 0.0;

			for(int j=0; j<indexArray.length; j++){
				
				int z = Double.valueOf(ds.getAnimatorNucSimDataStructure().getZAMapArray()[indexArray[j]].getX()).intValue();
				int a = Double.valueOf(ds.getAnimatorNucSimDataStructure().getZAMapArray()[indexArray[j]].getY()).intValue();
				double abund = atsds.getAbundArray()[j];
				
				if(indexArray[j]==alminusIndex){
					alminusAbund = abund;
				}else if(indexArray[j]==alstarIndex){
					alstarAbund = abund;
				}
				
				if(!isotopeMap.containsKey(z)){
					isotopeMap.put(z, new TreeMap<Integer, Double>());
				}
				
				isotopeMap.get(z).put(a, abund);

			}
			
			Iterator<Integer> itr = isotopeMapFull.keySet().iterator();
			while(itr.hasNext()){
				int z = itr.next();
				TreeSet<Integer> aList = isotopeMapFull.get(z);
				
				for(Integer a: aList){
					
					int n = a-z;
					double abund = 0.0;
					
					if(isotopeMap.containsKey(z) && isotopeMap.get(z).containsKey(a)){
						abund = isotopeMap.get(z).get(a);
					}
					
					String symbol = MainDataStructure.getElementSymbol(z) + String.valueOf(a);
					
					if(symbol.equals("n1")){
						symbol = "n";
					}else if(symbol.equals("H1")){
						symbol = "p";
					}else if(symbol.equals("H2")){
						symbol = "d";
					}else if(symbol.equals("H3")){
						symbol = "t";
					}
					
					if(z==13 && a==26){
						sb.append("Al*6\t" + z + "\t" + n + "\t" + a + "\t" + NumberFormats.getFormattedValueLong(alstarAbund) + "\n");
						sb.append("Al-6\t" + z + "\t" + n + "\t" + a + "\t" + NumberFormats.getFormattedValueLong(alminusAbund) + "\n");
					}else{
						sb.append(symbol + "\t" + z + "\t" + n + "\t" + a + "\t" + NumberFormats.getFormattedValueLong(abund) + "\n");
					}
					
				}
				
			}
			
			sb.append("\n");
		}
    	
    	TextSaver.saveText(sb.toString(), this);
    }
	
	private int getZmin(Point[] ZAMapArray){
		int min = 1000;
		for(int i=0; i<ZAMapArray.length; i++){
			min = (int)Math.min(min, ZAMapArray[i].getX());
		}
		return min;
	}
    
	private int getZmax(Point[] ZAMapArray){
		int max = 0;
		for(int i=0; i<ZAMapArray.length; i++){
			max = (int)Math.max(max, ZAMapArray[i].getX());
		}
		return max;
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
    	if(ae.getSource()==rangeButton){
    		
    		ArrayList<Integer> currentRangeList = new ArrayList<Integer>();
    		currentRangeList.add(zmin);
    		currentRangeList.add(zmax);
    		
    		ArrayList<Integer> completeRangeList = new ArrayList<Integer>();
    		Point[] ZAMapArray = ds.getAnimatorNucSimDataStructure().getZAMapArray();
    		int completeZmin = getZmin(ZAMapArray);
    		int completeZmax = getZmax(ZAMapArray);
    		completeRangeList.add(completeZmin);
    		completeRangeList.add(completeZmax);
    		
    		ArrayList<Integer> newRangeList = ElementVizChartRangeDialog.createElementVizChartRangeDialog(this, currentRangeList, completeRangeList);
    		zmin = newRangeList.get(0);
    		zmax = newRangeList.get(1);
    		
    		elementVizAnimatorPanel.setCurrentState(zmin, zmax);
    		ZRuler.setPreferredWidth((int)elementVizAnimatorPanel.getPreferredSize().getWidth());
           	NRuler.setPreferredHeight((int)elementVizAnimatorPanel.getPreferredSize().getHeight());
    	
    		ZRuler.setCurrentState(elementVizAnimatorPanel.zmin
    								, elementVizAnimatorPanel.zmax
    								, elementVizAnimatorPanel.nmin
    								, elementVizAnimatorPanel.nmax
    								, elementVizAnimatorPanel.mouseX
    								, elementVizAnimatorPanel.mouseY
    								, elementVizAnimatorPanel.xoffset
    								, elementVizAnimatorPanel.yoffset
    								, elementVizAnimatorPanel.crosshairsOn);
    								
        	NRuler.setCurrentState(elementVizAnimatorPanel.zmin
    								, elementVizAnimatorPanel.zmax
    								, elementVizAnimatorPanel.nmin
    								, elementVizAnimatorPanel.nmax
    								, elementVizAnimatorPanel.mouseX
    								, elementVizAnimatorPanel.mouseY
    								, elementVizAnimatorPanel.xoffset
    								, elementVizAnimatorPanel.yoffset
    								, elementVizAnimatorPanel.crosshairsOn);
    								
    		ZRuler.validate();
    		NRuler.validate();
    		
    	}else if((ae.getSource()==waitingYesBox || ae.getSource()==waitingMaybeBox) && ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getPointMap()!=null){
    	
    		elementVizAnimatorPanel.repaint();
    		
    	}else if(ae.getSource()==bottleMinorBox || ae.getSource()==bottleMajorBox){
    	
    		elementVizAnimatorPanel.repaint();
    	
    	}else if(ae.getSource()==waitingYesButton){
    		
    		Color newColor = JColorChooser.showDialog(this, "Choose Waiting Point Color", ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getYesColor());
    		if(newColor!=null){
    			ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setYesColor(newColor);
    			elementVizAnimatorPanel.repaint();
    			yesPanel.setColor(newColor);
    		}
    	
    	}else if(ae.getSource()==bottleColorMinorButton){
    		
    		Color newColor = JColorChooser.showDialog(this, "Choose Minor Bottleneck Color", ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getColorMinor());
    		if(newColor!=null){
    			ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setColorMinor(newColor);
    			elementVizAnimatorPanel.repaint();
    			bottleColorMinorPanel.setColor(newColor);
    		}
    	
    	}else if(ae.getSource()==bottleColorMajorButton){
    		
    		Color newColor = JColorChooser.showDialog(this, "Choose Minor Bottleneck Color", ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getColorMajor());
    		if(newColor!=null){
    			ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setColorMajor(newColor);
    			elementVizAnimatorPanel.repaint();
    			bottleColorMajorPanel.setColor(newColor);
    		}	
    		
    	}/*else if(ae.getSource()==waitingMaybeButton){
    		
    		Color newColor = JColorChooser.showDialog(this, "Choose Ambiguous Waiting Point Color", ds.getWaitingDataStructure().getMaybeColor());
    		if(newColor!=null){
    			ds.getWaitingDataStructure().setMaybeColor(newColor);
    			elementVizAnimatorPanel.repaint();
    			maybePanel.setColor(newColor);
    		}	
    	
    	}*/else if(ae.getSource()==pointButton){
    		
    		ds.setGroup("PUBLIC");
			boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
			ds.setGroup("SHARED");
			boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
			ds.setGroup("USER");
			boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.elementVizFrame.elementVizAnimatorFrame);
			
			if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
	    		if(pointFrame==null){
	    		pointFrame = new ElementVizPointFrame(ds
	    												, Cina.cinaMainDataStructure
	    												, Cina.cinaCGIComm);
	    		}
	    		pointFrame.setCurrentState(Integer.valueOf(timeStepminField.getText()), Integer.valueOf(timeStepmaxField.getText()));
	    		pointFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0), (int)(getLocation().getY() + getHeight()/2.0));
	    		pointFrame.setVisible(true);
			}
		
    	}else if(ae.getSource()==sumButton){
        	
    		if(ds.getAnimatorNucSimDataStructure().getSumDataStructure().getSum()==0.0){
    		
	    		int z = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getX());
	    		int a = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getY());
	    		
	    		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setZ(new Point(0, z));
	    		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setN(new Point(0, a-z));
	    		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setA(new Point(1, a));
	    		
	    		HashMap<SumDataStructure.Time, ArrayList<Double>> timeMap = new HashMap<SumDataStructure.Time, ArrayList<Double>>();
	    		timeMap.put(SumDataStructure.Time.TIME_STEP, new ArrayList<Double>());
	    		timeMap.get(SumDataStructure.Time.TIME_STEP).add(new Double(0));
	    		timeMap.get(SumDataStructure.Time.TIME_STEP).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1));
	    		timeMap.put(SumDataStructure.Time.TIME_SEC, new ArrayList<Double>());
	    		timeMap.get(SumDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime));
	    		timeMap.get(SumDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[ds.getAnimatorNucSimDataStructure().getTimeArray().length-1] - normalTime));
	    		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setTimeMap(timeMap);
	    		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setNormalTime(normalTime);
    		}
    		
    		if(sumFrame!=null){
    			sumFrame.setVisible(false);
    			sumFrame.dispose();
    			sumFrame = null;
    		}
    		
    		sumFrame = new SumFrame(Cina.cinaMainDataStructure
									, Cina.cgiCom
									, null
									, ds.getAnimatorNucSimDataStructure().getSumDataStructure());
    		sumFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0 - sumFrame.getWidth()/2.0)
    										, (int)(getLocation().getY() + getHeight()/2.0 - sumFrame.getHeight()/2.0));
    		sumFrame.setVisible(true);
		
    	}else if(ae.getSource()==fluxButton){
            	
        		if(ds.getAnimatorNucSimDataStructure().getFluxDataStructure().getReactions().equals("")){
    	    		
    	    		HashMap<FluxDataStructure.Time, ArrayList<Double>> timeMap = new HashMap<FluxDataStructure.Time, ArrayList<Double>>();
    	    		timeMap.put(FluxDataStructure.Time.TIME_STEP, new ArrayList<Double>());
    	    		timeMap.get(FluxDataStructure.Time.TIME_STEP).add(new Double(0));
    	    		timeMap.get(FluxDataStructure.Time.TIME_STEP).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1));
    	    		timeMap.put(FluxDataStructure.Time.TIME_SEC, new ArrayList<Double>());
    	    		timeMap.get(FluxDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime));
    	    		timeMap.get(FluxDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[ds.getAnimatorNucSimDataStructure().getTimeArray().length-1] - normalTime));
    	    		ds.getAnimatorNucSimDataStructure().getFluxDataStructure().setTimeMap(timeMap);
    	    		ds.getAnimatorNucSimDataStructure().getFluxDataStructure().setNormalTime(normalTime);
        		}
        		
        		if(fluxFrame!=null){
        			fluxFrame.setVisible(false);
        			fluxFrame.dispose();
        			fluxFrame = null;
        		}
        		
        		fluxFrame = new FluxFrame(Cina.cinaMainDataStructure
    									, Cina.cgiCom
    									, null
    									, ds.getAnimatorNucSimDataStructure().getFluxDataStructure());
        		fluxFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0 - fluxFrame.getWidth()/2.0)
        										, (int)(getLocation().getY() + getHeight()/2.0 - fluxFrame.getHeight()/2.0));
        		fluxFrame.setVisible(true);
    		
    	}else if(ae.getSource()==bottleButton){
    		
    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
        	ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setZone(ds.getAnimatorNucSimDataStructure().getZone());
        	
    		if(ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getListMajor()==null && ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getListMajor()==null){
    		
	    		int z = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getX());
	    		int a = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getY());
	    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setZ(new Point(0, z));
	    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setN(new Point(0, a-z));
	    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setA(new Point(1, a));
	    		
	    		HashMap<BottleDataStructure.Time, ArrayList<Double>> timeMap = new HashMap<BottleDataStructure.Time, ArrayList<Double>>();
	    		timeMap.put(BottleDataStructure.Time.TIME_STEP, new ArrayList<Double>());
	    		timeMap.get(BottleDataStructure.Time.TIME_STEP).add(new Double(0));
	    		timeMap.get(BottleDataStructure.Time.TIME_STEP).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1));
	    		timeMap.put(BottleDataStructure.Time.TIME_SEC, new ArrayList<Double>());
	    		timeMap.get(BottleDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime));
	    		timeMap.get(BottleDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[ds.getAnimatorNucSimDataStructure().getTimeArray().length-1] - normalTime));
	    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setTimeMap(timeMap);
	    		ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setNormalTime(normalTime);
    		}
    		
    		if(bottleFrame!=null){
    			bottleFrame.setVisible(false);
    			bottleFrame.dispose();
    			bottleFrame = null;
    		}
    		
    		bottleFrame = new BottleFrame(Cina.cinaMainDataStructure
    												, Cina.cgiCom
    												, null
    												, ds.getAnimatorNucSimDataStructure().getBottleDataStructure());
    		bottleFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0 - bottleFrame.getWidth()/2.0)
    										, (int)(getLocation().getY() + getHeight()/2.0 - bottleFrame.getHeight()/2.0));
    		bottleFrame.setVisible(true);
    		
    	}else if(ae.getSource()==waitingButton){
    		
    		ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
        	ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setZone(ds.getAnimatorNucSimDataStructure().getZone());
        	
    		if(ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getPointMap()==null){
    		
	    		int z = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getX());
	    		int a = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[ds.getAnimatorNucSimDataStructure().getZAMapArray().length-1].getY());
	    		ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setZ(new Point(0, z));
	    		ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setN(new Point(0, a-z));
	    		
	    		HashMap<WaitingDataStructure.Time, ArrayList<Double>> timeMap = new HashMap<WaitingDataStructure.Time, ArrayList<Double>>();
	    		timeMap.put(WaitingDataStructure.Time.TIME_STEP, new ArrayList<Double>());
	    		timeMap.get(WaitingDataStructure.Time.TIME_STEP).add(new Double(0));
	    		timeMap.get(WaitingDataStructure.Time.TIME_STEP).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1));
	    		timeMap.put(WaitingDataStructure.Time.TIME_SEC, new ArrayList<Double>());
	    		timeMap.get(WaitingDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime));
	    		timeMap.get(WaitingDataStructure.Time.TIME_SEC).add(new Double(ds.getAnimatorNucSimDataStructure().getTimeArray()[ds.getAnimatorNucSimDataStructure().getTimeArray().length-1] - normalTime));
	    		ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setTimeMap(timeMap);
	    		ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setNormalTime(normalTime);
    		}
    		
    		if(waitingFrame!=null){
    			waitingFrame.setVisible(false);
    			waitingFrame.dispose();
    			waitingFrame = null;
    		}
    		waitingFrame = new WaitingFrame(Cina.cinaMainDataStructure
    												, Cina.cgiCom
    												, null
    												, ds.getAnimatorNucSimDataStructure().getWaitingDataStructure());
    		waitingFrame.setLocation((int)(getLocation().getX() + getWidth()/2.0 - waitingFrame.getWidth()/2.0)
    										, (int)(getLocation().getY() + getHeight()/2.0 - waitingFrame.getHeight()/2.0));
    		waitingFrame.setVisible(true);
    		
    	}else if(ae.getSource()==abundButton){
    		
    		exportAllAbundances();
    		
    	}else if(checkDataFields()){
    
	    	if(ae.getSource()==playButton){
	    	
	    		playButton.setEnabled(false);
	    		stopButton.setEnabled(true);
	
	    		beginElementVizAnimatorUpdateTask((int)Math.round(1000*(1/(double)getFPS())));
	    	
	    	}else if(ae.getSource()==stopButton){
	    	
	    		stopButton.setEnabled(false);
	    		playButton.setEnabled(true);
	    		
	    		if(timer!=null){
	    	
	    			timer.cancel();
	    		
	    		}
	    	
	    	}else if(ae.getSource()==stepBackButton){
	    	
	    		stopButton.setEnabled(false);
	    		playButton.setEnabled(true);
	    		
	    		if(timer!=null){
	    	
	    			timer.cancel();
	    		
	    		}
	    	
	    		setCurrentData(timeStepSlider.getValue()-1);
	    	
	    	}else if(ae.getSource()==stepForwardButton){
	    	
	    		stopButton.setEnabled(false);
	    		playButton.setEnabled(true);
	    	
	    		if(timer!=null){
	    	
	    			timer.cancel();
	    		
	    		}
	    	
	    		setCurrentData(timeStepSlider.getValue()+1);
	    	
	    	}else if(ae.getSource()==applyButton){
	
				applyTimeStepSettings();
	
	    	}else if(ae.getSource()==exportButton){
	    		
	    		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){

		    		if(elementVizAnimatorExportFrame!=null){
	
						elementVizAnimatorExportFrame.setCurrentState();
		        		elementVizAnimatorExportFrame.setVisible(true);
		
		        	}else{
		        	
		        		elementVizAnimatorExportFrame = new ElementVizAnimatorExportFrame(ds);
		        		elementVizAnimatorExportFrame.setCurrentState();
		        		elementVizAnimatorExportFrame.setVisible(true);
		        			        	
		        	}
	        	
	        	}else{
	        	
	        		String string = "This feature is only availble to registered users.";
	        		Dialogs.createExceptionDialog(string, this);
	        	
	        	}
	    	
	    	}else if(ae.getSource()==saveButton){
	    
	    		createSaveDialog();
	    
		    }else if(ae.getSource()==okButton){
		    	
		    	saveDialog.setVisible(false);
		    	saveDialog.dispose();
		    
		    	PlotSaver.savePlot(new ElementVizAnimatorPrintPanel(whiteTextRadioButton.isSelected(), ds), this);
		    	
		    }
    	
    	}
    	
    	if(ae.getSource()==defaultButton){
	    	  
	    	if(timer!=null){
	    	
	    		timer.cancel();
	    	
	    	}
	    	
	    	stopButton.setEnabled(false);
	    	playButton.setEnabled(true);

	    	elementVizAnimatorPanel.setCurrentState(type, scheme, zmin, zmax);
	    	
	    	if(type.equals("Abundance")){
    	
		    	abundMax = getAbundMax();
		    	abundMin = getAbundMin();
		    	
		    	setMinAbund(abundMin);
		    	setMaxAbund(abundMax);
	    	
	    	}else if(type.equals("Derivative")){
	    	
	    		derAbundMax = getDerAbundMax();
		    	derAbundMin = -derAbundMax;
		    	
		    	setMinDerAbund(derAbundMin);
		    	setMaxDerAbund(derAbundMax);

	    	}else if(type.equals("Reaction Flux")){
	    	
	    		fluxMax = getFluxMax();
		    	fluxMin = getFluxMin();
		    	
		    	setMinFlux(fluxMin);
		    	setMaxFlux(fluxMax);

	    	}
	    	
			setTimeStep(0);
			setTime(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime);
			setTemp(ds.getAnimatorNucSimDataStructure().getTempArray()[0]);
			setDensity(ds.getAnimatorNucSimDataStructure().getDensityArray()[0]);
			
			timeStepSlider.removeChangeListener(this);
			timeStepSlider.setMinimum(getTimeStep());
			
			if(type.equals("Abundance") || type.equals("Reaction Flux")){
			
				timeStepSlider.setMaximum(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1);
			
			}else if(type.equals("Derivative")){
			
				timeStepSlider.setMaximum(ds.getAnimatorNucSimDataStructure().getTimeArray().length-2);
			
			}
			
			timeStepSlider.setValue(timeStepSlider.getMinimum());
			timeStepSlider.addChangeListener(this);
			
			setTimeStepmin(timeStepSlider.getMinimum());
			setTimeStepmax(timeStepSlider.getMaximum());
			
			setGotoTimeStep(timeStepSlider.getMinimum());
			
			setFPS(100);
	    	
	    }
	    
	    if(ae.getSource()==colorButton){
	    
	    	if(timer!=null){
	    	
	    		timer.cancel();
	    	
	    	}
	    	
	    	stopButton.setEnabled(false);
	    	playButton.setEnabled(true);
	    	
    		if(elementVizAnimatorColorSettingsFrame!=null){
				
				elementVizAnimatorColorSettingsFrame.setSize(862, 400);
				elementVizAnimatorColorSettingsFrame.setCurrentState(type, scheme);
        		elementVizAnimatorColorSettingsFrame.setVisible(true);
        		elementVizAnimatorColorSettingsFrame.validate();

        	}else{
        	
        		elementVizAnimatorColorSettingsFrame = new ElementVizAnimatorColorSettingsFrame(ds);
        		elementVizAnimatorColorSettingsFrame.setSize(862, 400);
        		elementVizAnimatorColorSettingsFrame.setCurrentState(type, scheme);
        		elementVizAnimatorColorSettingsFrame.setVisible(true);
        		elementVizAnimatorColorSettingsFrame.validate();
        			        	
        	}
        	
	    }
    
    }
    
    /**
     * Check data fields.
     *
     * @return true, if successful
     */
    public boolean checkDataFields(){
    
    	boolean goodDataFields = true;
    	
    	String type = (String)typeComboBox.getSelectedItem();
    	
    	try{
    	
    		if(Integer.valueOf(timeStepminField.getText()).intValue()>=Integer.valueOf(timeStepmaxField.getText()).intValue()){
    		
    			String string = "Timestep minimum must be less than timestep maximum.";
    		
    			Dialogs.createExceptionDialog(string, this);
    			
    			goodDataFields = false;
    		
    		}else if(Integer.valueOf(timeStepminField.getText()).intValue()<0){
    		
    			String string = "Timestep minimum must be greater than or equal to zero.";
    		
    			Dialogs.createExceptionDialog(string, this);
    			
    			goodDataFields = false;
    		
    		}else if((type.equals("Abundance"))
    					&&(Integer.valueOf(timeStepmaxField.getText()).intValue()>ds.getAnimatorNucSimDataStructure().getTimeArray().length-1)){
    		
    			String string = "Timestep maximum must be less than or equal to "
    								+ String.valueOf(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1)
    								+ ".";
    		
    			Dialogs.createExceptionDialog(string, this);
    			
    			goodDataFields = false;
    		
    		}else if((type.equals("Derivative"))
    					&&(Integer.valueOf(timeStepmaxField.getText()).intValue()>ds.getAnimatorNucSimDataStructure().getTimeArray().length-2)){
    		
    			String string = "Timestep maximum must be less than or equal to "
    								+ String.valueOf(ds.getAnimatorNucSimDataStructure().getTimeArray().length-2)
    								+ ".";
    		
    			Dialogs.createExceptionDialog(string, this);
    			
    			goodDataFields = false;
    		
    		}else if(Integer.valueOf(gotoTimeStepField.getText()).intValue()<Integer.valueOf(timeStepminField.getText()).intValue()){
    		
    			//dummy to check for nfe
    		
    		}
    		
    	}catch(NumberFormatException nfe){
    	
    		String string = "All fields must contain integer values.";
    		
    		Dialogs.createExceptionDialog(string, this);
    		
    		goodDataFields = false;
    	
    	}
    	
    	return goodDataFields;
    
    }
    
    /**
     * Begin element viz animator update task.
     *
     * @param millisec the millisec
     */
    public void beginElementVizAnimatorUpdateTask(int millisec){
	
		timer = new java.util.Timer();
		
		timer.schedule(new ElementVizAnimatorUpdateTask(), 0, millisec);
	
	}
    
    /**
     * Update animator.
     */
    public void updateAnimator(){
    
    	timeStepSlider.removeChangeListener(this);
		timeStepSlider.setValue(timeStepSlider.getValue()+1);
		timeStepSlider.addChangeListener(this);

		if((timeStepSlider.getValue()==timeStepSlider.getMaximum())
				&& !loopCheckBox.isSelected()){
		
			stopButton.setEnabled(false);
			playButton.setEnabled(true);
			
			if(timer!=null){
			
				timer.cancel();
			
			}
		
		}else if((timeStepSlider.getValue()==timeStepSlider.getMaximum())
				&& loopCheckBox.isSelected()){
		
			timeStepSlider.setValue(timeStepSlider.getMinimum());
		
		}
		
		if(type.equals("Abundance") || type.equals("Derivative")){
		
			if(elementVizAnimatorPanel.protonNumber>-1 && elementVizAnimatorPanel.viktor.contains(new Point(elementVizAnimatorPanel.protonNumber, elementVizAnimatorPanel.protonNumber + elementVizAnimatorPanel.neutronNumber))){
			
				valueField.setText(NumberFormats.getFormattedValueLong(elementVizAnimatorPanel.getValue(new Point(elementVizAnimatorPanel.protonNumber, elementVizAnimatorPanel.protonNumber + elementVizAnimatorPanel.neutronNumber))));
	    	
	    	}else{
	    	
	    		valueField.setText("");
	    		
	    	}
    	
    	}else if(type.equals("Reaction Flux")){
    	
    		foundFlux:
    		for(int i=0; i<elementVizAnimatorPanel.lineVector.size(); i++){
    
    			if(((Line2D.Double)elementVizAnimatorPanel.lineVector.elementAt(i)).ptSegDist(elementVizAnimatorPanel.mouseX, elementVizAnimatorPanel.mouseY)<=2.0){

					int nIn = (int)((((Line2D.Double)elementVizAnimatorPanel.lineVector.elementAt(i)).getX1() - elementVizAnimatorPanel.xoffset)/elementVizAnimatorPanel.boxWidth - 0.5);
					int zIn = (int)(-1*((((Line2D.Double)elementVizAnimatorPanel.lineVector.elementAt(i)).getY1() - elementVizAnimatorPanel.yoffset)/elementVizAnimatorPanel.boxHeight - 0.5 - elementVizAnimatorPanel.zmax));
					int nOut = (int)((((Line2D.Double)elementVizAnimatorPanel.lineVector.elementAt(i)).getX2() - elementVizAnimatorPanel.xoffset)/elementVizAnimatorPanel.boxWidth - 0.5);
					int zOut = (int)(-1*((((Line2D.Double)elementVizAnimatorPanel.lineVector.elementAt(i)).getY2() - elementVizAnimatorPanel.yoffset)/elementVizAnimatorPanel.boxHeight - 0.5 - elementVizAnimatorPanel.zmax));

					valueField.setText(NumberFormats.getFormattedValueLong(elementVizAnimatorPanel.getFluxValue(zIn, zIn+nIn, zOut, zOut+nOut)));

    				break foundFlux;
    			
    			}
				valueField.setText("");
    		
    		}
    	
    	}	
    	
		setCurrentData(timeStepSlider.getValue());

    }
    
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==timeStepSlider){
    	
    		setCurrentData(timeStepSlider.getValue());
    	
    	}else if(ce.getSource()==zoomSlider){
    		
    		if(zoomSlider.getValue()==100){
    		
    			sp.setColumnHeaderView(ZRuler);
        		sp.setRowHeaderView(NRuler);
    		
    		}else{
    		
    			sp.setColumnHeaderView(null);
        		sp.setRowHeaderView(null);
    		
    		}
    		
    		elementVizAnimatorPanel.setCurrentState(type, scheme, zmin, zmax);
    		
    		double scale = zoomSlider.getValue()/100.0;
			elementVizAnimatorPanel.boxHeight = (int)(29.0*scale);
			elementVizAnimatorPanel.boxWidth = (int)(29.0*scale);
	        
        	//elementVizAnimatorPanel.width = (elementVizAnimatorPanel.boxWidth*(elementVizAnimatorPanel.nmax-elementVizAnimatorPanel.nmin+1));
	        //elementVizAnimatorPanel.height = (elementVizAnimatorPanel.boxHeight*(elementVizAnimatorPanel.zmax-elementVizAnimatorPanel.zmin+1));
	        
	        //elementVizAnimatorPanel.xmax = (elementVizAnimatorPanel.xoffset + elementVizAnimatorPanel.width);
	        //elementVizAnimatorPanel.ymax = (elementVizAnimatorPanel.yoffset + elementVizAnimatorPanel.height);

        	//elementVizAnimatorPanel.setSize(elementVizAnimatorPanel.xmax+elementVizAnimatorPanel.xoffset, elementVizAnimatorPanel.ymax+elementVizAnimatorPanel.yoffset);
        			
			//elementVizAnimatorPanel.setPreferredSize(elementVizAnimatorPanel.getSize());
    		
    		elementVizAnimatorPanel.repaint();

			zoomField.setText(String.valueOf(zoomSlider.getValue()));
    		
    		if(!zoomSlider.getValueIsAdjusting()){
    		
	    		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
				sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
			
			}
			
			sp.validate();
    		
    	}

    }
    
    /**
     * Apply time step settings.
     */
    public void applyTimeStepSettings(){
    
    	int lowerBound = getTimeStepmin();
    	int upperBound = getTimeStepmax();
    	int currentStep = getGotoTimeStep();
    	
    	timeStepSlider.removeChangeListener(this);
    	timeStepSlider.setMinimum(lowerBound);
		timeStepSlider.setMaximum(upperBound);
		timeStepSlider.setValue(currentStep);
    	timeStepSlider.addChangeListener(this);
    	
    	setGotoTimeStep(timeStepSlider.getValue());
    	setCurrentData(timeStepSlider.getValue());
    	
    	elementVizAnimatorPanel.repaint();
    	
    }
    
    /**
     * Sets the current data.
     *
     * @param step the new current data
     */
    public void setCurrentData(int step){
    
    	setTimeStep(step);
		setTime(ds.getAnimatorNucSimDataStructure().getTimeArray()[step] - normalTime);
		setTemp(ds.getAnimatorNucSimDataStructure().getTempArray()[step]);
		setDensity(ds.getAnimatorNucSimDataStructure().getDensityArray()[step]);
		
		timeStepSlider.removeChangeListener(this);
		timeStepSlider.setValue(step);
		timeStepSlider.addChangeListener(this);
		
		elementVizAnimatorPanel.repaint();
    
    }
    
    /**
     * Clear color arrays.
     */
    public void clearColorArrays(){
    
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
    		if(ds.getNucSimDataStructureArrayAnimator()[i].getAbundTimestepDataStructureArray()!=null){
    		
	    		for(int j=0; j<ds.getNucSimDataStructureArrayAnimator()[i].getAbundTimestepDataStructureArray().length; j++){
	    		
	    			ds.getNucSimDataStructureArrayAnimator()[i].getAbundTimestepDataStructureArray()[j].setRedArray(null);
	    			ds.getNucSimDataStructureArrayAnimator()[i].getAbundTimestepDataStructureArray()[j].setGreenArray(null);
	    			ds.getNucSimDataStructureArrayAnimator()[i].getAbundTimestepDataStructureArray()[j].setBlueArray(null);
	    		
	    		}
    		
    		}
    		
    		if(ds.getNucSimDataStructureArrayAnimator()[i].getDerAbundTimestepDataStructureArray()!=null){
    		
	    		for(int j=0; j<ds.getNucSimDataStructureArrayAnimator()[i].getDerAbundTimestepDataStructureArray().length; j++){
	    		
	    			ds.getNucSimDataStructureArrayAnimator()[i].getDerAbundTimestepDataStructureArray()[j].setRedArray(null);
		    		ds.getNucSimDataStructureArrayAnimator()[i].getDerAbundTimestepDataStructureArray()[j].setGreenArray(null);
		    		ds.getNucSimDataStructureArrayAnimator()[i].getDerAbundTimestepDataStructureArray()[j].setBlueArray(null);
		    		
	    		}
    		
    		}
    		
    		if(ds.getNucSimDataStructureArrayAnimator()[i].getFluxTimestepDataStructureArray()!=null){
    		
	    		for(int j=0; j<ds.getNucSimDataStructureArrayAnimator()[i].getFluxTimestepDataStructureArray().length; j++){
	    		
	    			ds.getNucSimDataStructureArrayAnimator()[i].getFluxTimestepDataStructureArray()[j].setRedArray(null);
		    		ds.getNucSimDataStructureArrayAnimator()[i].getFluxTimestepDataStructureArray()[j].setGreenArray(null);
		    		ds.getNucSimDataStructureArrayAnimator()[i].getFluxTimestepDataStructureArray()[j].setBlueArray(null);
		    		
	    		}
    		
    		}
    	
    	}

	}
    
    /**
     * Initialize.
     */
    public void initialize(){
    	
    	for(int i=0; i<ds.getNucSimDataStructureArrayAnimator().length; i++){
    		ds.getNucSimDataStructureArrayAnimator()[i].setWaitingDataStructure(new WaitingDataStructure());
    		ds.getNucSimDataStructureArrayAnimator()[i].getWaitingDataStructure().setPath(ds.getNucSimDataStructureArrayAnimator()[i].getPath());
    		ds.getNucSimDataStructureArrayAnimator()[i].getWaitingDataStructure().setZone(ds.getNucSimDataStructureArrayAnimator()[i].getZone());
    		
    		ds.getNucSimDataStructureArrayAnimator()[i].setBottleDataStructure(new BottleDataStructure());
    		ds.getNucSimDataStructureArrayAnimator()[i].getBottleDataStructure().setPath(ds.getNucSimDataStructureArrayAnimator()[i].getPath());
    		ds.getNucSimDataStructureArrayAnimator()[i].getBottleDataStructure().setZone(ds.getNucSimDataStructureArrayAnimator()[i].getZone());
    		
    		ds.getNucSimDataStructureArrayAnimator()[i].setSumDataStructure(new SumDataStructure());
    		ds.getNucSimDataStructureArrayAnimator()[i].getSumDataStructure().setPath(ds.getNucSimDataStructureArrayAnimator()[i].getPath());
    	}
    	
    	ds.setAnimatorNucSimDataStructure(ds.getNucSimDataStructureArrayAnimator()[0]);
    	ds.getAnimatorNucSimDataStructure().setWaitingDataStructure(new WaitingDataStructure());
    	ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
    	ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().setZone(ds.getAnimatorNucSimDataStructure().getZone());
    	
    	ds.getAnimatorNucSimDataStructure().setBottleDataStructure(new BottleDataStructure());
    	ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
    	ds.getAnimatorNucSimDataStructure().getBottleDataStructure().setZone(ds.getAnimatorNucSimDataStructure().getZone());
    	
    	ds.getAnimatorNucSimDataStructure().setSumDataStructure(new SumDataStructure());
		ds.getAnimatorNucSimDataStructure().getSumDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
		
		ds.getAnimatorNucSimDataStructure().setFluxDataStructure(new FluxDataStructure());
		ds.getAnimatorNucSimDataStructure().getFluxDataStructure().setPath(ds.getAnimatorNucSimDataStructure().getPath());
    	
    	bottleMinorBox.setEnabled(false);
		bottleMinorBox.setSelected(false);
		bottleMajorBox.setEnabled(false);
		bottleMajorBox.setSelected(false);
		waitingYesBox.setEnabled(false);
		waitingYesBox.setSelected(false);
		waitingMaybeBox.setEnabled(false);
		waitingMaybeBox.setSelected(false);
		bottleColorMinorButton.setEnabled(false);
		bottleColorMajorButton.setEnabled(false);
		waitingYesButton.setEnabled(false);
		waitingMaybeButton.setEnabled(false);
		maybePanel.setColor(Color.GRAY);
		yesPanel.setColor(Color.GRAY);
		bottleColorMajorPanel.setColor(Color.GRAY);
		bottleColorMinorPanel.setColor(Color.GRAY);

    	nucSimComboBox.removeItemListener(this);
    	nucSimComboBox.removeAllItems();
    	
    	typeComboBox.removeItemListener(this);
    	typeComboBox.removeAllItems();
    	
    	if(ds.getAnimatorType().equals("abund")){
    	
    		typeComboBox.addItem("Abundance");
    		typeComboBox.addItem("Derivative");
    	
    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
	    
	    		nucSimComboBox.addItem(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
	    		
	    	}
    	
    	}else if(ds.getAnimatorType().equals("flux")){
    	
    		typeComboBox.addItem("Reaction Flux");
    		
    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
    			if(ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		    		
		    		nucSimComboBox.addItem(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		    		
		    	}
		    	
	    	}
    	
    	}else if(ds.getAnimatorType().equals("both")){
    	
    		typeComboBox.addItem("Abundance");
    		typeComboBox.addItem("Derivative");
    		typeComboBox.addItem("Reaction Flux");
    		
    		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
    		
    			if(ds.getNucSimDataStructureArrayAnimator()[i].getHasFluxes()){
		    		
		    		nucSimComboBox.addItem(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName());
		    		
		    	}
		    	
	    	}
	    	
	    }
    	
    	typeComboBox.setSelectedIndex(0);
    	typeComboBox.addItemListener(this);
    	
    	nucSimComboBox.setSelectedIndex(0);
    	nucSimComboBox.addItemListener(this);
    	
    	simulation = (String)nucSimComboBox.getSelectedItem();
    	type = (String)typeComboBox.getSelectedItem();
    	
    	if(type.equals("Abundance")){
    	
    		scheme = ds.getAbundScheme();
    	
    	}else if(type.equals("Derivative")){
    	
    		scheme = ds.getDerScheme();
    	
    	}else if(type.equals("Reaction Flux")){
    	
    		scheme = ds.getFluxScheme();
    	
    	}
    
    	typeComboBox.setPopupWidthToLongest();
    	nucSimComboBox.setPopupWidthToLongest();
    	
    	initializeAnimator(simulation, type, scheme);
    
	}
    
    /**
     * Initialize animator waiting.
     */
    public void initializeAnimatorWaiting(){
    	waitingYesBox.setEnabled(true);
    	waitingYesBox.setSelected(true);
		waitingMaybeBox.setEnabled(true);
		waitingYesButton.setEnabled(true);
		waitingMaybeButton.setEnabled(true);
		maybePanel.setColor(ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getMaybeColor());
		yesPanel.setColor(ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getYesColor());
		elementVizAnimatorPanel.repaint();
    }
    
    /**
     * Initialize animator bottle.
     */
    public void initializeAnimatorBottle(){
    	bottleMinorBox.setEnabled(true);
    	bottleMinorBox.setSelected(true);
		bottleColorMinorButton.setEnabled(true);
		bottleColorMinorPanel.setColor(ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getColorMinor());
		bottleMajorBox.setEnabled(true);
    	bottleMajorBox.setSelected(true);
		bottleColorMajorButton.setEnabled(true);
		bottleColorMajorPanel.setColor(ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getColorMajor());
		elementVizAnimatorPanel.repaint();
    }
    
    public void initializeAnimatorSum(){
		sumField.setText(NumberFormats.getFormattedValueLong(ds.getAnimatorNucSimDataStructure().getSumDataStructure().getSum()));
    }
    
    /**
     * Initialize animator.
     *
     * @param nucSim the nuc sim
     * @param type the type
     * @param scheme the scheme
     */
    public void initializeAnimator(String nucSim, String type, String scheme){
    
    	zoomField.setText(String.valueOf(zoomSlider.getValue()));
    
    	if(timer!=null){
    	
    		timer.cancel();
    	
    	}
    	
    	clearColorArrays();
    	
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

	        if(ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName().equals(nucSim)){

    			ds.setAnimatorNucSimDataStructure(ds.getNucSimDataStructureArrayAnimator()[i]);
    			normalTime = ds.getNormalTimeArray()[i];
    	
    		}
    	
    	}

    	Point[] ZAMapArray = ds.getAnimatorNucSimDataStructure().getZAMapArray();
		int completeZmin = getZmin(ZAMapArray);
		int completeZmax = getZmax(ZAMapArray);
    	
    	zmin = completeZmin;
    	zmax = completeZmax;
    	
		if(scheme.equals("Continuous")){

	    	if(type.equals("Abundance")){
	    	
		    	abundMax = getAbundMax();
		    	abundMin = getAbundMin();
		    	
		    	setMinAbund(abundMin);
		    	setMaxAbund(abundMax);
	    	
	    		valueLabel.setText("Abundance: ");
	    		minLabel.setText("Abundance Min: ");
	    		maxLabel.setText("Abundance Max: ");
	    	
	    		timeStepminLabel.setText("Timestep min: ");
	    		timeStepmaxLabel.setText("Timestep max: ");
	    		gotoTimeStepLabel.setText("Goto Timestep: ");
	    		
	    		applyButton.setText("Apply Timestep Settings");
	    		
	    		warningLabel.setText("Isotope: ");
	    		
	    		timeStepLabel.setText("Timestep: ");
	    		
	    		timeStepSliderLabel.setText("Timestep Control: ");
	    		fpsLabel.setText("Timesteps per Second: ");
	    		
	    		x0R_abund = ds.getAbundColorValues()[0];
			    x0G_abund = ds.getAbundColorValues()[1];
			   	x0B_abund = ds.getAbundColorValues()[2];
			    aR_abund = ds.getAbundColorValues()[3];
			    aG_abund = ds.getAbundColorValues()[4];
			    aB_abund = ds.getAbundColorValues()[5];
	    		
	    		elementVizRainbowPanel.setRGB(ds.getAbundColorValues());
	    		
	    	}else if(type.equals("Derivative")){
	    	
	    		derAbundMax = getDerAbundMax();

		    	derAbundMin = -1*derAbundMax;
		    	
		    	setMinDerAbund(derAbundMin);
		    	setMaxDerAbund(derAbundMax);
		    	
		    	valueLabel.setText("Derivative: ");
	    		minLabel.setText("Derivative Min: ");
	    		maxLabel.setText("Derivative Max: ");
	    		
	    		timeStepminLabel.setText("Interval min: ");
	    		timeStepmaxLabel.setText("Interval max: ");
	    		gotoTimeStepLabel.setText("Goto Interval: ");
	    		
	    		applyButton.setText("Apply Interval Settings");
	    	
	    		warningLabel.setText("Isotope: ");
	    	
	    		timeStepLabel.setText("Interval: ");
	    	
	    		timeStepSliderLabel.setText("Interval Control: ");
	    		fpsLabel.setText("Intervals per Second: ");
	    	
	    		x0R_der = ds.getDerColorValues()[0];
			    x0G_der = ds.getDerColorValues()[1];
			   	x0B_der = ds.getDerColorValues()[2];
			    aR_der = ds.getDerColorValues()[3];
			    aG_der = ds.getDerColorValues()[4];
			    aB_der = ds.getDerColorValues()[5];
	    		
	    		elementVizRainbowPanel.derAbundMag = derAbundMag;
	    		elementVizRainbowPanel.setRGB(ds.getDerColorValues());
	    	
	    	}else if(type.equals("Reaction Flux")){
	    	
		    	fluxMax = getFluxMax();
		    	fluxMin = getFluxMin();
		    	
		    	setMinFlux(fluxMin);
		    	setMaxFlux(fluxMax);
	    	
	    		valueLabel.setText("Normalized Flux: ");
	    		minLabel.setText("Reaction Flux Min: ");
	    		maxLabel.setText("Reaction Flux Max: ");
	    	
	    		timeStepminLabel.setText("Timestep min: ");
	    		timeStepmaxLabel.setText("Timestep max: ");
	    		gotoTimeStepLabel.setText("Goto Timestep: ");
	    		
	    		applyButton.setText("Apply Timestep Settings");
	    		
	    		warningLabel.setText("Reaction: ");
	    		
	    		timeStepLabel.setText("Timestep: ");
	    		
	    		timeStepSliderLabel.setText("Timestep Control: ");
	    		fpsLabel.setText("Timesteps per Second: ");
	    		
	    		x0R_flux = ds.getFluxColorValues()[0];
			    x0G_flux = ds.getFluxColorValues()[1];
			   	x0B_flux = ds.getFluxColorValues()[2];
			    aR_flux = ds.getFluxColorValues()[3];
			    aG_flux = ds.getFluxColorValues()[4];
			    aB_flux = ds.getFluxColorValues()[5];
	    		
	    		elementVizRainbowPanel.setRGB(ds.getFluxColorValues());
	    		
	    	}
    	
    	}else if(scheme.equals("Binned")){
    	
    		if(type.equals("Abundance")){
	    	
		    	Vector dataVector = ds.getAbundBinData();
	    	
		    	abundMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
				abundMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
				
				setMinAbund(abundMin);
	    		setMaxAbund(abundMax);
	    	
	    		valueLabel.setText("Abundance: ");
	    		minLabel.setText("Abundance Min: ");
	    		maxLabel.setText("Abundance Max: ");
	    	
	    		timeStepminLabel.setText("Timestep min: ");
	    		timeStepmaxLabel.setText("Timestep max: ");
	    		gotoTimeStepLabel.setText("Goto Timestep: ");
	    		
	    		applyButton.setText("Apply Timestep Settings");
	    		
	    		warningLabel.setText("Isotope: ");
	    		
	    		timeStepLabel.setText("Timestep: ");
	    		
	    		timeStepSliderLabel.setText("Timestep Control: ");
	    		fpsLabel.setText("Timesteps per Second: ");
	    		
	    	}else if(type.equals("Derivative")){
	    	
	    		Vector dataVector = ds.getDerBinData();
	    	
		    	derAbundMax = ((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue();
				derAbundMin = -derAbundMax;
				
				setMinDerAbund(derAbundMin);
	    		setMaxDerAbund(derAbundMax);

		    	valueLabel.setText("Derivative: ");
	    		minLabel.setText("Derivative Min: ");
	    		maxLabel.setText("Derivative Max: ");
	    		
	    		timeStepminLabel.setText("Interval min: ");
	    		timeStepmaxLabel.setText("Interval max: ");
	    		gotoTimeStepLabel.setText("Goto Interval: ");
	    		
	    		applyButton.setText("Apply Interval Settings");
	    	
	    		warningLabel.setText("Isotope: ");
	    	
	    		timeStepLabel.setText("Interval: ");
	    	
	    		timeStepSliderLabel.setText("Interval Control: ");
	    		fpsLabel.setText("Intervals per Second: ");
	    	
	    	}else if(type.equals("Reaction Flux")){
	    	
	    		Vector dataVector = ds.getFluxBinData();
	    	
		    	fluxMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
				fluxMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
				
				setMinFlux(fluxMin);
	    		setMaxFlux(fluxMax);							

	    		valueLabel.setText("Normalized Flux: ");
	    		minLabel.setText("Reaction Flux Min: ");
	    		maxLabel.setText("Reaction Flux Max: ");
	    	
	    		timeStepminLabel.setText("Timestep min: ");
	    		timeStepmaxLabel.setText("Timestep max: ");
	    		gotoTimeStepLabel.setText("Goto Timestep: ");
	    		
	    		applyButton.setText("Apply Timestep Settings");
	    		
	    		warningLabel.setText("Reaction: ");
	    		
	    		timeStepLabel.setText("Timestep: ");
	    		
	    		timeStepSliderLabel.setText("Timestep Control: ");
	    		fpsLabel.setText("Timesteps per Second: ");
	    		
	    		elementVizBinnedFluxPanel.setDataVector(dataVector);
	    		elementVizBinnedFluxPanel.setSize(200, (dataVector.size()-1)*15+50);
				elementVizBinnedFluxPanel.validate();
				elementVizBinnedFluxPanel.setPreferredSize(elementVizBinnedFluxPanel.getSize());
				elementVizBinnedFluxPanel.revalidate();
	    		
	    	}
    	
    	}
    	
		setTimeStep(0);
		setTime(ds.getAnimatorNucSimDataStructure().getTimeArray()[0] - normalTime);
		
		setTemp(ds.getAnimatorNucSimDataStructure().getTempArray()[0]);
		setDensity(ds.getAnimatorNucSimDataStructure().getDensityArray()[0]);
		
		timeStepSlider.removeChangeListener(this);
		timeStepSlider.setMinimum(getTimeStep());
		
		if(type.equals("Abundance") || type.equals("Reaction Flux")){
		
			timeStepSlider.setMaximum(ds.getAnimatorNucSimDataStructure().getTimeArray().length-1);
		
		}else if(type.equals("Derivative")){
		
			timeStepSlider.setMaximum(ds.getAnimatorNucSimDataStructure().getTimeArray().length-2);
		
		}
		
		timeStepSlider.setValue(timeStepSlider.getMinimum());
		timeStepSlider.addChangeListener(this);
		
		setTimeStepmin(timeStepSlider.getMinimum());
		setTimeStepmax(timeStepSlider.getMaximum());
		
		setGotoTimeStep(timeStepSlider.getMinimum());
		
		setFPS(100);

		setChartColors(type, scheme);

		elementVizRainbowPanel.setType(type, scheme);
		elementVizRainbowPanel.repaint();
		elementVizAnimatorPanel.setCurrentState(type, scheme, zmin, zmax);
		elementVizAnimatorPanel.revalidate();
	
		if(ds.getAnimatorNucSimDataStructure().getWaitingDataStructure().getPointMap()!=null){
    		initializeAnimatorWaiting();
    	}else{
    		waitingYesBox.setEnabled(false);
    		waitingYesBox.setSelected(false);
    		waitingYesBox.setSelected(false);
    		waitingMaybeBox.setEnabled(false);
    		waitingMaybeBox.setSelected(false);
    		waitingMaybeBox.setSelected(false);
    		waitingYesButton.setEnabled(false);
    		waitingMaybeButton.setEnabled(false);
    		maybePanel.setColor(Color.GRAY);
    		yesPanel.setColor(Color.GRAY);
    	}
		
		if(ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getListMajor()!=null 
				|| ds.getAnimatorNucSimDataStructure().getBottleDataStructure().getListMajor()!=null){
    		initializeAnimatorBottle();
    	}else{
    		bottleMajorBox.setEnabled(false);
    		bottleMajorBox.setSelected(false);
    		bottleMajorBox.setSelected(false);
    		bottleMinorBox.setEnabled(false);
    		bottleMinorBox.setSelected(false);
    		bottleMinorBox.setSelected(false);
    		bottleColorMajorButton.setEnabled(false);
    		bottleColorMinorButton.setEnabled(false);
    		bottleColorMinorPanel.setColor(Color.GRAY);
    		bottleColorMajorPanel.setColor(Color.GRAY);
    	}
		
		if(ds.getAnimatorNucSimDataStructure().getSumDataStructure().getSum()!=0.0){
    		initializeAnimatorSum();
    	}else{
    		sumField.setText("");
    	}
		
		sp.validate();
	
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(elementVizAnimatorPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)elementVizAnimatorPanel.getPreferredSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizAnimatorPanel.getPreferredSize().getHeight());
	
		ZRuler.setCurrentState(elementVizAnimatorPanel.zmin
								, elementVizAnimatorPanel.zmax
								, elementVizAnimatorPanel.nmin
								, elementVizAnimatorPanel.nmax
								, elementVizAnimatorPanel.mouseX
								, elementVizAnimatorPanel.mouseY
								, elementVizAnimatorPanel.xoffset
								, elementVizAnimatorPanel.yoffset
								, elementVizAnimatorPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizAnimatorPanel.zmin
								, elementVizAnimatorPanel.zmax
								, elementVizAnimatorPanel.nmin
								, elementVizAnimatorPanel.nmax
								, elementVizAnimatorPanel.mouseX
								, elementVizAnimatorPanel.mouseY
								, elementVizAnimatorPanel.xoffset
								, elementVizAnimatorPanel.yoffset
								, elementVizAnimatorPanel.crosshairsOn);
								
		ZRuler.validate();
		NRuler.validate();
		
		setFormatLayout(type, scheme);
	
    }
    
    /**
     * Sets the chart colors.
     *
     * @param type the type
     * @param scheme the scheme
     */
    public void setChartColors(String type, String scheme){

		Color color = Color.black;

		if(scheme.equals("Continuous")){
			
			if(type.equals("Abundance")){
	
				x0R_abund = ds.getAbundColorValues()[0];
			    x0G_abund = ds.getAbundColorValues()[1];
			   	x0B_abund = ds.getAbundColorValues()[2];
			    aR_abund = ds.getAbundColorValues()[3];
			    aG_abund = ds.getAbundColorValues()[4];
			    aB_abund = ds.getAbundColorValues()[5];
				
		    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
					
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
				
					ArrayList<Integer> list = new ArrayList<Integer>();
					for(int u=0; u<ds.getAnimatorNucSimDataStructure().getZAMapArray().length; u++){
						Point p = ds.getAnimatorNucSimDataStructure().getZAMapArray()[u];
						if(p.getX()==13.0 && p.getY()==26.0){
							for(int v=0; v<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray().length; v++){
								if(ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray()[v]==u){
									list.add(v);
								}
							}
						}
					}
					
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length;j++){
					
						float abund = 0.0f;
						if(list.contains(j)){
							for(Integer index: list){
								abund += ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[index];
							}
						}else{
							abund = ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[j];
						}
						
						color = getLogColor(abund, abundMax, abundMin);

						redArray[j] = (short)color.getRed();
						greenArray[j] = (short)color.getGreen();
						blueArray[j] = (short)color.getBlue();
						
					}	
		
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setRedArray(redArray);
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setGreenArray(greenArray);
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setBlueArray(blueArray);
					
		    	}
	    	
	    	}else if(type.equals("Derivative")){
	    	
	    		x0R_der = ds.getDerColorValues()[0];
			    x0G_der = ds.getDerColorValues()[1];
			   	x0B_der = ds.getDerColorValues()[2];
			    aR_der = ds.getDerColorValues()[3];
			    aG_der = ds.getDerColorValues()[4];
			    aB_der = ds.getDerColorValues()[5];
	   
	    		for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length-1; i++){
		
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length;j++){
	

						float derAbund = ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j];
						
						if(derAbund>=Math.pow(10, derAbundMag) || derAbund<=-Math.pow(10, derAbundMag)){
						
							color = getLogColorDerAbund(derAbund, derAbundMax, derAbundMag);
						
						}else{
						
							color = Color.black;
						
						}
						
						redArray[j] = (short)color.getRed();
						greenArray[j] = (short)color.getGreen();
						blueArray[j] = (short)color.getBlue();
						
					}	
		
					ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setRedArray(redArray);
					ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setGreenArray(greenArray);
					ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setBlueArray(blueArray);
					
		    	}
	    	
	    	}else if(type.equals("Reaction Flux")){
	
				x0R_flux = ds.getFluxColorValues()[0];
			    x0G_flux = ds.getFluxColorValues()[1];
			   	x0B_flux = ds.getFluxColorValues()[2];
			    aR_flux = ds.getFluxColorValues()[3];
			    aG_flux = ds.getFluxColorValues()[4];
			    aB_flux = ds.getFluxColorValues()[5];

		    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
	
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					byte[] linestyleArray = new byte[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					byte[] linewidthArray = new byte[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length;j++){
			
						color = getLogColor(Math.abs(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray()[j]), fluxMax, fluxMin);
						
						redArray[j] = (short)color.getRed();
						greenArray[j] = (short)color.getGreen();
						blueArray[j] = (short)color.getBlue();
						linestyleArray[j] = 0;
						linewidthArray[j] = 1;
							
					}	
		
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setRedArray(redArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setGreenArray(greenArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setBlueArray(blueArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setLinestyleArray(linestyleArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setLinewidthArray(linewidthArray);
					
					
		    	}
		    
	    	}
    	
    	}else if(scheme.equals("Binned")){
    	
    		if(type.equals("Abundance")){
    			
    			for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length];
				
					ArrayList<Integer> list = new ArrayList<Integer>();
					for(int u=0; u<ds.getAnimatorNucSimDataStructure().getZAMapArray().length; u++){
						Point p = ds.getAnimatorNucSimDataStructure().getZAMapArray()[u];
						if(p.getX()==13.0 && p.getY()==26.0){
							for(int v=0; v<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray().length; v++){
								if(ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray()[v]==u){
									list.add(v);
								}
							}
						}
					}
					
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray().length;j++){
			
						float abund = 0.0f;
						if(list.contains(j)){
							for(Integer index: list){
								abund += ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[index];
							}
						}else{
							abund = ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[j];
						}
						
						color = getBinnedColor("Abundance", abund);
						
						if(color!=null){
							
							redArray[j] = (short)color.getRed();
							greenArray[j] = (short)color.getGreen();
							blueArray[j] = (short)color.getBlue();
						
						}else{
							
							redArray[j] = -1;
							greenArray[j] = -1;
							blueArray[j] = -1;
						
						}
					
					}	
		
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setRedArray(redArray);
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setGreenArray(greenArray);
					ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].setBlueArray(blueArray);
					
		    	}
    			
    		}else if(type.equals("Derivative")){
    	
    			for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length-1; i++){
		
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length];
					
					if(ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length>0){
					
						for(int j=0; j<ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray().length;j++){
			
							float derAbund = ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j];
							
							color = getBinnedColor("Derivative", derAbund);
							
							if(color!=null){
							
								redArray[j] = (short)color.getRed();
								greenArray[j] = (short)color.getGreen();
								blueArray[j] = (short)color.getBlue();
							
							}else{
								
								redArray[j] = -1;
								greenArray[j] = -1;
								blueArray[j] = -1;
							
							}
						
						}
						
						ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setRedArray(redArray);
						ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setGreenArray(greenArray);
						ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].setBlueArray(blueArray);
						
					}
				
		    	}
    	
    		}else if(type.equals("Reaction Flux")){
    			
    			Vector binDataVector = ds.getFluxBinData();
    			
    			for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
					short[] redArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					short[] greenArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					short[] blueArray = new short[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					byte[] linestyleArray = new byte[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
					byte[] linewidthArray = new byte[ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length];
				
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray().length;j++){

						float value = ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray()[j];

						binFound:
						for(int k=0; k<binDataVector.size(); k++){
						
							int minMag = ((Integer)((Vector)binDataVector.elementAt(k)).elementAt(0)).intValue();
							int maxMag = ((Integer)((Vector)binDataVector.elementAt(k)).elementAt(1)).intValue();
						
							float min = (float)Math.pow(10, minMag);
							float max = (float)Math.pow(10, maxMag);
							
							if(value>=min && value<max){
							
								if(((Boolean)((Vector)binDataVector.elementAt(k)).elementAt(2)).booleanValue()){
								
									color = (Color)((Vector)binDataVector.elementAt(k)).elementAt(3);
									redArray[j] = (short)color.getRed();
									greenArray[j] = (short)color.getGreen();
									blueArray[j] = (short)color.getBlue();	
									linestyleArray[j] = ((Integer)((Vector)binDataVector.elementAt(k)).elementAt(4)).byteValue();
									linewidthArray[j] = ((Integer)((Vector)binDataVector.elementAt(k)).elementAt(5)).byteValue();
									
									break binFound;
									
								}
						
							}else{
									
								redArray[j] = -1;
								greenArray[j] = -1;
								blueArray[j] = -1;
								linestyleArray[j] = 0;
								linewidthArray[j] = 1;
								
							}
						
						}  
						
					}	
		
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setRedArray(redArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setGreenArray(greenArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setBlueArray(blueArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setLinestyleArray(linestyleArray);
					ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].setLinewidthArray(linewidthArray);
					
		    	}
    			
    		}
    	
    	}

    }
    
    /**
     * Gets the abund max.
     *
     * @return the abund max
     */
    public float getAbundMax(){
    
    	float max = 0.0f;
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
			for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){
    				
    			max = Math.max(max, ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[j]);
    		
    		}
    	
    	}
    	
    	return max;
    
    }
    
    /**
     * Gets the abund min.
     *
     * @return the abund min
     */
    public float getAbundMin(){
    
    	float min = 1E38f;
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
			for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){
    			
    			if(Math.min(min, ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[j])!=0.0){
    				
    				min = Math.min(min, ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[i].getAbundArray()[j]);
    		
    			}
    		
    		}
    	
    	}
    	
    	return min;
    
    }
    
    /**
     * Gets the der abund max.
     *
     * @return the der abund max
     */
    public float getDerAbundMax(){
    
    	float max = -1E38f;
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length-1; i++){
		
			for(int j=0; j<ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){
    			
    			max = Math.max(max, Math.abs(ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j]));
	
    		}
    	
    	}

    	return max;
    
    }
    
    /**
     * Gets the flux max.
     *
     * @return the flux max
     */
    public float getFluxMax(){
    
    	float max = 0.0f;
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
			for(int j=0; j<ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
    				
    			max = Math.max(max, Math.abs(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray()[j]));
    		
    		}
    	
    	}
    	
    	return max;
    
    }
    
    /**
     * Gets the flux min.
     *
     * @return the flux min
     */
    public float getFluxMin(){
    
    	float min = 1E38f;
    	
    	for(int i=0; i<ds.getAnimatorNucSimDataStructure().getTimeArray().length; i++){
		
			for(int j=0; j<ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
    			
    			if(Math.min(min, ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray()[j])!=0.0){
    				
    				min = Math.min(min, Math.abs(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[i].getFluxArray()[j]));
    		
    			}
    		
    		}
    	
    	}
    	
    	return min;
    
    }
    
    /**
     * Gets the binned color.
     *
     * @param type the type
     * @param value the value
     * @return the binned color
     */
    public Color getBinnedColor(String type, double value){
    
    	Color tempColor = null;
    
    	Vector binDataVector = new Vector();
    
    	if(type.equals("Abundance")){
    	
    		binDataVector = ds.getAbundBinData();
			
			binFound:
			for(int i=0; i<binDataVector.size(); i++){
			
				int minMag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(0)).intValue();
				int maxMag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
			
				float min = (float)Math.pow(10, minMag);
				float max = (float)Math.pow(10, maxMag);
				
				if(value>=min && value<max){
				
					if(((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue()){
					
						tempColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(3);
						break binFound;
					
					}
					tempColor = null;	
				
				}
			
			}   
				
    	}else if(type.equals("Derivative")){
    	
    	    binDataVector = ds.getDerBinData();		
    	
    		binFound:
    		for(int i=0; i<binDataVector.size(); i++){
			
				int minMag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(0)).intValue();
				int maxMag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
			
				float min = (float)Math.pow(10, minMag);
				float max = (float)Math.pow(10, maxMag);
				
				if(value>0){
				
					if(value>=min && value<max){
					
						if(((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue()){
						
							tempColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(3);
							break binFound;
						
						}
						tempColor = null;	
					
					}
				
				}else if(value<0){
				
					if(value<=-min && value>-max){
					
						if(((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue()){
						
							tempColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(4);
							break binFound;
						
						}
						tempColor = null;	
					
					}
				
				}
			
			}   
    	
    	}
    	
    	return tempColor;
    
    }
    
    /**
     * Gets the log color der abund.
     *
     * @param value the value
     * @param max the max
     * @param mag the mag
     * @return the log color der abund
     */
    public Color getLogColorDerAbund(float value, float max, float mag){
    	
        float normValue = 0.0f;
        
        if(ds.getDerIncludeValues()){
        
	        if(value!=0.0){
	        	
	        	if(value>0){
	        	
	        		float logConstant = 1.0f/(log10*(float)Math.log(max) - mag);  
	
					normValue = logConstant*log10*(float)Math.log(value) - logConstant*mag;
	        	
	        		normValue = 0.5f*normValue + 0.5f;
	        	
	        	}else if(value<0){
	        	
	        		value = -value;
	        	
	        		float logConstant = 1.0f/(log10*(float)Math.log(max) - mag);  
	
					normValue = logConstant*log10*(float)Math.log(value) - logConstant*mag;
					
					normValue = -0.5f*normValue + 0.5f;
	        	
	        	}
	
	        }
        
        	return getRGB(normValue);
        
    	}
		if(value>=-max && value<=max){
  	
			if(value!=0.0){
		    	
		    	if(value>0){
		    	
		    		float logConstant = 1.0f/(log10*(float)Math.log(max) - mag);  

					normValue = logConstant*log10*(float)Math.log(value) - logConstant*mag;
		    	
		    		normValue = 0.5f*normValue + 0.5f;
		    	
		    	}else if(value<0){
		    	
		    		value = -value;
		    	
		    		float logConstant = 1.0f/(log10*(float)Math.log(max) - mag);  

					normValue = logConstant*log10*(float)Math.log(value) - logConstant*mag;
					
					normValue = -0.5f*normValue + 0.5f;
		    	
		    	}

		    }
		
			return getRGB(normValue);
		
		}
		return Color.black;

    }
    
    /**
     * Gets the log color.
     *
     * @param value the value
     * @param max the max
     * @param min the min
     * @return the log color
     */
    public Color getLogColor(float value, float max, float min){
    
        float normValue = 0.0f;
        
        float logConstant = 1.0f/(log10*(float)Math.log(max) - log10*(float)Math.log(min));  
        
        boolean includeValues = false;
        
        if(type.equals("Abundance")){
        
        	includeValues = ds.getAbundIncludeValues();
        
        }else if(type.equals("Reaction Flux")){
        
        	includeValues = ds.getFluxIncludeValues();
        
        }
          
        if(includeValues){ 
         
	        if(value!=0.0){
	
				normValue = logConstant*log10*(float)Math.log(value) - logConstant*log10*(float)Math.log(min);
	
	        }
	
	        return getRGB(normValue); 
        
    	}
		if(value<=max && value>=min){
		
			if(value!=0.0){

				normValue = logConstant*log10*(float)Math.log(value) - logConstant*log10*(float)Math.log(min);

		    }

		    return getRGB(normValue); 
		
		}
		return Color.black;
        
    }
    
    /**
     * Gets the rGB.
     *
     * @param x the x
     * @return the rGB
     */
    public Color getRGB(double x){
    	
    	if(x>=1.0){x = 1.0;}
    	if(x<=0.0){x = 0.0;}
    	
    	int red = 0;
        int green = 0;
        int blue = 0;
    	
    	if(typeComboBox.getSelectedItem().toString().equals("Abundance")){
    	
	        red = (int)(255*Math.exp(-(x-x0R_abund)*(x-x0R_abund)/aR_abund/aR_abund));
	        green = (int)(255*Math.exp(-(x-x0G_abund)*(x-x0G_abund)/aG_abund/aG_abund));
	        blue = (int)(255*Math.exp(-(x-x0B_abund)*(x-x0B_abund)/aB_abund/aB_abund));
        
    	}else if(typeComboBox.getSelectedItem().toString().equals("Derivative")){
    		
    	    red = (int)(255*Math.exp(-(x-x0R_der)*(x-x0R_der)/aR_der/aR_der));
	        green = (int)(255*Math.exp(-(x-x0G_der)*(x-x0G_der)/aG_der/aG_der));
	        blue = (int)(255*Math.exp(-(x-x0B_der)*(x-x0B_der)/aB_der/aB_der));	
	        
    	}else if(typeComboBox.getSelectedItem().toString().equals("Reaction Flux")){
    		
    	    red = (int)(255*Math.exp(-(x-x0R_flux)*(x-x0R_flux)/aR_flux/aR_flux));
	        green = (int)(255*Math.exp(-(x-x0G_flux)*(x-x0G_flux)/aG_flux/aG_flux));
	        blue = (int)(255*Math.exp(-(x-x0B_flux)*(x-x0B_flux)/aB_flux/aB_flux));	
	        
    	}
        
        return new Color(red,green,blue);   
    }
 
    /**
     * Sets the time step.
     *
     * @param timeStep the new time step
     */
    public void setTimeStep(int timeStep){timeStepField.setText(String.valueOf(timeStep));}
    
    /**
     * Gets the time step.
     *
     * @return the time step
     */
    public int getTimeStep(){return Integer.valueOf(timeStepField.getText()).intValue();}
    
    /**
     * Sets the time.
     *
     * @param time the new time
     */
    public void setTime(double time){timeField.setText(NumberFormats.getFormattedValueLong(time));}
    
    /**
     * Gets the time.
     *
     * @return the time
     */
    public double getTime(){return Double.valueOf(timeField.getText()).doubleValue();}
    
    /**
     * Sets the temp.
     *
     * @param temp the new temp
     */
    public void setTemp(double temp){tempField.setText(NumberFormats.getFormattedValueLong(temp));}
    
    /**
     * Gets the temp.
     *
     * @return the temp
     */
    public double getTemp(){return Double.valueOf(tempField.getText()).doubleValue();}
    
    /**
     * Sets the density.
     *
     * @param density the new density
     */
    public void setDensity(double density){densityField.setText(NumberFormats.getFormattedValueLong(density));}
    
    /**
     * Gets the density.
     *
     * @return the density
     */
    public double getDensity(){return Double.valueOf(densityField.getText()).doubleValue();}
    
    /**
     * Sets the fPS.
     *
     * @param fps the new fPS
     */
    public void setFPS(int fps){fpsField.setText(String.valueOf(fps));}
    
    /**
     * Gets the fPS.
     *
     * @return the fPS
     */
    public int getFPS(){return Integer.valueOf(fpsField.getText()).intValue();}
    
    /**
     * Sets the time stepmin.
     *
     * @param timeStepmin the new time stepmin
     */
    public void setTimeStepmin(int timeStepmin){timeStepminField.setText(String.valueOf(timeStepmin));}
    
    /**
     * Gets the time stepmin.
     *
     * @return the time stepmin
     */
    public int getTimeStepmin(){return Integer.valueOf(timeStepminField.getText()).intValue();}
    
    /**
     * Sets the time stepmax.
     *
     * @param timeStepmax the new time stepmax
     */
    public void setTimeStepmax(int timeStepmax){timeStepmaxField.setText(String.valueOf(timeStepmax));}
    
    /**
     * Gets the time stepmax.
     *
     * @return the time stepmax
     */
    public int getTimeStepmax(){return Integer.valueOf(timeStepmaxField.getText()).intValue();}
    
    /**
     * Sets the goto time step.
     *
     * @param gotoTimeStep the new goto time step
     */
    public void setGotoTimeStep(int gotoTimeStep){gotoTimeStepField.setText(String.valueOf(gotoTimeStep));}
    
    /**
     * Gets the goto time step.
     *
     * @return the goto time step
     */
    public int getGotoTimeStep(){return Integer.valueOf(gotoTimeStepField.getText()).intValue();}
    
    /**
     * Sets the min abund.
     *
     * @param minAbund the new min abund
     */
    public void setMinAbund(float minAbund){minField.setText(NumberFormats.getFormattedValueLong(minAbund));}
    
    /**
     * Gets the min abund.
     *
     * @return the min abund
     */
    public float getMinAbund(){return Float.valueOf(minField.getText()).floatValue();}
    
    /**
     * Sets the max abund.
     *
     * @param maxAbund the new max abund
     */
    public void setMaxAbund(float maxAbund){maxField.setText(NumberFormats.getFormattedValueLong(maxAbund));}
    
    /**
     * Gets the max abund.
     *
     * @return the max abund
     */
    public float getMaxAbund(){return Float.valueOf(maxField.getText()).floatValue();}
    
    /**
     * Sets the min der abund.
     *
     * @param minDerAbund the new min der abund
     */
    public void setMinDerAbund(float minDerAbund){minField.setText(NumberFormats.getFormattedValueLong2(minDerAbund));}
    
    /**
     * Gets the min der abund.
     *
     * @return the min der abund
     */
    public float getMinDerAbund(){return Float.valueOf(minField.getText()).floatValue();}
    
    /**
     * Sets the max der abund.
     *
     * @param maxDerAbund the new max der abund
     */
    public void setMaxDerAbund(float maxDerAbund){maxField.setText(NumberFormats.getFormattedValueLong2(maxDerAbund));}
    
    /**
     * Gets the max der abund.
     *
     * @return the max der abund
     */
    public float getMaxDerAbund(){return Float.valueOf(maxField.getText()).floatValue();}
    
    /**
     * Sets the min flux.
     *
     * @param minFlux the new min flux
     */
    public void setMinFlux(float minFlux){minField.setText(NumberFormats.getFormattedValueLong(minFlux));}
    
    /**
     * Gets the min flux.
     *
     * @return the min flux
     */
    public float getMinFlux(){return Float.valueOf(minField.getText()).floatValue();}
    
    /**
     * Sets the max flux.
     *
     * @param maxFlux the new max flux
     */
    public void setMaxFlux(float maxFlux){maxField.setText(NumberFormats.getFormattedValueLong(maxFlux));}
    
    /**
     * Gets the max flux.
     *
     * @return the max flux
     */
    public float getMaxFlux(){return Float.valueOf(maxField.getText()).floatValue();}
    
    //Window closing listener
	/* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  

			if(waitingFrame!=null){
				waitingFrame.setVisible(false);
				waitingFrame.dispose();
				waitingFrame = null;
			}
			
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

		String delayString = "Please be patient while data is loaded. DO NOT operate the Element Synthesis Animator at this time!";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		GridBagConstraints gbc = new GridBagConstraints();
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
	
}

class WaitingColorPanel extends JPanel{
	
	public WaitingColorPanel(){
		setSize(100, 100);
	}
	
	public void setColor(Color color){
		setBackground(color);
	}
	
}
    
    