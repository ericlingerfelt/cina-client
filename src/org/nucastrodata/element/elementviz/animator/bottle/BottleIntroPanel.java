package org.nucastrodata.element.elementviz.animator.bottle;

import javax.swing.*;

import org.nucastrodata.Fonts;

import info.clearthought.layout.*;


/**
 * The Class BottleIntroPanel.
 */
public class BottleIntroPanel extends JPanel{

	/**
	 * Instantiates a new bottle intro panel.
	 */
	public BottleIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
				, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));
		
		JLabel topLabel = new JLabel("<html>Welcome to the Bottleneck Reaction Finder!<p><br></html>");
		
		JLabel label1 = new JLabel("Bottleneck");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Reaction");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Finder");
		label3.setFont(Fonts.bigTitleFont);
		
		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(topLabel, "2, 0, 2, 4, c, t");
		
	}	
	
}

