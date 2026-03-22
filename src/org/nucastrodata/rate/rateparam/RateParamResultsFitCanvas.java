package org.nucastrodata.rate.rateparam;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.RateParamDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.StaticPlotter;


/**
 * The Class RateParamResultsFitCanvas.
 */
public class RateParamResultsFitCanvas extends JPanel implements MouseMotionListener, MouseListener{
	
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
	
	//lin-log
	/** The plotmode. */
	int plotmode = 3;
	
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
	int dotSize = 5;
	
	/** The xlegoff. */
	int xlegoff = 100;
	
	/** The ylegoff. */
	int ylegoff = 10;
	
	//number of decimal places for numbers on x and y axis
	/** The xdplace. */
	int xdplace = 0;
	
	/** The ydplace. */
	int ydplace = 2;
	
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
	int[] doplot = {1};
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	/** The xmin. */
	double xmin = 1e-3;
	
	/** The xmax. */
	double xmax = 1e1;
		
	/** The ymin. */
	double ymin = -100;
	
	/** The ymax. */
	double ymax = 100;
	
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
	Color[] lcolor = {Color.BLACK};
	
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
	String ytitle = "((Fit-Num. Int.)/Num. Int.)*100";
	
	//set curve title for legend (not used no legend to be shown here)
	/** The curve title. */
	String[] curveTitle = {""};
	
	//set style of log plot (show number or log of number on each axis)
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
	boolean majorX = true;
    
    /** The minor x. */
    boolean minorX = true;
    
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
	
	//Reaction rate units
	/** The units. */
	String[] units = {"", " reactions/sec", " reactions/sec", " reactions/sec", " cm^3/(mole*s)"
                                , " cm^3/(mole*s)", " cm^3/(mole*s)", " cm^3/(mole*s)"
                                , " cm^6/(mole^2 * s)"};
	
	/** The init flag. */
	boolean initFlag = false;
	
	/** The tmin combo box init. */
	int tminComboBoxInit;
    
    /** The tmax combo box init. */
    int tmaxComboBoxInit;
    
    /** The pmin init. */
    double pminInit;
    
    /** The pmax init. */
    double pmaxInit;
	
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
	private RateParamDataStructure ds;
	
	//Constructor
	/**
	 * Instantiates a new rate param results fit canvas.
	 *
	 * @param tminInit the tmin init
	 * @param tmaxInit the tmax init
	 * @param pminInit the pmin init
	 * @param pmaxInit the pmax init
	 * @param ds the ds
	 */
	public RateParamResultsFitCanvas(int tminInit
										, int tmaxInit
										, double pminInit
										, double pmaxInit
										, RateParamDataStructure ds){
		this.ds = ds;
		
		tminComboBoxInit = tminInit;
		tmaxComboBoxInit = tmaxInit;
		
		this.pminInit = pminInit;
		this.pmaxInit = pmaxInit;
		
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
		
		for(int i=0; i<ds.getNumberPointsParam(); i++){
			
			x[0][i] = ds.getTempParamDataArray()[i];
			
			y[0][i] = ds.getPercentDiffDataArray()[i];
		
		}
		
		kmax = ds.getNumberPointsParam();
		
		npoints[0] = kmax;
		
		titleString = ds.getRateDataStructure().getReactionString();
	
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			titleString += " [" + ds.getRateDataStructure().getDecay() + "]";
		
		}
	
		if(initFlag){
		
			xmin = Math.pow(10, Cina.rateParamFrame.rateParamResultsFitFrame.getTmin());
			xmax = Math.pow(10, Cina.rateParamFrame.rateParamResultsFitFrame.getTmax());
			ymin = Cina.rateParamFrame.rateParamResultsFitFrame.getPercentDiffmin();
			ymax = Cina.rateParamFrame.rateParamResultsFitFrame.getPercentDiffmax();
			
			minorX = Cina.rateParamFrame.rateParamResultsFitFrame.getMinorT();
			majorX = Cina.rateParamFrame.rateParamResultsFitFrame.getMajorT();			
			minorY = Cina.rateParamFrame.rateParamResultsFitFrame.getMinorPercentDiff();
			majorY = Cina.rateParamFrame.rateParamResultsFitFrame.getMajorPercentDiff();
			
			xtickIntervals = Math.abs((int)Cina.rateParamFrame.rateParamResultsFitFrame.getTmin()
									- (int)Cina.rateParamFrame.rateParamResultsFitFrame.getTmax());
						
		}else{
					
			initFlag = true;
			
			xmin = Math.pow(10, tminComboBoxInit);
			xmax = Math.pow(10, tmaxComboBoxInit);
			
			ymin = pminInit;
			ymax = pmaxInit;
			
			xtickIntervals = Math.abs(tminComboBoxInit - tmaxComboBoxInit);
		
		} 
	
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
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g){
		
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