package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.datastructure.util.SimTypeDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.datastructure.util.RateDataStructure;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.enums.SimCat;

public class ElementSynthDataStructure implements DataStructure{
	
	private String libGroup;
	private String elementSynthSetupReport, simName, simWorkflowIndices, notes, isotope, type;
	private Point minZA, maxZA;
	private double finalTime;
	private int numberTimeSteps;
	private boolean simDone;
	private boolean simSaved;
	private Vector<String> nucSimVector;
	private TreeMap<SimCat, ArrayList<SimTypeDataStructure>> simTypeMap;
	private int numberPublicLibraryDataStructures;
	private int numberUserLibraryDataStructures;
	private int numberSharedLibraryDataStructures;
	private LibraryDataStructure[] publicLibraryDataStructureArray, userLibraryDataStructureArray, sharedLibraryDataStructureArray;
	private LibraryDataStructure currentLibraryDataStructure;
	private int numberPublicNucSimSetDataStructures;
	private int numberUserNucSimSetDataStructures;
	private int numberSharedNucSimSetDataStructures;
	private NucSimSetDataStructure[] publicNucSimSetDataStructureArray, userNucSimSetDataStructureArray, sharedNucSimSetDataStructureArray;
	private NucSimSetDataStructure currentNucSimSetDataStructure;
	private RateDataStructure rateDataStructure;
	private String statusText;
	private String errorText;
	private String saveNucSimSetReport;
	private String fileType;
	private String abundFile;
	private String thermoFile;
	private String elementSynthSummary;
	private boolean useSensStudy;
	private String addMissingInvRatesReport;
	private String report;
	private String[] rateIDArray;
	private String group;
	private String minIsotope;
	private String maxIsotope;
	private String path;
	private String name;
	private String overwrite;
	private String zone;
	private String rates;
	private String properties;
	private String src_lib;
	private String dest_lib;
	private String dest_group;
	private String del_src_lib;
	private String chk_temp_behavior;
	private String chk_overflow;
	private String chk_inverse;
	private String make_inverse;
	private ArrayList<String> libDirList = new ArrayList<String>();
	private ArrayList<String> libDirLibList = new ArrayList<String>();
	private ArrayList<String> sensLibList = new ArrayList<String>();
	private boolean useLibDir, useCustomThermo, useOneToOneLooping;
	private boolean useExistingSet;
	private int numSingleLoopingSims;
	private String loopingType;
	private String saveSimWorkflowRunMessage;
	private TreeMap<String, ThermoProfileSetDataStructure> setMap;
	private TreeMap<String, ElementSimWorkDataStructure> simWorkMap;
	private TreeMap<String, ElementSimWorkRunDataStructure> simWorkRunMap;
	private String paths, reactionRateID;
	private FolderType folderType;
	private int sensSimWorkflowRunIndex;
	private ElementSimWorkRunDataStructure currentSimWorkRunDataStructure;
	private ElementSimWorkDataStructure currentSimWorkDataStructure;
	private ElementSimWorkDataStructure compareSimWorkDataStructure;
	private RateLibDataStructure rateLibDataStructure;

	public ElementSynthDataStructure(){
		initialize();
	}
	
