package org.nucastrodata.element.elementviz.sum;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.print.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.PlotPrinter;
import org.nucastrodata.PlotSaver;

public class ElementVizSumPlotFrame extends JFrame implements ItemListener, ActionListener, WindowListener{
    
    Toolkit toolkit;

    JPanel controlPanel;

    ElementVizNucSimListPanel elementVizNucSimListPanel;

    ElementVizSumPlotPanel elementVizSumPlotPanel;

    public ElementVizSumTableFrame elementVizSumTableFrame;
    
    JButton printButton, saveButton, tableButton, applyButton;
    
    public JComboBox XComboBox;

    JCheckBox majorXCheckBox, majorYCheckBox, minorXCheckBox, minorYCheckBox;
    
    JCheckBox legendBox;

    JComboBox abundminComboBox, abundmaxComboBox, diffminComboBox, diffmaxComboBox;
    
	JTextField xmaxField, xminField, diffminField, diffmaxField;
	
	JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel, xGridLabel, yGridLabel, XComboBoxLabel;

	int abundminComboBoxInit;
    int abundmaxComboBoxInit;

    Container c;
    JScrollPane sp, sp1;
    GridBagConstraints gbc;
    
    TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap;
    TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> fracDiffMap;
    
    public ElementVizSumPlotFrame(TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap){
	
    	this.finalAbundMap = finalAbundMap;
    	
		c = getContentPane();
	
		setSize(1275, 800);
		
		setVisible(true);
		
		addWindowListener(this);
		
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();

		createNucSimListPanel();
		initializeNucSimListPanel();
		createFormatPanel();
		initializeFormatPanel();
		createResultsPlot();
		validate();
		
	}
	
    public void setFinalAbundMap(TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> finalAbundMap){
    	this.finalAbundMap = finalAbundMap;
    }
    
    public TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> getFinalAbundMap(){
    	return finalAbundMap;
    }
    
    public void calcFracDiffMap(){
    	
    	String y1 = elementVizNucSimListPanel.y1Box.getSelectedItem().toString();
    	String y2 = elementVizNucSimListPanel.y2Box.getSelectedItem().toString();
    	int diffType = elementVizNucSimListPanel.diffBox.getSelectedIndex();
    	
    	TreeMap<Integer, Double> y1Map = finalAbundMap.get(y1).get(getXType());
    	TreeMap<Integer, Double> y2Map = finalAbundMap.get(y2).get(getXType());
    	TreeMap<Integer, Double> valueMap = new TreeMap<Integer, Double>();
    	
    	Iterator<Integer> y1Itr = y1Map.keySet().iterator();
    	while(y1Itr.hasNext()){
    		int y1Int = y1Itr.next();
    		if(y2Map.containsKey(y1Int)){
    			if(diffType==0){
    				valueMap.put(y1Int, calcDiffType1(y1Map.get(y1Int), y2Map.get(y1Int)));
    			}else if(diffType==1){
    				valueMap.put(y1Int, calcDiffType2(y1Map.get(y1Int), y2Map.get(y1Int)));
    			}
    		}
    	}
    	
    	fracDiffMap = new TreeMap<String, HashMap<String, TreeMap<Integer, Double>>>();
    	fracDiffMap.put(y1 + " vs. " + y2, new HashMap<String, TreeMap<Integer, Double>>());
    	fracDiffMap.get(y1 + " vs. " + y2).put(getXType(), valueMap);
    }
    
    public TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> getFracDiffMap(){
    	return fracDiffMap;
    }
    
    private double calcDiffType1(double y1, double y2){
    	double value = (y2-y1)/y1;
    	return value;
    }
    
    private double calcDiffType2(double y1, double y2){
    	double value = (y2-y1)/(0.5*(y2+y1));
    	return value;
    }
    
    public void closeAllFrames(){
    	if(elementVizSumTableFrame!=null){
			elementVizSumTableFrame.setVisible(false);
			elementVizSumTableFrame.dispose();
		}
    }
    
