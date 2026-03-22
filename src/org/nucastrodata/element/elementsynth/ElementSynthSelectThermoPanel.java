package org.nucastrodata.element.elementsynth;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.export.print.PrintableEditorPane;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementSynthSelectThermoPanel extends JPanel implements ActionListener{
	
	private ElementSynthDataStructure ds;
	private WordWrapLabel topLabel;
	private JComboBox<ThermoProfileSetDataStructure> thermoComboBox;
	private JLabel thermoLabel, descLabel;
	private JScrollPane sp;
	private JPanel setPanel, descPanel;
	private PrintableEditorPane textPane;
	private ThermoProfileSetDataStructure selectedThermoProfileSet;
	
	public ElementSynthSelectThermoPanel(ElementSynthDataStructure ds){
		
		this.ds = ds;

		topLabel = new WordWrapLabel(true);

		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		sp = new JScrollPane(textPane);
		
		topLabel.setText("Select a set of thermodynamic profiles below.");
		
		thermoComboBox = new JComboBox<ThermoProfileSetDataStructure>();
		thermoComboBox.addActionListener(this);
		
		thermoLabel = new JLabel("Select Thermodynamic Profile Set:");
		descLabel = new JLabel("Thermodynamic Profile Set Information:");
		
		descPanel = new JPanel();
		double[] columnDesc = {TableLayoutConstants.FILL};
		double[] rowDesc = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL};
		descPanel.setLayout(new TableLayout(columnDesc, rowDesc));
		descPanel.add(descLabel, 	"0, 0, l, c");
		descPanel.add(sp, 		 	"0, 2, f, f");

		setPanel = new JPanel();
		double[] columnSet = {TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.FILL};
		double[] rowSet = {TableLayoutConstants.PREFERRED};
		setPanel.setLayout(new TableLayout(columnSet, rowSet));
		setPanel.add(thermoLabel, 		"0, 0, r, c");
		setPanel.add(thermoComboBox, 	"2, 0, f, c");
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL, 20};
		setLayout(new TableLayout(column, row));

		add(topLabel, 	"1, 1, c, c");
		add(setPanel, 	"1, 3, c, c");
		add(descPanel, 	"1, 5, f, f");
		
	}
	
	public void setCurrentState(){
	
		thermoComboBox.removeActionListener(this);
		thermoComboBox.removeAllItems();
		Iterator<ThermoProfileSetDataStructure> itr = ds.getSetMap().values().iterator();
		while(itr.hasNext()){
			ThermoProfileSetDataStructure tpsds = itr.next();
			if(tpsds.getFolderType() == FolderType.USER || tpsds.getFolderType() == FolderType.SHARED){
				thermoComboBox.addItem(tpsds);
			}
		}
		
		if(ds.getCurrentSimWorkDataStructure().getThermoProfileSet()!=null){
			thermoComboBox.setSelectedItem(ds.getCurrentSimWorkDataStructure().getThermoProfileSet());
		}else{
			thermoComboBox.setSelectedIndex(thermoComboBox.getItemCount()-1);
		}
		thermoComboBox.addActionListener(this);
		
		selectedThermoProfileSet = (ThermoProfileSetDataStructure) thermoComboBox.getSelectedItem();
		textPane.setText(getInfoReport());

	}
	
	public void getCurrentState(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
	
		swds.setStartTime(selectedThermoProfileSet.getStartTime());
		swds.setStopTime(selectedThermoProfileSet.getStopTime());
		swds.setThermoPath(selectedThermoProfileSet.getPath());
		swds.setZones(getZones());
		ds.setUseSensStudy(false);
		
		int[] newZoneArray = new int[selectedThermoProfileSet.getNumProfiles()];
		for(int i=0; i<newZoneArray.length; i++){
			newZoneArray[i] = i+1;
		}
		ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().setZoneArray(newZoneArray);
		ds.getCurrentSimWorkDataStructure().setThermoProfileSet(selectedThermoProfileSet);
		
	}
	
	private String getZones(){
		String string = "";
		for(int i=1; i<=selectedThermoProfileSet.getNumProfiles(); i++){
			if(i==selectedThermoProfileSet.getNumProfiles()){
				string += i;
			}else{
				string += i + ",";
			}
		}
		return string;
	}

	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==thermoComboBox){
			selectedThermoProfileSet = (ThermoProfileSetDataStructure) thermoComboBox.getSelectedItem();
			textPane.setText(getInfoReport());
			
		}
	}
	
	private String getInfoReport(){
		
		StringBuilder sb = new StringBuilder();
	
		sb.append("<html><body>");
		sb.append("<b>Thermodynamic Profile Report</b><br><br>");
		sb.append("<table border=\"1\">");
		sb.append("<tr><td><b>Thermodynamic Profile</b></td><td><b>" + selectedThermoProfileSet.getPath() + "</b></td></tr>");
		sb.append("<tr><td><b>Creation Date</b></td><td>" + selectedThermoProfileSet.getCreationDate() + "</td></tr>");
		sb.append("<tr><td><b>Description</b></td><td>" + selectedThermoProfileSet.getDesc() + "</td></tr>");
		sb.append("<tr><td><b>Number of Profiles</b></td><td>" + selectedThermoProfileSet.getNumProfiles() + "</td></tr>");
		sb.append("<tr><td><b>Start Time</b></td><td>" + selectedThermoProfileSet.getStartTime() + "</td></tr>");
		sb.append("<tr><td><b>Stop Time</b></td><td>" + selectedThermoProfileSet.getStopTime() + "</td></tr>");
		sb.append("</table><br>");
		return sb.toString();
	}

}