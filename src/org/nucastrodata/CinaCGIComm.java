package org.nucastrodata;

import java.io.*;
import java.net.*;

import javax.net.ssl.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.FluxTimestepDataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.NucDataDataStructure;
import org.nucastrodata.datastructure.util.NucDataSetDataStructure;
import org.nucastrodata.dialogs.ProgressBarDialog;
import org.nucastrodata.datastructure.util.RateDataStructure;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotFrame;
import org.nucastrodata.element.elementviz.abundtable.ElementVizAbundTableFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorExportFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorFrame;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotFrame;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotFrame;

import java.util.zip.*;
import java.security.*;

import org.nucastrodata.data.dataeval.DataEvalFrame;
import org.nucastrodata.data.dataman.DataManFrame;
import org.nucastrodata.data.dataman.DataManImportData2Panel;
import org.nucastrodata.data.dataviewer.DataViewerFrame;
import org.nucastrodata.rate.rategen.RateGenChartFrame;
import org.nucastrodata.rate.rategen.RateGenExtraFrame;
import org.nucastrodata.rate.rategen.RateGenFrame;
import org.nucastrodata.rate.rategen.RateGenInputCheckPanel;
import org.nucastrodata.rate.rategen.RateGenInputCheckPlotFrame;
import org.nucastrodata.rate.rategen.RateGenReadInputPanel;
import org.nucastrodata.rate.rateparam.RateParamChartFrame;
import org.nucastrodata.rate.rateparam.RateParamFrame;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPanel;
import org.nucastrodata.rate.rateparam.RateParamInputCheckPlotFrame;
import org.nucastrodata.rate.rateparam.RateParamPointFrame;
import org.nucastrodata.rate.rateparam.RateParamResultsOutputFrame;
import org.nucastrodata.rate.rateparam.RateParamStartFrame;
import org.nucastrodata.rate.rateman.RateManCreateRate2Panel;
import org.nucastrodata.rate.rateman.RateManFrame;
import org.nucastrodata.rate.rateman.RateManInvestRate3Panel;
import org.nucastrodata.rate.ratelibman.RateLibManFrame;
import org.nucastrodata.rate.rateviewer.RateViewerFrame;
import org.nucastrodata.rate.rateviewer.RateViewerPlotFrame;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizPointFrame;
import org.nucastrodata.info.RegisterFrame;

public class CinaCGIComm extends Component implements ActionListener{

	private String[] actionArray = {"GET ID"
									, "CHECK REACTION"
									, "READ INPUT"
									, "PREPROCESSING"
									, "GENERATE RATE"
									, "ABORT RATE GENERATION"
									, "RATE GENERATION UPDATE"
									, "RATE GENERATION OUTPUT"
									, "PARAMETERIZE RATE"
									, "ABORT RATE PARAMETERIZATION"
									, "RATE PARAMETERIZATION UPDATE"
									, "RATE PARAMETERIZATION OUTPUT"
									, "INVERSE PARAMETERS"
									, "GENERATE PARAMETER FORMAT"
									, "LOGOUT"
									, "GET RATE LIBRARY LIST"
									, "GET RATE LIBRARY ISOTOPES"
									, "GET RATE LIST"
									, "MODIFY RATE LIBRARY"
									, "MODIFY RATES"
									, "GET RATE LIBRARY INFO"
									, "GET RATE INFO"
									, "REGISTER"
									, "RATES EXIST"
									, "ADD MISSING INV RATES"
									, "GET INVERSE REACTION"
									, "SHARE RATE LIBRARY"
									, "GET ELEMENT SYNTHESIS TIME MAPPING"
									, "GET ELEMENT SYNTHESIS ISOTOPE MAPPING"
									, "GET ELEMENT SYNTHESIS THERMO PROFILE"
									, "GET ELEMENT SYNTHESIS ABUNDANCES"
									, "GET NUC DATA SET LIST"
									, "GET NUC DATA SET ISOTOPES"
									, "GET NUC DATA LIST"
									, "GET NUC DATA INFO"
									, "PARSE NUC DATA FILE"
									, "MODIFY NUC DATA SET"
									, "MODIFY NUC DATA"
									, "NUC DATA EXIST"
									, "SHARE NUC DATA SET"
									, "EXPORT RATE LIBRARY"
									, "MAKE ELEMENT SYNTHESIS MOVIE"
									, "GET ELEMENT SYNTHESIS FLUX MAPPING"
									, "GET ELEMENT SYNTHESIS FLUXES"
									, "GET ELEMENT SYNTHESIS WEIGHTED ABUNDANCES"};
	
	private String action;
	int numberActions = 45;
	int actionID = 10000;
	
	String headerString = "HEADER";
	String actionString = "ACTION";
	String userString = "USER";
	String idString = "ID";
	String pwString = "PW";
	String bodyString = "BODY";
	String reactionString = "REACTION";
	String reaction_typeString = "REACTION_TYPE";
	String notesString = "NOTES";
	String typeString = "TYPE";
	String formatString = "FORMAT";
	String xunitsString = "XUNITS";
	String yunitsString = "YUNITS";
	String filenameString = "FILENAME";
	String fileString = "FILE";
	String positive_chkString = "POSITIVE_CHK"; 
	String single_chkString = "SINGLE_CHK";
	String range_chkString = "RANGE_CHK";
	String continuity_chkString = "CONINUITY_CHK";
	String error_chkString = "ERROR_CHK";
	String reaction_chkString = "REACTION_CHK";
	String tminString = "TMIN";
	String tmaxString = "TMAX";
	String plevelString = "PLEVEL";
	String methodString = "METHOD";
	String iterationsString = "ITERATIONS";
	String s_parm_numString = "S_PARM_NUM";
	String start_parmsString = "START_PARMS";
	String parm_checkString = "PARM_CHECK";
	String max_diffString = "MAX_DIFF";
	String groupString = "GROUP";
	String libraryString = "LIBRARY";
	String isotopeString = "ISOTOPE";
	String typeDatabaseString = "TYPE";
	String src_libString = "SRC_LIB";
	String del_src_libString = "DEL_SRC_LIB";
	String dest_libString = "DEST_LIB";
	String dest_groupString = "DEST_GROUP";
	String chk_temp_behaviorString = "CHK_TEMP_BEHAVIOR";
	String chk_overflowString = "CHK_OVERFLOW";
	String chk_inverseString = "CHK_INVERSE";
	String ratesString = "RATES";
	String make_inverseString = "MAKE_INVERSE";
	String propertiesString = "PROPERTIES";
	String firstnameString = "FIRST_NAME";
	String lastnameString = "LAST_NAME";
	String emailString = "EMAIL";
	String institutionString = "INSTITUTION";
	String addressString = "ADDRESS";
	String researchString = "RESEARCH";
	String infoString = "INFO";
	String hear_of_suiteString = "HEAR_OF_SUITE";
	String max_timestepsString = "MAX_TIMESTEPS";
	String include_weakString = "INCLUDE_WEAK";
	String include_screeningString = "INCLUDE_SCREENING";
	String start_timeString = "START_TIME";
	String stop_timeString = "STOP_TIME";
	String pathString = "PATH";
	String nameString = "NAME";
	String overwriteString = "OVERWRITE";
	String zonesString = "ZONES";
	String zoneString = "ZONE";
	String isotopesString = "ISOTOPES";
	String setString = "SET";
	String nucDataString = "NUC_DATA";
	String src_setString = "SRC_SET";
	String dest_setString = "DEST_SET";
	String del_src_setString = "DEL_SRC_SET";
	String del_nuc_dataString = "DEL_NUC_DATA";
	String argumentsString = "ARGUMENTS";
	String simulationString = "SIMULATION";
	String eraseString = "ERASE";
	String new_pathString = "NEW_PATH";
	String reactionsString = "REACTIONS";
	String sumString = "SUM";
	String final_stepString = "FINAL_STEP";
	String widthString = "WIDTH";
	String heightString = "HEIGHT";
	String scaleFactorString = "SCALE_FACTOR";
	String compFactorString = "COMP_FACTOR";
	String frame_skip_intervalString = "FRAME_SKIP_INTERVAL";
	
	int byteCounter;
	
	Vector cautionVector = new Vector(0);
	Vector errorVector = new Vector(0);
	Vector reasonVector = new Vector(0);
	
	JDialog errorDialog;
	JDialog cautionDialog;
	JDialog reasonDialog;
	JDialog messageDialog;
	
	JButton okButton, yesButton, noButton, okButtonReason;
	JLabel cautionLabel;
	JTextArea errorTextArea;
	JTextArea cautionTextArea;
	JTextArea reasonTextArea;
	JPanel cautionButtonPanel;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	String outputString, inputString;
	
	String header;
	String user;
	String id;
	String pw;
	String pwEncrypted;
	String body;
	String reaction;
	String reaction_type;
	String notes;
	String type;
	String format;
	String xunits;
	String yunits;
	String filename;
	String file;
	String positive_chk;
	String single_chk;
	String range_chk;
	String continuity_chk;
	String error_chk;	
	String reaction_chk;
	String tmin;
	String tmax;
	String plevel;
	String method;
	String iterations;
	String tminparam;
	String tmaxparam;
	String plevelparam;
	String s_parm_num;
	String start_parms;
	String parm_check;
	String max_diff;
	String group;
	String library;
	String isotope;
	String typeDatabase;
	String src_lib;
	String dest_lib;
	String dest_group;
	String chk_temp_behavior;
	String chk_overflow;
	String chk_inverse;
	String del_src_lib;
	String rates;
	String make_inverse;
	String properties;
	String lastname;
	String firstname;
	String email;
	String institution;
	String address;
	String research;
	String info;
	String hear_of_suite;
	String path;
	String zones;
	String zone;
	String isotopes;
	String set;
	String nucData;
	String src_set;
	String dest_set;
	String del_src_set;
	String del_nuc_data;
	String arguments;
	String simulation;
	String erase;
	String new_path;
	String reactions;
	String sum;
	String final_step;
	String width;
	String height;
	String scale_factor;
	String comp_factor;
	String frame_skip_interval;
	
	boolean errorFlag = false;
	boolean cautionFlag = false;
	boolean reasonFlag = false;
	
	boolean[] flagArray = {errorFlag, cautionFlag, reasonFlag};
	
	static java.util.Timer timer;
	
	public CinaCGIComm(){}
	
	public void initialize(){
		
		outputString = "";
		inputString = "";
		header = "";
		user = "";
		id = "";
		pw = "";
		pwEncrypted = "";
		body = "";
		reaction = "";
		reaction_type = "";
		notes = "";
		type = "";
		format = "";
		xunits = "";
		yunits = "";
		filename = "";
		file = "";
		positive_chk = "";
		single_chk = "";
		range_chk = "";
		continuity_chk = "";
		error_chk = "";	
		reaction_chk = "";
		tmin = "";
		tmax = "";
		plevel = "";
		method = "";
		iterations = "";
		tminparam = "";
		tmaxparam = "";
		plevelparam = "";
		s_parm_num = "";
		start_parms = "";
		parm_check = "";
		max_diff = "";
		group = "";
		library = "";
		isotope = "";
		typeDatabase = "";
		src_lib = "";
		dest_lib = "";
		dest_group = "";
		chk_temp_behavior = "";
		chk_overflow = "";
		chk_inverse = "";
		del_src_lib = "";
		rates = "";
		make_inverse = "";
		properties = "";
		lastname = "";
		firstname = "";
		email = "";
		institution = "";
		address = "";
		research = "";
		info = "";
		hear_of_suite = "";
		path = "";
		zones = "";
		zone = "";
		isotopes = "";
		set = "";
		nucData = "";
		src_set = "";
		dest_set = "";
		del_src_set = "";
		del_nuc_data = "";
		format = "";
		arguments = "";
		simulation = "";
		erase = "";
		new_path = "";
		reactions = "";
		sum = "";
		final_step = "";
		width = "";
		height = "";	
		scale_factor = "";
		comp_factor = "";
		frame_skip_interval = "";
	}
	
	public boolean doCGICall(String actionString, Frame frame){
		
		boolean goodCGICall = false;
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm(actionString, frame, null);
		
		if(!flagArray[0] && !flagArray[1] && !flagArray[2]){goodCGICall = true;}

		return goodCGICall;
	
	}
	
	public boolean doCGICall(String actionString, Frame frame, ProgressBarDialog dialog){
		
		boolean goodCGICall = false;
		
		boolean[] flagArray = Cina.cinaCGIComm.doCGIComm(actionString, frame, dialog);
		
		if(!flagArray[0] && !flagArray[1] && !flagArray[2]){goodCGICall = true;}

		return goodCGICall;
	
	}
	
	public boolean[] doCGIComm(String action, Frame frame){
		
		return doCGIComm(action, frame, null);
	
	}
	
