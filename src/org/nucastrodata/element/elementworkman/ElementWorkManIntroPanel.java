package org.nucastrodata.element.elementworkman;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import javax.swing.*;
import org.nucastrodata.Fonts;

public class ElementWorkManIntroPanel extends JPanel{

	protected JRadioButton infoRadioButton, eraseRadioButton, copyRadioButton;

	public ElementWorkManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Element");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Synthesis");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Workflow");
		label3.setFont(Fonts.bigTitleFont);
		JLabel label4 = new JLabel("Manager");
		label4.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Element Synthesis Workflow Info", false);
		infoRadioButton.setFont(Fonts.textFont);
		infoRadioButton.setSelected(true);
				
		eraseRadioButton = new JRadioButton("Erase Element Synthesis Workflows", false);
		eraseRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Element Synthesis Workflows to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(copyRadioButton);
		buttonGroup.add(eraseRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(label4, "0, 6, l, c");
		add(infoRadioButton, "2, 0, l, c");
		add(copyRadioButton, "2, 2, l, c");
		add(eraseRadioButton, "2, 4, l, c");

	}
	
}
