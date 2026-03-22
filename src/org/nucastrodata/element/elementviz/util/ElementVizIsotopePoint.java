package org.nucastrodata.element.elementviz.util;

import org.nucastrodata.datastructure.DataStructure;


public class ElementVizIsotopePoint implements DataStructure, Comparable<ElementVizIsotopePoint>{
	

	private int z, a;
	private String label;

	public ElementVizIsotopePoint(){}
	
	public ElementVizIsotopePoint(int z, int a, String label){
		this.z = z;
		this.a = a;
		this.label = label;
	}
	
	public void initialize(){
		this.z = 0;
		this.a = 0;
		this.label = "";
	}
	
	public int getZ(){return z;}
	public void setZ(int z){this.z = z;}
	
	public int getA(){return a;}
	public void setA(int a){this.a = a;}
	
	public String getLabel(){return label;}
	public void setLabel(String label){this.label = label;}
	
	public int compareTo(ElementVizIsotopePoint evip){
		if(this.getZ()!=evip.getZ()){
			return this.getZ()-evip.getZ();
		}
		if(this.getA()!=evip.getA()){
			return this.getA()-evip.getA();
		}
		return this.getLabel().compareTo(evip.getLabel());
	}
	
	public boolean equals(Object object){
		if(object instanceof ElementVizIsotopePoint){
			ElementVizIsotopePoint evip = (ElementVizIsotopePoint)object;
			if(evip.getZ()==this.getZ() && evip.getA()==this.getA() && evip.label.equals(this.getLabel())){
				return true;
			}
			return false;
		}
		return false;
	}

	public int hashCode(){return this.z;}
	
	public String toString(){
		return label;
	}
}
