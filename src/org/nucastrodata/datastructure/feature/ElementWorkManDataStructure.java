package org.nucastrodata.datastructure.feature;

import java.util.*;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.enums.FolderType;

public class ElementWorkManDataStructure implements DataStructure{

	private TreeMap<String, ElementSimWorkDataStructure> simWorkMap, simWorkMapSelected;
	private String simWorkflowIndices, paths;
	private FolderType folderType;

	public ElementWorkManDataStructure(){initialize();}
	
	public void initialize(){
		setSimWorkMap(null);
		setSimWorkMapSelected(null);
		setFolderType(null);
		setSimWorkflowIndices("");
		setPaths(paths);
	}
	
	public TreeMap<String, ElementSimWorkDataStructure> getSimWorkMap(){return simWorkMap;}
	public void setSimWorkMap(TreeMap<String, ElementSimWorkDataStructure> simWorkMap){this.simWorkMap = simWorkMap;}
	
	public TreeMap<String, ElementSimWorkDataStructure> getSimWorkMapSelected(){return simWorkMapSelected;}
	public void setSimWorkMapSelected(TreeMap<String, ElementSimWorkDataStructure> simWorkMapSelected){this.simWorkMapSelected = simWorkMapSelected;}

	public FolderType getFolderType(){return folderType;}
	public void setFolderType(FolderType folderType){this.folderType = folderType;}
	
	public String getSimWorkflowIndices(){return simWorkflowIndices;}
	public void setSimWorkflowIndices(String newSimWorkflowIndices){simWorkflowIndices = newSimWorkflowIndices;}
	
	public String getPaths(){return paths;}
	public void setPaths(String paths){this.paths = paths;}
	
}