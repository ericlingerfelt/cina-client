package org.nucastrodata.element.thermoman;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.dialogs.CautionDialog2;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.io.FileGetter;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.WizardPanel;

public class ThermoManImport1Panel extends WizardPanel implements ActionListener{
	
	private ThermoManDataStructure ds;
	private WordWrapLabel topLabel;
	private MainDataStructure mds;
	private JTextField nameField, dirField;
	private JTextArea notesArea;
	private JLabel nameLabel, notesLabel, dirLabel;
	private JScrollPane sp; 
	private JPanel mainPanel;
	private JButton dirButton, helpButton;
	private ThermoManFrame frame;
	private ThermoManDataFileFrame helpFrame;
	
	public ThermoManImport1Panel(ThermoManFrame frame, ThermoManDataStructure ds,  MainDataStructure mds){
		
		this.ds = ds;
		this.mds = mds;
		this.frame = frame;

		topLabel = new WordWrapLabel(true);

		helpFrame = new ThermoManDataFileFrame();

		dirLabel = new JLabel("Thermodynamic Profile Set Directory:");
		nameLabel = new JLabel("Thermodynamic Profile Set Name:");
		notesLabel = new JLabel("Thermodynamic Profile Set Notes:");
	
		nameField = new JTextField();
		dirField = new JTextField();
		dirField.setEditable(false);

		notesArea = new JTextArea();
		notesArea.setLineWrap(true);
		notesArea.setWrapStyleWord(true);
		sp = new JScrollPane(notesArea);
		
		dirButton = new JButton("Select");
		dirButton.addActionListener(this);
		
		helpButton = new JButton("Help on Thermodynamic Profile Import");
		helpButton.addActionListener(this);
		
		topLabel.setText("Import a set of thermodynamic profiles into your user storage by entering a name and notes in the fields below and "
								+ "selecting the directory containing the thermodynamic profile files and the weights file. "
								+ "Click <i>Help on Thermodynamic Profile Import</i> to get detailed instructions on required input files. "
								+ "Click <i>Continue</i> to import the selected set of thermodynamic profiles.");
		
		mainPanel = new JPanel();
		
		double[] columnMain = {TableLayoutConstants.PREFERRED, 10, 
				TableLayoutConstants.FILL, 10,
				TableLayoutConstants.PREFERRED};
		double[] rowMain = {TableLayoutConstants.PREFERRED, 10, 
				TableLayoutConstants.PREFERRED, 20,
				TableLayoutConstants.PREFERRED, 10,
				TableLayoutConstants.FILL};
		mainPanel.setLayout(new TableLayout(columnMain, rowMain));

		mainPanel.add(nameLabel,      "0, 0, l, c");
		mainPanel.add(nameField,  	  "2, 0, 4, 0, f, c");
		mainPanel.add(dirLabel,       "0, 2, l, c");
		mainPanel.add(dirField,  	  "2, 2, f, c");
		mainPanel.add(dirButton,  	  "4, 2, f, c");
		mainPanel.add(notesLabel,     "0, 4, l, c");
		mainPanel.add(sp, 			  "0, 6, 4, 6, f, f");

		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
				, 20, TableLayoutConstants.FILL
				, 20, TableLayoutConstants.PREFERRED, 20};
		setLayout(new TableLayout(column, row));

