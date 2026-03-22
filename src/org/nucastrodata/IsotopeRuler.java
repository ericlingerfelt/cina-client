package org.nucastrodata;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;


/**
 * The Class IsotopeRuler.
 */
public class IsotopeRuler extends JComponent{

	/** The size. */
	int size = 20;
	
	/** The increment. */
	int increment = 29;
	
	/** The yoffset. */
	int zmin, zmax, nmin, nmax, mouseX, mouseY, xoffset, yoffset;
	
	/** The cross hairs on. */
	boolean crossHairsOn;
	
	/** The orientation. */
	String orientation;
    
    /** The real small font metrics. */
    FontMetrics realSmallFontMetrics = getFontMetrics(Fonts.realSmallFont);
	
	/**
	 * Instantiates a new isotope ruler.
	 *
	 * @param orientation the orientation
	 */
	public IsotopeRuler(String orientation){
		this.orientation = orientation;
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, new Color(240, 240, 240), new Color(150, 150, 150)));
	}

	public void setCurrentState(int newZmax, int newNmax, int newMouseX, int newMouseY, int newXoffset, int newYoffset, boolean newCrossHairsOn){
		setCurrentState(0, newZmax, 0, newNmax, newMouseX, newMouseY, newXoffset, newYoffset, newCrossHairsOn);
	}
	
	public void setCurrentState(int newZmin, int newZmax, int newNmin, int newNmax, int newMouseX, int newMouseY, int newXoffset, int newYoffset, boolean newCrossHairsOn){
		zmin=newZmin;
		zmax=newZmax;
		nmin=newNmin;
		nmax=newNmax;
		mouseX=newMouseX;
		mouseY=newMouseY;
		xoffset=newXoffset;
		yoffset=newYoffset;
		crossHairsOn=newCrossHairsOn;
		repaint();
	}
	
	/**
	 * Sets the preferred height.
	 *
	 * @param ph the new preferred height
	 */
	public void setPreferredHeight(int ph){setPreferredSize(new Dimension(size+5, ph));}
	
	/**
	 * Sets the preferred width.
	 *
	 * @param pw the new preferred width
	 */
    public void setPreferredWidth(int pw){setPreferredSize(new Dimension(pw, size));}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
	
		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		Rectangle drawHere = g.getClipBounds();
		
		increment = 29;
			
		g2.setColor(new Color(220, 220, 220));
        g2.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g2.setFont(Fonts.realSmallFont);
        g2.setColor(Color.black);
        
        int end = 0;
        int start = 0;
        int tickLength = 0;
        
        if(orientation.equals("Horizontal")){
        	start = (drawHere.x/increment)*increment +1;
            end = (((drawHere.x + drawHere.width)/increment) + 1)*increment;
        }else{     
        	start = (drawHere.y/increment)*increment + 7;
            end = (((drawHere.y + drawHere.height)/increment) + 1)*increment;       
        }
        
        if(start==0){
            tickLength = 20;
            if(orientation.equals("Horizontal")){
            	g2.drawLine(0, size-1, 0, size-tickLength-1);
            }else{
            	g2.drawLine(size-1, 0, size-tickLength-1, 0);
            }
            start = increment;
        }
 
        for(int i=start; i<end; i+=increment){
			tickLength = 20;
            if(tickLength != 0){
                if(orientation.equals("Horizontal")){
                	g2.drawLine(i, size-1, i, size-tickLength-1);
                }else{
                	g2.drawLine(size-1, i, size-tickLength-1, i);
                }
            }
        }

        if(orientation.equals("Horizontal")){
	        for(int i=nmin; i<nmax+1; i++){
	        	String string = String.valueOf(i);
	        	g2.drawString(string, xoffset+(i-nmin)*increment+increment/2-realSmallFontMetrics.stringWidth(string)/2 + 1, 13);
	        }
    	}else{
	        for(int i=zmin; i<zmax+1; i++){
	        	String string = "";
		        string = String.valueOf(Cina.cinaMainDataStructure.getElementSymbol(zmax-i+zmin));
		        g2.drawString(string, 3, yoffset + (i-zmin)*increment + increment/2 + realSmallFontMetrics.getHeight()/2 -3);
	        }
    	}
        
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(2));
        
        if(crossHairsOn){
	        if(orientation.equals("Horizontal")){
	       		int position = mouseX;
	        	g2.drawLine(position, size-1, position, size-tickLength-1);	        	
	        }else{	        
	        	int position = mouseY;
	        	g2.drawLine(size-1, position, size-tickLength-1, position);	        
	        }
    	}  
	}
}