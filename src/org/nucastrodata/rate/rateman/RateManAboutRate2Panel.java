package org.nucastrodata.rate.rateman;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateManDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.TextCopier;
import org.nucastrodata.TextSaver;
import org.nucastrodata.WizardPanel;


//This class creates the content for the second panel of the about rate sub feature of the rate man feature
/**
 * The Class RateManAboutRate2Panel.
 */
public class RateManAboutRate2Panel extends WizardPanel implements ActionListener, ItemListener{

	private JButton saveButton, copyButton;
	
	/** The valid temp range box. */
	private JCheckBox parametersBox, paramQualityBox, biblioCodeBox
						, QValueBox, reactionNotesBox, creationDateBox
						, refCitationBox, validTempRangeBox;
	
	/** The output text area. */
	private JTextArea outputTextArea;
	
	/** The compared rate vector. */
	private Vector foundHomeVector, comparedRateVector;
	
	/** The ds. */
	private RateManDataStructure ds;
	
	//Constructor					
	/**
	 * Instantiates a new rate man about rate2 panel.
	 *
	 * @param ds the ds
	 */
	public RateManAboutRate2Panel(RateManDataStructure ds){
	
		this.ds = ds;
	
		//Set the current panel index to 1 in the parent frame
		Cina.rateManFrame.setCurrentFeatureIndex(1);
		Cina.rateManFrame.setCurrentPanelIndex(2);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		addWizardPanel("Rate Manager", "Rate Info", "2", "2");
		
		JLabel titleLabel = new JLabel("<html>Select Reaction Rate<p>Information for Report: <html>");
		titleLabel.setFont(Fonts.titleFont);
		
		//Checkboxes////////////////////////////////////////////////////////////////////////////
		parametersBox = new JCheckBox("Parameters", false);
		parametersBox.addItemListener(this);
		parametersBox.setFont(Fonts.textFont);
		
		paramQualityBox = new JCheckBox("<html>Parameterization<p>Quality</html>", false);
		paramQualityBox.addItemListener(this);
		paramQualityBox.setFont(Fonts.textFont);
		
		biblioCodeBox = new JCheckBox("Biblio Code", false);
		biblioCodeBox.addItemListener(this);
		biblioCodeBox.setFont(Fonts.textFont);
		
		QValueBox = new JCheckBox("Q-Value", false);
		QValueBox.addItemListener(this);
		QValueBox.setFont(Fonts.textFont);
		
		reactionNotesBox = new JCheckBox("Notes", false);
		reactionNotesBox.addItemListener(this);
		reactionNotesBox.setFont(Fonts.textFont);
		
		creationDateBox = new JCheckBox("Creation Date", false);
		creationDateBox.addItemListener(this);
		creationDateBox.setFont(Fonts.textFont);
		
		refCitationBox = new JCheckBox("Reference Citation", false);
		refCitationBox.addItemListener(this);
		refCitationBox.setFont(Fonts.textFont);
		
		validTempRangeBox = new JCheckBox("Valid Temp. Range (T9)", false);
		validTempRangeBox.addItemListener(this);
		validTempRangeBox.setFont(Fonts.textFont);
				
		//Create text area//////////////////////////////////////////////TEXTAREAS///////////////
		outputTextArea = new JTextArea("");
		outputTextArea.setFont(Fonts.textFontFixedWidth);
		outputTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(outputTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(275, 300));
		
		//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		//Set gbc and put it all together
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		buttonPanel.add(saveButton, gbc);
		gbc.gridx = 1;
		buttonPanel.add(copyButton, gbc);
		
		JPanel cbPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 10, 0);
		cbPanel.add(titleLabel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		cbPanel.add(parametersBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		cbPanel.add(paramQualityBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		cbPanel.add(biblioCodeBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		cbPanel.add(QValueBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		cbPanel.add(reactionNotesBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		cbPanel.add(creationDateBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		cbPanel.add(refCitationBox, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		cbPanel.add(validTempRangeBox, gbc);
		
		mainPanel.add(cbPanel, BorderLayout.WEST);
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	//Method to refresh data in window
	/**
	 * Refresh data.
	 */
	private void refreshData(){
		outputTextArea.setText("");
		outputTextArea.setText(getTextAreaString());
		outputTextArea.setCaretPosition(0);
		
	}

	/**
	 * Gets the text area string.
	 *
	 * @return the text area string
	 */
	private String getTextAreaString(){
	
		//Initialize text area string
		String textAreaString = "";
	
		//Create a counter
		int numberRates = 0;
		
		//Loop over rate data strcuture array
		for(int i=0; i<ds.getCurrentRateDataStructureArray().length; i++){
			
			//Create text area string for each piece of info selected
			textAreaString = textAreaString + getReactionStringText(ds.getCurrentRateDataStructureArray()[i]) + "\n\n";
			if(parametersBox.isSelected()){textAreaString = textAreaString + getParametersText(ds.getCurrentRateDataStructureArray()[i]);}							
			if(paramQualityBox.isSelected()){textAreaString = textAreaString + getParamQualityText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(biblioCodeBox.isSelected()){textAreaString = textAreaString + getBiblioCodeText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(QValueBox.isSelected()){textAreaString = textAreaString + getQValueText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(reactionNotesBox.isSelected()){textAreaString = textAreaString + getReactionNotesText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(creationDateBox.isSelected()){textAreaString = textAreaString + getCreationDateText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(refCitationBox.isSelected()){textAreaString = textAreaString + getRefCitationText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			if(validTempRangeBox.isSelected()){textAreaString = textAreaString + getValidTempRangeText(ds.getCurrentRateDataStructureArray()[i]) + "\n";}
			textAreaString = textAreaString + "\n" 
								+ "_______________________________________________________________"
								+ "\n";
			
			//Increment counter
			numberRates++;
    			
    	}
	
		//If nothing is selected
		if(!parametersBox.isSelected()
			&& !biblioCodeBox.isSelected()
			&& !QValueBox.isSelected()
			&& !reactionNotesBox.isSelected()
			&& !paramQualityBox.isSelected()
			&& !creationDateBox.isSelected()
			&& !refCitationBox.isSelected()
			&& !validTempRangeBox.isSelected()){
		
			//Set text area string empty
			textAreaString = "";
		
		}
		
		return textAreaString;
	
	}

	//method for Item Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){refreshData();}

	//Method to construct the reaction string property
	/**
	 * Gets the reaction string text.
	 *
	 * @param apprds the apprds
	 * @return the reaction string text
	 */
	private String getReactionStringText(RateDataStructure apprds){
	
		String string = "";
	
		if(apprds.getDecay().equals("")){
	
			string = "Reaction Rate Information for "
								+ apprds.getReactionString()
								+ " ("
								+ apprds.getLibrary()
								+ ")";
							
		}else{
		
			string = "Reaction Rate Information for "
								+ apprds.getReactionString()
								+ " ["
								+ apprds.getDecay()
								+ "]"
								+ " ("
								+ apprds.getLibrary()
								+ ")";
								
		}
		
		return string;
	
	}

	//Method to construct the parameters property
	/**
	 * Gets the parameters text.
	 *
	 * @param apprds the apprds
	 * @return the parameters text
	 */
	private String getParametersText(RateDataStructure apprds){
	
		String[] paramStringArray = {"a(1) = ", "a(2) = ", "a(3) = ", "a(4) = ", "a(5) = ", "a(6) = ", "a(7) = "
									, "a(8) = ", "a(9) = ", "a(10)= ", "a(11)= ", "a(12)= ", "a(13)= ", "a(14)= "
									, "a(15)= ", "a(16)= ", "a(17)= ", "a(18)= ", "a(19)= ", "a(20)= ", "a(21)= "
									, "a(22)= ", "a(23)= ", "a(24)= ", "a(25)= ", "a(26)= ", "a(27)= ", "a(28)= "
									, "a(29)= ", "a(30)= ", "a(31)= ", "a(32)= ", "a(33)= ", "a(34)= ", "a(35)= "
									, "a(36)= ", "a(37)= ", "a(38)= ", "a(39)= ", "a(40)= ", "a(41)= ", "a(42)= "
									, "a(43)= ", "a(44)= ", "a(45)= ", "a(46)= ", "a(47)= ", "a(48)= ", "a(49)= "};
	
		String string = "";

		for(int i=0; i<apprds.getResonantInfo().length; i++){

			string = string + apprds.getResonantInfo()[i] + "\n\n";

			for(int j=0; j<7; j++){
			
				string = string 
							+ paramStringArray[i*7 + j]
							+ NumberFormats.getFormattedParameter(apprds.getParameterCompArray()[i][j])
							+ "\n"; 

			
			}
			
			string = string + "\n";
		
		}
		
		return string;
	
	}
	
	//Method to construct the parameterization quality property
	/**
	 * Gets the param quality text.
	 *
	 * @param apprds the apprds
	 * @return the param quality text
	 */
	private String getParamQualityText(RateDataStructure apprds){
	
		String string = "";

		if(apprds.getMaxPercentDiff()==0){
		
			string = "Max. of ((Parameterized Rate - Numerical Rate)/Numerical Rate) * 100: Not Entered \n";
	
		}else{
		
			string = "Max. of ((Parameterized Rate - Numerical Rate)/Numerical Rate) * 100: " + apprds.getMaxPercentDiff() + "\n";
		
		}
		
		if(apprds.getChisquared()==0){
		
			string = string + "Chisquared of Parameterization: Not Entered";
	
		}else{
		
			string = string + "Chisquared of Parameterization: " + String.valueOf(apprds.getChisquared());
		
		}
		
		return string;
	
	}
	
	//Method to construct the biblio code property
	/**
	 * Gets the biblio code text.
	 *
	 * @param apprds the apprds
	 * @return the biblio code text
	 */
	private String getBiblioCodeText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getBiblioString().equals("")){
		
			string = "Biblio Code: Not Entered";
		
		}else{
		
			string = "Biblio Code: " + apprds.getBiblioString();
		
		}
		
		return string;
	
	}
	
	//Method to construct the Q value property
	/**
	 * Gets the q value text.
	 *
	 * @param apprds the apprds
	 * @return the q value text
	 */
	private String getQValueText(RateDataStructure apprds){
	
		String string = "";
		
		if(String.valueOf(apprds.getQValue()).equals("") || String.valueOf(apprds.getQValue()).equals("0.0")){
		
			string = "Q-Value (MeV): Not Entered";
		
		}else{
		
			string = "Q-Value (MeV): " + String.valueOf(apprds.getQValue());
		
		}
		
		return string;
		
	}
	
	//Method to construct the notes property
	/**
	 * Gets the reaction notes text.
	 *
	 * @param apprds the apprds
	 * @return the reaction notes text
	 */
	private String getReactionNotesText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getReactionNotes().equals("")){
		
			string = "Reaction Notes: Not Entered";
	
		}else{
		
			string = "Reaction Notes: " + apprds.getReactionNotes();
		
		}
		
		return string;
	
	}

	//Method to construct the creation date property
	/**
	 * Gets the creation date text.
	 *
	 * @param apprds the apprds
	 * @return the creation date text
	 */
	private String getCreationDateText(RateDataStructure apprds){
	
		String string = "";
		
		if(String.valueOf(apprds.getCreationDate()).equals("")){
		
			string = "Creation Date: Not Entered";
		
		}else{
		
			string = "Creation Date: " + String.valueOf(apprds.getCreationDate());
		
		}
		
		return string;
		
	}
	
	//Method to construct the reference citation property
	/**
	 * Gets the ref citation text.
	 *
	 * @param apprds the apprds
	 * @return the ref citation text
	 */
	private String getRefCitationText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getRefCitation().equals("")){
		
			string = "Reference Citation: Not Entered";
	
		}else{
		
			string = "Reference Citation: " + String.valueOf(apprds.getRefCitation());
		
		}
		
		return string;
	
	}
	
	//Method to construct the valid temperature range property
	/**
	 * Gets the valid temp range text.
	 *
	 * @param apprds the apprds
	 * @return the valid temp range text
	 */
	private String getValidTempRangeText(RateDataStructure apprds){
	
		String string = "";
		
		if(apprds.getValidTempRange().equals("") || apprds.getValidTempRange().equals("0,0")){
		
			string = "Valid Temp. Range: Not Entered";
	
		}else{
		
			string = "Valid Temp. Range: " + String.valueOf(apprds.getValidTempRange());
		
		}
		
		return string;
	
	}
	
	//Method for Action Listener
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==saveButton){
		
			TextSaver.saveText(outputTextArea.getText(), Cina.rateManFrame);
		
		//If source is copy button
		}else if(ae.getSource()==copyButton){
    	
    		TextCopier.copyText(outputTextArea.getText());
    	
    	}
    	
	}

	/**
	 * Gets the rate id list.
	 *
	 * @return the rate id list
	 */
	private String getRateIDList(){
	
		String string = "";
	
		Vector partVector = new Vector();

		for(int i=0; i<ds.getRateIDVectorAboutRate().size(); i++){
		
			String currentRateID = ds.getRateIDVectorAboutRate().elementAt(i).toString();
		
			String currentFirstPart = currentRateID.substring(0,8);
			String currentSecondPart = currentRateID.substring(currentRateID.indexOf("\u0009"));
			
			String currentPartString = currentFirstPart + currentSecondPart;
			
			partVector.trimToSize();
			
			if(!partVector.contains(currentPartString)){
			
				partVector.addElement(currentPartString);
			
			}
			
		}

		partVector.trimToSize();

		for(int i=0; i<partVector.size(); i++){

			String firstPart = partVector.elementAt(i).toString().substring(0, partVector.elementAt(i).toString().indexOf("\u0009"));
	
			String secondPart = partVector.elementAt(i).toString().substring(partVector.elementAt(i).toString().indexOf("\u0009"));
	
			String[] libraryNameArray = new String[ds.getNumberPublicLibraryDataStructures()
													+ ds.getNumberSharedLibraryDataStructures()
													+ ds.getNumberUserLibraryDataStructures()];

			int counter = 0;
			
			for(int j=0; j<ds.getNumberPublicLibraryDataStructures(); j++){

				libraryNameArray[counter] = ds.getPublicLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}	
			
			for(int j=0; j<ds.getNumberSharedLibraryDataStructures(); j++){
			
				libraryNameArray[counter] = ds.getSharedLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}									
			
			for(int j=0; j<ds.getNumberUserLibraryDataStructures(); j++){
			
				libraryNameArray[counter] = ds.getUserLibraryDataStructureArray()[j].getLibName();
				
				counter++;
			
			}
			
			for(int j=0; j<libraryNameArray.length; j++){
				
				if(j==0 && i==0){
				
					string += firstPart + libraryNameArray[j] + secondPart;
				
				}else{
				
					string += "\n" + firstPart + libraryNameArray[j] + secondPart;
				
				}
				
			}
		
		}

		return string;
		
	}

	/**
	 * Gets the good rate id list.
	 *
	 * @return the good rate id list
	 */
	private String getGoodRateIDList(){
	
		String string = "";
		
		Vector rateIDVector = new Vector();
		
		String[] tempArray = ds.getAboutRateIDs();
		
		for(int i=0; i<tempArray.length; i++){
		
			StringTokenizer st09 = new StringTokenizer(tempArray[i], "\u0009");
		
			String firstPart = st09.nextToken();
			String secondPart = st09.nextToken();
		
			if(st09.nextToken().equals("EXIST=YES")){
			
				rateIDVector.addElement(firstPart + "\u0009" + secondPart);	

			}
		
		}

		rateIDVector.trimToSize();

		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i==0){
			
				string += (String)rateIDVector.elementAt(i);
			
			}else{
				
				string += "\n" + (String)rateIDVector.elementAt(i);
			
			}
		
		}

		int reactionCounter = 0;
		
		for(int i=1; i<rateIDVector.size(); i++){

			String currentFirstPart = rateIDVector.elementAt(i).toString().substring(0,8);
			String currentSecondPart = rateIDVector.elementAt(i).toString().substring(rateIDVector.elementAt(i).toString().indexOf("\u0009"));
			
			String lastFirstPart = rateIDVector.elementAt(i-1).toString().substring(0,8);
			String lastSecondPart = rateIDVector.elementAt(i-1).toString().substring(rateIDVector.elementAt(i-1).toString().indexOf("\u0009"));
			
			if(!currentFirstPart.equals(lastFirstPart)
				|| !currentSecondPart.equals(lastSecondPart)){
			
				reactionCounter++;
			
			}
		
		}

		Vector[] apprdsVectorArray = new Vector[reactionCounter+1]; 

		reactionCounter = 0;

		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i!=0){
			
				String currentFirstPart = rateIDVector.elementAt(i).toString().substring(0,8);
				String currentSecondPart = rateIDVector.elementAt(i).toString().substring(rateIDVector.elementAt(i).toString().indexOf("\u0009"));
				
				String lastFirstPart = rateIDVector.elementAt(i-1).toString().substring(0,8);
				String lastSecondPart = rateIDVector.elementAt(i-1).toString().substring(rateIDVector.elementAt(i-1).toString().indexOf("\u0009"));
				
				if(!currentFirstPart.equals(lastFirstPart)
					|| !currentSecondPart.equals(lastSecondPart)){
					
					reactionCounter++;
				
					apprdsVectorArray[reactionCounter] = new Vector();
				
					RateDataStructure apprds = new RateDataStructure();
				
					apprds.setReactionID((String)rateIDVector.elementAt(i));

					apprdsVectorArray[reactionCounter].addElement(apprds);
				
				}else{
				
					RateDataStructure apprds = new RateDataStructure();
				
					apprds.setReactionID((String)rateIDVector.elementAt(i));
					
					apprdsVectorArray[reactionCounter].addElement(apprds);
					
				}
			
			}else{
			
				apprdsVectorArray[reactionCounter] = new Vector();
			
				RateDataStructure apprds = new RateDataStructure();
				
				apprds.setReactionID((String)rateIDVector.elementAt(i));
				
				apprdsVectorArray[reactionCounter].addElement(apprds);

			}
		
		}

		ds.setAboutRateDataStructureVectorArray(apprdsVectorArray);

		return string;
	
	}

	/**
	 * Sets the about rate vector2 d array.
	 */
	private void setAboutRateVector2DArray(){
	
		Vector tempVector = new Vector();
		
		for(int i=0; i<ds.getAboutRateDataStructureVectorArray().length; i++){
	
			int numberRates = ds.getAboutRateDataStructureVectorArray()[i].size();

			int numberUniqueRateGroups = 2;

			comparedRateVector = new Vector();
			comparedRateVector.trimToSize();
			comparedRateVector.addElement(((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[i].elementAt(0))).getReactionID());

			for(int j=0; j<numberRates; j++){
	
				if(checkForUniqueRate(((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[i].elementAt(j)))
										, numberRates
										, i)){
				
					numberUniqueRateGroups++;
				
				}
			
			}
			
			Vector[] viktorArray = new Vector[numberUniqueRateGroups];
			
			for(int j=0; j<numberUniqueRateGroups; j++){
			
				viktorArray[j] = new Vector();
			
			}
		
			tempVector.addElement(getAboutRateVectorArray(viktorArray
																, ((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[i].elementAt(0)))
																, i));
		
		}
		
		tempVector.trimToSize();
		
		ds.setAboutRateMasterVector(tempVector);

	}
	
	/**
	 * Gets the about rate vector array.
	 *
	 * @param tempArray the temp array
	 * @param apprds the apprds
	 * @param vectorArrayIndex the vector array index
	 * @return the about rate vector array
	 */
	private Vector[] getAboutRateVectorArray(Vector[] tempArray, RateDataStructure apprds, int vectorArrayIndex){
	
		tempArray[0].addElement(apprds);
		
		foundHomeVector = new Vector();
		foundHomeVector.trimToSize();
		foundHomeVector.addElement(apprds.getReactionID());
		
		for(int i=0; i<ds.getAboutRateDataStructureVectorArray()[vectorArrayIndex].size(); i++){

			RateDataStructure apprdsTemp = ((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i)));
	
			boolean alreadyThere = false;
			
			for(int j=0; j<tempArray.length; j++){

				tempArray[j].trimToSize();

				int size = tempArray[j].size();
			
				if(foundHomeVector.contains(apprdsTemp.getReactionID())){
				
					alreadyThere = true;
					
				}
			
			}
			
			if(!alreadyThere){
			
				tempArray = findRateHome(apprdsTemp, tempArray);
			
			}
		
		}
	
		int blankCounter = 0;
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i].trimToSize();
		
			if(tempArray[i].size()!=0){
			
				blankCounter++;
			
			}
		
		}
	
