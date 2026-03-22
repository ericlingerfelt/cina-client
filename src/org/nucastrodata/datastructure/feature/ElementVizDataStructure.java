package org.nucastrodata.datastructure.feature;

import java.util.*;
import java.awt.*; 

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.feature.AbundRowData;
import org.nucastrodata.datastructure.feature.DerRowData;
import org.nucastrodata.datastructure.feature.FluxRowData;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;
import org.nucastrodata.enums.FolderType;

public class ElementVizDataStructure implements DataStructure{
	
	String[] abundFileNameArray;
	String libraryName;
	TreeMap<String, TreeMap<Double, NucSimDataStructure>> sensMap;
	TreeMap<String, TreeMap<IsotopePoint, Double>> finalAbundMap;
	Vector isotopeViktorAbundTable;
	Vector isotopeViktorAbund;
	Vector isotopeViktorWeight;
	Vector isotopeViktorScale;
	Vector nucSimVector;
	int numberUserNucSimSetDataStructures;
	int numberSharedNucSimSetDataStructures;
	int numberNucSimSetDataStructures;
	int numberPublicLibraryDataStructures;
	int numberUserLibraryDataStructures;
	int numberSharedLibraryDataStructures;
	LibraryDataStructure[] publicLibraryDataStructureArray, userLibraryDataStructureArray, sharedLibraryDataStructureArray;
	NucSimSetDataStructure[] userNucSimSetDataStructureArray, sharedNucSimSetDataStructureArray;
	LibraryDataStructure isotopePadLibraryDataStructure;
	NucSimSetDataStructure[] finalNucSimSetDataStructureArray, zoneNucSimSetDataStructureArray;
	NucSimSetDataStructure finalNucSimSetDataStructure;
	NucSimSetDataStructure currentNucSimSetDataStructure;
	int numberNucSimDataStructures;
	NucSimDataStructure[] nucSimDataStructureArrayFinalAbund;
	NucSimDataStructure[] nucSimDataStructureArray;
	NucSimDataStructure[] nucSimDataStructureArrayAnimator;
	NucSimDataStructure[] nucSimDataStructureArrayIntFlux;
	NucSimDataStructure[] nucSimDataStructureArrayScale;
	NucSimDataStructure animatorNucSimDataStructure;
	NucSimDataStructure intFluxNucSimDataStructure;
	NucSimDataStructure currentNucSimDataStructure;
	RateDataStructure rateDataStructure;
	public HashMap<Integer, HashMap<String, RateDataStructure>> map;
	public int zaIndex;
	double[][] timeDataArrayAbund;
	double[][] timeDataArrayThermo;
	double[][] timeDataArrayEdot;
	double[][] massDataArrayAbund;
	double[][] abundDataArray;
	double[][] ratioDataArrayAbund;
	double[][] densityDataArray;
	double[][] edotDataArray;
	double[][] tempDataArray;
	boolean[] abundNonZeroArray;
	boolean[] ratioNonZeroArrayAbund;
	boolean[] includeIsotopeArray;
	double[][] zoneDataArray;
	double[][] weightDataArray;
	double[][] scaleDataArray;
	boolean[] weightNonZeroArray;
	boolean[] scaleNonZeroArray;
	double[][] massDataArrayWeight;
	double[][] ratioDataArrayWeight;
	double[][] massDataArrayScale;
	double[][] ratioDataArrayScale;
	boolean[] ratioNonZeroArrayWeight;
	boolean[] ratioNonZeroArrayScale;
	int densitymin;
	int densitymax;
	int abundmin;
	int abundmax;
	int massminAbund;
	int massmaxAbund;
	int massminWeight;
	int massmaxWeight;
	int massminScale;
	int massmaxScale;
	int zonemin;
	int zonemax;
	int weightmin;
	int weightmax;
	int scalemin;
	int scalemax;
	int edotmin;
	int edotmax;
	double ratiominAbund;
	double ratiomaxAbund;
	double ratiominWeight;
	double ratiomaxWeight;
	double ratiominScale;
	double ratiomaxScale;
	double tempmin;
	double tempmax;
	double timeminAbund;
	double timemaxAbund;
	double timeminEdot;
	double timemaxEdot;
	double timeminThermo;
	double timemaxThermo;
	double[] normalTimeArray;
	int maxNumberTimePoints;
	int maxNumberZonePoints;
	int numberIsotopeRunsAbund;
	int numberIsotopeRunsWeight;
	int numberIsotopeRunsScale;
	String elementSynthMovieReport; 
	String animatorType;
	double[] abundColorValues;
	double[] derColorValues;
	double[] fluxColorValues;
	double[] finalAbundColorValues;
	double[] finalWeightedAbundColorValues;
	double[] intFluxColorValues;
	Vector abundBinData;
	Vector derBinData;
	Vector fluxBinData;
	Vector finalAbundBinData;
	Vector finalWeightedAbundBinData;
	Vector intFluxBinData;
	String abundScheme;
	String derScheme;
	String fluxScheme;
	String finalAbundScheme;
	String finalWeightedAbundScheme;
	String intFluxScheme;
	boolean abundIncludeValues;
	boolean derIncludeValues;
	boolean fluxIncludeValues;
	boolean finalAbundIncludeValues;
	boolean finalWeightedAbundIncludeValues;
	boolean intFluxIncludeValues;
	String path;
	String zones;
	String zone;
	String isotopes;
	String library;
	String reactions;
	String arguments;
	String simulation;
	String frame_skip_interval;
	String sum;
	String final_step;
	String rates;
	String properties;
	String group;
	String paths;
	String width;
	String height;
	String scaleFactor;
	String compFactor;
	private FolderType folderType;
	
	public ElementVizDataStructure(){initialize();}
	
