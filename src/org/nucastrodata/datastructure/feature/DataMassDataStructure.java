package org.nucastrodata.datastructure.feature;

import java.awt.*; 
import java.util.*;
import java.io.*;

import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.feature.RowData;
import org.nucastrodata.datastructure.util.MassModelDataStructure;



/**
 * The Class DataMassDataStructure.
 */
public class DataMassDataStructure implements DataStructure{

	/** The select masses flag. */
	int selectMassesFlag;
	
	/** The masses submitted. */
	boolean massesSubmitted;
	
	/** The exp model data structure. */
	MassModelDataStructure expModelDataStructure;
	
	/** The theory model data structure. */
	MassModelDataStructure theoryModelDataStructure;
	
	/** The selector model data structure. */
	MassModelDataStructure selectorModelDataStructure;

	/** The Z array. */
	double[] ZArray;
	
	/** The N array. */
	double[] NArray;
	
	/** The A array. */
	double[] AArray;
	
	/** The point vector. */
	Vector pointVector;
	
	/** The diff array. */
	double[] diffArray;
	
	/** The abs diff array. */
	double[] absDiffArray;
	
	/** The Z array rms. */
	double[] ZArrayRMS;
	
	/** The N array rms. */
	double[] NArrayRMS;
	
	/** The A array rms. */
	double[] AArrayRMS;
	
	/** The RMSZ array. */
	double[] RMSZArray;
	
	/** The RMSN array. */
	double[] RMSNArray;
	
	/** The RMSA array. */
	double[] RMSAArray;
	
	/** The Zmin. */
	int Zmin;
	
	/** The Zmax. */
	int Zmax;
	
	/** The Nmin. */
	int Nmin;
	
	/** The Nmax. */
	int Nmax;
	
	/** The Amin. */
	int Amin;
	
	/** The Amax. */
	int Amax;
	
	/** The Zmin rms. */
	int ZminRMS;
	
	/** The Zmax rms. */
	int ZmaxRMS;
	
	/** The Nmin rms. */
	int NminRMS;
	
	/** The Nmax rms. */
	int NmaxRMS;
	
	/** The Amin rms. */
	int AminRMS;
	
	/** The Amax rms. */
	int AmaxRMS;
	
	/** The diffmin. */
	double diffmin;
	
	/** The diffmax. */
	double diffmax;
	
	/** The abs diffmin. */
	double absDiffmin;
	
	/** The abs diffmax. */
	double absDiffmax;
	
	/** The RMS zmin. */
	double RMSZmin;
	
	/** The RMS zmax. */
	double RMSZmax;
	
	/** The RMS nmin. */
	double RMSNmin;
	
	/** The RMS nmax. */
	double RMSNmax;
	
	/** The RMS amin. */
	double RMSAmin;
	
	/** The RMS amax. */
	double RMSAmax;
	
	/** The chart color array. */
	Color[] chartColorArray;
	
	/** The theory uploaded. */
	boolean theoryUploaded;
	
	/** The exp uploaded. */
	boolean expUploaded;
	
	/** The theory notes. */
	String theoryNotes;
	
	/** The exp notes. */
	String expNotes;
	
	/** The exp file. */
	File expFile;
	
	/** The theory file. */
	File theoryFile;
	
	/** The theory model list. */
	String[] theoryModelList;
	
	/** The theory model. */
	String theoryModel;
	
	/** The exp model list. */
	String[] expModelList;
	
	/** The exp model. */
	String expModel;
	
	/** The isotope viktor. */
	Vector isotopeViktor;
	
	/** The stable array. */
	Vector stableArray;
	
	/** The r process array. */
	Vector rProcessArray;
	
	/** The AMDC string. */
	String AMDCString = "The AME2003 atomic mass evaluation (II). Tables, graphs, and references. G. Audi, A.H. Wapstra, and C. Thibault. Nuclear Physics A729, 337 (2003).";
    
    /** The W b03 string. */
    String WB03String = "Weizsaecker-Bethe mass formula"
								+ "\nwith newly adjusted parameter values to exp. masses of 2003 (WB03)"
								+ "\n"
								+ "\nA traditional nuclear mass formula based on consideration of a spherical liquid drop of a nucleus."
								+ "\nThere is no consideration on shell correction and deformation energy."
								+ "\nThe functional form is as follows:"
								+ "\nM(Z,N)=Z M_H+N M_n - a_V A + a_S A^(2/3) + a_I (N-Z)2/A + a_C Z2/A^(1/3)+d_eo"
								+ "\nwith"
								+ "\nd_eo="
								+ "\n-a_eo/A^(1/2) for even-even nuclei,"
								+ "\n            0 for odd-A nuclei,"
								+ "\n+a_eo/A^(1/2) for odd-odd nuclei."
								+ "\n(M_H: hydrogen mass excess, M_n neutron mass excess)"
								+ "\n"
								+ "\nParameter values are adjusted to reproduce exp. masses of 2003, and are:"
								+ "\na_V=-15.66267, a_S=17.68343, a_I=23.04812, a_C=0.71076, a_eo=12.72548."
								+ "\n(in MeV)"
								+ "\nby H. Koura and T. Tachibana."
								+ "\nThe bulletin of the Physical Society of Japan (BUTSURI), 60 (2005) (in Japanese) ";
    
