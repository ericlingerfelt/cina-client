package org.nucastrodata.enums;

public enum SimCat {

	THERMO("Custom Thermodynamic Profiles"),
	NOVA("Nova"),
	XRAY_BURST("Xray Burst"),
	SUPERNOVA("Supernova"),
	OTHER("Other");
	
	private String string;
	
	SimCat(String string){
		this.string = string;
	}
	
	public String toString(){return string;}
	
}
