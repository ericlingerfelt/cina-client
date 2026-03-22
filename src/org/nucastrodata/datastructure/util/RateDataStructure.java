package org.nucastrodata.datastructure.util;

import java.awt.*; 
import org.nucastrodata.datastructure.DataStructure;

import java.util.*;


//This class is the data structure for one reaction rate 
/**
 * The Class RateDataStructure.
 */
public class RateDataStructure implements DataStructure{
	
	/**
	 * The Enum DecayType.
	 */
	public static enum DecayType{
		
		/** The NONE. */
		NONE("none"), 
 
 /** The BET a_ plus. */
 BETA_PLUS("bet+"), 
 
 /** The BET a_ minus. */
 BETA_MINUS("bet-"), 
 
 /** The EC. */
 EC("ec");
		
		/** The string. */
		private String string;
		
		/**
		 * Instantiates a new decay type.
		 *
		 * @param string the string
		 */
		DecayType(String string){this.string = string;}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString(){return string;}
		
		/**
		 * Gets the cGI name.
		 *
		 * @return the cGI name
		 */
		public String getCGIName(){
			if(toString().equals("none")){
				return "";
			}
			return toString();
		}
	}
	
	/**
	 * The Enum ReactionType.
	 */
	public static enum ReactionType{
		
		/** The A_ b. */
		A_B("a-->b", 1, 1)
		, 
 
 /** The A_ bc. */
 A_BC("a-->b+c", 1, 2)
		, 
 
 /** The A_ bcd. */
 A_BCD("a-->b+c+d", 1, 3)
		, 
 
 /** The A b_ c. */
 AB_C("a+b-->c", 2, 1)
		, 
 
 /** The A b_ cd. */
 AB_CD("a+b-->c+d", 2, 2)
		, 
 
 /** The A b_ cde. */
 AB_CDE("a+b-->c+d+e", 2, 3)
		, 
 
 /** The A b_ cdef. */
 AB_CDEF("a+b-->c+d+e+f", 2, 4)
		, 
 
 /** The AB c_ de. */
 ABC_DE("a+b+c-->d(+e)", 3, 2);
		
		/** The name. */
		private String name;
		
		/** The number products. */
		private int numberReactants, numberProducts;
		
		/**
		 * Instantiates a new reaction type.
		 *
		 * @param name the name
		 * @param numberReactants the number reactants
		 * @param numberProducts the number products
		 */
		ReactionType(String name, int numberReactants, int numberProducts){
			this.name = name;
			this.numberReactants = numberReactants;
			this.numberProducts =numberProducts;
			
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		public String toString(){return name;}
		
		/**
		 * Gets the number reactants.
		 *
		 * @return the number reactants
		 */
		public int getNumberReactants(){return numberReactants;}
		
		/**
		 * Gets the number products.
		 *
		 * @return the number products
		 */
		public int getNumberProducts(){return numberProducts;}
	}
	
	/** The use second product. */
	private boolean useSecondProduct;
	
	/** The isotope point. */
	private IsotopePoint isotopePoint;
	
	//Declare vars to hold unique rate id, reaction type, and the number of parameters
	/** The reaction id. */
	String reactionID;
	
	/** The reaction string int. */
	String reactionStringInt;
	
	/** The reaction type. */
	int reactionType;
	
	/** The reaction type enum. */
	ReactionType reactionTypeEnum;
	
	/** The number parameters. */
	int numberParameters;
	
	//Declare array to hold all parameters
	//[index: number of parameters]
	/** The parameters. */
	double[] parameters;
	
	//Declare array to hold calculated temperature values
	//[index: number of temperature points used in grid]
	/** The Temp data array total. */
	double[] TempDataArrayTotal;
	
	//Declare array to hold calculated rate values
	//[index: number of temperature points used in grid]
	/** The Rate data array total. */
	double[] RateDataArrayTotal;
	
	//Declare array to hold calculated temperature values
	//For each parameter set
	//[index: number of parameters sets][index: number of temperature points used in grid]
	/** The Temp data array comp. */
	double[][] TempDataArrayComp;
	
	//Declare array to hold calculated rate values
	//For each parameter set
	//[index: number of parameters sets][index: number of temperature points used in grid]
	/** The Rate data array comp. */
	double[][] RateDataArrayComp;
	
	//Declare array to hold parameters
	//For each parameter set
	//[index: number of parameters sets][index: 7]
	/** The Parameter comp array. */
	double[][] ParameterCompArray;
	
