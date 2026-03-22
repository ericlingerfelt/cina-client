package org.nucastrodata.element.elementsynth;

import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;

import java.awt.GridBagLayout;
import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.element.elementsynth.controller.ElementSynthGetSimWorkflowRunStatusController;
import org.nucastrodata.element.elementsynth.listener.ElementSynthAbortSimWorkflowListener;
import org.nucastrodata.element.elementsynth.worker.ElementSynthAbortSimWorkflowRunWorker;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.*;

public class ElementSynthStatusPanel extends JPanel implements ActionListener, 
																ElementSynthAbortSimWorkflowListener{ 

	public JLabel statusLabel, zoneLabel, factorLabel, libLabel, offlineLabel;
	public JTextArea statusTextArea;
	public JButton abortButton, restartButton, returnButton, offlineButton, okButton;
	private ElementSynthDataStructure ds;
	private JPanel dataPanel;
	private JScrollPane sp;
	private ElementSynthFrame parent;
	private ElementSynthGetSimWorkflowRunStatusController controller;
	private JDialog offlineDialog;
	private JTextField nameField;
	
	public ElementSynthStatusPanel(ElementSynthDataStructure ds, ElementSynthFrame parent){
		
		this.ds = ds;
		this.parent = parent;
		
		controller = new ElementSynthGetSimWorkflowRunStatusController(ds, this, parent);
		
		abortButton = new JButton("Abort Simulation");
		abortButton.setFont(Fonts.buttonFont);
		abortButton.addActionListener(this);
		
		restartButton = new JButton("New Simulation (Step 1)");
		restartButton.setFont(Fonts.buttonFont);
		restartButton.addActionListener(this);
		
		offlineButton = new JButton("Make Available Offline");
		offlineButton.setFont(Fonts.buttonFont);
		offlineButton.addActionListener(this);
		
		statusLabel = new JLabel("Status of Element Synthesis Simulation: ");
		statusLabel.setFont(Fonts.textFont);
		zoneLabel = new JLabel("Current Zone: ");
		factorLabel = new JLabel("Current Scale Factor: ");
		libLabel = new JLabel("Current Rate Library: ");
		offlineLabel = new JLabel("Offline Simulation Name: ");

		statusTextArea = new JTextArea("");
		statusTextArea.setFont(Fonts.textFont);
		statusTextArea.setEditable(false);
		
		sp = new JScrollPane(statusTextArea);

		dataPanel = new JPanel();

	}

	public void setCurrentState(){
	
		ElementSimWorkRunDataStructure swrds = ds.getCurrentSimWorkRunDataStructure();
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
	
		swrds.setPreviousLib("");
		swrds.setPreviousScaleFactor("");
		swrds.setPreviousZone("");
		swrds.setPreviousText("");
		
		swrds.setCurrentLib("");
		swrds.setCurrentScaleFactor("");
		swrds.setCurrentZone("");
		swrds.setCurrentText("");
	
		removeAll();
		dataPanel.removeAll();
	
		if(swrds.getName().equals("")){
		
			int gap = 20;
			double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			switch(swds.getSimWorkflowMode()){
				case SINGLE_STANDARD_SENS:
				case SINGLE_CUSTOM_SENS:
					add(libLabel, "1, 1, c, c");
					add(factorLabel, "3, 1, c, c");
					break;
				case SINGLE_STANDARD:
				case SINGLE_CUSTOM:
				case DIR_STANDARD: 
				case DIR_CUSTOM_DOUBLE_LOOPING:
				case DIR_CUSTOM_SINGLE_LOOPING:
					add(libLabel, "1, 1, c, c");
					add(zoneLabel, "3, 1, c, c");
					break;
			}
			
			if(Cina.cinaMainDataStructure.isMasterUser()){
			
				double[] columnData = {TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED};
				double[] rowData = {TableLayoutConstants.PREFERRED};
				dataPanel.setLayout(new TableLayout(columnData, rowData));
				dataPanel.add(abortButton, "0, 0, f, c");
				dataPanel.add(offlineButton, "2, 0, f, c");
				dataPanel.add(restartButton, "4, 0, f, c");
			
			}else{
			
				double[] columnData = {TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED};
				double[] rowData = {TableLayoutConstants.PREFERRED};
				dataPanel.setLayout(new TableLayout(columnData, rowData));
				dataPanel.add(abortButton, "0, 0, f, c");
				dataPanel.add(restartButton, "2, 0, f, c");
			
			}
			
			add(statusLabel, "1, 3, 3, 3, c, c");
			add(sp, "1, 5, 3, 5, f, f");
			add(dataPanel, "1, 7, 3, 7, c, c");
		
		}else{
		
			int gap = 20;
			double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.FILL
								, gap, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			switch(swds.getSimWorkflowMode()){
				case SINGLE_STANDARD_SENS:
				case SINGLE_CUSTOM_SENS:
					add(libLabel, "1, 1, c, c");
					add(factorLabel, "3, 1, c, c");
					break;
				case SINGLE_STANDARD:
				case SINGLE_CUSTOM:
				case DIR_STANDARD: 
				case DIR_CUSTOM_DOUBLE_LOOPING:
				case DIR_CUSTOM_SINGLE_LOOPING:
					add(libLabel, "1, 1, c, c");
					add(zoneLabel, "3, 1, c, c");
					break;
			}
			
			double[] columnData = {TableLayoutConstants.PREFERRED
					, 10, TableLayoutConstants.PREFERRED};
			double[] rowData = {TableLayoutConstants.PREFERRED};
			dataPanel.setLayout(new TableLayout(columnData, rowData));
			dataPanel.add(abortButton, "0, 0, f, c");
			dataPanel.add(restartButton, "2, 0, f, c");
			
			add(statusLabel, "1, 3, 3, 3, c, c");
			add(offlineLabel, "1, 5, 3, 5, c, c");
			add(sp, "1, 7, 3, 7, f, f");
			add(dataPanel, "1, 9, 3, 9, c, c");
		
		}
		
		factorLabel.setText("Current Scale Factor: Please wait...");
		zoneLabel.setText("Current Zone: Please wait...");
		
		statusTextArea.setText(ds.getStatusText());
		if(ds.getSimDone()){
			abortButton.setEnabled(false);
			offlineButton.setEnabled(false);
		}else{
			offlineButton.setEnabled(true);
			abortButton.setEnabled(true);
		}
		
		offlineLabel.setText("Offline Simulation Name: " + swrds.getName());
		
		validate();
		repaint();
	}
	
	public void getCurrentState(){
		ds.setStatusText(statusTextArea.getText());
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==abortButton){
			abortElementSynthesis();
		}else if(ae.getSource()==restartButton){
			statusTextArea.setText("");	
			parent.restart();
		}else if(ae.getSource()==offlineButton){
			createOfflineDialog(parent);
		}else if(ae.getSource()==okButton){
			if(!nameField.getText().trim().equals("") && Cina.cinaMainDataStructure.isMasterUser()){	
				ds.getCurrentSimWorkRunDataStructure().setName(nameField.getText().trim());
				Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkRunDataStructure(), CGICom.SIM_WORKFLOW_RUN_NAME_EXISTS, Cina.elementSynthFrame);
				if(ds.getCurrentSimWorkRunDataStructure().getNameExists()){
					ds.getCurrentSimWorkRunDataStructure().setName("");
					String string = "The value you entered for offline simulation name is currently in use. "
							+ "This name will be available once this offline simulation has been saved or aborted.";
					AttentionDialog.createDialog(Cina.elementSynthFrame, string);
				}else{
					offlineDialog.setVisible(false);
					offlineDialog.dispose();
					Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkRunDataStructure(), CGICom.NAME_SIM_WORKFLOW_RUN, Cina.elementSynthFrame);
					setCurrentState();
				}
			}
		}
	}
	
	public void abortElementSynthesis(){
		ds.getCurrentSimWorkRunDataStructure().setName("");
		stopElementSynthesisGetSimWorkflowRunStatusController();
		ElementSynthAbortSimWorkflowRunWorker worker = new ElementSynthAbortSimWorkflowRunWorker(ds, parent, this);
		worker.execute();
	}

	public void startElementSynthesisGetSimWorkflowRunStatusController(){
		controller.start();
	}
	
	public void stopElementSynthesisGetSimWorkflowRunStatusController(){
		controller.stop();
	}

	public void updateAfterElementSynthAbortSimWorkflow(){
		ElementSimWorkRunDataStructure swrds = ds.getCurrentSimWorkRunDataStructure();
		swrds.setCurrentStatus(ElementSynthSimWorkflowRunStatus.ABORTED);
		statusLabel.setText("Status of Element Synthesis Simulation: Aborted");
		abortButton.setEnabled(false);
		offlineButton.setEnabled(false);
		restartButton.setEnabled(true);
		parent.setContinueEnabled(false);
		ds.setSimDone(true);
	}

	public void updateAfterGetSimWorkflowStatus(){
	
		ElementSimWorkRunDataStructure swrds = ds.getCurrentSimWorkRunDataStructure();
	
		if(!swrds.getCurrentText().equals("") && !swrds.getCurrentText().equals(swrds.getPreviousText())){	
			statusTextArea.append(swrds.getCurrentText() + "\n");
			statusTextArea.setCaretPosition(statusTextArea.getText().length());
		}
		
		if(!swrds.getCurrentLib().equals("") && !swrds.getCurrentLib().equals(swrds.getPreviousLib())){
			libLabel.setText("Current Rate Library: " + swrds.getCurrentLib());
		}
		
		if(!swrds.getCurrentScaleFactor().equals("") && !swrds.getCurrentScaleFactor().equals(swrds.getPreviousScaleFactor())){
			factorLabel.setText("Current Scale Factor: " + swrds.getCurrentScaleFactor());
		}
		
		if(!swrds.getCurrentZone().equals("") && !swrds.getCurrentZone().equals(swrds.getPreviousZone())){
			zoneLabel.setText("Current Zone: " + swrds.getCurrentZone());
		}
		
		statusLabel.setText("Status of Element Synthesis Simulation: " + swrds.getCurrentStatus().toString());
		
		swrds.setPreviousStatus(swrds.getCurrentStatus());
		swrds.setPreviousLib(swrds.getCurrentLib());
		swrds.setPreviousScaleFactor(swrds.getCurrentScaleFactor());
		swrds.setPreviousZone(swrds.getCurrentZone());
		swrds.setPreviousText(swrds.getCurrentText());
		
		if(swrds.getCurrentStatus()==ElementSynthSimWorkflowRunStatus.COMPLETE){
			stopElementSynthesisGetSimWorkflowRunStatusController();
			parent.setContinueEnabled(true);
			restartButton.setEnabled(true);
			abortButton.setEnabled(false);
			ds.setSimDone(true);
		}else if(swrds.getCurrentStatus()==ElementSynthSimWorkflowRunStatus.ERROR){
			stopElementSynthesisGetSimWorkflowRunStatusController();
			abortButton.setEnabled(false);
			restartButton.setEnabled(true);
			parent.setContinueEnabled(false);
			ds.setSimDone(true);
		}
		
	}
	
	public void createOfflineDialog(JFrame frame){
		  
		offlineDialog = new JDialog(frame, "Make Element Synthesis Simulation Available Offline", true);
		offlineDialog.setSize(480, 210);
		
		offlineDialog.getContentPane().setLayout(new GridBagLayout());
		offlineDialog.setLocationRelativeTo(frame);
		
		nameField = new JTextField();
		
		JLabel nameLabel = new JLabel();
		nameLabel.setFont(Fonts.textFont);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED, gap};
		offlineDialog.getContentPane().setLayout(new TableLayout(column, row));
		JPanel buttonPanel = new JPanel();
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("Enter the name for this Offline Element Synthesis Simulation.");
		nameLabel.setText("Offline Simulation name:");
		
		topLabel.setFont(Fonts.textFont);
	
		offlineDialog.getContentPane().add(topLabel, 				"1, 1, c, c");
		offlineDialog.getContentPane().add(nameLabel, 				"1, 3, l, c");
		offlineDialog.getContentPane().add(nameField, 				"1, 5, f, c");
		offlineDialog.getContentPane().add(buttonPanel, 			"1, 7, c, c");
		
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					offlineDialog.setVisible(false);
					offlineDialog.dispose();
				}
			}
		);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		offlineDialog.setVisible(true);
	}

}
