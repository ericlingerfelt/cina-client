package org.nucastrodata.element.elementviz.edot;

import java.awt.*;
import org.nucastrodata.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.StaticPlotter;

public class ElementVizEdotPlotPanel extends PlotPanel implements MouseMotionListener, MouseListener{

	StaticPlotter cinaStaticPlotter;
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
	int kmax = 2000;
	
	//max number of curves
	/** The imax. */
	int imax = 40;
	
	//int indicating solid line plot
	/** The mode. */
	int[] mode = null;
	
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
	int xdplace = 1;
	
	/** The ydplace. */
	int ydplace = 4;
	
	//number of data points for each curve
	/** The npoints. */
	int[] npoints = null;
	
	//set to NO autoscale to max and min of x and y sets
	/** The doscalex. */
	int doscalex = 0;
	
	/** The doscaley. */
	int doscaley = 0;
	
	//say yes to plot the curve
	/** The doplot. */
	int[] doplot = null;
	
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
	
	/** The edotmin combo box init. */
	int edotminComboBoxInit;
    
    /** The edotmax combo box init. */
    int edotmaxComboBoxInit;

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

	/**
	 * Instantiates a new element viz thermo plot panel.
	 *
	 * @param edotminInit the edotmin init
	 * @param edotmaxInit the edotmax init
	 * @param ds the ds
	 */
	public ElementVizEdotPlotPanel(int edotminInit
								, int edotmaxInit
								, ElementVizDataStructure ds){
		
		this.ds = ds;
		
		setColorArray();
					
		setBackground(Color.white);
		
		edotminComboBoxInit = edotminInit;
		edotmaxComboBoxInit = edotmaxInit;

		setPlotState();

		cinaStaticPlotter = new StaticPlotter();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		square.width = 80;
    	square.height = 80;
		
	}

	public void setPlotState(){
		
		int size = ds.getNucSimDataStructureArray().length;
		
		imax = size;
		kmax = ds.getMaxNumberTimePoints();
		mode = new int[size];			
		doplot = new int[size];			
		curveTitle = new String[size];
		npoints = new int[size];
	
		x = ds.getTimeDataArrayEdot();
		y = ds.getEdotDataArray();
		
		titleString = "Nuclear Energy Generation vs. Time";
		xtitle = "Time (sec)";
		ytitle = "Nuclear Energy Generation (Erg/Gm*Sec)";
		xdplace = 1;
		
		if(initFlag){
		
			for(int i=0; i<size; i++){
				npoints[i] = ds.getEdotDataArray()[i].length;
			}
	
			plotmode = 1;
	
			xmin = Cina.elementVizFrame.elementVizEdotPlotFrame.getTimemin();
			xmax = Cina.elementVizFrame.elementVizEdotPlotFrame.getTimemax();
	
			xtickIntervals = 10;
	
			ymin = Math.pow(10, Cina.elementVizFrame.elementVizEdotPlotFrame.getEdotmin());
			ymax = Math.pow(10, Cina.elementVizFrame.elementVizEdotPlotFrame.getEdotmax());
	
			ydplace = 0;
	
			ytickIntervals = Math.abs((int)Cina.elementVizFrame.elementVizEdotPlotFrame.getEdotmin()
								- (int)Cina.elementVizFrame.elementVizEdotPlotFrame.getEdotmax());
		
			minorX = Cina.elementVizFrame.elementVizEdotPlotFrame.getMinorX();
			majorX = Cina.elementVizFrame.elementVizEdotPlotFrame.getMajorX();			
			minorY = Cina.elementVizFrame.elementVizEdotPlotFrame.getMinorY();
			majorY = Cina.elementVizFrame.elementVizEdotPlotFrame.getMajorY();
	
			showLegend = false;
	
			for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
	
				mode[i] = 1;
	
				curveTitle[i] = Cina.elementVizFrame.elementVizEdotPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].getText();
			
				if(Cina.elementVizFrame.elementVizEdotPlotFrame.elementVizNucSimListPanel.checkBoxArray[i].isSelected()){
				
					doplot[i] = 1;
					
					if(Cina.elementVizFrame.elementVizEdotPlotFrame.legendBox.isSelected()){
					
						showLegend = true;
					
					}
				
				}else{
				
					doplot[i] = 0;
				
				}
		
			}
			
		}else{
		
			titleString = "Nuclear Energy Generation vs. Time";
			
			initFlag = true;

			plotmode = 1;

			xmin = ds.getTimeminEdot();
			xmax = ds.getTimemaxEdot();
			
			xtickIntervals = 10;
			
			ymin = Math.pow(10, ds.getEdotmin());
			ymax = Math.pow(10, ds.getEdotmax());
			
			ydplace = 0;
			
			ytickIntervals = Math.abs((int)ds.getEdotmin() - (int)ds.getEdotmax());

			for(int i=0; i<ds.getNucSimDataStructureArray().length; i++){
			
				if(ds.getNucSimDataStructureArray()[i].getEdotArray().length == 1){
				
					mode[i] = 1;
					doplot[i] = 0;
					curveTitle[i] = "";
					npoints[i] = 0;
				
				}else{
			
					mode[i] = 1;
					doplot[i] = 1;
					curveTitle[i] = "";
					npoints[i] = ds.getEdotDataArray()[i].length;
				
				}
				
			}

			showLegend = false;
		
		}
				
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
        
	}
	
	public void mouseEntered(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = true;
		repaint();
		
	}
	
	public void mouseExited(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = false;
		repaint();
		
	}
	
	public void mousePressed(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = true;	
		
		repaint();
	
	}
	
	public void mouseClicked(MouseEvent me){}
	
	public void mouseReleased(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = false;	
		
		repaint();
		
	}
	
	public void mouseMoved(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
		
		repaint();
	
	}
	
	public void mouseDragged(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		mouseDragging = true;
		
		repaint();
	
	}	
	
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
