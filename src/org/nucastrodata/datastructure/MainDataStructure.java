package org.nucastrodata.datastructure;

import java.awt.*; 
import java.io.File;
import java.util.*;

import org.nucastrodata.wizard.gui.filefilter.FilterType;

public class MainDataStructure implements DataStructure{
	
	private String header, user, id, pw, urlType, absolutePath;
	private boolean isMasterUser;
	
	private static String[] symbolString = {"n","H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na"
												, "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe" 
												, "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb"
												, "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba"
												, "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu"
												, "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn"
												, "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md"
												, "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc"
												, "Lv", "Ts", "Og", "Uue", "Ubn", "Ubu", "Ubb", "Ubt", "Ubq", "Ubp", "Ubh", "Ubs", "Ubo"
												, "Ube", "Utn", "Utu", "Utb", "Utt", "Utq", "Utp", "Uth", "Uts", "Uto", "Ute", "Uqn", "Uqu"
												, "Uqb", "Uqt", "Uqq", "Uqp", "Uqh", "Uqs", "Uqo", "Uqe", "Upn", "Upu", "Upb", "Upt", "Upq"
												, "Upp", "Uph", "Ups", "Upo", "Upe", "Uhn", "Uhu", "Uhb", "Uht", "Uhq", "Uhp", "Uhh", "Uhs"
												, "Uho", "Uhe", "Usn", "Usu", "Usb", "Ust", "Usq", "Usp"};

	private Point[] stablePointArray = {new Point(1,1), new Point(1,2), new Point(2,3), new Point(2,4), new Point(3,6), new Point(3,7), new Point(4,9)
								, new Point(5,10), new Point(5,11), new Point(6,12), new Point(6,13), new Point(7,14), new Point(7,15)
								, new Point(8,16), new Point(8,17), new Point(8,18), new Point(9,19), new Point(10,20), new Point(10,21)
								, new Point(10,22), new Point(11,23), new Point(12,24), new Point(12,25), new Point(12,26), new Point(13,27)
								, new Point(14,28), new Point(14,29), new Point(14,30), new Point(15,31), new Point(16,32), new Point(16,33)
								, new Point(16,34), new Point(17,35), new Point(16,36), new Point(18,36), new Point(17,37), new Point(18,38)
								, new Point(19,39), new Point(18,40), new Point(20,40), new Point(19,41), new Point(20,42), new Point(20,43)
								, new Point(20,44), new Point(21,45), new Point(20,46), new Point(22,46), new Point(22,47), new Point(22,48)
								, new Point(22,49), new Point(22,50), new Point(23,51), new Point(24,52), new Point(24,53), new Point(24,54)
								, new Point(26,54), new Point(25,55), new Point(26,56), new Point(26,57), new Point(26,58), new Point(28,58)
								, new Point(27,59), new Point(28,60), new Point(28,61), new Point(28,62), new Point(29,63), new Point(28,64)
								, new Point(30,64), new Point(29,65), new Point(30,66), new Point(30,67), new Point(30,68), new Point(31,69)
								, new Point(32,70), new Point(31,71), new Point(32,72), new Point(32,73), new Point(32,74), new Point(34,74)
								, new Point(33,75), new Point(32,76), new Point(34,76), new Point(34,77), new Point(34,78), new Point(35,79)
								, new Point(34,80), new Point(36,80), new Point(35,81), new Point(36,82), new Point(36,83), new Point(36,84)
								, new Point(38,84), new Point(37,85), new Point(36,86), new Point(38,86), new Point(38,87), new Point(38,88)
								, new Point(39,89), new Point(40,90), new Point(40,91), new Point(40,92), new Point(42,92), new Point(41,93)
								, new Point(40,94), new Point(42,94), new Point(42,95), new Point(42,96), new Point(44,96), new Point(42,97)
								, new Point(42,98), new Point(44,98), new Point(44,99), new Point(44,100), new Point(44,101), new Point(44,102)
								, new Point(46,102), new Point(45,103), new Point(44,104), new Point(46,104), new Point(46,105), new Point(46,106)
								, new Point(48,106), new Point(47,107), new Point(46,108), new Point(48,108), new Point(47,109), new Point(46,110)
								, new Point(48,110), new Point(48,111), new Point(48,112), new Point(50,112), new Point(49,113), new Point(48,114)
								, new Point(50,114), new Point(50,115), new Point(48,116), new Point(50,116), new Point(50,117), new Point(50,118)
								, new Point(50,119), new Point(50,120), new Point(52,120), new Point(51,121), new Point(50,122), new Point(52,122)
								, new Point(51,123), new Point(50,124), new Point(52,124), new Point(54,124), new Point(52,125), new Point(52,126)
								, new Point(54,126), new Point(53,127), new Point(54,128), new Point(54,129), new Point(54,130), new Point(56,130)
								, new Point(54,131), new Point(54,132), new Point(56,132), new Point(55,133), new Point(54,134), new Point(56,134)
								, new Point(56,135), new Point(56,136), new Point(58,136), new Point(56,137), new Point(56,138), new Point(58,138)
								, new Point(57,139), new Point(58,140), new Point(59,141), new Point(60,142), new Point(60,143), new Point(62,144)
								, new Point(60,145), new Point(60,146), new Point(60,148), new Point(62,150), new Point(63,151), new Point(62,152)
								, new Point(63,153), new Point(62,154), new Point(64,154), new Point(64,155), new Point(64,156), new Point(66,156)
								, new Point(64,157), new Point(64,158), new Point(66,158), new Point(65,159), new Point(64,160), new Point(66,160)
								, new Point(66,161), new Point(66,162), new Point(68,162), new Point(66,163), new Point(66,164), new Point(68,164)
								, new Point(67,165), new Point(68,166), new Point(68,167), new Point(68,168), new Point(70,168), new Point(69,169)
								, new Point(68,170), new Point(70,170), new Point(70,171), new Point(70,172), new Point(70,173), new Point(70,174)
								, new Point(71,175), new Point(70,176), new Point(72,176), new Point(72,177), new Point(72,178), new Point(72,179)
								, new Point(72,180), new Point(74,180), new Point(73,181), new Point(74,182), new Point(75,185), new Point(74,186)
								, new Point(76,187), new Point(76,188), new Point(76,189), new Point(76,190), new Point(77,191), new Point(76,192)
								, new Point(78,192), new Point(77,193), new Point(78,194), new Point(78,195), new Point(78,196), new Point(80,196)
								, new Point(79,197), new Point(78,198), new Point(80,198), new Point(80,199), new Point(80,200), new Point(80,201)
								, new Point(80,202), new Point(81,203), new Point(80,204), new Point(81,205), new Point(82,206), new Point(82,207)
								, new Point(82,208), new Point(83,209)};
								
