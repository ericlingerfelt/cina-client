package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.Reaction;


/**
 * The Class Reaction.
 */
public class Reaction implements DataStructure, Comparable<Reaction>{

	/** The point out. */
	private IsotopePoint pointIn, pointOut;
	
	/**
	 * Instantiates a new reaction.
	 */
	public Reaction(){}
	
	/**
	 * Instantiates a new reaction.
	 *
	 * @param pointIn the point in
	 * @param pointOut the point out
	 */
	public Reaction(IsotopePoint pointIn, IsotopePoint pointOut){
		this.pointIn = pointIn;
		this.pointOut = pointOut;
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setPointIn(null);
		setPointOut(null);
	}
	
	/**
	 * Gets the point in.
	 *
	 * @return the point in
	 */
	public IsotopePoint getPointIn(){return pointIn;}
	
	/**
	 * Sets the point in.
	 *
	 * @param pointIn the new point in
	 */
	public void setPointIn(IsotopePoint pointIn){this.pointIn = pointIn;}
	
	/**
	 * Gets the point out.
	 *
	 * @return the point out
	 */
	public IsotopePoint getPointOut(){return pointOut;}
	
	/**
	 * Sets the point out.
	 *
	 * @param pointOut the new point out
	 */
	public void setPointOut(IsotopePoint pointOut){this.pointOut = pointOut;}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Reaction r){
		if(this.getPointIn().compareTo(r.getPointIn())==0){
			return this.getPointOut().compareTo(r.getPointOut());
		}
		return this.getPointIn().compareTo(r.getPointIn());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		if(object instanceof Reaction){
			Reaction r = (Reaction)object;
			if((this.getPointIn().compareTo(r.getPointIn())==0)
					&& this.getPointOut().compareTo(r.getPointOut())==0){
				return true;
			}
			return false;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return this.getPointIn().toString() + " --> " + this.getPointOut().toString();
	}
}
