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
import org.nucastrodata.io.FileGetter;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.datamass.DataMassHelpFrame;


/**
 * The Class DataMassRefModelPanel.
 */
public class DataMassRefModelPanel extends WizardPanel implements ItemListener, ActionListener{

	/** The exp model combo box. */
	private JComboBox expModelComboBox;	
	
	/** The help button. */
	private JButton expBrowseButton, helpButton;
	
	/** The exp field. */
	private JTextField expField;
	
	/** The notes text area. */
	private JTextArea notesTextArea;
	
	/** The notes label. */
	private JLabel notesLabel;
	
	/** The good exp data upload. */
	protected boolean goodExpDataDownload, goodExpDataUpload;
	
	/** The delay dialog. */
	protected JDialog delayDialog;
	
	/** The exp upload radio button. */
	protected JRadioButton expSelectRadioButton, expUploadRadioButton;
	
	/** The ds. */
	private DataMassDataStructure ds;
	
	/**
	 * Instantiates a new data mass ref model panel.
	 *
	 * @param ds the ds
	 */
	public DataMassRefModelPanel(DataMassDataStructure ds){
	
		this.ds = ds;
	
		Cina.dataMassFrame.setCurrentPanelIndex(3);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		addWizardPanel("Mass Model Evaluator", "Select Reference Mass Model", "3", "4");
		
		JLabel label1 = new JLabel("<html>Select a REFERENCE MASS MODEL from the dropdown menu or upload<p>a reference mass model below :</html>");

		expModelComboBox = new JComboBox();
		expModelComboBox.setFont(Fonts.textFont);
		expModelComboBox.addItemListener(this);
		
		expSelectRadioButton = new JRadioButton("select reference mass model", true);
		expSelectRadioButton.setFont(Fonts.textFont);
		expSelectRadioButton.addItemListener(this);
		
		expUploadRadioButton = new JRadioButton("upload reference mass model", false);
		expUploadRadioButton.setFont(Fonts.textFont);
		expUploadRadioButton.addItemListener(this);

		ButtonGroup expButtonGroup = new ButtonGroup();
		expButtonGroup.add(expSelectRadioButton);
		expButtonGroup.add(expUploadRadioButton);
		
		expBrowseButton = new JButton("Browse...");
		expBrowseButton.setFont(Fonts.buttonFont);
		expBrowseButton.setEnabled(false);
		expBrowseButton.addActionListener(this);
		
		helpButton = new JButton("Help on File Format");
		helpButton.setFont(Fonts.buttonFont);
		helpButton.addActionListener(this);
		
		expField = new JTextField(10);
		expField.setEditable(false);
		
		notesLabel = new JLabel("Enter notes in the area below :");
		notesLabel.setFont(Fonts.textFont);
		
		notesTextArea = new JTextArea("", 4, 60);
		notesTextArea.setLineWrap(true);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setFont(Fonts.textFont);
		
		JScrollPane sp = new JScrollPane(notesTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		mainPanel.add(expSelectRadioButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(expModelComboBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(expUploadRadioButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(expField, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		mainPanel.add(expBrowseButton, gbc);
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
	
	/**
	 * Good data sets.
	 *
	 * @return true, if successful
	 */
	protected boolean goodDataSets(){
	
		if(ds.getExpModelDataStructure().getModelName().equals(ds.getTheoryModelDataStructure().getModelName())){
			return false;
		}else{
			return true;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==expBrowseButton){
		
			JFileChooser fileDialog = new JFileChooser(Cina.cinaMainDataStructure.getAbsolutePath());

			int returnVal = fileDialog.showOpenDialog(this); 
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
		
				File file = fileDialog.getSelectedFile();
				
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());

				expField.setText(file.getName());
				
				if(!uploadExpFile(file)){
				
					goodExpDataUpload = false;
				
					String string = "The file you have uploaded is empty. Please choose another.";
					
					Dialogs.createExceptionDialog(string, Cina.dataMassFrame);
					
					expField.setText("");
				
				}else{
					
					ds.setExpFile(file);
					
					goodExpDataUpload = true;
				
				}
			
			}else if(returnVal==JFileChooser.CANCEL_OPTION){
		
				Cina.cinaMainDataStructure.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		
			}
			
		}else if(ae.getSource()==helpButton){
		
			if(Cina.dataMassFrame.dataMassHelp2Frame==null){
	       	
       			Cina.dataMassFrame.dataMassHelp2Frame = new DataMassHelpFrame();
       		
	       	}else{
	       	
	       		Cina.dataMassFrame.dataMassHelp2Frame.setVisible(true);
	       	
	       	}
		
		}
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==expSelectRadioButton){
		
			if(!expSelectRadioButton.isSelected()){
			
				notesLabel.setText("Enter notes in the area below :");
				notesTextArea.setEditable(true);
			
				notesTextArea.setText(ds.getExpNotes());
				notesTextArea.setCaretPosition(0);
			
				expModelComboBox.setEnabled(false);
				expBrowseButton.setEnabled(true);
						
			}else{
			
				notesLabel.setText("Notes for this mass model are shown below: ");
				notesTextArea.setEditable(false);
			
				notesTextArea.setText(ds.getDataInfoArray()[expModelComboBox.getSelectedIndex()]);
				notesTextArea.setCaretPosition(0);
				
				expModelComboBox.setEnabled(true);
				expBrowseButton.setEnabled(false);
			
			}
		
		}else if(ie.getSource()==expModelComboBox){
		
			notesTextArea.setText(ds.getDataInfoArray()[expModelComboBox.getSelectedIndex()]);
			notesTextArea.setCaretPosition(0);
		}
	
	}
	
	/**
	 * Upload exp file.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	public boolean uploadExpFile(File file){
	
		boolean goodExpFile = true;

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
		
			goodExpFile = false;
		
		}
		
		parseExpDataString(string);
		
		return goodExpFile;
	
	}
	
	/**
	 * Good exp data download.
	 *
	 * @return true, if successful
	 */
	public boolean goodExpDataDownload(){
	
		goodExpDataDownload = true;
		
		final SwingWorker worker = new SwingWorker(){
		
			public Object construct(){
				
				if(Cina.dataMassFrame.dataMassRefModelPanel.downloadExpData()){
					
					Cina.dataMassFrame.dataMassRefModelPanel.goodExpDataDownload = true;
				
				}else{
				
					Cina.dataMassFrame.dataMassRefModelPanel.goodExpDataDownload = false;
				
				}
							
				return new Object();
				
			}
			
			public void finished(){
				
				Cina.dataMassFrame.massModel3GoodExpDataDownload();
				
				//Cina.setFrameColors(Cina.dataMassFrame);
				
				Cina.dataMassFrame.validate();
				
			}
			
		};
		
		worker.start();
		
		return goodExpDataDownload;
	
	}
	
	/**
	 * Good exp data upload.
	 *
	 * @return true, if successful
	 */
	public boolean goodExpDataUpload(){return goodExpDataUpload;}
	
	/**
	 * Download exp data.
	 *
	 * @return true, if successful
	 */
	public boolean downloadExpData(){
	
		boolean temp = false;

			String dataString = new String(FileGetter.getFileByName("MassModelData/" + ds.getExpModel()));
			parseExpDataString(dataString);
			
			temp = true;

		return temp;
		
	}
	
	/**
	 * Parses the exp data string.
	 *
	 * @param string the string
	 */
	public void parseExpDataString(String string){
	
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
					
					double massValue = Double.valueOf(stInner.nextToken()).doubleValue();
					
					outputPointArray[counter] = new Point(ZValue, NValue);
					outputDoubleArray[counter] = massValue;
	
					counter++;
					
				}
				
			}
			
			ds.getExpModelDataStructure().setZNArray(outputPointArray);
			ds.getExpModelDataStructure().setMassArray(outputDoubleArray);
			
			if(expSelectRadioButton.isSelected()){
	
				if(expModelComboBox.getSelectedItem().toString().indexOf("(")!=-1){
			
					ds.getExpModelDataStructure().setModelName(expModelComboBox.getSelectedItem().toString().substring(0, expModelComboBox.getSelectedItem().toString().indexOf("(")-1));
					
				}else{
				
					ds.getExpModelDataStructure().setModelName(expModelComboBox.getSelectedItem().toString());
						
				}
			
			}else{
			
				ds.getExpModelDataStructure().setModelName(expField.getText());
			
			}
			
		}catch(NumberFormatException nfe){
			
			AttentionDialog.createDialog(Cina.dataMassFrame, 
					"The data file you have uploaded is not in the correct format. "
					+ "Please click on the <i>Help on File Format</i> button to receive more information on the correct format.");
		
		}
		
	}
	
