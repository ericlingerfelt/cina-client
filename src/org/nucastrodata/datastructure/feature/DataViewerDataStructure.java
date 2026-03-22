package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;



//This class is the data structure for the rate Viewer 
/**
 * The Class DataViewerDataStructure.
 */
public class DataViewerDataStructure implements DataStructure{
	
	//Declare String vars to hold library group, library name, isotope string, and type database string
	//These variables in turn assign values to accompanying CGI variables below
	/** The lib group. */
	String libGroup;
	
	/** The nuc data set group. */
	String nucDataSetGroup;
	
	/** The library name. */
	String libraryName;
	
	/** The nuc data set name. */
	String nucDataSetName;
	
	/** The isotope string. */
	String isotopeString;
	
	/** The type string. */
	String typeString;
	
	//Declare Vector object to hold all chosen rateIDs
	/** The nuc data id vector. */
	Vector nucDataIDVector;
	
	//Declare variable to indicate which library is currently chosen in from
	//The library combo box in the main interface
	//This index corresponds directly to the combo box's current selected index.
	/** The nuc data set index. */
	int nucDataSetIndex;
	
	//Declare library data structure array to hold rate info gotten from server
	/** The nuc data set structure array. */
	NucDataSetDataStructure[] nucDataSetStructureArray;
	
	//Declare var to hold total number of library structures
	/** The number nuc data set structures. */
	int numberNucDataSetStructures;
	
	//Declare Vector array to hold which isotopes were chosen for each library
	//[index: libIndex]
	/** The viktor array. */
	Vector[] viktorArray;
	
	//Declare array to hold which reaction types are chosen
	//[index: number of reaction types chosen]
	/** The reaction type array. */
	int[] reactionTypeArray;
	
	//Declare array to hold the rateIDs selected
	//[index: number of rate ids selected]
	/** The nuc data id array. */
	String[] nucDataIDArray;
	
	//Declare var to hold number of public libraries
	/** The number public nuc data set data structures. */
	int numberPublicNucDataSetDataStructures;
	
	//Declare var to hold number of user libraries
	/** The number user nuc data set data structures. */
	int numberUserNucDataSetDataStructures;
	
	//Declare var to hold number of shared libraries
	/** The number shared nuc data set data structures. */
	int numberSharedNucDataSetDataStructures;
	
	//Declare lib data structure arrays to hold library data
	/** The shared nuc data set data structure array. */
	NucDataSetDataStructure[] publicNucDataSetDataStructureArray, userNucDataSetDataStructureArray, sharedNucDataSetDataStructureArray;
	
	//Declare temp var to hold lib data in CGIComm when data is parsed
	/** The current nuc data set data structure. */
	NucDataSetDataStructure currentNucDataSetDataStructure;
	
	/** The current library data structure. */
	LibraryDataStructure currentLibraryDataStructure;
	
	//Declare vars to hold min and max temp and rate 
	/** The C smin. */
	int CSmin;
	
	/** The C smax. */
	int CSmax;
	
	/** The S fmin. */
	double SFmin;
	
	/** The S fmax. */
	double SFmax;
	
	/** The energy c smin. */
	double energyCSmin;
	
	/** The energy c smax. */
	double energyCSmax;
	
	/** The energy s fmin. */
	double energySFmin;
	
	/** The energy s fmax. */
	double energySFmax;
	
	/** The max number sf points. */
	int maxNumberSFPoints;
	
	/** The max number cs points. */
	int maxNumberCSPoints;
	
	//Decalre vars for the number of total rates and the number of component rates
	/** The number total sf nuc data. */
	int numberTotalSFNucData;
	
	/** The number total cs nuc data. */
	int numberTotalCSNucData;

	///CGI///////////////////////////////
	//Declare CGI vars
	/** The group. */
	String group;
	
	/** The properties. */
	String properties;
	
	/** The nuc data. */
	String nucData;
	
	/** The library. */
	String library;
	
	/** The set. */
	String set;
	
	/** The isotope. */
	String isotope;
	
	/** The type. */
	String type;

	//Constructor
	/**
	 * Instantiates a new data viewer data structure.
	 */
	public DataViewerDataStructure(){initialize();}
	
	//Initialize data structure vars
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setLibGroup("");

		setNucDataSetGroup("");

		setLibraryName("");
		
		setNucDataSetName("");

		setIsotopeString("");

		setTypeString("");

		setNucDataSetIndex(0);
		
		setNucDataSetStructureArray(null);
		
		setNumberNucDataSetStructures(0);
		
