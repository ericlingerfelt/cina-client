package org.nucastrodata.datastructure.util;

import java.util.*;

import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.ReactionDataStructure;


/**
 * The Class RateLibDataStructure.
 */
public class RateLibDataStructure{
	
	/** The reaction map. */
	private TreeMap<IsotopePoint, ArrayList<ReactionDataStructure>> reactionMap;
	
	/** The isotope map full. */
	private TreeMap<Integer, ArrayList<IsotopePoint>> isotopeMapFull;
	
	/** The isotope map available. */
	private TreeMap<Integer, ArrayList<IsotopePoint>> isotopeMapAvailable;
	
	/**
	 * Instantiates a new rate lib data structure.
	 */
	public RateLibDataStructure(){initialize();}
	
	/**
	 * Initialize.
	 */
	public void initialize(){
		setReactionMap(null);
		setIsotopeMapFull(new TreeMap<Integer, ArrayList<IsotopePoint>>());
		setIsotopeMapAvailable(null);
		
	}

	/**
	 * Gets the reaction map.
	 *
	 * @return the reaction map
	 */
	public TreeMap<IsotopePoint, ArrayList<ReactionDataStructure>> getReactionMap(){return reactionMap;}
	
	/**
	 * Sets the reaction map.
	 *
	 * @param reactionMap the reaction map
	 */
	public void setReactionMap(TreeMap<IsotopePoint, ArrayList<ReactionDataStructure>> reactionMap){this.reactionMap = reactionMap;}
	
	/**
	 * Gets the isotope map full.
	 *
	 * @return the isotope map full
	 */
	public TreeMap<Integer, ArrayList<IsotopePoint>> getIsotopeMapFull(){return isotopeMapFull;}
	
	/**
	 * Sets the isotope map full.
	 *
	 * @param isotopeMapFull the isotope map full
	 */
	public void setIsotopeMapFull(TreeMap<Integer, ArrayList<IsotopePoint>> isotopeMapFull){this.isotopeMapFull = isotopeMapFull;}
	
	/**
	 * Gets the isotope map available.
	 *
	 * @return the isotope map available
	 */
	public TreeMap<Integer, ArrayList<IsotopePoint>> getIsotopeMapAvailable(){return isotopeMapAvailable;}
	
	/**
	 * Sets the isotope map available.
	 *
	 * @param isotopeMapAvailable the isotope map available
	 */
	public void setIsotopeMapAvailable(TreeMap<Integer, ArrayList<IsotopePoint>> isotopeMapAvailable){this.isotopeMapAvailable = isotopeMapAvailable;}
	
}
