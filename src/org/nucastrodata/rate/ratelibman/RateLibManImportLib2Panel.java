package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.dialogs.CautionDialog2;
import org.nucastrodata.rate.ratelibman.listener.RateLibManImportDirRateLibraryListener;
import org.nucastrodata.rate.ratelibman.listener.RateLibManImportSingleRateLibraryListener;
import org.nucastrodata.rate.ratelibman.worker.RateLibManImportDirRateLibraryWorker;
import org.nucastrodata.rate.ratelibman.worker.RateLibManImportSingleRateLibraryWorker;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.event.*;
import java.io.File;

import org.nucastrodata.Cina;
import org.nucastrodata.WizardPanel;

public class RateLibManImportLib2Panel extends WizardPanel implements ActionListener, 
																		RateLibManImportSingleRateLibraryListener, 
																		RateLibManImportDirRateLibraryListener{
	
	private RateLibManDataStructure ds;
	
	private WordWrapLabel topLabel, topLabelDir;
	private MainDataStructure mds;
	private JTextField nameField, nameFieldDir;
	private JTextArea notesArea, notesAreaDir;
	private JLabel nameLabel, notesLabel, nameLabelDir, notesLabelDir;
	private JPanel libPanel;
	private JScrollPane sp, spDir; 
	private JPanel mainPanel;
	private JButton createButton, createButton2, createButtonDir, createButton2Dir, helpButton;
	private Frame frame;
	
	public RateLibManImportLib2Panel(Frame frame, RateLibManDataStructure ds,  MainDataStructure mds){
		
		this.ds = ds;
		this.mds = mds;
		this.frame = frame;

		Cina.rateLibManFrame.setCurrentFeatureIndex(8);
		Cina.rateLibManFrame.setCurrentPanelIndex(2);

		addWizardPanel("Rate Library Manager", "Import One or More Libraries", "2", "2");

		mainPanel = new JPanel();

		topLabel = new WordWrapLabel(true);
		topLabelDir = new WordWrapLabel(true);

		nameLabel = new JLabel("Rate Library Name:");
		notesLabel = new JLabel("Rate Library Notes:");
		
		nameLabelDir = new JLabel("Rate Library Directory Name:");
		notesLabelDir = new JLabel("Rate Library Directory Notes:");
		
		nameField = new JTextField();
		nameFieldDir = new JTextField();
		
		notesArea = new JTextArea();
		notesArea.setLineWrap(true);
		notesArea.setWrapStyleWord(true);
		sp = new JScrollPane(notesArea);
		
		notesAreaDir = new JTextArea();
		notesAreaDir.setLineWrap(true);
		notesAreaDir.setWrapStyleWord(true);
		spDir = new JScrollPane(notesAreaDir);
		
		createButton = new JButton("Import New Rate Library");
		createButton.addActionListener(this);
		
		createButton2 = new JButton("Import Another New Rate Library");
		createButton2.addActionListener(this);
		
		createButtonDir = new JButton("Import New Rate Library Directory");
		createButtonDir.addActionListener(this);
		
		createButton2Dir = new JButton("Import Another New Rate Library Directory");
		createButton2Dir.addActionListener(this);
		
		helpButton = new JButton("Help on Rate Library File Format");
		helpButton.addActionListener(this);
		
		topLabel.setText("Click <i>Import New Rate Library</i> to import a rate library into your user storage.");
		topLabelDir.setText("Click <i>Import New Rate Library Directory</i> to import a directory of rate libraries into your user storage.");
		
		libPanel = new JPanel();
		double[] columnLib = {TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.FILL};
		double[] rowLib = {TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED, 10,
								TableLayoutConstants.FILL};
		libPanel.setLayout(new TableLayout(columnLib, rowLib));
		
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	public void gotoAfterImportSingle(){
		
		mainPanel.removeAll();
		
		String name = nameField.getText();
		
		topLabel.setText("You have successfully imported a new rate library called <i>" + name + "</i>. To import another, click <i>Import Another New Rate Library</i>.");
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED, 20};
		mainPanel.setLayout(new TableLayout(column, row));
		
		notesArea.setText("");
		nameField.setText("");
		
		mainPanel.add(topLabel, 		"1, 1, c, c");
		mainPanel.add(libPanel, 		"1, 3, f, f"); 
		mainPanel.add(createButton2, 	"1, 5, c, c");
		mainPanel.add(helpButton, 	    "1, 7, c, c");
		
		validate();
		repaint();
	}
	
	public void gotoAfterImportDir(){
		
		mainPanel.removeAll();
		
		String name = nameFieldDir.getText();
		
		topLabelDir.setText("You have successfully imported a new directory of rate libraries called " + name + ". To import another, click <i>Import Another New Rate Library Directory</i>.");
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.PREFERRED, 20};
		mainPanel.setLayout(new TableLayout(column, row));
		
		notesAreaDir.setText("");
		nameFieldDir.setText("");
		
		mainPanel.add(topLabelDir, 			"1, 1, c, c");
		mainPanel.add(libPanel, 			"1, 3, f, f");
		mainPanel.add(createButton2Dir, 	"1, 5, c, c");
		mainPanel.add(helpButton, 	    	"1, 7, c, c");
		
		validate();
		repaint();
	}
	
	private boolean goodData(){
		if(nameField.getText().trim().equals("")){
			String error = "Please enter a name for the new rate library.";
			AttentionDialog.createDialog(frame, error);
			return false;
		}else if(notesArea.getText().trim().equals("")){
			String error = "Please enter notes for the new rate library.";
			AttentionDialog.createDialog(frame, error);
			return false;
		}
		return true;
	}
	
	private boolean goodDataDir(){
		if(nameFieldDir.getText().trim().equals("")){
			String error = "Please enter a name for the new rate library directory.";
			AttentionDialog.createDialog(frame, error);
			return false;
		}else if(notesAreaDir.getText().trim().equals("")){
			String error = "Please enter notes for the new rate library directory.";
			AttentionDialog.createDialog(frame, error);
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==helpButton){
			

		}else if(ae.getSource()==createButton || ae.getSource()==createButton2){
	
			if(goodData()){
	
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new File(mds.getAbsolutePath()));
			    chooser.setDialogTitle("Select Rate Library NETSU File to Import");
			    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(true);
			    if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					if(!checkPublicLibName(nameField.getText().trim())){
						AttentionDialog.createDialog(frame, "The selected rate library already exists in the Public or Shared user spaces. "
																+ "Please rename this rate library and try importing it again.");
						return;
					}else if(!checkOverwriteLibName(nameField.getText().trim())){
						String string = "The selected rate library already exists in your user space. Do you want to overwrite this rate library with the selected file?";
						int returnValue = CautionDialog2.createCautionDialog2(frame, string, "Attention!");
						if(returnValue==CautionDialog2.YES){
							LibraryDataStructure lds = new LibraryDataStructure();
							lds.setLibName(nameField.getText().trim());
							lds.setLibNotes(notesArea.getText().trim());
							RateLibManImportSingleRateLibraryWorker worker  = new RateLibManImportSingleRateLibraryWorker(this, lds, file, frame);
							worker.execute();
						}
					}else{
						LibraryDataStructure lds = new LibraryDataStructure();
						lds.setLibName(nameField.getText().trim());
						lds.setLibNotes(notesArea.getText().trim());
						RateLibManImportSingleRateLibraryWorker worker  = new RateLibManImportSingleRateLibraryWorker(this, lds, file, frame);
						worker.execute();
					}
			    }
		    
			}
		    
		}else if(ae.getSource()==createButtonDir || ae.getSource()==createButton2Dir){
		
			if(goodDataDir()){
			
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new File(mds.getAbsolutePath()));
			    chooser.setDialogTitle("Select Directory of Rate Library NETSU Files to Import");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(true);
			    
			    if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
			    
		    		File dir = chooser.getSelectedFile();
		   
		    		if(checkOverwriteLibDirName(nameFieldDir.getText().trim())){
		    		
						String string = "The selected rate library directory already exists in your user space. Do you want to overwrite this rate library directory with this one?";
						int returnValue = CautionDialog2.createCautionDialog2(frame, string, "Attention!");
					
						if(returnValue==CautionDialog2.YES){
						
							RateLibManImportDirRateLibraryWorker worker  = new RateLibManImportDirRateLibraryWorker(this, ds, dir, frame, 
																													notesAreaDir.getText().trim(), 
																													nameFieldDir.getText().trim());
							worker.execute();
						
						}
						
		    		}else{
		    		
			    		RateLibManImportDirRateLibraryWorker worker  = new RateLibManImportDirRateLibraryWorker(this, ds, dir, frame, 
			    																									notesAreaDir.getText().trim(), 
			    																									nameFieldDir.getText().trim());
			    		worker.execute();
		    		}
			    
			    }
			
			}
		
		}
		
	} 
	
	public boolean checkOverwriteLibDirName(String name){
		if(ds.getLibDirList().contains(name)){
			return true;
		}
		return false;
	}
	
	public boolean checkPublicLibName(String name){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			if(name.equalsIgnoreCase(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			if(name.equalsIgnoreCase(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}

	public boolean checkOverwriteLibName(String name){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			if(name.equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}

	public void setCurrentState(){

		mainPanel.removeAll();
		libPanel.removeAll();

		if(ds.getImportDir()){
		
			libPanel.add(nameLabelDir,        "0, 0, r, c");
			libPanel.add(nameFieldDir,  	  "2, 0, f, c");
			libPanel.add(notesLabelDir,       "0, 2, r, c");
			libPanel.add(spDir, 			  "0, 4, 2, 4, f, f");
			
			double[] column = {20, TableLayoutConstants.FILL, 20};
			double[] row = {20, TableLayoutConstants.PREFERRED
								, 20, TableLayoutConstants.FILL
								, 20, TableLayoutConstants.PREFERRED, 20};
			mainPanel.setLayout(new TableLayout(column, row));
			
			mainPanel.add(topLabelDir, 		"1, 1, c, c");
			mainPanel.add(libPanel, 		"1, 3, f, f");
			mainPanel.add(createButtonDir, 	"1, 5, c, c");
			
		
		}else{
		
			
			libPanel.add(nameLabel,        "0, 0, r, c");
			libPanel.add(nameField,  	  "2, 0, f, c");
			libPanel.add(notesLabel,       "0, 2, r, c");
			libPanel.add(sp, 			  "0, 4, 2, 4, f, f");
			
			double[] column = {20, TableLayoutConstants.FILL, 20};
			double[] row = {20, TableLayoutConstants.PREFERRED
								, 20, TableLayoutConstants.FILL
								, 20, TableLayoutConstants.PREFERRED, 20};
			mainPanel.setLayout(new TableLayout(column, row));
			
			mainPanel.add(topLabel, 		"1, 1, c, c");
			mainPanel.add(libPanel, 		"1, 3, f, f");
			mainPanel.add(createButton, 	"1, 5, c, c");
		
		}
		
		validate();
		repaint();

	}
	
	public void getCurrentState(){}

	public void updateAfterImportSingleRateLibrary(LibraryDataStructure lds){
		gotoAfterImportSingle();
	}

	public void updateAfterImportDirRateLibrary(){
		gotoAfterImportDir();
	}

	public void updateAfterLibDirExists(){
		
	}

	
}