    /** The DUZU string. */
    String DUZUString = "J. Duflo and A.P. Zuker  Feb 8, 1995 28 parameters formula\n"
    					+ "Reference: Phys. Rev. C52, 1995, 23  July issue\n\n"
    					+ "Microscopic calculation of nuclear masses with 28"
    					+ " parameters. Fit to the measured masses from Oxygen-16 and"
    					+ " up with a root-mean-square deviation (rms) of 375 keV.";
    
	/** The ETFS i12 string. */
	String ETFSI12String = "The Extended Thomas-Fermi + Strutinsky Integral (ETFSI) model is a "
							+ "high-speed approximation of the self-consistent Hartree-Fock method "
							+ "based on a Skyrme-type interaction SkSC18. "
							+ "\n\nSee http://www-astro.ulb.ac.be/Html/etfsi2.html and the following references:"
							+ "\n* A.K. Dutta, J.-P. Arcoragi, J.M. Pearson, R. Behrman and F. Tondeur (1986) Nucl. Phys. A458, 77; "
							+ "\n* - F. Tondeur, A.K. Dutta, J.M. Pearson and R. Behrman (1987) Nucl. Phys. A470, 93; "
							+ "\n* - J.M. Pearson, Y. Aboussir, A.K. Dutta, R.C. Nayak, M. Faine and F. Tondeur (1991) Nucl. Phys. A528, 1; "
							+ "\n* - Y. Aboussir, J.M. Pearson, A.K. Dutta and F.Tondeur (1992), Nucl. Phys. A549, 155; "
							+ "\n* - Y. Aboussir, J.M. Pearson, A.K. Dutta and F.Tondeur (1995) At. Data and Nucl. Data Tables 61, 127; "
							+ "\n* - R.C. Nayak and J.M. Pearson (1995) Phys. Rev. C52, 2254. "
							+ "\n* - S. Goriely (2000) Proc. Int. Conf. on Capture Gamma-Ray Spectroscopy and Related Topics, (AIP, ed. S. Wender) pp. 287.";

	
	/** The HF b14 string. */
	String HFB14String = "The force used in the Hartree-Fock-Bogoliubov (HFB) mass model is a "
						+ "conventional 10-parameter Skyrme force, along with a 4-parameter "
						+ "elta-function pairing force."
						+ " Pairing correlations are introduced in the framework of the Bogoliubov "
						+ "method with a pairing strength slightly stronger for an odd number of "
						+ "nucleons (V-q) than for an even number (V+q). Deformations with axial "
						+ "and left-right symmetry are admitted. See http://www-astro.ulb.ac.be/Html/hfb14.html "
						+ "for more details, and see the references"
						+ "\n* - M. Samyn, S. Goriely, P.-H. Heenen, J.M. Pearson and F. Tondeur (2001) Nucl. Phys. A700, 142; "
						+ "\n* - S. Goriely, M. Samyn, P.-H. Heenen, J.M. Pearson, and F. Tondeur (2002) Phys. Rev. C66, 024326; "
						+ "\n* - M. Samyn, S. Goriely, and J.M. Pearson (2003) Nucl. Phys. A725, 69; "
						+ "\n* - S. Goriely, M. Samyn, M. Bender and J.M. Pearson (2003) Phys. Rev. C68, 054325; "
						+ "\n* - M. Samyn, S. Goriely, M. Bender and J.M. Pearson (2004) Phys. Rev. C70, 044309; "
						+ "\n* - S. Goriely, M. Samyn, J.M. Pearson, and M. Onsi (2005) Nucl. Phys. A750, 425; "
						+ "\n* - S. Goriely, M. Samyn, and J.M. Pearson (2006) Nucl. Phys. A773, 279; "
						+ "\n* - S. Goriely, M. Samyn, and J.M. Pearson (2007) Phys. Rev. C75, 064312.";

    
	/** The KTU y05 string. */
	String KTUY05String = "KTUY05 mass formula"
									+ "\nby H. Koura, T.Tachibana, M. Uno and M. Yamada"
									+ "\nProgr. Theor. Phys., 113 (2005) pp.305"
									+ "\n"
									+ "\nThis mass formula is composed of a gross term, an even-odd term and a shell term."
									+ "\nThe gross term represents a general trend of nuclear masses as a smooth function of Z, N, and A.  The even-odd term is also a function of Z, N, and A, and is considered on the Coulomb repulsive force among protons.  The shell term is obtained by using spherical single-particle potentials and by treating the deformed nucleus as a superposition of spherical nuclei, and includes liquid-drop deformation energies."
									+ "\nApplicable region: Z, N>1."
									+ "\nRMS dev. from 2149 exp. masses of 2003 (Z, N>7): 0.653 MeV."
									+ "\nRMS dev. from 1988 exp. neutron separation energies of 2003 (Z, N>7): 0.316 MeV.";

