package org.nucastrodata.element.elementsynth;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import info.clearthought.layout.*;

public class ElementSynthOptionsPanel extends JPanel implements ActionListener{

	private JLabel stopLimitLabel, workflowLabel, workflowValueLabel, timeStepLabel, stopTimeLabel, startTimeLabel;
	private JButton okButton, cancelButton, workflowButton;
	private JTextField timeStepField, stopTimeField, startTimeField, workflowField;
	private JCheckBox weakReactionsBox, screeningBox;
	private JTextArea notesTextArea; 
	private JDialog saveSimWorkDialog;
	private ElementSynthDataStructure ds;
	private WordWrapLabel topLabel;
	private JPanel dataPanel;
	private int gap = 20;
	private boolean simWorkflowSaved;
	
	public ElementSynthOptionsPanel(ElementSynthDataStructure ds){
		
		this.ds = ds;

		topLabel = new WordWrapLabel(true);
		
		timeStepLabel = new JLabel("Number of timesteps before exit:");
		timeStepLabel.setFont(Fonts.textFont);
		stopTimeLabel = new JLabel("Stop time (sec):");
		stopTimeLabel.setFont(Fonts.textFont);	
		stopLimitLabel = new JLabel("");
		stopLimitLabel.setFont(Fonts.textFont);		
		startTimeLabel = new JLabel("Start time (sec):");
		startTimeLabel.setFont(Fonts.textFont);		
		workflowLabel = new JLabel();
		workflowLabel.setFont(Fonts.textFont);		
		workflowValueLabel = new JLabel();
		workflowValueLabel.setFont(Fonts.textFont);		
		
		workflowButton = new JButton("Save Element Synthesis Workflow");
		workflowButton.addActionListener(this);
		
		timeStepField = new JTextField(20);
		timeStepField.setText("5000");		
		stopTimeField = new JTextField(20);
		startTimeField = new JTextField(20);
		workflowField = new JTextField(20);
		
		weakReactionsBox = new JCheckBox("Include weak reactions", true);
		weakReactionsBox.setEnabled(false);
		weakReactionsBox.setFont(Fonts.textFont);
		screeningBox = new JCheckBox("Include screening", true);
		screeningBox.setEnabled(false);
		screeningBox.setFont(Fonts.textFont);

		notesTextArea = new JTextArea("");
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		
		dataPanel = new JPanel();
		
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==workflowButton){
			String string = "Enter the name and notes for this Element Synthesis Workflow.";
			createSaveSimWorkDialog(Cina.elementSynthFrame, string);
		}else if(ae.getSource()==cancelButton){
			simWorkflowSaved = false;
			saveSimWorkDialog.setVisible(false);
			saveSimWorkDialog.dispose();
		}else if(ae.getSource()==okButton){
			saveSimWorkDialog.setVisible(false);
			saveSimWorkDialog.dispose();
			simWorkflowSaved = false;
			if(checkSimWorkDataFields()){
				getCurrentState();
				ds.getCurrentSimWorkDataStructure().setName(workflowField.getText().trim());
				ds.getCurrentSimWorkDataStructure().setNotes(notesTextArea.getText().trim());
				Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkDataStructure(), CGICom.CREATE_SIM_WORKFLOW, Cina.elementSynthFrame);
				simWorkflowSaved = true;
				setCurrentState();
			}
		}
	}
	
	public void setCurrentState(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		screeningBox.setSelected(swds.getIncludeScreening());
		weakReactionsBox.setSelected(swds.getIncludeWeak());
		timeStepField.setText("5000");
		startTimeField.setText(swds.getStartTime());
		stopTimeField.setText(swds.getStopTime());
		notesTextArea.setText(swds.getNotes());
		workflowLabel.setText("Element Synthesis Workflow: ");
		workflowValueLabel.setText(swds.getName());
		
		removeAll();
		dataPanel.removeAll();
		
		if(swds.getName().equals("")){
		
			if(Cina.cinaMainDataStructure.isMasterUser()){
				
				double[] column = {gap, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, 			"1, 1, c, c");
				add(workflowButton, 	"1, 3, c, c");
				add(dataPanel, 			"1, 5, c, c");
				
				topLabel.setText("Enter simulation options below. "
						+ "If you would like to save this Element Synthesis Workflow, click the <i>Save Element Synthesis Workflow</i> "
						+ "and enter a name and notes for the workflow. "
						+ "Click <i>Continue</i> to begin the simulation.");
				workflowField.setText(swds.getName());
				
				double[] columnData = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
				double[] rowData = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
				dataPanel.setLayout(new TableLayout(columnData, rowData));
				dataPanel.add(timeStepLabel, "0, 0, r, c");			
				dataPanel.add(timeStepField, "2, 0, f, c");		
				dataPanel.add(weakReactionsBox, "4, 0, l, c");	
				dataPanel.add(startTimeLabel, "0, 2, r, c");	
				dataPanel.add(startTimeField, "2, 2, f, c");
				dataPanel.add(screeningBox, "4, 2, l, c");	
				dataPanel.add(stopTimeLabel, "0, 4, r, c");		
				dataPanel.add(stopTimeField, "2, 4, f, c");		
				dataPanel.add(stopLimitLabel, "4, 4, l, c");
				
			}else{
			
				topLabel.setText("Enter simulation options below. Click <i>Continue</i> to begin the simulation.");
			
				double[] columnData = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
				double[] rowData = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
				dataPanel.setLayout(new TableLayout(columnData, rowData));
				dataPanel.add(timeStepLabel, "0, 0, r, c");			
				dataPanel.add(timeStepField, "2, 0, f, c");		
				dataPanel.add(weakReactionsBox, "4, 0, l, c");	
				dataPanel.add(startTimeLabel, "0, 2, r, c");	
				dataPanel.add(startTimeField, "2, 2, f, c");
				dataPanel.add(screeningBox, "4, 2, l, c");	
				dataPanel.add(stopTimeLabel, "0, 4, r, c");		
				dataPanel.add(stopTimeField, "2, 4, f, c");		
				dataPanel.add(stopLimitLabel, "4, 4, l, c");
				
				double[] column = {gap, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
									, 30, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, "1, 1, c, c");
				add(dataPanel, "1, 3, c, c");
			
			}
		
		}else{
	
			topLabel.setText("Enter simulation options below. Click <i>Continue</i> to begin the simulation.");
	
			double[] columnData = {TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED};
			double[] rowData = {TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED};
			dataPanel.setLayout(new TableLayout(columnData, rowData));
			dataPanel.add(workflowLabel, 		"0, 0, r, c");			
			dataPanel.add(workflowValueLabel, 	"2, 0, f, c");	
			dataPanel.add(timeStepLabel, 		"0, 2, r, c");			
			dataPanel.add(timeStepField, 		"2, 2, f, c");		
			dataPanel.add(weakReactionsBox, 	"4, 2, l, c");	
			dataPanel.add(startTimeLabel, 		"0, 4, r, c");	
			dataPanel.add(startTimeField, 		"2, 4, f, c");
			dataPanel.add(screeningBox, 		"4, 4, l, c");	
			dataPanel.add(stopTimeLabel,		"0, 6, r, c");		
			dataPanel.add(stopTimeField, 		"2, 6, f, c");		
			dataPanel.add(stopLimitLabel, 		"4, 6, l, c");
			
			double[] column = {gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, 30, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			add(topLabel, "1, 1, c, c");
			add(dataPanel, "1, 3, c, c");
			
		}

	}
	
	public void getCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		swds.setIncludeScreening(screeningBox.isSelected());
		swds.setIncludeWeak(weakReactionsBox.isSelected());
		swds.setMaxTimesteps(timeStepField.getText());
		swds.setStartTime(startTimeField.getText());
		swds.setStopTime(stopTimeField.getText());
		ds.getCurrentSimWorkRunDataStructure().setName("");
	}
	
	protected boolean checkSimWorkDataFields(){

		if(workflowField.getText().trim().equals("")){
		
			String string = "Please enter a value for element synthesis workflow name.";
			AttentionDialog.createDialog(Cina.elementSynthFrame, string);
			return false;
		
		}
		
		if(!workflowField.getText().trim().equals("")){
		
			ds.getCurrentSimWorkDataStructure().setName(workflowField.getText().trim());
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkDataStructure(), CGICom.SIM_WORKFLOW_IN_USE, Cina.elementSynthFrame);
			if(ds.getCurrentSimWorkDataStructure().getInUse()){
				String string = "The value you entered for Element Synthesis Workflow name is currently in use by an offline simulation. "
						+ "Please select another name for this workflow.";
				AttentionDialog.createDialog(Cina.elementSynthFrame, string);
				return false;
			}
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkDataStructure(), CGICom.SIM_WORKFLOW_NAME_EXISTS, Cina.elementSynthFrame);
			if(ds.getCurrentSimWorkDataStructure().getNameExists()){
				String string = "The value you entered for Element Synthesis Workflow name currently exists. "
						+ "Please select another value for Element Synthesis Workflow name.";
				AttentionDialog.createDialog(Cina.elementSynthFrame, string);
				return false;
			}
		
		}
		
		return true;

	}
	
	public boolean isSimWorkModified(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		if(swds.getName().equals("")){
			return false;
		}
		ds.setCompareSimWorkDataStructure(new ElementSimWorkDataStructure());
		ds.getCompareSimWorkDataStructure().setIndex(swds.getIndex());
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCompareSimWorkDataStructure(), CGICom.GET_SIM_WORKFLOW_INFO, Cina.elementSynthFrame);
		if(ds.getCurrentSimWorkDataStructure().compareTo(ds.getCompareSimWorkDataStructure()) == 0){
			return false;
		}else{
			String string = "You have modified the Element Synthesis Workflow currently loaded. "
					+ "You must save this workflow as a new Element Synthesis Workflow in order to continue.";
			createSaveSimWorkDialog(Cina.elementSynthFrame, string);
			return !simWorkflowSaved;
		}
	}
	
	public void createSaveSimWorkDialog(JFrame frame, String string){
		  
		saveSimWorkDialog = new JDialog(frame, "Save Element Synthesis Workflow", true);
		saveSimWorkDialog.setSize(480, 410);
		
		saveSimWorkDialog.getContentPane().setLayout(new GridBagLayout());
    	saveSimWorkDialog.setLocationRelativeTo(frame);
		
    	JLabel notesLabel = new JLabel("Element Synthesis Workflow notes:");
		notesLabel.setFont(Fonts.textFont);
		JLabel workflowLabel = new JLabel("Element Synthesis Workflow name:");
		workflowLabel.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(notesTextArea);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.FILL
						, gap, TableLayoutConstants.PREFERRED, gap};
		saveSimWorkDialog.getContentPane().setLayout(new TableLayout(column, row));
		JPanel buttonPanel = new JPanel();
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText(string);
		topLabel.setFont(Fonts.textFont);
	
		saveSimWorkDialog.getContentPane().add(topLabel, 				"1, 1, c, c");
		saveSimWorkDialog.getContentPane().add(workflowLabel,           "1, 3, l, c");
		saveSimWorkDialog.getContentPane().add(workflowField, 	        "1, 5, f, c");
		saveSimWorkDialog.getContentPane().add(notesLabel, 			    "1, 7, l, c");
		saveSimWorkDialog.getContentPane().add(sp, 		    			"1, 9, f, f");
		saveSimWorkDialog.getContentPane().add(buttonPanel, 			"1, 11, c, c");
		
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(this);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		saveSimWorkDialog.setVisible(true);
	}
}