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
import org.nucastrodata.io.FileGetter;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.datamass.DataMassSelectorFrame;


/**
 * The Class DataMassSelectIsotopesPanel.
 */
public class DataMassSelectIsotopesPanel extends WizardPanel implements ItemListener, ActionListener{

	/** The choose iso radio button. */
	JRadioButton allIsoRadioButton, rangeIsoRadioButton, chooseIsoRadioButton; 
	
	/** The submitted label. */
	JLabel submittedLabel;
	
	/** The good selector data download. */
	boolean goodSelectorDataDownload;
	
	/** The select button. */
	JButton selectButton;
	
	/** The gbc. */
	GridBagConstraints gbc;

	/** The delay dialog. */
	JDialog delayDialog;

	/** The main panel. */
	JPanel mainPanel;

	/** The ds. */
	private DataMassDataStructure ds;

	/**
	 * Instantiates a new data mass select isotopes panel.
	 *
	 * @param ds the ds
	 */
	public DataMassSelectIsotopesPanel(DataMassDataStructure ds){
	
		this.ds = ds;
	
		Cina.dataMassFrame.setCurrentPanelIndex(1);
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		addWizardPanel("Mass Model Evaluator", "Select Isotopes", "1", "4");
		
		JLabel topLabel = new JLabel("<html>Begin by selecting the isotopes of interest. If you choose to evaluate masses<p>"
										+ "by entering a range of isotopes or by selecting particular isotopes, click<p>"
										+ "<i>Open Isotope Selector</i> before continuing. In Steps 2 and 3, select from<p>"
										+ "a number of mass models or upload your own.</html>");
		
		JLabel label1 = new JLabel("Evaluate masses by: ");
		label1.setFont(Fonts.textFont);
		
		submittedLabel = new JLabel("<html><i>Isotopes Submitted!</i></html>");
		
		allIsoRadioButton = new JRadioButton("choosing all available isotopes", true);
		allIsoRadioButton.setFont(Fonts.textFont);
		allIsoRadioButton.addItemListener(this);
		
		rangeIsoRadioButton = new JRadioButton("entering a range of isotopes", false);
		rangeIsoRadioButton.setFont(Fonts.textFont);
		rangeIsoRadioButton.addItemListener(this);
		
		chooseIsoRadioButton = new JRadioButton("selecting particular isotopes", false);
		chooseIsoRadioButton.setFont(Fonts.textFont);
		chooseIsoRadioButton.addItemListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(allIsoRadioButton);
		buttonGroup.add(rangeIsoRadioButton);
		buttonGroup.add(chooseIsoRadioButton);
		
		selectButton = new JButton("Open Isotope Selector");
		selectButton.setFont(Fonts.buttonFont);
		selectButton.addActionListener(this);
		selectButton.setEnabled(false);
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 50, 5);
		gbc.gridwidth = 2;
		mainPanel.add(topLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(label1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(2, 5, 2, 2);
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(allIsoRadioButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		
		mainPanel.add(rangeIsoRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		mainPanel.add(chooseIsoRadioButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(selectButton, gbc);
			
		add(mainPanel, BorderLayout.CENTER);
		validate();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==selectButton){
		
			if(Cina.dataMassFrame.dataMassSelectorFrame==null){
				
				if(goodSelectorDataDownload()){
				
					
				
				}
				
			}else{
				
				Cina.dataMassFrame.dataMassSelectorFrame.setVisible(true);
				
			}
		
		}
	
	}
	
	/**
	 * Good selector data download.
	 *
	 * @return true, if successful
	 */
	public boolean goodSelectorDataDownload(){
	
		goodSelectorDataDownload = true;
		
		if(delayDialog==null){
		
			openDelayDialog(Cina.dataMassFrame);

			final SwingWorker worker = new SwingWorker(){
			
				public Object construct(){
					
					if(Cina.dataMassFrame.dataMassSelectIsotopesPanel.downloadSelectorData()){
						
						Cina.dataMassFrame.dataMassSelectIsotopesPanel.goodSelectorDataDownload = true;
						
						Cina.dataMassFrame.dataMassSelectorFrame = new DataMassSelectorFrame(ds);
						
						Cina.dataMassFrame.dataMassSelectorFrame.setCurrentState();
		
						Cina.dataMassFrame.dataMassSelectorFrame.setSize(825,600);
						Cina.dataMassFrame.dataMassSelectorFrame.setResizable(true);
		    			Cina.dataMassFrame.dataMassSelectorFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
		    			Cina.dataMassFrame.dataMassSelectorFrame.setVisible(true);	
						
						//Cina.setFrameColors(Cina.dataMassFrame.dataMassSelectorFrame);
					
					}else{
					
						Cina.dataMassFrame.dataMassSelectIsotopesPanel.goodSelectorDataDownload = false;
					
					}
								
					return new Object();
					
				}
				
				public void finished(){
					
					Cina.dataMassFrame.dataMassSelectIsotopesPanel.closeDelayDialog();
					
				}
				
			};
			
			worker.start();

		
		}
		
		return goodSelectorDataDownload;
	
	}
	
	/**
	 * Download selector data.
	 *
	 * @return true, if successful
	 */
	public boolean downloadSelectorData(){
	
		boolean temp = false;
				
			String dataString = new String(FileGetter.getFileByName("MassModelData/WB03"));
			parseSelectorDataString(dataString);
			
			temp = true;
			

		
		return temp;
		
	}
	
	/**
	 * Parses the selector data string.
	 *
	 * @param string the string
	 */
	public void parseSelectorDataString(String string){
	
		StringTokenizer st = new StringTokenizer(string, "\n");
		
		int numberTokens = st.countTokens();
		
		Point[] outputPointArray = new Point[numberTokens];
		
		for(int i=0; i<numberTokens; i++){
		
			String currentToken = st.nextToken();
	
			StringTokenizer stInner = new StringTokenizer(currentToken);
			
			int ZValue = Integer.valueOf(stInner.nextToken()).intValue();
			int NValue = Integer.valueOf(stInner.nextToken()).intValue();
			
			outputPointArray[i] = new Point(ZValue, NValue);
			
		}
		
		ds.getSelectorModelDataStructure().setZNArray(outputPointArray);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
	
		if(allIsoRadioButton.isSelected()){
		
			selectButton.setEnabled(false);
		
		}else if(rangeIsoRadioButton.isSelected()){
		
			selectButton.setEnabled(true);
			
		}else if(chooseIsoRadioButton.isSelected()){
		
			selectButton.setEnabled(true);
		
		}
		
		getCurrentState();
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		if(allIsoRadioButton.isSelected()){
		
			ds.setSelectMassesFlag(1);
		
		}else if(rangeIsoRadioButton.isSelected()){
		
			ds.setSelectMassesFlag(2);
		
		}else if(chooseIsoRadioButton.isSelected()){
		
			ds.setSelectMassesFlag(3);
		
		}
	
	}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
		
		if(ds.getSelectMassesFlag()==1){
		
			allIsoRadioButton.setSelected(true);
			rangeIsoRadioButton.setSelected(false);
			chooseIsoRadioButton.setSelected(false);
			
		}else if(ds.getSelectMassesFlag()==2){
		
			allIsoRadioButton.setSelected(false);
			rangeIsoRadioButton.setSelected(true);
			chooseIsoRadioButton.setSelected(false);
			
		}else if(ds.getSelectMassesFlag()==3){
		
			allIsoRadioButton.setSelected(false);
			rangeIsoRadioButton.setSelected(false);
			chooseIsoRadioButton.setSelected(true);
			
		}
		
		if((ds.getSelectMassesFlag()==3
				|| ds.getSelectMassesFlag()==2)
				&& ds.getMassesSubmitted()){
		
			gbc.gridx = 1;
			gbc.gridy = 3;
			gbc.gridwidth = 1;
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(submittedLabel, gbc);
		
		}else{
		
			mainPanel.remove(submittedLabel);
		
		}
		
		
		validate();
	
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

		String delayString = "Please be patient while the Mass Model Isotope Selector is prepared. DO NOT operate the Nuclear Data Evaluator's Toolkit at this time!";
		
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