	private Vector stablePointVector = new Vector();
	private File currentDir;
	private FilterType filter;
	private String currentFeatureSet = "rate";
	
	public MainDataStructure(){initialize();}
	
	public void initialize(){
		setHeader("2.8");
		setUser("");
		setID("");
		setPW("");
		setAbsolutePath("");
		setCurrentDir(null);
		for(int i=0; i<stablePointArray.length; i++){stablePointVector.addElement(stablePointArray[i]);}
		isMasterUser = false;
	}
	
	public boolean isMasterUser(){
		return isMasterUser;
	}
	
	public void setMasterUser(boolean isMasterUser){
		this.isMasterUser = isMasterUser;
	}

	public String getCurrentFeatureSet(){return currentFeatureSet;} 
	public void setCurrentFeatureSet(String newCurrentFeatureSet){currentFeatureSet = newCurrentFeatureSet;}
	
	public File getCurrentDir(){return currentDir;} 
	public void setCurrentDir(File currentDir){this.currentDir = currentDir;}
	
	public FilterType getFilter(){return filter;} 
	public void setFilter(FilterType filter){this.filter = filter;}

	public String getHeader(){return header;} 
	public void setHeader(String newHeader){header = newHeader;}

	public String getUser(){return user;} 
	public void setUser(String newUser){user = newUser;}
	
	public String getID(){return id;} 
	public void setID(String newID){id = newID;}
	
	public String getPW(){return pw;} 
	public void setPW(String newPW){pw = newPW;}
	
	public String getURLType(){return urlType;} 
	public void setURLType(String newURLType){urlType = newURLType;}

	public String getAbsolutePath(){return absolutePath;} 
	public void setAbsolutePath(String newAbsolutePath){absolutePath = newAbsolutePath;}
	
	public static String getElementSymbol(int z){return symbolString[z];}
	public Vector getStablePointVector(){return stablePointVector;}

	public double calcRate(double T9, double[] paramArray, int numberParams){
		
	    double THIRD = 1.0/3.0;
	    double FIVETHIRDS = 5.0/3.0;
        double T913 = Math.pow(T9,THIRD);
        double T953 = Math.pow(T9,FIVETHIRDS);	
		double rate = 0.0;
		
		switch(numberParams){
		
			case 7:
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9));
				break;
		