    public void createNucSimListPanel(){
    
    	JPanel nucSimListPanel = new JPanel();
    	
        nucSimListPanel.setLayout(new GridBagLayout());
		
		elementVizNucSimListPanel = new ElementVizNucSimListPanel(this);
	
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTH; 
        gbc.insets = new Insets(5,5,5,5);  
        
        nucSimListPanel.add(elementVizNucSimListPanel, gbc);   
          
        sp = new JScrollPane(nucSimListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(200, 400));
    
    }
    
    public void initializeNucSimListPanel(){
    	elementVizNucSimListPanel.initialize();
    }
    
    public void createFormatPanel(){  
               
        gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   
		
		JPanel buttonPanel = new JPanel();
        
        buttonPanel.setLayout(new GridLayout(1, 5, 5, 5));
		
		/////BUTTONS/////////////////////////////////////////////////////BUTTONS////////////////////
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
        
		applyButton = new JButton("Apply Entered Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);

        buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);

        XComboBoxLabel = new JLabel("Select Z, N, or A : ");
        
        XComboBox = new JComboBox();
        XComboBox.addItem("Z (proton number)");
        XComboBox.addItem("N (neutron number)");
        XComboBox.addItem("A = Z + N");
        XComboBox.setSelectedItem("Z (proton number)");
        XComboBox.addItemListener(this);
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        //Create ComboBoxs///////////////////////////////////////////////CHOICES//////////
        abundminComboBox = new JComboBox();
        abundminComboBox.setFont(Fonts.textFont);
        for(int i=-1; i>=-25; i--) {abundminComboBox.addItem(Integer.toString(i));}
        abundminComboBox.setSelectedItem(String.valueOf(abundminComboBoxInit));
        abundminComboBox.addItemListener(this);

        abundmaxComboBox = new JComboBox();
        abundmaxComboBox.setFont(Fonts.textFont);
        for(int i=0; i>=-24; i--) {abundmaxComboBox.addItem(Integer.toString(i));}
        abundmaxComboBox.setSelectedItem(String.valueOf(abundmaxComboBoxInit));
        abundmaxComboBox.addItemListener(this);
        
        diffminComboBox = new JComboBox();
        diffminComboBox.setFont(Fonts.textFont);
        for(int i=24; i>=-25; i--) {diffminComboBox.addItem(Integer.toString(i));}
        diffminComboBox.addItemListener(this);

        diffmaxComboBox = new JComboBox();
        diffmaxComboBox.setFont(Fonts.textFont);
        for(int i=25; i>=-24; i--) {diffmaxComboBox.addItem(Integer.toString(i));}
        diffmaxComboBox.addItemListener(this);
        
        //CREATE FIELDS/////////////////////////////////////////////////////////FIELDS/////////////////////
        xmaxField = new JTextField(7);
        xminField = new JTextField(7);
        
        diffminField = new JTextField(7);
        diffmaxField = new JTextField(7);

        //LABELS///////////////////////////////////////////////////////////////LABELS/////////////////
        xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Z Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("log Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        yminLabel = new JLabel("log Abund Min");
        yminLabel.setFont(Fonts.textFont);
        
        JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
        
        //CHECKBOXES///////////////////////////////////////////////////////CHECKBOXES////////////////////
        majorXCheckBox = new JCheckBox("Major Gridlines", true);
        minorXCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorYCheckBox = new JCheckBox("Major Gridlines", true);
        minorYCheckBox = new JCheckBox("Minor Gridlines", false);
        
        majorXCheckBox.addItemListener(this);
        minorXCheckBox.addItemListener(this);
        majorYCheckBox.addItemListener(this);
        minorYCheckBox.addItemListener(this);

		majorXCheckBox.setFont(Fonts.textFont);
        minorXCheckBox.setFont(Fonts.textFont);
        majorYCheckBox.setFont(Fonts.textFont);
        minorYCheckBox.setFont(Fonts.textFont);

        legendBox = new JCheckBox("Show Legend?", true);
	    legendBox.setFont(Fonts.textFont);
	    legendBox.addItemListener(this);
        
  		///////////////////////////////////////////////PUT IT ALL TOGETHER//////////////////////////////////////////////////////////////////
  		
  		gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridwidth = 1;
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(XComboBoxLabel, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(abundminComboBox, gbc);
  
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(ymaxLabel, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(abundmaxComboBox, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorYCheckBox, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYCheckBox, gbc);
  		
  		gbc.gridx = 7;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(legendBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(XComboBox, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(xminField, gbc);
  
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(xmaxField, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(majorXCheckBox, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXCheckBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 7;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;

        c.add(controlPanel, BorderLayout.SOUTH);
             
    }
    
	public void initializeFormatPanel(){
	
		setTitle("Final Abundance Profile Plotting Interface");
	
		XComboBox.setSelectedItem("Z (proton number)");
		
		abundminComboBoxInit = calcAbundminComboBoxInit("Z");
		abundmaxComboBoxInit = calcAbundmaxComboBoxInit("Z");

		abundminComboBox.removeItemListener(this);
		abundmaxComboBox.removeItemListener(this);
		
		abundminComboBox.setSelectedItem(String.valueOf(abundminComboBoxInit));
		abundmaxComboBox.setSelectedItem(String.valueOf(abundmaxComboBoxInit));
        
        abundminComboBox.addItemListener(this);
        abundmaxComboBox.addItemListener(this);
        
        xminField.setText(String.valueOf(calcXminFieldInit("Z", "abund")));
        xmaxField.setText(String.valueOf(calcXmaxFieldInit("Z", "abund")));
	
	}
	
	public void setFormatPanelState(String yType, boolean isLin){
		
		String xType = getXType(); 
    	
    	calcFracDiffMap();
    	
    	xminLabel.setText(xType + " Max");
		xminField.setText(String.valueOf(calcXminFieldInit(xType, yType)));
        xmaxField.setText(String.valueOf(calcXmaxFieldInit(xType, yType)));
		
		if(yType.equals("abund")){
			
			yminLabel.setText("log Abund Min");
			ymaxLabel.setText("log Max");
			
			controlPanel.remove(diffminComboBox);
			controlPanel.remove(diffmaxComboBox);
			controlPanel.remove(diffminField);
			controlPanel.remove(diffmaxField);
			
			abundminComboBox.removeItemListener(this);
    		abundmaxComboBox.removeItemListener(this);
    		
    		abundminComboBox.setSelectedItem(String.valueOf(calcAbundminComboBoxInit(getXType())));
    		abundmaxComboBox.setSelectedItem(String.valueOf(calcAbundmaxComboBoxInit(getXType())));
            
            abundminComboBox.addItemListener(this);
            abundmaxComboBox.addItemListener(this);
			
			gbc.gridx = 2;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(abundminComboBox, gbc);
			
			gbc.gridx = 4;
	  		gbc.gridy = 1;
	  		gbc.anchor = GridBagConstraints.EAST;
	  		controlPanel.add(abundmaxComboBox, gbc);
			
		}else if(yType.equals("diff")){
			
			if(isLin){
				
				yminLabel.setText("Diff Min");
				ymaxLabel.setText("Max");
				
				controlPanel.remove(abundminComboBox);
				controlPanel.remove(abundmaxComboBox);
				controlPanel.remove(diffminComboBox);
				controlPanel.remove(diffmaxComboBox);
				
				diffminField.setText(String.valueOf(calcDiffminFieldInit(getXType())));
				diffmaxField.setText(String.valueOf(calcDiffmaxFieldInit(getXType())));
				
				gbc.gridx = 2;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.EAST;
		  		controlPanel.add(diffminField, gbc);
				
				gbc.gridx = 4;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.EAST;
		  		controlPanel.add(diffmaxField, gbc);
		  		
			}else{
				
				yminLabel.setText("log Diff Min");
				ymaxLabel.setText("log Max");
				
				controlPanel.remove(abundminComboBox);
				controlPanel.remove(abundmaxComboBox);
				controlPanel.remove(diffminField);
				controlPanel.remove(diffmaxField);
				
				diffminComboBox.removeItemListener(this);
        		diffmaxComboBox.removeItemListener(this);
        		
    			diffminComboBox.setSelectedItem(String.valueOf(calcDiffminComboBoxInit(getXType())));
    			diffmaxComboBox.setSelectedItem(String.valueOf(calcDiffmaxComboBoxInit(getXType())));
 
    			diffminComboBox.addItemListener(this);
    			diffmaxComboBox.addItemListener(this);
    			
				gbc.gridx = 2;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.EAST;
		  		controlPanel.add(diffminComboBox, gbc);
				
				gbc.gridx = 4;
		  		gbc.gridy = 1;
		  		gbc.anchor = GridBagConstraints.EAST;
		  		controlPanel.add(diffmaxComboBox, gbc);
				
			}
			
		}
		
		validate();
		redrawPlot();
		
	}
    
    /**
     * Creates the results plot.
     */
    public void createResultsPlot(){
    	elementVizSumPlotPanel = new ElementVizSumPlotPanel(this);
		elementVizSumPlotPanel.setPreferredSize(elementVizSumPlotPanel.getSize());
		elementVizSumPlotPanel.revalidate();
		sp1 = new JScrollPane(elementVizSumPlotPanel);
		JViewport vp = new JViewport();
		vp.setView(elementVizSumPlotPanel);
		sp1.setBackground(Color.white);		
		sp1.setViewport(vp);
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp);
       	jsp.setDividerLocation(760);
       	c.add(jsp, BorderLayout.CENTER);
		setVisible(true);
		elementVizSumPlotPanel.setPreferredSize(elementVizSumPlotPanel.getSize());
		elementVizSumPlotPanel.revalidate();
    	
    }
    
    public double calcDiffmaxFieldInit(String xType){
    	double max = 1.0E-30;
    	HashMap<String, TreeMap<Integer, Double>> map = fracDiffMap.get(fracDiffMap.keySet().iterator().next());
    	TreeMap<Integer, Double> m = map.get(xType);
    	Iterator<Double> itr = m.values().iterator();
    	while(itr.hasNext()){
    		max = Math.max(itr.next(), max);
    	}
    	max = Math.ceil(max);
    	if(max > 100){
    		max = 100;
    	}
    	return max;
    }
    
    public double calcDiffminFieldInit(String xType){
    	double min = 1.0E30;
    	HashMap<String, TreeMap<Integer, Double>> map = fracDiffMap.get(fracDiffMap.keySet().iterator().next());
    	TreeMap<Integer, Double> m = map.get(xType);
    	Iterator<Double> itr = m.values().iterator();
    	while(itr.hasNext()){
    		min = Math.min(itr.next(), min);
    	}
    	min = Math.floor(min);
    	if(min < -100){
    		min = -100;
    	}
    	return min;
    }
    
    public int calcDiffminComboBoxInit(String xType){
    	double min = 1.0E30;
    	HashMap<String, TreeMap<Integer, Double>> map = fracDiffMap.get(fracDiffMap.keySet().iterator().next());
    	TreeMap<Integer, Double> m = map.get(xType);
    	Iterator<Double> itr = m.values().iterator();
    	while(itr.hasNext()){
    		min = Math.min(Math.abs(itr.next()), min);
    	}
		int diffMin = (int)Math.floor(0.434294482*Math.log(min));
		return diffMin;
	}
	
	public int calcDiffmaxComboBoxInit(String xType){
		double max = 1.0E-30;
    	HashMap<String, TreeMap<Integer, Double>> map = fracDiffMap.get(fracDiffMap.keySet().iterator().next());
    	TreeMap<Integer, Double> m = map.get(xType);
    	Iterator<Double> itr = m.values().iterator();
    	while(itr.hasNext()){
    		max = Math.max(Math.abs(itr.next()), max);
    	}
		int diffMax = (int)Math.ceil(0.434294482*Math.log(max));
		return diffMax;
	}
    
    public int calcAbundminComboBoxInit(String xType){
    	double min = 1.0;
    	Iterator<String> itr = finalAbundMap.keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(!xType.equals("Z") && elementVizNucSimListPanel.inZOnlyList(name)){
    			continue;
    		}
    		HashMap<String, TreeMap<Integer, Double>> m = finalAbundMap.get(name);
    		TreeMap<Integer, Double> tm = m.get(xType);
    		Iterator<Double> itrtm = tm.values().iterator();
    		while(itrtm.hasNext()){
    	  		min = Math.min(itrtm.next(), min);
    		}
    	}
		int abundMin = (int)Math.floor(0.434294482*Math.log(min));
		return abundMin;
	}
	
	public int calcAbundmaxComboBoxInit(String xType){
		double max = 1e-30;
		Iterator<String> itr = finalAbundMap.keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(!xType.equals("Z") && elementVizNucSimListPanel.inZOnlyList(name)){
    			continue;
    		}
    		HashMap<String, TreeMap<Integer, Double>> m = finalAbundMap.get(name);
    		TreeMap<Integer, Double> tm = m.get(xType);
    		Iterator<Double> itrtm = tm.values().iterator();
    		while(itrtm.hasNext()){
    	  		max = Math.max(itrtm.next(), max);
    		}
    	}
		int abundMax = (int)Math.ceil(0.434294482*Math.log(max));
		return abundMax;
	}
    
	public int calcXminFieldInit(String xType, String yType){
    	TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> map = null;
    	if(yType.equals("abund")){
    		map = this.getFinalAbundMap();
    	}else if(yType.equals("diff")){
    		map = this.getFracDiffMap();
    	}
    	int min = 1000;
    	Iterator<String> itr = map.keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(!xType.equals("Z") && elementVizNucSimListPanel.inZOnlyList(name)){
    			continue;
    		}
    		HashMap<String, TreeMap<Integer, Double>> m = map.get(name);
    		min = Math.min(m.get(xType).firstKey(), min);
    	}
    	return min;
    }
    
    public int calcXmaxFieldInit(String xType, String yType){
    	TreeMap<String, HashMap<String, TreeMap<Integer, Double>>> map = null;
    	if(yType.equals("abund")){
    		map = this.getFinalAbundMap();
    	}else if(yType.equals("diff")){
    		map = this.getFracDiffMap();
    	}
    	int max = 0;
    	Iterator<String> itr = map.keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(!xType.equals("Z") && elementVizNucSimListPanel.inZOnlyList(name)){
    			continue;
    		}
    		HashMap<String, TreeMap<Integer, Double>> m = map.get(name);
    		max = Math.max(m.get(xType).lastKey(), max);
    	}
    	return max;
    }
	
    public void itemStateChanged(ItemEvent ie){
        
      	if(ie.getSource()==majorXCheckBox){
      		
            if(majorXCheckBox.isSelected()){
            	
                minorXCheckBox.setEnabled(true);
                
            }else{
            	
                minorXCheckBox.setSelected(false);
                minorXCheckBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorYCheckBox){
        	
            if(majorYCheckBox.isSelected()){
            	
                minorYCheckBox.setEnabled(true);
                
            }else{

                minorYCheckBox.setSelected(false);
                minorYCheckBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==minorXCheckBox){
        	
            redrawPlot();
            
        }else if(ie.getSource()==minorYCheckBox){
        	
            redrawPlot();
           
        }else if(ie.getSource()==legendBox){
        	
       		redrawPlot();
            
        }else if(ie.getSource()==abundminComboBox || ie.getSource()==abundmaxComboBox){
	
       		redrawPlot();
       		
        }else if(ie.getSource()==diffminComboBox || ie.getSource()==diffmaxComboBox){
        	
       		redrawPlot();

        }else if(ie.getSource()==XComboBox){
        	if(XComboBox.getSelectedIndex()!=0){
        		elementVizNucSimListPanel.setZOnlyEnabled(false);
        	}else{
        		elementVizNucSimListPanel.setZOnlyEnabled(true);
        	}
        	elementVizNucSimListPanel.setYComboBoxItems(getXType());
            setFormatPanelState(getYType(), elementVizNucSimListPanel.linButton.isSelected());
        	redrawPlot();
        }

    }
    
    public String getXType(){
    	String type = "";
    	if(XComboBox.getSelectedIndex()==0){
    		type = "Z";
    	}else if(XComboBox.getSelectedIndex()==1){
    		type = "N";
    	}else if(XComboBox.getSelectedIndex()==2){
    		type = "A";
    	}
    	return type;
    }
    
    public String getYType(){
    	String type = "";
    	if(this.elementVizNucSimListPanel.diffButton.isSelected()){
    		type = "diff";
    	}else if(this.elementVizNucSimListPanel.abundButton.isSelected()){
    		type = "abund";
    	}
    	return type;
    }
    
    public int getAbundmin(){return Integer.valueOf((String)abundminComboBox.getSelectedItem()).intValue();} 
	public int getAbundmax(){return Integer.valueOf((String)abundmaxComboBox.getSelectedItem()).intValue();}
	
	public int getDiffminLog(){return Integer.valueOf((String)diffminComboBox.getSelectedItem()).intValue();} 
	public int getDiffmaxLog(){return Integer.valueOf((String)diffmaxComboBox.getSelectedItem()).intValue();}
	
	public double getDiffminLin(){return Double.valueOf(diffminField.getText()).doubleValue();} 
	public double getDiffmaxLin(){return Double.valueOf(diffmaxField.getText()).doubleValue();}
	
	public int getXmin(){return Integer.valueOf(xminField.getText()).intValue();} 
	public int getXmax(){return Integer.valueOf(xmaxField.getText()).intValue();}
	
	/**
	 * Gets the minor x.
	 *
	 * @return the minor x
	 */
	public boolean getMinorX(){return minorXCheckBox.isSelected();} 
	
	/**
	 * Gets the major x.
	 *
	 * @return the major x
	 */
	public boolean getMajorX(){return majorXCheckBox.isSelected();} 
	
	/**
	 * Gets the minor y.
	 *
	 * @return the minor y
	 */
	public boolean getMinorY(){return minorYCheckBox.isSelected();} 
	
	/**
	 * Gets the major y.
	 *
	 * @return the major y
	 */
	public boolean getMajorY(){return majorYCheckBox.isSelected();} 
    
    /**
     * Redraw plot.
     */
    public void redrawPlot(){
       	
   		//try{
   		
   			/*if(Double.valueOf(tempminField.getText()).doubleValue() >= Double.valueOf(tempmaxField.getText()).doubleValue()){
   			
   				String string = "Temperature minimum must be less than temperature maximum.";
   				
   				Dialogs.createExceptionDialog(string, this);
   			
   			}else if(Double.valueOf(timeminField.getText()).doubleValue() >= Double.valueOf(timemaxField.getText()).doubleValue()){
   			
   				String string = "Time minimum must be less than time maximum.";
   				
   				Dialogs.createExceptionDialog(string, this);
   			
   			}else{*/
   			
   				elementVizSumPlotPanel.setPreferredSize(elementVizSumPlotPanel.getSize());
	
				elementVizSumPlotPanel.setPlotState();
		   		
		   		elementVizSumPlotPanel.repaint();
   			
   			//}
   		
   		//}catch(NumberFormatException nfe){
   			
   			//String string = "Values for temperature/time minimum and maximum must be numeric values.";
   	
   			//Dialogs.createExceptionDialog(string, this);
   	
   		//}
   		
    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    	boolean noneChecked = true;
    	for(JCheckBox box: elementVizNucSimListPanel.boxList){
    		if(box.isSelected()){
    			noneChecked = false;
    		}
    	}
    	return noneChecked;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	
       if(ae.getSource()==tableButton){
       	
       		if(!noneChecked()){
       	
	       		if(elementVizSumTableFrame==null){
	       	
	       			elementVizSumTableFrame = new ElementVizSumTableFrame(this);
	       		
		       	}else{
		       	
		       		elementVizSumTableFrame.setTableText();
		       		elementVizSumTableFrame.setVisible(true);
		       	
		       	}
		       	
		 	}else{
	       	
	       		String string = "Please select simulations for the Table of Points from the checkbox list."; 
	       		
	       		Dialogs.createExceptionDialog(string, this);
	       		
	       	}
	       	
	    }else if(ae.getSource()==applyButton){
	       	
	       	redrawPlot();
	       	
       	}else if(ae.getSource()==printButton){
       	
       		PlotPrinter.print(new ElementVizSumPlotFramePrintable(), this);
       	
       	}else if(ae.getSource()==saveButton){
       	
       		PlotSaver.savePlot(elementVizSumPlotPanel, this);
       		
       	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent we){
    	
        setVisible(false);
        dispose();
        
    }
    
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
}  


class ElementVizSumPlotFramePrintable implements Printable{

	public int print(Graphics g, PageFormat pf, int pageIndex){
	
		if(pageIndex!=0){return NO_SUCH_PAGE;}

		Cina.elementVizFrame.elementVizSumPlotFrame.elementVizSumPlotPanel.paintMe(g);

        return PAGE_EXISTS;
		
	}

}
