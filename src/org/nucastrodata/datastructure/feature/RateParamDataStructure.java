//This was written by Eric Lingerfelt
//09/19/2003

package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;

import java.util.*;
import java.io.*;

import org.nucastrodata.PrintfFormat;


/**
 * The Class RateParamDataStructure.
 */
public class RateParamDataStructure implements DataStructure{

	/** The lib group. */
	String libGroup;
	
	/** The source lib. */
	String sourceLib;
	
	/** The dest lib name. */
	String destLibName;
	
	/** The dest lib group. */
	String destLibGroup;
	
	/** The delete source lib. */
	String deleteSourceLib;
	
	/** The nuc data set name. */
	String nucDataSetName;
	
	/** The isotope string. */
	String isotopeString;
	
	/** The nuc data set group. */
	String nucDataSetGroup;
	
	/** The nuc data properties. */
	String nucDataProperties;
	
	/** The isotope viktor. */
	Vector isotopeViktor;
	
	/** The rate viktor. */
	Vector rateViktor;
	
	/** The chart rate list vector. */
	Vector chartRateListVector;
	
	/** The rate i ds. */
	String[] rateIDs;	
	
	/** The rate data structure array. */
	RateDataStructure[] rateDataStructureArray;
	
	/** The number public library data structures. */
	int numberPublicLibraryDataStructures;
	
	/** The number user library data structures. */
	int numberUserLibraryDataStructures;
	
	/** The number shared library data structures. */
	int numberSharedLibraryDataStructures;
	
	/** The shared library data structure array. */
	LibraryDataStructure[] publicLibraryDataStructureArray, userLibraryDataStructureArray, sharedLibraryDataStructureArray;	
	
	/** The rate data structure. */
	RateDataStructure rateDataStructure;
	
	/** The current library data structure. */
	LibraryDataStructure currentLibraryDataStructure;

	/** The number public nuc data set data structures. */
	int numberPublicNucDataSetDataStructures;
	
	/** The number user nuc data set data structures. */
	int numberUserNucDataSetDataStructures;
	
	/** The number shared nuc data set data structures. */
	int numberSharedNucDataSetDataStructures;
	
	/** The shared nuc data set data structure array. */
	NucDataSetDataStructure[] publicNucDataSetDataStructureArray, userNucDataSetDataStructureArray, sharedNucDataSetDataStructureArray;
	
	/** The nuc data data structure. */
	NucDataDataStructure nucDataDataStructure;
	
	/** The current nuc data set data structure. */
	NucDataSetDataStructure currentNucDataSetDataStructure;
	
	/** The modify lib report. */
	String modifyLibReport;
	
	/** The modify rates report. */
	String modifyRatesReport;
	
	/** The demo combo box. */
	int demoComboBox;

	//or "CS(E)" or "BOTH";
	/** The possible types. */
	String possibleTypes;
	
	//Set the input type
	/** The input type. */
	String inputType;
	
	/** The type string. */
	String typeString;
	
	//Set input format
	/** The input format. */
	String inputFormat;
	
	//Input file name
	/** The input filename. */
	String inputFilename;
	
	/** The file init. */
	File fileInit;
	
	/** The input file. */
	String inputFile;
	
	//Input file directory
	/** The input file dir. */
	String inputFileDir;
	
	//Input units
	/** The x units. */
	String xUnits;
	
	/** The y units. */
	String yUnits;
	
	//Rate Units
	/** The rate units. */
	String rateUnits;
	
	//Number of input points
	/** The number points. */
	int numberPoints;
	
	//Reasonable Temp range after input check
	/** The allowed tempmin. */
	double allowedTempmin;
	
	/** The allowed tempmax. */
	double allowedTempmax;
	
	//Chosen temp range
	/** The tempmin. */
	double tempmin;
	
	/** The tempmax. */
	double tempmax;
	
	//Set output level
	/** The output level. */
	int outputLevel;

	/** The check input array. */
	String[] checkInputArray;
	
	/** The input warnings response array. */
	String[] inputWarningsResponseArray;
	
	//String holding status of Andy's code
	/** The status text. */
	String statusText;
	
	//print level of Andy's code
	/** The print level. */
	int printLevel;
	
	//number of points of R vs T from Andy's code
	/** The number points rt. */
	int numberPointsRT;
	
	//max and min temp for R(T) from Andy's code 
	/** The tempmin rt. */
	double tempminRT;
	
	/** The tempmax rt. */
	double tempmaxRT;
	
	//max and min rate for R(T) from Andy's code 
	/** The ratemin. */
	double ratemin;
	
	/** The ratemax. */
	double ratemax;
	
	//max and min of temp for rate param
	/** The tempmin param. */
	double tempminParam;
	
	/** The tempmax param. */
	double tempmaxParam;
	
	//up to 21 parameters from Jason's code
	//as strings to preserve E notation
	/** The param string array. */
	String[] paramStringArray;
	
	//up to 21 parameters from Jason's code
	/** The inverse param array. */
	double[] inverseParamArray;
	
	//up to 21 parameters from Jason's code
	//as strings to preserve E notation
	/** The inverse param string array. */
	String[] inverseParamStringArray;
	
	//number of starting parameters
	/** The number starting params. */
	int numberStartingParams;
	
	//print level for Jason's code
	/** The print level param. */
	int printLevelParam;
	
	//max number of iterations
	/** The max number iterations. */
	int maxNumberIterations;
	
	//maximum percent difference allowed for Jason's code
	/** The max percent diff allowed. */
	double maxPercentDiffAllowed;
	
	//the name of the technique used in Jason's code
	/** The technique param. */
	String techniqueParam;
	
	//max and min temp from Jason's code
	/** The tempmin param out. */
	double tempminParamOut;
	
	/** The tempmax param out. */
	double tempmaxParamOut;
	
	//max and min temp from Jason's code
	/** The ratemin param out. */
	double rateminParamOut;
	
	/** The ratemax param out. */
	double ratemaxParamOut;
	
	//number of points in rate parameterization
	/** The number points param. */
	int numberPointsParam;

	//parameter check?
	/** The parm check. */
	boolean parmCheck;
	
	//Arrays to hold the R(T) data from Andy's code
	/** The Rate data array. */
	double[] RateDataArray;
	
	/** The Temp data array. */
	double[] TempDataArray;
	
	/** The Rate data array orig. */
	double[] RateDataArrayOrig;
	
	/** The Temp data array orig. */
	double[] TempDataArrayOrig;
	
	//Arrays to hold the R(T) data from Jason's code
	/** The Rate param data array. */
	double[] RateParamDataArray;
	
	/** The Temp param data array. */
	double[] TempParamDataArray;
	
	//Arrays for low temp info
	/** The Low temp data array. */
	double[] LowTempDataArray;
	
	/** The High temp data array. */
	double[] HighTempDataArray;
	
	//Arrays for low temp info
	/** The Low rate data array. */
	double[] LowRateDataArray;
	
	/** The High rate data array. */
	double[] HighRateDataArray;
	
	//Array to hold percent difference between Jason's and Andy's
	/** The Percent diff data array. */
	double[] PercentDiffDataArray;
	
	/** The Temp data array extra. */
	double[] TempDataArrayExtra;
	
	/** The Rate data array extra. */
	double[] RateDataArrayExtra;
	
	/** The temp behavior string. */
	String tempBehaviorString;
	
