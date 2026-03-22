package org.nucastrodata.data.dataman;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataManDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.StaticPlotter;


/**
 * The Class DataManImportDataPlotCanvas.
 */
public class DataManImportDataPlotCanvas extends JPanel implements MouseMotionListener, MouseListener{
	
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
	int plotmode = 1;
	
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
	int kmax = 1000;
	
	//max number of curves
	/** The imax. */
	int imax = 1;
	
	//int indicating solid line plot
	/** The mode. */
	int[] mode = {1};
	
	//dotsize (not used for solid line plot but required parameter)
	/** The dot size. */
	int dotSize = 3;
	
	//offset for legend (not used here since plotting one curve at a time)
	/** The xlegoff. */
	int xlegoff = 100;
	
	/** The ylegoff. */
	int ylegoff = 10;
	
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
	int doscalex = 1;
	
	/** The doscaley. */
	int doscaley = 0;
	
	//say yes to plot the curve
	/** The doplot. */
	int[] doplot = {1};
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	/** The xmin. */
	double xmin = 0.0;
	
	/** The xmax. */
	double xmax = 0.0;
	
	/** The ymin. */
	double ymin = 1e3;
	
	/** The ymax. */
	double ymax = 1e7;
	
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
	Color[] lcolor = {new Color(0,0,0)};
	
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
	String xtitle = "";
	
	//title of y axis
	/** The ytitle. */
	String ytitle = "";
	
	//set curve title for legend (not used no legend to be shown here)
	/** The curve title. */
	String[] curveTitle = {""};
	
	//set style of log plot (show number or log of number on each axis)
	//not used here plot is linear
	/** The log style. */
	int logStyle = 1;
	
	//number of intervals between x and y tick marks
	/** The ytick intervals. */
	int ytickIntervals = 10;
	
	/** The xtick intervals. */
	int xtickIntervals = 10;
	
	//do NOT show the legend
	/** The show legend. */
	boolean showLegend = false;
	
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
	boolean majorX = false;
    
    /** The minor x. */
    boolean minorX = false;
    
    /** The major y. */
    boolean majorY = false;
    
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
	
	/** The Ymin combo box init. */
	int YminComboBoxInit;
    
    /** The Ymax combo box init. */
    int YmaxComboBoxInit;
	
	/** The Xmin combo box init. */
	int XminComboBoxInit;
    
    /** The Xmax combo box init. */
    int XmaxComboBoxInit;
	
	/** The init flag. */
	boolean initFlag = false;
	
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
	private DataManDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new data man import data plot canvas.
	 *
	 * @param yminInit the ymin init
	 * @param ymaxInit the ymax init
	 * @param ds the ds
	 */
	public DataManImportDataPlotCanvas(int yminInit, int ymaxInit, DataManDataStructure ds){
		
		this.ds = ds;
		
		YminComboBoxInit = yminInit;
		YmaxComboBoxInit = ymaxInit;
		
		setPlotState();
		
		cinaStaticPlotter = new StaticPlotter();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		square.width = 80;
    	square.height = 80;
    	
    	setBackground(Color.white);
		
	}
	
	/**
	 * Sets the plot state.
	 */
	public void setPlotState(){
		
		lcolor[0] = new Color(0,0,0);
		
		xdplace = 1;
		
		kmax = ds.getImportNucDataDataStructure().getNumberPoints();
	
		npoints[0] = kmax;
	
		for(int i=0; i<kmax; i++){
			
			x[0][i] = ds.getImportNucDataDataStructure().getXDataArray()[i];
	
			y[0][i] = ds.getImportNucDataDataStructure().getYDataArray()[i];
		
		}
		
		if(initFlag){
			
			if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
			
				plotmode = 1;

				ymin = Math.pow(10, Cina.dataManFrame.dataManImportDataPlotFrame.getYmin());
				ymax = Math.pow(10, Cina.dataManFrame.dataManImportDataPlotFrame.getYmax());
				
				ytickIntervals = Math.abs((int)Cina.dataManFrame.dataManImportDataPlotFrame.getYmin()
										- (int)Cina.dataManFrame.dataManImportDataPlotFrame.getYmax());

			}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
				
				plotmode = 0;
				
				ytickIntervals = 10;
				
				ymin = Double.valueOf(Cina.dataManFrame.dataManImportDataPlotFrame.YminField.getText()).doubleValue();
				ymax = Double.valueOf(Cina.dataManFrame.dataManImportDataPlotFrame.YmaxField.getText()).doubleValue();
				
			}

