package org.nucastrodata.element.elementsynth;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.html.*;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.print.PrintableEditorPane;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.dialogs.AttentionDialog;
import info.clearthought.layout.*;
import org.nucastrodata.element.elementsynth.listener.ElementSynthSaveSimWorkflowRunListener;
import org.nucastrodata.element.elementsynth.worker.ElementSynthSaveSimWorkflowRunWorker;
import org.nucastrodata.enums.FolderType;

public class ElementSynthResultsPanel extends JPanel implements ActionListener, ElementSynthSaveSimWorkflowRunListener{

	private WordWrapLabel topLabel;
	private JButton saveButton, okButton, cancelButton, yesButton, noButton;
	private JDialog saveNucSimSetDialog, nucSimSetSavedDialog, nucSimSetExistsDialog;
	private JTextField newNucSimSetField;
	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	private PrintableEditorPane textPane;
	private JCheckBox box;
	private JScrollPane sp;
	private JPanel buttonPanel;
	private MainDataStructure mds;
	private JTextArea notesTextArea; 
	
	public ElementSynthResultsPanel(ElementSynthDataStructure ds, 
											MainDataStructure mds, 
											ElementSynthFrame parent){
		
		this.ds = ds;
		this.mds = mds;
		this.parent = parent;
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		
		box = new JCheckBox("View Scaled Rate Parameters", false);
		box.setFont(Fonts.buttonFont);
		box.addActionListener(this);
		
		sp = new JScrollPane(textPane);
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Element Synthesis Simulation Report</html>");
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);
		
		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