	public boolean[] doCGIComm(String action, Frame frame, ProgressBarDialog dialog){

		
		this.action = action;
		
		//Call initialize
		initialize();
		
		Object transmissionObject = new Object();
		
		//Create actionID variable to get enumeration of the action called
		//The enumeration is the indexed location of the action in the action array					
		int actionID = getActionID(action, actionArray);

		try{
		
			//Encode action string
			action = URLEncoder.encode(action, "UTF-8");
	
			//Encode and retrieve header, user, id, and pw from the main data structure
			header = URLEncoder.encode(Cina.cinaMainDataStructure.getHeader(), "UTF-8");
			user = URLEncoder.encode(Cina.cinaMainDataStructure.getUser(), "UTF-8");
			id = URLEncoder.encode(Cina.cinaMainDataStructure.getID(), "UTF-8");
			
			pwEncrypted = getEncryptedString(Cina.cinaMainDataStructure.getPW());
			pw = URLEncoder.encode(pwEncrypted, "UTF-8");
			
			if(frame instanceof RateManFrame){
				
				//Get current CGI string variables
				isotope = URLEncoder.encode(Cina.rateManDataStructure.getIsotope(), "UTF-8");
				library = URLEncoder.encode(Cina.rateManDataStructure.getLibrary(), "UTF-8");
				typeDatabase = URLEncoder.encode(Cina.rateManDataStructure.getTypeDatabase(), "UTF-8");
				src_lib = URLEncoder.encode(Cina.rateManDataStructure.getSRC_LIB(), "UTF-8");
				dest_lib = URLEncoder.encode(Cina.rateManDataStructure.getDEST_LIB(), "UTF-8");
				chk_temp_behavior = URLEncoder.encode(Cina.rateManDataStructure.getCHK_TEMP_BEHAVIOR(), "UTF-8");
				chk_overflow = URLEncoder.encode(Cina.rateManDataStructure.getCHK_OVERFLOW(), "UTF-8");
				chk_inverse = URLEncoder.encode(Cina.rateManDataStructure.getCHK_INVERSE(), "UTF-8");
				del_src_lib = URLEncoder.encode(Cina.rateManDataStructure.getDEL_SRC_LIB(), "UTF-8");
				rates = URLEncoder.encode(Cina.rateManDataStructure.getRates(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.rateManDataStructure.getDEST_GROUP(), "UTF-8");
				group = URLEncoder.encode(Cina.rateManDataStructure.getGroup(), "UTF-8");
				reaction = URLEncoder.encode(Cina.rateManDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.rateManDataStructure.getReaction_type(), "UTF-8");
				properties = URLEncoder.encode(Cina.rateManDataStructure.getProperties(), "UTF-8");
				make_inverse = URLEncoder.encode(Cina.rateManDataStructure.getMake_inverse(), "UTF-8");
			
			}else if(frame instanceof RateLibManFrame){
			
				//Get current CGI string variables
				group = URLEncoder.encode(Cina.rateLibManDataStructure.getGroup(), "UTF-8");
				library = URLEncoder.encode(Cina.rateLibManDataStructure.getLibrary(), "UTF-8");
				rates = URLEncoder.encode(Cina.rateLibManDataStructure.getRates(), "UTF-8");
				isotope = URLEncoder.encode(Cina.rateLibManDataStructure.getIsotope(), "UTF-8");
				typeDatabase = URLEncoder.encode(Cina.rateLibManDataStructure.getTypeDatabase(), "UTF-8");
				src_lib = URLEncoder.encode(Cina.rateLibManDataStructure.getSRC_LIB(), "UTF-8");
				del_src_lib = URLEncoder.encode(Cina.rateLibManDataStructure.getDEL_SRC_LIB(), "UTF-8");
				dest_lib = URLEncoder.encode(Cina.rateLibManDataStructure.getDEST_LIB(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.rateLibManDataStructure.getDEST_GROUP(), "UTF-8");
				chk_temp_behavior = URLEncoder.encode(Cina.rateLibManDataStructure.getCHK_TEMP_BEHAVIOR(), "UTF-8");
				chk_overflow = URLEncoder.encode(Cina.rateLibManDataStructure.getCHK_OVERFLOW(), "UTF-8");
				chk_inverse = URLEncoder.encode(Cina.rateLibManDataStructure.getCHK_INVERSE(), "UTF-8");
				properties = URLEncoder.encode(Cina.rateLibManDataStructure.getProperties(), "UTF-8");
				make_inverse = URLEncoder.encode(Cina.rateLibManDataStructure.getMake_inverse(), "UTF-8");
				reaction = URLEncoder.encode(Cina.rateLibManDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.rateLibManDataStructure.getReaction_type(), "UTF-8");
				format = URLEncoder.encode(Cina.rateLibManDataStructure.getFormat(), "UTF-8");
			
			//If the parent frame is the rate Viewer
			}else if(frame instanceof RateViewerFrame
						|| frame instanceof RateViewerPlotFrame){
			
				//Get current CGI string variables
				group = URLEncoder.encode(Cina.rateViewerDataStructure.getGroup(), "UTF-8");
				rates = URLEncoder.encode(Cina.rateViewerDataStructure.getRates(), "UTF-8");
				properties = URLEncoder.encode(Cina.rateViewerDataStructure.getProperties(), "UTF-8");
				library = URLEncoder.encode(Cina.rateViewerDataStructure.getLibrary(), "UTF-8");
				isotope = URLEncoder.encode(Cina.rateViewerDataStructure.getIsotope(), "UTF-8");
				typeDatabase = URLEncoder.encode(Cina.rateViewerDataStructure.getTypeDatabase(), "UTF-8");
			
			//If the parent frame is the Rate Generator	
			}else if(frame instanceof RateGenFrame
						|| frame instanceof RateGenExtraFrame
						|| frame instanceof RateGenChartFrame){
			
				//Get current CGI string variables
				set = URLEncoder.encode(Cina.rateGenDataStructure.getSet(), "UTF-8");
				group = URLEncoder.encode(Cina.rateGenDataStructure.getGroup(), "UTF-8");
				nucData = URLEncoder.encode(Cina.rateGenDataStructure.getNucData(), "UTF-8");
				isotope = URLEncoder.encode(Cina.rateGenDataStructure.getIsotope(), "UTF-8");
				properties = URLEncoder.encode(Cina.rateGenDataStructure.getProperties(), "UTF-8");
				src_lib = URLEncoder.encode(Cina.rateGenDataStructure.getSRC_LIB(), "UTF-8");
				del_src_lib = URLEncoder.encode(Cina.rateGenDataStructure.getDEL_SRC_LIB(), "UTF-8");
				dest_lib = URLEncoder.encode(Cina.rateGenDataStructure.getDEST_LIB(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.rateGenDataStructure.getDEST_GROUP(), "UTF-8");
				chk_temp_behavior = URLEncoder.encode(Cina.rateGenDataStructure.getCHK_TEMP_BEHAVIOR(), "UTF-8");
				chk_overflow = URLEncoder.encode(Cina.rateGenDataStructure.getCHK_OVERFLOW(), "UTF-8");
				chk_inverse = URLEncoder.encode(Cina.rateGenDataStructure.getCHK_INVERSE(), "UTF-8");
				body = URLEncoder.encode(Cina.rateGenDataStructure.getBody(), "UTF-8");
				reaction = URLEncoder.encode(Cina.rateGenDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.rateGenDataStructure.getReaction_type(), "UTF-8");
				notes = URLEncoder.encode(Cina.rateGenDataStructure.getNotes(), "UTF-8"); 
				type = URLEncoder.encode(Cina.rateGenDataStructure.getType(), "UTF-8");
				format = URLEncoder.encode(Cina.rateGenDataStructure.getFormat(), "UTF-8");
				xunits = URLEncoder.encode(Cina.rateGenDataStructure.getXunits(), "UTF-8");
				yunits = URLEncoder.encode(Cina.rateGenDataStructure.getYunits(), "UTF-8");
				filename = URLEncoder.encode(Cina.rateGenDataStructure.getFilename(), "UTF-8");
				file = URLEncoder.encode(Cina.rateGenDataStructure.getFile(), "UTF-8");
				positive_chk = URLEncoder.encode(Cina.rateGenDataStructure.getPositive_chk(), "UTF-8");
				single_chk = URLEncoder.encode(Cina.rateGenDataStructure.getSingle_chk(), "UTF-8");
				range_chk = URLEncoder.encode(Cina.rateGenDataStructure.getRange_chk(), "UTF-8");
				continuity_chk = URLEncoder.encode(Cina.rateGenDataStructure.getContinuity_chk(), "UTF-8");
				error_chk = URLEncoder.encode(Cina.rateGenDataStructure.getError_chk(), "UTF-8");		
				reaction_chk = URLEncoder.encode(Cina.rateGenDataStructure.getReaction_chk(), "UTF-8");
				tmin = URLEncoder.encode(Cina.rateGenDataStructure.getTmin(), "UTF-8");
				tmax = URLEncoder.encode(Cina.rateGenDataStructure.getTmax(), "UTF-8");
				plevel = URLEncoder.encode(Cina.rateGenDataStructure.getPlevel(), "UTF-8");
				rates = URLEncoder.encode(Cina.rateGenDataStructure.getRates(), "UTF-8");
				library = URLEncoder.encode(Cina.rateGenDataStructure.getLibrary(), "UTF-8");
				typeDatabase = URLEncoder.encode(Cina.rateGenDataStructure.getTypeDatabase(), "UTF-8");
				dest_set = URLEncoder.encode(Cina.rateGenDataStructure.getDEST_SET(), "UTF-8");
				src_set = URLEncoder.encode(Cina.rateGenDataStructure.getSRC_SET(), "UTF-8");
				del_src_set = URLEncoder.encode(Cina.rateGenDataStructure.getDEL_SRC_SET(), "UTF-8");
				del_nuc_data = URLEncoder.encode(Cina.rateGenDataStructure.getDEL_NUC_DATA(), "UTF-8");
			
			//If the parent frame is Register 	
			}else if(frame instanceof RateParamFrame 
						|| frame instanceof RateParamResultsOutputFrame
						|| frame instanceof RateParamStartFrame
						|| frame instanceof RateParamChartFrame
						|| frame instanceof RateParamPointFrame){
			
				//Get current CGI string variables
				set = URLEncoder.encode(Cina.rateParamDataStructure.getSet(), "UTF-8");
				group = URLEncoder.encode(Cina.rateParamDataStructure.getGroup(), "UTF-8");
				nucData = URLEncoder.encode(Cina.rateParamDataStructure.getNucData(), "UTF-8");
				isotope = URLEncoder.encode(Cina.rateParamDataStructure.getIsotope(), "UTF-8");
				properties = URLEncoder.encode(Cina.rateParamDataStructure.getProperties(), "UTF-8");
				src_lib = URLEncoder.encode(Cina.rateParamDataStructure.getSRC_LIB(), "UTF-8");
				del_src_lib = URLEncoder.encode(Cina.rateParamDataStructure.getDEL_SRC_LIB(), "UTF-8");
				dest_lib = URLEncoder.encode(Cina.rateParamDataStructure.getDEST_LIB(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.rateParamDataStructure.getDEST_GROUP(), "UTF-8");
				chk_temp_behavior = URLEncoder.encode(Cina.rateParamDataStructure.getCHK_TEMP_BEHAVIOR(), "UTF-8");
				chk_overflow = URLEncoder.encode(Cina.rateParamDataStructure.getCHK_OVERFLOW(), "UTF-8");
				chk_inverse = URLEncoder.encode(Cina.rateParamDataStructure.getCHK_INVERSE(), "UTF-8");
				body = URLEncoder.encode(Cina.rateParamDataStructure.getBody(), "UTF-8");
				reaction = URLEncoder.encode(Cina.rateParamDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.rateParamDataStructure.getReaction_type(), "UTF-8");
				notes = URLEncoder.encode(Cina.rateParamDataStructure.getNotes(), "UTF-8"); 
				type = URLEncoder.encode(Cina.rateParamDataStructure.getType(), "UTF-8");
				format = URLEncoder.encode(Cina.rateParamDataStructure.getFormat(), "UTF-8");
				xunits = URLEncoder.encode(Cina.rateParamDataStructure.getXunits(), "UTF-8");
				yunits = URLEncoder.encode(Cina.rateParamDataStructure.getYunits(), "UTF-8");
				filename = URLEncoder.encode(Cina.rateParamDataStructure.getFilename(), "UTF-8");
				file = URLEncoder.encode(Cina.rateParamDataStructure.getFile(), "UTF-8");
				positive_chk = URLEncoder.encode(Cina.rateParamDataStructure.getPositive_chk(), "UTF-8");
				single_chk = URLEncoder.encode(Cina.rateParamDataStructure.getSingle_chk(), "UTF-8");
				range_chk = URLEncoder.encode(Cina.rateParamDataStructure.getRange_chk(), "UTF-8");
				continuity_chk = URLEncoder.encode(Cina.rateParamDataStructure.getContinuity_chk(), "UTF-8");
				error_chk = URLEncoder.encode(Cina.rateParamDataStructure.getError_chk(), "UTF-8");		
				reaction_chk = URLEncoder.encode(Cina.rateParamDataStructure.getReaction_chk(), "UTF-8");
				tmin = URLEncoder.encode(Cina.rateParamDataStructure.getTmin(), "UTF-8");
				tmax = URLEncoder.encode(Cina.rateParamDataStructure.getTmax(), "UTF-8");
				plevel = URLEncoder.encode(Cina.rateParamDataStructure.getPlevel(), "UTF-8");
				method = URLEncoder.encode(Cina.rateParamDataStructure.getMethod(), "UTF-8");
				iterations = URLEncoder.encode(Cina.rateParamDataStructure.getIterations(), "UTF-8");
				tminparam = URLEncoder.encode(Cina.rateParamDataStructure.getTminparam(), "UTF-8");
				tmaxparam = URLEncoder.encode(Cina.rateParamDataStructure.getTmaxparam(), "UTF-8");
				plevelparam = URLEncoder.encode(Cina.rateParamDataStructure.getPlevelparam(), "UTF-8");
				s_parm_num = URLEncoder.encode(Cina.rateParamDataStructure.getS_parm_num(), "UTF-8");
				start_parms = URLEncoder.encode(Cina.rateParamDataStructure.getStart_parms(), "UTF-8");
				parm_check = URLEncoder.encode(Cina.rateParamDataStructure.getParm_check(), "UTF-8");
				max_diff = URLEncoder.encode(Cina.rateParamDataStructure.getMax_diff(), "UTF-8");
				rates = URLEncoder.encode(Cina.rateParamDataStructure.getRates(), "UTF-8");
				make_inverse = URLEncoder.encode(Cina.rateParamDataStructure.getMake_inverse(), "UTF-8");
				library = URLEncoder.encode(Cina.rateParamDataStructure.getLibrary(), "UTF-8");
				typeDatabase = URLEncoder.encode(Cina.rateParamDataStructure.getTypeDatabase(), "UTF-8");
				
			//If the parent frame is Register 	
			}else if(frame instanceof RegisterFrame){
			
				//Get current CGI string variables
				firstname = URLEncoder.encode(Cina.registerDataStructure.getFirstname(), "UTF-8");
				lastname = URLEncoder.encode(Cina.registerDataStructure.getLastname(), "UTF-8");
				email = URLEncoder.encode(Cina.registerDataStructure.getEmail(), "UTF-8");
				institution = URLEncoder.encode(Cina.registerDataStructure.getInstitution(), "UTF-8");
				address = URLEncoder.encode(Cina.registerDataStructure.getAddress(), "UTF-8");
				research = URLEncoder.encode(Cina.registerDataStructure.getResearch(), "UTF-8");
				info = URLEncoder.encode(Cina.registerDataStructure.getInfo(), "UTF-8");
				hear_of_suite = URLEncoder.encode(Cina.registerDataStructure.getHear_of_suite(), "UTF-8");
			
			}else if(frame instanceof ElementSynthFrame){
				
				//Get current CGI string variables
				group = URLEncoder.encode(Cina.elementSynthDataStructure.getLibGroup(), "UTF-8");
				library = URLEncoder.encode(Cina.elementSynthDataStructure.getCurrentSimWorkDataStructure().getLibrary(), "UTF-8");
				type = URLEncoder.encode(Cina.elementSynthDataStructure.getCurrentSimWorkDataStructure().getAl26Type(), "UTF-8");
				isotope = URLEncoder.encode(Cina.elementSynthDataStructure.getIsotope(), "UTF-8");
				rates = URLEncoder.encode(Cina.elementSynthDataStructure.getRates(), "UTF-8");
				properties = URLEncoder.encode(Cina.elementSynthDataStructure.getProperties(), "UTF-8");
				path = URLEncoder.encode(Cina.elementSynthDataStructure.getPath(), "UTF-8");
			
			//If the parent frame is the Element Synthesis Simulator
			}else if(frame instanceof ElementVizFrame
						|| frame instanceof ElementVizAnimatorExportFrame
						|| frame instanceof ElementVizAbundPlotFrame
						|| frame instanceof ElementVizAbundTableFrame
						|| frame instanceof ElementVizWeightPlotFrame
						|| frame instanceof ElementVizScalePlotFrame
						|| frame instanceof ElementVizPointFrame
						|| frame instanceof ElementVizAnimatorFrame){
	
				//Get current CGI string variables
				path = URLEncoder.encode(Cina.elementVizDataStructure.getPath(), "UTF-8");
				zones = URLEncoder.encode(Cina.elementVizDataStructure.getZones(), "UTF-8");
				zone = URLEncoder.encode(Cina.elementVizDataStructure.getZone(), "UTF-8");
				isotopes = URLEncoder.encode(Cina.elementVizDataStructure.getIsotopes(), "UTF-8");
				library = URLEncoder.encode(Cina.elementVizDataStructure.getLibrary(), "UTF-8");
				reactions = URLEncoder.encode(Cina.elementVizDataStructure.getReactions(), "UTF-8");
				arguments = URLEncoder.encode(Cina.elementVizDataStructure.getArguments(), "UTF-8");
				simulation = URLEncoder.encode(Cina.elementVizDataStructure.getSimulation(), "UTF-8");
				sum = URLEncoder.encode(Cina.elementVizDataStructure.getSum(), "UTF-8");
				final_step = URLEncoder.encode(Cina.elementVizDataStructure.getFinal_step(), "UTF-8");
				rates = URLEncoder.encode(Cina.elementVizDataStructure.getRates(), "UTF-8");
				properties = URLEncoder.encode(Cina.elementVizDataStructure.getProperties(), "UTF-8");
				group = URLEncoder.encode(Cina.elementVizDataStructure.getGroup(), "UTF-8");
				width = URLEncoder.encode(Cina.elementVizDataStructure.getWidth(), "UTF-8");
				height = URLEncoder.encode(Cina.elementVizDataStructure.getHeight(), "UTF-8");
				scale_factor = URLEncoder.encode(Cina.elementVizDataStructure.getScaleFactor(), "UTF-8");
				comp_factor = URLEncoder.encode(Cina.elementVizDataStructure.getCompFactor(), "UTF-8");
				frame_skip_interval = URLEncoder.encode(Cina.elementVizDataStructure.getFrame_skip_interval(), "UTF-8");
				
			}else if(frame instanceof DataManFrame){
				
				group = URLEncoder.encode(Cina.dataManDataStructure.getGroup(), "UTF-8");
				set = URLEncoder.encode(Cina.dataManDataStructure.getSet(), "UTF-8");
				isotope = URLEncoder.encode(Cina.dataManDataStructure.getIsotope(), "UTF-8");
				type = URLEncoder.encode(Cina.dataManDataStructure.getType(), "UTF-8");
				reaction = URLEncoder.encode(Cina.dataManDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.dataManDataStructure.getReaction_type(), "UTF-8");
				notes = URLEncoder.encode(Cina.dataManDataStructure.getNotes(), "UTF-8"); 
				type = URLEncoder.encode(Cina.dataManDataStructure.getType(), "UTF-8");
				format = URLEncoder.encode(Cina.dataManDataStructure.getFormat(), "UTF-8");
				xunits = URLEncoder.encode(Cina.dataManDataStructure.getXunits(), "UTF-8");
				yunits = URLEncoder.encode(Cina.dataManDataStructure.getYunits(), "UTF-8");
				filename = URLEncoder.encode(Cina.dataManDataStructure.getFilename(), "UTF-8");
				file = URLEncoder.encode(Cina.dataManDataStructure.getFile(), "UTF-8");
				properties = URLEncoder.encode(Cina.dataManDataStructure.getProperties(), "UTF-8");
				nucData = URLEncoder.encode(Cina.dataManDataStructure.getNucData(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.dataManDataStructure.getDEST_GROUP(), "UTF-8");
				dest_set = URLEncoder.encode(Cina.dataManDataStructure.getDEST_SET(), "UTF-8");
				src_set = URLEncoder.encode(Cina.dataManDataStructure.getSRC_SET(), "UTF-8");
				del_src_set = URLEncoder.encode(Cina.dataManDataStructure.getDEL_SRC_SET(), "UTF-8");
				del_nuc_data = URLEncoder.encode(Cina.dataManDataStructure.getDEL_NUC_DATA(), "UTF-8");
				
			}else if(frame instanceof DataViewerFrame){
				
				group = URLEncoder.encode(Cina.dataViewerDataStructure.getGroup(), "UTF-8");	
				library = URLEncoder.encode(Cina.dataViewerDataStructure.getLibrary(), "UTF-8");
				set = URLEncoder.encode(Cina.dataViewerDataStructure.getSet(), "UTF-8");
				isotope = URLEncoder.encode(Cina.dataViewerDataStructure.getIsotope(), "UTF-8");
				type = URLEncoder.encode(Cina.dataViewerDataStructure.getType(), "UTF-8");
				properties = URLEncoder.encode(Cina.dataViewerDataStructure.getProperties(), "UTF-8");
				nucData = URLEncoder.encode(Cina.dataViewerDataStructure.getNucData(), "UTF-8");
			
			}else if(frame instanceof DataEvalFrame){
			
				group = URLEncoder.encode(Cina.dataEvalDataStructure.getGroup(), "UTF-8");
				library = URLEncoder.encode(Cina.dataEvalDataStructure.getLibrary(), "UTF-8");
				set = URLEncoder.encode(Cina.dataEvalDataStructure.getSet(), "UTF-8");
				isotope = URLEncoder.encode(Cina.dataEvalDataStructure.getIsotope(), "UTF-8");
				type = URLEncoder.encode(Cina.dataEvalDataStructure.getType(), "UTF-8");
				reaction = URLEncoder.encode(Cina.dataEvalDataStructure.getReaction(), "UTF-8");
				reaction_type = URLEncoder.encode(Cina.dataEvalDataStructure.getReaction_type(), "UTF-8");
				notes = URLEncoder.encode(Cina.dataEvalDataStructure.getNotes(), "UTF-8"); 
				type = URLEncoder.encode(Cina.dataEvalDataStructure.getType(), "UTF-8");
				format = URLEncoder.encode(Cina.dataEvalDataStructure.getFormat(), "UTF-8");
				xunits = URLEncoder.encode(Cina.dataEvalDataStructure.getXunits(), "UTF-8");
				yunits = URLEncoder.encode(Cina.dataEvalDataStructure.getYunits(), "UTF-8");
				filename = URLEncoder.encode(Cina.dataEvalDataStructure.getFilename(), "UTF-8");
				file = URLEncoder.encode(Cina.dataEvalDataStructure.getFile(), "UTF-8");
				properties = URLEncoder.encode(Cina.dataEvalDataStructure.getProperties(), "UTF-8");
				nucData = URLEncoder.encode(Cina.dataEvalDataStructure.getNucData(), "UTF-8");
				dest_group = URLEncoder.encode(Cina.dataEvalDataStructure.getDEST_GROUP(), "UTF-8");
				dest_set = URLEncoder.encode(Cina.dataEvalDataStructure.getDEST_SET(), "UTF-8");
				src_set = URLEncoder.encode(Cina.dataEvalDataStructure.getSRC_SET(), "UTF-8");
				del_src_set = URLEncoder.encode(Cina.dataEvalDataStructure.getDEL_SRC_SET(), "UTF-8");
				del_nuc_data = URLEncoder.encode(Cina.dataEvalDataStructure.getDEL_NUC_DATA(), "UTF-8");
				
			}
		
		}catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}

		switch(actionID){
		
			case 0:
				
				outputString = doGET_ID(header, action, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_IDString(inputString, frame);
				
				break;
				
			case 1:
				
				outputString = doCHECK_REACTION(header, id, action
											, user, pw, reaction, reaction_type);
				inputString = transmitCGIString(outputString);
				flagArray = parseCHECK_REACTIONString(inputString, frame);
			
				break;
				
			case 2:
			
				outputString = doREAD_INPUT(header, id, action, user, pw
										, reaction, reaction_type, notes, type, format, xunits, yunits, filename, file);						
				inputString = transmitCGIString(outputString);
				flagArray = parseREAD_INPUTString(inputString, frame);
				
				break;
		
			case 3:
			
				outputString = doPREPROCESSING(header, id, action, user, pw
											, positive_chk, single_chk
											, range_chk, continuity_chk
											, error_chk, reaction_chk);
				inputString = transmitCGIString(outputString);
				flagArray = parsePREPROCESSINGString(inputString, frame);
			
				break;
				
			case 4:
			
				outputString = doGENERATE_RATE(header, id, action, user, pw, tmin, tmax, plevel);
				inputString = transmitCGIString(outputString);
				flagArray = parseGENERATE_RATEString(inputString, frame);
			
				break;
		
			case 5:
			
				outputString = doABORT_RATE_GENERATION(header, id, action, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseABORT_RATE_GENERATIONString(inputString, frame);
			
				break;
				
			case 6:
			
				outputString = doRATE_GENERATION_UPDATE(header, id, action, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseRATE_GENERATION_UPDATEString(inputString, frame);
			
				break;
				
			case 7:
			
				outputString = doRATE_GENERATION_OUTPUT(header, id, action, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseRATE_GENERATION_OUTPUTString(inputString, frame);
			
				break;
		
			case 8:
			
				outputString = doPARAMETERIZE_RATE(header, id, action, user
													, pw, method, iterations
													, tminparam, tmaxparam
													, plevelparam, s_parm_num
													, parm_check, max_diff, start_parms);
				inputString = transmitCGIString(outputString);
				flagArray = parsePARAMETERIZE_RATEString(inputString, frame);
			
				break;
				
			case 9:
			
				outputString = doABORT_RATE_PARAMETERIZATION(header, id, action
														, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseABORT_RATE_PARAMETERIZATIONString(inputString, frame);
			
				break;
		
			case 10:
			
				outputString = doRATE_PARAMETERIZATION_UPDATE(header, id, action
														, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseRATE_PARAMETERIZATION_UPDATEString(inputString, frame);
			
				break;
		
			case 11:
			
				outputString = doRATE_PARAMETERIZATION_OUTPUT(header, id, action
															, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseRATE_PARAMETERIZATION_OUTPUTString(inputString, frame);
			
				break;
			
			case 12:
			
				outputString = doINVERSE_PARAMETERS(header, id, action
															, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseINVERSE_PARAMETERSString(inputString, frame);
			
				break;
				
			case 13:

				outputString = doGENERATE_PARAMETER_FORMAT(header, id, action
														, user, pw, body);
				inputString = transmitCGIString(outputString);
				flagArray = parseGENERATE_PARAMETER_FORMATString(inputString, frame);
			
				break;
				
			case 14:
			
				outputString = doLOGOUT(header, id, action, user, pw);
				inputString = transmitCGIString(outputString);
				flagArray = parseLOGOUTString(inputString, frame);
			
				break;
				
			case 15:

				outputString = doGET_RATE_LIBRARY_LIST(header, id, action, user, pw, group);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_RATE_LIBRARY_LISTString(inputString, frame);
			
				break;
			
			case 16:

				outputString = doGET_RATE_LIBRARY_ISOTOPES(header, id, action, user, pw, library);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_RATE_LIBRARY_ISOTOPESString(inputString, frame);
			
				break;
			
			case 17:

				outputString = doGET_RATE_LIST(header, id, action, user, pw, library, isotope, typeDatabase);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_RATE_LISTString(inputString, frame);
			
				break;
				
			case 18:
				
				outputString = doMODIFY_RATE_LIBRARY(header, id, action, user, pw, src_lib, dest_lib, dest_group
														, chk_temp_behavior, chk_overflow, chk_inverse, del_src_lib);
				inputString = transmitCGIString(outputString);
				flagArray = parseMODIFY_RATE_LIBRARYString(inputString, frame);
			
				break;
				
			case 19:

				outputString = doMODIFY_RATES(header, id, action, user, pw, rates, dest_lib
													, chk_temp_behavior, chk_overflow, chk_inverse
													, properties, make_inverse);								
				inputString = transmitCGIString(outputString);
				flagArray = parseMODIFY_RATESString(inputString, frame);
			
				break;
			
			case 20:

				outputString = doGET_RATE_LIBRARY_INFO(header, id, action, user, pw, library);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_RATE_LIBRARY_INFOString(inputString, frame);
			
				break;
			
			case 21:

				outputString = doGET_RATE_INFO(header, id, action, user, pw, rates, properties);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_RATE_INFOString(inputString, frame);
		
				break;
			
			case 22:

				outputString = doREGISTER(header, id, action, user, pw, firstname, lastname
												, email, institution, address, research
												, hear_of_suite, info);
				inputString = transmitCGIString(outputString);
				flagArray = parseREGISTERString(inputString, frame);
			
				break;
				
			case 23:

				outputString = doRATES_EXIST(header, id, action, user, pw, rates);
				inputString = transmitCGIString(outputString);
				flagArray = parseRATES_EXISTString(inputString, frame);
				break;
				
			case 24:

				outputString = doADD_MISSING_INV_RATES(header, id, action, user, pw, library);
				inputString = transmitCGIString(outputString);
				flagArray = parseADD_MISSING_INV_RATESString(inputString, frame);
			
				break;
			
			case 25:

				outputString = doGET_INVERSE_REACTION(header, id, action, user, pw, reaction
														, reaction_type);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_INVERSE_REACTIONString(inputString, frame);
			
				break;
			
			case 26:

				outputString = doSHARE_RATE_LIBRARY(header, id, action, user, pw, library);
				inputString = transmitCGIString(outputString);
				flagArray = parseSHARE_RATE_LIBRARYString(inputString, frame);
			
				break;
				
			case 27:

				outputString = doGET_ELEMENT_SYNTHESIS_TIME_MAPPING(header, id, action, user, pw, path, zones);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_ELEMENT_SYNTHESIS_TIME_MAPPINGString(inputString, frame);
				break;
				
			case 28:

				outputString = doGET_ELEMENT_SYNTHESIS_ISOTOPE_MAPPING(header, id, action, user, pw, path);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_ELEMENT_SYNTHESIS_ISOTOPE_MAPPINGString(inputString, frame);
				break;
			
			case 29:

				outputString = doGET_ELEMENT_SYNTHESIS_THERMO_PROFILE(header, id, action, user, pw, path, zones);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_ELEMENT_SYNTHESIS_THERMO_PROFILEString(inputString, frame);
				break;
				
			case 30:
				
				outputString = doGET_ELEMENT_SYNTHESIS_ABUNDANCES(header, id, action, user, pw, path, zone, isotopes, final_step);
				transmissionObject = transmitCGIObject(outputString, dialog);
				flagArray = parseGET_ELEMENT_SYNTHESIS_ABUNDANCESString(transmissionObject, frame, final_step, dialog);
			
				break;
				
			case 31:

				outputString = doGET_NUC_DATA_SET_LIST(header, id, action, user, pw, group);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_NUC_DATA_SET_LISTString(inputString, frame);
			
				break;
				
			case 32:

				outputString = doGET_NUC_DATA_SET_ISOTOPES(header, id, action, user, pw, set);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_NUC_DATA_SET_ISOTOPESString(inputString, frame);
			
				break;
				
			case 33:

				outputString = doGET_NUC_DATA_LIST(header, id, action, user, pw, set, isotope, type);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_NUC_DATA_LISTString(inputString, frame);
			
				break;
				
			case 34:

				outputString = doGET_NUC_DATA_INFO(header, id, action, user, pw, nucData, properties);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_NUC_DATA_INFOString(inputString, frame);
			
				break;
				
			case 35:
			
				outputString = doPARSE_NUC_DATA_FILE(header, id, action, user, pw
										, reaction, reaction_type, notes, type, format, xunits, yunits, filename, file);						
				inputString = transmitCGIString(outputString);
				flagArray = parsePARSE_NUC_DATA_FILEString(inputString, frame);
			
				break;
				
			case 36:
				
				outputString = doMODIFY_NUC_DATA_SET(header, id, action, user, pw, src_set, dest_set, dest_group, del_src_set);
				inputString = transmitCGIString(outputString);
				flagArray = parseMODIFY_NUC_DATA_SETString(inputString, frame);
			
				break;	
				
			case 37:

				outputString = doMODIFY_NUC_DATA(header, id, action, user, pw, nucData, dest_set, del_nuc_data, properties);
				inputString = transmitCGIString(outputString);
				flagArray = parseMODIFY_NUC_DATAString(inputString, frame);
			
				break;
				
			case 38:

				outputString = doNUC_DATA_EXIST(header, id, action, user, pw, nucData);
				inputString = transmitCGIString(outputString);
				flagArray = parseNUC_DATA_EXISTString(inputString, frame);
			
				break;
				
			case 39:

				outputString = doSHARE_NUC_DATA_SET(header, id, action, user, pw, set);
				inputString = transmitCGIString(outputString);
				flagArray = parseSHARE_NUC_DATA_SETString(inputString, frame);
			
				break;
				
			case 40:

				outputString = doEXPORT_RATE_LIBRARY(header, id, action, user, pw, library, format);
				inputString = transmitCGIString(outputString);
				flagArray = parseEXPORT_RATE_LIBRARYString(inputString, frame);
			
				break;
				
			case 41:

				outputString = doMAKE_ELEMENT_SYNTHESIS_MOVIE(header, id, action, user, pw, arguments, simulation, comp_factor, scale_factor, frame_skip_interval);
				inputString = transmitCGIString(outputString);
				flagArray = parseMAKE_ELEMENT_SYNTHESIS_MOVIEString(inputString, frame);
			
				break;
				
			case 42:

				outputString = doGET_ELEMENT_SYNTHESIS_FLUX_MAPPING(header, id, action, user, pw, path);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_ELEMENT_SYNTHESIS_FLUX_MAPPINGString(inputString, frame);
			
				break;
				
			case 43:
				
				outputString = doGET_ELEMENT_SYNTHESIS_FLUXES(header, id, action, user, pw, path, zone, reactions, sum);
				transmissionObject = transmitCGIObject(outputString, dialog);
				flagArray = parseGET_ELEMENT_SYNTHESIS_FLUXESString(transmissionObject, frame, dialog);
			
				break;
				
			case 44:
				
				outputString = doGET_ELEMENT_SYNTHESIS_WEIGHTED_ABUNDANCES(header, id, action, user, pw, path, isotopes);
				inputString = transmitCGIString(outputString);
				flagArray = parseGET_ELEMENT_SYNTHESIS_WEIGHTED_ABUNDANCESString(inputString, frame);
			
				break;
			
		}
		
		if(!action.equals("GET+ELEMENT+SYNTHESIS+ABUNDANCES") 
				//&& !action.equals("GET+RATE+LIBRARY+ISOTOPES")
				&& !action.equals("GET+ELEMENT+SYNTHESIS+FLUXES")
				&& !action.equals("GET+ELEMENT+SYNTHESIS+ISOTOPE+MAPPING")
				&& !action.equals("GET+ELEMENT+SYNTHESIS+FLUX+MAPPING")
				&& !action.equals("GET+ELEMENT+SYNTHESIS+TIME+MAPPING")
				&& !action.equals("GET+ELEMENT+SYNTHESIS+THERMO+PROFILE")
				){
			printCGIIO(outputString, inputString, action);
		}else{
			printCGIIO(outputString, inputString, action);
		}
		
		if((action.equals("GET+ELEMENT+SYNTHESIS+ABUNDANCES") && transmissionObject==null)
			|| (action.equals("GET+ELEMENT+SYNTHESIS+FLUXES") && transmissionObject==null)){

			if(inputString.equals("") && !action.equals("GET+RATE+LIST")){
			
				flagArray[0] = true;
				
				Vector tempViktor = new Vector();
				
				String stringError = "ERROR= The server is not responding. Please check your internet connection. Then close and reload the suite.";
				
				tempViktor.addElement(stringError);
				tempViktor.trimToSize();
	
				createErrorDialog(tempViktor, frame);
			
			}
		
		}
		
		//Return flag array to feature calling this action
		return flagArray;
		
	}
	
	public void printCGIIO(String out, String in, String action){
		try{
			System.out.println("***************************************");
			System.out.println(URLDecoder.decode(out, "UTF-8"));
			System.out.println(in.trim());
			System.out.println("**************************************");
		}catch(Exception e){
			
		}
		
	}
	
	public Object transmitCGIObject(String string, ProgressBarDialog dialog){
		
		//Initialize totalInputString var to hold full output string 
		String totalInputString = "";
		
		Object object = null;
		
		try{

			URL url = null;
			
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
			
				url = new URL("https://nucastrodata2.ornl.gov/cgi-bin/cinadev");
			
			}else if(Cina.cinaMainDataStructure.getURLType().equals("NONDEV")){
			
				url = new URL("https://nucastrodata2.ornl.gov/cgi-bin/cina");
			
			}
				
			//Open URL Connection
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
		
			//Set propery
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
	
			//Set URLConnection to send output from CGI
			urlConnection.setDoOutput(true);
	
			//Create printWriter object to send encoded string to CGI
			PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
	
			//Send string to CGI
			printWriter.print(string);
			
			//Close printWriter	
			printWriter.close();
	
			//Create input stream to get output from server
			InputStream inputStream = urlConnection.getInputStream();

			int[] intArray = new int[4];

			for(int i=0; i<4; i++){
			
				intArray[i] = inputStream.read();
		
			}

			byte[] byteArray = new byte[4];

			for(int i=0; i<4; i++){
			
				byteArray[i] = new Integer(intArray[i]).byteValue();
			
			}

			int numberBytes = getNumberBytes(intArray);
			
			if(isotopes.equals("") && dialog!=null){

				dialog.setMaximum(numberBytes);

			}

			if(!new String(byteArray).equals("ERRO")){
	
				File file = File.createTempFile("test", ".zip");
				
				file.deleteOnExit();
	
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				
				if(isotopes.equals("") && dialog!=null){
					
					dialog.setMaximum((int)(numberBytes/5));
				
				}
				
				synchronized(inputStream){
				
					synchronized(fileOutputStream){
						
						byte[] buffer = new byte[256];
						
						int totalBytesRead = 0;
						
						while(true){
						
							int bytesRead = inputStream.read(buffer);
							
							totalBytesRead += bytesRead;
							
							if(isotopes.equals("") && dialog!=null){
								
								dialog.setCurrentValue(totalBytesRead);
							
							}
							
							if(bytesRead==-1){break;}
							
							fileOutputStream.write(buffer, 0, bytesRead);

						}
				
					}
				
				}

				if(isotopes.equals("") && dialog!=null){

					dialog.appendText("DONE!\n");
					
				}

				inputStream.close();
				fileOutputStream.close();
			
				
				ZipFile zipFile = new ZipFile(file);
				
				Enumeration e = zipFile.entries();
				
				ZipEntry zipEntry = (ZipEntry)(e.nextElement());

				InputStream is = zipFile.getInputStream(zipEntry);
				
				object = is;

			}else{
			
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
				IOUtilities.readStream(inputStream, baos);
				
				totalInputString = "ERRO" + new String(baos.toByteArray());
	
				inputStream.close();
				baos.close();	
				
				object = totalInputString;
			
			}
		
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR=An error has occurred connecting to our web server. "
			+ "Please check your internet connection and restart this software.";
		}
		
		return object;

	}
	
	public String transmitCGIString(String string){
		
		//Initialize totalInputString var to hold full output string 
		String totalInputString = "";
		
		try{

			URL url = null;
			
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				
				url = new URL("https://nucastrodata2.ornl.gov/cgi-bin/cinadev");
			
			}else if(Cina.cinaMainDataStructure.getURLType().equals("NONDEV")){
			
				url = new URL("https://nucastrodata2.ornl.gov/cgi-bin/cina");
			
			}
				
			//Open URL Connection
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
		
			//Set propery
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
	
			//Set URLConnection to send output from CGI
			urlConnection.setDoOutput(true);
	
			//Create printWriter object to send encoded string to CGI
			PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
	
			//Send string to CGI
			printWriter.print(string);
			
			//Close printWriter	
			printWriter.close();
	
			//Create input stream to get output from server
			InputStream inputStream = urlConnection.getInputStream();

			if(action.equals("EXPORT RATE LIBRARY")){
	
				int[] intArray = new int[4];
	
				for(int i=0; i<4; i++){
				
					intArray[i] = inputStream.read();
				
				}
				inputStream.read();
				int numberBytes = getNumberBytes(intArray);

				byte[] byteArray = new byte[4];
	
				for(int i=0; i<4; i++){
				
					byteArray[i] = new Integer(intArray[i]).byteValue();
				
				}
	
				if(!new String(byteArray).equals("ERRO")){
					
     				Cina.rateLibManFrame.rateLibManExportLibPanel.progressBar.setMaximum(numberBytes);
					Cina.rateLibManFrame.rateLibManExportLibPanel.progressBarTextArea.setText("File is now downloading. DO NOT operate the Rate Library Manager at this time!");
					Cina.rateLibManFrame.rateLibManExportLibPanel.progressBar.setVisible(true);
					Cina.rateLibManFrame.rateLibManExportLibPanel.progressBarDialog.validate();
					
					File file = new File(Cina.rateLibManDataStructure.getExportFilename());
					
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					
					synchronized(inputStream){
					
						synchronized(fileOutputStream){
					
							byte[] buffer = new byte[256];
							
							int totalBytesRead = 0;
							
							while(true){
							
								int bytesRead = inputStream.read(buffer);
								
								totalBytesRead += bytesRead;

								Cina.rateLibManFrame.rateLibManExportLibPanel.progressBar.setValue(totalBytesRead);
								
								if(bytesRead==-1){break;}
	
								fileOutputStream.write(buffer, 0, bytesRead);
					
								
							
							}
					
						}
					
					}
					
					inputStream.close();
					fileOutputStream.close();
					
					totalInputString = new String("dummy string");
				
				}else{
				
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
					IOUtilities.readStream(inputStream, baos);
					
					totalInputString = "ERRO" + new String(baos.toByteArray());
					
					inputStream.close();
					baos.close();
				
				}

			}else{
	
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				IOUtilities.readStream(inputStream, baos);
				
				totalInputString = new String(baos.toByteArray());
				
				baos.close();
			
			}
		
		
		}catch(IOException ioe){
		
			ioe.printStackTrace();
			
			if(action.equals("EXPORT RATE LIBRARY")){
				
				String newString = "An error has occurred saving zipped library. Please try again or contact coordinator@nucastrodata.org.";
				
			 	Dialogs.createExceptionDialog(newString, Cina.rateLibManFrame);
				
			}

		}catch(Exception e){
			
			e.printStackTrace();
			
			//Dialogs.createExceptionDialog(e.printStackTrace(), Cina.passwordDialog);
			
			return "ERROR=An error has occurred connecting to our web server. "
			+ "Please check your internet connection and restart this software.";
		}
		
		//Return string to parse
		return totalInputString;
	}

	public int getNumberBytes(int[] tempArray){
	
		int temp = 0;
		
		temp = tempArray[0]
				+ (int)(Math.pow(2, 8)*tempArray[1])
				+ (int)(Math.pow(2, 16)*tempArray[2])
				+ (int)(Math.pow(2, 24)*tempArray[3]);
		
		return temp;
	
	}

	public int getActionID(String string, String[] actionArray){
	
		//Loop over action array elements
		for(int i=0; i<numberActions; i++){
		
			//If action sent to CGIComm matched action array element
			if(string.equals(actionArray[i])){
			
				//Then the actionID is the index of that action array element
				actionID = i;
			
			}
		
		}
	
		//Return action ID
		return actionID;
	
	}
	
	public String getEncryptedString(String string){
	
		String encryptedString = "";
		
		try{
	
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			sha.update(string.getBytes());
	
			byte[] byteArray = sha.digest();
			
			for(int i=0; i<byteArray.length; i++){
				
				int temp = Integer.valueOf(Byte.toString(byteArray[i])).intValue();
				
				if(temp<0){
				
					if(String.valueOf(Integer.toHexString(temp + 256)).length()==1){
				
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp + 256));
					
					}else{
						
						encryptedString += String.valueOf(Integer.toHexString(temp + 256));
					
					}
				
				}else{
				
					if(String.valueOf(Integer.toHexString(temp)).length()==1){
				
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp));
					
					}else{
						
						encryptedString += String.valueOf(Integer.toHexString(temp));
					
					}
				
				}
			
			}
		
		}catch(NoSuchAlgorithmException nsae){
		
			nsae.printStackTrace();
			
		}

		return encryptedString;
	
	}
	
	public boolean[] parseGET_IDString(String string, Frame frame){
	
		//Clear vectors for messages
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		//Create flagArray temp array
		boolean[] booleanArray = new boolean[3];
	
		//Create tokenizer on new line char
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		//Initialize current token string
		String currentToken = "";
	
		//get number of tokens 
		int numberTokens = st.countTokens();
		
		//Loop over number of tokens
		for(int i=0; i<numberTokens; i++){
			
			//Assign new token to current token
			currentToken = st.nextToken();
			
			//If current token starts with "ERROR="
			if(currentToken.startsWith("ERROR=")){
			
				//Add error message to error vector
				errorVector.addElement(currentToken);
			
			//If current token does not start with "ERROR="
			}else{
				
				//If current token atrts with "CAUTION="
				if(currentToken.startsWith("CAUTION=")){
		
					//Add caution message to caution vector
					cautionVector.addElement(currentToken);
		
				//If current token starts with "ID="
				}else if(currentToken.startsWith("ID=")){
			
					//Get id
					id = currentToken.substring(3);
			
				}
			}	
		}
		
		//Trim away any empty object from vectors 
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		//If you have an error
		if(!errorVector.isEmpty()){
		
			//Open the error dialog over parent frame
			createErrorDialog(errorVector, frame);
			
			//Assign first element of boolean array (ie flag array) to true
			booleanArray[0] = true;
		
		//If no errors
		}else{
		
			//If you have a caution
			if(!cautionVector.isEmpty()){
			
				//Open th ecaution dialog over parent frame
				createCautionDialog(cautionVector, frame);
				
				//Assign second element of boolean array (ie flag array) to true
				booleanArray[1] = true;
			
			}
			
			//Set ID in main data structure
			Cina.cinaMainDataStructure.setID(id);
		}
		
		//Return flag array
		return booleanArray;
	}
	
	public boolean[] parseCHECK_REACTIONString(String string, Frame frame){
	
		//Clear vectors for messages
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		//Create flagArray temp array
		boolean[] booleanArray = new boolean[3];
	
		//Create tokenizer on new line char
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		//Initialize current token string
		String currentToken = "";
	
		//get number of tokens 
		int numberTokens = st.countTokens();
		
		//Loop over number of tokens
		for(int i=0; i<numberTokens; i++){
			
			//Assign new token to current token
			currentToken = st.nextToken();
			
			//If current token starts with "ERROR="
			if(currentToken.startsWith("ERROR=")){
			
				//Add error message to error vector
				errorVector.addElement(currentToken);
			
			//If current token does not start with "ERROR="
			}else{
				
				//If current token starts with "CAUTION="
				if(currentToken.startsWith("CAUTION=")){
		
					//Add caution message to caution vector
					cautionVector.addElement(currentToken);
		
				//If current token starts with "REACTION="
				}else if(currentToken.startsWith("REACTION=")){
			
					/*if(frame instanceof EvalComFrame){
					
						Cina.evalComDataStructure.getRateDataStructure().setReactionString(currentToken.substring(9));
					
					}else if(frame instanceof EvalComFrame){
						
						Cina.evalManDataStructure.getRateDataStructure().setReactionString(currentToken.substring(9));
						
				
					//If parent frame is Rate Gen
					}else */if(frame instanceof RateGenFrame
						|| frame instanceof RateGenChartFrame){
			
						//Assign reaction string to Rate Data Structure of the Rate Gen Data Structure
						Cina.rateGenDataStructure.getRateDataStructure().setReactionString(currentToken.substring(9));
					
					//If parent frame is Rate man
					}else if(frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex!=6){
					
						//Assign reaction string to Create Rate Data Structure of the Rate Man Data Structure
						Cina.rateManDataStructure.getCreateRateDataStructure().setReactionString(currentToken.substring(9));

					}else if(frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==6){
					
						//Assign reaction string to Create Rate Data Structure of the Rate Man Data Structure
						Cina.rateManDataStructure.getInvestRateDataStructure().setReactionString(currentToken.substring(9));

					}else if(frame instanceof DataManFrame){
					
						//Assign reaction string to Create Rate Data Structure of the Rate Man Data Structure
						Cina.dataManDataStructure.getImportNucDataDataStructure().setReactionString(currentToken.substring(9));

					}else if(frame instanceof RateParamFrame
								|| frame instanceof RateParamChartFrame){
					
						Cina.rateParamDataStructure.getRateDataStructure().setReactionString(currentToken.substring(9));

					}
				
				}else if(currentToken.startsWith("REACTION_INT=")){
					
					if(frame instanceof RateGenChartFrame){
			
						//Assign reaction string to Rate Data Structure of the Rate Gen Data Structure
						Cina.rateGenDataStructure.getRateDataStructure().setReactionStringInt(currentToken.substring(13));
					
					}else if(frame instanceof RateParamChartFrame){
					
						Cina.rateParamDataStructure.getRateDataStructure().setReactionStringInt(currentToken.substring(13));
					
					}
					
				}else if(currentToken.startsWith("VALID=")){
					
					boolean isValid = false;
					
					if(currentToken.substring(6).equals("N")){
						isValid = false;
					}else if(currentToken.substring(6).equals("Y")){
						isValid = true;
					}
					
					/*if(frame instanceof EvalComFrame){
						Cina.evalComDataStructure.setReactionValid(isValid);
					}else if(frame instanceof EvalManFrame){
						Cina.evalManDataStructure.setReactionValid(isValid);
					}*/
					
				}else if(currentToken.startsWith("REASON=")){
				
					//Add reasons to vector
					reasonVector.addElement("Reaction is invalid.");
					reasonVector.addElement(currentToken.substring(7));	
				
				}
			}	
		}
		
		//Trim away any empty object from vectors
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		//If you have an error
		if(!errorVector.isEmpty()){
		
			//Open the error dialog over parent frame
			createErrorDialog(errorVector, frame);
			
			//Assign first element of boolean array (ie flag array) to true
			booleanArray[0] = true;
		
		//If you have a reason
		}else if(!reasonVector.isEmpty()){
			
			//Open a reason dialog over parent frame
			createReasonDialog(reasonVector, frame);
			
			//Assign third element of boolean array (ie flag array) to true
			booleanArray[2] = true;
		
		//If you do not have a reason or an error
		}else{
			
			//If you have a caution
			if(!cautionVector.isEmpty()){
			
				//Open caution dialog over parent frame
				createCautionDialog(cautionVector, frame);
				
				//Assign second element of boolean array (ie flag array) to true
				booleanArray[1] = true;
			
			}
			
		}
		
		//Return flag array
		return booleanArray;
	}

	public boolean[] parseREAD_INPUTString(String string, Frame frame){
	
		//Clear vectors for messages
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();

		//Create flagArray temp array
		boolean[] booleanArray = new boolean[3];
	
		//Create tokenizer on new line char
		StringTokenizer st = new StringTokenizer(string, "\n");

		//Initialize current token string
		String currentToken = "";
	
		//get number of tokens
		int numberTokens = st.countTokens();
		
		//Loop over number of tokens
		for(int i=0; i<numberTokens; i++){
			
			//Assign new token to current token
			currentToken = st.nextToken();
			
			//If current token starts with "ERROR="
			if(currentToken.startsWith("ERROR=")){
			
				//Add error message to error vector
				errorVector.addElement(currentToken);
			
			//If current token does not start with "ERROR="
			}else{
				
				//If current token starts with "CAUTION="
				if(currentToken.startsWith("CAUTION=")){
		
					//Add caution message to caution vector
					cautionVector.addElement(currentToken);
		
				//If current token starts with "POINTS="
				}else if(currentToken.startsWith("POINTS=")){
					
					if(frame instanceof RateGenFrame){
						
						Cina.rateGenDataStructure.setNumberPoints((int)Double.valueOf(currentToken.substring(7)).doubleValue());
					
					}else if(frame instanceof RateParamFrame
								|| frame instanceof RateParamPointFrame){
					
						Cina.rateParamDataStructure.setNumberPoints((int)Double.valueOf(currentToken.substring(7)).doubleValue());
					
					}

				//If current token starts with "DATA="
				}else if(currentToken.startsWith("DATA=")){	
				
					if(frame instanceof RateGenFrame){
				
						StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
					
						String currentToken2 = "";
					
						double[] inputDataArray = new double[Cina.rateGenDataStructure.getNumberPoints()];
						double[] inputErrorDataArray = new double[Cina.rateGenDataStructure.getNumberPoints()];
						
						double[] outputDataArray = new double[Cina.rateGenDataStructure.getNumberPoints()];
						double[] outputErrorDataArray = new double[Cina.rateGenDataStructure.getNumberPoints()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberPoints(); j++){
							
							currentToken2 = st09.nextToken();
							
							StringTokenizer tempST = new StringTokenizer(currentToken2);
							
							inputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							inputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							outputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							outputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
						}
						
						if(Cina.rateGenDataStructure.getInputType().equals("CS(E)")){
						
							if(Cina.rateGenDataStructure.getInputFormat().equals("1,0,2,0")
									|| Cina.rateGenDataStructure.getInputFormat().equals("1,2,3,4")){
								
								Cina.rateGenDataStructure.setEnergyDataArray(inputDataArray);
								Cina.rateGenDataStructure.setEnergyErrorDataArray(inputErrorDataArray);
							
								Cina.rateGenDataStructure.setCrossSectionDataArray(outputDataArray);
								Cina.rateGenDataStructure.setCrossSectionErrorDataArray(outputErrorDataArray);
							
							}else if(Cina.rateGenDataStructure.getInputFormat().equals("2,0,1,0")
									|| Cina.rateGenDataStructure.getInputFormat().equals("3,4,1,2")){
							
								Cina.rateGenDataStructure.setEnergyDataArray(outputDataArray);
								Cina.rateGenDataStructure.setEnergyErrorDataArray(outputErrorDataArray);
							
								Cina.rateGenDataStructure.setCrossSectionDataArray(inputDataArray);
								Cina.rateGenDataStructure.setCrossSectionErrorDataArray(inputErrorDataArray);
							
							}
							
						}else if(Cina.rateGenDataStructure.getInputType().equals("S(E)")){
						
							if(Cina.rateGenDataStructure.getInputFormat().equals("1,0,2,0")
									|| Cina.rateGenDataStructure.getInputFormat().equals("1,2,3,4")){
								
								Cina.rateGenDataStructure.setEnergyDataArray(inputDataArray);
								Cina.rateGenDataStructure.setEnergyErrorDataArray(inputErrorDataArray);
							
								Cina.rateGenDataStructure.setSFactorDataArray(outputDataArray);
								Cina.rateGenDataStructure.setSFactorErrorDataArray(outputErrorDataArray);
							
							}else if(Cina.rateGenDataStructure.getInputFormat().equals("2,0,1,0")
									|| Cina.rateGenDataStructure.getInputFormat().equals("3,4,1,2")){
							
								Cina.rateGenDataStructure.setEnergyDataArray(outputDataArray);
								Cina.rateGenDataStructure.setEnergyErrorDataArray(outputErrorDataArray);
							
								Cina.rateGenDataStructure.setSFactorDataArray(inputDataArray);
								Cina.rateGenDataStructure.setSFactorErrorDataArray(inputErrorDataArray);
							
							}
							
						}
							
					}else if(frame instanceof RateParamFrame
								|| frame instanceof RateParamPointFrame){
							
						StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
					
						String currentToken2 = "";
					
						double[] inputDataArray = new double[Cina.rateParamDataStructure.getNumberPoints()];
						double[] inputErrorDataArray = new double[Cina.rateParamDataStructure.getNumberPoints()];
						
						double[] outputDataArray = new double[Cina.rateParamDataStructure.getNumberPoints()];
						double[] outputErrorDataArray = new double[Cina.rateParamDataStructure.getNumberPoints()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberPoints(); j++){
							
							currentToken2 = st09.nextToken();
							
							StringTokenizer tempST = new StringTokenizer(currentToken2);
							
							inputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							inputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							outputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
							outputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
							
						}
					
						if(Cina.rateParamDataStructure.getInputType().equals("R(T)")){
							
							if(Cina.rateParamDataStructure.getInputFormat().equals("1,0,2,0")
									|| Cina.rateParamDataStructure.getInputFormat().equals("1,2,3,4")){
	
								Cina.rateParamDataStructure.setTempDataArray(inputDataArray);
								Cina.rateParamDataStructure.setRateDataArray(outputDataArray);
						
								if(Cina.rateParamFrame.currentPanelIndex==2){
								
									Cina.rateParamDataStructure.setTempDataArrayOrig(inputDataArray);
									Cina.rateParamDataStructure.setRateDataArrayOrig(outputDataArray);
								
								}
						
							}else if(Cina.rateParamDataStructure.getInputFormat().equals("2,0,1,0")
									|| Cina.rateParamDataStructure.getInputFormat().equals("3,4,1,2")){
							
								Cina.rateParamDataStructure.setTempDataArray(outputDataArray);
								Cina.rateParamDataStructure.setRateDataArray(inputDataArray);
	
								if(Cina.rateParamFrame.currentPanelIndex==2){
								
									Cina.rateParamDataStructure.setTempDataArrayOrig(outputDataArray);
									Cina.rateParamDataStructure.setRateDataArrayOrig(inputDataArray);
								
								}
							}
							
						}
							
					}
									
				}else if(currentToken.startsWith("TEMP_MIN=")){
					
					Cina.rateParamDataStructure.setTempminRT(Double.valueOf(currentToken.substring(9)).doubleValue());

				}else if(currentToken.startsWith("TEMP_MAX=")){
					
					Cina.rateParamDataStructure.setTempmaxRT(Double.valueOf(currentToken.substring(9)).doubleValue());
					
				}else if(currentToken.startsWith("TEMP_ERROR_MIN=")){
					
					//Cina.rateParamDataStructure.setTempErrormin(Double.valueOf(currentToken.substring(12)).doubleValue());
					
				}else if(currentToken.startsWith("TEMP_ERROR_MAX=")){
					
					//Cina.rateParamDataStructure.setTempErrormax(Double.valueOf(currentToken.substring(12)).doubleValue());
				
				}else if(currentToken.startsWith("RATE_MIN=")){
					
					Cina.rateParamDataStructure.setRatemin(Double.valueOf(currentToken.substring(9)).doubleValue());
					
				}else if(currentToken.startsWith("RATE_MAX=")){
					
					Cina.rateParamDataStructure.setRatemax(Double.valueOf(currentToken.substring(9)).doubleValue());
					
				}else if(currentToken.startsWith("RATE_ERROR_MIN=")){
					
					//Cina.rateParamDataStructure.setRateErrormin(Double.valueOf(currentToken.substring(12)).doubleValue());
					
				}else if(currentToken.startsWith("RATE_ERROR_MAX=")){
					
					//Cina.rateParamDataStructure.setRateErrormax(Double.valueOf(currentToken.substring(12)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_MIN=")){
						
					Cina.rateGenDataStructure.setEmin(Double.valueOf(currentToken.substring(11)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_MAX=")){
					
					Cina.rateGenDataStructure.setEmax(Double.valueOf(currentToken.substring(11)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_ERROR_MIN=")){
					
					Cina.rateGenDataStructure.setEErrormin(Double.valueOf(currentToken.substring(17)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_ERROR_MAX=")){
					
					Cina.rateGenDataStructure.setEErrormax(Double.valueOf(currentToken.substring(17)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_MIN=")){
					
					Cina.rateGenDataStructure.setSFactormin(Double.valueOf(currentToken.substring(13)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_MAX=")){
					
					Cina.rateGenDataStructure.setSFactormax(Double.valueOf(currentToken.substring(13)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_ERROR_MIN=")){
					
					Cina.rateGenDataStructure.setSFactorErrormin(Double.valueOf(currentToken.substring(19)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_ERROR_MAX=")){
					
					Cina.rateGenDataStructure.setSFactorErrormin(Double.valueOf(currentToken.substring(19)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_MIN=")){
					
					Cina.rateGenDataStructure.setCrossSectionmin(Double.valueOf(currentToken.substring(18)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_MAX=")){
				
					Cina.rateGenDataStructure.setCrossSectionmax(Double.valueOf(currentToken.substring(18)).doubleValue());	
					
				}else if(currentToken.startsWith("CROSS_SECTION_ERROR_MIN=")){
					
					Cina.rateGenDataStructure.setCrossSectionErrormin(Double.valueOf(currentToken.substring(24)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_ERROR_MAX=")){
					
					Cina.rateGenDataStructure.setCrossSectionErrormin(Double.valueOf(currentToken.substring(24)).doubleValue());
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parsePREPROCESSINGString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		String[] inputWarningsResponseArray = {"SKIPPED", "SKIPPED", "SKIPPED", "SKIPPED", "SKIPPED"};
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("POSITIVE_CHK=")){
					
					if(currentToken.substring(13).equals("PASSED")){
					
						inputWarningsResponseArray[0] = "PASSED";
						
					}else if(currentToken.substring(13).equals("FAILED")){
					
						inputWarningsResponseArray[0] = "FAILED";
					
					}else if(currentToken.substring(13).equals("SKIPPED")){
					
						inputWarningsResponseArray[0] = "SKIPPED";
					
					}
					
				}else if(currentToken.startsWith("POSITIVE_REASON=")){
						
					reasonVector.addElement("Positive definite test failed.");
					reasonVector.addElement(currentToken.substring(16));
				
				}else if(currentToken.startsWith("SINGLE_CHK=")){
				
					if(currentToken.substring(11).equals("PASSED")){
					
						inputWarningsResponseArray[1] = "PASSED";
						
					}else if(currentToken.substring(11).equals("FAILED")){
					
						inputWarningsResponseArray[1] = "FAILED";
					
					}else if(currentToken.substring(11).equals("SKIPPED")){
					
						inputWarningsResponseArray[1] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("SINGLE_REASON=")){
				
					reasonVector.addElement("Single valued test failed.");
					reasonVector.addElement(currentToken.substring(14));
				
				}else if(currentToken.startsWith("CONTINUITY_CHK=")){
				
					if(currentToken.substring(15).equals("PASSED")){
					
						inputWarningsResponseArray[2] = "PASSED";
						
					}else if(currentToken.substring(15).equals("FAILED")){
					
						inputWarningsResponseArray[2] = "FAILED";
					
					}else if(currentToken.substring(15).equals("SKIPPED")){
					
						inputWarningsResponseArray[2] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("CONTINUITY_REASON=")){
				
					reasonVector.addElement("Continuous test failed.");
					reasonVector.addElement(currentToken.substring(18));
								
				}else if(currentToken.startsWith("ERROR_CHK=")){
			
					if(currentToken.substring(10).equals("PASSED")){
					
						inputWarningsResponseArray[4] = "PASSED";
						
					}else if(currentToken.substring(10).equals("FAILED")){
					
						inputWarningsResponseArray[4] = "FAILED";
					
					}else if(currentToken.substring(10).equals("SKIPPED")){
					
						inputWarningsResponseArray[4] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("ERROR_REASON=")){
				
					reasonVector.addElement("Large uncertainties test failed.");
					reasonVector.addElement(currentToken.substring(13));
				
				}else if(currentToken.startsWith("REACTION_CHK=")){
				
					//do nothing
				
				}else if(currentToken.startsWith("REACTION_REASON=")){
				
					reasonVector.addElement("Reaction test failed.");
					reasonVector.addElement(currentToken.substring(16));
					
				}else if(currentToken.startsWith("RANGE_CHK_ENERGY=")){
					
					if(currentToken.substring(17).equals("PASSED")
							&& !inputWarningsResponseArray[3].equals("FAILED")){
					
						inputWarningsResponseArray[3] = "PASSED";
						
					}else if(currentToken.substring(17).equals("FAILED")){
					
						inputWarningsResponseArray[3] = "FAILED";
					
					}else if(currentToken.substring(17).equals("SKIPPED")
							&& inputWarningsResponseArray[3].equals("SKIPPED")){
					
						inputWarningsResponseArray[3] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("RANGE_ENERGY_REASON=")){
				
					reasonVector.addElement("Energy range test failed.");
					reasonVector.addElement(currentToken.substring(20));
				
				}else if(currentToken.startsWith("RANGE_CHK_S_FACTOR=")){
				
					if(currentToken.substring(17).equals("PASSED")
							&& !inputWarningsResponseArray[3].equals("FAILED")){
					
						inputWarningsResponseArray[3] = "PASSED";
						
					}else if(currentToken.substring(17).equals("FAILED")){
					
						inputWarningsResponseArray[3] = "FAILED";
					
					}else if(currentToken.substring(17).equals("SKIPPED")
							&& inputWarningsResponseArray[3].equals("SKIPPED")){
					
						inputWarningsResponseArray[3] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("RANGE_S_FACTOR_REASON=")){
				
					reasonVector.addElement("S-factor range test failed.");
					reasonVector.addElement(currentToken.substring(22));
				
				}else if(currentToken.startsWith("RANGE_CHK_CROSS_SECTION=")){
				
					if(currentToken.substring(17).equals("PASSED")
							&& !inputWarningsResponseArray[3].equals("FAILED")){
					
						inputWarningsResponseArray[3] = "PASSED";
						
					}else if(currentToken.substring(17).equals("FAILED")){
					
						inputWarningsResponseArray[3] = "FAILED";
					
					}else if(currentToken.substring(17).equals("SKIPPED")
							&& inputWarningsResponseArray[3].equals("SKIPPED")){
					
						inputWarningsResponseArray[3] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("RANGE_CROSS_SECTION_REASON=")){
				
					reasonVector.addElement("Cross section range test failed.");
					reasonVector.addElement(currentToken.substring(27));
				
				}else if(currentToken.startsWith("RANGE_CHK_TEMP=")){
				
					if(currentToken.substring(17).equals("PASSED")
							&& !inputWarningsResponseArray[3].equals("FAILED")){
					
						inputWarningsResponseArray[3] = "PASSED";
						
					}else if(currentToken.substring(17).equals("FAILED")){
					
						inputWarningsResponseArray[3] = "FAILED";
					
					}else if(currentToken.substring(17).equals("SKIPPED")
							&& inputWarningsResponseArray[3].equals("SKIPPED")){
					
						inputWarningsResponseArray[3] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("RANGE_TEMP_REASON=")){
				
					reasonVector.addElement("Temperature range test failed.");
					reasonVector.addElement(currentToken.substring(18));
				
				}else if(currentToken.startsWith("RANGE_CHK_RATE=")){
				
					if(currentToken.substring(17).equals("PASSED")
							&& !inputWarningsResponseArray[3].equals("FAILED")){
					
						inputWarningsResponseArray[3] = "PASSED";
						
					}else if(currentToken.substring(17).equals("FAILED")){
					
						inputWarningsResponseArray[3] = "FAILED";
					
					}else if(currentToken.substring(17).equals("SKIPPED")
							&& inputWarningsResponseArray[3].equals("SKIPPED")){
					
						inputWarningsResponseArray[3] = "SKIPPED";
					
					}
				
				}else if(currentToken.startsWith("RANGE_RATE_REASON=")){
					
					reasonVector.addElement("Rate range test failed.");
					reasonVector.addElement(currentToken.substring(18));
				
				}else if(currentToken.startsWith("TMIN=")){
				
					if(frame instanceof RateGenFrame){
				
						Cina.rateGenDataStructure.setAllowedTempmin(Double.valueOf(currentToken.substring(5)).doubleValue());
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setAllowedTempmin(Double.valueOf(currentToken.substring(5)).doubleValue());

					}
				
				}else if(currentToken.startsWith("TMAX=")){
				
					if(frame instanceof RateGenFrame){
				
						Cina.rateGenDataStructure.setAllowedTempmax(Double.valueOf(currentToken.substring(5)).doubleValue());
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setAllowedTempmax(Double.valueOf(currentToken.substring(5)).doubleValue());

					}
				
				}	
			}	
		}
		
		if(frame instanceof RateGenFrame){
		
			Cina.rateGenDataStructure.setInputWarningsResponseArray(inputWarningsResponseArray);
		
		}else if(frame instanceof RateParamFrame){
		
			Cina.rateParamDataStructure.setInputWarningsResponseArray(inputWarningsResponseArray);
		
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGENERATE_RATEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("START=")){
				
				
				}else if(currentToken.startsWith("REASON=")){
					
					reasonVector.addElement("Rate generator failed to begin.");
					reasonVector.addElement(currentToken.substring(7));
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}

	public boolean[] parseABORT_RATE_GENERATIONString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("STOP=")){
				
				
				}else if(currentToken.startsWith("REASON=")){
					
					reasonVector.addElement("Rate generator failed to abort.");
					reasonVector.addElement(currentToken.substring(7));
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}
	
	public boolean[] parseRATE_GENERATION_UPDATEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String statusString = "";
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("TEXT_SKIPPED=")){
				
					if(currentToken.substring(13).equals("Y")){
						
						String skippedTextString = "...\n";
					
						Cina.rateGenDataStructure.setStatusText(Cina.rateGenDataStructure.getStatusText() + skippedTextString);
					
						Cina.rateGenFrame.rateGenStatusPanel.statusTextArea.append(skippedTextString);
					
					}
				
				}else if(currentToken.startsWith("GENERATION=")){
					
					if(currentToken.substring(11).equals("COMPLETE")){
						
						Cina.rateGenFrame.rateGenStatusPanel.statusLabel.setText("Status Report: Program Complete");
					
						if(Cina.rateGenFrame.rateGenStatusPanel.timer!=null){
					
							Cina.rateGenFrame.rateGenStatusPanel.timer.cancel();
						
						}
						
						Cina.rateGenFrame.continueButton.setEnabled(true);
						
						Cina.rateGenFrame.rateGenStatusPanel.abortButton.setEnabled(false);
					
					}else if(currentToken.substring(11).equals("RUNNING")){
						
						Cina.rateGenFrame.rateGenStatusPanel.statusLabel.setText("Status Report: Program Running");
						
					}
					
				}else if(currentToken.startsWith("TEXT=")){
					
					statusString = currentToken.substring(5);
					
					statusString = statusString.replaceAll("\u0008", "\n");
					
					Cina.rateGenDataStructure.setStatusText(Cina.rateGenDataStructure.getStatusText() + statusString);
					
					Cina.rateGenFrame.rateGenStatusPanel.statusTextArea.append(statusString);
					
					Cina.rateGenFrame.rateGenStatusPanel.statusTextArea.setCaretPosition(Cina.rateGenFrame.rateGenStatusPanel.statusTextArea.getText().length());									
				
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}
	
	public boolean[] parseRATE_GENERATION_OUTPUTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("T_MIN=")){
			
					Cina.rateGenDataStructure.setTempminRT(Double.valueOf(currentToken.substring(6)).doubleValue());
					
				}else if(currentToken.startsWith("T_MAX=")){
			
					Cina.rateGenDataStructure.setTempmaxRT(Double.valueOf(currentToken.substring(6)).doubleValue());
					
				}else if(currentToken.startsWith("R_MIN=")){
			
					Cina.rateGenDataStructure.setRatemin(Double.valueOf(currentToken.substring(6)).doubleValue());
					
				}else if(currentToken.startsWith("R_MAX=")){
			
					Cina.rateGenDataStructure.setRatemax(Double.valueOf(currentToken.substring(6)).doubleValue());
					
				}else if(currentToken.startsWith("DR_DT_MIN=")){
			
					Cina.rateGenDataStructure.setRateDerivmin(Double.valueOf(currentToken.substring(10)).doubleValue());
					
				}else if(currentToken.startsWith("DR_DT_MAX=")){
			
					Cina.rateGenDataStructure.setRateDerivmax(Double.valueOf(currentToken.substring(10)).doubleValue());
					
				}else if(currentToken.startsWith("INT_TECHNIQUE=")){
			
					Cina.rateGenDataStructure.setTechniqueRT(currentToken.substring(14));
					
				}else if(currentToken.startsWith("NUM_INTERPOLATED_POINTS=")){
			
					Cina.rateGenDataStructure.setNumberInterpolatedPointsRT((int)Double.valueOf(currentToken.substring(24)).doubleValue());
					
				}else if(currentToken.startsWith("POINTS=")){
			
					Cina.rateGenDataStructure.setNumberPointsRT((int)Double.valueOf(currentToken.substring(7)).doubleValue());
					
				}else if(currentToken.startsWith("DATA=")){
			
					StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
				
					String currentToken2 = "";
			
					double[] inputDataArray = new double[Cina.rateGenDataStructure.getNumberPointsRT()];
					double[] outputDataArray = new double[Cina.rateGenDataStructure.getNumberPointsRT()];
			
					for(int j=0; j<Cina.rateGenDataStructure.getNumberPointsRT(); j++){
						
						currentToken2 = st09.nextToken();
						
						StringTokenizer tempST = new StringTokenizer(currentToken2);
						
						inputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						outputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						
					}
					
					Cina.rateGenDataStructure.setTempDataArray(inputDataArray);
					Cina.rateGenDataStructure.setRateDataArray(outputDataArray);
	
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parsePARAMETERIZE_RATEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("START=")){
				
				
				}else if(currentToken.startsWith("REASON=")){
					
					reasonVector.addElement("Rate parameterization failed to begin.");
					reasonVector.addElement(currentToken.substring(7));
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}
	
	public boolean[] parseABORT_RATE_PARAMETERIZATIONString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("STOP=")){
				
				
				}else if(currentToken.startsWith("REASON=")){
					
					reasonVector.addElement("Rate parameterization failed to abort.");
					reasonVector.addElement(currentToken.substring(7));
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}

	public boolean[] parseRATE_PARAMETERIZATION_UPDATEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String statusString = "";
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("TEXT_SKIPPED=")){
				
					if(currentToken.substring(13).equals("Y")){
						
						String skippedTextString = "...\n";
					
						Cina.rateParamDataStructure.setStatusText(Cina.rateParamDataStructure.getStatusText() + skippedTextString);
					
						Cina.rateParamFrame.rateParamStatusPanel.statusTextArea.append(skippedTextString);
					
					}
				
				}else if(currentToken.startsWith("PARAMETERIZATION=")){
					
					if(currentToken.substring(17).equals("COMPLETE")){
						
						Cina.rateParamFrame.rateParamStatusPanel.statusLabel.setText("Status Report: Program Complete");
					
						if(Cina.rateParamFrame.rateParamStatusPanel.timer!=null){
					
							Cina.rateParamFrame.rateParamStatusPanel.timer.cancel();
						
						}
						
						Cina.rateParamFrame.continueButton.setEnabled(true);
						
						Cina.rateParamFrame.rateParamStatusPanel.abortButton.setEnabled(false);
					
					}else if(currentToken.substring(17).equals("RUNNING")){
						
						Cina.rateParamFrame.rateParamStatusPanel.statusLabel.setText("Status Report: Program Running");
						
					}
					
				}else if(currentToken.startsWith("TEXT=")){
					
					statusString = currentToken.substring(5);
					
					statusString = statusString.replaceAll("\u0008", "\n");
					
					Cina.rateParamDataStructure.setStatusText(Cina.rateParamDataStructure.getStatusText() + statusString);
					
					Cina.rateParamFrame.rateParamStatusPanel.statusTextArea.append(statusString);
					
					Cina.rateParamFrame.rateParamStatusPanel.statusTextArea.setCaretPosition(Cina.rateParamFrame.rateParamStatusPanel.statusTextArea.getText().length());									
													
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	
	}
	
	public boolean[] parseRATE_PARAMETERIZATION_OUTPUTString(String string, Frame frame){
	

		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		String outputString = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REASON=")){
					
					outputString = currentToken.substring(7);
				
					outputString = outputString.replaceAll("\u0008", "\n");
					
					reasonVector.addElement(outputString);
					
					Cina.rateParamDataStructure.setTempBehaviorString(outputString);
					
				}else if(currentToken.startsWith("PARAMETERS=")){
			
					Cina.rateParamDataStructure.getRateDataStructure().setNumberParameters((int)Double.valueOf(currentToken.substring(11)).doubleValue());
					
				}else if(currentToken.startsWith("DATA=")){
		
					Cina.rateParamDataStructure.setNumberPointsParam(Cina.rateParamDataStructure.getRateDataArrayOrig().length);
		
					StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
				
					String currentToken2 = "";

					double[] outputParamArray = new double[st09.countTokens()];
					
					String[] outputParamStringArray = new String[st09.countTokens()];
					
					double[] outputDataArray = new double[Cina.rateParamDataStructure.getNumberPointsParam()];
					
					double[] outputPercentDiffDataArray = new double[Cina.rateParamDataStructure.getNumberPointsParam()];
					
					for(int j=0; j<Cina.rateParamDataStructure.getRateDataStructure().getNumberParameters(); j++){
						
						currentToken2 = st09.nextToken();
			
						outputParamArray[j] = Double.valueOf(currentToken2).doubleValue();
						
						outputParamStringArray[j] = NumberFormats.getFormattedParameter(outputParamArray[j]);
						
					}
					
					Cina.rateParamDataStructure.getRateDataStructure().setParameters(outputParamArray);
					
					Cina.rateParamDataStructure.setParamStringArray(outputParamStringArray);
					
					for(int k=0; k<Cina.rateParamDataStructure.getNumberPointsParam(); k++){
					
						outputDataArray[k] = Cina.cinaMainDataStructure.calcRate(Cina.rateParamDataStructure.getTempDataArrayOrig()[k]
																								, Cina.rateParamDataStructure.getRateDataStructure().getParameters()
																								, Cina.rateParamDataStructure.getRateDataStructure().getNumberParameters());
						
						if(String.valueOf(outputDataArray[k]).equals("Infinity")){
			
							outputDataArray[k] = Double.MAX_VALUE;
						
						}
						
						if(outputDataArray[k]==0.0){
						
							outputDataArray[k] = 1.0E-100;
						
						}
						
						outputPercentDiffDataArray[k] = 100*((Cina.rateParamDataStructure.getRateDataArrayOrig()[k] - outputDataArray[k])/Cina.rateParamDataStructure.getRateDataArrayOrig()[k]);
						
					}
					
					Cina.rateParamDataStructure.setRateParamDataArray(outputDataArray);
					
					Cina.rateParamDataStructure.setTempParamDataArray(Cina.rateParamDataStructure.getTempDataArrayOrig());
					
					Cina.rateParamDataStructure.setPercentDiffDataArray(outputPercentDiffDataArray);
	
					Cina.rateParamDataStructure.getRateDataStructure().setChisquared(Cina.rateParamDataStructure.getOrigChisquared());
					
					Cina.rateParamDataStructure.getRateDataStructure().setMaxPercentDiff(Cina.rateParamDataStructure.getOrigMaxPercentDiff());
	
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
		
		}else if(reasonVector.isEmpty()){
		
			Cina.rateParamDataStructure.setTempBehaviorString("");
		
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}

	public boolean[] parseINVERSE_PARAMETERSString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("DATA=")){
				
					String[] outputStringArray = new String[35];
				
					double[] outputArray = new double[outputStringArray.length];
				
					StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
				
					String currentToken2 = "";
				
					for(int j=0; j<Cina.rateParamDataStructure.getRateDataStructure().getNumberParameters(); j++){
					
						currentToken2 = st09.nextToken();
			
						outputArray[j] = Double.valueOf(currentToken2).doubleValue();
						
						outputStringArray[j] = currentToken2; 
					
					}
					
					Cina.rateParamDataStructure.setInverseParamArray(outputArray);
					
					Cina.rateParamDataStructure.setInverseParamStringArray(outputStringArray);
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGENERATE_PARAMETER_FORMATString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";
	
		String outputString = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("TEXT=")){
				
					outputString = currentToken.substring(5);
				
					outputString = outputString.replaceAll("\u0008", "\n");
				
					Cina.rateParamFrame.rateParamResultsOutputFrame.setOutputTextAreaString(outputString);
				
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}

	public boolean[] parseLOGOUTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(timer!=null){
				
					timer.cancel();
				
				}
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_RATE_LIBRARY_LISTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
				
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("PUBLIC=")){
				
					StringTokenizer publicLibTokenizer = new StringTokenizer(currentToken.substring(7), "\u0009");
					
					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateLibManDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateLibManDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
				
						
						}
						
						Cina.rateLibManDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateViewerFrame){
					
						Cina.rateViewerDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateViewerDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateViewerDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
				
						}
						
						Cina.rateViewerDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateGenDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
		
						}
						
						Cina.rateGenDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateParamDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
		
						}
						
						Cina.rateParamDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateManFrame){
					
						Cina.rateManDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateManDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateManDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
			
						}
						
						Cina.rateManDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementSynthFrame){
					
						Cina.elementSynthDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementSynthDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementSynthDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
		
						}
						
						Cina.elementSynthDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementVizAnimatorFrame){
					
						Cina.elementVizDataStructure.setNumberPublicLibraryDataStructures(publicLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementVizDataStructure.getNumberPublicLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementVizDataStructure.getNumberPublicLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(publicLibTokenizer.nextToken());
		
						}
						
						Cina.elementVizDataStructure.setPublicLibraryDataStructureArray(outputArray);
					
					}
					
				}else if(currentToken.startsWith("SHARED=")){
				
					StringTokenizer sharedLibTokenizer = new StringTokenizer(currentToken.substring(7), "\u0009");
					
					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateLibManDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateLibManDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
		
						}
						
						Cina.rateLibManDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateViewerFrame){
					
						Cina.rateViewerDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateViewerDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateViewerDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
			
						}
						
						Cina.rateViewerDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateGenDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
			
						}
						
						Cina.rateGenDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateParamDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
			
						}
						
						Cina.rateParamDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateManFrame){
					
						Cina.rateManDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateManDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateManDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
			
						}
						
						Cina.rateManDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementSynthFrame){
					
						Cina.elementSynthDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementSynthDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementSynthDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
					
						}
						
						Cina.elementSynthDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementVizAnimatorFrame){
					
						Cina.elementVizDataStructure.setNumberSharedLibraryDataStructures(sharedLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementVizDataStructure.getNumberSharedLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementVizDataStructure.getNumberSharedLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(sharedLibTokenizer.nextToken());
					
						}
						
						Cina.elementVizDataStructure.setSharedLibraryDataStructureArray(outputArray);
					
					}
					
				}else if(currentToken.startsWith("USER=")){
				
					StringTokenizer userLibTokenizer = new StringTokenizer(currentToken.substring(5).trim(), "\u0009");

					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
		
							
						}
						
						Cina.rateLibManDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateViewerFrame){
					
						Cina.rateViewerDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateViewerDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateViewerDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
					
						}
						
						Cina.rateViewerDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateGenDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
				
						}
						
						Cina.rateGenDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateParamDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
				
						}
						
						Cina.rateParamDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof RateManFrame){
					
						Cina.rateManDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.rateManDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.rateManDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
				
						}
						
						Cina.rateManDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementSynthFrame){
					
						Cina.elementSynthDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementSynthDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementSynthDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
	
						}
						
						Cina.elementSynthDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}else if(frame instanceof ElementVizAnimatorFrame){
					
						Cina.elementVizDataStructure.setNumberUserLibraryDataStructures(userLibTokenizer.countTokens());
						
						LibraryDataStructure[] outputArray = new LibraryDataStructure[Cina.elementVizDataStructure.getNumberUserLibraryDataStructures()];
						
						for(int j=0; j<Cina.elementVizDataStructure.getNumberUserLibraryDataStructures(); j++){
	
							outputArray[j] = new LibraryDataStructure();
							
							outputArray[j].setLibName(userLibTokenizer.nextToken());
	
						}
						
						Cina.elementVizDataStructure.setUserLibraryDataStructureArray(outputArray);
					
					}
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_RATE_LIBRARY_ISOTOPESString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		String libraryName = URLDecoder.decode(library);
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith(libraryName + "=")){
				
					if(frame instanceof ElementSynthFrame){
					
						String[] array = string.split("\n"); 
						RateLibDataStructure rlds = null;
						TreeMap<Integer, ArrayList<IsotopePoint>> map = null;
					
						rlds = Cina.elementSynthDataStructure.getRateLibDataStructure();
						map = rlds.getIsotopeMapFull();
						String[] arrayTmp = array[i].split("=");
						String[] arrayIsotope = arrayTmp[1].split("\u0009");
						if(rlds!=null){
						
							for(int j=0; j<arrayIsotope.length; j++){
								String[] subsubarray = arrayIsotope[j].split(",");
								int z = Integer.valueOf(subsubarray[0]);
								int a = Integer.valueOf(subsubarray[1]);
								IsotopePoint ip = new IsotopePoint(z, a);
								if(map.get(z)==null){
									map.put(z, new ArrayList<IsotopePoint>());
								}
								map.get(z).add(ip);
							}
						}
					
					}else{
				
						StringTokenizer isotopeListTokenizer = new StringTokenizer(currentToken.substring(libraryName.length() + 1), "\u0009");
						
						String[] isotopePairArray = new String[isotopeListTokenizer.countTokens()];
						
						int[] tempZArray = new int[120];
						
						int currentZ = 0;
						
						int previousZ = 0;
						
						int ZCounter = 0;
						
						boolean firstTimeFlag = true;
						
						int numberZTokens = isotopeListTokenizer.countTokens();
	
						//Count the Z's for array initialization
						for(int j=0; j<numberZTokens; j++){
						
							isotopePairArray[j] = isotopeListTokenizer.nextToken();
							
							StringTokenizer isotopePairTokenizer = new StringTokenizer(isotopePairArray[j], ",");
							
							currentZ = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
							
							isotopePairTokenizer.nextToken();
							
							if(firstTimeFlag){
							
								tempZArray[ZCounter] = currentZ;
							
								ZCounter++;
							
								firstTimeFlag = false;
							
							}else if(currentZ!=previousZ){
							
								tempZArray[ZCounter] = currentZ;
							
								ZCounter++;
							
							}
							
							previousZ = currentZ;	
						}
						
						int[] outputZArray = new int[ZCounter];
						
						for(int j=0; j<ZCounter; j++){
						
							outputZArray[j] = tempZArray[j];
						
						}	
						
						if(frame instanceof RateLibManFrame){
						
							Cina.rateLibManDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof RateViewerFrame){
						
							Cina.rateViewerDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof RateManFrame){
						
							Cina.rateManDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof ElementVizFrame
									|| frame instanceof ElementVizAbundPlotFrame
									|| frame instanceof ElementVizAbundTableFrame
									|| frame instanceof ElementVizWeightPlotFrame
									|| frame instanceof ElementVizScalePlotFrame){
						
							Cina.elementVizDataStructure.getIsotopePadLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof DataViewerFrame){
						
							Cina.dataViewerDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof DataEvalFrame){
						
							Cina.dataEvalDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof RateGenFrame
									|| frame instanceof RateGenChartFrame){
						
							Cina.rateGenDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}else if(frame instanceof RateParamFrame
									|| frame instanceof RateParamChartFrame
									|| frame instanceof RateParamStartFrame){
						
							Cina.rateParamDataStructure.getCurrentLibraryDataStructure().setZList(outputZArray);
						
						}
						
						//Step2 
						
						int[][] tempIsotopeListArray = new int[ZCounter][200];
						 
						for(int j=0; j<ZCounter; j++){
						
							for(int k=0; k<200; k++){
							
								tempIsotopeListArray[j][k] = -1;
							
							}
						
						} 
						 
						isotopeListTokenizer = new StringTokenizer(currentToken.substring(libraryName.length() + 1), "\u0009");
						
						isotopePairArray = new String[isotopeListTokenizer.countTokens()];
						
						currentZ = 0;
						
						previousZ = 0;
						
						ZCounter = 0;
						
						firstTimeFlag = true;
						
						numberZTokens = isotopeListTokenizer.countTokens();
						
						int k = 0;
						
						//Count the Z's for array initialization
						for(int j=0; j<numberZTokens; j++){
						
							isotopePairArray[j] = isotopeListTokenizer.nextToken();
							
							StringTokenizer isotopePairTokenizer = new StringTokenizer(isotopePairArray[j], ",");
							
							currentZ = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
							
							if(firstTimeFlag){
							
								firstTimeFlag = false;
							
							}else if(currentZ!=previousZ){
							
								ZCounter++;
								
								k=0;
							
							}
							
							tempIsotopeListArray[ZCounter][k] = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
							
							previousZ = currentZ;
							
							k++;	
						}
						
						if(frame instanceof RateLibManFrame){
						
							Cina.rateLibManDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
						
						}else if(frame instanceof RateViewerFrame){
						
							Cina.rateViewerDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
						
						}else if(frame instanceof RateManFrame){
						
							Cina.rateManDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
	
						}else if(frame instanceof ElementVizFrame
									|| frame instanceof ElementVizAbundPlotFrame
									|| frame instanceof ElementVizAbundTableFrame
									|| frame instanceof ElementVizWeightPlotFrame
									|| frame instanceof ElementVizScalePlotFrame){
						
							Cina.elementVizDataStructure.getIsotopePadLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
						
						}else if(frame instanceof DataViewerFrame){
						
							Cina.dataViewerDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
						
						}else if(frame instanceof DataEvalFrame){
						
							Cina.dataEvalDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
						
						}else if(frame instanceof RateGenFrame
									|| frame instanceof RateGenChartFrame){
							
							Cina.rateGenDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
										
						}else if(frame instanceof RateParamFrame
									|| frame instanceof RateParamChartFrame
									|| frame instanceof RateParamStartFrame){
							
							Cina.rateParamDataStructure.getCurrentLibraryDataStructure().setIsotopeList(tempIsotopeListArray);
										
						}
						
					}
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}

	public boolean[] parseGET_RATE_LISTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";

		int numberTokens = st.countTokens();
		
		String[] rateIDArray = new String[numberTokens];
		
		RateDataStructure[] outputArray = new RateDataStructure[numberTokens];
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			System.out.println(currentToken);
			
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{
				
					rateIDArray[i] = currentToken;

					outputArray[i] = new RateDataStructure();
					
					outputArray[i].setReactionID(rateIDArray[i]);
					
					outputArray[i].parseReactionID();
					
				}
			}	
		}
		
		if(frame instanceof ElementSynthFrame){
		
			Cina.elementSynthDataStructure.setRateIDArray(rateIDArray);
		
		}else if(frame instanceof RateLibManFrame){
		
			Cina.rateLibManDataStructure.getCurrentLibraryDataStructure().setRateDataStructures(outputArray);
		
		}else if(frame instanceof RateViewerFrame){
		
			Cina.rateViewerDataStructure.getCurrentLibraryDataStructure().setRateDataStructures(outputArray);
		
		}else if(frame instanceof RateManFrame){
		
			Cina.rateManDataStructure.getCurrentLibraryDataStructure().setRateDataStructures(outputArray);
		
		}else if(frame instanceof RateGenFrame
					|| frame instanceof RateGenChartFrame){
		
			Cina.rateGenDataStructure.getCurrentLibraryDataStructure().setRateDataStructures(outputArray);
		
		}else if(frame instanceof RateParamFrame
					|| frame instanceof RateParamChartFrame
					|| frame instanceof RateParamStartFrame){
		
			Cina.rateParamDataStructure.getCurrentLibraryDataStructure().setRateDataStructures(outputArray);
		
		}

		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseMODIFY_RATE_LIBRARYString(String string, Frame frame){

		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String outputString = "";

		String currentToken = "";
		
		int numberTokens = st.countTokens();
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("MODIFY=")){
				
		
				
				}else if(currentToken.startsWith("REASON=")){
				
					reasonVector.addElement("Creation of new library failed.");
					reasonVector.addElement(currentToken.substring(7));
				
				}else if(currentToken.startsWith("REPORT=")){
				
					outputString = currentToken.substring(7);
				
					outputString = outputString.replaceAll("\u0008", "\n");

					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setModifyLibReport(outputString);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setModifyLibReport(outputString);
					
					}else if(frame instanceof RateManFrame){
					
						Cina.rateManDataStructure.setModifyLibReport(outputString);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setModifyLibReport(outputString);
					
					}
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseMODIFY_RATESString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		String outputString = "";
	
		int numberTokens = st.countTokens();
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("MESSAGE=")){
					
					createMessageDialog(currentToken.substring(8), frame);
				
				}else if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("MODIFY=")){
				
					if(currentToken.substring(7).equals("SUCCESS")){
					
						if(frame instanceof RateLibManFrame){
					
							LibraryDataStructure[] appldsa = new LibraryDataStructure[Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures()+1];
						
							for(int j=0; j<Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures(); j++){
							
								appldsa[j] = Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j];
							
							}
							
							appldsa[Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures()] = new LibraryDataStructure();
							appldsa[Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures()].setLibName(dest_lib);
						
							Cina.rateLibManDataStructure.setUserLibraryDataStructureArray(appldsa);
						
						}else if(frame instanceof RateGenFrame){
						
							LibraryDataStructure[] appldsa = new LibraryDataStructure[Cina.rateGenDataStructure.getNumberUserLibraryDataStructures()+1];
						
							for(int j=0; j<Cina.rateGenDataStructure.getNumberUserLibraryDataStructures(); j++){
							
								appldsa[j] = Cina.rateGenDataStructure.getUserLibraryDataStructureArray()[j];
							
							}
							
							appldsa[Cina.rateGenDataStructure.getNumberUserLibraryDataStructures()] = new LibraryDataStructure();
							appldsa[Cina.rateGenDataStructure.getNumberUserLibraryDataStructures()].setLibName(dest_lib);
						
							Cina.rateGenDataStructure.setUserLibraryDataStructureArray(appldsa);
						
						}else if(frame instanceof RateManFrame){
						
							LibraryDataStructure[] appldsa = new LibraryDataStructure[Cina.rateManDataStructure.getNumberUserLibraryDataStructures()+1];
						
							for(int j=0; j<Cina.rateManDataStructure.getNumberUserLibraryDataStructures(); j++){
							
								appldsa[j] = Cina.rateManDataStructure.getUserLibraryDataStructureArray()[j];
							
							}
							
							appldsa[Cina.rateManDataStructure.getNumberUserLibraryDataStructures()] = new LibraryDataStructure();
							appldsa[Cina.rateManDataStructure.getNumberUserLibraryDataStructures()].setLibName(dest_lib);
						
							Cina.rateManDataStructure.setUserLibraryDataStructureArray(appldsa);
						
						}else if(frame instanceof RateParamFrame){
						
							LibraryDataStructure[] appldsa = new LibraryDataStructure[Cina.rateParamDataStructure.getNumberUserLibraryDataStructures()+1];
						
							for(int j=0; j<Cina.rateParamDataStructure.getNumberUserLibraryDataStructures(); j++){
							
								appldsa[j] = Cina.rateParamDataStructure.getUserLibraryDataStructureArray()[j];
							
							}
							
							appldsa[Cina.rateParamDataStructure.getNumberUserLibraryDataStructures()] = new LibraryDataStructure();
							appldsa[Cina.rateParamDataStructure.getNumberUserLibraryDataStructures()].setLibName(dest_lib);
						
							Cina.rateParamDataStructure.setUserLibraryDataStructureArray(appldsa);
						
						}
					
					}
				
				}else if(currentToken.startsWith("REASON=")){
				
					reasonVector.addElement("Creation of new library failed.");
					reasonVector.addElement(currentToken.substring(7));
				
				}else if(currentToken.startsWith("REPORT=")){
				
					outputString = currentToken.substring(7);
				
					outputString = outputString.replaceAll("\u0008", "\n");
					
					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setModifyRatesReport(outputString);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setModifyRatesReport(outputString);
					
					}else if(frame instanceof RateManFrame){

						Cina.rateManDataStructure.setModifyRatesReport(outputString);
					
					}else if(frame instanceof RateParamFrame){

						Cina.rateParamDataStructure.setModifyRatesReport(outputString);
					
					}
				
				
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_RATE_LIBRARY_INFOString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		int index = string.indexOf("=");
	
		string = string.substring(index+1);
	
		StringTokenizer st = new StringTokenizer(string, "\u0009");
	
		String currentToken = "";
	
		String outputString = "";
	
		int numberTokens = st.countTokens();
		
		LibraryDataStructure applds = new LibraryDataStructure();
			
		applds.setLibName(URLDecoder.decode(library));
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("Library Notes=")){
				
					applds.setLibNotes(currentToken.substring(14).replaceAll("\u000c", "\n"));
				
				}else if(currentToken.startsWith("Creation Date=")){
				
					applds.setCreationDate(currentToken.substring(14));
				
				}else if(currentToken.startsWith("Library Recipe=")){
				
					applds.setLibraryRecipe(currentToken.substring(15));
				
				}else if(currentToken.startsWith("All Inverses Present=")){

					//if(currentToken.substring(21).trim().equals("YES")){

						applds.setAllInversesPresent(true);
					
					//}else if(currentToken.substring(21).trim().equals("NO")){
					
						//applds.setAllInversesPresent(false);
					
					//}
					
				}
				