	public void initialize(){
		sensMap = null;
		
		setAbundFileNameArray(null);

		setFinalAbundMap(null);
		
		setNumberPublicLibraryDataStructures(0);
		setNumberUserLibraryDataStructures(0);
		setNumberSharedLibraryDataStructures(0);
		
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		
		setLibraryName("");
		
		setIsotopeViktorAbundTable(new Vector());
		setIsotopeViktorAbund(new Vector());
		setIsotopeViktorWeight(new Vector());
		setIsotopeViktorScale(new Vector());
		
		setNucSimVector(new Vector());
		
		setNumberUserNucSimSetDataStructures(0);
		setNumberSharedNucSimSetDataStructures(0);
		
		setUserNucSimSetDataStructureArray(null);
		setSharedNucSimSetDataStructureArray(null);

		setFinalNucSimSetDataStructureArray(null);
		setZoneNucSimSetDataStructureArray(null);
		setCurrentNucSimSetDataStructure(null);
		setFinalNucSimSetDataStructure(null);
		setNumberNucSimDataStructures(0);
		setNumberNucSimSetDataStructures(0);
			
		setNucSimDataStructureArray(null);
		setNucSimDataStructureArrayAnimator(null);
		setNucSimDataStructureArrayIntFlux(null);
		setNucSimDataStructureArrayFinalAbund(null);
		setNucSimDataStructureArrayScale(null);
		setCurrentNucSimDataStructure(null);
		setAnimatorNucSimDataStructure(null);
		setIntFluxNucSimDataStructure(null);
		
		setIsotopePadLibraryDataStructure(null);
		
		setTimeDataArrayAbund(null);
		setTimeDataArrayThermo(null);
		setMassDataArrayAbund(null);
		
		setAbundDataArray(null);
		setRatioDataArrayAbund(null);
		setTempDataArray(null);
		setDensityDataArray(null);
		
		setAbundNonZeroArray(null);
		setRatioNonZeroArrayAbund(null);
		setIncludeIsotopeArray(null);
		
		setZoneDataArray(null);
		setWeightDataArray(null);
		setScaleDataArray(null);
		setWeightNonZeroArray(null);
		setScaleNonZeroArray(null);
		setMassDataArrayWeight(null);
		setRatioDataArrayWeight(null);
		setMassDataArrayScale(null);
		setRatioDataArrayScale(null);
		setRatioNonZeroArrayWeight(null);
		setRatioNonZeroArrayScale(null);
		
		setDensitymin(0);
		setDensitymax(0);
		
		setAbundmin(0);
		setAbundmax(0);
		
		setWeightmin(0);
		setWeightmax(0);
		
		setScalemin(0);
		setScalemax(0);
		
		setZonemin(0);
		setZonemax(0);
		
		setMassminAbund(0);
		setMassmaxAbund(0);
		
		setMassminWeight(0);
		setMassmaxWeight(0);
		
		setMassminScale(0);
		setMassmaxScale(0);
		
		setRatiominAbund(0);
		setRatiomaxAbund(0);
		
		setRatiominWeight(0);
		setRatiomaxWeight(0);
		
		setRatiominScale(0);
		setRatiomaxScale(0);
		
		setTempmin(0);
		setTempmax(0);
		
		setEdotmin(0);
		setEdotmax(0);
		
		setTimeminAbund(0);
		setTimemaxAbund(0);
		
		setTimeminEdot(0);
		setTimemaxEdot(0);
		
		setTimeminThermo(0);
		setTimemaxThermo(0);
		
		setNormalTimeArray(null);
		
		setMaxNumberTimePoints(0);
		setMaxNumberZonePoints(0);
		
		setNumberIsotopeRunsAbund(0);
		setNumberIsotopeRunsWeight(0);
		setNumberIsotopeRunsScale(0);
		
		setElementSynthMovieReport("");
		
		setAnimatorType("");
		
		double[] initColorValuesArrayAbund = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
		double[] initColorValuesArrayDer = {1.0, 0.0, 0.0, 0.5, 0.0, 0.5};
		double[] initColorValuesArrayFlux = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
		double[] initColorValuesArrayFinalAbund = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		double[] initColorValuesArrayFinalWeightedAbund = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
		double[] initColorValuesArrayIntFlux = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
		setAbundColorValues(initColorValuesArrayAbund);
		setDerColorValues(initColorValuesArrayDer);
		setFluxColorValues(initColorValuesArrayFlux);
		setFinalAbundColorValues(initColorValuesArrayFinalAbund);
		setFinalWeightedAbundColorValues(initColorValuesArrayFinalWeightedAbund);
		setIntFluxColorValues(initColorValuesArrayIntFlux);
		
		Vector abundColumnData = new Vector();
		abundColumnData.addElement(new AbundRowData(-5, 0, true, new Color (255,0,0)));
		abundColumnData.addElement(new AbundRowData(-10, -5, true, new Color(215,0,0)));
		abundColumnData.addElement(new AbundRowData(-15, -10, true, new Color(175,0,0)));
		abundColumnData.addElement(new AbundRowData(-20, -15, true, new Color(135,0,0)));
		abundColumnData.addElement(new AbundRowData(-25, -20, true, new Color(95,0,0)));
		
		Vector derColumnData = new Vector();
		derColumnData.addElement(new DerRowData(-1, 0, true, new Color (255,0,0), new Color(0,255,0)));
		derColumnData.addElement(new DerRowData(-2, -1, true, new Color(215,0,0), new Color(0,215,0)));
		derColumnData.addElement(new DerRowData(-3, -2, true, new Color(175,0,0), new Color(0,175,0)));
		derColumnData.addElement(new DerRowData(-4, -3, true, new Color(135,0,0), new Color(0,135,0)));
		derColumnData.addElement(new DerRowData(-5, -4, true, new Color(95,0,0), new Color(0,95,0)));
		
		Vector fluxColumnData = new Vector();
		fluxColumnData.addElement(new FluxRowData(-1, 0, true, new Color (255,0,0), 0, 1));
		fluxColumnData.addElement(new FluxRowData(-2, -1, true, new Color(215,0,0), 0, 1));
		fluxColumnData.addElement(new FluxRowData(-3, -2, true, new Color(175,0,0), 0, 1));
		fluxColumnData.addElement(new FluxRowData(-4, -3, true, new Color(135,0,0), 0, 1));
		fluxColumnData.addElement(new FluxRowData(-5, -4, true, new Color(95,0,0), 0, 1));
		
		Vector finalAbundColumnData = new Vector();
		finalAbundColumnData.addElement(new AbundRowData(-5, 0, true, new Color (255,0,0)));
		finalAbundColumnData.addElement(new AbundRowData(-10, -5, true, new Color(215,0,0)));
		finalAbundColumnData.addElement(new AbundRowData(-15, -10, true, new Color(175,0,0)));
		finalAbundColumnData.addElement(new AbundRowData(-20, -15, true, new Color(135,0,0)));
		finalAbundColumnData.addElement(new AbundRowData(-25, -20, true, new Color(95,0,0)));
		
		Vector finalWeightedAbundColumnData = new Vector();
		finalWeightedAbundColumnData.addElement(new AbundRowData(-5, 0, true, new Color (255,0,0)));
		finalWeightedAbundColumnData.addElement(new AbundRowData(-10, -5, true, new Color(215,0,0)));
		finalWeightedAbundColumnData.addElement(new AbundRowData(-15, -10, true, new Color(175,0,0)));
		finalWeightedAbundColumnData.addElement(new AbundRowData(-20, -15, true, new Color(135,0,0)));
		finalWeightedAbundColumnData.addElement(new AbundRowData(-25, -20, true, new Color(95,0,0)));
		
		Vector intFluxColumnData = new Vector();
		intFluxColumnData.addElement(new FluxRowData(-1, 0, true, new Color (255,0,0), 0, 1));
		intFluxColumnData.addElement(new FluxRowData(-2, -1, true, new Color(215,0,0), 0, 1));
		intFluxColumnData.addElement(new FluxRowData(-3, -2, true, new Color(175,0,0), 0, 1));
		intFluxColumnData.addElement(new FluxRowData(-4, -3, true, new Color(135,0,0), 0, 1));
		intFluxColumnData.addElement(new FluxRowData(-5, -4, true, new Color(95,0,0), 0, 1));
		
		setAbundBinData(abundColumnData);
		setDerBinData(derColumnData);
		setFluxBinData(fluxColumnData);
		setFinalAbundBinData(finalAbundColumnData);
		setFinalWeightedAbundBinData(finalWeightedAbundColumnData);
		setIntFluxBinData(intFluxColumnData);
		
		setAbundScheme("Continuous");
		setDerScheme("Continuous");
		setFluxScheme("Binned");
		setFinalAbundScheme("Continuous");
		setFinalWeightedAbundScheme("Continuous");
		setIntFluxScheme("Binned");
		
		setAbundIncludeValues(true);
		setDerIncludeValues(true);
		setFluxIncludeValues(true);
		setFinalAbundIncludeValues(true);
		setFinalWeightedAbundIncludeValues(true);
		setIntFluxIncludeValues(true);
		
		setRateDataStructure(null);
		
		setPaths("");
		setPath("");
		setZones("");
		setZone("");
		setIsotopes("");
		setReactions("");
		setArguments("");
		setSimulation("");
		setSum("N");
		setFinal_step("N");
		setProperties("");
		setRates("");
		setGroup("");
		setWidth("");
		setHeight("");
		setScaleFactor("");
		setCompFactor("");
		setFrame_skip_interval("");
		setFolderType(null);
	}

	public String getScaleFactor(){return scaleFactor;}
	public void setScaleFactor(String newScaleFactor){scaleFactor = newScaleFactor;}
	
