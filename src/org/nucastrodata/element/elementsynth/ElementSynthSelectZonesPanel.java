package org.nucastrodata.element.elementsynth;

import javax.swing.*;
import java.awt.Frame;
import java.awt.event.*;

import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.enums.SimCat;
import org.nucastrodata.Cina;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.io.FileGetter;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.*;

public class ElementSynthSelectZonesPanel extends JPanel implements ActionListener{
	
	protected JComboBox zonesComboBox;
	private JRadioButton allZonesRadioButton, oneZoneRadioButton;
	private JButton viewThermoButton, viewWeightsButton;
	private ElementSynthDataStructure ds;
	private JLabel zonesLabel;
	private MainDataStructure mds;
	private WordWrapLabel topLabel;
	private JPanel zonesPanel;
	private double gap = 20;
	
	public ElementSynthSelectZonesPanel(ElementSynthDataStructure ds, MainDataStructure mds){
		
		this.ds = ds;
		this.mds = mds;

		topLabel = new WordWrapLabel(true);
		zonesLabel = new JLabel("Select Zone: ");
		zonesComboBox = new JComboBox();
		
		allZonesRadioButton = new JRadioButton("Multiple zone simulation", false);
		allZonesRadioButton.addActionListener(this);
		
		oneZoneRadioButton = new JRadioButton("Single zone simulation", true);
		oneZoneRadioButton.addActionListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(allZonesRadioButton);
		buttonGroup.add(oneZoneRadioButton);
		
		viewWeightsButton = new JButton("View Zone Weights");
		viewWeightsButton.addActionListener(this);
		
		viewThermoButton = new JButton("View Thermodynamic Profile");
		viewThermoButton.addActionListener(this);

		zonesPanel = new JPanel();
		
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));
		
		add(topLabel, "1, 1, f, c");
		add(zonesPanel, "1, 5, c, c");
		
		validate();	
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==viewThermoButton){
			
			ElementSynthViewThermoWorker worker = new ElementSynthViewThermoWorker(Cina.elementSynthFrame, ds, allZonesRadioButton, zonesComboBox);
			worker.execute();
			
		}else if(ae.getSource()==viewWeightsButton){
			
			ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
			
			if(Cina.elementSynthFrame.elementSynthWeightFileFrame==null){
				Cina.elementSynthFrame.elementSynthWeightFileFrame = new ElementSynthDataFileFrame();
			}
			
			String filepath = "";
			if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimCat()==SimCat.THERMO && Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				filepath = "/var/www/cina_files_dev/USER/" + Cina.cinaMainDataStructure.getUser() + "/thermo/" + swds.getThermoPath().split("/")[1] + "/weights";
			}else if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimCat()==SimCat.THERMO){
				filepath = "/var/www/cina_files/USER/" + Cina.cinaMainDataStructure.getUser() + "/thermo/" + swds.getThermoPath().split("/")[1] + "/weights";
			}else if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				filepath = "/var/www/cina_files_dev/PUBLIC/thermo/" + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath().split("/")[1] + "/weights";
			}else{
				filepath = "/var/www/cina_files/PUBLIC/thermo/" + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath().split("/")[1] + "/weights";
			}

			String text = "";
			String title = "";
			text = new String(FileGetter.getFileByPath(filepath));
			title = "Zone Weights for " + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName();
			
			Cina.elementSynthFrame.elementSynthWeightFileFrame.initialize(title, Cina.elementSynthFrame);
			Cina.elementSynthFrame.elementSynthWeightFileFrame.setText(text);
			
		}else if(ae.getSource()==allZonesRadioButton || ae.getSource()==oneZoneRadioButton){
	
			zonesComboBox.setEnabled(!allZonesRadioButton.isSelected());
			
		}
	}
	
	public void setCurrentState(){
		
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		
		zonesComboBox.removeAllItems();
		for(int i=0; i<ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray().length; i++){
			zonesComboBox.addItem(String.valueOf(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[i]));
		}
		if(ds.getCurrentSimWorkDataStructure().getZoneArray().length == 1){
			zonesComboBox.setSelectedItem(String.valueOf(ds.getCurrentSimWorkDataStructure().getZoneArray()[0]));
		}else{
			zonesComboBox.setSelectedIndex(0);
		}
		
		if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray().length>1){
			allZonesRadioButton.setSelected(ds.getCurrentSimWorkDataStructure().getZoneArray().length != 1);
			allZonesRadioButton.setEnabled(true);
			oneZoneRadioButton.setSelected(ds.getCurrentSimWorkDataStructure().getZoneArray().length == 1);
			zonesComboBox.setEnabled(ds.getCurrentSimWorkDataStructure().getZoneArray().length == 1);
		}else{
			allZonesRadioButton.setSelected(false);
			allZonesRadioButton.setEnabled(false);
			oneZoneRadioButton.setSelected(true);
			zonesComboBox.setEnabled(true);
		}
		
		zonesPanel.removeAll();
		
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD:
			case SINGLE_CUSTOM:
			case DIR_STANDARD: 
			case DIR_CUSTOM_DOUBLE_LOOPING:
				topLabel.setText("Select either a single zone or multiple zone simulation below. "
						+ "If a single zone simulation is selected, please select a zone number from the dropdown menu.");
				if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray().length>1){
					allZonesRadioButton.setEnabled(true);
				}
				
				if(ds.getCurrentSimWorkDataStructure().getZoneArray().length>1){
					allZonesRadioButton.setSelected(true);
				}
				
				double[] columnZones = {TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.FILL};
				double[] rowZones = {TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED
									, 20, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED
									, 15, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED};
				zonesPanel.setLayout(new TableLayout(columnZones, rowZones));
				zonesPanel.add(oneZoneRadioButton, 	"0, 0, l, c");
				zonesPanel.add(zonesLabel, 			"2, 0, r, c");
				zonesPanel.add(zonesComboBox, 		"4, 0, f, c");
				zonesPanel.add(allZonesRadioButton, 	"0, 2, l, c");
				zonesPanel.add(viewWeightsButton, 	"0, 4, 4, 4, f, c");
				zonesPanel.add(viewThermoButton, 	"0, 6, 4, 6, f, c");
				break;
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
			
				allZonesRadioButton.setSelected(false);
				allZonesRadioButton.setEnabled(false);
				oneZoneRadioButton.setSelected(true);
				zonesComboBox.setEnabled(true);
			
				topLabel.setText("Select a single zone for your sensitivity study from the dropdown menu below.");
				zonesComboBox.setEnabled(true);
				
				double[] columnZones1 = {TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.FILL};
				double[] rowZones1 = {TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED
									, 20, TableLayoutConstants.PREFERRED};
				zonesPanel.setLayout(new TableLayout(columnZones1, rowZones1));
				zonesPanel.add(zonesLabel, 			"0, 0, r, c");
				zonesPanel.add(zonesComboBox, 		"2, 0, f, c");
				zonesPanel.add(viewWeightsButton, 	"0, 2, 2, 2, f, c");
				zonesPanel.add(viewThermoButton, 	"0, 4, 2, 4, f, c");
				break;
			default:
				break;
			}
		
		zonesPanel.validate();
	
	}
	
	public void getCurrentState(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD:
				swds.setThermoPath(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath());
				break;
			case SINGLE_STANDARD_SENS:
				swds.setThermoPath(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath());
				break;
			case DIR_STANDARD:
				swds.setThermoPath(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath());
				break;
			default:
				break;
		}
	
		swds.setZones(getZones());

		String startTime = NumberFormats.getFormattedValueLong1(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getStartTime());
		String stopTime = NumberFormats.getFormattedValueLong1(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getStopTime());
		
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD:
				swds.setStartTime(startTime);
				swds.setStopTime(stopTime);
				break;
			case SINGLE_STANDARD_SENS:
				swds.setStartTime(startTime);
				swds.setStopTime(stopTime);
				break;
			case DIR_STANDARD:
				swds.setStartTime(startTime);
				swds.setStopTime(stopTime);
				break;
			default:
				break;
		}
	}
	
	private String getZones(){
	
		String string = "";
		if(allZonesRadioButton.isSelected()){
			for(int i=0; i<ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray().length; i++){
				if(i==0){
					string += String.valueOf(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[i]);
				}else{
					string += "," + String.valueOf(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[i]);
				}
			}
		}else{
			string = zonesComboBox.getSelectedItem().toString();
		}

		return string;
	}

}

