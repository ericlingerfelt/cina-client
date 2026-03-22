package org.nucastrodata.element.elementviz.finalabund;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.ElementVizIsotopePanel;
import org.nucastrodata.element.elementviz.util.ElementVizRainbowPanel;
import org.nucastrodata.Cina;
import org.nucastrodata.Corner;
import org.nucastrodata.Fonts;
import org.nucastrodata.IsotopeRuler;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PlotSaver;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.element.elementviz.finalabund.ElementVizFinalAbundColorSettingsFrame;
import org.nucastrodata.element.elementviz.finalabund.ElementVizFinalAbundPanel;
import org.nucastrodata.element.elementviz.finalabund.ElementVizFinalAbundPrintPanel;

public class ElementVizFinalAbundFrame extends JFrame implements ActionListener, ChangeListener, WindowListener, ItemListener{

    JScrollPane rainbowPane, isotopePanelPane, finalAbundPanelPane;
    Container c;
    GridBagConstraints gbc;
    JLabel nucSimLabel, valueLabel, zoomLabel, warningLabel, minLabel, maxLabel;
	JTextField valueField, maxField, minField, zoomField;
	JCheckBox showLabelsCheckBox, showStableCheckBox;
	JButton colorButton, saveButton, okButton, tableButton;
	JSlider zoomSlider;
    public JComboBox nucSimComboBox;
	JPanel rightPanel;
    JRadioButton whiteTextRadioButton, blackTextRadioButton;
    ElementVizRainbowPanel elementVizRainbowPanel;
    ElementVizIsotopePanel elementVizIsotopePanel;
    ElementVizFinalAbundPanel elementVizFinalAbundPanel;
	public ElementVizFinalAbundColorSettingsFrame elementVizFinalAbundColorSettingsFrame;
	public ElementVizFinalAbundTableFrame elementVizFinalAbundTableFrame;
    double finalAbundMax, finalAbundMin;
    JDialog delayDialog, saveDialog;
    float log10 = 0.434294482f;
	double x0R = 0.8;
    double x0G = 0.6;
    double x0B = 0.2;
    double aR = 0.5;
    double aG = 0.4;
    double aB = 0.3; 
    IsotopeRuler ZRuler, NRuler;
    String scheme = "";
    String simulation = "";
    private ElementVizDataStructure ds;
    
