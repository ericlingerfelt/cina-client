package org.nucastrodata.data.datamass;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.io.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.datamass.DataMassHelpFrame;
import org.nucastrodata.io.FileGetter;


/**
 * The Class DataMassTheoryModelPanel.
 */
public class DataMassTheoryModelPanel extends WizardPanel implements ItemListener, ActionListener{

	/** The theory model combo box. */
	JComboBox theoryModelComboBox;
	
	/** The gbc. */
	GridBagConstraints gbc;

	/** The theory upload radio button. */
	JRadioButton theorySelectRadioButton, theoryUploadRadioButton;

	/** The theory button group. */
	ButtonGroup theoryButtonGroup;
	
	/** The help button. */
	JButton theoryBrowseButton, helpButton;

	/** The theory field. */
	JTextField theoryField;

	/** The notes text area. */
	JTextArea notesTextArea;

	/** The good theory data upload. */
	boolean goodTheoryDataDownload, goodTheoryDataUpload;

	/** The delay dialog. */
	JDialog delayDialog;

	/** The notes label. */
	JLabel notesLabel;

	/** The sp. */
	JScrollPane sp;

	/** The ds. */
	private DataMassDataStructure ds;

	/**
	 * Instantiates a new data mass theory model panel.
	 *
	 * @param ds the ds
	 */
	public DataMassTheoryModelPanel(DataMassDataStructure ds){
	
		this.ds = ds;
	
		Cina.dataMassFrame.setCurrentPanelIndex(2);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		addWizardPanel("Mass Model Evaluator", "Select Theoretical Mass Model", "2", "4");
		
		JLabel label1 = new JLabel("<html>Select a THEORETICAL MASS MODEL from the dropdown menu or upload<p>a theoretical mass model below :</html>");
		
		theoryModelComboBox = new JComboBox();
		theoryModelComboBox.setFont(Fonts.textFont);
		theoryModelComboBox.addItemListener(this);
		
		theorySelectRadioButton = new JRadioButton("select theoretical mass model", true);
		theorySelectRadioButton.setFont(Fonts.textFont);
		theorySelectRadioButton.addItemListener(this);
		
		theoryUploadRadioButton = new JRadioButton("upload theoretical mass model", false);
		theoryUploadRadioButton.setFont(Fonts.textFont);
		theoryUploadRadioButton.addItemListener(this);
		
		theoryButtonGroup = new ButtonGroup();
		theoryButtonGroup.add(theorySelectRadioButton);
		theoryButtonGroup.add(theoryUploadRadioButton);
	
		theoryBrowseButton = new JButton("Browse...");
		theoryBrowseButton.setFont(Fonts.buttonFont);
		theoryBrowseButton.setEnabled(false);
		theoryBrowseButton.addActionListener(this);
		
		helpButton = new JButton("Help on File Format");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this);
		
		theoryField = new JTextField(10);
		theoryField.setEditable(false);
		