class ElementSynthViewThermoWorker extends SwingWorker<Void, Void> {

	private PleaseWaitDialog dialog;
	private String text, title;
	private ElementSynthDataStructure ds;
	private JRadioButton allZonesRadioButton;
	private JComboBox zonesComboBox;
	private Frame owner;

	public ElementSynthViewThermoWorker(Frame owner, ElementSynthDataStructure ds, JRadioButton allZonesRadioButton, JComboBox zonesComboBox){
		this.owner = owner;
		this.ds = ds;
		this.allZonesRadioButton = allZonesRadioButton;
		this.zonesComboBox = zonesComboBox;
		dialog = new PleaseWaitDialog(owner, "Please wait while downloading thermodynamic profile data.");
		dialog.open();
	}

	protected Void doInBackground(){
		if(Cina.elementSynthFrame.elementSynthThermoFileFrame==null){
			Cina.elementSynthFrame.elementSynthThermoFileFrame = new ElementSynthDataFileFrame();
		}
		String filepath = "";
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimCat()==SimCat.THERMO && Cina.cinaMainDataStructure.getURLType().equals("DEV")){
			filepath = "/var/www/cina_files_dev/USER/" + Cina.cinaMainDataStructure.getUser() + "/thermo/" + swds.getThermoPath().split("/")[1];
		}else if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimCat()==SimCat.THERMO){
			filepath = "/var/www/cina_files/USER/" + Cina.cinaMainDataStructure.getUser() + "/thermo/" + swds.getThermoPath().split("/")[1];
		}else if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
			filepath = "/var/www/cina_files_dev/PUBLIC/thermo/" + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath().split("/")[1];
		}else{
			filepath = "/var/www/cina_files/PUBLIC/thermo/" + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getThermoPath().split("/")[1];
		}
		text = "";
		title = "";
		if(allZonesRadioButton.isSelected()){
			for(int i=0; i<zonesComboBox.getItemCount(); i++){
				text += "Thermo Profile for Zone " + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[i] + "\n\n";
				text += new String(FileGetter.getFileByPath(filepath + "/" + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[i])) + "\n\n";
			}
			title = "Thermo Profiles for " + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName() + " for all Zones";
		}else{
			text = new String(FileGetter.getFileByPath(filepath + "/" 
								+ ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getZoneArray()[zonesComboBox.getSelectedIndex()]));
			title = "Thermo Profile for " + ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName() + " for " + zonesComboBox.getSelectedItem();
		}

		return null;
	}
	
	protected void done(){
		dialog.close();
		Cina.elementSynthFrame.elementSynthThermoFileFrame.initialize(title, owner);
		Cina.elementSynthFrame.elementSynthThermoFileFrame.setText(text);
	}
}
