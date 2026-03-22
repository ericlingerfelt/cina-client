package org.nucastrodata.element.elementviz.animator.sum;

import javax.swing.*;
import org.nucastrodata.Fonts;
import info.clearthought.layout.*;

public class SumIntroPanel extends JPanel{

	public SumIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));
		
		JLabel topLabel = new JLabel("<html>Welcome to the Abundance Summer!<p><br></html>");
		
		JLabel label1 = new JLabel("Abundance");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Summer");
		label2.setFont(Fonts.bigTitleFont);
		
		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(topLabel, "2, 0, 2, 4, c, t");
		
	}	
	
}