package org.nucastrodata.rate.rateparam;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import org.nucastrodata.Cina;


/**
 * The Class RateParamIsotopePadPanel.
 */
public class RateParamIsotopePadPanel extends JPanel implements MouseListener, MouseMotionListener, Scrollable{
	
    /** The xoffset. */
    int xoffset = 58;  
    
    /** The yoffset. */
    int yoffset = 35;
    
    /** The crosshairs on. */
    boolean crosshairsOn = false;
    
    /** The box width. */
    int boxWidth = 29;
    
    /** The box height. */
    int boxHeight = 29;
    
    /** The zmax. */
    static int zmax = 20;       
    
    /** The nmax. */
    static int nmax = 20; 
    
    /** The mouse x. */
    int mouseX = 0;              
    
    /** The mouse y. */
    int mouseY = 0;   
    
    /** The crosshair size. */
    int crosshairSize = 30;
    
    /** The proton number. */
    static int protonNumber=0;   
    
    /** The neutron number. */
    static int neutronNumber=0; 
    
    /** The width. */
    static int width;           
    
    /** The height. */
    static int height;   
            
    /** The xmax. */
    int xmax;                    
    
    /** The ymax. */
    int ymax;   
                    
    /** The max plot n. */
    int maxPlotN = 0;          
    
    /** The max plot z. */
    int maxPlotZ = 0;
               
    /** The biggest n. */
    int biggestN;
    
    /** The isotope viktor. */
    static Vector isotopeViktor = new Vector();

   // Neutron numbers for min particle-stable mass for given Z
    /** The min drip n. */
   static int[] minDripN;

    // Neutron numbers for max particle-stable mass for given Z
    /** The max drip n. */
    static int[] maxDripN;

    // Boolean array indicating whether isotope is particle stable
    // (that is, this array defines the drip lines)

    /** The min z drip. */
    int[] minZDrip;

	/** The Z list. */
	int[] ZList;
	
	/** The isotope list. */
	int[][] isotopeList;

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
    
    /** The ds. */
    private RateParamDataStructure ds;
    
    /**
     * Instantiates a new rate param isotope pad panel.
     *
     * @param ds the ds
     */
    public RateParamIsotopePadPanel(RateParamDataStructure ds){
    	
    	this.ds = ds;
    	
    	setAutoscrolls(true);
    	
        addMouseListener(this);
        addMouseMotionListener(this);
        
        width = (int)(boxWidth*(nmax+1));
        height = (int)(boxHeight*(zmax+1));
        xmax = (int)(xoffset + width);
        ymax = (int)(yoffset + height);
        
        setSize(xmax+xoffset,ymax+yoffset);

        setBackground(Color.black);
    }

	/**
	 * Initialize.
	 */
	public void initialize(){
	
		isotopeViktor = ds.getIsotopeViktor();
		
		ds.setCurrentLibraryDataStructure(new LibraryDataStructure());	
		ds.setLibrary("ReaclibV2.2");
	
		if(Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", Cina.rateParamFrame.rateParamChartFrame)){
        
	        ZList = getZList();
	        
	        isotopeList = getIsotopeList();
	        
	        minDripN = getMinDripN();
	        
	        maxDripN = getMaxDripN();
	        
	        minZDrip = new int[200];
	        
	        initPStable();
        
    	}
	
	}

	/**
	 * Gets the z list.
	 *
	 * @return the z list
	 */
	public int[] getZList(){

		ZList = ds.getCurrentLibraryDataStructure().getZList();

		return ZList;

	}

	/**
	 * Gets the isotope list.
	 *
	 * @return the isotope list
	 */
	public int[][] getIsotopeList(){

		isotopeList = ds.getCurrentLibraryDataStructure().getIsotopeList();

		return isotopeList;

	}

	/**
	 * Gets the min drip n.
	 *
	 * @return the min drip n
	 */
	public int[] getMinDripN(){
	
		int[] tempArray = new int[ZList.length];
	
		for(int i=0; i<ZList.length; i++){
		
			tempArray[i] = isotopeList[i][0] - ZList[i];
		
		}
	
		return tempArray;
	
	}