		add(topLabel, 		"1, 1, c, c");
		add(mainPanel, 		"1, 3, f, f");
		add(helpButton, 	"1, 5, c, c");
		
	}
	
	public void setCurrentState(){

		if(ds.getImportedThermoProfileSetDataStructure()!=null){
			ThermoProfileSetDataStructure tpsds = ds.getImportedThermoProfileSetDataStructure();
			nameField.setText(tpsds.getName());
			dirField.setText(tpsds.getDir());
			notesArea.setText(tpsds.getDesc());
		}else{
			nameField.setText("");
			dirField.setText("");
			notesArea.setText("");
		}

	}
	
	public void getCurrentState(){
	
		ThermoProfileSetDataStructure tpsds = new ThermoProfileSetDataStructure();
		tpsds.setName(nameField.getText().trim());
		tpsds.setDesc(notesArea.getText().trim());
		tpsds.setFolderType(FolderType.USER);
		tpsds.setPath(FolderType.USER.name() + "/" + tpsds.getName());
		tpsds.setDir(dirField.getText());
		ds.setImportedThermoProfileSetDataStructure(tpsds);
	
	}
	
	public boolean goodData(){
	
		if(nameField.getText().trim().equals("")){
		
			String error = "Please enter a name for the new set of thermodynamic profiles.";
			AttentionDialog.createDialog(frame, error);
			return false;
			
		}else if(notesArea.getText().trim().equals("")){
		
			String error = "Please enter notes for the new set of thermodynamic profiles.";
			AttentionDialog.createDialog(frame, error);
			return false;
			
		}
		
		File[] array = new File(dirField.getText()).listFiles();
		
		for(File file: array){
			if(file.isDirectory()){
				String error = "A directory exists in the selected thermodynamic profile path named " 
								+ file.getName() 
								+ ". There can be no directories in the selected thermodynamic profile path for this import.";
				AttentionDialog.createDialog(frame, error);
				return false;
			}
		}
		
		boolean weightsFound = false;
		for(File file: array){
			String filename = file.getName();
			if(filename.equals("weights")){
				weightsFound = true;
				break;
			}
		}

		if(!weightsFound){
			String error = "A \"weights\" file was not found in the selected thermodynamic profile path. "
							+ "You must include exactly one file named \"weights\" for this import.";
			AttentionDialog.createDialog(frame, error);
			return false;
		}
		
		for(File file: array){
			String filename = file.getName();
			if(!filename.equals("weights")){
				try{
					Integer.valueOf(filename);
				}catch(NumberFormatException nfe){
					String error = "There are some files in the selected thermodynamic profile path that are not named after integers. "
									+ "All thermodynamic profile files must be named as integers starting with \"1\".";
					AttentionDialog.createDialog(frame, error);
					return false;
				}
			}
		}
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(File file: array){
			String filename = file.getName();
			if(!filename.equals("weights")){
				try{
					list.add(Integer.valueOf(filename));
				}catch(NumberFormatException nfe){
					
				}
			}
		}
		
		Collections.sort(list);
		
		for(int i=1; i<=list.size(); i++){
			if(list.get(i-1) != i){
				String error = "The files in the selected thermodynamic profile path must start with \"1\" and increase incrementally.";
				AttentionDialog.createDialog(frame, error);
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkPath(){
		String path = FolderType.USER.name() + "/" + nameField.getText();
		ThermoProfileSetDataStructure tpsds = ds.getSetMap().get(path);
		if(tpsds != null){
			String string = "The selected set of thermodynamic profiles already exists in your user space. "
								+ "Do you want to overwrite that set of thermodynamic profiles with this one?";
			int returnValue = CautionDialog2.createCautionDialog2(frame, string, "Attention!");
			if(returnValue==CautionDialog2.YES){
				return true;
			}
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==helpButton){
			String text = "The thermodynamic profiles in the selected directory "
						+ "must be named sequentially with integers starting  with \"1\". "
						+ "Below is an example of an appropriately formatted profile. \n\n";
			String title = "Help on Thermodynamic Profile Import";
			String filepath = "/var/www/cina_files/PUBLIC/thermo/1.35M.solarWD/1";
			text += new String(FileGetter.getFileByPath(filepath)) + "\n";
			text += "The selected directory must also contain a \"weights\" file similar to the example below.\n\n";
			filepath = "/var/www/cina_files/PUBLIC/thermo/1.35M.solarWD/weights";
			text += new String(FileGetter.getFileByPath(filepath));
			helpFrame.initialize(title, frame);
			helpFrame.setText(text);
		}else if(ae.getSource()==dirButton){
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new File(mds.getAbsolutePath()));
		    chooser.setDialogTitle("Select Directory of Thermodynamic Profiles to Import");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(true);	
		    if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
	    		dirField.setText(chooser.getSelectedFile().getAbsolutePath());   
		    }
		}
	}
}