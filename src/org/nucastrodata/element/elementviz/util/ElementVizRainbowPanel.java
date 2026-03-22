package org.nucastrodata.element.elementviz.util;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.Fonts;
import java.util.*;

/**
 * The Class ElementVizRainbowPanel.
 */
public class ElementVizRainbowPanel extends JPanel{
	
	/** The x0 r. */
	public double x0R = 0.80;
	
	/** The x0 g. */
	public double x0G = 0.60;
	
	/** The x0 b. */
	public double x0B = 0.20;
	
	/** The a r. */
	public double aR = 0.50;
	
	/** The a g. */
	public double aG = 0.40;
	
	/** The a b. */
	public double aB = 0.30;
	
	/** The type. */
	String type;
	
	/** The scheme. */
	String scheme;
	
	/** The der abund mag. */
	public int derAbundMag;

	/** The ds. */
	private ElementVizDataStructure ds;

	/**
	 * Instantiates a new element viz rainbow panel.
	 *
	 * @param ds the ds
	 */
	public ElementVizRainbowPanel(ElementVizDataStructure ds){
		
		this.ds = ds;
		
		setSize(100, 50);
		setType("Abundance", "Continuous");
	}	
	
	/**
	 * Sets the rgb.
	 *
	 * @param x0R the x0 r
	 * @param x0G the x0 g
	 * @param x0B the x0 b
	 * @param aR the a r
	 * @param aG the a g
	 * @param aB the a b
	 */
	public void setRGB(double x0R, double x0G, double x0B, double aR, double aG, double aB){
	
		this.x0R = x0R;
		this.x0G = x0G;
		this.x0B = x0B;
		this.aR = aR;
		this.aG = aG;
		this.aB = aB;
	
	}
	
	/**
	 * Sets the rGB.
	 *
	 * @param tempArray the new rGB
	 */
	public void setRGB(double[] tempArray){
	
		this.x0R = tempArray[0];
		this.x0G = tempArray[1];
		this.x0B = tempArray[2];
		this.aR = tempArray[3];
		this.aG = tempArray[4];
		this.aB = tempArray[5];
	
	}
		
	/**
	 * Gets the rGB.
	 *
	 * @param x the x
	 * @return the rGB
	 */
	public Color getRGB(double x){
    	if(x>=1.0){x = 1.0;}
    	if(x<=0.0){x = 0.0;}
    	
        int red = (int)(255*Math.exp(-(x-x0R)*(x-x0R)/aR/aR));
        int green = (int)(255*Math.exp(-(x-x0G)*(x-x0G)/aG/aG));
        int blue = (int)(255*Math.exp(-(x-x0B)*(x-x0B)/aB/aB));
        
        return new Color(red,green,blue);   
    }
	
	/**
	 * Sets the type.
	 *
	 * @param type the type
	 * @param scheme the scheme
	 */
	public void setType(String type, String scheme){
	
		this.type = type;
		this.scheme = scheme;
	
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		
		if(scheme.equals("Continuous")){
		
			for(int i=0; i<100; i++){
	
				g2.setColor(getRGB((double)i/100.0));
				g2.drawLine(0, 100-i, 50, 100-i);
				
			}
			
			g2.setFont(Fonts.medTitleFont);
			g2.setColor(Color.black);
			g2.drawString("Max", 0, 18);
			g2.setColor(Color.white);
			g2.drawString("Min", 2, 92);
			
			if(type.equals("Derivative")){
			
				g2.setColor(Color.black);
				g2.setStroke(new BasicStroke(1));
				g2.drawLine(35, 50, 50, 50);
				
				g2.setFont(Fonts.realSmallFont);
				g2.setStroke(new BasicStroke(1));
					
				g2.drawString("\u00b1" + "1E" + String.valueOf(derAbundMag), 2, 53);
				
			}
		
		}else if(scheme.equals("Binned")){
		
			g2.setFont(Fonts.realSmallFont);
		
			Vector binDataVector = new Vector();
		
			if(type.equals("Abundance")){
			
				binDataVector = ds.getAbundBinData();
			
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
						
								tempColor = (Color)((Vector)binDataVector.elementAt(j)).elementAt(3);
								break binFound;
								
							}
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

					g2.setColor(tempColor);
				
					g2.drawLine(0, 100-i, 50, 100-i);
					
				}
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(24, 100-lineIndex, 50, 100-lineIndex);

					g2.drawString(String.valueOf(mag), 2, 100 - lineIndex + 4);

				}
			
			}else if(type.equals("Derivative")){
			
				binDataVector = ds.getDerBinData();
			
				int minMagTotal = ((Integer)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).intValue();
				int maxMagTotal = ((Integer)((Vector)binDataVector.elementAt(0)).elementAt(1)).intValue();

				Color tempColor = new Color(0, 0, 0);

				boolean noShowBinToggle = true;

				for(int i=0; i<50; i++){
				
					double mag = ((((double)maxMagTotal-(double)minMagTotal)/50.0)*(double)i) + (double)minMagTotal;

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
				
					g2.drawLine(0, 50+i, 50, 50+i);
					
				}
				
				for(int i=0; i<50; i++){
				
					double mag = ((((double)maxMagTotal-(double)minMagTotal)/50.0)*(double)i) + (double)minMagTotal;

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
				
					g2.drawLine(0, 50-i, 50, 50-i);
					
				}
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((50.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(35, 50-lineIndex, 50, 50-lineIndex);

					g2.drawString("1E" + String.valueOf(mag), 2, 50 - lineIndex + 4);

				}
				
				int thresholdMag = ((Integer)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).intValue();
				
				g2.drawString("\u00b1" + "1E" + String.valueOf(thresholdMag), 2, 54);
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((50.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(35, 50 + lineIndex, 50, 50 + lineIndex);

					g2.drawString("-1E" + String.valueOf(mag), 2, 50 + lineIndex + 4);

				}
						
			}else if(type.equals("Reaction Flux")){
			
				binDataVector = ds.getFluxBinData();
			
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
				
					g2.drawLine(0, 100-i, 50, 100-i);
					
				}
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(24, 100-lineIndex, 50, 100-lineIndex);

					g2.drawString(String.valueOf(mag), 2, 100 - lineIndex + 4);

				}
			
			}else if(type.equals("Final Abundance")){
			
				binDataVector = ds.getFinalAbundBinData();
			
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
				
					g2.drawLine(0, 100-i, 50, 100-i);
					
				}
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(24, 100-lineIndex, 50, 100-lineIndex);

					g2.drawString(String.valueOf(mag), 2, 100 - lineIndex + 4);

				}
				
			}else if(type.equals("Integrated Flux")){
			
				binDataVector = ds.getIntFluxBinData();
			
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
				
					g2.drawLine(0, 100-i, 50, 100-i);
					
				}
				
				for(int i=1; i<binDataVector.size(); i++){
				
					g2.setColor(Color.black);
				
					int mag = ((Integer)((Vector)binDataVector.elementAt(i)).elementAt(1)).intValue();
				
					int lineIndex = (int)((100.0/((double)maxMagTotal-(double)minMagTotal))*((double)mag-(double)minMagTotal));
					
					g2.drawLine(24, 100-lineIndex, 50, 100-lineIndex);

					g2.drawString(String.valueOf(mag), 2, 100 - lineIndex + 4);

				}
				
			}
			
		}
		
    }
    
}
