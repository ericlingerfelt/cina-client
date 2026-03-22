package org.nucastrodata.element.elementviz.finalabund;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.geom.*;

import org.nucastrodata.Cina;
import org.nucastrodata.NumberFormats;


/**
 * The Class ElementVizFinalAbundPrintPanel.
 */
public class ElementVizFinalAbundPrintPanel extends JPanel{
	
    /** The xoffset. */
    int xoffset = 58;  
    
    /** The yoffset. */
    int yoffset = 35;
    
    /** The box width. */
    int boxWidth = 29;
    
    /** The box height. */
    int boxHeight = 29;
    
    /** The zmax. */
    int zmax = 0;       
    
    /** The nmax. */
    int nmax = 0; 
    
    /** The zmin. */
    int zmin = 0;
    
    /** The nmin. */
    int nmin = 0;
    
    /** The proton number. */
    int protonNumber=0;   
    
    /** The neutron number. */
    int neutronNumber=0; 
    
    /** The width. */
    int width;           
    
    /** The height. */
    int height;   
            
    /** The xmax. */
    int xmax;                    
    
    /** The ymax. */
    int ymax;   

	/** The ZA map array. */
	Point[] ZAMapArray;

	/** The max drip n. */
	int[] maxDripN;
	
	/** The min drip n. */
	int[] minDripN;
	
	/** The min z drip. */
	int[] minZDrip;

    /** The select color. */
    Color selectColor = new Color(153,102,153);
    
    /** The frame color. */
    Color frameColor = Color.white;
    
    /** The non select color. */
    Color nonSelectColor = new Color(0,0,180);
    
    /** The mouse over color. */
    Color mouseOverColor = new Color(0,0,120);
    
    /** The iso label color. */
    Color isoLabelColor = Color.white;
    
    /** The init abund color. */
    Color initAbundColor = new Color(230,185,0);

    /** The small font. */
    Font smallFont = new Font("SanSerif", Font.PLAIN, 11);
    
    /** The small font metrics. */
    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
    
    /** The real small font. */
    Font realSmallFont = new Font("SanSerif", Font.PLAIN, 10);
    
    /** The real small font metrics. */
    FontMetrics realSmallFontMetrics = getFontMetrics(realSmallFont);
    
    /** The tiny font. */
    Font tinyFont = new Font("SanSerif", Font.PLAIN, 9);
    
