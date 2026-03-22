package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManLibErrorCheck1Panel.
 */
public class RateLibManLibErrorCheck1Panel extends WizardPanel{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The main panel. */
	JPanel mainPanel; 
	
	/** The lib combo box. */
	JComboBox libComboBox;
	
	/** The label2. */
	JLabel label1, label2;
	
	/** The over box. */
	JCheckBox tempBox, invBox, overBox;
	
	/** The delay dialog. */
	JDialog delayDialog;
	
	/** The good error check lib. */
	boolean goodErrorCheckLib = false;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man lib error check1 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManLibErrorCheck1Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(4);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);

		label1 = new JLabel("Library to check: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("Checks to perform: ");
		label2.setFont(Fonts.textFont);
		
		tempBox = new JCheckBox("Extreme temperature behavior", true);
		invBox = new JCheckBox("<html>Rates determined by detailed balance<p>(inverse rates) included</html>", true);
		overBox = new JCheckBox("Overflow", true);
		
		tempBox.setFont(Fonts.textFont);
		invBox.setFont(Fonts.textFont);
		overBox.setFont(Fonts.textFont);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		mainPanel.add(label1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		mainPanel.add(libComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		
		mainPanel.add(label2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		mainPanel.add(tempBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		mainPanel.add(invBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		mainPanel.add(overBox, gbc);
		
		addWizardPanel("Rate Library Manager", "Library Error Check", "1", "2");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	//method to make CGI call to werase user lib
	/**
	 * Good error check lib.
	 *
	 * @return true, if successful
	 */
	public boolean goodErrorCheckLib(){
	
		//Create local boolean
		goodErrorCheckLib = false;
		
		final SwingWorker worker = new SwingWorker(){
		
			public Object construct(){
		
				//Set CGI vars for call
				ds.setSourceLib((String)libComboBox.getSelectedItem());
				ds.setDestLibGroup("USER");
				ds.setDestLibName("");
				ds.setDeleteSourceLib("NO");
			
				//Make CGI call for MODIFY RATE LIBRARY action
				boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATE LIBRARY", Cina.rateLibManFrame);
			
				//If no errors
				if(!flagArray[0]){
				
					//If no reasons
					if(!flagArray[2]){
					
						//If no cautions
						if(!flagArray[1]){
						
							//Set boolean to true
							Cina.rateLibManFrame.rateLibManLibErrorCheck1Panel.goodErrorCheckLib = true;
						
						}
						
					}
					
				}
				
				return new Object();
				
			}
		
			public void finished(){
				
				closeDelayDialog();

				Cina.rateLibManFrame.libErrorCheck1ErrorCheckLib();

				//Cina.setFrameColors(Cina.rateLibManFrame);
				
				Cina.rateLibManFrame.validate();
				
			}
			
		};
		
		worker.start();
		
		//Return boolean					
		return goodErrorCheckLib;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		libComboBox.removeAllItems();
		
		String[] sourceLibArray = new String[ds.getNumberPublicLibraryDataStructures()
										+ ds.getNumberSharedLibraryDataStructures()
										+ ds.getNumberUserLibraryDataStructures()];
		
		int sourceLibArrayCounter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			libComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
			
			sourceLibArray[sourceLibArrayCounter] = "Public";
			
			sourceLibArrayCounter++;
	
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			libComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
	
			sourceLibArray[sourceLibArrayCounter] = "Shared";
			
			sourceLibArrayCounter++;
			
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
	
			sourceLibArray[sourceLibArrayCounter] = "User";
			
			sourceLibArrayCounter++;
			
		}
		
		libComboBox.setSelectedItem("ReaclibV2.2");
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(overBox.isSelected()){
		
			ds.setCHK_OVERFLOW("YES");
		
		}else{
		
			ds.setCHK_OVERFLOW("NO");
		
		}
		
		if(invBox.isSelected()){
		
			ds.setCHK_INVERSE("YES");
		
		}else{
		
			ds.setCHK_INVERSE("NO");
		
		}
	
		if(tempBox.isSelected()){
		
			ds.setCHK_TEMP_BEHAVIOR("YES");
		
		}else{
		
			ds.setCHK_TEMP_BEHAVIOR("NO");
		
		}
	
	}
	
	/**
	 * Open delay dialog.
	 *
	 * @param frame the frame
	 */
	public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait.", false);
    	delayDialog.setSize(340, 200);
    	delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while library is checked for errors. DO NOT operate the Rate Library Manager at this time!";
		
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

}