	public void initialize(){
		
		setType("");
		setIsotope("");
		setNotes("");
		setRateLibDataStructure(new RateLibDataStructure());
		setCurrentSimWorkRunDataStructure(new ElementSimWorkRunDataStructure());
		setCurrentSimWorkDataStructure(new ElementSimWorkDataStructure());
		setCompareSimWorkDataStructure(new ElementSimWorkDataStructure());
		setLibGroup("");
		setSimDone(false);
		setSimSaved(false);
		setNucSimVector(null);
		setSimName("");
		setMake_inverse("NO");
		setSRC_LIB("");
		setDEST_LIB("");
		setDEST_GROUP("");
		setDEL_SRC_LIB("NO");
		setCHK_TEMP_BEHAVIOR("NO");
		setCHK_OVERFLOW("NO");
		setCHK_INVERSE("NO");
		setRateIDArray(null);
		setRates("");
		setProperties("");
		setRateDataStructure(null);
		setSimTypeMap(null);
		setNumberPublicLibraryDataStructures(0);
		setNumberUserLibraryDataStructures(0);
		setNumberSharedLibraryDataStructures(0);
		setPublicLibraryDataStructureArray(null); 
		setUserLibraryDataStructureArray(null);
		setSharedLibraryDataStructureArray(null);
		setCurrentLibraryDataStructure(new LibraryDataStructure());
		setNumberPublicNucSimSetDataStructures(0);
		setNumberUserNucSimSetDataStructures(0);
		setNumberSharedNucSimSetDataStructures(0);
		setPublicNucSimSetDataStructureArray(null); 
		setUserNucSimSetDataStructureArray(null);
		setSharedNucSimSetDataStructureArray(null);
		setCurrentNucSimSetDataStructure(new NucSimSetDataStructure());
		setReport("");
		setElementSynthSetupReport("");
		setNumberTimeSteps(0);
		setMinZA(new Point(0, 0));
		setMaxZA(new Point(24, 54));
		setStatusText("");
		setErrorText("");
		setSaveNucSimSetReport("");
		setFileType("");
		setAbundFile("");
		setThermoFile("");
		setElementSynthSummary("");
		setAddMissingInvRatesReport("");
		setFinalTime(0.0);
		setReport("");
		setMinIsotope("");
		setMaxIsotope("");
		setPath("");
		setName("");
		setOverwrite("");
		setZone("");
		setUseSensStudy(false);
		setUseLibDir(false);
		setUseCustomThermo(false);
		setUseExistingSet(false);
		setUseOneToOneLooping(false);
		setNumSingleLoopingSims(0);
		setLoopingType("");
		setSaveSimWorkflowRunMessage("");
		setFolderType(FolderType.ALL);
		setPaths("");
		setSetMap(null);
		setSimWorkflowIndices("");
		setSimWorkRunMap(null);
		setSimWorkMap(null);
		setSimWorkflowIndices("");
		setPaths(paths);
		setSensSimWorkflowRunIndex(-1);
		setReactionRateID("");
		setGroup("");
	}
	
	public String getType(){return type;} 
	public void setType(String type){this.type = type;}
	
	public RateLibDataStructure getRateLibDataStructure(){return rateLibDataStructure;}
	public void setRateLibDataStructure(RateLibDataStructure rateLibDataStructure){this.rateLibDataStructure = rateLibDataStructure;}
	
	public String getNotes(){return notes;} 
	public void setNotes(String notes){this.notes = notes;}
	
	public String getIsotope(){return isotope;} 
	public void setIsotope(String isotope){this.isotope = isotope;}
	
	public String getLibGroup(){return libGroup;}
	public void setLibGroup(String newLibGroup){libGroup = newLibGroup;}
	
	public ElementSimWorkDataStructure getCompareSimWorkDataStructure(){return compareSimWorkDataStructure;}
	public void setCompareSimWorkDataStructure(ElementSimWorkDataStructure compareSimWorkDataStructure){this.compareSimWorkDataStructure = compareSimWorkDataStructure;}
	
	public ElementSimWorkDataStructure getCurrentSimWorkDataStructure(){return currentSimWorkDataStructure;}
	public void setCurrentSimWorkDataStructure(ElementSimWorkDataStructure currentSimWorkDataStructure){this.currentSimWorkDataStructure = currentSimWorkDataStructure;}
	
	public ElementSimWorkRunDataStructure getCurrentSimWorkRunDataStructure(){return currentSimWorkRunDataStructure;}
	public void setCurrentSimWorkRunDataStructure(ElementSimWorkRunDataStructure currentSimWorkRunDataStructure){this.currentSimWorkRunDataStructure = currentSimWorkRunDataStructure;}
	
	public TreeMap<String, ElementSimWorkRunDataStructure> getSimWorkRunMap(){return simWorkRunMap;}
	public void setSimWorkRunMap(TreeMap<String, ElementSimWorkRunDataStructure> simWorkRunMap){this.simWorkRunMap = simWorkRunMap;}
	
	public TreeMap<String, ElementSimWorkDataStructure> getSimWorkMap(){return simWorkMap;}
	public void setSimWorkMap(TreeMap<String, ElementSimWorkDataStructure> simWorkMap){this.simWorkMap = simWorkMap;}

	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public String getPaths(){return paths;}
	public void setPaths(String paths){this.paths = paths;}
	
	public TreeMap<String, ThermoProfileSetDataStructure> getSetMap(){return setMap;}
	public void setSetMap(TreeMap<String, ThermoProfileSetDataStructure> setMap){this.setMap = setMap;}
	