    /** The tiny font metrics. */
    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);
    
    /** The big font. */
    Font bigFont = new Font("SanSerif", Font.PLAIN, 16);
    
    /** The big font metrics. */
    FontMetrics bigFontMetrics = getFontMetrics(bigFont);
    
    /** The really big font. */
    Font reallyBigFont = new Font("SanSerif", Font.PLAIN, 20);
    
    /** The really big font metrics. */
    FontMetrics reallyBigFontMetrics = getFontMetrics(reallyBigFont);
    
    /** The really really big font. */
    Font reallyReallyBigFont = new Font("SanSerif", Font.PLAIN, 25);
    
    /** The water mark font. */
    Font waterMarkFont = new Font("SanSerif", Font.PLAIN, 40);
    
    /** The water mark font metrics. */
    FontMetrics waterMarkFontMetrics = getFontMetrics(waterMarkFont);
    
    /** The text font. */
    Font textFont = new Font("SanSerif", Font.PLAIN, 12);
    
    /** The title font. */
    Font titleFont = new Font("SanSerif", Font.BOLD, 12);
    
    /** The viktor. */
    Vector viktor;
    
    /** The scheme. */
    String scheme;
    
    /** The title scale factor. */
    double titleScaleFactor;
    
    /** The legend scale factor. */
    double legendScaleFactor;
    
    /** The legend trans. */
    Point legendTrans;
    
    /** The use white text. */
    boolean useWhiteText;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /**
     * Instantiates a new element viz final abund print panel.
     *
     * @param useWhiteText the use white text
     * @param ds the ds
     */
    public ElementVizFinalAbundPrintPanel(boolean useWhiteText, ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
		setCurrentState(ds.getFinalWeightedAbundScheme(), useWhiteText);
        
    }

	/**
	 * Sets the current state.
	 *
	 * @param scheme the scheme
	 * @param useWhiteText the use white text
	 */
	public void setCurrentState(String scheme, boolean useWhiteText){

		this.scheme = scheme;
		this.useWhiteText = useWhiteText;
		
		ZAMapArray = ds.getFinalNucSimSetDataStructure().getZAMapArray();

		viktor = new Vector();

		for(int i=0; i<ZAMapArray.length; i++){
		
			viktor.addElement(ZAMapArray[i]);
		
		}

		nmax = getNmax();
		zmax = getZmax();
		nmin = getNmin();
		zmin = getZmin();
		minDripN = getMinDripN(zmax, zmin);
		maxDripN = getMaxDripN(zmax, zmin);
		minZDrip = getMinZDrip(nmax, nmin);
        
    	double scale = ((double)Cina.elementVizFrame.elementVizFinalAbundFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
        
    	width = (int)(boxWidth*(nmax+1));
        height = (int)(boxHeight*(zmax+1));
        
        xmax = (int)(xoffset + width);
        ymax = (int)(yoffset + height);
    
    	setSize(xmax+xoffset, ymax+yoffset);
    			
		setPreferredSize(getSize());
		
		titleScaleFactor = getTitleScaleFactor();
		legendTrans = getLegendTrans();
		legendScaleFactor = getLegendScaleFactor();
	
		repaint();
		
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){
	
		int max = 0;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			max = (int)Math.max(max, ZAMapArray[i].getY() - ZAMapArray[i].getX());
		
		}
		
		return max;
	
	}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){
	
		int max = 0;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			max = (int)Math.max(max, ZAMapArray[i].getX());
		
		}
		
		return max;
	
	}
	
	/**
	 * Gets the nmin.
	 *
	 * @return the nmin
	 */
	public int getNmin(){
	
		int min = 1000;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			min = (int)Math.min(min, ZAMapArray[i].getY() - ZAMapArray[i].getX());
		
		}
		
		return min;
	
	}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	public int getZmin(){
	
		int min = 1000;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			min = (int)Math.min(min, ZAMapArray[i].getX());
		
		}
		
		return min;
	
	}
	
	/**
	 * Gets the min drip n.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the min drip n
	 */
	public int[] getMinDripN(int max, int min){
	
		int[] tempArray = new int[max-min+1];
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i] = 3000;
		
		}
		
		int currentZ = -1;
		int currentN = -1;
		
		int counter = 0;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			currentZ = (int)ZAMapArray[i].getX();
			
			currentN = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
			
			if(currentZ==(counter+min)){
			
				tempArray[counter] = Math.min(currentN, tempArray[counter]);
			
			}else{
			
				counter++;
			
				tempArray[counter] = Math.min(currentN, tempArray[counter]);
			
			}
			
		}
		
		return tempArray;
	
	}
	
	/**
	 * Gets the max drip n.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the max drip n
	 */
	public int[] getMaxDripN(int max, int min){
	
		int[] tempArray = new int[max-min+1];
		
		int currentZ = -1;
		int currentN = -1;
		
		int counter = 0;
		
		for(int i=0; i<ZAMapArray.length; i++){
		
			currentZ = (int)ZAMapArray[i].getX();
			
			currentN = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
			
			if(currentZ==(counter+min)){
			
				tempArray[counter] = (int)Math.max(currentN, tempArray[counter]);
			
			}else{
			
				counter++;
			
				tempArray[counter] = (int)Math.max(currentN, tempArray[counter]);
			
			}

		}
		
		return tempArray;
	
	}
	
	/**
	 * Do i belong.
	 *
	 * @param P the p
	 * @param N the n
	 * @return true, if successful
	 */
	boolean doIBelong(int P, int N){
    	
        boolean include = false;
        
        if(P>=zmin && P<=zmax){
        	
            if(N>=minDripN[P-zmin] && N<=maxDripN[P-zmin]){
            	
                include = true;
                
            }
        }
        
        return include;
        
    }
	
	/**
	 * Gets the min z drip.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the min z drip
	 */
	public int[] getMinZDrip(int max, int min){
	
		int[] tempArray = new int[max+1];
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i] = -1;
		
		}
		
		for(int n=nmin; n<max+1; n++){
			
            for(int z=zmin; z<zmax+1; z++){

				if(doIBelong(z, n)){

	                tempArray[n] = z;
	                break;
	                
	            }

            }
            
        }
		
		return tempArray;
	
	}

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D)g;

        super.paintComponent(g2); 	

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints hintsRender = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2.setRenderingHints(hints);
		g2.addRenderingHints(hintsRender);

		if(useWhiteText){
		
			g2.setColor(Color.black);
		
		}else{
			
			g2.setColor(Color.white);
		
		}

		g2.fillRect(0, 0, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());

		double scale = ((double)Cina.elementVizFrame.elementVizFinalAbundFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
		
		int smallFontSize = (int)(11.0*scale);
		int tinyFontSize = (int)(8.0*scale);
		
		Font smallFont = new Font("SanSerif", Font.PLAIN, smallFontSize);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	    Font tinyFont = new Font("SanSerif", Font.PLAIN, tinyFontSize);
	    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);

		Color minimumColor = new Color(0, 0, 0);

		if(scheme.equals("Continuous")){
	
			minimumColor = Cina.elementVizFrame.elementVizFinalAbundFrame.getLogColor(0.0
																									, (double)Cina.elementVizFrame.elementVizFinalAbundFrame.finalAbundMax
																									, (double)Cina.elementVizFrame.elementVizFinalAbundFrame.finalAbundMin);
		}	
		
		Color color = Color.black;

		if(!useWhiteText){color = Color.white;}

        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
        	
        	if(scheme.equals("Continuous")){
        	
				colorFound:
				for(int j=0; j<ds.getFinalNucSimSetDataStructure().getIndexArray().length; j++){
	
					if(ds.getFinalNucSimSetDataStructure().getIndexArray()[j]==i){
					
						int red = (int)ds.getFinalNucSimSetDataStructure().getRedArray()[j];
						int green = (int)ds.getFinalNucSimSetDataStructure().getGreenArray()[j];
						int blue = (int)ds.getFinalNucSimSetDataStructure().getBlueArray()[j];
						
						color = new Color(red, green, blue);
						
						g2.setColor(color);
						
						break colorFound;
					
					}else{
						
						g2.setColor(minimumColor);
					
					}
					
				}
				
				g2.fillRect(xoffset+n*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
        
		        if(useWhiteText){
	        
		        	g2.setColor(frameColor);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
		        
		        g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
			
			}else if(scheme.equals("Binned")){
			
				colorFound:
				for(int j=0; j<ds.getFinalNucSimSetDataStructure().getIndexArray().length; j++){
	
					if((ds.getFinalNucSimSetDataStructure().getIndexArray()[j]==i)
							&& ds.getFinalNucSimSetDataStructure().getRedArray()[j]!=-1){
					
						int red = (int)ds.getFinalNucSimSetDataStructure().getRedArray()[j];
						int green = (int)ds.getFinalNucSimSetDataStructure().getGreenArray()[j];
						int blue = (int)ds.getFinalNucSimSetDataStructure().getBlueArray()[j];
						
						color = new Color(red, green, blue);
						
						g2.setColor(color);
					
						break colorFound;
					
					}else{
						
						if(useWhiteText){
	        
				        	g2.setColor(Color.black);
				        
				       	}else{
				       		
				       		g2.setColor(frameColor);
				       	
				       	}
					
					}
					
				}
			
				g2.fillRect(xoffset+n*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
        
		        if(useWhiteText){
	        
		        	g2.setColor(frameColor);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
		        
		        g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
				
			}

        }
        
        if(Cina.elementVizFrame.elementVizFinalAbundFrame.showStableCheckBox.isSelected()){
	        		
	        for(int i=0; i<ZAMapArray.length; i++){
        	
	        	int z = (int)ZAMapArray[i].getX();
	        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        		
	        	Point isoPoint = new Point(z, z+n);
	        	
		        if(Cina.cinaMainDataStructure.getStablePointVector().contains(isoPoint)){
		        		
		        	g2.setColor(Color.red);

		        	float[] dash1 = {3.0f, 4.0f};
		        	
		        	g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash1, 0.0f));
		        	
		        	g2.drawRect(xoffset+n*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
		        		
		        }
	        		
			}
			
			g2.setStroke(new BasicStroke(1.0f));
			
	  	}
	   
        if(useWhiteText){
	        
        	g2.setColor(Color.white);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
        
        if(Cina.elementVizFrame.elementVizFinalAbundFrame.zoomSlider.getValue()>=75){
        	
	        if(Cina.elementVizFrame.elementVizFinalAbundFrame.showLabelsCheckBox.isSelected()){
	        
		        for(int i=0; i<ZAMapArray.length; i++){
		        	
		        	int z = (int)ZAMapArray[i].getX();
		        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
		                
			        String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
			        String tempS2 = String.valueOf(z+n);
			        
			        int wid = (int)(realSmallFontMetrics.stringWidth(tempS)
			                  + tinyFontMetrics.stringWidth(tempS2));
			                  
			        int xzero = (int)(xoffset+n*boxWidth+boxWidth/2-wid/2);
			        
			        int yzero = (int)(yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
			        
			        g2.setFont(tinyFont);
			        if(useWhiteText){
	        
			        	g2.setColor(Color.white);
			        
			       	}else{
			       		
			       		g2.setColor(Color.black);
			       	
			       	}
		
					if(z>=2){
		
			        	g2.drawString(tempS2,xzero,yzero);
			        	
			        	xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(realSmallFont);
				        g2.drawString(tempS,xzero,yzero);   
				        
				    }else if(z==1 && n==0){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("p",xzero-2,yzero-2);
			        
			       	}else if(z==1 && n==1){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("d",xzero-2,yzero-2);
			        
			       	}else if(z==1 && n==2){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("t",xzero,yzero-2);
			        
			       	}else if(z==0){
			       	
			       		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString(tempS,xzero-2,yzero-2);
			       	
			       	}
		   
		        }
	        
	    	}
	    	
		}

	    if(Cina.elementVizFrame.elementVizFinalAbundFrame.zoomSlider.getValue()>=75){
	    	
	         // Labels for vertical axis

	        g2.setFont(smallFont);
	        if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	        
	        for(int z=zmin; z<=zmax; z++){
		           
	           String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
	           
	           int ds = (int)(minDripN[z-zmin]*boxWidth);   // Inset to drip line
	           g2.drawString(tempS,
	                            xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
	                            yoffset + (zmax-z)*boxHeight + boxHeight/2
	                            + smallFontMetrics.getHeight()/2 -3);
	       		
	        }
	        
	        // Labels for horizontal axis
	
	        g2.setFont(smallFont);
	        if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	        
	        for(int n=nmin; n<nmax+1; n++){
	        	
	           String tempS = String.valueOf(n);
	           
	           if(minZDrip[n]!=-1){
	           
		           g2.drawString(tempS,
		                        xoffset+n*boxWidth+boxWidth/2
		                        -smallFontMetrics.stringWidth(tempS)/2 + 1,
		                        yoffset+height + 17 - minZDrip[n]*boxHeight);
	                        
	           }
	                        
	        }
        
    	}
    	
    	g2.setRenderingHints(hintsText);
    	
    	if(titleScaleFactor<1.0){
		
			g2.scale(titleScaleFactor, titleScaleFactor);
		
		}
		
		g2.translate(50, 50);
    	
    	if(useWhiteText){
	        
        	g2.setColor(Color.white);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
       	
       	String string = ds.getFinalNucSimSetDataStructure().getPath();
       	
		g2.setFont(reallyBigFont);
		g2.drawString(string.substring(string.lastIndexOf("/")+1), 0, 0);

		g2.translate(-50, -50);
    	
    	if(titleScaleFactor<1.0){
    		
    		g2.scale(1/titleScaleFactor, 1/titleScaleFactor);
    	
    	}
	
		g2.translate((int)legendTrans.getX(), (int)legendTrans.getY());
		g2.scale(legendScaleFactor, legendScaleFactor);

		g2.setColor(Color.gray);
    	g2.setFont(waterMarkFont);
    	g2.drawString("nucastrodata.org", -70, 300);
    	
    	if(useWhiteText){
	        
        	g2.setColor(Color.white);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
    	g2.drawString("nucastrodata.org", -72, 302);
    	
    	g2.setRenderingHints(hintsText);
    	
    	if(scheme.equals("Continuous")){
		
			for(int i=0; i<200; i++){
				
				g2.setColor(Cina.elementVizFrame.elementVizFinalAbundFrame.getRGB((double)i/200.0));
				g2.drawLine(28, 200-i, 63, 200-i);
			
			}
			
			g2.setColor(Color.black);
	    	g2.drawRect(28, 0, 35, 200);
			
		}else if(scheme.equals("Binned")){
			
			g2.setFont(realSmallFont);
		
			Vector binDataVector = ds.getFinalWeightedAbundBinData();
			
			int minMagTotal = ((Integer)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).intValue();
			int maxMagTotal = ((Integer)((Vector)binDataVector.elementAt(0)).elementAt(1)).intValue();

			Color tempColor = new Color(0, 0, 0);

			boolean noShowBinToggle = true;

			for(int i=0; i<200; i++){
			
				double mag = ((((double)maxMagTotal-(double)minMagTotal)/200.0)*(double)i) + (double)minMagTotal;

				binFound:
				for(int j=0; j<binDataVector.size(); j++){
	
					int minMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(0)).intValue();
					int maxMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(1)).intValue();
					
					if(mag>=minMag && mag<maxMag){
					
						if(((Boolean)((Vector)binDataVector.elementAt(j)).elementAt(2)).booleanValue()){
					
							tempColor = (Color)((Vector)binDataVector.elementAt(j)).elementAt(3);
							break binFound;
							
						}else{
						
							if(noShowBinToggle){
							
								noShowBinToggle = !noShowBinToggle;
								tempColor = Color.white;
							
							}else{
							
								noShowBinToggle = !noShowBinToggle;
								tempColor = Color.red;
							
							}
					
							break binFound;
						
						}
						
					}
					
				}

				g2.setColor(tempColor);
				g2.drawLine(28, 200-i, 63, 200-i);
				
			}
			
			g2.setColor(Color.black);
    		g2.drawRect(28, 0, 35, 200);
			
			for(int i=1; i<binDataVector.size(); i++){
				
		        g2.setColor(Color.black);
			
				int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
			
				int lineIndex = (int)((200.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
				
				g2.drawLine(52, 200-lineIndex, 63, 200-lineIndex);
				g2.setFont(realSmallFont);
				g2.drawString(String.valueOf(mag), 30, 204-lineIndex);

			}
		
		
		}
		if(scheme.equals("Continuous")){
			
			g2.setFont(bigFont);
			if(useWhiteText){
        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
			g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizFinalAbundFrame.getFinalAbundMax()), 71, 16);
			g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizFinalAbundFrame.getFinalAbundMin()), 71, 200);
		
			g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
		
			g2.setFont(reallyReallyBigFont);
			g2.drawString("Final Weighted", -186, -10);
		
			g2.setFont(reallyReallyBigFont);
			g2.drawString("Abundance", -167, 18);
		
			g2.transform(AffineTransform.getRotateInstance(Math.PI/2));
		
		}else if(scheme.equals("Binned")){
			
			g2.setFont(bigFont);
			if(useWhiteText){
        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	       	
	       	Vector binDataVector = ds.getFinalWeightedAbundBinData();
	       	
			g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(0))).elementAt(1)).intValue())), 71, 16);
			g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(binDataVector.size()-1))).elementAt(0)).intValue())), 71, 200);
		
			g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
		
			g2.setFont(reallyReallyBigFont);
			g2.drawString("Final Weighted", -186, -10);
		
			g2.setFont(reallyReallyBigFont);
			g2.drawString("Abundance", -167, 18);
			
			g2.transform(AffineTransform.getRotateInstance(Math.PI/2));
			
		}	

    }
    
    /**
     * Gets the legend trans.
     *
     * @return the legend trans
     */
    public Point getLegendTrans(){
		
		double legendXPos = 0.0;
		double legendYPos = 0.0;
		int legendXTrans = 0;
	    int legendYTrans = 0;
		
		legendXPos = width - 158;
		legendYPos = height - 300;
		
		if(legendXPos>0 && legendYPos>0){
		
			Point point = new Point(0, 0);
		
			for(int i=0; i<ZAMapArray.length; i++){
	        	
	        	int z = (int)ZAMapArray[i].getX();
	        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        	
	        	int isoXPos = xoffset+n*boxWidth+boxHeight;
	        	int isoYPos = yoffset+(zmax-z)*boxHeight+boxWidth;
	        
	        	double legendDistance = Math.sqrt((legendXPos*legendXPos) + (legendYPos*legendYPos));
				double isoDistance = Math.sqrt((isoXPos*isoXPos) + (isoYPos*isoYPos));
	        
	        	if(isoDistance>legendDistance){
	        		
	        		Point currentPoint = new Point((int)(isoXPos - legendXPos), (int)(isoYPos - legendYPos));
	
	        		if(currentPoint.getX()>point.getX() && currentPoint.getY()>point.getY()){
	        		
	        			point.setLocation((int)currentPoint.getX(), (int)currentPoint.getY());
	        		
	        		}
	        		
	        	}
	        	
	        }
	        
	        legendXTrans = (int)point.getX() + 10;
			legendYTrans = (int)point.getY() + 10;
			
			if(legendXTrans==10 && legendYTrans==10){
			
				legendXTrans = 0;
				legendYTrans = 0;
			
			}
        
        	legendXTrans = (int)legendXPos + legendXTrans;
        	legendYTrans = (int)legendYPos + legendYTrans;
        
    	}else{
    	
    		double isoDistance = 1E6;

    		for(int i=0; i<ZAMapArray.length; i++){
	        	
	        	int z = (int)ZAMapArray[i].getX();
	        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        	
	        	int isoXPos = xoffset+n*boxWidth+boxHeight;
	        	int isoYPos = yoffset+(zmax-z)*boxHeight+boxWidth;
	        	
	        	double currentDistance = Math.sqrt(Math.pow(getSize().getWidth()-isoXPos, 2) + Math.pow(getSize().getHeight()-isoYPos, 2));

				isoDistance = Math.min(currentDistance, isoDistance);
	        	
	        }
	        
	        double newLegendXPos = getSize().getWidth() - (isoDistance/Math.sqrt(2.0));
	        double newLegendYPos = getSize().getHeight() - (isoDistance/Math.sqrt(2.0));
	        
	        legendXTrans = (int)newLegendXPos;
	        legendYTrans = (int)newLegendYPos;

    	}
    	
    	return new Point(legendXTrans, legendYTrans);
	
	}
	
	/**
	 * Gets the legend scale factor.
	 *
	 * @return the legend scale factor
	 */
	public double getLegendScaleFactor(){
	
		double factor = 1.0;
		
		int legendWidth = waterMarkFontMetrics.stringWidth("nucastrodata.org");
		int legendHeight = 315;
		
		double widthFactor = (getSize().getWidth()-legendTrans.getX())/legendWidth;
		double heightFactor = (getSize().getHeight()-legendTrans.getY())/legendHeight;
		
		factor = Math.min(widthFactor, heightFactor);
		
		int leftSpace = waterMarkFontMetrics.stringWidth("nuca");	
		
		int widthSpace = (int)(getSize().getWidth()-legendTrans.getX() + leftSpace*factor - factor*legendWidth);
		
		if(widthSpace>=25){
		
			legendTrans.setLocation(legendTrans.getX()+widthSpace-25, legendTrans.getY());
		
		}
		
		int heightSpace = (int)((getSize().getHeight()-legendTrans.getY()) - (factor*legendHeight));
		
		if(heightSpace>=25){
		
			legendTrans.setLocation(legendTrans.getX(), legendTrans.getY()+heightSpace-25);
		
		}

		return factor;
	
	}
	
	/**
	 * Gets the title scale factor.
	 *
	 * @return the title scale factor
	 */
	public double getTitleScaleFactor(){
   
		double factor = 1.0;
		
		String string = ds.getFinalNucSimSetDataStructure().getPath();
     
		int width = reallyBigFontMetrics.stringWidth(string.substring(string.lastIndexOf("/")+1));
		int xLabel = 50;
		int fullWidth = xLabel + width;
		int hite = reallyBigFontMetrics.getHeight();
		int yLabel = 50 - reallyBigFontMetrics.getHeight();
		int fullHite = yLabel + hite;
        
        double isoDistance = 1E6;
        
        Point point = new Point(0, 0);
        
        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
        	
        	int isoXPos = xoffset+n*boxWidth;
        	int isoYPos = yoffset+(zmax-z)*boxHeight;
        	
        	double currentDistance = Math.sqrt(Math.pow(isoXPos, 2) + Math.pow(isoYPos, 2));
        	
        	if(currentDistance<isoDistance){
        	
        		isoDistance = currentDistance;
        		point.setLocation((int)(isoXPos), (int)(isoYPos));
        	
        	}
        	
        }
		
		double widthFactor = point.getX()/fullWidth;
		double hiteFactor = point.getY()/fullHite;
		
		factor = Math.min(widthFactor, hiteFactor);
		
		return factor;
    
    }

}

