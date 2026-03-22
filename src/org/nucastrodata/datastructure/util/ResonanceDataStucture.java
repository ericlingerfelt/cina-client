package org.nucastrodata.datastructure.util;

import java.util.ArrayList;

import org.nucastrodata.datastructure.DataStructure;

public class ResonanceDataStucture implements DataStructure{

	private boolean includeResonance;
	private boolean isBroad;
	private ArrayList<Double> valueList;
	
	public ResonanceDataStucture(){initialize();}
	
	public void initialize() {
		
		includeResonance = false;
		isBroad = false;
		valueList = new ArrayList<Double>();
		
	}

	public boolean getIncludeResonance(){return includeResonance;} 
	public void setIncludeResonance(boolean includeResonance){this.includeResonance = includeResonance;}
	
	public boolean getIsBroad(){return isBroad;} 
	public void setIsBroad(boolean isBroad){this.isBroad = isBroad;}
	
	public ArrayList<Double> getValueList(){return valueList;} 
	public void setValueList(ArrayList<Double> valueList){this.valueList = valueList;}
}
