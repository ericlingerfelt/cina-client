package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;


/**
 * The Class RateLibManSharedLibPanel.
 */
public class RateLibManSharedLibPanel extends WizardPanel implements ActionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The middle panel. */
	JPanel mainPanel, middlePanel; 
	
	/** The top label. */
	JLabel label1, label2, topLabel; 
		
	/** The lib combo box. */
	JComboBox libComboBox;
	
	/** The ok button. */
	JButton moveLibButton, yesButton, noButton, yesButtonLib, noButtonLib, okButton;
	
	/** The add missing inv rates dialog. */
	JDialog cautionDialog, moveLibDialog, cautionDialogLib, delayDialog, addMissingInvRatesDialog;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man shared lib panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManSharedLibPanel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(5);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		middlePanel = new JPanel(new GridBagLayout());
		
		libComboBox = new JComboBox();
		libComboBox.setFont(Fonts.textFont);
		
		label1 = new JLabel("Library to move to Shared folder: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("There are no User libraries to move.");
		label2.setFont(Fonts.textFont);
		
		topLabel = new JLabel("<html>With the Move Library to Shared Folder tool, you can move rate "
							 + "libraries from<p>your User storage to the Shared Library folder. "
							 + "Libraries in the Shared library folder<p>can be accessed by all Users of the suite.<p><br>"
							 + "Contact coordinator@nucastrodata.org if you wish to remove or replace a library<p>that you have moved into the Shared library folder.</html>");
		
		moveLibButton = new JButton("Move Library");
		moveLibButton.addActionListener(this);
		
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
		middlePanel.add(moveLibButton, gbc);
		
		gbc.gridwidth = 1;
		
		addWizardPanel("Rate Library Manager", "Move Library to Shared Folder", "1", "1");
		
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
	
		if(ae.getSource()==moveLibButton){
		
			if(goodLibInfoInverseCheck()){
	
				if(ds.getCurrentLibraryDataStructure().getAllInversesPresent()){

					String string = "You are about to move the User library " + (String)libComboBox.getSelectedItem() + " to the Shared library folder."
									+ " "
									+ "Libraries in the Shared library folder can be accessed by all Users of the suite.";
		
					createCautionDialog(string, Cina.rateLibManFrame);

				}else{
				
					String string = "The library you have selected for exporting does not have a complete set of "
										+ "reaction rates as determined by detailed balance (inverse rates)."
										+ " This is a requirement for library export. Do you want to calculate these reactions and add them to this library?";
										
					createCautionDialogLib(string, Cina.rateLibManFrame);					
				
				}
			
			}
		
		}else if(ae.getSource()==yesButton){
			
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			if(goodMoveLib()){
			
				if(libComboBox.getItemCount()>1){
			
					libComboBox.removeItem(libComboBox.getSelectedItem());
				
				}else{
				
					middlePanel.remove(libComboBox);
			
					middlePanel.remove(label1);
					
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.anchor = GridBagConstraints.WEST;
					gbc.insets = new Insets(20, 5, 20, 5);
					
					middlePanel.add(label2, gbc);
					
					moveLibButton.setEnabled(false);
				
				}
			
				String string = ds.getSharedLibReport();
				
				createMoveLibDialog(string, Cina.rateLibManFrame);
			
			}
	
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}else if(ae.getSource()==yesButtonLib){
		
			cautionDialogLib.setVisible(false);
			cautionDialogLib.dispose();
		
			openDelayDialog(Cina.rateLibManFrame);
		
			ds.setLibraryName((String)libComboBox.getSelectedItem());
			boolean goodInverseRates = Cina.cinaCGIComm.doCGICall("ADD MISSING INV RATES", Cina.rateLibManFrame);
		
			closeDelayDialog();
		
			if(goodInverseRates){
			
				String string = ds.getAddMissingInvRatesReport();
			
				createAddMissingInvRatesDialog(string, Cina.rateLibManFrame);

			}
		
		}else if(ae.getSource()==noButtonLib){
		
			cautionDialogLib.setVisible(false);
			cautionDialogLib.dispose();
		
		}else if(ae.getSource()==okButton){
		
			addMissingInvRatesDialog.setVisible(false);
			addMissingInvRatesDialog.dispose();
			
			String string = "You are about to move the User library " + (String)libComboBox.getSelectedItem() + " to the Shared library folder."
									+ " "
									+ "Libraries in the Shared library folder can be accessed by all Users of the suite.";
		
			createCautionDialog(string, Cina.rateLibManFrame);
		
		}
		
		//Cina.setFrameColors(Cina.rateLibManFrame);
		
		validate();
		
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
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		libComboBox.removeAllItems();
		
		if(ds.getNumberUserLibraryDataStructures()!=0){
		
			for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
		
				libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
				
			}
		
			libComboBox.setSelectedIndex(0);
		
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
			
			moveLibButton.setEnabled(true);
		
		}else{
			
			middlePanel.remove(libComboBox);
			
			middlePanel.remove(label1);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(20, 5, 20, 5);
			
			middlePanel.add(label2, gbc);
			
			moveLibButton.setEnabled(false);
		
		}
		
		validate();

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Good move lib.
	 *
	 * @return true, if successful
	 */
	public boolean goodMoveLib(){
	
		boolean goodMoveLib = false;
		
		ds.setLibraryName((String)libComboBox.getSelectedItem());
	
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("SHARE RATE LIBRARY", Cina.rateLibManFrame);
	
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
				
					goodMoveLib = true;
				
				}
				
			}
			
		}
								
		return goodMoveLib;
	
	}
	
	/**
	 * Creates the caution dialog lib.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createCautionDialogLib(String string, Frame frame){
	
		GridBagConstraints gbc1 = new GridBagConstraints();
			
		//Create a new JDialog object
    	cautionDialogLib = new JDialog(frame, "Caution!", true);
    	cautionDialogLib.setSize(320, 215);
    	cautionDialogLib.getContentPane().setLayout(new GridBagLayout());
		cautionDialogLib.setLocationRelativeTo(frame);
		
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
		
		yesButtonLib = new JButton("Yes");
		yesButtonLib.setFont(Fonts.buttonFont);
		yesButtonLib.addActionListener(this);
		
		noButtonLib = new JButton("No");
		noButtonLib.setFont(Fonts.buttonFont);
		noButtonLib.addActionListener(this);
		
		JPanel cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButtonLib, gbc1);
		
		gbc1.gridx = 1;
		gbc1.gridy = 0;
		
		cautionButtonPanel.add(noButtonLib, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		cautionDialogLib.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		cautionDialogLib.getContentPane().add(cautionButtonPanel, gbc1);
		
		//Cina.setFrameColors(cautionDialogLib);

		cautionDialogLib.setVisible(true);

	}
	
	/**
	 * Creates the move lib dialog.
	 *
	 * @param string the string
	 * @param frame the frame
	 */
	public void createMoveLibDialog(String string, JFrame frame){
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	moveLibDialog = new JDialog(frame, "Rate Library Moved", true);
    	moveLibDialog.setSize(350, 210);
    	moveLibDialog.getContentPane().setLayout(new GridBagLayout());
		moveLibDialog.setLocationRelativeTo(frame);
		
		JTextArea moveLibTextArea = new JTextArea("", 5, 30);
		moveLibTextArea.setLineWrap(true);
		moveLibTextArea.setWrapStyleWord(true);
		moveLibTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(moveLibTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		moveLibTextArea.setText(string);
		moveLibTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		JButton okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				moveLibDialog.setVisible(false);
				moveLibDialog.dispose();
			}
		
		});
		
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(3, 3, 0, 3);
		
		moveLibDialog.getContentPane().add(sp, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		
		gbc1.insets = new Insets(5, 3, 0, 3);
		moveLibDialog.getContentPane().add(okButton, gbc1);
		
		//Cina.setFrameColors(moveLibDialog);
		
		//Show the dialog
		moveLibDialog.setVisible(true);
	
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
		cautionTextArea.setFont(Fonts.textFont);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = string;

		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		JLabel cautionLabel = new JLabel("Do you wish to continue?");
		cautionLabel.setFont(Fonts.textFont);	
		
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
		okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
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

}