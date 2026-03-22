package org.nucastrodata.element.elementsynth;

import java.util.Iterator;

import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import info.clearthought.layout.*;

public class ElementSynthSelectLibDirPanel extends JPanel{
	
	private JComboBox<String> libDirComboBox;
	protected JDialog delayDialogInverse;
	protected boolean goodInverseRates;
	private ElementSynthDataStructure ds;
	private WordWrapLabel topLabel;
	private JLabel libDirLabel;
	private JPanel libDirPanel;
	private double gap;

	public ElementSynthSelectLibDirPanel(ElementSynthDataStructure ds){
		
		this.ds  = ds;
		
		gap = 20;
		
		libDirComboBox = new JComboBox<String>();
		libDirComboBox.setFont(Fonts.textFont);
		
		topLabel = new WordWrapLabel(true);
		
		libDirLabel = new JLabel("Reaction Rate Library Directory:");
		libDirLabel.setFont(Fonts.textFont);
	
		libDirPanel = new JPanel();
		double[] columnLibDir = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		double[] rowLibDir = {TableLayoutConstants.PREFERRED};
		libDirPanel.setLayout(new TableLayout(columnLibDir, rowLibDir));
		libDirPanel.add(libDirLabel, "0, 0, r, c");
		libDirPanel.add(libDirComboBox, "2, 0, f, c");
	}
	
	public void setCurrentState(){

		removeAll();
		
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		
		if(ds.getLibDirList().size()>0){
		
			topLabel.setText("Please select a directory of rate libraries below.");
			libDirComboBox.removeAllItems();
			Iterator<String> itr = ds.getLibDirList().iterator();
			while(itr.hasNext()){
				libDirComboBox.addItem(itr.next());
			}
			if(!swds.getLibDir().equals("")){
				libDirComboBox.setSelectedItem(swds.getLibDir());
			}else{
				libDirComboBox.setSelectedIndex(0);
			}
			double[] column = {gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED
								, 30, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			
			add(topLabel, "1, 1, c, c");
			add(libDirPanel, "1, 3, c, c");
			
			Cina.elementSynthFrame.setContinueEnabled(true);
			
		}else{
		
			topLabel.setText("You do not currently have any directories of rate libraries to select.");
			double[] column = {gap, TableLayoutConstants.FILL, gap};
			double[] row = {gap, TableLayoutConstants.PREFERRED, gap};
			setLayout(new TableLayout(column, row));
			
			add(topLabel, "1, 1, c, c");
			
			Cina.elementSynthFrame.setContinueEnabled(false);
			
		}
		
		validate();
		repaint();
	}
	
	public void getCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		swds.setLibDir((String)libDirComboBox.getSelectedItem());
	}	
	
}
