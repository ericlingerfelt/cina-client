package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import javax.swing.*;
import java.util.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;
import org.w3c.dom.*;



/**
 * The Class RateManDataStructure.
 */
public class RateManDataStructure implements DataStructure{
	
	/** The rate properties. */
	private String libGroup, libraryName, isotopeString, typeDatabaseString
					, sourceLib, sourceLibGroup, destLibName, destLibGroup
					, deleteSourceLib, modifyRatesReport, modifyLibReport
					, rateProperties;

	/** The number shared library data structures. */
	private int numberPublicLibraryDataStructures
				, numberUserLibraryDataStructures
				, numberSharedLibraryDataStructures;
	
	/** The shared library data structure array. */
	private LibraryDataStructure[] publicLibraryDataStructureArray
													, userLibraryDataStructureArray
													, sharedLibraryDataStructureArray;
	
	/** The current library data structure. */
	private LibraryDataStructure currentLibraryDataStructure;
	
	/** The invest rate data structure array. */
	private RateDataStructure[] currentRateDataStructureArray
													, investRateDataStructureArray;
	
	/** The invest rate data structure. */
	private RateDataStructure createRateDataStructure
												, modifyRateDataStructure
												, modifyRateDataStructureCompare
												, errorCheckRateDataStructure
												, investRateDataStructure;
	
	/** The rate id vector about rate. */
	Vector rateIDVectorAboutRate;
	
	/** The decay vector about rate. */
	Vector decayVectorAboutRate;
	
	/** The home lib vector about rate. */
	Vector homeLibVectorAboutRate;
	
	/** The rate id vector rate grid. */
	Vector rateIDVectorRateGrid;
	
	/** The decay vector rate grid. */
	Vector decayVectorRateGrid;
	
	/** The home lib vector rate grid. */
	Vector homeLibVectorRateGrid;
	
	/** The input file. */
	String inputFile;
	
	/** The temp grid. */
	double[] tempGrid;
	
	/** The temp grid file. */
	String tempGridFile;
	
	/** The current rate exists. */
	boolean currentRateExists;
	
	/** The current rate string modify. */
	String currentRateStringModify;
	
	/** The current library string modify. */
	String currentLibraryStringModify;
	
	/** The current rate string error check. */
	String currentRateStringErrorCheck;
	
	/** The current library string error check. */
	String currentLibraryStringErrorCheck;
	
	/** The current rate string invest. */
	String currentRateStringInvest;
	
	/** The appended notes. */
	String appendedNotes;
	
	/** The data source string. */
	String dataSourceString;
	
	/** The rate temp grid. */
	double[] rateTempGrid;
	
	/** The temp units. */
	String tempUnits;
	
	/** The use second product. */
	boolean useSecondProduct;
	
	/** The invest rate selector. */
	String investRateSelector;
	
	/** The invest rate i ds. */
	String[] investRateIDs;
	
	/** The invest rate vector array. */
	Vector[] investRateVectorArray;
	
	/** The about rate i ds. */
	String[] aboutRateIDs;
	
	/** The about rate vector array. */
	Vector[] aboutRateVectorArray;
	
	/** The about rate data structure vector array. */
	Vector[] aboutRateDataStructureVectorArray;
	
	/** The about rate master vector. */
	Vector aboutRateMasterVector;
	
	/** The number total rates. */
	int numberTotalRates;
	
	/** The number comp rates. */
	int numberCompRates;
	
	/** The ratemin. */
	int ratemin;
	
	/** The ratemax. */
	int ratemax;
	
	/** The tempmin. */
	int tempmin;
	
	/** The tempmax. */
	int tempmax;
	
	/** The isotope viktor. */
	Vector isotopeViktor;
	
	/** The rate viktor. */
	Vector rateViktor;
	
	/** The invest rate chart rate list vector. */
	Vector investRateChartRateListVector;
	
	/** The isotope pad library data structure. */
	LibraryDataStructure isotopePadLibraryDataStructure;
	
	//CGI variables
	/** The group. */
	String group;
	
	/** The library. */
	String library;
	
	/** The isotope. */
	String isotope;
	
	/** The type database. */
	String typeDatabase;
	
	/** The src_lib. */
	String src_lib;
	
	/** The dest_lib. */
	String dest_lib;
	
