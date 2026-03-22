package org.nucastrodata.data.dataviewer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataViewerDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;

import org.nucastrodata.Cina;


/**
 * The Class DataViewerIsotopePadPanel.
 */
public class DataViewerIsotopePadPanel extends JPanel implements MouseListener, MouseMotionListener, Scrollable{
	
    /** The xoffset. */
    int xoffset = 58;  
    
    /** The yoffset. */
    int yoffset = 35;
    
    /** The crosshairs on. */
    boolean crosshairsOn = false;
    
    /** The drag on. */
    boolean dragOn = false;
    
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
    
    /** The mouse x drag. */
    int mouseXDrag = 0;              
    
    /** The mouse y drag. */
    int mouseYDrag = 0;
    
    /** The crosshair size. */
    int crosshairSize = 30;
    
    /** The proton number. */
    static int protonNumber=0;   
    
    /** The neutron number. */
    static int neutronNumber=0; 
    
    /** The proton number drag. */
    static int protonNumberDrag=0;   
    
    /** The neutron number drag. */
    static int neutronNumberDrag=0; 
    
    /** The proton number drag vector. */
    static Vector protonNumberDragVector = new Vector();
    
    /** The neutron number drag vector. */
    static Vector neutronNumberDragVector = new Vector();
    
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
    
    /** The viktor. */
    static Vector viktor = new Vector();
    
    /** The viktor array. */
    Vector[] viktorArray;

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
    private DataViewerDataStructure ds;
    
