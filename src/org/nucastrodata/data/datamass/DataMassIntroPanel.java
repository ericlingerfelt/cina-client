package org.nucastrodata.data.datamass;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;

import org.nucastrodata.Fonts;


/**
 * The Class DataMassIntroPanel.
 */
public class DataMassIntroPanel extends JPanel{

	/**
	 * Instantiates a new data mass intro panel.
	 */
	public DataMassIntroPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel label1 = new JLabel("Mass");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Model");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Evaluator");
		label3.setFont(Fonts.bigTitleFont);
		
		
		JLabel dummyLabel = new JLabel("<html>Welcome to the Mass Model Evaluator!<br><br>"
										+ "This tool will allow you to compare and visualize<p>masses, "
										+ "Q-values, and separation energies from<p>theoretical models and experimental data.</html>");
  											
  		JPanel titlePanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		titlePanel.add(label1, gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		titlePanel.add(label2, gbc);
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		titlePanel.add(label3, gbc);
		JPanel  htmlPanel = new JPanel(new FlowLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		htmlPanel.add(dummyLabel);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 165, 10);
		add(titlePanel, gbc);	
		gbc.gridx = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 50, 160, 0);
		gbc.anchor = GridBagConstraints.EAST;
		add(htmlPanel, gbc);	
	}	
}