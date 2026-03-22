package org.nucastrodata.enums;

public enum FolderType {
	
	PUBLIC("Public"), 
	SHARED("Shared"), 
	USER("User"), 
	ALL("All");
	
	private String string;

	FolderType(String string){
		this.string = string;
	}

	public String toString(){return string;}
}
