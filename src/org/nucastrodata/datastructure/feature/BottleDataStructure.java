package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.Reaction;


/**
 * The Class BottleDataStructure.
 */
public class BottleDataStructure implements DataStructure{

	/** The path. */
	private String rateLibrary, path;
	
	/** The time. */
	private Time time;
	
	/** The time map. */
	private HashMap<Time, ArrayList<Double>> timeMap;
	
	/** The range. */
	private Range range;
	
	/** The a. */
	private Point z, n, a;
	
	/** The normal time. */
	private double lowThreshold, highThreshold, sepFactor, normalTime; 
	
	/** The zone. */
	private int zone;
	
	/** The list major. */
	private ArrayList<Reaction> listMajor;
	
	/** The list minor. */
	private ArrayList<Reaction> listMinor;
	
	/** The failed map list. */
	private TreeMap<Integer, ArrayList<Integer>> failedMapList;
	
	/** The color minor. */
	private Color colorMajor, colorMinor;
	
	/**
	 * The Enum Range.
	 */
	public enum Range{
/** The DEFAULT. */
DEFAULT, 
 /** The CUSTOM. */
 CUSTOM}
	
	/**
	 * The Enum Time.
	 */
	public enum Time{

/** The TIM e_ step. */
TIME_STEP, 
 
 /** The TIM e_ sec. */
 TIME_SEC}
	
	/**
	 * Instantiates a new bottle data structure.
	 */
	public BottleDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibrary("");
		setPath("");
		setZone(-1);
		setNormalTime(0.0);
		setTime(Time.TIME_STEP);
		setRange(Range.DEFAULT);
		setTimeMap(null);
		setZ(null);
		setN(null);
		setA(null);
		setLowThreshold(1E-12);
		setHighThreshold(1E-5);
		setSepFactor(1E-4);
		setColorMajor(Color.red);
		setColorMinor(Color.green);
		setListMajor(null);
		setListMinor(null);
		setFailedMapList(null);
	}
	
	/**
	 * Gets the failed map list.
	 *
	 * @return the failed map list
	 */
	public TreeMap<Integer, ArrayList<Integer>> getFailedMapList(){return failedMapList;} 
	
	/**
	 * Sets the failed map list.
	 *
	 * @param failedMapList the failed map list
	 */
	public void setFailedMapList(TreeMap<Integer, ArrayList<Integer>> failedMapList){this.failedMapList = failedMapList;}
	
	/**
	 * Gets the list minor.
	 *
	 * @return the list minor
	 */
	public ArrayList<Reaction> getListMinor(){return listMinor;} 
	
	/**
	 * Sets the list minor.
	 *
	 * @param listMinor the new list minor
	 */
	public void setListMinor(ArrayList<Reaction> listMinor){this.listMinor = listMinor;}
	
	/**
	 * Gets the list major.
	 *
	 * @return the list major
	 */
	public ArrayList<Reaction> getListMajor(){return listMajor;} 
	
	/**
	 * Sets the list major.
	 *
	 * @param listMajor the new list major
	 */
	public void setListMajor(ArrayList<Reaction> listMajor){this.listMajor = listMajor;}
	
	/**
	 * Gets the zone.
	 *
	 * @return the zone
	 */
	public int getZone(){return zone;}
	
	/**
	 * Sets the zone.
	 *
	 * @param newZone the new zone
	 */
	public void setZone(int newZone){zone = newZone;}
	
	/**
	 * Gets the color major.
	 *
	 * @return the color major
	 */
	public Color getColorMajor(){return colorMajor;} 
	
	/**
	 * Sets the color major.
	 *
	 * @param colorMajor the new color major
	 */
	public void setColorMajor(Color colorMajor){this.colorMajor = colorMajor;}
	
	/**
	 * Gets the color minor.
	 *
	 * @return the color minor
	 */
	public Color getColorMinor(){return colorMinor;} 
	
	/**
	 * Sets the color minor.
	 *
	 * @param colorMinor the new color minor
	 */
	public void setColorMinor(Color colorMinor){this.colorMinor = colorMinor;}
	
	/**
	 * Gets the rate library.
	 *
	 * @return the rate library
	 */
	public String getRateLibrary(){return rateLibrary;} 
	
	/**
	 * Sets the rate library.
	 *
	 * @param rateLibrary the new rate library
	 */
	public void setRateLibrary(String rateLibrary){this.rateLibrary = rateLibrary;}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath(){return path;} 
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path){this.path = path;}
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Time getTime(){return time;} 
	
	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(Time time){this.time = time;}
	
	/**
	 * Gets the range.
	 *
	 * @return the range
	 */
	public Range getRange(){return range;} 
	
	/**
	 * Sets the range.
	 *
	 * @param range the new range
	 */
	public void setRange(Range range){this.range = range;}
	
	/**
	 * Gets the time map.
	 *
	 * @return the time map
	 */
	public HashMap<Time, ArrayList<Double>> getTimeMap(){return timeMap;} 
	
	/**
	 * Sets the time map.
	 *
	 * @param timeMap the time map
	 */
	public void setTimeMap(HashMap<Time, ArrayList<Double>> timeMap){this.timeMap = timeMap;}
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public Point getZ(){return z;} 
	
	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(Point z){this.z = z;}
	
	/**
	 * Gets the n.
	 *
	 * @return the n
	 */
	public Point getN(){return n;} 
	
	/**
	 * Sets the n.
	 *
	 * @param n the new n
	 */
	public void setN(Point n){this.n = n;}
	
	/**
	 * Gets the a.
	 *
	 * @return the a
	 */
	public Point getA(){return a;} 
	
	/**
	 * Sets the a.
	 *
	 * @param a the new a
	 */
	public void setA(Point a){this.a = a;}
	
	/**
	 * Gets the low threshold.
	 *
	 * @return the low threshold
	 */
	public double getLowThreshold(){return lowThreshold;} 
	
	/**
	 * Sets the low threshold.
	 *
	 * @param lowThreshold the new low threshold
	 */
	public void setLowThreshold(double lowThreshold){this.lowThreshold = lowThreshold;}
	
	/**
	 * Gets the high threshold.
	 *
	 * @return the high threshold
	 */
	public double getHighThreshold(){return highThreshold;} 
	
	/**
	 * Sets the high threshold.
	 *
	 * @param highThreshold the new high threshold
	 */
	public void setHighThreshold(double highThreshold){this.highThreshold = highThreshold;}
	
	/**
	 * Gets the normal time.
	 *
	 * @return the normal time
	 */
	public double getNormalTime(){return normalTime;} 
	
	/**
	 * Sets the normal time.
	 *
	 * @param normalTime the new normal time
	 */
	public void setNormalTime(double normalTime){this.normalTime = normalTime;}
	
	/**
	 * Gets the sep factor.
	 *
	 * @return the sep factor
	 */
	public double getSepFactor(){return sepFactor;} 
	
	/**
	 * Sets the sep factor.
	 *
	 * @param sepFactor the new sep factor
	 */
	public void setSepFactor(double sepFactor){this.sepFactor = sepFactor;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return path + "(" + zone + ")";
	}
	
}