	public String getSaveSimWorkflowRunMessage(){return saveSimWorkflowRunMessage;}
	public void setSaveSimWorkflowRunMessage(String newSaveSimWorkflowRunMessage){saveSimWorkflowRunMessage = newSaveSimWorkflowRunMessage;}
	
	public String getLoopingType(){return loopingType;}
	public void setLoopingType(String newLoopingType){loopingType = newLoopingType;}
	
	public int getNumSingleLoopingSims(){return numSingleLoopingSims;}
	public void setNumSingleLoopingSims(int newNumSingleLoopingSims){numSingleLoopingSims = newNumSingleLoopingSims;}
	
	public boolean getUseOneToOneLooping(){return useOneToOneLooping;}
	public void setUseOneToOneLooping(boolean newUseOneToOneLooping){useOneToOneLooping = newUseOneToOneLooping;}
	
	public boolean getUseExistingSet(){return useExistingSet;}
	public void setUseExistingSet(boolean newUseExistingSet){useExistingSet = newUseExistingSet;}
	
	public boolean getUseSensStudy(){return useSensStudy;}
	public void setUseSensStudy(boolean newUseSensStudy){useSensStudy = newUseSensStudy;}
	
	public boolean getUseCustomThermo(){return useCustomThermo;}
	public void setUseCustomThermo(boolean newUseCustomThermo){useCustomThermo = newUseCustomThermo;}
	
	public boolean getUseLibDir(){return useLibDir;}
	public void setUseLibDir(boolean newUseLibDir){useLibDir = newUseLibDir;}
	
	public ArrayList<String> getSensLibList(){return sensLibList;}
	public void setSensLibList(ArrayList<String> newSensLibList){sensLibList = newSensLibList;}
	
	public ArrayList<String> getLibDirLibList(){return libDirLibList;}
	public void setLibDirLibList(ArrayList<String> newLibDirLibList){libDirLibList = newLibDirLibList;}
	
	public ArrayList<String> getLibDirList(){return libDirList;}
	public void setLibDirList(ArrayList<String> newLibDirList){libDirList = newLibDirList;}

	public Vector getNucSimVector(){return nucSimVector;}
	public void setNucSimVector(Vector newNucSimVector){nucSimVector = newNucSimVector;}
	
	public boolean getSimDone(){return simDone;}
	public void setSimDone(boolean newSimDone){simDone = newSimDone;}
	
	public boolean getSimSaved(){return simSaved;}
	public void setSimSaved(boolean newSimSaved){simSaved = newSimSaved;}
	
	public String getMake_inverse(){return make_inverse;}
	public void setMake_inverse(String newMake_inverse){make_inverse = newMake_inverse;}
	
	public RateDataStructure getRateDataStructure(){return rateDataStructure;}
	public void setRateDataStructure(RateDataStructure newRateDataStructure){rateDataStructure = newRateDataStructure;}
	
	public String[] getRateIDArray(){return rateIDArray;}
	public void setRateIDArray(String[] newRateIDArray){rateIDArray = newRateIDArray;}
	
	public String getProperties(){return properties;}
	public void setProperties(String newProperties){properties = newProperties;}
	
	public String getSimName(){return simName;}
	public void setSimName(String newSimName){simName = newSimName;}
	
	public String getRates(){return rates;}
	public void setRates(String newRates){rates = newRates;}
	
	public Point getMinZA(){return minZA;}
	public void setMinZA(Point newMinZA){minZA = newMinZA;}
	
	public int getNumberTimeSteps(){return numberTimeSteps;}
	public void setNumberTimeSteps(int newNumberTimeSteps){numberTimeSteps = newNumberTimeSteps;}
	
	public Point getMaxZA(){return maxZA;}
	public void setMaxZA(Point newMaxZA){maxZA = newMaxZA;}

	public String getElementSynthSetupReport(){return elementSynthSetupReport;}
	public void setElementSynthSetupReport(String newElementSynthSetupReport){elementSynthSetupReport = newElementSynthSetupReport;}
	
	public TreeMap<SimCat, ArrayList<SimTypeDataStructure>> getSimTypeMap(){return simTypeMap;}
	public void setSimTypeMap(TreeMap<SimCat, ArrayList<SimTypeDataStructure>> newSimTypeMap){simTypeMap = newSimTypeMap;}
	
