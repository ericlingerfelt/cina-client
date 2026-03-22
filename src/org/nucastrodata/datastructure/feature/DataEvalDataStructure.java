package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;


import java.util.*;
import java.io.*;


/**
 * The Class DataEvalDataStructure.
 */
public class DataEvalDataStructure implements DataStructure{

	/** The y units. */
	private String nucDataSetGroup, nucDataSetName, isotopeString, typeString
					, inputFilename, inputFile, inputFileDir, xUnits, yUnits;

	/** The extra2 nuc data data structure. */
	private NucDataDataStructure inter1NucDataDataStructure
													, inter2NucDataDataStructure
													, inter3NucDataDataStructure
													, trans1NucDataDataStructure
													, trans2NucDataDataStructure
													, extra1NucDataDataStructure
													, extra2NucDataDataStructure;

	/** The number shared nuc data set data structures. */
	private int numberPublicNucDataSetDataStructures
				, numberUserNucDataSetDataStructures
				, numberSharedNucDataSetDataStructures;
	
	/** The shared nuc data set data structure array. */
	private NucDataSetDataStructure[] publicNucDataSetDataStructureArray
														, userNucDataSetDataStructureArray
														, sharedNucDataSetDataStructureArray;
	
	/** The current nuc data data structure array. */
	private NucDataDataStructure[] currentNucDataDataStructureArray;
	
	/** The current nuc data set data structure. */
	private NucDataSetDataStructure currentNucDataSetDataStructure;
	
	/** The current library data structure. */
	private LibraryDataStructure currentLibraryDataStructure;
	
	/** The home nuc data set vector erase data. */
	private Vector nucDataIDVectorAboutData
					, homeNucDataSetVectorAboutData
					, nucDataIDVectorEraseData
					, homeNucDataSetVectorEraseData;
	
	/** The current nuc data set string1 extra. */
	private String currentNucDataString1Eval
					, currentRateString1Eval
					, currentNucDataSetString1Eval
					, currentNucDataString2Eval
					, currentRateString2Eval
					, currentNucDataSetString2Eval
					, currentNucDataString1Trans
					, currentRateString1Trans
					, currentNucDataSetString1Trans
					, currentNucDataString1Extra
					, currentRateString1Extra
					, currentNucDataSetString1Extra;
	
	/** The current nuc data exists. */
	private boolean pastedData, useSecondProduct, currentNucDataExists;

	/** The shared nuc data set report. */
	private String nucDataProperties, destNucDataSetName, sourceNucDataSet
					, destNucDataSetGroup, deleteSourceNucDataSet, deleteNucData
					, modifyNucDataReport, modifyNucDataSetReport, sharedNucDataSetReport;
					
	/** The extra ymax. */
	private double interXmin, interXmax
					, interYmin, interYmax
					, transXmin, transXmax
					, transYmin, transYmax
					, extraXmin, extraXmax
					, extraYmin, extraYmax;

	/** The number low e points max. */
	private int numberHighEPointsMax, numberLowEPointsMax;
	
	/** The slope array. */
	private double[] slopeArray;
	
	//CGI Vars/////////////////////////////////////////////////////////////////////////////////////
	/** The del_nuc_data. */
	private String filename, file, xunits, yunits, reaction, reaction_type
					, group, set, isotope, type, properties, nucData, format
					, notes, library, dest_set, src_set, dest_group, del_src_set
					, del_nuc_data;
	
	//Constructor
	/**
	 * Instantiates a new data eval data structure.
	 */
	public DataEvalDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setNucDataSetGroup("");
		setNucDataSetName("");
		setIsotopeString("");
		setTypeString("");
		setNucDataProperties("");
		setInputFilename("");
		setInputFile("");
		setInputFileDir("");
		setXUnits("keV");
		setYUnits("keV-b");
		setNucData("");
		setReaction("");
		setReaction_type("");
		
		setNumberPublicNucDataSetDataStructures(0);
		setNumberUserNucDataSetDataStructures(0);
		setNumberSharedNucDataSetDataStructures(0);
		
		setPublicNucDataSetDataStructureArray(null); 
		setUserNucDataSetDataStructureArray(null);
		setSharedNucDataSetDataStructureArray(null);
		
		setInter1NucDataDataStructure(new NucDataDataStructure());
		setInter2NucDataDataStructure(new NucDataDataStructure());
		setInter3NucDataDataStructure(new NucDataDataStructure());
		setTrans1NucDataDataStructure(new NucDataDataStructure());
		setTrans2NucDataDataStructure(new NucDataDataStructure());
		setExtra1NucDataDataStructure(new NucDataDataStructure());
		setExtra2NucDataDataStructure(new NucDataDataStructure());
		
		setCurrentNucDataDataStructureArray(null);
		setCurrentNucDataSetDataStructure(null);
		setCurrentLibraryDataStructure(null);
		
		setNucDataIDVectorAboutData(new Vector());
		setHomeNucDataSetVectorAboutData(new Vector());
		setNucDataIDVectorEraseData(new Vector());
		setHomeNucDataSetVectorEraseData(new Vector());
		
		setCurrentNucDataString1Eval("");
		setCurrentRateString1Eval("");
		setCurrentNucDataSetString1Eval("");
		setCurrentNucDataString2Eval("");
		setCurrentRateString2Eval("");
		setCurrentNucDataSetString2Eval("");
		setCurrentNucDataString1Trans("");
		setCurrentRateString1Trans("");
		setCurrentNucDataSetString1Trans("");
		setCurrentNucDataString1Extra("");
		setCurrentRateString1Extra("");
		setCurrentNucDataSetString1Extra("");
		
		setPastedData(false);
		setUseSecondProduct(false);
		
