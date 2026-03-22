package org.nucastrodata.element.elementviz.scale;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;

import java.awt.event.*;
import java.text.DecimalFormat;

import org.nucastrodata.StaticPlotter;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotControlsPanel;


/**
 * The Class ElementVizScalePlotPanel.
 */
public class ElementVizScalePlotPanel extends PlotPanel implements MouseMotionListener, MouseListener{

	//Declare the cinaStaticPlotter!
	/** The astro pilot project static plotter. */
	StaticPlotter cinaStaticPlotter;
	
	// Define some standard colors for convenience
    /** The A iyellow. */
	Color AIyellow=new Color (255,204,0);
    
    /** The A iorange. */
    Color AIorange=new Color(255,153,0);
    
    /** The A ired. */
    Color AIred=new Color(204,51,0);
    
    /** The A ipurple. */
    Color AIpurple=new Color(153,102,153);
    
    /** The A iblue. */
    Color AIblue=new Color(102,153,153);
    
    /** The A igreen. */
    Color AIgreen=new Color(153,204,153);
    
    /** The gray51. */
    Color gray51=new Color(51,51,51);
    
    /** The gray102. */
    Color gray102=new Color(102,102,102);
    
    /** The gray153. */
    Color gray153=new Color(153,153,153);
    
    /** The gray204. */
    Color gray204=new Color(204,204,204);
    
    /** The gray245. */
    Color gray245=new Color(245,245,245);
    
    /** The gray250. */
    Color gray250=new Color(252,252,252);
	
	//linear mode
	/** The plotmode. */
	int plotmode = 2;
	
	//upper left corner of plot
	/** The x1. */
	int x1 = 4;
	
	/** The y1. */
	int y1 = 4;
	
	//lower right corner of plot
	/** The x2. */
	int x2 = 504;
	
	/** The y2. */
	int y2 = 504;
	
	//max number of points per curve
	/** The kmax. */
	int kmax = 1;
	
	//max number of curves
	/** The imax. */
	int imax = 1;
	
	//int indicating solid line plot
	/** The mode. */
	int[] mode = new int[1];
	
	//dotsize (not used for solid line plot but required parameter)
	/** The dot size. */
	int dotSize = 5;
	
	//offset for legend
	/** The xlegoff. */
	int xlegoff = 80;
	
	/** The ylegoff. */
	int ylegoff = 40;
	
	//number of decimal places for numbers on x and y axis
	/** The xdplace. */
	int xdplace = 0;
	
	/** The ydplace. */
	int ydplace = 0;
	
	//number of data points for each curve
	/** The npoints. */
	int[] npoints = new int[1];
	
	//set to NO autoscale to max and min of x and y sets
	/** The doscalex. */
	int doscalex = 0;
	
	/** The doscaley. */
	int doscaley = 0;
	
	//say yes to plot the curve
	/** The doplot. */
	int[] doplot = new int[1];
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	/** The xmin. */
	double xmin = 0;
	
	/** The xmax. */
	double xmax = 0;
		
	/** The ymin. */
	double ymin = 0;
	
	/** The ymax. */
	double ymax = 0;
	
	//set empty space around plot as fraction of total height
	//and width of plot
	/** The delxmin. */
	double delxmin = 0.0;
	
	/** The delymin. */
	double delymin = 0.0;
	
	/** The delxmax. */
	double delxmax = 0.0;
	
	/** The delymax. */
	double delymax = 0.0;
	
	//Set colors for lines or curves
	/** The lcolor. */
	Color[] lcolor = new Color[40];
	
	/** The color array. */
	Color[] colorArray = new Color[1];
	
	/** The bgcolor. */
	Color bgcolor=Color.white;        // plot background color
    
    /** The axiscolor. */
    Color axiscolor=gray51;           // axis color
    
    /** The legendfg. */
    Color legendfg=gray250;           // legend box color
    
    /** The framefg. */
    Color framefg=Color.white;        // frame color
    
    /** The drop shadow. */
    Color dropShadow = gray153;       // legend box dropshadow color
    
    /** The legendbg. */
    Color legendbg=gray204;           // legend box frame color
    
