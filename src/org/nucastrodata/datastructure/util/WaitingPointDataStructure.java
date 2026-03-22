package org.nucastrodata.datastructure.util;

import java.util.*;

import org.nucastrodata.datastructure.DataStructure;


/**
 * The Class WaitingPointDataStructure.
 */
public class WaitingPointDataStructure implements DataStructure{

	/** The point. */
	private IsotopePoint point;
	
	/** The decision. */
	private Double decision;
	
	/** The flag list. */
	private ArrayList<Double> flagList;
	
	/**
	 * Instantiates a new waiting point data structure.
	 */
	public WaitingPointDataStructure(){
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setPoint(null);
		setDecision(-1.0);
		setFlagList(null);
	}
	
	/**
	 * Gets the point.
	 *
	 * @return the point
	 */
	public IsotopePoint getPoint(){return point;}
	
	/**
	 * Sets the point.
	 *
	 * @param point the new point
	 */
	public void setPoint(IsotopePoint point){this.point = point;}
	
	/**
	 * Gets the decision.
	 *
	 * @return the decision
	 */
	public Double getDecision(){return decision;}
	
	/**
	 * Sets the decision.
	 *
	 * @param decision the new decision
	 */
	public void setDecision(Double decision){this.decision = decision;}
	
	/**
	 * Gets the flag list.
	 *
	 * @return the flag list
	 */
	public ArrayList<Double> getFlagList(){return flagList;}
	
	/**
	 * Sets the flag list.
	 *
	 * @param flagList the new flag list
	 */
	public void setFlagList(ArrayList<Double> flagList){this.flagList = flagList;}
	
}
