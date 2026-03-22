package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.enums.*;

public class ElementSimWorkDataStructure implements DataStructure, Comparable<ElementSimWorkDataStructure>{

	private String name, path, library, libGroup; 
	private SimWorkflowMode simWorkflowMode;
	private FolderType folderType;
	private int index;
	private int[] zoneArray;
	private boolean includeWeak;
	private boolean includeScreening, nameExists, inUse;
	private String notes;
	private String initAbundPath;
	private String thermoPath;
	private String maxTimesteps;
	private String startTime;
	private String stopTime;
	private String sunetPath;
	private String scaleFactors;
	private String reactionRate, params;
	private String zones;
	private String reactionRateID, al26Type, creationDate, libraryPath, libDir, simType;
	private ThermoProfileSetDataStructure thermoProfileSet;
	private SimTypeDataStructure simTypeDataStructure;
	private ReactionDataStructure reactionDataStructure;
	
	public ElementSimWorkDataStructure(){initialize();}
	
	public void initialize(){
		setPath("");
		setName("");
		setFolderType(null);
		setIndex(-1);
		setSimWorkflowMode(SimWorkflowMode.SINGLE_STANDARD);
		setInitAbundPath("");
		setThermoPath("");
		setMaxTimesteps("");
		setStartTime("");
		setStopTime("");
		setSunetPath("");
		setIncludeWeak(true);
		setIncludeScreening(true);
		setNotes("");
		setReactionRate("");
		setParams("");
		setZones("");
		setReactionRateID("");
		setCreationDate("");
		setLibraryPath("");
		setLibDir("");
		setSimType("");
		setLibrary("ReaclibV2.2");
		setLibGroup("PUBLIC");
		setAl26Type("METASTABLE");
		setNameExists(false);
		setInUse(false);
		setScaleFactors("");
		setThermoProfileSet(new ThermoProfileSetDataStructure());
		setReactionDataStructure(new ReactionDataStructure());
		setSimTypeDataStructure(null);
		setZoneArray(new int[0]);
	}
	
	public int compareTo(ElementSimWorkDataStructure eswds){
	
		print();
		eswds.print();
	
		if(eswds.simWorkflowMode != simWorkflowMode ||
			!eswds.library.equals(library) ||
			eswds.includeWeak != includeWeak ||
			eswds.includeScreening != includeScreening ||
			!eswds.initAbundPath.equals(initAbundPath) ||
			!eswds.thermoPath.equals(thermoPath) ||
			!eswds.maxTimesteps.equals(maxTimesteps) ||
			!eswds.startTime.equals(startTime) ||
			!eswds.stopTime.equals(stopTime) ||
			!eswds.sunetPath.equals(sunetPath) ||
			!eswds.scaleFactors.equals(scaleFactors) ||
			!eswds.reactionRate.equals(reactionRate) ||
			!eswds.params.equals(params) ||
			!eswds.zones.equals(zones) ||
			!eswds.al26Type.equals(al26Type) ||
			!eswds.libraryPath.equals(libraryPath) ||
			!eswds.libDir.equals(libDir) ||
			!eswds.simType.equals(simType)){
			return 1;
		}
		return 0;
	}
	
	public void print(){
		String string = "";
		string += simWorkflowMode + "\n";
		string += library + "\n";
		string += includeWeak + "\n";
		string += includeScreening + "\n";
		string += initAbundPath + "\n";
		string += thermoPath + "\n";
		string += maxTimesteps + "\n";
		string += startTime + "\n";
		string += stopTime + "\n";
		string += sunetPath + "\n";
		string += scaleFactors + "\n";
		string += reactionRate + "\n";
		string += params + "\n";
		string += zones + "\n";
		string += reactionRateID + "\n";
		string += al26Type + "\n";
		string += libraryPath + "\n";
		string += libDir + "\n";
		string += simType + "\n";
	}
	
	public int[] getZoneArray(){return zoneArray;}
	public void setZoneArray(int[] newZoneArray){zoneArray = newZoneArray;}
	
	public ReactionDataStructure getReactionDataStructure(){return reactionDataStructure;}
	public void setReactionDataStructure(ReactionDataStructure newReactionDataStructure){reactionDataStructure = newReactionDataStructure;}
	
