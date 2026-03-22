package org.nucastrodata.element.elementviz.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;


/**
 * The Class SolidLine.
 */
public class SolidLine extends ImageIcon{

	/* (non-Javadoc)
	 * @see javax.swing.ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y){

		Graphics2D g2 = (Graphics2D)g;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(hints);
	
		g2.setColor(Color.black);
		
		g2.setStroke(new BasicStroke(2));
		
		g2.drawLine(0, 7, 100, 7);
		
	}

}
