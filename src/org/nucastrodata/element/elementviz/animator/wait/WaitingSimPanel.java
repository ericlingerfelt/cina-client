package org.nucastrodata.element.elementviz.animator.wait;

import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure.Time;
import org.nucastrodata.datastructure.util.NucSimDataStructure;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PrintfFormat;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;


/**
 * The Class WaitingSimPanel.
 */
public class WaitingSimPanel extends JPanel implements ActionListener{

	/** The ds. */
	private WaitingDataStructure ds;
	
	/** The sim label. */
	private JLabel topLabel, rateLibLabel, simLabel;
	
	/** The custom button. */
	private JRadioButton timestepButton, timesecButton, defaultButton, customButton;
	
	/** The nmax field. */
	private JTextField timestepminField, timestepmaxField, timesecminField, timesecmaxField
						, zminField, zmaxField, nminField, nmaxField;
	
	/**
	 * Instantiates a new waiting sim panel.
	 *
	 * @param ds the ds
	 */
	public WaitingSimPanel(WaitingDataStructure ds){
		
		 this.ds = ds;
		
		 double gap = 10;
		 double[] column = {TableLayoutConstants.PREFERRED};
		 double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		 setLayout(new TableLayout(column, row));
		
		 topLabel = new JLabel();
		 simLabel = new JLabel();
		 simLabel.setFont(Fonts.textFont);
		 rateLibLabel = new JLabel();
		 rateLibLabel.setFont(Fonts.textFont);
		 
		 JLabel timeWindowLabel = new JLabel("Enter Time Window below: "); 
		 timeWindowLabel.setFont(Fonts.textFont);
		 
		 JLabel nucleiRangeLabel = new JLabel("Enter Nuclei Range below: "); 
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
		 
		 defaultButton = new JRadioButton("Default (all isotopes)", true);
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
		
		 double[] columnTime = {TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED
					 				, gap, TableLayoutConstants.PREFERRED};
		 double[] rowTime = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		 JPanel timePanel = new JPanel(new TableLayout(columnTime, rowTime));
		 
		 timePanel.add(timeWindowLabel, "0, 1, 2, 1, l, c");
		 timePanel.add(timestepButton, "0, 3");
		 timePanel.add(timesecButton, "0, 5");
		 timePanel.add(timestepminLabel, "2, 3, r, c");
		 timePanel.add(timestepminField, "4, 3, l, c");
		 timePanel.add(timestepmaxLabel, "6, 3, r, c");
		 timePanel.add(timestepmaxField, "8, 3, l, c");
		 timePanel.add(timesecminLabel, "2, 5, r, c");
		 timePanel.add(timesecminField, "4, 5, l, c");
		 timePanel.add(timesecmaxLabel, "6, 5, r, c");
		 timePanel.add(timesecmaxField, "8, 5, l, c");
		 
		 double[] columnRange = {TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED
				 					, gap, TableLayoutConstants.PREFERRED};
		 double[] rowRange = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
		 JPanel rangePanel = new JPanel(new TableLayout(columnRange, rowRange));
		 
		 rangePanel.add(nucleiRangeLabel, "0, 1");
		 rangePanel.add(defaultButton, "0, 3");
		 rangePanel.add(customButton, "0, 5");
		 rangePanel.add(zminLabel, "2, 3, r, c");
		 rangePanel.add(zminField, "4, 3, l, c");
		 rangePanel.add(zmaxLabel, "6, 3, r, c");
		 rangePanel.add(zmaxField, "8, 3, l, c");
		 rangePanel.add(nminLabel, "2, 5, r, c");
		 rangePanel.add(nminField, "4, 5, l, c");
		 rangePanel.add(nmaxLabel, "6, 5, r, c");
		 rangePanel.add(nmaxField, "8, 5, l, c");
		 
		 add(topLabel, "0, 1");
		 add(simLabel, "0, 3");
		 //add(rateLibLabel, "0, 5");
		 add(timePanel, "0, 5");
		 add(rangePanel, "0, 7");
		 
	}
	
