package org.nucastrodata.element.elementviz.weight;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class ElementVizWeightIsotopeListPanel.
 */
public class ElementVizWeightIsotopeListPanel extends JPanel implements ItemListener, ActionListener{

	/** The warning label. */
	JLabel warningLabel;

	/** The panel array. */
	JPanel[] panelArray;
	
	/** The check box array. */
	JCheckBox[] checkBoxArray;

	/** The number isotope runs. */
	int numberIsotopeRuns;
	
	/** The number isotope run panels. */
	int numberIsotopeRunPanels;

	/** The number isotopes. */
	int numberIsotopes;
	
	/** The number isotope panels. */
	int numberIsotopePanels;

	/** The gbc. */
	GridBagConstraints gbc;

	/** The init flag. */
	boolean initFlag = false;

	/** The ratio radio button. */
	JRadioButton weightRadioButton, ratioRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;

	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The unselect all button. */
	JButton selectAllButton, unselectAllButton;

    /**
     * Instantiates a new element viz weight isotope list panel.
     *
     * @param ds the ds
     */
    public ElementVizWeightIsotopeListPanel(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	weightRadioButton = new JRadioButton("Weight vs. Zone", true);
		weightRadioButton.addItemListener(this);
		
		ratioRadioButton = new JRadioButton("Ratios vs. Mass", false);
		ratioRadioButton.addItemListener(this);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(weightRadioButton);
		buttonGroup.add(ratioRadioButton);
		
		selectAllButton = new JButton("Select All Isotopes");
		selectAllButton.addActionListener(this);
		unselectAllButton = new JButton("Unselect All Isotopes");
		unselectAllButton.addActionListener(this);
    
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==selectAllButton){
    		int counter = 0;
    		for(int i=0; i<checkBoxArray.length; i++){
    			if(checkBoxArray[i].isEnabled()){
    				if(counter < 40){
    					checkBoxArray[i].setSelected(true);
    					counter++;
    				}
    			}
    		}
    	}else if(ae.getSource()==unselectAllButton){
    		for(int i=0; i<checkBoxArray.length; i++){
    			if(checkBoxArray[i].isEnabled()){
    				checkBoxArray[i].setSelected(false);
    			}
    		}
    	}
    }
    
    /**
     * Initialize.
     */
    public void initialize(){
	
		removeAll();
	
		gbc = new GridBagConstraints();

		setLayout(new GridBagLayout());

		warningLabel = new JLabel("<html>Choose either final weighted abundance<p>vs. zone or summed final weighted abundance<p>ratio vs. mass using the radio buttons below.<br><p>Disabled checkboxes below indicate<p>a zero weighted abundance throughout<p>the simulation. Isotopes not appearing<p>in the checkbox list below were not<p>included in the simulation.</html>");

		if(initFlag){

			if(weightRadioButton.isSelected()){
			
				numberIsotopeRuns = ds.getNumberIsotopeRunsWeight();
	
				numberIsotopeRunPanels = numberIsotopeRuns;
		
				panelArray = new JPanel[numberIsotopeRunPanels];
				checkBoxArray = new JCheckBox[numberIsotopeRunPanels];
				
				for(int i=0; i<numberIsotopeRunPanels; i++){
		
					panelArray[i] = new JPanel(new GridBagLayout());
					checkBoxArray[i] = new JCheckBox();
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				setCheckBoxArrayLabelsWeight(checkBoxArray);
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(5, 5, 5, 5);
				add(warningLabel, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;
				add(weightRadioButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;	
				add(ratioRadioButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 3;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.WEST;	
				add(selectAllButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 4;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;	
				add(unselectAllButton, gbc);
				gbc.fill = GridBagConstraints.NONE;
				
				for(int i=0; i<numberIsotopeRunPanels; i++){
				
					gbc.gridx = 0;
					gbc.gridy = 0;
				
					gbc.anchor = GridBagConstraints.CENTER;
				
					gbc.insets = new Insets(1, 0, 0, 0);
		
					panelArray[i].add(checkBoxArray[i], gbc);
				
					gbc.gridx = 0;
					gbc.gridy = i+5;
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					
					add(panelArray[i], gbc);
		
				}
			
			}else if(ratioRadioButton.isSelected()){

				numberIsotopes = ds.getIsotopeViktorWeight().size();
	
				numberIsotopePanels = numberIsotopes;
		
				panelArray = new JPanel[numberIsotopePanels];
				checkBoxArray = new JCheckBox[numberIsotopePanels];
				
				for(int i=0; i<numberIsotopePanels; i++){
		
					panelArray[i] = new JPanel(new GridBagLayout());
					checkBoxArray[i] = new JCheckBox();
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				setCheckBoxArrayLabelsRatio(checkBoxArray);
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(5, 5, 5, 5);
				add(warningLabel, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;
				add(weightRadioButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;	
				add(ratioRadioButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 3;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.WEST;	
				add(selectAllButton, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 4;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;	
				add(unselectAllButton, gbc);
				gbc.fill = GridBagConstraints.NONE;
				
				boolean[] includeIsotopeArray = getIncludeIsotopeArray();
				
				for(int i=0; i<numberIsotopePanels; i++){
				
					gbc.gridx = 0;
					gbc.gridy = 0;
				
					gbc.anchor = GridBagConstraints.CENTER;
				
					gbc.insets = new Insets(1, 0, 0, 0);
		
					panelArray[i].add(checkBoxArray[i], gbc);
				
					gbc.gridx = 0;
					gbc.gridy = i+5;
					
					gbc.anchor = GridBagConstraints.NORTHWEST;

					if(includeIsotopeArray[i]){
					
						add(panelArray[i], gbc);
					
					}
		
				}
				
			}
			
		}else{

			
			numberIsotopeRuns = ds.getNumberIsotopeRunsWeight();
	
			numberIsotopeRunPanels = numberIsotopeRuns;
	
			panelArray = new JPanel[numberIsotopeRunPanels];
			checkBoxArray = new JCheckBox[numberIsotopeRunPanels];
			
			for(int i=0; i<numberIsotopeRunPanels; i++){
	
				panelArray[i] = new JPanel(new GridBagLayout());
				checkBoxArray[i] = new JCheckBox();
				checkBoxArray[i].removeItemListener(this);
				checkBoxArray[i].setSelected(false);
				checkBoxArray[i].addItemListener(this);
				checkBoxArray[i].setFont(Fonts.textFont);
			
			}
			
			setCheckBoxArrayLabelsWeight(checkBoxArray);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(5, 5, 5, 5);
			add(warningLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(5, 25, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			add(weightRadioButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(5, 25, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;	
			add(ratioRadioButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(5, 25, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;	
			add(selectAllButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(5, 25, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;	
			add(unselectAllButton, gbc);
			gbc.fill = GridBagConstraints.NONE;
			
			for(int i=0; i<numberIsotopeRunPanels; i++){
			
				gbc.gridx = 0;
				gbc.gridy = 0;
			
				gbc.anchor = GridBagConstraints.CENTER;
			
				gbc.insets = new Insets(1, 0, 0, 0);
	
				panelArray[i].add(checkBoxArray[i], gbc);
			
				gbc.gridx = 0;
				gbc.gridy = i+5;
				
				gbc.anchor = GridBagConstraints.NORTHWEST;
				
				add(panelArray[i], gbc);
	
			}
		
		}
		
		initFlag = true;
	
		repaint();
		validate();

    }
    
    /**
     * Sets the check box array labels weight.
     *
     * @param array the new check box array labels weight
     */
    public void setCheckBoxArrayLabelsWeight(JCheckBox[] array){

		int numRuns = 0;

		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){

			for(int j=0; j<ds.getZoneNucSimSetDataStructureArray()[i].getLabelViktor().size(); j++){
			
				String string = ds.getZoneNucSimSetDataStructureArray()[i].getPath();
			
				array[numRuns].setText((String)ds.getZoneNucSimSetDataStructureArray()[i].getLabelViktor().elementAt(j)
									+ " (" 
									+ string.substring(string.lastIndexOf("/")+1)
									+ ")");
				
				array[numRuns].removeItemListener(this);				
				array[numRuns].setEnabled(ds.getWeightNonZeroArray()[numRuns]);
				array[numRuns].addItemListener(this);
									
				numRuns++;
	
			}
		
		}
				
    }
    
    /**
     * Sets the check box array labels ratio.
     *
     * @param array the new check box array labels ratio
     */
    public void setCheckBoxArrayLabelsRatio(JCheckBox[] array){

    	for(int i=0; i<ds.getIsotopeViktorWeight().size(); i++){
	
			array[i].setText(getIsotopeLabel((Point)ds.getIsotopeViktorWeight().elementAt(i)));
			array[i].setEnabled(ds.getRatioNonZeroArrayWeight()[i]);

    	}
    	
    }
    
    /**
     * Gets the include isotope array.
     *
     * @return the include isotope array
     */
    public boolean[] getIncludeIsotopeArray(){
    
    	boolean[] tempArray = new boolean[ds.getIsotopeViktorWeight().size()];
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		int currentZ = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX();
    		int currentA = (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getX()
    						+ (int)((Point)ds.getIsotopeViktorWeight().elementAt(i)).getY();
    		
    		boolean num = false;
    		boolean denom = false;
    					
    		for(int j=0; j<ds.getZoneNucSimSetDataStructureArray().length; j++){
    		
    			String string = ds.getZoneNucSimSetDataStructureArray()[j].getPath();
    			if(string.substring(string.lastIndexOf("/")+1).equals((String)Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.numComboBox.getSelectedItem())){
    			
    				numFound:
    				for(int k=0; k<ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray().length; k++){
    				
    					int Z = (int)ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray()[k].getX();
    					int A = (int)ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray()[k].getY();
    					
    					if(Z==currentZ && A==currentA){
    					
    						num = true;
    						break numFound;
    					
    					}
    				
    				}
    				
    				denomFound:
    				for(int k=0; k<ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray().length; k++){
    				
    					int Z = (int)ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray()[k].getX();
    					int A = (int)ds.getZoneNucSimSetDataStructureArray()[j].getZAMapArray()[k].getY();
    					
    					if(Z==currentZ && A==currentA){
    					
    						denom = true;
    						break denomFound;
    					
    					}
    				
    				}
    			
    			}
    		
    		}
    		
    		if(num & denom){
    		
    			tempArray[i] = true;
    		
    		}else{
    		
    			tempArray[i] = false;
    		
    		}
    	
    	}
    	
    	return tempArray;
    
    }
    
    /**
     * Gets the isotope label.
     *
     * @param point the point
     * @return the isotope label
     */
    public String getIsotopeLabel(Point point){
    
    	String string = "";
    
    	int Z = (int)point.getX();
    	
    	int A = (int)point.getX() + (int)point.getY();
    	
    	if(Z>1){
    	
    		string = String.valueOf(A) + Cina.cinaMainDataStructure.getElementSymbol(Z);
    	
    	}else if(Z==0){
    	
    		string = "n";
    	
    	}else if(Z==1 && A==1){
    	
    		string = "p";
    	
    	}else if(Z==1 && A==2){
    	
    		string = "d";
    	
    	}else if(Z==1 && A==3){
    	
    		string = "t";
    	
    	}
    	
    	return string;
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){

		if(ie.getSource() instanceof JCheckBox){

			int numberChecked = 0;
	
			if(weightRadioButton.isSelected()){
	
				for(int i=0; i<numberIsotopeRunPanels; i++){
				
					if(checkBoxArray[i].isSelected()){
					
						numberChecked++;
					
					}
				
				}
				
			}else if(ratioRadioButton.isSelected()){
			
				for(int i=0; i<numberIsotopePanels; i++){
				
					if(checkBoxArray[i].isSelected()){
					
						numberChecked++;
					
					}
				
				}
				
			}
			
			if(numberChecked<1000){
	
	    		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.redrawPlot();
	    	
	    	}else{
	    	
	    		String string = "You may only select up to 1000 curves to plot at one time.";
	    		
	    		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizWeightPlotFrame);
	    	
	    		((JCheckBox)ie.getSource()).setSelected(false);
	    		
	    	}
    	
    	}else if(ie.getSource()==weightRadioButton){
        
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.remove(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.ratioPanel);
        	
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.validate();
        	
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.applyButton.setText("Apply Zone Range");
        	
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.gridx = 0;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.gridy = 1;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.anchor = GridBagConstraints.CENTER;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.add(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.weightPanel, Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc);
	  		
	  		initialize();
	  		
	  		//Cina.setFrameColors(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel);
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.redrawPlot();
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.validate();
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.validate();
	  		
	  	}else if(ie.getSource()==ratioRadioButton){
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.remove(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.weightPanel);

	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.validate();
	  		
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.applyButton.setText("Apply Mass/Ratio Range");
        	
        	Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.gridx = 0;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.gridy = 1;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc.anchor = GridBagConstraints.CENTER;
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.controlPanel.add(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.ratioPanel, Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.gbc);
	  		
	  		initialize();
	  		
	  		//Cina.setFrameColors(Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel);
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.redrawPlot();
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.elementVizWeightPlotControlsPanel.validate();
	  		
	  		Cina.elementVizFrame.elementVizWeightPlotFrame.validate();

      	}

    }

}    