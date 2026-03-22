package org.nucastrodata.element.elementviz.scale;

import info.clearthought.layout.*;
import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import java.awt.print.*;
import java.text.DecimalFormat;
import java.util.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.scale.ElementVizScaleIsotopeListPanel;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotFramePrintable;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotPanel;
import org.nucastrodata.element.elementviz.scale.ElementVizScaleTableFrame;
import org.nucastrodata.wizard.gui.WordWrapLabel;

/**
 * The Class ElementVizScalePlotControlsPanel.
 */
public class ElementVizScalePlotControlsPanel extends WizardPanel implements ActionListener{

    /** The button panel. */
    private JPanel controlPanel, buttonPanel;
    
    /** The scale combo box. */
    private JComboBox minYComboBox, maxYComboBox
    					, minXComboBox, maxXComboBox
    					, setNameComboBox, scaleComboBox;

    /** The list panel. */
    public ElementVizScaleIsotopeListPanel listPanel;
    
    /** The plot panel. */
    private ElementVizScalePlotPanel plotPanel;
    
    /** The table frame. */
    private ElementVizScaleTableFrame tableFrame;
    
    /** The top panel. */
    private JPanel topPanel;
    
    /** The zoom label. */
    private WordWrapLabel zoomLabel;
    
    /** The apply button. */
    private JButton printButton, saveButton, tableButton, applyButton, autoButton;

    /** The norm box. */
    public JCheckBox majorXBox, majorYBox
		    			, minorXBox, minorYBox
		    			, legendBox, normBox;
    
    public JRadioButton logButtonX, logButtonY, linButtonX, linButtonY; 
    
	/** The max x field. */
	private JTextField maxYField, minYField
						, minXField, maxXField;
	
	/** The min x label. */
	private JLabel maxYLabel, minYLabel
					, maxXLabel, minXLabel
					, abundLabel, scaleLabel;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /** The map. */
    private TreeMap<Double, NucSimDataStructure> map;
    
    /** The ynorm. */
    public double[][] x, y, yreg, ynorm;
    
    /** The index. */
    public int[] index;
    
    /** The factors. */
    public double[] factors;
    
    /**
     * The Enum Mode.
     */
    public enum Mode{
					/** The ABUND. */
					ABUND, 
					 /** The NORM. */
					NORM
	}
    
    /** The mode. */
    public Mode mode; 
    
    public enum PlotType{
    	LOG, LIN;
    }
    
    public PlotType plotTypeX, plotTypeY;
    
    /** The scale factor. */
    public double scaleFactor;
    
    /** The set name. */
    public String setName;
    
    private DecimalFormat df = new DecimalFormat("0.000E0");
    
    private JScrollPane plotPane;
    
    /**
     * Instantiates a new element viz scale plot controls panel.
     *
     * @param ds the ds
     */
    public ElementVizScalePlotControlsPanel(ElementVizDataStructure ds){

		this.ds = ds;
		
		addWizardPanel("Sensitivity Study Plotting Interface", "Plot Final Abundance vs. Rate Scale Factor", "2", "2");
		
		WordWrapLabel topLabel = new WordWrapLabel();
		topLabel.setText("Choose either final abundance vs. rate scale factor or " +
								"normalized final abundance vs. rate scale factor using the radio buttons " +
								"below right. If the normalized final abudance choice is selected, select " +
								"a rate scale factor from the dropdown menu below right. Select a different sensitivity " +
								"study from the associated dropdown menu below right.");
		topPanel = new JPanel();
		double[] colTop = {TableLayoutConstants.FILL};
		double[] rowTop = {TableLayoutConstants.PREFERRED};
		topPanel.setLayout(new TableLayout(colTop, rowTop));
		topPanel.add(topLabel, "0, 0, f, c");
		
		listPanel = new ElementVizScaleIsotopeListPanel(ds, this);
		JScrollPane listPane = new JScrollPane(listPanel);
		
		logButtonX = new JRadioButton("Log");
		logButtonX.addActionListener(this);
		linButtonX = new JRadioButton("Lin");
		linButtonX.addActionListener(this);
		ButtonGroup groupX = new ButtonGroup();
		groupX.add(logButtonX);
		groupX.add(linButtonX);
		
		logButtonY = new JRadioButton("Log");
		logButtonY.addActionListener(this);
		linButtonY = new JRadioButton("Lin");
		linButtonY.addActionListener(this);
		ButtonGroup groupY = new ButtonGroup();
		groupY.add(logButtonY);
		groupY.add(linButtonY);
		
		abundLabel = new JLabel("Final Abund");
		abundLabel.setFont(Fonts.titleFont);
		
		scaleLabel = new JLabel("Rate Scale Factor");
		scaleLabel.setFont(Fonts.titleFont);
		
		printButton = new JButton("Print");
		printButton.addActionListener(this);
		printButton.setFont(Fonts.buttonFont);

		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setFont(Fonts.buttonFont);

		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);

