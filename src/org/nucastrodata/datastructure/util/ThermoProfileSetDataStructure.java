package org.nucastrodata.datastructure.util;

import java.io.File;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.enums.FolderType;

public class ThermoProfileSetDataStructure implements DataStructure, Comparable<ThermoProfileSetDataStructure>{
	
	private String name, desc, startTime, stopTime, path, creationDate, dir;
	private int numProfiles; 
	private File zipFile;
	private FolderType folderType;
	
	public ThermoProfileSetDataStructure(){initialize();}
	
	public void initialize(){

		setDir("");
		setPath("");	
		setName("");
		setFolderType(null);
		setDesc("");
		setStartTime("");
		setStopTime("");
		setNumProfiles(0);
		setZipFile(null);
		setCreationDate("");
		
	}
	
	public boolean equals(Object object){
		if(object instanceof ThermoProfileSetDataStructure){
			ThermoProfileSetDataStructure tpsds = (ThermoProfileSetDataStructure)object;
			return tpsds.getName().equals(getName()) && tpsds.getFolderType().equals(getFolderType());
		}
		return false;
	}
	
	public String toString(){
		return name;
	}
	
	public int compareTo(ThermoProfileSetDataStructure o) {
		return o.toString().compareToIgnoreCase(toString());
	}
	
	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public String getDir(){return dir;}
	public void setDir(String newDir){dir = newDir;}
	
	public String getPath(){return path;}
	public void setPath(String newPath){path = newPath;}
	
	public String getName(){return name;}
	public void setName(String newName){name = newName;}
	
	public File getZipFile(){return zipFile;}
	public void setZipFile(File newZipFile){zipFile = newZipFile;}
	
	public String getDesc(){return desc;}
	public void setDesc(String newDesc){desc = newDesc;}
	
	public String getStartTime(){return startTime;}
	public void setStartTime(String newStartTime){startTime = newStartTime;}
	
	public String getStopTime(){return stopTime;}
	public void setStopTime(String newStopTime){stopTime = newStopTime;}
	
	public int getNumProfiles(){return numProfiles;}
	public void setNumProfiles(int newNumProfiles){numProfiles = newNumProfiles;}

	public String getCreationDate(){return creationDate;}
	public void setCreationDate(String newCreationDate){creationDate = newCreationDate;}
	
}