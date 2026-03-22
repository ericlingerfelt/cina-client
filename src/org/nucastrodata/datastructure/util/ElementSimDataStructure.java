package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.enums.FolderType;
import java.util.*;
import org.nucastrodata.enums.*;

public class ElementSimDataStructure implements DataStructure{

	private String name, path; 
	private TreeMap<SimProperty, String> propMap;
	private FolderType folderType;
	
	public ElementSimDataStructure(){initialize();}
	
	public void initialize(){
		setPath("");
		setName("");
		setFolderType(null);
		setPropMap(new TreeMap<SimProperty, String>());
	}
	
	public String getPath(){return path;}
	public void setPath(String path){this.path = path;}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public TreeMap<SimProperty, String> getPropMap(){return propMap;}
	public void setPropMap(TreeMap<SimProperty, String> propMap){this.propMap = propMap;}

	public String toString(){
		return name;
	}
	
}
