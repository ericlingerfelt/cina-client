package org.nucastrodata.datastructure.feature;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.LibraryDirectoryDataStructure;

import java.util.*;

/**
 * The Class RateLibManDataStructure.
 */
public class RateLibManDataStructure implements DataStructure{
	
	/** The lib group. */
	String libGroup;
	
	/** The library name. */
	String libraryName;
	
	/** The isotope string. */
	String isotopeString;
	
	/** The type database string. */
	String typeDatabaseString;
	
	/** The source lib. */
	String sourceLib;
	
	/** The source lib group. */
	String sourceLibGroup;
	
	/** The dest lib name. */
	String destLibName;
	
	/** The dest lib group. */
	String destLibGroup;
	
	/** The dest custom lib name. */
	String destCustomLibName;
	
	/** The source custom lib. */
	String sourceCustomLib;
	
	/** The modify rates report. */
	String modifyRatesReport;
	
	/** The modify lib report. */
	String modifyLibReport;
	
	/** The shared lib report. */
	String sharedLibReport;
	
	/** The export lib report. */
	String exportLibReport;
	
	/** The delete source lib. */
	String deleteSourceLib;
	
	/** The rate properties. */
	String rateProperties;
	
	/** The rate id array. */
	String[] rateIDArray;
	
	/** The number rate i ds. */
	int numberRateIDs;
	
	/** The number public library data structures. */
	int numberPublicLibraryDataStructures;
	
	/** The number user library data structures. */
	int numberUserLibraryDataStructures;
	
	/** The number shared library data structures. */
	int numberSharedLibraryDataStructures;
	
	/** The shared library data structure array. */
	LibraryDataStructure[] publicLibraryDataStructureArray, userLibraryDataStructureArray, sharedLibraryDataStructureArray;
	
	/** The current library data structure. */
	LibraryDataStructure currentLibraryDataStructure;
	
	/** The rate id vector. */
	Vector rateIDVector;
	
	/** The decay vector. */
	Vector decayVector;
	
	/** The home lib vector. */
	Vector homeLibVector;
	
	/** The add missing inv rates report. */
	String addMissingInvRatesReport;
	
	/** The inverse reaction. */
	String inverseReaction;
	
	/** The export filename. */
	String exportFilename;
	
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
	
	/** The chk_temp_behavior. */
	String chk_temp_behavior;
	
	/** The chk_overflow. */
	String chk_overflow;
	
	/** The chk_inverse. */
	String chk_inverse;
	
	/** The rates. */
	String rates;
	
	/** The reaction. */
	String reaction;
	
	/** The reaction_type. */
	String reaction_type;
	
	/** The properties. */
	String properties;
	
	/** The make_inverse. */
	String make_inverse;
	
	/** The format. */
	String format;
	
	boolean importDir;
	
	String libDir;
	
	boolean libDirExists;
	
	ArrayList<String> libDirList = new ArrayList<String>();
	
	LibraryDirectoryDataStructure libDirDS;
	
	
	//Constructor
	/**
	 * Instantiates a new rate lib man data structure.
	 */
	public RateLibManDataStructure(){initialize();}
	
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
		
		setDestLibName("");
		setDestLibGroup("");
		
		setDestCustomLibName("");
		setSourceCustomLib("");
		
		setDeleteSourceLib("");
		
		setModifyRatesReport("");
		setModifyLibReport("");
		setSharedLibReport("");
		setExportLibReport("");
		
		setRateProperties("");
		
		setRateIDArray(null);
		
		setNumberRateIDs(0);
			
		setNumberPublicLibraryDataStructures(0);
		
		setNumberUserLibraryDataStructures(0);
		
		setNumberSharedLibraryDataStructures(0);
		
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		
		setCurrentLibraryDataStructure(null);
		
		setRateIDVector(new Vector());
		
		setDecayVector(new Vector());
		
		setHomeLibVector(new Vector());
		
		setMake_inverse("NO");
		
		setAddMissingInvRatesReport("");
		
		setReaction("");
		
		setReaction_type("");
		
		setInverseReaction("");
		
		setExportFilename("");
		
		setCHK_TEMP_BEHAVIOR("");
		setCHK_OVERFLOW("");
		setCHK_INVERSE("");
		
		setFormat("");
		
		setImportDir(false);
		
		setLibDir("");
		
		setLibDirExists(false);
		
		setLibDir("");
		
