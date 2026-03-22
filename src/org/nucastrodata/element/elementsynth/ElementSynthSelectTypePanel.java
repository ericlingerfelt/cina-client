package org.nucastrodata.element.elementsynth;

import javax.swing.*;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.SimTypeDataStructure;
import java.awt.event.*;
import java.util.ArrayList;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import info.clearthought.layout.*;
import org.nucastrodata.enums.SimCat;
import org.nucastrodata.wizard.gui.WordWrapLabel;

public class ElementSynthSelectTypePanel extends JPanel implements ActionListener{
	
	private JComboBox typeComboBox, catComboBox;
	private JTextArea descTextArea;
	private ElementSynthDataStructure ds;
	private JButton regButton; 
	private WordWrapLabel topLabel, topLabel2;
	private JScrollPane sp;
	private JLabel descLabel, typeLabel, catLabel;
	private ElementSynthFrame parent;
	private JPanel boxPanel;
	private MainDataStructure mds;
	
	public ElementSynthSelectTypePanel(ElementSynthDataStructure ds, ElementSynthFrame parent, MainDataStructure mds){
		
		this.ds = ds;
		this.parent = parent;
		this.mds = mds;
		
		descLabel = new JLabel("Simulation Description:");
		descLabel.setFont(Fonts.textFont);
		
		catLabel = new JLabel("Simulation Category:");
		typeLabel = new JLabel("Simulation Type:");
		
		topLabel = new WordWrapLabel(true);
		topLabel2 = new WordWrapLabel(true);
		
		topLabel.setText("<html>Please select an element synthesis simulation category and then an element synthesis simulation type from the dropdown menus below.</html>");
		topLabel2.setText("<html>Only registered users may run the Element Synthesis Simulator.<br><br>The Element Synthesis Visualizer contains trial simulations for guest users.</html>");
		
		catComboBox = new JComboBox();
		catComboBox.setFont(Fonts.textFont);
		
		typeComboBox = new JComboBox();
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.addActionListener(this);
		
		boxPanel = new JPanel();
		double[] column = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		boxPanel.setLayout(new TableLayout(column, row));
		boxPanel.add(catLabel,     "0, 0, r, c");
		boxPanel.add(catComboBox,  "2, 0, f, c");
		boxPanel.add(typeLabel,    "0, 2, r, c");
		boxPanel.add(typeComboBox, "2, 2, f, c");
		
		regButton = new JButton("REGISTER NOW!");
		regButton.setFont(Fonts.buttonFont);
		regButton.addActionListener(this);
		
		descTextArea = new JTextArea("");
		descTextArea.setLineWrap(true);
		descTextArea.setWrapStyleWord(true);
		descTextArea.setEditable(false);
		
		sp = new JScrollPane(descTextArea);
		validate();
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==regButton){
			Cina.openRegister();
		}else if(ae.getSource()==catComboBox){
			SimCat type = (SimCat)catComboBox.getSelectedItem();
			typeComboBox.removeActionListener(this);
			typeComboBox.removeAllItems();
			parent.setContinueEnabled(true);
			typeComboBox.setEnabled(true);
			ArrayList<SimTypeDataStructure> list = ds.getSimTypeMap().get(type);
			for(SimTypeDataStructure stds: list){
				typeComboBox.addItem(stds);
			}
			typeComboBox.setSelectedIndex(0);
			typeComboBox.addActionListener(this);
			setDesc(type, 0);
		}else if(ae.getSource()==typeComboBox){
			setDesc((SimCat)catComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());
		}
	}

	public void setDesc(SimCat cat, int index){
		descTextArea.setText("");
		SimTypeDataStructure stds = ds.getSimTypeMap().get(cat).get(index);
		descTextArea.append("Element Synthesis Simulation Category: " + cat.toString() + "\n");
		descTextArea.append("Element Synthesis Simulation Type: " + stds.getSimTypeName() + "\n\n");
		descTextArea.append("Element Synthesis Simulation Type Description: " + stds.getDescription() + "\n\n");
		descTextArea.append("Available Tracked Nuclei Lists and Associated Initial Abundances:\n");
		for(int i=0; i<stds.getSunetPathArray().length; i++){
			descTextArea.append(stds.getSunetPathArray()[i] + ": " + stds.getSunetDescArray()[i] + ": " + stds.getInitAbundDescArray()[i]  + "\n");
		}
		descTextArea.setCaretPosition(0);
	}

	public void setCurrentState(){
		removeAll();
		catComboBox.removeAllItems();
		for(SimCat type: SimCat.values()){
			switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
				case SINGLE_STANDARD:
					if(type!=SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case SINGLE_STANDARD_SENS:
					if(type!=SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case SINGLE_CUSTOM:
					if(type==SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case SINGLE_CUSTOM_SENS:
					if(type==SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case DIR_STANDARD:
					if(type!=SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case DIR_CUSTOM_DOUBLE_LOOPING:
					if(type==SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
				case DIR_CUSTOM_SINGLE_LOOPING:
					if(type==SimCat.THERMO){
						catComboBox.addItem(type);
					}
					break;
			}
			
		}
		catComboBox.setSelectedIndex(0);
		catComboBox.addActionListener(this);
		
		SimCat type = (SimCat)catComboBox.getSelectedItem();
		typeComboBox.removeActionListener(this);
		typeComboBox.removeAllItems();
		parent.setContinueEnabled(true);
		typeComboBox.setEnabled(true);
		ArrayList<SimTypeDataStructure> list = ds.getSimTypeMap().get(type);
		for(SimTypeDataStructure stds: list){
			typeComboBox.addItem(stds);
		}
		typeComboBox.setSelectedIndex(0);
		typeComboBox.addActionListener(this);
		setDesc(type, 0);
		
		int gap = 20;
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
			double[] column = {gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, 20, TableLayoutConstants.PREFERRED
								, 20, TableLayoutConstants.PREFERRED
								, 5, TableLayoutConstants.FILL, gap};
			setLayout(new TableLayout(column, row));
			add(topLabel,        "1, 1, c, c");
			add(boxPanel,        "1, 3, c, c");
			add(descLabel,       "1, 5, l, c");
			add(sp,              "1, 7, f, f");
			
			if(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure()!=null){
				catComboBox.setSelectedItem(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimCat());
				typeComboBox.setSelectedItem(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure());
			}
			parent.setContinueEnabled(true);
		}else{
			double[] column = {gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			add(topLabel2, "1, 1, f, c");
			add(regButton, "1, 3, c, c");
			parent.setContinueEnabled(false);
		}
		repaint();
		validate();
	}
	
	public void getCurrentState(){
		ds.getCurrentSimWorkDataStructure().setSimTypeDataStructure(ds.getSimTypeMap().get((SimCat)catComboBox.getSelectedItem()).get(typeComboBox.getSelectedIndex()));
		ds.getCurrentSimWorkDataStructure().setSimType(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName());
	}
}