	/** The FRD m95 string. */
	String FRDM95String = "FRDM mass formula"
							+ "\nby P. Moeller, J.R. Nix, W.D. Myers and J. Swiatecki"
							+ "\nAt. Data Nucl. Data Tables, 59 (1995) 185"
							+ "\n"
							+ "\nThis mass formula is composed of a macroscopic term and a microscopic term."
							+ "\nThe macroscopic term is based on consideration of a droplet as a smooth function of Z, N, A and nuclear deformations.  The even-odd correction is indroduced with the Lipkin-Nogami method.  The microscopic term is obtained using folded-Yukawa single-particle potentials."
							+ "\nApplicable region: Z, N>7."
							+ "\nRMS dev. from 2149 exp. masses of 2003 (Z, N>7): 0.656 MeV."
							+ "\nRMS dev. from 1988 exp. neutron separation energies of 2003 (Z, N>7): 0.399 MeV.";
	
	/** The HF b2 string. */
	String HFB2String = "Hartree-Fock-Bogoliubov mass formula with a Skyrme force of BSk2 (HFB2)"
									+ "\nby S. Goriely, M. Samyn, P.-H. Heenen, J.M. Pearson and F. Tondeur"
									+ "\nPhys. Rev. C, 66 (2002) 024326"
									+ "\n"
									+ "\nThis mass formula is caluculated from the Hartree-Fock-Bogoliubov method with a Skyrme nuclear force.  An isoscalar effective mass M* is unconstraint to adjust to experimental masses.  The Skyrme force they obtained is referred to as BSk2."
									+ "\nApplicable region: Z, N>7."
									+ "\nRMS dev. from 2149 exp. masses of 2003 (Z, N>7): 0.659 MeV."
									+ "\nRMS dev. from 1988 exp. neutron separation energies of 2003 (Z, N>7): 0.470 MeV.";
									
	/** The HF b8 string. */
	String HFB8String = "Hartree-Fock-Bogoliubov mass formula with a Skyrme force of BSk8 (HFB8)"
									+ "\nby M. Samyn, S. Goriely, M. Bender and  J.M. Pearson"
									+ "\nPhys. Rev. C, 70 (2004) 044309"
									+ "\n"
									+ "\nThis mass formula is a revised version of their previous HFB calculation.  Unlike the HFB2, an isoscalar effective mass M* is constrained to 0.8M, and the wave function in this method is projected on the exact particle number.  The Skyrme force they obtained is referred to as BSk8."
									+ "\nApplicable region: Z>7 N>7."
									+ "\nRMS dev. from 2149 exp. masses of 2003 (Z, N>7): 0.640 MeV."
									+ "\nRMS dev. from 1988 exp. neutron separation energies of 2003 (Z, N>7): 0.564 MeV.";
									
	/** The M s96 string. */
	String MS96String = "Thomas-Fermi mass formula (MS96)"
							+ "\nby W.D. Myers and J. Swiatecki"
							+ "\nNucl. Phys. A, 601 (1996) 141"
							+ "\n"
							+ "\nThis mass formula is composed of macroscopic term and a microscopic term."
							+ "\nThe macroscopic term is obtained with the Thomas-Fermi calculation.  The microscopic term is exact the same of the microscopic term in the FRDM formula for Z>7, N>7."
							+ "\nApplicable region: Z, N>1."
							+ "\nRMS dev. from 2149 exp. masses of 2003 (Z, N>7): 0.637 MeV."
							+ "\nRMS dev. from 1988 exp. neutron separation energies of 2003 (Z, N>7): 0.353 MeV.";
	
	/** The data info array. */
	String[] dataInfoArray = {AMDCString
								, WB03String
								, DUZUString
								, ETFSI12String
								, HFB14String
								, KTUY05String
								, FRDM95String
								, HFB2String
								, HFB8String
								, MS96String};
	
	/** The bin data. */
	Vector binData;
	
	/** The scheme. */
	String scheme;
	
	/** The color values. */
	double[] colorValues;
	