    /**
     * Instantiates a new data viewer isotope pad panel.
     *
     * @param ds the ds
     */
    public DataViewerIsotopePadPanel(DataViewerDataStructure ds){
    	
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
	 * Good isotope list.
	 *
	 * @return true, if successful
	 */
	public boolean goodIsotopeList(){
    
    	boolean goodIsotopeList = false;
    
    	ds.setCurrentLibraryDataStructure(new LibraryDataStructure());
					
		ds.setLibraryName("ReaclibV2.2");
					
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm("GET RATE LIBRARY ISOTOPES", Cina.dataViewerFrame);
		
		if(!flagArray[0]){
		
			if(!flagArray[2]){
			
				if(!flagArray[1]){
					
					goodIsotopeList = true;
					
				}
			}
		}
		
		return goodIsotopeList;
		
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
	 * Initialize.
	 */
	public void initialize(){
	
		if(goodIsotopeList()){
        
	        ZList = getZList();
	        
	        isotopeList = getIsotopeList();
	        
	        minDripN = getMinDripN();
	        
	        maxDripN = getMaxDripN();
	        
	        minZDrip = new int[200];
	        
	        initPStable();
        
    	}
	
	}

	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){
	
		initPStable();
		
		viktor = ds.getViktorArray()[ds.getNucDataSetIndex()];
		
		repaint();
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
	
		viktorArray = ds.getViktorArray();
		
		viktorArray[ds.getNucDataSetIndex()] = viktor;
	
		ds.setViktorArray(viktorArray);
	
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
    	
		mouseXDrag = (int)me.getX();
        mouseYDrag = (int)me.getY();
        mouseX = (int)me.getX();
        mouseY = (int)me.getY();
        getNZ(mouseX,mouseY);
	
        if(!viktor.contains(new Point((int)protonNumber, (int)neutronNumber))
        	&& Cina.dataViewerFrame.containsIsotope((int)protonNumber, (int)neutronNumber)){
        	
            viktor.addElement(new Point((int)protonNumber, (int)neutronNumber));
            
        }else{
        	
            viktor.removeElement(new Point((int)protonNumber, (int)neutronNumber));
            
        }
        
        Color theColor;
      
        if(viktor.contains(new Point((int)protonNumber, (int)neutronNumber))) { 
        
            theColor = selectColor;

        }else if(!viktor.contains(new Point((int)protonNumber, (int)neutronNumber))
        			&& Cina.dataViewerFrame.containsIsotope((int)protonNumber, (int)neutronNumber)){      
                                            
            theColor = nonSelectColor; 
            
        }else{
        
        	theColor = Color.black;
        
        }
        
        getCurrentState();
        
        boolean empty = true;
        
		for(int i=0; i<ds.getViktorArray().length; i++){
		
			if(ds.getViktorArray()[i].size()!=0){
			
				empty = false;
			
			}

		}
		
		boolean checked = false;
		
		for(int i=0; i<8; i++){
		
			if(Cina.dataViewerFrame.checkBoxArray[i].isSelected()){
			
				checked = true;
			
			}
			
		}
		
		if(empty || !checked){
		
			Cina.dataViewerFrame.dataButton.setEnabled(false);
		
		}else{
		
			Cina.dataViewerFrame.dataButton.setEnabled(true);
		
		}
        
        dragOn = false;
        
        repaint(xoffset+neutronNumber*boxWidth,
                        yoffset+(zmax-protonNumber)*boxHeight,
                        boxWidth,boxHeight);
                            
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) {

    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	    	
    	Image image = toolkit.createImage("blankImage.image");
    
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
    	
    	crosshairsOn = true;
       	
       	dragOn = false;
    	
    	mouseX = (int)me.getX();
        mouseY = (int)me.getY();
        
        getNZ(mouseX,mouseY);
       	
       	Cina.dataViewerFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataViewerFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.dataViewerFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataViewerFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	repaint();
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {
    	
    	Cina.dataViewerFrame.elementField.setText("");
    	Cina.dataViewerFrame.zField.setText("");	
    	Cina.dataViewerFrame.aField.setText("");
    	
    	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	
    	crosshairsOn = false;
		dragOn = false;
		
		Cina.dataViewerFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataViewerFrame.NRuler.setPreferredHeight((int)getSize().getHeight());

		Cina.dataViewerFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataViewerFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint();
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent me) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me) {
		
		protonNumberDragVector.trimToSize();
		neutronNumberDragVector.trimToSize();
		
		for(int i=0; i<protonNumberDragVector.size(); i++){
		
			for(int j=0; j<neutronNumberDragVector.size(); j++){
		
				int z = ((Integer)(protonNumberDragVector.elementAt(i))).intValue();
				int n = ((Integer)(neutronNumberDragVector.elementAt(j))).intValue();
				
				if(!viktor.contains(new Point(z, n)) && Cina.dataViewerFrame.containsIsotope(z, n)){
				
					viktor.addElement(new Point(z, n));
				
				}
		
			}
		
		}

		protonNumberDragVector.clear();
		neutronNumberDragVector.clear();

		dragOn = false;
		
		repaint();
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
		
		mouseX = (int)me.getX();
        mouseY = (int)me.getY();
        getNZ(mouseX,mouseY);
        
        if((int)protonNumber>=0 && (int)neutronNumber>=1){
        
	        Cina.dataViewerFrame.zField.setText(String.valueOf((int)protonNumber));	
	        Cina.dataViewerFrame.elementField.setText(Cina.cinaMainDataStructure.getElementSymbol((int)protonNumber));
	    	Cina.dataViewerFrame.aField.setText(String.valueOf((int)neutronNumber + (int)protonNumber));
    	
    	}else{
    	
    		Cina.dataViewerFrame.elementField.setText("");
	    	Cina.dataViewerFrame.zField.setText("");	
	    	Cina.dataViewerFrame.aField.setText("");
    	
    	}
    	
    	Cina.dataViewerFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataViewerFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
    	
    	Cina.dataViewerFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataViewerFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	repaint();
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){
		
		neutronNumberDragVector.clear();
		protonNumberDragVector.clear();
		
		mouseX = (int)me.getX();
        mouseY = (int)me.getY();

        getNZ(mouseX,mouseY);
        
        if((int)protonNumber>-1 && (int)protonNumber<119 && (neutronNumber>=minDripN[(int)protonNumber]) && (neutronNumber<=maxDripN[(int)protonNumber])){
        
        	Cina.dataViewerFrame.aField.setText(String.valueOf((int)neutronNumber + (int)protonNumber));
        
        	Cina.dataViewerFrame.zField.setText(String.valueOf((int)protonNumber));

		    Cina.dataViewerFrame.elementField.setText(Cina.cinaMainDataStructure.getElementSymbol((int)protonNumber));

    	}else{
    	
    		Cina.dataViewerFrame.elementField.setText("");
	    	Cina.dataViewerFrame.zField.setText("");	
	    	Cina.dataViewerFrame.aField.setText("");
    	
    	}
    	
    	Cina.dataViewerFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataViewerFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.dataViewerFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataViewerFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	int W = 0;
    	int H = 0;
    	int X = 0;
    	int Y = 0;
    	
		W = Math.abs(mouseX-mouseXDrag);
	
		H = Math.abs(mouseY-mouseYDrag);
    	
    	if((mouseX-mouseXDrag)>0){
    	
    		X = mouseXDrag;
    		
    	}else{
    	
    		X = mouseXDrag - W;
    	
    	}
    	
    	if((mouseY-mouseYDrag)>0){
    	
    		Y = mouseYDrag;
    		
    	}else{
    	
    		Y = mouseYDrag - H;
    	
    	}
    	
    	Point minPoint = getNZPoint(X, Y);
    	Point maxPoint = getNZPoint(X+W, Y+H);
    	
    	int nminDrag = (int)minPoint.getY();
    	int nmaxDrag = (int)maxPoint.getY();
    	
    	int zminDrag = (int)minPoint.getX();
    	int zmaxDrag = (int)maxPoint.getX();
    	
    	if(nmaxDrag>nminDrag){
    	
	    	for(int i=nminDrag; i<=nmaxDrag; i++){
	    	
	    		neutronNumberDragVector.addElement(new Integer(i));
	
	    	}
    	
    	}else{
    	
    		for(int i=nmaxDrag; i<=nminDrag; i++){
	    	
	    		neutronNumberDragVector.addElement(new Integer(i));
	
	    	}
    	
    	
    	}
    	
    	if(zmaxDrag>zminDrag){
    	
	    	for(int i=zminDrag; i<=zmaxDrag; i++){
	    	
	    		protonNumberDragVector.addElement(new Integer(i));
	
	    	}
    	
    	}else{
    	
    		for(int i=zmaxDrag; i<=zminDrag; i++){
	    	
	    		protonNumberDragVector.addElement(new Integer(i));
	
	    	}
    	
    	
    	}
    	
    	dragOn = true;
    	
    	repaint();
    	
	
	}
	
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
            
            if((int)tprotonNumber!=-1){
            
	            if(tneutronNumber < minDripN[(int)tprotonNumber]
	              || tneutronNumber > (int)Math.min((long)maxDripN[(int)tprotonNumber],(long)nmax)){
	                protonNumber = 0;
	                
	                neutronNumber = 0;
	                
	            }else{
	            	
	                protonNumber = tprotonNumber;
	                neutronNumber = tneutronNumber;
	                
	            }
            
        	}
        }
    }

	/**
	 * Gets the nZ point.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the nZ point
	 */
	public Point getNZPoint(int x, int y) {

		Point point = new Point();

        // Return immediately if outside bounds of the chart
        if(x < xoffset || x > xmax
                       || y < yoffset
                       || y > ymax){
                        
             //DO NOTHING

        }else{
        	
            double fracY = (double)(y-yoffset)/(double)height;
            
            int tprotonNumber = (int)(zmax - ((int)(fracY * (zmax+1))));
            
            double fracX = (double)(x-xoffset)/(double)width;
            
            int tneutronNumber = (int)(fracX * (nmax+1));
            
            point.setLocation(tprotonNumber, tneutronNumber);
          
        }
        
        return point;
        
    }

	/**
	 * Gets the drag nz.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the drag nz
	 */
	public void getDragNZ(int x, int y) {

        // Return immediately if outside bounds of the chart
        if(x < xoffset || x > xmax
                       || y < yoffset
                       || y > ymax){
                        
                protonNumberDrag = 0;
                neutronNumberDrag = 0;
 
        // Otherwise determine the N and Z of the clicked square
        // if between the drip lines

        }else{
        	
            double fracY = (double)(y-yoffset)/(double)height;
            
            int tprotonNumber = (int)(zmax - ((int)(fracY * (zmax+1))));
            
            double fracX = (double)(x-xoffset)/(double)width;
            
            int tneutronNumber = (int)(fracX * (nmax+1));
            
            protonNumberDrag = tprotonNumber;
            neutronNumberDrag = tneutronNumber;

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
        
        for (int z=0; z<=zmax; z++){
        	
            for (int n=minDripN[z]; n<=(int)Math.min((long)maxDripN[z],(long)nmax); n++){
                
                if(!doIBelong(z, n)){
                	
                	continue;
                	
                }  
                
                boolean overIsotope = false;
                
                if(z==protonNumber && n==neutronNumber && crosshairsOn){
                
                	overIsotope = true;
                
                }
                
                if(z==protonNumberDrag && n==neutronNumberDrag && crosshairsOn && dragOn){
                
                	overIsotope = true;
                
                }
                
                if(protonNumberDragVector.contains(new Integer(z)) && neutronNumberDragVector.contains(new Integer(n)) && crosshairsOn && dragOn){
                
                	overIsotope = true;
                
                }
                
                if(Cina.dataViewerFrame.containsIsotope((int)z, (int)n)){
                
	                if(viktor.contains(new Point((int)z, (int)n))){
	                    
	                    g2.setColor(selectColor);
	                    
	                    /*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, false);*/
	                    
	                }else if(!viktor.contains(new Point((int)z, (int)n))
	                			&& !overIsotope){
	                    
	                    g2.setColor(nonSelectColor);
	                    
	                    /*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, true);*/
	                    
	                }else if(!viktor.contains(new Point((int)z, (int)n))
	                			&& overIsotope){
	                
	                	g2.setColor(mouseOverColor);
	                	
	                	/*g2.fill3DRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight, true);*/
	                
	                }
                
                	
                
            	}else{
            	
            		g2.setColor(Color.black);
            	
            		/*g2.setColor(frameColor);
            	
            		g2.drawRect(xoffset+n*boxWidth,
                           yoffset+(zmax-z)*boxHeight,
                           boxWidth,boxHeight);*/
            	
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
                	
               	}  
                
                boolean overIsotope = false;
                
                if(z==protonNumber && n==neutronNumber && crosshairsOn){
                
                	overIsotope = true;
                
                }
                
                if(z==protonNumberDrag && n==neutronNumberDrag && crosshairsOn && dragOn){
                
                	overIsotope = true;
                
                }
                
                if(protonNumberDragVector.contains(new Integer(z)) && neutronNumberDragVector.contains(new Integer(n)) && crosshairsOn && dragOn){
                
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
		        
		        if(!viktor.contains(new Point((int)z, (int)n)) && overIsotope){

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
		     	
		     	if(viktor.contains(new Point((int)z, (int)n))){
		     	
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
        
        if(dragOn){
        
        	g.setColor(Color.red);
        	
        	int W = 0;
        	int H = 0;
        	int X = 0;
        	int Y = 0;
        	
    		W = Math.abs(mouseX-mouseXDrag);
    	
    		H = Math.abs(mouseY-mouseYDrag);
        	
        	if((mouseX-mouseXDrag)>0){
        	
        		X = mouseXDrag;
        		
        	}else{
        	
        		X = mouseXDrag - W;
        	
        	}
        	
        	if((mouseY-mouseYDrag)>0){
        	
        		Y = mouseYDrag;
        		
        	}else{
        	
        		Y = mouseYDrag - H;
        	
        	}
        	
        	g2.setStroke(new BasicStroke(2));
        	g2.drawRect(X, Y, W, H);
        
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

