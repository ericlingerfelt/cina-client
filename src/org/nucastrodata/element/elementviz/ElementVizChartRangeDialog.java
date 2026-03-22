package org.nucastrodata.element.elementviz;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.*;
import java.util.ArrayList;
import info.clearthought.layout.*;
import javax.swing.*;

import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.wizard.gui.WordWrapLabel;

public class ElementVizChartRangeDialog extends JDialog implements ActionListener{

	private ArrayList<Integer> rangeList, currentRangeList, completeRangeList;
	private JComboBox zminBox, zmaxBox;
	private int zmin, zmax;
	private JButton closeButton, defaultButton, applyButton;
	
	public static ArrayList<Integer> createElementVizChartRangeDialog(Frame frame
																		, ArrayList<Integer> currentRangeList
																		, ArrayList<Integer> completeRangeList){
		
		ElementVizChartRangeDialog dialog = new ElementVizChartRangeDialog(frame, currentRangeList, completeRangeList);
		dialog.setVisible(true);
		return dialog.rangeList;
	}
	
	private ElementVizChartRangeDialog(Frame frame
										, ArrayList<Integer> currentRangeList
										, ArrayList<Integer> completeRangeList){
		
		super(frame, "Set Chart Range", Dialog.ModalityType.APPLICATION_MODAL);
		
		this.currentRangeList = currentRangeList;
		this.completeRangeList = completeRangeList;
		setSize(500, 225);
		setLocationRelativeTo(frame);
		
		zmin = completeRangeList.get(0);
		zmax = completeRangeList.get(1);
		
		zminBox = new JComboBox();
		for(int i=zmin; i<zmax; i++){
			zminBox.addItem(i);
		}
		zminBox.setSelectedItem(currentRangeList.get(0));
		
		zmaxBox = new JComboBox();
		for(int i=zmin+1; i<=zmax; i++){
			zmaxBox.addItem(i);
		}
		zmaxBox.setSelectedItem(currentRangeList.get(1));
		
		WordWrapLabel topLabel = new WordWrapLabel();
		topLabel.setText("Select a New Range for the nuclide chart using the options listed below.");
		JLabel zminLabel = new JLabel("Z Min:");
		JLabel zmaxLabel = new JLabel("Z Max:");
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		
		defaultButton = new JButton("Use Defaults");
		defaultButton.addActionListener(this);
		
		applyButton = new JButton("Apply Selected Range");
		applyButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		double[] colButton = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(colButton, rowButton));
		buttonPanel.add(defaultButton,  "0, 0, c, c");
		buttonPanel.add(applyButton, 	"2, 0, c, c");
		buttonPanel.add(closeButton, 	"4, 0, c, c");
		
		JPanel inputPanel = new JPanel();
		double[] colInput = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.FILL};
		double[] rowInput = {TableLayoutConstants.PREFERRED
								, 10, TableLayoutConstants.PREFERRED};
		inputPanel.setLayout(new TableLayout(colInput, rowInput));
		inputPanel.add(zminLabel,  	"0, 0, c, c");
		inputPanel.add(zminBox, 	"2, 0, c, c");
		inputPanel.add(zmaxLabel,  	"0, 2, c, c");
		inputPanel.add(zmaxBox, 	"2, 2, c, c");
		
		double[] col = {20, TableLayoutConstants.FILL, 20};
		double[] row = {20, TableLayoutConstants.PREFERRED, 20
						, TableLayoutConstants.PREFERRED, 20
						, TableLayoutConstants.PREFERRED, 20};
		setLayout(new TableLayout(col, row));
		add(topLabel, 		"1, 1, c, c");
		add(inputPanel,  	"1, 3, c, c");
		add(buttonPanel, 	"1, 5, c, c");
		
	}
	
	private boolean goodRange(){
		
		boolean goodRange = true;
		
		int zmin = (Integer)zminBox.getSelectedItem();
		int zmax = (Integer)zmaxBox.getSelectedItem();
		
		if(zmin >= zmax){
			AttentionDialog.createDialog(this, "Please select a value for Z Min that is less than Z Max.");
			goodRange = false;
		}
		return goodRange;
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==closeButton){
			rangeList = currentRangeList;
			setVisible(false);
		}else if(ae.getSource()==defaultButton){
			rangeList = completeRangeList;
			setVisible(false);
		}else if(ae.getSource()==applyButton){
			
			if(goodRange()){
				rangeList = new ArrayList<Integer>();
				rangeList.add((Integer)zminBox.getSelectedItem());
				rangeList.add((Integer)zmaxBox.getSelectedItem());
				setVisible(false);
			}
			
		}
		
	}

}
