package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.w3c.dom.*;



public class RateViewerDataStructure implements DataStructure{
	
	String libGroup;
	String libraryName;
	String isotopeString;
	String typeDatabaseString;
	Vector rateIDVector;
	int libIndex;
	LibraryDataStructure[] libraryStructureArray;
	int numberLibraryStructures;
	Vector[] viktorArray;
	int[] reactionTypeArray;
	String[] rateIDArray;
	String[] unitsArray;
	int numberPublicLibraryDataStructures;
	int numberUserLibraryDataStructures;
	int numberSharedLibraryDataStructures;
	LibraryDataStructure[] publicLibraryDataStructureArray, userLibraryDataStructureArray, sharedLibraryDataStructureArray;
	LibraryDataStructure currentLibraryDataStructure;
	int ratemin;
	int ratemax;
	int tempmin;
	int tempmax;
	double[] tempGrid;
	int numberTotalRates;
	int numberCompRates;
	boolean currentRateExists;
	Vector[] rateDataStructureVectorArray;
	Vector masterVector;
	
	/** The add rate array. */
	double[] addRateArray;
	
	/** The add temp array. */
	double[] addTempArray;
	
	/** The rate added. */
	boolean rateAdded;
	
	/** The add rate name. */
	String addRateName;
	
	///CGI///////////////////////////////
	//Declare CGI vars
	/** The group. */
	String group;
	
	/** The properties. */
	String properties;
	
	/** The rates. */
	String rates;
	
	/** The library. */
	String library;
	
	/** The isotope. */
	String isotope;
	
	/** The type database. */
	String typeDatabase;

	//Constructor
	/**
	 * Instantiates a new rate viewer data structure.
	 */
	public RateViewerDataStructure(){initialize();}
	
	//Initialize data structure vars
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setLibGroup("");

		setLibraryName("");

		setIsotopeString("");

		setTypeDatabaseString("");

		setLibIndex(0);
		
		setLibraryStructureArray(null);
		
		setNumberLibraryStructures(0);
		
		setViktorArray(null);
		
		setReactionTypeArray(null);
		
		setRateIDArray(null);
		
		setUnitsArray(null);
		
		setNumberPublicLibraryDataStructures(0);
		
		setNumberUserLibraryDataStructures(0);
		
		setNumberSharedLibraryDataStructures(0);
		
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		
		setCurrentLibraryDataStructure(null);
		
		setRatemin(0);
		setRatemax(0);
		
		setTempmin(0);
		setTempmax(0);
		
		double[] tempGridDoubleArray = {0.01, 0.011, 0.012, 0.013, 0.014, 0.015, 0.016, 0.017, 0.018, 0.019
							, 0.02, 0.0225, 0.025, 0.0275
							, 0.03, 0.0325, 0.035, 0.0375
							, 0.04, 0.0425, 0.045, 0.0475
							, 0.05, 0.0525, 0.055, 0.0575
							, 0.06, 0.0625, 0.065, 0.0675
							, 0.07, 0.0725, 0.075, 0.0775
							, 0.08, 0.0825, 0.085, 0.0875
							, 0.09, 0.0925, 0.095, 0.0975
							, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95
							, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10};
		setTempGrid(tempGridDoubleArray);
	
		setNumberTotalRates(0);
		setNumberCompRates(0);
		
		setCurrentRateExists(false);
		
		setRateDataStructureVectorArray(null);
		setMasterVector(null);
	
		setProperties("");
		setRates("");
		
