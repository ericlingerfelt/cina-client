package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;


import java.util.*;


/**
 * The Class DataManDataStructure.
 */
public class DataManDataStructure implements DataStructure{

	/** The nuc data set group. */
	String nucDataSetGroup;
	
	/** The nuc data set name. */
	String nucDataSetName;
	
	/** The isotope string. */
	String isotopeString;
	
	/** The type string. */
	String typeString;

	/** The input filename. */
	String inputFilename;
	
	/** The input file. */
	String inputFile;
	
	/** The input file dir. */
	String inputFileDir;

	/** The x units. */
	String xUnits;
	
	/** The y units. */
	String yUnits;

	/** The import nuc data data structure. */
	NucDataDataStructure importNucDataDataStructure;

	/** The number public nuc data set data structures. */
	int numberPublicNucDataSetDataStructures;
	
	/** The number user nuc data set data structures. */
	int numberUserNucDataSetDataStructures;
	
	/** The number shared nuc data set data structures. */
	int numberSharedNucDataSetDataStructures;
	
	/** The shared nuc data set data structure array. */
	NucDataSetDataStructure[] publicNucDataSetDataStructureArray, userNucDataSetDataStructureArray, sharedNucDataSetDataStructureArray;
	
	/** The current nuc data data structure array. */
	NucDataDataStructure[] currentNucDataDataStructureArray;
	
	/** The current nuc data set data structure. */
	NucDataSetDataStructure currentNucDataSetDataStructure;
	
	/** The nuc data id vector about data. */
	Vector nucDataIDVectorAboutData;
	
	/** The decay vector about data. */
	Vector decayVectorAboutData;
	
	/** The home nuc data set vector about data. */
	Vector homeNucDataSetVectorAboutData;
	
	/** The nuc data id vector erase data. */
	Vector nucDataIDVectorEraseData;
	
	/** The decay vector erase data. */
	Vector decayVectorEraseData;
	
	/** The home nuc data set vector erase data. */
	Vector homeNucDataSetVectorEraseData;
	
	/** The pasted data. */
	boolean pastedData;
	
	/** The use second product. */
	boolean useSecondProduct;

	/** The nuc data properties. */
	String nucDataProperties;
	
	/** The dest nuc data set name. */
	String destNucDataSetName;
	
	/** The source nuc data set. */
	String sourceNucDataSet;
	
	/** The dest nuc data set group. */
	String destNucDataSetGroup;
	
	/** The delete source nuc data set. */
	String deleteSourceNucDataSet;
	
	/** The delete nuc data. */
	String deleteNucData;
	
	/** The modify nuc data report. */
	String modifyNucDataReport;
	
	/** The modify nuc data set report. */
	String modifyNucDataSetReport;
	
	/** The shared nuc data set report. */
	String sharedNucDataSetReport;

	/** The current nuc data exists. */
	boolean currentNucDataExists;

	//CGI Vars/////////////////////////////////////////////////////////////////////////////////////
	/** The filename. */
	String filename;
	
	/** The file. */
	String file;

	/** The xunits. */
	String xunits;
	
	/** The yunits. */
	String yunits;

	/** The reaction. */
	String reaction;
	
	/** The reaction_type. */
	String reaction_type;

	/** The group. */
	String group;
	
	/** The set. */
	String set;
	
	/** The isotope. */
	String isotope;
	
	/** The type. */
	String type;
	
	/** The properties. */
	String properties;
	
	/** The nuc data. */
	String nucData;
	
	/** The format. */
	String format;
	
	/** The notes. */
	String notes;
	
	/** The dest_set. */
	String dest_set;
	
	/** The src_set. */
	String src_set;
	
	/** The dest_group. */
	String dest_group;
	
	/** The del_src_set. */
	String del_src_set;
	
	/** The del_nuc_data. */
	String del_nuc_data;
	
	//Constructor
	/**
	 * Instantiates a new data man data structure.
	 */
	public DataManDataStructure(){initialize();}
	
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
		
		setNumberPublicNucDataSetDataStructures(0);
		
		setNumberUserNucDataSetDataStructures(0);
		
		setNumberSharedNucDataSetDataStructures(0);
		
		setPublicNucDataSetDataStructureArray(null); 
		setUserNucDataSetDataStructureArray(null);
		setSharedNucDataSetDataStructureArray(null);
		
		NucDataDataStructure appndds = new NucDataDataStructure();
		
		appndds.setReactionType(4);
		
		Point[] isoIn = new Point[3];
		Point[] isoOut = new Point[4];

		for(int i=0; i<3; i++){
			isoIn[i] = new Point(0, 0);
		}

		for(int i=0; i<4; i++){
			isoOut[i] = new Point(0, 0);
		}
		
		isoIn[0] = new Point(1, 2);
		isoIn[1] = new Point(1, 2);
		isoOut[0] = new Point(2, 4);
		
		appndds.setIsoIn(isoIn);
		appndds.setIsoOut(isoOut);
		
		setImportNucDataDataStructure(appndds);
		
		setCurrentNucDataDataStructureArray(null);
		
		setCurrentNucDataSetDataStructure(null);
		
		setPastedData(false);
		
		setReaction("");
	
		setReaction_type("");
		
		setNucDataIDVectorAboutData(new Vector());
		setDecayVectorAboutData(new Vector());
		setHomeNucDataSetVectorAboutData(new Vector());
		
		setNucDataIDVectorEraseData(new Vector());
		setDecayVectorEraseData(new Vector());
		setHomeNucDataSetVectorEraseData(new Vector());
		
		setUseSecondProduct(false);
		
		setDestNucDataSetName("");
		setDestNucDataSetGroup("");
		setModifyNucDataReport("");
		setModifyNucDataSetReport("");
		setSharedNucDataSetReport("");
		setSourceNucDataSet("");
		setCurrentNucDataExists(false);
		setDeleteSourceNucDataSet("");
		setDeleteNucData("");
		
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
	 * Gets the import nuc data data structure.
	 *
	 * @return the import nuc data data structure
	 */
	public NucDataDataStructure getImportNucDataDataStructure(){return importNucDataDataStructure;}
	
	/**
	 * Sets the import nuc data data structure.
	 *
	 * @param newImportNucDataDataStructure the new import nuc data data structure
	 */
	public void setImportNucDataDataStructure(NucDataDataStructure newImportNucDataDataStructure){importNucDataDataStructure = newImportNucDataDataStructure;}
	
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
	 * Gets the decay vector about data.
	 *
	 * @return the decay vector about data
	 */
	public Vector getDecayVectorAboutData(){return decayVectorAboutData;}
	
	/**
	 * Sets the decay vector about data.
	 *
	 * @param newDecayVectorAboutData the new decay vector about data
	 */
	public void setDecayVectorAboutData(Vector newDecayVectorAboutData){decayVectorAboutData = newDecayVectorAboutData;}
	
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
	 * Gets the decay vector erase data.
	 *
	 * @return the decay vector erase data
	 */
	public Vector getDecayVectorEraseData(){return decayVectorEraseData;}
	
	/**
	 * Sets the decay vector erase data.
	 *
	 * @param newDecayVectorEraseData the new decay vector erase data
	 */
	public void setDecayVectorEraseData(Vector newDecayVectorEraseData){decayVectorEraseData = newDecayVectorEraseData;}
	
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
	
}