	/**
	 * Good timesteps.
	 *
	 * @return true, if successful
	 */
	public boolean goodTimesteps(){

		NucSimDataStructure nsds = Cina.elementVizDataStructure.getAnimatorNucSimDataStructure();
		double[] timeArray = nsds.getTimeArray();
		
		if(timesecButton.isSelected()){
		
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
		
			return max - min <= 1000;
			
		}
		
		String timestepmin = timestepminField.getText().trim();
		String timestepmax = timestepmaxField.getText().trim();
		
		return (Integer.valueOf(timestepmax) - Integer.valueOf(timestepmin)) <= 1000;
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	public boolean goodData(){
		
		String zmin = zminField.getText().trim();
		String zmax = zmaxField.getText().trim();
		String nmin = zminField.getText().trim();
		String nmax = zmaxField.getText().trim();
		String timestepmin = timestepminField.getText().trim();
		String timestepmax = timestepmaxField.getText().trim();
		String timesecmin = timesecminField.getText().trim();
		String timesecmax = timesecmaxField.getText().trim();
		
		if(zmin.equals("")
				|| zmax.equals("")
				|| nmin.equals("")
				|| nmax.equals("")
				|| timestepmin.equals("")
				|| timestepmax.equals("")
				|| timesecmin.equals("")
				|| timesecmax.equals("")){
			return false;
		}
		
		try{
			Integer.valueOf(zmin);
			Integer.valueOf(zmax);
			Integer.valueOf(nmin);
			Integer.valueOf(nmax);
			Integer.valueOf(timestepmin);
			Integer.valueOf(timestepmax);
			Double.valueOf(timesecmin);
			Double.valueOf(timesecmax);
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
			}else if(customButton.isSelected()){
				zminField.setEditable(true);
				zmaxField.setEditable(true);
				nminField.setEditable(true);
				nmaxField.setEditable(true);
			}
		}
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		topLabel.setText("<html>Please select the appropriate radio button and enter a time (sec) or timestep range<p>for the simulation containing less than 1000 timesteps."
 							+ " Then select the appropriate<p>radio button and enter an isotope range or select default to choose all isotopes.</html>");
		rateLibLabel.setText("Rate Library for this simulation: " + ds.getRateLibrary());
		simLabel.setText("Selected simulation: " + ds.getPath());
		
		if(ds.getTime()==WaitingDataStructure.Time.TIME_SEC){
			timesecButton.setSelected(true);
			timestepButton.setSelected(false);
		}else if(ds.getTime()==WaitingDataStructure.Time.TIME_STEP){
			timesecButton.setSelected(false);
			timestepButton.setSelected(true);
		}
		
		if(ds.getRange()==WaitingDataStructure.Range.CUSTOM){
			customButton.setSelected(true);
			defaultButton.setSelected(false);
		}else if(ds.getRange()==WaitingDataStructure.Range.DEFAULT){
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
		
		timestepminField.setText(new Integer(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(0).intValue()).toString());
		timestepmaxField.setText(new Integer(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(1).intValue()).toString());
		timesecminField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(0)).trim());
		timesecmaxField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(1)).trim());
		
		zminField.setText(new Integer((int)ds.getZ().getX()).toString());
		zmaxField.setText(new Integer((int)ds.getZ().getY()).toString());
		nminField.setText(new Integer((int)ds.getN().getX()).toString());
		nmaxField.setText(new Integer((int)ds.getN().getY()).toString());
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
			ds.setTime(WaitingDataStructure.Time.TIME_SEC);
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
			ds.setTime(WaitingDataStructure.Time.TIME_STEP);
			timesecminField.setText(String.valueOf(timeArray[Integer.valueOf(timestepminField.getText())] 
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
			timesecmaxField.setText(String.valueOf(timeArray[Integer.valueOf(timestepmaxField.getText())]
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
		}
		
		HashMap<Time, ArrayList<Double>> timeMap = new HashMap<Time, ArrayList<Double>>();
		timeMap.put(WaitingDataStructure.Time.TIME_STEP, new ArrayList<Double>());
		timeMap.get(WaitingDataStructure.Time.TIME_STEP).add(new Double(timestepminField.getText()));
		timeMap.get(WaitingDataStructure.Time.TIME_STEP).add(new Double(timestepmaxField.getText()));
		timeMap.put(WaitingDataStructure.Time.TIME_SEC, new ArrayList<Double>());
		timeMap.get(WaitingDataStructure.Time.TIME_SEC).add(new Double(timesecminField.getText()));
		timeMap.get(WaitingDataStructure.Time.TIME_SEC).add(new Double(timesecmaxField.getText()));
		
    	ds.setTimeMap(timeMap);

		if(customButton.isSelected()){
			ds.setRange(WaitingDataStructure.Range.CUSTOM);
		}else if(defaultButton.isSelected()){
			ds.setRange(WaitingDataStructure.Range.DEFAULT);
		}
		
		ds.setZ(new Point(Integer.valueOf(zminField.getText()), Integer.valueOf(zmaxField.getText())));
		ds.setN(new Point(Integer.valueOf(nminField.getText()), Integer.valueOf(nmaxField.getText())));
		
	}
	
}
