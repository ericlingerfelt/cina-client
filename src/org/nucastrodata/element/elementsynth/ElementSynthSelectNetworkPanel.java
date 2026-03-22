package org.nucastrodata.element.elementsynth;

import javax.swing.*;
import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;
import org.nucastrodata.io.FileGetter;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.*;

public class ElementSynthSelectNetworkPanel extends JPanel implements ActionListener{
	
	private JComboBox sunetComboBox;
	private JTextArea sunetDescArea;
	private JButton sunetButton, abundButton;
	private ElementSynthDataStructure ds;
	private WordWrapLabel topLabel2;
	
	public ElementSynthSelectNetworkPanel(ElementSynthDataStructure ds){
		
		this.ds = ds;
		double gap = 20;

		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("Please select a tracked nuclei list from the dropdown menu below. " +
								"You can view the list and it's associated initial abundances by clicking " +
								"one of the buttons below.");
		
		topLabel2 = new WordWrapLabel(true);
		
		sunetComboBox = new JComboBox();
		sunetComboBox.addActionListener(this);
		
		sunetDescArea = new JTextArea();
		sunetDescArea.setLineWrap(true);
		sunetDescArea.setWrapStyleWord(true);
		sunetDescArea.setEditable(false);
		JScrollPane sp = new JScrollPane(sunetDescArea);

		JLabel sunetLabel = new JLabel("Tracked Nuclei List:");
		JLabel sunetDescLabel = new JLabel("Tracked Nuclei List and Initial Abundances Description:");
		
		sunetButton = new JButton("View Selected Tracked Nuclei List");
		sunetButton.addActionListener(this);
		
		abundButton = new JButton("View Selected Initial Abundances");
		abundButton.addActionListener(this);
		
		JPanel panel = new JPanel();
		double[] column2 = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		double[] row2 = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		panel.setLayout(new TableLayout(column2, row2));
		panel.add(sunetLabel, "0, 0, r, c");
		panel.add(sunetComboBox, "2, 0, f, c");
		panel.add(sunetButton, "0, 2, 2, 2, f, c");
		panel.add(abundButton, "0, 4, 2, 4, f, c");
		
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.FILL, gap};
		setLayout(new TableLayout(column, row));
		add(topLabel, 		"1, 1, c, c");
		add(topLabel2, 		"1, 3, c, c");
		add(panel, 			"1, 5, c, c");
		add(sunetDescLabel, "1, 7, l, c");
		add(sp, 			"1, 9, f, f");
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==sunetButton){
			if(Cina.elementSynthFrame.elementSynthSunetFileFrame==null){
				Cina.elementSynthFrame.elementSynthSunetFileFrame = new ElementSynthDataFileFrame();
			}

			String sunetPath = ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSunetPathArray()[sunetComboBox.getSelectedIndex()];
			Cina.elementSynthFrame.elementSynthSunetFileFrame.initialize("Tracked Nuclei List " 
																						+ sunetComboBox.getSelectedItem()
																						, Cina.elementSynthFrame);
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				sunetPath = "/var/www/cina_files_dev/PUBLIC/sunet/" + sunetComboBox.getSelectedItem();
			}else{
				sunetPath = "/var/www/cina_files/PUBLIC/sunet/" + sunetComboBox.getSelectedItem();
			}
			Cina.elementSynthFrame.elementSynthSunetFileFrame.setText(new String(FileGetter.getFileByPath(sunetPath)));
		}else if(ae.getSource()==abundButton){
			if(Cina.elementSynthFrame.elementSynthAbundFileFrame==null){
				Cina.elementSynthFrame.elementSynthAbundFileFrame = new ElementSynthDataFileFrame();
			}
			String initAbundPath = ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getInitAbundPathArray()[sunetComboBox.getSelectedIndex()];
			Cina.elementSynthFrame.elementSynthAbundFileFrame.initialize("Initial Abundances for " 
																						+ ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName()
																						+ " associated with Tracked Nuclei List " 
																						+ sunetComboBox.getSelectedItem()
																						, Cina.elementSynthFrame);
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				initAbundPath = "/var/www/cina_files_dev/PUBLIC/init_abund/" + initAbundPath.split("/")[1];
			}else{
				initAbundPath = "/var/www/cina_files/PUBLIC/init_abund/" + initAbundPath.split("/")[1];
			}
			Cina.elementSynthFrame.elementSynthAbundFileFrame.setText(new String(FileGetter.getFileByPath(initAbundPath)));
		}else if(ae.getSource()==sunetComboBox){
			setDesc(sunetComboBox.getSelectedIndex());
		}
	}
	
	public void setCurrentState(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
	
		sunetComboBox.removeAllItems();
		for(String sunetPath: ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSunetPathArray()){
			sunetComboBox.addItem(sunetPath.split("/")[1]);
		}
		if(!swds.getSunetPath().equals("")){
			sunetComboBox.setSelectedItem(swds.getSunetPath().split("/")[1]);
		}else{
			sunetComboBox.setSelectedIndex(0);
		}
		
		setDesc(sunetComboBox.getSelectedIndex());
		
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
			case DIR_STANDARD:
				topLabel2.setCyanText("");
				break;
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				topLabel2.setCyanText("WARNING: Tracked nuclei must be consistent with the custom thermodynamic profiles selected in a future step.");
				break;
		}
		
	}
	
	private void setDesc(int index){
		sunetDescArea.setText("");
		sunetDescArea.append("Tracked Nuclei List Description: " 
				+ ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSunetDescArray()[index] + "\n\n");
		sunetDescArea.append("Associated Initial Abundances Description: " 
				+ ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getInitAbundDescArray()[index]);
		sunetDescArea.setCaretPosition(0);
	}
	
	public void getCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		swds.setSunetPath(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSunetPathArray()[sunetComboBox.getSelectedIndex()]);
		swds.setInitAbundPath(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getInitAbundPathArray()[sunetComboBox.getSelectedIndex()]);
		if(swds.getSunetPath().contains("R_Process")){
			swds.setAl26Type("STABLE");
		}else{
			swds.setAl26Type("METASTABLE");
		}	
	}	
	
}