	/** The include values. */
	boolean includeValues;
	
	//Constructor
	/**
	 * Instantiates a new data mass data structure.
	 */
	public DataMassDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.datastructure.DataStructure#initialize()
	 */
	public void initialize(){

		setSelectMassesFlag(0);
		setMassesSubmitted(false);
		
		String[] tempArray = {"AMDC (standard)"
								, "WB03 (alt. standard)"
								, "DUZU"
								, "ETFSI12"
								, "HFB14"
								, "KTUY05"
								, "FRDM95"
								, "HFB2"
								, "HFB8"
								, "MS96"};
		
		setTheoryModelList(tempArray);
		setTheoryModel("KTUY05");
		
		setExpModelList(tempArray);
		setExpModel("AMDC");

		setStableArray(new Vector());
		setRProcessArray(new Vector());
		
		setExpModelDataStructure(new MassModelDataStructure());
		setTheoryModelDataStructure(new MassModelDataStructure());
		setSelectorModelDataStructure(new MassModelDataStructure());
		
		setIsotopeViktor(new Vector());
		
		setZArray(null);
		setNArray(null);
		setAArray(null);
		setPointVector(new Vector());
		
		setDiffArray(null);
		setAbsDiffArray(null);
		
		setZArrayRMS(null);
		setNArrayRMS(null);
		setAArrayRMS(null);
		
		setRMSZArray(null);
		setRMSNArray(null);
		setRMSAArray(null);
		
		setZmin(0);
		setZmax(0);
		setNmin(0);
		setNmax(0);
		setAmin(0);
		setAmax(0);
		
		setDiffmin(0.0);
		setDiffmax(0.0);
		
		setAbsDiffmin(0.0);
		setAbsDiffmax(0.0);
		
		setZminRMS(0);
		setZmaxRMS(0);
		setNminRMS(0);
		setNmaxRMS(0);
		setAminRMS(0);
		setAmaxRMS(0);
		setRMSZmin(0.0);
		setRMSZmax(0.0);
		setRMSNmin(0.0);
		setRMSNmax(0.0);
		setRMSAmin(0.0);
		setRMSAmax(0.0);
		
		setChartColorArray(null);
		
		setTheoryUploaded(false);
		setExpUploaded(false);
		
		setTheoryNotes("");
		setExpNotes("");
		
		setExpFile(null);
		setTheoryFile(null);
		
		double[] initColorValuesArray = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
		setColorValues(initColorValuesArray);
		
		Vector columnData = new Vector();
		columnData.addElement(new RowData(1, 2, true, new Color (255,0,0)));
		columnData.addElement(new RowData(0, 1, true, new Color(175,0,0)));
		columnData.addElement(new RowData(-1, 0, true, new Color(0,0,175)));
		columnData.addElement(new RowData(-2, -1, true, new Color(0,0,255)));
		
		setBinData(columnData);
		setScheme("Continuous");
		
		setIncludeValues(true);
		
	}
	
	/**
	 * Gets the select masses flag.
	 *
	 * @return the select masses flag
	 */
	public int getSelectMassesFlag(){return selectMassesFlag;}
	
	/**
	 * Sets the select masses flag.
	 *
	 * @param newSelectMassesFlag the new select masses flag
	 */
	public void setSelectMassesFlag(int newSelectMassesFlag){selectMassesFlag = newSelectMassesFlag;}
	
	/**
	 * Gets the masses submitted.
	 *
	 * @return the masses submitted
	 */
	public boolean getMassesSubmitted(){return massesSubmitted;}
	
	/**
	 * Sets the masses submitted.
	 *
	 * @param newMassesSubmitted the new masses submitted
	 */
	public void setMassesSubmitted(boolean newMassesSubmitted){massesSubmitted = newMassesSubmitted;}
	
	/**
	 * Gets the theory model list.
	 *
	 * @return the theory model list
	 */
	public String[] getTheoryModelList(){return theoryModelList;}
	
	/**
	 * Sets the theory model list.
	 *
	 * @param newTheoryModelList the new theory model list
	 */
	public void setTheoryModelList(String[] newTheoryModelList){theoryModelList = newTheoryModelList;}

	/**
	 * Gets the theory model.
	 *
	 * @return the theory model
	 */
	public String getTheoryModel(){return theoryModel;}
	
	/**
	 * Sets the theory model.
	 *
	 * @param newTheoryModel the new theory model
	 */
	public void setTheoryModel(String newTheoryModel){theoryModel = newTheoryModel;}

	/**
	 * Gets the exp model list.
	 *
	 * @return the exp model list
	 */
	public String[] getExpModelList(){return expModelList;}
	
