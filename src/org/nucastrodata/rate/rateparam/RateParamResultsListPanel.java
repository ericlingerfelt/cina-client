package org.nucastrodata.rate.rateparam;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;


/**
 * The Class RateParamResultsListPanel.
 */
public class RateParamResultsListPanel extends JPanel implements ItemListener{
	
	/** The panel array. */
	JPanel[] panelArray;
	
	/** The check box array. */
	JCheckBox[] checkBoxArray;
	
	/** The number boxes. */
	int numberBoxes;
	
	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The ds. */
	private RateParamDataStructure ds;
	
	/**
	 * Instantiates a new rate param results list panel.
	 *
	 * @param ds the ds
	 */
    public RateParamResultsListPanel(RateParamDataStructure ds){
    	this.ds = ds;
    }
    
    /**
     * Initialize.
     */
    public void initialize(){
    	removeAll();
		gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		JLabel topLabel = new JLabel("<html>Select curves to plot below :</html>");

		numberBoxes = 4;

		if(ds.getExtraPoints()){
			numberBoxes++;
		}

		panelArray = new JPanel[numberBoxes];
		checkBoxArray = new JCheckBox[numberBoxes];
		
		for(int i=0; i<numberBoxes; i++){
			panelArray[i] = new JPanel(new GridBagLayout());
			checkBoxArray[i] = new JCheckBox();
			checkBoxArray[i].removeItemListener(this);
			checkBoxArray[i].setSelected(true);
			checkBoxArray[i].addItemListener(this);
			checkBoxArray[i].setFont(Fonts.textFont);
		}
		
		setCheckBoxArrayLabels(checkBoxArray);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		add(topLabel, gbc);
		
		for(int i=0; i<numberBoxes; i++){
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(1, 0, 0, 0);
		
			panelArray[i].add(checkBoxArray[i], gbc);
			gbc.gridx = 0;
			gbc.gridy = i+1;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			add(panelArray[i], gbc);
			
			if((checkBoxArray[i].getText().equals("Low Temp Extrapolation") && !ds.getLowTemp())
					|| (checkBoxArray[i].getText().equals("High Temp Extrapolation") && !ds.getHighTemp())){
			
				remove(panelArray[i]);
				checkBoxArray[i].removeItemListener(this);
				checkBoxArray[i].setSelected(false);
				checkBoxArray[i].addItemListener(this);
						
			}
		}
		
		
		validate();
    }
    
    /**
     * Sets the check box array labels.
     *
     * @param array the new check box array labels
     */
    public void setCheckBoxArrayLabels(JCheckBox[] array){
    	
    	if(ds.getExtraPoints()){
    	
    		checkBoxArray[0].setText("Fit");
			checkBoxArray[1].setText("Numerical Integration");
			checkBoxArray[2].setText("Added Points");
			checkBoxArray[3].setText("Low Temp Extrapolation");
			checkBoxArray[4].setText("High Temp Extrapolation");

    	}else{
    	
    		checkBoxArray[0].setText("Fit");
			checkBoxArray[1].setText("Numerical Integration");
			checkBoxArray[2].setText("Low Temp Extrapolation");
			checkBoxArray[3].setText("High Temp Extrapolation");
    	
    	}
    	
    }
    
    /**
     * Checks if is box selected.
     *
     * @param index the index
     * @return true, if is box selected
     */
    public boolean isBoxSelected(int index){
    	return checkBoxArray[index].isSelected();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
		
		Cina.rateParamFrame.rateParamResultsPlotFrame.rateParamResultsPlotCanvas.setPlotState();

    }
}    