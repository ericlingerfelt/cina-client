package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.enums.SimCat;


public class SimTypeDataStructure implements DataStructure{
	
	private String simTypeName, thermoPath, thermoDesc, description;
	private SimCat simCat;
	private String[] sunetPathArray, sunetDescArray, initAbundPathArray, initAbundDescArray;
	private int[] zoneArray;
	private double startTime;
	private double stopTime;
	
	public SimTypeDataStructure(){initialize();}
	
	public void initialize(){

		setSimTypeName("");
		setSimCat(null);
		setSunetPathArray(null);
		setSunetDescArray(null);
		setZoneArray(null);
		setInitAbundPathArray(null);
		setInitAbundDescArray(null);
		setThermoPath("");
		setThermoDesc("");
		setStartTime(-1.0);
		setStopTime(-1.0);
		setDescription("");
		
	}
	
	public SimCat getSimCat(){return simCat;}
	public void setSimCat(SimCat newSimCat){simCat = newSimCat;}
	
	public String getSimTypeName(){return simTypeName;}
	public void setSimTypeName(String newSimTypeName){simTypeName = newSimTypeName;}
	
	public String[] getSunetPathArray(){return sunetPathArray;}
	public void setSunetPathArray(String[] newSunetPathArray){sunetPathArray = newSunetPathArray;}
	
	public String[] getSunetDescArray(){return sunetDescArray;}
	public void setSunetDescArray(String[] newSunetDescArray){sunetDescArray = newSunetDescArray;}
	
	public int[] getZoneArray(){return zoneArray;}
	public void setZoneArray(int[] newZoneArray){zoneArray = newZoneArray;}
	
	public String[] getInitAbundPathArray(){return initAbundPathArray;}
	public void setInitAbundPathArray(String[] newInitAbundPathArray){initAbundPathArray = newInitAbundPathArray;}
	
	public String[] getInitAbundDescArray(){return initAbundDescArray;}
	public void setInitAbundDescArray(String[] newInitAbundDescArray){initAbundDescArray = newInitAbundDescArray;}
	
	public String getThermoPath(){return thermoPath;}
	public void setThermoPath(String newThermoPath){thermoPath = newThermoPath;}
	
	public String getThermoDesc(){return thermoDesc;}
	public void setThermoDesc(String newThermoDesc){thermoDesc = newThermoDesc;}
	
	public double getStartTime(){return startTime;}
	public void setStartTime(double newStartTime){startTime = newStartTime;}
	
	public double getStopTime(){return stopTime;}
	public void setStopTime(double newStopTime){stopTime = newStopTime;}	
	
	public String getDescription(){return description;}
	public void setDescription(String newDescription){description = newDescription;}

	public String toString(){
		return simTypeName;
	}
	
}