	public SimTypeDataStructure getSimTypeDataStructure(){return simTypeDataStructure;}
	public void setSimTypeDataStructure(SimTypeDataStructure newSimTypeDataStructure){simTypeDataStructure = newSimTypeDataStructure;}
	
	public ThermoProfileSetDataStructure getThermoProfileSet(){return thermoProfileSet;}
	public void setThermoProfileSet(ThermoProfileSetDataStructure newThermoProfileSet){thermoProfileSet = newThermoProfileSet;}
	
	public boolean getInUse(){return inUse;}
	public void setInUse(boolean newInUse){inUse = newInUse;}
	
	public boolean getNameExists(){return nameExists;}
	public void setNameExists(boolean newNameExists){nameExists = newNameExists;}
	
	public String getCreationDate(){return creationDate;}
	public void setCreationDate(String newCreationDate){creationDate = newCreationDate;}
	
	public String getLibraryPath(){return libraryPath;}
	public void setLibraryPath(String newLibraryPath){libraryPath = newLibraryPath;}
	
	public String getLibDir(){return libDir;}
	public void setLibDir(String newLibDir){libDir = newLibDir;}
	
	public String getSimType(){return simType;}
	public void setSimType(String newSimType){simType = newSimType;}
	
	public SimWorkflowMode getSimWorkflowMode(){return simWorkflowMode;}
	public void setSimWorkflowMode(SimWorkflowMode newSimWorkflowMode){simWorkflowMode = newSimWorkflowMode;}
	
	public int getIndex(){return index;}
	public void setIndex(int index){this.index = index;}
	
	public String getPath(){return path;}
	public void setPath(String path){this.path = path;}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public boolean getIncludeWeak(){return includeWeak;}
	public void setIncludeWeak(boolean newIncludeWeak){includeWeak = newIncludeWeak;}
	
	public boolean getIncludeScreening(){return includeScreening;}
	public void setIncludeScreening(boolean newIncludeScreening){includeScreening = newIncludeScreening;}

	public String getSunetPath(){return sunetPath;} 
	public void setSunetPath(String newSunetPath){sunetPath = newSunetPath;}
	
	public String getNotes(){return notes;}
	public void setNotes(String newNotes){notes = newNotes;}
	
	public String getInitAbundPath(){return initAbundPath;}
	public void setInitAbundPath(String newInitAbundPath){initAbundPath = newInitAbundPath;}
	
	public String getThermoPath(){return thermoPath;}
	public void setThermoPath(String newThermoPath){thermoPath = newThermoPath;}
	
	public String getMaxTimesteps(){return maxTimesteps;}
	public void setMaxTimesteps(String newMaxTimesteps){maxTimesteps = newMaxTimesteps;}
	
	public String getStartTime(){return startTime;}
	public void setStartTime(String newStartTime){startTime = newStartTime;}
	
	public String getStopTime(){return stopTime;}
	public void setStopTime(String newStopTime){stopTime = newStopTime;}
	
	public String getScaleFactors(){return scaleFactors;}
	public void setScaleFactors(String newScaleFactors){scaleFactors = newScaleFactors;}
	
	public String getParams(){return params;}
	public void setParams(String newParams){params = newParams;}
	
	public String getReactionRate(){return reactionRate;}
	public void setReactionRate(String newReactionRate){reactionRate = newReactionRate;}
	
	public String getZones(){return zones;}
	public void setZones(String newZones){zones = newZones;}
	
	public String getReactionRateID(){return reactionRateID;}
	public void setReactionRateID(String newReactionRateID){reactionRateID = newReactionRateID;}
	
	public String getAl26Type(){return al26Type;} 
	public void setAl26Type(String newAl26Type){al26Type = newAl26Type;}
	
	public String getLibrary(){return library;}
	public void setLibrary(String newLibrary){library = newLibrary;}
	
	public String getLibGroup(){return libGroup;}
	public void setLibGroup(String newLibGroup){libGroup = newLibGroup;}

	public String toString(){
		return name;
	}
	
}
