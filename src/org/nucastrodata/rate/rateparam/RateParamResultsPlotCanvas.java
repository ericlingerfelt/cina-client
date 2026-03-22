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
 * The Class RateParamResultsPlotCanvas.
 */
public class RateParamResultsPlotCanvas extends JPanel implements MouseMotionListener, MouseListener{
	
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
	int kmax = 1000;
	
	//max number of curves
	/** The imax. */
	int imax = 3;
	
	//int indicating solid line plot
	/** The mode. */
	int[] mode = {1, 3, 3};
	
	//dotsize (not used for solid line plot but required parameter)
	/** The dot size. */
	int dotSize = 3;
	
	//offset for legend
	/** The xlegoff. */
	int xlegoff = 100;
	
	/** The ylegoff. */
	int ylegoff = 50;
	
	//number of decimal places for numbers on x and y axis
	/** The xdplace. */
	int xdplace = 0;
	
	/** The ydplace. */
	int ydplace = 0;
	
	//number of data points for each curve
	/** The npoints. */
	int[] npoints = new int[3];
	
	//set to NO autoscale to max and min of x and y sets
	/** The doscalex. */
	int doscalex = 0;
	
	/** The doscaley. */
	int doscaley = 0;
	
	//say yes to plot the curve
	/** The doplot. */
	int[] doplot = {1, 1, 1};
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	/** The xmin. */
	double xmin = 1e-2;
	
	/** The xmax. */
	double xmax = 1e1;
		
	/** The ymin. */
	double ymin = 1e-9;
	
	/** The ymax. */
	double ymax = 1e8;
	
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
	Color[] lcolor = {Color.BLACK, Color.red, Color.green};
	
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
	String[] curveTitle = {"Fit Region", "Low Temp Extrapolation", "High Temp Extrapolation"};
	
	//set style of log plot (show number or log of number on each axis)
	/** The log style. */
	int logStyle = 1;
	
	//number of intervals between x and y tick marks
	/** The ytick intervals. */
	int ytickIntervals = 1;
	
	/** The xtick intervals. */
	int xtickIntervals = 1;
	
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
    boolean minorX = true;
    
    /** The major y. */
    boolean majorY = true;
    
    /** The minor y. */
    boolean minorY = false;
    
    //Show title and subtitle
    /** The title. */
    boolean title = false;
    
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

    /** The tmin spinner init. */
    int tminSpinnerInit;
    
    /** The tmax spinner init. */
    int tmaxSpinnerInit;
    
    /** The rmin spinner init. */
    int rminSpinnerInit;
    