	//Declare Point arrays to hold Z,A points for reactants and products
	//[index: 3]
	/** The iso in. */
	Point[] isoIn;
	//[index: 4]
	/** The iso out. */
	Point[] isoOut;
	
	//Declare String array to hold resonance info
	//Example: "nr, r, r"
	//[index: number of parameter sets]  
	/** The resonant info. */
	String[] resonantInfo; 
	
	//Declare vars to hold the number of reactants and products,
	//the name of the reaction, whether this reaction is calculated 
	//by detailed balance (ie inverse reaction), the Q-value in MeV
	/** The number reactants. */
	int numberReactants;
	
	/** The number products. */
	int numberProducts;
	
	/** The reaction string. */
	String reactionString;
	
	/** The inverse. */
	boolean inverse;
	
	/** The Q value. */
	double QValue;
	
	//Declare String to hold decay type for internal program operation
	//Can be either "ec", "bet+", "bet-", or "<blank>"
	//These are set by the setDecayType method
	/** The decay. */
	String decay;
	
	/** The decay enum. */
	DecayType decayEnum;
	
	//Declare var to hold notes on the reaction
	/** The reaction notes. */
	String reactionNotes;

	//Max and min of temp as gotten from the valid temp range
	/** The Tmin. */
	double Tmin;
	
	/** The Tmax. */
	double Tmax;
	
	//Declare vars to hold the valid temp range, biblio code, parent library name, 
	//Chi-squared value, the rate creation date, the maximum percent difference,
	//and the reference citation
	/** The valid temp range. */
	String validTempRange;
	
	/** The biblio string. */
	String biblioString;
	
	/** The library. */
	String library;
	
	/** The chisquared. */
	double chisquared;
	
	/** The creation date. */
	String creationDate;
	
	/** The max percent diff. */
	double maxPercentDiff;
	
	/** The ref citation. */
	String refCitation;
	
	//Declare vars to hold the max and min temp and rate for this 
	//reaction rate. They are the max and min temp and rate from 
	//the temp and rate data arrays declared above 
	/** The tempmin. */
	double tempmin;
	
	/** The tempmax. */
	double tempmax;
	
	/** The ratemin. */
	double ratemin;
	
	/** The ratemax. */
	double ratemax;
	
	//Declare String to hold decay type. This is the decay Strng that 
	//is seen by the user. Can be "elecytron capture", "beta+", "beta-", or "none"
	/** The decay type. */
	String decayType = "";
	
	//Constructor
	/**
	 * Instantiates a new rate data structure.
	 */
	public RateDataStructure(){initialize();}
	
	//Initialize data structure vars
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setIsotopePoint(new IsotopePoint(0, 0));
		
		setReactionStringInt("");

		isoIn = new Point[3];
		isoOut = new Point[4];

		for(int i=0; i<3; i++){
			isoIn[i] = new Point(0, 0);
		}

		for(int i=0; i<4; i++){
			isoOut[i] = new Point(0, 0);
		}
		
		double[] parametersDoubleArray = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
											, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		
		setParameters(parametersDoubleArray);
		
		setReactionID("");
	
		setReactionType(0);
		setReactionTypeEnum(ReactionType.A_B);
		
		setNumberParameters(7);
		
		setTempDataArrayTotal(null);
		
		setRateDataArrayTotal(null);
		
		setTempDataArrayComp(null);
		
		setRateDataArrayComp(null);
		
		double[][] parameterCompDoubleArray = {{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
											, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};
		
		setParameterCompArray(parameterCompDoubleArray);
		
		String[] stringArray = {"nr"};
		
		setResonantInfo(stringArray); 
		
		setNumberReactants(0);
		
		setNumberProducts(0);
	
		setReactionString("");
		
		setInverse(false);
		
		setQValue(0.0);
		
		setDecay("");
		
		setReactionNotes("");
		
		setTmin(0.0);
		
		setTmax(0.0);
		
		setValidTempRange("0,0");
		
		setBiblioString("");
		
		setLibrary("");

		setChisquared(0);
		
		setCreationDate("");
		
		setMaxPercentDiff(0);
		
		setRefCitation("");
		
		setTempmin(0.0);
		setTempmax(0.0);
		
		setRatemin(0.0);
		setRatemax(0.0);
		
		setDecayType("");
		setDecayEnum(DecayType.NONE);
		setUseSecondProduct(false);

	}
	
