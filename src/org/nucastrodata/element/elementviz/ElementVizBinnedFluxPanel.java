package org.nucastrodata.element.elementviz;
import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;

import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;

import java.util.Vector;


/**
 * The Class ElementVizBinnedFluxPanel.
 */
public class ElementVizBinnedFluxPanel extends JPanel{
	
	/** The data vector. */
	Vector dataVector;
	
	/** The type. */
	String type;
	
	/**
	 * Instantiates a new element viz binned flux panel.
	 *
	 * @param type the type
	 */
	public ElementVizBinnedFluxPanel(String type){
	
		this.type = type;
		setBackground(Color.white);
	}	

	/**
	 * Sets the data vector.
	 *
	 * @param vector the new data vector
	 */
	public void setDataVector(Vector vector){dataVector = vector;}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		
		g2.setColor(Color.black);
		g2.setFont(Fonts.titleFont);
		
		g2.drawString(type, 20, 20);
		
		int counter = 0;
		
		for(int i=0; i<dataVector.size(); i++){
		
			boolean showBin = ((Boolean)((Vector)dataVector.elementAt(i)).elementAt(2)).booleanValue();
		
			if(showBin){
			
				double min = Math.pow(10, ((Integer)((Vector)dataVector.elementAt(i)).elementAt(0)).intValue());
				double max = Math.pow(10, ((Integer)((Vector)dataVector.elementAt(i)).elementAt(1)).intValue());
				Color color = (Color)((Vector)dataVector.elementAt(i)).elementAt(3);
				int linestyle = ((Integer)((Vector)dataVector.elementAt(i)).elementAt(4)).intValue();
				int linewidth = ((Integer)((Vector)dataVector.elementAt(i)).elementAt(5)).intValue();
			
				float[] dash1 = {3.0f, 4.0f};
				float[] dash2 = {2.0f, 3.0f};
				float[] dash3 = {1.0f, 2.0f};
				
				BasicStroke stroke = new BasicStroke();
				
				switch(linestyle){
				
					case 0:
						stroke = new BasicStroke(linewidth);
						break;
						
					case 1:
						stroke = new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash1, 0.0f);
						break;
						
					case 2:
						stroke = new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash2, 0.0f);
						break;
						
					case 3:
						stroke = new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash3, 0.0f);
						break;
			
				}
				
				g2.setColor(color);
				g2.setStroke(stroke);
				g2.drawLine(10, counter*15+35, 70, counter*15+35);
				
				g2.setColor(Color.black);
				g2.setFont(Fonts.textFont);
				g2.drawString(NumberFormats.getFormattedValueLong4(min) + "-->" + NumberFormats.getFormattedValueLong4(max), 80, counter*15+40);
	
				counter++;
	
			}
		
		}
		
    }
    
}
