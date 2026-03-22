package org.nucastrodata.rate.ratelibman;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.event.*;
import java.util.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;

/**
 * The Class RateLibManCustomLibCreation1Panel.
 */
public class RateLibManCustomLibCreation1Panel extends WizardPanel implements ActionListener{
	
	//Declare GBC
	/** The gbc. */
	GridBagConstraints gbc;
	
	//Declare panels
	/** The field panel. */
	JPanel mainPanel, buttonPanel, fieldPanel; 
	
	/** The out list. */
	JList libList, outList;
	
	/** The out list model. */
	DefaultListModel libListModel, outListModel;
	
	/** The no button. */
	JButton addLibButton, removeLibButton, yesButton, noButton;
	
	/** The sp2. */
	JScrollPane sp1, sp2;
	
	/** The out list label. */
	JLabel libListLabel, outListLabel;
	
	/** The top label. */
	WordWrapLabel topLabel;
	
	/** The save lib as label. */
	JLabel saveLibAsLabel;
	
	/** The save lib as field. */
	JTextField saveLibAsField;
	
	/** The add missing inv rates dialog. */
	JDialog delayDialog, cautionDialog, addMissingInvRatesDialog;
	
	/** The good lib merge. */
	boolean goodLibMerge;
	
	/** The ds. */
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man custom lib creation1 panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManCustomLibCreation1Panel(RateLibManDataStructure ds){
		
		this.ds = ds;
		
		//Set the current panel index to 1 in the parent frame
		Cina.rateLibManFrame.setCurrentFeatureIndex(3);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
		
		addWizardPanel("Rate Library Manager", "Merge Existing Libraries", "1", "2");
		
		if(!Cina.cinaMainDataStructure.getUser().equals("guest")){
		
			topLabel = new WordWrapLabel();
			topLabel.setText("Welcome to the Merge Existing Libraries tool. "
					+ "<p>With this tool you can create a new reaction rate library by performing a prioritized "
					+ "merge of several rate libraries.");
		}else{
		
			topLabel = new WordWrapLabel();
			topLabel.setText("Welcome to the Merge Existing Libraries tool. "
					+ "<p>With this tool you can create a new reaction rate library by performing a prioritized "
					+ "merge of several rate libraries. "
					+ "Guest users can not merge libraries. They can demo this tool and receive a "
					+ "representative report in Step 2.");
		
		}
		
		saveLibAsField = new JTextField(20);
		
		saveLibAsLabel = new JLabel("Save new library as: ");
		saveLibAsLabel.setFont(Fonts.textFont);
		
		fieldPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		fieldPanel.add(saveLibAsLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		fieldPanel.add(saveLibAsField, gbc);
		
		libListLabel = new JLabel("Select a library to merge");
		
		libListModel = new DefaultListModel();
		
		libList = new JList(libListModel);
		libList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sp1 = new JScrollPane(libList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		outListLabel = new JLabel("Highest to lowest priority");
		
		outListModel = new DefaultListModel();
		
		outList = new JList(outListModel);
		outList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sp2 = new JScrollPane(outList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		addLibButton = new JButton("Add Library >>");
		addLibButton.setFont(Fonts.buttonFont);
		addLibButton.addActionListener(this);
		
		removeLibButton = new JButton("<< Remove Library");
		removeLibButton.setFont(Fonts.buttonFont);
		removeLibButton.addActionListener(this);
		
		buttonPanel.add(addLibButton);
		buttonPanel.add(removeLibButton);
		
		double gap = 10;
		double[] column = {20, TableLayoutConstants.FILL, gap
							, TableLayoutConstants.FILL, gap
							, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, 20};
		setLayout(new TableLayout(column, row));
		
		add(topLabel,         "1, 1, 5, 1, f, c");
		add(fieldPanel,       "1, 3, 5, 3, c, c");
		add(libListLabel,     "1, 5, c, c");
		add(sp1,              "1, 7, f, f");
		add(outListLabel,     "5, 5, c, c");
		add(sp2,              "5, 7, f, f");
		add(buttonPanel,      "3, 7, c, c");
		
	}
	
	/**
	 * Check base libs.
	 *
	 * @return true, if successful
	 */
	public boolean checkBaseLibs(){

		if(outListModel.contains(saveLibAsField.getText())){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * Check save as lib field.
	 *
	 * @return true, if successful
	 */
	public boolean checkSaveAsLibField(){
		if(saveLibAsField.getText().equals("")){
			return false;
		}else{	
			return true;
		}
	}
	
	/**
	 * Check public lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkPublicLibName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equalsIgnoreCase(ds.getPublicLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equalsIgnoreCase(ds.getSharedLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Check overwrite lib name.
	 *
	 * @return true, if successful
	 */
	public boolean checkOverwriteLibName(){
	
		boolean goodName = true;
	
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			if(saveLibAsField.getText().equals(ds.getUserLibraryDataStructureArray()[i].getLibName())){
			
				goodName = false;
			
			}
		
		}
	
		return goodName;
	
	}
	
	/**
	 * Rates selected.
	 *
	 * @return true, if successful
	 */
	public boolean ratesSelected(){
		if(outListModel.getSize()<2){
			return false;
		}else{
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==addLibButton){
			
			if(libList.getSelectedValue()!=null){

				if(saveLibAsField.getText().equals(libList.getSelectedValue())){
			
					String string = "You have selected a base library that has the same name as your new library. Please select a different merging library.";
				
					Dialogs.createExceptionDialog(string, Cina.rateLibManFrame);
			
				}else if(goodLibInfoInverseCheck()){
	
					if(ds.getCurrentLibraryDataStructure().getLibType().equals("USER")){
	
						if(ds.getCurrentLibraryDataStructure().getAllInversesPresent()){
		
							outListModel.addElement(libList.getSelectedValue());
					
							libListModel.removeElement(libList.getSelectedValue());
		
						}else{
						
							String string = "The library you have selected for merging does not have a complete set of "
												+ "reaction rates as determined by detailed balance (inverse rates)."
												+ " This is a requirement for library merger. Do you want to calculate these reactions and add them to this library?";
												
							createCautionDialog(string, Cina.rateLibManFrame);					
						
						}
					
					}else{
						
						outListModel.addElement(libList.getSelectedValue());
					
						libListModel.removeElement(libList.getSelectedValue());
					
					}
				
				}
				
			}
			
		}else if(ae.getSource()==removeLibButton){
		
			libListModel.addElement(outList.getSelectedValue());
		
			outListModel.removeElement(outList.getSelectedValue());
		
		}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
			openDelayDialog(Cina.rateLibManFrame);
		
			ds.setLibraryName((String)libList.getSelectedValue());
			boolean goodInverseRates = Cina.cinaCGIComm.doCGICall("ADD MISSING INV RATES", Cina.rateLibManFrame);
		
			closeDelayDialog();
		
			if(goodInverseRates){
			
				String string = ds.getAddMissingInvRatesReport();
			
				createAddMissingInvRatesDialog(string, Cina.rateLibManFrame);
					
				outListModel.addElement(libList.getSelectedValue());
		
				libListModel.removeElement(libList.getSelectedValue());
			
			}
		
		}else if(ae.getSource()==noButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
		}
	
	}
	
	/**
	 * Good lib merge.
	 *
	 * @return true, if successful
	 */
	public boolean goodLibMerge(){
		
		goodLibMerge = false;
		
		final SwingWorker worker = new SwingWorker(){
		
			public Object construct(){
				
				ds.setDeleteSourceLib("NO");
				
				ds.setCHK_TEMP_BEHAVIOR("NO");
				ds.setCHK_OVERFLOW("NO");
				ds.setCHK_INVERSE("NO");
				
				boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("MODIFY RATE LIBRARY", Cina.rateLibManFrame);
			
				if(!flagArray[0]){
				
					if(!flagArray[2]){
					
						if(!flagArray[1]){
							
							Cina.rateLibManFrame.rateLibManCustomLibCreation1Panel.goodLibMerge = true;
							
						}
						
					}
					
				}	
				
				return new Object();
				
			}
			
			public void finished(){
				
				closeDelayDialog();
				
				Cina.rateLibManFrame.libCreation1GoodLibMerge();
				
				//Cina.setFrameColors(Cina.rateLibManFrame);
				
				Cina.rateLibManFrame.validate();
				
			}
			
		};
		
		worker.start();
		
		return goodLibMerge;
		
	}
	
	/**
	 * Good lib info inverse check.
	 *
	 * @return true, if successful
	 */
	public boolean goodLibInfoInverseCheck(){
		
		boolean goodLibInfoInverseCheck = false;
		
		ds.setLibraryName((String)libList.getSelectedValue());
		
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
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		ds.setDestCustomLibName(saveLibAsField.getText());
		
		ds.setDestLibGroup("USER");
		
		ds.setSourceCustomLib(getSRC_LIB());
	
	}
	
	/**
	 * Gets the sR c_ lib.
	 *
	 * @return the sR c_ lib
	 */
	public String getSRC_LIB(){
	
		String string = "";
		
		for(int i=0; i<outListModel.size(); i++){
		
			if(i!=outListModel.size()-1){
		
				string = string + outListModel.elementAt(i) + "\u0009";
		
			}else{
			
				string = string + outListModel.elementAt(i);
			
			}
		
		}
		
		return string;
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		String[] sourceLibArray = new String[ds.getNumberPublicLibraryDataStructures()
										+ ds.getNumberSharedLibraryDataStructures()
										+ ds.getNumberUserLibraryDataStructures()];
		
		int sourceLibArrayCounter = 0;
		
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
	
			libListModel.addElement(ds.getPublicLibraryDataStructureArray()[i].getLibName());
			
			sourceLibArray[sourceLibArrayCounter] = "Public";
			
			sourceLibArrayCounter++;
	
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
	
			libListModel.addElement(ds.getSharedLibraryDataStructureArray()[i].getLibName());
			
			sourceLibArray[sourceLibArrayCounter] = "Shared";
			
			sourceLibArrayCounter++;
	
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
	
			libListModel.addElement(ds.getUserLibraryDataStructureArray()[i].getLibName());
	
			sourceLibArray[sourceLibArrayCounter] = "User";
			
			sourceLibArrayCounter++;
			
		}
		
		setListModelLibs();
		
		saveLibAsField.setText(ds.getDestCustomLibName());
		
	}
	
	/**
	 * Sets the list model libs.
	 */
	public void setListModelLibs(){
	
		String string = ds.getSourceCustomLib();
		
		StringTokenizer st = new StringTokenizer(string, "\u0009");
		
		int numberTokens = st.countTokens();
		
		String currentToken = "";
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();

			libListModel.removeElement(currentToken);
			outListModel.addElement(currentToken);
		
		}
	
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

		String delayString = "Please be patient while new library is constructed. DO NOT operate the Rate Library Manager at this time!";
		
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
	
}