		setAddRateArray(null);
		setAddTempArray(null);
		setRateAdded(false);
		setAddRateName("");
		
	}
	
	//Set and get methods for this data structure
	/**
	 * Gets the lib group.
	 *
	 * @return the lib group
	 */
	public String getLibGroup(){return libGroup;}
	
	/**
	 * Sets the lib group.
	 *
	 * @param newLibGroup the new lib group
	 */
	public void setLibGroup(String newLibGroup){
		
		libGroup = newLibGroup;
		setGroup(libGroup);
		
	}
	
	/**
	 * Gets the library name.
	 *
	 * @return the library name
	 */
	public String getLibraryName(){return libraryName;}
	
	/**
	 * Sets the library name.
	 *
	 * @param newLibraryName the new library name
	 */
	public void setLibraryName(String newLibraryName){
		
		libraryName = newLibraryName;
		setLibrary(libraryName);
		
	}
	
	/**
	 * Gets the isotope string.
	 *
	 * @return the isotope string
	 */
	public String getIsotopeString(){return isotopeString;}
	
	/**
	 * Sets the isotope string.
	 *
	 * @param newIsotopeString the new isotope string
	 */
	public void setIsotopeString(String newIsotopeString){
		
		isotopeString = newIsotopeString;
		setIsotope(isotopeString);
		
	}
	
	/**
	 * Gets the type database string.
	 *
	 * @return the type database string
	 */
	public String getTypeDatabaseString(){return typeDatabaseString;}
	
	/**
	 * Sets the type database string.
	 *
	 * @param newTypeDatabaseString the new type database string
	 */
	public void setTypeDatabaseString(String newTypeDatabaseString){
		
		typeDatabaseString = newTypeDatabaseString;
		setTypeDatabase(typeDatabaseString);
		
	}
	
	/**
	 * Gets the lib index.
	 *
	 * @return the lib index
	 */
	public int getLibIndex(){return libIndex;}
	
	/**
	 * Sets the lib index.
	 *
	 * @param newLibIndex the new lib index
	 */
	public void setLibIndex(int newLibIndex){libIndex = newLibIndex;}

	/**
	 * Gets the library structure array.
	 *
	 * @return the library structure array
	 */
	public LibraryDataStructure[] getLibraryStructureArray(){return libraryStructureArray;}
	
	/**
	 * Sets the library structure array.
	 *
	 * @param newLibraryStructureArray the new library structure array
	 */
	public void setLibraryStructureArray(LibraryDataStructure[] newLibraryStructureArray){libraryStructureArray = newLibraryStructureArray;}
	
	/**
	 * Gets the number library structures.
	 *
	 * @return the number library structures
	 */
	public int getNumberLibraryStructures(){return numberLibraryStructures;}
	
	/**
	 * Sets the number library structures.
	 *
	 * @param newNumberLibraryStructures the new number library structures
	 */
	public void setNumberLibraryStructures(int newNumberLibraryStructures){numberLibraryStructures = newNumberLibraryStructures;}
	
	/**
	 * Gets the viktor array.
	 *
	 * @return the viktor array
	 */
	public Vector[] getViktorArray(){return viktorArray;}
	
	/**
	 * Sets the viktor array.
	 *
	 * @param newViktorArray the new viktor array
	 */
	public void setViktorArray(Vector[] newViktorArray){viktorArray = newViktorArray;}
	
	/**
	 * Gets the reaction type array.
	 *
	 * @return the reaction type array
	 */
	public int[] getReactionTypeArray(){return reactionTypeArray;}
	
	/**
	 * Sets the reaction type array.
	 *
	 * @param newReactionTypeArray the new reaction type array
	 */
	public void setReactionTypeArray(int[] newReactionTypeArray){reactionTypeArray = newReactionTypeArray;}
	
	/**
	 * Gets the rate id array.
	 *
	 * @return the rate id array
	 */
	public String[] getRateIDArray(){return rateIDArray;}
	
	/**
	 * Sets the rate id array.
	 *
	 * @param newRateIDArray the new rate id array
	 */
	public void setRateIDArray(String[] newRateIDArray){rateIDArray = newRateIDArray;}
	
	/**
	 * Gets the units array.
	 *
	 * @return the units array
	 */
	public String[] getUnitsArray(){return unitsArray;}
	
	/**
	 * Sets the units array.
	 *
	 * @param newUnitsArray the new units array
	 */
	public void setUnitsArray(String[] newUnitsArray){unitsArray = newUnitsArray;}
	
	/**
	 * Gets the number public library data structures.
	 *
	 * @return the number public library data structures
	 */
	public int getNumberPublicLibraryDataStructures(){return numberPublicLibraryDataStructures;}
	
	/**
	 * Sets the number public library data structures.
	 *
	 * @param newNumberPublicLibraryDataStructures the new number public library data structures
	 */
	public void setNumberPublicLibraryDataStructures(int newNumberPublicLibraryDataStructures){numberPublicLibraryDataStructures = newNumberPublicLibraryDataStructures;}
	
	/**
	 * Gets the number user library data structures.
	 *
	 * @return the number user library data structures
	 */
	public int getNumberUserLibraryDataStructures(){return numberUserLibraryDataStructures;}
	
	/**
	 * Sets the number user library data structures.
	 *
	 * @param newNumberUserLibraryDataStructures the new number user library data structures
	 */
	public void setNumberUserLibraryDataStructures(int newNumberUserLibraryDataStructures){numberUserLibraryDataStructures = newNumberUserLibraryDataStructures;}
	
	/**
	 * Gets the number shared library data structures.
	 *
	 * @return the number shared library data structures
	 */
	public int getNumberSharedLibraryDataStructures(){return numberSharedLibraryDataStructures;}
	
	/**
	 * Sets the number shared library data structures.
	 *
	 * @param newNumberSharedLibraryDataStructures the new number shared library data structures
	 */
	public void setNumberSharedLibraryDataStructures(int newNumberSharedLibraryDataStructures){numberSharedLibraryDataStructures = newNumberSharedLibraryDataStructures;}
	
	/**
	 * Gets the public library data structure array.
	 *
	 * @return the public library data structure array
	 */
	public LibraryDataStructure[] getPublicLibraryDataStructureArray(){return publicLibraryDataStructureArray;}
	
	/**
	 * Sets the public library data structure array.
	 *
	 * @param newPublicLibraryDataStructureArray the new public library data structure array
	 */
	public void setPublicLibraryDataStructureArray(LibraryDataStructure[] newPublicLibraryDataStructureArray){publicLibraryDataStructureArray = newPublicLibraryDataStructureArray;}
	
	/**
	 * Gets the user library data structure array.
	 *
	 * @return the user library data structure array
	 */
	public LibraryDataStructure[] getUserLibraryDataStructureArray(){return userLibraryDataStructureArray;}
	
	/**
	 * Sets the user library data structure array.
	 *
	 * @param newUserLibraryDataStructureArray the new user library data structure array
	 */
	public void setUserLibraryDataStructureArray(LibraryDataStructure[] newUserLibraryDataStructureArray){userLibraryDataStructureArray = newUserLibraryDataStructureArray;}
	
	/**
	 * Gets the shared library data structure array.
	 *
	 * @return the shared library data structure array
	 */
	public LibraryDataStructure[] getSharedLibraryDataStructureArray(){return sharedLibraryDataStructureArray;}
	
	/**
	 * Sets the shared library data structure array.
	 *
	 * @param newSharedLibraryDataStructureArray the new shared library data structure array
	 */
	public void setSharedLibraryDataStructureArray(LibraryDataStructure[] newSharedLibraryDataStructureArray){sharedLibraryDataStructureArray = newSharedLibraryDataStructureArray;}
	
	/**
	 * Gets the current library data structure.
	 *
	 * @return the current library data structure
	 */
	public LibraryDataStructure getCurrentLibraryDataStructure(){return currentLibraryDataStructure;}
	
	/**
	 * Sets the current library data structure.
	 *
	 * @param newCurrentLibraryDataStructure the new current library data structure
	 */
	public void setCurrentLibraryDataStructure(LibraryDataStructure newCurrentLibraryDataStructure){currentLibraryDataStructure = newCurrentLibraryDataStructure;}
	
	/**
	 * Gets the ratemin.
	 *
	 * @return the ratemin
	 */
	public int getRatemin(){return ratemin;} 
	
	/**
	 * Sets the ratemin.
	 *
	 * @param newRatemin the new ratemin
	 */
	public void setRatemin(int newRatemin){ratemin = newRatemin;}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	public int getRatemax(){return ratemax;} 
	
	/**
	 * Sets the ratemax.
	 *
	 * @param newRatemax the new ratemax
	 */
	public void setRatemax(int newRatemax){ratemax = newRatemax;}
	
	/**
	 * Gets the tempmin.
	 *
	 * @return the tempmin
	 */
	public int getTempmin(){return tempmin;} 
	
	/**
	 * Sets the tempmin.
	 *
	 * @param newTempmin the new tempmin
	 */
	public void setTempmin(int newTempmin){tempmin = newTempmin;}
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	public int getTempmax(){return tempmax;} 
	
	/**
	 * Sets the tempmax.
	 *
	 * @param newTempmax the new tempmax
	 */
	public void setTempmax(int newTempmax){tempmax = newTempmax;}
	
	/**
	 * Gets the temp grid.
	 *
	 * @return the temp grid
	 */
	public double[] getTempGrid(){return tempGrid;} 
	
	/**
	 * Sets the temp grid.
	 *
	 * @param newTempGrid the new temp grid
	 */
	public void setTempGrid(double[] newTempGrid){tempGrid = newTempGrid;}
	
	/**
	 * Gets the number total rates.
	 *
	 * @return the number total rates
	 */
	public int getNumberTotalRates(){return numberTotalRates;}
	
	/**
	 * Sets the number total rates.
	 *
	 * @param newNumberTotalRates the new number total rates
	 */
	public void setNumberTotalRates(int newNumberTotalRates){numberTotalRates = newNumberTotalRates;}
	
	/**
	 * Gets the number comp rates.
	 *
	 * @return the number comp rates
	 */
	public int getNumberCompRates(){return numberCompRates;}
	
	/**
	 * Sets the number comp rates.
	 *
	 * @param newNumberCompRates the new number comp rates
	 */
	public void setNumberCompRates(int newNumberCompRates){numberCompRates = newNumberCompRates;}
	
	/**
	 * Gets the current rate exists.
	 *
	 * @return the current rate exists
	 */
	public boolean getCurrentRateExists(){return currentRateExists;}
	
	/**
	 * Sets the current rate exists.
	 *
	 * @param newCurrentRateExists the new current rate exists
	 */
	public void setCurrentRateExists(boolean newCurrentRateExists){currentRateExists = newCurrentRateExists;}
	
	/**
	 * Gets the rate data structure vector array.
	 *
	 * @return the rate data structure vector array
	 */
	public Vector[] getRateDataStructureVectorArray(){return rateDataStructureVectorArray;}
	
	/**
	 * Sets the rate data structure vector array.
	 *
	 * @param newRateDataStructureVectorArray the new rate data structure vector array
	 */
	public void setRateDataStructureVectorArray(Vector[] newRateDataStructureVectorArray){rateDataStructureVectorArray = newRateDataStructureVectorArray;}
	
	/**
	 * Gets the master vector.
	 *
	 * @return the master vector
	 */
	public Vector getMasterVector(){return masterVector;}
	
	/**
	 * Sets the master vector.
	 *
	 * @param newMasterVector the new master vector
	 */
	public void setMasterVector(Vector newMasterVector){masterVector = newMasterVector;}
	
	/**
	 * Gets the adds the rate array.
	 *
	 * @return the adds the rate array
	 */
	public double[] getAddRateArray(){return addRateArray;}
	
	/**
	 * Sets the adds the rate array.
	 *
	 * @param newAddRateArray the new adds the rate array
	 */
	public void setAddRateArray(double[] newAddRateArray){addRateArray = newAddRateArray;}

	/**
	 * Gets the adds the temp array.
	 *
	 * @return the adds the temp array
	 */
	public double[] getAddTempArray(){return addTempArray;}
	
	/**
	 * Sets the adds the temp array.
	 *
	 * @param newAddTempArray the new adds the temp array
	 */
	public void setAddTempArray(double[] newAddTempArray){addTempArray = newAddTempArray;}

	/**
	 * Gets the rate added.
	 *
	 * @return the rate added
	 */
	public boolean getRateAdded(){return rateAdded;}
	
	/**
	 * Sets the rate added.
	 *
	 * @param newRateAdded the new rate added
	 */
	public void setRateAdded(boolean newRateAdded){rateAdded = newRateAdded;}

	/**
	 * Gets the adds the rate name.
	 *
	 * @return the adds the rate name
	 */
	public String getAddRateName(){return addRateName;}
	
	/**
	 * Sets the adds the rate name.
	 *
	 * @param newAddRateName the new adds the rate name
	 */
	public void setAddRateName(String newAddRateName){addRateName = newAddRateName;}

	//CGI variables//////////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup(){return group;}
	
	/**
	 * Sets the group.
	 *
	 * @param newGroup the new group
	 */
	public void setGroup(String newGroup){group = newGroup;}
	
	/**
	 * Gets the rates.
	 *
	 * @return the rates
	 */
	public String getRates(){return rates;}
	
	/**
	 * Sets the rates.
	 *
	 * @param newRates the new rates
	 */
	public void setRates(String newRates){rates = newRates;} 

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public String getProperties(){return properties;}
	
	/**
	 * Sets the properties.
	 *
	 * @param newProperties the new properties
	 */
	public void setProperties(String newProperties){properties = newProperties;} 

	/**
	 * Gets the library.
	 *
	 * @return the library
	 */
	public String getLibrary(){return library;}
	
	/**
	 * Sets the library.
	 *
	 * @param newLibrary the new library
	 */
	public void setLibrary(String newLibrary){library = newLibrary;}

	/**
	 * Gets the isotope.
	 *
	 * @return the isotope
	 */
	public String getIsotope(){return isotope;}
	
	/**
	 * Sets the isotope.
	 *
	 * @param newIsotope the new isotope
	 */
	public void setIsotope(String newIsotope){isotope = newIsotope;}

	/**
	 * Gets the type database.
	 *
	 * @return the type database
	 */
	public String getTypeDatabase(){return typeDatabase;}
	
	/**
	 * Sets the type database.
	 *
	 * @param newTypeDatabase the new type database
	 */
	public void setTypeDatabase(String newTypeDatabase){typeDatabase = newTypeDatabase;}

}