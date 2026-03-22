package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.file.filter.*;

import java.awt.event.*;
import java.io.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.file.filter.ZIPFileFilter;


/**
 * The Class RateLibManExportLibPanel.
 */
public class RateLibManExportLibPanel extends WizardPanel implements ActionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The middle panel. */
	JPanel mainPanel, middlePanel; 
	
	/** The top label. */
	JLabel label1, label2, bytesLabel, topLabel; 
		
	/** The output combo box. */
	JComboBox libComboBox, outputComboBox;
	
	/** The export button. */
	JButton exportButton;
	
	/** The no button. */
	JButton yesButton, noButton;
	
	/** The file dialog. */
	JFileChooser fileDialog;
	
	/** The progress bar. */
	public JProgressBar progressBar;
	
	/** The add missing inv rates dialog. */
	public JDialog exportLibDialog, progressBarDialog, delayDialog, cautionDialog, addMissingInvRatesDialog;;
	
	/** The save filename. */
	String saveFilename;
	
	/** The file. */
	File file;
	
	/** The progress bar text area. */
	public JTextArea progressBarTextArea;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man export lib panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManExportLibPanel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(7);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		middlePanel = new JPanel(new GridBagLayout());
		
		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		outputComboBox = new JComboBox();
		outputComboBox.addItem("FULL NETSU");
		outputComboBox.addItem("ASCII");
		outputComboBox.setFont(Fonts.textFont);
		
		label1 = new JLabel("Library to export: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("Library export format: ");
		label2.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>With the Export Library tool, you can download a zipped file of a<p>formatted rate library.</html>");
		
		exportButton = new JButton("Export Library");
		exportButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(20, 5, 20, 5);
		
		middlePanel.add(label1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		middlePanel.add(libComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(20, 5, 20, 5);
		
		middlePanel.add(label2, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		middlePanel.add(outputComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		middlePanel.add(exportButton, gbc);
		
		gbc.gridwidth = 1;
		
		addWizardPanel("Rate Library Manager", "Export Library", "1", "1");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		mainPanel.add(middlePanel, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==exportButton){
		
			if(goodLibInfoInverseCheck()){
			
				if(ds.getCurrentLibraryDataStructure().getLibType().equals("USER")){
	
					
	
					if(ds.getCurrentLibraryDataStructure().getAllInversesPresent()){
	
						export();
	
					}else{
					
						String string = "The library you have selected for exporting does not have a complete set of "
											+ "reaction rates as determined by detailed balance (inverse rates)."
											+ " This is a requirement for library export. Do you want to calculate these reactions and add them to this library?";
											
						createCautionDialog(string, Cina.rateLibManFrame);					
					
					}
				
				}else{
				
					export();
				
				}
				
			}

		}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
			openDelayDialog(Cina.rateLibManFrame);
		
			ds.setLibraryName((String)libComboBox.getSelectedItem());
			boolean goodInverseRates = Cina.cinaCGIComm.doCGICall("ADD MISSING INV RATES", Cina.rateLibManFrame);
		
			closeDelayDialog();
		
			if(goodInverseRates){
			
				String string = ds.getAddMissingInvRatesReport();
			
				createAddMissingInvRatesDialog(string, Cina.rateLibManFrame);
			
				export();
			
			}
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}
		
	} 
	
	/**
	 * Good lib info inverse check.
	 *
	 * @return true, if successful
	 */
	public boolean goodLibInfoInverseCheck(){
		
		boolean goodLibInfoInverseCheck = false;
		
		ds.setLibraryName((String)libComboBox.getSelectedItem());
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY INFO", Cina.rateLibManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodLibInfoInverseCheck = true;	
					
				}
			}
		}
		
		return goodLibInfoInverseCheck;
	
	}
	
	/**
	 * Export.
	 */
	public void export(){
	
		fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());
		
		fileDialog.addChoosableFileFilter(new ZIPFileFilter());
		fileDialog.setAcceptAllFileFilterUsed(false);
		
		saveFilename = "";
		
		int returnVal = fileDialog.showSaveDialog(Cina.rateLibManFrame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			
			file = fileDialog.getSelectedFile();
			
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

			saveFilename = fileDialog.getSelectedFile().getAbsolutePath();

			if(fileDialog.getFileFilter() instanceof ZIPFileFilter){
		
				if(saveFilename.endsWith(".zip")){
					
					ds.setExportFilename(saveFilename);
				
				}else{
				
					ds.setExportFilename(saveFilename + ".zip");
					
				}
				
			}
			
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
			Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
		}
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			
			openProgressBarDialog(Cina.rateLibManFrame);
			progressBarTextArea.setText("Please be patient while file is constructed. DO NOT operate the Rate Library Manager at this time!");
			progressBar.setVisible(false);
			progressBarDialog.validate();
			progressBar.setValue(0);
			
			final SwingWorker worker = new SwingWorker(){
			
					public Object construct(){
						
						goodExportLib();
						
	        	    	return new Object();
					
					}
			
					public void finished(){
					
						Cina.rateLibManFrame.rateLibManExportLibPanel.closeProgressBarDialog();
						
					}
					
			};
				
			worker.start();
		
		}

	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		//Remove all choices from the library combo box
		libComboBox.removeAllItems();

		//Loop over public libraries
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			//Add library to combo box
			libComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
	
		}
		
		//Loop over shared libraries
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			//Add library to combo box
			libComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());

		}
		
		//Loop over user libraries
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			//Add library to combo box
			libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
	
		}
		
		//Set library combo box to REACLIB
		libComboBox.setSelectedItem("ReaclibV2.2");

		validate();

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Good export lib.
	 *
	 * @return true, if successful
	 */
	public boolean goodExportLib(){
	
		boolean goodExportLib = false;
		
		ds.setLibraryName((String)libComboBox.getSelectedItem());
	
		ds.setFormat((String)outputComboBox.getSelectedItem());
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("EXPORT RATE LIBRARY", Cina.rateLibManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodExportLib = true;
				
				}
				
			}
			
		}
								
		return goodExportLib;
	
	}
	
	/**
	 * Open delay dialog.
	 *
	 * @param frame the frame
	 */
	public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait", false);
    	delayDialog.setSize(340, 200);
    	delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		//delayTextArea.setFont(CinaFonts.textFont);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while inverse rates are added. DO NOT operate the Rate Library Manager at this time!";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		
		//Cina.setFrameColors(delayDialog);	
		
		delayDialog.validate();
		
		delayDialog.setVisible(true);
		
	}
	
	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	
	}
	
	/**
	 * Creates the add missing inv rates dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createAddMissingInvRatesDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	addMissingInvRatesDialog = new JDialog(frame, "Inverse Rates Created and Added to Library", true);
    	addMissingInvRatesDialog.setSize(350, 210);
    	addMissingInvRatesDialog.getContentPane().setLayout(new GridBagLayout());
		addMissingInvRatesDialog.setLocationRelativeTo(frame);
		
		JTextArea addMissingInvRatesTextArea = new JTextArea("", 5, 30);
		addMissingInvRatesTextArea.setLineWrap(true);
		addMissingInvRatesTextArea.setWrapStyleWord(true);
		addMissingInvRatesTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(addMissingInvRatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		addMissingInvRatesTextArea.setText(string);
		addMissingInvRatesTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					addMissingInvRatesDialog.setVisible(false);
					addMissingInvRatesDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		addMissingInvRatesDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		addMissingInvRatesDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(addMissingInvRatesDialog);
		
		//Show the dialog
		addMissingInvRatesDialog.setVisible(true);
	
	}
	
	/**
	 * Creates the caution dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialog(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	cautionDialog = new JDialog(frame, "Caution!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		JTextArea cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;

		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc1);
		
		//Cina.setFrameColors(cautionDialog);

		cautionDialog.setVisible(true);

	}
	
	/**
	 * Creates the export lib dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createExportLibDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	exportLibDialog = new JDialog(frame, "Rate Library Exported", true);
    	exportLibDialog.setSize(350, 210);
    	exportLibDialog.getContentPane().setLayout(new GridBagLayout());
		exportLibDialog.setLocationRelativeTo(frame);
		
		JTextArea exportLibTextArea = new JTextArea("", 5, 30);
		exportLibTextArea.setLineWrap(true);
		exportLibTextArea.setWrapStyleWord(true);
		exportLibTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(exportLibTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		exportLibTextArea.setText(string);
		exportLibTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					exportLibDialog.setVisible(false);
					exportLibDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		exportLibDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		exportLibDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(exportLibDialog);
		
		//Show the dialog
		exportLibDialog.setVisible(true);
	
	}
	
	/**
	 * Open progress bar dialog.
	 *
	 * @param frame the frame
	 */
	public void openProgressBarDialog(Frame frame){
		
		progressBarDialog = new JDialog(frame, "Please wait", false);
    	progressBarDialog.setSize(363, 220);
    	progressBarDialog.getContentPane().setLayout(new GridBagLayout());
		progressBarDialog.setLocationRelativeTo(frame);
		
		progressBarTextArea = new JTextArea("", 5, 30);
		progressBarTextArea.setLineWrap(true);
		progressBarTextArea.setWrapStyleWord(true);
		progressBarTextArea.setEditable(false);
		
		bytesLabel = new JLabel("");
		
		JScrollPane sp = new JScrollPane(progressBarTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String progressBarString = "Please be patient while file is downloaded. DO NOT operate the Rate Library Manager at this time!";
		
		progressBarTextArea.setText(progressBarString);
		
		progressBarTextArea.setCaretPosition(0);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		progressBarDialog.getContentPane().add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		progressBarDialog.getContentPane().add(bytesLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		
		progressBarDialog.getContentPane().add(progressBar, gbc);
		
		//Cina.setFrameColors(progressBarDialog);	
		
		progressBarDialog.validate();
		
		progressBarDialog.setVisible(true);
		
	}
	
	/**
	 * Close progress bar dialog.
	 */
	public void closeProgressBarDialog(){
		
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	
	}
	
}