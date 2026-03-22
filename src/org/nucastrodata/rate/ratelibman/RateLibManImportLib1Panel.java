package org.nucastrodata.rate.ratelibman;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import org.nucastrodata.Cina;
import org.nucastrodata.WizardPanel;

public class RateLibManImportLib1Panel extends WizardPanel{

	private RateLibManDataStructure ds;
	private JPanel mainPanel;
	private WordWrapLabel topLabel;
	private MainDataStructure mds;
	private Frame frame;
	private JRadioButton singleButton, dirButton;

	public RateLibManImportLib1Panel(Frame frame, RateLibManDataStructure ds,  MainDataStructure mds){
		
		this.ds = ds;
		this.mds = mds;
		this.frame = frame;

		Cina.rateLibManFrame.setCurrentFeatureIndex(8);
		Cina.rateLibManFrame.setCurrentPanelIndex(1);

		addWizardPanel("Rate Library Manager", "Import One or More Libraries", "1", "2");

		mainPanel = new JPanel();

		topLabel = new WordWrapLabel(true);
		
		topLabel.setText("Please select an option below for importing either a single rate library or a directory of rate libraries.");
		
		singleButton = new JRadioButton("Import a Single Rate Library");
		dirButton = new JRadioButton("Import a Directory of Rate Libraries");
		
		ButtonGroup group = new ButtonGroup();
		group.add(singleButton);
		group.add(dirButton);
		
		JPanel buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED, 10, 
								TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(singleButton,     	"0, 0, l, c");
		buttonPanel.add(dirButton,  	  		"0, 2, l, c");
		
		double[] column = {20, TableLayoutConstants.FILL, 20};
		double[] row = {100, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED, 20};
		mainPanel.setLayout(new TableLayout(column, row));
		
		mainPanel.add(topLabel, 			"1, 1, c, c");
		mainPanel.add(buttonPanel, 		"1, 3, c, c");
		
		add(mainPanel, BorderLayout.CENTER);
		
		validate();
		
	}
	
	public void setCurrentState(){

		dirButton.setSelected(ds.getImportDir());
		singleButton.setSelected(!ds.getImportDir());
		
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
		ds.setImportDir(dirButton.isSelected());
		
	}

}
