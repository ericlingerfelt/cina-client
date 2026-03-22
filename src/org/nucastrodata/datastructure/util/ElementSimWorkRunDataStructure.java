package org.nucastrodata.datastructure.util;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthSimWorkflowRunStatus;

public class ElementSimWorkRunDataStructure implements DataStructure{

	private String currentZone;
	private String currentLib;
	private String currentScaleFactor;
	private String currentText;
	private String startDate, endDate;
	private String name;
	private boolean nameExists;
	private int simWorkflowIndex, index;
	private ElementSynthSimWorkflowRunStatus currentStatus;
	private String previousZone;
	private String previousLib;
	private String previousScaleFactor;
	private String previousText;
	private ElementSynthSimWorkflowRunStatus previousStatus;
	private boolean isOffline;
	private ElementSimWorkDataStructure simWorkDataStructure;
	
	public ElementSimWorkRunDataStructure(){initialize();}
	
	public void initialize(){
		setSimWorkflowIndex(-1);
		setName("");
		setCurrentZone("");
		setCurrentStatus(null);
		setCurrentLib("");
		setCurrentScaleFactor("");
		setCurrentText("");
		setIndex(-1);
		setEndDate("");
		setStartDate("");
		setPreviousZone("");
		setPreviousStatus(null);
		setPreviousLib("");
		setPreviousScaleFactor("");
		setPreviousText("");
		setIsOffline(false);
		setNameExists(false);
		setSimWorkDataStructure(null);
	}
	
	public ElementSimWorkDataStructure getSimWorkDataStructure(){return simWorkDataStructure;}
	public void setSimWorkDataStructure(ElementSimWorkDataStructure simWorkDataStructure){this.simWorkDataStructure = simWorkDataStructure;}
	
	public boolean getNameExists(){return nameExists;}
	public void setNameExists(boolean newNameExists){nameExists = newNameExists;}
	
	public boolean getIsOffline(){return isOffline;}
	public void setIsOffline(boolean newIsOffline){isOffline = newIsOffline;}
	
	public String getEndDate(){return endDate;}
	public void setEndDate(String newEndDate){endDate = newEndDate;}
	
	public String getStartDate(){return startDate;}
	public void setStartDate(String newStartDate){startDate = newStartDate;}
	
	public int getIndex(){return index;}
	public void setIndex(int index){this.index = index;}

	public String getCurrentZone(){return currentZone;}
	public void setCurrentZone(String newCurrentZone){currentZone = newCurrentZone;}
	
	public ElementSynthSimWorkflowRunStatus getCurrentStatus(){return currentStatus;}
	public void setCurrentStatus(ElementSynthSimWorkflowRunStatus newCurrentStatus){currentStatus = newCurrentStatus;}
	
	public String getCurrentLib(){return currentLib;}
	public void setCurrentLib(String newCurrentLib){currentLib = newCurrentLib;}
	
	public String getCurrentScaleFactor(){return currentScaleFactor;}
	public void setCurrentScaleFactor(String newCurrentScaleFactor){currentScaleFactor = newCurrentScaleFactor;}
	
	public String getCurrentText(){return currentText;}
	public void setCurrentText(String newCurrentText){currentText = newCurrentText;}
	
	public int getSimWorkflowIndex(){return simWorkflowIndex;}
	public void setSimWorkflowIndex(int newSimWorkflowIndex){simWorkflowIndex = newSimWorkflowIndex;}
	
	public String getName(){return name;}
	public void setName(String newName){name = newName;}
	
	public String getPreviousZone(){return previousZone;}
	public void setPreviousZone(String newPreviousZone){previousZone = newPreviousZone;}
	
	public ElementSynthSimWorkflowRunStatus getPreviousStatus(){return previousStatus;}
	public void setPreviousStatus(ElementSynthSimWorkflowRunStatus newPreviousStatus){previousStatus = newPreviousStatus;}
	
	public String getPreviousLib(){return previousLib;}
	public void setPreviousLib(String newPreviousLib){previousLib = newPreviousLib;}
	
	public String getPreviousScaleFactor(){return previousScaleFactor;}
	public void setPreviousScaleFactor(String newPreviousScaleFactor){previousScaleFactor = newPreviousScaleFactor;}
	
	public String getPreviousText(){return previousText;}
	public void setPreviousText(String newPreviousText){previousText = newPreviousText;}
	
	public String toString(){
		return name;
	}
	
}
