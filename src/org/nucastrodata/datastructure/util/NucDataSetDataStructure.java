package org.nucastrodata.datastructure.util;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;

import org.nucastrodata.datastructure.util.NucDataDataStructure;


/**
 * The Class NucDataSetDataStructure.
 */
public class NucDataSetDataStructure implements DataStructure{

	/** The creation date. */
	private String nucDataSetName, nucDataSetNotes, creationDate;
	
	/** The isotope list. */
	private int[][] isotopeList;
	
	/** The Z list. */
	private int[] ZList;
	
	/** The nuc data data structures. */
	private NucDataDataStructure[] nucDataDataStructures;

	//Constructor
	/**
	 * Instantiates a new nuc data set data structure.
	 */
	public NucDataSetDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setNucDataSetName("");
		setNucDataSetNotes("");
		setCreationDate("");
		setZList(null);
		setIsotopeList(null);
		setNucDataDataStructures(null);
	}
	
	/**
	 * Gets the nuc data set name.
	 *
	 * @return the nuc data set name
	 */
	public String getNucDataSetName(){return nucDataSetName;}
	
	/**
	 * Sets the nuc data set name.
	 *
	 * @param newNucDataSetName the new nuc data set name
	 */
	public void setNucDataSetName(String newNucDataSetName){nucDataSetName = newNucDataSetName;}
	
	/**
	 * Gets the nuc data set notes.
	 *
	 * @return the nuc data set notes
	 */
	public String getNucDataSetNotes(){return nucDataSetNotes;}
	
	/**
	 * Sets the nuc data set notes.
	 *
	 * @param newNucDataSetNotes the new nuc data set notes
	 */
	public void setNucDataSetNotes(String newNucDataSetNotes){nucDataSetNotes = newNucDataSetNotes;}
	
	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public String getCreationDate(){return creationDate;}
	
	/**
	 * Sets the creation date.
	 *
	 * @param newCreationDate the new creation date
	 */
	public void setCreationDate(String newCreationDate){creationDate = newCreationDate;}
	
	/**
	 * Gets the z list.
	 *
	 * @return the z list
	 */
	public int[] getZList(){return ZList;}
	
	/**
	 * Sets the z list.
	 *
	 * @param newZList the new z list
	 */
	public void setZList(int[] newZList){ZList = newZList;}
	
	/**
	 * Gets the isotope list.
	 *
	 * @return the isotope list
	 */
	public int[][] getIsotopeList(){return isotopeList;}
	
	/**
	 * Sets the isotope list.
	 *
	 * @param newIsotopeList the new isotope list
	 */
	public void setIsotopeList(int[][] newIsotopeList){isotopeList = newIsotopeList;}
	
	/**
	 * Gets the nuc data data structures.
	 *
	 * @return the nuc data data structures
	 */
	public NucDataDataStructure[] getNucDataDataStructures(){return nucDataDataStructures;}
	
	/**
	 * Sets the nuc data data structures.
	 *
	 * @param newNucDataDataStructures the new nuc data data structures
	 */
	public void setNucDataDataStructures(NucDataDataStructure[] newNucDataDataStructures){nucDataDataStructures = newNucDataDataStructures;}
	
}