	/**
	 * Sets the exp model list.
	 *
	 * @param newExpModelList the new exp model list
	 */
	public void setExpModelList(String[] newExpModelList){expModelList = newExpModelList;}

	/**
	 * Gets the exp model.
	 *
	 * @return the exp model
	 */
	public String getExpModel(){return expModel;}
	
	/**
	 * Sets the exp model.
	 *
	 * @param newExpModel the new exp model
	 */
	public void setExpModel(String newExpModel){expModel = newExpModel;}

	/**
	 * Gets the stable array.
	 *
	 * @return the stable array
	 */
	public Vector getStableArray(){return stableArray;}
	
	/**
	 * Sets the stable array.
	 *
	 * @param newStableArray the new stable array
	 */
	public void setStableArray(Vector newStableArray){stableArray = newStableArray;}
	
	/**
	 * Gets the r process array.
	 *
	 * @return the r process array
	 */
	public Vector getRProcessArray(){return rProcessArray;}
	
	/**
	 * Sets the r process array.
	 *
	 * @param newRProcessArray the new r process array
	 */
	public void setRProcessArray(Vector newRProcessArray){rProcessArray = newRProcessArray;}
	
	/**
	 * Gets the exp model data structure.
	 *
	 * @return the exp model data structure
	 */
	public MassModelDataStructure getExpModelDataStructure(){return expModelDataStructure;}
	
	/**
	 * Sets the exp model data structure.
	 *
	 * @param newExpModelDataStructure the new exp model data structure
	 */
	public void setExpModelDataStructure(MassModelDataStructure newExpModelDataStructure){expModelDataStructure = newExpModelDataStructure;}

	/**
	 * Gets the theory model data structure.
	 *
	 * @return the theory model data structure
	 */
	public MassModelDataStructure getTheoryModelDataStructure(){return theoryModelDataStructure;}
	
	/**
	 * Sets the theory model data structure.
	 *
	 * @param newTheoryModelDataStructure the new theory model data structure
	 */
	public void setTheoryModelDataStructure(MassModelDataStructure newTheoryModelDataStructure){theoryModelDataStructure = newTheoryModelDataStructure;}
	
	/**
	 * Gets the selector model data structure.
	 *
	 * @return the selector model data structure
	 */
	public MassModelDataStructure getSelectorModelDataStructure(){return selectorModelDataStructure;}
	
	/**
	 * Sets the selector model data structure.
	 *
	 * @param newSelectorModelDataStructure the new selector model data structure
	 */
	public void setSelectorModelDataStructure(MassModelDataStructure newSelectorModelDataStructure){selectorModelDataStructure = newSelectorModelDataStructure;}
	
	/**
	 * Gets the z array.
	 *
	 * @return the z array
	 */
	public double[] getZArray(){return ZArray;}
	
	/**
	 * Sets the z array.
	 *
	 * @param newZArray the new z array
	 */
	public void setZArray(double[] newZArray){ZArray = newZArray;}
	
	/**
	 * Gets the n array.
	 *
	 * @return the n array
	 */
	public double[] getNArray(){return NArray;}
	
	/**
	 * Sets the n array.
	 *
	 * @param newNArray the new n array
	 */
	public void setNArray(double[] newNArray){NArray = newNArray;}
	
	/**
	 * Gets the a array.
	 *
	 * @return the a array
	 */
	public double[] getAArray(){return AArray;}
	
	/**
	 * Sets the a array.
	 *
	 * @param newAArray the new a array
	 */
	public void setAArray(double[] newAArray){AArray = newAArray;}
	
	/**
	 * Gets the point vector.
	 *
	 * @return the point vector
	 */
	public Vector getPointVector(){return pointVector;}
	
	/**
	 * Sets the point vector.
	 *
	 * @param newPointVector the new point vector
	 */
	public void setPointVector(Vector newPointVector){pointVector = newPointVector;}
	
	/**
	 * Gets the diff array.
	 *
	 * @return the diff array
	 */
	public double[] getDiffArray(){return diffArray;}
	
	/**
	 * Sets the diff array.
	 *
	 * @param newDiffArray the new diff array
	 */
	public void setDiffArray(double[] newDiffArray){diffArray = newDiffArray;}
	
	/**
	 * Gets the abs diff array.
	 *
	 * @return the abs diff array
	 */
	public double[] getAbsDiffArray(){return absDiffArray;}
	
	/**
	 * Sets the abs diff array.
	 *
	 * @param newAbsDiffArray the new abs diff array
	 */
	public void setAbsDiffArray(double[] newAbsDiffArray){absDiffArray = newAbsDiffArray;}
	
	/**
	 * Gets the z array rms.
	 *
	 * @return the z array rms
	 */
	public double[] getZArrayRMS(){return ZArrayRMS;}
	
