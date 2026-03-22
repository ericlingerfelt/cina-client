package org.nucastrodata.data.datamass;

import java.awt.*;

import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.StaticPlotter;


/**
 * The Class DataMassPlotPanel.
 */
public class DataMassPlotPanel extends PlotPanel implements MouseMotionListener, MouseListener{

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
	int x2 = 700;
	
	/** The y2. */
	int y2 = 504;
	
	//max number of points per curve
	/** The kmax. */
	int kmax = 2000;
	
	//max number of curves
	/** The imax. */
	int imax = 40;
	
	//int indicating solid line plot
	/** The mode. */
	int[] mode = {1};
	
	//dotsize (not used for solid line plot but required parameter)
	/** The dot size. */
	int dotSize = 3;
	
	//offset for legend
	/** The xlegoff. */
	int xlegoff = 80;
	
	/** The ylegoff. */
	int ylegoff = 40;
	
	//number of decimal places for numbers on x and y axis
	/** The xdplace. */
	int xdplace = 0;
	
	/** The ydplace. */
	int ydplace = 4;
	
	//number of data points for each curve
	/** The npoints. */
	int[] npoints = {1};
	
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
	Color[] lcolor = new Color[1];
	
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
	String[] curveTitle = null;
	
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
	
	/** The init flag. */
	boolean initFlag = false;
	
	/** The min spinner init. */
	int minSpinnerInit;
    
    /** The max spinner init. */
    int maxSpinnerInit;

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
	private DataMassDataStructure ds;

	/**
	 * Instantiates a new data mass plot panel.
	 *
	 * @param ds the ds
	 */
	public DataMassPlotPanel(DataMassDataStructure ds){
		
		this.ds = ds;

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
		
		String type = "";
		
		if(initFlag){
		
			type = Cina.dataMassFrame.dataMassPlotFrame.type;
		
		}else{
		
			type = "Diff";
		
		}
		
		if(type.equals("RMS")){
		
			titleString = "RMS Difference of "
						+ ds.getTheoryModelDataStructure().getModelName()
						+ " and "
						+ ds.getExpModelDataStructure().getModelName();
			
			if(!initFlag){
	
				xtickIntervals = 10;
				ytickIntervals = 10;
				
				ydplace = 3;
				xdplace = 0;
				
				lcolor[0] = Color.black;
				
				showLegend = false;
				
				plotmode = 0;
				
				int size = 1;
				
				imax = size;
				mode = new int[size];					
				doplot = new int[size];					
				curveTitle = new String[size];
				npoints = new int[size];
				
				kmax = ds.getZArrayRMS().length;
				
				x = new double[imax][kmax];
				y = new double[imax][kmax];
				
				npoints[0] = kmax;
				curveTitle[0] = "";
				
				dotSize = 1;
				doplot[0] = 1;
	
				initFlag = true;
				mode[0] = 2;
				xtitle = "Z (proton number)";
				ytitle = "RMS Value (MeV)";
			
				//xmin = ds.getZminRMS();
				xmin = 0.0;
				xmax = ds.getZmaxRMS();
	
				//ymin = ds.getRMSZmin();
				ymin = 0.0;
				ymax = ds.getRMSZmax();
				
				x[0] = ds.getZArrayRMS();
				y[0] = ds.getRMSZArray();
	
			}else{
	
				x = new double[imax][kmax];
				y = new double[imax][kmax];
	
				xtickIntervals = 10;
				ytickIntervals = 10;
				
				ydplace = 3;
				xdplace = 0;
				
				lcolor[0] = Color.black;
				
				showLegend = false;
				
				plotmode = 0;
				
				int size = 1;
				
				imax = size;
				mode = new int[size];					
				doplot = new int[size];					
				curveTitle = new String[size];
				npoints = new int[size];
				
				curveTitle[0] = "";
				
				dotSize = 1;
				doplot[0] = 1;
	
				minorX = Cina.dataMassFrame.dataMassPlotFrame.getMinorX();
				majorX = Cina.dataMassFrame.dataMassPlotFrame.getMajorX();			
				minorY = Cina.dataMassFrame.dataMassPlotFrame.getMinorY();
				majorY = Cina.dataMassFrame.dataMassPlotFrame.getMajorY();
				
				xmin = Cina.dataMassFrame.dataMassPlotFrame.getXmin();
				xmax = Cina.dataMassFrame.dataMassPlotFrame.getXmax();
				
				ymin = Cina.dataMassFrame.dataMassPlotFrame.getYmin();
				ymax = Cina.dataMassFrame.dataMassPlotFrame.getYmax();
				
				if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
					
					xtitle = "Z (proton number)";
					x[0] = ds.getZArrayRMS();
					y[0] = ds.getRMSZArray();
					kmax = ds.getZArrayRMS().length;
				
				}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				
					xtitle = "N (neutron number)";
					x[0] = ds.getNArrayRMS();
					y[0] = ds.getRMSNArray();
					kmax = ds.getNArrayRMS().length;
				
				}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				
					xtitle = "A = Z + N";
					x[0] = ds.getAArrayRMS();
					y[0] = ds.getRMSAArray();
					kmax = ds.getAArrayRMS().length;
				
				}
				
