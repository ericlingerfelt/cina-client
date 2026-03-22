package org.nucastrodata.element.elementviz;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.StaticPlotter;

import java.awt.geom.*;


/**
 * The Class ElementVizReactionPanel.
 */
public class ElementVizReactionPanel extends JPanel{

	/** The element in. */
	String elementIn = "";
	
	/** The element out. */
	String elementOut = ""; 
	
	/** The a in. */
	String aIn = ""; 
	
	/** The a out. */
	String aOut = "";
	
	/**
	 * Instantiates a new element viz reaction panel.
	 */
	public ElementVizReactionPanel(){
		setBackground(Color.white);
	}
	
	/**
	 * Sets the values.
	 *
	 * @param elementIn the element in
	 * @param elementOut the element out
	 * @param aIn the a in
	 * @param aOut the a out
	 */
	public void setValues(String elementIn, String elementOut, String aIn, String aOut){
	
		this.elementIn = elementIn;
		this.elementOut = elementOut;
		this.aIn = aIn;
		this.aOut = aOut;
	
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);

		if(!elementIn.equals("")){
		
			Font f = Fonts.medTitleFont;
		
			g2.setColor(Color.black);
			g2.setFont(f);
			
			int startXPos = 30;
			int yPos = 23;
			
			StaticPlotter appsp = new StaticPlotter();
			int length = appsp.leftSuperScript(elementIn, aIn, startXPos, yPos, f, "medium", g2);
		
			int x1 = length + startXPos + 2;
			int y1 = yPos - 8;
			int x2 = length + startXPos + 30;
			int y2 = yPos - 8;
			
			g2.setStroke(new BasicStroke(3.0f));
			
			Line2D.Double newLine = new Line2D.Double(x1, y1, x2, y2);
			
			g2.draw(newLine);
			
			double theta = Math.atan((double)(y2-y1)/(double)(x2-x1));
			double length2 = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
			
			int side = 1;
			
			double arrowAngle = Math.PI/6;
  			int arrowLength = 10;
			
			if(x2<x1){side = -1;}
			
			Point tip = new Point((int)(x1+side*length2*Math.cos(theta))
							, (int)(y1+side*length2*Math.sin(theta)));
							
			Point side1 = new Point((int)(tip.getX()-side*arrowLength*Math.cos(theta+arrowAngle))
							, (int)(tip.getY()-side*arrowLength*Math.sin(theta+arrowAngle)));
							
			Point side2 = new Point((int)(tip.getX()-side*arrowLength*Math.cos(theta-arrowAngle))
							, (int)(tip.getY()-side*arrowLength*Math.sin(theta-arrowAngle)));
	
			g2.draw(new Line2D.Double(tip.getX()
    							, tip.getY()
    							, side1.getX()
    							, side1.getY()));
    							
    		g2.draw(new Line2D.Double(tip.getX()
    							, tip.getY()
    							, side2.getX()
    							, side2.getY()));
			
			appsp.leftSuperScript(elementOut, aOut, 30 + length + startXPos + 4, yPos, f, "medium", g2);
				
		}

	}

}