	/**
	 * Sets the z array rms.
	 *
	 * @param newZArrayRMS the new z array rms
	 */
	public void setZArrayRMS(double[] newZArrayRMS){ZArrayRMS = newZArrayRMS;}
	
	/**
	 * Gets the n array rms.
	 *
	 * @return the n array rms
	 */
	public double[] getNArrayRMS(){return NArrayRMS;}
	
	/**
	 * Sets the n array rms.
	 *
	 * @param newNArrayRMS the new n array rms
	 */
	public void setNArrayRMS(double[] newNArrayRMS){NArrayRMS = newNArrayRMS;}
	
	/**
	 * Gets the a array rms.
	 *
	 * @return the a array rms
	 */
	public double[] getAArrayRMS(){return AArrayRMS;}
	
	/**
	 * Sets the a array rms.
	 *
	 * @param newAArrayRMS the new a array rms
	 */
	public void setAArrayRMS(double[] newAArrayRMS){AArrayRMS = newAArrayRMS;}
		
	/**
	 * Gets the rMSZ array.
	 *
	 * @return the rMSZ array
	 */
	public double[] getRMSZArray(){return RMSZArray;}
	
	/**
	 * Sets the rMSZ array.
	 *
	 * @param newRMSZArray the new rMSZ array
	 */
	public void setRMSZArray(double[] newRMSZArray){RMSZArray = newRMSZArray;}
	
	/**
	 * Gets the rMSN array.
	 *
	 * @return the rMSN array
	 */
	public double[] getRMSNArray(){return RMSNArray;}
	
	/**
	 * Sets the rMSN array.
	 *
	 * @param newRMSNArray the new rMSN array
	 */
	public void setRMSNArray(double[] newRMSNArray){RMSNArray = newRMSNArray;}
	
	/**
	 * Gets the rMSA array.
	 *
	 * @return the rMSA array
	 */
	public double[] getRMSAArray(){return RMSAArray;}
	
	/**
	 * Sets the rMSA array.
	 *
	 * @param newRMSAArray the new rMSA array
	 */
	public void setRMSAArray(double[] newRMSAArray){RMSAArray = newRMSAArray;}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	public int getZmin(){return Zmin;}
	
