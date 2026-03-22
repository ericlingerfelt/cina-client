package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import javax.swing.*;

import org.nucastrodata.datastructure.DataStructure;



/**
 * The Class RegisterDataStructure.
 */
public class RegisterDataStructure implements DataStructure{
	
	/** The first name. */
	String firstName;
	
	/** The last name. */
	String lastName;
	
	/** The email string. */
	String emailString;
	
	/** The institution string. */
	String institutionString;
	
	/** The address string. */
	String addressString;
	
	/** The research string. */
	String researchString;
	
	/** The add info string. */
	String addInfoString;
	
	/** The hear of string. */
	String hearOfString;
	
	//CGI//////
	/** The firstname. */
	String firstname;
	
	/** The lastname. */
	String lastname;
	
	/** The email. */
	String email;
	
	/** The institution. */
	String institution;
	
	/** The address. */
	String address;
	
	/** The research. */
	String research;
	
	/** The info. */
	String info;
	
	/** The hear_of_suite. */
	String hear_of_suite;
	
	//Constructor
	/**
	 * Instantiates a new register data structure.
	 */
	public RegisterDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setFirstName("");
		setLastName("");
		setEmailString("");
		setInstitutionString("");
		setAddressString("");
		setResearchString("");
		setAddInfoString("");
		setHearOfString("");

	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName(){return firstName;}
	
	/**
	 * Sets the first name.
	 *
	 * @param newFirstName the new first name
	 */
	public void setFirstName(String newFirstName){
		
		firstName = newFirstName;
		setFirstname(firstName);
		
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName(){return lastName;}
	
	/**
	 * Sets the last name.
	 *
	 * @param newLastName the new last name
	 */
	public void setLastName(String newLastName){
		
		lastName = newLastName;
		setLastname(lastName);
		
	}
	
	/**
	 * Gets the email string.
	 *
	 * @return the email string
	 */
	public String getEmailString(){return emailString;}
	
	/**
	 * Sets the email string.
	 *
	 * @param newEmailString the new email string
	 */
	public void setEmailString(String newEmailString){
		
		emailString = newEmailString;
		setEmail(emailString);
		
	}
	
	/**
	 * Gets the institution string.
	 *
	 * @return the institution string
	 */
	public String getInstitutionString(){return institutionString;}
	
	/**
	 * Sets the institution string.
	 *
	 * @param newInstitutionString the new institution string
	 */
	public void setInstitutionString(String newInstitutionString){
		
		institutionString = newInstitutionString;
		setInstitution(institutionString);
		
	}
	
	/**
	 * Gets the address string.
	 *
	 * @return the address string
	 */
	public String getAddressString(){return addressString;}
	
	/**
	 * Sets the address string.
	 *
	 * @param newAddressString the new address string
	 */
	public void setAddressString(String newAddressString){
		
		addressString = newAddressString;
		setAddress(addressString);
		
	}
	
	/**
	 * Gets the research string.
	 *
	 * @return the research string
	 */
	public String getResearchString(){return researchString;}
	
	/**
	 * Sets the research string.
	 *
	 * @param newResearchString the new research string
	 */
	public void setResearchString(String newResearchString){
		
		researchString = newResearchString;
		setResearch(researchString);
		
	}
	
	/**
	 * Gets the hear of string.
	 *
	 * @return the hear of string
	 */
	public String getHearOfString(){return hearOfString;}
	
	/**
	 * Sets the hear of string.
	 *
	 * @param newHearOfString the new hear of string
	 */
	public void setHearOfString(String newHearOfString){
		
		hearOfString = newHearOfString;
		setHear_of_suite(hearOfString);
		
	}
	
	/**
	 * Gets the adds the info string.
	 *
	 * @return the adds the info string
	 */
	public String getAddInfoString(){return addInfoString;}
	
	/**
	 * Sets the adds the info string.
	 *
	 * @param newAddInfoString the new adds the info string
	 */
	public void setAddInfoString(String newAddInfoString){
		
		addInfoString = newAddInfoString;
		setInfo(addInfoString);
		
	}
	
	//CGI/////////////////
	
	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname(){return firstname;} 
	
	/**
	 * Sets the firstname.
	 *
	 * @param newFirstname the new firstname
	 */
	public void setFirstname(String newFirstname){firstname = newFirstname;}
	
	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname(){return lastname;} 
	
	/**
	 * Sets the lastname.
	 *
	 * @param newLastname the new lastname
	 */
	public void setLastname(String newLastname){lastname = newLastname;}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail(){return email;} 
	
	/**
	 * Sets the email.
	 *
	 * @param newEmail the new email
	 */
	public void setEmail(String newEmail){email = newEmail;}
	
	/**
	 * Gets the institution.
	 *
	 * @return the institution
	 */
	public String getInstitution(){return institution;} 
	
	/**
	 * Sets the institution.
	 *
	 * @param newInstitution the new institution
	 */
	public void setInstitution(String newInstitution){institution = newInstitution;}
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress(){return address;} 
	
	/**
	 * Sets the address.
	 *
	 * @param newAddress the new address
	 */
	public void setAddress(String newAddress){address = newAddress;}
	
	/**
	 * Gets the research.
	 *
	 * @return the research
	 */
	public String getResearch(){return research;} 
	
	/**
	 * Sets the research.
	 *
	 * @param newResearch the new research
	 */
	public void setResearch(String newResearch){research = newResearch;}
	
	/**
	 * Gets the hear_of_suite.
	 *
	 * @return the hear_of_suite
	 */
	public String getHear_of_suite(){return hear_of_suite;} 
	
	/**
	 * Sets the hear_of_suite.
	 *
	 * @param newHear_of_suite the new hear_of_suite
	 */
	public void setHear_of_suite(String newHear_of_suite){hear_of_suite = newHear_of_suite;}
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo(){return info;} 
	
	/**
	 * Sets the info.
	 *
	 * @param newInfo the new info
	 */
	public void setInfo(String newInfo){info = newInfo;}
}