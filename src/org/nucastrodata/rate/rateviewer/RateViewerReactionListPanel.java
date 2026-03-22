package org.nucastrodata.rate.rateviewer;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateViewerDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;


/**
 * The Class RateViewerReactionListPanel.
 */
public class RateViewerReactionListPanel extends JPanel implements ItemListener{
	
	/** The panel array. */
	JPanel[] panelArray;
	
	/** The check box array. */
	JCheckBox[] checkBoxArray;
	
	/** The number rate panels. */
	int numberTotalRates, numberCompRates, numberRatePanels;
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The ds. */
	private RateViewerDataStructure ds;
	
	/**
	 * Instantiates a new rate viewer reaction list panel.
	 *
	 * @param ds the ds
	 */
    public RateViewerReactionListPanel(RateViewerDataStructure ds){
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

		if(ds.getRateAdded()){
		
			numberTotalRates++;
			numberRatePanels++;
		
		}

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
    }
    
    /**
     * Sets the check box array labels.
     *
     * @param array the new check box array labels
     */
    public void setCheckBoxArrayLabels(JCheckBox[] array){
    	int numberRates = 0;

    	for(int i=0; i<ds.getNumberLibraryStructures(); i++){
    		if(ds.getLibraryStructureArray()[i].getRateDataStructures()!=null){
    			for(int j=0; j<ds.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getDecay().equals("")){
	    				array[numberRates].setText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionString()
	    											+ " ("
	    											+ ds.getLibraryStructureArray()[i].getLibName()
	    											+ ")");					
    				}else{
    					array[numberRates].setText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionString()
    												+ " ["
    												+ ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getDecay()
    												+ "]"	 
	    											+ " ("
	    											+ ds.getLibraryStructureArray()[i].getLibName()
	    											+ ")");
    				}
    											
    				numberRates++;
    				
    				if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length>1){
    					for(int k=0; k<ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo().length; k++){
    						if(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getDecay().equals("")){
	    						array[numberRates].setText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionString()
	    													+ " ("
	    													+ ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo()[k]
	    													+ "; "
	    													+ ds.getLibraryStructureArray()[i].getLibName()
	    													+ ")");							
	    					}else{
	    						array[numberRates].setText(ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionString()
	    													+ " ["
		    												+ ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getDecay()
		    												+ "]"
	    													+ " ("
	    													+ ds.getLibraryStructureArray()[i].getRateDataStructures()[j].getResonantInfo()[k]
	    													+ "; "
	    													+ ds.getLibraryStructureArray()[i].getLibName()
	    													+ ")");							
	    					}
    													
    						numberRates++;
   
    					}
    				}
    			}
    		}
    	}
    	
    	if(ds.getRateAdded()){
		
			array[numberRates].setText(ds.getAddRateName());
		
		}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
		int numberChecked = 0;

		for(int i=0; i<numberRatePanels; i++){
			if(checkBoxArray[i].isSelected()){numberChecked++;}
		}

		if(numberChecked<40){
    		Cina.rateViewerFrame.rateViewerPlotFrame.redrawPlot();
    	}else{
    		String string = "You may only select up to 40 curves to plot at one time.";
    		Dialogs.createExceptionDialog(string, Cina.rateViewerFrame.rateViewerPlotFrame);
    		((JCheckBox)ie.getSource()).setSelected(false);
    	}
    }
}    