		Vector[] tempArrayNew = new Vector[blankCounter];
	
		for(int i=0; i<tempArrayNew.length; i++){
		
			tempArrayNew[i] = tempArray[i];
		
		}
	
		return tempArrayNew;
	
	}

	/**
	 * Find rate home.
	 *
	 * @param apprds the apprds
	 * @param tempArray the temp array
	 * @return the vector[]
	 */
	private Vector[] findRateHome(RateDataStructure apprds, Vector[] tempArray){
	
		boolean foundHome = false;

		for(int i=0; i<tempArray.length; i++){
			
			tempArray[i].trimToSize();
		
			int size = tempArray[i].size();
		
			for(int j=0; j<size; j++){
			
				if(((RateDataStructure)tempArray[i].elementAt(j)).getNumberParameters()
					== apprds.getNumberParameters()){
				
					boolean sameRate = true;
				
					for(int k=0; k<apprds.getNumberParameters(); k++){
					
						if(((RateDataStructure)tempArray[i].elementAt(j)).getParameters()[k]!=apprds.getParameters()[k]){
						
							sameRate = false;
						
						}
					
					}
					
					if(sameRate && !foundHomeVector.contains(apprds.getReactionID())){
	
						foundHomeVector.addElement(apprds.getReactionID());
	
						tempArray[i].addElement(apprds);
						
						foundHome = true;
					
					}
				
				}
			
			}
		
		}
	
		breakOut:
		if(!foundHome){
		
			for(int i=0; i<tempArray.length; i++){
			
				tempArray[i].trimToSize();
		
				int size = tempArray[i].size();
			
				if(size==0){
					
					tempArray[i].addElement(apprds);
					break breakOut;
				
				}
				
			}
		
		}
	
		return tempArray;
	
	}
	
	/**
	 * Check for unique rate.
	 *
	 * @param apprds the apprds
	 * @param numberRates the number rates
	 * @param vectorArrayIndex the vector array index
	 * @return true, if successful
	 */
	private boolean checkForUniqueRate(RateDataStructure apprds, int numberRates, int vectorArrayIndex){
	
		boolean unique = false;
	
		for(int i=0; i<numberRates; i++){
		
			if((apprds.getNumberParameters()==((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i))).getNumberParameters())
					&& !comparedRateVector.contains(apprds.getReactionID())){
			
				unique = compareParameters(apprds
							, ((RateDataStructure)(ds.getAboutRateDataStructureVectorArray()[vectorArrayIndex].elementAt(i))));
				
				comparedRateVector.addElement(apprds.getReactionID());
				
			}
		
		}
		
		return unique;
	
	}
	
	/**
	 * Compare parameters.
	 *
	 * @param apprds1 the apprds1
	 * @param apprds2 the apprds2
	 * @return true, if successful
	 */
	private boolean compareParameters(RateDataStructure apprds1, RateDataStructure apprds2){
	
		boolean diffParameters = false;
		
		foundAnswer:
		for(int i=0; i<apprds1.getNumberParameters(); i++){
		
			if(apprds1.getParameters()[i]!=apprds2.getParameters()[i]){
			
				diffParameters = true;
				
				break foundAnswer;
			
			}
		
		}
	
		return diffParameters;
	
	}

	//Method to get the current state of this panel
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	//Method to set the current state of this panel
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){}

}