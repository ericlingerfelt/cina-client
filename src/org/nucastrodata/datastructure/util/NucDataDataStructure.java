package org.nucastrodata.datastructure.util;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;


import java.util.*;


/**
 * The Class NucDataDataStructure.
 */
public class NucDataDataStructure implements DataStructure{

	/** The decay type. */
	private String nucDataID, nucDataType, nucDataName, dataFormat, reactionString
			, decay, nucDataNotes, nucDataSet, creationDate, refCitation
			, orgCode, peopleCode, decayType;
	
	/** The number points. */
	private int reactionType, numberReactants, numberProducts, numberPoints;
	
	/** The Y data array. */
	private double[] XDataArray, YDataArray;
	
	/** The iso out. */
	private Point[] isoIn, isoOut;
	
	/** The ymax. */
	private double xmin, xmax, ymin, ymax;

	//Constructor
	/**
	 * Instantiates a new nuc data data structure.
	 */
	public NucDataDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setNucDataID("");
		setNucDataType("");
		setNucDataName("");
		setDataFormat("");
		setReactionType(0);
		setXDataArray(null);
		setYDataArray(null);
		isoIn = new Point[3];
		isoOut = new Point[4];
		for(int i=0; i<3; i++){isoIn[i] = new Point(0, 0);}
		for(int i=0; i<4; i++){isoOut[i] = new Point(0, 0);}
		setNumberReactants(0);
		setNumberProducts(0);
		setReactionString("");
		setDecay("");
		setNucDataNotes("");
		setNucDataSet("");
		setXmin(0.0);
		setXmax(0.0);
		setYmin(0.0);
		setYmax(0.0);
		setNumberPoints(0);
		setDecayType("");
		setCreationDate("");
		setRefCitation("");
		setOrgCode("");
		setPeopleCode("");
	}
	
	/**
	 * Gets the nuc data id.
	 *
	 * @return the nuc data id
	 */
	public String getNucDataID(){return nucDataID;}
	
	/**
	 * Sets the nuc data id.
	 *
	 * @param newNucDataID the new nuc data id
	 */
	public void setNucDataID(String newNucDataID){nucDataID = newNucDataID;}
	
	/**
	 * Gets the nuc data type.
	 *
	 * @return the nuc data type
	 */
	public String getNucDataType(){return nucDataType;}
	
	/**
	 * Sets the nuc data type.
	 *
	 * @param newNucDataType the new nuc data type
	 */
	public void setNucDataType(String newNucDataType){nucDataType = newNucDataType;}
	
	/**
	 * Gets the nuc data name.
	 *
	 * @return the nuc data name
	 */
	public String getNucDataName(){return nucDataName;}
	
	/**
	 * Sets the nuc data name.
	 *
	 * @param newNucDataName the new nuc data name
	 */
	public void setNucDataName(String newNucDataName){nucDataName = newNucDataName;}
	
	/**
	 * Gets the data format.
	 *
	 * @return the data format
	 */
	public String getDataFormat(){return dataFormat;}
	
	/**
	 * Sets the data format.
	 *
	 * @param newDataFormat the new data format
	 */
	public void setDataFormat(String newDataFormat){dataFormat = newDataFormat;}
	
	/**
	 * Gets the reaction type.
	 *
	 * @return the reaction type
	 */
	public int getReactionType(){return reactionType;}
	
	/**
	 * Sets the reaction type.
	 *
	 * @param newReactionType the new reaction type
	 */
	public void setReactionType(int newReactionType){reactionType = newReactionType;}
	
	/**
	 * Gets the x data array.
	 *
	 * @return the x data array
	 */
	public double[] getXDataArray(){return XDataArray;}
	
	/**
	 * Sets the x data array.
	 *
	 * @param newXDataArray the new x data array
	 */
	public void setXDataArray(double[] newXDataArray){XDataArray = newXDataArray;}
	
	/**
	 * Gets the y data array.
	 *
	 * @return the y data array
	 */
	public double[] getYDataArray(){return YDataArray;}
	
	/**
	 * Sets the y data array.
	 *
	 * @param newYDataArray the new y data array
	 */
	public void setYDataArray(double[] newYDataArray){YDataArray = newYDataArray;}
	
	/**
	 * Gets the iso in.
	 *
	 * @return the iso in
	 */
	public Point[] getIsoIn(){return isoIn;}
	
	/**
	 * Sets the iso in.
	 *
	 * @param newIsoIn the new iso in
	 */
	public void setIsoIn(Point[] newIsoIn){isoIn = newIsoIn;}
	
	/**
	 * Gets the iso out.
	 *
	 * @return the iso out
	 */
	public Point[] getIsoOut(){return isoOut;}
	
	/**
	 * Sets the iso out.
	 *
	 * @param newIsoOut the new iso out
	 */
	public void setIsoOut(Point[] newIsoOut){isoOut = newIsoOut;}
	
	/**
	 * Gets the number reactants.
	 *
	 * @return the number reactants
	 */
	public int getNumberReactants(){return numberReactants;}
	
	/**
	 * Sets the number reactants.
	 *
	 * @param newNumberReactants the new number reactants
	 */
	public void setNumberReactants(int newNumberReactants){numberReactants = newNumberReactants;}
	
	/**
	 * Gets the number products.
	 *
	 * @return the number products
	 */
	public int getNumberProducts(){return numberProducts;}
	
	/**
	 * Sets the number products.
	 *
	 * @param newNumberProducts the new number products
	 */
	public void setNumberProducts(int newNumberProducts){numberProducts = newNumberProducts;}
	
	/**
	 * Gets the reaction string.
	 *
	 * @return the reaction string
	 */
	public String getReactionString(){return reactionString;}
	
	/**
	 * Sets the reaction string.
	 *
	 * @param newReactionString the new reaction string
	 */
	public void setReactionString(String newReactionString){reactionString = newReactionString;}
	
	/**
	 * Gets the decay.
	 *
	 * @return the decay
	 */
	public String getDecay(){return decay;}
	
	/**
	 * Sets the decay.
	 *
	 * @param newDecay the new decay
	 */
	public void setDecay(String newDecay){decay = newDecay;}
	
	/**
	 * Gets the nuc data notes.
	 *
	 * @return the nuc data notes
	 */
	public String getNucDataNotes(){return nucDataNotes;}
	
	/**
	 * Sets the nuc data notes.
	 *
	 * @param newNucDataNotes the new nuc data notes
	 */
	public void setNucDataNotes(String newNucDataNotes){nucDataNotes = newNucDataNotes;}
	
	/**
	 * Gets the nuc data set.
	 *
	 * @return the nuc data set
	 */
	public String getNucDataSet(){return nucDataSet;}
	
	/**
	 * Sets the nuc data set.
	 *
	 * @param newNucDataSet the new nuc data set
	 */
	public void setNucDataSet(String newNucDataSet){nucDataSet = newNucDataSet;}
	
	/**
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){return xmin;}
	
	/**
	 * Sets the xmin.
	 *
	 * @param newXmin the new xmin
	 */
	public void setXmin(double newXmin){xmin = newXmin;}
	
	/**
	 * Gets the xmax.
	 *
	 * @return the xmax
	 */
	public double getXmax(){return xmax;}
	
	/**
	 * Sets the xmax.
	 *
	 * @param newXmax the new xmax
	 */
	public void setXmax(double newXmax){xmax = newXmax;}
	
	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){return ymin;}
	
	/**
	 * Sets the ymin.
	 *
	 * @param newYmin the new ymin
	 */
	public void setYmin(double newYmin){ymin = newYmin;}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){return ymax;}
	
	/**
	 * Sets the ymax.
	 *
	 * @param newYmax the new ymax
	 */
	public void setYmax(double newYmax){ymax = newYmax;}
	
	/**
	 * Gets the number points.
	 *
	 * @return the number points
	 */
	public int getNumberPoints(){return numberPoints;}
	
	/**
	 * Sets the number points.
	 *
	 * @param newNumberPoints the new number points
	 */
	public void setNumberPoints(int newNumberPoints){numberPoints = newNumberPoints;}
	
	/**
	 * Gets the decay type.
	 *
	 * @return the decay type
	 */
	public String getDecayType(){return decayType;}
	
	/**
	 * Sets the decay type.
	 *
	 * @param newDecayType the new decay type
	 */
	public void setDecayType(String newDecayType){
		
		decayType = newDecayType;
		
		if(decayType.equals("electron capture")){
			setDecay("ec");
		}else if(decayType.equals("beta-")){
			setDecay("bet-");
		}else if(decayType.equals("beta+")){
			setDecay("bet+");
		}else if(decayType.equals("none")){
			setDecay("");
		}
		
	}
	
	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public String getCreationDate(){return creationDate;}
	
	/**
	 * Sets the creation date.
	 *
	 * @param newCreationDate the new creation date
	 */
	public void setCreationDate(String newCreationDate){creationDate = newCreationDate;}
	
	/**
	 * Gets the ref citation.
	 *
	 * @return the ref citation
	 */
	public String getRefCitation(){return refCitation;}
	
	/**
	 * Sets the ref citation.
	 *
	 * @param newRefCitation the new ref citation
	 */
	public void setRefCitation(String newRefCitation){refCitation = newRefCitation;}
	
	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode(){return orgCode;}
	
	/**
	 * Sets the org code.
	 *
	 * @param newOrgCode the new org code
	 */
	public void setOrgCode(String newOrgCode){orgCode = newOrgCode;}
	
	/**
	 * Gets the people code.
	 *
	 * @return the people code
	 */
	public String getPeopleCode(){return peopleCode;}
	
	/**
	 * Sets the people code.
	 *
	 * @param newPeopleCode the new people code
	 */
	public void setPeopleCode(String newPeopleCode){peopleCode = newPeopleCode;}
	
	/**
	 * Parses the nuc data id.
	 */
	public void parseNucDataID(){
	
		String id = getNucDataID();
		
		StringTokenizer st09 = new StringTokenizer(id, "\u0009");
		
		String firstPart09 = st09.nextToken();
		String secondPart09 = st09.nextToken();
		String thirdPart09 = st09.nextToken();
		
		setReactionType(Integer.valueOf(firstPart09.substring(0,2)).intValue());
		setNucDataSet(firstPart09.substring(8));
		setReactionString(secondPart09.substring(0, secondPart09.indexOf("\u000b")));
		setDecay(secondPart09.substring(secondPart09.indexOf("\u000b")+1));
		setNucDataType(thirdPart09.substring(0, thirdPart09.indexOf("\u000b")));
		setNucDataName(thirdPart09.substring(thirdPart09.indexOf("\u000b")+1));
		
		if(getDecay().equals("")){
			setDecayType("none");
		}else if(getDecay().equals("ec")){
			setDecayType("electron capture");
		}else if(getDecay().equals("bet+")){
			setDecayType("beta+");
		}else if(getDecay().equals("bet-")){
			setDecayType("beta-");
		}
	
	}
	
}