	/**
	 * Gets the max drip n.
	 *
	 * @return the max drip n
	 */
	public int[] getMaxDripN(){
	
		int[] tempArray = new int[ZList.length];
	
		for(int i=0; i<ZList.length; i++){
		
			int lastIndex = 0;
		
			for(int j=0; j<isotopeList[i].length; j++){
		
				if(isotopeList[i][j]!=-1){
				
					lastIndex = j;
				
				}
		
			}		
		
			tempArray[i] = isotopeList[i][lastIndex] - ZList[i];
			
		}
	
		return tempArray;
	
	}

	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		isotopeViktor = ds.getIsotopeViktor();
	
		initPStable();
		
		repaint();
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){

		ds.setIsotopeViktor(isotopeViktor);
	
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
        
        if(P>=0 && P<=zmax){
        	
            if(N>=minDripN[P] && N<=maxDripN[P]){
            	
                include = true;
                
            }
        }
        
        return include;
        
    }

    /**
     * Inits the p stable.
     */
    void initPStable() {

        // Generic fill with "true" if between min and max mass
        // particle stable isotopes
        int z = 0;
        int n = 0;
        biggestN = 0;

        for (z=0; z<=zmax; z++) {
            int highN = (int)Math.min((long)nmax, (long)maxDripN[z]);
            for (n=minDripN[z]; n<=highN; n++) { 
                if(highN > biggestN){biggestN = highN;}
            }
        }
        
        // For later plotting convenience, determine array of
        // min particle-stable Z for given N
        for(n=0; n<=biggestN; n++){
            for (z=0; z<=zmax; z++){
                if(doIBelong(z, n)){
                    minZDrip[n] = z;
                    break;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent me) {

        mouseX = (int)me.getX();
        mouseY = (int)me.getY();
        getNZ(mouseX,mouseY);
	
		if((int)protonNumber>-1 && (neutronNumber>=minDripN[(int)protonNumber]) && (neutronNumber<=maxDripN[(int)protonNumber])){
	
			isotopeViktor.clear();
		
	        if(!isotopeViktor.contains(new Point((int)protonNumber, (int)neutronNumber))){
	        	
	            isotopeViktor.addElement(new Point((int)protonNumber, (int)neutronNumber));
	            
	        }else{
	        	
	            isotopeViktor.removeElement(new Point((int)protonNumber, (int)neutronNumber));
	            
	        }
	        
	        Color theColor;
	      
	        if(isotopeViktor.contains(new Point((int)protonNumber, (int)neutronNumber))) { 
	        
	            theColor = selectColor;
	
	        }else if(!isotopeViktor.contains(new Point((int)protonNumber, (int)neutronNumber))){      
	                                            
	            theColor = nonSelectColor; 
	            
	        }
	        
	        getCurrentState();
	        
	        boolean empty = true;
	
			if(ds.getIsotopeViktor().size()!=0){
			
				empty = false;
			
			}
			
			boolean rateSelected = false;
	
			if(Cina.rateParamFrame.rateParamChartFrame.rateListModel.size()!=0){
			
				rateSelected = true;
			
			}
	        
	        ds.setLibrary("ReaclibV2.2");
			ds.setIsotope(Cina.rateParamFrame.rateParamChartFrame.getIsotopes());
			ds.setTypeDatabase("");
	        
	        if(Cina.cinaCGIComm.doCGICall("GET RATE LIST", Cina.rateParamFrame.rateParamChartFrame)){
	        
	        	Cina.rateParamFrame.rateParamChartFrame.setRateList();
	        
	        }
         
	        repaint();
	        
    	}
                            
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) {

    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	    	
    	Image image = toolkit.createImage("blankImage.image");
    
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
    	
    	crosshairsOn = true;
    	
    	Cina.rateParamFrame.rateParamChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.rateParamFrame.rateParamChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.rateParamFrame.rateParamChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.rateParamFrame.rateParamChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	repaint();
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {
    	
    	Cina.rateParamFrame.rateParamChartFrame.elementField.setText("");
    	Cina.rateParamFrame.rateParamChartFrame.zField.setText("");	
    	Cina.rateParamFrame.rateParamChartFrame.aField.setText("");
    	
    	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	
    	crosshairsOn = false;

		Cina.rateParamFrame.rateParamChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.rateParamFrame.rateParamChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.rateParamFrame.rateParamChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.rateParamFrame.rateParamChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint();
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent me) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
		
		mouseX = (int)me.getX();
        mouseY = (int)me.getY();
        getNZ(mouseX,mouseY);
        
        if((int)protonNumber>-1 && (neutronNumber>=minDripN[(int)protonNumber]) && (neutronNumber<=maxDripN[(int)protonNumber])){
        
        	Cina.rateParamFrame.rateParamChartFrame.aField.setText(String.valueOf((int)neutronNumber + (int)protonNumber));
        
        	Cina.rateParamFrame.rateParamChartFrame.zField.setText(String.valueOf((int)protonNumber));
        
        	if((int)protonNumber>=2){
        	
		        Cina.rateParamFrame.rateParamChartFrame.elementField.setText(Cina.cinaMainDataStructure.getElementSymbol((int)protonNumber));
		    	
	    	}else if((int)protonNumber==0){
	    	
	    		Cina.rateParamFrame.rateParamChartFrame.elementField.setText("n");
	    	
	    	}else if((int)protonNumber==1 && (int)neutronNumber==0){
	    	
	    		Cina.rateParamFrame.rateParamChartFrame.elementField.setText("p");
	    	
	    	}else if((int)protonNumber==1 && (int)neutronNumber==1){
	    	
	    		Cina.rateParamFrame.rateParamChartFrame.elementField.setText("d");
	    	
	    	}else if((int)protonNumber==1 && (int)neutronNumber==2){
	    	
	    		Cina.rateParamFrame.rateParamChartFrame.elementField.setText("t");
	    	
	    	}
    	
    	}else{
    	
    		Cina.rateParamFrame.rateParamChartFrame.elementField.setText("");
	    	Cina.rateParamFrame.rateParamChartFrame.zField.setText("");	
	    	Cina.rateParamFrame.rateParamChartFrame.aField.setText("");
    	
    	}
    	
    	Cina.rateParamFrame.rateParamChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.rateParamFrame.rateParamChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.rateParamFrame.rateParamChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.rateParamFrame.rateParamChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	repaint();
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){}
	
    /**
     * Gets the nZ.
     *
     * @param x the x
     * @param y the y
     * @return the nZ
     */
    public void getNZ(int x, int y) {

        // Return immediately if outside bounds of the chart
        if(x < xoffset || x > xmax
                       || y < yoffset
                       || y > ymax){
                        
                protonNumber = 0;
                neutronNumber = 0;
 
        // Otherwise determine the N and Z of the clicked square
        // if between the drip lines

        }else{
        	
            double fracY = (double)(y-yoffset)/(double)height;
            
            int tprotonNumber = (int)(zmax - ((int)(fracY * (zmax+1))));
            
            double fracX = (double)(x-xoffset)/(double)width;
            
            int tneutronNumber = (int)(fracX * (nmax+1));
            
            protonNumber = tprotonNumber;
            neutronNumber = tneutronNumber;

        }
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;

        super.paintComponent(g2); 
		
		RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(hintsText);
		
        maxPlotN = maxPlotZ = 0;
        
        g2.setColor(frameColor);
        
        for(int z=0; z<=zmax; z++){
        	
            for(int n=minDripN[z]; n<=(int)Math.min((long)maxDripN[z],(long)nmax); n++){
                
                if(!doIBelong(z, n)){
                	
                	continue;
                	
                }  // omit dripped
                
		        getNZ(mouseX,mouseY);
                
                boolean overIsotope = false;
                
                if(z==protonNumber && n==neutronNumber && crosshairsOn){
                
                	overIsotope = true;
                
                }
                
                if(isotopeViktor.contains(new Point((int)z, (int)n))){
                    
                    g2.setColor(selectColor);
                    
                    /*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, false);*/
                    
                }else if(!isotopeViktor.contains(new Point((int)z, (int)n))
                			&& !overIsotope){
                    
                    g2.setColor(nonSelectColor);
                    
                    /*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, true);*/
                    
                }else if(!isotopeViktor.contains(new Point((int)z, (int)n))
                			&& overIsotope){
                
                	g2.setColor(mouseOverColor);
                	
                	/*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, true);*/
                
                }
            
            	g2.fillRect(xoffset+n*boxWidth,
                       yoffset+(zmax-z)*boxHeight,
                       boxWidth,boxHeight);
            
                g2.setColor(frameColor);
                
                g2.drawRect(xoffset+n*boxWidth,
                       yoffset+(zmax-z)*boxHeight,
                       boxWidth,boxHeight);
                      
                if(n>maxPlotN){maxPlotN = n;}
                if(z>maxPlotZ){maxPlotZ = z;}
    
            }
        }
        	
        g2.setColor(isoLabelColor);
        
        for (int z=0; z<=zmax; z++){
        	
            for(int n=minDripN[z]; n<=(int)Math.min((long)maxDripN[z],(long)nmax); n++){
                
                if(!doIBelong(z, n)){
                	
                	continue;
                	
               	}  // Omit dripped
               	
                //if(protonNumber == 0){return;}
                
                getNZ(mouseX,mouseY);
                
                boolean overIsotope = false;
                
                if(z==protonNumber && n==neutronNumber && crosshairsOn){
                
                	overIsotope = true;
                
                }
                
		        String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
		        String tempS2 = String.valueOf(z+n);
		        
		        int wid = (int)(realSmallFontMetrics.stringWidth(tempS)
		                  + tinyFontMetrics.stringWidth(tempS2));
		                  
		        int xzero = (int)(xoffset+n*boxWidth+boxWidth/2-wid/2);
		        
		        int yzero = (int)(yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
		        
		        g2.setFont(tinyFont);
		        g2.setColor(isoLabelColor);
		        
		        if(!isotopeViktor.contains(new Point((int)z, (int)n)) && overIsotope){

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
		     	
		     	if(isotopeViktor.contains(new Point((int)z, (int)n))){
		     	
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
        
        // Labels for vertical axis

        g2.setFont(smallFont);
        g2.setColor(Color.white);
        
        for(int z=0; z<=zmax-1; z++){
        	
           if(minDripN[zmax-z] > nmax){
           		continue;
           }
           
           String tempS = Cina.cinaMainDataStructure.getElementSymbol(zmax-z);
           
           if(tempS.equals("H")){
            
            	int ds = (int)(minDripN[zmax-z]*boxWidth);   // Inset to drip line
                g2.drawString(tempS,
                                xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
                                yoffset + z*boxHeight + boxHeight/2
                                + smallFontMetrics.getHeight()/2 -3);	
            
           }else if(tempS.equals("He")){
                int ds = (int)(minDripN[zmax-z]*boxWidth);   // Inset to drip line
                g2.drawString(tempS,
                                xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
                                yoffset + z*boxHeight + boxHeight/2
                                + smallFontMetrics.getHeight()/2 -3);
           }else{
                int ds = (int)(minDripN[zmax-z]*boxWidth);   // Inset to drip line
                g2.drawString(tempS,
                                xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
                                yoffset + z*boxHeight + boxHeight/2
                                + smallFontMetrics.getHeight()/2 -3);
           }
        }
        
        // Labels for horizontal axis

        g2.setFont(smallFont);
        g2.setColor(Color.white);
        
        for(int n=0; n<=maxPlotN; n++){
        	
           String tempS = String.valueOf(n);
           
           g2.drawString(tempS,
                        xoffset+n*boxWidth+boxWidth/2
                        -smallFontMetrics.stringWidth(tempS)/2 + 1,
                        yoffset+height + 17 - minZDrip[n]*boxHeight);
                        
        }
        
        if(crosshairsOn){
        	g2.setStroke(new BasicStroke(2));
        	g2.setColor(Color.red);

        	g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
        	
        	g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
        
        }

    }

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize(){return super.getPreferredSize();}
	
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize(){return getPreferredSize();}

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation,
                                          int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition -
                             (currentPosition / 29)
                              * 29;
            return (newPosition == 0) ? 29: newPosition;
        } else {
            return ((currentPosition / 29) + 1)
                   * 29
                   - currentPosition;
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - 29;
        } else {
            return visibleRect.height - 29;
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth(){return false;}
    
    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight(){return false;}

}  

