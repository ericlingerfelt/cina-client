package org.nucastrodata.element.elementviz.edot;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;

public class ElementVizNucSimListPanel extends JPanel implements ItemListener, ActionListener{

	private JPanel[] panelArray;
	public JCheckBox[] checkBoxArray;
	private int numberNucSims;
	private int numberNucSimsPanels;
	private GridBagConstraints gbc;
	private ElementVizDataStructure ds;
	private JButton selectAllButton, unselectAllButton;

	public ElementVizNucSimListPanel(ElementVizDataStructure ds){
    	
    	this.ds = ds;
        
        selectAllButton = new JButton("Select All Zones");
		selectAllButton.addActionListener(this);
		unselectAllButton = new JButton("Unselect All Zones");
		unselectAllButton.addActionListener(this);
    
    }
    
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

    public void initialize(){
    	
    	//Remove all components on the panel
    	removeAll();
	
		//Instantiate gbc
		gbc = new GridBagConstraints();

		//Set panel layout
		setLayout(new GridBagLayout());

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
			
			if(ds.getNucSimDataStructureArray()[i].getEdotArray().length == 1){
			
				checkBoxArray[i].removeItemListener(this);
				checkBoxArray[i].setEnabled(false);
				checkBoxArray[i].setSelected(false);
				checkBoxArray[i].addItemListener(this);
				checkBoxArray[i].setFont(Fonts.textFont);
			
			}else{
			
				checkBoxArray[i].removeItemListener(this);
				checkBoxArray[i].setSelected(true);
				checkBoxArray[i].addItemListener(this);
				checkBoxArray[i].setFont(Fonts.textFont);
			
			}
			
		}
		
		//Call method to set check box names
		setCheckBoxArrayLabels(checkBoxArray);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;	
		add(selectAllButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
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
			gbc.gridy = i+2;
			
			gbc.anchor = GridBagConstraints.NORTHWEST;
			
			add(panelArray[i], gbc);

		}
		
		
		
		validate();

    }
    
    public void setCheckBoxArrayLabels(JCheckBox[] array){
    	
		//Loop over all available nuc sims
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			
			//Set the text of the checkbox
			array[i].setText(ds.getNucSimDataStructureArray()[i].getNucSimName());
			
		}
		
    }
    
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
	    		Cina.elementVizFrame.elementVizEdotPlotFrame.redrawPlot();
	    		
	    	//If the number of nuc sims selected exceeds 50 
	    	}else{
	    	
	    		//Tell user that you can't do that
	    		String string = "You may only select up to 1000 curves to plot at one time.";
	    		
	    		Dialogs.createExceptionDialog(string, Cina.elementVizFrame.elementVizEdotPlotFrame);
	    	
	    		//Unselect last selected nuc sim
	    		((JCheckBox)ie.getSource()).setSelected(false);
	    		
	    	}
    	
    	}

    }
    
}    