	public String getCompFactor(){return compFactor;}
	public void setCompFactor(String newCompFactor){compFactor = newCompFactor;}

	public String getWidth(){return width;}
	public void setWidth(String newWidth){width = newWidth;}
	
	public String getHeight(){return height;}
	public void setHeight(String newHeight){height = newHeight;}

	public TreeMap<String, TreeMap<IsotopePoint, Double>> getFinalAbundMap(){return finalAbundMap;}
	public void setFinalAbundMap(TreeMap<String, TreeMap<IsotopePoint, Double>> newFinalAbundMap){finalAbundMap = newFinalAbundMap;}
	
	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	/**
	 * Gets the sens map.
	 *
	 * @return the sens map
	 */
	public TreeMap<String, TreeMap<Double, NucSimDataStructure>> getSensMap(){return sensMap;}
	
	/**
	 * Sets the sens map.
	 *
	 * @param sensMap the sens map
	 */
	public void setSensMap(TreeMap<String, TreeMap<Double, NucSimDataStructure>> sensMap){this.sensMap = sensMap;}
	
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
	 * Gets the abund file name array.
	 *
	 * @return the abund file name array
	 */
	public String[] getAbundFileNameArray(){return abundFileNameArray;}
	
	/**
	 * Sets the abund file name array.
	 *
	 * @param newAbundFileNameArray the new abund file name array
	 */
	public void setAbundFileNameArray(String[] newAbundFileNameArray){abundFileNameArray = newAbundFileNameArray;}

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

	public Vector getIsotopeViktorAbundTable(){return isotopeViktorAbundTable;}
	public void setIsotopeViktorAbundTable(Vector newIsotopeViktorAbundTable){isotopeViktorAbundTable = newIsotopeViktorAbundTable;}

	/**
	 * Gets the isotope viktor abund.
	 *
	 * @return the isotope viktor abund
	 */
	public Vector getIsotopeViktorAbund(){return isotopeViktorAbund;}
	
	/**
	 * Sets the isotope viktor abund.
	 *
	 * @param newIsotopeViktorAbund the new isotope viktor abund
	 */
	public void setIsotopeViktorAbund(Vector newIsotopeViktorAbund){isotopeViktorAbund = newIsotopeViktorAbund;}
	
	/**
	 * Gets the isotope viktor weight.
	 *
	 * @return the isotope viktor weight
	 */
	public Vector getIsotopeViktorWeight(){return isotopeViktorWeight;}
	
	/**
	 * Sets the isotope viktor weight.
	 *
	 * @param newIsotopeViktorWeight the new isotope viktor weight
	 */
	public void setIsotopeViktorWeight(Vector newIsotopeViktorWeight){isotopeViktorWeight = newIsotopeViktorWeight;}
	
	/**
	 * Gets the isotope viktor scale.
	 *
	 * @return the isotope viktor scale
	 */
	public Vector getIsotopeViktorScale(){return isotopeViktorScale;}
	
	/**
	 * Sets the isotope viktor scale.
	 *
	 * @param newIsotopeViktorScale the new isotope viktor scale
	 */
	public void setIsotopeViktorScale(Vector newIsotopeViktorScale){isotopeViktorScale = newIsotopeViktorScale;}
	
	/**
	 * Gets the rate data structure.
	 *
	 * @return the rate data structure
	 */
	public RateDataStructure getRateDataStructure(){return rateDataStructure;}
	
	/**
	 * Sets the rate data structure.
	 *
	 * @param rateDataStructure the new rate data structure
	 */
	public void setRateDataStructure(RateDataStructure rateDataStructure){this.rateDataStructure = rateDataStructure;}
	
	/**
	 * Gets the nuc sim vector.
	 *
	 * @return the nuc sim vector
	 */
	public Vector getNucSimVector(){return nucSimVector;}
	
	/**
	 * Sets the nuc sim vector.
	 *
	 * @param newNucSimVector the new nuc sim vector
	 */
	public void setNucSimVector(Vector newNucSimVector){nucSimVector = newNucSimVector;}
	
	/**
	 * Gets the number user nuc sim set data structures.
	 *
	 * @return the number user nuc sim set data structures
	 */
	public int getNumberUserNucSimSetDataStructures(){return numberUserNucSimSetDataStructures;}
	
	/**
	 * Sets the number user nuc sim set data structures.
	 *
	 * @param newNumberUserNucSimSetDataStructures the new number user nuc sim set data structures
	 */
	public void setNumberUserNucSimSetDataStructures(int newNumberUserNucSimSetDataStructures){numberUserNucSimSetDataStructures = newNumberUserNucSimSetDataStructures;}
	
	/**
	 * Gets the number shared nuc sim set data structures.
	 *
	 * @return the number shared nuc sim set data structures
	 */
	public int getNumberSharedNucSimSetDataStructures(){return numberSharedNucSimSetDataStructures;}
	
	/**
	 * Sets the number shared nuc sim set data structures.
	 *
	 * @param newNumberSharedNucSimSetDataStructures the new number shared nuc sim set data structures
	 */
	public void setNumberSharedNucSimSetDataStructures(int newNumberSharedNucSimSetDataStructures){numberSharedNucSimSetDataStructures = newNumberSharedNucSimSetDataStructures;}

	/**
	 * Gets the number nuc sim set data structures.
	 *
	 * @return the number nuc sim set data structures
	 */
	public int getNumberNucSimSetDataStructures(){return numberNucSimSetDataStructures;}
	
	/**
	 * Sets the number nuc sim set data structures.
	 *
	 * @param newNumberNucSimSetDataStructures the new number nuc sim set data structures
	 */
	public void setNumberNucSimSetDataStructures(int newNumberNucSimSetDataStructures){numberNucSimSetDataStructures = newNumberNucSimSetDataStructures;}
	
	/**
	 * Gets the user nuc sim set data structure array.
	 *
	 * @return the user nuc sim set data structure array
	 */
	public NucSimSetDataStructure[] getUserNucSimSetDataStructureArray(){return userNucSimSetDataStructureArray;}
	
	/**
	 * Sets the user nuc sim set data structure array.
	 *
	 * @param newUserNucSimSetDataStructureArray the new user nuc sim set data structure array
	 */
	public void setUserNucSimSetDataStructureArray(NucSimSetDataStructure[] newUserNucSimSetDataStructureArray){userNucSimSetDataStructureArray = newUserNucSimSetDataStructureArray;}
	
	/**
	 * Gets the shared nuc sim set data structure array.
	 *
	 * @return the shared nuc sim set data structure array
	 */
	public NucSimSetDataStructure[] getSharedNucSimSetDataStructureArray(){return sharedNucSimSetDataStructureArray;}
	
	/**
	 * Sets the shared nuc sim set data structure array.
	 *
	 * @param newSharedNucSimSetDataStructureArray the new shared nuc sim set data structure array
	 */
	public void setSharedNucSimSetDataStructureArray(NucSimSetDataStructure[] newSharedNucSimSetDataStructureArray){sharedNucSimSetDataStructureArray = newSharedNucSimSetDataStructureArray;}
	
	/**
	 * Gets the final nuc sim set data structure array.
	 *
	 * @return the final nuc sim set data structure array
	 */
	public NucSimSetDataStructure[] getFinalNucSimSetDataStructureArray(){return finalNucSimSetDataStructureArray;}
	
	/**
	 * Sets the final nuc sim set data structure array.
	 *
	 * @param newFinalNucSimSetDataStructureArray the new final nuc sim set data structure array
	 */
	public void setFinalNucSimSetDataStructureArray(NucSimSetDataStructure[] newFinalNucSimSetDataStructureArray){finalNucSimSetDataStructureArray = newFinalNucSimSetDataStructureArray;}
	
