package org.nucastrodata.eval.wizard.reactionselector;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.datastructure.util.ReactionDataStructure;

import java.util.*;

import org.nucastrodata.eval.wizard.reactionselector.IsotopePointComparator;
import org.nucastrodata.ColorFormat;
import org.nucastrodata.Fonts;


/**
 * The Class IsotopeSelectorChart.
 */
public class IsotopeSelectorChart extends JPanel implements MouseListener, MouseMotionListener{

	/** The box size. */
	private final int boxSize = 30;
	
	/** The xoffset. */
	private final int xoffset = 40;  
    
    /** The yoffset. */
    private final int yoffset = 15;           
    
    /** The crosshair size. */
    private final int crosshairSize = 30;
    
    /** The mouse y. */
    private int mouseX, mouseY;
    
    /** The nmax. */
    private int zmax, nmax;
    
    /** The mouse z. */
    private int mouseN, mouseZ;
    
    /** The chart height. */
    private int chartWidth, chartHeight;
    
    /** The ymax. */
    private int xmax, ymax;
    
    /** The crosshairs on. */
    private boolean crosshairsOn;
    
    /** The min drip z. */
    private ArrayList<Integer> minDripZ;
    
    /** The rlds. */
    private RateLibDataStructure rlds;
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The n ruler. */
    private IsotopeRuler zRuler, nRuler;
    
    /** The ip selected. */
    private IsotopePoint ipSelected;
    
    /** The rscl. */
    private ReactionSelectorChartListener rscl;
    
    /** The max n paint. */
    private int minZPaint, maxZPaint, minNPaint, maxNPaint;
    