	/** The current rate exists. */
	boolean currentRateExists;
	
	/** The pasted data. */
	boolean pastedData;
	
	/** The use second product. */
	boolean useSecondProduct;
	
	/** The slope array. */
	double[] slopeArray;
	
	/** The number high e points max. */
	int numberHighEPointsMax;
	
	/** The number low e points max. */
	int numberLowEPointsMax;

	/** The upload data. */
	boolean uploadData;

	/** The current nuc data string. */
	String currentNucDataString;
	
	/** The current rate string. */
	String currentRateString;
	
	/** The current nuc data set string. */
	String currentNucDataSetString;

	/** The starting parameters. */
	double[] startingParameters;
	
	/** The starting parameters lib. */
	double[] startingParametersLib;
	
	/** The start lib. */
	String startLib;
	
	/** The start rate. */
	String startRate;
	
	/** The start from rate. */
	boolean startFromRate;

	/** The extra points. */
	boolean extraPoints;
	
	/** The rate submitted. */
	boolean rateSubmitted;
	
	/** The rate modified. */
	boolean rateModified;
	
	/** The low temp. */
	boolean lowTemp;
	
	/** The high temp. */
	boolean highTemp;

	///////////////////////////CGICOMM//PROPERTIES////////////////////////////
	/** The type database. */
	String typeDatabase;
	
	/** The group. */
	String group;
	
	/** The library. */
	String library;
	
	/** The src_lib. */
	String src_lib;
	
	/** The dest_lib. */
	String dest_lib;
	
	/** The dest_group. */
	String dest_group;
	
	/** The del_src_lib. */
	String del_src_lib;
	
	/** The isotope. */
	String isotope;
	
	/** The set. */
	String set;
	
	/** The nuc data. */
	String nucData;
	
	/** The chk_temp_behavior. */
	String chk_temp_behavior;
	
	/** The chk_overflow. */
	String chk_overflow;
	
	/** The chk_inverse. */
	String chk_inverse;
	
	/** The properties. */
	String properties;
	
	//the body of the transmission
	/** The body. */
	String body;
	
	//string containing the Z,A pairs
	/** The reaction. */
	String reaction;
	
	/** The reaction_type. */
	String reaction_type;
	
	//notes left at the rate identification step of RateGen
	/** The notes. */
	String notes;
	
	//S factor or Cross Section vs. Energy
	/** The type. */
	String type;
	
	//format of data indicating columns of data
	/** The format. */
	String format;
	
	/** The xunits. */
	String xunits;
	
	/** The yunits. */
	String yunits;
	
	//file name of data file
	/** The filename. */
	String filename;
	
	/** The file. */
	String file;
	
	//equal to Y or N for each check below
	/** The positive_chk. */
	String positive_chk;
	
	/** The single_chk. */
	String single_chk;
	
	/** The range_chk. */
	String range_chk;
	
	/** The continuity_chk. */
	String continuity_chk;
	
	/** The error_chk. */
	String error_chk;
	
	/** The reaction_chk. */
	String reaction_chk;
	
	//max and min temperature indicated by user for rate gen (Andy's code)
	/** The tmin. */
	String tmin;
	
	/** The tmax. */
	String tmax;
	
	//print level for andy's code
	/** The plevel. */
	String plevel;
	
	//Method of parameterization
	/** The method. */
	String method;
	
	//Max number of iterations
	/** The iterations. */
	String iterations;
	
	//Min and max temp param
	/** The tminparam. */
	String tminparam;
	
	/** The tmaxparam. */
	String tmaxparam;
	
	//Outputlevel param
	/** The plevelparam. */
	String plevelparam;
	
	//number of starting parameters
	/** The s_parm_num. */
	String s_parm_num;
	
	/** The start_parms. */
	String start_parms;
	
	//parameter check
	/** The parm_check. */
	String parm_check;
	
	//max percent diff
	/** The max_diff. */
	String max_diff;
	
	/** The rates. */
	String rates;
	
	/** The make_inverse. */
	String make_inverse;
	
	///////////////////////////////////////////////////////////////////////////
	
	//Constructor
	/**
	 * Instantiates a new rate param data structure.
	 */
	public RateParamDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setLibrary("");
		setLibGroup("");
		setNucDataSetGroup("");
		setSourceLib("");
		setTypeDatabase("");
		setDestLibName("");
		setDestLibGroup("");
		setDeleteSourceLib("");

		setIsotopeViktor(new Vector());
		setRateViktor(new Vector());
		setChartRateListVector(new Vector());
		setRateIDs(null);

		setRateDataStructureArray(null);

		setFileInit(null);

		setIsotopeString("");
		setTypeString("");
		setNucDataSetName("");

		setNucDataProperties("");

		setNumberPublicLibraryDataStructures(0);
		
		setNumberUserLibraryDataStructures(0);
		
		setNumberSharedLibraryDataStructures(0);
		
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		
		setCurrentLibraryDataStructure(null);
		
		setNumberPublicNucDataSetDataStructures(0);
		
		setNumberUserNucDataSetDataStructures(0);
		
		setNumberSharedNucDataSetDataStructures(0);
		
		setPublicNucDataSetDataStructureArray(null); 
		setUserNucDataSetDataStructureArray(null);
		setSharedNucDataSetDataStructureArray(null);

		setNucDataDataStructure(new NucDataDataStructure());

		setCurrentNucDataSetDataStructure(null);

		RateDataStructure apprds = new RateDataStructure();

		apprds.setReactionType(4);
		
		setDemoComboBox(0);	

		setModifyLibReport("");
		setModifyRatesReport("");
		
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
		
		setRateDataStructure(apprds);
		
		setPossibleTypes("BOTH");
		
		setInputType("S(E)");
		setInputFormat("1,0,2,0");
		setInputFilename("");
		setInputFile("");
		setInputFileDir("");
		
		setXUnits("T9");
		setYUnits("keV-b");
		setRateUnits("");
		
		setNumberPoints(0);
		
		setAllowedTempmin(0.0);
		setAllowedTempmax(0.0);
		
		setTempmin(0.0);
		setTempmax(0.0);
		
		setOutputLevel(1);

		String[] checkInputStringArray = {"Y", "Y", "Y", "Y", "Y"};
		setCheckInputArray(checkInputStringArray);
		
		String[] inputWarningsResponseStringArray = {"SKIPPED", "SKIPPED", "SKIPPED", "SKIPPED", "SKIPPED"};
		setInputWarningsResponseArray(inputWarningsResponseStringArray);
		
		setStatusText("");
		
		setPrintLevel(1);
		
		setNumberPointsRT(0);
		
		setTempminRT(0.0);
		setTempmaxRT(0.0);
		
		setRatemin(0.0);
		setRatemax(0.0);
		
		setTempminParam(0.0);
		setTempmaxParam(0.0);
		
		double[] inverseParamDoubleArray = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
						, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
						
		setInverseParamArray(inverseParamDoubleArray);
		
		String[] ParamStringStringArray = {"", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""};
								
		setParamStringArray(ParamStringStringArray);
		
		String[] inverseParamStringStringArray = {"", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""
								, "", "", "", "", "", "", ""};
													
		setInverseParamStringArray(inverseParamStringStringArray);

		setNumberStartingParams(7);
		
		setPrintLevelParam(2);
		
