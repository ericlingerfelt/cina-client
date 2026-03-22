package org.nucastrodata.element.elementviz;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.StaticPlotter;


/**
 * The Class ElementVizIsotopePanel.
 */
public class ElementVizIsotopePanel extends JPanel{

	/** The element. */
	String element = "";
	
	/** The a. */
	String a = ""; 
	
	/**
	 * Instantiates a new element viz isotope panel.
	 */
	public ElementVizIsotopePanel(){
		setBackground(Color.white);
	}
	
	/**
	 * Sets the values.
	 *
	 * @param element the element
	 * @param a the a
	 */
	public void setValues(String element, String a){
		this.element = element;
		this.a = a;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		if(!element.equals("")){

			Font f = Fonts.medTitleFont;
		
			g2.setColor(Color.black);
			g2.setFont(f);
			
			int startXPos = 70;
			int yPos = 23;
			
			StaticPlotter appsp = new StaticPlotter();
			int length = appsp.leftSuperScript(element, a, startXPos, yPos, f, "medium", g2);
				
		}

	}

}