    /** The rmax spinner init. */
    int rmaxSpinnerInit;
	
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
	 * Instantiates a new rate param results plot canvas.
	 *
	 * @param rminInit the rmin init
	 * @param rmaxInit the rmax init
	 * @param tminInit the tmin init
	 * @param tmaxInit the tmax init
	 * @param ds the ds
	 */
	public RateParamResultsPlotCanvas(int rminInit
											, int rmaxInit
											, int tminInit
											, int tmaxInit
											, RateParamDataStructure ds){
		
		this.ds = ds;
		
		rminSpinnerInit = rminInit;
		rmaxSpinnerInit = rmaxInit;
		tminSpinnerInit = tminInit;
		tmaxSpinnerInit = tmaxInit;
		
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
		
		titleString = ds.getRateDataStructure().getReactionString();
		ytitle = "Rate" + " (" + ds.getRateUnits() + ")";
	
		if(!ds.getRateDataStructure().getDecay().equals("")){
		
			titleString += " ["
							+ ds.getRateDataStructure().getDecay()
							+ "]";
		
		}
		
		if(ds.getExtraPoints()){
		
			imax = 5;
			npoints = new int[imax];
			
			mode = new int[]{1, 10, 10, 3, 3};
			lcolor = new Color[]{Color.magenta, Color.black, Color.red, Color.blue, Color.cyan};
			curveTitle = new String[]{"Fit", "Numerical Integration", "Added Points", "Low Temp Extrapolation", "High Temp Extrapolation"};
			
			kmax = ds.getTempParamDataArray().length;
			npoints[0] = kmax;
			
			kmax = ds.getRateDataArray().length;
			npoints[1] = kmax;
			
			kmax = ds.getRateDataArrayExtra().length;
			npoints[2] = kmax;
			
			kmax = ds.getLowRateDataArray().length;
			npoints[3] = kmax;
			
			kmax = ds.getHighRateDataArray().length;
			npoints[4] = kmax;
			
			kmax = (int)Math.max(ds.getTempDataArray().length, ds.getRateDataArray().length);
			kmax = (int)Math.max(ds.getTempDataArrayExtra().length, kmax);
			kmax = (int)Math.max(ds.getLowRateDataArray().length, kmax);
			kmax = (int)Math.max(ds.getHighRateDataArray().length, kmax);
			
			x = new double[imax][kmax];
			y = new double[imax][kmax];
			
			x[0] = ds.getTempParamDataArray();
			y[0] = getNonzeroArray(ds.getRateParamDataArray());
		
			x[1] = ds.getTempDataArray();
			y[1] = ds.getRateDataArray();
		
			x[2] = ds.getTempDataArrayExtra();
			y[2] = ds.getRateDataArrayExtra();
		
			x[3] = ds.getLowTempDataArray();
			y[3] = ds.getLowRateDataArray();
			
			x[4] = ds.getHighTempDataArray();
			y[4] = ds.getHighRateDataArray();
		
		}else{
		
			imax = 4;
			
			npoints = new int[imax];
			
			mode = new int[]{1, 10, 3, 3};
			lcolor = new Color[]{Color.magenta, Color.black, Color.blue, Color.cyan};
			curveTitle = new String[]{"Fit", "Numerical Integration", "Low Temp Extrapolation", "High Temp Extrapolation"};
			
			kmax = ds.getTempParamDataArray().length;
			npoints[0] = kmax;
			
			kmax = ds.getRateDataArray().length;
			npoints[1] = kmax;
			
			kmax = ds.getLowRateDataArray().length;
			npoints[2] = kmax;
			
			kmax = ds.getHighRateDataArray().length;
			npoints[3] = kmax;
			
			kmax = (int)Math.max(ds.getTempDataArray().length, ds.getRateDataArray().length);
			kmax = (int)Math.max(ds.getLowRateDataArray().length, kmax);
			kmax = (int)Math.max(ds.getHighRateDataArray().length, kmax);
			
			x = new double[imax][kmax];
			y = new double[imax][kmax];
			
			x[0] = ds.getTempParamDataArray();
			y[0] = getNonzeroArray(ds.getRateParamDataArray());
		
			x[1] = ds.getTempDataArray();
			y[1] = ds.getRateDataArray();
		
			x[2] = ds.getLowTempDataArray();
			y[2] = ds.getLowRateDataArray();
			
			x[3] = ds.getHighTempDataArray();
			y[3] = ds.getHighRateDataArray();
		
		
		}
	
		if(initFlag){
		
			xmin = Math.pow(10, Cina.rateParamFrame.rateParamResultsPlotFrame.getTmin());
			xmax = Math.pow(10, Cina.rateParamFrame.rateParamResultsPlotFrame.getTmax());
			ymin = Math.pow(10, Cina.rateParamFrame.rateParamResultsPlotFrame.getRmin());
			ymax = Math.pow(10, Cina.rateParamFrame.rateParamResultsPlotFrame.getRmax());
			
			minorX = Cina.rateParamFrame.rateParamResultsPlotFrame.getMinorT();
			majorX = Cina.rateParamFrame.rateParamResultsPlotFrame.getMajorT();			
			minorY = Cina.rateParamFrame.rateParamResultsPlotFrame.getMinorR();
			majorY = Cina.rateParamFrame.rateParamResultsPlotFrame.getMajorR();
			
			xtickIntervals = Math.abs((int)Cina.rateParamFrame.rateParamResultsPlotFrame.getTmin()
									- (int)Cina.rateParamFrame.rateParamResultsPlotFrame.getTmax());
		
			ytickIntervals = Math.abs((int)Cina.rateParamFrame.rateParamResultsPlotFrame.getRmin()
									- (int)Cina.rateParamFrame.rateParamResultsPlotFrame.getRmax());
		
			
			doplot = new int[x.length];
			
			for(int i=0; i<x.length; i++){
				if(Cina.rateParamFrame.rateParamResultsPlotFrame.rateParamResultsListPanel.isBoxSelected(i)){
					doplot[i] = 1;
				}else{
					doplot[i] = 0;
				}
			}
			
			showLegend = false;
			for(int i=0; i<x.length; i++){
				if(Cina.rateParamFrame.rateParamResultsPlotFrame.rateParamResultsListPanel.isBoxSelected(i)){
					showLegend = true;
				}
			}
			
		}else{		
			
			initFlag = true;
			showLegend = true;
			
			xmin = Math.pow(10, tminSpinnerInit);
			xmax = Math.pow(10, tmaxSpinnerInit);
			ymin = Math.pow(10, rminSpinnerInit);
			ymax = Math.pow(10, rmaxSpinnerInit);
			
			xtickIntervals = Math.abs(tminSpinnerInit - tmaxSpinnerInit);
			ytickIntervals = Math.abs(rminSpinnerInit - rmaxSpinnerInit);
			
			doplot = new int[x.length];
			
			for(int i=0; i<x.length; i++){
				
				if(ds.getExtraPoints()){
					
					if(!ds.getLowTemp() && i==3){
						doplot[i] = 0;
					}else if(!ds.getHighTemp() && i==4){
						doplot[i] = 0;
					}else{
						doplot[i] = 1;
					}
					
				}else{
				
					if(!ds.getLowTemp() && i==2){
						doplot[i] = 0;
					}else if(!ds.getHighTemp() && i==3){
						doplot[i] = 0;
					}else{
						doplot[i] = 1;
					}
				
				}
				
			}
			
		} 
		
		repaint();
	
	}
	
	/**
	 * Gets the nonzero array.
	 *
	 * @param array the array
	 * @return the nonzero array
	 */
	private double[] getNonzeroArray(double[] array){
		double[] outputArray = new double[array.length];
		for(int i=0; i<array.length; i++){
			if(array[i]<=0.0){
				outputArray[i] = 1E-100;
			}else{
				outputArray[i] = array[i];
			}
		}
		return outputArray;
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
                  xoffset, yoffset, topmarg, rightmarg, g);
                  
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
                  xoffset, yoffset, topmarg, rightmarg, g);
                  
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