			case 14:
				
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9));
				
				break;
				
			case 21:
			
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[14] 
								+ paramArray[15]/T9 
								+ paramArray[16]/T913 
								+ paramArray[17]*T913 
								+ paramArray[18]*T9
                         		+ paramArray[19]*T953 
                         		+ paramArray[20]*Math.log(T9));
                         		
				break;
				
			case 28:
			
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[14] 
								+ paramArray[15]/T9 
								+ paramArray[16]/T913 
								+ paramArray[17]*T913 
								+ paramArray[18]*T9
                         		+ paramArray[19]*T953 
                         		+ paramArray[20]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[21] 
								+ paramArray[22]/T9 
								+ paramArray[23]/T913 
								+ paramArray[24]*T913 
								+ paramArray[25]*T9
                         		+ paramArray[26]*T953 
                         		+ paramArray[27]*Math.log(T9));
                         		
            	break;
                         		
         	case 35:
			
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[14] 
								+ paramArray[15]/T9 
								+ paramArray[16]/T913 
								+ paramArray[17]*T913 
								+ paramArray[18]*T9
                         		+ paramArray[19]*T953 
                         		+ paramArray[20]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[21] 
								+ paramArray[22]/T9 
								+ paramArray[23]/T913 
								+ paramArray[24]*T913 
								+ paramArray[25]*T9
                         		+ paramArray[26]*T953 
                         		+ paramArray[27]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[28] 
								+ paramArray[29]/T9 
								+ paramArray[30]/T913 
								+ paramArray[31]*T913 
								+ paramArray[32]*T9
                         		+ paramArray[33]*T953 
                         		+ paramArray[34]*Math.log(T9));
                         		
				break;
				
			case 42:
			
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[14] 
								+ paramArray[15]/T9 
								+ paramArray[16]/T913 
								+ paramArray[17]*T913 
								+ paramArray[18]*T9
                         		+ paramArray[19]*T953 
                         		+ paramArray[20]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[21] 
								+ paramArray[22]/T9 
								+ paramArray[23]/T913 
								+ paramArray[24]*T913 
								+ paramArray[25]*T9
                         		+ paramArray[26]*T953 
                         		+ paramArray[27]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[28] 
								+ paramArray[29]/T9 
								+ paramArray[30]/T913 
								+ paramArray[31]*T913 
								+ paramArray[32]*T9
                         		+ paramArray[33]*T953 
                         		+ paramArray[34]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[35] 
								+ paramArray[36]/T9 
								+ paramArray[37]/T913 
								+ paramArray[38]*T913 
								+ paramArray[39]*T9
                         		+ paramArray[40]*T953 
                         		+ paramArray[41]*Math.log(T9));
                         		
				break;
				
			case 49:
			
				rate = Math.exp(paramArray[0] 
								+ paramArray[1]/T9 
								+ paramArray[2]/T913 
								+ paramArray[3]*T913 
								+ paramArray[4]*T9
                         		+ paramArray[5]*T953 
                         		+ paramArray[6]*Math.log(T9))
                         	
                         	+ Math.exp(paramArray[7] 
								+ paramArray[8]/T9 
								+ paramArray[9]/T913 
								+ paramArray[10]*T913 
								+ paramArray[11]*T9
                         		+ paramArray[12]*T953 
                         		+ paramArray[13]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[14] 
								+ paramArray[15]/T9 
								+ paramArray[16]/T913 
								+ paramArray[17]*T913 
								+ paramArray[18]*T9
                         		+ paramArray[19]*T953 
                         		+ paramArray[20]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[21] 
								+ paramArray[22]/T9 
								+ paramArray[23]/T913 
								+ paramArray[24]*T913 
								+ paramArray[25]*T9
                         		+ paramArray[26]*T953 
                         		+ paramArray[27]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[28] 
								+ paramArray[29]/T9 
								+ paramArray[30]/T913 
								+ paramArray[31]*T913 
								+ paramArray[32]*T9
                         		+ paramArray[33]*T953 
                         		+ paramArray[34]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[35] 
								+ paramArray[36]/T9 
								+ paramArray[37]/T913 
								+ paramArray[38]*T913 
								+ paramArray[39]*T9
                         		+ paramArray[40]*T953 
                         		+ paramArray[41]*Math.log(T9))
                         		
                         	+ Math.exp(paramArray[42] 
								+ paramArray[43]/T9 
								+ paramArray[44]/T913 
								+ paramArray[45]*T913 
								+ paramArray[46]*T9
                         		+ paramArray[47]*T953 
                         		+ paramArray[48]*Math.log(T9));
                         		
        		break;
		
		}
		
        return rate;
    }
	
}