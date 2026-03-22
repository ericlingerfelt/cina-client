package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;

public class RateLibManAboutLibPanel extends WizardPanel implements ItemListener{
	
	private JPanel mainPanel, boxPanel; 
	private JLabel label1, label2; 
	private JTextArea area1, area2;
	private JScrollPane sp1, sp2;
	public JComboBox libComboBox;
	private RateLibManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate lib man about lib panel.
	 *
	 * @param ds the ds
	 */
	public RateLibManAboutLibPanel(RateLibManDataStructure ds){
		
		this.ds = ds;

		Cina.rateLibManFrame.setCurrentFeatureIndex(1);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		JLabel label = new JLabel("Choose rate library: ");
		label.setFont(Fonts.textFont);
		
		libComboBox = new JComboBox();
		libComboBox.addItemListener(this);
		libComboBox.setFont(Fonts.textFont);
		
		label1 = new JLabel("Specific comments: ");
		label1.setFont(Fonts.textFont);
		
		label2 = new JLabel("Library information: ");
		label2.setFont(Fonts.textFont);
		
		String string = "Contents: reaction identifier, Q value, biblio information, parameters"
							 + ", descriptors (resonant vs. nonresonant, forward vs. reverse). Rates are parameterized as functions of temperature.)"
							 + "\n\nR = exp(a1 + a2/T9 + a3/T9^(1/3) + a4*T9^(1/3) + a5*T9 + a6*T9^(5/3) + a7*ln(T9))\n"
							 + "+ [if needed] exp(a8 + a9/T9 + a10/T9^(1/3) + a11*T9^(1/3) + a12*T9 + a13*T9^(5/3) + a14*ln(T9)) \n"
							 + "+ [if needed]exp(a15 + a16/T9 + a17/T9^(1/3) + a18*T9^(1/3) + a19*T9 + a20*T9^(5/3) + a21*ln(T9)) \n"
							 + "\nFormat: plain text online library";
		
		area1 = new JTextArea("Use the dropdown menu to select a rate library.");
		area1.setFont(Fonts.textFont);
		area1.setLineWrap(true);
		area1.setWrapStyleWord(true);
		
		sp1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		area2 = new JTextArea(string);
		area2.setFont(Fonts.textFont);
		area2.setLineWrap(true);
		area2.setWrapStyleWord(true);
		sp2 = new JScrollPane(area2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		boxPanel = new JPanel();
		double[] columnBox = {TableLayoutConstants.PREFERRED, 20, TableLayoutConstants.FILL};
		double[] rowBox = {TableLayoutConstants.PREFERRED};
		boxPanel.setLayout(new TableLayout(columnBox, rowBox));
		boxPanel.add(label, "0, 0, r, c");
		boxPanel.add(libComboBox, "2, 0, f, c");
		
		mainPanel = new JPanel();
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED, 20
								, TableLayoutConstants.PREFERRED, 20
								, TableLayoutConstants.FILL, 20
								, TableLayoutConstants.PREFERRED, 20
								, TableLayoutConstants.FILL, 20};
		mainPanel.setLayout(new TableLayout(column, row));
		mainPanel.add(boxPanel, "1, 1, f, c");
		mainPanel.add(label2, "1, 3, l, c");
		mainPanel.add(sp2, "1, 5, f, f");
		mainPanel.add(label1, "1, 7, l, c");
		mainPanel.add(sp1, "1, 9, f, f");
		
		addWizardPanel("Rate Library Manager", "Library Info", "1", "1");
		
		add(mainPanel, BorderLayout.CENTER);

		validate();
		
	}
	
	public void itemStateChanged(ItemEvent ie){
	
		if(ie.getSource()==libComboBox){
			
			ds.setLibraryName((String)libComboBox.getSelectedItem());
			Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY INFO", Cina.rateLibManFrame);
			area1.setText(getLibraryDescriptor(ds.getCurrentLibraryDataStructure()));
			area1.setCaretPosition(0);
			
		}
	}

	public void getCurrentState(){}
	
	public String getLibraryDescriptor(LibraryDataStructure applds){
	
		String string = "";
		string = "Library name: " + applds.getLibName() + "\n";
		string = string + "Library notes: " + applds.getLibNotes() + "\n";
		string = string + "Creation date: " + applds.getCreationDate() + "\n";
		string = string + "Library recipe: " + applds.getLibraryRecipe() + "\n";
		return string;
	
	}

	public void setCurrentState(){
		
		libComboBox.removeAllItems();
		libComboBox.removeItemListener(this);

		for(int i=0; i<ds.getNumberPublicLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getPublicLibraryDataStructureArray()[i].getLibName());
		}
		
		for(int i=0; i<ds.getNumberSharedLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getSharedLibraryDataStructureArray()[i].getLibName());
		}

		for(int i=0; i<ds.getNumberUserLibraryDataStructures(); i++){
			libComboBox.addItem(ds.getUserLibraryDataStructureArray()[i].getLibName());
		}
		
		libComboBox.removeItemListener(this);
		libComboBox.setSelectedItem("ReaclibV2.2");
		libComboBox.addItemListener(this);
		
		ds.setLibrary((String)libComboBox.getSelectedItem());
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY INFO", Cina.rateLibManFrame);
		area1.setText(getLibraryDescriptor(ds.getCurrentLibraryDataStructure()));
		area1.setCaretPosition(0);
		libComboBox.addItemListener(this);
	
	}
	
}