	/**
	 * Gets the isotope point.
	 *
	 * @return the isotope point
	 */
	public IsotopePoint getIsotopePoint(){return isotopePoint;}
	
	/**
	 * Sets the isotope point.
	 *
	 * @param isotopePoint the new isotope point
	 */
	public void setIsotopePoint(IsotopePoint isotopePoint){this.isotopePoint = isotopePoint;}
	
	//Set and get methods for data structure vars
	/**
	 * Gets the reaction id.
	 *
	 * @return the reaction id
	 */
	public String getReactionID(){return reactionID;}
	
	/**
	 * Sets the reaction id.
	 *
	 * @param newReactionID the new reaction id
	 */
	public void setReactionID(String newReactionID){reactionID = newReactionID;}
	
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
	 * Gets the reaction type enum.
	 *
	 * @return the reaction type enum
	 */
	public ReactionType getReactionTypeEnum(){return reactionTypeEnum;}
	
	/**
	 * Sets the reaction type enum.
	 *
	 * @param newReactionTypeEnum the new reaction type enum
	 */
	public void setReactionTypeEnum(ReactionType newReactionTypeEnum){reactionTypeEnum = newReactionTypeEnum;}
	
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
	 * Gets the number parameters.
	 *
	 * @return the number parameters
	 */
	public int getNumberParameters(){return numberParameters;}
	
	/**
	 * Sets the number parameters.
	 *
	 * @param newNumberParameters the new number parameters
	 */
	public void setNumberParameters(int newNumberParameters){numberParameters = newNumberParameters;}
	
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
	 * Gets the reaction string int.
	 *
	 * @return the reaction string int
	 */
	public String getReactionStringInt(){return reactionStringInt;}
	
	/**
	 * Sets the reaction string int.
	 *
	 * @param newReactionStringInt the new reaction string int
	 */
	public void setReactionStringInt(String newReactionStringInt){reactionStringInt = newReactionStringInt;}
	
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
	 * Gets the inverse.
	 *
	 * @return the inverse
	 */
	public boolean getInverse(){return inverse;}
	
	/**
	 * Sets the inverse.
	 *
	 * @param newInverse the new inverse
	 */
	public void setInverse(boolean newInverse){inverse = newInverse;}
	
	/**
	 * Gets the q value.
	 *
	 * @return the q value
	 */
	public double getQValue(){return QValue;}
	
	/**
	 * Sets the q value.
	 *
	 * @param newQValue the new q value
	 */
	public void setQValue(double newQValue){QValue = newQValue;}
	
	/**
	 * Gets the chisquared.
	 *
	 * @return the chisquared
	 */
	public double getChisquared(){return chisquared;}
	
	/**
	 * Sets the chisquared.
	 *
	 * @param newChisquared the new chisquared
	 */
	public void setChisquared(double newChisquared){chisquared = newChisquared;}
	
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
	 * Gets the decay enum.
	 *
	 * @return the decay enum
	 */
	public DecayType getDecayEnum(){return decayEnum;}
	
	/**
	 * Sets the decay enum.
	 *
	 * @param decayEnum the new decay enum
	 */
	public void setDecayEnum(DecayType decayEnum){this.decayEnum = decayEnum;}
	
	/**
	 * Gets the reaction notes.
	 *
	 * @return the reaction notes
	 */
	public String getReactionNotes(){return reactionNotes;}
	
	/**
	 * Sets the reaction notes.
	 *
	 * @param newReactionNotes the new reaction notes
	 */
	public void setReactionNotes(String newReactionNotes){reactionNotes = newReactionNotes;}
	
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
	 * Gets the max percent diff.
	 *
	 * @return the max percent diff
	 */
	public double getMaxPercentDiff(){return maxPercentDiff;}
	
	/**
	 * Sets the max percent diff.
	 *
	 * @param newMaxPercentDiff the new max percent diff
	 */
	public void setMaxPercentDiff(double newMaxPercentDiff){maxPercentDiff = newMaxPercentDiff;}
	
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
	 * Gets the use second product.
	 *
	 * @return the use second product
	 */
	public boolean getUseSecondProduct(){return useSecondProduct;}
	
	/**
	 * Sets the use second product.
	 *
	 * @param useSecondProduct the new use second product
	 */
	public void setUseSecondProduct(boolean useSecondProduct){this.useSecondProduct = useSecondProduct;}
	
