package org.nucastrodata.element.elementviz.animator;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.text.*;
import java.awt.geom.*;
import java.io.*;

import org.nucastrodata.Cina;


/**
 * The Class ElementVizAnimatorPrintPanel.
 */
public class ElementVizAnimatorPrintPanel extends JPanel{
	
	/** The log10. */
	final double log10 = 0.434294482;
	
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
    int protonNumber = 0;   
    
    /** The neutron number. */
    int neutronNumber = 0; 
    
    /** The ymax. */
    int width, height, xmax, ymax;            

	/** The ZA map array. */
	Point[] ZAMapArray;

	/** The min z drip. */
	int[] maxDripN, minDripN, minZDrip;

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
    
    /** The really really big font metrics. */
    FontMetrics reallyReallyBigFontMetrics = getFontMetrics(reallyReallyBigFont);
    
    /** The water mark font. */
    Font waterMarkFont = new Font("SanSerif", Font.PLAIN, 40);
    
    /** The water mark font metrics. */
    FontMetrics waterMarkFontMetrics = getFontMetrics(waterMarkFont);
    
    /** The text font. */
    Font textFont = new Font("SanSerif", Font.PLAIN, 12);
    
    /** The title font. */
    Font titleFont = new Font("SanSerif", Font.BOLD, 12);
   
	/** The scale. */
	double scale;
    
    /** The type. */
    String type;
    
    /** The scheme. */
    String scheme;
    
    /** The slider bar scale factor. */
    double sliderBarScaleFactor;
    
    /** The legend scale factor. */
    double legendScaleFactor;
    
    /** The legend trans. */
    Point legendTrans;
    
    /** The use white text. */
    boolean useWhiteText;
    
    /** The stroke array. */
    BasicStroke[][] strokeArray;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /**
     * Instantiates a new element viz animator print panel.
     *
     * @param useWhiteText the use white text
     * @param ds the ds
     */
    public ElementVizAnimatorPrintPanel(boolean useWhiteText, ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	setBackground(Color.black);
    	setStrokeArray();
    	setCurrentState(Cina.elementVizFrame.elementVizAnimatorFrame.type
    					, Cina.elementVizFrame.elementVizAnimatorFrame.scheme
    					, Cina.elementVizFrame.elementVizAnimatorFrame.zmin
    					, Cina.elementVizFrame.elementVizAnimatorFrame.zmax
    					, useWhiteText);
    	
    }

	/**
	 * Sets the stroke array.
	 */
	public void setStrokeArray(){
	
		strokeArray = new BasicStroke[6][4];
		
		float[] dash1 = {3.0f, 4.0f};
		float[] dash2 = {2.0f, 3.0f};
		float[] dash3 = {1.0f, 2.0f};
		
		for(int i=0; i<6; i++){
		
			strokeArray[i][0] = new BasicStroke((float)(i));
			strokeArray[i][1] = new BasicStroke((float)(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash1, 0.0f);
			strokeArray[i][2] = new BasicStroke((float)(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash2, 0.0f);
			strokeArray[i][3] = new BasicStroke((float)(i), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash3, 0.0f);
					
		}
	
	}

	/**
	 * Sets the current state.
	 *
	 * @param type the type
	 * @param scheme the scheme
	 * @param useWhiteText the use white text
	 */	
	public void setCurrentState(String type, String scheme, int zmin, int zmax, boolean useWhiteText){

		this.type = type;
		this.scheme = scheme;
		this.useWhiteText = useWhiteText;
		
		ZAMapArray = ds.getAnimatorNucSimDataStructure().getZAMapArray();
		
		this.zmin = zmin;
		this.zmax = zmax;
		this.nmin = getNmin(ZAMapArray);
		this.nmax = getNmax(ZAMapArray);
		
		minDripN = getMinDripN(zmax, zmin);
		maxDripN = getMaxDripN(zmax, zmin);
		minZDrip = getMinZDrip(nmax, nmin);

		scale = ((double)Cina.elementVizFrame.elementVizAnimatorFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
    
		width = (int)(boxWidth*((nmax-nmin)+1));
        height = (int)(boxHeight*((zmax-zmin)+1));
        
        xmax = (int)(xoffset + width);
        ymax = (int)(yoffset + height);
    	
    	setSize(xmax+xoffset, ymax+yoffset);
    			
		setPreferredSize(getSize());

		sliderBarScaleFactor = getSliderBarScaleFactor();

		legendTrans = getLegendTrans(type, scheme);
		legendScaleFactor = getLegendScaleFactor(type, scheme);
		
		repaint();
		
	}
	
	private int getNmin(Point[] ZAMapArray){
		int min = 1000;
		for(int i=0; i<ZAMapArray.length; i++){
			int currentZ = (int)ZAMapArray[i].getX();
			if(currentZ <= zmax && currentZ >= zmin){
				min = (int)Math.min(min, ZAMapArray[i].getY() - ZAMapArray[i].getX());
			}
		}
		return min;
	}
	
	private int getNmax(Point[] ZAMapArray){
		int max = 0;
		for(int i=0; i<ZAMapArray.length; i++){
			int currentZ = (int)ZAMapArray[i].getX();
			if(currentZ <= zmax && currentZ >= zmin){
				max = (int)Math.max(max, ZAMapArray[i].getY() - ZAMapArray[i].getX());
			}
		}
		return max;
	}
	
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
			if(currentZ <= max && currentZ >= min){
				if(currentZ==(counter+min)){
					tempArray[counter] = Math.min(currentN, tempArray[counter]);
				}else{
					counter++;
					tempArray[counter] = Math.min(currentN, tempArray[counter]);
				}
			}
		}
		
		return tempArray;
	}
	
	public int[] getMaxDripN(int max, int min){
	
		int[] tempArray = new int[max-min+1];
		
		int currentZ = -1;
		int currentN = -1;
		
		int counter = 0;
		
		for(int i=0; i<ZAMapArray.length; i++){
			currentZ = (int)ZAMapArray[i].getX();
			currentN = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
			if(currentZ <= max && currentZ >= min){
				if(currentZ==(counter+min)){
					tempArray[counter] = (int)Math.max(currentN, tempArray[counter]);
				}else{
					counter++;
					tempArray[counter] = (int)Math.max(currentN, tempArray[counter]);
				}
			}
		}
		return tempArray;
	
	}
	
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
		RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2.setRenderingHints(hints);
		g2.addRenderingHints(hintsText);

		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
		
		int smallFontSize = (int)(11.0*scale);
		int tinyFontSize = (int)(8.0*scale);
		
		Font smallFont = new Font("SanSerif", Font.PLAIN, smallFontSize);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	    Font tinyFont = new Font("SanSerif", Font.PLAIN, tinyFontSize);
	    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);
	    
