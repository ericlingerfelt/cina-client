package org.nucastrodata.element.elementviz.intflux;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.NumberFormats;


/**
 * The Class ElementVizIntFluxPanel.
 */
public class ElementVizIntFluxPanel extends JPanel implements MouseListener, MouseMotionListener, Scrollable{
	
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
    
    /** The line vector. */
    Vector lineVector = new Vector();
    
    /** The scheme abund. */
    String schemeAbund;
    
    /** The scheme flux. */
    String schemeFlux;
    
    /** The stroke array. */
    BasicStroke[][] strokeArray;
    
    /** The init flag. */
    boolean initFlag = true;
    
    /** The ds. */
    private ElementVizDataStructure ds;
    
    /**
     * Instantiates a new element viz int flux panel.
     *
     * @param ds the ds
     */
    public ElementVizIntFluxPanel(ElementVizDataStructure ds){
    	
    	this.ds = ds;
    	
    	setAutoscrolls(true);
    	
        addMouseListener(this);
        addMouseMotionListener(this);
        
        setStrokeArray();
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
	 */
	public void setCurrentState(String schemeAbund, String schemeFlux){

		this.schemeAbund = schemeAbund;
		this.schemeFlux = schemeFlux;

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
        
        if(!initFlag){
        
        	double scale = ((double)Cina.elementVizFrame.elementVizIntFluxFrame.zoomSlider.getValue())/100.0;
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

    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	    	
    	Image image = toolkit.createImage("blankImage.image");
    
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
    	
    	crosshairsOn = true;
    	
    	Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint();

    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {

    	Cina.elementVizFrame.elementVizIntFluxFrame.valueField.setText("");
    	
    	Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.setValues("", "");																							
		Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.repaint();
    	
    	Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.setValues("", "", "", "");																							
		Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.repaint();
    	
    	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	
    	crosshairsOn = false;
    	
		Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

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
    	
		foundFlux:
		for(int i=0; i<lineVector.size(); i++){

			if(((Line2D.Double)lineVector.elementAt(i)).ptSegDist(mouseX, mouseY)<=2.0){

				int nIn = (int)((((Line2D.Double)lineVector.elementAt(i)).getX1() - xoffset)/boxWidth - 0.5);
				int zIn = (int)(-1*((((Line2D.Double)lineVector.elementAt(i)).getY1() - yoffset)/boxHeight - 0.5 - zmax));
				int nOut = (int)((((Line2D.Double)lineVector.elementAt(i)).getX2() - xoffset)/boxWidth - 0.5);
				int zOut = (int)(-1*((((Line2D.Double)lineVector.elementAt(i)).getY2() - yoffset)/boxHeight - 0.5 - zmax));

	    		Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.setValues(Cina.cinaMainDataStructure.getElementSymbol(zIn)
	    																										, Cina.cinaMainDataStructure.getElementSymbol(zOut)
	    																										, String.valueOf(zIn+nIn)
	    																										, String.valueOf(zOut+nOut));
	    																										
				Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.repaint();
				Cina.elementVizFrame.elementVizIntFluxFrame.valueField.setText(NumberFormats.getFormattedValueLong(getFluxValue(zIn, zIn+nIn, zOut, zOut+nOut)));
				
				Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.remove(Cina.elementVizFrame.elementVizIntFluxFrame.isotopePanelPane);
				
				GridBagConstraints gbc = new GridBagConstraints();
				
				gbc.gridx = 0;
				gbc.gridy = 11;
				gbc.gridwidth = 4;
				gbc.insets = new Insets(3, 3, 3, 3);
				gbc.anchor = GridBagConstraints.CENTER;
				
				Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.add(Cina.elementVizFrame.elementVizIntFluxFrame.reactionPanelPane, gbc);
				
				//Cina.setFrameColors(Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel);
				Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.validate();
				
		    	Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.setValues("", "");																							
				Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.repaint();

				break foundFlux;
			
			}else{
			
				Cina.elementVizFrame.elementVizIntFluxFrame.valueField.setText("");
	    		Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.setValues("", "", "", "");																							
				Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.repaint();
				
				if((int)protonNumber>-1 
		        	&& viktor.contains(new Point((int)protonNumber, (int)protonNumber + (int)neutronNumber))
		        	&& Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected()){
		        	
		        	Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.remove(Cina.elementVizFrame.elementVizIntFluxFrame.reactionPanelPane);
		        	
		        	GridBagConstraints gbc = new GridBagConstraints();
				
					gbc.gridx = 0;
					gbc.gridy = 11;
					gbc.gridwidth = 4;
					gbc.insets = new Insets(3, 3, 3, 3);
					gbc.anchor = GridBagConstraints.CENTER;
					
					Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.add(Cina.elementVizFrame.elementVizIntFluxFrame.isotopePanelPane, gbc);
					
					//Cina.setFrameColors(Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel);
					Cina.elementVizFrame.elementVizIntFluxFrame.rightPanel.validate();
		        	
		        	Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.setValues(Cina.cinaMainDataStructure.getElementSymbol((int)protonNumber)
		        																								, String.valueOf((int)neutronNumber + (int)protonNumber));
		        																																															
					Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.repaint();
		    		Cina.elementVizFrame.elementVizIntFluxFrame.valueField.setText(NumberFormats.getFormattedValueLong(getValue(new Point((int)protonNumber, (int)protonNumber + (int)neutronNumber))));
		    	
		    	}else{
		    	
		    		Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.setValues("", "");																							
					Cina.elementVizFrame.elementVizIntFluxFrame.elementVizIsotopePanel.repaint();
					
					Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.setValues("", "", "", "");																							
					Cina.elementVizFrame.elementVizIntFluxFrame.elementVizReactionPanel.repaint();
		    	
		    	}
			
			}
		
		}
    	
    	Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setPreferredWidth((int)getSize().getWidth());
       	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
       	Cina.elementVizFrame.elementVizIntFluxFrame.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	Cina.elementVizFrame.elementVizIntFluxFrame.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
    	
    	repaint();
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){}
	
	/**
	 * Gets the value.
	 *
	 * @param index the index
	 * @return the value
	 */
	public double getValue(Point point){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<viktor.size(); i++){
			Point p = (Point)viktor.get(i);
			if(p.getX()==point.getX() && p.getY()==point.getY()){
				list.add(i);
			}
		}
		
		double temp = 0.0;
		
		for(Integer index: list){
		
			gotAbund:
			for(int i=0; i<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; i++){
			
				if(ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray()[i]==index){
				
					temp += ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getAbundArray()[i];
					break gotAbund;
								
				}
			
			}
		
		}
		
		return temp;
	
	}
	
	/**
	 * Gets the flux value.
	 *
	 * @param zIn the z in
	 * @param aIn the a in
	 * @param zOut the z out
	 * @param aOut the a out
	 * @return the flux value
	 */
	public double getFluxValue(int zIn, int aIn, int zOut, int aOut){
	
		double fluxValue = 0.0;
		
		int inIndex = 0;
		int outIndex = 0;
		int reactionIndex = 0;
		
		foundIsoIn:
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getZAMapArray().length; i++){
			
			if((int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[i].getX())==zIn
					&& (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[i].getY())==aIn){
			
				inIndex = i;
				break foundIsoIn;
			
			}
			
		}
		
		foundIsoOut:
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getZAMapArray().length; i++){
			
			if((int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[i].getX())==zOut
					&& (int)(ds.getIntFluxNucSimDataStructure().getZAMapArray()[i].getY())==aOut){
			
				outIndex = i;
				break foundIsoOut;
			
			}
			
		}
		
		foundReaction:
		for(int i=0; i<ds.getIntFluxNucSimDataStructure().getReactionMapArray().length; i++){
		
			if((int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[i].getX())==inIndex
					&& (int)(ds.getIntFluxNucSimDataStructure().getReactionMapArray()[i].getY())==outIndex){
			
				fluxValue = ds.getIntFluxNucSimDataStructure().getIntFluxArray()[i];
			
				break foundReaction;
			
			}
		
		}
		
		return fluxValue;
	
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

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints hintsRender = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		g2.setRenderingHints(hints);
		g2.addRenderingHints(hintsRender);

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

		boolean showFinalAbund = Cina.elementVizFrame.elementVizIntFluxFrame.showFinalAbundCheckBox.isSelected();
		boolean al26Found = false;
		
        for(int i=0; i<ZAMapArray.length; i++){
        	
        	int z = (int)ZAMapArray[i].getX();
        	int n = (int)ZAMapArray[i].getY() - (int)ZAMapArray[i].getX();
        	
        	if(al26Found && z==13 && n==13){
        		continue;
        	}
        	
        	if(schemeAbund.equals("Continuous")){
        	
	        	if(showFinalAbund){
	        	
	        		g2.setColor(minimumColorAbund);
	        		
					colorFound:
					for(int j=0; j<ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray().length; j++){
		
						if(ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getIndexArray()[j]==i){
						
							int red = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getRedArray()[j];
							int green = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getGreenArray()[j];
							int blue = (int)ds.getIntFluxNucSimDataStructure().getAbundTimestepDataStructureArray()[0].getBlueArray()[j];
							
							color = new Color(red, green, blue);
							
							if(z==13 && n==13){
								al26Found = true;
			            	}
							
							g2.setColor(color);
							
							break colorFound;
						
						}
						
					}
					
					g2.fillRect(xoffset+n*boxWidth,
					                   yoffset+(zmax-z)*boxHeight,
					                   boxWidth,boxHeight);
	        
			        g2.setColor(frameColor);
			        
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
				        
			        g2.setColor(frameColor);
			        
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
							
							g2.setColor(Color.black);
						
						}
						
					}
				
					g2.fillRect(xoffset+n*boxWidth,
				                   yoffset+(zmax-z)*boxHeight,
				                   boxWidth,boxHeight);
	        
			        g2.setColor(frameColor);
			        
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
				        
			        g2.setColor(frameColor);
			        
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
	   
        g2.setColor(isoLabelColor);
        
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

