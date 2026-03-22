package org.nucastrodata.element.thermoman;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import javax.swing.*;
import org.nucastrodata.Fonts;

public class ThermoManIntroPanel extends JPanel{

	protected JRadioButton infoRadioButton, eraseRadioButton, copyRadioButton, importRadioButton;

	public ThermoManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Thermodynamic");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Profile");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Manager");
		label3.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Thermodynamic Profile Info", false);
		infoRadioButton.setFont(Fonts.textFont);
		infoRadioButton.setSelected(true);
				
		eraseRadioButton = new JRadioButton("Erase Thermodynamic Profiles", false);
		eraseRadioButton.setFont(Fonts.textFont);
		
		importRadioButton = new JRadioButton("Import Thermodynamic Profiles", false);
		importRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Thermodynamic Profiles to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(copyRadioButton);
		buttonGroup.add(eraseRadioButton);
		buttonGroup.add(importRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(infoRadioButton, 	"2, 0, l, c");
		add(importRadioButton, 	"2, 2, l, c");
		add(copyRadioButton, 	"2, 4, l, c");
		add(eraseRadioButton, 	"2, 6, l, c");

	}
	
}