				if(frame instanceof RateLibManFrame){
				
					for(int j=0; j<Cina.rateLibManDataStructure.getNumberUserLibraryDataStructures(); j++){
								
						if(Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("USER");
						
							Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j].setLibType("USER");
							Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(Cina.rateLibManDataStructure.getUserLibraryDataStructureArray()[j]);
	
						}			
					}	
					
					Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(applds);
					
					for(int j=0; j<Cina.rateLibManDataStructure.getNumberPublicLibraryDataStructures(); j++){
								
						if(Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("PUBLIC");
						
							Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j].setLibType("PUBLIC");
							Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(Cina.rateLibManDataStructure.getPublicLibraryDataStructureArray()[j]);
							
						}					
					}
					
					Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(applds);
					
					for(int j=0; j<Cina.rateLibManDataStructure.getNumberSharedLibraryDataStructures(); j++){
								
						if(Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("SHARED");
													
							Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j].setLibType("SHARED");
							Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(Cina.rateLibManDataStructure.getSharedLibraryDataStructureArray()[j]);
							
						}					
					}
				
					Cina.rateLibManDataStructure.setCurrentLibraryDataStructure(applds);
				
				}else if(frame instanceof ElementSynthFrame){

					for(int j=0; j<Cina.elementSynthDataStructure.getNumberUserLibraryDataStructures(); j++){
						
						if(Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("USER");
	
							Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j].setLibType("USER");
							Cina.elementSynthDataStructure.setCurrentLibraryDataStructure(Cina.elementSynthDataStructure.getUserLibraryDataStructureArray()[j]);
	
						}			
					}	
					
					Cina.elementSynthDataStructure.setCurrentLibraryDataStructure(applds);
					
					for(int j=0; j<Cina.elementSynthDataStructure.getNumberPublicLibraryDataStructures(); j++){
								
						if(Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("PUBLIC");
						
							Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j].setLibType("PUBLIC");
							Cina.elementSynthDataStructure.setCurrentLibraryDataStructure(Cina.elementSynthDataStructure.getPublicLibraryDataStructureArray()[j]);
							
						}					
					}
					
					Cina.elementSynthDataStructure.setCurrentLibraryDataStructure(applds);
					
					for(int j=0; j<Cina.elementSynthDataStructure.getNumberSharedLibraryDataStructures(); j++){
								
						if(Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].getLibName().equals(applds.getLibName())){
						
							applds.setLibType("SHARED");
													
							Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].setLibNotes(applds.getLibNotes());
							Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].setCreationDate(applds.getCreationDate());
							Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].setLibraryRecipe(applds.getLibraryRecipe());
							Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].setAllInversesPresent(applds.getAllInversesPresent());
							Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j].setLibType("SHARED");
							Cina.elementSynthDataStructure.setCurrentLibraryDataStructure(Cina.elementSynthDataStructure.getSharedLibraryDataStructureArray()[j]);
							
						}					
					}
				
				}	
			}	                            
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_RATE_INFOString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();
		
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				//errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{
					
					RateDataStructure apprds = new RateDataStructure();

					int index0b = currentToken.indexOf("\u000b");

					int indexSecond09 = currentToken.indexOf("\u0009", index0b);

					String uniqueString = currentToken.substring(index0b+1, indexSecond09);

					StringTokenizer st11 = new StringTokenizer(currentToken, "\u000b");
					
					String rateID = st11.nextToken() + "\u000b" + uniqueString;
					
					apprds.setReactionID(rateID);
					
					String secondPart = st11.nextToken();

					StringTokenizer st09 = new StringTokenizer(secondPart, "\u0009");
					
					int numberTokens2 = st09.countTokens();
					
					for(int j=0; j<numberTokens2; j++){
					
						String currentToken2 = st09.nextToken();

						if(currentToken2.startsWith("Reaction String = ")){

							apprds.setReactionString(currentToken2.substring(18));
						
						}else if(currentToken2.startsWith("Number of Parameters = ")){
						
							apprds.setNumberParameters(Integer.valueOf(currentToken2.substring(23)).intValue());

						}else if(currentToken2.startsWith("Resonant Components = ")){
						
							String currentToken3 = currentToken2.substring(22);
								
							StringTokenizer stComma = new StringTokenizer(currentToken3, ",");
						
							int numberTokens3 = stComma.countTokens();
							
							String[] outputArray = new String[numberTokens3];
							
							for(int k=0; k<numberTokens3; k++){
							
								outputArray[k] = stComma.nextToken();
								
							}
							
							apprds.setResonantInfo(outputArray);
						
						}else if(currentToken2.startsWith("Parameters = ")){
						
							String currentToken3 = currentToken2.substring(13);
						
							StringTokenizer stComma = new StringTokenizer(currentToken3, ",");
						
							int numberTokens3 = stComma.countTokens();

							double[] outputArray = new double[numberTokens3];
						
							for(int k=0; k<numberTokens3; k++){
							
								outputArray[k] = Double.valueOf(stComma.nextToken()).doubleValue();
																
							}
							
							apprds.setParameters(outputArray);
							
							double[][] outputArrayComp = new double[(int)(outputArray.length/7)][7];
														
							for(int k=0; k<(int)(outputArray.length/7); k++){
							
								for(int l=0; l<7; l++){
								
									outputArrayComp[k][l] = outputArray[(k*7) + l];
										
								}
							}
							
							apprds.setParameterCompArray(outputArrayComp);
							
						}else if(currentToken2.startsWith("Reaction Type = ")){
						
							String currentToken3 = currentToken2.substring(16);
						
							StringTokenizer stComma = new StringTokenizer(currentToken3, ",");
							
							int numberTokensComma = stComma.countTokens();
						
							apprds.setReactionType(Integer.valueOf(stComma.nextToken()).intValue());
							
							if(numberTokensComma==2){
							
								String currentToken4 = stComma.nextToken();
							
								if(currentToken4.equals("v")){
								
									apprds.setInverse(true);
								
								}else if(currentToken4.equals("ec")
											|| currentToken4.equals("bet-")
											|| currentToken4.equals("bet+")){
								
									apprds.setDecay(currentToken4);
								
								}
							
							}else if(numberTokensComma==3){
							
								String currentToken4 = stComma.nextToken();
							
								if(currentToken4.equals("v")){
								
									apprds.setInverse(true);
								
								}else if(currentToken4.equals("ec")
											|| currentToken4.equals("bet-")
											|| currentToken4.equals("bet+")){
								
									apprds.setDecay(currentToken4);
								
								}
								
								String currentToken5 = stComma.nextToken();
							
								if(currentToken5.equals("v")){
								
									apprds.setInverse(true);
								
								}else if(currentToken5.equals("ec")
											|| currentToken5.equals("bet-")
											|| currentToken5.equals("bet+")){
								
									apprds.setDecay(currentToken5);
								
								}
							
							}
						
						}else if(currentToken2.startsWith("Q-value = ")){
						
							String currentToken3 = currentToken2.substring(10);

							apprds.setQValue(Double.valueOf(currentToken3).doubleValue());
						
						}else if(currentToken2.startsWith("Reaction Notes = ")){
						
							String currentToken3 = currentToken2.substring(17);

							apprds.setReactionNotes(currentToken3.replaceAll("\u000c", "\n"));
						
						}else if(currentToken2.startsWith("Biblio Code = ")){
						
							String currentToken3 = currentToken2.substring(14);

							apprds.setBiblioString(currentToken3);
						
						}else if(currentToken2.startsWith("Chisquared = ")){
						
							String currentToken3 = currentToken2.substring(13);
							
							apprds.setChisquared(Double.valueOf(currentToken3).doubleValue());
						
						}else if(currentToken2.startsWith("Creation Date = ")){
						
							String currentToken3 = currentToken2.substring(16);
							
							apprds.setCreationDate(currentToken3);
						
						}else if(currentToken2.startsWith("Max Percent Difference = ")){
						
							String currentToken3 = currentToken2.substring(25);
							
							apprds.setMaxPercentDiff(Double.valueOf(currentToken3).doubleValue());
						
						}else if(currentToken2.startsWith("Reference Citation = ")){
						
							String currentToken3 = currentToken2.substring(21);
							
							apprds.setRefCitation(currentToken3);
						
						}else if(currentToken2.startsWith("Valid Temp Range = ")){
						
							String currentToken3 = currentToken2.substring(19);
							
							apprds.setValidTempRange(currentToken3);
						
						}
						
					}
					
					transferRateDataStructure(apprds, frame);
					
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseREGISTERString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REGISTER=")){


				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseRATES_EXISTString(String string, Frame frame){

		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		Vector tempVector = new Vector();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				//errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{

					if((frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==6)
							|| (frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==1
								&& Cina.rateManFrame.currentPanelIndex==2)
							|| frame instanceof RateViewerPlotFrame){
					
						tempVector.addElement(currentToken);		
					
					}else{

						if(frame instanceof ElementVizPointFrame){
							
							String rateID = currentToken.substring(0, currentToken.lastIndexOf("\u0009"));
							String output = currentToken.substring(currentToken.lastIndexOf("\u0009")+1);
			
							if(output.equals("EXIST=YES")){
								HashMap<String, RateDataStructure> rateMap = Cina.elementVizDataStructure.map.get(Cina.elementVizDataStructure.zaIndex);
								rateMap.put(rateID, new RateDataStructure());
							}
							
						}else{
						
							StringTokenizer st09 = new StringTokenizer(currentToken, "\u0009");
		
							st09.nextToken();
							st09.nextToken();
							
							String outputString = st09.nextToken();
		
							if(frame instanceof RateGenFrame){
							
								if(outputString.equals("EXIST=YES")){
								
									Cina.rateGenDataStructure.setCurrentRateExists(true);
									
								}else if(outputString.equals("EXIST=NO")){
								
									Cina.rateGenDataStructure.setCurrentRateExists(false);
								
								}
								
							}else if(frame instanceof RateParamFrame){
							
								if(outputString.equals("EXIST=YES")){
								
									Cina.rateParamDataStructure.setCurrentRateExists(true);
									
								}else if(outputString.equals("EXIST=NO")){
								
									Cina.rateParamDataStructure.setCurrentRateExists(false);
								
								}
								
							}else if(frame instanceof RateManFrame){
		
								if(outputString.equals("EXIST=YES")){
								
									Cina.rateManDataStructure.setCurrentRateExists(true);
									
								}else if(outputString.equals("EXIST=NO")){
								
									Cina.rateManDataStructure.setCurrentRateExists(false);
								
								}
								
							}else if(frame instanceof RateViewerPlotFrame){
		
								if(outputString.equals("EXIST=YES")){
								
									Cina.rateViewerDataStructure.setCurrentRateExists(true);
									
								}else if(outputString.equals("EXIST=NO")){
								
									Cina.rateViewerDataStructure.setCurrentRateExists(false);
								
								}
								
							}
							
						}
					}
				}
			}	
		}
		
		if(frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==6){
			
			tempVector.trimToSize();
				
			String[] tempStringArray = new String[tempVector.size()];
			
			for(int i=0; i<tempVector.size(); i++){
			
				tempStringArray[i] = (String)tempVector.elementAt(i);
			
			}
			
			Cina.rateManDataStructure.setInvestRateIDs(tempStringArray);
				
		}
		
		if(frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==1){
			
			tempVector.trimToSize();
				
			String[] tempStringArray = new String[tempVector.size()];
			
			for(int i=0; i<tempVector.size(); i++){
			
				tempStringArray[i] = (String)tempVector.elementAt(i);
			
			}
			
			Cina.rateManDataStructure.setAboutRateIDs(tempStringArray);
				
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}

	public boolean[] parseADD_MISSING_INV_RATESString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{

				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("MODIFY=")){
				
					if(currentToken.substring(7).equals("SUCCESS")){
					
					
					}
				
				}else if(currentToken.startsWith("REASON=")){
				
					reasonVector.addElement("Creation of inverse rates failed.");
					reasonVector.addElement(currentToken.substring(7));
				
				}else if(currentToken.startsWith("REPORT=")){
				
					String output = currentToken.substring(7);
				
					output = output.replaceAll("\u0008", "\n");
					
					if(frame instanceof RateLibManFrame){
					
						Cina.rateLibManDataStructure.setAddMissingInvRatesReport(output);
					
					}else if(frame instanceof ElementSynthFrame){
					
						Cina.elementSynthDataStructure.setAddMissingInvRatesReport(output);
					
					}
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_INVERSE_REACTIONString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REACTION=")){
				
					String outputString = currentToken.substring(9);
					
					Cina.rateLibManDataStructure.setInverseReaction(outputString);
				
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseSHARE_RATE_LIBRARYString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REPORT=")){
				
					String outputString = currentToken.substring(7);

					outputString = outputString.replaceAll("\u0008", "\n");
					
					Cina.rateLibManDataStructure.setSharedLibReport(outputString);
				
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_TIME_MAPPINGString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{

					StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
					stEquals.nextToken();
					
					String tempString = stEquals.nextToken();
					
					StringTokenizer stComma = new StringTokenizer(tempString, ",");
					
					int numberTokensComma = stComma.countTokens();
					
					double[] outputArray = new double[numberTokensComma];
					
					for(int j=0; j<numberTokensComma; j++){
					
						outputArray[j] = Double.valueOf(stComma.nextToken()).doubleValue();
						
					}
				
					Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setTimeArray(outputArray);
				
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_ISOTOPE_MAPPINGString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{
				
					StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
					stEquals.nextToken();
					
					String tempString = stEquals.nextToken();
					
					StringTokenizer st09 = new StringTokenizer(tempString, "\u0009");

					int numberTokens09 = st09.countTokens();
					
					Point[] outputArrayZA = new Point[numberTokens09];
					String[] outputArrayLabel = new String[numberTokens09];
					
					for(int j=0; j<numberTokens09; j++){
					
						StringTokenizer stComma = new StringTokenizer(st09.nextToken(), ",");
					
						outputArrayZA[j] = new Point(Integer.valueOf(stComma.nextToken()).intValue(), Integer.valueOf(stComma.nextToken()).intValue());
						outputArrayLabel[j] = stComma.nextToken();

					}
				
					Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setZAMapArray(outputArrayZA);
					Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setIsotopeLabelMapArray(outputArrayLabel);
				
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_THERMO_PROFILEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{

					StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
					stEquals.nextToken();
					
					String tempString = stEquals.nextToken();
					
					StringTokenizer st09 = new StringTokenizer(tempString, "\u0009");

					int numberTokens09 = st09.countTokens();
					
					double[] outputArrayTemp = new double[numberTokens09];
					double[] outputArrayDensity = new double[numberTokens09];
					
					for(int j=0; j<numberTokens09; j++){
					
						StringTokenizer stComma = new StringTokenizer(st09.nextToken(), ",");
					
						outputArrayTemp[j] = Double.valueOf(stComma.nextToken()).doubleValue();

						outputArrayDensity[j] = Double.valueOf(stComma.nextToken()).doubleValue();
					
					}
				
					Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setTempArray(outputArrayTemp);
					Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setDensityArray(outputArrayDensity);
					
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_ABUNDANCESString(Object object, Frame frame, String final_step, ProgressBarDialog dialog){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();

		boolean[] booleanArray = new boolean[3];

		if(!(object instanceof String)){

			if(isotopes.equals("") && final_step.equals("N") && dialog!=null){
	
				dialog.setCurrentValue(0);
				dialog.appendText("Parsing abundance data for " 
									+ Cina.elementVizDataStructure.getCurrentNucSimDataStructure().getNucSimName()
									+ "...");
																	
			}else if(isotopes.equals("") && final_step.equals("Y") && dialog!=null){
			
				dialog.setCurrentValue(0);
				dialog.appendText("Parsing final abundance data for "
									+ Cina.elementVizDataStructure.getCurrentNucSimDataStructure().getNucSimName()
									+ "...");
			
			}
	
			String currentToken = "";
			
			AbundTimestepDataStructure[] appatdsa = new AbundTimestepDataStructure[0];
			
			if(final_step.equals("N")){
			
				appatdsa = new AbundTimestepDataStructure[Cina.elementVizDataStructure.getCurrentNucSimDataStructure().getTimeArray().length];
			
			}else if(final_step.equals("Y")){
			
				appatdsa = new AbundTimestepDataStructure[1];
			
			}
			
			int timestep = 0;
	
			BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)object));
	
			int numberBytes = 0;
	
			for(int i=0; i<appatdsa.length; i++){
				
				try{
				
					currentToken = br.readLine();

					numberBytes += currentToken.getBytes().length;

	
					if(isotopes.equals("") && dialog!=null){
	
						dialog.setCurrentValue(numberBytes);
						
					}
				
				}catch(IOException ioe){
				
					ioe.printStackTrace();
				
				}
				
				if(currentToken.startsWith("ERROR=")){
				
					errorVector.addElement(currentToken);
				
				}else{
					
					if(currentToken.startsWith("CAUTION=")){
			
						cautionVector.addElement(currentToken);
			
					}else{
						
						StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
						appatdsa[timestep] = new AbundTimestepDataStructure();
					
						if(stEquals.countTokens()>1){
						
							stEquals.nextToken();
							
							String tempString = stEquals.nextToken();
							
							StringTokenizer st09 = new StringTokenizer(tempString, "\u0009");
							
							int numberAbund = st09.countTokens();
							
							float[] outputArrayAbund = new float[numberAbund];
							
							int[] outputArrayIndex = new int[numberAbund];
							
							for(int j=0; j<numberAbund; j++){
							
								StringTokenizer stComma = new StringTokenizer(st09.nextToken(), ",");
							
								outputArrayIndex[j] = Integer.valueOf(stComma.nextToken()).intValue();
								outputArrayAbund[j] = Float.valueOf(stComma.nextToken()).floatValue();
	
							}
							
							appatdsa[timestep].setAbundArray(outputArrayAbund);
							appatdsa[timestep].setIndexArray(outputArrayIndex);
						
						}else{
						
							float[] outputArrayAbund = new float[1];
							
							int[] outputArrayIndex = new int[1];
							
							outputArrayAbund[0] = 0.0f;
							
							outputArrayIndex[0] = -1;
							
							appatdsa[timestep].setAbundArray(outputArrayAbund);
							appatdsa[timestep].setIndexArray(outputArrayIndex);	
											
						}
						
						timestep++;
	
					}
				}	
			}
	
			Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setAbundTimestepDataStructureArray(appatdsa);
			
		}else{
		
			errorVector.addElement((String)object);
		
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_NUC_DATA_SET_LISTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
				
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("PUBLIC=")){
				
					StringTokenizer publicNucDataSetTokenizer = new StringTokenizer(currentToken.substring(7), "\u0009");
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.setNumberPublicNucDataSetDataStructures(publicNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataManDataStructure.getNumberPublicNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataManDataStructure.getNumberPublicNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(publicNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataManDataStructure.setPublicNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataViewerFrame){
					
						Cina.dataViewerDataStructure.setNumberPublicNucDataSetDataStructures(publicNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataViewerDataStructure.getNumberPublicNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataViewerDataStructure.getNumberPublicNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(publicNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataViewerDataStructure.setPublicNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.setNumberPublicNucDataSetDataStructures(publicNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataEvalDataStructure.getNumberPublicNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataEvalDataStructure.getNumberPublicNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(publicNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataEvalDataStructure.setPublicNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberPublicNucDataSetDataStructures(publicNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateGenDataStructure.getNumberPublicNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberPublicNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(publicNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateGenDataStructure.setPublicNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberPublicNucDataSetDataStructures(publicNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateParamDataStructure.getNumberPublicNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberPublicNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(publicNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateParamDataStructure.setPublicNucDataSetDataStructureArray(outputArray);
					
					}
					
				}else if(currentToken.startsWith("SHARED=")){
				
					StringTokenizer sharedNucDataSetTokenizer = new StringTokenizer(currentToken.substring(7), "\u0009");
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.setNumberSharedNucDataSetDataStructures(sharedNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataManDataStructure.getNumberSharedNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataManDataStructure.getNumberSharedNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(sharedNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataManDataStructure.setSharedNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataViewerFrame){
					
						Cina.dataViewerDataStructure.setNumberSharedNucDataSetDataStructures(sharedNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataViewerDataStructure.getNumberSharedNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataViewerDataStructure.getNumberSharedNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(sharedNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataViewerDataStructure.setSharedNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.setNumberSharedNucDataSetDataStructures(sharedNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataEvalDataStructure.getNumberSharedNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataEvalDataStructure.getNumberSharedNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(sharedNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataEvalDataStructure.setSharedNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberSharedNucDataSetDataStructures(sharedNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateGenDataStructure.getNumberSharedNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberSharedNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(sharedNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateGenDataStructure.setSharedNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberSharedNucDataSetDataStructures(sharedNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateParamDataStructure.getNumberSharedNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberSharedNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(sharedNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateParamDataStructure.setSharedNucDataSetDataStructureArray(outputArray);
					
					}
					
				}else if(currentToken.startsWith("USER=")){
				
					StringTokenizer userNucDataSetTokenizer = new StringTokenizer(currentToken.substring(5), "\u0009");
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.setNumberUserNucDataSetDataStructures(userNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(userNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataManDataStructure.setUserNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataViewerFrame){
					
						Cina.dataViewerDataStructure.setNumberUserNucDataSetDataStructures(userNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataViewerDataStructure.getNumberUserNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataViewerDataStructure.getNumberUserNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(userNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataViewerDataStructure.setUserNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.setNumberUserNucDataSetDataStructures(userNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(userNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.dataEvalDataStructure.setUserNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setNumberUserNucDataSetDataStructures(userNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(userNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateGenDataStructure.setUserNucDataSetDataStructureArray(outputArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.setNumberUserNucDataSetDataStructures(userNucDataSetTokenizer.countTokens());
						
						NucDataSetDataStructure[] outputArray = new NucDataSetDataStructure[Cina.rateParamDataStructure.getNumberUserNucDataSetDataStructures()];
						
						for(int j=0; j<Cina.rateParamDataStructure.getNumberUserNucDataSetDataStructures(); j++){
	
							outputArray[j] = new NucDataSetDataStructure();
							
							outputArray[j].setNucDataSetName(userNucDataSetTokenizer.nextToken());
						
						}
						
						Cina.rateParamDataStructure.setUserNucDataSetDataStructureArray(outputArray);
					
					}
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_NUC_DATA_SET_ISOTOPESString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		int numberTokens = st.countTokens();
		
		String setName = URLDecoder.decode(set);
				
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith(setName + "=")){
				
					StringTokenizer isotopeListTokenizer = new StringTokenizer(currentToken.substring(setName.length() + 1), "\u0009");
					
					String[] isotopePairArray = new String[isotopeListTokenizer.countTokens()];
					
					int[] tempZArray = new int[100];
					
					int currentZ = 0;
					
					int previousZ = 0;
					
					int ZCounter = 0;
					
					boolean firstTimeFlag = true;
					
					int numberZTokens = isotopeListTokenizer.countTokens();

					//Count the Z's for array initialization
					for(int j=0; j<numberZTokens; j++){
					
						isotopePairArray[j] = isotopeListTokenizer.nextToken();
						
						StringTokenizer isotopePairTokenizer = new StringTokenizer(isotopePairArray[j], ",");
						
						currentZ = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
						
						isotopePairTokenizer.nextToken();
						
						if(firstTimeFlag){
						
							tempZArray[ZCounter] = currentZ;
						
							ZCounter++;
						
							firstTimeFlag = false;
						
						}else if(currentZ!=previousZ){
						
							tempZArray[ZCounter] = currentZ;
						
							ZCounter++;
						
						}
						
						previousZ = currentZ;	
					}
					
					int[] outputZArray = new int[ZCounter];
					
					for(int j=0; j<ZCounter; j++){
					
						outputZArray[j] = tempZArray[j];
					
					}	
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.getCurrentNucDataSetDataStructure().setZList(outputZArray);
					
					}else if(frame instanceof DataViewerFrame){
					
						Cina.dataViewerDataStructure.getCurrentNucDataSetDataStructure().setZList(outputZArray);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.getCurrentNucDataSetDataStructure().setZList(outputZArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.getCurrentNucDataSetDataStructure().setZList(outputZArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.getCurrentNucDataSetDataStructure().setZList(outputZArray);
					
					}
					
					//Step2 
					
					int[][] tempIsotopeListArray = new int[ZCounter][200];
					 
					for(int j=0; j<ZCounter; j++){
					
						for(int k=0; k<200; k++){
						
							tempIsotopeListArray[j][k] = -1;
						
						}
					
					} 
					 
					isotopeListTokenizer = new StringTokenizer(currentToken.substring(setName.length() + 1), "\u0009");
					
					isotopePairArray = new String[isotopeListTokenizer.countTokens()];
					
					currentZ = 0;
					
					previousZ = 0;
					
					ZCounter = 0;
					
					firstTimeFlag = true;
					
					numberZTokens = isotopeListTokenizer.countTokens();
					
					int k = 0;
					
					//Count the Z's for array initialization
					for(int j=0; j<numberZTokens; j++){
					
						isotopePairArray[j] = isotopeListTokenizer.nextToken();
						
						StringTokenizer isotopePairTokenizer = new StringTokenizer(isotopePairArray[j], ",");
						
						currentZ = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
						
						if(firstTimeFlag){
						
							firstTimeFlag = false;
						
						}else if(currentZ!=previousZ){
						
							ZCounter++;
							
							k=0;
						
						}
						
						tempIsotopeListArray[ZCounter][k] = Integer.valueOf(isotopePairTokenizer.nextToken()).intValue();
						
						previousZ = currentZ;
						
						k++;	
					}
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.getCurrentNucDataSetDataStructure().setIsotopeList(tempIsotopeListArray);
					
					}else if(frame instanceof DataViewerFrame){
					
						Cina.dataViewerDataStructure.getCurrentNucDataSetDataStructure().setIsotopeList(tempIsotopeListArray);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.getCurrentNucDataSetDataStructure().setIsotopeList(tempIsotopeListArray);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.getCurrentNucDataSetDataStructure().setIsotopeList(tempIsotopeListArray);
					
					}else if(frame instanceof RateParamFrame){
					
						Cina.rateParamDataStructure.getCurrentNucDataSetDataStructure().setIsotopeList(tempIsotopeListArray);
					
					}
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}

	public boolean[] parseGET_NUC_DATA_LISTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";

		int numberTokens = st.countTokens();
		
		String[] nucDataIDArray = new String[numberTokens];
		
		NucDataDataStructure[] outputArray = new NucDataDataStructure[numberTokens];
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{
				
					nucDataIDArray[i] = currentToken;

					outputArray[i] = new NucDataDataStructure();
					
					outputArray[i].setNucDataID(nucDataIDArray[i]);
					
					outputArray[i].parseNucDataID();
					
				}
			}	
		}
		
		if(frame instanceof DataManFrame){
		
			Cina.dataManDataStructure.getCurrentNucDataSetDataStructure().setNucDataDataStructures(outputArray);
		
		}else if(frame instanceof DataViewerFrame){
		
			Cina.dataViewerDataStructure.getCurrentNucDataSetDataStructure().setNucDataDataStructures(outputArray);
		
		}else if(frame instanceof DataEvalFrame){
		
			Cina.dataEvalDataStructure.getCurrentNucDataSetDataStructure().setNucDataDataStructures(outputArray);
		
		}else if(frame instanceof RateGenFrame){
		
			Cina.rateGenDataStructure.getCurrentNucDataSetDataStructure().setNucDataDataStructures(outputArray);
		
		}else if(frame instanceof RateParamFrame){
		
			Cina.rateParamDataStructure.getCurrentNucDataSetDataStructure().setNucDataDataStructures(outputArray);
		
		}

		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseGET_NUC_DATA_INFOString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{

					try{
					
						NucDataDataStructure appndds = new NucDataDataStructure();
	
						StringTokenizer st11 = new StringTokenizer(currentToken, "\u000b");
	
						String firstPart = st11.nextToken();
						String secondPart = st11.nextToken();
						String thirdPart = st11.nextToken();
						
						StringTokenizer st09 = new StringTokenizer(thirdPart, "\u0009");
						
						String lastPart = st09.nextToken();
						
						String nucDataID = firstPart + "\u000b" + secondPart + "\u000b" + lastPart;
						
						appndds.setNucDataID(nucDataID);
						
						appndds.setNucDataSet(firstPart.substring(8, firstPart.indexOf("\u0009")));
						
						appndds.setNucDataName(thirdPart.substring(0, thirdPart.indexOf("\u0009")));
	
						appndds.setNucDataType(secondPart.substring(secondPart.indexOf("\u0009")+1));
	
						st09 = new StringTokenizer(thirdPart.substring(thirdPart.indexOf("\u0009")), "\u0009");
						
						int numberTokens2 = st09.countTokens();
						
						for(int j=0; j<numberTokens2; j++){
						
							String currentToken2 = st09.nextToken();
	
							if(currentToken2.startsWith("Reaction String = ")){
	
								appndds.setReactionString(currentToken2.substring(18));
							
							}else if(currentToken2.startsWith("Number of Points = ")){
							
								appndds.setNumberPoints(Integer.valueOf(currentToken2.substring(19)).intValue());
	
							}else if(currentToken2.startsWith("X points = ")){
							
								StringTokenizer stComma = new StringTokenizer(currentToken2.substring(11), ",");
							
								double[] outputArray = new double[stComma.countTokens()];
							
								for(int k=0; k<outputArray.length; k++){
								
									outputArray[k] = Double.valueOf(stComma.nextToken()).doubleValue();
								
								}
							
								appndds.setXDataArray(outputArray);
								
							}else if(currentToken2.startsWith("Y points = ")){
							
								StringTokenizer stComma = new StringTokenizer(currentToken2.substring(11), ",");
							
								double[] outputArray = new double[stComma.countTokens()];
							
								for(int k=0; k<outputArray.length; k++){
								
									outputArray[k] = Double.valueOf(stComma.nextToken()).doubleValue();
								
								}
							
								appndds.setYDataArray(outputArray);
								
							}else if(currentToken2.startsWith("Reaction Type = ")){
							
								String currentToken3 = currentToken2.substring(16);
							
								StringTokenizer stComma = new StringTokenizer(currentToken3, ",");
								
								int numberTokensComma = stComma.countTokens();
							
								appndds.setReactionType(Integer.valueOf(stComma.nextToken()).intValue());
								
								if(numberTokensComma==2){
								
									String currentToken4 = stComma.nextToken();
								
									if(currentToken4.equals("ec")
												|| currentToken4.equals("bet-")
												|| currentToken4.equals("bet+")){
									
										appndds.setDecay(currentToken4);
									
									}
								
								}
							
							}else if(currentToken2.startsWith("Nuc Data Notes = ")){
							
								String currentToken3 = currentToken2.substring(17);
	
								appndds.setNucDataNotes(currentToken3.replaceAll("\u000c", "\n"));
							
							}else if(currentToken2.startsWith("Organization Code = ")){
							
								String currentToken3 = currentToken2.substring(20);
	
								appndds.setOrgCode(currentToken3);
							
							}else if(currentToken2.startsWith("People Code = ")){
							
								String currentToken3 = currentToken2.substring(14);
	
								appndds.setPeopleCode(currentToken3);
							
							}else if(currentToken2.startsWith("Creation Date = ")){
							
								String currentToken3 = currentToken2.substring(16);
								
								appndds.setCreationDate(currentToken3);
							
							}else if(currentToken2.startsWith("Reference Citation = ")){
							
								String currentToken3 = currentToken2.substring(21);
								
								appndds.setRefCitation(currentToken3);
							
							}
							
						}
						
						transferNucDataDataStructure(appndds, frame);
					
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parsePARSE_NUC_DATA_FILEString(String string, Frame frame){
	
		//Clear vectors for messages
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();

		//Create flagArray temp array
		boolean[] booleanArray = new boolean[3];
	
		//Create tokenizer on new line char
		StringTokenizer st = new StringTokenizer(string, "\n");

		//Initialize current token string
		String currentToken = "";
	
		//get number of tokens
		int numberTokens = st.countTokens();
		
		//Loop over number of tokens
		for(int i=0; i<numberTokens; i++){
			
			//Assign new token to current token
			currentToken = st.nextToken();
			
			//If current token starts with "ERROR="
			if(currentToken.startsWith("ERROR=")){
			
				//Add error message to error vector
				errorVector.addElement(currentToken);
			
			//If current token does not start with "ERROR="
			}else{
				
				//If current token starts with "CAUTION="
				if(currentToken.startsWith("CAUTION=")){
		
					//Add caution message to caution vector
					cautionVector.addElement(currentToken);
		
				//If current token starts with "POINTS="
				}else if(currentToken.startsWith("POINTS=")){
					
					//Asign number of points to two places in the Rate Gen Data Structure
					Cina.dataManDataStructure.getImportNucDataDataStructure().setNumberPoints((int)Double.valueOf(currentToken.substring(7)).doubleValue());

				//If current token starts with "DATA="
				}else if(currentToken.startsWith("DATA=")){	
				
					StringTokenizer st09 = new StringTokenizer(currentToken.substring(5), "\u0009");
				
					String currentToken2 = "";
				
					double[] inputDataArray = new double[Cina.dataManDataStructure.getImportNucDataDataStructure().getNumberPoints()];
					double[] inputErrorDataArray = new double[Cina.dataManDataStructure.getImportNucDataDataStructure().getNumberPoints()];
					
					double[] outputDataArray = new double[Cina.dataManDataStructure.getImportNucDataDataStructure().getNumberPoints()];
					double[] outputErrorDataArray = new double[Cina.dataManDataStructure.getImportNucDataDataStructure().getNumberPoints()];
					
					for(int j=0; j<Cina.dataManDataStructure.getImportNucDataDataStructure().getNumberPoints(); j++){
						
						currentToken2 = st09.nextToken();
						
						StringTokenizer tempST = new StringTokenizer(currentToken2);
						
						inputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						
						inputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						
						outputDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						
						outputErrorDataArray[j] = Double.valueOf(tempST.nextToken()).doubleValue();
						
					}
					

					if(Cina.dataManDataStructure.getImportNucDataDataStructure().getDataFormat().equals("1,0,2,0")
							|| Cina.dataManDataStructure.getImportNucDataDataStructure().getDataFormat().equals("1,2,3,4")){
						
						Cina.dataManDataStructure.getImportNucDataDataStructure().setXDataArray(inputDataArray);
						//Cina.rateGenDataStructure.setEnergyErrorDataArray(inputErrorDataArray);
					
						Cina.dataManDataStructure.getImportNucDataDataStructure().setYDataArray(outputDataArray);
						//Cina.rateGenDataStructure.setCrossSectionErrorDataArray(outputErrorDataArray);
					
					}else if(Cina.dataManDataStructure.getImportNucDataDataStructure().getDataFormat().equals("2,0,1,0")
							|| Cina.dataManDataStructure.getImportNucDataDataStructure().getDataFormat().equals("3,4,1,2")){
					
						Cina.dataManDataStructure.getImportNucDataDataStructure().setXDataArray(outputDataArray);
						//Cina.rateGenDataStructure.setEnergyErrorDataArray(outputErrorDataArray);
					
						Cina.dataManDataStructure.getImportNucDataDataStructure().setYDataArray(inputDataArray);
						//Cina.rateGenDataStructure.setCrossSectionErrorDataArray(inputErrorDataArray);
					
					}
							
				}else if(currentToken.startsWith("ENERGY_MIN=")){
					
					Cina.dataManDataStructure.getImportNucDataDataStructure().setXmin(Double.valueOf(currentToken.substring(11)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_MAX=")){
					
					Cina.dataManDataStructure.getImportNucDataDataStructure().setXmax(Double.valueOf(currentToken.substring(11)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_ERROR_MIN=")){
					
					//Cina.rateGenDataStructure.setEErrormin(Double.valueOf(currentToken.substring(17)).doubleValue());
					
				}else if(currentToken.startsWith("ENERGY_ERROR_MAX=")){
					
					//Cina.rateGenDataStructure.setEErrormax(Double.valueOf(currentToken.substring(17)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_MIN=")){
					
					Cina.dataManDataStructure.getImportNucDataDataStructure().setYmin(Double.valueOf(currentToken.substring(13)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_MAX=")){
					
					Cina.dataManDataStructure.getImportNucDataDataStructure().setYmax(Double.valueOf(currentToken.substring(13)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_ERROR_MIN=")){
					
					//Cina.rateGenDataStructure.setSFactorErrormin(Double.valueOf(currentToken.substring(19)).doubleValue());
					
				}else if(currentToken.startsWith("S_FACTOR_ERROR_MAX=")){
					
					//Cina.rateGenDataStructure.setSFactorErrormin(Double.valueOf(currentToken.substring(19)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_MIN=")){
					
					Cina.dataManDataStructure.getImportNucDataDataStructure().setYmin(Double.valueOf(currentToken.substring(18)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_MAX=")){
				
					Cina.dataManDataStructure.getImportNucDataDataStructure().setYmax(Double.valueOf(currentToken.substring(18)).doubleValue());	
					
				}else if(currentToken.startsWith("CROSS_SECTION_ERROR_MIN=")){
					
					//Cina.rateGenDataStructure.setCrossSectionErrormin(Double.valueOf(currentToken.substring(24)).doubleValue());
					
				}else if(currentToken.startsWith("CROSS_SECTION_ERROR_MAX=")){
					
					//Cina.rateGenDataStructure.setCrossSectionErrormin(Double.valueOf(currentToken.substring(24)).doubleValue());
					
				}
			}	
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseMODIFY_NUC_DATA_SETString(String string, Frame frame){

		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String outputString = "";

		String currentToken = "";
		
		int numberTokens = st.countTokens();
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("MODIFY=")){
				
		
				
				}else if(currentToken.startsWith("REASON=")){
				
					reasonVector.addElement("Creation of new nuclear data set failed.");
					reasonVector.addElement(currentToken.substring(7));
				
				}else if(currentToken.startsWith("REPORT=")){
				
					outputString = currentToken.substring(7);
				
					outputString = outputString.replaceAll("\u0008", "\n");

					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.setModifyNucDataSetReport(outputString);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.setModifyNucDataSetReport(outputString);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setModifyNucDataSetReport(outputString);
					
					}
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseMODIFY_NUC_DATAString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");
	
		String currentToken = "";
	
		String outputString = "";
	
		int numberTokens = st.countTokens();
			
		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("MESSAGE=")){
					
					createMessageDialog(currentToken.substring(8), frame);
				
				}else if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("MODIFY=")){
				
					if(currentToken.substring(7).equals("SUCCESS")){
					
						if(frame instanceof DataManFrame){
					
							NucDataSetDataStructure[] appndsdsa = new NucDataSetDataStructure[Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures()+1];
						
							for(int j=0; j<Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures(); j++){
							
								appndsdsa[j] = Cina.dataManDataStructure.getUserNucDataSetDataStructureArray()[j];
							
							}
							
							appndsdsa[Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures()] = new NucDataSetDataStructure();
						
							appndsdsa[Cina.dataManDataStructure.getNumberUserNucDataSetDataStructures()].setNucDataSetName(dest_set);
						
							Cina.dataManDataStructure.setUserNucDataSetDataStructureArray(appndsdsa);
						
						}else if(frame instanceof DataEvalFrame){
					
							NucDataSetDataStructure[] appndsdsa = new NucDataSetDataStructure[Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures()+1];
						
							for(int j=0; j<Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures(); j++){
							
								appndsdsa[j] = Cina.dataEvalDataStructure.getUserNucDataSetDataStructureArray()[j];
							
							}
							
							appndsdsa[Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures()] = new NucDataSetDataStructure();
						
							appndsdsa[Cina.dataEvalDataStructure.getNumberUserNucDataSetDataStructures()].setNucDataSetName(dest_set);
						
							Cina.dataEvalDataStructure.setUserNucDataSetDataStructureArray(appndsdsa);
						
						}else if(frame instanceof RateGenFrame){
					
							NucDataSetDataStructure[] appndsdsa = new NucDataSetDataStructure[Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures()+1];
						
							for(int j=0; j<Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures(); j++){
							
								appndsdsa[j] = Cina.rateGenDataStructure.getUserNucDataSetDataStructureArray()[j];
							
							}
							
							appndsdsa[Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures()] = new NucDataSetDataStructure();
						
							appndsdsa[Cina.rateGenDataStructure.getNumberUserNucDataSetDataStructures()].setNucDataSetName(dest_set);
						
							Cina.rateGenDataStructure.setUserNucDataSetDataStructureArray(appndsdsa);
						
						}
					
					}
				
				}else if(currentToken.startsWith("REASON=")){
				
					reasonVector.addElement("Creation of new nuclear data set failed.");
					reasonVector.addElement(currentToken.substring(7));
				
				}else if(currentToken.startsWith("REPORT=")){
				
					outputString = currentToken.substring(7);
				
					outputString = outputString.replaceAll("\u0008", "\n");
					
					if(frame instanceof DataManFrame){
					
						Cina.dataManDataStructure.setModifyNucDataReport(outputString);
					
					}else if(frame instanceof DataEvalFrame){
					
						Cina.dataEvalDataStructure.setModifyNucDataReport(outputString);
					
					}else if(frame instanceof RateGenFrame){
					
						Cina.rateGenDataStructure.setModifyNucDataReport(outputString);
					
					}
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
	}
	
	public boolean[] parseNUC_DATA_EXISTString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{

					StringTokenizer st09 = new StringTokenizer(currentToken, "\u0009");

					st09.nextToken();
					st09.nextToken();
					st09.nextToken();
					
					String outputString = st09.nextToken();
					
					if(frame instanceof DataManFrame){
					
						if(outputString.equals("EXIST=YES")){
						
							Cina.dataManDataStructure.setCurrentNucDataExists(true);
							
						}else if(outputString.equals("EXIST=NO")){
						
							Cina.dataManDataStructure.setCurrentNucDataExists(false);
						
						}
					}else if(frame instanceof DataEvalFrame){
					
						if(outputString.equals("EXIST=YES")){
						
							Cina.dataEvalDataStructure.setCurrentNucDataExists(true);
							
						}else if(outputString.equals("EXIST=NO")){
						
							Cina.dataEvalDataStructure.setCurrentNucDataExists(false);
						
						}
					}else if(frame instanceof RateGenFrame){
					
						if(outputString.equals("EXIST=YES")){
						
							Cina.rateGenDataStructure.setCurrentNucDataExists(true);
							
						}else if(outputString.equals("EXIST=NO")){
						
							Cina.rateGenDataStructure.setCurrentNucDataExists(false);
						
						}
					}
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseSHARE_NUC_DATA_SETString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REPORT=")){
				
					String outputString = currentToken.substring(7);

					outputString = outputString.replaceAll("\u0008", "\n");
					
					Cina.dataManDataStructure.setSharedNucDataSetReport(outputString);
				
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}

	public boolean[] parseEXPORT_RATE_LIBRARYString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseMAKE_ELEMENT_SYNTHESIS_MOVIEString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
	
		boolean[] booleanArray = new boolean[3];
	
		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		int numberTokens = st.countTokens();

		for(int i=0; i<numberTokens; i++){
			
			currentToken = st.nextToken();
			
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else if(currentToken.startsWith("REPORT=")){
				
					String output = currentToken.substring(7);
				
					output = output.replaceAll("\u0008", "\n");
					
					Cina.elementVizDataStructure.setElementSynthMovieReport(output);
					
				}
			}	
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_FLUX_MAPPINGString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();
		
		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");

		String currentToken = "";

		String tempString2 = string.trim();

		if(tempString2.startsWith("ERROR=")){
		
			Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setHasFluxes(false);
		
		}else{
		
			int numberTokens = st.countTokens();

			for(int i=0; i<numberTokens; i++){
			
				currentToken = st.nextToken();
				
				if(currentToken.startsWith("ERROR=")){
				
					errorVector.addElement(currentToken);
				
				}else{
					
					if(currentToken.startsWith("CAUTION=")){
			
						cautionVector.addElement(currentToken);
			
					}else{
					
						StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
						
						stEquals.nextToken();
						
						String tempString = stEquals.nextToken();
						
						StringTokenizer st09 = new StringTokenizer(tempString, "\u0009");
	
						int numberTokens09 = st09.countTokens();
						
						Point[] outputArray = new Point[numberTokens09];
						
						for(int j=0; j<numberTokens09; j++){
						
							StringTokenizer stComma = new StringTokenizer(st09.nextToken(), ",");
						
							outputArray[j] = new Point(Integer.valueOf(stComma.nextToken()).intValue(), Integer.valueOf(stComma.nextToken()).intValue());
							
						}
					
						Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setHasFluxes(true);
						Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setReactionMapArray(outputArray);
					
					}
				}	
			}
			
			errorVector.trimToSize();
			cautionVector.trimToSize();
			reasonVector.trimToSize();
			
			if(!errorVector.isEmpty()){
			
				createErrorDialog(errorVector, frame);
				booleanArray[0] = true;
				
			}else if(!reasonVector.isEmpty()){
				
				createReasonDialog(reasonVector, frame);
				booleanArray[2] = true;
				
			}else{
				
				if(!cautionVector.isEmpty()){
				
					createCautionDialog(cautionVector, frame);
					booleanArray[1] = true;
				
				}
				
			}
			
		}

		return booleanArray;
		
	}

	public boolean[] parseGET_ELEMENT_SYNTHESIS_FLUXESString(Object object, Frame frame, ProgressBarDialog dialog){
		
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();

		boolean[] booleanArray = new boolean[3];
		
		if(!(object instanceof String)){
		
			if(isotopes.equals("") && dialog!=null){
	
				dialog.setCurrentValue(0);
				dialog.appendText("Parsing reaction flux data for "
									+ Cina.elementVizDataStructure.getCurrentNucSimDataStructure().getNucSimName()
									+ "...");
			}
	
			String currentToken = "";
	
			FluxTimestepDataStructure[] appftdsa = new FluxTimestepDataStructure[Cina.elementVizDataStructure.getCurrentNucSimDataStructure().getTimeArray().length];
	
			int timestep = 0;
	
			BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)object));
	
			int numberBytes = 0;
	
			for(int i=0; i<appftdsa.length; i++){
				
				try{
				
					currentToken = br.readLine();
	
					numberBytes += currentToken.getBytes().length;
	
					if(isotopes.equals("") && dialog!=null){
	
						dialog.setCurrentValue(numberBytes);
					
					}
					
				}catch(IOException ioe){
				
					ioe.printStackTrace();
				
				}
				
				if(currentToken.startsWith("ERROR=")){
				
					errorVector.addElement(currentToken);
				
				}else{
					
					if(currentToken.startsWith("CAUTION=")){
			
						cautionVector.addElement(currentToken);
			
					}else{
					
						StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
						appftdsa[timestep] = new FluxTimestepDataStructure();
					
						if(stEquals.countTokens()>1){
						
							stEquals.nextToken();
							
							String tempString = stEquals.nextToken();
							
							StringTokenizer st09 = new StringTokenizer(tempString, "\u0009");
							
							int numberFlux = st09.countTokens();
							
							float[] outputArrayFlux = new float[numberFlux];
							int[] outputArrayIndex = new int[numberFlux];
							byte[] outputArrayLinestyle = new byte[numberFlux];
							byte[] outputArrayLinewidth = new byte[numberFlux];
							
							for(int j=0; j<numberFlux; j++){
							
								StringTokenizer stComma = new StringTokenizer(st09.nextToken(), ",");
							
								outputArrayIndex[j] = Integer.valueOf(stComma.nextToken()).intValue();
								outputArrayFlux[j] = Float.valueOf(stComma.nextToken()).floatValue();
								outputArrayLinestyle[j] = 0;
								outputArrayLinewidth[j] = 1;
	
							}
							
							appftdsa[timestep].setFluxArray(outputArrayFlux);
							appftdsa[timestep].setIndexArray(outputArrayIndex);
							appftdsa[timestep].setLinewidthArray(outputArrayLinewidth);
							appftdsa[timestep].setLinestyleArray(outputArrayLinestyle);
						
						}else{
						
							float[] outputArrayFlux = new float[1];
							int[] outputArrayIndex = new int[1];
							byte[] outputArrayLinestyle = new byte[1];
							byte[] outputArrayLinewidth = new byte[1];
							
							outputArrayFlux[0] = 0.0f;
							outputArrayIndex[0] = -1;
							outputArrayLinestyle[0] = 0;
							outputArrayLinewidth[0] = 1;
							
							appftdsa[timestep].setFluxArray(outputArrayFlux);
							appftdsa[timestep].setIndexArray(outputArrayIndex);	
							appftdsa[timestep].setLinewidthArray(outputArrayLinewidth);
							appftdsa[timestep].setLinestyleArray(outputArrayLinestyle);
																	
						}
					
						timestep++;
	
					}
				}	
			}
	
			Cina.elementVizDataStructure.getCurrentNucSimDataStructure().setFluxTimestepDataStructureArray(appftdsa);
			
		}else{
		
			errorVector.addElement((String)object);
		
		}
			
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public boolean[] parseGET_ELEMENT_SYNTHESIS_WEIGHTED_ABUNDANCESString(String string, Frame frame){
	
		errorVector.clear();
		cautionVector.clear();
		reasonVector.clear();

		boolean[] booleanArray = new boolean[3];

		StringTokenizer st = new StringTokenizer(string, "\n");
		
		String currentToken = "";

		int numberTokens = st.countTokens();
		
		double[][] zoneArray = new double[numberTokens][];
		double[] finalArray = new double[numberTokens];
		
		Vector zoneNumberVector = new Vector();
		int[] indexArray = new int[numberTokens];
		
		for(int i=0; i<numberTokens; i++){
		
			currentToken = st.nextToken();
		
			if(currentToken.startsWith("ERROR=")){
			
				errorVector.addElement(currentToken);
			
			}else{
				
				if(currentToken.startsWith("CAUTION=")){
		
					cautionVector.addElement(currentToken);
		
				}else{
					
					StringTokenizer stEquals = new StringTokenizer(currentToken, "=");
					
					String indexZoneToken = stEquals.nextToken();
					
					StringTokenizer stComma = new StringTokenizer(indexZoneToken, ",");
					
					indexArray[i] = Integer.valueOf(stComma.nextToken()).intValue();
					zoneArray[i] = new double[Integer.valueOf(stComma.nextToken()).intValue()];
					
					String abundToken = stEquals.nextToken();
					
					StringTokenizer st09 = new StringTokenizer(abundToken, "\u0009");
					
					int numberTokens09 = st09.countTokens();
					
					zoneNumberVector = new Vector();
					
					for(int j=0; j<numberTokens09; j++){
					
						if(j==(numberTokens09-1)){
						
							finalArray[i] = Double.valueOf(st09.nextToken()).doubleValue();
						
						}else{
							
							StringTokenizer stComma2 = new StringTokenizer(st09.nextToken(), ",");
							zoneNumberVector.addElement(stComma2.nextToken());
							zoneArray[i][j] = Double.valueOf(stComma2.nextToken()).doubleValue();
						
						}
					
					}
					
				}
				
			}
			
		}
		
		zoneNumberVector.trimToSize();
		
		int[] zoneNumberArray = new int[zoneNumberVector.size()];
		
		for(int i=0; i<zoneNumberVector.size(); i++){
		
			zoneNumberArray[i] = Integer.valueOf(zoneNumberVector.elementAt(i).toString()).intValue();
		
		}
		
		Cina.elementVizDataStructure.getCurrentNucSimSetDataStructure().setZoneArray(zoneNumberArray);
		Cina.elementVizDataStructure.getCurrentNucSimSetDataStructure().setIndexArray(indexArray);
		Cina.elementVizDataStructure.getCurrentNucSimSetDataStructure().setZoneAbundArray(zoneArray);
		Cina.elementVizDataStructure.getCurrentNucSimSetDataStructure().setFinalAbundArray(finalArray);
		
		TreeMap<Integer, Double> map = new TreeMap<Integer, Double>();
		
		for(int i=0; i<indexArray.length; i++){
			int index = indexArray[i];
			double finalAbund = finalArray[i];
			Point point = Cina.elementVizDataStructure.getCurrentNucSimSetDataStructure().getZAMapArray()[index];
			int a = (int)point.getY();
			if(!map.containsKey(a)){
				map.put(a, finalAbund);
			}else{
				map.put(a, finalAbund + map.get(a));
			}
		}
		
		Iterator<Integer> itr = map.keySet().iterator();
		while(itr.hasNext()){
			int a = itr.next();
			double finalSummedAbund = map.get(a);
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		if(!errorVector.isEmpty()){
		
			createErrorDialog(errorVector, frame);
			booleanArray[0] = true;
			
		}else if(!reasonVector.isEmpty()){
			
			createReasonDialog(reasonVector, frame);
			booleanArray[2] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				createCautionDialog(cautionVector, frame);
				booleanArray[1] = true;
			
			}
			
		}
		
		return booleanArray;
		
	}
	
	public void transferNucDataDataStructure(NucDataDataStructure appndds, Frame frame){
	
		if(frame instanceof DataManFrame 
					&& (Cina.dataManFrame.currentFeatureIndex==1 || Cina.dataManFrame.currentFeatureIndex==1)){
		
			for(int i=0; i<Cina.dataManDataStructure.getCurrentNucDataDataStructureArray().length; i++){
				
				if(Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].getNucDataID().equals(appndds.getNucDataID())){
	
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setReactionString(appndds.getReactionString());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setNucDataName(appndds.getNucDataName());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setNucDataType(appndds.getNucDataType());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setNucDataSet(appndds.getNucDataSet());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setNumberPoints(appndds.getNumberPoints());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setXDataArray(appndds.getXDataArray());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setYDataArray(appndds.getYDataArray());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setReactionType(appndds.getReactionType());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setOrgCode(appndds.getOrgCode());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setPeopleCode(appndds.getPeopleCode());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setNucDataNotes(appndds.getNucDataNotes());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setCreationDate(appndds.getCreationDate());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setRefCitation(appndds.getRefCitation());
					Cina.dataManDataStructure.getCurrentNucDataDataStructureArray()[i].setDecay(appndds.getDecay());
				
				}
			
			}
		
		}else if(frame instanceof DataViewerFrame){
		
			for(int i=0; i<Cina.dataViewerDataStructure.getNumberNucDataSetStructures(); i++){
	
				if(Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()!=null){
			
					for(int j=0; j<Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures().length; j++){
	
						if(Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].getNucDataID().equals(appndds.getNucDataID())){

							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setReactionString(appndds.getReactionString());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setNucDataName(appndds.getNucDataName());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setNucDataSet(appndds.getNucDataSet());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setNucDataType(appndds.getNucDataType());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setNumberPoints(appndds.getNumberPoints());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setXDataArray(appndds.getXDataArray());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setYDataArray(appndds.getYDataArray());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setReactionType(appndds.getReactionType());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setOrgCode(appndds.getOrgCode());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setPeopleCode(appndds.getPeopleCode());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setNucDataNotes(appndds.getNucDataNotes());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setCreationDate(appndds.getCreationDate());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setRefCitation(appndds.getRefCitation());
							Cina.dataViewerDataStructure.getNucDataSetStructureArray()[i].getNucDataDataStructures()[j].setDecay(appndds.getDecay());
		
						}
					
					}
				
				}
				
			}
		
		}else if(frame instanceof DataEvalFrame 
					&& Cina.dataEvalFrame.currentFeatureIndex==1
					&& Cina.dataEvalFrame.currentPanelIndex==1){

			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.dataEvalDataStructure.getInter1NucDataDataStructure().setDecay(appndds.getDecay());

		}else if(frame instanceof DataEvalFrame 
					&& Cina.dataEvalFrame.currentFeatureIndex==1
					&& Cina.dataEvalFrame.currentPanelIndex==2){

			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.dataEvalDataStructure.getInter2NucDataDataStructure().setDecay(appndds.getDecay());

		}else if(frame instanceof DataEvalFrame 
					&& Cina.dataEvalFrame.currentFeatureIndex==2
					&& Cina.dataEvalFrame.currentPanelIndex==1){

			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.dataEvalDataStructure.getTrans1NucDataDataStructure().setDecay(appndds.getDecay());

		}else if(frame instanceof DataEvalFrame 
					&& Cina.dataEvalFrame.currentFeatureIndex==3
					&& Cina.dataEvalFrame.currentPanelIndex==1){

			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.dataEvalDataStructure.getExtra1NucDataDataStructure().setDecay(appndds.getDecay());

		}else if(frame instanceof RateGenFrame){

			Cina.rateGenDataStructure.getNucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.rateGenDataStructure.getNucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.rateGenDataStructure.getNucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.rateGenDataStructure.getNucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.rateGenDataStructure.getNucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.rateGenDataStructure.getNucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.rateGenDataStructure.getNucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.rateGenDataStructure.getNucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.rateGenDataStructure.getNucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.rateGenDataStructure.getNucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.rateGenDataStructure.getNucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.rateGenDataStructure.getNucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.rateGenDataStructure.getNucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.rateGenDataStructure.getNucDataDataStructure().setDecay(appndds.getDecay());

		}else if(frame instanceof RateParamFrame){

			Cina.rateParamDataStructure.getNucDataDataStructure().setReactionString(appndds.getReactionString());
			Cina.rateParamDataStructure.getNucDataDataStructure().setNucDataName(appndds.getNucDataName());
			Cina.rateParamDataStructure.getNucDataDataStructure().setNucDataSet(appndds.getNucDataSet());
			Cina.rateParamDataStructure.getNucDataDataStructure().setNucDataType(appndds.getNucDataType());
			Cina.rateParamDataStructure.getNucDataDataStructure().setNumberPoints(appndds.getNumberPoints());
			Cina.rateParamDataStructure.getNucDataDataStructure().setXDataArray(appndds.getXDataArray());
			Cina.rateParamDataStructure.getNucDataDataStructure().setYDataArray(appndds.getYDataArray());
			Cina.rateParamDataStructure.getNucDataDataStructure().setReactionType(appndds.getReactionType());
			Cina.rateParamDataStructure.getNucDataDataStructure().setOrgCode(appndds.getOrgCode());
			Cina.rateParamDataStructure.getNucDataDataStructure().setPeopleCode(appndds.getPeopleCode());
			Cina.rateParamDataStructure.getNucDataDataStructure().setNucDataNotes(appndds.getNucDataNotes());
			Cina.rateParamDataStructure.getNucDataDataStructure().setCreationDate(appndds.getCreationDate());
			Cina.rateParamDataStructure.getNucDataDataStructure().setRefCitation(appndds.getRefCitation());
			Cina.rateParamDataStructure.getNucDataDataStructure().setDecay(appndds.getDecay());

		}
		
	}
	
	public void transferRateDataStructure(RateDataStructure apprds, Frame frame){
	
		if(frame instanceof ElementSynthFrame){
			Cina.elementSynthDataStructure.setRateDataStructure(new RateDataStructure());
			RateDataStructure esrds = Cina.elementSynthDataStructure.getRateDataStructure();
			esrds.setReactionString(apprds.getReactionString());
			esrds.setNumberParameters(apprds.getNumberParameters());
			esrds.setParameters(apprds.getParameters());
			esrds.setResonantInfo(apprds.getResonantInfo());
			esrds.setParameterCompArray(apprds.getParameterCompArray());
			esrds.setReactionType(apprds.getReactionType());
			esrds.setBiblioString(apprds.getBiblioString());
			esrds.setReactionNotes(apprds.getReactionNotes());
			esrds.setQValue(apprds.getQValue());
			esrds.setChisquared(apprds.getChisquared());
			esrds.setCreationDate(apprds.getCreationDate());
			esrds.setMaxPercentDiff(apprds.getMaxPercentDiff());
			esrds.setRefCitation(apprds.getRefCitation());
			esrds.setValidTempRange(apprds.getValidTempRange());
			esrds.setDecay(apprds.getDecay());
			esrds.setInverse(apprds.getInverse());
		}else if(frame instanceof RateViewerFrame){
			
			for(int i=0; i<Cina.rateViewerDataStructure.getNumberLibraryStructures(); i++){
	
				if(Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()!=null){
			
					for(int j=0; j<Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures().length; j++){
	
						if(Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].getReactionID().equals(apprds.getReactionID())){
	
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setReactionString(apprds.getReactionString());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setNumberParameters(apprds.getNumberParameters());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setParameters(apprds.getParameters());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setResonantInfo(apprds.getResonantInfo());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setParameterCompArray(apprds.getParameterCompArray());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setReactionType(apprds.getReactionType());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setBiblioString(apprds.getBiblioString());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setReactionNotes(apprds.getReactionNotes());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setQValue(apprds.getQValue());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setChisquared(apprds.getChisquared());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setCreationDate(apprds.getCreationDate());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setMaxPercentDiff(apprds.getMaxPercentDiff());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setRefCitation(apprds.getRefCitation());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setValidTempRange(apprds.getValidTempRange());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setDecay(apprds.getDecay());
							Cina.rateViewerDataStructure.getLibraryStructureArray()[i].getRateDataStructures()[j].setInverse(apprds.getInverse());
		
						}
					
					}
				
				}
			
			}
		
		}else if(frame instanceof RateManFrame 
					&& ((Cina.rateManFrame.currentFeatureIndex==1 && Cina.rateManFrame.currentPanelIndex==1)
					|| Cina.rateManFrame.currentFeatureIndex==4)){
		
			for(int i=0; i<Cina.rateManDataStructure.getCurrentRateDataStructureArray().length; i++){
				
				if(Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].getReactionID().equals(apprds.getReactionID())){
	
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setReactionString(apprds.getReactionString());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setNumberParameters(apprds.getNumberParameters());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setParameters(apprds.getParameters());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setResonantInfo(apprds.getResonantInfo());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setParameterCompArray(apprds.getParameterCompArray());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setReactionType(apprds.getReactionType());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setBiblioString(apprds.getBiblioString());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setReactionNotes(apprds.getReactionNotes());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setQValue(apprds.getQValue());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setChisquared(apprds.getChisquared());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setCreationDate(apprds.getCreationDate());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setMaxPercentDiff(apprds.getMaxPercentDiff());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setRefCitation(apprds.getRefCitation());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setValidTempRange(apprds.getValidTempRange());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setDecay(apprds.getDecay());
					Cina.rateManDataStructure.getCurrentRateDataStructureArray()[i].setInverse(apprds.getInverse());
				
				}
			
			}
		
		}else if(frame instanceof RateManFrame 
					&& Cina.rateManFrame.currentFeatureIndex==6){
		
			for(int i=0; i<Cina.rateManDataStructure.getInvestRateDataStructureArray().length; i++){
				
				if(Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].getReactionID().equals(apprds.getReactionID())){
	
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setReactionString(apprds.getReactionString());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setNumberParameters(apprds.getNumberParameters());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setParameters(apprds.getParameters());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setResonantInfo(apprds.getResonantInfo());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setParameterCompArray(apprds.getParameterCompArray());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setReactionType(apprds.getReactionType());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setBiblioString(apprds.getBiblioString());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setReactionNotes(apprds.getReactionNotes());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setQValue(apprds.getQValue());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setChisquared(apprds.getChisquared());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setCreationDate(apprds.getCreationDate());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setMaxPercentDiff(apprds.getMaxPercentDiff());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setRefCitation(apprds.getRefCitation());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setValidTempRange(apprds.getValidTempRange());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setDecay(apprds.getDecay());
					Cina.rateManDataStructure.getInvestRateDataStructureArray()[i].setInverse(apprds.getInverse());
				
				}
			
			}
		
		}else if(frame instanceof RateManFrame 
					&& Cina.rateManFrame.currentFeatureIndex==1
					&& Cina.rateManFrame.currentPanelIndex==2){
		
			for(int i=0; i<Cina.rateManDataStructure.getAboutRateDataStructureVectorArray().length; i++){
				
				for(int j=0; j<Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].size(); j++){

					if(((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).getReactionID().equals(apprds.getReactionID())){

						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setReactionString(apprds.getReactionString());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setNumberParameters(apprds.getNumberParameters());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setParameters(apprds.getParameters());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setResonantInfo(apprds.getResonantInfo());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setParameterCompArray(apprds.getParameterCompArray());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setReactionType(apprds.getReactionType());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setBiblioString(apprds.getBiblioString());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setReactionNotes(apprds.getReactionNotes());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setQValue(apprds.getQValue());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setChisquared(apprds.getChisquared());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setCreationDate(apprds.getCreationDate());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setMaxPercentDiff(apprds.getMaxPercentDiff());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setRefCitation(apprds.getRefCitation());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setValidTempRange(apprds.getValidTempRange());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setDecay(apprds.getDecay());
						((RateDataStructure)(Cina.rateManDataStructure.getAboutRateDataStructureVectorArray()[i].elementAt(j))).setInverse(apprds.getInverse());
					
					}

				}

			}
		
		}else if(frame instanceof RateViewerPlotFrame){
		
			for(int i=0; i<Cina.rateViewerDataStructure.getRateDataStructureVectorArray().length; i++){
				
				for(int j=0; j<Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].size(); j++){

					if(((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).getReactionID().equals(apprds.getReactionID())){

						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setReactionString(apprds.getReactionString());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setNumberParameters(apprds.getNumberParameters());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setParameters(apprds.getParameters());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setResonantInfo(apprds.getResonantInfo());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setParameterCompArray(apprds.getParameterCompArray());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setReactionType(apprds.getReactionType());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setBiblioString(apprds.getBiblioString());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setReactionNotes(apprds.getReactionNotes());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setQValue(apprds.getQValue());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setChisquared(apprds.getChisquared());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setCreationDate(apprds.getCreationDate());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setMaxPercentDiff(apprds.getMaxPercentDiff());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setRefCitation(apprds.getRefCitation());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setValidTempRange(apprds.getValidTempRange());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setDecay(apprds.getDecay());
						((RateDataStructure)(Cina.rateViewerDataStructure.getRateDataStructureVectorArray()[i].elementAt(j))).setInverse(apprds.getInverse());
					
					}

				}

			}
		
		}else if(frame instanceof RateManFrame && Cina.rateManFrame.currentFeatureIndex==3){

			Cina.rateManDataStructure.getModifyRateDataStructure().setReactionString(apprds.getReactionString());
			Cina.rateManDataStructure.getModifyRateDataStructure().setNumberParameters(apprds.getNumberParameters());
			Cina.rateManDataStructure.getModifyRateDataStructure().setParameters(apprds.getParameters());
			Cina.rateManDataStructure.getModifyRateDataStructure().setResonantInfo(apprds.getResonantInfo());
			Cina.rateManDataStructure.getModifyRateDataStructure().setParameterCompArray(apprds.getParameterCompArray());
			Cina.rateManDataStructure.getModifyRateDataStructure().setReactionType(apprds.getReactionType());
			Cina.rateManDataStructure.getModifyRateDataStructure().setBiblioString(apprds.getBiblioString());
			Cina.rateManDataStructure.getModifyRateDataStructure().setReactionNotes(apprds.getReactionNotes());
			Cina.rateManDataStructure.getModifyRateDataStructure().setQValue(apprds.getQValue());
			Cina.rateManDataStructure.getModifyRateDataStructure().setChisquared(apprds.getChisquared());
			Cina.rateManDataStructure.getModifyRateDataStructure().setCreationDate(apprds.getCreationDate());
			Cina.rateManDataStructure.getModifyRateDataStructure().setMaxPercentDiff(apprds.getMaxPercentDiff());
			Cina.rateManDataStructure.getModifyRateDataStructure().setRefCitation(apprds.getRefCitation());
			Cina.rateManDataStructure.getModifyRateDataStructure().setValidTempRange(apprds.getValidTempRange());
			Cina.rateManDataStructure.getModifyRateDataStructure().setInverse(apprds.getInverse());
			Cina.rateManDataStructure.getModifyRateDataStructure().setDecay(apprds.getDecay());
		
		}else if(frame instanceof RateParamStartFrame){

			Cina.rateParamDataStructure.setStartingParameters(apprds.getParameters());

		}else if(frame instanceof ElementVizPointFrame){
			HashMap<String, RateDataStructure> rateMap = Cina.elementVizDataStructure.map.get(Cina.elementVizDataStructure.zaIndex);
			RateDataStructure rds = rateMap.get(apprds.getReactionID());
			rds.setParameters(apprds.getParameters());
			rds.setNumberParameters(apprds.getNumberParameters());
			rds.setQValue(apprds.getQValue());
		}

	}

	public void createMessageDialog(String messageString, Frame frame){
		
		messageDialog = new JDialog(frame, "Message", false);
    	messageDialog.setSize(350, 210);
    	messageDialog.getContentPane().setLayout(new GridBagLayout());
		messageDialog.setLocationRelativeTo(frame);
		
		JTextArea messageTextArea = new JTextArea("", 5, 30);
		messageTextArea.setLineWrap(true);
		messageTextArea.setWrapStyleWord(true);
		messageTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(messageTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		messageTextArea.setText(messageString);
		
		messageTextArea.setCaretPosition(0);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		messageDialog.getContentPane().add(sp, gbc);
		
		//Cina.setFrameColors(messageDialog);
		
		messageDialog.setVisible(true);	
		
		messageDialog.validate();
		
	}

	public void createErrorDialog(Vector viktor, Frame frame){
    	
    	//Create a new JDialog object
    	errorDialog = new JDialog(frame, "Error!", true);
    	errorDialog.setSize(350, 210);
    	errorDialog.getContentPane().setLayout(new GridBagLayout());
		errorDialog.setLocationRelativeTo(frame);
		
		errorTextArea = new JTextArea("", 5, 30);
		errorTextArea.setLineWrap(true);
		errorTextArea.setWrapStyleWord(true);
		errorTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(errorTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String errorString = "";
		
		for(int i=0; i<viktor.size(); i++){
			
			errorString = errorString + ((String)viktor.elementAt(i)).substring(6) + "\n\n";
			
		}
		
		errorString = errorString.replaceAll("\u0008", "\u0010");
		
		errorTextArea.setText(errorString);
		
		errorTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		okButton = new JButton("Ok");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		
		errorDialog.getContentPane().add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.insets = new Insets(5, 3, 0, 3);
		errorDialog.getContentPane().add(okButton, gbc);
		
		//Cina.setFrameColors(errorDialog);
		
		//Show the dialog
		errorDialog.setVisible(true);
	
	}
	
	public void createCautionDialog(Vector viktor, Frame frame){
	
		//Create a new JDialog object
    	cautionDialog = new JDialog(frame, "Caution!", true);
    	cautionDialog.setSize(320, 215);
    	cautionDialog.getContentPane().setLayout(new GridBagLayout());
		cautionDialog.setLocationRelativeTo(frame);
		
		cautionTextArea = new JTextArea("", 5, 30);
		cautionTextArea.setLineWrap(true);
		cautionTextArea.setWrapStyleWord(true);
		cautionTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(cautionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		
		String cautionString = "";
		
		for(int i=0; i<viktor.size(); i++){
			
			cautionString = cautionString + ((String)viktor.elementAt(i)).substring(8) + "\n\n";
			
		}
		
		cautionTextArea.setText(cautionString);
		
		cautionTextArea.setCaretPosition(0);
		
		cautionLabel = new JLabel("Do you wish to continue?");
		//cautionLabel.setFont(CinaFonts.textFont);	
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(this);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(this);
		
		cautionButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		
		cautionButtonPanel.add(yesButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		cautionButtonPanel.add(noButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		
		cautionDialog.getContentPane().add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		cautionDialog.getContentPane().add(cautionLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		cautionDialog.getContentPane().add(cautionButtonPanel, gbc);
		
		//Cina.setFrameColors(cautionDialog);

		cautionDialog.setVisible(true);
	
	}
	
	public void createReasonDialog(Vector viktor, Frame frame){
    	
    	//Create a new JDialog object
    	reasonDialog = new JDialog(frame, "Attention!", true);
    	reasonDialog.setSize(350, 210);
    	reasonDialog.getContentPane().setLayout(new GridBagLayout());
		reasonDialog.setLocationRelativeTo(frame);
		
		reasonTextArea = new JTextArea("", 5, 30);
		reasonTextArea.setLineWrap(true);
		reasonTextArea.setWrapStyleWord(true);
		reasonTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(reasonTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String reasonString = "";
		
		for(int i=0; i<viktor.size(); i++){
			
			reasonString = reasonString + (String)viktor.elementAt(i) + "\n\n";
			
		}
		
		reasonTextArea.setText(reasonString);
		
		reasonTextArea.setCaretPosition(0);
		
		//Create submit button and its properties
		okButtonReason = new JButton("Ok");
		okButtonReason.setFont(Fonts.buttonFont);
		okButtonReason.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 3);
		
		reasonDialog.getContentPane().add(sp, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		reasonDialog.getContentPane().add(okButtonReason, gbc);
		
		//Cina.setFrameColors(reasonDialog);
		
		//Show the dialog
		reasonDialog.setVisible(true);
	
	}
	
	public void actionPerformed(ActionEvent ae){
	    	
		if(ae.getSource()==okButton){
		
			errorDialog.setVisible(false);
			errorDialog.dispose();
		
		}else if(ae.getSource()==noButton){
			
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
		
			if(Cina.elementSynthFrame.getPanelIndex()==1){
			
				Cina.elementSynthFrame.setContinueEnabled(true);
				
			}
		
		}else if(ae.getSource()==okButtonReason){
			
			reasonDialog.setVisible(false);
			reasonDialog.dispose();

		}else if(ae.getSource()==yesButton){
		
			cautionDialog.setVisible(false);
			cautionDialog.dispose();
			
			if(Cina.rateParamFrame.currentPanelIndex==2){
			
				//Remove it from the frame
				Cina.rateParamFrame.c.remove(Cina.rateParamFrame.rateParamReadInputPanel);
				
				//Create the input check panel
				Cina.rateParamFrame.rateParamInputCheckPanel = new RateParamInputCheckPanel(Cina.rateParamDataStructure);
				
				//Add it to the frame
				Cina.rateParamFrame.c.add(Cina.rateParamFrame.rateParamInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(Cina.rateParamFrame.rateParamInputCheckPlotFrame==null){
					
					Cina.rateParamFrame.rateParamInputCheckPlotFrame = new RateParamInputCheckPlotFrame(Cina.rateParamDataStructure);
					Cina.rateParamFrame.rateParamInputCheckPlotFrame.setFormatPanelState();
				
				}else{
					
					Cina.rateParamFrame.rateParamInputCheckPlotFrame.setFormatPanelState();
					Cina.rateParamFrame.rateParamInputCheckPlotFrame.rateParamInputCheckPlotCanvas.setPlotState();
					Cina.rateParamFrame.rateParamInputCheckPlotFrame.setVisible(true);
				
				}
			
				//Set its current state relative to the data structure
				Cina.rateParamFrame.rateParamInputCheckPanel.setCurrentState();
			
			}else if(Cina.rateGenFrame.currentPanelIndex==1){
			
				//Remove the rate ID JPanel from frame
				Cina.rateGenFrame.remove(Cina.rateGenFrame.rateGenIDPanel);
				
				//Create read input panel
				Cina.rateGenFrame.rateGenReadInputPanel = new RateGenReadInputPanel(Cina.rateGenDataStructure);
				
				//Add it to the frame
				Cina.rateGenFrame.c.add(Cina.rateGenFrame.rateGenReadInputPanel, BorderLayout.CENTER);
				
				//Set the read input panel's current state
				Cina.rateGenFrame.rateGenReadInputPanel.setCurrentState();
				
				Cina.rateGenFrame.validate();
				
			}else if(Cina.rateGenFrame.currentPanelIndex==2){
				
				//Remove it from the frame
				Cina.rateGenFrame.remove(Cina.rateGenFrame.rateGenReadInputPanel);
				
				//Create the input check panel
				Cina.rateGenFrame.rateGenInputCheckPanel = new RateGenInputCheckPanel(Cina.rateGenDataStructure);
				
				//Add it to the frame
				Cina.rateGenFrame.c.add(Cina.rateGenFrame.rateGenInputCheckPanel, BorderLayout.CENTER);
				
				//Create output plot to echo info back to user before
				//implementing preprocessing
				if(Cina.rateGenFrame.rateGenInputCheckPlotFrame==null){
					
					Cina.rateGenFrame.rateGenInputCheckPlotFrame = new RateGenInputCheckPlotFrame(Cina.rateGenDataStructure);
				
				}else{
					
					Cina.rateGenFrame.rateGenInputCheckPlotFrame.reinitialize();
					Cina.rateGenFrame.rateGenInputCheckPlotFrame.setFormatPanelState();
					Cina.rateGenFrame.rateGenInputCheckPlotFrame.rateGenInputCheckPlotCanvas.setPlotState();
					Cina.rateGenFrame.rateGenInputCheckPlotFrame.setVisible(true);
				
				}
				
				//Set its current state relative to the data structure
				Cina.rateGenFrame.rateGenInputCheckPanel.setCurrentState();
				
				Cina.rateGenFrame.validate();
			
			}else if(Cina.rateManFrame.currentFeatureIndex==2 && Cina.rateManFrame.currentPanelIndex==1){

				Cina.rateManFrame.rateManCreateRate1Panel.getCurrentState();

				Cina.rateManFrame.c.remove(Cina.rateManFrame.rateManCreateRate1Panel);
			
				Cina.rateManFrame.rateManCreateRate2Panel = new RateManCreateRate2Panel(Cina.rateManDataStructure);
				
				Cina.rateManFrame.rateManCreateRate2Panel.setCurrentState();

				Cina.rateManFrame.c.add(Cina.rateManFrame.rateManCreateRate2Panel, BorderLayout.CENTER);
				
			}else if(Cina.rateManFrame.currentFeatureIndex==6 && Cina.rateManFrame.currentPanelIndex==2){

				Cina.rateManDataStructure.setLibGroup("PUBLIC");
				boolean goodPublicLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
				
				Cina.rateManDataStructure.setLibGroup("SHARED");
				boolean goodSharedLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
				
				Cina.rateManDataStructure.setLibGroup("USER");
				boolean goodUserLibraries = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", Cina.rateManFrame);
				
				if(goodPublicLibraries && goodSharedLibraries && goodUserLibraries){
	
					Cina.rateManDataStructure.setRates(Cina.rateManFrame.rateManInvestRate2ZAPanel.getRateIDList());
	
					if(Cina.cinaCGIComm.doCGICall("RATES EXIST", Cina.rateManFrame)){
				
						Cina.rateManDataStructure.setRates(Cina.rateManFrame.rateManInvestRate2ZAPanel.getGoodRateIDList());

						Cina.rateManDataStructure.setRateProperties("Reaction String"
				        																+ "\u0009Number of Parameters"
				        																+ "\u0009Parameters"
				        																+ "\u0009Resonant Components"
				        																+ "\u0009Reaction Type"
				        																+ "\u0009Biblio Code"
				        																+ "\u0009Reaction Notes"
				        																+ "\u0009Q-value"
				        																+ "\u0009Chisquared"
				        																+ "\u0009Creation Date"
				        																+ "\u0009Max Percent Difference"
				        																+ "\u0009Reference Citation"
				        																+ "\u0009Valid Temp Range");
				
						if(Cina.cinaCGIComm.doCGICall("GET RATE INFO", Cina.rateManFrame)){

							Cina.rateManFrame.rateManInvestRate2ZAPanel.getCurrentState();
						
							Cina.rateManFrame.rateManInvestRate3Panel = new RateManInvestRate3Panel(Cina.rateManDataStructure);
						
							Cina.rateManFrame.c.remove(Cina.rateManFrame.rateManInvestRate2ZAPanel);
							
							Cina.rateManFrame.rateManInvestRate3Panel.setInvestRateVectorArray();
							
							Cina.rateManFrame.rateManInvestRate3Panel.setCurrentState();
		
							Cina.rateManFrame.removeFullButtonPanel();
							
							Cina.rateManFrame.addEndButtonPanel();
		
							Cina.rateManFrame.c.add(Cina.rateManFrame.rateManInvestRate3Panel, BorderLayout.CENTER);
						
						}
					
					}
				
				}
				

			}else if(Cina.dataManFrame.currentFeatureIndex==2 && Cina.dataManFrame.currentPanelIndex==1){
			
				Cina.dataManFrame.c.remove(Cina.dataManFrame.dataManImportData1Panel);
			
				Cina.dataManFrame.dataManImportData2Panel = new DataManImportData2Panel(Cina.dataManDataStructure);
				
				Cina.dataManFrame.dataManImportData2Panel.setCurrentState();
	
				Cina.dataManFrame.c.add(Cina.dataManFrame.dataManImportData2Panel, BorderLayout.CENTER);
				
			}
		}
	}
	
	public String doGET_ID(String header
							, String action
							, String user
							, String pw){
								
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ actionString
					+ "="
					+ "GET+ID"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doCHECK_REACTION(String header
									, String id
									, String action
									, String user
									, String pw
									, String reaction
									, String reaction_type){
										
		String string = "";
		
		
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "CHECK+REACTION"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ reactionString
					+ "="
					+ reaction
					+ "&"
					+ reaction_typeString
					+ "="
					+ reaction_type;

		return string;
	
	}
	
	public String doREAD_INPUT(String header
								, String id
								, String action
								, String user
								, String pw
								, String reaction
								, String reaction_type
								, String notes
								, String type
								, String format
								, String xunits
								, String yunits
								, String filename
								, String file){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "READ+INPUT"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ reactionString
					+ "="
					+ reaction
					+ "&"
					+ reaction_typeString
					+ "="
					+ reaction_type
					+ "&"
					+ notesString
					+ "="
					+ notes
					+ "&"
					+ typeString
					+ "="
					+ type 
					+ "&"
					+ formatString
					+ "="
					+ format
					+ "&"
					+ xunitsString
					+ "="
					+ xunits
					+ "&"
					+ yunitsString
					+ "="
					+ yunits
					+ "&"
					+ filenameString
					+ "="
					+ filename
					+ "&"
					+ fileString
					+ "="
					+ file;

		return string;
	
	}
	
	public String doPREPROCESSING(String header
									, String id
									, String action
									, String user
									, String pw
									, String positive_chk
									, String single_chk
									, String range_chk
									, String continuity_chk
									, String error_chk
									, String reaction_chk){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "PREPROCESSING"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ positive_chkString
					+ "="
					+ positive_chk
					+ "&"
					+ single_chkString
					+ "="
					+ single_chk
					+ "&"
					+ range_chkString
					+ "="
					+ range_chk
					+ "&"
					+ continuity_chkString
					+ "="
					+ continuity_chk
					+ "&"
					+ error_chkString
					+ "="
					+ error_chk
					+ "&"
					+ reaction_chkString
					+ "="
					+ reaction_chk;
							
		return string;
	
	}
	
	public String doGENERATE_RATE(String header
									, String id
									, String action
									, String user
									, String pw
									, String tmin
									, String tmax
									, String plevel){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GENERATE+RATE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ tminString
					+ "="
					+ tmin
					+ "&"
					+ tmaxString
					+ "="
					+ tmax
					+ "&"
					+ plevelString
					+ "="
					+ plevel;
		
		return string;
	
	}
	
	public String doABORT_RATE_GENERATION(String header
											, String id
											, String action
											, String user
											, String pw){
												
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "ABORT+RATE+GENERATION"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_GENERATION_UPDATE(String header
											, String id
											, String action
											, String user
											, String pw){
												
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+GENERATION+UPDATE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_GENERATION_STATUS_FILE(String header
													, String id
													, String action
													, String user
													, String pw){
														
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+GENERATION+STATUS+FILE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_GENERATION_OUTPUT(String header
											, String id
											, String action
											, String user
											, String pw){
												
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+GENERATION+OUTPUT"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doPARAMETERIZE_RATE(String header
										, String id
										, String action
										, String user
										, String pw
										, String method
										, String iterations
										, String tminparam
										, String tmaxparam
										, String plevelparam
										, String s_parm_num
										, String parm_check
										, String max_diff
										, String start_parms){
											
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "PARAMETERIZE+RATE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ methodString
					+ "="
					+ method
					+ "&"
					+ iterationsString
					+ "="
					+ iterations
					+ "&"
					+ tminString
					+ "="
					+ tminparam
					+ "&"
					+ tmaxString
					+ "="
					+ tmaxparam
					+ "&"
					+ plevelString
					+ "="
					+ plevelparam
					+ "&"
					+ s_parm_numString
					+ "="
					+ s_parm_num
					+ "&"
					+ parm_checkString
					+ "="
					+ parm_check
					+ "&"
					+ max_diffString
					+ "="
					+ max_diff
					+ "&"
					+ start_parmsString
					+ "="
					+ start_parms;
		
		return string;
	
	}

	public String doABORT_RATE_PARAMETERIZATION(String header
													, String id
													, String action
													, String user
													, String pw){
														
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "ABORT+RATE+PARAMETERIZATION"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_PARAMETERIZATION_UPDATE(String header
													, String id
													, String action
													, String user
													, String pw){
														
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+PARAMETERIZATION+UPDATE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_PARAMETERIZATION_STATUS_FILE(String header
														, String id
														, String action
														, String user
														, String pw){
															
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+PARAMETERIZATION+STATUS+FILE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doRATE_PARAMETERIZATION_OUTPUT(String header
													, String id
													, String action
													, String user
													, String pw){
														
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATE+PARAMETERIZATION+OUTPUT"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doINVERSE_PARAMETERS(String header
												, String id
												, String action
												, String user
												, String pw){
														
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "INVERSE+PARAMETERS"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doGENERATE_PARAMETER_FORMAT(String header
												, String id
												, String action
												, String user
												, String pw
												, String body){
													
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GENERATE+PARAMETER+FORMAT"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ bodyString
					+ "="
					+ body;
		
		return string;
	
	}
	
	public String doLOGOUT(String header
								, String id
								, String action
								, String user
								, String pw){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "LOGOUT"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw;
		
		return string;
	
	}
	
	public String doGET_RATE_LIBRARY_LIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String group){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+RATE+LIBRARY+LIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ groupString
					+ "="
					+ group;
		
		return string;
	
	}

	public String doGET_RATE_LIBRARY_ISOTOPES(String header
								, String id
								, String action
								, String user
								, String pw
								, String library){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+RATE+LIBRARY+ISOTOPES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library;

		return string;
	
	}

	public String doGET_RATE_LIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String library
								, String isotope
								, String typeDatabase){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+RATE+LIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library
					+ "&"
					+ isotopeString
					+ "="
					+ isotope
					+ "&"
					+ typeDatabaseString
					+ "="
					+ typeDatabase;
		
		return string;
	
	}

	public String doMODIFY_RATE_LIBRARY(String header
								, String id
								, String action
								, String user
								, String pw
								, String src_lib
								, String dest_lib
								, String dest_group
								, String chk_temp_behavior
								, String chk_overflow
								, String chk_inverse
								, String del_src_lib){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "MODIFY+RATE+LIBRARY"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ src_libString
					+ "="
					+ src_lib
					+ "&"
					+ dest_libString
					+ "="
					+ dest_lib
					+ "&"
					+ dest_groupString
					+ "="
					+ dest_group
					+ "&"
					+ chk_temp_behaviorString
					+ "="
					+ chk_temp_behavior
					+ "&"
					+ chk_overflowString
					+ "="
					+ chk_overflow
					+ "&"
					+ chk_inverseString
					+ "="
					+ chk_inverse
					+ "&"
					+ del_src_libString
					+ "="
					+ del_src_lib;
		
		return string;
	
	}
	
	public String doMODIFY_RATES(String header
								, String id
								, String action
								, String user
								, String pw
								, String rates
								, String dest_lib
								, String chk_temp_behavior
								, String chk_overflow
								, String chk_inverse 
								, String properties
								, String make_inverse){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "MODIFY+RATES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ ratesString
					+ "="
					+ rates
					+ "&"
					+ dest_libString
					+ "="
					+ dest_lib
					+ "&"
					+ chk_temp_behaviorString
					+ "="
					+ chk_temp_behavior
					+ "&"
					+ chk_overflowString
					+ "="
					+ chk_overflow
					+ "&"
					+ chk_inverseString
					+ "="
					+ chk_inverse
					+ "&"
					+ propertiesString
					+ "="
					+ properties
					+ "&"
					+ make_inverseString
					+ "="
					+ make_inverse;

		return string;
	
	}
	
	public String doGET_RATE_LIBRARY_INFO(String header
								, String id
								, String action
								, String user
								, String pw
								, String library){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+RATE+LIBRARY+INFO"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library;

		
		return string;
	
	}

	public String doGET_RATE_INFO(String header
								, String id
								, String action
								, String user
								, String pw
								, String rates
								, String properties){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+RATE+INFO"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ ratesString
					+ "="
					+ rates
					+ "&"
					+ propertiesString
					+ "="
					+ properties;

		return string;
	
	}

	public String doREGISTER(String header
								, String id
								, String action
								, String user
								, String pw
								, String firstname
								, String lastname
								, String email
								, String institution
								, String address
								, String research
								, String hear_of_suite
								, String info){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "REGISTER"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ firstnameString
					+ "="
					+ firstname
					+ "&"
					+ lastnameString
					+ "="
					+ lastname
					+ "&"
					+ emailString
					+ "="
					+ email
					+ "&"
					+ institutionString
					+ "="
					+ institution
					+ "&"
					+ addressString
					+ "="
					+ address
					+ "&"
					+ researchString
					+ "="
					+ research
					+ "&"
					+ hear_of_suiteString
					+ "="
					+ hear_of_suite
					+ "&"
					+ infoString
					+ "="
					+ info;

		
		return string;
	
	}
	
	public String doRATES_EXIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String rates){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "RATES+EXIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ ratesString
					+ "="
					+ rates;
			
		return string;
	
	}

	public String doADD_MISSING_INV_RATES(String header
								, String id
								, String action
								, String user
								, String pw
								, String library){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "ADD+MISSING+INV+RATES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library;
			
		return string;
	
	}

	public String doGET_INVERSE_REACTION(String header
								, String id
								, String action
								, String user
								, String pw
								, String reaction
								, String reaction_type){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+INVERSE+REACTION"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ reactionString
					+ "="
					+ reaction
					+ "&"
					+ reaction_typeString
					+ "="
					+ reaction_type;
			
		return string;
	
	}

	public String doSHARE_RATE_LIBRARY(String header
								, String id
								, String action
								, String user
								, String pw
								, String library){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "SHARE+RATE+LIBRARY"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library;
			
		return string;
	
	}
	
	public String doGET_ELEMENT_SYNTHESIS_TIME_MAPPING(String header
									, String id
									, String action
									, String user
									, String pw
									, String path
									, String zones){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+TIME+MAPPING"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zonesString
					+ "="
					+ zones;

		return string;
	
	}
	
	public String doGET_ELEMENT_SYNTHESIS_ISOTOPE_MAPPING(String header
									, String id
									, String action
									, String user
									, String pw
									, String path){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+ISOTOPE+MAPPING"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zonesString
					+ "="
					+ zones;

		return string;
	
	}

	public String doGET_ELEMENT_SYNTHESIS_THERMO_PROFILE(String header
									, String id
									, String action
									, String user
									, String pw
									, String path
									, String zones){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+THERMO+PROFILE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zonesString
					+ "="
					+ zones;

		return string;
	
	}
	
	public String doGET_ELEMENT_SYNTHESIS_ABUNDANCES(String header
									, String id
									, String action
									, String user
									, String pw
									, String path
									, String zone
									, String isotopes
									, String final_step){
										
		String string = "";

		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+ABUNDANCES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zoneString
					+ "="
					+ zone
					+ "&"
					+ isotopesString
					+ "="
					+ isotopes
					+ "&"
					+ final_stepString
					+ "="
					+ final_step;

		return string;
	
	}
	
	public String doGET_NUC_DATA_SET_LIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String group){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+NUC+DATA+SET+LIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ groupString
					+ "="
					+ group;
		
		return string;
	
	}
	
	public String doGET_NUC_DATA_SET_ISOTOPES(String header
								, String id
								, String action
								, String user
								, String pw
								, String set){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+NUC+DATA+SET+ISOTOPES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ setString
					+ "="
					+ set;

		return string;
	
	}
	
	public String doGET_NUC_DATA_LIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String set
								, String isotope
								, String type){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+NUC+DATA+LIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ setString
					+ "="
					+ set
					+ "&"
					+ isotopeString
					+ "="
					+ isotope
					+ "&"
					+ typeString
					+ "="
					+ type;
		
		return string;
	
	}
	
	public String doGET_NUC_DATA_INFO(String header
								, String id
								, String action
								, String user
								, String pw
								, String nucData
								, String properties){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+NUC+DATA+INFO"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ nucDataString
					+ "="
					+ nucData
					+ "&"
					+ propertiesString
					+ "="
					+ properties;

		return string;
	
	}
	
	public String doPARSE_NUC_DATA_FILE(String header
								, String id
								, String action
								, String user
								, String pw
								, String reaction
								, String reaction_type
								, String notes
								, String type
								, String format
								, String xunits
								, String yunits
								, String filename
								, String file){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "PARSE+NUC+DATA+FILE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ reactionString
					+ "="
					+ reaction
					+ "&"
					+ reaction_typeString
					+ "="
					+ reaction_type
					+ "&"
					+ notesString
					+ "="
					+ notes
					+ "&"
					+ typeString
					+ "="
					+ type 
					+ "&"
					+ formatString
					+ "="
					+ format
					+ "&"
					+ xunitsString
					+ "="
					+ xunits
					+ "&"
					+ yunitsString
					+ "="
					+ yunits
					+ "&"
					+ filenameString
					+ "="
					+ filename
					+ "&"
					+ fileString
					+ "="
					+ file;

		return string;
	
	}
	
	public String doMODIFY_NUC_DATA_SET(String header
								, String id
								, String action
								, String user
								, String pw
								, String src_set
								, String dest_set
								, String dest_group
								, String del_src_set){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "MODIFY+NUC+DATA+SET"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ src_setString
					+ "="
					+ src_set
					+ "&"
					+ dest_setString
					+ "="
					+ dest_set
					+ "&"
					+ dest_groupString
					+ "="
					+ dest_group
					+ "&"
					+ del_src_setString
					+ "="
					+ del_src_set;
		
		return string;
	
	}
	
	public String doMODIFY_NUC_DATA(String header
								, String id
								, String action
								, String user
								, String pw
								, String nucData
								, String dest_set
								, String del_nuc_data
								, String properties){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "MODIFY+NUC+DATA"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ nucDataString
					+ "="
					+ nucData
					+ "&"
					+ dest_setString
					+ "="
					+ dest_set
					+ "&"
					+ del_nuc_dataString
					+ "="
					+ del_nuc_data
					+ "&"
					+ propertiesString
					+ "="
					+ properties;
					
		return string;
	
	}
	
	public String doNUC_DATA_EXIST(String header
								, String id
								, String action
								, String user
								, String pw
								, String nucData){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "NUC+DATA+EXIST"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ nucDataString
					+ "="
					+ nucData;
			
		return string;
	
	}

	public String doSHARE_NUC_DATA_SET(String header
								, String id
								, String action
								, String user
								, String pw
								, String set){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "SHARE+NUC+DATA+SET"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ setString
					+ "="
					+ set;
			
		return string;
	
	}

	public String doEXPORT_RATE_LIBRARY(String header
								, String id
								, String action
								, String user
								, String pw
								, String library
								, String format){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "EXPORT+RATE+LIBRARY"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ libraryString
					+ "="
					+ library
					+ "&"
					+ formatString
					+ "="
					+ format;
			
		return string;
	
	}
	
	public String doMAKE_ELEMENT_SYNTHESIS_MOVIE(String header
								, String id
								, String action
								, String user
								, String pw
								, String arguments
								, String simulation
								, String width
								, String height
								, String frame_skip_interval){
									
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "MAKE+ELEMENT+SYNTHESIS+MOVIE"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ argumentsString
					+ "="
					+ arguments
					+ "&"
					+ simulationString
					+ "="
					+ simulation
					+ "&"
					+ widthString
					+ "="
					+ width
					+ "&"
					+ heightString
					+ "="
					+ height
					+ "&"
					+ frame_skip_intervalString
					+ "="
					+ frame_skip_interval;
			
		return string;
	
	}

	public String doGET_ELEMENT_SYNTHESIS_FLUX_MAPPING(String header
									, String id
									, String action
									, String user
									, String pw
									, String path){
										
		String string = "";
		
		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+FLUX+MAPPING"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zonesString
					+ "="
					+ zones;

		return string;
	
	}

	public String doGET_ELEMENT_SYNTHESIS_FLUXES(String header
									, String id
									, String action
									, String user
									, String pw
									, String path
									, String zone
									, String reactions
									, String sum){
										
		String string = "";

		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+FLUXES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ zoneString
					+ "="
					+ zone
					+ "&"
					+ reactionsString
					+ "="
					+ reactions
					+ "&"
					+ sumString
					+ "="
					+ sum;

		return string;
	
	}
	
	public String doGET_ELEMENT_SYNTHESIS_WEIGHTED_ABUNDANCES(String header
																, String id
																, String action
																, String user
																, String pw
																, String path
																, String isotopes){
																	
		String string = "";

		string = headerString 
					+ "="
					+ header
					+ "&"
					+ idString
					+ "="
					+ id
					+ "&"
					+ actionString
					+ "="
					+ "GET+ELEMENT+SYNTHESIS+WEIGHTED+ABUNDANCES"
					+ "&"
					+ userString
					+ "="
					+ user
					+ "&"
					+ pwString
					+ "="
					+ pw
					+ "&"
					+ pathString
					+ "="
					+ path
					+ "&"
					+ isotopesString
					+ "="
					+ isotopes;

		return string;
	
	}
	
} 