	/**
	 * Gets the zone nuc sim set data structure array.
	 *
	 * @return the zone nuc sim set data structure array
	 */
	public NucSimSetDataStructure[] getZoneNucSimSetDataStructureArray(){return zoneNucSimSetDataStructureArray;}
	
	/**
	 * Sets the zone nuc sim set data structure array.
	 *
	 * @param newZoneNucSimSetDataStructureArray the new zone nuc sim set data structure array
	 */
	public void setZoneNucSimSetDataStructureArray(NucSimSetDataStructure[] newZoneNucSimSetDataStructureArray){zoneNucSimSetDataStructureArray = newZoneNucSimSetDataStructureArray;}
	
	/**
	 * Gets the final nuc sim set data structure.
	 *
	 * @return the final nuc sim set data structure
	 */
	public NucSimSetDataStructure getFinalNucSimSetDataStructure(){return finalNucSimSetDataStructure;}
	
	/**
	 * Sets the final nuc sim set data structure.
	 *
	 * @param newFinalNucSimSetDataStructure the new final nuc sim set data structure
	 */
	public void setFinalNucSimSetDataStructure(NucSimSetDataStructure newFinalNucSimSetDataStructure){finalNucSimSetDataStructure = newFinalNucSimSetDataStructure;}
	
	/**
	 * Gets the current nuc sim set data structure.
	 *
	 * @return the current nuc sim set data structure
	 */
	public NucSimSetDataStructure getCurrentNucSimSetDataStructure(){return currentNucSimSetDataStructure;}
	
	/**
	 * Sets the current nuc sim set data structure.
	 *
	 * @param newCurrentNucSimSetDataStructure the new current nuc sim set data structure
	 */
	public void setCurrentNucSimSetDataStructure(NucSimSetDataStructure newCurrentNucSimSetDataStructure){currentNucSimSetDataStructure = newCurrentNucSimSetDataStructure;}
	
	/**
	 * Gets the current nuc sim data structure.
	 *
	 * @return the current nuc sim data structure
	 */
	public NucSimDataStructure getCurrentNucSimDataStructure(){return currentNucSimDataStructure;}
	
	/**
	 * Sets the current nuc sim data structure.
	 *
	 * @param newCurrentNucSimDataStructure the new current nuc sim data structure
	 */
	public void setCurrentNucSimDataStructure(NucSimDataStructure newCurrentNucSimDataStructure){currentNucSimDataStructure = newCurrentNucSimDataStructure;}
	
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
	
	/**
	 * Gets the number nuc sim data structures.
	 *
	 * @return the number nuc sim data structures
	 */
	public int getNumberNucSimDataStructures(){return numberNucSimDataStructures;}
	
	/**
	 * Sets the number nuc sim data structures.
	 *
	 * @param newNumberNucSimDataStructures the new number nuc sim data structures
	 */
	public void setNumberNucSimDataStructures(int newNumberNucSimDataStructures){numberNucSimDataStructures = newNumberNucSimDataStructures;}

	/**
	 * Gets the nuc sim data structure array.
	 *
	 * @return the nuc sim data structure array
	 */
	public NucSimDataStructure[] getNucSimDataStructureArray(){return nucSimDataStructureArray;}
	
	/**
	 * Sets the nuc sim data structure array.
	 *
	 * @param newNucSimDataStructureArray the new nuc sim data structure array
	 */
	public void setNucSimDataStructureArray(NucSimDataStructure[] newNucSimDataStructureArray){nucSimDataStructureArray = newNucSimDataStructureArray;}
	
	/**
	 * Gets the nuc sim data structure array animator.
	 *
	 * @return the nuc sim data structure array animator
	 */
	public NucSimDataStructure[] getNucSimDataStructureArrayAnimator(){return nucSimDataStructureArrayAnimator;}
	
	/**
	 * Sets the nuc sim data structure array animator.
	 *
	 * @param newNucSimDataStructureArrayAnimator the new nuc sim data structure array animator
	 */
	public void setNucSimDataStructureArrayAnimator(NucSimDataStructure[] newNucSimDataStructureArrayAnimator){nucSimDataStructureArrayAnimator = newNucSimDataStructureArrayAnimator;}
	
	/**
	 * Gets the nuc sim data structure array int flux.
	 *
	 * @return the nuc sim data structure array int flux
	 */
	public NucSimDataStructure[] getNucSimDataStructureArrayIntFlux(){return nucSimDataStructureArrayIntFlux;}
	
	/**
	 * Sets the nuc sim data structure array int flux.
	 *
	 * @param newNucSimDataStructureArrayIntFlux the new nuc sim data structure array int flux
	 */
	public void setNucSimDataStructureArrayIntFlux(NucSimDataStructure[] newNucSimDataStructureArrayIntFlux){nucSimDataStructureArrayIntFlux = newNucSimDataStructureArrayIntFlux;}
	
	public NucSimDataStructure[] getNucSimDataStructureArrayFinalAbund(){return nucSimDataStructureArrayFinalAbund;}
	public void setNucSimDataStructureArrayFinalAbund(NucSimDataStructure[] newNucSimDataStructureArrayFinalAbund){nucSimDataStructureArrayFinalAbund = newNucSimDataStructureArrayFinalAbund;}
	
	/**
	 * Gets the nuc sim data structure array scale.
	 *
	 * @return the nuc sim data structure array scale
	 */
	public NucSimDataStructure[] getNucSimDataStructureArrayScale(){return nucSimDataStructureArrayScale;}
	
	/**
	 * Sets the nuc sim data structure array scale.
	 *
	 * @param newNucSimDataStructureArrayScale the new nuc sim data structure array scale
	 */
	public void setNucSimDataStructureArrayScale(NucSimDataStructure[] newNucSimDataStructureArrayScale){nucSimDataStructureArrayScale = newNucSimDataStructureArrayScale;}
	
	/**
	 * Gets the animator nuc sim data structure.
	 *
	 * @return the animator nuc sim data structure
	 */
	public NucSimDataStructure getAnimatorNucSimDataStructure(){return animatorNucSimDataStructure;}
	
	/**
	 * Sets the animator nuc sim data structure.
	 *
	 * @param newAnimatorNucSimDataStructure the new animator nuc sim data structure
	 */
	public void setAnimatorNucSimDataStructure(NucSimDataStructure newAnimatorNucSimDataStructure){animatorNucSimDataStructure = newAnimatorNucSimDataStructure;}
	
	/**
	 * Gets the int flux nuc sim data structure.
	 *
	 * @return the int flux nuc sim data structure
	 */
	public NucSimDataStructure getIntFluxNucSimDataStructure(){return intFluxNucSimDataStructure;}
	
	/**
	 * Sets the int flux nuc sim data structure.
	 *
	 * @param newIntFluxNucSimDataStructure the new int flux nuc sim data structure
	 */
	public void setIntFluxNucSimDataStructure(NucSimDataStructure newIntFluxNucSimDataStructure){intFluxNucSimDataStructure = newIntFluxNucSimDataStructure;}
	
	/**
	 * Gets the time data array abund.
	 *
	 * @return the time data array abund
	 */
	public double[][] getTimeDataArrayAbund(){return timeDataArrayAbund;}
	
	/**
	 * Sets the time data array abund.
	 *
	 * @param newTimeDataArrayAbund the new time data array abund
	 */
	public void setTimeDataArrayAbund(double[][] newTimeDataArrayAbund){timeDataArrayAbund = newTimeDataArrayAbund;}
	
	/**
	 * Gets the time data array thermo.
	 *
	 * @return the time data array thermo
	 */
	public double[][] getTimeDataArrayThermo(){return timeDataArrayThermo;}
	