		setMaxNumberIterations(50000);
		setMaxPercentDiffAllowed(2.0);
			
		setTechniqueParam("PARALLEL MARQUARDT");
		
		setTempminParamOut(0.0);
		setTempmaxParamOut(0.0);
		
		setRateminParamOut(0.0);
		setRatemaxParamOut(0.0);
		
		setNumberPointsParam(0);

		setParmCheck(false);
			
		setRateDataArray(null);
		setTempDataArray(null);	

		setRateDataArrayOrig(null);
		setTempDataArrayOrig(null);

		setRateParamDataArray(null);
		setTempParamDataArray(null);
	
		setPercentDiffDataArray(null);
		
		setLowRateDataArray(null);
		setLowTempDataArray(null);
		
		setHighRateDataArray(null);
		setHighTempDataArray(null);
		
		setRateDataArrayExtra(null);
		setTempDataArrayExtra(null);
		
		setTempBehaviorString("");
		
		setCurrentRateExists(false);
		
		setPastedData(false);
		
		setUploadData(true);
		
		setUseSecondProduct(false);
		
		setSlopeArray(null);
		
		setRateSubmitted(false);
		
		setNumberHighEPointsMax(0);
		setNumberLowEPointsMax(0);
		
		setCurrentNucDataString("");
		setCurrentRateString("");
		setCurrentNucDataSetString("");
		
		setBody("");
		setReaction("");
		setReaction_type("");
		setProperties("");
		setNotes("");
		
		setReaction_chk("Y");
		
		setRates("");
		
		setMake_inverse("NO");
		
		setCHK_TEMP_BEHAVIOR("");
		setCHK_OVERFLOW("");
		setCHK_INVERSE("");
		
		setNucData("");
		
		setStartingParameters(null);
		setStartingParametersLib(null);
		setStartLib("");
		setStartRate("");
		setStartFromRate(false);
		
