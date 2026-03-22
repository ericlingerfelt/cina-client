package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;


/**
 * The Class FluxTimestepDataStructure.
 */
public class FluxTimestepDataStructure implements DataStructure{
	
	/** The flux array. */
	private float[] fluxArray;
	
	/** The index array. */
	private int[] indexArray;
	
	/** The blue array. */
	private short[] redArray, greenArray, blueArray;
	
	/** The linewidth array. */
	private byte[] linestyleArray, linewidthArray;
		
	//Constructor
	/**
	 * Instantiates a new flux timestep data structure.
	 */
	public FluxTimestepDataStructure(){initialize();}
	
	//Initializes data structure
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setFluxArray(null);
		setIndexArray(null);
		setLinestyleArray(null);
		setLinewidthArray(null);
		setRedArray(null);
		setGreenArray(null);
		setBlueArray(null);
	}
	
	//Get and set methods for abundance array
	/**
	 * Gets the flux array.
	 *
	 * @return the flux array
	 */
	public float[] getFluxArray(){return fluxArray;}
	
	/**
	 * Sets the flux array.
	 *
	 * @param newFluxArray the new flux array
	 */
	public void setFluxArray(float[] newFluxArray){fluxArray = newFluxArray;}
	
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
	
	/**
	 * Gets the linestyle array.
	 *
	 * @return the linestyle array
	 */
	public byte[] getLinestyleArray(){return linestyleArray;}
	
	/**
	 * Sets the linestyle array.
	 *
	 * @param newLinestyleArray the new linestyle array
	 */
	public void setLinestyleArray(byte[] newLinestyleArray){linestyleArray = newLinestyleArray;}
	
	/**
	 * Gets the linewidth array.
	 *
	 * @return the linewidth array
	 */
	public byte[] getLinewidthArray(){return linewidthArray;}
	
	/**
	 * Sets the linewidth array.
	 *
	 * @param newLinewidthArray the new linewidth array
	 */
	public void setLinewidthArray(byte[] newLinewidthArray){linewidthArray = newLinewidthArray;}
	
}