	/**
	 * Sets the time data array thermo.
	 *
	 * @param newTimeDataArrayThermo the new time data array thermo
	 */
	public void setTimeDataArrayThermo(double[][] newTimeDataArrayThermo){timeDataArrayThermo = newTimeDataArrayThermo;}
	
	/**
	 * Gets the mass data array abund.
	 *
	 * @return the mass data array abund
	 */
	public double[][] getMassDataArrayAbund(){return massDataArrayAbund;}
	
	/**
	 * Sets the mass data array abund.
	 *
	 * @param newMassDataArrayAbund the new mass data array abund
	 */
	public void setMassDataArrayAbund(double[][] newMassDataArrayAbund){massDataArrayAbund = newMassDataArrayAbund;}
	
	/**
	 * Gets the abund data array.
	 *
	 * @return the abund data array
	 */
	public double[][] getAbundDataArray(){return abundDataArray;}
	
	/**
	 * Sets the abund data array.
	 *
	 * @param newAbundDataArray the new abund data array
	 */
	public void setAbundDataArray(double[][] newAbundDataArray){abundDataArray = newAbundDataArray;}
	
	/**
	 * Gets the ratio data array abund.
	 *
	 * @return the ratio data array abund
	 */
	public double[][] getRatioDataArrayAbund(){return ratioDataArrayAbund;}
	
	/**
	 * Sets the ratio data array abund.
	 *
	 * @param newRatioDataArrayAbund the new ratio data array abund
	 */
	public void setRatioDataArrayAbund(double[][] newRatioDataArrayAbund){ratioDataArrayAbund = newRatioDataArrayAbund;}
	
	/**
	 * Gets the temp data array.
	 *
	 * @return the temp data array
	 */
	public double[][] getTempDataArray(){return tempDataArray;}
	
	/**
	 * Sets the temp data array.
	 *
	 * @param newTempDataArray the new temp data array
	 */
	public void setTempDataArray(double[][] newTempDataArray){tempDataArray = newTempDataArray;}
	
	/**
	 * Gets the density data array.
	 *
	 * @return the density data array
	 */
	public double[][] getDensityDataArray(){return densityDataArray;}
	
	/**
	 * Sets the density data array.
	 *
	 * @param newDensityDataArray the new density data array
	 */
	public void setDensityDataArray(double[][] newDensityDataArray){densityDataArray = newDensityDataArray;}
	
	/**
	 * Gets the abund non zero array.
	 *
	 * @return the abund non zero array
	 */
	public boolean[] getAbundNonZeroArray(){return abundNonZeroArray;}
	
	/**
	 * Sets the abund non zero array.
	 *
	 * @param newAbundNonZeroArray the new abund non zero array
	 */
	public void setAbundNonZeroArray(boolean[] newAbundNonZeroArray){abundNonZeroArray = newAbundNonZeroArray;}

	/**
	 * Gets the ratio non zero array abund.
	 *
	 * @return the ratio non zero array abund
	 */
	public boolean[] getRatioNonZeroArrayAbund(){return ratioNonZeroArrayAbund;}
	
	/**
	 * Sets the ratio non zero array abund.
	 *
	 * @param newRatioNonZeroArrayAbund the new ratio non zero array abund
	 */
	public void setRatioNonZeroArrayAbund(boolean[] newRatioNonZeroArrayAbund){ratioNonZeroArrayAbund = newRatioNonZeroArrayAbund;}

	/**
	 * Gets the include isotope array.
	 *
	 * @return the include isotope array
	 */
	public boolean[] getIncludeIsotopeArray(){return includeIsotopeArray;}
	
	/**
	 * Sets the include isotope array.
	 *
	 * @param newIncludeIsotopeArray the new include isotope array
	 */
	public void setIncludeIsotopeArray(boolean[] newIncludeIsotopeArray){includeIsotopeArray = newIncludeIsotopeArray;}

	/**
	 * Gets the zone data array.
	 *
	 * @return the zone data array
	 */
	public double[][] getZoneDataArray(){return zoneDataArray;}
	
	/**
	 * Sets the zone data array.
	 *
	 * @param newZoneDataArray the new zone data array
	 */
	public void setZoneDataArray(double[][] newZoneDataArray){zoneDataArray = newZoneDataArray;}

	/**
	 * Gets the weight data array.
	 *
	 * @return the weight data array
	 */
	public double[][] getWeightDataArray(){return weightDataArray;}
	
	/**
	 * Sets the weight data array.
	 *
	 * @param newWeightDataArray the new weight data array
	 */
	public void setWeightDataArray(double[][] newWeightDataArray){weightDataArray = newWeightDataArray;}
	
	/**
	 * Gets the scale data array.
	 *
	 * @return the scale data array
	 */
	public double[][] getScaleDataArray(){return scaleDataArray;}
	
	/**
	 * Sets the scale data array.
	 *
	 * @param newScaleDataArray the new scale data array
	 */
	public void setScaleDataArray(double[][] newScaleDataArray){scaleDataArray = newScaleDataArray;}

	/**
	 * Gets the weight non zero array.
	 *
	 * @return the weight non zero array
	 */
	public boolean[] getWeightNonZeroArray(){return weightNonZeroArray;}
	
	/**
	 * Sets the weight non zero array.
	 *
	 * @param newWeightNonZeroArray the new weight non zero array
	 */
	public void setWeightNonZeroArray(boolean[] newWeightNonZeroArray){weightNonZeroArray = newWeightNonZeroArray;}
	
	/**
	 * Gets the scale non zero array.
	 *
	 * @return the scale non zero array
	 */
	public boolean[] getScaleNonZeroArray(){return scaleNonZeroArray;}
	
	/**
	 * Sets the scale non zero array.
	 *
	 * @param newScaleNonZeroArray the new scale non zero array
	 */
	public void setScaleNonZeroArray(boolean[] newScaleNonZeroArray){scaleNonZeroArray = newScaleNonZeroArray;}
	
	/**
	 * Gets the mass data array weight.
	 *
	 * @return the mass data array weight
	 */
	public double[][] getMassDataArrayWeight(){return massDataArrayWeight;}
	
	/**
	 * Sets the mass data array weight.
	 *
	 * @param newMassDataArrayWeight the new mass data array weight
	 */
	public void setMassDataArrayWeight(double[][] newMassDataArrayWeight){massDataArrayWeight = newMassDataArrayWeight;}

	/**
	 * Gets the ratio data array weight.
	 *
	 * @return the ratio data array weight
	 */
	public double[][] getRatioDataArrayWeight(){return ratioDataArrayWeight;}
	
	/**
	 * Sets the ratio data array weight.
	 *
	 * @param newRatioDataArrayWeight the new ratio data array weight
	 */
	public void setRatioDataArrayWeight(double[][] newRatioDataArrayWeight){ratioDataArrayWeight = newRatioDataArrayWeight;}

	/**
	 * Gets the mass data array scale.
	 *
	 * @return the mass data array scale
	 */
	public double[][] getMassDataArrayScale(){return massDataArrayScale;}
	
	/**
	 * Sets the mass data array scale.
	 *
	 * @param newMassDataArrayScale the new mass data array scale
	 */
	public void setMassDataArrayScale(double[][] newMassDataArrayScale){massDataArrayScale = newMassDataArrayScale;}

	/**
	 * Gets the ratio data array scale.
	 *
	 * @return the ratio data array scale
	 */
	public double[][] getRatioDataArrayScale(){return ratioDataArrayScale;}
	
	/**
	 * Sets the ratio data array scale.
	 *
	 * @param newRatioDataArrayScale the new ratio data array scale
	 */
	public void setRatioDataArrayScale(double[][] newRatioDataArrayScale){ratioDataArrayScale = newRatioDataArrayScale;}
	
