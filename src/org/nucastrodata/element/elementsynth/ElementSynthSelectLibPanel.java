package org.nucastrodata.element.elementsynth;

import java.awt.*;

import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import info.clearthought.layout.*;

public class ElementSynthSelectLibPanel extends JPanel{
	
	private JComboBox<String> libComboBox;
	protected JDialog delayDialogInverse;
	protected boolean goodInverseRates;
	private ElementSynthDataStructure ds;
	private WordWrapLabel topLabel;
	private JLabel libLabel;
	private JPanel libPanel;
	private double gap;

	public ElementSynthSelectLibPanel(ElementSynthDataStructure ds){
		
		this.ds  = ds;
		
		gap = 20;
		
		libComboBox = new JComboBox<String>();
		libComboBox.setFont(Fonts.textFont);
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("Please select a reaction rate library for the element synthesis simulation.");
		
		libLabel = new JLabel("Reaction Rate Library:");
		libLabel.setFont(Fonts.textFont);

		libPanel = new JPanel();
		double[] columnLib = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		double[] rowLib = {TableLayoutConstants.PREFERRED};
		libPanel.setLayout(new TableLayout(columnLib, rowLib));
		libPanel.add(libLabel, "0, 0, r, c");
		libPanel.add(libComboBox, "2, 0, f, c");

		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 30, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));

		add(topLabel, "1, 1, c, c");
		add(libPanel, "1, 3, c, c");

	}
	
	public void setCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		
		libComboBox.removeAllItems();
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
		}
		if(swds.getLibrary().equals("")){
			libComboBox.setSelectedIndex(0);
		}else{
			libComboBox.setSelectedItem(swds.getLibrary());
		}
	
		validate();
		repaint();
	}
	
	public void getCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		swds.setLibrary((String)libComboBox.getSelectedItem());
		swds.setLibGroup(getLibGroup(swds.getLibrary()));
		swds.setLibraryPath(swds.getLibGroup() + "/" + swds.getLibrary());
	}	

	private String getLibGroup(String library) {
		String group = "";
		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			if(ds.getPublicLibraryDataStructureArray()[i].getLibName().equals(library)){
				group = "PUBLIC";
			}
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			if(ds.getSharedLibraryDataStructureArray()[i].getLibName().equals(library)){
				group = "SHARED";
			}
		}
		
		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			if(ds.getUserLibraryDataStructureArray()[i].getLibName().equals(library)){
				group = "USER";
			}
		}
		return group;
	}

	protected void openDelayDialogInverse(Frame frame){
		
		delayDialogInverse = new JDialog(frame, "Please wait.", false);
    	delayDialogInverse.setSize(340, 200);
    	delayDialogInverse.getContentPane().setLayout(new GridBagLayout());
		delayDialogInverse.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please be patient while missing inverse rates are calculated. DO NOT operate the Element Synthesis Simulator at this time!";
		delayTextArea.setText(delayString);
		delayTextArea.setCaretPosition(0);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		delayDialogInverse.getContentPane().add(sp, gbc);
		
		delayDialogInverse.validate();
		delayDialogInverse.setVisible(true);
	}
	
	public void closeDelayDialogInverse(){
		delayDialogInverse.setVisible(false);
		delayDialogInverse.dispose();
		delayDialogInverse=null;
	}
	
	protected boolean goodLibInfoInverseCheck(){
		boolean goodLibInfoInverseCheck = false;
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY INFO", Cina.elementSynthFrame);
		if(!flagArray[0]){
			if(!flagArray[2]){
				if(!flagArray[1]){
					goodLibInfoInverseCheck = true;	
				}
			}
		}
		return goodLibInfoInverseCheck;
	}
}