				ytitle = "RMS Value (MeV)";
				mode[0] = 2;
				
				npoints[0] = kmax;
	
			}
			
		}else if(type.equals("Diff")){
		
			titleString = "Mass Differences of "
					+ ds.getTheoryModelDataStructure().getModelName()
					+ " and "
					+ ds.getExpModelDataStructure().getModelName();

			xtickIntervals = 10;
			ytickIntervals = 10;
			
			ydplace = 3;
			xdplace = 0;
			
			lcolor[0] = Color.black;
			
			showLegend = false;
			
			plotmode = 0;
			
			int size = 1;
			
			imax = size;
			mode = new int[size];					
			doplot = new int[size];					
			curveTitle = new String[size];
			npoints = new int[size];
			
			kmax = ds.getZArray().length;
			
			x = new double[imax][kmax];
			y = new double[imax][kmax];
			
			npoints[0] = kmax;
			curveTitle[0] = "";
			
			dotSize = 1;
			doplot[0] = 1;
			
			if(!initFlag){
	
				initFlag = true;
				mode[0] = 5;
				xtitle = "Z (proton number)";
				ytitle = "Mass Difference (MeV)";
			
				xmin = ds.getZmin();
				xmax = ds.getZmax();
	
				ymin = ds.getDiffmin();
				ymax = ds.getDiffmax();
				
				x[0] = ds.getZArray();
				y[0] = ds.getDiffArray();
	
			}else{
	
				minorX = Cina.dataMassFrame.dataMassPlotFrame.getMinorX();
				majorX = Cina.dataMassFrame.dataMassPlotFrame.getMajorX();			
				minorY = Cina.dataMassFrame.dataMassPlotFrame.getMinorY();
				majorY = Cina.dataMassFrame.dataMassPlotFrame.getMajorY();
				
				xmin = Cina.dataMassFrame.dataMassPlotFrame.getXmin();
				xmax = Cina.dataMassFrame.dataMassPlotFrame.getXmax();
				
				ymin = Cina.dataMassFrame.dataMassPlotFrame.getYmin();
				ymax = Cina.dataMassFrame.dataMassPlotFrame.getYmax();
				
				if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
					
					xtitle = "Z (proton number)";
					x[0] = ds.getZArray();
	
				}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				
					xtitle = "N (neutron number)";
					x[0] = ds.getNArray();
					
				}else if(Cina.dataMassFrame.dataMassPlotFrame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				
					xtitle = "A = Z + N";
					x[0] = ds.getAArray();
					
				}
	
				ytitle = "Mass Difference (MeV)";
				mode[0] = 5;
				y[0] = ds.getDiffArray();
	
			}
		
		}
		
		repaint();
			
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
                  xoffset, yoffset, 20, rightmarg, g); 
       	             
	}
  
}  