	/**
	 * Gets the valid temp range.
	 *
	 * @return the valid temp range
	 */
	public String getValidTempRange(){return validTempRange;}
	
	/**
	 * Sets the valid temp range.
	 *
	 * @param newValidTempRange the new valid temp range
	 */
	public void setValidTempRange(String newValidTempRange){
		
		validTempRange = newValidTempRange;
		
		StringTokenizer st = new StringTokenizer(validTempRange, ",");
		
		setTmin(Double.valueOf(st.nextToken()).doubleValue());
		setTmax(Double.valueOf(st.nextToken()).doubleValue());
		
	}

	/**
	 * Gets the tmin.
	 *
	 * @return the tmin
	 */
	public double getTmin(){return Tmin;}
	
	/**
	 * Sets the tmin.
	 *
	 * @param newTmin the new tmin
	 */
	public void setTmin(double newTmin){Tmin = newTmin;}
	
	/**
	 * Gets the tmax.
	 *
	 * @return the tmax
	 */
	public double getTmax(){return Tmax;}
	
	/**
	 * Sets the tmax.
	 *
	 * @param newTmax the new tmax
	 */
	public void setTmax(double newTmax){Tmax = newTmax;}
	
	/**
	 * Gets the biblio string.
	 *
	 * @return the biblio string
	 */
	public String getBiblioString(){return biblioString;}
	
	/**
	 * Sets the biblio string.
	 *
	 * @param newBiblioString the new biblio string
	 */
	public void setBiblioString(String newBiblioString){biblioString = newBiblioString;}
	
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

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public double[] getParameters(){return parameters;}
	
	/**
	 * Sets the parameters.
	 *
	 * @param newParameters the new parameters
	 */
	public void setParameters(double[] newParameters){parameters = newParameters;}
	
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
	 * Gets the temp data array total.
	 *
	 * @return the temp data array total
	 */
	public double[] getTempDataArrayTotal(){return TempDataArrayTotal;}
	
	/**
	 * Sets the temp data array total.
	 *
	 * @param newTempDataArrayTotal the new temp data array total
	 */
	public void setTempDataArrayTotal(double[] newTempDataArrayTotal){TempDataArrayTotal = newTempDataArrayTotal;}
	
	/**
	 * Gets the rate data array total.
	 *
	 * @return the rate data array total
	 */
	public double[] getRateDataArrayTotal(){return RateDataArrayTotal;}
	
	/**
	 * Sets the rate data array total.
	 *
	 * @param newRateDataArrayTotal the new rate data array total
	 */
	public void setRateDataArrayTotal(double[] newRateDataArrayTotal){RateDataArrayTotal = newRateDataArrayTotal;}
	
	/**
	 * Gets the temp data array comp.
	 *
	 * @return the temp data array comp
	 */
	public double[][] getTempDataArrayComp(){return TempDataArrayComp;}
	
	/**
	 * Sets the temp data array comp.
	 *
	 * @param newTempDataArrayComp the new temp data array comp
	 */
	public void setTempDataArrayComp(double[][] newTempDataArrayComp){TempDataArrayComp = newTempDataArrayComp;}
	
	/**
	 * Gets the rate data array comp.
	 *
	 * @return the rate data array comp
	 */
	public double[][] getRateDataArrayComp(){return RateDataArrayComp;}
	
	/**
	 * Sets the rate data array comp.
	 *
	 * @param newRateDataArrayComp the new rate data array comp
	 */
	public void setRateDataArrayComp(double[][] newRateDataArrayComp){RateDataArrayComp = newRateDataArrayComp;}

	/**
	 * Gets the parameter comp array.
	 *
	 * @return the parameter comp array
	 */
	public double[][] getParameterCompArray(){return ParameterCompArray;}
	
	/**
	 * Sets the parameter comp array.
	 *
	 * @param newParameterCompArray the new parameter comp array
	 */
	public void setParameterCompArray(double[][] newParameterCompArray){ParameterCompArray = newParameterCompArray;}

	/**
	 * Gets the resonant info.
	 *
	 * @return the resonant info
	 */
	public String[] getResonantInfo(){return resonantInfo;}
	
	/**
	 * Sets the resonant info.
	 *
	 * @param newResonantInfo the new resonant info
	 */
	public void setResonantInfo(String[] newResonantInfo){resonantInfo = newResonantInfo;}
	
