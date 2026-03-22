package org.nucastrodata.datastructure.feature;

import java.util.*;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;

public class FluxDataStructure implements DataStructure{

	/** The path. */
	private String rateLibrary, path, reactions;
	
	/** The time. */
	private Time time;
	
	/** The time map. */
	private HashMap<Time, ArrayList<Double>> timeMap;
	
	/** The normal time. */
	private double normalTime; 
	
	private NucSimDataStructure sim;
	
	/**
	 * The Enum Time.
	 */
	public enum Time{
TIME_STEP, 
TIME_SEC}
	
	/**
	 * Instantiates a new bottle data structure.
	 */
	public FluxDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setRateLibrary("");
		setPath("");
		setReactions("");
		setNormalTime(0.0);
		setTime(Time.TIME_STEP);
		setTimeMap(null);
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

	public String getReactions(){return reactions;} 
	public void setReactions(String reactions){this.reactions = reactions;}
	
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


