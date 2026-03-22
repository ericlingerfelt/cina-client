package org.nucastrodata.element.elementviz;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;

import org.nucastrodata.Fonts;


/**
 * The Class ElementVizIntroPanel.
 */
public class ElementVizIntroPanel extends JPanel{

	/** The gbc. */
	GridBagConstraints gbc;
	
	/** The data label. */
	JLabel label1, label2, label3, dataLabel;
	
	/** The html panel. */
	JPanel titlePanel, htmlPanel;	

	//Constructor
	/**
	 * Instantiates a new element viz intro panel.
	 */
	public ElementVizIntroPanel(){

		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		label1 = new JLabel("Element");
		label1.setFont(Fonts.bigTitleFont);

		label2 = new JLabel("Synthesis");
		label2.setFont(Fonts.bigTitleFont);

		label3 = new JLabel("Visualizer");
		label3.setFont(Fonts.bigTitleFont);

		JLabel dummyLabel = new JLabel("<html>Welcome to the Element Synthesis Visualizer<br><br>"
											+ "This tool will enable you to:<br><br>"
											+ "- Create 1-D plots of Element Synthesis simulation results<br><br>"
											+ "- Create animations and static images of Element Synthesis "
											+ "<p>simulation results on a nuclide chart</html>");

  		titlePanel = new JPanel(new GridBagLayout());
  		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		titlePanel.add(label1, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		titlePanel.add(label2, gbc);
		
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		titlePanel.add(label3, gbc);
		
		htmlPanel = new JPanel(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		htmlPanel.add(dummyLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 100, 0);
		add(titlePanel, gbc);

		gbc.gridx = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 60, 70, 0);
		gbc.anchor = GridBagConstraints.EAST;
		add(htmlPanel, gbc);

	}

}