		setInterXmin(0.0);
		setInterXmax(0.0);
		setInterYmin(0.0);
		setInterYmax(0.0);
		setTransXmin(0.0);
		setTransXmax(0.0);
		setTransYmin(0.0);
		setTransYmax(0.0);
		setExtraXmin(0.0);
		setExtraXmax(0.0);
		setExtraYmin(0.0);
		setExtraYmax(0.0);
		
		setNumberHighEPointsMax(0);
		setNumberLowEPointsMax(0);
		setSlopeArray(null);
		setDestNucDataSetName("");
		setDestNucDataSetGroup("");
		setModifyNucDataReport("");
		setModifyNucDataSetReport("");
		setSharedNucDataSetReport("");
		setSourceNucDataSet("");
		setCurrentNucDataExists(false);
		setDeleteSourceNucDataSet("");
		setDeleteNucData("");
		setLibrary("");
		setFormat("1,0,2,0");
		setNotes("");
		
	}
	
	/**
	 * Gets the dest nuc data set group.
	 *
	 * @return the dest nuc data set group
	 */
	public String getDestNucDataSetGroup(){return destNucDataSetGroup;}
	
	/**
	 * Sets the dest nuc data set group.
	 *
	 * @param newDestNucDataSetGroup the new dest nuc data set group
	 */
	public void setDestNucDataSetGroup(String newDestNucDataSetGroup){
		
		destNucDataSetGroup = newDestNucDataSetGroup;
		setDEST_GROUP(destNucDataSetGroup);
		
	}
	
	/**
	 * Gets the delete source nuc data set.
	 *
	 * @return the delete source nuc data set
	 */
	public String getDeleteSourceNucDataSet(){return deleteSourceNucDataSet;}
	
	/**
	 * Sets the delete source nuc data set.
	 *
	 * @param newDeleteSourceNucDataSet the new delete source nuc data set
	 */
	public void setDeleteSourceNucDataSet(String newDeleteSourceNucDataSet){
		
		deleteSourceNucDataSet = newDeleteSourceNucDataSet;
		setDEL_SRC_SET(deleteSourceNucDataSet);
		
	}
	
	/**
	 * Gets the delete nuc data.
	 *
	 * @return the delete nuc data
	 */
	public String getDeleteNucData(){return deleteNucData;}
	
	/**
	 * Sets the delete nuc data.
	 *
	 * @param newDeleteNucData the new delete nuc data
	 */
	public void setDeleteNucData(String newDeleteNucData){
		
		deleteNucData = newDeleteNucData;
		setDEL_NUC_DATA(deleteNucData);
		
	}
	
	/**
	 * Gets the source nuc data set.
	 *
	 * @return the source nuc data set
	 */
	public String getSourceNucDataSet(){return sourceNucDataSet;}
	
	/**
	 * Sets the source nuc data set.
	 *
	 * @param newSourceNucDataSet the new source nuc data set
	 */
	public void setSourceNucDataSet(String newSourceNucDataSet){
		
		sourceNucDataSet = newSourceNucDataSet;
		setSRC_SET(sourceNucDataSet);
		
	}
	
	/**
	 * Gets the modify nuc data report.
	 *
	 * @return the modify nuc data report
	 */
	public String getModifyNucDataReport(){return modifyNucDataReport;}
	
	/**
	 * Sets the modify nuc data report.
	 *
	 * @param newModifyNucDataReport the new modify nuc data report
	 */
	public void setModifyNucDataReport(String newModifyNucDataReport){modifyNucDataReport = newModifyNucDataReport;}
	
	/**
	 * Gets the modify nuc data set report.
	 *
	 * @return the modify nuc data set report
	 */
	public String getModifyNucDataSetReport(){return modifyNucDataSetReport;}
	
	/**
	 * Sets the modify nuc data set report.
	 *
	 * @param newModifyNucDataSetReport the new modify nuc data set report
	 */
	public void setModifyNucDataSetReport(String newModifyNucDataSetReport){modifyNucDataSetReport = newModifyNucDataSetReport;}
	
	/**
	 * Gets the shared nuc data set report.
	 *
	 * @return the shared nuc data set report
	 */
	public String getSharedNucDataSetReport(){return sharedNucDataSetReport;}
	
	/**
	 * Sets the shared nuc data set report.
	 *
	 * @param newSharedNucDataSetReport the new shared nuc data set report
	 */
	public void setSharedNucDataSetReport(String newSharedNucDataSetReport){sharedNucDataSetReport = newSharedNucDataSetReport;}
	
	/**
	 * Gets the dest nuc data set name.
	 *
	 * @return the dest nuc data set name
	 */
	public String getDestNucDataSetName(){return destNucDataSetName;}
	
	/**
	 * Sets the dest nuc data set name.
	 *
	 * @param newDestNucDataSetName the new dest nuc data set name
	 */
	public void setDestNucDataSetName(String newDestNucDataSetName){
		
		destNucDataSetName = newDestNucDataSetName;
		setDEST_SET(destNucDataSetName);
		
	}
	
	/**
	 * Gets the nuc data set group.
	 *
	 * @return the nuc data set group
	 */
	public String getNucDataSetGroup(){return nucDataSetGroup;}
	
	/**
	 * Sets the nuc data set group.
	 *
	 * @param newNucDataSetGroup the new nuc data set group
	 */
	public void setNucDataSetGroup(String newNucDataSetGroup){
		
		nucDataSetGroup = newNucDataSetGroup;
		setGroup(nucDataSetGroup);
		
	}
	
	/**
	 * Gets the isotope string.
	 *
	 * @return the isotope string
	 */
	public String getIsotopeString(){return isotopeString;}
	
	/**
	 * Sets the isotope string.
	 *
	 * @param newIsotopeString the new isotope string
	 */
	public void setIsotopeString(String newIsotopeString){
		
		isotopeString = newIsotopeString;
		setIsotope(isotopeString);
		
	}
	
	/**
	 * Gets the type string.
	 *
	 * @return the type string
	 */
	public String getTypeString(){return typeString;}
	
	/**
	 * Sets the type string.
	 *
	 * @param newTypeString the new type string
	 */
	public void setTypeString(String newTypeString){
		
		typeString = newTypeString;
		setType(typeString);
		
	}
	
	/**
	 * Sets the nuc data properties.
	 *
	 * @param newNucDataProperties the new nuc data properties
	 */
	public void setNucDataProperties(String newNucDataProperties){
		
		nucDataProperties = newNucDataProperties;
		setProperties(nucDataProperties);
		
	}
	
	/**
	 * Gets the input filename.
	 *
	 * @return the input filename
	 */
	public String getInputFilename(){return inputFilename;}
	
	/**
	 * Sets the input filename.
	 *
	 * @param newFilename the new input filename
	 */
	public void setInputFilename(String newFilename){
		
		inputFilename = newFilename;
		setFilename(inputFilename);
	
	}
	
	/**
	 * Gets the input file.
	 *
	 * @return the input file
	 */
	public String getInputFile(){return inputFile;}
	
	/**
	 * Sets the input file.
	 *
	 * @param newFile the new input file
	 */
	public void setInputFile(String newFile){
		
		inputFile = newFile;
		setFile(inputFile);
	
	}
	
	/**
	 * Gets the input file dir.
	 *
	 * @return the input file dir
	 */
	public String getInputFileDir(){return inputFileDir;}
	
	/**
	 * Sets the input file dir.
	 *
	 * @param newFileDir the new input file dir
	 */
	public void setInputFileDir(String newFileDir){inputFileDir = newFileDir;}
	
	/**
	 * Gets the x units.
	 *
	 * @return the x units
	 */
	public String getXUnits(){return xUnits;}
	
	/**
	 * Sets the x units.
	 *
	 * @param newXUnits the new x units
	 */
	public void setXUnits(String newXUnits){
		
		xUnits = newXUnits;
		
		if(xUnits.equals("eV")){
		
			setXunits("EV");
		
		}else if(xUnits.equals("keV")){
		
			setXunits("KEV");
		
		}else if(xUnits.equals("MeV")){
		
			setXunits("MEV");
		
		}else if(xUnits.equals("GeV")){
		
			setXunits("GEV");
		
		}
			
	}
	
	/**
	 * Gets the y units.
	 *
	 * @return the y units
	 */
	public String getYUnits(){return yUnits;}
	
	/**
	 * Sets the y units.
	 *
	 * @param newYUnits the new y units
	 */
	public void setYUnits(String newYUnits){
		
		yUnits = newYUnits;
		
		if(yUnits.equals("b")){
		
			setYunits("b");
		
		}else if(yUnits.equals("mb")){
		
			setYunits("mb");
		
		}else if(yUnits.equals("ub")){
		
			setYunits("ub");
		
		}else if(yUnits.equals("nb")){
		
			setYunits("nb");
		
		}else if(yUnits.equals("pb")){
		
			setYunits("pb");
		
		}else if(yUnits.equals("eV-b")){
		
			setYunits("EV-B");
		
		}else if(yUnits.equals("keV-b")){
		
			setYunits("KEV-B");
		
		}else if(yUnits.equals("MeV-b")){
		
			setYunits("MEV-B");
		
		}else if(yUnits.equals("GeV-b")){
		
			setYunits("GEV-B");
		
		}
			
	}
	
	/**
	 * Gets the nuc data set name.
	 *
	 * @return the nuc data set name
	 */
	public String getNucDataSetName(){return nucDataSetName;}
	
	/**
	 * Sets the nuc data set name.
	 *
	 * @param newNucDataSetName the new nuc data set name
	 */
	public void setNucDataSetName(String newNucDataSetName){
		
		nucDataSetName = newNucDataSetName;
		setSet(nucDataSetName);
		
	}
	
	/**
	 * Gets the number public nuc data set data structures.
	 *
	 * @return the number public nuc data set data structures
	 */
	public int getNumberPublicNucDataSetDataStructures(){return numberPublicNucDataSetDataStructures;}
	
	/**
	 * Sets the number public nuc data set data structures.
	 *
	 * @param newNumberPublicNucDataSetDataStructures the new number public nuc data set data structures
	 */
	public void setNumberPublicNucDataSetDataStructures(int newNumberPublicNucDataSetDataStructures){numberPublicNucDataSetDataStructures = newNumberPublicNucDataSetDataStructures;}
	
	/**
	 * Gets the number user nuc data set data structures.
	 *
	 * @return the number user nuc data set data structures
	 */
	public int getNumberUserNucDataSetDataStructures(){return numberUserNucDataSetDataStructures;}
	
	/**
	 * Sets the number user nuc data set data structures.
	 *
	 * @param newNumberUserNucDataSetDataStructures the new number user nuc data set data structures
	 */
	public void setNumberUserNucDataSetDataStructures(int newNumberUserNucDataSetDataStructures){numberUserNucDataSetDataStructures = newNumberUserNucDataSetDataStructures;}
	
	/**
	 * Gets the number shared nuc data set data structures.
	 *
	 * @return the number shared nuc data set data structures
	 */
	public int getNumberSharedNucDataSetDataStructures(){return numberSharedNucDataSetDataStructures;}
	
	/**
	 * Sets the number shared nuc data set data structures.
	 *
	 * @param newNumberSharedNucDataSetDataStructures the new number shared nuc data set data structures
	 */
	public void setNumberSharedNucDataSetDataStructures(int newNumberSharedNucDataSetDataStructures){numberSharedNucDataSetDataStructures = newNumberSharedNucDataSetDataStructures;}
	
	/**
	 * Gets the public nuc data set data structure array.
	 *
	 * @return the public nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getPublicNucDataSetDataStructureArray(){return publicNucDataSetDataStructureArray;}
	
	/**
	 * Sets the public nuc data set data structure array.
	 *
	 * @param newPublicNucDataSetDataStructureArray the new public nuc data set data structure array
	 */
	public void setPublicNucDataSetDataStructureArray(NucDataSetDataStructure[] newPublicNucDataSetDataStructureArray){publicNucDataSetDataStructureArray = newPublicNucDataSetDataStructureArray;}
	
	/**
	 * Gets the user nuc data set data structure array.
	 *
	 * @return the user nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getUserNucDataSetDataStructureArray(){return userNucDataSetDataStructureArray;}
	
	/**
	 * Sets the user nuc data set data structure array.
	 *
	 * @param newUserNucDataSetDataStructureArray the new user nuc data set data structure array
	 */
	public void setUserNucDataSetDataStructureArray(NucDataSetDataStructure[] newUserNucDataSetDataStructureArray){userNucDataSetDataStructureArray = newUserNucDataSetDataStructureArray;}
	
	/**
	 * Gets the shared nuc data set data structure array.
	 *
	 * @return the shared nuc data set data structure array
	 */
	public NucDataSetDataStructure[] getSharedNucDataSetDataStructureArray(){return sharedNucDataSetDataStructureArray;}
	
	/**
	 * Sets the shared nuc data set data structure array.
	 *
	 * @param newSharedNucDataSetDataStructureArray the new shared nuc data set data structure array
	 */
	public void setSharedNucDataSetDataStructureArray(NucDataSetDataStructure[] newSharedNucDataSetDataStructureArray){sharedNucDataSetDataStructureArray = newSharedNucDataSetDataStructureArray;}
	
	/**
	 * Gets the current nuc data data structure array.
	 *
	 * @return the current nuc data data structure array
	 */
	public NucDataDataStructure[] getCurrentNucDataDataStructureArray(){return currentNucDataDataStructureArray;}
	
	/**
	 * Sets the current nuc data data structure array.
	 *
	 * @param newCurrentNucDataDataStructureArray the new current nuc data data structure array
	 */
	public void setCurrentNucDataDataStructureArray(NucDataDataStructure[] newCurrentNucDataDataStructureArray){currentNucDataDataStructureArray = newCurrentNucDataDataStructureArray;}
	
	/**
	 * Gets the inter1 nuc data data structure.
	 *
	 * @return the inter1 nuc data data structure
	 */
	public NucDataDataStructure getInter1NucDataDataStructure(){return inter1NucDataDataStructure;}
	
	/**
	 * Sets the inter1 nuc data data structure.
	 *
	 * @param newInter1NucDataDataStructure the new inter1 nuc data data structure
	 */
	public void setInter1NucDataDataStructure(NucDataDataStructure newInter1NucDataDataStructure){inter1NucDataDataStructure = newInter1NucDataDataStructure;}
	
	/**
	 * Gets the inter2 nuc data data structure.
	 *
	 * @return the inter2 nuc data data structure
	 */
	public NucDataDataStructure getInter2NucDataDataStructure(){return inter2NucDataDataStructure;}
	
	/**
	 * Sets the inter2 nuc data data structure.
	 *
	 * @param newInter2NucDataDataStructure the new inter2 nuc data data structure
	 */
	public void setInter2NucDataDataStructure(NucDataDataStructure newInter2NucDataDataStructure){inter2NucDataDataStructure = newInter2NucDataDataStructure;}
	
	/**
	 * Gets the inter3 nuc data data structure.
	 *
	 * @return the inter3 nuc data data structure
	 */
	public NucDataDataStructure getInter3NucDataDataStructure(){return inter3NucDataDataStructure;}
	
	/**
	 * Sets the inter3 nuc data data structure.
	 *
	 * @param newInter3NucDataDataStructure the new inter3 nuc data data structure
	 */
	public void setInter3NucDataDataStructure(NucDataDataStructure newInter3NucDataDataStructure){inter3NucDataDataStructure = newInter3NucDataDataStructure;}
	
	/**
	 * Gets the trans1 nuc data data structure.
	 *
	 * @return the trans1 nuc data data structure
	 */
	public NucDataDataStructure getTrans1NucDataDataStructure(){return trans1NucDataDataStructure;}
	
	/**
	 * Sets the trans1 nuc data data structure.
	 *
	 * @param newTrans1NucDataDataStructure the new trans1 nuc data data structure
	 */
	public void setTrans1NucDataDataStructure(NucDataDataStructure newTrans1NucDataDataStructure){trans1NucDataDataStructure = newTrans1NucDataDataStructure;}
	
	/**
	 * Gets the trans2 nuc data data structure.
	 *
	 * @return the trans2 nuc data data structure
	 */
	public NucDataDataStructure getTrans2NucDataDataStructure(){return trans2NucDataDataStructure;}
	
	/**
	 * Sets the trans2 nuc data data structure.
	 *
	 * @param newTrans2NucDataDataStructure the new trans2 nuc data data structure
	 */
	public void setTrans2NucDataDataStructure(NucDataDataStructure newTrans2NucDataDataStructure){trans2NucDataDataStructure = newTrans2NucDataDataStructure;}
	
	/**
	 * Gets the extra1 nuc data data structure.
	 *
	 * @return the extra1 nuc data data structure
	 */
	public NucDataDataStructure getExtra1NucDataDataStructure(){return extra1NucDataDataStructure;}
	
	/**
	 * Sets the extra1 nuc data data structure.
	 *
	 * @param newExtra1NucDataDataStructure the new extra1 nuc data data structure
	 */
	public void setExtra1NucDataDataStructure(NucDataDataStructure newExtra1NucDataDataStructure){extra1NucDataDataStructure = newExtra1NucDataDataStructure;}
	
	/**
	 * Gets the extra2 nuc data data structure.
	 *
	 * @return the extra2 nuc data data structure
	 */
	public NucDataDataStructure getExtra2NucDataDataStructure(){return extra2NucDataDataStructure;}
	
	/**
	 * Sets the extra2 nuc data data structure.
	 *
	 * @param newExtra2NucDataDataStructure the new extra2 nuc data data structure
	 */
	public void setExtra2NucDataDataStructure(NucDataDataStructure newExtra2NucDataDataStructure){extra2NucDataDataStructure = newExtra2NucDataDataStructure;}
	
	/**
	 * Gets the current nuc data set data structure.
	 *
	 * @return the current nuc data set data structure
	 */
	public NucDataSetDataStructure getCurrentNucDataSetDataStructure(){return currentNucDataSetDataStructure;}
	
	/**
	 * Sets the current nuc data set data structure.
	 *
	 * @param newCurrentNucDataSetDataStructure the new current nuc data set data structure
	 */
	public void setCurrentNucDataSetDataStructure(NucDataSetDataStructure newCurrentNucDataSetDataStructure){currentNucDataSetDataStructure = newCurrentNucDataSetDataStructure;}
	
	/**
	 * Gets the current library data structure.
	 *
	 * @return the current library data structure
	 */
	public LibraryDataStructure getCurrentLibraryDataStructure(){return currentLibraryDataStructure;}
	
	/**
	 * Sets the current library data structure.
	 *
	 * @param newCurrentLibraryDataStructure the new current library data structure
	 */
	public void setCurrentLibraryDataStructure(LibraryDataStructure newCurrentLibraryDataStructure){currentLibraryDataStructure = newCurrentLibraryDataStructure;}
	
	/**
	 * Gets the nuc data id vector about data.
	 *
	 * @return the nuc data id vector about data
	 */
	public Vector getNucDataIDVectorAboutData(){return nucDataIDVectorAboutData;}
	
	/**
	 * Sets the nuc data id vector about data.
	 *
	 * @param newNucDataIDVectorAboutData the new nuc data id vector about data
	 */
	public void setNucDataIDVectorAboutData(Vector newNucDataIDVectorAboutData){
		
		nucDataIDVectorAboutData = newNucDataIDVectorAboutData;
		
		String tempNucData = "";
		
		for(int i=0; i<nucDataIDVectorAboutData.size(); i++){
		
			if(i<nucDataIDVectorAboutData.size()-1){
		
				tempNucData = tempNucData + (String)(nucDataIDVectorAboutData.elementAt(i)) + "\n";	
			
			}else{
			
				tempNucData = tempNucData + (String)(nucDataIDVectorAboutData.elementAt(i));
			
			}
		
		}
		
		setNucData(tempNucData);
	
	}
	
	/**
	 * Gets the home nuc data set vector about data.
	 *
	 * @return the home nuc data set vector about data
	 */
	public Vector getHomeNucDataSetVectorAboutData(){return homeNucDataSetVectorAboutData;}
	
	/**
	 * Sets the home nuc data set vector about data.
	 *
	 * @param newHomeNucDataSetVectorAboutData the new home nuc data set vector about data
	 */
	public void setHomeNucDataSetVectorAboutData(Vector newHomeNucDataSetVectorAboutData){homeNucDataSetVectorAboutData = newHomeNucDataSetVectorAboutData;}
	
	/**
	 * Gets the nuc data id vector erase data.
	 *
	 * @return the nuc data id vector erase data
	 */
	public Vector getNucDataIDVectorEraseData(){return nucDataIDVectorEraseData;}
	
	/**
	 * Sets the nuc data id vector erase data.
	 *
	 * @param newNucDataIDVectorEraseData the new nuc data id vector erase data
	 */
	public void setNucDataIDVectorEraseData(Vector newNucDataIDVectorEraseData){
		
		nucDataIDVectorEraseData = newNucDataIDVectorEraseData;
		
		String tempNucData = "";
		
		for(int i=0; i<nucDataIDVectorEraseData.size(); i++){
		
			if(i<nucDataIDVectorEraseData.size()-1){
		
				tempNucData = tempNucData + (String)(nucDataIDVectorEraseData.elementAt(i)) + "\n";	
			
			}else{
			
				tempNucData = tempNucData + (String)(nucDataIDVectorEraseData.elementAt(i));
			
			}
		
		}
		
		setNucData(tempNucData);
	
	}
	
	/**
	 * Gets the home nuc data set vector erase data.
	 *
	 * @return the home nuc data set vector erase data
	 */
	public Vector getHomeNucDataSetVectorEraseData(){return homeNucDataSetVectorEraseData;}
	
	/**
	 * Sets the home nuc data set vector erase data.
	 *
	 * @param newHomeNucDataSetVectorEraseData the new home nuc data set vector erase data
	 */
	public void setHomeNucDataSetVectorEraseData(Vector newHomeNucDataSetVectorEraseData){homeNucDataSetVectorEraseData = newHomeNucDataSetVectorEraseData;}
	
	/**
	 * Gets the current nuc data string1 eval.
	 *
	 * @return the current nuc data string1 eval
	 */
	public String getCurrentNucDataString1Eval(){return currentNucDataString1Eval;}
	
	/**
	 * Sets the current nuc data string1 eval.
	 *
	 * @param newCurrentNucDataString1Eval the new current nuc data string1 eval
	 */
	public void setCurrentNucDataString1Eval(String newCurrentNucDataString1Eval){currentNucDataString1Eval = newCurrentNucDataString1Eval;}
	
	/**
	 * Gets the current rate string1 eval.
	 *
	 * @return the current rate string1 eval
	 */
	public String getCurrentRateString1Eval(){return currentRateString1Eval;}
	
	/**
	 * Sets the current rate string1 eval.
	 *
	 * @param newCurrentRateString1Eval the new current rate string1 eval
	 */
	public void setCurrentRateString1Eval(String newCurrentRateString1Eval){currentRateString1Eval = newCurrentRateString1Eval;}
	
	/**
	 * Gets the current nuc data set string1 eval.
	 *
	 * @return the current nuc data set string1 eval
	 */
	public String getCurrentNucDataSetString1Eval(){return currentNucDataSetString1Eval;}
	
	/**
	 * Sets the current nuc data set string1 eval.
	 *
	 * @param newCurrentNucDataSetString1Eval the new current nuc data set string1 eval
	 */
	public void setCurrentNucDataSetString1Eval(String newCurrentNucDataSetString1Eval){currentNucDataSetString1Eval = newCurrentNucDataSetString1Eval;}
	
	/**
	 * Gets the current nuc data string2 eval.
	 *
	 * @return the current nuc data string2 eval
	 */
	public String getCurrentNucDataString2Eval(){return currentNucDataString2Eval;}
	
	/**
	 * Sets the current nuc data string2 eval.
	 *
	 * @param newCurrentNucDataString2Eval the new current nuc data string2 eval
	 */
	public void setCurrentNucDataString2Eval(String newCurrentNucDataString2Eval){currentNucDataString2Eval = newCurrentNucDataString2Eval;}
	
	/**
	 * Gets the current rate string2 eval.
	 *
	 * @return the current rate string2 eval
	 */
	public String getCurrentRateString2Eval(){return currentRateString2Eval;}
	
	/**
	 * Sets the current rate string2 eval.
	 *
	 * @param newCurrentRateString2Eval the new current rate string2 eval
	 */
	public void setCurrentRateString2Eval(String newCurrentRateString2Eval){currentRateString2Eval = newCurrentRateString2Eval;}
	
	/**
	 * Gets the current nuc data set string2 eval.
	 *
	 * @return the current nuc data set string2 eval
	 */
	public String getCurrentNucDataSetString2Eval(){return currentNucDataSetString2Eval;}
	
	/**
	 * Sets the current nuc data set string2 eval.
	 *
	 * @param newCurrentNucDataSetString2Eval the new current nuc data set string2 eval
	 */
	public void setCurrentNucDataSetString2Eval(String newCurrentNucDataSetString2Eval){currentNucDataSetString2Eval = newCurrentNucDataSetString2Eval;}
	
	/**
	 * Gets the current nuc data string1 trans.
	 *
	 * @return the current nuc data string1 trans
	 */
	public String getCurrentNucDataString1Trans(){return currentNucDataString1Trans;}
	
	/**
	 * Sets the current nuc data string1 trans.
	 *
	 * @param newCurrentNucDataString1Trans the new current nuc data string1 trans
	 */
	public void setCurrentNucDataString1Trans(String newCurrentNucDataString1Trans){currentNucDataString1Trans = newCurrentNucDataString1Trans;}
	
	/**
	 * Gets the current rate string1 trans.
	 *
	 * @return the current rate string1 trans
	 */
	public String getCurrentRateString1Trans(){return currentRateString1Trans;}
	
	/**
	 * Sets the current rate string1 trans.
	 *
	 * @param newCurrentRateString1Trans the new current rate string1 trans
	 */
	public void setCurrentRateString1Trans(String newCurrentRateString1Trans){currentRateString1Trans = newCurrentRateString1Trans;}
	
	/**
	 * Gets the current nuc data set string1 trans.
	 *
	 * @return the current nuc data set string1 trans
	 */
	public String getCurrentNucDataSetString1Trans(){return currentNucDataSetString1Trans;}
	
	/**
	 * Sets the current nuc data set string1 trans.
	 *
	 * @param newCurrentNucDataSetString1Trans the new current nuc data set string1 trans
	 */
	public void setCurrentNucDataSetString1Trans(String newCurrentNucDataSetString1Trans){currentNucDataSetString1Trans = newCurrentNucDataSetString1Trans;}
	
	/**
	 * Gets the current nuc data string1 extra.
	 *
	 * @return the current nuc data string1 extra
	 */
	public String getCurrentNucDataString1Extra(){return currentNucDataString1Extra;}
	
	/**
	 * Sets the current nuc data string1 extra.
	 *
	 * @param newCurrentNucDataString1Extra the new current nuc data string1 extra
	 */
	public void setCurrentNucDataString1Extra(String newCurrentNucDataString1Extra){currentNucDataString1Extra = newCurrentNucDataString1Extra;}
	
	/**
	 * Gets the current rate string1 extra.
	 *
	 * @return the current rate string1 extra
	 */
	public String getCurrentRateString1Extra(){return currentRateString1Extra;}
	
	/**
	 * Sets the current rate string1 extra.
	 *
	 * @param newCurrentRateString1Extra the new current rate string1 extra
	 */
	public void setCurrentRateString1Extra(String newCurrentRateString1Extra){currentRateString1Extra = newCurrentRateString1Extra;}
	
	/**
	 * Gets the current nuc data set string1 extra.
	 *
	 * @return the current nuc data set string1 extra
	 */
	public String getCurrentNucDataSetString1Extra(){return currentNucDataSetString1Extra;}
	
	/**
	 * Sets the current nuc data set string1 extra.
	 *
	 * @param newCurrentNucDataSetString1Extra the new current nuc data set string1 extra
	 */
	public void setCurrentNucDataSetString1Extra(String newCurrentNucDataSetString1Extra){currentNucDataSetString1Extra = newCurrentNucDataSetString1Extra;}
		
	/**
	 * Gets the pasted data.
	 *
	 * @return the pasted data
	 */
	public boolean getPastedData(){return pastedData;}
	
	/**
	 * Sets the pasted data.
	 *
	 * @param newPastedData the new pasted data
	 */
	public void setPastedData(boolean newPastedData){pastedData = newPastedData;}
	
	/**
	 * Gets the use second product.
	 *
	 * @return the use second product
	 */
	public boolean getUseSecondProduct(){return useSecondProduct;}
	
	/**
	 * Sets the use second product.
	 *
	 * @param newUseSecondProduct the new use second product
	 */
	public void setUseSecondProduct(boolean newUseSecondProduct){useSecondProduct = newUseSecondProduct;}
	
	/**
	 * Gets the current nuc data exists.
	 *
	 * @return the current nuc data exists
	 */
	public boolean getCurrentNucDataExists(){return currentNucDataExists;}
	
	/**
	 * Sets the current nuc data exists.
	 *
	 * @param newCurrentNucDataExists the new current nuc data exists
	 */
	public void setCurrentNucDataExists(boolean newCurrentNucDataExists){currentNucDataExists = newCurrentNucDataExists;}
	
	/**
	 * Gets the inter xmin.
	 *
	 * @return the inter xmin
	 */
	public double getInterXmin(){return interXmin;}
	
	/**
	 * Sets the inter xmin.
	 *
	 * @param newInterXmin the new inter xmin
	 */
	public void setInterXmin(double newInterXmin){interXmin = newInterXmin;}
	
	/**
	 * Gets the inter xmax.
	 *
	 * @return the inter xmax
	 */
	public double getInterXmax(){return interXmax;}
	
	/**
	 * Sets the inter xmax.
	 *
	 * @param newInterXmax the new inter xmax
	 */
	public void setInterXmax(double newInterXmax){interXmax = newInterXmax;}
	
	/**
	 * Gets the inter ymin.
	 *
	 * @return the inter ymin
	 */
	public double getInterYmin(){return interYmin;}
	
	/**
	 * Sets the inter ymin.
	 *
	 * @param newInterYmin the new inter ymin
	 */
	public void setInterYmin(double newInterYmin){interYmin = newInterYmin;}
	
	/**
	 * Gets the inter ymax.
	 *
	 * @return the inter ymax
	 */
	public double getInterYmax(){return interYmax;}
	
	/**
	 * Sets the inter ymax.
	 *
	 * @param newInterYmax the new inter ymax
	 */
	public void setInterYmax(double newInterYmax){interYmax = newInterYmax;}
	
	/**
	 * Gets the trans xmin.
	 *
	 * @return the trans xmin
	 */
	public double getTransXmin(){return transXmin;}
	
	/**
	 * Sets the trans xmin.
	 *
	 * @param newTransXmin the new trans xmin
	 */
	public void setTransXmin(double newTransXmin){transXmin = newTransXmin;}
	
	/**
	 * Gets the trans xmax.
	 *
	 * @return the trans xmax
	 */
	public double getTransXmax(){return transXmax;}
	
	/**
	 * Sets the trans xmax.
	 *
	 * @param newTransXmax the new trans xmax
	 */
	public void setTransXmax(double newTransXmax){transXmax = newTransXmax;}
	
	/**
	 * Gets the trans ymin.
	 *
	 * @return the trans ymin
	 */
	public double getTransYmin(){return transYmin;}
	
	/**
	 * Sets the trans ymin.
	 *
	 * @param newTransYmin the new trans ymin
	 */
	public void setTransYmin(double newTransYmin){transYmin = newTransYmin;}
	
	/**
	 * Gets the trans ymax.
	 *
	 * @return the trans ymax
	 */
	public double getTransYmax(){return transYmax;}
	
	/**
	 * Sets the trans ymax.
	 *
	 * @param newTransYmax the new trans ymax
	 */
	public void setTransYmax(double newTransYmax){transYmax = newTransYmax;}
	
	/**
	 * Gets the extra xmin.
	 *
	 * @return the extra xmin
	 */
	public double getExtraXmin(){return extraXmin;}
	
	/**
	 * Sets the extra xmin.
	 *
	 * @param newExtraXmin the new extra xmin
	 */
	public void setExtraXmin(double newExtraXmin){extraXmin = newExtraXmin;}
	
	/**
	 * Gets the extra xmax.
	 *
	 * @return the extra xmax
	 */
	public double getExtraXmax(){return extraXmax;}
	
	/**
	 * Sets the extra xmax.
	 *
	 * @param newExtraXmax the new extra xmax
	 */
	public void setExtraXmax(double newExtraXmax){extraXmax = newExtraXmax;}
	
	/**
	 * Gets the extra ymin.
	 *
	 * @return the extra ymin
	 */
	public double getExtraYmin(){return extraYmin;}
	
	/**
	 * Sets the extra ymin.
	 *
	 * @param newExtraYmin the new extra ymin
	 */
	public void setExtraYmin(double newExtraYmin){extraYmin = newExtraYmin;}
	
	/**
	 * Gets the extra ymax.
	 *
	 * @return the extra ymax
	 */
	public double getExtraYmax(){return extraYmax;}
	
	/**
	 * Sets the extra ymax.
	 *
	 * @param newExtraYmax the new extra ymax
	 */
	public void setExtraYmax(double newExtraYmax){extraYmax = newExtraYmax;}
	
	/**
	 * Gets the number high e points max.
	 *
	 * @return the number high e points max
	 */
	public int getNumberHighEPointsMax(){return numberHighEPointsMax;}
	
	/**
	 * Sets the number high e points max.
	 *
	 * @param newNumberHighEPointsMax the new number high e points max
	 */
	public void setNumberHighEPointsMax(int newNumberHighEPointsMax){numberHighEPointsMax = newNumberHighEPointsMax;}	

	/**
	 * Gets the number low e points max.
	 *
	 * @return the number low e points max
	 */
	public int getNumberLowEPointsMax(){return numberLowEPointsMax;}
	
	/**
	 * Sets the number low e points max.
	 *
	 * @param newNumberLowEPointsMax the new number low e points max
	 */
	public void setNumberLowEPointsMax(int newNumberLowEPointsMax){numberLowEPointsMax = newNumberLowEPointsMax;}

	/**
	 * Gets the slope array.
	 *
	 * @return the slope array
	 */
	public double[] getSlopeArray(){return slopeArray;}
	
	/**
	 * Sets the slope array.
	 *
	 * @param newSlopeArray the new slope array
	 */
	public void setSlopeArray(double[] newSlopeArray){slopeArray = newSlopeArray;}
	
	//CGI Vars//////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup(){return group;}
	
	/**
	 * Sets the group.
	 *
	 * @param newGroup the new group
	 */
	public void setGroup(String newGroup){group = newGroup;}
	
	/**
	 * Gets the isotope.
	 *
	 * @return the isotope
	 */
	public String getIsotope(){return isotope;}
	
	/**
	 * Sets the isotope.
	 *
	 * @param newIsotope the new isotope
	 */
	public void setIsotope(String newIsotope){isotope = newIsotope;}
	
	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	public String getSet(){return set;}
	
	/**
	 * Sets the sets the.
	 *
	 * @param newSet the new sets the
	 */
	public void setSet(String newSet){set = newSet;}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType(){return type;}
	
	/**
	 * Sets the type.
	 *
	 * @param newType the new type
	 */
	public void setType(String newType){type = newType;}
	
	/**
	 * Gets the format.
	 *
	 * @return the format
	 */
	public String getFormat(){return format;}
	
	/**
	 * Sets the format.
	 *
	 * @param newFormat the new format
	 */
	public void setFormat(String newFormat){format = newFormat;}
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes(){return notes;}
	
	/**
	 * Sets the notes.
	 *
	 * @param newNotes the new notes
	 */
	public void setNotes(String newNotes){notes = newNotes;}
	
	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public String getProperties(){return properties;}
	
	/**
	 * Sets the properties.
	 *
	 * @param newProperties the new properties
	 */
	public void setProperties(String newProperties){properties = newProperties;} 
	
	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename(){return filename;} 
	
	/**
	 * Sets the filename.
	 *
	 * @param newFilename the new filename
	 */
	public void setFilename(String newFilename){filename = newFilename;}
	
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public String getFile(){return file;} 
	
	/**
	 * Sets the file.
	 *
	 * @param newFile the new file
	 */
	public void setFile(String newFile){file = newFile;}
	
	/**
	 * Gets the xunits.
	 *
	 * @return the xunits
	 */
	public String getXunits(){return xunits;} 
	
	/**
	 * Sets the xunits.
	 *
	 * @param newXunits the new xunits
	 */
	public void setXunits(String newXunits){xunits = newXunits;}
	
	/**
	 * Gets the yunits.
	 *
	 * @return the yunits
	 */
	public String getYunits(){return yunits;} 
	
	/**
	 * Sets the yunits.
	 *
	 * @param newYunits the new yunits
	 */
	public void setYunits(String newYunits){yunits = newYunits;}
	
	/**
	 * Gets the reaction.
	 *
	 * @return the reaction
	 */
	public String getReaction(){return reaction;} 
	
	/**
	 * Sets the reaction.
	 *
	 * @param newReaction the new reaction
	 */
	public void setReaction(String newReaction){reaction = newReaction;}
	
	/**
	 * Gets the reaction_type.
	 *
	 * @return the reaction_type
	 */
	public String getReaction_type(){return reaction_type;} 
	
	/**
	 * Sets the reaction_type.
	 *
	 * @param newReaction_type the new reaction_type
	 */
	public void setReaction_type(String newReaction_type){reaction_type = newReaction_type;}
	
	/**
	 * Gets the nuc data.
	 *
	 * @return the nuc data
	 */
	public String getNucData(){return nucData;}
	
	/**
	 * Sets the nuc data.
	 *
	 * @param newNucData the new nuc data
	 */
	public void setNucData(String newNucData){nucData = newNucData;} 
	
	/**
	 * Gets the dES t_ set.
	 *
	 * @return the dES t_ set
	 */
	public String getDEST_SET(){return dest_set;}
	
	/**
	 * Sets the dES t_ set.
	 *
	 * @param newDEST_SET the new dES t_ set
	 */
	public void setDEST_SET(String newDEST_SET){dest_set = newDEST_SET;}
	
	/**
	 * Gets the sR c_ set.
	 *
	 * @return the sR c_ set
	 */
	public String getSRC_SET(){return src_set;}
	
	/**
	 * Sets the sR c_ set.
	 *
	 * @param newSRC_SET the new sR c_ set
	 */
	public void setSRC_SET(String newSRC_SET){src_set = newSRC_SET;}
	
	/**
	 * Gets the dES t_ group.
	 *
	 * @return the dES t_ group
	 */
	public String getDEST_GROUP(){return dest_group;}
	
	/**
	 * Sets the dES t_ group.
	 *
	 * @param newDEST_GROUP the new dES t_ group
	 */
	public void setDEST_GROUP(String newDEST_GROUP){dest_group = newDEST_GROUP;}
	
	/**
	 * Gets the dE l_ sr c_ set.
	 *
	 * @return the dE l_ sr c_ set
	 */
	public String getDEL_SRC_SET(){return del_src_set;}
	
	/**
	 * Sets the dE l_ sr c_ set.
	 *
	 * @param newDEL_SRC_SET the new dE l_ sr c_ set
	 */
	public void setDEL_SRC_SET(String newDEL_SRC_SET){del_src_set = newDEL_SRC_SET;}
	
	/**
	 * Gets the dE l_ nu c_ data.
	 *
	 * @return the dE l_ nu c_ data
	 */
	public String getDEL_NUC_DATA(){return del_nuc_data;}
	
	/**
	 * Sets the dE l_ nu c_ data.
	 *
	 * @param newDEL_NUC_DATA the new dE l_ nu c_ data
	 */
	public void setDEL_NUC_DATA(String newDEL_NUC_DATA){del_nuc_data = newDEL_NUC_DATA;}
	
	/**
	 * Gets the library.
	 *
	 * @return the library
	 */
	public String getLibrary(){return library;}
	
	/**
	 * Sets the library.
	 *
	 * @param newLibrary the new library
	 */
	public void setLibrary(String newLibrary){library = newLibrary;}
	
}