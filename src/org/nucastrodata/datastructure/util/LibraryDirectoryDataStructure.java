package org.nucastrodata.datastructure.util;

import java.util.ArrayList;

import org.nucastrodata.datastructure.DataStructure;

public class LibraryDirectoryDataStructure implements DataStructure{

	private String name, notes, creationDate, libraryRecipe;
	
	ArrayList<String> libList = new ArrayList<String>();

	//Constructor
	/**
	 * Instantiates a new library data structure.
	 */
	public LibraryDirectoryDataStructure(){initialize();}
	
	//Method to initialize variables
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setName("");
		setNotes("");
		setCreationDate("");
		setLibraryRecipe("");
		setLibList(new ArrayList<String>());
	}

	public ArrayList<String> getLibList(){return libList;}
	public void setLibList(ArrayList<String> newLibList){libList = newLibList;}

	/**
	 * Gets the lib name.
	 *
	 * @return the lib name
	 */
	public String getName(){return name;}
	
	/**
	 * Sets the lib name.
	 *
	 * @param newLibName the new lib name
	 */
	public void setName(String newName){name = newName;}

	/**
	 * Gets the lib notes.
	 *
	 * @return the lib notes
	 */
	public String getNotes(){return notes;}
	
	/**
	 * Sets the lib notes.
	 *
	 * @param newLibNotes the new lib notes
	 */
	public void setNotes(String newNotes){notes = newNotes;}
	
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
	 * Gets the library recipe.
	 *
	 * @return the library recipe
	 */
	public String getLibraryRecipe(){return libraryRecipe;}
	
	/**
	 * Sets the library recipe.
	 *
	 * @param newLibraryRecipe the new library recipe
	 */
	public void setLibraryRecipe(String newLibraryRecipe){libraryRecipe = newLibraryRecipe;}

	
}