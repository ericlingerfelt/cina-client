package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.WaitingPointDataStructure;



/**
 * The Class WaitingDataStructure.
 */
public class WaitingDataStructure implements DataStructure{

	/** The path. */
	private String rateLibrary, path;
	
	/** The time. */
	private Time time;
	
	/** The time map. */
	private HashMap<Time, ArrayList<Double>> timeMap;
	
	/** The range. */
	private Range range;
	
	/** The n. */
	private Point z, n;
	
	/** The normal time. */
	private double abund, effLifetime, ratio, normalTime; 
	
	/** The zone. */
	private int zone;
	
	/** The point map. */
	private HashMap<PointType, TreeMap<IsotopePoint, WaitingPointDataStructure>> pointMap;
	
	/** The maybe color. */
	private Color yesColor, maybeColor;
	
	/**
	 * The Enum PointType.
	 */
	public enum PointType{
/** The YES. */
YES, 
 /** The MAYBE. */
 MAYBE}
	
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
	 * Instantiates a new waiting data structure.
	 */
	public WaitingDataStructure(){
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
		setAbund(1E-6);
		setEffLifetime(0.2);
		setRatio(0.5);
		setPointMap(null);
		setYesColor(Color.red);
		setMaybeColor(Color.green);
	}
	
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
	 * Gets the yes color.
	 *
	 * @return the yes color
	 */
	public Color getYesColor(){return yesColor;} 
	
	/**
	 * Sets the yes color.
	 *
	 * @param yesColor the new yes color
	 */
	public void setYesColor(Color yesColor){this.yesColor = yesColor;}
	
	/**
	 * Gets the maybe color.
	 *
	 * @return the maybe color
	 */
	public Color getMaybeColor(){return maybeColor;} 
	
	/**
	 * Sets the maybe color.
	 *
	 * @param maybeColor the new maybe color
	 */
	public void setMaybeColor(Color maybeColor){this.maybeColor = maybeColor;}
	
	/**
	 * Gets the point map.
	 *
	 * @return the point map
	 */
	public HashMap<PointType, TreeMap<IsotopePoint, WaitingPointDataStructure>> getPointMap(){return pointMap;} 
	
	/**
	 * Sets the point map.
	 *
	 * @param pointMap the point map
	 */
	public void setPointMap(HashMap<PointType, TreeMap<IsotopePoint, WaitingPointDataStructure>> pointMap){this.pointMap = pointMap;}
	
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
	 * Gets the abund.
	 *
	 * @return the abund
	 */
	public double getAbund(){return abund;} 
	
	/**
	 * Sets the abund.
	 *
	 * @param abund the new abund
	 */
	public void setAbund(double abund){this.abund = abund;}
	
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
	 * Gets the eff lifetime.
	 *
	 * @return the eff lifetime
	 */
	public double getEffLifetime(){return effLifetime;} 
	
	/**
	 * Sets the eff lifetime.
	 *
	 * @param effLifetime the new eff lifetime
	 */
	public void setEffLifetime(double effLifetime){this.effLifetime = effLifetime;}
	
	/**
	 * Gets the ratio.
	 *
	 * @return the ratio
	 */
	public double getRatio(){return ratio;} 
	
	/**
	 * Sets the ratio.
	 *
	 * @param ratio the new ratio
	 */
	public void setRatio(double ratio){this.ratio = ratio;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return path + "(" + zone + ")";
	}
	
}
