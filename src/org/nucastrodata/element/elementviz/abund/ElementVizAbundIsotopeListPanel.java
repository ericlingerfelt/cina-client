package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotControlsPanel;


/**
 * The Class ElementVizAbundIsotopeListPanel.
 */
public class ElementVizAbundIsotopeListPanel extends JPanel implements ItemListener, ActionListener{

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
	JRadioButton abundRadioButton, ratioRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;
	
	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The control panel. */
	private ElementVizAbundPlotControlsPanel controlPanel;
	
	/** The unselect all button. */
	JButton selectAllButton, unselectAllButton;

    /**
     * Instantiates a new element viz abund isotope list panel.
     *
     * @param ds the ds
     * @param controlPanel the control panel
     */
    public ElementVizAbundIsotopeListPanel(ElementVizDataStructure ds, ElementVizAbundPlotControlsPanel controlPanel){
    
    	this.ds = ds;
    	this.controlPanel = controlPanel;
    	
    	abundRadioButton = new JRadioButton("Abund vs. Time", true);
		abundRadioButton.addItemListener(this);
		
		ratioRadioButton = new JRadioButton("Ratios vs. Mass", false);
		ratioRadioButton.addItemListener(this);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(abundRadioButton);
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
    				if(counter < 1000){
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
		if(initFlag){
			if(abundRadioButton.isSelected()){
				warningLabel = new JLabel("<html>Choose either abundance vs. time<p>or final abundance ratio vs. mass<p>using the radio buttons below.<br><p>Disabled checkboxes below indicate<p>a zero abundance throughout the<p>simulation. Isotopes not appearing<p>in the checkbox list below were not<p>included in the simulation.</html>");
				numberIsotopeRuns = ds.getNumberIsotopeRunsAbund();
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
				
				setCheckBoxArrayLabelsAbund(checkBoxArray);
				
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(1, 0, 20, 0);
				add(warningLabel, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;
				add(abundRadioButton, gbc);
				
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
				warningLabel = new JLabel("<html>Choose either abundance vs. time<p>or final abundance ratio vs. mass<p>using the radio buttons below.<br><p>Disabled checkboxes below indicate<p>that the final abundance of the<p>numerator or denominator is zero.<p>Isotopes not appearing in the<p>checkbox list below were not<p>included in the simulation.</html>");
				numberIsotopes = ds.getIsotopeViktorAbund().size();
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
				gbc.insets = new Insets(1, 0, 20, 0);
				add(warningLabel, gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.insets = new Insets(5, 25, 5, 5);
				gbc.anchor = GridBagConstraints.WEST;
				add(abundRadioButton, gbc);
				
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

			warningLabel = new JLabel("<html>Choose either abundance vs. time<p>or final abundance ratio vs. mass<p>using the radio buttons below.<br><p>Disabled checkboxes below indicate<p>a zero abundance throughout the<p>simulation. Isotopes not appearing<p>in the checkbox list below were not<p>included in the simulation.</html>");
			numberIsotopeRuns = ds.getNumberIsotopeRunsAbund();
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
			
			setCheckBoxArrayLabelsAbund(checkBoxArray);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(1, 0, 20, 0);
			add(warningLabel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(5, 25, 5, 5);
			gbc.anchor = GridBagConstraints.WEST;
			add(abundRadioButton, gbc);
			
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
		validate();
    }
    
    /**
     * Sets the check box array labels abund.
     *
     * @param array the new check box array labels abund
     */
    public void setCheckBoxArrayLabelsAbund(JCheckBox[] array){

		int numRuns = 0;

		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			for(int j=0; j<ds.getNucSimDataStructureArray()[i].getLabelViktor().size(); j++){
			
				array[numRuns].setText((String)ds.getNucSimDataStructureArray()[i].getLabelViktor().elementAt(j)
									+ " (" 
									+ ds.getNucSimDataStructureArray()[i].getNucSimName()
									+ ")");
				
				array[numRuns].removeItemListener(this);				
				array[numRuns].setEnabled(ds.getAbundNonZeroArray()[numRuns]);
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

    	for(int i=0; i<ds.getIsotopeViktorAbund().size(); i++){
	
			array[i].setText(getIsotopeLabel((Point)ds.getIsotopeViktorAbund().elementAt(i)));
			array[i].setEnabled(ds.getRatioNonZeroArrayAbund()[i]);

    	}
    	
    }
    
    /**
     * Gets the include isotope array.
     *
     * @return the include isotope array
     */
    public boolean[] getIncludeIsotopeArray(){
    
    	boolean[] tempArray = new boolean[ds.getIsotopeViktorAbund().size()];
    	
    	for(int i=0; i<tempArray.length; i++){
    	
    		int currentZ = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX();
    		int currentA = (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getX()
    						+ (int)((Point)ds.getIsotopeViktorAbund().elementAt(i)).getY();
    		
    		boolean num = false;
    		boolean denom = false;
    					
    		for(int j=0; j<ds.getNucSimDataStructureArray().length; j++){
    		
    			if(ds.getNucSimDataStructureArray()[j].getNucSimName().equals((String)controlPanel.numComboBox.getSelectedItem())){
    			
    				numFound:
    				for(int k=0; k<ds.getNucSimDataStructureArray()[j].getZAMapArray().length; k++){
    				
    					int Z = (int)ds.getNucSimDataStructureArray()[j].getZAMapArray()[k].getX();
    					int A = (int)ds.getNucSimDataStructureArray()[j].getZAMapArray()[k].getY();
    					
    					if(Z==currentZ && A==currentA){
    					
    						num = true;
    						break numFound;
    					
    					}
    				
    				}
    				
    				denomFound:
    				for(int k=0; k<ds.getNucSimDataStructureArray()[j].getZAMapArray().length; k++){
    				
    					int Z = (int)ds.getNucSimDataStructureArray()[j].getZAMapArray()[k].getX();
    					int A = (int)ds.getNucSimDataStructureArray()[j].getZAMapArray()[k].getY();
    					
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
	
			if(abundRadioButton.isSelected()){
	
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
	
	    		controlPanel.redrawPlot();
	    	
	    	}else{
	    	
	    		String string = "You may only select up to 1000 curves to plot at one time.";
	    		
	    		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
	    	
	    		((JCheckBox)ie.getSource()).setSelected(false);
	    		
	    	}
    	
    	}else if(ie.getSource()==abundRadioButton){
        
        	controlPanel.controlPanel.remove(controlPanel.ratioPanel);
        	controlPanel.controlPanel.validate();
        	controlPanel.applyButton.setText("Apply Time Range");
        	
        	controlPanel.gbc.gridx = 0;
	  		controlPanel.gbc.gridy = 1;
	  		controlPanel.gbc.anchor = GridBagConstraints.CENTER;
	  		controlPanel.controlPanel.add(controlPanel.abundPanel, controlPanel.gbc);
	  		
	  		initialize();
	  		
	  		controlPanel.changeButton.setEnabled(false);
	  		controlPanel.redrawPlot();
	  		controlPanel.validate();
	  		
	  		Cina.elementVizFrame.elementVizAbundPlotFrame.validate();
	  		Cina.elementVizFrame.elementVizAbundPlotFrame.repaint();
	  		
	  	}else if(ie.getSource()==ratioRadioButton){
	  		
	  		/*if(!controlPanel.goodStopTime() ){
	  		
	  			String string = "The simulations chosen for abundance ratios do not have the same stoptime.";
	  			
	  			Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizAbundPlotFrame);
	  		
	  		}*/
	  		
	  		controlPanel.changeButton.setEnabled(true);
	  		controlPanel.controlPanel.remove(controlPanel.abundPanel);

	  		controlPanel.controlPanel.validate();
	  		
        	controlPanel.applyButton.setText("Apply Mass/Ratio Range");
        	
        	controlPanel.gbc.gridx = 0;
	  		controlPanel.gbc.gridy = 1;
	  		controlPanel.gbc.anchor = GridBagConstraints.CENTER;
	  		controlPanel.controlPanel.add(controlPanel.ratioPanel, controlPanel.gbc);
	  		
	  		initialize();
	
	  		controlPanel.redrawPlot();
	  		controlPanel.validate();
	  		
	  		Cina.elementVizFrame.elementVizAbundPlotFrame.validate();
	  		Cina.elementVizFrame.elementVizAbundPlotFrame.repaint();
        
      	}

    }

}    