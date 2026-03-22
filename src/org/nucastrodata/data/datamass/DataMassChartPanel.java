package org.nucastrodata.data.datamass;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.awt.event.*;
import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.NumberFormats;


/**
 * The Class DataMassChartPanel.
 */
public class DataMassChartPanel extends JPanel implements MouseListener, MouseMotionListener, Scrollable{
	
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
    int zmax = 0;       
    
    /** The nmax. */
    int nmax = 0; 
    
    /** The zmin. */
    int zmin = 0;
    
    /** The nmin. */
    int nmin = 0;
    
    /** The mouse x. */
    int mouseX = 0;              
    
    /** The mouse y. */
    int mouseY = 0;   
    
    /** The crosshair size. */
    int crosshairSize = 30;
    
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

	/** The ZN map array. */
	Point[] ZNMapArray;

	/** The max drip n. */
	int[] maxDripN;
	
	/** The min drip n. */
	int[] minDripN;
	
	/** The min z drip. */
	int[] minZDrip;

	/** The n magic array. */
	int[] nMagicArray = {20, 28, 50, 82, 126, 184, 228};
	
	/** The z magic array. */
	int[] zMagicArray = {20, 28, 50, 82, 114, 126};

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

    /** The real small font. */
    Font realSmallFont = new Font("SanSerif", Font.PLAIN, 10);
    
    /** The real small font metrics. */
    FontMetrics realSmallFontMetrics = getFontMetrics(realSmallFont);
    
    /** The big font. */
    Font bigFont = new Font("SanSerif", Font.PLAIN, 16);
    
    /** The big font metrics. */
    FontMetrics bigFontMetrics = getFontMetrics(bigFont);
    
    /** The viktor. */
    Vector viktor;
    
    /** The step. */
    int step = 0;
    
    /** The type. */
    int type;
    
    /** The quantity. */
    int quantity;
    
    /** The init flag. */
    boolean initFlag = true;
    
    /** The color array. */
    Color[] colorArray;
    
    /** The ds. */
    private DataMassDataStructure ds;
    