		setLibDirDS(new LibraryDirectoryDataStructure());
		
	}
	
	public LibraryDirectoryDataStructure getLibDirDS(){return libDirDS;}
	public void setLibDirDS(LibraryDirectoryDataStructure newLibDirDS){libDirDS = newLibDirDS;}
	
	public ArrayList<String> getLibDirList(){return libDirList;}
	public void setLibDirList(ArrayList<String> newLibDirList){libDirList = newLibDirList;}
	
	public String getLibDir(){return libDir;}
	public void setLibDir(String newLibDir){libDir = newLibDir;}
	
	public boolean getLibDirExists(){return libDirExists;}
	public void setLibDirExists(boolean newLibDirExists){libDirExists = newLibDirExists;}
	
	public boolean getImportDir(){return importDir;}
	public void setImportDir(boolean newImportDir){importDir = newImportDir;}
	
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
	 * Gets the source custom lib.
	 *
	 * @return the source custom lib
	 */
	public String getSourceCustomLib(){return sourceCustomLib;}
	
	/**
	 * Sets the source custom lib.
	 *
	 * @param newSourceCustomLib the new source custom lib
	 */
	public void setSourceCustomLib(String newSourceCustomLib){
		
		sourceCustomLib = newSourceCustomLib;
		setSRC_LIB(sourceCustomLib);
		
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
	 * Gets the shared lib report.
	 *
	 * @return the shared lib report
	 */
	public String getSharedLibReport(){return sharedLibReport;}
	
	/**
	 * Sets the shared lib report.
	 *
	 * @param newSharedLibReport the new shared lib report
	 */
	public void setSharedLibReport(String newSharedLibReport){sharedLibReport = newSharedLibReport;}
	
	/**
	 * Gets the export lib report.
	 *
	 * @return the export lib report
	 */
	public String getExportLibReport(){return exportLibReport;}
	
	/**
	 * Sets the export lib report.
	 *
	 * @param newExportLibReport the new export lib report
	 */
	public void setExportLibReport(String newExportLibReport){exportLibReport = newExportLibReport;}
	
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
	 * Gets the dest custom lib name.
	 *
	 * @return the dest custom lib name
	 */
	public String getDestCustomLibName(){return destCustomLibName;}
	
	/**
	 * Sets the dest custom lib name.
	 *
	 * @param newDestCustomLibName the new dest custom lib name
	 */
	public void setDestCustomLibName(String newDestCustomLibName){
		
		destCustomLibName = newDestCustomLibName;
		setDEST_LIB(destCustomLibName);
		
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
	 * Gets the number rate i ds.
	 *
	 * @return the number rate i ds
	 */
	public int getNumberRateIDs(){return numberRateIDs;}
	
	/**
	 * Sets the number rate i ds.
	 *
	 * @param newNumberRateIDs the new number rate i ds
	 */
	public void setNumberRateIDs(int newNumberRateIDs){numberRateIDs = newNumberRateIDs;}
		
	/**
	 * Gets the rate id vector.
	 *
	 * @return the rate id vector
	 */
	public Vector getRateIDVector(){return rateIDVector;}
	
	/**
	 * Sets the rate id vector.
	 *
	 * @param newRateIDVector the new rate id vector
	 */
	public void setRateIDVector(Vector newRateIDVector){
		
		rateIDVector = newRateIDVector;
		
		String tempRates = "";
		
		for(int i=0; i<rateIDVector.size(); i++){
		
			if(i<rateIDVector.size()-1){
		
				tempRates = tempRates + (String)(rateIDVector.elementAt(i)) + "\n";	
			
			}else{
			
				tempRates = tempRates + (String)(rateIDVector.elementAt(i));
			
			}
		
		}
		
		setRates(tempRates);
	
	}
	
	/**
	 * Gets the decay vector.
	 *
	 * @return the decay vector
	 */
	public Vector getDecayVector(){return decayVector;}
	
	/**
	 * Sets the decay vector.
	 *
	 * @param newDecayVector the new decay vector
	 */
	public void setDecayVector(Vector newDecayVector){decayVector = newDecayVector;}
	
	/**
	 * Gets the home lib vector.
	 *
	 * @return the home lib vector
	 */
	public Vector getHomeLibVector(){return homeLibVector;}
	
	/**
	 * Sets the home lib vector.
	 *
	 * @param newHomeLibVector the new home lib vector
	 */
	public void setHomeLibVector(Vector newHomeLibVector){homeLibVector = newHomeLibVector;}
	
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
	 * Gets the adds the missing inv rates report.
	 *
	 * @return the adds the missing inv rates report
	 */
	public String getAddMissingInvRatesReport(){return addMissingInvRatesReport;}
	
	/**
	 * Sets the adds the missing inv rates report.
	 *
	 * @param newAddMissingInvRatesReport the new adds the missing inv rates report
	 */
	public void setAddMissingInvRatesReport(String newAddMissingInvRatesReport){addMissingInvRatesReport = newAddMissingInvRatesReport;}

	/**
	 * Gets the inverse reaction.
	 *
	 * @return the inverse reaction
	 */
	public String getInverseReaction(){return inverseReaction;}
	
	/**
	 * Sets the inverse reaction.
	 *
	 * @param newInverseReaction the new inverse reaction
	 */
	public void setInverseReaction(String newInverseReaction){inverseReaction = newInverseReaction;}

	/**
	 * Gets the export filename.
	 *
	 * @return the export filename
	 */
	public String getExportFilename(){return exportFilename;}
	
	/**
	 * Sets the export filename.
	 *
	 * @param newExportFilename the new export filename
	 */
	public void setExportFilename(String newExportFilename){exportFilename = newExportFilename;}

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
	
	/**
	 * Gets the format.
	 *
	 * @return the format
	 */
	public String getFormat(){return format;}
	
	/**
	 * Sets the format.
	 *
	 * @param newFormat the new format
	 */
	public void setFormat(String newFormat){format = newFormat;}
	
}