package org.nucastrodata.datastructure.util;


import org.nucastrodata.datastructure.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;


/**
 * The Class IsotopePoint.
 */
public class IsotopePoint implements DataStructure, Comparable<IsotopePoint>{
	
	/** The a. */
	private int z, a;
	
	/**
	 * Instantiates a new isotope point.
	 */
	public IsotopePoint(){}
	
	/**
	 * Instantiates a new isotope point.
	 *
	 * @param z the z
	 * @param a the a
	 */
	public IsotopePoint(int z, int a){
		this.z = z;
		this.a = a;
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		this.z = 0;
		this.a = 0;
	}
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ(){return z;}
	
	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z){this.z = z;}
	
	/**
	 * Gets the a.
	 *
	 * @return the a
	 */
	public int getA(){return a;}
	
	/**
	 * Sets the a.
	 *
	 * @param a the new a
	 */
	public void setA(int a){this.a = a;}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(IsotopePoint ip){
		if(this.getZ()!=ip.getZ()){
			return this.getZ()-ip.getZ();
		}
		return this.getA()-ip.getA();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		if(object instanceof IsotopePoint){
			IsotopePoint ids = (IsotopePoint)object;
			if(ids.getZ()==this.getZ() && ids.getA()==this.getA()){
				return true;
			}
			return false;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){return this.z;}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(getZ()==0){
			return MainDataStructure.getElementSymbol(getZ());
		}
		return getA() + MainDataStructure.getElementSymbol(getZ());
	}
}
