package org.nucastrodata.datastructure.util;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;


import java.util.*;


//This class is the data structure for one timestep of a element synthesis 
//simulation
//It holds the non-zero abundances and the isotope index for each abundance 
//in separate arrays
//The isotope index matched the isotope mapping for each simulation
/**
 * The Class AbundTimestepDataStructure.
 */
public class AbundTimestepDataStructure implements DataStructure{
	
	//Declate abundance array
	/** The abund array. */
	private float[] abundArray;
	
	//Declare isotope index array
	/** The index array. */
	private int[] indexArray;
	
	/** The blue array. */
	private short[] redArray, greenArray, blueArray;
	
	//Constructor
	/**
	 * Instantiates a new abund timestep data structure.
	 */
	public AbundTimestepDataStructure(){initialize();}
	
	//Initializes data structure
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setAbundArray(null);
		setIndexArray(null);
		setRedArray(null);
		setGreenArray(null);
		setBlueArray(null);
	}
	
	/**
	 * Gets the abund from isotope index.
	 *
	 * @param isotopeIndex the isotope index
	 * @return the abund from isotope index
	 */
	public double getAbundFromIsotopeIndex(int isotopeIndex){
		for(int i=0; i<indexArray.length; i++){
			if(indexArray[i]==isotopeIndex){
				return abundArray[i];
			}
		}
		return 0.0;
	}
	
	//Get and set methods for abundance array
	/**
	 * Gets the abund array.
	 *
	 * @return the abund array
	 */
	public float[] getAbundArray(){return abundArray;}
	
	/**
	 * Sets the abund array.
	 *
	 * @param newAbundArray the new abund array
	 */
	public void setAbundArray(float[] newAbundArray){abundArray = newAbundArray;}
	
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