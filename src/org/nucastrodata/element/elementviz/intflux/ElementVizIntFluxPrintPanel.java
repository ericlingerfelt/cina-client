package org.nucastrodata.element.elementviz.intflux;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.util.*;
import java.awt.geom.*;

import org.nucastrodata.Cina;
import org.nucastrodata.NumberFormats;


/**
 * The Class ElementVizIntFluxPrintPanel.
 */
public class ElementVizIntFluxPrintPanel extends JPanel{
	
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
    
    /** The line vector. */
    Vector lineVector = new Vector();
    
    /** The scheme abund. */
    String schemeAbund;
    
    /** The scheme flux. */
    String schemeFlux;
    
    /** The stroke array. */
    BasicStroke[][] strokeArray;

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
     * Instantiates a new element viz int flux print panel.
     *
     * @param useWhiteText the use white text
     * @param ds the ds
     */
    public ElementVizIntFluxPrintPanel(boolean useWhiteText, ElementVizDataStructure ds){
        
        this.ds = ds;
        
        setStrokeArray();
        setCurrentState(ds.getFinalAbundScheme()
        					, ds.getIntFluxScheme()
        					, useWhiteText);
        setBackground(Color.black);
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
	 * @param schemeAbund the scheme abund
	 * @param schemeFlux the scheme flux
	 * @param useWhiteText the use white text
	 */
	public void setCurrentState(String schemeAbund, String schemeFlux, boolean useWhiteText){

		this.schemeAbund = schemeAbund;
		this.schemeFlux = schemeFlux;
		this.useWhiteText = useWhiteText;

		ZAMapArray = ds.getIntFluxNucSimDataStructure().getZAMapArray();

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
        
    	double scale = ((double)Cina.elementVizFrame.elementVizIntFluxFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
        
    	width = (int)(boxWidth*(nmax+1));
        height = (int)(boxHeight*(zmax+1));
        
        xmax = (int)(xoffset + width);
        ymax = (int)(yoffset + height);
    
    	setSize(xmax+xoffset, ymax+yoffset);		
		setPreferredSize(getSize());		
		
		titleScaleFactor = getTitleScaleFactor();
		legendTrans = getLegendTrans(schemeFlux, Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected());
		legendScaleFactor = getLegendScaleFactor(schemeFlux, Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected());
		
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

		double scale = ((double)Cina.elementVizFrame.elementVizIntFluxFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
		
		int smallFontSize = (int)(11.0*scale);
		int tinyFontSize = (int)(8.0*scale);
		
		Font smallFont = new Font("SanSerif", Font.PLAIN, smallFontSize);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	    Font tinyFont = new Font("SanSerif", Font.PLAIN, tinyFontSize);
	    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);

		Color minimumColorAbund = new Color(0, 0, 0);
		Color minimumColorFlux = new Color(0, 0, 0);
		
		if(schemeAbund.equals("Continuous")){
	
			minimumColorAbund = Cina.elementVizFrame.elementVizIntFluxFrame.getLogColor(0.0
																									, (double)Cina.elementVizFrame.elementVizIntFluxFrame.finalAbundMax
																									, (double)Cina.elementVizFrame.elementVizIntFluxFrame.finalAbundMin
																									, "Final Abundance");
		}	
		
		if(schemeFlux.equals("Binned")){
			
			minimumColorFlux = Cina.elementVizFrame.elementVizIntFluxFrame.getLogColor(0.0
																									, Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMax
																									, Cina.elementVizFrame.elementVizIntFluxFrame.intFluxMin
																									, "Integrated Flux");
				
		}
		
		Color color = Color.black;
		
		if(!useWhiteText){color = Color.white;}

		boolean showFinalAbund = Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected();

        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
        	
        	if(schemeAbund.equals("Continuous")){
        	
	        	if(showFinalAbund){
	        	
					colorFound:
					for(int j=0; j<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; j++){
		
						if(ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray()[j]==i){
						
							int red = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getRedArray()[j];
							int green = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getGreenArray()[j];
							int blue = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							g2.setColor(color);
							
							break colorFound;
						
						}else{
							
							g2.setColor(minimumColorAbund);
						
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
				
				}else{
	        	
	        		if(Cina.elementVizFrame.elementVizIntFluxFrame.showStableCheckBox.isSelected()){
	        		
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
			
			}else if(schemeAbund.equals("Binned")){
			
				if(showFinalAbund){
	        	
					colorFound:
					for(int j=0; j<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; j++){
		
						if((ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray()[j]==i)
								&& ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getRedArray()[j]!=-1){
						
							int red = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getRedArray()[j];
							int green = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getGreenArray()[j];
							int blue = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getBlueArray()[j];
							
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
				
				}else{
	        	
	        		if(Cina.elementVizFrame.elementVizIntFluxFrame.showStableCheckBox.isSelected()){
	        		
	        			Point isoPoint = new Point(z, z+n);
	        	
		        		if(Cina.cinaMainDataStructure.getStablePointVector().contains(isoPoint)){
		        		
		        			g2.setColor(new Color(70, 70, 70));
		        		
		        		}else{
		        		
		        			g2.setColor(Color.black);
		        		
		        		}
	        		
	        		}else{
	        		
	        			g2.setColor(Color.black);
	        			
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

        }
        
        if(Cina.elementVizFrame.elementVizIntFluxFrame.showStableCheckBox.isSelected()
        	&& showFinalAbund){
	        		
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
        
        if(Cina.elementVizFrame.elementVizIntFluxFrame.zoomSlider.getValue()>=75){
        	
	        if(Cina.elementVizFrame.elementVizIntFluxFrame.showLabelsCheckBox.isSelected()){
	        
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
	    	
		lineVector.clear();
	
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getReactionMapArray().length; i++){
		
			int fluxIndex = i;
			
			if(fluxIndex>=0){
			
    			int reactantIndex = 0;
    			int productIndex = 0;
    			
    			if(ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i]>=0){
    			
	    			reactantIndex = (int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[fluxIndex].getX());
	    			productIndex = (int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[fluxIndex].getY());

    			}else{
    			
    				reactantIndex = (int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[fluxIndex].getY());
	    			productIndex = (int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[fluxIndex].getX());

	    		}
	    			
    			int zIn = (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[reactantIndex].getX());
    			int nIn = (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[reactantIndex].getY()
    							- ds.getIntFluxNucSimDataStructure().getZAMapArray()[reactantIndex].getX());
    			
    			int zOut = (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[productIndex].getX());
    			int nOut = (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[productIndex].getY()
    							- ds.getIntFluxNucSimDataStructure().getZAMapArray()[productIndex].getX());
    			
    			if(ds.getIntFluxNucSimDataStructure().getIntFluxRedArray()[i]!=-1){
    			
    				int red = (int)ds.getIntFluxNucSimDataStructure().getIntFluxRedArray()[i];
					int green = (int)ds.getIntFluxNucSimDataStructure().getIntFluxGreenArray()[i];
					int blue = (int)ds.getIntFluxNucSimDataStructure().getIntFluxBlueArray()[i];
					
					color = new Color(red, green, blue);
					
					if(!color.equals(Color.black)){
					
						g2.setColor(color);
						
		    			g2.setStroke(strokeArray[(int)ds.getIntFluxNucSimDataStructure().getIntFluxLinewidthArray()[i]][(int)ds.getIntFluxNucSimDataStructure().getIntFluxLinestyleArray()[i]]);
		    			
		    			double arrowAngle = Math.PI/8;
  						int arrowLength = 10;
		    			
		    			double x1 = xoffset+(nIn+0.5)*boxWidth;
		    			double y1 = yoffset+(zmax-zIn+0.5)*boxHeight;
		    			double x2 = xoffset+(nOut+0.5)*boxWidth;
		    			double y2 = yoffset+(zmax-zOut+0.5)*boxHeight;
		    			
		    			Line2D.Double newLine = new Line2D.Double(x1, y1, x2, y2);
		    			
		    			g2.draw(newLine);
		    			
		    			lineVector.addElement(newLine);
		    			
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
		    			
		    			g2.setStroke(new BasicStroke((float)(ds.getIntFluxNucSimDataStructure().getIntFluxLinewidthArray()[i])));
		    				
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
	    
	    if(Cina.elementVizFrame.elementVizIntFluxFrame.zoomSlider.getValue()>=75){
	    	
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
       	
		g2.setFont(reallyBigFont);
		g2.drawString(ds.getIntFluxNucSimDataStructure().getNucSimName(), 0, 0);

		g2.translate(-50, -50);
    	
    	if(titleScaleFactor<1.0){
    		
    		g2.scale(1/titleScaleFactor, 1/titleScaleFactor);
    	
    	}
	
		g2.translate((int)legendTrans.getX(), (int)legendTrans.getY());
		g2.scale(legendScaleFactor, legendScaleFactor);
		
		if(Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected()){
		
			g2.setColor(Color.gray);
	    	g2.setFont(waterMarkFont);
	    	g2.drawString("nucastrodata.org", -70, 468);
	    	
	    	if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	    	g2.drawString("nucastrodata.org", -72, 470);
		
		}else if(schemeFlux.equals("Binned")){
		
			g2.setColor(Color.gray);
	    	g2.setFont(waterMarkFont);
	    	g2.drawString("nucastrodata.org", -48, 238);
	    	
	    	if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	    	g2.drawString("nucastrodata.org", -50, 240);
		
		}else if(schemeFlux.equals("Continuous")){
		
			g2.setColor(Color.gray);
	    	g2.setFont(waterMarkFont);
	    	g2.drawString("nucastrodata.org", -70, 248);
	    	
	    	if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	    	g2.drawString("nucastrodata.org", -72, 250);
		
		}
    	
    	if(Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected()){
    	
	    	if(ds.getIntFluxScheme().equals("Continuous")){

	    		for(int i=0; i<200; i++){
					
					g2.setColor(Cina.elementVizFrame.elementVizIntFluxFrame.getRGB(((double)i/200.0), "Integrated Flux"));
					g2.drawLine(28, 420-i, 63, 420-i);
				
				}
	    	
	    		g2.setColor(Color.black);
	    		g2.drawRect(28, 220, 35, 200);
	    	
	    		g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMax()), 71, 236);
				g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMin()), 71, 420);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Integrated Flux", -410, 18);
	    	
	    		g2.transform(AffineTransform.getRotateInstance(Math.PI/2));
	    	
	    	}else if(ds.getIntFluxScheme().equals("Binned")){
	    		
	    		g2.setFont(realSmallFont);
			
				Vector binDataVector = ds.getIntFluxBinData();

	    		g2.setColor(Color.white);
				g2.fillRect(-22, 230, 170, (binDataVector.size()-1)*15+50);
			
				g2.setColor(Color.black);
				g2.drawRect(-22, 230, 170, (binDataVector.size()-1)*15+50);
			
				g2.setColor(Color.black);
				g2.setFont(titleFont);
				g2.drawString("Integrated Flux Legend", -2, 250);
				
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
						g2.drawLine(-12, 230 + counter*15+35, 48, 230 + counter*15+35);
						
				        g2.setColor(Color.black);
						g2.setFont(textFont);
						g2.drawString(NumberFormats.getFormattedValueLong4(min) + "-->" + NumberFormats.getFormattedValueLong4(max), 58, 230 + counter*15+40);
			
						counter++;
			
					}
					
				}
	    	
	    	}
	    	
			if(ds.getFinalAbundScheme().equals("Continuous")){
			
				for(int i=0; i<200; i++){
				
					g2.setColor(Cina.elementVizFrame.elementVizIntFluxFrame.getRGB(((double)i/200.0), "Final Abundance"));
					g2.drawLine(28, 200-i, 63, 200-i);
				
				}
			
				g2.setColor(Color.black);
	    		g2.drawRect(28, 0, 35, 200);
			
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getFinalAbundMax()), 71, 16);
				g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getFinalAbundMin()), 71, 200);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Final Abundance", -195, 18);
			
				g2.transform(AffineTransform.getRotateInstance(Math.PI/2));
			
			}else if(ds.getFinalAbundScheme().equals("Binned")){
			
				g2.setFont(realSmallFont);
		
				Vector binDataVector = ds.getFinalAbundBinData();
				
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
				
				g2.setFont(bigFont);
				if(useWhiteText){
	        
		        	g2.setColor(Color.white);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}
				g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(0))).elementAt(1)).intValue())), 71, 14);
				g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Math.pow(10,((Integer)((Vector)(binDataVector.elementAt(binDataVector.size()-1))).elementAt(0)).intValue())), 71, 198);
			
				g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
			
				g2.setFont(reallyReallyBigFont);
				g2.drawString("Final Abundance", -195, 18);
				
				g2.transform(AffineTransform.getRotateInstance(Math.PI/2));		
	
	    	}
	    	
	    }else if(ds.getIntFluxScheme().equals("Continuous")){
	    
    		for(int i=0; i<200; i++){
				
				g2.setColor(Cina.elementVizFrame.elementVizIntFluxFrame.getRGB(((double)i/200.0), "Integrated Flux"));
				g2.drawLine(28, 200-i, 63, 200-i);
			
			}
    	
    		g2.setColor(Color.black);
	    	g2.drawRect(28, 0, 35, 200);
    	
    		g2.setFont(bigFont);
			if(useWhiteText){
	        
	        	g2.setColor(Color.white);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
			g2.drawString("Max: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMax()), 71, 16);
			g2.drawString("Min: " + NumberFormats.getFormattedAbundance(Cina.elementVizFrame.elementVizIntFluxFrame.getIntFluxMin()), 71, 200);
		
			g2.transform(AffineTransform.getRotateInstance(-Math.PI/2));
		
			g2.setFont(reallyReallyBigFont);
			g2.drawString("Integrated Flux", -190, 18);
	    
	    }else if(ds.getIntFluxScheme().equals("Binned")){
	    
	    	g2.setFont(realSmallFont);
			
			Vector binDataVector = ds.getIntFluxBinData();
		
	        g2.setColor(Color.white);
			g2.fillRect(0, 0, 170, (binDataVector.size()-1)*15+50);

			g2.setColor(Color.black);
			g2.drawRect(0, 0, 170, (binDataVector.size()-1)*15+50);

	        g2.setColor(Color.black);
			g2.setFont(titleFont);
			g2.drawString("Integrated Flux Legend", 20, 20);
			
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
					g2.drawString(NumberFormats.getFormattedValueLong4(min) + "-->" + NumberFormats.getFormattedValueLong4(max), 80, counter*15+40);
		
					counter++;
		
				}
				
			}
			
	    }

    }
    
    /**
     * Gets the title scale factor.
     *
     * @return the title scale factor
     */
    public double getTitleScaleFactor(){
   
		double factor = 1.0;
		
		int width = reallyBigFontMetrics.stringWidth(ds.getIntFluxNucSimDataStructure().getNucSimName());
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
    
    /**
     * Gets the legend trans.
     *
     * @param scheme the scheme
     * @param includeAbund the include abund
     * @return the legend trans
     */
    public Point getLegendTrans(String scheme, boolean includeAbund){
		
		double legendXPos = 0.0;
		double legendYPos = 0.0;
		int legendXTrans = 0;
	    int legendYTrans = 0;
	        
		if(includeAbund){
		
			legendXPos = width - 158;
			legendYPos = height - 440;
		
		}else if(scheme.equals("Binned")){
		
			legendXPos = width - 180;
			legendYPos = height - 210;
		
		}else if(scheme.equals("Continuous")){
		
			legendXPos = width - 158;
			legendYPos = height - 220;
		
		}
		
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
	        
	        legendXTrans = (int)point.getX() - 10;
			legendYTrans = (int)point.getY() - 10;
			
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
	 * @param scheme the scheme
	 * @param includeAbund the include abund
	 * @return the legend scale factor
	 */
	public double getLegendScaleFactor(String scheme, boolean includeAbund){
	
		double factor = 1.0;
		
		int legendWidth = waterMarkFontMetrics.stringWidth("nucastrodata.org");
		int legendHeight = 0;
		
		if(includeAbund){
		
			legendHeight = 460;
		
		}else if(scheme.equals("Binned")){
		
			legendHeight = 250;
				
		}else if(scheme.equals("Continuous")){
		
			legendHeight = 250;
		
		}
		
		double widthFactor = (getSize().getWidth()-legendTrans.getX())/legendWidth;
		double heightFactor = (getSize().getHeight()-legendTrans.getY())/legendHeight;
		
		factor = Math.min(widthFactor, heightFactor);
		
		int leftSpace = 0;
		
		if(includeAbund){
		
			leftSpace = waterMarkFontMetrics.stringWidth("nuca");
		
		}else if(scheme.equals("Binned")){
		
			leftSpace = waterMarkFontMetrics.stringWidth("nu");	
				
		}else if(scheme.equals("Continuous")){
	
			leftSpace = waterMarkFontMetrics.stringWidth("nuca");
			
		}
		
		
		int widthSpace = (int)(getSize().getWidth()-legendTrans.getX() + leftSpace*factor - factor*legendWidth);
		
		if(widthSpace>=25){
		
			legendTrans.setLocation(legendTrans.getX()+widthSpace-25, legendTrans.getY());
		
		}
		
		int heightSpace = (int)(((double)getSize().getHeight()-legendTrans.getY()) - (factor*(double)legendHeight));
		
		legendTrans.setLocation(legendTrans.getX(), legendTrans.getY()+heightSpace-(factor*25));

		return factor;
	
	}

}