	/**
	 * Sets the zmin.
	 *
	 * @param newZmin the new zmin
	 */
	public void setZmin(int newZmin){Zmin = newZmin;}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){return Zmax;}
	
	/**
	 * Sets the zmax.
	 *
	 * @param newZmax the new zmax
	 */
	public void setZmax(int newZmax){Zmax = newZmax;}
	
	/**
	 * Gets the nmin.
	 *
	 * @return the nmin
	 */
	public int getNmin(){return Nmin;}
	
	/**
	 * Sets the nmin.
	 *
	 * @param newNmin the new nmin
	 */
	public void setNmin(int newNmin){Nmin = newNmin;}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){return Nmax;}
	
	/**
	 * Sets the nmax.
	 *
	 * @param newNmax the new nmax
	 */
	public void setNmax(int newNmax){Nmax = newNmax;}
	
	/**
	 * Gets the amin.
	 *
	 * @return the amin
	 */
	public int getAmin(){return Amin;}
	
	/**
	 * Sets the amin.
	 *
	 * @param newAmin the new amin
	 */
	public void setAmin(int newAmin){Amin = newAmin;}
	
	/**
	 * Gets the amax.
	 *
	 * @return the amax
	 */
	public int getAmax(){return Amax;}
	
	/**
	 * Sets the amax.
	 *
	 * @param newAmax the new amax
	 */
	public void setAmax(int newAmax){Amax = newAmax;}
	
	/**
	 * Gets the diffmin.
	 *
	 * @return the diffmin
	 */
	public double getDiffmin(){return diffmin;}
	
	/**
	 * Sets the diffmin.
	 *
	 * @param newDiffmin the new diffmin
	 */
	public void setDiffmin(double newDiffmin){diffmin = newDiffmin;}
	
	/**
	 * Gets the diffmax.
	 *
	 * @return the diffmax
	 */
	public double getDiffmax(){return diffmax;}
	
	/**
	 * Sets the diffmax.
	 *
	 * @param newDiffmax the new diffmax
	 */
	public void setDiffmax(double newDiffmax){diffmax = newDiffmax;}
	
	/**
	 * Gets the abs diffmin.
	 *
	 * @return the abs diffmin
	 */
	public double getAbsDiffmin(){return absDiffmin;}
	
	/**
	 * Sets the abs diffmin.
	 *
	 * @param newAbsDiffmin the new abs diffmin
	 */
	public void setAbsDiffmin(double newAbsDiffmin){absDiffmin = newAbsDiffmin;}
	
	/**
	 * Gets the abs diffmax.
	 *
	 * @return the abs diffmax
	 */
	public double getAbsDiffmax(){return absDiffmax;}
	
	/**
	 * Sets the abs diffmax.
	 *
	 * @param newAbsDiffmax the new abs diffmax
	 */
	public void setAbsDiffmax(double newAbsDiffmax){absDiffmax = newAbsDiffmax;}
	
	/**
	 * Gets the zmin rms.
	 *
	 * @return the zmin rms
	 */
	public int getZminRMS(){return ZminRMS;}
	
	/**
	 * Sets the zmin rms.
	 *
	 * @param newZminRMS the new zmin rms
	 */
	public void setZminRMS(int newZminRMS){ZminRMS = newZminRMS;}
	
	/**
	 * Gets the zmax rms.
	 *
	 * @return the zmax rms
	 */
	public int getZmaxRMS(){return ZmaxRMS;}
	
	/**
	 * Sets the zmax rms.
	 *
	 * @param newZmaxRMS the new zmax rms
	 */
	public void setZmaxRMS(int newZmaxRMS){ZmaxRMS = newZmaxRMS;}
	
	/**
	 * Gets the nmin rms.
	 *
	 * @return the nmin rms
	 */
	public int getNminRMS(){return NminRMS;}
	
	/**
	 * Sets the nmin rms.
	 *
	 * @param newNminRMS the new nmin rms
	 */
	public void setNminRMS(int newNminRMS){NminRMS = newNminRMS;}
	
	/**
	 * Gets the nmax rms.
	 *
	 * @return the nmax rms
	 */
	public int getNmaxRMS(){return NmaxRMS;}
	
	/**
	 * Sets the nmax rms.
	 *
	 * @param newNmaxRMS the new nmax rms
	 */
	public void setNmaxRMS(int newNmaxRMS){NmaxRMS = newNmaxRMS;}
	
	/**
	 * Gets the amin rms.
	 *
	 * @return the amin rms
	 */
	public int getAminRMS(){return AminRMS;}
	
	/**
	 * Sets the amin rms.
	 *
	 * @param newAminRMS the new amin rms
	 */
	public void setAminRMS(int newAminRMS){AminRMS = newAminRMS;}
	
	/**
	 * Gets the amax rms.
	 *
	 * @return the amax rms
	 */
	public int getAmaxRMS(){return AmaxRMS;}
	
	/**
	 * Sets the amax rms.
	 *
	 * @param newAmaxRMS the new amax rms
	 */
	public void setAmaxRMS(int newAmaxRMS){AmaxRMS = newAmaxRMS;}
	
	/**
	 * Gets the rMS zmin.
	 *
	 * @return the rMS zmin
	 */
	public double getRMSZmin(){return RMSZmin;}
	
	/**
	 * Sets the rMS zmin.
	 *
	 * @param newRMSZmin the new rMS zmin
	 */
	public void setRMSZmin(double newRMSZmin){RMSZmin = newRMSZmin;}
	
	/**
	 * Gets the rMS zmax.
	 *
	 * @return the rMS zmax
	 */
	public double getRMSZmax(){return RMSZmax;}
	
	/**
	 * Sets the rMS zmax.
	 *
	 * @param newRMSZmax the new rMS zmax
	 */
	public void setRMSZmax(double newRMSZmax){RMSZmax = newRMSZmax;}
	
	/**
	 * Gets the rMS nmin.
	 *
	 * @return the rMS nmin
	 */
	public double getRMSNmin(){return RMSNmin;}
	
	/**
	 * Sets the rMS nmin.
	 *
	 * @param newRMSNmin the new rMS nmin
	 */
	public void setRMSNmin(double newRMSNmin){RMSNmin = newRMSNmin;}
	
	/**
	 * Gets the rMS nmax.
	 *
	 * @return the rMS nmax
	 */
	public double getRMSNmax(){return RMSNmax;}
	
	/**
	 * Sets the rMS nmax.
	 *
	 * @param newRMSNmax the new rMS nmax
	 */
	public void setRMSNmax(double newRMSNmax){RMSNmax = newRMSNmax;}
	
	/**
	 * Gets the rMS amin.
	 *
	 * @return the rMS amin
	 */
	public double getRMSAmin(){return RMSAmin;}
	
	/**
	 * Sets the rMS amin.
	 *
	 * @param newRMSAmin the new rMS amin
	 */
	public void setRMSAmin(double newRMSAmin){RMSAmin = newRMSAmin;}
	
	/**
	 * Gets the rMS amax.
	 *
	 * @return the rMS amax
	 */
	public double getRMSAmax(){return RMSAmax;}
	
	/**
	 * Sets the rMS amax.
	 *
	 * @param newRMSAmax the new rMS amax
	 */
	public void setRMSAmax(double newRMSAmax){RMSAmax = newRMSAmax;}
	
	/**
	 * Gets the chart color array.
	 *
	 * @return the chart color array
	 */
	public Color[] getChartColorArray(){return chartColorArray;}
	
	/**
	 * Sets the chart color array.
	 *
	 * @param newChartColorArray the new chart color array
	 */
	public void setChartColorArray(Color[] newChartColorArray){chartColorArray = newChartColorArray;}
	
	/**
	 * Gets the theory uploaded.
	 *
	 * @return the theory uploaded
	 */
	public boolean getTheoryUploaded(){return theoryUploaded;}
	
	/**
	 * Sets the theory uploaded.
	 *
	 * @param newTheoryUploaded the new theory uploaded
	 */
	public void setTheoryUploaded(boolean newTheoryUploaded){theoryUploaded = newTheoryUploaded;}
	
	/**
	 * Gets the exp uploaded.
	 *
	 * @return the exp uploaded
	 */
	public boolean getExpUploaded(){return expUploaded;}
	
	/**
	 * Sets the exp uploaded.
	 *
	 * @param newExpUploaded the new exp uploaded
	 */
	public void setExpUploaded(boolean newExpUploaded){expUploaded = newExpUploaded;}
	
	/**
	 * Gets the theory notes.
	 *
	 * @return the theory notes
	 */
	public String getTheoryNotes(){return theoryNotes;}
	
	/**
	 * Sets the theory notes.
	 *
	 * @param newTheoryNotes the new theory notes
	 */
	public void setTheoryNotes(String newTheoryNotes){theoryNotes = newTheoryNotes;}
	
	/**
	 * Gets the exp notes.
	 *
	 * @return the exp notes
	 */
	public String getExpNotes(){return expNotes;}
	
	/**
	 * Sets the exp notes.
	 *
	 * @param newExpNotes the new exp notes
	 */
	public void setExpNotes(String newExpNotes){expNotes = newExpNotes;}
	
	/**
	 * Gets the theory file.
	 *
	 * @return the theory file
	 */
	public File getTheoryFile(){return theoryFile;}
	
	/**
	 * Sets the theory file.
	 *
	 * @param newTheoryFile the new theory file
	 */
	public void setTheoryFile(File newTheoryFile){theoryFile = newTheoryFile;}
	
	/**
	 * Gets the exp file.
	 *
	 * @return the exp file
	 */
	public File getExpFile(){return expFile;}
	
	/**
	 * Sets the exp file.
	 *
	 * @param newExpFile the new exp file
	 */
	public void setExpFile(File newExpFile){expFile = newExpFile;}
	
	/**
	 * Gets the isotope viktor.
	 *
	 * @return the isotope viktor
	 */
	public Vector getIsotopeViktor(){return isotopeViktor;}
	
	/**
	 * Sets the isotope viktor.
	 *
	 * @param newIsotopeViktor the new isotope viktor
	 */
	public void setIsotopeViktor(Vector newIsotopeViktor){isotopeViktor = newIsotopeViktor;}
	
	/**
	 * Gets the data info array.
	 *
	 * @return the data info array
	 */
	public String[] getDataInfoArray(){return dataInfoArray;}
	
	/**
	 * Gets the color values.
	 *
	 * @return the color values
	 */
	public double[] getColorValues(){return colorValues;}
	
	/**
	 * Sets the color values.
	 *
	 * @param newColorValues the new color values
	 */
	public void setColorValues(double[] newColorValues){colorValues = newColorValues;}
	
	/**
	 * Gets the scheme.
	 *
	 * @return the scheme
	 */
	public String getScheme(){return scheme;}
	
	/**
	 * Sets the scheme.
	 *
	 * @param newScheme the new scheme
	 */
	public void setScheme(String newScheme){scheme = newScheme;}
	
	/**
	 * Gets the bin data.
	 *
	 * @return the bin data
	 */
	public Vector getBinData(){return binData;}
	
	/**
	 * Sets the bin data.
	 *
	 * @param newBinData the new bin data
	 */
	public void setBinData(Vector newBinData){binData = newBinData;}
	
	/**
	 * Gets the include values.
	 *
	 * @return the include values
	 */
	public boolean getIncludeValues(){return includeValues;}
	
	/**
	 * Sets the include values.
	 *
	 * @param newIncludeValues the new include values
	 */
	public void setIncludeValues(boolean newIncludeValues){includeValues = newIncludeValues;}
	
}

class RowData extends Vector{

	public RowData(double min, double max, boolean show, Color color){
	
		addElement(new Double(min));
		addElement(new Double(max));
		addElement(new Boolean(show));
		addElement(color);
	
	}

}