	/**
	 * Gets the ratio non zero array weight.
	 *
	 * @return the ratio non zero array weight
	 */
	public boolean[] getRatioNonZeroArrayWeight(){return ratioNonZeroArrayWeight;}
	
	/**
	 * Sets the ratio non zero array weight.
	 *
	 * @param newRatioNonZeroArrayWeight the new ratio non zero array weight
	 */
	public void setRatioNonZeroArrayWeight(boolean[] newRatioNonZeroArrayWeight){ratioNonZeroArrayWeight = newRatioNonZeroArrayWeight;}
	
	/**
	 * Gets the ratio non zero array scale.
	 *
	 * @return the ratio non zero array scale
	 */
	public boolean[] getRatioNonZeroArrayScale(){return ratioNonZeroArrayScale;}
	
	/**
	 * Sets the ratio non zero array scale.
	 *
	 * @param newRatioNonZeroArrayScale the new ratio non zero array scale
	 */
	public void setRatioNonZeroArrayScale(boolean[] newRatioNonZeroArrayScale){ratioNonZeroArrayScale = newRatioNonZeroArrayScale;}

	/**
	 * Gets the densitymin.
	 *
	 * @return the densitymin
	 */
	public int getDensitymin(){return densitymin;}
	
	/**
	 * Sets the densitymin.
	 *
	 * @param newDensitymin the new densitymin
	 */
	public void setDensitymin(int newDensitymin){densitymin = newDensitymin;}
	
	/**
	 * Gets the densitymax.
	 *
	 * @return the densitymax
	 */
	public int getDensitymax(){return densitymax;}
	
	/**
	 * Sets the densitymax.
	 *
	 * @param newDensitymax the new densitymax
	 */
	public void setDensitymax(int newDensitymax){densitymax = newDensitymax;}
	
	/**
	 * Gets the abundmin.
	 *
	 * @return the abundmin
	 */
	public int getAbundmin(){return abundmin;}
	
	/**
	 * Sets the abundmin.
	 *
	 * @param newAbundmin the new abundmin
	 */
	public void setAbundmin(int newAbundmin){abundmin = newAbundmin;}
	
	/**
	 * Gets the abundmax.
	 *
	 * @return the abundmax
	 */
	public int getAbundmax(){return abundmax;}
	
	/**
	 * Sets the abundmax.
	 *
	 * @param newAbundmax the new abundmax
	 */
	public void setAbundmax(int newAbundmax){abundmax = newAbundmax;}
	
	/**
	 * Gets the weightmin.
	 *
	 * @return the weightmin
	 */
	public int getWeightmin(){return weightmin;}
	
	/**
	 * Sets the weightmin.
	 *
	 * @param newWeightmin the new weightmin
	 */
	public void setWeightmin(int newWeightmin){weightmin = newWeightmin;}
	
	/**
	 * Gets the weightmax.
	 *
	 * @return the weightmax
	 */
	public int getWeightmax(){return weightmax;}
	
	/**
	 * Sets the weightmax.
	 *
	 * @param newWeightmax the new weightmax
	 */
	public void setWeightmax(int newWeightmax){weightmax = newWeightmax;}
	
	/**
	 * Gets the scalemin.
	 *
	 * @return the scalemin
	 */
	public int getScalemin(){return scalemin;}
	
	/**
	 * Sets the scalemin.
	 *
	 * @param newScalemin the new scalemin
	 */
	public void setScalemin(int newScalemin){scalemin = newScalemin;}
	
	/**
	 * Gets the scalemax.
	 *
	 * @return the scalemax
	 */
	public int getScalemax(){return scalemax;}
	
	/**
	 * Sets the scalemax.
	 *
	 * @param newScalemax the new scalemax
	 */
	public void setScalemax(int newScalemax){scalemax = newScalemax;}
	
	/**
	 * Gets the zonemin.
	 *
	 * @return the zonemin
	 */
	public int getZonemin(){return zonemin;}
	
	/**
	 * Sets the zonemin.
	 *
	 * @param newZonemin the new zonemin
	 */
	public void setZonemin(int newZonemin){zonemin = newZonemin;}
	
	/**
	 * Gets the zonemax.
	 *
	 * @return the zonemax
	 */
	public int getZonemax(){return zonemax;}
	
	/**
	 * Sets the zonemax.
	 *
	 * @param newZonemax the new zonemax
	 */
	public void setZonemax(int newZonemax){zonemax = newZonemax;}
	
	/**
	 * Gets the massmin abund.
	 *
	 * @return the massmin abund
	 */
	public int getMassminAbund(){return massminAbund;}
	
	/**
	 * Sets the massmin abund.
	 *
	 * @param newMassminAbund the new massmin abund
	 */
	public void setMassminAbund(int newMassminAbund){massminAbund = newMassminAbund;}
	
	/**
	 * Gets the massmax abund.
	 *
	 * @return the massmax abund
	 */
	public int getMassmaxAbund(){return massmaxAbund;}
	
	/**
	 * Sets the massmax abund.
	 *
	 * @param newMassmaxAbund the new massmax abund
	 */
	public void setMassmaxAbund(int newMassmaxAbund){massmaxAbund = newMassmaxAbund;}
	
	/**
	 * Gets the massmin weight.
	 *
	 * @return the massmin weight
	 */
	public int getMassminWeight(){return massminWeight;}
	
	/**
	 * Sets the massmin weight.
	 *
	 * @param newMassminWeight the new massmin weight
	 */
	public void setMassminWeight(int newMassminWeight){massminWeight = newMassminWeight;}
	
	/**
	 * Gets the massmax weight.
	 *
	 * @return the massmax weight
	 */
	public int getMassmaxWeight(){return massmaxWeight;}
	
	/**
	 * Sets the massmax weight.
	 *
	 * @param newMassmaxWeight the new massmax weight
	 */
	public void setMassmaxWeight(int newMassmaxWeight){massmaxWeight = newMassmaxWeight;}
	
	/**
	 * Gets the massmin scale.
	 *
	 * @return the massmin scale
	 */
	public int getMassminScale(){return massminScale;}
	
	/**
	 * Sets the massmin scale.
	 *
	 * @param newMassminScale the new massmin scale
	 */
	public void setMassminScale(int newMassminScale){massminScale = newMassminScale;}
	
	/**
	 * Gets the massmax scale.
	 *
	 * @return the massmax scale
	 */
	public int getMassmaxScale(){return massmaxScale;}
	
	/**
	 * Sets the massmax scale.
	 *
	 * @param newMassmaxScale the new massmax scale
	 */
	public void setMassmaxScale(int newMassmaxScale){massmaxScale = newMassmaxScale;}
	
	/**
	 * Gets the ratiomin abund.
	 *
	 * @return the ratiomin abund
	 */
	public double getRatiominAbund(){return ratiominAbund;}
	
	/**
	 * Sets the ratiomin abund.
	 *
	 * @param newRatiominAbund the new ratiomin abund
	 */
	public void setRatiominAbund(double newRatiominAbund){ratiominAbund = newRatiominAbund;}
	
	/**
	 * Gets the ratiomax abund.
	 *
	 * @return the ratiomax abund
	 */
	public double getRatiomaxAbund(){return ratiomaxAbund;}
	
	/**
	 * Sets the ratiomax abund.
	 *
	 * @param newRatiomaxAbund the new ratiomax abund
	 */
	public void setRatiomaxAbund(double newRatiomaxAbund){ratiomaxAbund = newRatiomaxAbund;}
	
	/**
	 * Gets the ratiomin weight.
	 *
	 * @return the ratiomin weight
	 */
	public double getRatiominWeight(){return ratiominWeight;}
	
	/**
	 * Sets the ratiomin weight.
	 *
	 * @param newRatiominWeight the new ratiomin weight
	 */
	public void setRatiominWeight(double newRatiominWeight){ratiominWeight = newRatiominWeight;}
	