    public ElementVizFinalAbundFrame(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	c = getContentPane();
    	
        c.setLayout(new BorderLayout());

		gbc = new GridBagConstraints();

		setTitle("Final Weighted Abundance Nuclide Chart");
		setSize(new Dimension(908, 645));
		
		//LABELS//////////////////////////////////////////////LABELS//////////////////////
		nucSimLabel = new JLabel("Choose Simulation: ");
		
		warningLabel = new JLabel("<html>Place the cursor over an isotope<p>to read its final weighted abundance</html>");
    	
    	valueLabel = new JLabel("Final Weighted Abund: ");
    	valueLabel.setFont(Fonts.textFont);
    	
    	maxLabel = new JLabel("Final Weighted Abund Max: ");
    	maxLabel.setFont(Fonts.textFont);
    	
    	minLabel = new JLabel("Final Weighted Abund Min: ");
    	minLabel.setFont(Fonts.textFont);
		
		zoomLabel = new JLabel("Zoom (%): ");
		zoomLabel.setFont(Fonts.textFont);
		
		//COMBOBOX//////////////////////////////////////////////COMBOBOX/////////////////////
		nucSimComboBox = new JComboBox();
		nucSimComboBox.setFont(Fonts.textFont);
		nucSimComboBox.addItemListener(this);
		
		//FIELD/////////////////////////////////////////////////FIELDS///////////////////////
   		
   		valueField = new JTextField(10);
   		valueField.setEditable(false);
   		
   		maxField = new JTextField(10);
   		maxField.setEditable(false);
   		
   		minField = new JTextField(10);
   		minField.setEditable(false);
   		
		zoomField = new JTextField(3);
		zoomField.setEditable(false);
		
		//CHECKBOX//////////////////////////////////////////////CHECKBOX////////////////////
		showLabelsCheckBox = new JCheckBox("Isotope Labels", false);
		showLabelsCheckBox.setFont(Fonts.textFont);
		showLabelsCheckBox.addItemListener(this);
		
		showStableCheckBox = new JCheckBox("Stable Isotopes", false);
		showStableCheckBox.setFont(Fonts.textFont);
		showStableCheckBox.addItemListener(this);
		
		//SLIDERS///////////////////////////////////////////////SLIDERS/////////////////////
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
		zoomSlider.addChangeListener(this);
		
		//BUTTONS//////////////////////////////////////////////BUTTONS////////////////////////
		
		colorButton = new JButton("<html>Final Weighted Abund<p>Color Scale Settings</html>");
		colorButton.setFont(Fonts.buttonFont);
		colorButton.addActionListener(this);

		saveButton = new JButton("Save Chart as Image");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		tableButton = new JButton("Table of Points");
		tableButton.setFont(Fonts.buttonFont);
		tableButton.addActionListener(this);

		elementVizRainbowPanel = new ElementVizRainbowPanel(ds);
		rainbowPane = new JScrollPane(elementVizRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rainbowPane.setPreferredSize(new Dimension(50, 100));
		
		elementVizIsotopePanel = new ElementVizIsotopePanel();
		isotopePanelPane = new JScrollPane(elementVizIsotopePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		isotopePanelPane.setPreferredSize(new Dimension(200, 30));
	
		//PANELS/////////////////////////////////////////////////////////////////////PANELS//////////////////////////
		rightPanel = new JPanel(new GridBagLayout());

    		ds.setFinalNucSimSetDataStructure(ds.getFinalNucSimSetDataStructureArray()[0]);
		
		elementVizFinalAbundPanel = new ElementVizFinalAbundPanel(ds);
		elementVizFinalAbundPanel.setCurrentState("Continuous");
		
        finalAbundPanelPane = new JScrollPane(elementVizFinalAbundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		JViewport vp = new JViewport();
		
		vp.setView(elementVizFinalAbundPanel);
		
		finalAbundPanelPane.setViewport(vp);
	
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)elementVizFinalAbundPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizFinalAbundPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(elementVizFinalAbundPanel.zmax
								, elementVizFinalAbundPanel.nmax
								, elementVizFinalAbundPanel.mouseX
								, elementVizFinalAbundPanel.mouseY
								, elementVizFinalAbundPanel.xoffset
								, elementVizFinalAbundPanel.yoffset
								, elementVizFinalAbundPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizFinalAbundPanel.zmax
								, elementVizFinalAbundPanel.nmax
								, elementVizFinalAbundPanel.mouseX
								, elementVizFinalAbundPanel.mouseY
								, elementVizFinalAbundPanel.xoffset
								, elementVizFinalAbundPanel.yoffset
								, elementVizFinalAbundPanel.crosshairsOn);
	
		finalAbundPanelPane.setColumnHeaderView(ZRuler);
        finalAbundPanelPane.setRowHeaderView(NRuler);
	
		finalAbundPanelPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        finalAbundPanelPane.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        finalAbundPanelPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		finalAbundPanelPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		finalAbundPanelPane.getHorizontalScrollBar().setValue(finalAbundPanelPane.getHorizontalScrollBar().getMinimum());
		finalAbundPanelPane.getVerticalScrollBar().setValue(finalAbundPanelPane.getVerticalScrollBar().getMaximum());

        addWindowListener(this);
    } 
    
    public void closeAllFrames(){
    	if(elementVizFinalAbundColorSettingsFrame!=null){
    		elementVizFinalAbundColorSettingsFrame.closeAllFrames();
			elementVizFinalAbundColorSettingsFrame.setVisible(false);
			elementVizFinalAbundColorSettingsFrame.dispose();
		}
    }
    
    public void setFormatLayout(){
    
    	c.removeAll();

		rightPanel.removeAll();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(nucSimLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(nucSimComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 4;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(rainbowPane, gbc);
		
		gbc.gridheight = 1;
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(maxLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(maxField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(minLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(minField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(warningLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(colorButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(isotopePanelPane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		rightPanel.add(valueLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(valueField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(showLabelsCheckBox, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(showStableCheckBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.anchor = GridBagConstraints.EAST;
		rightPanel.add(zoomLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 11;
		gbc.anchor = GridBagConstraints.WEST;
		rightPanel.add(zoomField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(zoomSlider, gbc);

		gbc.gridx = 0;
		gbc.gridy = 13;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(saveButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 14;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		rightPanel.add(tableButton, gbc);

		gbc.gridwidth = 1;

    	rightPanel.setSize(rightPanel.getPreferredSize());
    	rightPanel.validate();
    	
    	c.add(finalAbundPanelPane, BorderLayout.CENTER);
    	c.add(rightPanel, BorderLayout.EAST);
    	
    	validate();
  
    }

    public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==nucSimComboBox){
    	
    		if(delayDialog==null){
    	
    			openDelayDialog(this);

	    		simulation = nucSimComboBox.getSelectedItem().toString();
	    		scheme = ds.getFinalWeightedAbundScheme();

	    		nucSimComboBox.removeItemListener(this);
	    	
	    		final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
					
						initializeFinalAbund(simulation, scheme);		
						return new Object();
						
					}
					
					public void finished(){
						
						closeDelayDialog();
						
						nucSimComboBox.addItemListener(Cina.elementVizFrame.elementVizFinalAbundFrame);
						
					}
					
				};
				
				worker.start();
				
				if(elementVizFinalAbundColorSettingsFrame!=null){
					
					elementVizFinalAbundColorSettingsFrame.setCurrentState(ds.getFinalWeightedAbundScheme());
					elementVizFinalAbundColorSettingsFrame.setSize(elementVizFinalAbundColorSettingsFrame.getPreferredSize());
					elementVizFinalAbundColorSettingsFrame.validate();
	
	        	}
	        	
	        }
			
    	}else if(ie.getSource()==showLabelsCheckBox){
    	
    		elementVizFinalAbundPanel.repaint();
    	
    	}else if(ie.getSource()==showStableCheckBox){
    	
    		elementVizFinalAbundPanel.repaint();
    	
    	}
    	
    }
    
    public void actionPerformed(ActionEvent ae){
	    
	    if(ae.getSource()==colorButton){
	    	
    		if(elementVizFinalAbundColorSettingsFrame!=null){
				
			elementVizFinalAbundColorSettingsFrame.setSize(862, 400);
			elementVizFinalAbundColorSettingsFrame.setCurrentState(ds.getFinalWeightedAbundScheme());
        		elementVizFinalAbundColorSettingsFrame.setVisible(true);
        		elementVizFinalAbundColorSettingsFrame.validate();

        	}else{
        	
        		elementVizFinalAbundColorSettingsFrame = new ElementVizFinalAbundColorSettingsFrame(ds);
        		elementVizFinalAbundColorSettingsFrame.setSize(862, 400);
        		elementVizFinalAbundColorSettingsFrame.setCurrentState(ds.getFinalWeightedAbundScheme());
        		elementVizFinalAbundColorSettingsFrame.setVisible(true);
        		elementVizFinalAbundColorSettingsFrame.validate();
        			        	
        	}
	    
	    }else if(ae.getSource()==saveButton){
	    
	    		createSaveDialog();
	    
	    }else if(ae.getSource()==okButton){
	    	
		    	saveDialog.setVisible(false);
		    	saveDialog.dispose();
		    
		    	PlotSaver.savePlot(new ElementVizFinalAbundPrintPanel(whiteTextRadioButton.isSelected(), ds), this);
	    	
	    }else if(ae.getSource()==tableButton){
	    
	    		if(elementVizFinalAbundTableFrame==null){
		       	
	    			elementVizFinalAbundTableFrame = new ElementVizFinalAbundTableFrame(ds);
       		
	       	}else{
	       	
	       		elementVizFinalAbundTableFrame.setTableText();
	       		elementVizFinalAbundTableFrame.setVisible(true);
	       	
	       	}
	    
	    }
    
    }
    
    public void stateChanged(ChangeEvent ce){
    
    	if(ce.getSource()==zoomSlider){
    		
    		if(zoomSlider.getValue()==100){
    		
    			finalAbundPanelPane.setColumnHeaderView(ZRuler);
        		finalAbundPanelPane.setRowHeaderView(NRuler);
    		
    		}else{
    		
    			finalAbundPanelPane.setColumnHeaderView(null);
        		finalAbundPanelPane.setRowHeaderView(null);
    		
    		}
    		
    		double scale = ((double)zoomSlider.getValue())/100.0;
			elementVizFinalAbundPanel.boxHeight = (int)(29.0*scale);
			elementVizFinalAbundPanel.boxWidth = (int)(29.0*scale);
	        
        	elementVizFinalAbundPanel.width = (int)(elementVizFinalAbundPanel.boxWidth*(elementVizFinalAbundPanel.nmax+1));
	        elementVizFinalAbundPanel.height = (int)(elementVizFinalAbundPanel.boxHeight*(elementVizFinalAbundPanel.zmax+1));
	        
	        elementVizFinalAbundPanel.xmax = (int)(elementVizFinalAbundPanel.xoffset + elementVizFinalAbundPanel.width);
	        elementVizFinalAbundPanel.ymax = (int)(elementVizFinalAbundPanel.yoffset + elementVizFinalAbundPanel.height);

        	elementVizFinalAbundPanel.setSize(elementVizFinalAbundPanel.xmax+elementVizFinalAbundPanel.xoffset, elementVizFinalAbundPanel.ymax+elementVizFinalAbundPanel.yoffset);
        			
			elementVizFinalAbundPanel.setPreferredSize(elementVizFinalAbundPanel.getSize());
    		
    		elementVizFinalAbundPanel.repaint();

			zoomField.setText(String.valueOf(zoomSlider.getValue()));
    		
    		if(!zoomSlider.getValueIsAdjusting()){
    		
	    		finalAbundPanelPane.getHorizontalScrollBar().setValue(finalAbundPanelPane.getHorizontalScrollBar().getMinimum());
				finalAbundPanelPane.getVerticalScrollBar().setValue(finalAbundPanelPane.getVerticalScrollBar().getMaximum());
			
			}
    		
    		finalAbundPanelPane.validate();
    		
    	}

    }
    
    public void initialize(){
    	
    	nucSimComboBox.removeItemListener(this);
    	nucSimComboBox.removeAllItems();
    		
		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
				
			String string = ds.getFinalNucSimSetDataStructureArray()[i].getPath();
	    	nucSimComboBox.addItem(string.substring(string.lastIndexOf("/")+1));

	  	}
    	
    	nucSimComboBox.setSelectedIndex(0);
    	nucSimComboBox.addItemListener(this);
    	
    	simulation = (String)nucSimComboBox.getSelectedItem();
    	
    	scheme = ds.getFinalWeightedAbundScheme();

    	initializeFinalAbund(simulation, scheme);
    
	}
    
    public void initializeFinalAbund(String nucSim, String scheme){
    
    	zoomField.setText(String.valueOf(zoomSlider.getValue()));
    	
    	for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){

			String string = ds.getFinalNucSimSetDataStructureArray()[i].getPath();

	        if(string.substring(string.lastIndexOf("/")+1).equals(nucSim)){

    			ds.setFinalNucSimSetDataStructure(ds.getFinalNucSimSetDataStructureArray()[i]);
    	
    		}
    	
    	}

		if(scheme.equals("Continuous")){
		
			finalAbundMax = getFinalAbundMax();
	    	finalAbundMin = getFinalAbundMin();
	    	
	    	setMinFinalAbund(finalAbundMin);
	    	setMaxFinalAbund(finalAbundMax);
    		
    		x0R = ds.getFinalWeightedAbundColorValues()[0];
		    x0G = ds.getFinalWeightedAbundColorValues()[1];
		   	x0B = ds.getFinalWeightedAbundColorValues()[2];
		    aR = ds.getFinalWeightedAbundColorValues()[3];
		    aG = ds.getFinalWeightedAbundColorValues()[4];
		    aB = ds.getFinalWeightedAbundColorValues()[5];
    		
    		elementVizRainbowPanel.setRGB(ds.getFinalWeightedAbundColorValues());
		
		}else if(scheme.equals("Binned")){
		
			Vector dataVector = ds.getFinalWeightedAbundBinData();
	    	
	    	finalAbundMax = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(0))).elementAt(1)).intValue());
			finalAbundMin = (float)Math.pow(10,((Integer)((Vector)(dataVector.elementAt(dataVector.size()-1))).elementAt(0)).intValue());
			
			setMinFinalAbund(finalAbundMin);
    		setMaxFinalAbund(finalAbundMax);
		
		}

		setChartColors(scheme);

		elementVizRainbowPanel.setType("Abundance", scheme);
		elementVizRainbowPanel.repaint();

		elementVizFinalAbundPanel.setCurrentState(scheme);
		elementVizFinalAbundPanel.revalidate();
	
		finalAbundPanelPane.getHorizontalScrollBar().setMinimum(0);
		finalAbundPanelPane.getVerticalScrollBar().setMaximum(elementVizFinalAbundPanel.getHeight());
	
		finalAbundPanelPane.getHorizontalScrollBar().setValue(finalAbundPanelPane.getHorizontalScrollBar().getMinimum());
		finalAbundPanelPane.getVerticalScrollBar().setValue(finalAbundPanelPane.getVerticalScrollBar().getMaximum());
		
		ZRuler.setPreferredWidth((int)elementVizFinalAbundPanel.getPreferredSize().getWidth());
       	NRuler.setPreferredHeight((int)elementVizFinalAbundPanel.getPreferredSize().getHeight());
	
		ZRuler.setCurrentState(elementVizFinalAbundPanel.zmax
								, elementVizFinalAbundPanel.nmax
								, elementVizFinalAbundPanel.mouseX
								, elementVizFinalAbundPanel.mouseY
								, elementVizFinalAbundPanel.xoffset
								, elementVizFinalAbundPanel.yoffset
								, elementVizFinalAbundPanel.crosshairsOn);
								
    	NRuler.setCurrentState(elementVizFinalAbundPanel.zmax
								, elementVizFinalAbundPanel.nmax
								, elementVizFinalAbundPanel.mouseX
								, elementVizFinalAbundPanel.mouseY
								, elementVizFinalAbundPanel.xoffset
								, elementVizFinalAbundPanel.yoffset
								, elementVizFinalAbundPanel.crosshairsOn);
								
		ZRuler.validate();
		NRuler.validate();
		
		setFormatLayout();
	
    }
    
    public void setChartColors(String scheme){

		Color color = Color.black;

		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<ds.getFinalNucSimSetDataStructure().getZAMapArray().length; i++){
			Point p = ds.getFinalNucSimSetDataStructure().getZAMapArray()[i];
			if(p.getX()==13.0 && p.getY()==26.0){
				for(int j=0; j<ds.getFinalNucSimSetDataStructure().getIndexArray().length; j++){
					if(ds.getFinalNucSimSetDataStructure().getIndexArray()[j]==i){
						list.add(j);
					}
				}
			}
		}
		
		if(scheme.equals("Continuous")){
			
			x0R = ds.getFinalWeightedAbundColorValues()[0];
		    x0G = ds.getFinalWeightedAbundColorValues()[1];
		   	x0B = ds.getFinalWeightedAbundColorValues()[2];
		    aR = ds.getFinalWeightedAbundColorValues()[3];
		    aG = ds.getFinalWeightedAbundColorValues()[4];
		    aB = ds.getFinalWeightedAbundColorValues()[5];
				
			short[] redArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
			short[] greenArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
			short[] blueArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
			
			for(int i=0; i<ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length; i++){
			
				double finalAbund = 0.0;
				
				if(list.contains(i)){
					for(Integer index: list){
						finalAbund += ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[index];
					}
				}else{
					finalAbund = ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[i];
				}
				
				color = getLogColor(finalAbund
									, finalAbundMax
									, finalAbundMin);

				redArray[i] = (short)color.getRed();
				greenArray[i] = (short)color.getGreen();
				blueArray[i] = (short)color.getBlue();
				
			}	

			ds.getFinalNucSimSetDataStructure().setRedArray(redArray);
			ds.getFinalNucSimSetDataStructure().setGreenArray(greenArray);
			ds.getFinalNucSimSetDataStructure().setBlueArray(blueArray);
    	
	    }else if(scheme.equals("Binned")){
	    
			short[] redArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
			short[] greenArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
			short[] blueArray = new short[ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length];
		
			for(int i=0; i<ds.getFinalNucSimSetDataStructure().getFinalAbundArray().length; i++){
	
				color = null;
    
		    	Vector binDataVector = ds.getFinalWeightedAbundBinData();
				
		    	double value = 0.0;
		    	if(list.contains(i)){
					for(Integer index: list){
						value += ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[index];
					}
				}else{
					value = ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[i];
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

			ds.getFinalNucSimSetDataStructure().setRedArray(redArray);
			ds.getFinalNucSimSetDataStructure().setGreenArray(greenArray);
			ds.getFinalNucSimSetDataStructure().setBlueArray(blueArray);
			
	    }

    }
    
    public double getFinalAbundMax(){
    
    	double max = 0.0f;
    	
		for(int i=0; i<ds.getFinalNucSimSetDataStructure().getIndexArray().length; i++){
				
			max = Math.max(max, ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[i]);
		
		}
    	
    	return max;
    
    }
    
    public double getFinalAbundMin(){
    
    	double min = 1E38f;
    	
		for(int i=0; i<ds.getFinalNucSimSetDataStructure().getIndexArray().length; i++){
			
			if(Math.min(min, ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[i])!=0.0){
				
				min = Math.min(min, ds.getFinalNucSimSetDataStructure().getFinalAbundArray()[i]);
		
			}
		
		}

    	return min;
    
    }
    
    public Color getLogColor(double value, double max, double min){
    	
        double normValue = 0.0;
        
        double logConstant = 1.0/(log10*Math.log(max) - log10*Math.log(min));  
        
        boolean includeValues = false;
        
        includeValues = ds.getFinalWeightedAbundIncludeValues();
          
        if(includeValues){ 
         
	        if(value!=0.0){
	
				normValue = logConstant*log10*Math.log(value) - logConstant*log10*Math.log(min);
	
	        }
	
	        return getRGB(normValue); 
        
    	}else{
    	
    		if(value<=max && value>=min){
    		
    			normValue = logConstant*log10*Math.log(value) - logConstant*log10*Math.log(min);
		
		        return getRGB(normValue); 
    		
    		}else{
    		
    			return Color.black;
    		
    		}
    	
    	}
        
    }

    public Color getRGB(double x){
    	
    	if(x>=1.0){x = 1.0;}
    	if(x<=0.0){x = 0.0;}
    	
    	int red = 0;
        int green = 0;
        int blue = 0;
    	
        red = (int)(255*Math.exp(-(x-x0R)*(x-x0R)/aR/aR));
        green = (int)(255*Math.exp(-(x-x0G)*(x-x0G)/aG/aG));
        blue = (int)(255*Math.exp(-(x-x0B)*(x-x0B)/aB/aB));
        
        return new Color(red,green,blue);   
    }

    public void setMinFinalAbund(double minFinalAbund){minField.setText(NumberFormats.getFormattedValueLong(minFinalAbund));}
    public double getMinFinalAbund(){return Double.valueOf(minField.getText()).doubleValue();}
    public void setMaxFinalAbund(double maxFinalAbund){maxField.setText(NumberFormats.getFormattedValueLong(maxFinalAbund));}
    public double getMaxFinalAbund(){return Double.valueOf(maxField.getText()).doubleValue();}

    public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  

		   setVisible(false);
		   dispose();
		   
    	}
    	
    } 

    public void windowActivated(WindowEvent we){}
    public void windowClosed(WindowEvent we){}
    public void windowDeactivated(WindowEvent we){}
    public void windowDeiconified(WindowEvent we){}
    public void windowIconified(WindowEvent we){}
    public void windowOpened(WindowEvent we){}
    
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

		String delayString = "Please be patient while data is loaded. DO NOT operate the Final Weighted Abundance Nuclide Chart at this time!";
		
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
	
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	
	}
	
}
    
    