		setExtraPoints(false);
		setRateModified(false);
		setLowTemp(false);
		setHighTemp(false);

	}
	
	/**
	 * Gets the file init.
	 *
	 * @return the file init
	 */
	public File getFileInit(){return fileInit;} 
	
	/**
	 * Sets the file init.
	 *
	 * @param newFileInit the new file init
	 */
	public void setFileInit(File newFileInit){fileInit = newFileInit;}
	
	/**
	 * Gets the starting parameters.
	 *
	 * @return the starting parameters
	 */
	public double[] getStartingParameters(){return startingParameters;}
	
	/**
	 * Sets the starting parameters.
	 *
	 * @param newStartingParameters the new starting parameters
	 */
	public void setStartingParameters(double[] newStartingParameters){
		
		startingParameters = newStartingParameters;
		
		if(newStartingParameters!=null){
			
			String paramString = "";
						
			for(int i=0; i<startingParameters.length; i++){
			
				if(i!=0){
					paramString += "," + String.valueOf(startingParameters[i]);
				}else{
					paramString += String.valueOf(startingParameters[i]);
				}
			
			}
			
			setStart_parms(paramString);
			
		}else{
		
			setStart_parms("");
		
		}
		
	}
	
	/**
	 * Gets the starting parameters lib.
	 *
	 * @return the starting parameters lib
	 */
	public double[] getStartingParametersLib(){return startingParametersLib;}
	
	/**
	 * Sets the starting parameters lib.
	 *
	 * @param newStartingParametersLib the new starting parameters lib
	 */
	public void setStartingParametersLib(double[] newStartingParametersLib){startingParametersLib = newStartingParametersLib;}
	
	/**
	 * Gets the start lib.
	 *
	 * @return the start lib
	 */
	public String getStartLib(){return startLib;}
	
	/**
	 * Sets the start lib.
	 *
	 * @param newStartLib the new start lib
	 */
	public void setStartLib(String newStartLib){startLib = newStartLib;}
	
	/**
	 * Gets the start rate.
	 *
	 * @return the start rate
	 */
	public String getStartRate(){return startRate;}
	
	/**
	 * Sets the start rate.
	 *
	 * @param newStartRate the new start rate
	 */
	public void setStartRate(String newStartRate){startRate = newStartRate;}
	
	/**
	 * Gets the start from rate.
	 *
	 * @return the start from rate
	 */
	public boolean getStartFromRate(){return startFromRate;}
	
	/**
	 * Sets the start from rate.
	 *
	 * @param newStartFromRate the new start from rate
	 */
	public void setStartFromRate(boolean newStartFromRate){startFromRate = newStartFromRate;}
	
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
	 * Sets the nuc data properties.
	 *
	 * @param newNucDataProperties the new nuc data properties
	 */
	public void setNucDataProperties(String newNucDataProperties){
		
		nucDataProperties = newNucDataProperties;
		setProperties(nucDataProperties);
		
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
	 * Gets the chart rate list vector.
	 *
	 * @return the chart rate list vector
	 */
	public Vector getChartRateListVector(){return chartRateListVector;}
	
	/**
	 * Sets the chart rate list vector.
	 *
	 * @param newChartRateListVector the new chart rate list vector
	 */
	public void setChartRateListVector(Vector newChartRateListVector){chartRateListVector = newChartRateListVector;}

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
	 * Gets the rate i ds.
	 *
	 * @return the rate i ds
	 */
	public String[] getRateIDs(){return rateIDs;}
	
	/**
	 * Sets the rate i ds.
	 *
	 * @param newRateIDs the new rate i ds
	 */
	public void setRateIDs(String[] newRateIDs){rateIDs = newRateIDs;}
	
	/**
	 * Gets the rate data structure array.
	 *
	 * @return the rate data structure array
	 */
	public RateDataStructure[] getRateDataStructureArray(){return rateDataStructureArray;}
	
	/**
	 * Sets the rate data structure array.
	 *
	 * @param newRateDataStructureArray the new rate data structure array
	 */
	public void setRateDataStructureArray(RateDataStructure[] newRateDataStructureArray){rateDataStructureArray = newRateDataStructureArray;}
	
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
	 * Gets the rate data structure.
	 *
	 * @return the rate data structure
	 */
	public RateDataStructure getRateDataStructure(){return rateDataStructure;}
	
	/**
	 * Sets the rate data structure.
	 *
	 * @param newRateDataStructure the new rate data structure
	 */
	public void setRateDataStructure(RateDataStructure newRateDataStructure){rateDataStructure = newRateDataStructure;}
	
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
	 * Gets the nuc data data structure.
	 *
	 * @return the nuc data data structure
	 */
	public NucDataDataStructure getNucDataDataStructure(){return nucDataDataStructure;}
	
	/**
	 * Sets the nuc data data structure.
	 *
	 * @param newNucDataDataStructure the new nuc data data structure
	 */
	public void setNucDataDataStructure(NucDataDataStructure newNucDataDataStructure){nucDataDataStructure = newNucDataDataStructure;}
	
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
	 * Gets the demo combo box.
	 *
	 * @return the demo combo box
	 */
	public int getDemoComboBox(){return demoComboBox;}
	
	/**
	 * Sets the demo combo box.
	 *
	 * @param newDemoComboBox the new demo combo box
	 */
	public void setDemoComboBox(int newDemoComboBox){demoComboBox = newDemoComboBox;}

	/**
	 * Gets the possible types.
	 *
	 * @return the possible types
	 */
	public String getPossibleTypes(){return possibleTypes;}
	
	/**
	 * Sets the possible types.
	 *
	 * @param newPossibleTypes the new possible types
	 */
	public void setPossibleTypes(String newPossibleTypes){possibleTypes = newPossibleTypes;}
	
	/**
	 * Gets the x units.
	 *
	 * @return the x units
	 */
	public String getXUnits(){return xUnits;}
	
	/**
	 * Sets the x units.
	 *
	 * @param newXUnits the new x units
	 */
	public void setXUnits(String newXUnits){
		
		xUnits = newXUnits;
		
		if(xUnits.equals("T0")){
		
			setXunits("T0");
		
		}else if(xUnits.equals("T3")){
		
			setXunits("T3");
		
		}else if(xUnits.equals("T6")){
		
			setXunits("T6");
		
		}else if(xUnits.equals("T9")){
		
			setXunits("T9");
		
		}else if(xUnits.equals("eV")){
		
			setXunits("EV");
		
		}else if(xUnits.equals("keV")){
		
			setXunits("KEV");
		
		}else if(xUnits.equals("MeV")){
		
			setXunits("MEV");
		
		}else if(xUnits.equals("GeV")){
		
			setXunits("GEV");
		
		}
			
	}
	
	/**
	 * Gets the y units.
	 *
	 * @return the y units
	 */
	public String getYUnits(){return yUnits;}
	
	/**
	 * Sets the y units.
	 *
	 * @param newYUnits the new y units
	 */
	public void setYUnits(String newYUnits){
		
		yUnits = newYUnits;
		
		if(yUnits.equals("b")){
		
			setYunits("B");
		
		}else if(yUnits.equals("kb")){
		
			setYunits("KB");
		
		}else if(yUnits.equals("Mb")){
		
			setYunits("MB");
		
		}else if(yUnits.equals("Gb")){
		
			setYunits("GB");
		
		}else if(yUnits.equals("eV-b")){
		
			setYunits("EV-B");
		
		}else if(yUnits.equals("keV-b")){
		
			setYunits("KEV-B");
		
		}else if(yUnits.equals("MeV-b")){
		
			setYunits("MEV-B");
		
		}else if(yUnits.equals("GeV-b")){
		
			setYunits("GEV-B");
		
		}
			
	}
	
	/**
	 * Gets the rate units.
	 *
	 * @return the rate units
	 */
	public String getRateUnits(){return rateUnits;}
	
	/**
	 * Sets the rate units.
	 *
	 * @param newRateUnits the new rate units
	 */
	public void setRateUnits(String newRateUnits){rateUnits = newRateUnits;}
	
	/**
	 * Gets the input type.
	 *
	 * @return the input type
	 */
	public String getInputType(){return inputType;}
	
	/**
	 * Sets the input type.
	 *
	 * @param newType the new input type
	 */
	public void setInputType(String newType){
		
		inputType = newType;
		setType(inputType);
		
	}
	
	/**
	 * Gets the input format.
	 *
	 * @return the input format
	 */
	public String getInputFormat(){return inputFormat;}
	
	/**
	 * Sets the input format.
	 *
	 * @param newFormat the new input format
	 */
	public void setInputFormat(String newFormat){
		
		inputFormat = newFormat;
		setFormat(inputFormat);
		
	}
	
	/**
	 * Gets the input filename.
	 *
	 * @return the input filename
	 */
	public String getInputFilename(){return inputFilename;}
	
	/**
	 * Sets the input filename.
	 *
	 * @param newFilename the new input filename
	 */
	public void setInputFilename(String newFilename){
		
		inputFilename = newFilename;
		setFilename(inputFilename);
	
	}
	
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
	public void setInputFile(String newFile){
		
		inputFile = newFile;
		setFile(inputFile);
	
	}
	
	/**
	 * Gets the input file dir.
	 *
	 * @return the input file dir
	 */
	public String getInputFileDir(){return inputFileDir;}
	
	/**
	 * Sets the input file dir.
	 *
	 * @param newFileDir the new input file dir
	 */
	public void setInputFileDir(String newFileDir){inputFileDir = newFileDir;}
	
	/**
	 * Gets the number points.
	 *
	 * @return the number points
	 */
	public int getNumberPoints(){return numberPoints;} 
	
	/**
	 * Sets the number points.
	 *
	 * @param newNumberPoints the new number points
	 */
	public void setNumberPoints(int newNumberPoints){numberPoints = newNumberPoints;}
	
	/**
	 * Gets the check input array.
	 *
	 * @return the check input array
	 */
	public String[] getCheckInputArray(){return checkInputArray;}
	
	/**
	 * Sets the check input array.
	 *
	 * @param newCheckInputArray the new check input array
	 */
	public void setCheckInputArray(String[] newCheckInputArray){
	
		checkInputArray = newCheckInputArray;	
	
		for(int i=0; i<5; i++){	
			
			switch(i){
			
				case 0:
				
					setPositive_chk(checkInputArray[i]);
				
					break;
					
				case 1:
				
					setSingle_chk(checkInputArray[i]);
				
					break;
					
				case 2:
				
					setRange_chk(checkInputArray[i]);
				
					break;
					
				case 3:
				
					setContinuity_chk(checkInputArray[i]);
				
					break;
					
				case 4:
				
					setError_chk(checkInputArray[i]);
				
					break;			
			
			}
		
		}
	
	}
	
	/**
	 * Gets the input warnings response array.
	 *
	 * @return the input warnings response array
	 */
	public String[] getInputWarningsResponseArray(){return inputWarningsResponseArray;}
	
	/**
	 * Sets the input warnings response array.
	 *
	 * @param newInputWarningsResponseArray the new input warnings response array
	 */
	public void setInputWarningsResponseArray(String[] newInputWarningsResponseArray){inputWarningsResponseArray = newInputWarningsResponseArray;}
	
	/**
	 * Gets the allowed tempmin.
	 *
	 * @return the allowed tempmin
	 */
	public double getAllowedTempmin(){return allowedTempmin;} 
	
	/**
	 * Sets the allowed tempmin.
	 *
	 * @param newAllowedTempmin the new allowed tempmin
	 */
	public void setAllowedTempmin(double newAllowedTempmin){allowedTempmin = newAllowedTempmin;}
	
	/**
	 * Gets the allowed tempmax.
	 *
	 * @return the allowed tempmax
	 */
	public double getAllowedTempmax(){return allowedTempmax;} 
	
	/**
	 * Sets the allowed tempmax.
	 *
	 * @param newAllowedTempmax the new allowed tempmax
	 */
	public void setAllowedTempmax(double newAllowedTempmax){allowedTempmax = newAllowedTempmax;}
	
	/**
	 * Gets the tempmin.
	 *
	 * @return the tempmin
	 */
	public double getTempmin(){return tempmin;} 
	
	/**
	 * Sets the tempmin.
	 *
	 * @param newTempmin the new tempmin
	 */
	public void setTempmin(double newTempmin){
		tempmin = newTempmin;
		setTmin(String.valueOf(tempmin));
	}
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	public double getTempmax(){return tempmax;} 
	
	/**
	 * Sets the tempmax.
	 *
	 * @param newTempmax the new tempmax
	 */
	public void setTempmax(double newTempmax){
		tempmax = newTempmax;
		setTmax(String.valueOf(tempmax));
	}
	
	/**
	 * Gets the output level.
	 *
	 * @return the output level
	 */
	public int getOutputLevel(){return outputLevel;} 
	
	/**
	 * Sets the output level.
	 *
	 * @param newOutputLevel the new output level
	 */
	public void setOutputLevel(int newOutputLevel){
		outputLevel = newOutputLevel;
		setPlevel(String.valueOf(outputLevel));
	}
	
	/**
	 * Gets the status text.
	 *
	 * @return the status text
	 */
	public String getStatusText(){return statusText;}
	
	/**
	 * Sets the status text.
	 *
	 * @param newStatusText the new status text
	 */
	public void setStatusText(String newStatusText){statusText = newStatusText;}
	
	/**
	 * Gets the prints the level.
	 *
	 * @return the prints the level
	 */
	public int getPrintLevel(){return printLevel;} 
	
	/**
	 * Sets the prints the level.
	 *
	 * @param newPrintLevel the new prints the level
	 */
	public void setPrintLevel(int newPrintLevel){printLevel = newPrintLevel;}
	
	/**
	 * Gets the number points rt.
	 *
	 * @return the number points rt
	 */
	public int getNumberPointsRT(){return numberPointsRT;} 
	
	/**
	 * Sets the number points rt.
	 *
	 * @param newNumberPointsRT the new number points rt
	 */
	public void setNumberPointsRT(int newNumberPointsRT){numberPointsRT = newNumberPointsRT;}
	
	/**
	 * Gets the tempmin rt.
	 *
	 * @return the tempmin rt
	 */
	public double getTempminRT(){return tempminRT;} 
	
	/**
	 * Sets the tempmin rt.
	 *
	 * @param newTempminRT the new tempmin rt
	 */
	public void setTempminRT(double newTempminRT){tempminRT = newTempminRT;}
	
	/**
	 * Gets the tempmax rt.
	 *
	 * @return the tempmax rt
	 */
	public double getTempmaxRT(){return tempmaxRT;} 
	
	/**
	 * Sets the tempmax rt.
	 *
	 * @param newTempmaxRT the new tempmax rt
	 */
	public void setTempmaxRT(double newTempmaxRT){tempmaxRT = newTempmaxRT;}
	
	/**
	 * Gets the ratemin.
	 *
	 * @return the ratemin
	 */
	public double getRatemin(){return ratemin;} 
	
	/**
	 * Sets the ratemin.
	 *
	 * @param newRatemin the new ratemin
	 */
	public void setRatemin(double newRatemin){ratemin = newRatemin;}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	public double getRatemax(){return ratemax;} 
	
	/**
	 * Sets the ratemax.
	 *
	 * @param newRatemax the new ratemax
	 */
	public void setRatemax(double newRatemax){ratemax = newRatemax;}
	
	/**
	 * Gets the tempmin param.
	 *
	 * @return the tempmin param
	 */
	public double getTempminParam(){return tempminParam;} 
	
	/**
	 * Sets the tempmin param.
	 *
	 * @param newTempminParam the new tempmin param
	 */
	public void setTempminParam(double newTempminParam){
		tempminParam = newTempminParam;
		setTminparam(String.valueOf(tempminParam));
	}
	
	/**
	 * Gets the tempmax param.
	 *
	 * @return the tempmax param
	 */
	public double getTempmaxParam(){return tempmaxParam;} 
	
	/**
	 * Sets the tempmax param.
	 *
	 * @param newTempmaxParam the new tempmax param
	 */
	public void setTempmaxParam(double newTempmaxParam){
		tempmaxParam = newTempmaxParam;
		setTmaxparam(String.valueOf(tempmaxParam));
	}

	/**
	 * Gets the param string array.
	 *
	 * @return the param string array
	 */
	public String[] getParamStringArray(){return paramStringArray;}
	
	/**
	 * Sets the param string array.
	 *
	 * @param newParamStringArray the new param string array
	 */
	public void setParamStringArray(String[] newParamStringArray){paramStringArray = newParamStringArray;}
	
	/**
	 * Gets the inverse param array.
	 *
	 * @return the inverse param array
	 */
	public double[] getInverseParamArray(){return inverseParamArray;}
	
	/**
	 * Sets the inverse param array.
	 *
	 * @param newInverseParamArray the new inverse param array
	 */
	public void setInverseParamArray(double[] newInverseParamArray){inverseParamArray = newInverseParamArray;}
	
	/**
	 * Gets the inverse param string array.
	 *
	 * @return the inverse param string array
	 */
	public String[] getInverseParamStringArray(){return inverseParamStringArray;}
	
	/**
	 * Sets the inverse param string array.
	 *
	 * @param newInverseParamStringArray the new inverse param string array
	 */
	public void setInverseParamStringArray(String[] newInverseParamStringArray){inverseParamStringArray = newInverseParamStringArray;}
	
	/**
	 * Gets the number points param.
	 *
	 * @return the number points param
	 */
	public int getNumberPointsParam(){return numberPointsParam;} 
	
	/**
	 * Sets the number points param.
	 *
	 * @param newNumberPointsParam the new number points param
	 */
	public void setNumberPointsParam(int newNumberPointsParam){numberPointsParam = newNumberPointsParam;}
	
	/**
	 * Gets the tempmin param out.
	 *
	 * @return the tempmin param out
	 */
	public double getTempminParamOut(){return tempminParamOut;} 
	
	/**
	 * Sets the tempmin param out.
	 *
	 * @param newTempminParamOut the new tempmin param out
	 */
	public void setTempminParamOut(double newTempminParamOut){tempminParamOut = newTempminParamOut;}
	
	/**
	 * Gets the tempmax param out.
	 *
	 * @return the tempmax param out
	 */
	public double getTempmaxParamOut(){return tempmaxParamOut;} 
	
	/**
	 * Sets the tempmax param out.
	 *
	 * @param newTempmaxParamOut the new tempmax param out
	 */
	public void setTempmaxParamOut(double newTempmaxParamOut){tempmaxParamOut = newTempmaxParamOut;}
	
	/**
	 * Gets the ratemin param out.
	 *
	 * @return the ratemin param out
	 */
	public double getRateminParamOut(){return rateminParamOut;} 
	
	/**
	 * Sets the ratemin param out.
	 *
	 * @param newRateminParamOut the new ratemin param out
	 */
	public void setRateminParamOut(double newRateminParamOut){rateminParamOut = newRateminParamOut;}
	
	/**
	 * Gets the ratemax param out.
	 *
	 * @return the ratemax param out
	 */
	public double getRatemaxParamOut(){return ratemaxParamOut;} 
	
	/**
	 * Sets the ratemax param out.
	 *
	 * @param newRatemaxParamOut the new ratemax param out
	 */
	public void setRatemaxParamOut(double newRatemaxParamOut){ratemaxParamOut = newRatemaxParamOut;}
	
	/**
	 * Gets the max percent diff allowed.
	 *
	 * @return the max percent diff allowed
	 */
	public double getMaxPercentDiffAllowed(){return maxPercentDiffAllowed;} 
	
	/**
	 * Sets the max percent diff allowed.
	 *
	 * @param newMaxPercentDiffAllowed the new max percent diff allowed
	 */
	public void setMaxPercentDiffAllowed(double newMaxPercentDiffAllowed){
		maxPercentDiffAllowed = newMaxPercentDiffAllowed;
		setMax_diff(String.valueOf(maxPercentDiffAllowed));
	}

	/**
	 * Gets the parm check.
	 *
	 * @return the parm check
	 */
	public boolean getParmCheck(){return parmCheck;} 
	
	/**
	 * Sets the parm check.
	 *
	 * @param newParmCheck the new parm check
	 */
	public void setParmCheck(boolean newParmCheck){
		parmCheck = newParmCheck;
		
		if(parmCheck){
			
			setParm_check("Y");
			
		}else{
			
			setParm_check("N");
		
		}
	}
	
	/**
	 * Gets the temp data array.
	 *
	 * @return the temp data array
	 */
	public double[] getTempDataArray(){return TempDataArray;}
	
	/**
	 * Sets the temp data array.
	 *
	 * @param newTempDataArray the new temp data array
	 */
	public void setTempDataArray(double[] newTempDataArray){TempDataArray = newTempDataArray;}
	
	/**
	 * Gets the rate data array.
	 *
	 * @return the rate data array
	 */
	public double[] getRateDataArray(){return RateDataArray;}
	
	/**
	 * Sets the rate data array.
	 *
	 * @param newRateDataArray the new rate data array
	 */
	public void setRateDataArray(double[] newRateDataArray){RateDataArray = newRateDataArray;}

	/**
	 * Gets the temp data array orig.
	 *
	 * @return the temp data array orig
	 */
	public double[] getTempDataArrayOrig(){return TempDataArrayOrig;}
	
	/**
	 * Sets the temp data array orig.
	 *
	 * @param newTempDataArrayOrig the new temp data array orig
	 */
	public void setTempDataArrayOrig(double[] newTempDataArrayOrig){TempDataArrayOrig = newTempDataArrayOrig;}
	
	/**
	 * Gets the rate data array orig.
	 *
	 * @return the rate data array orig
	 */
	public double[] getRateDataArrayOrig(){return RateDataArrayOrig;}
	
	/**
	 * Sets the rate data array orig.
	 *
	 * @param newRateDataArrayOrig the new rate data array orig
	 */
	public void setRateDataArrayOrig(double[] newRateDataArrayOrig){RateDataArrayOrig = newRateDataArrayOrig;}

	/**
	 * Gets the low temp data array.
	 *
	 * @return the low temp data array
	 */
	public double[] getLowTempDataArray(){return LowTempDataArray;}
	
	/**
	 * Sets the low temp data array.
	 *
	 * @param newLowTempDataArray the new low temp data array
	 */
	public void setLowTempDataArray(double[] newLowTempDataArray){LowTempDataArray = newLowTempDataArray;}
	
	/**
	 * Gets the high temp data array.
	 *
	 * @return the high temp data array
	 */
	public double[] getHighTempDataArray(){return HighTempDataArray;}
	
	/**
	 * Sets the high temp data array.
	 *
	 * @param newHighTempDataArray the new high temp data array
	 */
	public void setHighTempDataArray(double[] newHighTempDataArray){HighTempDataArray = newHighTempDataArray;}
	
	/**
	 * Gets the low rate data array.
	 *
	 * @return the low rate data array
	 */
	public double[] getLowRateDataArray(){return LowRateDataArray;}
	
	/**
	 * Sets the low rate data array.
	 *
	 * @param newLowRateDataArray the new low rate data array
	 */
	public void setLowRateDataArray(double[] newLowRateDataArray){LowRateDataArray = newLowRateDataArray;}
	
	/**
	 * Gets the high rate data array.
	 *
	 * @return the high rate data array
	 */
	public double[] getHighRateDataArray(){return HighRateDataArray;}
	
	/**
	 * Sets the high rate data array.
	 *
	 * @param newHighRateDataArray the new high rate data array
	 */
	public void setHighRateDataArray(double[] newHighRateDataArray){HighRateDataArray = newHighRateDataArray;}
	
	/**
	 * Gets the temp param data array.
	 *
	 * @return the temp param data array
	 */
	public double[] getTempParamDataArray(){return TempParamDataArray;}
	
	/**
	 * Sets the temp param data array.
	 *
	 * @param newTempParamDataArray the new temp param data array
	 */
	public void setTempParamDataArray(double[] newTempParamDataArray){TempParamDataArray = newTempParamDataArray;}
	
	/**
	 * Gets the rate param data array.
	 *
	 * @return the rate param data array
	 */
	public double[] getRateParamDataArray(){return RateParamDataArray;}
	
	/**
	 * Sets the rate param data array.
	 *
	 * @param newRateParamDataArray the new rate param data array
	 */
	public void setRateParamDataArray(double[] newRateParamDataArray){RateParamDataArray = newRateParamDataArray;}
	
	/**
	 * Gets the percent diff data array.
	 *
	 * @return the percent diff data array
	 */
	public double[] getPercentDiffDataArray(){return PercentDiffDataArray;}
	
	/**
	 * Sets the percent diff data array.
	 *
	 * @param newPercentDiffDataArray the new percent diff data array
	 */
	public void setPercentDiffDataArray(double[] newPercentDiffDataArray){PercentDiffDataArray = newPercentDiffDataArray;}

	/**
	 * Gets the temp data array extra.
	 *
	 * @return the temp data array extra
	 */
	public double[] getTempDataArrayExtra(){return TempDataArrayExtra;}
	
	/**
	 * Sets the temp data array extra.
	 *
	 * @param newTempDataArrayExtra the new temp data array extra
	 */
	public void setTempDataArrayExtra(double[] newTempDataArrayExtra){TempDataArrayExtra = newTempDataArrayExtra;}
	
	/**
	 * Gets the rate data array extra.
	 *
	 * @return the rate data array extra
	 */
	public double[] getRateDataArrayExtra(){return RateDataArrayExtra;}
	
	/**
	 * Sets the rate data array extra.
	 *
	 * @param newRateDataArrayExtra the new rate data array extra
	 */
	public void setRateDataArrayExtra(double[] newRateDataArrayExtra){RateDataArrayExtra = newRateDataArrayExtra;}

	/**
	 * Gets the extra points.
	 *
	 * @return the extra points
	 */
	public boolean getExtraPoints(){return extraPoints;}
	
	/**
	 * Sets the extra points.
	 *
	 * @param newExtraPoints the new extra points
	 */
	public void setExtraPoints(boolean newExtraPoints){extraPoints = newExtraPoints;}
	
	/**
	 * Gets the number starting params.
	 *
	 * @return the number starting params
	 */
	public int getNumberStartingParams(){return numberStartingParams;} 
	
	/**
	 * Sets the number starting params.
	 *
	 * @param newNumberStartingParams the new number starting params
	 */
	public void setNumberStartingParams(int newNumberStartingParams){
		numberStartingParams = newNumberStartingParams;
		setS_parm_num(String.valueOf(numberStartingParams));
	}
	
	/**
	 * Gets the prints the level param.
	 *
	 * @return the prints the level param
	 */
	public int getPrintLevelParam(){return printLevelParam;} 
	
	/**
	 * Sets the prints the level param.
	 *
	 * @param newPrintLevelParam the new prints the level param
	 */
	public void setPrintLevelParam(int newPrintLevelParam){
		printLevelParam = newPrintLevelParam;
		setPlevelparam(String.valueOf(printLevelParam));
	}
	
	/**
	 * Gets the max number iterations.
	 *
	 * @return the max number iterations
	 */
	public int getMaxNumberIterations(){return maxNumberIterations;} 
	
	/**
	 * Sets the max number iterations.
	 *
	 * @param newMaxNumberIterations the new max number iterations
	 */
	public void setMaxNumberIterations(int newMaxNumberIterations){
		maxNumberIterations = newMaxNumberIterations;
		setIterations(String.valueOf(maxNumberIterations));
	}
	
	/**
	 * Gets the technique param.
	 *
	 * @return the technique param
	 */
	public String getTechniqueParam(){return techniqueParam;} 
	
	/**
	 * Sets the technique param.
	 *
	 * @param newTechniqueParam the new technique param
	 */
	public void setTechniqueParam(String newTechniqueParam){
		techniqueParam = newTechniqueParam;
		setMethod(techniqueParam);
	}
	
	/**
	 * Gets the current nuc data string.
	 *
	 * @return the current nuc data string
	 */
	public String getCurrentNucDataString(){return currentNucDataString;}
	
	/**
	 * Sets the current nuc data string.
	 *
	 * @param newCurrentNucDataString the new current nuc data string
	 */
	public void setCurrentNucDataString(String newCurrentNucDataString){currentNucDataString = newCurrentNucDataString;}
	
	/**
	 * Gets the current rate string.
	 *
	 * @return the current rate string
	 */
	public String getCurrentRateString(){return currentRateString;}
	
	/**
	 * Sets the current rate string.
	 *
	 * @param newCurrentRateString the new current rate string
	 */
	public void setCurrentRateString(String newCurrentRateString){currentRateString = newCurrentRateString;}
	
	/**
	 * Gets the current nuc data set string.
	 *
	 * @return the current nuc data set string
	 */
	public String getCurrentNucDataSetString(){return currentNucDataSetString;}
	
	/**
	 * Sets the current nuc data set string.
	 *
	 * @param newCurrentNucDataSetString the new current nuc data set string
	 */
	public void setCurrentNucDataSetString(String newCurrentNucDataSetString){currentNucDataSetString = newCurrentNucDataSetString;}
	
	/**
	 * Gets the temp behavior string.
	 *
	 * @return the temp behavior string
	 */
	public String getTempBehaviorString(){return tempBehaviorString;}
	
	/**
	 * Sets the temp behavior string.
	 *
	 * @param newTempBehaviorString the new temp behavior string
	 */
	public void setTempBehaviorString(String newTempBehaviorString){tempBehaviorString = newTempBehaviorString;}
	
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
	 * Gets the pasted data.
	 *
	 * @return the pasted data
	 */
	public boolean getPastedData(){return pastedData;}
	
	/**
	 * Sets the pasted data.
	 *
	 * @param newPastedData the new pasted data
	 */
	public void setPastedData(boolean newPastedData){pastedData = newPastedData;}

	/**
	 * Gets the upload data.
	 *
	 * @return the upload data
	 */
	public boolean getUploadData(){return uploadData;}
	
	/**
	 * Sets the upload data.
	 *
	 * @param newUploadData the new upload data
	 */
	public void setUploadData(boolean newUploadData){uploadData = newUploadData;}

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
	 * Gets the slope array.
	 *
	 * @return the slope array
	 */
	public double[] getSlopeArray(){return slopeArray;}
	
	/**
	 * Sets the slope array.
	 *
	 * @param newSlopeArray the new slope array
	 */
	public void setSlopeArray(double[] newSlopeArray){slopeArray = newSlopeArray;}
	
	/**
	 * Gets the number high e points max.
	 *
	 * @return the number high e points max
	 */
	public int getNumberHighEPointsMax(){return numberHighEPointsMax;}
	
	/**
	 * Sets the number high e points max.
	 *
	 * @param newNumberHighEPointsMax the new number high e points max
	 */
	public void setNumberHighEPointsMax(int newNumberHighEPointsMax){numberHighEPointsMax = newNumberHighEPointsMax;}	

	/**
	 * Gets the number low e points max.
	 *
	 * @return the number low e points max
	 */
	public int getNumberLowEPointsMax(){return numberLowEPointsMax;}
	
	/**
	 * Sets the number low e points max.
	 *
	 * @param newNumberLowEPointsMax the new number low e points max
	 */
	public void setNumberLowEPointsMax(int newNumberLowEPointsMax){numberLowEPointsMax = newNumberLowEPointsMax;}
	
	/**
	 * Gets the rate submitted.
	 *
	 * @return the rate submitted
	 */
	public boolean getRateSubmitted(){return rateSubmitted;}
	
	/**
	 * Sets the rate submitted.
	 *
	 * @param newRateSubmitted the new rate submitted
	 */
	public void setRateSubmitted(boolean newRateSubmitted){rateSubmitted = newRateSubmitted;}
	
	/**
	 * Gets the rate modified.
	 *
	 * @return the rate modified
	 */
	public boolean getRateModified(){return rateModified;}
	
	/**
	 * Sets the rate modified.
	 *
	 * @param newRateModified the new rate modified
	 */
	public void setRateModified(boolean newRateModified){rateModified = newRateModified;}
	
	/**
	 * Gets the low temp.
	 *
	 * @return the low temp
	 */
	public boolean getLowTemp(){return lowTemp;}
	
	/**
	 * Sets the low temp.
	 *
	 * @param newLowTemp the new low temp
	 */
	public void setLowTemp(boolean newLowTemp){lowTemp = newLowTemp;}
	
	/**
	 * Gets the high temp.
	 *
	 * @return the high temp
	 */
	public boolean getHighTemp(){return highTemp;}
	
	/**
	 * Sets the high temp.
	 *
	 * @param newHighTemp the new high temp
	 */
	public void setHighTemp(boolean newHighTemp){highTemp = newHighTemp;}
	
	/////////////////////////////CinaCGIComm///////////////////////////////////////////////
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
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody(){return body;} 
	
	/**
	 * Sets the body.
	 *
	 * @param newBody the new body
	 */
	public void setBody(String newBody){body = newBody;}
	
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
	
	/**
	 * Gets the xunits.
	 *
	 * @return the xunits
	 */
	public String getXunits(){return xunits;} 
	
	/**
	 * Sets the xunits.
	 *
	 * @param newXunits the new xunits
	 */
	public void setXunits(String newXunits){xunits = newXunits;}
	
	/**
	 * Gets the yunits.
	 *
	 * @return the yunits
	 */
	public String getYunits(){return yunits;} 
	
	/**
	 * Sets the yunits.
	 *
	 * @param newYunits the new yunits
	 */
	public void setYunits(String newYunits){yunits = newYunits;}
	
	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename(){return filename;} 
	
	/**
	 * Sets the filename.
	 *
	 * @param newFilename the new filename
	 */
	public void setFilename(String newFilename){filename = newFilename;}
	
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public String getFile(){return file;} 
	
	/**
	 * Sets the file.
	 *
	 * @param newFile the new file
	 */
	public void setFile(String newFile){file = newFile;}
			
	/**
	 * Gets the positive_chk.
	 *
	 * @return the positive_chk
	 */
	public String getPositive_chk(){return positive_chk;} 
	
	/**
	 * Sets the positive_chk.
	 *
	 * @param newPositive_chk the new positive_chk
	 */
	public void setPositive_chk(String newPositive_chk){positive_chk = newPositive_chk;}
	
	/**
	 * Gets the single_chk.
	 *
	 * @return the single_chk
	 */
	public String getSingle_chk(){return single_chk;} 
	
	/**
	 * Sets the single_chk.
	 *
	 * @param newSingle_chk the new single_chk
	 */
	public void setSingle_chk(String newSingle_chk){single_chk = newSingle_chk;}
	
	/**
	 * Gets the range_chk.
	 *
	 * @return the range_chk
	 */
	public String getRange_chk(){return range_chk;} 
	
	/**
	 * Sets the range_chk.
	 *
	 * @param newRange_chk the new range_chk
	 */
	public void setRange_chk(String newRange_chk){range_chk = newRange_chk;}
	
	/**
	 * Gets the continuity_chk.
	 *
	 * @return the continuity_chk
	 */
	public String getContinuity_chk(){return continuity_chk;} 
	
	/**
	 * Sets the continuity_chk.
	 *
	 * @param newContinuity_chk the new continuity_chk
	 */
	public void setContinuity_chk(String newContinuity_chk){continuity_chk = newContinuity_chk;}
	
	/**
	 * Gets the error_chk.
	 *
	 * @return the error_chk
	 */
	public String getError_chk(){return error_chk;} 
	
	/**
	 * Sets the error_chk.
	 *
	 * @param newError_chk the new error_chk
	 */
	public void setError_chk(String newError_chk){error_chk = newError_chk;}
	
	/**
	 * Gets the reaction_chk.
	 *
	 * @return the reaction_chk
	 */
	public String getReaction_chk(){return reaction_chk;} 
	
	/**
	 * Sets the reaction_chk.
	 *
	 * @param newReaction_chk the new reaction_chk
	 */
	public void setReaction_chk(String newReaction_chk){reaction_chk = newReaction_chk;}
	
	/**
	 * Gets the tmin.
	 *
	 * @return the tmin
	 */
	public String getTmin(){return tmin;} 
	
	/**
	 * Sets the tmin.
	 *
	 * @param newTmin the new tmin
	 */
	public void setTmin(String newTmin){tmin = newTmin;}
	
	/**
	 * Gets the tmax.
	 *
	 * @return the tmax
	 */
	public String getTmax(){return tmax;} 
	
	/**
	 * Sets the tmax.
	 *
	 * @param newTmax the new tmax
	 */
	public void setTmax(String newTmax){tmax = newTmax;}
	
	/**
	 * Gets the plevel.
	 *
	 * @return the plevel
	 */
	public String getPlevel(){return plevel;} 
	
	/**
	 * Sets the plevel.
	 *
	 * @param newPlevel the new plevel
	 */
	public void setPlevel(String newPlevel){plevel = newPlevel;}
	
	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod(){return method;} 
	
	/**
	 * Sets the method.
	 *
	 * @param newMethod the new method
	 */
	public void setMethod(String newMethod){method = newMethod;}
	
	/**
	 * Gets the iterations.
	 *
	 * @return the iterations
	 */
	public String getIterations(){return iterations;} 
	
	/**
	 * Sets the iterations.
	 *
	 * @param newIterations the new iterations
	 */
	public void setIterations(String newIterations){iterations = newIterations;}
	
	/**
	 * Gets the tminparam.
	 *
	 * @return the tminparam
	 */
	public String getTminparam(){return tminparam;} 
	
	/**
	 * Sets the tminparam.
	 *
	 * @param newTminparam the new tminparam
	 */
	public void setTminparam(String newTminparam){tminparam = newTminparam;}
	
	/**
	 * Gets the tmaxparam.
	 *
	 * @return the tmaxparam
	 */
	public String getTmaxparam(){return tmaxparam;} 
	
	/**
	 * Sets the tmaxparam.
	 *
	 * @param newTmaxparam the new tmaxparam
	 */
	public void setTmaxparam(String newTmaxparam){tmaxparam = newTmaxparam;}
	
	/**
	 * Gets the plevelparam.
	 *
	 * @return the plevelparam
	 */
	public String getPlevelparam(){return plevelparam;} 
	
	/**
	 * Sets the plevelparam.
	 *
	 * @param newPlevelparam the new plevelparam
	 */
	public void setPlevelparam(String newPlevelparam){plevelparam = newPlevelparam;}
	
	/**
	 * Gets the s_parm_num.
	 *
	 * @return the s_parm_num
	 */
	public String getS_parm_num(){return s_parm_num;} 
	
	/**
	 * Sets the s_parm_num.
	 *
	 * @param newS_parm_num the new s_parm_num
	 */
	public void setS_parm_num(String newS_parm_num){s_parm_num = newS_parm_num;}
	
	/**
	 * Gets the start_parms.
	 *
	 * @return the start_parms
	 */
	public String getStart_parms(){return start_parms;} 
	
	/**
	 * Sets the start_parms.
	 *
	 * @param newStart_parms the new start_parms
	 */
	public void setStart_parms(String newStart_parms){start_parms = newStart_parms;}
	
	/**
	 * Gets the parm_check.
	 *
	 * @return the parm_check
	 */
	public String getParm_check(){return parm_check;} 
	
	/**
	 * Sets the parm_check.
	 *
	 * @param newParm_check the new parm_check
	 */
	public void setParm_check(String newParm_check){parm_check = newParm_check;}
	
	/**
	 * Gets the max_diff.
	 *
	 * @return the max_diff
	 */
	public String getMax_diff(){return max_diff;} 
	
	/**
	 * Sets the max_diff.
	 *
	 * @param newMax_diff the new max_diff
	 */
	public void setMax_diff(String newMax_diff){max_diff = newMax_diff;}
	
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
	 * Gets the orig max percent diff.
	 *
	 * @return the orig max percent diff
	 */
	public double getOrigMaxPercentDiff(){
	
		double diff = 0.0;
		
		double[] paramArray = getRateParamDataArray();
		double[] dataArray = getRateDataArrayOrig();
		
		for(int i=0; i<paramArray.length; i++){
		
			double newDiff = Math.abs((paramArray[i]-dataArray[i])/dataArray[i]);
			diff = Math.max(diff, newDiff);
			
		}
		
		if(!String.valueOf(diff).equalsIgnoreCase("infinity")){
			
			diff = Double.valueOf(new PrintfFormat("%12.4G").sprintf(100*diff)).doubleValue();
		
			
		}else{
			diff = Double.POSITIVE_INFINITY;
		}
			
		return diff;
	
	}
	
	/**
	 * Gets the orig chisquared.
	 *
	 * @return the orig chisquared
	 */
	public double getOrigChisquared(){
	
		double chi = 0.0;
		
		double[] paramArray = getRateParamDataArray();
		double[] dataArray = getRateDataArrayOrig();
		
		for(int i=0; i<paramArray.length; i++){
		
			double arg = (paramArray[i]-dataArray[i])/(0.01*dataArray[i]);
			double term = Math.pow(arg, 2);
		
			chi += term;
			
		}
		
		if(!String.valueOf(chi).equalsIgnoreCase("infinity")){
			chi = Double.valueOf(new PrintfFormat("%12.3G").sprintf(chi)).doubleValue();
		}else{
			chi = Double.POSITIVE_INFINITY;
		}
		
		return chi; 
	
	}
	
}