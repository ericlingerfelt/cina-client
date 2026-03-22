package org.nucastrodata.datastructure.util;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;


import java.util.*;


/**
 * The Class MassModelDataStructure.
 */
public class MassModelDataStructure implements DataStructure{

	/** The ZN array. */
	private Point[] ZNArray;
	
	/** The mass array. */
	private double[] massArray;
	
	/** The model name. */
	private String modelName;

	/**
	 * Instantiates a new mass model data structure.
	 */
	public MassModelDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setModelName("");
		setZNArray(null);
		setMassArray(null);
	}
	
	/**
	 * Gets the model name.
	 *
	 * @return the model name
	 */
	public String getModelName(){return modelName;}
	
	/**
	 * Sets the model name.
	 *
	 * @param newModelName the new model name
	 */
	public void setModelName(String newModelName){modelName = newModelName;}
	
	/**
	 * Gets the zN array.
	 *
	 * @return the zN array
	 */
	public Point[] getZNArray(){return ZNArray;}
	
	/**
	 * Sets the zN array.
	 *
	 * @param newZNArray the new zN array
	 */
	public void setZNArray(Point[] newZNArray){ZNArray = newZNArray;}
	
	/**
	 * Gets the mass array.
	 *
	 * @return the mass array
	 */
	public double[] getMassArray(){return massArray;}
	
	/**
	 * Sets the mass array.
	 *
	 * @param newMassArray the new mass array
	 */
	public void setMassArray(double[] newMassArray){massArray = newMassArray;}
}