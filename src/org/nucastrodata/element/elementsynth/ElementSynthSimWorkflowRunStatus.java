package org.nucastrodata.element.elementsynth;

public enum ElementSynthSimWorkflowRunStatus{

	RUNNING("Running"),
	COMPLETE("Complete"),
	ERROR("Error"), 
	ABORTED("Aborted");
	
	private String string;

	ElementSynthSimWorkflowRunStatus(String string){
		this.string = string;
	}

	public String toString(){return string;}
	
}
