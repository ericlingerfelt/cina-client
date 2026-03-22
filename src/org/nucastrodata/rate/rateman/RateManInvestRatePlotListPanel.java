package org.nucastrodata.rate.rateman;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RateManInvestRatePlotListPanel.
 */
public class RateManInvestRatePlotListPanel extends JPanel implements ItemListener{

    /** The color1. */
    Color color1 = new Color(210,210,210);
    
    /** The color2. */
    Color color2 = new Color(235,235,235);

	/** The panel array. */
	JPanel[] panelArray;
	
	/** The check box array. */
	JCheckBox[] checkBoxArray;

	/** The number total rates. */
	int numberTotalRates;
	
	/** The number comp rates. */
	int numberCompRates;
	
	/** The number rate panels. */
	int numberRatePanels;

	/** The gbc. */
	GridBagConstraints gbc;

	/** The ds. */
	private RateManDataStructure ds; 

    /**
     * Instantiates a new rate man invest rate plot list panel.
     *
     * @param ds the ds
     */
    public RateManInvestRatePlotListPanel(RateManDataStructure ds){
    	this.ds = ds;
    }
    
    /**
     * Initialize.
     */
    public void initialize(){
    	
    	removeAll();
	
		gbc = new GridBagConstraints();

		setLayout(new GridBagLayout());

		numberTotalRates = ds.getNumberTotalRates();

		numberCompRates = ds.getNumberCompRates();

		numberRatePanels = numberTotalRates + numberCompRates;

		panelArray = new JPanel[numberRatePanels];
		checkBoxArray = new JCheckBox[numberRatePanels];
		
		for(int i=0; i<numberRatePanels; i++){

			panelArray[i] = new JPanel(new GridBagLayout());
			checkBoxArray[i] = new JCheckBox();
			checkBoxArray[i].removeItemListener(this);
			checkBoxArray[i].setSelected(false);
			checkBoxArray[i].addItemListener(this);
			checkBoxArray[i].setFont(Fonts.textFont);
		
		}
		
		setCheckBoxArrayLabels(checkBoxArray);
		
		for(int i=0; i<numberRatePanels; i++){
		
			gbc.gridx = 0;
			gbc.gridy = 0;
		
			gbc.anchor = GridBagConstraints.CENTER;
		
			if(checkBoxArray[i].getText().indexOf("(nr")!=-1
					|| checkBoxArray[i].getText().indexOf("(r")!=-1){
			
				gbc.insets = new Insets(1, 15, 0, 0);
			
			}else{
			
				gbc.insets = new Insets(1, 0, 0, 0);
			
			}
		
			panelArray[i].add(checkBoxArray[i], gbc);
		
			gbc.gridx = 0;
			gbc.gridy = i;
			
			gbc.anchor = GridBagConstraints.NORTHWEST;
			
			add(panelArray[i], gbc);

		}
		
		

		validate();
		
		setVisible(false);
		setVisible(true);

    }
    
    /**
     * Sets the check box array labels.
     *
     * @param array the new check box array labels
     */
    public void setCheckBoxArrayLabels(JCheckBox[] array){
    	
    	int numberRates = 0;
    	
    	//Loop over libraries
    	for(int i=0; i<ds.getInvestRateVectorArray().length; i++){

			boolean rateUsed = false;

			//Loop over rates
			for(int j=0; j<ds.getInvestRateVectorArray()[i].size(); j++){	
		
				if(((JCheckBox)Cina.rateManFrame.rateManInvestRate3Panel.rateManInvestRateListPanel.checkBoxVectorArray[i].elementAt(j)).isSelected()){
	
    				rateUsed = true;
    				
    			}
    		
    		}
    		
    		if(rateUsed){
    			
    			array[numberRates].setText("Distinct Rate #" 
    										+ String.valueOf(i+1));

				numberRates++;
				
				if(((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length>1){
				
					for(int k=0; k<((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo().length; k++){

						array[numberRates].setText("Distinct Rate #" 
													+ String.valueOf(i+1)
													+ " ("
													+ ((RateDataStructure)ds.getInvestRateVectorArray()[i].elementAt(0)).getResonantInfo()[k]
													+ ")");

													
						numberRates++;
					
					}
				}
    		}
    	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){

		int numberChecked = 0;

		for(int i=0; i<numberRatePanels; i++){
		
			if(checkBoxArray[i].isSelected()){
			
				numberChecked++;
			
			}
		
		}

		if(numberChecked<40){

    		Cina.rateManFrame.rateManInvestRatePlotFrame.redrawPlot();
    	
    	}else{
    	
    		String string = "You may only select up to 40 curves to plot at one time.";
    		
    		Dialogs.createExceptionDialog(string, Cina.rateManFrame.rateManInvestRatePlotFrame);
    	
    		((JCheckBox)ie.getSource()).setSelected(false);
    		
    	}

    }

}    