		saveButton = new JButton("Save Simulation");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);

	}

	public void setCurrentState(){
		setCurrentState(box.isSelected());
		saveButton.setEnabled(!ds.getSimSaved());
	}

	private boolean goodNucSimName(){
		String string = newNucSimSetField.getText().trim();
		if(newNucSimSetField.getText().equals("")){
			return false;
		}
		return !string.startsWith(".") && string.matches("[._a-zA-Z0-9]+");
	}
	
	public void updateAfterElementSynthSaveSimWorkflowRun(){
		ds.setSimSaved(true);
		saveButton.setEnabled(false);
		Vector<String> vector = new Vector<String>();
		ds.setNucSimVector(vector);
		
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		
		switch(swds.getSimWorkflowMode()){
		
			case SINGLE_STANDARD:
			case SINGLE_CUSTOM:
				if(swds.getZones().indexOf(",")!=-1){
					String[] array = swds.getZones().split(",");
					for(String str: array){
						int zone = Integer.parseInt(str);
						vector.add(newNucSimSetField.getText() 
								+ " (zone_" 
								+ String.format("%1$02d", zone) 
								+ ")");
					}
				}else{
					int zone = Integer.parseInt(swds.getZones());
					vector.add(newNucSimSetField.getText() 
							+ " (zone_" 
							+ String.format("%1$02d", zone) 
							+ ")");
				}
				break;
				
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
			
				String[] scaleArray = swds.getScaleFactors().split(",");
			
				for(int i=0; i<scaleArray.length; i++){
					int zone = Integer.parseInt(swds.getZones());
					vector.add(newNucSimSetField.getText() 
							+ "_"
							+ (i+1) 
							+ " (zone_" 
							+ String.format("%1$02d", zone) 
							+ ")");
				}
				
			break;
			
			case DIR_STANDARD: 
			case DIR_CUSTOM_DOUBLE_LOOPING:
			
				ArrayList<String> libList = ds.getLibDirLibList();
				Iterator<String> itr = libList.iterator();
				while(itr.hasNext()){
					String libName = itr.next();
					if(swds.getZones().indexOf(",")!=-1){
						String[] array = swds.getZones().split(",");
						for(String str: array){
							int zone = Integer.parseInt(str);
							vector.add(newNucSimSetField.getText() + "_" + libName
									+ " (zone_" 
									+ String.format("%1$02d", zone) 
									+ ")");
						}
					}else{
						int zone = Integer.parseInt(swds.getZones());
						vector.add(newNucSimSetField.getText() + "_" + libName 
								+ " (zone_" 
								+ String.format("%1$02d", zone) 
								+ ")");
					}
				}
				
				
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
			
				ArrayList<String> libList2 = ds.getLibDirLibList();
				Iterator<String> itr2 = libList2.iterator();
				for(int i=0; i<ds.getNumSingleLoopingSims(); i++){
					String libName = itr2.next();
					vector.add(newNucSimSetField.getText() + "_" + libName 
							+ " (zone_" 
							+ String.format("%1$02d", (i+1)) 
							+ ")");
				}
				
				break;
			
		}
		
		createNucSimSetSavedDialog(ds.getSaveSimWorkflowRunMessage(), parent);
	}
	
	private void saveSimWorkflowRun(){
	
		if(goodNucSimName()){
			ds.setFolderType(FolderType.USER);
			ds.setName(newNucSimSetField.getText().trim());
			ds.setNotes(notesTextArea.getText().trim());
			if(Cina.cgiCom.doCGICall(mds, ds, CGICom.GET_SIMS, parent)){
				boolean simExists = false;
				switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
					case SINGLE_STANDARD:
						simExists = nucSimSetExists();
						break;
					case SINGLE_STANDARD_SENS:
						simExists = nucSensSimSetExists();
						break;
					case SINGLE_CUSTOM:
						simExists = nucSimSetExists();
						break;
					case SINGLE_CUSTOM_SENS:
						simExists = nucSensSimSetExists();
						break;
					case DIR_STANDARD:
						simExists = nucSimSetLibDirExists();
						break;
					case DIR_CUSTOM_DOUBLE_LOOPING:
						simExists = nucSimSetLibDirExists();
						break;
					case DIR_CUSTOM_SINGLE_LOOPING:
						simExists = nucSimSetLibDirExists();
						break;
					
				}
				if(simExists){
					String string = "This simulation already exists. Do you want to overwrite " + newNucSimSetField.getText() + "?";
					createNucSimSetExistsDialog(string, parent);
				}else{
					ElementSynthSaveSimWorkflowRunWorker worker = new ElementSynthSaveSimWorkflowRunWorker(ds, parent, this);
					worker.execute();
				}
			}
		}else{
			String string = "Simulation names may only contain letters, numbers, underscore, and period. "
								+ "Also, the first character of a simulation name can not be a period.";
			AttentionDialog.createDialog(parent, string);
		}
	}
	
	public boolean nucSensSimSetExists(){
		boolean nucSensSimSetExists = false;
		for(int i=0; i<ds.getNumberUserNucSimSetDataStructures(); i++){
			if(ds.getUserNucSimSetDataStructureArray()[i].getPath().equals("/USER/" + newNucSimSetField.getText() + "_1")){nucSensSimSetExists = true;}
		}
		return nucSensSimSetExists;
	}
	
	public boolean nucSimSetExists(){
		boolean nucSimSetExists = false;
		for(int i=0; i<ds.getNumberUserNucSimSetDataStructures(); i++){
			if(ds.getUserNucSimSetDataStructureArray()[i].getPath().equals("/USER/" + newNucSimSetField.getText())){nucSimSetExists = true;}
		}
		return nucSimSetExists;
	}
	
	public boolean nucSimSetLibDirExists(){
		boolean nucSimSetLibDirExists = false;
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		for(int i=0; i<ds.getNumberUserNucSimSetDataStructures(); i++){
			if(ds.getUserNucSimSetDataStructureArray()[i].getPath().startsWith(("/USER/" + newNucSimSetField.getText() + "_" + swds.getLibDir() + "_"))){nucSimSetLibDirExists = true;}
		}
		return nucSimSetLibDirExists;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButton){
			createSaveNucSimSetDialog(parent);
		}else if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), this, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), this, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
		}else if(ae.getSource()==printButton){
			textPane.print();
		}else if(ae.getSource()==box){
			setCurrentState(box.isSelected());
		}else if(ae.getSource()==okButton){
			saveNucSimSetDialog.setVisible(false);
			saveNucSimSetDialog.dispose();
			saveSimWorkflowRun();
		}else if(ae.getSource()==yesButton){
			nucSimSetExistsDialog.setVisible(false);
			nucSimSetExistsDialog.dispose();
			ElementSynthSaveSimWorkflowRunWorker worker = new ElementSynthSaveSimWorkflowRunWorker(ds, parent, this);
			worker.execute();
		}
	}
	
	private String getTextString(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
	
		String string = "";
		string += "Element Synthesis Simulation Type\t" + swds.getSimType() + "\n";
		switch(swds.getSimWorkflowMode()){
			case SINGLE_CUSTOM_SENS:
			case SINGLE_CUSTOM:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "Thermo Profile Set\t" + swds.getThermoPath() + "\n";
				break;
			default:
				break;
		}
		string += "Number of Timesteps Before Exit\t" + swds.getMaxTimesteps() + "\n";
		string += "Start Time (sec)\t" + swds.getStartTime() + "\n";
		string += "Stop Time (sec)\t" + swds.getStopTime() + "\n";
		string += "Include Weak Reactions\t" + (swds.getIncludeWeak() ? "Yes" : "No") + "\n";
		string += "Include Screening\t" + (swds.getIncludeScreening() ? "Yes" : "No") + "\n";
		switch(swds.getSimWorkflowMode()){
			case DIR_CUSTOM_SINGLE_LOOPING:
				String tmpString = "";
				for(int i=0; i<ds.getNumSingleLoopingSims(); i++){
					if(i<(ds.getNumSingleLoopingSims()-1)){
						tmpString += (i+1) + ",";
					}else{
						tmpString += (i+1);
					}
				}
				string += "Selected Zones\t" + tmpString + "\n";
				break;
			case DIR_STANDARD:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
				string += "Selected Zones\t" + swds.getZones() + "\n";
				break;
		}
		
		string += "26Al State\t" 
				+ (ds.getType().equals("STABLE") ? "only stable": "metastable + ground")  + "\n";
		string += "Initial Abundance Profile\t" + "PUBLIC/" + swds.getInitAbundPath().substring(swds.getInitAbundPath().lastIndexOf("/")+1) + "\n";
		string += "Sunet File\t" 
				+ "PUBLIC/" + swds.getSunetPath().substring(swds.getSunetPath().lastIndexOf("/")+1) 
				+ "\n";
		
		switch(swds.getSimWorkflowMode()){
			case DIR_CUSTOM_DOUBLE_LOOPING:
				string += "Reaction Rate Library Directory\t" + swds.getLibDir() + "\n";
				string += "Looping Type\tDouble Looping\n";
				break;
			case DIR_STANDARD:
				string += "Reaction Rate Library Directory\t" + swds.getLibDir() + "\n";
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "Reaction Rate Library Directory\t" + swds.getLibDir() + "\n";
				string += "Looping Type\tSingle Looping\n";
				break;
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
				string += "Reaction Rate Library\t" + swds.getLibGroup() + "/" + swds.getLibrary() + "\n";
				break;
		}
		
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
				string += "Selected Reaction Rate\t" + swds.getReactionRate() + "\n";
				if(box.isSelected()){
					String[] array = swds.getScaleFactors().split(",");
					for(String factor: array){
						string += "Scale Factor" + factor + "\n";
						string += "Rate Parameters for Scale Factor = " + factor + "\n" + getFormattedScaledParameters(Double.valueOf(factor), false) + "\n";
					}
				}else{
					string += "Scale Factors\t" + swds.getScaleFactors() + "\n";
				}
				break;
			default:
				break;
		}
		return string;
	}
	
	public void setCurrentState(boolean fullReport){
		removeAll();
		
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
				double gap = 20;
				double[] column = {gap, TableLayoutConstants.FILL, gap};
				double[] row = {gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, gap};
				setLayout(new TableLayout(column, row));
				add(topLabel, "1, 1, c, c");
				add(sp, "1, 3, f, f");
				add(box, "1, 5, l, f");
				add(buttonPanel, "1, 7, f, c");
				add(saveButton, "1, 9, c, c");
				break;
			case SINGLE_STANDARD:
			case SINGLE_CUSTOM:
			case DIR_STANDARD: 
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				double gap1 = 20;
				double[] column1 = {gap1, TableLayoutConstants.FILL, gap1};
				double[] row1 = {gap1, TableLayoutConstants.PREFERRED
								, gap1, TableLayoutConstants.FILL
								, 10, TableLayoutConstants.PREFERRED
								, gap1, TableLayoutConstants.PREFERRED, gap1};
				setLayout(new TableLayout(column1, row1));
				add(topLabel, "1, 1, c, c");
				add(sp, "1, 3, f, f");
				add(buttonPanel, "1, 5, f, c");
				add(saveButton, "1, 7, c, c");
				break;
		}
		validate();
		repaint();
		
		String string = "";
		string += "<html><body><table border=\"1\">";
		string += "<tr><td><b>Element Synthesis Simulation Type</b></td><td>" + swds.getSimType() + "</td></tr>";
		switch(swds.getSimWorkflowMode()){
			case SINGLE_CUSTOM_SENS:
			case SINGLE_CUSTOM:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "<tr><td><b>Thermo Profile Set</b></td><td>" + swds.getThermoPath() + "</td></tr>";
				break;
			default:
				break;
		}
		string += "<tr><td><b>Number of Timesteps Before Exit</b></td><td>" + swds.getMaxTimesteps() + "</td></tr>";
		string += "<tr><td><b>Start Time (sec)</b></td><td>" + swds.getStartTime() + "</td></tr>";
		string += "<tr><td><b>Stop Time (sec)</b></td><td>" + swds.getStopTime() + "</td></tr>";
		string += "<tr><td><b>Include Weak Reactions</b></td><td>" + (swds.getIncludeWeak() ? "Yes" : "No") + "</td></tr>";
		string += "<tr><td><b>Include Screening</b></td><td>" + (swds.getIncludeScreening() ? "Yes" : "No") + "</td></tr>";
		switch(swds.getSimWorkflowMode()){
			case DIR_CUSTOM_SINGLE_LOOPING:
				String tmpString = "";
				for(int i=0; i<ds.getNumSingleLoopingSims(); i++){
					if(i<(ds.getNumSingleLoopingSims()-1)){
						tmpString += (i+1) + ",";
					}else{
						tmpString += (i+1);
					}
				}
				string += "<tr><td><b>Selected Zones</b></td><td>" + tmpString+ "</td></tr>";
				break;
			case DIR_STANDARD:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
				string += "<tr><td><b>Selected Zones</b></td><td>" + swds.getZones() + "</td></tr>";
				break;
		}
		string += "<tr><td><b>26Al State</b></td><td>" 
				+ (ds.getType().equals("STABLE") ? "only stable": "metastable + ground")  + "</td></tr>";
		string += "<tr><td><b>Initial Abundance Profile</b></td><td>" + "PUBLIC/" + swds.getInitAbundPath().substring(swds.getInitAbundPath().lastIndexOf("/")+1) + "</td></tr>";
		string += "<tr><td><b>Sunet File</b></td><td>" 
				+ "PUBLIC/" + swds.getSunetPath().substring(swds.getSunetPath().lastIndexOf("/")+1) 
				+ "</td></tr>";
		
		switch(swds.getSimWorkflowMode()){
			case DIR_CUSTOM_DOUBLE_LOOPING:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + swds.getLibDir() + "</td></tr>";
				string += "<tr><td><b>Looping Type</b></td><td>Double Looping</td></tr>";
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + swds.getLibDir() + "</td></tr>";
				string += "<tr><td><b>Looping Type</b></td><td>Single Looping</td></tr>";
				break;
			case DIR_STANDARD:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + swds.getLibDir() + "</td></tr>";
				break;
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
				string += "<tr><td><b>Reaction Rate Library</b></td><td>" + swds.getLibGroup() + "/" + swds.getLibrary() + "</td></tr>";
				break;
		}
		
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
				string += "<tr><td><b>Selected Reaction Rate</b></td><td>" + swds.getReactionRate() + "</td></tr>";
				if(fullReport){
					String[] array = swds.getScaleFactors().split(",");
					for(String factor: array){
						string += "<tr><td><b>Rate Parameters for<br>Scale Factor = " + factor + "</b></td><td>" + getFormattedScaledParameters(Double.valueOf(factor), true) + "</td></tr>";
					}
				}else{
					string += "<tr><td><b>Scale Factors</b></td><td>" + swds.getScaleFactors() + "</td></tr>";
				}
				break;
			default:
				break;
		}
		string += "</table></body></html>";
		textPane.setText(string);
		textPane.setCaretPosition(0);
	}
	
	private String getFormattedScaledParameters(double factor, boolean useHtml){
		String string = "";
		double[][] parameterCompArray = ds.getRateDataStructure().getParameterCompArray();
		for(int i=0; i<parameterCompArray.length; i++){
			double param = parameterCompArray[i][0] - Math.log(1.0);
			param = param + Math.log(factor);
			string += "a(" + ((7*i)+1) + ") =" + NumberFormats.getFormattedParameter(param);
			if(useHtml){
				string += "<br>";
			}else{
				string += "\n";
			}
			for(int j=1; j<parameterCompArray[i].length; j++){
				string += "a(" + ((7*i)+(j+1)) + ") =" + NumberFormats.getFormattedParameter(parameterCompArray[i][j]);
				if(j<parameterCompArray[i].length-1){
					if(useHtml){
						string += "<br>";
					}else{
						string += "\n";
					}
				}
			}
			if(useHtml){
				string += "<br>";
			}else{
				string += "\n";
			}
		}
		if(useHtml){
			return string.substring(0, string.lastIndexOf("<br>"));
		}
		return string.substring(0, string.lastIndexOf("\n"));
	}
	
	public void createNucSimSetExistsDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		nucSimSetExistsDialog = new JDialog(frame, "Caution!", true);
		nucSimSetExistsDialog.setSize(320, 215);
		nucSimSetExistsDialog.getContentPane().setLayout(new GridBagLayout());
		nucSimSetExistsDialog.setLocationRelativeTo(frame);
		
		JTextArea nucSimSetExistsTextArea = new JTextArea("", 5, 30);
		nucSimSetExistsTextArea.setLineWrap(true);
		nucSimSetExistsTextArea.setWrapStyleWord(true);
		nucSimSetExistsTextArea.setFont(Fonts.textFont);
		nucSimSetExistsTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucSimSetExistsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String nucSimSetExistsString = string;
		nucSimSetExistsTextArea.setText(nucSimSetExistsString);
		nucSimSetExistsTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucSimSetExistsDialog.setVisible(false);
					nucSimSetExistsDialog.dispose();
				}
			}
		);
		
		JPanel nucSimSetExistsButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		nucSimSetExistsButtonPanel.add(yesButton, gbc1);
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		nucSimSetExistsButtonPanel.add(noButton, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		nucSimSetExistsDialog.getContentPane().add(sp, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		nucSimSetExistsDialog.getContentPane().add(nucSimSetExistsButtonPanel, gbc1);
		
		nucSimSetExistsDialog.setVisible(true);
	}
	
	public void createNucSimSetSavedDialog(String string, JFrame frame){
	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	nucSimSetSavedDialog = new JDialog(frame, "Element Synthesis Simulation Saved", true);
    	nucSimSetSavedDialog.setSize(350, 210);
    	nucSimSetSavedDialog.getContentPane().setLayout(new GridBagLayout());
		nucSimSetSavedDialog.setLocationRelativeTo(frame);
		
		JTextArea nucSimSetSavedTextArea = new JTextArea("", 5, 30);
		nucSimSetSavedTextArea.setLineWrap(true);
		nucSimSetSavedTextArea.setWrapStyleWord(true);
		nucSimSetSavedTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(nucSimSetSavedTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		nucSimSetSavedTextArea.setText(string);
		nucSimSetSavedTextArea.setCaretPosition(0);

		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					nucSimSetSavedDialog.setVisible(false);
					nucSimSetSavedDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		nucSimSetSavedDialog.getContentPane().add(sp, gbc1);
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.insets = new Insets(5, 3, 0, 3);
		nucSimSetSavedDialog.getContentPane().add(okButton, gbc1);
	
		nucSimSetSavedDialog.setVisible(true);
		
		
	}
	
	public void createSaveNucSimSetDialog(JFrame frame){
  
		saveNucSimSetDialog = new JDialog(frame, "Save Element Synthesis Simulation", true);
		saveNucSimSetDialog.setSize(480, 410);
		
    	saveNucSimSetDialog.getContentPane().setLayout(new GridBagLayout());
		saveNucSimSetDialog.setLocationRelativeTo(frame);
		
		newNucSimSetField = new JTextField();
		
		JLabel nameLabel = new JLabel();
		nameLabel.setFont(Fonts.textFont);
		
		JLabel notesLabel = new JLabel("Simulation notes:");
		notesLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea("");
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		JScrollPane sp = new JScrollPane(notesTextArea);
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED
						, gap, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.FILL
						, gap, TableLayoutConstants.PREFERRED, gap};
		saveNucSimSetDialog.getContentPane().setLayout(new TableLayout(column, row));
		JPanel buttonPanel = new JPanel();
		WordWrapLabel topLabel = new WordWrapLabel(true);
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD:
			case SINGLE_CUSTOM:
				topLabel.setText("Enter the name and notes for the Element Synthesis Simulation.");
				nameLabel.setText("Simulation name:");
				break;
			case DIR_STANDARD: 
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				topLabel.setText("Enter the principle name and notes for the Element Synthesis Simulation in the fields below. "
						+ "This name will be appended by an underscore and then the name of the rate library used by the simulation that was calculated.");
				nameLabel.setText("Simulation principle name:");
				break;
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
				topLabel.setText("Enter the principle name and notes for the sensitivity study in the fields below. "
									+ "This name will be appended by an underscore and then an integer corresponding to the order in which each single-zone simulation was calculated.");
				nameLabel.setText("Simulation principle name:");
			break;
		}
		topLabel.setFont(Fonts.textFont);
	
		saveNucSimSetDialog.getContentPane().add(topLabel, 				"1, 1, c, c");
		saveNucSimSetDialog.getContentPane().add(nameLabel, 			"1, 3, l, c");
		saveNucSimSetDialog.getContentPane().add(newNucSimSetField, 	"1, 5, f, c");
		saveNucSimSetDialog.getContentPane().add(notesLabel, 			"1, 7, l, c");
		saveNucSimSetDialog.getContentPane().add(sp, 					"1, 9, f, f");
		saveNucSimSetDialog.getContentPane().add(buttonPanel, 			"1, 11, c, c");
		
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					saveNucSimSetDialog.setVisible(false);
					saveNucSimSetDialog.dispose();
				}
			}
		);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		saveNucSimSetDialog.setVisible(true);
	}

}