		notesLabel = new JLabel("Enter notes in the area below :");
		notesLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea("", 4, 60);
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		
		sp = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(425, 100));
		
		JPanel notesPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		
		notesPanel.add(notesLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		
		notesPanel.add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 15, 5);
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(label1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(theorySelectRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(theoryModelComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(theoryUploadRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(theoryField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		
		mainPanel.add(theoryBrowseButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		
		mainPanel.add(helpButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(15, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(notesPanel, gbc);
		
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==theoryBrowseButton){
		
			JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				theoryField.setText(file.getName());
				
				if(!uploadTheoryFile(file)){
				
					goodTheoryDataUpload = false;
				
					String string = "The file you have uploaded is empty. Please choose another.";
					
					Dialogs.createExceptionDialog(string, Cina.dataMassFrame);
					
					theoryField.setText("");
				
				}else{
					
					ds.setTheoryFile(file);
				
					goodTheoryDataUpload = true;
					
				}
			
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==helpButton){
		
			if(Cina.dataMassFrame.dataMassHelp1Frame==null){
	       	
       			Cina.dataMassFrame.dataMassHelp1Frame = new DataMassHelpFrame();
       		
	       	}else{
	       	
	       		Cina.dataMassFrame.dataMassHelp1Frame.setVisible(true);
	       	
	       	}
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==theorySelectRadioButton){
		
			if(!theorySelectRadioButton.isSelected()){
			
				notesLabel.setText("Enter notes in the area below :");
				notesTextArea.setEditable(true);
				
				notesTextArea.setText(ds.getTheoryNotes());
				notesTextArea.setCaretPosition(0);
			
				theoryModelComboBox.setEnabled(false);
				theoryBrowseButton.setEnabled(true);
						
			}else{
				
				notesLabel.setText("Notes for this mass model are shown below: ");
				notesTextArea.setEditable(false);
			
				notesTextArea.setText(ds.getDataInfoArray()[theoryModelComboBox.getSelectedIndex()]);
				notesTextArea.setCaretPosition(0);
			
				theoryModelComboBox.setEnabled(true);
				theoryBrowseButton.setEnabled(false);
			
			}
		
		}else if(ie.getSource()==theoryModelComboBox){
		
			notesTextArea.setText(ds.getDataInfoArray()[theoryModelComboBox.getSelectedIndex()]);
			notesTextArea.setCaretPosition(0);
		
		}
	
	}
	
	/**
	 * Upload theory file.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	public boolean uploadTheoryFile(File file){
	
		boolean goodTheoryFile = true;

		int i = (int)file.length();
		
		byte[] stringBuffer = new byte[i];
		
		try{
		
			FileInputStream fileInputStream = new FileInputStream(file);
			
			fileInputStream.read(stringBuffer);
			
			fileInputStream.close();
		
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		
		String string = new String(stringBuffer);
		
		if(string.equals("")){
		
			goodTheoryFile = false;
		
		}
		
		parseTheoryDataString(string);
		
		return goodTheoryFile;
	
	}
	
	/**
	 * Good theory data download.
	 *
	 * @return true, if successful
	 */
	public boolean goodTheoryDataDownload(){
	
		goodTheoryDataDownload = true;
		
		final SwingWorker worker = new SwingWorker(){
		
			public Object construct(){
				
				if(Cina.dataMassFrame.dataMassTheoryModelPanel.downloadTheoryData()){
					
					Cina.dataMassFrame.dataMassTheoryModelPanel.goodTheoryDataDownload = true;
				
				}else{
				
					Cina.dataMassFrame.dataMassTheoryModelPanel.goodTheoryDataDownload = false;
				
				}
							
				return new Object();
				
			}
			
			public void finished(){
				
				closeDelayDialog();
				
				Cina.dataMassFrame.massModel2GoodTheoryDataDownload();
				
				//Cina.setFrameColors(Cina.dataMassFrame);
				
				Cina.dataMassFrame.validate();
				
			}
			
		};
		
		worker.start();
		
		return goodTheoryDataDownload;
	
	}

	/**
	 * Good theory data upload.
	 *
	 * @return true, if successful
	 */
	public boolean goodTheoryDataUpload(){
		
		return goodTheoryDataUpload;
	
	}
	
	/**
	 * Download theory data.
	 *
	 * @return true, if successful
	 */
	public boolean downloadTheoryData(){
	
		boolean temp = false;
					
			String dataString = new String(FileGetter.getFileByName("MassModelData/" 
					+ ds.getTheoryModel()));
			parseTheoryDataString(dataString);
			
			temp = true;
			

		return temp;
		
	}
	
	/**
	 * Parses the theory data string.
	 *
	 * @param string the string
	 */
	public void parseTheoryDataString(String string){
	
		try{
	
			StringTokenizer st = new StringTokenizer(string, "\n");
			
			int numberTokens = st.countTokens();
			int counter = 0;
			
			for(int i=0; i<numberTokens; i++){
			
				String currentToken = st.nextToken();
				
				if(currentToken.indexOf("#")==-1){
				
					counter++;
				
				}
		
			}
			
			st = new StringTokenizer(string, "\n");
			
			Point[] outputPointArray = new Point[counter];
			double[] outputDoubleArray = new double[counter];
			
			counter = 0;
			
			for(int i=0; i<numberTokens; i++){
			
				String currentToken = st.nextToken();
				
				if(currentToken.indexOf("#")==-1){
				
					StringTokenizer stInner = new StringTokenizer(currentToken);
					
					int ZValue = Integer.valueOf(stInner.nextToken()).intValue();
					int NValue = Integer.valueOf(stInner.nextToken()).intValue();
					
					double	massValue = Double.valueOf(stInner.nextToken()).doubleValue();
					
					outputPointArray[counter] = new Point(ZValue, NValue);
					outputDoubleArray[counter] = massValue;
	
					counter++;
					
				}
				
			}
			
			ds.getTheoryModelDataStructure().setZNArray(outputPointArray);
			ds.getTheoryModelDataStructure().setMassArray(outputDoubleArray);
			
			if(theorySelectRadioButton.isSelected()){
			
				if(theoryModelComboBox.getSelectedItem().toString().indexOf("(")!=-1){
			
					ds.getTheoryModelDataStructure().setModelName(theoryModelComboBox.getSelectedItem().toString().substring(0, theoryModelComboBox.getSelectedItem().toString().indexOf("(")-1));
					
				}else{
				
					ds.getTheoryModelDataStructure().setModelName(theoryModelComboBox.getSelectedItem().toString());
						
				}
				
			}else{
			
				ds.getTheoryModelDataStructure().setModelName(theoryField.getText());
			
			}
		
		}catch(NumberFormatException nfe){
		
			AttentionDialog.createDialog(Cina.dataMassFrame, 
					"The data file you have uploaded is not in the correct format. "
					+ "Please click on the <i>Help on File Format</i> button to receive more information on the correct format.");
		
		}
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		if(theorySelectRadioButton.isSelected()){

			if(theoryModelComboBox.getSelectedItem().toString().indexOf("(")!=-1){
			
				ds.setTheoryModel(theoryModelComboBox.getSelectedItem().toString().substring(0, theoryModelComboBox.getSelectedItem().toString().indexOf("(")-1));
				
			}else{
			
				ds.setTheoryModel(theoryModelComboBox.getSelectedItem().toString());
					
			}
		
			ds.setTheoryUploaded(false);
		
		}else{
		
			ds.setTheoryModel(theoryField.getText());
		
			ds.setTheoryUploaded(true);
		
		}
		
		if(theoryUploadRadioButton.isSelected()){
		
			ds.setTheoryNotes(notesTextArea.getText());
		
		}
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		theoryModelComboBox.removeAllItems();
	
		for(int i=0; i<ds.getTheoryModelList().length; i++){
		
			theoryModelComboBox.addItem(ds.getTheoryModelList()[i]);
		
		}

		if(!Cina.cinaMainDataStructure.getUser().equals("hiroyuki")){
		
			theoryModelComboBox.removeItem("MS96");
			theoryModelComboBox.removeItem("HFB2");
				
		}

		if(ds.getTheoryUploaded()){
			
			theorySelectRadioButton.setSelected(false);
			theoryUploadRadioButton.setSelected(true);
			theoryModelComboBox.setEnabled(false);
			theoryBrowseButton.setEnabled(true);
			theoryField.setText(ds.getTheoryModel());
			
			notesLabel.setText("Enter notes in the area below :");
			notesTextArea.setEditable(true);

			notesTextArea.setText(ds.getTheoryNotes());
			notesTextArea.setCaretPosition(0);
			
			uploadTheoryFile(ds.getTheoryFile());
			goodTheoryDataUpload = true;
		
		}else{
		
			theorySelectRadioButton.setSelected(true);
			theoryUploadRadioButton.setSelected(false);
			theoryModelComboBox.setEnabled(true);
			theoryBrowseButton.setEnabled(false);
			theoryModelComboBox.setSelectedItem((String)ds.getTheoryModel());
		
			notesLabel.setText("Notes for this mass model are shown below :");
			notesTextArea.setEditable(false);
			
			notesTextArea.setText(ds.getDataInfoArray()[theoryModelComboBox.getSelectedIndex()]);
			notesTextArea.setCaretPosition(0);
		}
	
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
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while the theoretical mass model you have requested is downloaded and parsed. DO NOT operate the Nuclear Data Evaluator's Toolkit at this time!";
		
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