package org.nucastrodata.element.elementsynth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetSimWorkflowRunsListener;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetSimWorkflowsListener;
import org.nucastrodata.element.elementsynth.worker.ElementSynthGetSimWorkflowRunsWorker;
import org.nucastrodata.element.elementsynth.worker.ElementSynthGetSimWorkflowsWorker;
import org.nucastrodata.enums.SimWorkflowMode;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementSynthWorkflowPanel extends JPanel implements ActionListener, 
																	ElementSynthGetSimWorkflowRunsListener, 
																	ElementSynthGetSimWorkflowsListener{

	private WordWrapLabel topLabel, topLabel2, topLabel3;
	private MainDataStructure mds;
	private ElementSynthDataStructure ds;
	
	private JComboBox<String> loopingBox;
	private JRadioButton singleButton, dirButton;
	private JRadioButton standardButton, customButton, standardButton2, customButton2;
	private JRadioButton performSensButton, performSingleButton;
	private JCheckBox sensBox;
	private JPanel libDirPanel, thermoPanel, thermoPanel2, sensPanel, sensPanel2, loopingPanel, simWorkPanel;
	private JLabel libDirLabel, thermolabel, thermoLabel2, sensLabel2, loopingLabel;
	private JButton loadSimWorkButton, loadSimWorkRunButton;
	private int gap = 20;
	
	public ElementSynthWorkflowPanel(ElementSynthDataStructure ds, MainDataStructure mds){
		
		this.ds = ds;
		this.mds = mds;
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("This step allows you to either");
		
		topLabel3 = new WordWrapLabel(true);
		topLabel3.setText("(1) Create a new workflow using the options below, "
				+ "<br>(2) Load an existing workflow by clicking <i>Load Element Synthesis Workflow</i>, or "
				+ "<br>(3) Load an offline simulation running in the background by clicking <i>Load Offline Element Synthesis Simulation</i>.");
		
		topLabel2 = new WordWrapLabel(true);
		topLabel2.setText("Please choose between a single simulation or a series of simulations (called a sensitivity study).");
		
		loopingBox = new JComboBox<String>();
		loopingBox.addItem("Double Looping Over a Rate Library Directory and a Custom Thermo Profile Set");
		loopingBox.addItem("Single Looping Over a Rate Library Directory and a Custom Thermo Profile Set");
		
		libDirLabel = new JLabel("Reaction Rate Library Option:");
		thermolabel = new JLabel("Thermodynamic Profile Option:");
		thermoLabel2 = new JLabel("Thermodynamic Profile Option:");
		sensLabel2 = new JLabel("Element Synthesis Simulation Option:");
		loopingLabel = new JLabel("Rate Library Directory Looping Option:");
		
		sensBox = new JCheckBox("Perform Sensitivity Study", false);
		
		singleButton = new JRadioButton("Select a Single Rate Library", true);
		singleButton.addActionListener(this);
		
		dirButton = new JRadioButton("Select a Directory of Rate Libraries", false);
		dirButton.addActionListener(this);
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(singleButton);
		group1.add(dirButton);
		
		standardButton = new JRadioButton("Select Standard Thermodynamic Profiles", true);
		standardButton.addActionListener(this);
		customButton = new JRadioButton("Select Custom Thermodynamic Profiles", false);
		customButton.addActionListener(this);
		
		standardButton2 = new JRadioButton("Select Standard Thermodynamic Profiles", true);
		standardButton2.addActionListener(this);
		customButton2 = new JRadioButton("Select Custom Thermodynamic Profiles", false);
		customButton2.addActionListener(this);
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(standardButton);
		group2.add(customButton);
		
		ButtonGroup group3 = new ButtonGroup();
		group3.add(standardButton2);
		group3.add(customButton2);
		
		performSensButton = new JRadioButton("Perform Sensitivity Study", false);
		performSingleButton = new JRadioButton("Perform Single Simulation", true);
		
		ButtonGroup group4 = new ButtonGroup();
		group4.add(performSingleButton);
		group4.add(performSensButton);
		
		libDirPanel = new JPanel();
		double[] columnLibDir = {TableLayoutConstants.PREFERRED};
		double[] rowLibDir = {TableLayoutConstants.PREFERRED, 20, 
								TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		libDirPanel.setLayout(new TableLayout(columnLibDir, rowLibDir));
		libDirPanel.add(libDirLabel,     	"0, 0, l, c");
		libDirPanel.add(singleButton,     	"0, 2, l, c");
		libDirPanel.add(dirButton,  	  	"0, 4, l, c");
		
		thermoPanel = new JPanel();
		double[] columnThermo = {TableLayoutConstants.PREFERRED};
		double[] rowThermo = {TableLayoutConstants.PREFERRED, 20, 
								TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		thermoPanel.setLayout(new TableLayout(columnThermo, rowThermo));
		thermoPanel.add(thermolabel,     	"0, 0, l, c");
		thermoPanel.add(standardButton,     "0, 2, l, c");
		thermoPanel.add(customButton,  	  	"0, 4, l, c");
		
		thermoPanel2 = new JPanel();
		double[] columnThermo2 = {TableLayoutConstants.PREFERRED};
		double[] rowThermo2 = {TableLayoutConstants.PREFERRED, 20, 
								TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		thermoPanel2.setLayout(new TableLayout(columnThermo2, rowThermo2));
		thermoPanel2.add(thermoLabel2,     	 "0, 0, l, c");
		thermoPanel2.add(standardButton2,    "0, 2, l, c");
		thermoPanel2.add(customButton2,  	 "0, 4, l, c");
		
		sensPanel2 = new JPanel();
		double[] columnSens2 = {TableLayoutConstants.PREFERRED};
		double[] rowSens2 = {TableLayoutConstants.PREFERRED, 20, 
								TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		sensPanel2.setLayout(new TableLayout(columnSens2, rowSens2));
		sensPanel2.add(sensLabel2,     			"0, 0, l, c");
		sensPanel2.add(performSingleButton,     	"0, 2, l, c");
		sensPanel2.add(performSensButton,  	  	"0, 4, l, c");
		
		loopingPanel = new JPanel();
		double[] columnLooping = {TableLayoutConstants.FILL};
		double[] rowLooping = {TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		loopingPanel.setLayout(new TableLayout(columnLooping, rowLooping));
		loopingPanel.add(loopingLabel,     		"0, 0, l, c");
		loopingPanel.add(loopingBox,  	  		"0, 2, f, c");
		
		sensPanel = new JPanel();
		double[] columnSens = {TableLayoutConstants.FILL};
		double[] rowSens = {TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		sensPanel.setLayout(new TableLayout(columnSens, rowSens));
		sensPanel.add(sensBox,     			"0, 0, l, c");
		sensPanel.add(new JLabel(""),  	 	"0, 2, f, c");
		
		loadSimWorkButton = new JButton("Load Element Synthesis Workflow");
		loadSimWorkButton.addActionListener(this);
		
		loadSimWorkRunButton = new JButton("Load Offline Element Synthesis Simulation");
		loadSimWorkRunButton.addActionListener(this);
		
		simWorkPanel = new JPanel();
		double[] columnSimWork = {TableLayoutConstants.FILL};
		double[] rowSimWork = {TableLayoutConstants.PREFERRED, 20, 
								TableLayoutConstants.PREFERRED};
		simWorkPanel.setLayout(new TableLayout(columnSimWork, rowSimWork));
		simWorkPanel.add(loadSimWorkButton,  "0, 0, f, c");
		simWorkPanel.add(loadSimWorkRunButton,  "0, 2, f, c");
		
	}
	
	public void getCurrentState(){
		
		ElementSimWorkDataStructure eswds = ds.getCurrentSimWorkDataStructure();
		
		if(mds.isMasterUser()){
	
			ds.setUseOneToOneLooping(loopingBox.getSelectedIndex()==1);
			ds.setUseSensStudy(sensBox.isSelected());
			ds.setUseLibDir(dirButton.isSelected());
			ds.setUseCustomThermo(customButton.isSelected());
			
			if(!ds.getUseLibDir() && !ds.getUseCustomThermo() && !ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_STANDARD);
			}else if(!ds.getUseLibDir() && !ds.getUseCustomThermo() && ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_STANDARD_SENS);
			}else if(!ds.getUseLibDir() && ds.getUseCustomThermo() && !ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_CUSTOM);
			}else if(!ds.getUseLibDir() && ds.getUseCustomThermo() && ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_CUSTOM_SENS);
			}else if(ds.getUseLibDir() && !ds.getUseCustomThermo()){
				eswds.setSimWorkflowMode(SimWorkflowMode.DIR_STANDARD);
			}else if(ds.getUseLibDir() && ds.getUseCustomThermo() && !ds.getUseOneToOneLooping()){
				eswds.setSimWorkflowMode(SimWorkflowMode.DIR_CUSTOM_DOUBLE_LOOPING);
			}else if(ds.getUseLibDir() && ds.getUseCustomThermo() && ds.getUseOneToOneLooping()){
				eswds.setSimWorkflowMode(SimWorkflowMode.DIR_CUSTOM_SINGLE_LOOPING);
			}
		
		}else{
		
			ds.setUseSensStudy(performSensButton.isSelected());
			ds.setUseCustomThermo(customButton2.isSelected());
			
			if(!ds.getUseCustomThermo() && !ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_STANDARD);
			}else if(!ds.getUseCustomThermo() && ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_STANDARD_SENS);
			}else if(ds.getUseCustomThermo() && !ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_CUSTOM);
			}else if(ds.getUseCustomThermo() && ds.getUseSensStudy()){
				eswds.setSimWorkflowMode(SimWorkflowMode.SINGLE_CUSTOM_SENS);
			}
			
		}
	}
	
	public void setCurrentState(){
		
		removeAll();
		
		if(mds.isMasterUser()){
		
			loopingBox.setSelectedIndex(ds.getUseOneToOneLooping() ? 1 : 0);
			
			singleButton.setSelected(!ds.getUseLibDir());
			dirButton.setSelected(ds.getUseLibDir());
			
			standardButton.setSelected(!ds.getUseCustomThermo());
			customButton.setSelected(ds.getUseCustomThermo());
			
			sensBox.setSelected(ds.getUseSensStudy());
			
			if(ds.getUseLibDir()){
				sensBox.setSelected(false);
			}
		
			double[] column = {gap, TableLayoutConstants.FILL, 50, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 40, TableLayoutConstants.PREFERRED
								, 40, TableLayoutConstants.PREFERRED
								, 30, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			add(topLabel, 		"1, 1, 3, 1, c, c");
			add(topLabel3, 		"1, 3, 3, 3, c, c");
			add(libDirPanel, 	"1, 5, c, c");
			add(thermoPanel, 	"3, 5, c, c");
			
			if(ds.getUseLibDir() && ds.getUseCustomThermo()){
				add(loopingPanel, 	"1, 7, 3, 7, c, c");
			}else if(ds.getUseLibDir()){
				//ADD NOTHING
			}else{
				add(sensPanel, 	"1, 7, 3, 7, c, c");
			}
			
			add(simWorkPanel, 	"1, 9, 3, 9, c, c");
			
		}else{
		
			performSingleButton.setSelected(!ds.getUseSensStudy());
			performSensButton.setSelected(ds.getUseSensStudy());
			
			standardButton2.setSelected(!ds.getUseCustomThermo());
			customButton2.setSelected(ds.getUseCustomThermo());
			
			double[] column = {gap, TableLayoutConstants.FILL, 50, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, 40, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			add(topLabel2, 		"1, 1, 3, 1, c, c");
			add(sensPanel2, 	"1, 3, c, c");
			add(thermoPanel2, 	"3, 3, c, c");
			
		}
		
		revalidate();
		validate();
		repaint();
		
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==dirButton 
				|| ae.getSource()==singleButton 
				|| ae.getSource()==customButton 
				|| ae.getSource()==standardButton){
		
			removeAll();
			
			if(singleButton.isSelected()){
				double[] column = {gap, TableLayoutConstants.FILL, 50, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED
									, 40, TableLayoutConstants.PREFERRED
									, 40, TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, 		"1, 1, 3, 1, c, c");
				add(topLabel3, 		"1, 3, 3, 3, c, c");
				add(libDirPanel, 	"1, 5, c, c");
				add(thermoPanel, 	"3, 5, c, c");
				add(sensPanel, 		"1, 7, 3, 7, c, c");
				add(simWorkPanel, 		"1, 9, 3, 9, c, c");
				sensBox.setEnabled(true);
				sensBox.setSelected(false);
			}else if(dirButton.isSelected() && customButton.isSelected()){
				double[] column = {gap, TableLayoutConstants.FILL, 50, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED
									, 40, TableLayoutConstants.PREFERRED
									, 40, TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, 		"1, 1, 3, 1, c, c");
				add(topLabel3, 		"1, 3, 3, 3, c, c");
				add(libDirPanel, 	"1, 5, c, c");
				add(thermoPanel, 	"3, 5, c, c");
				add(loopingPanel, 	"1, 7, 3, 7, c, c");
				add(simWorkPanel, 	"1, 9, 3, 9, c, c");
				sensBox.setEnabled(false);
				sensBox.setSelected(false);
			}else if(dirButton.isSelected()){
				double[] column = {gap, TableLayoutConstants.FILL, 50, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
									, 10, TableLayoutConstants.PREFERRED
									, 40, TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, 		"1, 1, 3, 1, c, c");
				add(topLabel3, 		"1, 3, 3, 3, c, c");
				add(libDirPanel, 	"1, 5, c, c");
				add(thermoPanel, 	"3, 5, c, c");
				add(simWorkPanel, 	"1, 7, 3, 7, c, c");
				sensBox.setEnabled(false);
				sensBox.setSelected(false);
			}
			
			validate();
			repaint();
			
		}else if(ae.getSource()==loadSimWorkButton){
		
			ElementSynthGetSimWorkflowsWorker worker = new ElementSynthGetSimWorkflowsWorker(ds, Cina.elementSynthFrame, this);
			worker.execute();
		
		}else if(ae.getSource()==loadSimWorkRunButton){
			
			ElementSynthGetSimWorkflowRunsWorker worker = new ElementSynthGetSimWorkflowRunsWorker(ds, Cina.elementSynthFrame, this);
			worker.execute();
			
		}
		
	}

	private void setWorkflowFlags(){
	
		ds.setUseOneToOneLooping(false);
		ds.setUseLibDir(false);
		ds.setUseCustomThermo(false);
		ds.setUseSensStudy(false);
	
		ElementSimWorkDataStructure eswds = ds.getCurrentSimWorkDataStructure();
	
		switch(eswds.getSimWorkflowMode()){
		
			case SINGLE_STANDARD_SENS:
				ds.setUseSensStudy(true);
				break;
			case SINGLE_CUSTOM_SENS:
				ds.setUseCustomThermo(true);
				ds.setUseSensStudy(true);
				break;
			case SINGLE_STANDARD:
				break;
			case SINGLE_CUSTOM:
				ds.setUseCustomThermo(true);
				break;
			case DIR_STANDARD:
				ds.setUseLibDir(true); 
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				ds.setUseCustomThermo(true);
				ds.setUseLibDir(true); 
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				ds.setUseCustomThermo(true);
				ds.setUseLibDir(true); 
				break;
		
		}
	
	
	}

	public void updateAfterElementSynthGetSimWorkflows(){
		
		if(ds.getSimWorkMap() == null || ds.getSimWorkMap().size()==0){
			AttentionDialog.createDialog(Cina.elementSynthFrame, "There are currently no pre-existing element synthesis workflows to load.");
		}else{
			ElementSimWorkDataStructure eswds = ElementSynthSelectSimWorkDialog.createElementSynthSelectSimWorkflowDialog(Cina.elementSynthFrame, ds);
			if(eswds != null){
				ds.setCurrentSimWorkDataStructure(eswds);
				setWorkflowFlags();
				AttentionDialog.createDialog(Cina.elementSynthFrame, "You will now be forwarded to the Simulation Options step.");
				Cina.elementSynthFrame.gotoOptionsPanelFromWorkflowPanel();
			}
		}
		
	}

	public void updateAfterElementSynthGetSimWorkflowRuns(){
	
		if(ds.getSimWorkRunMap() == null || ds.getSimWorkRunMap().size()==0){
			AttentionDialog.createDialog(Cina.elementSynthFrame, "There are currently no offline element synthesis simulations to load.");
		}else{
			ElementSimWorkRunDataStructure eswrds = ElementSynthSelectSimWorkRunDialog.createElementSynthSelectSimWorkRunDialog(Cina.elementSynthFrame, ds);
			if(eswrds != null){
				ds.setCurrentSimWorkRunDataStructure(eswrds);
				ds.setCurrentSimWorkDataStructure(eswrds.getSimWorkDataStructure());
				setWorkflowFlags();
				AttentionDialog.createDialog(Cina.elementSynthFrame, "You will now be forwarded to the Simulation Status step.");
				Cina.elementSynthFrame.gotoStatusPanelFromWorkflowPanel();
			}
		}
		
	}
	
}
