package org.nucastrodata.element.elementsynth;

import javax.swing.*;
import org.nucastrodata.Fonts;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.*;

public class ElementSynthIntroPanel extends JPanel{

	public ElementSynthIntroPanel(){
		
		double[] col = {50, TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.FILL, 50};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));

		JLabel label1 = new JLabel("Element");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Synthesis");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Simulator");
		label3.setFont(Fonts.bigTitleFont);

		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Welcome to the Element Synthesis Simulator. " 
							+ "<br><br>This application will enable you to "
							+ "perform Element Synthesis calculations with custom rate libraries, "
							+ "pre-calculated thermodynamic profiles for single or multiple zones, "
							+ "and a set of initial abundances. Sensitivity studies for single zone "
							+ "simulations over a selected reaction rate may also be performed.</html>");
		
		add(label1, "1, 0, l, c");
		add(label2, "1, 2, l, c");
		add(label3, "1, 4, l, c");
		add(topLabel, "3, 0, 2, 6, f, c");
	}	
}