	/** The dest_group. */
	String dest_group;
	
	/** The del_src_lib. */
	String del_src_lib;
	
	/** The rates. */
	String rates;
	
	/** The chk_temp_behavior. */
	String chk_temp_behavior;
	
	/** The chk_overflow. */
	String chk_overflow;
	
	/** The chk_inverse. */
	String chk_inverse;
	
	/** The properties. */
	String properties;
	
	/** The reaction. */
	String reaction;
	
	/** The reaction_type. */
	String reaction_type;
	
	/** The notes. */
	String notes;
	
	/** The make_inverse. */
	String make_inverse;
	
	//Constructor
	/**
	 * Instantiates a new rate man data structure.
	 */
	public RateManDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setLibGroup("");
		setLibraryName("");
		
		setIsotopeString("");
		setTypeDatabaseString("");
		
		setSourceLib("");
		setSourceLibGroup("");
		
		setDeleteSourceLib("");
		
		setDestLibName("");
		setDestLibGroup("");
		
		setModifyRatesReport("");
		setModifyLibReport("");
		
		setRateProperties("");
			
		setNumberPublicLibraryDataStructures(0);
		
		setNumberUserLibraryDataStructures(0);
		
		setNumberSharedLibraryDataStructures(0);
		
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		
		setCurrentLibraryDataStructure(null);
		
		setCurrentRateDataStructureArray(null);
		setInvestRateDataStructureArray(null);
		
		setRateIDVectorAboutRate(new Vector());
		setDecayVectorAboutRate(new Vector());
		setHomeLibVectorAboutRate(new Vector());
		
		setRateIDVectorRateGrid(new Vector());
		setDecayVectorRateGrid(new Vector());
		setHomeLibVectorRateGrid(new Vector());
		
		RateDataStructure apprds = new RateDataStructure();
		RateDataStructure apprds2 = new RateDataStructure();
		RateDataStructure apprds3 = new RateDataStructure();
		RateDataStructure apprds4 = new RateDataStructure();
		RateDataStructure apprds5 = new RateDataStructure();
		
		apprds.setReactionType(4);
		apprds4.setReactionType(4);
		
		Point[] isoIn = new Point[3];
		Point[] isoOut = new Point[4];

		for(int i=0; i<3; i++){
			isoIn[i] = new Point(0, 0);
		}

		for(int i=0; i<4; i++){
			isoOut[i] = new Point(0, 0);
		}
		
		isoIn[0] = new Point(1, 2);
		isoIn[1] = new Point(1, 2);
		isoOut[0] = new Point(2, 4);
		
		apprds.setIsoIn(isoIn);
		apprds.setIsoOut(isoOut);
		
		apprds4.setIsoIn(isoIn);
		apprds4.setIsoOut(isoOut);
		
		setCreateRateDataStructure(apprds);
		
		setModifyRateDataStructure(apprds2);
		
		setErrorCheckRateDataStructure(apprds3);
		
		setInvestRateDataStructure(apprds4);
		
		setModifyRateDataStructureCompare(apprds5);
		
		setReaction("");
	
		setReaction_type("");
	
		setNotes("");
		
		setInputFile("");
		
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
		
		setTempGridFile("");
		
		setTempUnits("T9");
		
		setRateTempGrid(tempGridDoubleArray);
		
		setCurrentRateExists(false);
		
		setCurrentRateStringModify("");
		setCurrentLibraryStringModify("");
		
		setCurrentRateStringErrorCheck("");
		setCurrentLibraryStringErrorCheck("");
		
		setCurrentRateStringInvest("");
		
		setAppendedNotes("");
		
		setDataSourceString("");
		
		setUseSecondProduct(false);
		
		setInvestRateSelector("CHART");
		setInvestRateIDs(null);
		setInvestRateVectorArray(null);
		
		setAboutRateIDs(null);
		setAboutRateVectorArray(null);
		setAboutRateDataStructureVectorArray(null);
		setAboutRateMasterVector(null);
				
		setNumberTotalRates(0);
		setNumberCompRates(0);
		
		setRatemin(0);
		setRatemax(0);
		
		setTempmin(0);
		setTempmax(0);
		
		setIsotopeViktor(new Vector());
		setRateViktor(new Vector());
		setInvestRateChartRateListVector(new Vector());
		
