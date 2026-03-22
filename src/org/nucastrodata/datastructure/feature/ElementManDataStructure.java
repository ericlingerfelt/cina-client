package org.nucastrodata.datastructure.feature;

import java.util.*;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.ElementSimDataStructure;
import org.nucastrodata.enums.FolderType;

public class ElementManDataStructure implements DataStructure{

	private TreeMap<String, ElementSimDataStructure> simMap, simMapSelected;
	private String path, type, paths;
	private FolderType folderType;

	public ElementManDataStructure(){initialize();}
	
	public void initialize(){
		setSimMap(null);
		setSimMapSelected(null);
		setPath("");
		setType("");
		setPaths("");
		setFolderType(null);
	}
	
	public String getPath(){return path;}
	public void setPath(String path){this.path = path;}

	public String getPaths(){return paths;}
	public void setPaths(String paths){this.paths = paths;}
	
	public String getType(){return type;}
	public void setType(String type){this.type = type;}
	
	public TreeMap<String, ElementSimDataStructure> getSimMap(){return simMap;}
	public void setSimMap(TreeMap<String, ElementSimDataStructure> simMap){this.simMap = simMap;}
	
	public TreeMap<String, ElementSimDataStructure> getSimMapSelected(){return simMapSelected;}
	public void setSimMapSelected(TreeMap<String, ElementSimDataStructure> simMapSelected){this.simMapSelected = simMapSelected;}

	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
}