    /** The labelcolor. */
    Color labelcolor = gray51;        // axis label color
    
    /** The tic label color. */
    Color ticLabelColor = gray51;     // axis tic label color
	
	//title of x axis
	/** The xtitle. */
	String xtitle = "Temperature (T9)";
	
	//title of y axis
	/** The ytitle. */
	String ytitle = "Rate";
	
	//set curve title for legend
	/** The curve title. */
	String[] curveTitle = new String[1];
	
	//set style of log plot (show number or log of number on each axis)
	/** The log style. */
	int logStyle = 1;
	
	//number of intervals between x and y tick marks
	/** The ytick intervals. */
	int ytickIntervals = 0;
	
	/** The xtick intervals. */
	int xtickIntervals = 0;
	
	//do NOT show the legend
	/** The show legend. */
	boolean showLegend = true;
	
	//double arrays to hold x and y points 
	//first entry for each curve and next entry for number of points
	/** The x. */
	double[][] x = new double[imax][kmax];
	
	/** The y. */
	double[][] y = new double[imax][kmax];
	
	//show major minor tick marks
	//for X and Y
	//must change to current variables
	//here and in CinaStaticPlotter
	/** The major x. */
	boolean majorX = true;
    
    /** The minor x. */
    boolean minorX = false;
    
    /** The major y. */
    boolean majorY = true;
    
    /** The minor y. */
    boolean minorY = false;
    
    //Show title and subtitle
    /** The title. */
    boolean title = true;
    
    /** The subtitle. */
    boolean subtitle = false;
    
    //Title and subtitle names
    /** The title string. */
    String titleString = "";
    
    /** The subtitle string. */
    String subtitleString = "";
    
    //Is the legend inside the graph?
    /** The inside legend. */
    boolean insideLegend = true;
    
    //Initialize legend position
    /** The location. */
    String location = "NW";
    
    /** The xoffset. */
    int xoffset=65;         // pixels to left of y axis
    
    /** The yoffset. */
    int yoffset=40;         // pixels below x axis
    
    /** The topmarg. */
    int topmarg=30;         // pixels above graph
    
    /** The rightmarg. */
    int rightmarg=20;       // pixels to right of graph
	
	/** The mouse x. */
	int mouseX = 0;
	
	/** The mouse y. */
	int mouseY = 0;

	/** The show window. */
	boolean showWindow = false;
	
	/** The mouse dragging. */
	boolean mouseDragging = false;
	
	/** The square. */
	Rectangle square = new Rectangle();

	/** The ds. */
	private ElementVizDataStructure ds;
	
	/** The parent. */
	private ElementVizScalePlotControlsPanel parent;
	
	/** The initialized. */
	private boolean initialized = false;
	
	private DecimalFormat df = new DecimalFormat("0.0E0");
	
	/**
	 * Instantiates a new element viz scale plot panel.
	 *
	 * @param ds the ds
	 * @param parent the parent
	 */
	public ElementVizScalePlotPanel(ElementVizDataStructure ds, ElementVizScalePlotControlsPanel parent){
		this.ds = ds;
		this.parent = parent;
		setColorArray();
		setBackground(Color.white);
		cinaStaticPlotter = new StaticPlotter();
		addMouseListener(this);
		addMouseMotionListener(this);
		square.width = 80;
    	square.height = 80;
	}