	public int getNumberPublicLibraryDataStructures(){return numberPublicLibraryDataStructures;}
	public void setNumberPublicLibraryDataStructures(int newNumberPublicLibraryDataStructures){numberPublicLibraryDataStructures = newNumberPublicLibraryDataStructures;}
	
	public int getNumberUserLibraryDataStructures(){return numberUserLibraryDataStructures;}
	public void setNumberUserLibraryDataStructures(int newNumberUserLibraryDataStructures){numberUserLibraryDataStructures = newNumberUserLibraryDataStructures;}
	
	public int getNumberSharedLibraryDataStructures(){return numberSharedLibraryDataStructures;}
	public void setNumberSharedLibraryDataStructures(int newNumberSharedLibraryDataStructures){numberSharedLibraryDataStructures = newNumberSharedLibraryDataStructures;}
	
	public LibraryDataStructure[] getPublicLibraryDataStructureArray(){return publicLibraryDataStructureArray;}
	public void setPublicLibraryDataStructureArray(LibraryDataStructure[] newPublicLibraryDataStructureArray){publicLibraryDataStructureArray = newPublicLibraryDataStructureArray;}
	
	public LibraryDataStructure[] getUserLibraryDataStructureArray(){return userLibraryDataStructureArray;}
	public void setUserLibraryDataStructureArray(LibraryDataStructure[] newUserLibraryDataStructureArray){userLibraryDataStructureArray = newUserLibraryDataStructureArray;}

	public LibraryDataStructure[] getSharedLibraryDataStructureArray(){return sharedLibraryDataStructureArray;}
	public void setSharedLibraryDataStructureArray(LibraryDataStructure[] newSharedLibraryDataStructureArray){sharedLibraryDataStructureArray = newSharedLibraryDataStructureArray;}
	
	public LibraryDataStructure getCurrentLibraryDataStructure(){return currentLibraryDataStructure;}
	public void setCurrentLibraryDataStructure(LibraryDataStructure newCurrentLibraryDataStructure){currentLibraryDataStructure = newCurrentLibraryDataStructure;}

	public int getNumberPublicNucSimSetDataStructures(){return numberPublicNucSimSetDataStructures;}
	public void setNumberPublicNucSimSetDataStructures(int newNumberPublicNucSimSetDataStructures){numberPublicNucSimSetDataStructures = newNumberPublicNucSimSetDataStructures;}
	
	public int getNumberUserNucSimSetDataStructures(){return numberUserNucSimSetDataStructures;}
	public void setNumberUserNucSimSetDataStructures(int newNumberUserNucSimSetDataStructures){numberUserNucSimSetDataStructures = newNumberUserNucSimSetDataStructures;}
	
	public int getNumberSharedNucSimSetDataStructures(){return numberSharedNucSimSetDataStructures;}
	public void setNumberSharedNucSimSetDataStructures(int newNumberSharedNucSimSetDataStructures){numberSharedNucSimSetDataStructures = newNumberSharedNucSimSetDataStructures;}
	
	public NucSimSetDataStructure[] getPublicNucSimSetDataStructureArray(){return publicNucSimSetDataStructureArray;}
	public void setPublicNucSimSetDataStructureArray(NucSimSetDataStructure[] newPublicNucSimSetDataStructureArray){publicNucSimSetDataStructureArray = newPublicNucSimSetDataStructureArray;}
	
	public NucSimSetDataStructure[] getUserNucSimSetDataStructureArray(){return userNucSimSetDataStructureArray;}
	public void setUserNucSimSetDataStructureArray(NucSimSetDataStructure[] newUserNucSimSetDataStructureArray){userNucSimSetDataStructureArray = newUserNucSimSetDataStructureArray;}
	
	public NucSimSetDataStructure[] getSharedNucSimSetDataStructureArray(){return sharedNucSimSetDataStructureArray;}
	public void setSharedNucSimSetDataStructureArray(NucSimSetDataStructure[] newSharedNucSimSetDataStructureArray){sharedNucSimSetDataStructureArray = newSharedNucSimSetDataStructureArray;}
	
	public NucSimSetDataStructure getCurrentNucSimSetDataStructure(){return currentNucSimSetDataStructure;}
	public void setCurrentNucSimSetDataStructure(NucSimSetDataStructure newCurrentNucSimSetDataStructure){currentNucSimSetDataStructure = newCurrentNucSimSetDataStructure;}
	