		setIsotopePadLibraryDataStructure(null);
		
		setMake_inverse("NO");
		
		setCHK_TEMP_BEHAVIOR("");
		setCHK_OVERFLOW("");
		setCHK_INVERSE("");
			
	}
	
	/**
	 * Gets the source lib.
	 *
	 * @return the source lib
	 */
	public String getSourceLib(){return sourceLib;}
	
	/**
	 * Sets the source lib.
	 *
	 * @param newSourceLib the new source lib
	 */
	public void setSourceLib(String newSourceLib){
		
		sourceLib = newSourceLib;
		setSRC_LIB(sourceLib);
		
	}
	
	/**
	 * Gets the delete source lib.
	 *
	 * @return the delete source lib
	 */
	public String getDeleteSourceLib(){return deleteSourceLib;}
	
	/**
	 * Sets the delete source lib.
	 *
	 * @param newDeleteSourceLib the new delete source lib
	 */
	public void setDeleteSourceLib(String newDeleteSourceLib){
		
		deleteSourceLib = newDeleteSourceLib;
		setDEL_SRC_LIB(deleteSourceLib);
		
	}
	
	/**
	 * Sets the rate properties.
	 *
	 * @param newRateProperties the new rate properties
	 */
	public void setRateProperties(String newRateProperties){
		
		rateProperties = newRateProperties;
		setProperties(rateProperties);
		
	}
	
	/**
	 * Gets the source lib group.
	 *
	 * @return the source lib group
	 */
	public String getSourceLibGroup(){return sourceLibGroup;}
	
	/**
	 * Sets the source lib group.
	 *
	 * @param newSourceLibGroup the new source lib group
	 */
	public void setSourceLibGroup(String newSourceLibGroup){sourceLibGroup = newSourceLibGroup;}
	
	/**
	 * Gets the modify rates report.
	 *
	 * @return the modify rates report
	 */
	public String getModifyRatesReport(){return modifyRatesReport;}
	
	/**
	 * Sets the modify rates report.
	 *
	 * @param newModifyRatesReport the new modify rates report
	 */
	public void setModifyRatesReport(String newModifyRatesReport){modifyRatesReport = newModifyRatesReport;}
	
	/**
	 * Gets the modify lib report.
	 *
	 * @return the modify lib report
	 */
	public String getModifyLibReport(){return modifyLibReport;}
	
	/**
	 * Sets the modify lib report.
	 *
	 * @param newModifyLibReport the new modify lib report
	 */
	public void setModifyLibReport(String newModifyLibReport){modifyLibReport = newModifyLibReport;}
	
	/**
	 * Gets the dest lib name.
	 *
	 * @return the dest lib name
	 */
	public String getDestLibName(){return destLibName;}
	
	/**
	 * Sets the dest lib name.
	 *
	 * @param newDestLibName the new dest lib name
	 */
	public void setDestLibName(String newDestLibName){
		
		destLibName = newDestLibName;
		setDEST_LIB(destLibName);
		
	}
	
	/**
	 * Gets the dest lib group.
	 *
	 * @return the dest lib group
	 */
	public String getDestLibGroup(){return destLibGroup;}
	
	/**
	 * Sets the dest lib group.
	 *
	 * @param newDestLibGroup the new dest lib group
	 */
	public void setDestLibGroup(String newDestLibGroup){
		
		destLibGroup = newDestLibGroup;
		setDEST_GROUP(destLibGroup);
		
	}
	
	/**
	 * Gets the rate id vector about rate.
	 *
	 * @return the rate id vector about rate
	 */
	public Vector getRateIDVectorAboutRate(){return rateIDVectorAboutRate;}
	
	/**
	 * Sets the rate id vector about rate.
	 *
	 * @param newRateIDVectorAboutRate the new rate id vector about rate
	 */
	public void setRateIDVectorAboutRate(Vector newRateIDVectorAboutRate){
		
		rateIDVectorAboutRate = newRateIDVectorAboutRate;
		
		String tempRates = "";
		
		for(int i=0; i<rateIDVectorAboutRate.size(); i++){
		
			if(i<rateIDVectorAboutRate.size()-1){
		
				tempRates = tempRates + (String)(rateIDVectorAboutRate.elementAt(i)) + "\n";	
			
			}else{
			
				tempRates = tempRates + (String)(rateIDVectorAboutRate.elementAt(i));
			
			}
		
		}
		
		setRates(tempRates);
	
	}
	
	/**
	 * Gets the decay vector about rate.
	 *
	 * @return the decay vector about rate
	 */
	public Vector getDecayVectorAboutRate(){return decayVectorAboutRate;}
	
	/**
	 * Sets the decay vector about rate.
	 *
	 * @param newDecayVectorAboutRate the new decay vector about rate
	 */
	public void setDecayVectorAboutRate(Vector newDecayVectorAboutRate){decayVectorAboutRate = newDecayVectorAboutRate;}
	
	/**
	 * Gets the home lib vector about rate.
	 *
	 * @return the home lib vector about rate
	 */
	public Vector getHomeLibVectorAboutRate(){return homeLibVectorAboutRate;}
	
	/**
	 * Sets the home lib vector about rate.
	 *
	 * @param newHomeLibVectorAboutRate the new home lib vector about rate
	 */
	public void setHomeLibVectorAboutRate(Vector newHomeLibVectorAboutRate){homeLibVectorAboutRate = newHomeLibVectorAboutRate;}
	
	/**
	 * Gets the rate id vector rate grid.
	 *
	 * @return the rate id vector rate grid
	 */
	public Vector getRateIDVectorRateGrid(){return rateIDVectorRateGrid;}
	
	/**
	 * Sets the rate id vector rate grid.
	 *
	 * @param newRateIDVectorRateGrid the new rate id vector rate grid
	 */
	public void setRateIDVectorRateGrid(Vector newRateIDVectorRateGrid){
		
		rateIDVectorRateGrid = newRateIDVectorRateGrid;
		
		String tempRates = "";
		
		for(int i=0; i<rateIDVectorRateGrid.size(); i++){
		
			if(i<rateIDVectorRateGrid.size()-1){
		
				tempRates = tempRates + (String)(rateIDVectorRateGrid.elementAt(i)) + "\n";	
			
			}else{
			
				tempRates = tempRates + (String)(rateIDVectorRateGrid.elementAt(i));
			
			}
		
		}

		setRates(tempRates);
	
	}
	
	/**
	 * Gets the decay vector rate grid.
	 *
	 * @return the decay vector rate grid
	 */
	public Vector getDecayVectorRateGrid(){return decayVectorRateGrid;}
	
	/**
	 * Sets the decay vector rate grid.
	 *
	 * @param newDecayVectorRateGrid the new decay vector rate grid
	 */
	public void setDecayVectorRateGrid(Vector newDecayVectorRateGrid){decayVectorRateGrid = newDecayVectorRateGrid;}
	
	/**
	 * Gets the home lib vector rate grid.
	 *
	 * @return the home lib vector rate grid
	 */
	public Vector getHomeLibVectorRateGrid(){return homeLibVectorRateGrid;}
	
	/**
	 * Sets the home lib vector rate grid.
	 *
	 * @param newHomeLibVectorRateGrid the new home lib vector rate grid
	 */
	public void setHomeLibVectorRateGrid(Vector newHomeLibVectorRateGrid){homeLibVectorRateGrid = newHomeLibVectorRateGrid;}
	
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
	 * Gets the current rate data structure array.
	 *
	 * @return the current rate data structure array
	 */
	public RateDataStructure[] getCurrentRateDataStructureArray(){return currentRateDataStructureArray;}
	
	/**
	 * Sets the current rate data structure array.
	 *
	 * @param newCurrentRateDataStructureArray the new current rate data structure array
	 */
	public void setCurrentRateDataStructureArray(RateDataStructure[] newCurrentRateDataStructureArray){currentRateDataStructureArray = newCurrentRateDataStructureArray;}
	
	/**
	 * Gets the invest rate data structure array.
	 *
	 * @return the invest rate data structure array
	 */
	public RateDataStructure[] getInvestRateDataStructureArray(){return investRateDataStructureArray;}
	
	/**
	 * Sets the invest rate data structure array.
	 *
	 * @param newInvestRateDataStructureArray the new invest rate data structure array
	 */
	public void setInvestRateDataStructureArray(RateDataStructure[] newInvestRateDataStructureArray){investRateDataStructureArray = newInvestRateDataStructureArray;}

	/**
	 * Gets the about rate data structure vector array.
	 *
	 * @return the about rate data structure vector array
	 */
	public Vector[] getAboutRateDataStructureVectorArray(){return aboutRateDataStructureVectorArray;}
	
	/**
	 * Sets the about rate data structure vector array.
	 *
	 * @param newAboutRateDataStructureVectorArray the new about rate data structure vector array
	 */
	public void setAboutRateDataStructureVectorArray(Vector[] newAboutRateDataStructureVectorArray){aboutRateDataStructureVectorArray = newAboutRateDataStructureVectorArray;}

	/**
	 * Gets the creates the rate data structure.
	 *
	 * @return the creates the rate data structure
	 */
	public RateDataStructure getCreateRateDataStructure(){return createRateDataStructure;}
	
	/**
	 * Sets the creates the rate data structure.
	 *
	 * @param newCreateRateDataStructure the new creates the rate data structure
	 */
	public void setCreateRateDataStructure(RateDataStructure newCreateRateDataStructure){createRateDataStructure = newCreateRateDataStructure;}

	/**
	 * Gets the modify rate data structure.
	 *
	 * @return the modify rate data structure
	 */
	public RateDataStructure getModifyRateDataStructure(){return modifyRateDataStructure;}
	
	/**
	 * Sets the modify rate data structure.
	 *
	 * @param newModifyRateDataStructure the new modify rate data structure
	 */
	public void setModifyRateDataStructure(RateDataStructure newModifyRateDataStructure){modifyRateDataStructure = newModifyRateDataStructure;}

	/**
	 * Gets the modify rate data structure compare.
	 *
	 * @return the modify rate data structure compare
	 */
	public RateDataStructure getModifyRateDataStructureCompare(){return modifyRateDataStructureCompare;}
	
	/**
	 * Sets the modify rate data structure compare.
	 *
	 * @param newModifyRateDataStructureCompare the new modify rate data structure compare
	 */
	public void setModifyRateDataStructureCompare(RateDataStructure newModifyRateDataStructureCompare){modifyRateDataStructureCompare = newModifyRateDataStructureCompare;}

	/**
	 * Gets the error check rate data structure.
	 *
	 * @return the error check rate data structure
	 */
	public RateDataStructure getErrorCheckRateDataStructure(){return errorCheckRateDataStructure;}
	
	/**
	 * Sets the error check rate data structure.
	 *
	 * @param newErrorCheckRateDataStructure the new error check rate data structure
	 */
	public void setErrorCheckRateDataStructure(RateDataStructure newErrorCheckRateDataStructure){errorCheckRateDataStructure = newErrorCheckRateDataStructure;}

	/**
	 * Gets the invest rate data structure.
	 *
	 * @return the invest rate data structure
	 */
	public RateDataStructure getInvestRateDataStructure(){return investRateDataStructure;}
	
	/**
	 * Sets the invest rate data structure.
	 *
	 * @param newInvestRateDataStructure the new invest rate data structure
	 */
	public void setInvestRateDataStructure(RateDataStructure newInvestRateDataStructure){investRateDataStructure = newInvestRateDataStructure;}

	/**
	 * Gets the input file.
	 *
	 * @return the input file
	 */
	public String getInputFile(){return inputFile;}
	
	/**
	 * Sets the input file.
	 *
	 * @param newFile the new input file
	 */
	public void setInputFile(String newFile){inputFile = newFile;}

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
	 * Gets the temp grid file.
	 *
	 * @return the temp grid file
	 */
	public String getTempGridFile(){return tempGridFile;}
	
	/**
	 * Sets the temp grid file.
	 *
	 * @param newTempGridFile the new temp grid file
	 */
	public void setTempGridFile(String newTempGridFile){tempGridFile = newTempGridFile;}

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
	 * Gets the current rate string modify.
	 *
	 * @return the current rate string modify
	 */
	public String getCurrentRateStringModify(){return currentRateStringModify;}
	
	/**
	 * Sets the current rate string modify.
	 *
	 * @param newCurrentRateStringModify the new current rate string modify
	 */
	public void setCurrentRateStringModify(String newCurrentRateStringModify){currentRateStringModify = newCurrentRateStringModify;}
	
	/**
	 * Gets the current library string modify.
	 *
	 * @return the current library string modify
	 */
	public String getCurrentLibraryStringModify(){return currentLibraryStringModify;}
	
	/**
	 * Sets the current library string modify.
	 *
	 * @param newCurrentLibraryStringModify the new current library string modify
	 */
	public void setCurrentLibraryStringModify(String newCurrentLibraryStringModify){currentLibraryStringModify = newCurrentLibraryStringModify;}
	
	/**
	 * Gets the current rate string error check.
	 *
	 * @return the current rate string error check
	 */
	public String getCurrentRateStringErrorCheck(){return currentRateStringErrorCheck;}
	
	/**
	 * Sets the current rate string error check.
	 *
	 * @param newCurrentRateStringErrorCheck the new current rate string error check
	 */
	public void setCurrentRateStringErrorCheck(String newCurrentRateStringErrorCheck){currentRateStringErrorCheck = newCurrentRateStringErrorCheck;}
	
	/**
	 * Gets the current library string error check.
	 *
	 * @return the current library string error check
	 */
	public String getCurrentLibraryStringErrorCheck(){return currentLibraryStringErrorCheck;}
	
	/**
	 * Sets the current library string error check.
	 *
	 * @param newCurrentLibraryStringErrorCheck the new current library string error check
	 */
	public void setCurrentLibraryStringErrorCheck(String newCurrentLibraryStringErrorCheck){currentLibraryStringErrorCheck = newCurrentLibraryStringErrorCheck;}
	
	/**
	 * Gets the current rate string invest.
	 *
	 * @return the current rate string invest
	 */
	public String getCurrentRateStringInvest(){return currentRateStringInvest;}
	
	/**
	 * Sets the current rate string invest.
	 *
	 * @param newCurrentRateStringInvest the new current rate string invest
	 */
	public void setCurrentRateStringInvest(String newCurrentRateStringInvest){currentRateStringInvest = newCurrentRateStringInvest;}
	
	/**
	 * Gets the data source string.
	 *
	 * @return the data source string
	 */
	public String getDataSourceString(){return dataSourceString;}
	
	/**
	 * Sets the data source string.
	 *
	 * @param newDataSourceString the new data source string
	 */
	public void setDataSourceString(String newDataSourceString){dataSourceString = newDataSourceString;}
	
	/**
	 * Gets the rate temp grid.
	 *
	 * @return the rate temp grid
	 */
	public double[] getRateTempGrid(){return rateTempGrid;} 
	
	/**
	 * Sets the rate temp grid.
	 *
	 * @param newRateTempGrid the new rate temp grid
	 */
	public void setRateTempGrid(double[] newRateTempGrid){rateTempGrid = newRateTempGrid;}
	
	/**
	 * Gets the temp units.
	 *
	 * @return the temp units
	 */
	public String getTempUnits(){return tempUnits;}
	
	/**
	 * Sets the temp units.
	 *
	 * @param newTempUnits the new temp units
	 */
	public void setTempUnits(String newTempUnits){tempUnits = newTempUnits;}
	
	/**
	 * Gets the appended notes.
	 *
	 * @return the appended notes
	 */
	public String getAppendedNotes(){return appendedNotes;}
	
	/**
	 * Sets the appended notes.
	 *
	 * @param newAppendedNotes the new appended notes
	 */
	public void setAppendedNotes(String newAppendedNotes){appendedNotes = newAppendedNotes;}
	
	/**
	 * Gets the use second product.
	 *
	 * @return the use second product
	 */
	public boolean getUseSecondProduct(){return useSecondProduct;}
	
	/**
	 * Sets the use second product.
	 *
	 * @param newUseSecondProduct the new use second product
	 */
	public void setUseSecondProduct(boolean newUseSecondProduct){useSecondProduct = newUseSecondProduct;}
	
	/**
	 * Gets the invest rate selector.
	 *
	 * @return the invest rate selector
	 */
	public String getInvestRateSelector(){return investRateSelector;}
	
	/**
	 * Sets the invest rate selector.
	 *
	 * @param newInvestRateSelector the new invest rate selector
	 */
	public void setInvestRateSelector(String newInvestRateSelector){investRateSelector = newInvestRateSelector;}
	
	/**
	 * Gets the invest rate i ds.
	 *
	 * @return the invest rate i ds
	 */
	public String[] getInvestRateIDs(){return investRateIDs;}
	
	/**
	 * Sets the invest rate i ds.
	 *
	 * @param newInvestRateIDs the new invest rate i ds
	 */
	public void setInvestRateIDs(String[] newInvestRateIDs){investRateIDs = newInvestRateIDs;}
	
	/**
	 * Gets the invest rate vector array.
	 *
	 * @return the invest rate vector array
	 */
	public Vector[] getInvestRateVectorArray(){return investRateVectorArray;}
	
	/**
	 * Sets the invest rate vector array.
	 *
	 * @param newInvestRateVectorArray the new invest rate vector array
	 */
	public void setInvestRateVectorArray(Vector[] newInvestRateVectorArray){investRateVectorArray = newInvestRateVectorArray;}
	
	/**
	 * Gets the about rate i ds.
	 *
	 * @return the about rate i ds
	 */
	public String[] getAboutRateIDs(){return aboutRateIDs;}
	
	/**
	 * Sets the about rate i ds.
	 *
	 * @param newAboutRateIDs the new about rate i ds
	 */
	public void setAboutRateIDs(String[] newAboutRateIDs){aboutRateIDs = newAboutRateIDs;}
	
	/**
	 * Gets the about rate vector array.
	 *
	 * @return the about rate vector array
	 */
	public Vector[] getAboutRateVectorArray(){return aboutRateVectorArray;}
	
	/**
	 * Sets the about rate vector array.
	 *
	 * @param newAboutRateVectorArray the new about rate vector array
	 */
	public void setAboutRateVectorArray(Vector[] newAboutRateVectorArray){aboutRateVectorArray = newAboutRateVectorArray;}
	
	/**
	 * Gets the about rate master vector.
	 *
	 * @return the about rate master vector
	 */
	public Vector getAboutRateMasterVector(){return aboutRateMasterVector;}
	
	/**
	 * Sets the about rate master vector.
	 *
	 * @param newAboutRateMasterVector the new about rate master vector
	 */
	public void setAboutRateMasterVector(Vector newAboutRateMasterVector){aboutRateMasterVector = newAboutRateMasterVector;}
	
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
	 * Gets the isotope viktor.
	 *
	 * @return the isotope viktor
	 */
	public Vector getIsotopeViktor(){return isotopeViktor;}
	
	/**
	 * Sets the isotope viktor.
	 *
	 * @param newIsotopeViktor the new isotope viktor
	 */
	public void setIsotopeViktor(Vector newIsotopeViktor){isotopeViktor = newIsotopeViktor;}
	
	/**
	 * Gets the rate viktor.
	 *
	 * @return the rate viktor
	 */
	public Vector getRateViktor(){return rateViktor;}
	
	/**
	 * Sets the rate viktor.
	 *
	 * @param newRateViktor the new rate viktor
	 */
	public void setRateViktor(Vector newRateViktor){rateViktor = newRateViktor;}
	
	/**
	 * Gets the invest rate chart rate list vector.
	 *
	 * @return the invest rate chart rate list vector
	 */
	public Vector getInvestRateChartRateListVector(){return investRateChartRateListVector;}
	
	/**
	 * Sets the invest rate chart rate list vector.
	 *
	 * @param newInvestRateChartRateListVector the new invest rate chart rate list vector
	 */
	public void setInvestRateChartRateListVector(Vector newInvestRateChartRateListVector){investRateChartRateListVector = newInvestRateChartRateListVector;}
	
	/**
	 * Gets the isotope pad library data structure.
	 *
	 * @return the isotope pad library data structure
	 */
	public LibraryDataStructure getIsotopePadLibraryDataStructure(){return isotopePadLibraryDataStructure;}
	
	/**
	 * Sets the isotope pad library data structure.
	 *
	 * @param newIsotopePadLibraryDataStructure the new isotope pad library data structure
	 */
	public void setIsotopePadLibraryDataStructure(LibraryDataStructure newIsotopePadLibraryDataStructure){isotopePadLibraryDataStructure = newIsotopePadLibraryDataStructure;}

	//CGI variables/////////////////////////////////////
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
	
	/**
	 * Gets the sR c_ lib.
	 *
	 * @return the sR c_ lib
	 */
	public String getSRC_LIB(){return src_lib;}
	
	/**
	 * Sets the sR c_ lib.
	 *
	 * @param newSRC_LIB the new sR c_ lib
	 */
	public void setSRC_LIB(String newSRC_LIB){src_lib = newSRC_LIB;}
	
	/**
	 * Gets the dE l_ sr c_ lib.
	 *
	 * @return the dE l_ sr c_ lib
	 */
	public String getDEL_SRC_LIB(){return del_src_lib;}
	
	/**
	 * Sets the dE l_ sr c_ lib.
	 *
	 * @param newDEL_SRC_LIB the new dE l_ sr c_ lib
	 */
	public void setDEL_SRC_LIB(String newDEL_SRC_LIB){del_src_lib = newDEL_SRC_LIB;}
	
	/**
	 * Gets the dES t_ lib.
	 *
	 * @return the dES t_ lib
	 */
	public String getDEST_LIB(){return dest_lib;}
	
	/**
	 * Sets the dES t_ lib.
	 *
	 * @param newDEST_LIB the new dES t_ lib
	 */
	public void setDEST_LIB(String newDEST_LIB){dest_lib = newDEST_LIB;}
	
	/**
	 * Gets the dES t_ group.
	 *
	 * @return the dES t_ group
	 */
	public String getDEST_GROUP(){return dest_group;}
	
	/**
	 * Sets the dES t_ group.
	 *
	 * @param newDEST_GROUP the new dES t_ group
	 */
	public void setDEST_GROUP(String newDEST_GROUP){dest_group = newDEST_GROUP;}
	
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
	 * Gets the reaction.
	 *
	 * @return the reaction
	 */
	public String getReaction(){return reaction;} 
	
	/**
	 * Sets the reaction.
	 *
	 * @param newReaction the new reaction
	 */
	public void setReaction(String newReaction){reaction = newReaction;}
	
	/**
	 * Gets the reaction_type.
	 *
	 * @return the reaction_type
	 */
	public String getReaction_type(){return reaction_type;} 
	
	/**
	 * Sets the reaction_type.
	 *
	 * @param newReaction_type the new reaction_type
	 */
	public void setReaction_type(String newReaction_type){reaction_type = newReaction_type;}	
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes(){return notes;} 
	
	/**
	 * Sets the notes.
	 *
	 * @param newNotes the new notes
	 */
	public void setNotes(String newNotes){notes = newNotes;}
	
	/**
	 * Gets the make_inverse.
	 *
	 * @return the make_inverse
	 */
	public String getMake_inverse(){return make_inverse;}
	
	/**
	 * Sets the make_inverse.
	 *
	 * @param newMake_inverse the new make_inverse
	 */
	public void setMake_inverse(String newMake_inverse){make_inverse = newMake_inverse;} 
	
	/**
	 * Gets the cH k_ tem p_ behavior.
	 *
	 * @return the cH k_ tem p_ behavior
	 */
	public String getCHK_TEMP_BEHAVIOR(){return chk_temp_behavior;}
	
	/**
	 * Sets the cH k_ tem p_ behavior.
	 *
	 * @param newCHK_TEMP_BEHAVIOR the new cH k_ tem p_ behavior
	 */
	public void setCHK_TEMP_BEHAVIOR(String newCHK_TEMP_BEHAVIOR){chk_temp_behavior = newCHK_TEMP_BEHAVIOR;}
	
	/**
	 * Gets the cH k_ overflow.
	 *
	 * @return the cH k_ overflow
	 */
	public String getCHK_OVERFLOW(){return chk_overflow;}
	
	/**
	 * Sets the cH k_ overflow.
	 *
	 * @param newCHK_OVERFLOW the new cH k_ overflow
	 */
	public void setCHK_OVERFLOW(String newCHK_OVERFLOW){chk_overflow = newCHK_OVERFLOW;}
	
	/**
	 * Gets the cH k_ inverse.
	 *
	 * @return the cH k_ inverse
	 */
	public String getCHK_INVERSE(){return chk_inverse;}
	
	/**
	 * Sets the cH k_ inverse.
	 *
	 * @param newCHK_INVERSE the new cH k_ inverse
	 */
	public void setCHK_INVERSE(String newCHK_INVERSE){chk_inverse = newCHK_INVERSE;}
	
}