	/**
	 * Gets the ratiomax weight.
	 *
	 * @return the ratiomax weight
	 */
	public double getRatiomaxWeight(){return ratiomaxWeight;}
	
	/**
	 * Sets the ratiomax weight.
	 *
	 * @param newRatiomaxWeight the new ratiomax weight
	 */
	public void setRatiomaxWeight(double newRatiomaxWeight){ratiomaxWeight = newRatiomaxWeight;}
	
	/**
	 * Gets the ratiomin scale.
	 *
	 * @return the ratiomin scale
	 */
	public double getRatiominScale(){return ratiominScale;}
	
	/**
	 * Sets the ratiomin scale.
	 *
	 * @param newRatiominScale the new ratiomin scale
	 */
	public void setRatiominScale(double newRatiominScale){ratiominScale = newRatiominScale;}
	
	/**
	 * Gets the ratiomax scale.
	 *
	 * @return the ratiomax scale
	 */
	public double getRatiomaxScale(){return ratiomaxScale;}
	
	/**
	 * Sets the ratiomax scale.
	 *
	 * @param newRatiomaxScale the new ratiomax scale
	 */
	public void setRatiomaxScale(double newRatiomaxScale){ratiomaxScale = newRatiomaxScale;}
	
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
	public void setTempmin(double newTempmin){tempmin = newTempmin;}
	
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
	public void setTempmax(double newTempmax){tempmax = newTempmax;}
	
	/**
	 * Gets the timemin abund.
	 *
	 * @return the timemin abund
	 */
	public double getTimeminAbund(){return timeminAbund;}
	
	/**
	 * Sets the timemin abund.
	 *
	 * @param newTimeminAbund the new timemin abund
	 */
	public void setTimeminAbund(double newTimeminAbund){timeminAbund = newTimeminAbund;}
	
	/**
	 * Gets the timemax abund.
	 *
	 * @return the timemax abund
	 */
	public double getTimemaxAbund(){return timemaxAbund;}
	
	/**
	 * Sets the timemax abund.
	 *
	 * @param newTimemaxAbund the new timemax abund
	 */
	public void setTimemaxAbund(double newTimemaxAbund){timemaxAbund = newTimemaxAbund;}
	
	/**
	 * Gets the timemin thermo.
	 *
	 * @return the timemin thermo
	 */
	public double getTimeminThermo(){return timeminThermo;}
	
	/**
	 * Sets the timemin thermo.
	 *
	 * @param newTimeminThermo the new timemin thermo
	 */
	public void setTimeminThermo(double newTimeminThermo){timeminThermo = newTimeminThermo;}
	
	/**
	 * Gets the timemax thermo.
	 *
	 * @return the timemax thermo
	 */
	public double getTimemaxThermo(){return timemaxThermo;}
	
	/**
	 * Sets the timemax thermo.
	 *
	 * @param newTimemaxThermo the new timemax thermo
	 */
	public void setTimemaxThermo(double newTimemaxThermo){timemaxThermo = newTimemaxThermo;}
	
	/**
	 * Gets the normal time array.
	 *
	 * @return the normal time array
	 */
	public double[] getNormalTimeArray(){return normalTimeArray;}
	
	/**
	 * Sets the normal time array.
	 *
	 * @param newNormalTimeArray the new normal time array
	 */
	public void setNormalTimeArray(double[] newNormalTimeArray){normalTimeArray = newNormalTimeArray;}
	
	/**
	 * Gets the max number time points.
	 *
	 * @return the max number time points
	 */
	public int getMaxNumberTimePoints(){return maxNumberTimePoints;}
	
	/**
	 * Sets the max number time points.
	 *
	 * @param newMaxNumberTimePoints the new max number time points
	 */
	public void setMaxNumberTimePoints(int newMaxNumberTimePoints){maxNumberTimePoints = newMaxNumberTimePoints;}
	
	/**
	 * Gets the max number zone points.
	 *
	 * @return the max number zone points
	 */
	public int getMaxNumberZonePoints(){return maxNumberZonePoints;}
	
	/**
	 * Sets the max number zone points.
	 *
	 * @param newMaxNumberZonePoints the new max number zone points
	 */
	public void setMaxNumberZonePoints(int newMaxNumberZonePoints){maxNumberZonePoints = newMaxNumberZonePoints;}
	
	/**
	 * Gets the number isotope runs abund.
	 *
	 * @return the number isotope runs abund
	 */
	public int getNumberIsotopeRunsAbund(){return numberIsotopeRunsAbund;}
	
	/**
	 * Sets the number isotope runs abund.
	 *
	 * @param newNumberIsotopeRunsAbund the new number isotope runs abund
	 */
	public void setNumberIsotopeRunsAbund(int newNumberIsotopeRunsAbund){numberIsotopeRunsAbund = newNumberIsotopeRunsAbund;}
	
	/**
	 * Gets the number isotope runs weight.
	 *
	 * @return the number isotope runs weight
	 */
	public int getNumberIsotopeRunsWeight(){return numberIsotopeRunsWeight;}
	
	/**
	 * Sets the number isotope runs weight.
	 *
	 * @param newNumberIsotopeRunsWeight the new number isotope runs weight
	 */
	public void setNumberIsotopeRunsWeight(int newNumberIsotopeRunsWeight){numberIsotopeRunsWeight = newNumberIsotopeRunsWeight;}
	
	/**
	 * Gets the number isotope runs scale.
	 *
	 * @return the number isotope runs scale
	 */
	public int getNumberIsotopeRunsScale(){return numberIsotopeRunsScale;}
	
	/**
	 * Sets the number isotope runs scale.
	 *
	 * @param newNumberIsotopeRunsScale the new number isotope runs scale
	 */
	public void setNumberIsotopeRunsScale(int newNumberIsotopeRunsScale){numberIsotopeRunsScale = newNumberIsotopeRunsScale;}
	
	/**
	 * Gets the element synth movie report.
	 *
	 * @return the element synth movie report
	 */
	public String getElementSynthMovieReport(){return elementSynthMovieReport;}
	
	/**
	 * Sets the element synth movie report.
	 *
	 * @param newElementSynthMovieReport the new element synth movie report
	 */
	public void setElementSynthMovieReport(String newElementSynthMovieReport){elementSynthMovieReport = newElementSynthMovieReport;}
	
	/**
	 * Gets the animator type.
	 *
	 * @return the animator type
	 */
	public String getAnimatorType(){return animatorType;}
	
	/**
	 * Sets the animator type.
	 *
	 * @param newAnimatorType the new animator type
	 */
	public void setAnimatorType(String newAnimatorType){animatorType = newAnimatorType;}
	
	/**
	 * Gets the abund color values.
	 *
	 * @return the abund color values
	 */
	public double[] getAbundColorValues(){return abundColorValues;}
	
	/**
	 * Sets the abund color values.
	 *
	 * @param newAbundColorValues the new abund color values
	 */
	public void setAbundColorValues(double[] newAbundColorValues){abundColorValues = newAbundColorValues;}
	
	/**
	 * Gets the der color values.
	 *
	 * @return the der color values
	 */
	public double[] getDerColorValues(){return derColorValues;}
	
	/**
	 * Sets the der color values.
	 *
	 * @param newDerColorValues the new der color values
	 */
	public void setDerColorValues(double[] newDerColorValues){derColorValues = newDerColorValues;}
	
	/**
	 * Gets the flux color values.
	 *
	 * @return the flux color values
	 */
	public double[] getFluxColorValues(){return fluxColorValues;}
	
	/**
	 * Sets the flux color values.
	 *
	 * @param newFluxColorValues the new flux color values
	 */
	public void setFluxColorValues(double[] newFluxColorValues){fluxColorValues = newFluxColorValues;}
	