	public double getFinalTime(){return finalTime;}
	public void setFinalTime(double newFinalTime){finalTime = newFinalTime;}
	
	public String getReport(){return report;}
	public void setReport(String newReport){report = newReport;}

	public String getStatusText(){return statusText;}
	public void setStatusText(String newStatusText){statusText = newStatusText;}
	
	public String getErrorText(){return errorText;}
	public void setErrorText(String newErrorText){errorText = newErrorText;}
	
	public String getSaveNucSimSetReport(){return saveNucSimSetReport;}
	public void setSaveNucSimSetReport(String newSaveNucSimSetReport){saveNucSimSetReport = newSaveNucSimSetReport;}

	public String getFileType(){return fileType;}
	public void setFileType(String newFileType){fileType = newFileType;}
	
	public String getThermoFile(){return thermoFile;}
	public void setThermoFile(String newThermoFile){thermoFile = newThermoFile;}
	
	public String getAbundFile(){return abundFile;}
	public void setAbundFile(String newAbundFile){abundFile = newAbundFile;}
	
	public String getElementSynthSummary(){return elementSynthSummary;}
	public void setElementSynthSummary(String newElementSynthSummary){elementSynthSummary = newElementSynthSummary;}
	
	public String getAddMissingInvRatesReport(){return addMissingInvRatesReport;}
	public void setAddMissingInvRatesReport(String newAddMissingInvRatesReport){addMissingInvRatesReport = newAddMissingInvRatesReport;}
	
	public String getGroup(){return group;}
	public void setGroup(String newGroup){group = newGroup;}
	
	public String getMinIsotope(){return minIsotope;}
	public void setMinIsotope(String newMinIsotope){minIsotope = newMinIsotope;}
	
	public String getMaxIsotope(){return maxIsotope;}
	public void setMaxIsotope(String newMaxIsotope){maxIsotope = newMaxIsotope;}
	
	public String getPath(){return path;}
	public void setPath(String newPath){path = newPath;}
	
	public String getName(){return name;}
	public void setName(String newName){name = newName;}

	public String getOverwrite(){return overwrite;}
	public void setOverwrite(String newOverwrite){overwrite = newOverwrite;}
	
	public String getZone(){return zone;}
	public void setZone(String newZone){zone = newZone;}
	
	public String getSRC_LIB(){return src_lib;}
	public void setSRC_LIB(String newSRC_LIB){src_lib = newSRC_LIB;}
	
	public String getDEST_LIB(){return dest_lib;}
	public void setDEST_LIB(String newDEST_LIB){dest_lib = newDEST_LIB;}
	
	public String getDEST_GROUP(){return dest_group;}
	public void setDEST_GROUP(String newDEST_GROUP){dest_group = newDEST_GROUP;}
	
	public String getDEL_SRC_LIB(){return del_src_lib;}
	public void setDEL_SRC_LIB(String newDEL_SRC_LIB){del_src_lib = newDEL_SRC_LIB;}
	
	public String getCHK_TEMP_BEHAVIOR(){return chk_temp_behavior;}
	public void setCHK_TEMP_BEHAVIOR(String newCHK_TEMP_BEHAVIOR){chk_temp_behavior = newCHK_TEMP_BEHAVIOR;}

	public String getCHK_OVERFLOW(){return chk_overflow;}
	public void setCHK_OVERFLOW(String newCHK_OVERFLOW){chk_overflow = newCHK_OVERFLOW;}
	
	public String getCHK_INVERSE(){return chk_inverse;}
	public void setCHK_INVERSE(String newCHK_INVERSE){chk_inverse = newCHK_INVERSE;}

	public String getSimWorkflowIndices(){return simWorkflowIndices;}
	public void setSimWorkflowIndices(String newSimWorkflowIndices){simWorkflowIndices = newSimWorkflowIndices;}

	public int getSensSimWorkflowRunIndex(){return sensSimWorkflowRunIndex;}
	public void setSensSimWorkflowRunIndex(int newSensSimWorkflowRunIndex){sensSimWorkflowRunIndex = newSensSimWorkflowRunIndex;}
	
	public String getReactionRateID(){return reactionRateID;}
	public void setReactionRateID(String newReactionRateID){reactionRateID = newReactionRateID;}

}