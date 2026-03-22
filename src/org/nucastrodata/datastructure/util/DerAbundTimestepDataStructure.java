package org.nucastrodata.datastructure.util;
import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;


import java.util.*;


//This class is the data structure for one timestep of a element synthesis 
//simulation
//It holds the non-zero abundances and the isotope index for each derivative of 
//abundance in separate arrays
//The isotope index matched the isotope mapping for each simulation
/**
 * The Class DerAbundTimestepDataStructure.
 */
public class DerAbundTimestepDataStructure implements DataStructure{
	
	//Declate abundance array
	/** The der abund array. */
	private float[] derAbundArray;
	
	//Declare isotope index array
	/** The index array. */
	private int[] indexArray;
	
	/** The blue array. */
	private short[] redArray, greenArray, blueArray;
	
	//Constructor
	/**
	 * Instantiates a new der abund timestep data structure.
	 */
	public DerAbundTimestepDataStructure(){initialize();}
	
	//Initializes data structure
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setDerAbundArray(null);
		setIndexArray(null);
		setRedArray(null);
		setGreenArray(null);
		setBlueArray(null);
	}
	
	//Get and set methods for abundance array
	/**
	 * Gets the der abund array.
	 *
	 * @return the der abund array
	 */
	public float[] getDerAbundArray(){return derAbundArray;}
	
	/**
	 * Sets the der abund array.
	 *
	 * @param newDerAbundArray the new der abund array
	 */
	public void setDerAbundArray(float[] newDerAbundArray){derAbundArray = newDerAbundArray;}
	
	//Get and set methods for isotope index array
	/**
	 * Gets the index array.
	 *
	 * @return the index array
	 */
	public int[] getIndexArray(){return indexArray;}
	
	/**
	 * Sets the index array.
	 *
	 * @param newIndexArray the new index array
	 */
	public void setIndexArray(int[] newIndexArray){indexArray = newIndexArray;}
	
	/**
	 * Gets the red array.
	 *
	 * @return the red array
	 */
	public short[] getRedArray(){return redArray;}
	
	/**
	 * Sets the red array.
	 *
	 * @param newRedArray the new red array
	 */
	public void setRedArray(short[] newRedArray){redArray = newRedArray;}
	
	/**
	 * Gets the green array.
	 *
	 * @return the green array
	 */
	public short[] getGreenArray(){return greenArray;}
	
	/**
	 * Sets the green array.
	 *
	 * @param newGreenArray the new green array
	 */
	public void setGreenArray(short[] newGreenArray){greenArray = newGreenArray;}
	
	/**
	 * Gets the blue array.
	 *
	 * @return the blue array
	 */
	public short[] getBlueArray(){return blueArray;}
	
	/**
	 * Sets the blue array.
	 *
	 * @param newBlueArray the new blue array
	 */
	public void setBlueArray(short[] newBlueArray){blueArray = newBlueArray;}
		
}