	/**
	 * Gets the final abund color values.
	 *
	 * @return the final abund color values
	 */
	public double[] getFinalAbundColorValues(){return finalAbundColorValues;}
	
	/**
	 * Sets the final abund color values.
	 *
	 * @param newFinalAbundColorValues the new final abund color values
	 */
	public void setFinalAbundColorValues(double[] newFinalAbundColorValues){finalAbundColorValues = newFinalAbundColorValues;}

	public double[] getFinalWeightedAbundColorValues(){return finalWeightedAbundColorValues;}
	public void setFinalWeightedAbundColorValues(double[] newFinalWeightedAbundColorValues){finalWeightedAbundColorValues = newFinalWeightedAbundColorValues;}

	public double[] getIntFluxColorValues(){return intFluxColorValues;}
	public void setIntFluxColorValues(double[] newIntFluxColorValues){intFluxColorValues = newIntFluxColorValues;}

	public Vector getAbundBinData(){return abundBinData;}
	public void setAbundBinData(Vector newAbundBinData){abundBinData = newAbundBinData;}
	
	public Vector getDerBinData(){return derBinData;}
	public void setDerBinData(Vector newDerBinData){derBinData = newDerBinData;}

	public Vector getFluxBinData(){return fluxBinData;}
	public void setFluxBinData(Vector newFluxBinData){fluxBinData = newFluxBinData;}

	public Vector getFinalAbundBinData(){return finalAbundBinData;}
	public void setFinalAbundBinData(Vector newFinalAbundBinData){finalAbundBinData = newFinalAbundBinData;}
	
	public Vector getFinalWeightedAbundBinData(){return finalWeightedAbundBinData;}
	public void setFinalWeightedAbundBinData(Vector newFinalWeightedAbundBinData){finalWeightedAbundBinData = newFinalWeightedAbundBinData;}
	
	public Vector getIntFluxBinData(){return intFluxBinData;}
	public void setIntFluxBinData(Vector newIntFluxBinData){intFluxBinData = newIntFluxBinData;}
	
	public String getAbundScheme(){return abundScheme;}
	public void setAbundScheme(String newAbundScheme){abundScheme = newAbundScheme;}
	
	public String getDerScheme(){return derScheme;}
	public void setDerScheme(String newDerScheme){derScheme = newDerScheme;}
	
	public String getFluxScheme(){return fluxScheme;}
	public void setFluxScheme(String newFluxScheme){fluxScheme = newFluxScheme;}

	public String getFinalAbundScheme(){return finalAbundScheme;}
	public void setFinalAbundScheme(String newFinalAbundScheme){finalAbundScheme = newFinalAbundScheme;}
	
	public String getFinalWeightedAbundScheme(){return finalWeightedAbundScheme;}
	public void setFinalWeightedAbundScheme(String newFinalWeightedAbundScheme){finalWeightedAbundScheme = newFinalWeightedAbundScheme;}
	
	public String getIntFluxScheme(){return intFluxScheme;}
	public void setIntFluxScheme(String newIntFluxScheme){intFluxScheme = newIntFluxScheme;}
	
	public boolean getAbundIncludeValues(){return abundIncludeValues;}
	public void setAbundIncludeValues(boolean newAbundIncludeValues){abundIncludeValues = newAbundIncludeValues;}

	public boolean getDerIncludeValues(){return derIncludeValues;}
	public void setDerIncludeValues(boolean newDerIncludeValues){derIncludeValues = newDerIncludeValues;}
	
	public boolean getFluxIncludeValues(){return fluxIncludeValues;}
	public void setFluxIncludeValues(boolean newFluxIncludeValues){fluxIncludeValues = newFluxIncludeValues;}
	
	public boolean getFinalAbundIncludeValues(){return finalAbundIncludeValues;}
	public void setFinalAbundIncludeValues(boolean newFinalAbundIncludeValues){finalAbundIncludeValues = newFinalAbundIncludeValues;}
	
	public boolean getFinalWeightedAbundIncludeValues(){return finalWeightedAbundIncludeValues;}
	public void setFinalWeightedAbundIncludeValues(boolean newFinalWeightedAbundIncludeValues){finalWeightedAbundIncludeValues = newFinalWeightedAbundIncludeValues;}

	public boolean getIntFluxIncludeValues(){return intFluxIncludeValues;}
	public void setIntFluxIncludeValues(boolean newIntFluxIncludeValues){intFluxIncludeValues = newIntFluxIncludeValues;}
	
	public String getPaths(){return paths;}
	public void setPaths(String newPaths){paths = newPaths;}

	public String getPath(){return path;}
	public void setPath(String newPath){path = newPath;}

	public String getZones(){return zones;}
	public void setZones(String newZones){zones = newZones;}
	
	public String getZone(){return zone;}
	public void setZone(String newZone){zone = newZone;}

	public String getIsotopes(){return isotopes;}
	public void setIsotopes(String newIsotopes){isotopes = newIsotopes;}
	
	public String getLibrary(){return library;}
	public void setLibrary(String newLibrary){library = newLibrary;}
	
	public String getReactions(){return reactions;}
	public void setReactions(String newReactions){reactions = newReactions;}
	
	public String getArguments(){return arguments;}
	public void setArguments(String newArguments){arguments = newArguments;}
	
	public String getSimulation(){return simulation;}
	public void setSimulation(String newSimulation){simulation = newSimulation;}

	public String getFrame_skip_interval(){return frame_skip_interval;}
	public void setFrame_skip_interval(String newFrame_skip_interval){frame_skip_interval = newFrame_skip_interval;}
	
	public String getSum(){return sum;}
	public void setSum(String newSum){sum = newSum;}

	public String getFinal_step(){return final_step;}
	public void setFinal_step(String newFinal_step){final_step = newFinal_step;}
	
	public String getRates(){return rates;}
	public void setRates(String newRates){rates = newRates;} 
	
	public String getProperties(){return properties;}
	public void setProperties(String newProperties){properties = newProperties;} 

	public String getGroup(){return group;}
	public void setGroup(String newGroup){group = newGroup;}

	public double getTimeminEdot(){return timeminEdot;}
	public void setTimeminEdot(double newTimeminEdot){timeminEdot = newTimeminEdot;}

	public double getTimemaxEdot(){return timemaxEdot;}
	public void setTimemaxEdot(double newTimemaxEdot){timemaxEdot = newTimemaxEdot;}

	public int getEdotmin(){return edotmin;}
	public void setEdotmin(int newEdotmin){edotmin = newEdotmin;}
	
	public int getEdotmax(){return edotmax;}
	public void setEdotmax(int newEdotmax){edotmax = newEdotmax;}

	public double[][] getTimeDataArrayEdot(){return timeDataArrayEdot;}
	public void setTimeDataArrayEdot(double[][] newTimeDataArrayEdot){timeDataArrayEdot = newTimeDataArrayEdot;}

	public double[][] getEdotDataArray(){return edotDataArray;}
	public void setEdotDataArray(double[][] newEdotDataArray){edotDataArray = newEdotDataArray;}

}

class AbundRowData extends Vector{

	public AbundRowData(int min, int max, boolean show, Color color){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(color);
	
	}

}

class DerRowData extends Vector{

	public DerRowData(int min, int max, boolean show, Color posColor, Color negColor){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(posColor);
		addElement(negColor);
	
	}

}

class FluxRowData extends Vector{

	public FluxRowData(int min, int max, boolean show, Color color, int linestyle, int linewidth){
	
		addElement(new Integer(min));
		addElement(new Integer(max));
		addElement(new Boolean(show));
		addElement(color);
		addElement(new Integer(linestyle));
		addElement(new Integer(linewidth));
	
	}

}