		applyButton = new JButton("Apply Range/Domain Settings");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);

		autoButton = new JButton("Autoscale Plot");
		autoButton.setFont(Fonts.buttonFont);
		autoButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(printButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(autoButton);
		buttonPanel.add(tableButton);
		
		controlPanel = new JPanel();
		
		legendBox = new JCheckBox("Show Legend?", true);
		legendBox.setFont(Fonts.textFont);
		legendBox.addActionListener(this);
		
		normBox = new JCheckBox("Normalize to Scale Factor", false);
		normBox.setFont(Fonts.textFont);
		normBox.addActionListener(this);		
		
		minYComboBox = new JComboBox();
        minYComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-30; i--) {minYComboBox.addItem(Integer.toString(i));}
		minYComboBox.addActionListener(this);

        maxYComboBox = new JComboBox();
        maxYComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-29; i--) {maxYComboBox.addItem(Integer.toString(i));}
		maxYComboBox.addActionListener(this);
		
		minXComboBox = new JComboBox();
        minXComboBox.setFont(Fonts.textFont);
        for(int i=29; i>=-30; i--) {minXComboBox.addItem(Integer.toString(i));}
		minXComboBox.addActionListener(this);

        maxXComboBox = new JComboBox();
        maxXComboBox.setFont(Fonts.textFont);
        for(int i=30; i>=-29; i--) {maxXComboBox.addItem(Integer.toString(i));}
		maxXComboBox.addActionListener(this);
		
		majorXBox = new JCheckBox("Major Gridlines", true);
		majorXBox.setFont(Fonts.textFont);
		majorXBox.addActionListener(this);
		
		majorYBox = new JCheckBox("Major Gridlines", true);
		majorYBox.setFont(Fonts.textFont);
		majorYBox.addActionListener(this);
		
		minorXBox = new JCheckBox("Minor Gridlines", false);
		minorXBox.setFont(Fonts.textFont);
		minorXBox.addActionListener(this);
		
		minorYBox = new JCheckBox("Minor Gridlines", false);
		minorYBox.setFont(Fonts.textFont);
		minorYBox.addActionListener(this);		
		
		setNameComboBox = new JComboBox();
		setNameComboBox.setFont(Fonts.textFont);

		scaleComboBox = new JComboBox();
		scaleComboBox.setFont(Fonts.textFont);
		
		maxYField = new JTextField();
		minYField = new JTextField();
		minXField = new JTextField();
		maxXField = new JTextField();
		
		maxYLabel = new JLabel("Max");
		maxYLabel.setFont(Fonts.textFont);
		
		minYLabel = new JLabel("Min");
		minYLabel.setFont(Fonts.textFont);
		
		maxXLabel = new JLabel("Max");
		maxXLabel.setFont(Fonts.textFont);
		
		minXLabel = new JLabel("Min");
		minXLabel.setFont(Fonts.textFont);
		
		zoomLabel = new WordWrapLabel();
		zoomLabel.setText("Plot Controls (Hold down your left mouse button over plot to magnify)");
		
		plotPanel = new ElementVizScalePlotPanel(ds, this);
		plotPane = new JScrollPane(plotPanel);
		
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, plotPane, listPane);
       	jsp.setDividerLocation(550);
		
       	double[] col = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED, 10};
		setLayout(new TableLayout(col, row));
       	
		add(topPanel, "1, 1 ,f, c");
       	add(jsp, "1, 3, f, f");
       	add(controlPanel, "1, 5, f, c");
       	add(buttonPanel, "1, 7, f, c");
	}
    
    /**
     * Gets the map.
     *
     * @return the map
     */
    public TreeMap<Double, NucSimDataStructure> getMap(){return map;}
    
    /**
     * Sets the scale factor.
     *
     * @param scaleIndex the new scale factor
     */
    public void setScaleFactor(int scaleIndex){
		scaleFactor=x[0][scaleIndex];
    	for(int i=0; i<yreg.length; i++){
    		for(int j=0; j<yreg[i].length; j++){
    			ynorm[i][j] = yreg[i][j]/yreg[i][scaleIndex];
    		}
    	}
    	y = ynorm;
    	setYLimits();
    	redrawPlot();
    }
    
    /**
     * Gets the isotope array.
     *
     * @return the isotope array
     */
    private int[] getIsotopeArray(){
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	Iterator<NucSimDataStructure> itr = map.values().iterator();
    	while(itr.hasNext()){
    		NucSimDataStructure sim = itr.next();
    		AbundTimestepDataStructure timestep = sim.getAbundTimestepDataStructureArray()[0];
    		int[] indexArray = timestep.getIndexArray();
    		for(int index: indexArray){
    			if(!list.contains(index) && index!=-1){
    				list.add(index);
    			}
    		}
    	}
    	Collections.sort(list);
    	int[] array = new int[list.size()];
    	for(int i=0; i<list.size(); i++){
    		array[i] = list.get(i);
    	}
    	return array;
    }
    
    /**
     * Checks if is array index non zero.
     *
     * @param counter the counter
     * @return true, if is array index non zero
     */
    public boolean isArrayIndexNonZero(int counter){
    	double[] array = yreg[counter];
    	for(int i=0; i<array.length; i++){
    		if(array[0]>=1E-30){
    			return true;
    		}
    	}
    	return false;
    }

    private double[] getScaleFactorArray(){
    	double[] array = new double[map.keySet().size()];
    	Iterator<Double> itr = map.keySet().iterator();
    	int counter = 0;
    	while(itr.hasNext()){
    		array[counter] = itr.next();
    		counter++;
    	}
    	return array;
    }
    
    public void setSetName(String setName){
    	
    	this.setName = setName;
    	map = ds.getSensMap().get(setName);
    	index = getIsotopeArray();
    	factors = getScaleFactorArray();
    	
    	x = new double[index.length][factors.length];
    	yreg = new double[index.length][factors.length];
    	ynorm = new double[index.length][factors.length];
    	
    	for(int i=0; i<index.length; i++){
    		for(int j=0; j<factors.length; j++){
    			double scaleFactor = factors[j];
    			int isotopeIndex = index[i];
    			double abund = map.get(scaleFactor).getAbundTimestepDataStructureArray()[0].getAbundFromIsotopeIndex(isotopeIndex);
    			x[i][j] = scaleFactor;
    			yreg[i][j] = abund;
    		}
    	}
    	
    	y = yreg;
    	
    }
   
	public void setCurrentState(){
		setCurrentState(null, null, null);
	}
	
	public void setCurrentState(Mode mode){
		setCurrentState(mode, plotTypeX, plotTypeY);
	}
    
	public void setPlotLimits(){
		setYLimits();
		setXLimits();
		redrawPlot();
	}
	
	public void setYLimits(){
		
		double linMinY = getLinMin(y);
    	double linMaxY = getLinMax(y);
    	if(linMaxY<=linMinY){
    		linMaxY = linMinY + 1;
    	}
    	minYField.setText(df.format(linMinY));
    	maxYField.setText(df.format(linMaxY));
    	
    	int logMinY = getLogMin(y);
    	int logMaxY = getLogMax(y);
    	if(logMaxY<=logMinY){
    		logMaxY = logMinY + 1;
    	}
    	minYComboBox.removeActionListener(this);
    	maxYComboBox.removeActionListener(this);
    	minYComboBox.setSelectedItem(String.valueOf(logMinY));
    	maxYComboBox.setSelectedItem(String.valueOf(logMaxY));
    	minYComboBox.addActionListener(this);
    	maxYComboBox.addActionListener(this);
    	
	}
	
	public void setXLimits(){

		double linMinX = getLinMin(x);
    	double linMaxX = getLinMax(x);
    	if(linMaxX<=linMinX){
    		linMaxX = linMinX + 1;
    	}
    	minXField.setText(df.format(linMinX));
    	maxXField.setText(df.format(linMaxX));
    	
    	int logMinX = getLogMin(x);
    	int logMaxX = getLogMax(x);
    	if(logMaxX<=logMinX){
    		logMaxX = logMinX + 1;
    	}
    	minXComboBox.removeActionListener(this);
    	maxXComboBox.removeActionListener(this);
    	minXComboBox.setSelectedItem(String.valueOf(logMinX));
    	maxXComboBox.setSelectedItem(String.valueOf(logMaxX));
    	minXComboBox.addActionListener(this);
    	maxXComboBox.addActionListener(this);
    	
	}
	
    /**
	 * Sets the current state.
	 *
	 * @param mode the new current state
	 */
	public void setCurrentState(Mode mode, PlotType plotTypeX, PlotType plotTypeY){
		
		this.mode = mode;
		this.plotTypeX = plotTypeX;
		this.plotTypeY = plotTypeY;
		
		if(this.plotTypeX==null){
			this.plotTypeX = PlotType.LIN;
			linButtonX.setSelected(true);
		}
		
		if(this.plotTypeY==null){
			this.plotTypeY = PlotType.LOG;
			logButtonY.setSelected(true);
		}
		
		if(this.mode==null){
			this.mode = Mode.ABUND;
			listPanel.setCurrentState();
			plotPanel.setCurrentState(this.mode, plotTypeX, plotTypeY);
	   		plotPanel.repaint();
		}
		
		if(this.mode==Mode.ABUND){
			abundLabel.setText("Final Abund");
			y = yreg;
		}else{
			abundLabel.setText("Norm Final Abund");
			y = ynorm;
		}
		
		setPlotLimits();
		
		controlPanel.removeAll();

		double[] col = {TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.FILL
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.FILL
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED
				, 5, TableLayoutConstants.PREFERRED};
		
		controlPanel.setLayout(new TableLayout(col, row));
		
		controlPanel.add(zoomLabel,               "0, 0, 18, 0, c, c");
		
		controlPanel.add(abundLabel,              "0, 2, r, c");
		controlPanel.add(logButtonY,              "2, 2, r, c");
		controlPanel.add(linButtonY,              "4, 2, r, c");
		controlPanel.add(minYLabel,               "6, 2, r, c");
		if(this.plotTypeY==PlotType.LOG){
			controlPanel.add(minYComboBox,        "8, 2, f, c");
		}else{
			controlPanel.add(minYField,           "8, 2, f, c");
		}
		controlPanel.add(maxYLabel,               "10, 2, r, c");
		if(this.plotTypeY==PlotType.LOG){
			controlPanel.add(maxYComboBox,        "12, 2, f, c");
		}else{
			controlPanel.add(maxYField,           "12, 2, f, c");
		}
		controlPanel.add(majorYBox,               "14, 2, f, c");
		controlPanel.add(minorYBox, 			  "16, 2, f, c");
		controlPanel.add(legendBox,               "18, 2, f, c");
		
		
		controlPanel.add(scaleLabel,              "0, 4, r, c");
		controlPanel.add(logButtonX,              "2, 4, r, c");
		controlPanel.add(linButtonX,              "4, 4, r, c");
		controlPanel.add(minXLabel,               "6, 4, r, c");
		if(this.plotTypeX==PlotType.LOG){
			controlPanel.add(minXComboBox,        "8, 4, f, c");
		}else{
			controlPanel.add(minXField,           "8, 4, f, c");
		}
		controlPanel.add(maxXLabel,               "10, 4, r, c");
		if(this.plotTypeX==PlotType.LOG){
			controlPanel.add(maxXComboBox,        "12, 4, f, c");
		}else{
			controlPanel.add(maxXField,           "12, 4, f, c");
		}
		controlPanel.add(majorXBox,               "14, 4, f, c");
		controlPanel.add(minorXBox,               "16, 4, f, c");
		controlPanel.add(new JLabel(),            "18, 4, f, c");
		
		validate();
		
		repaint();

	}
	
    /**
     * Redraw plot.
     *
     * @return true, if successful
     */
    public boolean redrawPlot(){
    	
    	String stringEx = "";
    	
    	try{
    	
	    	String stringX = "Rate scale factor minimum must be less than rate scale factor maximum.";
	    	String stringY = "Final abundance minimum must be less than final abundance maximum.";
	    	String stringYNorm = "Normalized final abundance minimum must be less than normalized final abundance maximum.";
	   
	    	String stringExX = "Values for rate scale factor maximum and minimum must be numeric values.";
	    	String stringExY = "Values for final abundance maximum and minimum must be numeric values.";
	    	String stringExYNorm = "Values for normalized final abundancer maximum and minimum must be numeric values.";

	    	if(plotTypeX==PlotType.LOG){
	    		if(Integer.valueOf(minXComboBox.getSelectedItem().toString()).intValue()
	    				>= Integer.valueOf(maxXComboBox.getSelectedItem().toString()).intValue()){
		   			Dialogs.createExceptionDialog(stringX, Cina.elementVizFrame.elementVizScalePlotFrame);
		   			return false; 
	   			}
			}else{
				stringEx = stringExX;
				if(Double.valueOf(minXField.getText()).doubleValue() >= Double.valueOf(maxXField.getText()).doubleValue()){
	   				Dialogs.createExceptionDialog(stringX, Cina.elementVizFrame.elementVizScalePlotFrame);
	   				return false; 
	   			}
			}
			
	    	if(plotTypeY==PlotType.LOG){
	    		if(Integer.valueOf(minYComboBox.getSelectedItem().toString()).intValue()
	    				>= Integer.valueOf(maxYComboBox.getSelectedItem().toString()).intValue()){
		   			Dialogs.createExceptionDialog(mode==Mode.ABUND ? stringY : stringYNorm, Cina.elementVizFrame.elementVizScalePlotFrame);
		   			return false; 
	   			}
			}else{
				stringEx = mode==Mode.ABUND ? stringExY : stringExYNorm;
				if(Double.valueOf(minYField.getText()).doubleValue() >= Double.valueOf(maxYField.getText()).doubleValue()){
	   				Dialogs.createExceptionDialog(mode==Mode.ABUND ? stringY : stringYNorm, Cina.elementVizFrame.elementVizScalePlotFrame);
	   				return false; 
	   			}
			}
    	
	    	plotPanel.setCurrentState(mode, plotTypeX, plotTypeY);
	   		plotPanel.repaint();
	   		return true;
	    	
    	}catch(NumberFormatException nfe){
   			Dialogs.createExceptionDialog(stringEx, Cina.elementVizFrame.elementVizScalePlotFrame);
   			return false; 
   		}
    	
    }
    
    public int getLogMin(double[][] array){
    	double min = 1e30;
		int logmin = 0;
		for(int i=0; i<array.length; i++){
			if(listPanel.boxList.get(i).isSelected()){
	    		for(int j=0; j<array[i].length; j++){
					min = Math.min(min, array[i][j]);
	    		}
			}
		}
		logmin = (int)Math.floor(0.434294482*Math.log(min));
		if(logmin<-30){
			logmin = -30;
		}
		return logmin;
    }
    
    public int getLogMax(double[][] array){
    	double max = 0;
		int logmax = 0;
		for(int i=0; i<array.length; i++){
			if(listPanel.boxList.get(i).isSelected()){
	    		for(int j=0; j<array[i].length; j++){
					max = Math.max(max, array[i][j]);
	    		}
			}
    	}
		logmax = (int)Math.ceil(0.434294482*Math.log(max));
		if(logmax>30){
			logmax = 30;
		}
		return logmax;
    }
    
    public double getLinMin(double[][] array){
    	double min = Double.MAX_VALUE;
    	for(int i=0; i<array.length; i++){
    		if(listPanel.boxList.get(i).isSelected()){
	    		for(int j=0; j<array[i].length; j++){
	    			min = Math.min(array[i][j], min);
	    		}
    		}
    	}
    	return min;
    }
    
    public double getLinMax(double[][] array){
    	double max = Double.MIN_VALUE;
    	for(int i=0; i<array.length; i++){
    		if(listPanel.boxList.get(i).isSelected()){
	    		for(int j=0; j<array[i].length; j++){
	    			max = Math.max(array[i][j], max);
	    		}
    		}
    	}
    	return max;
    }
    
    public double getAbundMin(){
    	if(plotTypeY==PlotType.LOG){
    		return Math.pow(10, getLogMinY());
		}
    	return getLinMinY();
    }
    
    public double getAbundMax(){
    	if(plotTypeY==PlotType.LOG){
    		return Math.pow(10, getLogMaxY());
		}
    	return getLinMaxY();
    }
    
    public double getScaleMin(){
    	if(plotTypeX==PlotType.LOG){
    		return Math.pow(10, getLogMinX());
		}
    	return getLinMinX();
    }
    
    public double getScaleMax(){
    	if(plotTypeX==PlotType.LOG){
    		return Math.pow(10, getLogMaxX());
		}
    	return getLinMaxX();
    }
    
    public double getLogMinY(){return Double.valueOf((String)minYComboBox.getSelectedItem()).doubleValue();} 
	public double getLogMaxY(){return Double.valueOf((String)maxYComboBox.getSelectedItem()).doubleValue();}
	
	public double getLinMinY(){return Double.valueOf(minYField.getText()).doubleValue();} 
	public double getLinMaxY(){return Double.valueOf(maxYField.getText()).doubleValue();}
	
	public double getLogMinX(){return Double.valueOf((String)minXComboBox.getSelectedItem()).doubleValue();} 
	public double getLogMaxX(){return Double.valueOf((String)maxXComboBox.getSelectedItem()).doubleValue();}
	
	public double getLinMinX(){return Double.valueOf(minXField.getText()).doubleValue();} 
	public double getLinMaxX(){return Double.valueOf(maxXField.getText()).doubleValue();}

	public boolean getMinorY(){return minorYBox.isSelected();} 
	public boolean getMajorY(){return majorYBox.isSelected();} 
	public boolean getMinorX(){return minorXBox.isSelected();} 
	public boolean getMajorX(){return majorXBox.isSelected();} 
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
       if(ae.getSource()==tableButton){
       		if(!listPanel.noneChecked()){
	       		if(tableFrame==null){
	       			tableFrame = new ElementVizScaleTableFrame(this);
		       	}
	       		tableFrame.setTableText();
	       		tableFrame.setVisible(true);
	       	}else{
	       		String string = "Please select isotopes for the Table of Points from the checkbox list.";
	       		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizScalePlotFrame);
	       	}
	       	
       	}else if(ae.getSource()==printButton){
       		PlotPrinter.print(new ElementVizScalePlotFramePrintable(plotPanel), Cina.elementVizFrame.elementVizScalePlotFrame);
       	}else if(ae.getSource()==saveButton){
       		PlotSaver.savePlot(plotPanel, Cina.elementVizFrame.elementVizScalePlotFrame);
       	}else if(ae.getSource()==autoButton){
       		setPlotLimits();
       	}else if(ae.getSource()==linButtonX 
       			|| ae.getSource()==logButtonX
       			|| ae.getSource()==linButtonY 
       			|| ae.getSource()==logButtonY){
       		setCurrentState(mode
       						, linButtonX.isSelected() ? PlotType.LIN : PlotType.LOG
       						, linButtonY.isSelected() ? PlotType.LIN : PlotType.LOG);
       	}else if(ae.getSource()==majorXBox){
            if(majorXBox.isSelected()){
            	minorXBox.setEnabled(true);
            }else{
            	minorXBox.setSelected(false);
            	minorXBox.setEnabled(false);
            }
            redrawPlot();
        }else if(ae.getSource()==majorYBox){
            if(majorYBox.isSelected()){
            	minorYBox.setEnabled(true);
            }else{
            	minorYBox.setSelected(false);
            	minorYBox.setEnabled(false);
            }
            redrawPlot();
        }else if(ae.getSource()==minorYBox || ae.getSource()==minorXBox
        			|| ae.getSource()==minYComboBox || ae.getSource()==maxYComboBox
        			|| ae.getSource()==minXComboBox || ae.getSource()==maxXComboBox
        			|| ae.getSource()==legendBox || ae.getSource()==applyButton){
            redrawPlot();
        }
    }
   
    /**
     * Close all frames.
     */
    public void closeAllFrames(){
    	if(tableFrame!=null){
	    	tableFrame.setVisible(false);
	    	tableFrame.dispose();
    	}
    }
    
}  

class ElementVizScalePlotFramePrintable implements Printable{

	private ElementVizScalePlotPanel plotPanel;
	
	public ElementVizScalePlotFramePrintable(ElementVizScalePlotPanel plotPanel){
		this.plotPanel = plotPanel;
	}
	
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		plotPanel.paintMe(g);
        return PAGE_EXISTS;
	}

}