	/**
	 * Instantiates a new isotope selector chart.
	 */
	public IsotopeSelectorChart(){
		addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.black);
	}
	
	/**
	 * Sets the scroll pane.
	 *
	 * @param sp the new scroll pane
	 */
	public void setScrollPane(JScrollPane sp){this.sp = sp;}
	
	/**
	 * Sets the z ruler.
	 *
	 * @param zRuler the new z ruler
	 */
	public void setZRuler(IsotopeRuler zRuler){this.zRuler = zRuler;}
	
	/**
	 * Sets the n ruler.
	 *
	 * @param nRuler the new n ruler
	 */
	public void setNRuler(IsotopeRuler nRuler){this.nRuler = nRuler;}
	
	/**
	 * Gets the isotope point.
	 *
	 * @return the isotope point
	 */
	public IsotopePoint getIsotopePoint(){
		return ipSelected;
	}
	
	/**
	 * Initialize.
	 *
	 * @param rds the rds
	 * @param rlds the rlds
	 * @param rscl the rscl
	 */
	public void initialize(ReactionDataStructure rds
							, RateLibDataStructure rlds
							, ReactionSelectorChartListener rscl){
		
		this.rlds = rlds;
		this.rscl = rscl;
		zmax = rlds.getIsotopeMapFull().lastKey();
		ArrayList<IsotopePoint> list = rlds.getIsotopeMapFull().get(rlds.getIsotopeMapFull().lastKey());
		Collections.sort(list, new IsotopePointComparator());
		nmax = list.get(list.size()-1).getA() - zmax;
		chartWidth = boxSize*(nmax+1);
        chartHeight = boxSize*(zmax+1);
        xmax = xoffset + chartWidth;
        ymax = yoffset + chartHeight;
        setSize(xmax+2*xoffset,ymax+2*yoffset);
        setPreferredSize(getSize());
        minDripZ = getMinDripZ();
        ipSelected = rds.getIsotopePoint();
        repaint();
	}
	
	/**
	 * Gets the min drip z.
	 *
	 * @return the min drip z
	 */
	private ArrayList<Integer> getMinDripZ(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int n=0; n<=nmax; n++){
			Iterator<Integer> itr = rlds.getIsotopeMapFull().keySet().iterator();
			boolean isotopeNotFound = true;
			while(isotopeNotFound){
				Integer z = itr.next();
				if(rlds.getIsotopeMapFull().get(z).contains(new IsotopePoint(z, z+n))){
					list.add(z);
					isotopeNotFound = false;
				}
			}
		}
		return list;
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){return nmax;}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){return zmax;}
	
	/**
	 * Gets the mouse x.
	 *
	 * @return the mouse x
	 */
	public int getMouseX(){return mouseX;}
	
	/**
	 * Gets the mouse y.
	 *
	 * @return the mouse y
	 */
	public int getMouseY(){return mouseY;}
	
	/**
	 * Gets the x offset.
	 *
	 * @return the x offset
	 */
	public int getXOffset(){return xoffset;}
	
	/**
	 * Gets the y offset.
	 *
	 * @return the y offset
	 */
	public int getYOffset(){return yoffset;}
	
	/**
	 * Gets the box size.
	 *
	 * @return the box size
	 */
	public int getBoxSize(){return boxSize;}
	
	/**
	 * Gets the crosshairs on.
	 *
	 * @return the crosshairs on
	 */
	public boolean getCrosshairsOn(){return crosshairsOn;}
	
	/**
	 * Sets the rate lib data structure.
	 *
	 * @param rlds the new rate lib data structure
	 */
	public void setRateLibDataStructure(RateLibDataStructure rlds){this.rlds = rlds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){
        setMouseNZ(me);
        repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.createImage("blankImage.image");
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
		crosshairsOn = true;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		crosshairsOn = false;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		setMouseNZ(me);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
        setMouseNZ(me);
        if(rlds.getIsotopeMapAvailable().get(mouseZ)!=null && rlds.getIsotopeMapAvailable().get(mouseZ).contains(new IsotopePoint(mouseZ, mouseZ+mouseN))){
        	ipSelected = new IsotopePoint(mouseZ, mouseZ+mouseN);
        	rscl.fireIsotopeSelected(ipSelected);
        }
        repaint();
	}	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
        setMouseNZ(me);
        zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
        repaint();
	}
	
	/**
	 * Sets the mouse nz.
	 *
	 * @param me the new mouse nz
	 */
	private void setMouseNZ(MouseEvent me){
		mouseX = me.getX();
        mouseY = me.getY();
        
        if(crosshairsOn){
        	double fracY = (double)(mouseY-yoffset)/(double)chartHeight;
        	double fracX = (double)(mouseX-xoffset)/(double)chartWidth;
            mouseZ = (zmax-((int)(fracY*(zmax+1))));
            mouseN = (int)(fracX *(nmax+1));
        }else{
        	mouseZ = 0;
            mouseN = 0;
        }
        
	}
	
	/**
	 * Gets the nZ point.
	 *
	 * @param x the x
	 * @param y the y
	 * @param returnNullAllowed the return null allowed
	 * @return the nZ point
	 */
	private Point getNZPoint(int x, int y, boolean returnNullAllowed){
        if(returnNullAllowed){
        	double fracY = (double)(y-yoffset)/(double)chartHeight;
        	double fracX = (double)(x-xoffset)/(double)chartWidth;
        	return new Point((int)(fracX *(nmax+1))
        						, (zmax-((int)(fracY*(zmax+1)))));
        }
        return null;
	}
	
	/**
	 * Sets the paint values.
	 */
	public void setPaintValues(){
		
		Point minPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()+sp.getViewport().getHeight()
								, true);
		Point maxZPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()
								, true);
		Point maxNPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()+sp.getViewport().getWidth()
								, sp.getVerticalScrollBar().getValue()
								, true);
		
		minNPaint = (int)minPaintPoint.getX();
		minZPaint = (int)minPaintPoint.getY();
		maxNPaint = (int)maxNPaintPoint.getX();
		maxZPaint = (int)maxZPaintPoint.getY();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
        RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(hintsText);
		setPaintValues();
		
		Iterator<Integer> itr = rlds.getIsotopeMapFull().keySet().iterator();
		while(itr.hasNext()){
			Integer z = itr.next();
			if(z>=minZPaint && z<=maxZPaint){
				drawZDripLabel(g2, z);
				fillIsotopeBoxes(g2, z);
			    drawIsotopeBoxes(g2, z);
			}
		}
		
		drawNDripLabels(g2);
		if(crosshairsOn){drawCrosshairs(g2);}
		
	}
	
	/**
	 * Fill isotope boxes.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void fillIsotopeBoxes(Graphics2D g2, Integer z){
		g2.setColor(ColorFormat.frontColor);
		if(rlds.getIsotopeMapAvailable().get(z)!=null){
			Iterator<IsotopePoint> itr = rlds.getIsotopeMapAvailable().get(z).iterator();
			while(itr.hasNext()){
				IsotopePoint ip = itr.next();
				int x = xoffset+(ip.getA()-z)*boxSize;
				int y = yoffset+(zmax-z)*boxSize;
				if(ipSelected.getA()==ip.getA() && ipSelected.getZ()==z){
					g2.setColor(new Color(153, 102, 153));
				}else if(mouseZ==z && mouseN==ip.getA()-z){
					g2.setColor(new Color(0, 0, 120));
				}else{
					g2.setColor(new Color(0, 0, 180));
				}
				g2.fillRect(x, y, boxSize, boxSize);
			}
		}
	}
	
	/**
	 * Draw isotope boxes.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void drawIsotopeBoxes(Graphics2D g2, Integer z){
		g2.setColor(ColorFormat.frontColor);
		Iterator<IsotopePoint> itr = rlds.getIsotopeMapFull().get(z).iterator();
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			int x = xoffset+(ip.getA()-z)*boxSize;
			int y = yoffset+(zmax-z)*boxSize;
			g2.drawRect(x, y, boxSize, boxSize);
			if(mouseZ==z && mouseN==ip.getA()-z){
				drawIsotopeLabel(g2, z, ip);
			}else if(ipSelected.getZ()==z && ipSelected.getA()==ip.getA()){
				drawIsotopeLabel(g2, z, ip);
			}
		}
	}
	
	/**
	 * Draw isotope label.
	 *
	 * @param g2 the g2
	 * @param z the z
	 * @param ip the ip
	 */
	private void drawIsotopeLabel(Graphics2D g2, Integer z, IsotopePoint ip){
		String elementString = MainDataStructure.getElementSymbol(z);
		String massString = String.valueOf(ip.getA());
		
		int wid = (getFontMetrics(Fonts.realSmallFont).stringWidth(elementString) 
						+ getFontMetrics(Fonts.tinyFont).stringWidth(massString));  
		int x = (int)(xoffset+boxSize*(ip.getA()-z+0.5)-wid/2.0);
		int y = (int)(yoffset+boxSize*(zmax-z+0.5)+1);
      
		g2.setFont(Fonts.tinyFont);
		g2.setColor(ColorFormat.frontColor);
		g2.drawString(massString, x, y);
    	x+=getFontMetrics(Fonts.tinyFont).stringWidth(massString);
        y+=5;
        g2.setFont(Fonts.realSmallFont);
        g2.drawString(elementString, x, y);   
	}
	
	/**
	 * Draw z drip label.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void drawZDripLabel(Graphics2D g2, Integer z){
		g2.setFont(Fonts.smallFont);
		g2.setColor(ColorFormat.frontColor);
		String elementString = MainDataStructure.getElementSymbol(z);
		int dx = (int)((boxSize-getFontMetrics(Fonts.smallFont).stringWidth(elementString))/2.0);
        int dy = (int)((boxSize-getFontMetrics(Fonts.smallFont).getHeight()/2.0)/2.0);
        int x = xoffset+(rlds.getIsotopeMapFull().get(z).get(0).getA()-z-1)*boxSize+dx;
        int y = yoffset+(zmax-z+1)*boxSize-dy;
        if(!elementString.equals("n")){
        	g2.drawString(elementString, x, y);
        }
	}
	
	/**
	 * Draw n drip labels.
	 *
	 * @param g2 the g2
	 */
	private void drawNDripLabels(Graphics2D g2){
		g2.setFont(Fonts.smallFont);
		g2.setColor(ColorFormat.frontColor);
		Iterator<Integer> itr = minDripZ.iterator();
		int n = 0;
		while(itr.hasNext()){
			String nLabel = String.valueOf(n);
			int x = (int)(xoffset+boxSize*(n+0.5)-getFontMetrics(Fonts.smallFont).stringWidth(nLabel)/2 + 1);
			int y = yoffset+boxSize*(zmax+1-itr.next())+17;
			g2.drawString(nLabel, x, y);
			n++;
		}
	}
	
	/**
	 * Draw crosshairs.
	 *
	 * @param g2 the g2
	 */
	private void drawCrosshairs(Graphics2D g2){
		g2.setStroke(new BasicStroke(2));
    	g2.setColor(Color.red);
    	g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
    	g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
	}
	
}

class IsotopePointComparator implements Comparator<IsotopePoint>{
	public int compare(IsotopePoint one, IsotopePoint two){
		if(one.getZ()!=two.getZ()){
			return one.getZ()-two.getZ();
		}
		return one.getA()-two.getA();
	}
}



