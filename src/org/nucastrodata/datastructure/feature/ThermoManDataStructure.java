package org.nucastrodata.datastructure.feature;

import java.util.TreeMap;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.enums.FolderType;

public class ThermoManDataStructure implements DataStructure{
	
	private TreeMap<String, ThermoProfileSetDataStructure> setMap, setMapSelected;
	private String path, paths;
	private FolderType folderType;
	private ThermoProfileSetDataStructure tpsds;
	
	public ThermoManDataStructure(){initialize();}
	
	public void initialize(){
		setSetMap(null);
		setSetMapSelected(null);
		setPath("");
		setFolderType(null);
		setPaths("");
		setImportedThermoProfileSetDataStructure(null);
	}

	public String getPath(){return path;}
	public void setPath(String path){this.path = path;}
	
	public String getPaths(){return paths;}
	public void setPaths(String paths){this.paths = paths;}
	
	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public TreeMap<String, ThermoProfileSetDataStructure> getSetMap(){return setMap;}
	public void setSetMap(TreeMap<String, ThermoProfileSetDataStructure> setMap){this.setMap = setMap;}
	
	public TreeMap<String, ThermoProfileSetDataStructure> getSetMapSelected(){return setMapSelected;}
	public void setSetMapSelected(TreeMap<String, ThermoProfileSetDataStructure> setMapSelected){this.setMapSelected = setMapSelected;}

	public ThermoProfileSetDataStructure getImportedThermoProfileSetDataStructure(){return this.tpsds;}
	public void setImportedThermoProfileSetDataStructure(ThermoProfileSetDataStructure tpsds) {this.tpsds = tpsds;}
	

}