		setViktorArray(null);
		
		setReactionTypeArray(null);
		
		setNucDataIDArray(null);
		
		setNumberPublicNucDataSetDataStructures(0);
		
		setNumberUserNucDataSetDataStructures(0);
		
		setNumberSharedNucDataSetDataStructures(0);
		
		setPublicNucDataSetDataStructureArray(null); 
		setUserNucDataSetDataStructureArray(null);
		setSharedNucDataSetDataStructureArray(null);
		
		setCurrentNucDataSetDataStructure(null);
		
		setCurrentLibraryDataStructure(null);
		
		setCSmin(0);
		setCSmax(0);
		
		setSFmin(0.0);
		setSFmax(0.0);
		
		setEnergyCSmin(0);
		setEnergyCSmax(0);
	
		setEnergySFmin(0);
		setEnergySFmax(0);
	
		setNumberTotalCSNucData(0);
		setNumberTotalSFNucData(0);
		
		setMaxNumberSFPoints(0);
		setMaxNumberCSPoints(0);
		
		setProperties("");
		setNucData("");
		
	}
	
	//Set and get methods for this data structure
	/**
	 * Gets the nuc data set group.
	 *
	 * @return the nuc data set group
	 */
	public String getNucDataSetGroup(){return nucDataSetGroup;}
	
	/**
	 * Sets the nuc data set group.
	 *
	 * @param newNucDataSetGroup the new nuc data set group
	 */
	public void setNucDataSetGroup(String newNucDataSetGroup){
		
		nucDataSetGroup = newNucDataSetGroup;
		setGroup(nucDataSetGroup);
		
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
	public void setNucDataSetName(String newNucDataSetName){
		
		nucDataSetName = newNucDataSetName;
		setSet(nucDataSetName);
		
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
	 * Gets the type string.
	 *
	 * @return the type string
	 */
	public String getTypeString(){return typeString;}
	
	/**
	 * Sets the type string.
	 *
	 * @param newTypeString the new type string
	 */
	public void setTypeString(String newTypeString){
		
		typeString = newTypeString;
		setType(typeString);
		
	}
	
	/**
	 * Gets the nuc data set index.
	 *
	 * @return the nuc data set index
	 */
	public int getNucDataSetIndex(){return nucDataSetIndex;}
	
	/**
	 * Sets the nuc data set index.
	 *
	 * @param newNucDataSetIndex the new nuc data set index
	 */
	public void setNucDataSetIndex(int newNucDataSetIndex){nucDataSetIndex = newNucDataSetIndex;}

	/**
	 * Gets the nuc data set structure array.
	 *
	 * @return the nuc data set structure array
	 */
	public NucDataSetDataStructure[] getNucDataSetStructureArray(){return nucDataSetStructureArray;}
	
	/**
	 * Sets the nuc data set structure array.
	 *
	 * @param newNucDataSetStructureArray the new nuc data set structure array
	 */
	public void setNucDataSetStructureArray(NucDataSetDataStructure[] newNucDataSetStructureArray){nucDataSetStructureArray = newNucDataSetStructureArray;}
	
	/**
	 * Gets the number nuc data set structures.
	 *
	 * @return the number nuc data set structures
	 */
	public int getNumberNucDataSetStructures(){return numberNucDataSetStructures;}
	
	/**
	 * Sets the number nuc data set structures.
	 *
	 * @param newNumberNucDataSetStructures the new number nuc data set structures
	 */
	public void setNumberNucDataSetStructures(int newNumberNucDataSetStructures){numberNucDataSetStructures = newNumberNucDataSetStructures;}
	
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
	 * Gets the nuc data id array.
	 *
	 * @return the nuc data id array
	 */
	public String[] getNucDataIDArray(){return nucDataIDArray;}
	
	/**
	 * Sets the nuc data id array.
	 *
	 * @param newNucDataIDArray the new nuc data id array
	 */
	public void setNucDataIDArray(String[] newNucDataIDArray){nucDataIDArray = newNucDataIDArray;}
	
	/**
	 * Gets the number public nuc data set data structures.
	 *
	 * @return the number public nuc data set data structures
	 */
	public int getNumberPublicNucDataSetDataStructures(){return numberPublicNucDataSetDataStructures;}
	
	/**
	 * Sets the number public nuc data set data structures.
	 *
	 * @param newNumberPublicNucDataSetDataStructures the new number public nuc data set data structures
	 */
	public void setNumberPublicNucDataSetDataStructures(int newNumberPublicNucDataSetDataStructures){numberPublicNucDataSetDataStructures = newNumberPublicNucDataSetDataStructures;}
	
	/**
	 * Gets the number user nuc data set data structures.
	 *
	 * @return the number user nuc data set data structures
	 */
	public int getNumberUserNucDataSetDataStructures(){return numberUserNucDataSetDataStructures;}
	
	/**
	 * Sets the number user nuc data set data structures.
	 *
	 * @param newNumberUserNucDataSetDataStructures the new number user nuc data set data structures
	 */
	public void setNumberUserNucDataSetDataStructures(int newNumberUserNucDataSetDataStructures){numberUserNucDataSetDataStructures = newNumberUserNucDataSetDataStructures;}
	
	/**
	 * Gets the number shared nuc data set data structures.
	 *
	 * @return the number shared nuc data set data structures
	 */
	public int getNumberSharedNucDataSetDataStructures(){return numberSharedNucDataSetDataStructures;}
	
	/**
	 * Sets the number shared nuc data set data structures.
	 *
	 * @param newNumberSharedNucDataSetDataStructures the new number shared nuc data set data structures
	 */
	public void setNumberSharedNucDataSetDataStructures(int newNumberSharedNucDataSetDataStructures){numberSharedNucDataSetDataStructures = newNumberSharedNucDataSetDataStructures;}
	
	/**
	 * Gets the public nuc data set data structure array.
	 *
	 * @return the public nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getPublicNucDataSetDataStructureArray(){return publicNucDataSetDataStructureArray;}
	
	/**
	 * Sets the public nuc data set data structure array.
	 *
	 * @param newPublicNucDataSetDataStructureArray the new public nuc data set data structure array
	 */
	public void setPublicNucDataSetDataStructureArray(NucDataSetDataStructure[] newPublicNucDataSetDataStructureArray){publicNucDataSetDataStructureArray = newPublicNucDataSetDataStructureArray;}
	
	/**
	 * Gets the user nuc data set data structure array.
	 *
	 * @return the user nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getUserNucDataSetDataStructureArray(){return userNucDataSetDataStructureArray;}
	
	/**
	 * Sets the user nuc data set data structure array.
	 *
	 * @param newUserNucDataSetDataStructureArray the new user nuc data set data structure array
	 */
	public void setUserNucDataSetDataStructureArray(NucDataSetDataStructure[] newUserNucDataSetDataStructureArray){userNucDataSetDataStructureArray = newUserNucDataSetDataStructureArray;}
	
	/**
	 * Gets the shared nuc data set data structure array.
	 *
	 * @return the shared nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getSharedNucDataSetDataStructureArray(){return sharedNucDataSetDataStructureArray;}
	
	/**
	 * Sets the shared nuc data set data structure array.
	 *
	 * @param newSharedNucDataSetDataStructureArray the new shared nuc data set data structure array
	 */
	public void setSharedNucDataSetDataStructureArray(NucDataSetDataStructure[] newSharedNucDataSetDataStructureArray){sharedNucDataSetDataStructureArray = newSharedNucDataSetDataStructureArray;}
	
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
	 * Gets the current nuc data set data structure.
	 *
	 * @return the current nuc data set data structure
	 */
	public NucDataSetDataStructure getCurrentNucDataSetDataStructure(){return currentNucDataSetDataStructure;}
	
	/**
	 * Sets the current nuc data set data structure.
	 *
	 * @param newCurrentNucDataSetDataStructure the new current nuc data set data structure
	 */
	public void setCurrentNucDataSetDataStructure(NucDataSetDataStructure newCurrentNucDataSetDataStructure){currentNucDataSetDataStructure = newCurrentNucDataSetDataStructure;}
	
	/**
	 * Gets the c smin.
	 *
	 * @return the c smin
	 */
	public int getCSmin(){return CSmin;} 
	
	/**
	 * Sets the c smin.
	 *
	 * @param newCSmin the new c smin
	 */
	public void setCSmin(int newCSmin){CSmin = newCSmin;}
	
	/**
	 * Gets the c smax.
	 *
	 * @return the c smax
	 */
	public int getCSmax(){return CSmax;} 
	
	/**
	 * Sets the c smax.
	 *
	 * @param newCSmax the new c smax
	 */
	public void setCSmax(int newCSmax){CSmax = newCSmax;}
	
	/**
	 * Gets the s fmin.
	 *
	 * @return the s fmin
	 */
	public double getSFmin(){return SFmin;} 
	
	/**
	 * Sets the s fmin.
	 *
	 * @param newSFmin the new s fmin
	 */
	public void setSFmin(double newSFmin){SFmin = newSFmin;}
	
	/**
	 * Gets the s fmax.
	 *
	 * @return the s fmax
	 */
	public double getSFmax(){return SFmax;} 
	
	/**
	 * Sets the s fmax.
	 *
	 * @param newSFmax the new s fmax
	 */
	public void setSFmax(double newSFmax){SFmax = newSFmax;}
	
	/**
	 * Gets the energy c smin.
	 *
	 * @return the energy c smin
	 */
	public double getEnergyCSmin(){return energyCSmin;} 
	
	/**
	 * Sets the energy c smin.
	 *
	 * @param newEnergyCSmin the new energy c smin
	 */
	public void setEnergyCSmin(double newEnergyCSmin){energyCSmin = newEnergyCSmin;}
	
	/**
	 * Gets the energy c smax.
	 *
	 * @return the energy c smax
	 */
	public double getEnergyCSmax(){return energyCSmax;} 
	
	/**
	 * Sets the energy c smax.
	 *
	 * @param newEnergyCSmax the new energy c smax
	 */
	public void setEnergyCSmax(double newEnergyCSmax){energyCSmax = newEnergyCSmax;}
	
	/**
	 * Gets the energy s fmin.
	 *
	 * @return the energy s fmin
	 */
	public double getEnergySFmin(){return energySFmin;} 
	
	/**
	 * Sets the energy s fmin.
	 *
	 * @param newEnergySFmin the new energy s fmin
	 */
	public void setEnergySFmin(double newEnergySFmin){energySFmin = newEnergySFmin;}
	
	/**
	 * Gets the energy s fmax.
	 *
	 * @return the energy s fmax
	 */
	public double getEnergySFmax(){return energySFmax;} 
	
	/**
	 * Sets the energy s fmax.
	 *
	 * @param newEnergySFmax the new energy s fmax
	 */
	public void setEnergySFmax(double newEnergySFmax){energySFmax = newEnergySFmax;}
	
	/**
	 * Gets the number total cs nuc data.
	 *
	 * @return the number total cs nuc data
	 */
	public int getNumberTotalCSNucData(){return numberTotalCSNucData;}
	
	/**
	 * Sets the number total cs nuc data.
	 *
	 * @param newNumberTotalCSNucData the new number total cs nuc data
	 */
	public void setNumberTotalCSNucData(int newNumberTotalCSNucData){numberTotalCSNucData = newNumberTotalCSNucData;}
	
	/**
	 * Gets the number total sf nuc data.
	 *
	 * @return the number total sf nuc data
	 */
	public int getNumberTotalSFNucData(){return numberTotalSFNucData;}
	
	/**
	 * Sets the number total sf nuc data.
	 *
	 * @param newNumberTotalSFNucData the new number total sf nuc data
	 */
	public void setNumberTotalSFNucData(int newNumberTotalSFNucData){numberTotalSFNucData = newNumberTotalSFNucData;}
	
	/**
	 * Gets the max number sf points.
	 *
	 * @return the max number sf points
	 */
	public int getMaxNumberSFPoints(){return maxNumberSFPoints;}
	
	/**
	 * Sets the max number sf points.
	 *
	 * @param newMaxNumberSFPoints the new max number sf points
	 */
	public void setMaxNumberSFPoints(int newMaxNumberSFPoints){maxNumberSFPoints = newMaxNumberSFPoints;}
	
	/**
	 * Gets the max number cs points.
	 *
	 * @return the max number cs points
	 */
	public int getMaxNumberCSPoints(){return maxNumberCSPoints;}
	
	/**
	 * Sets the max number cs points.
	 *
	 * @param newMaxNumberCSPoints the new max number cs points
	 */
	public void setMaxNumberCSPoints(int newMaxNumberCSPoints){maxNumberCSPoints = newMaxNumberCSPoints;}
	
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
	 * Gets the nuc data.
	 *
	 * @return the nuc data
	 */
	public String getNucData(){return nucData;}
	
	/**
	 * Sets the nuc data.
	 *
	 * @param newNucData the new nuc data
	 */
	public void setNucData(String newNucData){nucData = newNucData;} 

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
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public String getSet(){return set;}
	
	/**
	 * Sets the sets the.
	 *
	 * @param newSet the new sets the
	 */
	public void setSet(String newSet){set = newSet;}

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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType(){return type;}
	
	/**
	 * Sets the type.
	 *
	 * @param newType the new type
	 */
	public void setType(String newType){type = newType;}
	
}