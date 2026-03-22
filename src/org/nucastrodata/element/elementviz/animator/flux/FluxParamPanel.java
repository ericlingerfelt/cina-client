package org.nucastrodata.element.elementviz.animator.flux;

import java.awt.event.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class FluxParamPanel extends JPanel implements ActionListener{

	private FluxDataStructure ds;
	
	private WordWrapLabel topLabel;
	
	private JButton pasteButton;
	
	private JTextArea area;

	public FluxParamPanel(FluxDataStructure ds){
		
		 this.ds = ds;
		
		 double gap = 10;
		 double[] column = {gap, TableLayoutConstants.FILL, gap};
		 double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED, gap};
		
		 setLayout(new TableLayout(column, row));
		
		 topLabel = new WordWrapLabel();
		 pasteButton = new JButton("Paste from Clipboard");
		 pasteButton.addActionListener(this);
		 area = new JTextArea();
		 JScrollPane areaPane = new JScrollPane(area);
		 
		 add(topLabel, "1, 1, f, c");
		 add(areaPane, "1, 3, f, f");
		 add(pasteButton, "1, 5, r, c");
		 
	}
	
	public boolean goodData(){
		if(area.getText().trim().equals("")){
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==pasteButton){
			area.paste();
		}
	}
	
	public void setCurrentState(){
		topLabel.setText("<html>Please enter the Z and A values for the key reactant and product " +
							"of each reaction in the text area below. Each reaction must be entered in four columns on a separate line. For example, " +
							"\"13N &rarr; 14O\" would be entered as \"7 13 8 14\" on a single line. Use <i>Paste from Clipboard</i> to " +
							"copy and paste a list of reactions into the text area.</html>");
		area.setText(ds.getReactions());
	}
	
	public void getCurrentState(){
		ds.setReactions(area.getText());
	}
	
}