	/**
	 * Gets the tempmin.
	 *
	 * @return the tempmin
	 */
	public double getTempmin(){return tempmin;} 
	
	/**
	 * Sets the tempmin.
	 *
	 * @param newTempmin the new tempmin
	 */
	public void setTempmin(double newTempmin){tempmin = newTempmin;}
	
	/**
	 * Gets the tempmax.
	 *
	 * @return the tempmax
	 */
	public double getTempmax(){return tempmax;} 
	
	/**
	 * Sets the tempmax.
	 *
	 * @param newTempmax the new tempmax
	 */
	public void setTempmax(double newTempmax){tempmax = newTempmax;}
	
	/**
	 * Gets the ratemin.
	 *
	 * @return the ratemin
	 */
	public double getRatemin(){return ratemin;} 
	
	/**
	 * Sets the ratemin.
	 *
	 * @param newRatemin the new ratemin
	 */
	public void setRatemin(double newRatemin){ratemin = newRatemin;}
	
	/**
	 * Gets the ratemax.
	 *
	 * @return the ratemax
	 */
	public double getRatemax(){return ratemax;} 
	
	/**
	 * Sets the ratemax.
	 *
	 * @param newRatemax the new ratemax
	 */
	public void setRatemax(double newRatemax){ratemax = newRatemax;}
	
	/**
	 * Parses the reaction id eval.
	 */
	public void parseReactionIDEval(){
		String id = getReactionID();
		setReactionType(Integer.valueOf(id.substring(0,2)).intValue());
		int z = Integer.valueOf(id.substring(2,5)).intValue();
		int a = Integer.valueOf(id.substring(5,8)).intValue();
		setIsotopePoint(new IsotopePoint(z, a));
		String[] array09 = id.split("\u0009");
		String[] array0b = array09[1].split("\u000b");
		setReactionString(array0b[0]);
		if(array0b.length>1){
			if(array0b[1].equals("bet+")){
				setDecayEnum(DecayType.BETA_PLUS);
			}else if(array0b[1].equals("bet-")){
				setDecayEnum(DecayType.BETA_MINUS);
			}else if(array0b[1].equals("ec")){
				setDecayEnum(DecayType.EC);
			}
		}else{
			setDecayEnum(DecayType.NONE);
		}
	}
	
	//Method to parse reaction type, source library, and reaction name from rate ID
	/**
	 * Parses the reaction id.
	 */
	public void parseReactionID(){
	
		String id = getReactionID();

		setReactionType(Integer.valueOf(id.substring(0,2)).intValue());

		StringTokenizer st = new StringTokenizer(id.substring(8), "\u0009");
		
		setLibrary(st.nextToken());
		
		String currentToken = st.nextToken();
		
		StringTokenizer st11 = new StringTokenizer(currentToken, "\u000b");
		
		int numberTokens = st11.countTokens();
		
		setReactionString(st11.nextToken());
		
		if(numberTokens==2){
		
			String tempToken = st11.nextToken();
		
			if(tempToken.equals("ec")){
			
				setDecayType("electron capture");
			
			}else if(tempToken.equals("bet-")){
			
				setDecayType("beta-");
			
			}else if(tempToken.equals("bet+")){
			
				setDecayType("beta+");
			
			}
			
		}else{
		
			setDecayType("none");
		
		}
		
	}
	
	//Method to parse reaction type, source library, and reaction name from rate ID
	/**
	 * Parses the id for decay type.
	 */
	public void parseIDForDecayType(){
	
		String id = getReactionID();

		StringTokenizer st = new StringTokenizer(id.substring(8), "\u0009");

		st.nextToken();
		
		String currentToken = st.nextToken();

		StringTokenizer st11 = new StringTokenizer(currentToken, "\u000b");
		
		int numberTokens = st11.countTokens();

		st11.nextToken();
		
		if(numberTokens==2){
		
			String tempToken = st11.nextToken();
		
			if(tempToken.equals("ec")){
			
				setDecayType("electron capture");
				setDecay("ec");
			
			}else if(tempToken.equals("bet-")){
			
				setDecayType("beta-");
				setDecay("bet-");
			
			}else if(tempToken.equals("bet+")){
			
				setDecayType("beta+");
				setDecay("bet+");
			
			}
			
		}else{
		
			setDecayType("none");
			setDecay("");
		
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(getDecayEnum()==DecayType.NONE){
			return reactionString;
		}
		return reactionString + " [" + getDecayEnum() + "]";
	}
	
}
