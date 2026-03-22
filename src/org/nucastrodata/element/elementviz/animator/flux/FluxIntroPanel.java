package org.nucastrodata.element.elementviz.animator.flux;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.nucastrodata.Fonts;

public class FluxIntroPanel extends JPanel{

	/**
	 * Instantiates a new bottle intro panel.
	 */
	public FluxIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
				, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		
		setLayout(new TableLayout(col, row));
		
		JLabel topLabel = new JLabel("<html>Welcome to the Table of Reaction Flux Values!<p><br></html>");
		
		JLabel label1 = new JLabel("Table of");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Reaction");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Flux Values");
		label3.setFont(Fonts.bigTitleFont);
		
		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(topLabel, "2, 0, 2, 4, c, t");
		
	}	
	
}

