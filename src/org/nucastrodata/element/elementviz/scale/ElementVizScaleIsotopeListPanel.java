package org.nucastrodata.element.elementviz.scale;

import info.clearthought.layout.*;
import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.*;
import java.awt.event.*;
import java.util.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotControlsPanel;
import org.nucastrodata.wizard.gui.WordWrapLabel;


/**
 * The Class ElementVizScaleIsotopeListPanel.
 */
public class ElementVizScaleIsotopeListPanel extends JPanel implements ActionListener{

	/** The box list. */
	public ArrayList<JCheckBox> boxList;
	
	/** The index list. */
	public ArrayList<Integer> indexList;
	
	/** The norm radio button. */
	private JRadioButton abundRadioButton, normRadioButton;
	
	/** The scale combo box. */
	public JComboBox setComboBox, scaleComboBox;
	
	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The parent. */
	private ElementVizScalePlotControlsPanel parent;

    /**
     * Instantiates a new element viz scale isotope list panel.
     *
     * @param ds the ds
     * @param parent the parent
     */
    public ElementVizScaleIsotopeListPanel(ElementVizDataStructure ds, ElementVizScalePlotControlsPanel parent){
    	
    	this.ds = ds;
    	this.parent = parent;
    	
    	abundRadioButton = new JRadioButton("Final Abund vs. Rate Scale Factor", true);
    	abundRadioButton.addActionListener(this);
    	abundRadioButton.setFont(Fonts.textFont);
    	
    	normRadioButton = new JRadioButton("Norm Final Abund vs. Rate Scale Factor", false);
    	normRadioButton.addActionListener(this);
    	normRadioButton.setFont(Fonts.textFont);
		
    	ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(abundRadioButton);
		buttonGroup.add(normRadioButton);
    
		setComboBox = new JComboBox();
		setComboBox.setFont(Fonts.textFont);
		
		scaleComboBox = new JComboBox();
		scaleComboBox.setFont(Fonts.textFont);
		
		setPreferredSize(new Dimension(200, 1000));

    }
    
    /**
     * None checked.
     *
     * @return true, if successful
     */
    public boolean noneChecked(){
    	for(JCheckBox box: boxList){
    		if(box.isSelected()){
    			return false;
    		}
    	}
    	return true;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==abundRadioButton || ae.getSource()==normRadioButton){
    		if(abundRadioButton.isSelected()){
    			scaleComboBox.setEnabled(false);
    			parent.setCurrentState(ElementVizScalePlotControlsPanel.Mode.ABUND);
    		}else if(normRadioButton.isSelected()){
    			scaleComboBox.setEnabled(true);
    			parent.setScaleFactor(scaleComboBox.getSelectedIndex());
    			parent.setCurrentState(ElementVizScalePlotControlsPanel.Mode.NORM);
    		}
    	}else if(ae.getSource() instanceof JCheckBox){

			int numberChecked = 0;
			
			if(numberChecked<1000){
	    		parent.setYLimits();
	    		parent.redrawPlot();
	    	}else{
	    		String string = "You may only select up to 1000 curves to plot at one time.";
	    		Dialogs.createExceptionDialog(string
	    			, Cina.elementVizFrame.elementVizScalePlotFrame);
	    		((JCheckBox)ae.getSource()).setSelected(false);
	    	}
    	
    	}else if(ae.getSource()==setComboBox){
    		
    		parent.setSetName(setComboBox.getSelectedItem().toString());
    		
    		scaleComboBox.removeActionListener(this);
    		scaleComboBox.removeAllItems();
    		for(double scaleFactor: parent.factors){
    			scaleComboBox.addItem(scaleFactor);
    		}
    		scaleComboBox.setSelectedIndex(0);
    		if(abundRadioButton.isSelected()){
    			scaleComboBox.setEnabled(false);
    		}else if(normRadioButton.isSelected()){
    			scaleComboBox.setEnabled(true);
    			parent.setScaleFactor(scaleComboBox.getSelectedIndex());
    		}
    		scaleComboBox.addActionListener(this);
    		parent.setPlotLimits();
    	}else if(ae.getSource()==scaleComboBox){
    		parent.setScaleFactor(scaleComboBox.getSelectedIndex());
    		parent.redrawPlot();
    	}
    }
    
    /**
     * Sets the current state.
     */
    public void setCurrentState(){
	
		removeAll();

		WordWrapLabel topLabel = new WordWrapLabel();
		topLabel.setText("Disabled checkboxes below indicate a zero final abundance " +
								"over all scale factors. Isotopes not appearing in the checkbox list " +
								"below were not included in the simulation.");

		TreeMap<String, TreeMap<Double, NucSimDataStructure>> map = ds.getSensMap();
		
		setComboBox.removeActionListener(this);
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()){
			setComboBox.addItem(itr.next());
		}
		setComboBox.setSelectedIndex(0);
		parent.setSetName(setComboBox.getSelectedItem().toString());
		setComboBox.addActionListener(this);
		
		scaleComboBox.removeActionListener(this);
		for(double scaleFactor: parent.factors){
			scaleComboBox.addItem(scaleFactor);
		}
		scaleComboBox.setSelectedIndex(0);
		scaleComboBox.setEnabled(false);
		scaleComboBox.addActionListener(this);
		
		JLabel setLabel = new JLabel("Sensitivity Study:");
		setLabel.setFont(Fonts.textFont);
		
		JLabel scaleLabel = new JLabel("Normalized @ Scale Factor:");
		scaleLabel.setFont(Fonts.textFont);
		
		JPanel selectPanel = new JPanel();
		double[] colSelect = {10, TableLayoutConstants.FILL, 10};
		double[] rowSelect = {10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, 10};
		selectPanel.setLayout(new TableLayout(colSelect, rowSelect));
		selectPanel.add(abundRadioButton, "1, 1, f, c");
		selectPanel.add(normRadioButton, "1, 3, f, c");
		selectPanel.add(scaleLabel, "1, 5, l, c");
		selectPanel.add(scaleComboBox, "1, 7, f, c");
		selectPanel.add(setLabel, "1, 9, l, c");
		selectPanel.add(setComboBox, "1, 11, f, c");
		
		JPanel boxPanel = new JPanel(new GridLayout(parent.index.length, 1, 5, 5));
		boxList = new ArrayList<JCheckBox>();
		indexList = new ArrayList<Integer>();
		int counter = 0;
		for(int index: parent.index){
			JCheckBox box = new JCheckBox();
			boxList.add(box);
			indexList.add(index);
			box.setText(parent.getMap().values().iterator().next().getIsotopeLabelMapArray()[index]);
			box.setFont(Fonts.textFont);
			if(!parent.isArrayIndexNonZero(counter)){
				box.setEnabled(false);
				box.setSelected(false);
			}else{
				box.setSelected(true);
			}
			
			box.addActionListener(this);
			boxPanel.add(box);
			counter++;
		}
		
		double[] col = {10, TableLayoutConstants.FILL, 10};
		double[] row = {10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED, 10};
		setLayout(new TableLayout(col, row));
		
		add(topLabel, "1, 1, f, c");
		add(selectPanel, "1, 3, c, c");
		add(boxPanel, "1, 5, c, c");
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
    		string = String.valueOf(A) + MainDataStructure.getElementSymbol(Z);
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
}    