			if(Cina.dataManFrame.dataManImportDataPlotFrame.EBox.isSelected()){
	
				doscalex = 1;
	
			}else{
			
				doscalex = 0;
			
			}
	
			xmin = Double.valueOf(Cina.dataManFrame.dataManImportDataPlotFrame.EminField.getText()).doubleValue();
			xmax = Double.valueOf(Cina.dataManFrame.dataManImportDataPlotFrame.EmaxField.getText()).doubleValue();
			
		}else{
					
			initFlag = true;
			
			if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
			
				plotmode = 1;

				ytickIntervals = Math.abs((int)(YminComboBoxInit - YmaxComboBoxInit));
			
				ymin = Math.pow(10, YminComboBoxInit);
				ymax = Math.pow(10, YmaxComboBoxInit);

			}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
			
				plotmode = 0;
				
				ytickIntervals = 10;
			
				ymin = ds.getImportNucDataDataStructure().getYmin();
				ymax = ds.getImportNucDataDataStructure().getYmax();
				
			}

			doscalex = 1;
			
			xmin = ds.getImportNucDataDataStructure().getXmin();
			xmax = ds.getImportNucDataDataStructure().getXmax();
		
		} 
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("CS(E)")){
			
			ytitle = "Cross Section (b)";
			xtitle = "Ec.m. (keV)";
			
		}else if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
			
			ytitle = "S Factor (keV-b)";
			xtitle = "Ec.m. (keV)";
			
		}
		
		majorX = true;
		majorY = true;
		
		if(ytickIntervals<=10){
		
			minorY = true;
		
		}else{
		
			minorY = false;
		
		}
		
		if(ds.getImportNucDataDataStructure().getNucDataType().equals("S(E)")){
		
			minorY = false;
		
		}
		
		if(ds.getImportNucDataDataStructure().getDecay().equals("")){
		
			titleString = ds.getImportNucDataDataStructure().getNucDataName() 
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ ")";
						
		}else{
		
			titleString = ds.getImportNucDataDataStructure().getNucDataName() 
							+ " ("
							+ ds.getImportNucDataDataStructure().getReactionString()
							+ " ["
							+ ds.getImportNucDataDataStructure().getDecay()
							+ "]"
							+ ")";
		
		}
		
		repaint();
	
	}
	
	/**
	 * Sets the do scalex.
	 *
	 * @param flag the new do scalex
	 */
	public void setDoScalex(int flag){
		doscalex = flag;
		
		repaint();
	}
	
	/**
	 * Sets the xmin.
	 *
	 * @param Xmin the new xmin
	 */
	public void setXmin(double Xmin){
	
		xmin = Xmin;
	
	}
	
	/**
	 * Sets the xmax.
	 *
	 * @param Xmax the new xmax
	 */
	public void setXmax(double Xmax){
	
		xmax = Xmax;
	
	}
	
	/**
	 * Sets the ymin.
	 *
	 * @param Ymin the new ymin
	 */
	public void setYmin(double Ymin){
	
		ymin = Ymin;
	
	}
	
	/**
	 * Sets the ymax.
	 *
	 * @param Ymax the new ymax
	 */
	public void setYmax(double Ymax){
	
		ymax = Ymax;
	
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

		Graphics2D g2 = (Graphics2D)g;

		super.paintComponent(g2);

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setRenderingHints(hints);

		cinaStaticPlotter.plotIt(plotmode,x1,y1,x2,y2,
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
                  xoffset, yoffset, topmarg, rightmarg, g2);
                  
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
                  xoffset, yoffset, topmarg, rightmarg, g2);
                  
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
                  lcolor,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,majorX, minorX, 
                  majorY, minorY, title, subtitle, 
                  titleString, subtitleString, 
                  insideLegend, location, 
                  xoffset, yoffset, topmarg, rightmarg, g);  
       	             
	}
	
}
