package org.nucastrodata.element.elementviz.animator.flux;

import java.awt.event.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.datastructure.feature.FluxDataStructure.Time;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.util.ArrayList;
import java.util.HashMap;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PrintfFormat;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class FluxSimPanel extends JPanel implements ActionListener{

	private FluxDataStructure ds;
	
	private JLabel rateLibLabel, simLabel;
	
	private WordWrapLabel topLabel;
	
	private JRadioButton timestepButton, timesecButton;
	
	private JTextField timestepminField, timestepmaxField, timesecminField, timesecmaxField;

	public FluxSimPanel(FluxDataStructure ds){
		
		 this.ds = ds;
		
		 double gap = 10;
		 double[] column = {gap, TableLayoutConstants.FILL, gap};
		 double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED, gap};
		
		 setLayout(new TableLayout(column, row));
		
		 topLabel = new WordWrapLabel();
		 simLabel = new JLabel();
		 simLabel.setFont(Fonts.textFont);
		 rateLibLabel = new JLabel();
		 rateLibLabel.setFont(Fonts.textFont);
		 
		 JLabel timeWindowLabel = new JLabel("Enter Timestep Window below: "); 
		 timeWindowLabel.setFont(Fonts.textFont);
		 
		 timestepButton = new JRadioButton("Timestep", true);
		 timestepButton.setFont(Fonts.textFont);
		 timestepButton.addActionListener(this);
		 
		 timesecButton = new JRadioButton("Time (sec)", false);
		 timesecButton.setFont(Fonts.textFont);
		 timesecButton.addActionListener(this);
		 
		 ButtonGroup timeGroup = new ButtonGroup();
		 timeGroup.add(timestepButton);
		 timeGroup.add(timesecButton);
		 
		 timestepminField = new JTextField(7);
		 timestepmaxField = new JTextField(7);
		 
		 timesecminField = new JTextField(7);
		 timesecminField.setEditable(false);
		 timesecmaxField = new JTextField(7);
		 timesecmaxField.setEditable(false);
		 
		 JLabel timestepminLabel = new JLabel("Timestep min: ");
		 timestepminLabel.setFont(Fonts.textFont);
		 
		 JLabel timestepmaxLabel = new JLabel("Timestep max: ");
		 timestepmaxLabel.setFont(Fonts.textFont);
		 
		 JLabel timesecminLabel = new JLabel("Time min (sec): ");
		 timesecminLabel.setFont(Fonts.textFont);
		 
		 JLabel timesecmaxLabel = new JLabel("Time max (sec): ");
		 timesecmaxLabel.setFont(Fonts.textFont);
		
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
		 
		 add(topLabel, "1, 1, f, c");
		 add(simLabel, "1, 3, c, c");
		 add(timePanel, "1, 5, c, c");
		 
	}
	
	public boolean goodData(){
		
		String timestepmin = timestepminField.getText().trim();
		String timestepmax = timestepmaxField.getText().trim();
		
		if(timestepmin.equals("")
				|| timestepmax.equals("")){
			return false;
		}
		
		try{
			Integer.valueOf(timestepmin);
			Integer.valueOf(timestepmax);
		}catch(NumberFormatException nfe){
			return false;
		}
		
		return true;
	}
	
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
		}
	}
	
	public void setCurrentState(){
		topLabel.setText("<html>Please enter a timestep range for the simulation.</html>");
		rateLibLabel.setText("Rate Library for this simulation: " + ds.getRateLibrary());
		simLabel.setText("Selected simulation: " + ds.getPath());
		
		if(ds.getTime()==FluxDataStructure.Time.TIME_SEC){
			timesecButton.setSelected(true);
			timestepButton.setSelected(false);
		}else if(ds.getTime()==FluxDataStructure.Time.TIME_STEP){
			timesecButton.setSelected(false);
			timestepButton.setSelected(true);
		}
		
		timestepminField.setText(new Integer(ds.getTimeMap().get(FluxDataStructure.Time.TIME_STEP).get(0).intValue()).toString());
		timestepmaxField.setText(new Integer(ds.getTimeMap().get(FluxDataStructure.Time.TIME_STEP).get(1).intValue()).toString());
		timesecminField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(FluxDataStructure.Time.TIME_SEC).get(0)).trim());
		timesecmaxField.setText(new PrintfFormat("%13.4E").sprintf(ds.getTimeMap().get(FluxDataStructure.Time.TIME_SEC).get(1)).trim());
	}
	
	public void getCurrentState(){
		
		NucSimDataStructure nsds = Cina.elementVizDataStructure.getAnimatorNucSimDataStructure();
		double[] timeArray = nsds.getTimeArray();
		
		if(timesecButton.isSelected()){
			ds.setTime(FluxDataStructure.Time.TIME_SEC);
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
			ds.setTime(FluxDataStructure.Time.TIME_STEP);
			timesecminField.setText(String.valueOf(timeArray[Integer.valueOf(timestepminField.getText())] 
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
			timesecmaxField.setText(String.valueOf(timeArray[Integer.valueOf(timestepmaxField.getText())]
			                                                 - Cina.elementVizFrame.elementVizAnimatorFrame.normalTime));
		}
		
		HashMap<Time, ArrayList<Double>> timeMap = new HashMap<FluxDataStructure.Time, ArrayList<Double>>();
		timeMap.put(FluxDataStructure.Time.TIME_STEP, new ArrayList<Double>());
		timeMap.get(FluxDataStructure.Time.TIME_STEP).add(new Double(timestepminField.getText()));
		timeMap.get(FluxDataStructure.Time.TIME_STEP).add(new Double(timestepmaxField.getText()));
		timeMap.put(FluxDataStructure.Time.TIME_SEC, new ArrayList<Double>());
		timeMap.get(FluxDataStructure.Time.TIME_SEC).add(new Double(timesecminField.getText()));
		timeMap.get(FluxDataStructure.Time.TIME_SEC).add(new Double(timesecmaxField.getText()));
    	ds.setTimeMap(timeMap);
		
	}
	
}


