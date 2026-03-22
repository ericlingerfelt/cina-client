package org.nucastrodata.wizard.gui;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.ColorFormat;
import org.nucastrodata.Fonts;


/**
 * The Class JScrollPaneCorner.
 */
public class JScrollPaneCorner extends JComponent{
	
	/** The string. */
	private String string;
	
	/**
	 * Instantiates a new j scroll pane corner.
	 *
	 * @param string the string
	 */
	public JScrollPaneCorner(String string){
		this.string = string;
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	}
	
	/**
	 * Instantiates a new j scroll pane corner.
	 */
	public JScrollPaneCorner(){
		this("");
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		g2.setColor(ColorFormat.backColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(ColorFormat.frontColor);
		g2.setFont(Fonts.textFont);
		int x = (int)(getWidth()-getWidth()/2.0-getFontMetrics(Fonts.textFont).stringWidth(string)/2.0);
		g2.drawString(string, x, 12);
	}
}
