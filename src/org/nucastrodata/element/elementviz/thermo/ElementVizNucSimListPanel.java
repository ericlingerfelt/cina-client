package org.nucastrodata.element.elementviz.thermo;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


//This class creates the checkbox list for the Thermo Profile Plotter of the Element Visualizer
/**
 * The Class ElementVizNucSimListPanel.
 */
public class ElementVizNucSimListPanel extends JPanel implements ItemListener, ActionListener{

	//Declare panel array
	//Each panel will hold one checkbox
	/** The panel array. */
	JPanel[] panelArray;
	
	//Declare checkbox array for nuc sim check boxes
	/** The check box array. */
	JCheckBox[] checkBoxArray;

	//Declare variables for the number of nuc sims and, 
	//in turn, the number of panels required
	/** The number nuc sims. */
	int numberNucSims;
	
	/** The number nuc sims panels. */
	int numberNucSimsPanels;

	//Declare warning label
	/** The warning label. */
	JLabel warningLabel;

	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;

	/** The density radio button. */
	JRadioButton tempRadioButton, densityRadioButton;
	
	/** The button group. */
	ButtonGroup buttonGroup;

	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The unselect all button. */
	JButton selectAllButton, unselectAllButton;

	//Constructor
    /**
	 * Instantiates a new element viz nuc sim list panel.
	 *
	 * @param ds the ds
	 */
	public ElementVizNucSimListPanel(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	tempRadioButton = new JRadioButton("Temperature", true);
        tempRadioButton.addItemListener(this);
        
        densityRadioButton = new JRadioButton("Density", false);
        densityRadioButton.addItemListener(this);
        
        buttonGroup = new ButtonGroup();
        buttonGroup.add(tempRadioButton);
        buttonGroup.add(densityRadioButton);
        
        selectAllButton = new JButton("Select All Profiles");
		selectAllButton.addActionListener(this);
		unselectAllButton = new JButton("Unselect All Profiles");
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
    			checkBoxArray[i].setSelected(false);
    		}
    	}
    }
    
    //Method to initialize this panel
    /**
     * Initialize.
     */
    public void initialize(){
    	
    	//Remove all components on the panel
    	removeAll();
	
		//Instantiate gbc
		gbc = new GridBagConstraints();

		//Set panel layout
		setLayout(new GridBagLayout());

		//Create warning label
		warningLabel = new JLabel("<html>Choose either temperature vs.<p>time or density vs. time using the<p>radio buttons below.</html>");
	
		//Get the number of nuc sims chosen in the Element Viz interface
		numberNucSims = ds.getNucSimDataStructureArray().length;

		//Set the number of panels to the number of nuc sims
		numberNucSimsPanels = numberNucSims;

		//Create a panel array and a checkbox array
		panelArray = new JPanel[numberNucSimsPanels];
		checkBoxArray = new JCheckBox[numberNucSimsPanels];
		
		//Loop over number of panels
		for(int i=0; i<numberNucSimsPanels; i++){

			//Create each panel
			panelArray[i] = new JPanel(new GridBagLayout());
			
			//Create each check box
			checkBoxArray[i] = new JCheckBox();
			
			//Set check box properties
			checkBoxArray[i].removeItemListener(this);
			checkBoxArray[i].setSelected(true);
			checkBoxArray[i].addItemListener(this);
			checkBoxArray[i].setFont(Fonts.textFont);
		
		}
		
		//Call method to set check box names
		setCheckBoxArrayLabels(checkBoxArray);
		
		//Set gbc and put it all together
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(1, 0, 20, 0);
		add(warningLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		add(tempRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;	
		add(densityRadioButton, gbc);
		
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
		
		for(int i=0; i<numberNucSimsPanels; i++){
		
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
		
		
		
		validate();

    }
    
    //Method to set th enames of each check box
    /**
     * Sets the check box array labels.
     *
     * @param array the new check box array labels
     */
    public void setCheckBoxArrayLabels(JCheckBox[] array){
    	
		//Loop over all available nuc sims
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			
			//Set the text of the checkbox
			array[i].setText(ds.getNucSimDataStructureArray()[i].getNucSimName());
			
		}
		
    }
    
    //Method for Item Listener
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    	
    	if(ie.getSource() instanceof JCheckBox){

			//Create counter
			int numberChecked = 0;
	
			//Loop over number of nuc sims
			for(int i=0; i<numberNucSimsPanels; i++){
			
				//If nuc sim us selected
				if(checkBoxArray[i].isSelected()){
				
					//Increment counter
					numberChecked++;
				
				}
			
			}
	
			//If the number of nuc sims selected does not exceeds 40 
			if(numberChecked<1000){
	
				//rewdraw thermo profile plot
	    		Cina.elementVizFrame.elementVizThermoPlotFrame.redrawPlot();
	    		
	    	//If the number of nuc sims selected exceeds 50 
	    	}else{
	    	
	    		//Tell user that you can't do that
	    		String string = "You may only select up to 1000 curves to plot at one time.";
	    		
	    		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizThermoPlotFrame);
	    	
	    		//Unselect last selected nuc sim
	    		((JCheckBox)ie.getSource()).setSelected(false);
	    		
	    	}
    	
    	}else if(ie.getSource()==tempRadioButton || ie.getSource()==densityRadioButton){
        
        	if(tempRadioButton.isSelected()){
        
        		Cina.elementVizFrame.elementVizThermoPlotFrame.yminLabel.setText("Temp Min");
        		Cina.elementVizFrame.elementVizThermoPlotFrame.ymaxLabel.setText("Max");
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.applyButton.setText("Apply Temp/Time Range");
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.remove(Cina.elementVizFrame.elementVizThermoPlotFrame.densityminComboBox);
        		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.remove(Cina.elementVizFrame.elementVizThermoPlotFrame.densitymaxComboBox);
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridx = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridy = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.anchor = GridBagConstraints.EAST;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.add(Cina.elementVizFrame.elementVizThermoPlotFrame.tempminField, Cina.elementVizFrame.elementVizThermoPlotFrame.gbc);

		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridx = 3;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridy = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.anchor = GridBagConstraints.EAST;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.add(Cina.elementVizFrame.elementVizThermoPlotFrame.tempmaxField, Cina.elementVizFrame.elementVizThermoPlotFrame.gbc);
        		
    		}else{

    			Cina.elementVizFrame.elementVizThermoPlotFrame.yminLabel.setText("log Density Min");
        		Cina.elementVizFrame.elementVizThermoPlotFrame.ymaxLabel.setText("Max");
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.applyButton.setText("Apply Time Range");
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.remove(Cina.elementVizFrame.elementVizThermoPlotFrame.tempminField);
        		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.remove(Cina.elementVizFrame.elementVizThermoPlotFrame.tempmaxField);
        		
        		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridx = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridy = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.anchor = GridBagConstraints.WEST;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.add(Cina.elementVizFrame.elementVizThermoPlotFrame.densityminComboBox, Cina.elementVizFrame.elementVizThermoPlotFrame.gbc);

		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridx = 3;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.gridy = 1;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.gbc.anchor = GridBagConstraints.WEST;
		  		Cina.elementVizFrame.elementVizThermoPlotFrame.controlPanel.add(Cina.elementVizFrame.elementVizThermoPlotFrame.densitymaxComboBox, Cina.elementVizFrame.elementVizThermoPlotFrame.gbc);
    		
    		}

    		Cina.elementVizFrame.elementVizThermoPlotFrame.validate();
    		
        	Cina.elementVizFrame.elementVizThermoPlotFrame.redrawPlot();
        
        }

    }
    
    /**
     * Gets the temp radio button state.
     *
     * @return the temp radio button state
     */
    public boolean getTempRadioButtonState(){return tempRadioButton.isSelected();}

}    