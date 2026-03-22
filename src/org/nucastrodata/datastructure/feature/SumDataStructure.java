package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;

public class SumDataStructure implements DataStructure{

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
	private double normalTime, sum; 
	
	private NucSimDataStructure sim;
	
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
	public SumDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibrary("");
		setPath("");
		setNormalTime(0.0);
		setTime(Time.TIME_STEP);
		setRange(Range.DEFAULT);
		setTimeMap(null);
		setZ(null);
		setN(null);
		setA(null);
		setSum(0.0);
		setSim(null);
	}
	
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
	
	public double getSum(){return sum;} 
	public void setSum(double sum){this.sum = sum;}
	
	public NucSimDataStructure getSim(){return sim;} 
	public void setSim(NucSimDataStructure sim){this.sim = sim;}
	
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
	
}

