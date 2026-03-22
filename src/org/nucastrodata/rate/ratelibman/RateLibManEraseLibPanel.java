package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


//This class creates the window for the Erase Library sub feature of th eRate Library Manager feature 
/**
 * The Class RateLibManEraseLibPanel.
 */
public class RateLibManEraseLibPanel extends WizardPanel implements ActionListener{
	
	//Declare gbc
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The middle panel. */
	JPanel mainPanel, middlePanel; 
	
	//Declare labels
	/** The top label. */
	JLabel label1, label2, topLabel; 
	
	//Declare other components
	/** The lib combo box. */
	JComboBox libComboBox;
	
	/** The no button. */
	JButton eraseLibButton, yesButton, noButton;
	
	//Declare dialogs
	/** The erase lib dialog. */
	JDialog cautionDialog, eraseLibDialog;

	/** The ds. */
	private RateLibManDataStructure ds;

	//Constructor
	/**
	 * Instantiates a new rate lib man erase lib panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManEraseLibPanel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		//Set current feature index to 6
		Cina.rateLibManFrame.setCurrentFeatureIndex(6);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		//Create center panel
		mainPanel = new JPanel(new GridBagLayout());
		
		//Instantiate gbc
		gbc = new GridBagConstraints();
		
		//Create components
		middlePanel = new JPanel(new GridBagLayout());
		
		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		label1 = new JLabel("Library to erase: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("There are no User libraries to erase.");
		label2.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>With the Erase Library tool, you can delete rate libraries from your User storage.</html>");
		
		eraseLibButton = new JButton("Erase Library");
		eraseLibButton.addActionListener(this);
		
		
		//Put it all together
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 5, 20, 5);
		
		middlePanel.add(label1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		middlePanel.add(libComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		middlePanel.add(eraseLibButton, gbc);
		
		gbc.gridwidth = 1;
		
		addWizardPanel("Rate Library Manager", "Erase Library", "1", "1");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		mainPanel.add(middlePanel, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		//If source is the erase lib button
		if(ae.getSource()==eraseLibButton){
		
			//Ask user one more time if that is what they wnat to do 
			String string = "You are about to erase the User library " + (String)libComboBox.getSelectedItem() + ".";
		
			createCautionDialog(string, Cina.rateLibManFrame);
		
		//If source is yes button of caution dialog
		}else if(ae.getSource()==yesButton){
			
			//Close caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			//If the CGI call to erase the library is successful
			if(goodEraseLib()){
			
				//If there is at least one more library to erase
				if(libComboBox.getItemCount()>1){
			
					//Remove the selected library from the menu
					libComboBox.removeItem(libComboBox.getSelectedItem());
				
				//If there is not at least one more library to erase
				}else{
				
					//Remove panel components
					middlePanel.remove(libComboBox);
			
					middlePanel.remove(label1);
					
					//Add panel components to tell user that there are no more libraries to erase 
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.anchor = GridBagConstraints.WEST;
					gbc.insets = new Insets(20, 5, 20, 5);
					
					middlePanel.add(label2, gbc);
					
					eraseLibButton.setEnabled(false);
				
				}
			
				//Show user a report detailing lib erase
				String string = ds.getModifyLibReport();
				
				createEraseLibDialog(string, Cina.rateLibManFrame);
			
			}
	
		//If no button is source
		//ie the user chooses not to erase lib
		}else if(ae.getSource()==noButton){
		
			//Close caution dialog
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}
		
		//Set color formatting
		//Cina.setFrameColors(Cina.rateLibManFrame);
		
		validate();
		
	} 
	
	//Method to set the current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		//Remove all item from the library combo box
		libComboBox.removeAllItems();
		
		//If there are libraries to erase
		if(ds.getNumberUserLibraryDataStructures()!=0){
		
			//Loop over user libraries
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
				//Add the libraries to the combo box
				libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
				
			}
		
			//Select first item in combo box
			libComboBox.setSelectedIndex(0);
		
			//Remove unwanted components from panel
			middlePanel.remove(label2);
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(20, 5, 20, 5);
			
			middlePanel.add(label1, gbc);
		
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;
			middlePanel.add(libComboBox, gbc);
			
			eraseLibButton.setEnabled(true);
		
		//If there are no more user libraries to erase
		}else{
			
			//Remove panel components
			middlePanel.remove(libComboBox);
			
			middlePanel.remove(label1);
			
			//Add panel components to tell user that there are no more libraries to erase
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(20, 5, 20, 5);
			
			middlePanel.add(label2, gbc);
			
			//Disabel erase lib button
			eraseLibButton.setEnabled(false);
		
		}
		
		validate();

	}
	
	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	//method to make CGI call to werase user lib
	/**
	 * Good erase lib.
	 *
	 * @return true, if successful
	 */
	public boolean goodEraseLib(){
	
		//Create local boolean
		boolean goodEraseLib = false;
		
		//Set CGI vars for call
		ds.setSourceLib((String)libComboBox.getSelectedItem());
		ds.setDestLibGroup("USER");
		ds.setDestLibName("");
		ds.setDeleteSourceLib("YES");
	
		ds.setCHK_TEMP_BEHAVIOR("NO");
		ds.setCHK_OVERFLOW("NO");
		ds.setCHK_INVERSE("NO");
	
		//Make CGI call for MODIFY RATE LIBRARY action
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATE LIBRARY", Cina.rateLibManFrame);
	
		//If no errors
		if(!flagArray[0]){
		
			//If no reasons
			if(!flagArray[2]){
			
				//If no cautions
				if(!flagArray[1]){
				
					//Set boolean to true
					goodEraseLib = true;
				
				}
				
			}
			
		}
		
		//Return boolean					
		return goodEraseLib;
	
	}
	
	//Create erase lib dialog for erase lib report
	/**
	 * Creates the erase lib dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createEraseLibDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	eraseLibDialog = new JDialog(frame, "Rate Library Erased", true);
    	eraseLibDialog.setSize(350, 210);
    	eraseLibDialog.getContentPane().setLayout(new GridBagLayout());
		eraseLibDialog.setLocationRelativeTo(frame);
		
		JTextArea eraseLibTextArea = new JTextArea("", 5, 30);
		eraseLibTextArea.setLineWrap(true);
		eraseLibTextArea.setWrapStyleWord(true);
		eraseLibTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(eraseLibTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		eraseLibTextArea.setText(string);
		eraseLibTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					eraseLibDialog.setVisible(false);
					eraseLibDialog.dispose();
				}
			}
		);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		eraseLibDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		eraseLibDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(eraseLibDialog);
		
		//Show the dialog
		eraseLibDialog.setVisible(true);
	
	}
	
	//Create caution dialog to ask user if they want to erase this library for sure
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
		gbc1.gridwidth = 2;
		gbc1.insets = new Insets(0, 0, 0, 0);
		cautionButtonPanel.add(cautionLabel, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.gridwidth = 1;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		
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

}