		if(useWhiteText){
		
			g2.setColor(Color.black);
		
		}else{
			
			g2.setColor(Color.white);
		
		}
		g2.fillRect(0, 0, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());
		
		Color minimumColor = new Color(0, 0, 0);

		if(scheme.equals("Continuous")){

			if(type.equals("Abundance")){
	
				minimumColor = Cina.elementVizFrame.elementVizAnimatorFrame.getLogColor(0.0f
																										, Cina.elementVizFrame.elementVizAnimatorFrame.abundMax
																										, Cina.elementVizFrame.elementVizAnimatorFrame.abundMin);
				
			}else if(type.equals("Derivative")){
	
				minimumColor = Cina.elementVizFrame.elementVizAnimatorFrame.getLogColorDerAbund(0.0f
																										, Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMax
																										, Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMin);
				
			}else if(type.equals("Reaction Flux")){
	
				minimumColor = Cina.elementVizFrame.elementVizAnimatorFrame.getLogColor(0.0f
																										, Cina.elementVizFrame.elementVizAnimatorFrame.fluxMax
																										, Cina.elementVizFrame.elementVizAnimatorFrame.fluxMin);
				
			}
			
		}

		int step = Cina.elementVizFrame.elementVizAnimatorFrame.timeStepSlider.getValue();

		Color color = Color.black;
		
		if(!useWhiteText){color = Color.white;}
		
		boolean al26Found = false;
		
        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        	
        	if(z<zmin || z>zmax || n<nmin || n>nmax){
        		continue;
        	}
        	
        	if(al26Found && z==13 && n==13){
        		continue;
        	}
        	