	/**
	 * Parses the model data structures.
	 */
	public void parseModelDataStructures(){
	
		ds.setPointVector(getPointVector());
		
		////////////////////////////////////////1-D///////////////////////////////////////////
		ds.setZArray(getZArray());
		ds.setZmin(getZmin());
		ds.setZmax(getZmax());

		ds.setNArray(getNArray());
		ds.setNmin(getNmin());
		ds.setNmax(getNmax());

		ds.setAArray(getAArray());
		ds.setAmin(getAmin());
		ds.setAmax(getAmax());
		///////////////////////////////////////////////////////////////////////////////////////////
		
		ds.setDiffmin(getDiffmin());
		ds.setDiffmax(getDiffmax());

		ds.setAbsDiffArray(getAbsDiffArray());
		
		ds.setAbsDiffmin(getAbsDiffmin());
		ds.setAbsDiffmax(getAbsDiffmax());
		
		///////////////////////////////////////1-D/////////////////////////////////////////////
		ds.setZArrayRMS(getZArrayRMS());
		ds.setZminRMS(getZminRMS());
		ds.setZmaxRMS(getZmaxRMS());

		ds.setNArrayRMS(getNArrayRMS());
		ds.setNminRMS(getNminRMS());
		ds.setNmaxRMS(getNmaxRMS());

		ds.setAArrayRMS(getAArrayRMS());
		ds.setAminRMS(getAminRMS());
		ds.setAmaxRMS(getAmaxRMS());

		ds.setRMSZArray(getRMSZArray());
		ds.setRMSZmin(getRMSZmin());
		ds.setRMSZmax(getRMSZmax());

		ds.setRMSNArray(getRMSNArray());
		ds.setRMSNmin(getRMSNmin());
		ds.setRMSNmax(getRMSNmax());

		ds.setRMSAArray(getRMSAArray());
		ds.setRMSAmin(getRMSAmin());
		ds.setRMSAmax(getRMSAmax());
		/////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
	/**
	 * Gets the point vector.
	 *
	 * @return the point vector
	 */
	public Vector getPointVector(){
		
		Vector outputVector = new Vector();
		Vector diffVector = new Vector();
		
		int theoryLength = ds.getTheoryModelDataStructure().getZNArray().length;
		int expLength = ds.getExpModelDataStructure().getZNArray().length;
		int isotopeViktorLength = ds.getIsotopeViktor().size();
		
		if(ds.getSelectMassesFlag()==1){
		
			for(int i=0; i<theoryLength; i++){
			
				for(int j=0; j<expLength; j++){
					
					int ZTheory = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getX();
					int NTheory = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getY();
			
					int ZExp = (int)ds.getExpModelDataStructure().getZNArray()[j].getX();
					int NExp = (int)ds.getExpModelDataStructure().getZNArray()[j].getY();
			
					if((ZTheory==ZExp)&&(NTheory==NExp)){
					
						outputVector.addElement(new Point(ZTheory, NTheory));
						
						double mass = ds.getTheoryModelDataStructure().getMassArray()[i]
										- ds.getExpModelDataStructure().getMassArray()[j];
						
						diffVector.addElement(new Double(mass));
	
					}
				
				}
			
			}
		
		}else{
		
			int counter = 0;
		
			int biggestZ = findBiggestZ(ds.getIsotopeViktor());
		
			foundAllSelectedIsotopes:
			for(int i=0; i<theoryLength; i++){
			
				int ZTheory = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getX();
				int NTheory = (int)ds.getTheoryModelDataStructure().getZNArray()[i].getY();
			
				for(int j=0; j<expLength; j++){
					
					int ZExp = (int)ds.getExpModelDataStructure().getZNArray()[j].getX();
					int NExp = (int)ds.getExpModelDataStructure().getZNArray()[j].getY();
					
					for(int k=0; k<isotopeViktorLength; k++){
					
						int ZSelected = (int)((Point)(ds.getIsotopeViktor().elementAt(k))).getX();
						int NSelected = (int)((Point)(ds.getIsotopeViktor().elementAt(k))).getY();
			
						if((ZTheory==ZExp)&&(NTheory==NExp)){
						
							if(ZTheory<=biggestZ){
						
								if((ZTheory==ZSelected)&&(NTheory==NSelected)){
							
									counter++;
									
									outputVector.addElement(new Point(ZTheory, NTheory));
									
									double mass = ds.getTheoryModelDataStructure().getMassArray()[i]
													- ds.getExpModelDataStructure().getMassArray()[j];
									
									diffVector.addElement(new Double(mass));
							
								}
							
							}else{
							
								break foundAllSelectedIsotopes;
							
							}
		
						}
					
					}
				
				}
			
			}
		
		}

		outputVector.trimToSize();
		diffVector.trimToSize();
		
		double[] outputArray = new double[diffVector.size()];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Double)(diffVector.elementAt(i))).doubleValue();
		
		}
		
		ds.setDiffArray(outputArray);
		
		return outputVector;

	}
	
	/**
	 * Find biggest z.
	 *
	 * @param vector the vector
	 * @return the int
	 */
	public int findBiggestZ(Vector vector){
	
		int biggestZ = 0;
		
		for(int i=0; i<vector.size(); i++){
		
			biggestZ = Math.max(biggestZ, (int)((Point)(vector.elementAt(i))).getX());
		
		}
		
		return biggestZ;
	
	}
	
	/**
	 * Gets the z array.
	 *
	 * @return the z array
	 */
	public double[] getZArray(){
	
		double[] outputArray = new double[ds.getPointVector().size()];
	
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Point)(ds.getPointVector().elementAt(i))).getX();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the n array.
	 *
	 * @return the n array
	 */
	public double[] getNArray(){
	
		double[] outputArray = new double[ds.getPointVector().size()];
	
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Point)(ds.getPointVector().elementAt(i))).getY();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the a array.
	 *
	 * @return the a array
	 */
	public double[] getAArray(){
	
		double[] outputArray = new double[ds.getPointVector().size()];
	
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Point)(ds.getPointVector().elementAt(i))).getX()
								+ ((Point)(ds.getPointVector().elementAt(i))).getY();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the abs diff array.
	 *
	 * @return the abs diff array
	 */
	public double[] getAbsDiffArray(){
	
		double[] outputArray = new double[ds.getDiffArray().length];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = Math.abs(ds.getDiffArray()[i]);
		
		}
		
		return outputArray;
	
	}
	
	/**
	 * Gets the z array rms.
	 *
	 * @return the z array rms
	 */
	public double[] getZArrayRMS(){

		Vector ZVector = new Vector();
	
		for(int i=0; i<ds.getPointVector().size(); i++){

			Integer tempDouble = new Integer((int)((Point)(ds.getPointVector().elementAt(i))).getX());
			
			if(!ZVector.contains(tempDouble)){
				
				ZVector.addElement(tempDouble);
				
			}
		
		}
	
		ZVector.trimToSize();
		
		int[] outputArray = new int[ZVector.size()];
		
		for(int i=0; i<ZVector.size(); i++){
		
			outputArray[i] = ((Integer)(ZVector.elementAt(i))).intValue();
		
		}
	
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
	
		double[] outputArrayDouble = new double[outputArray.length];
	
		for(int i=0; i<outputArray.length; i++){
			
			outputArrayDouble[i] = (double)outputArray[i];
		
		}
	
		return outputArrayDouble;
	
	}
	
	/**
	 * Gets the n array rms.
	 *
	 * @return the n array rms
	 */
	public double[] getNArrayRMS(){

		Vector NVector = new Vector();
	
		for(int i=0; i<ds.getPointVector().size(); i++){

			Integer tempDouble = new Integer((int)((Point)(ds.getPointVector().elementAt(i))).getY());
			
			if(!NVector.contains(tempDouble)){
				
				NVector.addElement(tempDouble);
				
			}
		
		}
	
		NVector.trimToSize();
		
		int[] outputArray = new int[NVector.size()];
		
		for(int i=0; i<NVector.size(); i++){
		
			outputArray[i] = ((Integer)(NVector.elementAt(i))).intValue();
		
		}
	
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
	
		double[] outputArrayDouble = new double[outputArray.length];
	
		for(int i=0; i<outputArray.length; i++){
			
			outputArrayDouble[i] = (double)outputArray[i];
		
		}
	
		return outputArrayDouble;
	
	}
	
	/**
	 * Gets the a array rms.
	 *
	 * @return the a array rms
	 */
	public double[] getAArrayRMS(){

		Vector AVector = new Vector();
	
		for(int i=0; i<ds.getPointVector().size(); i++){

			Integer tempDouble = new Integer((int)((Point)(ds.getPointVector().elementAt(i))).getX()
											+ (int)((Point)(ds.getPointVector().elementAt(i))).getY());
			
			if(!AVector.contains(tempDouble)){
				
				AVector.addElement(tempDouble);
				
			}
		
		}
	
		AVector.trimToSize();
		
		int[] outputArray = new int[AVector.size()];
		
		for(int i=0; i<AVector.size(); i++){
		
			outputArray[i] = ((Integer)(AVector.elementAt(i))).intValue();
		
		}
	
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
	
		double[] outputArrayDouble = new double[outputArray.length];
	
		for(int i=0; i<outputArray.length; i++){
			
			outputArrayDouble[i] = (double)outputArray[i];
			
		}
	
		return outputArrayDouble;
	
	}
	
	/**
	 * Gets the rMSZ array.
	 *
	 * @return the rMSZ array
	 */
	double[] getRMSZArray(){
		
		Vector RMSVector = new Vector();
		
		for(int i=0; i<ds.getZArrayRMS().length; i++){
		
			int currentZ = (int)ds.getZArrayRMS()[i];

			double sumSquared = 0.0;
			int numberSum = 0;
		
			for(int j=0; j<ds.getPointVector().size(); j++){

				if(((Point)(ds.getPointVector().elementAt(j))).getX()
						==currentZ){
				 
					sumSquared += Math.pow(ds.getDiffArray()[j], 2);
				 	numberSum++;
				 
				}

			}

			double RMSValue = Math.sqrt(sumSquared/(double)numberSum);
			RMSVector.addElement(new Double(RMSValue));

		}
	
		RMSVector.trimToSize();
	
		double[] outputArray = new double[RMSVector.size()];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Double)(RMSVector.elementAt(i))).doubleValue();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the rMSN array.
	 *
	 * @return the rMSN array
	 */
	double[] getRMSNArray(){
		
		Vector RMSVector = new Vector();
		
		for(int i=0; i<ds.getNArrayRMS().length; i++){
		
			int currentN = (int)ds.getNArrayRMS()[i];
		
			double sumSquared = 0.0;
			int numberSum = 0;
		
			for(int j=0; j<ds.getPointVector().size(); j++){
		
				if(((Point)(ds.getPointVector().elementAt(j))).getY()
						==currentN){

					sumSquared += Math.pow(ds.getDiffArray()[j], 2);
				 	numberSum++;
				 
				}

			}
			
			double RMSValue = Math.sqrt(sumSquared/(double)numberSum);
			RMSVector.addElement(new Double(RMSValue));
		
		}
	
		RMSVector.trimToSize();
	
		double[] outputArray = new double[RMSVector.size()];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Double)(RMSVector.elementAt(i))).doubleValue();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the rMSA array.
	 *
	 * @return the rMSA array
	 */
	double[] getRMSAArray(){
		
		Vector RMSVector = new Vector();
		
		for(int i=0; i<ds.getAArrayRMS().length; i++){
		
			int currentA = (int)ds.getAArrayRMS()[i];
		
			double sumSquared = 0.0;
			int numberSum = 0;
		
			for(int j=0; j<ds.getPointVector().size(); j++){
			
				int Z = (int)((Point)(ds.getPointVector().elementAt(j))).getX();
				int N = (int)((Point)(ds.getPointVector().elementAt(j))).getY();
		
				if((Z+N)==currentA){
				 
					sumSquared += Math.pow(ds.getDiffArray()[j], 2);
				 	numberSum++;
				 
				}	
			
			}
		
			double RMSValue = Math.sqrt(sumSquared/(double)numberSum);
			RMSVector.addElement(new Double(RMSValue));
		
		}
	
		RMSVector.trimToSize();
	
		double[] outputArray = new double[RMSVector.size()];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = ((Double)(RMSVector.elementAt(i))).doubleValue();
		
		}
	
		return outputArray;
	
	}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	public int getZmin(){
	
		int Zmin = 1000;
		
		for(int i=0; i<ds.getZArray().length; i++){
		
			Zmin = (int)Math.min(Zmin, ds.getZArray()[i]);
		
		}
		
		return Zmin;
	
	}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){
	
		int Zmax = 0;
		
		for(int i=0; i<ds.getZArray().length; i++){
		
			Zmax = (int)Math.max(Zmax, ds.getZArray()[i]);
		
		}
		
		return Zmax;
	
	}
	
	/**
	 * Gets the nmin.
	 *
	 * @return the nmin
	 */
	public int getNmin(){
	
		int Nmin = 1000;
		
		for(int i=0; i<ds.getNArray().length; i++){
		
			Nmin = (int)Math.min(Nmin, ds.getNArray()[i]);
		
		}
		
		return Nmin;
	
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){
	
		int Nmax = 0;
		
		for(int i=0; i<ds.getNArray().length; i++){
		
			Nmax = (int)Math.max(Nmax, ds.getNArray()[i]);
		
		}
		
		return Nmax;
	
	}
	
	/**
	 * Gets the amin.
	 *
	 * @return the amin
	 */
	public int getAmin(){
	
		int Amin = 1000;
		
		for(int i=0; i<ds.getAArray().length; i++){
		
			Amin = (int)Math.min(Amin, ds.getAArray()[i]);
		
		}
		
		return Amin;
	
	}
			
	/**
	 * Gets the amax.
	 *
	 * @return the amax
	 */
	public int getAmax(){
	
		int Amax = 0;
		
		for(int i=0; i<ds.getAArray().length; i++){
		
			Amax = (int)Math.max(Amax, ds.getAArray()[i]);
		
		}
		
		return Amax;
	
	}
	
	/**
	 * Gets the diffmin.
	 *
	 * @return the diffmin
	 */
	public double getDiffmin(){
	
		double Diffmin = 1.0E100;
		
		for(int i=0; i<ds.getDiffArray().length; i++){
		
			Diffmin = Math.min(Diffmin, ds.getDiffArray()[i]);
		
		}
		
		Diffmin = Math.round(Diffmin);
		
		return Diffmin;
	
	}
	
	/**
	 * Gets the diffmax.
	 *
	 * @return the diffmax
	 */
	public double getDiffmax(){
	
		double Diffmax = -1.0E100;
		
		for(int i=0; i<ds.getDiffArray().length; i++){
		
			Diffmax = Math.max(Diffmax, ds.getDiffArray()[i]);
		
		}
		
		Diffmax = Math.round(Diffmax);
		
		return Diffmax;
	
	}

	/**
	 * Gets the abs diffmin.
	 *
	 * @return the abs diffmin
	 */
	public double getAbsDiffmin(){
	
		double Diffmin = 1.0E100;
		
		for(int i=0; i<ds.getAbsDiffArray().length; i++){
		
			Diffmin = Math.min(Diffmin, ds.getAbsDiffArray()[i]);
		
		}
		
		Diffmin = Math.round(Diffmin);

		return Diffmin;
	
	}
	
	/**
	 * Gets the abs diffmax.
	 *
	 * @return the abs diffmax
	 */
	public double getAbsDiffmax(){
	
		double Diffmax = -1.0E100;
		
		for(int i=0; i<ds.getAbsDiffArray().length; i++){
		
			Diffmax = Math.max(Diffmax, ds.getAbsDiffArray()[i]);
		
		}
		
		Diffmax = Math.round(Diffmax);
		
		return Diffmax;
	
	}

	/**
	 * Gets the zmin rms.
	 *
	 * @return the zmin rms
	 */
	public int getZminRMS(){
	
		int Zmin = 1000;
		
		for(int i=0; i<ds.getZArrayRMS().length; i++){
		
			Zmin = (int)Math.min(Zmin, ds.getZArrayRMS()[i]);
		
		}
		
		return Zmin;
	
	}
	
	/**
	 * Gets the zmax rms.
	 *
	 * @return the zmax rms
	 */
	public int getZmaxRMS(){
	
		int Zmax = 0;
		
		for(int i=0; i<ds.getZArrayRMS().length; i++){
		
			Zmax = (int)Math.max(Zmax, ds.getZArrayRMS()[i]);
		
		}
		
		return Zmax;
	
	}
	
	/**
	 * Gets the nmin rms.
	 *
	 * @return the nmin rms
	 */
	public int getNminRMS(){
	
		int Nmin = 1000;
		
		for(int i=0; i<ds.getNArrayRMS().length; i++){
		
			Nmin = (int)Math.min(Nmin, ds.getNArrayRMS()[i]);
		
		}
		
		return Nmin;
	
	}
	
	/**
	 * Gets the nmax rms.
	 *
	 * @return the nmax rms
	 */
	public int getNmaxRMS(){
	
		int Nmax = 0;
		
		for(int i=0; i<ds.getNArrayRMS().length; i++){
		
			Nmax = (int)Math.max(Nmax, ds.getNArrayRMS()[i]);
		
		}
		
		return Nmax;
	
	}
	
	/**
	 * Gets the amin rms.
	 *
	 * @return the amin rms
	 */
	public int getAminRMS(){
	
		int Amin = 1000;
		
		for(int i=0; i<ds.getAArrayRMS().length; i++){
		
			Amin = (int)Math.min(Amin, ds.getAArrayRMS()[i]);
		
		}
		
		return Amin;
	
	}
			
	/**
	 * Gets the amax rms.
	 *
	 * @return the amax rms
	 */
	public int getAmaxRMS(){
	
		int Amax = 0;
		
		for(int i=0; i<ds.getAArrayRMS().length; i++){
		
			Amax = (int)Math.max(Amax, ds.getAArrayRMS()[i]);
		
		}
		
		return Amax;
	
	}

	/**
	 * Gets the rMS zmin.
	 *
	 * @return the rMS zmin
	 */
	public double getRMSZmin(){
	
		double RMSZmin = 1.0E100;
		
		for(int i=0; i<ds.getRMSZArray().length; i++){
		
			RMSZmin = Math.min(RMSZmin, ds.getRMSZArray()[i]);
		
		}
		
		RMSZmin = Math.round(RMSZmin);
		
		return RMSZmin;
	
	}
	
	/**
	 * Gets the rMS zmax.
	 *
	 * @return the rMS zmax
	 */
	public double getRMSZmax(){
	
		double RMSZmax = 0.0;
		
		for(int i=0; i<ds.getRMSZArray().length; i++){
		
			RMSZmax = Math.max(RMSZmax, ds.getRMSZArray()[i]);
		
		}
		
		RMSZmax = Math.round(RMSZmax);
		
		return RMSZmax;
	
	}
	
	/**
	 * Gets the rMS nmin.
	 *
	 * @return the rMS nmin
	 */
	public double getRMSNmin(){
	
		double RMSNmin = 1.0E100;
		
		for(int i=0; i<ds.getRMSNArray().length; i++){
		
			RMSNmin = Math.min(RMSNmin, ds.getRMSNArray()[i]);
		
		}
		
		RMSNmin = Math.round(RMSNmin);
		
		return RMSNmin;
	
	}
	
	/**
	 * Gets the rMS nmax.
	 *
	 * @return the rMS nmax
	 */
	public double getRMSNmax(){
	
		double RMSNmax = 0.0;
		
		for(int i=0; i<ds.getRMSNArray().length; i++){
		
			RMSNmax = Math.max(RMSNmax, ds.getRMSNArray()[i]);
		
		}
		
		RMSNmax = Math.round(RMSNmax);
		
		return RMSNmax;
	
	}
	
	/**
	 * Gets the rMS amin.
	 *
	 * @return the rMS amin
	 */
	public double getRMSAmin(){
	
		double RMSAmin = 1.0E100;
		
		for(int i=0; i<ds.getRMSAArray().length; i++){
		
			RMSAmin = Math.min(RMSAmin, ds.getRMSAArray()[i]);
		
		}
		
		RMSAmin = Math.round(RMSAmin);
		
		return RMSAmin;
	
	}
	
	/**
	 * Gets the rMS amax.
	 *
	 * @return the rMS amax
	 */
	public double getRMSAmax(){
	
		double RMSAmax = 0.0;
		
		for(int i=0; i<ds.getRMSAArray().length; i++){
		
			RMSAmax = Math.max(RMSAmax, ds.getRMSAArray()[i]);
		
		}
		
		RMSAmax = Math.round(RMSAmax);
		
		return RMSAmax;
	
	}
	
	/**
	 * Quick sort.
	 *
	 * @param numbers the numbers
	 * @param left the left
	 * @param right the right
	 * @return the int[]
	 */
	public int[] quickSort(int[] numbers, int left, int right){

		int l_hold = left;
		int r_hold = right;
		int pivot = numbers[left];
		
		while(left<right){

			while ((numbers[right]>=pivot)&&(left<right)){

				right--;

			}

			if(left!=right){

				numbers[left] = numbers[right];
				left++;

			}

			while((numbers[left]<=pivot)&&(left<right)){

				left++;

			}

			if(left!=right){

				numbers[right] = numbers[left];
				right--;

			}

		}

		numbers[left] = pivot;

		pivot = left;

		left = l_hold;
		right = r_hold;

		if(left<pivot){

			quickSort(numbers, left, pivot-1);

		}

		if(right>pivot){

			quickSort(numbers, pivot+1, right);

		}
		
		return numbers;

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		if(expSelectRadioButton.isSelected()){

			if(expModelComboBox.getSelectedItem().toString().indexOf("(")!=-1){
			
				ds.setExpModel(expModelComboBox.getSelectedItem().toString().substring(0, expModelComboBox.getSelectedItem().toString().indexOf("(")-1));
				
			}else{
			
				ds.setExpModel(expModelComboBox.getSelectedItem().toString());
					
			}
		
			ds.setExpUploaded(false);
		
		}else{
		
			ds.setExpModel(expField.getText());
		
			ds.setExpUploaded(true);
		
		}
		
		if(expUploadRadioButton.isSelected()){
		
			ds.setExpNotes(notesTextArea.getText());
		
		}
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		expModelComboBox.removeAllItems();
	
		for(int i=0; i<ds.getExpModelList().length; i++){
		
			expModelComboBox.addItem(ds.getExpModelList()[i]);
		
		}
		
		if(!Cina.cinaMainDataStructure.getUser().equals("hiroyuki")){
		
			expModelComboBox.removeItem("MS96");
			expModelComboBox.removeItem("HFB2");
				
		}

		if(ds.getExpUploaded()){
			
			expSelectRadioButton.setSelected(false);
			expUploadRadioButton.setSelected(true);
			expModelComboBox.setEnabled(false);
			expBrowseButton.setEnabled(true);
			expField.setText(ds.getExpModel());
		
			notesLabel.setText("Enter notes in the area below :");
			notesTextArea.setEditable(true);
		
			notesTextArea.setText(ds.getExpNotes());
			notesTextArea.setCaretPosition(0);
			uploadExpFile(ds.getExpFile());
			goodExpDataUpload = true;
		
		}else{
		
			expSelectRadioButton.setSelected(true);
			expModelComboBox.setEnabled(true);
			expBrowseButton.setEnabled(false);
			expModelComboBox.setSelectedItem((String)ds.getExpModel());
			
			notesLabel.setText("Notes for this mass model are shown below: ");
			notesTextArea.setEditable(false);
			
			notesTextArea.setText(ds.getDataInfoArray()[expModelComboBox.getSelectedIndex()]);
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

		String delayString = "Please be patient while the reference mass model you requested is downloaded and parsed. DO NOT operate the Nuclear Data Evaluator's Toolkit at this time!";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		GridBagConstraints gbc = new GridBagConstraints();
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