    /**
     * Instantiates a new data mass chart panel.
     *
     * @param ds the ds
     */
    public DataMassChartPanel(DataMassDataStructure ds){
    	
    	this.ds = ds;
    	
    	setAutoscrolls(true);
    	
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.black);
    }

	/**
	 * Sets the current state.
	 *
	 * @param type the type
	 * @param quantity the quantity
	 */
	public void setCurrentState(int type, int quantity){

		this.type = type;
		this.quantity = quantity;

		if(type==2 || type==3){

			ZNMapArray = new Point[ds.getPointVector().size()];
	
			for(int i=0; i<ZNMapArray.length; i++){
			
				ZNMapArray[i] = (Point)(ds.getPointVector().elementAt(i));
			
			}
			
			viktor = ds.getPointVector();
		
		}else if(type==0){
		
			if(ds.getSelectMassesFlag()!=1){
		
				Point[] ZNMapArrayTotal = ds.getTheoryModelDataStructure().getZNArray();
				
				Vector tempVector = new Vector();
				
				for(int i=0; i<ZNMapArrayTotal.length; i++){
				
					if(ds.getIsotopeViktor().contains(ZNMapArrayTotal[i])){
					
						tempVector.addElement(ZNMapArrayTotal[i]);
					
					}
				
				}
				
				tempVector.trimToSize();
				
				ZNMapArray = new Point[tempVector.size()];
				
				for(int i=0; i<tempVector.size(); i++){
				
					ZNMapArray[i] = (Point)tempVector.elementAt(i);
				
				}
			
			}else{
			
				ZNMapArray = ds.getTheoryModelDataStructure().getZNArray();
			
				viktor = new Vector();
				
				for(int i=0; i<ZNMapArray.length; i++){
				
					viktor.addElement(ZNMapArray[i]);
				
				}
			
			}
			
			viktor = new Vector();
			
			for(int i=0; i<ZNMapArray.length; i++){
			
				viktor.addElement(ZNMapArray[i]);
			
			}
		
		}else if(type==1){
		
			if(ds.getSelectMassesFlag()!=1){
		
				Point[] ZNMapArrayTotal = ds.getExpModelDataStructure().getZNArray();
				
				Vector tempVector = new Vector();
				
				for(int i=0; i<ZNMapArrayTotal.length; i++){
				
					if(ds.getIsotopeViktor().contains(ZNMapArrayTotal[i])){
					
						tempVector.addElement(ZNMapArrayTotal[i]);
					
					}
				
				}
				
				tempVector.trimToSize();
				
				ZNMapArray = new Point[tempVector.size()];
				
				for(int i=0; i<tempVector.size(); i++){
				
					ZNMapArray[i] = (Point)tempVector.elementAt(i);
				
				}
			
			}else{
			
				ZNMapArray = ds.getExpModelDataStructure().getZNArray();
			
				viktor = new Vector();
				
				for(int i=0; i<ZNMapArray.length; i++){
				
					viktor.addElement(ZNMapArray[i]);
				
				}
			
			}
			
			viktor = new Vector();
			
			for(int i=0; i<ZNMapArray.length; i++){
			
				viktor.addElement(ZNMapArray[i]);
			
			}
		
		}

		nmax = getNmax();
		nmin = getNmin();
		zmax = getZmax();
		zmin = getZmin();

		minDripN = getMinDripN(zmax, zmin);
		maxDripN = getMaxDripN(zmax, zmin);
		minZDrip = getMinZDrip(nmax, nmin);
        
        if(!initFlag){
        
        	double scale = ((double)Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue())/100.0;
			boxHeight = (int)(29.0*scale);
			boxWidth = (int)(29.0*scale);
	        
        	width = (int)(boxWidth*(nmax+1));
	        height = (int)(boxHeight*(zmax+1));
	        
	        xmax = (int)(xoffset + width);
	        ymax = (int)(yoffset + height);
        
        	setSize(xmax+xoffset, ymax+yoffset);
        			
			setPreferredSize(getSize());
		
		}else{
		
			width = (int)(boxWidth*(nmax+1));
	        height = (int)(boxHeight*(zmax+1));
	        
	        xmax = (int)(xoffset + width);
	        ymax = (int)(yoffset + height);
		
			setSize(xmax+xoffset, ymax+yoffset);
        			
			setPreferredSize(getSize());
			
			initFlag = false;
		
		}
		
		repaint();
		
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){
	
		int max = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			max = (int)Math.max(max, ZNMapArray[i].getY());
		
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
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			min = (int)Math.min(min, ZNMapArray[i].getY());
		
		}

		return min;
	
	}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){
	
		int max = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			max = (int)Math.max(max, ZNMapArray[i].getX());
		
		}

		return max;
	
	}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	public int getZmin(){
	
		int min = 1000;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			min = (int)Math.min(min, ZNMapArray[i].getX());
		
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
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			currentZ = (int)ZNMapArray[i].getX();
			
			currentN = (int)ZNMapArray[i].getY();
			
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
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			currentZ = (int)ZNMapArray[i].getX();
			
			currentN = (int)ZNMapArray[i].getY();
			
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
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent me) {}

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) {
		
		if(Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue()==100
			&& !Cina.dataMassFrame.dataMassChartFrame.showLabelsCheckBox.isSelected()){
			
	    	Toolkit toolkit = Toolkit.getDefaultToolkit();
	    	    	
	    	Image image = toolkit.createImage("blankImage.image");
	    
	    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
    	
    	}
    	
    	crosshairsOn = true;
    	
    	Cina.dataMassFrame.dataMassChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataMassFrame.dataMassChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.dataMassFrame.dataMassChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataMassFrame.dataMassChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint(Cina.dataMassFrame.dataMassChartFrame.sp.getViewport().getViewRect());

    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {

    	Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.setValues("", "");
    	Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.repaint();
    	Cina.dataMassFrame.dataMassChartFrame.valueField.setText("");
    	
    	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	
    	crosshairsOn = false;
    	
		Cina.dataMassFrame.dataMassChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataMassFrame.dataMassChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.dataMassFrame.dataMassChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataMassFrame.dataMassChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint(Cina.dataMassFrame.dataMassChartFrame.sp.getViewport().getViewRect());
    	
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
        
        if((int)protonNumber>-1 && viktor.contains(new Point((int)protonNumber, (int)neutronNumber))){

    		Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.setValues(Cina.cinaMainDataStructure.getElementSymbol((int)protonNumber)
    																							, String.valueOf((int)neutronNumber + (int)protonNumber));
    		
    		Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.repaint();
    	
    		double value = Cina.dataMassFrame.dataMassChartFrame.valueArray[(viktor.indexOf(new Point((int)protonNumber, (int)neutronNumber)))];
    	
    		if(value!=1E100){
    	
    			Cina.dataMassFrame.dataMassChartFrame.valueField.setText(NumberFormats.getFormattedDer(value));

			}else{
				
				Cina.dataMassFrame.dataMassChartFrame.valueField.setText("Does Not Exist");
			
			}

    	}else{
    	
    		Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.setValues("", "");
    		Cina.dataMassFrame.dataMassChartFrame.dataMassIsotopePanel.repaint();
	    	Cina.dataMassFrame.dataMassChartFrame.valueField.setText("");
    	
    	}
    	
    	Cina.dataMassFrame.dataMassChartFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.dataMassFrame.dataMassChartFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.dataMassFrame.dataMassChartFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.dataMassFrame.dataMassChartFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint(Cina.dataMassFrame.dataMassChartFrame.sp.getViewport().getViewRect());
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){}
	
	/**
	 * Gets the mass diff.
	 *
	 * @param index the index
	 * @return the mass diff
	 */
	public double getMassDiff(int index){
		
		double temp = 0.0;
		
		temp = ds.getDiffArray()[index];
		
		return temp;
	
	}
	
	/**
	 * Gets the abs mass diff.
	 *
	 * @param index the index
	 * @return the abs mass diff
	 */
	public double getAbsMassDiff(int index){
		
		double temp = 0.0;
		
		temp = ds.getAbsDiffArray()[index];
		
		return temp;
	
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
		RenderingHints hintsRender = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		g2.setRenderingHints(hintsText);
		g2.addRenderingHints(hintsRender);

		double scale = ((double)Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue())/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
		
		int smallFontSize = (int)(11.0*scale);
		int tinyFontSize = (int)(8.0*scale);
		
		Font smallFont = new Font("SanSerif", Font.PLAIN, smallFontSize);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	    Font tinyFont = new Font("SanSerif", Font.PLAIN, tinyFontSize);
	    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);
		
		if(Cina.dataMassFrame.dataMassChartFrame.showMagicCheckBox.isSelected()){
    		g2.setStroke(new BasicStroke(2));
    		g2.setColor(Color.red);
    		
    		for(int i=0; i<nMagicArray.length; i++){
    		
    			g2.drawLine(xoffset+nMagicArray[i]*boxWidth, this.height, xoffset+nMagicArray[i]*boxWidth, 0);
    			g2.drawLine((xoffset+nMagicArray[i]*boxWidth)+boxWidth, this.height, (xoffset+nMagicArray[i]*boxWidth)+boxWidth, 0);
    		
    		}
    		
    		for(int i=0; i<zMagicArray.length; i++){	
    		
    			g2.drawLine(this.width, yoffset+(zmax-zMagicArray[i])*boxHeight, 0, yoffset+(zmax-zMagicArray[i])*boxHeight);
    			g2.drawLine(this.width, (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight, 0, (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight);
    		
    		}
    	
    	}
		
		colorArray = ds.getChartColorArray();
		
        for(int i=0; i<ZNMapArray.length; i++){
        	
        	int z = (int)ZNMapArray[i].getX();
        	int n = (int)ZNMapArray[i].getY();

			g2.setStroke(new BasicStroke(1));

			if(colorArray[i]!=null){

				g2.setColor(colorArray[i]);
			
			}else{
				
				g2.setColor(Color.black);
			
			}

	        g2.fillRect(xoffset+n*boxWidth,
	                   yoffset+(zmax-z)*boxHeight,
	                   boxWidth,boxHeight);
	        
	        if(Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue()>=50){
	        
		        g2.setColor(frameColor);
	
		        g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
	                   
	       	}
	       	
	       	g2.setColor(isoLabelColor);
        
	        if(Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue()>=75){
	        	
		        if(Cina.dataMassFrame.dataMassChartFrame.showLabelsCheckBox.isSelected()){
    
    				String tempS = Cina.cinaMainDataStructure.getElementSymbol(z);
			        String tempS2 = String.valueOf(z+n);
			        
			        int wid = (int)(realSmallFontMetrics.stringWidth(tempS)
			                  + tinyFontMetrics.stringWidth(tempS2));
			                  
			        int xzero = (int)(xoffset+n*boxWidth+boxWidth/2-wid/2);
			        
			        int yzero = (int)(yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
			        
			        g2.setFont(tinyFont);
			        g2.setColor(isoLabelColor);
		
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
       	
       	if(Cina.dataMassFrame.dataMassChartFrame.showRProcessCheckBox.isSelected()){
       	
       		for(int i=0; i<ds.getRProcessArray().size(); i++){
       		
       			int z = (int)((Point)ds.getRProcessArray().elementAt(i)).getX();
       			int n = (int)((Point)ds.getRProcessArray().elementAt(i)).getY();
       		
       			g2.setColor(Color.gray);
       			
       			g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
       		
       		}
       	
       	}
       	
        g2.setColor(isoLabelColor);
	    	
	   	if(Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue()>=75){ 	
	   
	        // Labels for vertical axis

	        g2.setFont(smallFont);
	        g2.setColor(Color.white);
	        
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
	        g2.setColor(Color.white);
	        
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
    	
    	if(Cina.dataMassFrame.dataMassChartFrame.showStableCheckBox.isSelected()){
    
    		for(int i=0; i<ZNMapArray.length; i++){
        	
	        	int z = (int)ZNMapArray[i].getX();
	        	int n = (int)ZNMapArray[i].getY();
	
				g2.setStroke(new BasicStroke(1));
    
	        	if(ds.getStableArray().contains(new Point(z, n))){
	        	
	        		g2.setColor(Color.red);
	        		
	        		g2.drawRect(xoffset+n*boxWidth,
	                   yoffset+(zmax-z)*boxHeight,
	                   boxWidth,boxHeight);
	        		
	        	}
	        	
	        }
    	
    	}
    	
        if(Cina.dataMassFrame.dataMassChartFrame.zoomSlider.getValue()==100
        	&& !Cina.dataMassFrame.dataMassChartFrame.showLabelsCheckBox.isSelected()){
        	
        	if(crosshairsOn){
        		
				g2.setStroke(new BasicStroke(2));
				
        		g2.setColor(Color.red);

        		g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
        	
        		g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
        
        	}
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