        	if(scheme.equals("Continuous")){
        	
	        	if(type.equals("Abundance")){
	        	
	        		g2.setColor(minimumColor);
	        		
					colorFound:
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getIndexArray().length; j++){
		
						if(ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getIndexArray()[j]==i){
						
							int red = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getRedArray()[j];
							int green = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getGreenArray()[j];
							int blue = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							if(z==13 && n==13){
								al26Found = true;
			            	}
							
							g2.setColor(color);
							
							break colorFound;
						
						}
						
					}
					
	        		g2.fillRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
	        
	        		if(Cina.elementVizFrame.elementVizAnimatorFrame.showOutlineCheckBox.isSelected()){
	        
				        if(useWhiteText){
	        
				        	g2.setColor(frameColor);
				        
				       	}else{
				       		
				       		g2.setColor(Color.black);
				       	
				       	}
				        
				        g2.drawRect(xoffset+(n-nmin)*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
				
					}
				
				}else if(type.equals("Derivative")){
				
					colorFound:
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray().length; j++){
		
						if(ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray()[j]==i){
	
							int red = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getRedArray()[j];
							int green = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getGreenArray()[j];
							int blue = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							g2.setColor(color);
	
							if(z==13 && n==13){
								al26Found = true;
			            	}
							
							break colorFound;
						
						}else{
							
							if(useWhiteText){
	        
					        	g2.setColor(Color.black);
					        
					       	}else{
					       		
					       		g2.setColor(frameColor);
					       	
					       	}
						
						}
					
					}
				
					if(ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray().length==0){
					
						if(useWhiteText){
	        
				        	g2.setColor(Color.black);
				        
				       	}else{
				       		
				       		g2.setColor(frameColor);
				       	
				       	}
				
					}
				
					g2.fillRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
					
					if(Cina.elementVizFrame.elementVizAnimatorFrame.showOutlineCheckBox.isSelected()){
					      
				        if(useWhiteText){
	        
				        	g2.setColor(frameColor);
				        
				       	}else{
				       		
				       		g2.setColor(Color.black);
				       	
				       	}
				        
				        g2.drawRect(xoffset+(n-nmin)*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
			                   
			        }
				
				}else if(type.equals("Reaction Flux")){
	        	
	        		if(Cina.elementVizFrame.elementVizAnimatorFrame.showStableCheckBox.isSelected()){
	        		
	        			Point isoPoint = new Point(z, z+n);
	        	
		        		if(Cina.cinaMainDataStructure.getStablePointVector().contains(isoPoint)){
		        		
		        			g2.setColor(new Color(70, 70, 70));
		        		
		        		}else{
		        		
		        			if(useWhiteText){
	        
					        	g2.setColor(Color.black);
					        
					       	}else{
					       		
					       		g2.setColor(frameColor);
					       	
					       	}
		        		
		        		}
	        		
	        		}else{
	        		
	        			if(useWhiteText){
	        
				        	g2.setColor(Color.black);
				        
				       	}else{
				       		
				       		g2.setColor(frameColor);
				       	
				       	}
	        			
	        		}
					
	        		g2.fillRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
				      
			        if(useWhiteText){
	        
			        	g2.setColor(frameColor);
			        
			       	}else{
			       		
			       		g2.setColor(Color.black);
			       	
			       	}
		        
			        g2.drawRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
				
				}
			
			}else if(scheme.equals("Binned")){
			
				if(type.equals("Abundance")){
	        	
					colorFound:
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getIndexArray().length; j++){
		
						if((ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getIndexArray()[j]==i)
								&& ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getRedArray()[j]!=-1){
						
							int red = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getRedArray()[j];
							int green = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getGreenArray()[j];
							int blue = (int)ds.getAnimatorNucSimDataStructure().getAbundTimestepDataStructureArray()[step].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							g2.setColor(color);
						
							if(z==13 && n==13){
								al26Found = true;
			            	}
							
							break colorFound;
						
						}else{
							
							if(useWhiteText){
	        
					        	g2.setColor(Color.black);
					        
					       	}else{
					       		
					       		g2.setColor(frameColor);
					       	
					       	}
						
						}
						
					}
				
					g2.fillRect(xoffset+(n-nmin)*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
	        
	        		if(Cina.elementVizFrame.elementVizAnimatorFrame.showOutlineCheckBox.isSelected()){
	        
				        if(useWhiteText){
	        
				        	g2.setColor(frameColor);
				        
				       	}else{
				       		
				       		g2.setColor(Color.black);
				       	
				       	}
				        
				        g2.drawRect(xoffset+(n-nmin)*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
			                   
			       	}
				
				}else if(type.equals("Derivative")){
				
					colorFound:
					for(int j=0; j<ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray().length; j++){
		
						if((ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray()[j]==i)
								&& ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getRedArray()[j]!=-1){
							
							int red = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getRedArray()[j];
							int green = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getGreenArray()[j];
							int blue = (int)ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							if(z==13 && n==13){
								al26Found = true;
			            	}
							
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
					
					if(ds.getAnimatorNucSimDataStructure().getDerAbundTimestepDataStructureArray()[step].getIndexArray().length==0){
					
						if(useWhiteText){
	        
				        	g2.setColor(Color.black);
				        
				       	}else{
				       		
				       		g2.setColor(frameColor);
				       	
				       	}
					
					}
					
					g2.fillRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
	        		
	        		if(Cina.elementVizFrame.elementVizAnimatorFrame.showOutlineCheckBox.isSelected()){
	        		
		        		if(useWhiteText){
	        
				        	g2.setColor(frameColor);
				        
				       	}else{
				       		
				       		g2.setColor(Color.black);
				       	
				       	}
		        			
		        		g2.drawRect(xoffset+(n-nmin)*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
			                   
			        }
				
				}else if(type.equals("Reaction Flux")){
	        	
					if(Cina.elementVizFrame.elementVizAnimatorFrame.showStableCheckBox.isSelected()){
	        		
	        			Point isoPoint = new Point(z, z+n);
	        	
		        		if(Cina.cinaMainDataStructure.getStablePointVector().contains(isoPoint)){
		        		
		        			g2.setColor(new Color(70, 70, 70));
		        		
		        		}else{
		        		
		        			if(useWhiteText){
	        
					        	g2.setColor(Color.black);
					        
					       	}else{
					       		
					       		g2.setColor(frameColor);
					       	
					       	}
		        		
		        		}
	        		
	        		}else{
	        		
	        			if(useWhiteText){
	        
				        	g2.setColor(Color.black);
				        
				       	}else{
				       		
				       		g2.setColor(frameColor);
				       	
				       	}
	        			
	        		}
					
					g2.fillRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
				        
			        if(useWhiteText){
	        
			        	g2.setColor(frameColor);
			        
			       	}else{
			       		
			       		g2.setColor(Color.black);
			       	
			       	}
			        
			        g2.drawRect(xoffset+(n-nmin)*boxWidth,
			                   yoffset+(zmax-z)*boxHeight,
			                   boxWidth,boxHeight);
				
				}
			
			}

        }
        
        if(Cina.elementVizFrame.elementVizAnimatorFrame.showStableCheckBox.isSelected()
        	&& (type.equals("Derivative") || type.equals("Abundance"))){
	        		
	        for(int i=0; i<ZAMapArray.length; i++){
        	
	        	int z = (int)ZAMapArray[i].getX();
	        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        		
	        	if(z<zmin || z>zmax || n<nmin || n>nmax){
	        		continue;
	        	}
	        	
	        	Point isoPoint = new Point(z, z+n);
	        	
		        if(Cina.cinaMainDataStructure.getStablePointVector().contains(isoPoint)){
		        		
		        	g2.setColor(Color.red);

		        	float[] dash1 = {3.0f, 4.0f};
		        	
		        	g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash1, 0.0f));
		        	
		        	g2.fillRect(xoffset+(n-nmin)*boxWidth,
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
        
        if(Cina.elementVizFrame.elementVizAnimatorFrame.zoomSlider.getValue()>=75){
        	
	        if(Cina.elementVizFrame.elementVizAnimatorFrame.showLabelsCheckBox.isSelected()){
	        
		        for(int i=0; i<ZAMapArray.length; i++){
		        	
		        	int z = (int)ZAMapArray[i].getX();
		        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
		                
		        	if(z<zmin || z>zmax || n<nmin || n>nmax){
		        		continue;
		        	}
		        	
			        String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
			        String tempS2 = String.valueOf(z+n);
			        
			        int wid = (int)(realSmallFontMetrics.stringWidth(tempS)
			                  + tinyFontMetrics.stringWidth(tempS2));
			                  
			        int xzero = (xoffset+(n-nmin)*boxWidth+boxWidth/2-wid/2);
			        
			        int yzero = (yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
			        
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
        
        if(type.equals("Reaction Flux")){

    		for(int i=0; i<ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getIndexArray().length; i++){
    		
    			int fluxIndex = ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getIndexArray()[i];
    			
    			if(fluxIndex>=0){
    			
	    			int reactantIndex = 0;
	    			int productIndex = 0;
	    			
	    			if(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getFluxArray()[i]>=0){
	    			
		    			reactantIndex = (int)(ds.getAnimatorNucSimDataStructure().getReactionMapArray()[fluxIndex].getX());
		    			productIndex = (int)(ds.getAnimatorNucSimDataStructure().getReactionMapArray()[fluxIndex].getY());

	    			}else{
	    			
	    				reactantIndex = (int)(ds.getAnimatorNucSimDataStructure().getReactionMapArray()[fluxIndex].getY());
		    			productIndex = (int)(ds.getAnimatorNucSimDataStructure().getReactionMapArray()[fluxIndex].getX());

		    		}
		    			
	    			int zIn = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[reactantIndex].getX());
	    			int nIn = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[reactantIndex].getY()
	    							- ds.getAnimatorNucSimDataStructure().getZAMapArray()[reactantIndex].getX());
	    			
	    			int zOut = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[productIndex].getX());
	    			int nOut = (int)(ds.getAnimatorNucSimDataStructure().getZAMapArray()[productIndex].getY()
	    							- ds.getAnimatorNucSimDataStructure().getZAMapArray()[productIndex].getX());
	    			
	    			if(zIn<zmin || zIn>zmax || nIn<nmin || nIn>nmax){
	            		continue;
	            	}
	    			
	    			if(zOut<zmin || zOut>zmax || nOut<nmin || nOut>nmax){
	            		continue;
	            	}
	    			
	    			if(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getRedArray()[i]!=-1){
	    			
	    				int red = (int)ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getRedArray()[i];
						int green = (int)ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getGreenArray()[i];
						int blue = (int)ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getBlueArray()[i];
						
						color = new Color(red, green, blue);
						
						if(!color.equals(Color.black)){
						
							g2.setColor(color);
							
			    			g2.setStroke(strokeArray[(int)ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getLinewidthArray()[i]][(int)ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getLinestyleArray()[i]]);
			    			
			    			double arrowAngle = Math.PI/8;
	  						int arrowLength = 10;
			    			
	  						double x1 = xoffset+(nIn-nmin+0.5)*boxWidth;
			    			double y1 = yoffset+(zmax-zIn+0.5)*boxHeight;
			    			double x2 = xoffset+(nOut-nmin+0.5)*boxWidth;
			    			double y2 = yoffset+(zmax-zOut+0.5)*boxHeight;
			    			
			    			Line2D.Double newLine = new Line2D.Double(x1, y1, x2, y2);
			    			
			    			g2.draw(newLine);

			    			double theta = Math.atan((double)(y2-y1)/(double)(x2-x1));
	    					double length = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
			    			
			    			int side = 1;
			    			
			    			if(x2<x1){side = -1;}
			    			
			    			Point tip = new Point((int)(x1+side*length*Math.cos(theta))
			    							, (int)(y1+side*length*Math.sin(theta)));
			    							
							Point side1 = new Point((int)(tip.getX()-side*arrowLength*Math.cos(theta+arrowAngle))
											, (int)(tip.getY()-side*arrowLength*Math.sin(theta+arrowAngle)));
											
							Point side2 = new Point((int)(tip.getX()-side*arrowLength*Math.cos(theta-arrowAngle))
											, (int)(tip.getY()-side*arrowLength*Math.sin(theta-arrowAngle)));
			    			
			    			g2.setStroke(new BasicStroke((float)(ds.getAnimatorNucSimDataStructure().getFluxTimestepDataStructureArray()[step].getLinewidthArray()[i])));
			    				
			    			g2.draw(new Line2D.Double(tip.getX()
				    							, tip.getY()
				    							, side1.getX()
				    							, side1.getY()));
				    							
				    		g2.draw(new Line2D.Double(tip.getX()
				    							, tip.getY()
				    							, side2.getX()
				    							, side2.getY()));
			    							
			    		}
			    							
			    	}
    							
    			}
    		
    		}
    	
    	}
        
        if(Cina.elementVizFrame.elementVizAnimatorFrame.zoomSlider.getValue()>=75){
	    	
	        // Labels for vertical axis

	        g2.setFont(smallFont);
	        if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	        
	        for(int z=zmin; z<=zmax; z++){
		           
	           String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
	           
	           int ds = (int)((minDripN[z-zmin]-nmin)*boxWidth);   // Inset to drip line
	           g2.drawString(tempS,
	                            xoffset - 8 + ds - smallFontMetrics.stringWidth(tempS),
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
	                        xoffset+(n-nmin)*boxWidth+boxWidth/2
	                        -smallFontMetrics.stringWidth(tempS)/2 + 1,
	                        yoffset+height + 17 + (-minZDrip[n]+zmin)*boxHeight);
	                        
	           }
	                        
	        }
        
    	}
    	
    	int yLabel = 0;
		
		if(useWhiteText){
	        
        	g2.setColor(Color.white);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}

		if(sliderBarScaleFactor<1.0){
		
			g2.scale(sliderBarScaleFactor, sliderBarScaleFactor);
		
		}
		
		g2.translate(50, 50);
		
		g2.setFont(reallyBigFont);
		g2.drawString(ds.getAnimatorNucSimDataStructure().getNucSimName(), 0, yLabel);
		
		yLabel += 30;
    	
		g2.setFont(bigFont);
		g2.drawString("Timestep = " + String.valueOf(step), 0, yLabel);
		
		yLabel += 30;
    	
		g2.setFont(bigFont);
		g2.drawString("Time (sec) = " 
						+ getFormattedTemp(ds.getAnimatorNucSimDataStructure().getTimeArray()[step] 
						- Cina.elementVizFrame.elementVizAnimatorFrame.normalTime)
						, 0
						, yLabel);
		
		yLabel += 30;
    	
		g2.setFont(bigFont);
		g2.drawString("Density (g/cm^3) = " + getFormattedDensity(ds.getAnimatorNucSimDataStructure().getDensityArray()[step]), 0, yLabel);
		
		yLabel += 30;
    	
		g2.setFont(bigFont);
		g2.drawString("Temperature (T9) = " + getFormattedTemp(ds.getAnimatorNucSimDataStructure().getTempArray()[step]), 0, yLabel);
		
		yLabel += 30;
    	
    	g2.translate(-50, -50);
    	
    	if(sliderBarScaleFactor<1.0){
    		
    		g2.scale(1/sliderBarScaleFactor, 1/sliderBarScaleFactor);
    	
    	}
    	
    	/////////////////////////////////////////////////////////////////////////////////////////////
    	
    	g2.translate((int)legendTrans.getX(), (int)legendTrans.getY());
    	g2.scale(legendScaleFactor, legendScaleFactor);
    	
    	if(type.equals("Reaction Flux")
			&& scheme.equals("Binned")){
		
			g2.setColor(Color.gray);
	    	g2.setFont(waterMarkFont);
	    	g2.drawString("nucastrodata.org", -48, 228);
	    	
	    	if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	    	g2.drawString("nucastrodata.org", -50, 230);
		
		}else{

			g2.setColor(Color.gray);
	    	g2.setFont(waterMarkFont);
	    	g2.drawString("nucastrodata.org", -70, 300);
	    	
	    	if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	    	g2.drawString("nucastrodata.org", -72, 302);
		
		}
	
		g2.setRenderingHints(hintsText);
		
		if(scheme.equals("Continuous")){
		
			for(int i=0; i<200; i++){
				
				g2.setColor(Cina.elementVizFrame.elementVizAnimatorFrame.getRGB((double)i/200.0));
				g2.drawLine(28, 200-i, 63, 200-i);
			
			}
		
			g2.setColor(Color.black);
	    	g2.drawRect(28, 0, 35, 200);
		
			if(type.equals("Derivative")){

				g2.setColor(Color.black);
				g2.setStroke(new BasicStroke(1));
				g2.drawLine(28, 100, 63, 100);
				
				g2.setFont(realSmallFont);
				g2.setStroke(new BasicStroke(1));
					
				g2.drawString("\u00b1" + "1E" + String.valueOf(Cina.elementVizFrame.elementVizAnimatorFrame.derAbundMag), 30, 97);
				
			}
		
		}else if(scheme.equals("Binned")){
		
			g2.setFont(realSmallFont);
		
			if(type.equals("Abundance")){
			
				Vector binDataVector = ds.getAbundBinData();
			
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
					g2.drawString(String.valueOf(mag), 30, 62-lineIndex);

				}
			
			}else if(type.equals("Derivative")){

				Vector binDataVector = ds.getDerBinData();

				int minMagTotal = ((Integer)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).intValue();
				int maxMagTotal = ((Integer)((Vector)binDataVector.elementAt(0)).elementAt(1)).intValue();

				Color tempColor = new Color(0, 0, 0);

				boolean noShowBinToggle = true;

				for(int i=0; i<100; i++){
				
					double mag = ((((double)maxMagTotal-(double)minMagTotal)/100.0)*(double)i) + (double)minMagTotal;

					binFound:
					for(int j=0; j<binDataVector.size(); j++){
		
						int minMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(0)).intValue();
						int maxMag = ((Integer)((Vector)binDataVector.elementAt(j)).elementAt(1)).intValue();
						
						if(mag>=minMag && mag<maxMag){
						
							if(((Boolean)((Vector)binDataVector.elementAt(j)).elementAt(2)).booleanValue()){
						
								tempColor = (Color)((Vector)binDataVector.elementAt(j)).elementAt(4);
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
					g2.drawLine(28, 100+i, 63, 100+i);
					
				}
				
				for(int i=0; i<100; i++){
				
					double mag = ((((double)maxMagTotal-(double)minMagTotal)/100.0)*(double)i) + (double)minMagTotal;

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

					g2.drawLine(28, 100-i, 63, 100-i);
					
				}
				
				g2.setColor(Color.black);
	    		g2.drawRect(28, 0, 35, 200);
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(54, 100-lineIndex, 63, 100-lineIndex);

					g2.drawString("1E" + String.valueOf(mag), 30, 104-lineIndex);

				}
				
				int thresholdMag = ((Integer)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).intValue();
				
				g2.drawString("\u00b1" + "1E" + String.valueOf(thresholdMag), 30, 104);
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(56, 100+lineIndex, 63, 100+lineIndex);

					g2.drawString("-1E" + String.valueOf(mag), 30, 103+lineIndex);

				}
						
			}else if(type.equals("Reaction Flux")){
			
				Vector binDataVector = ds.getFluxBinData();
			
				g2.setColor(Color.white);
				g2.fillRect(0, 0, 170, (binDataVector.size()-1)*15+50);
			
				g2.setColor(Color.black);
				g2.drawRect(0, 0, 170, (binDataVector.size()-1)*15+50);
			
				g2.setColor(Color.black);
				g2.setFont(titleFont);
				g2.drawString("Normalized Flux Legend", 20, 20);
				
				int counter = 0;
				
				for(int i=0; i<binDataVector.size(); i++){
				
					boolean showBin = ((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue();
				
					if(showBin){
					
						double min = Math.pow(10, ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(0)).intValue());
						double max = Math.pow(10, ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue());
						Color binColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(3);
						int linestyle = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(4)).intValue();
						int linewidth = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(5)).intValue();
					
						float[] dash1 = {3.0f, 4.0f};
						float[] dash2 = {2.0f, 3.0f};
						float[] dash3 = {1.0f, 2.0f};
						
						BasicStroke stroke = new BasicStroke();
						
						switch(linestyle){
						
							case 0:
								stroke = new BasicStroke((float)(linewidth));
								break;
								
							case 1:
								stroke = new BasicStroke((float)(linewidth), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash1, 0.0f);
								break;
								
							case 2:
								stroke = new BasicStroke((float)(linewidth), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash2, 0.0f);
								break;
								
							case 3:
								stroke = new BasicStroke((float)(linewidth), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, dash3, 0.0f);
								break;
					
						}
						
						g2.setColor(binColor);
						g2.setStroke(stroke);
						g2.drawLine(10, counter*15+35, 70, counter*15+35);
						
						g2.setColor(Color.black);
						g2.setFont(textFont);
						g2.drawString(getFormattedValueLong4(min) + "-->" + getFormattedValueLong4(max), 80, counter*15+40);
			
						counter++;
			
					}
				
				}
				
			}
		
		}

		if(scheme.equals("Continuous")){

			if(type.equals("Abundance")){
			
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + getFormattedAbundance(Cina.elementVizFrame.elementVizAnimatorFrame.getAbundMax()), 71, 16);
				g2.drawString("Min: " + getFormattedAbundance(Cina.elementVizFrame.elementVizAnimatorFrame.getAbundMin()), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Abundance", -167, 18);
			
			}else if(type.equals("Derivative")){
			
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + getFormattedDer(Cina.elementVizFrame.elementVizAnimatorFrame.getDerAbundMax()), 71, 16);
				g2.drawString("Min: " + getFormattedDer(-Cina.elementVizFrame.elementVizAnimatorFrame.getDerAbundMax()), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Derivative", -157, 18);
			
			}else if(type.equals("Reaction Flux")){
			
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + getFormattedAbundance(Cina.elementVizFrame.elementVizAnimatorFrame.getFluxMax()), 71, 16);
				g2.drawString("Min: " + getFormattedAbundance(Cina.elementVizFrame.elementVizAnimatorFrame.getFluxMin()), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Reaction Flux", -178, 18);
			
			}
		
		}else if(scheme.equals("Binned")){
		
			if(type.equals("Abundance")){
			
				Vector binDataVector = ds.getAbundBinData();
			
				g2.setFont(bigFont);
					if(useWhiteText){
		        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(0))).elementAt(1)).intValue())), 71, 16);
				g2.drawString("Min: " + getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(binDataVector.size()-1))).elementAt(0)).intValue())), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Abundance", -167, 18);
			
			}else if(type.equals("Derivative")){
			
				Vector binDataVector = ds.getDerBinData();
			
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + getFormattedDer(Math.pow(10, ((Integer)((Vector)(binDataVector.elementAt(0))).elementAt(1)).intValue())), 71, 16);
				g2.drawString("Min: " + getFormattedDer(-1*Math.pow(10, ((Integer)((Vector)(binDataVector.elementAt(0))).elementAt(1)).intValue())), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Derivative", -157, 18);
			
			}
		
		}

    }
	
	/**
	 * Gets the legend trans.
	 *
	 * @param type the type
	 * @param scheme the scheme
	 * @return the legend trans
	 */
	public Point getLegendTrans(String type, String scheme){
		
		double legendXPos = 0.0;
		double legendYPos = 0.0;
		int legendXTrans = 0;
	    int legendYTrans = 0;
	        
		if(type.equals("Reaction Flux")
				&& scheme.equals("Binned")){
		
			legendXPos = width-180;
			legendYPos = height-200;
		
		}else{
		
			legendXPos = width - 158;
			legendYPos = height - 300;
		
		}
		
		if(legendXPos>0 && legendYPos>0){
		
			Point point = new Point(0, 0);
		
			for(int i=0; i<ZAMapArray.length; i++){
	        	
	        	int z = (int)ZAMapArray[i].getX();
	        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
	        	
	        	if(z<zmin || z>zmax || n<nmin || n>nmax){
	        		continue;
	        	}
	        	
	        	int isoXPos = xoffset+(n-nmin)*boxWidth+boxWidth;
	        	int isoYPos = yoffset+(zmax-z+1)*boxHeight+boxHeight;
	        
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
	        	
	        	if(z<zmin || z>zmax || n<nmin || n>nmax){
	        		continue;
	        	}
	        	
	        	int isoXPos = xoffset+(n-nmin)*boxWidth+boxWidth;
	        	int isoYPos = yoffset+(zmax-z+1)*boxHeight+boxHeight;
	        	
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
	 * @param type the type
	 * @param scheme the scheme
	 * @return the legend scale factor
	 */
	public double getLegendScaleFactor(String type, String scheme){
	
		double factor = 1.0;
		
		int legendWidth = waterMarkFontMetrics.stringWidth("nucastrodata.org");
		int legendHeight = 0;
		
		if(type.equals("Reaction Flux")
				&& scheme.equals("Binned")){
					
			legendHeight = 250;
		
		}else{
		
			legendHeight = 315;
		
		}
		
		double widthFactor = (getSize().getWidth()-legendTrans.getX())/legendWidth;
		double heightFactor = (getSize().getHeight()-legendTrans.getY())/legendHeight;
		
		factor = Math.min(widthFactor, heightFactor);
		
		int leftSpace = 0;
		
		if(type.equals("Reaction Flux")
				&& scheme.equals("Binned")){
		
			leftSpace = waterMarkFontMetrics.stringWidth("nu") + 20;		
				
		}else{
		
			leftSpace = waterMarkFontMetrics.stringWidth("nuca");	
		
		}
		
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
	 * Gets the slider bar scale factor.
	 *
	 * @return the slider bar scale factor
	 */
	public double getSliderBarScaleFactor(){

		double factor = 1.0;
		
		int step = Cina.elementVizFrame.elementVizAnimatorFrame.timeStepSlider.getValue();
		
		String widestString = "Temperature (T9) = " + getFormattedTemp(ds.getAnimatorNucSimDataStructure().getTempArray()[step]);
		int width = bigFontMetrics.stringWidth(widestString);
		int xLabel = 50;
		int fullWidth = xLabel + width;
		int hite = 0;
		int yLabel = 0;
		
		hite += reallyBigFontMetrics.getHeight() + 30 - bigFontMetrics.getHeight();
		yLabel = 50 - reallyBigFontMetrics.getHeight();
		hite += 30;
		hite += 30;
		hite += 30;
		hite += bigFontMetrics.getHeight();
		
		int fullHite = yLabel + hite;
        
        double isoDistance = 1E6;
        
        Point point = new Point(0, 0);
        
        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
        	
        	if(z<zmin || z>zmax || n<nmin || n>nmax){
        		continue;
        	}
        	
        	int isoXPos = xoffset+(n-nmin)*boxWidth;
        	int isoYPos = yoffset+(zmax-z+1)*boxHeight;
        	
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
	
	/**
	 * Gets the formatted time.
	 *
	 * @param number the number
	 * @return the formatted time
	 */
	public String getFormattedTime(double number){
	
		String string = "";
	
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
		FieldPosition fp = new FieldPosition(0);
	
		StringBuffer sb = new StringBuffer();
	
		sb = decimalFormat.format(number, sb, fp);
	
		string = sb.toString();

		return string;
		
	}

	/**
	 * Gets the formatted temp.
	 *
	 * @param number the number
	 * @return the formatted temp
	 */
	public String getFormattedTemp(double number){

		String string = "";
	
		DecimalFormat decimalFormat = new DecimalFormat("0.000E00");
	
		FieldPosition fp = new FieldPosition(0);
	
		StringBuffer sb = new StringBuffer();
	
		sb = decimalFormat.format(number, sb, fp);
	
		string = sb.toString();

		if(!string.substring(6,7).equals("-")){
		
			String[] tempArray = new String[2];
			
			tempArray = string.split("E");
			
			string = tempArray[0] + "E" + "+" + tempArray[1];
		
		}

		return string;
		
	}
    
    /**
     * Gets the formatted density.
     *
     * @param number the number
     * @return the formatted density
     */
    public String getFormattedDensity(double number){

		String string = "";
	
		DecimalFormat decimalFormat = new DecimalFormat("0.000E00");
	
		FieldPosition fp = new FieldPosition(0);
	
		StringBuffer sb = new StringBuffer();
	
		sb = decimalFormat.format(number, sb, fp);
	
		string = sb.toString();

		if(!string.substring(6,7).equals("-")){
		
			String[] tempArray = new String[2];
			
			tempArray = string.split("E");
			
			string = tempArray[0] + "E" + "+" + tempArray[1];
		
		}

		return string;
		
	}
	
	/**
	 * Gets the formatted abundance.
	 *
	 * @param number the number
	 * @return the formatted abundance
	 */
	public String getFormattedAbundance(double number){

		String string = "";
	
		DecimalFormat decimalFormat = new DecimalFormat("0.00E00");
	
		FieldPosition fp = new FieldPosition(0);
	
		StringBuffer sb = new StringBuffer();
	
		sb = decimalFormat.format(number, sb, fp);
	
		string = sb.toString();

		if(!string.substring(5,6).equals("-")){
		
			String[] tempArray = new String[2];
			
			tempArray = string.split("E");
			
			string = tempArray[0] + "E" + "+" + tempArray[1];
		
		}

		return string;
		
	}
	
	/**
	 * Gets the formatted interval.
	 *
	 * @param number the number
	 * @return the formatted interval
	 */
	public static String getFormattedInterval(double number){
		
		String string = "";
		
		DecimalFormat decimalFormat = new DecimalFormat("0.##########");
		
		FieldPosition fp = new FieldPosition(0);
		
		StringBuffer sb = new StringBuffer();
		
		sb = decimalFormat.format(number, sb, fp);
		
		string = sb.toString();
		
		return string;
		
	}
	
	/**
	 * Gets the formatted der.
	 *
	 * @param number the number
	 * @return the formatted der
	 */
	public static String getFormattedDer(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.00E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(5,6).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(6,7).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		/*if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}*/

		return string;
	}
	
	/**
	 * Gets the formatted value long4.
	 *
	 * @param number the number
	 * @return the formatted value long4
	 */
	public static String getFormattedValueLong4(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.#E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}

}

