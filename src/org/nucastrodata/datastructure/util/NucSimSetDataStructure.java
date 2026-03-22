package org.nucastrodata.datastructure.util;

import java.awt.*;
import org.nucastrodata.datastructure.DataStructure;
import java.util.*;

public class NucSimSetDataStructure implements DataStructure{

	private String path;
	private double totalWeights;
	private NucSimDataStructure[] nucSimDataStructureArray;
	private Point[] ZAMapArray;
	private String[] isotopeLabelMapArray;
	private double[][] zoneAbundArray;
	private double[] finalAbundArray;
	private int[] indexArray;
	private int[] zoneArray;
	private short[] redArray, greenArray, blueArray;
	private Vector labelViktor;
	
	public NucSimSetDataStructure(){initialize();}

	public void initialize(){
	
		setPath("");
		Point[] initZAMapArray = {new Point(0, 0)};
		setZAMapArray(initZAMapArray);
		setNucSimDataStructureArray(null);
		setIsotopeLabelMapArray(null);
		setZoneAbundArray(null);
		setFinalAbundArray(null);
		setIndexArray(null);
		setZoneArray(null);
		setRedArray(null);
		setGreenArray(null);
		setBlueArray(null);
		setTotalWeights(0.0);
		setLabelViktor(new Vector());
	}

	public double getTotalWeights(){return totalWeights;}
	public void setTotalWeights(double newTotalWeights){totalWeights = newTotalWeights;}

	public String getPath(){return path;}
	public void setPath(String newPath){path = newPath;}

	public NucSimDataStructure[] getNucSimDataStructureArray(){return nucSimDataStructureArray;}
	public void setNucSimDataStructureArray(NucSimDataStructure[] newNucSimDataStructureArray){nucSimDataStructureArray = newNucSimDataStructureArray;}

	public Point[] getZAMapArray(){return ZAMapArray;}
	public void setZAMapArray(Point[] newZAMapArray){ZAMapArray = newZAMapArray;}

	public String[] getIsotopeLabelMapArray(){return isotopeLabelMapArray;}
	public void setIsotopeLabelMapArray(String[] newIsotopeLabelMapArray){isotopeLabelMapArray = newIsotopeLabelMapArray;}

	public double[][] getZoneAbundArray(){return zoneAbundArray;}
	public void setZoneAbundArray(double[][] newZoneAbundArray){zoneAbundArray = newZoneAbundArray;}
	
	public double[] getFinalAbundArray(){return finalAbundArray;}
	public void setFinalAbundArray(double[] newFinalAbundArray){finalAbundArray = newFinalAbundArray;}
	
	public int[] getIndexArray(){return indexArray;}
	public void setIndexArray(int[] newIndexArray){indexArray = newIndexArray;}
	
	public int[] getZoneArray(){return zoneArray;}
	public void setZoneArray(int[] newZoneArray){zoneArray = newZoneArray;}
	
	public short[] getRedArray(){return redArray;}
	public void setRedArray(short[] newRedArray){redArray = newRedArray;}
	
	public short[] getGreenArray(){return greenArray;}
	public void setGreenArray(short[] newGreenArray){greenArray = newGreenArray;}
	
	public short[] getBlueArray(){return blueArray;}
	public void setBlueArray(short[] newBlueArray){blueArray = newBlueArray;}
	
	public Vector getLabelViktor(){return labelViktor;}
	public void setLabelViktor(Vector newLabelViktor){labelViktor = newLabelViktor;}

}
