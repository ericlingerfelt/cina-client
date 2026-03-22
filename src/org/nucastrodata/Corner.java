package org.nucastrodata;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;


/**
 * The Class Corner.
 */
public class Corner extends JComponent{

	/**
	 * Instantiates a new corner.
	 */
	public Corner(){setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED
																					, new Color(240, 240, 240)
																					, new Color(150, 150, 150)));}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		g2.setColor(new Color(220, 220, 220));
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
}