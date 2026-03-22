package org.nucastrodata.element.elementviz.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;


/**
 * The Class SparseDashedLine2.
 */
public class SparseDashedLine2 extends ImageIcon{

	/* (non-Javadoc)
	 * @see javax.swing.ImageIcon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y){

		Graphics2D g2 = (Graphics2D)g;

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(hints);
	
		g2.setColor(Color.black);
		
		float[] dash = {1.0f, 2.0f};
		
		g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash, 0.0f));
		
		g2.drawLine(0, 7, 100, 7);
		
	}

}
