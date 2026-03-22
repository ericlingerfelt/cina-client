package org.nucastrodata.element.elementviz.animator.bottle;

import java.awt.event.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.datastructure.feature.BottleDataStructure.Time;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PrintfFormat;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

/**
 * The Class BottleSimPanel.
 */
public class BottleSimPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BottleDataStructure ds;
	
	/** The sim label. */
	private JLabel rateLibLabel, simLabel;
	
	/** The top label. */
	private WordWrapLabel topLabel;
	
	/** The custom button. */
	private JRadioButton timestepButton, timesecButton, defaultButton, customButton;
	
	/** The amax field. */
	private JTextField timestepminField, timestepmaxField, timesecminField, timesecmaxField
						, zminField, zmaxField, nminField, nmaxField, aminField, amaxField;
	
	/**
	 * Instantiates a new bottle sim panel.
	 *
	 * @param ds the ds
	 */
	public BottleSimPanel(BottleDataStructure ds){
		
		 this.ds = ds;
		
		 double gap = 10;
		 double[] column = {gap, TableLayoutConstants.FILL, gap};
		 double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		 setLayout(new TableLayout(column, row));
		
		 topLabel = new WordWrapLabel(true);
		 simLabel = new JLabel();
		 simLabel.setFont(Fonts.textFont);
		 rateLibLabel = new JLabel();
		 rateLibLabel.setFont(Fonts.textFont);
		 
		 JLabel timeWindowLabel = new JLabel("Enter Timestep Window below: "); 
		 timeWindowLabel.setFont(Fonts.textFont);
		 
		 JLabel nucleiRangeLabel = new JLabel("Enter Mass Range below: "); 
		 nucleiRangeLabel.setFont(Fonts.textFont);
		 
		 timestepButton = new JRadioButton("Timestep", true);
		 timestepButton.setFont(Fonts.textFont);
		 timestepButton.addActionListener(this);
		 
		 timesecButton = new JRadioButton("Time (sec)", false);
		 timesecButton.setFont(Fonts.textFont);
		 timesecButton.addActionListener(this);
		 
		 ButtonGroup timeGroup = new ButtonGroup();
		 timeGroup.add(timestepButton);
		 timeGroup.add(timesecButton);
		 
		 defaultButton = new JRadioButton("Default (all masses)", true);
		 defaultButton.setFont(Fonts.textFont);
		 defaultButton.addActionListener(this);
		 
		 customButton = new JRadioButton("Custom (enter range)", false);
		 customButton.setFont(Fonts.textFont);
		 customButton.addActionListener(this);
		 
		 ButtonGroup rangeGroup = new ButtonGroup();
		 rangeGroup.add(defaultButton);
		 rangeGroup.add(customButton);
		 
		 timestepminField = new JTextField(7);
		 timestepmaxField = new JTextField(7);
		 
		 timesecminField = new JTextField(7);
		 timesecminField.setEditable(false);
		 timesecmaxField = new JTextField(7);
		 timesecmaxField.setEditable(false);
		 
		 zminField = new JTextField(5);
		 zminField.setEditable(false);
		 zmaxField = new JTextField(5);
		 zmaxField.setEditable(false);
		 
		 nminField = new JTextField(5);
		 nminField.setEditable(false);
		 nmaxField = new JTextField(5);
		 nmaxField.setEditable(false);
		 
		 aminField = new JTextField(5);
		 aminField.setEditable(false);
		 amaxField = new JTextField(5);
		 amaxField.setEditable(false);
		 
		 JLabel timestepminLabel = new JLabel("Timestep min: ");
		 timestepminLabel.setFont(Fonts.textFont);
		 
		 JLabel timestepmaxLabel = new JLabel("Timestep max: ");
		 timestepmaxLabel.setFont(Fonts.textFont);
		 
		 JLabel timesecminLabel = new JLabel("Time min (sec): ");
		 timesecminLabel.setFont(Fonts.textFont);
		 
		 JLabel timesecmaxLabel = new JLabel("Time max (sec): ");
		 timesecmaxLabel.setFont(Fonts.textFont);
		 
		 JLabel zminLabel = new JLabel("Z min: ");
		 zminLabel.setFont(Fonts.textFont);
		 
		 JLabel zmaxLabel = new JLabel("Z max: ");
		 zmaxLabel.setFont(Fonts.textFont);
		 
		 JLabel nminLabel = new JLabel("N min: ");
		 nminLabel.setFont(Fonts.textFont);
		 
		 JLabel nmaxLabel = new JLabel("N max: ");
		 nmaxLabel.setFont(Fonts.textFont);
		 
		 JLabel aminLabel = new JLabel("A min: ");
		 aminLabel.setFont(Fonts.textFont);
		 
		 JLabel amaxLabel = new JLabel("A max: ");
		 amaxLabel.setFont(Fonts.textFont);
		
		 double[] columnTime = {TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED};
		 double[] rowTime = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		 JPanel timePanel = new JPanel(new TableLayout(columnTime, rowTime));
		 
		 timePanel.add(timeWindowLabel, "0, 1, 2, 1, l, c");
		 timePanel.add(timestepminLabel, "0, 3, r, c");
		 timePanel.add(timestepminField, "2, 3, l, c");
		 timePanel.add(timestepmaxLabel, "4, 3, r, c");
		 timePanel.add(timestepmaxField, "6, 3, l, c");
		 
		 double[] columnRange = {TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED};
		 double[] rowRange = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		 JPanel rangePanel = new JPanel(new TableLayout(columnRange, rowRange));
		 
		 rangePanel.add(nucleiRangeLabel, "0, 1, l, c");
		 rangePanel.add(customButton, "0, 3, l, c");
		 rangePanel.add(defaultButton, "0, 5, l, c");
		 rangePanel.add(aminLabel, "2, 3, r, c");
		 rangePanel.add(aminField, "4, 3, l, c");
		 rangePanel.add(amaxLabel, "6, 3, r, c");
		 rangePanel.add(amaxField, "8, 3, l, c");
		 
		 add(topLabel, "1, 1, f, c");
		 add(simLabel, "1, 3, c, c");
		 add(timePanel, "1, 5, c, c");
		 add(rangePanel, "1, 7, c, c");
		 
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		
		String timestepmin = timestepminField.getText().trim();
		String timestepmax = timestepmaxField.getText().trim();
		String amin = aminField.getText().trim();
		String amax = amaxField.getText().trim();
		
		if(amin.equals("")
				|| amax.equals("")
				|| timestepmin.equals("")
				|| timestepmax.equals("")){
			return false;
		}
		
		try{
			Integer.valueOf(timestepmin);
			Integer.valueOf(timestepmax);
			Integer.valueOf(amin);
			Integer.valueOf(amax);
		}catch(NumberFormatException nfe){
			return false;
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==timestepButton || ae.getSource()==timesecButton){
			if(timestepButton.isSelected()){
				timestepminField.setEditable(true);
				timestepmaxField.setEditable(true);
				timesecminField.setEditable(false);
				timesecmaxField.setEditable(false);
			}else if(timesecButton.isSelected()){
				timestepminField.setEditable(false);
				timestepmaxField.setEditable(false);
				timesecminField.setEditable(true);
				timesecmaxField.setEditable(true);
			}
		}else if(ae.getSource()==defaultButton || ae.getSource()==customButton){
			if(defaultButton.isSelected()){
				zminField.setEditable(false);
				zmaxField.setEditable(false);
				nminField.setEditable(false);
				nmaxField.setEditable(false);
				aminField.setEditable(false);
				amaxField.setEditable(false);
			}else if(customButton.isSelected()){
				zminField.setEditable(true);
				zmaxField.setEditable(true);
				nminField.setEditable(true);
				nmaxField.setEditable(true);
				aminField.setEditable(true);
				amaxField.setEditable(true);
			}
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		topLabel.setText("<html>Please enter a timestep range for the simulation."
 							+ " Then select the appropriate radio button and enter a mass range or select default to choose all masses."
 							+ "</html>");
		rateLibLabel.setText("Rate Library for this simulation: " + ds.getRateLibrary());
		simLabel.setText("Selected simulation: " + ds.getPath());
		
		if(ds.getTime()==BottleDataStructure.Time.TIME_SEC){
			timesecButton.setSelected(true);
			timestepButton.setSelected(false);
		}else if(ds.getTime()==BottleDataStructure.Time.TIME_STEP){
			timesecButton.setSelected(false);
			timestepButton.setSelected(true);
		}
		
		if(ds.getRange()==BottleDataStructure.Range.CUSTOM){
			customButton.setSelected(true);
			defaultButton.setSelected(false);
		}else if(ds.getRange()==BottleDataStructure.Range.DEFAULT){
			customButton.setSelected(false);
			defaultButton.setSelected(true);
		}
		
		if(defaultButton.isSelected()){
			zminField.setEditable(false);
			zmaxField.setEditable(false);
			nminField.setEditable(false);
			nmaxField.setEditable(false);
		}else if(customButton.isSelected()){
			zminField.setEditable(true);
			zmaxField.setEditable(true);
			nminField.setEditable(true);
			nmaxField.setEditable(true);
		}
		
		timestepminField.setText(new Integer(ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(0).intValue()).toString());
		timestepmaxField.setText(new Integer(ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(1).intValue()).toString());
		timesecminField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(0)).trim());
		timesecmaxField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(1)).trim());
		
		zminField.setText(new Integer((int)ds.getZ().getX()).toString());
		zmaxField.setText(new Integer((int)ds.getZ().getY()).toString());
		nminField.setText(new Integer((int)ds.getN().getX()).toString());
		nmaxField.setText(new Integer((int)ds.getN().getY()).toString());
		aminField.setText(new Integer((int)ds.getA().getX()).toString());
		amaxField.setText(new Integer((int)ds.getA().getY()).toString());
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		NucSimDataStructure nsds = Cina.elementVizDataStructure.getAnimatorNucSimDataStructure();
		double[] timeArray = nsds.getTimeArray();
		
		if(timesecButton.isSelected()){
			ds.setTime(BottleDataStructure.Time.TIME_SEC);
			double min = Double.valueOf(timesecminField.getText());
			foundMinStep:
			for(int i=0; i<timeArray.length; i++){
				if((timeArray[i] - ds.getNormalTime())>=min){
					timestepminField.setText(String.valueOf(i-1));
					break foundMinStep;
				}
			}
			double max = Double.valueOf(timesecmaxField.getText());
			foundMaxStep:
			for(int i=0; i<timeArray.length; i++){
				if((timeArray[i] - ds.getNormalTime())>=max){
					timestepmaxField.setText(String.valueOf(i));
					break foundMaxStep;
				}
			}
		}else{
			ds.setTime(BottleDataStructure.Time.TIME_STEP);
			timesecminField.setText(String.valueOf(timeArray[Integer.valueOf(timestepminField.getText())] 
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
			timesecmaxField.setText(String.valueOf(timeArray[Integer.valueOf(timestepmaxField.getText())]
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
		}
		
		HashMap<Time, ArrayList<Double>> timeMap = new HashMap<Time, ArrayList<Double>>();
		timeMap.put(BottleDataStructure.Time.TIME_STEP, new ArrayList<Double>());
		timeMap.get(BottleDataStructure.Time.TIME_STEP).add(new Double(timestepminField.getText()));
		timeMap.get(BottleDataStructure.Time.TIME_STEP).add(new Double(timestepmaxField.getText()));
		timeMap.put(BottleDataStructure.Time.TIME_SEC, new ArrayList<Double>());
		timeMap.get(BottleDataStructure.Time.TIME_SEC).add(new Double(timesecminField.getText()));
		timeMap.get(BottleDataStructure.Time.TIME_SEC).add(new Double(timesecmaxField.getText()));
 
    	ds.setTimeMap(timeMap);

		if(customButton.isSelected()){
			ds.setRange(BottleDataStructure.Range.CUSTOM);
		}else if(defaultButton.isSelected()){
			ds.setRange(BottleDataStructure.Range.DEFAULT);
		}
		
		ds.setZ(new Point(Integer.valueOf(zminField.getText()), Integer.valueOf(zmaxField.getText())));
		ds.setN(new Point(Integer.valueOf(nminField.getText()), Integer.valueOf(nmaxField.getText())));
		ds.setA(new Point(Integer.valueOf(aminField.getText()), Integer.valueOf(amaxField.getText())));
		
	}
	
}