	/**
	 * Sets the current state.
	 *
	 * @param parentmode the new current state
	 */
	public void setCurrentState(ElementVizScalePlotControlsPanel.Mode parentmode
								, ElementVizScalePlotControlsPanel.PlotType plotTypeX
								, ElementVizScalePlotControlsPanel.PlotType plotTypeY){
			
		int size = parent.index.length;
		colorArray = new Color[size];
		imax = size;
		kmax = parent.getMap().size();
		mode = new int[size];				
		doplot = new int[size];				
		curveTitle = new String[size];
		npoints = new int[size];
		majorX = parent.getMajorX();
		minorX = parent.getMinorX();
		majorY = parent.getMajorY();
		minorY = parent.getMinorY();
		showLegend = false;
		
		if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LIN
				&& plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LIN){
			plotmode = 0;
		}else if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LIN
				&& plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LOG){
			plotmode = 1;
		}else if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LOG
				&& plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LIN){
			plotmode = 3;
		}else if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LOG
				&& plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LOG){
			plotmode = 2;
		}
		
		int colorCounter = 0;
		for(int i=0; i<size; i++){
    		if(parent.listPanel.boxList.get(i).isSelected()){
    			doplot[i] = 1;
    			if(parent.legendBox.isSelected()){
    				showLegend = true;
    			}
    		}else{
    			doplot[i] = 0;
    		}
    		
    		if(colorCounter==40){colorCounter=0;}
    		
    		colorArray[i] = lcolor[colorCounter];
    		colorCounter++;
    		mode[i] = 2;
    		curveTitle[i] = parent.listPanel.boxList.get(i).getText();
			npoints[i] = kmax;
		}
		
		if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LIN){
			xmax = parent.getLinMaxX();
			xmin = parent.getLinMinX();
			xtickIntervals = 10;
			xdplace = 2;
		}else if(plotTypeX==ElementVizScalePlotControlsPanel.PlotType.LOG){
			xmax = Math.pow(10, parent.getLogMaxX());
			xmin = Math.pow(10, parent.getLogMinX());
			xtickIntervals = (int)Math.abs(parent.getLogMaxX() - parent.getLogMinX());
			xdplace = 0;
		}
		
		if(plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LIN){
			ymax = parent.getLinMaxY();
			ymin = parent.getLinMinY();
			ytickIntervals = 10;
			ydplace = 4;
		}else if(plotTypeY==ElementVizScalePlotControlsPanel.PlotType.LOG){
			ymax = Math.pow(10, parent.getLogMaxY());
			ymin = Math.pow(10, parent.getLogMinY());
			ytickIntervals = (int)Math.abs(parent.getLogMaxY() - parent.getLogMinY());
			ydplace = 0;
		}
		
		x = parent.x;
		xtitle = "Rate Scale Factor";
		
		if(parentmode==ElementVizScalePlotControlsPanel.Mode.ABUND){
			titleString = parent.setName 
							+ " (" 
							+ parent.getMap().values().iterator().next().getReactionRate() 
							+ ")";
			ytitle = "Final Abundance";
			y = parent.yreg;
		}else if(parentmode==ElementVizScalePlotControlsPanel.Mode.NORM){
			titleString = parent.setName 
							+ " (" 
							+ parent.getMap().values().iterator().next().getReactionRate()
							+ ": Norm @ Rate Scale Factor="
							+ parent.scaleFactor
							+ ")";
			ytitle = "Normalized Final Abundance";
			y = parent.ynorm;
		}	
		initialized = true;
		
	}
	
	/**
	 * Sets the color array.
	 */
	public void setColorArray(){
	
		Color AIyellow=new Color (255,204,0);
        Color AIorange=new Color(255,153,0);
        Color AIred=new Color(204,51,0);
        Color AIpurple=new Color(153,102,153);
        Color AIblue=new Color(102,153,153);
        Color AIgreen=new Color(153,204,153);
        Color gray51=new Color(51,51,51);
        Color gray102=new Color(102,102,102);
        Color gray153=new Color(153,153,153);
        Color gray204=new Color(204,204,204);
        Color gray245=new Color(245,245,245);
        Color gray250=new Color(252,252,252);

        lcolor[0] = Color.black;
        lcolor[1] = Color.blue;
        lcolor[2] = Color.red;
        lcolor[3] = Color.magenta;
        lcolor[4] = gray102;
        lcolor[5] = new Color(0,220,0);
        lcolor[6] = AIblue;
        lcolor[7] = AIpurple;
        lcolor[8] = AIorange;
        lcolor[9] = AIgreen;

        lcolor[10] = new Color(51,153,51);
        lcolor[11] = new Color(0,51,102);
        lcolor[12] = new Color(0,153,153);
        lcolor[13] = new Color(0,51,153);
        lcolor[14] = new Color(51,153,153);
        lcolor[15] = new Color(0,153,204);
        lcolor[16] = new Color(51,0,153);
        lcolor[17] = new Color(51,204,153);
        lcolor[18] = new Color(153,153,0);
        lcolor[19] = new Color(153,102,51);
        lcolor[20] = new Color(153,51,0);

        lcolor[21] = gray51;
        lcolor[22] = AIred;
        lcolor[23] = gray153;
        lcolor[24] = new Color(153,153,102);
        lcolor[25] = new Color(102,51,153);
        lcolor[26] = new Color(153,51,204);
        lcolor[27] = new Color(153,153,204);
        lcolor[28] = new Color(102,204,255);
        lcolor[29] = new Color(153,51,255);
        lcolor[30] = new Color(255,102,51);

        lcolor[31] = new Color(204,51,102);
        lcolor[32] = new Color(204,153,102);
        lcolor[33] = new Color(204,204,102);
        lcolor[34] = new Color(255,153,102);
        lcolor[35] = new Color(204,51,153);
        lcolor[36] = new Color(204,153,153);
        lcolor[37] = new Color(255,51,153);
        lcolor[38] = new Color(255,153,153);
        lcolor[39] = new Color(255,204,153);
        //lcolor[40] = new Color(204,51,204);

        /*lcolor[41] = new Color(204,153,204);
        lcolor[42] = new Color(255,51,204);
        lcolor[43] = new Color(204,51,255);
        lcolor[44] = new Color(255,51,255);*/
        
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = true;
		repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = false;
		repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = true;	
		
		repaint();
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = false;	
		
		repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
		
		repaint();
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		mouseDragging = true;
		
		repaint();
	
	}	
	
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g){
    	
    	if(initialized){
    	
	    	Graphics2D g2 = (Graphics2D)g;
	
			super.paintComponent(g2);
	
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
			g2.setRenderingHints(hints);
	    
	        cinaStaticPlotter.plotIt(plotmode,x1,y1,x2,y2,
	                  kmax,imax,mode,
	                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
	                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
	                  delxmin,delxmax,delymin,delymax,
	                  colorArray,bgcolor,axiscolor,legendfg,framefg,
	                  dropShadow,legendbg,labelcolor,ticLabelColor,
	                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
	                  xtickIntervals,showLegend,x,y,majorX, minorX, 
	                  majorY, minorY, title, subtitle, 
	                  titleString, subtitleString, 
	                  insideLegend, location, 
	                  xoffset, yoffset, topmarg, rightmarg, df, df, g2);    
	                  
	       	if(showWindow && mouseDragging){
	
	    		square.x = mouseX - 40;
	    		square.y = mouseY - 40;
	 
	    		g2.clip(square);
	    	
	    		g2.scale(2, 2);
	
				int shiftX = (int)((1*mouseX - x1)/2);
				int shiftY = (int)((1*mouseY - y1)/2);
	
				int newX1 = x1 - shiftX;
				int newY1 = y1 - shiftY;
				int newX2 = x2 - shiftX;
				int newY2 = y2 - shiftY;  
	
				cinaStaticPlotter.plotIt(plotmode,newX1,newY1,newX2,newY2,
	                  kmax,imax,mode,
	                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
	                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
	                  delxmin,delxmax,delymin,delymax,
	                  lcolor,bgcolor,axiscolor,legendfg,framefg,
	                  dropShadow,legendbg,labelcolor,ticLabelColor,
	                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
	                  xtickIntervals,showLegend,x,y,majorX, minorX, 
	                  majorY, minorY, title, subtitle, 
	                  titleString, subtitleString, 
	                  insideLegend, location, 
	                  xoffset, yoffset, topmarg, rightmarg, df, df, g2); 
	                  
	        }
       	
    	}

    }
    
    /**
     * Paint me.
     *
     * @param g the g
     */
    public void paintMe(Graphics g){

		cinaStaticPlotter.plotIt(plotmode,x1,y1,x2,y2,
                  kmax,imax,mode,
                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
                  delxmin,delxmax,delymin,delymax,
                  colorArray,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,majorX, minorX, 
                  majorY, minorY, title, subtitle, 
                  titleString, subtitleString, 
                  insideLegend, location, 
                  xoffset, yoffset, 20, rightmarg, null, df, g); 
       	             
	}
  
}  

