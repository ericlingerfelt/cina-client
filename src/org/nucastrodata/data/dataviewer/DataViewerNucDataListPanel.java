package org.nucastrodata.data.dataviewer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


//This class creates the checkbox list for the Thermo Profile Plotter of the Element Visualizer
/**
 * The Class DataViewerNucDataListPanel.
 */
public class DataViewerNucDataListPanel extends JPanel implements ItemListener{

	//Declare panel array
	//Each panel will hold one checkbox
	/** The panel array. */
	JPanel[] panelArray;
	
	//Declare checkbox array for nuc sim check boxes
	/** The check box array. */
	JCheckBox[] checkBoxArray;

	//Declare variables for the number of nuc sims and, 
	//in turn, the number of panels required
	/** The number nuc data. */
	int numberNucData;
	
	/** The number nuc data panels. */
	int numberNucDataPanels;

	//Declare warning label
	/** The warning label. */
	JLabel warningLabel;

	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;

	/** The init flag. */
	boolean initFlag = false;

	/** The ds. */
	private DataViewerDataStructure ds;

	//Constructor
    /**
	 * Instantiates a new data viewer nuc data list panel.
	 *
	 * @param ds the ds
	 */
	public DataViewerNucDataListPanel(DataViewerDataStructure ds){
    	this.ds = ds;
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
		warningLabel = new JLabel("<html>Choose either cross section vs.<p>energy or S factor vs. energy using the<p>radio buttons in the lower section<p>of this interface.</html>");
	
		if(initFlag){
	
			if(Cina.dataViewerFrame.dataViewerPlotFrame.SFRadioButton.isSelected()){
	
				//Get the number of nuc sims chosen in the Element Viz interface
				numberNucData = ds.getNumberTotalSFNucData();
		
				//Set the number of panels to the number of nuc sims
				numberNucDataPanels = numberNucData;
		
				//Create a panel array and a checkbox array
				panelArray = new JPanel[numberNucDataPanels];
				checkBoxArray = new JCheckBox[numberNucDataPanels];
				
				//Loop over number of panels
				for(int i=0; i<numberNucDataPanels; i++){
		
					//Create each panel
					panelArray[i] = new JPanel(new GridBagLayout());
					
					//Create each check box
					checkBoxArray[i] = new JCheckBox();
					
					//Set check box properties
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				//Call method to set check box names
				setCheckBoxArrayLabels(checkBoxArray, "S(E)");
				
				//Set gbc and put it all together
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(1, 0, 20, 0);
				gbc.anchor = GridBagConstraints.WEST;
				add(warningLabel, gbc);
				
				for(int i=0; i<numberNucDataPanels; i++){
				
					gbc.gridx = 0;
					gbc.gridy = 0;
				
					gbc.anchor = GridBagConstraints.CENTER;
				
					gbc.insets = new Insets(1, 0, 0, 0);
		
					panelArray[i].add(checkBoxArray[i], gbc);
				
					gbc.gridx = 0;
					gbc.gridy = i+1;
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					
					add(panelArray[i], gbc);
		
				}
			
			}else if(Cina.dataViewerFrame.dataViewerPlotFrame.CSRadioButton.isSelected()){
			
				//Get the number of nuc sims chosen in the Element Viz interface
				numberNucData = ds.getNumberTotalCSNucData();
		
				//Set the number of panels to the number of nuc sims
				numberNucDataPanels = numberNucData;
		
				//Create a panel array and a checkbox array
				panelArray = new JPanel[numberNucDataPanels];
				checkBoxArray = new JCheckBox[numberNucDataPanels];
				
				//Loop over number of panels
				for(int i=0; i<numberNucDataPanels; i++){
		
					//Create each panel
					panelArray[i] = new JPanel(new GridBagLayout());
					
					//Create each check box
					checkBoxArray[i] = new JCheckBox();
					
					//Set check box properties
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				//Call method to set check box names
				setCheckBoxArrayLabels(checkBoxArray, "CS(E)");
				
				//Set gbc and put it all together
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.insets = new Insets(1, 0, 20, 0);
				gbc.anchor = GridBagConstraints.WEST;
				add(warningLabel, gbc);
				
				for(int i=0; i<numberNucDataPanels; i++){
				
					gbc.gridx = 0;
					gbc.gridy = 0;
				
					gbc.anchor = GridBagConstraints.CENTER;
				
					gbc.insets = new Insets(1, 0, 0, 0);
		
					panelArray[i].add(checkBoxArray[i], gbc);
				
					gbc.gridx = 0;
					gbc.gridy = i+1;
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					
					add(panelArray[i], gbc);
		
				}
			
			}
		
		}else{
		
			initFlag = true;
			
			if(ds.getNumberTotalCSNucData()>0){
			
				//Get the number of nuc sims chosen in the Element Viz interface
				numberNucData = ds.getNumberTotalCSNucData();
		
				//Set the number of panels to the number of nuc sims
				numberNucDataPanels = numberNucData;
		
				//Create a panel array and a checkbox array
				panelArray = new JPanel[numberNucDataPanels];
				checkBoxArray = new JCheckBox[numberNucDataPanels];
				
				//Loop over number of panels
				for(int i=0; i<numberNucDataPanels; i++){
		
					//Create each panel
					panelArray[i] = new JPanel(new GridBagLayout());
					
					//Create each check box
					checkBoxArray[i] = new JCheckBox();
					
					//Set check box properties
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				//Call method to set check box names
				setCheckBoxArrayLabels(checkBoxArray, "CS(E)");
			
			}else{
			
				//Get the number of nuc sims chosen in the Element Viz interface
				numberNucData = ds.getNumberTotalSFNucData();
		
				//Set the number of panels to the number of nuc sims
				numberNucDataPanels = numberNucData;
		
				//Create a panel array and a checkbox array
				panelArray = new JPanel[numberNucDataPanels];
				checkBoxArray = new JCheckBox[numberNucDataPanels];
				
				//Loop over number of panels
				for(int i=0; i<numberNucDataPanels; i++){
		
					//Create each panel
					panelArray[i] = new JPanel(new GridBagLayout());
					
					//Create each check box
					checkBoxArray[i] = new JCheckBox();
					
					//Set check box properties
					checkBoxArray[i].removeItemListener(this);
					checkBoxArray[i].setSelected(false);
					checkBoxArray[i].addItemListener(this);
					checkBoxArray[i].setFont(Fonts.textFont);
				
				}
				
				//Call method to set check box names
				setCheckBoxArrayLabels(checkBoxArray, "S(E)");
			
			
			}
			
			//Set gbc and put it all together
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(1, 0, 20, 0);
			gbc.anchor = GridBagConstraints.WEST;
			add(warningLabel, gbc);
			
			for(int i=0; i<numberNucDataPanels; i++){
			
				gbc.gridx = 0;
				gbc.gridy = 0;
			
				gbc.anchor = GridBagConstraints.CENTER;
			
				gbc.insets = new Insets(1, 0, 0, 0);
	
				panelArray[i].add(checkBoxArray[i], gbc);
			
				gbc.gridx = 0;
				gbc.gridy = i+1;
				
				gbc.anchor = GridBagConstraints.NORTHWEST;
				
				add(panelArray[i], gbc);
	
			}
		
		}
		
		
		
		validate();

    }
    
    //Method to set th enames of each check box
    /**
     * Sets the check box array labels.
     *
     * @param array the array
     * @param type the type
     */
    public void setCheckBoxArrayLabels(JCheckBox[] array, String type){

		int counter = 0;

		//Loop over libraries
		for(int i=0; i<ds.getNumberNucDataSetStructures(); i++){
    		
    		//If library has any rates
    		if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
    		
    			//Loop over rates
    			for(int j=0; j<ds.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
    				
    				if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataType().equals(type)){
    				
    					if(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getDecay().equals("")){
    				
							//Set the text of the checkbox
							array[counter].setText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataName()
													+ " ("
													+ ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getReactionString()
													+ ", "
													+ ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataSet()
													+ ")");
													
		
							counter++;
						
						}else{
						
							//Set the text of the checkbox
							array[counter].setText(ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataName()
													+ " ("
													+ ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getReactionString()
													+ " ["
													+ ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getDecay()
													+ "]"
													+ ", "
													+ ds.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataSet()
													+ ")");
													
		
							counter++;
						
						}
					}
				}
	    	}
	    }
	}
    
    //Method for Item Listener
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){

		//Create counter
		int numberChecked = 0;

		//Loop over number of nuc sims
		for(int i=0; i<numberNucDataPanels; i++){
		
			//If nuc sim us selected
			if(checkBoxArray[i].isSelected()){
			
				//Increment counter
				numberChecked++;
			
			}
		
		}

		//If the number of nuc sims selected does not exceeds 40 
		if(numberChecked<40){

			//rewdraw thermo profile plot
    		Cina.dataViewerFrame.dataViewerPlotFrame.redrawPlot();
    		
    	//If the number of nuc sims selected exceeds 40 
    	}else{
    	
    		//Tell user that you can't do that
    		String string = "You may only select up to 40 curves to plot at one time.";
    		
    		Dialogs.createExceptionDialog(string, Cina.dataViewerFrame.dataViewerPlotFrame);
    	
    		//Unselect last selected nuc sim
    		((JCheckBox)ie.getSource()).setSelected(false);
    		
    	}

    }

}    