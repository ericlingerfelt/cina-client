package org.nucastrodata.datastructure.util;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;

import org.nucastrodata.datastructure.util.RateDataStructure;


//This class is the data structure for a reaction rate library
/**
 * The Class LibraryDataStructure.
 */
public class LibraryDataStructure implements DataStructure{

	//Declare strings for library name, notes, creation date, and recipe 
	/** The library recipe. */
	private String libType, libName, libNotes, creationDate, libraryRecipe, libDir;
	
	//Declare array to hold the isotopes
	//[Z][index: number of A's for that Z] = A
	/** The isotope list. */
	private int[][] isotopeList;
	
	//Declare array to hold the Z's for all elements
	/** The Z list. */
	private int[] ZList;
	
	//Declare an array of rate data structures for this library
	/** The rate data structures. */
	private RateDataStructure[] rateDataStructures;

	//Declare a boolean to indicate wether this library has all of its inverses
	/** The all inverses present. */
	private boolean allInversesPresent;
	
	private File zipFile;

	//Constructor
	/**
	 * Instantiates a new library data structure.
	 */
	public LibraryDataStructure(){initialize();}
	
	//Method to initialize variables
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setLibType("");
		setLibName("");
		setLibNotes("");
		setCreationDate("");
		setLibraryRecipe("");
		setZList(null);
		setIsotopeList(null);
		setRateDataStructures(null);
		setAllInversesPresent(false);
		setZipFile(null);
		setLibDir("");
	}

	public String getLibDir(){return libDir;}
	public void setLibDir(String newLibDir){libDir = newLibDir;}
	
	public File getZipFile(){return zipFile;}
	public void setZipFile(File newZipFile){zipFile = newZipFile;}

	//Set and Get methods for data strcuture variables
	/**
	 * Gets the lib type.
	 *
	 * @return the lib type
	 */
	public String getLibType(){return libType;}
	
	/**
	 * Sets the lib type.
	 *
	 * @param newLibType the new lib type
	 */
	public void setLibType(String newLibType){libType = newLibType;}
	
	/**
	 * Gets the lib name.
	 *
	 * @return the lib name
	 */
	public String getLibName(){return libName;}
	
	/**
	 * Sets the lib name.
	 *
	 * @param newLibName the new lib name
	 */
	public void setLibName(String newLibName){libName = newLibName;}

	/**
	 * Gets the lib notes.
	 *
	 * @return the lib notes
	 */
	public String getLibNotes(){return libNotes;}
	
	/**
	 * Sets the lib notes.
	 *
	 * @param newLibNotes the new lib notes
	 */
	public void setLibNotes(String newLibNotes){libNotes = newLibNotes;}
	
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
	 * Gets the library recipe.
	 *
	 * @return the library recipe
	 */
	public String getLibraryRecipe(){return libraryRecipe;}
	
	/**
	 * Sets the library recipe.
	 *
	 * @param newLibraryRecipe the new library recipe
	 */
	public void setLibraryRecipe(String newLibraryRecipe){libraryRecipe = newLibraryRecipe;}

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
	 * Gets the rate data structures.
	 *
	 * @return the rate data structures
	 */
	public RateDataStructure[] getRateDataStructures(){return rateDataStructures;}
	
	/**
	 * Sets the rate data structures.
	 *
	 * @param newRateDataStructures the new rate data structures
	 */
	public void setRateDataStructures(RateDataStructure[] newRateDataStructures){rateDataStructures = newRateDataStructures;}
	
	/**
	 * Gets the all inverses present.
	 *
	 * @return the all inverses present
	 */
	public boolean getAllInversesPresent(){return allInversesPresent;}
	
	/**
	 * Sets the all inverses present.
	 *
	 * @param newAllInversesPresent the new all inverses present
	 */
	public void setAllInversesPresent(boolean newAllInversesPresent){allInversesPresent = newAllInversesPresent;}
	
}