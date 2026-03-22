package org.nucastrodata.io;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;

import javax.net.ssl.*;

import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure.PointType;
import org.nucastrodata.datastructure.util.ElementSimDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.datastructure.util.LibraryDirectoryDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.datastructure.util.RateLibDataStructure;
import org.nucastrodata.datastructure.util.Reaction;
import org.nucastrodata.datastructure.util.ReactionDataStructure;
import org.nucastrodata.datastructure.util.SimTypeDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.datastructure.util.UncaughtException;
import org.nucastrodata.datastructure.util.WaitingPointDataStructure;
import org.nucastrodata.enums.*;

import java.security.*;
import java.awt.event.*;

import org.nucastrodata.Cina;
import org.nucastrodata.IOUtilities;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.GeneralDialog;
import org.nucastrodata.element.elementsynth.ElementSynthSimWorkflowRunStatus;
import org.nucastrodata.exception.CaughtExceptionHandler;
import org.nucastrodata.format.StringComparatorIgnoreCase;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.io.CGIComParser;
import org.nucastrodata.io.CGIComSubmitProperty;

public class CGICom {

	private String action, user, id, pw, library, library_path, isotope, type
					, timestep, range, min_abund, min_eff_life
					, rat_eff_life, path, zone
					, low_threshold, high_threshold, sep_factor, notes, paths, init_abund_path
					, al26_type, sunet_path, thermo_path
					, max_timesteps, include_weak, include_screening
					, start_time, stop_time, sim_type, zones, scale_factors, reaction_rate, params
					, name, desc, lib_dir, stack_trace, sim_workflow_index, sim_workflow_run_index, sens_sim_workflow_run_index
					, sim_workflow_mode, sim_name, reaction_rate_id, folder_type, sim_workflow_indices;
	
	private final String actionString = "ACTION";
	private final String userString = "USER";
	private final String idString = "ID";
	private final String pwString = "PW";
	private final String libraryString = "LIBRARY";
	private final String library_pathString = "LIBRARY_PATH";
	private final String isotopeString = "ISOTOPE";
	private final String typeString = "TYPE";
	private final String timestepString = "TIME_STEP";
	private final String rangeString = "RANGE";
	private final String min_abundString = "MIN_ABUND";
	private final String min_eff_lifeString = "MIN_EFF_LIFE";
	private final String rat_eff_lifeString = "RAT_EFF_LIFE";
	private final String pathString = "PATH";
	private final String zoneString = "ZONE";
	private final String low_thresholdString = "LOW_THRESHOLD";
	private final String high_thresholdString = "HIGH_THRESHOLD";
	private final String sep_factorString = "SEP_FACTOR";
	private final String notesString = "NOTES";
	private final String pathsString = "PATHS";
	private final String init_abund_pathString = "INIT_ABUND_PATH";
	private final String al26_typeString = "AL26_TYPE";
	private final String sunet_pathString = "SUNET_PATH";
	private final String thermo_pathString = "THERMO_PATH";
	private final String max_timestepsString = "MAX_TIMESTEPS";
	private final String include_weakString = "INCLUDE_WEAK";
	private final String include_screeningString = "INCLUDE_SCREENING";
	private final String start_timeString = "START_TIME";
	private final String stop_timeString = "STOP_TIME";
	private final String sim_typeString = "SIM_TYPE";
	private final String zonesString = "ZONES";
	private final String scale_factorsString = "SCALE_FACTORS";
	private final String reaction_rateString = "REACTION_RATE";
	private final String paramsString = "PARAMS";
	private final String nameString = "NAME";
	private final String descString = "DESC";
	private final String lib_dirString = "LIB_DIR";
	private final String stack_traceString = "STACK_TRACE";
	private final String sim_workflow_indexString = "SIM_WORKFLOW_INDEX";
	private final String sim_workflow_run_indexString = "SIM_WORKFLOW_RUN_INDEX";
	private final String sim_workflow_modeString = "SIM_WORKFLOW_MODE";
	private final String sim_nameString = "SIM_NAME";
	private final String reaction_rate_idString = "REACTION_RATE_ID";
	private final String folder_typeString = "FOLDER_TYPE";
	private final String sim_workflow_indicesString = "SIM_WORKFLOW_INDICES";
	private final String sens_sim_workflow_run_indexString = "SENS_SIM_WORKFLOW_RUN_INDEX";
	
	private final String[] actionArray = {"GET_WAITING_POINTS"
											, "GET_BOTTLENECK_REACTIONS"
											, "GET_SIMS"
											, "GET_SIM_INFO"
											, "COPY_SIMS_TO_SHARED"
											, "ERASE_SIMS"
											, "GET_SENS_NETWORK_ISOTOPES"
											, "GET_SENS_NETWORK_REACTIONS"
											, "GET_THERMO_PROFILE_SETS"
											, "IMPORT_THERMO_PROFILE_SET"
											, "ERASE_THERMO_PROFILE_SETS"
											, "IMPORT_RATE_LIBRARY"
											, "IS_MASTER_USER"
											, "GET_TOTAL_WEIGHTS"
											, "GET_LIB_DIRS"
											, "ERASE_LIB_DIR"
											, "GET_LIB_DIR_LIBS"
											, "GET_LIB_DIR_INFO"
											, "GET_ELEMENT_SYNTHESIS_ZONES"
											, "LOG_JAVA_EXCEPTION"
											, "ABORT_SIM_WORKFLOW_RUN"
											, "GET_SIM_WORKFLOW_RUN_STATUS"
											, "SAVE_SIM_WORKFLOW_RUN"
											, "EXECUTE_SIM_WORKFLOW_RUN"
											, "IS_SIM_SENS"
											, "GET_SIM_WORKFLOW_TYPES"
											, "CREATE_SIM_WORKFLOW"
											, "COPY_THERMO_PROFILE_SETS_TO_SHARED"
											, "GET_THERMO_PROFILE_SET_INFO"
											, "GET_ELEMENT_SYNTHESIS_EDOT_VALUES"
											, "CREATE_SIM_WORKFLOW_RUN"
											, "GET_SIM_WORKFLOWS"
											, "GET_SIM_WORKFLOW_INFO"
											, "COPY_SIM_WORKFLOWS_TO_SHARED"
											, "ERASE_SIM_WORKFLOWS"
											, "GET_SIM_WORKFLOW_RUNS"
											, "SIM_WORKFLOW_NAME_EXISTS"
											, "SIM_WORKFLOW_RUN_NAME_EXISTS"
											, "SIM_WORKFLOW_IN_USE"
											, "NAME_SIM_WORKFLOW_RUN"
											, "ERASE_SIM_WORKFLOW_RUN"};
	
	
	public static final int GET_WAITING_POINTS = 0;
	public static final int GET_BOTTLENECK_REACTIONS = 1;
	public static final int GET_SIMS = 2;
	public static final int GET_SIM_INFO = 3;
	public static final int COPY_SIMS_TO_SHARED = 4;
	public static final int ERASE_SIMS = 5;
	public static final int GET_SENS_NETWORK_ISOTOPES = 6;
	public static final int GET_SENS_NETWORK_REACTIONS = 7;
	public static final int GET_THERMO_PROFILE_SETS = 8;
	public static final int IMPORT_THERMO_PROFILE_SET = 9;
	public static final int ERASE_THERMO_PROFILE_SETS = 10;
	public static final int IMPORT_RATE_LIBRARY = 11;
	public static final int IS_MASTER_USER = 12;
	public static final int GET_TOTAL_WEIGHTS = 13;
	public static final int GET_LIB_DIRS = 14;
	public static final int ERASE_LIB_DIR = 15;
	public static final int GET_LIB_DIR_LIBS = 16;
	public static final int GET_LIB_DIR_INFO = 17;
	public static final int GET_ELEMENT_SYNTHESIS_ZONES = 18;
	public static final int LOG_JAVA_EXCEPTION = 19;
	public static final int ABORT_SIM_WORKFLOW_RUN = 20;
	public static final int GET_SIM_WORKFLOW_RUN_STATUS = 21;
	public static final int SAVE_SIM_WORKFLOW_RUN = 22;
	public static final int EXECUTE_SIM_WORKFLOW_RUN = 23;
	public static final int IS_SIM_SENS = 24;
	public static final int GET_SIM_WORKFLOW_TYPES = 25;
	public static final int CREATE_SIM_WORKFLOW = 26;
	public static final int COPY_THERMO_PROFILE_SETS_TO_SHARED = 27;
	public static final int GET_THERMO_PROFILE_SET_INFO = 28;
	public static final int GET_ELEMENT_SYNTHESIS_EDOT_VALUES = 29;
	public static final int CREATE_SIM_WORKFLOW_RUN = 30;
	public static final int GET_SIM_WORKFLOWS = 31;
	public static final int GET_SIM_WORKFLOW_INFO = 32;
	public static final int COPY_SIM_WORKFLOWS_TO_SHARED = 33;
	public static final int ERASE_SIM_WORKFLOWS = 34;
	public static final int GET_SIM_WORKFLOW_RUNS = 35;
	public static final int SIM_WORKFLOW_NAME_EXISTS = 36;
	public static final int SIM_WORKFLOW_RUN_NAME_EXISTS = 37;
	public static final int SIM_WORKFLOW_IN_USE = 38;
	public static final int NAME_SIM_WORKFLOW_RUN = 39;
	public static final int ERASE_SIM_WORKFLOW_RUN = 40;
	
	private int totalBytesRead, totalBytesWritten;
	
	/** The parser. */
	private CGIComParser parser = new CGIComParser();
	
	/**
	 * Initialize.
	 */
	private void initialize(){
		action = "";
		user = "";
		id = "";
		pw = "";
		stack_trace = "";
		library = "";
		library_path = "";
		isotope = "";
		type = "";
		timestep = "";
		range = "";
		min_abund = "";
		min_eff_life = "";
		rat_eff_life = "";
		path = "";
		zone = "";
		low_threshold = "";
		high_threshold = "";
		sep_factor = "";
		notes = "";
		paths = "";
		init_abund_path = "";
		al26_type = "";
		sunet_path = "";
		thermo_path = "";
		max_timesteps = "";
		include_weak = "";
		include_screening = "";
		start_time = "";
		stop_time = "";
		sim_type = "";
		zones = "";
		scale_factors = "";
		reaction_rate = "";
		params = "";
		name = "";
		desc = "";
		lib_dir = "";
		sim_workflow_index = "";
		sim_workflow_run_index = "";
		sim_workflow_mode = "";
		sim_name = "";
		reaction_rate_id = "";
		folder_type = "";
		sim_workflow_indices = "";
		sens_sim_workflow_run_index = "";
		totalBytesRead = 0;
		totalBytesWritten = 0;
	}
	
	/**
	 * Do cgi call.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param action the action
	 * @param frame the frame
	 * @return true, if successful
	 */
	public boolean doCGICall(MainDataStructure mds
							, DataStructure ds
							, int action
							, Frame frame){
	
		boolean[] flagArray = this.doCGICom(mds, ds, action, frame, null);
		return !flagArray[0] && !flagArray[1] && !flagArray[2];
	}
	
	public boolean doCGICall(MainDataStructure mds, DataStructure ds, int action, Frame frame, File uploadFile) {
		boolean[] flagArray = this.doCGICom(mds, ds, action, frame, uploadFile);
		return !flagArray[0] && !flagArray[1] && !flagArray[2];
	}
	
	/**
	 * Do cgi com.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param action the action
	 * @param frame the frame
	 * @return the boolean[]
	 */
	public boolean[] doCGICom(MainDataStructure mds
				, DataStructure ds
				, int action
				, Frame frame
				, File uploadFile){

		initialize();

		ArrayList<CGIComSubmitProperty> propList = getCGIComSubmitProperties(action, mds, ds);
		String outputString = getOutputString(propList);
		if(action==IMPORT_THERMO_PROFILE_SET || action==IMPORT_RATE_LIBRARY){
			outputString = getOutputStringMultipartFormData(propList);
		}else{
			outputString = getOutputString(propList);
		}

		System.out.println(outputString.trim());
		
		String inputString = "";
			
		if(action==IMPORT_THERMO_PROFILE_SET || action==IMPORT_RATE_LIBRARY){
			try {
				inputString = transmitCGIString(outputString, mds, uploadFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			inputString = transmitCGIString(outputString, mds, action);
		}
		
		System.out.println(inputString.trim());
		System.out.println("************************************************");
		
		return parser.parse(action, mds, ds, frame, inputString);

	}
	
	/**
	 * Gets the cGI com submit properties.
	 *
	 * @param actionInt the action int
	 * @param mds the mds
	 * @param ds the ds
	 * @return the cGI com submit properties
	 */
	private ArrayList<CGIComSubmitProperty> getCGIComSubmitProperties(int actionInt
												, MainDataStructure mds
												, DataStructure ds){
		
		ArrayList<CGIComSubmitProperty> propList = new ArrayList<CGIComSubmitProperty>();
		
		try{
			
			action = URLEncoder.encode(actionArray[actionInt], "UTF-8");
			user = URLEncoder.encode(mds.getUser(), "UTF-8");
			id = URLEncoder.encode(mds.getID(), "UTF-8");
			pw = URLEncoder.encode(getEncryptedString(mds.getPW()), "UTF-8");
			
			if(ds instanceof UncaughtException){
				UncaughtException ued = (UncaughtException)ds;
				stack_trace = ued.getStackTrace();
			}else if(ds instanceof org.nucastrodata.datastructure.feature.ElementSynthDataStructure){
				ElementSynthDataStructure esds = ((ElementSynthDataStructure)ds);
				path = URLEncoder.encode(esds.getPath(), "UTF-8");
				sim_name = URLEncoder.encode(esds.getName(), "UTF-8");
				type = URLEncoder.encode(esds.getType(), "UTF-8");
				paths = URLEncoder.encode(esds.getPaths(), "UTF-8");
				if(esds.getFolderType()!=null){
					folder_type = URLEncoder.encode(esds.getFolderType().name(), "UTF-8");
				}
				sens_sim_workflow_run_index = URLEncoder.encode(String.valueOf(esds.getSensSimWorkflowRunIndex()), "UTF-8");
				sim_workflow_indices = URLEncoder.encode(esds.getSimWorkflowIndices(), "UTF-8"); 
				sim_workflow_run_index = URLEncoder.encode(String.valueOf(esds.getCurrentSimWorkRunDataStructure().getIndex()), "UTF-8");
				sunet_path = URLEncoder.encode(esds.getCurrentSimWorkDataStructure().getSunetPath(), "UTF-8");
				library = URLEncoder.encode(esds.getCurrentSimWorkDataStructure().getLibrary(), "UTF-8");
				type = URLEncoder.encode(esds.getCurrentSimWorkDataStructure().getAl26Type(), "UTF-8");
				lib_dir = URLEncoder.encode(esds.getCurrentSimWorkDataStructure().getLibDir(), "UTF-8");
				isotope = URLEncoder.encode(esds.getIsotope(), "UTF-8");
				notes = URLEncoder.encode(esds.getNotes(), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.util.ElementSimWorkDataStructure){
				ElementSimWorkDataStructure eswds = ((ElementSimWorkDataStructure)ds);
				sim_type = URLEncoder.encode(eswds.getSimType(), "UTF-8");
				name = URLEncoder.encode(eswds.getName(), "UTF-8");
				sim_workflow_index = URLEncoder.encode(String.valueOf(eswds.getIndex()), "UTF-8");
				sim_workflow_indices = URLEncoder.encode(String.valueOf(eswds.getIndex()), "UTF-8");
				sim_workflow_mode = URLEncoder.encode(String.valueOf(eswds.getSimWorkflowMode()), "UTF-8");
				zones = URLEncoder.encode(eswds.getZones(), "UTF-8");
				thermo_path = URLEncoder.encode(eswds.getThermoPath(), "UTF-8");	
				sunet_path = URLEncoder.encode(eswds.getSunetPath(), "UTF-8");
				init_abund_path = URLEncoder.encode(eswds.getInitAbundPath(), "UTF-8");
				params = URLEncoder.encode(eswds.getParams(), "UTF-8");
				max_timesteps = URLEncoder.encode(eswds.getMaxTimesteps(), "UTF-8");
				include_weak = URLEncoder.encode(eswds.getIncludeWeak() ? "Y" : "N", "UTF-8");
				include_screening = URLEncoder.encode(eswds.getIncludeScreening() ? "Y" : "N", "UTF-8");
				start_time = URLEncoder.encode(eswds.getStartTime(), "UTF-8");
				stop_time = URLEncoder.encode(eswds.getStopTime(), "UTF-8");
				scale_factors = URLEncoder.encode(String.valueOf(eswds.getScaleFactors()), "UTF-8");
				reaction_rate = URLEncoder.encode(eswds.getReactionRate(), "UTF-8");
				reaction_rate_id = URLEncoder.encode(eswds.getReactionRateID(), "UTF-8");
				notes = URLEncoder.encode(eswds.getNotes(), "UTF-8");
				al26_type = URLEncoder.encode(eswds.getAl26Type(), "UTF-8");
				library_path = URLEncoder.encode(eswds.getLibraryPath(), "UTF-8");
				library = URLEncoder.encode(eswds.getLibrary(), "UTF-8");
				lib_dir = URLEncoder.encode(eswds.getLibDir(), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure){
				ElementSimWorkRunDataStructure eswrds = ((ElementSimWorkRunDataStructure)ds);
				name = URLEncoder.encode(eswrds.getName(), "UTF-8");
				sim_workflow_index = URLEncoder.encode(String.valueOf(eswrds.getSimWorkflowIndex()), "UTF-8");
				sim_workflow_run_index = URLEncoder.encode(String.valueOf(eswrds.getIndex()), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.util.NucSimSetDataStructure){
				path = URLEncoder.encode(((org.nucastrodata.datastructure.util.NucSimSetDataStructure)ds).getPath(), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.feature.WaitingDataStructure){
				WaitingDataStructure wds = ((WaitingDataStructure)ds);
				String timestepmin = new Integer(wds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(0).intValue()).toString();
				String timestepmax = new Integer(wds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(1).intValue()).toString();
				timestep = URLEncoder.encode(timestepmin + "," + timestepmax, "UTF-8");
				if(wds.getRange()==WaitingDataStructure.Range.CUSTOM){
					range = wds.getZ().getX() 
								+ ","
								+ wds.getN().getX()
								+ " "
								+ wds.getZ().getY() 
								+ ","
								+ wds.getN().getY();
				}else{
					range = "";
				}
				range = URLEncoder.encode(range, "UTF-8");
				min_abund = URLEncoder.encode(new Double(wds.getAbund()).toString(), "UTF-8");
				min_eff_life = URLEncoder.encode(new Double(wds.getEffLifetime()).toString(), "UTF-8");
				rat_eff_life = URLEncoder.encode(new Double(wds.getRatio()).toString(), "UTF-8");
				path = URLEncoder.encode(wds.getPath(), "UTF-8");
				zone = URLEncoder.encode(String.valueOf(wds.getZone()), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.feature.BottleDataStructure){
				BottleDataStructure bds = ((BottleDataStructure)ds);
				String timestepmin = new Integer(bds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(0).intValue()).toString();
				String timestepmax = new Integer(bds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(1).intValue()).toString();
				timestep = URLEncoder.encode(timestepmin + "," + timestepmax, "UTF-8");
				if(bds.getRange()==BottleDataStructure.Range.CUSTOM){
					range = bds.getA().getX() 
								+ ","
								+ bds.getA().getY();
				}else{
					range = "";
				}
				range = URLEncoder.encode(range, "UTF-8");
				low_threshold = URLEncoder.encode(new Double(bds.getLowThreshold()).toString(), "UTF-8");
				high_threshold = URLEncoder.encode(new Double(bds.getHighThreshold()).toString(), "UTF-8");
				sep_factor = URLEncoder.encode(new Double(bds.getSepFactor()).toString(), "UTF-8");
				path = URLEncoder.encode(bds.getPath(), "UTF-8");
				zone = URLEncoder.encode(String.valueOf(bds.getZone()), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.feature.ElementManDataStructure){
				ElementManDataStructure emds = ((ElementManDataStructure)ds);
				path = URLEncoder.encode(emds.getPath(), "UTF-8");
				paths = URLEncoder.encode(emds.getPaths(), "UTF-8");
				if(emds.getFolderType()!=null){
					folder_type = URLEncoder.encode(emds.getFolderType().name(), "UTF-8");
				}
			}else if(ds instanceof org.nucastrodata.datastructure.feature.ElementWorkManDataStructure){
			
				ElementWorkManDataStructure ewmds = ((ElementWorkManDataStructure)ds);
				sim_workflow_indices = URLEncoder.encode(ewmds.getSimWorkflowIndices(), "UTF-8"); 
				if(ewmds.getFolderType()!=null){
					folder_type = URLEncoder.encode(ewmds.getFolderType().name(), "UTF-8");
				}
			}else if(ds instanceof org.nucastrodata.datastructure.feature.ElementVizDataStructure){
				ElementVizDataStructure evds = ((ElementVizDataStructure)ds);
				path = URLEncoder.encode(evds.getPath(), "UTF-8");
				paths = URLEncoder.encode(evds.getPaths(), "UTF-8");
				if(evds.getFolderType()!=null){
					folder_type = URLEncoder.encode(evds.getFolderType().name(), "UTF-8");
				}
				zones = URLEncoder.encode(String.valueOf(evds.getZone()), "UTF-8");
			}else if(ds instanceof org.nucastrodata.datastructure.feature.ThermoManDataStructure){
				ThermoManDataStructure tmds = ((ThermoManDataStructure)ds);
				path = URLEncoder.encode(tmds.getPath(), "UTF-8");
				paths = URLEncoder.encode(tmds.getPaths(), "UTF-8");
				if(tmds.getFolderType()!=null){
					folder_type = URLEncoder.encode(tmds.getFolderType().name(), "UTF-8");
				}
				if(tmds.getImportedThermoProfileSetDataStructure()!=null){
					name = URLEncoder.encode(tmds.getImportedThermoProfileSetDataStructure().getName(), "UTF-8");
					desc = URLEncoder.encode(tmds.getImportedThermoProfileSetDataStructure().getDesc(), "UTF-8");
				}
			}else if(ds instanceof org.nucastrodata.datastructure.util.LibraryDataStructure){
				LibraryDataStructure lds = ((LibraryDataStructure)ds);
				library = lds.getLibName();
				notes = lds.getLibNotes();
				lib_dir = lds.getLibDir();
			}else if(ds instanceof org.nucastrodata.datastructure.feature.RateLibManDataStructure){
				RateLibManDataStructure rlmds = ((RateLibManDataStructure)ds);
				lib_dir = rlmds.getLibDir();
			}
			
		}catch(UnsupportedEncodingException usee){
			usee.printStackTrace();
		}
		
		switch(actionInt){
		
			case GET_BOTTLENECK_REACTIONS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(pathString, path));
				propList.add(new CGIComSubmitProperty(zoneString, zone));
				propList.add(new CGIComSubmitProperty(timestepString, timestep));
				propList.add(new CGIComSubmitProperty(rangeString, range));
				propList.add(new CGIComSubmitProperty(low_thresholdString, low_threshold));
				propList.add(new CGIComSubmitProperty(high_thresholdString, high_threshold));
				propList.add(new CGIComSubmitProperty(sep_factorString, sep_factor));
				break;
		
			case GET_WAITING_POINTS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(pathString, path));
				propList.add(new CGIComSubmitProperty(zoneString, zone));
				propList.add(new CGIComSubmitProperty(timestepString, timestep));
				propList.add(new CGIComSubmitProperty(rangeString, range));
				propList.add(new CGIComSubmitProperty(min_abundString, min_abund));
				propList.add(new CGIComSubmitProperty(min_eff_lifeString, min_eff_life));
				propList.add(new CGIComSubmitProperty(rat_eff_lifeString, rat_eff_life));
				break;
		
			case GET_SENS_NETWORK_ISOTOPES: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(typeString, type));
				propList.add(new CGIComSubmitProperty(sunet_pathString, sunet_path));
				propList.add(new CGIComSubmitProperty(libraryString, library));
				break;
				
			case GET_SENS_NETWORK_REACTIONS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(sens_sim_workflow_run_indexString, sens_sim_workflow_run_index));
				propList.add(new CGIComSubmitProperty(isotopeString, isotope));
				break;
		
			case IS_SIM_SENS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathString, path));
				break;
		
			case GET_SIMS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(folder_typeString, folder_type));
				break;
				
			case GET_SIM_INFO: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case ERASE_SIMS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case COPY_SIMS_TO_SHARED: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case IMPORT_RATE_LIBRARY:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(libraryString, library));
				propList.add(new CGIComSubmitProperty(notesString, notes));
				propList.add(new CGIComSubmitProperty(lib_dirString, lib_dir));
				break;
				
			case IS_MASTER_USER: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				break;
				
			case GET_TOTAL_WEIGHTS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathString, path));
				break;
				
			case GET_LIB_DIRS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				break;
				
			case ERASE_LIB_DIR: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(lib_dirString, lib_dir));
				break;
				
			case GET_LIB_DIR_LIBS: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(lib_dirString, lib_dir));
				break;
				
			case GET_LIB_DIR_INFO: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(lib_dirString, lib_dir));
				break;
				
			case GET_ELEMENT_SYNTHESIS_ZONES: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case LOG_JAVA_EXCEPTION: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(stack_traceString, stack_trace));
				break;
				
			case ABORT_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				break;
				
			case GET_SIM_WORKFLOW_RUN_STATUS:
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				break;
				
			case SAVE_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				propList.add(new CGIComSubmitProperty(sim_nameString, sim_name));
				propList.add(new CGIComSubmitProperty(notesString, notes));
				break;
			
			case EXECUTE_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				break;
			
			case CREATE_SIM_WORKFLOW:
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				propList.add(new CGIComSubmitProperty(nameString, name));
				propList.add(new CGIComSubmitProperty(sim_workflow_modeString, sim_workflow_mode));
				propList.add(new CGIComSubmitProperty(notesString, notes));
				propList.add(new CGIComSubmitProperty(init_abund_pathString, init_abund_path));
				propList.add(new CGIComSubmitProperty(thermo_pathString, thermo_path));
				propList.add(new CGIComSubmitProperty(max_timestepsString, max_timesteps));
				propList.add(new CGIComSubmitProperty(include_weakString, include_weak));
				propList.add(new CGIComSubmitProperty(include_screeningString, include_screening));
				propList.add(new CGIComSubmitProperty(start_timeString, start_time));
				propList.add(new CGIComSubmitProperty(stop_timeString, stop_time));
				propList.add(new CGIComSubmitProperty(zonesString, zones));
				propList.add(new CGIComSubmitProperty(libraryString, library));
				propList.add(new CGIComSubmitProperty(library_pathString, library_path));
				propList.add(new CGIComSubmitProperty(sunet_pathString, sunet_path));
				propList.add(new CGIComSubmitProperty(lib_dirString, lib_dir));
				propList.add(new CGIComSubmitProperty(scale_factorsString, scale_factors));
				propList.add(new CGIComSubmitProperty(paramsString, params));
				propList.add(new CGIComSubmitProperty(reaction_rateString, reaction_rate));
				propList.add(new CGIComSubmitProperty(al26_typeString, al26_type));
				propList.add(new CGIComSubmitProperty(sim_typeString, sim_type));
				propList.add(new CGIComSubmitProperty(reaction_rate_idString, reaction_rate_id));
				break;
				
			case CREATE_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_indexString, sim_workflow_index));
				propList.add(new CGIComSubmitProperty(nameString, name));
				break;
				
			case GET_SIM_WORKFLOW_TYPES: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(pwString, pw));
				break;
				
			case GET_SIM_WORKFLOWS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(folder_typeString, folder_type));
				break;
				
			case GET_SIM_WORKFLOW_INFO:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_indicesString, sim_workflow_indices));
				break;
				
			case COPY_SIM_WORKFLOWS_TO_SHARED:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_indicesString, sim_workflow_indices));
				break;
				
			case ERASE_SIM_WORKFLOWS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_indicesString, sim_workflow_indices));
				break;
				
			case ERASE_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				break;
				
			case GET_SIM_WORKFLOW_RUNS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				break;
				
			case NAME_SIM_WORKFLOW_RUN:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(sim_workflow_run_indexString, sim_workflow_run_index));
				propList.add(new CGIComSubmitProperty(nameString, name));
				break;
				
			case SIM_WORKFLOW_NAME_EXISTS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(nameString, name));
				break;
				
			case SIM_WORKFLOW_RUN_NAME_EXISTS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(nameString, name));
				break;
				
			case SIM_WORKFLOW_IN_USE:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(nameString, name));
				break;
				
			case COPY_THERMO_PROFILE_SETS_TO_SHARED: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_THERMO_PROFILE_SET_INFO: 
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_THERMO_PROFILE_SETS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(folder_typeString, folder_type));
				break;
				
			case IMPORT_THERMO_PROFILE_SET:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(nameString, name));
				propList.add(new CGIComSubmitProperty(descString, desc));
				break;
				
			case ERASE_THERMO_PROFILE_SETS:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(pathsString, paths));
				break;
				
			case GET_ELEMENT_SYNTHESIS_EDOT_VALUES:
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(actionString, action));
				propList.add(new CGIComSubmitProperty(pathString, path));
				propList.add(new CGIComSubmitProperty(zonesString, zones));
				break;
				

		}
		
		return propList;
		
	}

	/**
	 * Gets the encrypted string.
	 *
	 * @param string the string
	 * @return the encrypted string
	 */
	private String getEncryptedString(String string){
		
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
	
	/**
	 * Gets the output string.
	 *
	 * @param propList the prop list
	 * @return the output string
	 */
	private String getOutputString(ArrayList<CGIComSubmitProperty> propList){
	
		String string = "";
		
		Iterator<CGIComSubmitProperty> itr = propList.iterator();
		while(itr.hasNext()){
			CGIComSubmitProperty prop = itr.next();
			string += prop.getProperty() + "=" + prop.getValue();
			if(itr.hasNext()){
				string += "&";
			}
		}
		return string;
		
	}

	private String getOutputStringMultipartFormData(ArrayList<CGIComSubmitProperty> propList){
		String string = "";
		Iterator<CGIComSubmitProperty> itr = propList.iterator();
		while(itr.hasNext()){
			CGIComSubmitProperty prop = itr.next();
			string += "--" 
                + id
                + "\r\n" 
                + "Content-Disposition: form-data; name=" 
                + "\""
                + prop.getProperty()
                + "\"\r\n\r\n" 
                + prop.getValue()
                + "\r\n";
		}
		return string;
	}

	private String getFileHeader(String filename)
    {
        return "--" 
                + id
                + "\r\nContent-Disposition: form-data; name=\"cinafile\"; filename=\"" 
                + filename
                + "\"\r\nContent-type: binary\r\n\r\n";
    }
	
	/**
	 * Write file.
	 *
	 * @param os the os
	 * @param filepath the filepath
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeFile(OutputStream os, File uploadFile) throws IOException
    {
        int buffer = 1024 * 10;
        FileInputStream is = new FileInputStream(uploadFile);
        byte[] data = new byte[buffer];
        int bytes;
        while ((bytes = is.read(data, 0, buffer)) > 0)
        {
        	os.write(data, 0, bytes);
        	totalBytesWritten += bytes;
        }
        is.close();
    }

	private String transmitCGIString(String inputString, MainDataStructure mds, File uploadFile) throws Exception{	
		URL url = null;
		if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
			url = new URL("https://nucastrodata2.ornl.gov/cina_dev/cina.php");
		}else if(Cina.cinaMainDataStructure.getURLType().equals("NONDEV")){
			url = new URL("https://nucastrodata2.ornl.gov/cina/cina.php");
		}
		HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
		urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary=" + id);
		urlConnection.setDoOutput(true);

		String trailer = "\r\n--" + id + "--\r\n";
		OutputStream os = urlConnection.getOutputStream();
		os.write(inputString.getBytes());
		if (uploadFile!=null){
			totalBytesWritten = 0;
			String header = getFileHeader(uploadFile.getName());
			os.write(header.getBytes());
			writeFile(os, uploadFile);
		}
		os.write(trailer.getBytes());
		os.close();
		
		InputStream is = urlConnection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtilities.readStream(is, baos);
		is.close();
		return baos.toString();
	}
	
	/**
	 * Transmit cgi string.
	 *
	 * @param string the string
	 * @param mds the mds
	 * @param action the action
	 * @param data the data
	 * @param ds the ds
	 * @return the string
	 */
	private String transmitCGIString(String string, MainDataStructure mds, int action, byte[] data, DataStructure ds){
		
		String totalInputString = "";
		int numberOfAttempts = 0;
		
		try{

			URL url = null;
			
			if(Cina.cinaMainDataStructure.getURLType().equals("DEV")){
				url = new URL("https://nucastrodata2.ornl.gov/cina_dev/cina.php");
			}else if(Cina.cinaMainDataStructure.getURLType().equals("NONDEV")){
				url = new URL("https://nucastrodata2.ornl.gov/cina/cina.php");
			}
			
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			os.write(string.getBytes());
			if(data!=null){
				
			}
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			totalInputString = new String(baos.toByteArray());
			baos.close();
		
		}catch(java.net.ConnectException ce){
			numberOfAttempts++;
			if(numberOfAttempts<10){
				transmitCGIString(string, mds, action, data, ds);
			}else{
				CaughtExceptionHandler.handleException(ce);
				return "ERROR=An error has occurred processing a web request to the CINA web and data server. The appropriate staff have been notified.";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR=An error has occurred processing a web request to the CINA web and data server. The appropriate staff have been notified.";
		}

		return totalInputString;
	}
	
	/**
	 * Transmit cgi string.
	 *
	 * @param string the string
	 * @param mds the mds
	 * @param action the action
	 * @return the string
	 */
	private String transmitCGIString(String string, MainDataStructure mds, int action){
		return transmitCGIString(string, mds, action, null, null);
	}

}

/**
 *CGIComSubmitProperty (c) 2006 Eric J. Lingerfelt
 *
 *This class holds the property and value String used to create the String transmitted to the CGICom URL
 *
 *@author Eric J. Lingerfelt
 */
class CGIComSubmitProperty{
	private String property;
	private String value;
	
	/**
	 *Constructor
	 *
	 *@param property the property
	 *@param value the value
	 */
	public CGIComSubmitProperty(String property, String value){
		this.property = property;
		this.value = value;
	}
	
	/**
	 *Gets the property String
	 *
	 *@return the property String
	 */
	public String getProperty(){return property;}
	
	/**
	 *Gets the value String
	 *
	 *@return the value String
	 */
	public String getValue(){return value;}
}

class CGIComParser implements ActionListener{

	private CautionDialog cautionDialog;
	
	/**
	 *Calls the correct parser for the output String from the called URL
	 *
	 *@param actionInt int indicating which action was called
	 *@param mds the MainDataStructure instance
	 *@param ds the DataStructure to parse info to
	 *@param frame the window that called this action
	 *@param string the output String from the URL to parse
	 *
	 *@return a boolean array indicating if any CAUTIONs, ERRORs, or REASONs were returned by the URL  
	 */
	public boolean[] parse(int actionInt
							, MainDataStructure mds
							, DataStructure ds
							, Frame frame
							, String string){
								
		Vector<String> cautionVector = new Vector<String>();
		Vector<String> errorVector = new Vector<String>();
		Vector<String> reasonVector = new Vector<String>();
		
		switch(actionInt){
		
			case CGICom.GET_SIMS:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_SIMSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementManDataStructure){
					parseGET_SIMSString((ElementManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementVizDataStructure){
					parseGET_SIMSString((ElementVizDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				
				break;
			case CGICom.GET_SIM_INFO:
				parseGET_SIM_INFOString((ElementManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.ERASE_SIMS:
				parseERASE_SIMSString((ElementManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.COPY_SIMS_TO_SHARED:
				parseCOPY_SIMS_TO_SHAREDString((ElementManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_WAITING_POINTS:
				parseGET_WAITING_POINTSString((WaitingDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_BOTTLENECK_REACTIONS:
				parseGET_BOTTLENECK_REACTIONSString((BottleDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SENS_NETWORK_ISOTOPES:
				parseGET_SENS_NETWORK_ISOTOPESString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SENS_NETWORK_REACTIONS:
				parseGET_SENS_NETWORK_REACTIONSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.IS_MASTER_USER:
				parseIS_MASTER_USERString((MainDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_TOTAL_WEIGHTS:
				parseGET_TOTAL_WEIGHTSString((NucSimSetDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_LIB_DIRS:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_LIB_DIRSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof RateLibManDataStructure){
					parseGET_LIB_DIRSString((RateLibManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.GET_LIB_DIR_LIBS:
				parseGET_LIB_DIR_LIBSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_LIB_DIR_INFO:
				parseGET_LIB_DIR_INFOString((RateLibManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_ELEMENT_SYNTHESIS_ZONES:
				parseGET_ELEMENT_SYNTHESIS_ZONESString((ElementVizDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.ABORT_SIM_WORKFLOW_RUN:
				parseABORT_SIM_WORKFLOW_RUNString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SIM_WORKFLOW_RUN_STATUS:
				parseGET_SIM_WORKFLOW_RUN_STATUSString((ElementSimWorkRunDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.SAVE_SIM_WORKFLOW_RUN:
				parseSAVE_SIM_WORKFLOW_RUNString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.EXECUTE_SIM_WORKFLOW_RUN:
				parseEXECUTE_SIM_WORKFLOW_RUNString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.IS_SIM_SENS:
				parseIS_SIM_SENSString((ElementVizDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SIM_WORKFLOW_TYPES:
				parseGET_SIM_WORKFLOW_TYPESString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;	
			case CGICom.CREATE_SIM_WORKFLOW:
				parseCREATE_SIM_WORKFLOWString((ElementSimWorkDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.COPY_THERMO_PROFILE_SETS_TO_SHARED:
				parseCOPY_THERMO_PROFILE_SETS_TO_SHAREDString((ThermoManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_THERMO_PROFILE_SET_INFO:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_THERMO_PROFILE_SET_INFOString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ThermoManDataStructure){
					parseGET_THERMO_PROFILE_SET_INFOString((ThermoManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.GET_THERMO_PROFILE_SETS:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_THERMO_PROFILE_SETSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ThermoManDataStructure){
					parseGET_THERMO_PROFILE_SETSString((ThermoManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.IMPORT_THERMO_PROFILE_SET:
				parseIMPORT_THERMO_PROFILE_SETString((ThermoManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.IMPORT_RATE_LIBRARY:
				parseIMPORT_RATE_LIBRARYString((LibraryDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.ERASE_THERMO_PROFILE_SETS:
				parseERASE_THERMO_PROFILE_SETSString((ThermoManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_ELEMENT_SYNTHESIS_EDOT_VALUES:
				parseGET_ELEMENT_SYNTHESIS_EDOT_VALUESString((ElementVizDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.CREATE_SIM_WORKFLOW_RUN:
				parseCREATE_SIM_WORKFLOW_RUNString((ElementSimWorkRunDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SIM_WORKFLOWS:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_SIM_WORKFLOWSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementWorkManDataStructure){
					parseGET_SIM_WORKFLOWSString((ElementWorkManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.GET_SIM_WORKFLOW_INFO:
				if(ds instanceof ElementSynthDataStructure){
					parseGET_SIM_WORKFLOW_INFOString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementWorkManDataStructure){
					parseGET_SIM_WORKFLOW_INFOString((ElementWorkManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementSimWorkDataStructure){
					parseGET_SIM_WORKFLOW_INFOString((ElementSimWorkDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.COPY_SIM_WORKFLOWS_TO_SHARED:
				parseCOPY_SIM_WORKFLOWS_TO_SHAREDString((ElementWorkManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.ERASE_SIM_WORKFLOWS:
				if(ds instanceof ElementSynthDataStructure){
					parseERASE_SIM_WORKFLOWSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}else if(ds instanceof ElementWorkManDataStructure){
					parseERASE_SIM_WORKFLOWSString((ElementWorkManDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				}
				break;
			case CGICom.ERASE_SIM_WORKFLOW_RUN:
				parseERASE_SIM_WORKFLOW_RUNString((ElementSimWorkRunDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.GET_SIM_WORKFLOW_RUNS:
				parseGET_SIM_WORKFLOW_RUNSString((ElementSynthDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.SIM_WORKFLOW_NAME_EXISTS:
				parseSIM_WORKFLOW_NAME_EXISTSString((ElementSimWorkDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.SIM_WORKFLOW_RUN_NAME_EXISTS:
				parseSIM_WORKFLOW_RUN_NAME_EXISTSString((ElementSimWorkRunDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.SIM_WORKFLOW_IN_USE:
				parseSIM_WORKFLOW_IN_USEString((ElementSimWorkDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;
			case CGICom.NAME_SIM_WORKFLOW_RUN:
				parseNAME_SIM_WORKFLOW_RUNString((ElementSimWorkRunDataStructure)ds, string, errorVector, cautionVector, reasonVector);
				break;

		
		}
		
		errorVector.trimToSize();
		cautionVector.trimToSize();
		reasonVector.trimToSize();
		
		boolean[] flagArray = new boolean[3];
		
		if(!errorVector.isEmpty()){
			
			GeneralDialog dialog = new GeneralDialog(frame, getErrorString(errorVector), new String("Error!"));
			dialog.setVisible(true);
			flagArray[0] = true;
			
		}else{
			
			if(!cautionVector.isEmpty()){
			
				cautionDialog = new CautionDialog(frame, this, getCautionString(cautionVector), "Caution!");
				cautionDialog.setVisible(true);
				flagArray[1] = true;
			
			}
			
		}
		
		return flagArray;
		
	}

	public String getErrorString(Vector vector){
		
		String string = "";
		
		for(int i=0; i<vector.size(); i++){
			string += vector.get(i).toString() + "\n\n";
		}
		
		return string;
	
	}

	public String getCautionString(Vector vector){
		
		String string = "";
		
		for(int i=0; i<vector.size(); i++){
			string += vector.get(i).toString() + "\n\n";
		}
		string += "Do you want to continue?";
		
		return string;
	
	}
	
	private void parseNAME_SIM_WORKFLOW_RUNString(ElementSimWorkRunDataStructure eswrds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 



		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}
			}

	}
	
	private void parseSIM_WORKFLOW_IN_USEString(ElementSimWorkDataStructure eswds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){

		String[] array = string.split("\n"); 



		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("IN_USE=")){
					eswds.setInUse(Boolean.valueOf(token.substring(7)));
				}
			}

	}
	
	private void parseSIM_WORKFLOW_NAME_EXISTSString(ElementSimWorkDataStructure eswds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("NAME_EXISTS=")){
				eswds.setNameExists(Boolean.valueOf(token.substring(12)));
			}
		}

	}
	
	private void parseSIM_WORKFLOW_RUN_NAME_EXISTSString(ElementSimWorkRunDataStructure eswrds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("NAME_EXISTS=")){
					eswrds.setNameExists(Boolean.valueOf(token.substring(12)));
				}
			}

	}
	
	private void parseGET_SIM_WORKFLOW_INFOString(ElementSimWorkDataStructure eswds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("NAME=")){
					eswds.setName(token.substring(5));
				}else if(token.startsWith("FOLDER_TYPE=")){
					eswds.setFolderType(FolderType.valueOf(token.substring(12)));
				}else if(token.startsWith("THERMO_PATH=")){
					eswds.setThermoPath(token.substring(12));
				}else if(token.startsWith("LIBRARY_PATH=")){
					eswds.setLibraryPath(token.substring(13));
				}else if(token.startsWith("AL26_TYPE=")){
					eswds.setAl26Type(token.substring(10));
				}else if(token.startsWith("SUNET_PATH=")){
					String[] comps = token.substring(11).split("/");
					eswds.setSunetPath("PUBLIC/" + comps[comps.length-1]);
				}else if(token.startsWith("SIM_TYPE=")){
					eswds.setSimType(token.substring(9));
				}else if(token.startsWith("NOTES=")){
					eswds.setNotes(token.substring(6));
				}else if(token.startsWith("INIT_ABUND_PATH=")){
					String[] comps = token.substring(16).split("/");
					eswds.setInitAbundPath("PUBLIC/" + comps[comps.length-1]);
				}else if(token.startsWith("MAX_TIMESTEPS=")){
					eswds.setMaxTimesteps(token.substring(14));
				}else if(token.startsWith("INCLUDE_WEAK=")){
					eswds.setIncludeWeak(token.substring(13).equals("Y"));
				}else if(token.startsWith("INCLUDE_SCREENING=")){
					eswds.setIncludeScreening(token.substring(18).equals("Y"));
				}else if(token.startsWith("START_TIME=")){
					eswds.setStartTime(token.substring(11));
				}else if(token.startsWith("STOP_TIME=")){
					eswds.setStopTime(token.substring(10));
				}else if(token.startsWith("ZONES=")){
					eswds.setZones(token.substring(6));
					String[] zoneArray = token.substring(6).split(",");
					int[] valueArray = new int[zoneArray.length];
					for(int index=0; index<zoneArray.length; index++){
						valueArray[index] = Integer.valueOf(zoneArray[index]);
					}
					eswds.setZoneArray(valueArray);
				}else if(token.startsWith("CREATION_DATE=")){
					eswds.setCreationDate(token.substring(14));
				}else if(token.startsWith("SCALE_FACTOR=")){
					String scaleFactors = eswds.getScaleFactors();
					if(scaleFactors.equals("")){
						scaleFactors = token.substring(13);
					}else{
						scaleFactors = String.join(",", scaleFactors, token.substring(13));
					}
					eswds.setScaleFactors(scaleFactors);
				}else if(token.startsWith("PARAMS=")){
					String params = eswds.getParams();
					if(params.equals("")){
						params = token.substring(7);
					}else{
						params = String.join(":", params, token.substring(7));
					}
					eswds.setParams(params);
				}else if(token.startsWith("REACTION_RATE=")){
					eswds.setReactionRate(token.substring(14));
				}else if(token.startsWith("LIB_DIR=")){
					eswds.setLibDir(token.substring(8));
				}else if(token.startsWith("SIM_WORKFLOW_MODE=")){
					eswds.setSimWorkflowMode(SimWorkflowMode.valueOf(token.substring(18)));
				}
			}
	}
	
	private void parseGET_SIM_WORKFLOW_INFOString(ElementWorkManDataStructure ds
															, String string
															, Vector<String> errorVector
															, Vector<String> cautionVector
															, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String name = "";
		ElementSimWorkDataStructure eswds = null;
		FolderType folderType = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("NAME=")){
				name = token.substring(5);
				eswds = ds.getSimWorkMapSelected().get(folderType.name() + "/" + name);
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("THERMO_PATH=")){
				eswds.setThermoPath(token.substring(12));
			}else if(token.startsWith("LIBRARY_PATH=")){
				eswds.setLibraryPath(token.substring(13));
			}else if(token.startsWith("AL26_TYPE=")){
				eswds.setAl26Type(token.substring(10));
			}else if(token.startsWith("SUNET_PATH=")){
				String[] comps = token.substring(11).split("/");
				eswds.setSunetPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("SIM_TYPE=")){
				eswds.setSimType(token.substring(9));
			}else if(token.startsWith("NOTES=")){
				eswds.setNotes(token.substring(6));
			}else if(token.startsWith("INIT_ABUND_PATH=")){
				String[] comps = token.substring(16).split("/");
				eswds.setInitAbundPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("MAX_TIMESTEPS=")){
				eswds.setMaxTimesteps(token.substring(14));
			}else if(token.startsWith("INCLUDE_WEAK=")){
				eswds.setIncludeWeak(token.substring(13).equals("Y"));
			}else if(token.startsWith("INCLUDE_SCREENING=")){
				eswds.setIncludeScreening(token.substring(18).equals("Y"));
			}else if(token.startsWith("START_TIME=")){
				eswds.setStartTime(token.substring(11));
			}else if(token.startsWith("STOP_TIME=")){
				eswds.setStopTime(token.substring(10));
			}else if(token.startsWith("ZONES=")){
				eswds.setZones(token.substring(6));
				String[] zoneArray = token.substring(6).split(",");
				int[] valueArray = new int[zoneArray.length];
				for(int index=0; index<zoneArray.length; index++){
					valueArray[index] = Integer.valueOf(zoneArray[index]);
				}
				eswds.setZoneArray(valueArray);
			}else if(token.startsWith("CREATION_DATE=")){
				eswds.setCreationDate(token.substring(14));
			}else if(token.startsWith("SCALE_FACTOR=")){
				String scaleFactors = eswds.getScaleFactors();
				if(scaleFactors.equals("")){
					scaleFactors = token.substring(13);
				}else{
					scaleFactors = String.join(",", scaleFactors, token.substring(13));
				}
				eswds.setScaleFactors(scaleFactors);
			}else if(token.startsWith("PARAMS=")){
				String params = eswds.getParams();
				if(params.equals("")){
					params = token.substring(7);
				}else{
					params = String.join(":", params, token.substring(7));
				}
				eswds.setParams(params);
			}else if(token.startsWith("REACTION_RATE=")){
				eswds.setReactionRate(token.substring(14));
			}else if(token.startsWith("LIB_DIR=")){
				eswds.setLibDir(token.substring(8));
			}else if(token.startsWith("SIM_WORKFLOW_MODE=")){
				eswds.setSimWorkflowMode(SimWorkflowMode.valueOf(token.substring(18)));
			}
		}
	}
	
	private void parseGET_SIM_WORKFLOW_INFOString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String name = "";
		ElementSimWorkDataStructure eswds = null;
		FolderType folderType = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("NAME=")){
				name = token.substring(5);
				eswds = ds.getSimWorkMap().get(folderType.name() + "/" + name);
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("THERMO_PATH=")){
				eswds.setThermoPath(token.substring(12));
			}else if(token.startsWith("LIBRARY_PATH=")){
				eswds.setLibraryPath(token.substring(13));
			}else if(token.startsWith("AL26_TYPE=")){
				eswds.setAl26Type(token.substring(10));
			}else if(token.startsWith("SUNET_PATH=")){
				String[] comps = token.substring(11).split("/");
				eswds.setSunetPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("SIM_TYPE=")){
				eswds.setSimType(token.substring(9));
			}else if(token.startsWith("NOTES=")){
				eswds.setNotes(token.substring(6));
			}else if(token.startsWith("INIT_ABUND_PATH=")){
				String[] comps = token.substring(16).split("/");
				eswds.setInitAbundPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("MAX_TIMESTEPS=")){
				eswds.setMaxTimesteps(token.substring(14));
			}else if(token.startsWith("INCLUDE_WEAK=")){
				eswds.setIncludeWeak(token.substring(13).equals("Y"));
			}else if(token.startsWith("INCLUDE_SCREENING=")){
				eswds.setIncludeScreening(token.substring(18).equals("Y"));
			}else if(token.startsWith("START_TIME=")){
				eswds.setStartTime(token.substring(11));
			}else if(token.startsWith("STOP_TIME=")){
				eswds.setStopTime(token.substring(10));
			}else if(token.startsWith("ZONES=")){
				eswds.setZones(token.substring(6));
				String[] zoneArray = token.substring(6).split(",");
				int[] valueArray = new int[zoneArray.length];
				for(int index=0; index<zoneArray.length; index++){
					valueArray[index] = Integer.valueOf(zoneArray[index]);
				}
				eswds.setZoneArray(valueArray);
			}else if(token.startsWith("CREATION_DATE=")){
				eswds.setCreationDate(token.substring(14));
			}else if(token.startsWith("SCALE_FACTOR=")){
				String scaleFactors = eswds.getScaleFactors();
				if(scaleFactors.equals("")){
					scaleFactors = token.substring(13);
				}else{
					scaleFactors = String.join(",", scaleFactors, token.substring(13));
				}
				eswds.setScaleFactors(scaleFactors);
			}else if(token.startsWith("PARAMS=")){
				String params = eswds.getParams();
				if(params.equals("")){
					params = token.substring(7);
				}else{
					params = String.join(":", params, token.substring(7));
				}
				eswds.setParams(params);
			}else if(token.startsWith("REACTION_RATE=")){
				eswds.setReactionRate(token.substring(14));
			}else if(token.startsWith("LIB_DIR=")){
				eswds.setLibDir(token.substring(8));
			}else if(token.startsWith("SIM_WORKFLOW_MODE=")){
				eswds.setSimWorkflowMode(SimWorkflowMode.valueOf(token.substring(18)));
			}
			
		}

	}
	
	private void parseGET_SIM_WORKFLOWSString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		TreeMap<String, ElementSimWorkDataStructure> map = new TreeMap<String, ElementSimWorkDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		ElementSimWorkDataStructure esds = null;
		FolderType folderType = null;
		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				esds = new ElementSimWorkDataStructure();
				esds.setFolderType(folderType);
				esds.setName(token.substring(5));
				esds.setPath(esds.getFolderType().name() + "/" + esds.getName());
				map.put(esds.getPath(), esds);
			}else if(token.startsWith("SIM_WORKFLOW_INDEX=")){
				esds.setIndex(Integer.parseInt(token.substring(19)));
			}
		}
		
		ds.setSimWorkMap(map);

	}
	
	private void parseGET_SIM_WORKFLOWSString(ElementWorkManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){
			
		TreeMap<String, ElementSimWorkDataStructure> map = new TreeMap<String, ElementSimWorkDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		ElementSimWorkDataStructure esds = null;
		FolderType folderType = null;
		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				esds = new ElementSimWorkDataStructure();
				esds.setFolderType(folderType);
				esds.setName(token.substring(5));
				esds.setPath(esds.getFolderType().name() + "/" + esds.getName());
				map.put(esds.getPath(), esds);
			}else if(token.startsWith("SIM_WORKFLOW_INDEX=")){
				esds.setIndex(Integer.parseInt(token.substring(19)));
			}
		}
		
		ds.setSimWorkMap(map);

	}
	
	private void parseCOPY_SIM_WORKFLOWS_TO_SHAREDString(ElementWorkManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}
			}

	}
	
	private void parseERASE_SIM_WORKFLOWSString(ElementWorkManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}
			}

	}
	
	private void parseERASE_SIM_WORKFLOWSString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}
			}

	}
	
	private void parseERASE_SIM_WORKFLOW_RUNString(ElementSimWorkRunDataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					errorVector.trimToSize();
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}
			}

	}
	
	private void parseGET_SIM_WORKFLOW_RUNSString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){


		TreeMap<String, ElementSimWorkRunDataStructure> map = new TreeMap<String, ElementSimWorkRunDataStructure>();
		ElementSimWorkRunDataStructure swrds = null;
		ElementSimWorkDataStructure swds = null;
		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("SIM_WORKFLOW_RUN_INDEX=")){
				swrds.setIndex(Integer.parseInt(token.substring(23)));
			}else if(token.startsWith("NAME=")){
				swrds = new ElementSimWorkRunDataStructure();
				swrds.setName(token.substring(5));
				map.put(swrds.getName(), swrds);
			}else if(token.startsWith("START_DATE=")){
				swrds.setStartDate(token.substring(11));
			}else if(token.startsWith("END_DATE=")){
				swrds.setEndDate(token.substring(9));
			}else if(token.startsWith("CURRENT_ZONE=")){
				swrds.setCurrentZone(token.substring(13));
			}else if(token.startsWith("CURRENT_LIB=")){
				swrds.setCurrentLib(token.substring(12));
			}else if(token.startsWith("CURRENT_SCALE_FACTOR=")){
				swrds.setCurrentScaleFactor(token.substring(21));
			}else if(token.startsWith("CURRENT_STATUS=")){
				swrds.setCurrentStatus(ElementSynthSimWorkflowRunStatus.valueOf(token.substring(15)));
			}else if(token.startsWith("CURRENT_TEXT=")){
				swrds.setCurrentText(token.substring(13).replaceAll("\\|", "\n"));
			///////////////////////////////////////////////////////////////////////////////
			}else if(token.startsWith("SIM_WORKFLOW_INDEX=")){
				swds = new ElementSimWorkDataStructure();
				swds.setIndex(Integer.parseInt(token.substring(19)));
				swrds.setSimWorkDataStructure(swds);
				swrds.setSimWorkflowIndex(Integer.parseInt(token.substring(19)));
			}else if(token.startsWith("SIM_WORKFLOW_NAME=")){
				swds.setName(token.substring(18));
			}else if(token.startsWith("FOLDER_TYPE=")){
				swds.setFolderType(FolderType.valueOf(token.substring(12)));
			}else if(token.startsWith("THERMO_PATH=")){
				swds.setThermoPath(token.substring(12));
			}else if(token.startsWith("LIBRARY_PATH=")){
				swds.setLibraryPath(token.substring(13));
			}else if(token.startsWith("AL26_TYPE=")){
				swds.setAl26Type(token.substring(10));
			}else if(token.startsWith("SUNET_PATH=")){
				String[] comps = token.substring(11).split("/");
				swds.setSunetPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("SIM_TYPE=")){
				swds.setSimType(token.substring(9));
			}else if(token.startsWith("NOTES=")){
				swds.setNotes(token.substring(6));
			}else if(token.startsWith("INIT_ABUND_PATH=")){
				String[] comps = token.substring(16).split("/");
				swds.setInitAbundPath("PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("MAX_TIMESTEPS=")){
				swds.setMaxTimesteps(token.substring(14));
			}else if(token.startsWith("INCLUDE_WEAK=")){
				swds.setIncludeWeak(token.substring(13).equals("1"));
			}else if(token.startsWith("INCLUDE_SCREENING=")){
				swds.setIncludeScreening(token.substring(18).equals("1"));
			}else if(token.startsWith("START_TIME=")){
				swds.setStartTime(token.substring(11));
			}else if(token.startsWith("STOP_TIME=")){
				swds.setStopTime(token.substring(10));
			}else if(token.startsWith("ZONES=")){
				swds.setZones(token.substring(6));
				String[] zoneArray = token.substring(6).split(",");
				int[] valueArray = new int[zoneArray.length];
				for(int index=0; index<zoneArray.length; index++){
					valueArray[index] = Integer.valueOf(zoneArray[index]);
				}
				swds.setZoneArray(valueArray);
			}else if(token.startsWith("CREATION_DATE=")){
				swds.setCreationDate(token.substring(14));
			}else if(token.startsWith("SCALE_FACTOR=")){
				String scaleFactors = swds.getScaleFactors();
				if(scaleFactors.equals("")){
					scaleFactors = token.substring(13);
				}else{
					scaleFactors = String.join(",", scaleFactors, token.substring(13));
				}
				swds.setScaleFactors(scaleFactors);
			}else if(token.startsWith("PARAMS=")){
				String params = swds.getParams();
				if(params.equals("")){
					params = token.substring(7);
				}else{
					params = String.join(":", params, token.substring(7));
				}
				swds.setParams(params);
			}else if(token.startsWith("REACTION_RATE=")){
				swds.setReactionRate(token.substring(14));
			}else if(token.startsWith("LIB_DIR=")){
				swds.setLibDir(token.substring(8));
			}else if(token.startsWith("SIM_WORKFLOW_MODE=")){
				swds.setSimWorkflowMode(SimWorkflowMode.valueOf(token.substring(18)));
			}
		}
			
		ds.setSimWorkRunMap(map);

	}
	
	private void parseCREATE_SIM_WORKFLOW_RUNString(ElementSimWorkRunDataStructure eswrds
																, String string
																, Vector<String> errorVector
																, Vector<String> cautionVector
																, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("SIM_WORKFLOW_RUN_INDEX=")){
				eswrds.setIndex(Integer.valueOf(token.substring(23)));
			}
		}

	}
	
	private void parseGET_ELEMENT_SYNTHESIS_EDOT_VALUESString(ElementVizDataStructure ds
																	, String string
																	, Vector<String> errorVector
																	, Vector<String> cautionVector
																	, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String values = "";

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("EDOT_VALUES=")){	
				values = token.substring(12);
			}
		}
		
		double[] outputArray = null;
		
		if(!values.equals("")){
		
			String[] valueArray = values.split(",");
			outputArray = new double[valueArray.length];
			for(int i=0; i<valueArray.length; i++){
				double value = Double.valueOf(valueArray[i]);
				if(value<1e-25){
					value = 1e-25;
				}
				outputArray[i] = Double.valueOf(value);
			}
		
		}else{
		
			outputArray = new double[1];
		
		}
		
		ds.getCurrentNucSimDataStructure().setEdotArray(outputArray);
		
	}
	
	private void parseCOPY_THERMO_PROFILE_SETS_TO_SHAREDString(ThermoManDataStructure ds
																	, String string
																	, Vector<String> errorVector
																	, Vector<String> cautionVector
																	, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseERASE_THERMO_PROFILE_SETSString(ThermoManDataStructure ds
																	, String string
																	, Vector<String> errorVector
																	, Vector<String> cautionVector
																	, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseGET_THERMO_PROFILE_SETSString(ThermoManDataStructure ds
																	, String string
																	, Vector<String> errorVector
																	, Vector<String> cautionVector
																	, Vector<String> reasonVector){

		TreeMap<String, ThermoProfileSetDataStructure> map = new TreeMap<String, ThermoProfileSetDataStructure>(new StringComparatorIgnoreCase());
		ThermoProfileSetDataStructure tpsds = null;
		FolderType folderType = null;
		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				tpsds = new ThermoProfileSetDataStructure();
				tpsds.setFolderType(folderType);
				tpsds.setName(token.substring(5));
				tpsds.setPath(tpsds.getFolderType().name() + "/" + tpsds.getName());
				map.put(tpsds.getPath(), tpsds);
			}
		}
		
		ds.setSetMap(map);
	}
	
	private void parseGET_THERMO_PROFILE_SETSString(ElementSynthDataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		TreeMap<String, ThermoProfileSetDataStructure> map = new TreeMap<String, ThermoProfileSetDataStructure>(new StringComparatorIgnoreCase());
		ThermoProfileSetDataStructure tpsds = null;
		FolderType folderType = null;
		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				tpsds = new ThermoProfileSetDataStructure();
				tpsds.setFolderType(folderType);
				tpsds.setName(token.substring(5));
				tpsds.setPath(tpsds.getFolderType().name() + "/" + tpsds.getName());
				map.put(tpsds.getPath(), tpsds);
			}
		}

		ds.setSetMap(map);
	
	}
	
	private void parseGET_THERMO_PROFILE_SET_INFOString(ThermoManDataStructure ds
																, String string
																, Vector<String> errorVector
																, Vector<String> cautionVector
																, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String path = "";
		ThermoProfileSetDataStructure tpsds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				path = token.substring(5);
				tpsds = ds.getSetMapSelected().get(path);
			}else if(token.startsWith("FOLDER_TYPE=")){
				tpsds.setFolderType(FolderType.valueOf(token.substring(12)));
			}else if(token.startsWith("DESC=")){
				String substring = token.substring(5);
				if(substring.equals("")){
					substring = "N/A";
				}
				try {
					substring = URLDecoder.decode(substring, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				tpsds.setDesc(substring);
			}else if(token.startsWith("START_TIME=")){
				String substring = token.substring(11);
				tpsds.setStartTime(substring);
			}else if(token.startsWith("STOP_TIME=")){
				String substring = token.substring(10);
				tpsds.setStopTime(substring);
			}else if(token.startsWith("NUM_PROFILES=")){
				String substring = token.substring(13);
				tpsds.setNumProfiles(Integer.valueOf(substring));
			}else if(token.startsWith("CREATION_DATE=")){
				tpsds.setCreationDate(token.substring(14));
			}
		}
	}
	
	private void parseGET_THERMO_PROFILE_SET_INFOString(ElementSynthDataStructure ds
															, String string
															, Vector<String> errorVector
															, Vector<String> cautionVector
															, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String path = "";
		ThermoProfileSetDataStructure tpsds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				path = token.substring(5);
				tpsds = ds.getSetMap().get(path);
			}else if(token.startsWith("FOLDER_TYPE=")){
				tpsds.setFolderType(FolderType.valueOf(token.substring(12)));
			}else if(token.startsWith("DESC=")){
				String substring = token.substring(5);
				if(substring.equals("")){
					substring = "N/A";
				}
				try {
					substring = URLDecoder.decode(substring, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				tpsds.setDesc(substring);
			}else if(token.startsWith("START_TIME=")){
				String substring = token.substring(11);
				tpsds.setStartTime(substring);
			}else if(token.startsWith("STOP_TIME=")){
				String substring = token.substring(10);
				tpsds.setStopTime(substring);
			}else if(token.startsWith("NUM_PROFILES=")){
				String substring = token.substring(13);
				tpsds.setNumProfiles(Integer.valueOf(substring));
			}else if(token.startsWith("CREATION_DATE=")){
				tpsds.setCreationDate(token.substring(14));
			}
		}
	}

	private void parseIMPORT_RATE_LIBRARYString(LibraryDataStructure tmds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}

		}
	}

	private void parseIMPORT_THERMO_PROFILE_SETString(ThermoManDataStructure tmds
																, String string
																, Vector<String> errorVector
																, Vector<String> cautionVector
																, Vector<String> reasonVector){

		ThermoProfileSetDataStructure tpsds = tmds.getImportedThermoProfileSetDataStructure();
		
		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("START_TIME=")){
					String substring = token.substring(11);
					tpsds.setStartTime(substring);
				}else if(token.startsWith("STOP_TIME=")){
					String substring = token.substring(10);
					tpsds.setStopTime(substring);
				}else if(token.startsWith("NUM_PROFILES=")){
					String substring = token.substring(13);
					tpsds.setNumProfiles(Integer.valueOf(substring));
				}else if(token.startsWith("CREATION_DATE=")){
					tpsds.setCreationDate(token.substring(14));
				}

			}
	}
	
	private void parseCREATE_SIM_WORKFLOWString(ElementSimWorkDataStructure eswds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("SIM_WORKFLOW_INDEX=")){
					eswds.setIndex(Integer.valueOf(token.substring(19)));
				}
			}
	}
	
	private void parseEXECUTE_SIM_WORKFLOW_RUNString(ElementSynthDataStructure esds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseGET_SIM_WORKFLOW_TYPESString(ElementSynthDataStructure esds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		TreeMap<SimCat, ArrayList<SimTypeDataStructure>> map = new TreeMap<SimCat, ArrayList<SimTypeDataStructure>>();
		for(SimCat type: SimCat.values()){
			map.put(type, new ArrayList<SimTypeDataStructure>());
		}
		Cina.elementSynthDataStructure.setSimTypeMap(map);
		SimTypeDataStructure stds = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("CATEGORY=")){
				stds = new SimTypeDataStructure();
				stds.setSimCat(SimCat.valueOf(token.substring(9)));
				map.get(stds.getSimCat()).add(stds);
			}else if(token.startsWith("TYPE=")){
				stds.setSimTypeName(token.substring(5));
			}else if(token.startsWith("TYPE_DESC=")){
				stds.setDescription(token.substring(10));
			}else if(token.startsWith("SUNET_PATH=")){
				stds.setSunetPathArray(token.substring(11).split(","));
			}else if(token.startsWith("SUNET_DESC=")){
				stds.setSunetDescArray(token.substring(11).split(","));
			}else if(token.startsWith("INIT_ABUND_PATH=")){
				stds.setInitAbundPathArray(token.substring(16).split(","));
			}else if(token.startsWith("INIT_ABUND_DESC=")){
				stds.setInitAbundDescArray(token.substring(16).split(","));
			}else if(token.startsWith("THERMO_PATH=")){
				if(!token.substring(7).equals("")){
					stds.setThermoPath(token.substring(12));
				}
			}else if(token.startsWith("THERMO_DESC=")){	
				stds.setThermoDesc(token.substring(12));
			}else if(token.startsWith("ZONES=")){
				if(!token.substring(6).equals("")){
					String[] zoneArray = token.substring(6).split(",");
					int[] valueArray = new int[zoneArray.length];
					for(int index=0; index<zoneArray.length; index++){
						valueArray[index] = Integer.valueOf(zoneArray[index]);
					}
					stds.setZoneArray(valueArray);
				}
			}else if(token.startsWith("START_TIME=")){
				if(!token.substring(11).equals("")){
					stds.setStartTime(Double.valueOf(token.substring(11)));
				}
			}else if(token.startsWith("STOP_TIME=")){
				if(!token.substring(10).equals("")){
					stds.setStopTime(Double.valueOf(token.substring(10)));
				}
			}
		}
	}
	
	private void parseSAVE_SIM_WORKFLOW_RUNString(ElementSynthDataStructure esds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("SAVE_SIM_WORKFLOW_RUN_MESSAGE=")){	
				esds.setSaveSimWorkflowRunMessage(token.substring(30));
			}
		}
	}
	
	private void parseGET_SIM_WORKFLOW_RUN_STATUSString(ElementSimWorkRunDataStructure eswrd
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("CURRENT_ZONE=")){
				eswrd.setCurrentZone(token.substring(13));
			}else if(token.startsWith("CURRENT_LIB=")){
				eswrd.setCurrentLib(token.substring(12));
			}else if(token.startsWith("CURRENT_SCALE_FACTOR=")){
				eswrd.setCurrentScaleFactor(token.substring(21));
			}else if(token.startsWith("CURRENT_STATUS=")){
				eswrd.setCurrentStatus(ElementSynthSimWorkflowRunStatus.valueOf(token.substring(15)));
			}else if(token.startsWith("CURRENT_TEXT=")){
				eswrd.setCurrentText(token.substring(13).replaceAll("\\|", "\n"));
			}
		}
	}
	
	private void parseABORT_SIM_WORKFLOW_RUNString(ElementSynthDataStructure esds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
	
		errorFound:
		for(int i=0; i<array.length; i++){
		
			String token = array[i];
		
			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseGET_ELEMENT_SYNTHESIS_ZONESString(ElementVizDataStructure evds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		NucSimSetDataStructure nssds = null;
		String path = "";

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("PATH=")){
					path = token.substring(5);
					
					for(int j=0; j<evds.getNumberSharedNucSimSetDataStructures(); j++){
						String name = evds.getSharedNucSimSetDataStructureArray()[j].getPath();
						if(path.equals(name)){
							nssds = evds.getSharedNucSimSetDataStructureArray()[j];
						}
					}
					
					for(int j=0; j<evds.getNumberUserNucSimSetDataStructures(); j++){
						String name = evds.getUserNucSimSetDataStructureArray()[j].getPath();
						if(path.equals(name)){
							nssds = evds.getUserNucSimSetDataStructureArray()[j];
						}
					}
					
				}else if(token.startsWith("ZONES=")){
					String zones = token.substring(6);
					String[] zoneArray = zones.split(",");
					
					int numberZones = zoneArray.length;
					NucSimDataStructure[] nsdsArray = new NucSimDataStructure[numberZones];
					
					for(int j=0; j<numberZones; j++){
					
						nsdsArray[j] = new NucSimDataStructure();
						nsdsArray[j].setZone(Integer.valueOf(zoneArray[j]).intValue());
						nsdsArray[j].setPath(path);
	
					}
					nssds.setNucSimDataStructureArray(nsdsArray);
				}

			}
	}
	
	private void parseIS_MASTER_USERString(MainDataStructure mds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("IS_MASTER_USER=")){
					mds.setMasterUser(Boolean.valueOf(token.substring(15)));
				}

			}
	}
	
	private void parseIS_SIM_SENSString(ElementVizDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("IS_SIM_SENS=")){	
				String value = token.substring(12);
				ds.getCurrentNucSimDataStructure().setIsSimSens(value.toLowerCase().equals("true"));
			}else if(token.startsWith("SCALE_FACTOR=")){
				double value = Double.parseDouble(token.substring(13));
				ds.getCurrentNucSimDataStructure().setScaleFactor(value);
			}else if(token.startsWith("REACTION_RATE=")){
				ds.getCurrentNucSimDataStructure().setReactionRate(token.substring(14));
			}
		}
	}
	
	private void parseGET_LIB_DIR_LIBSString(ElementSynthDataStructure ds
											, String string
											, Vector<String> errorVector
											, Vector<String> cautionVector
											, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		ArrayList<String> list = new ArrayList<String>();
		ds.setLibDirLibList(list);

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("LIB_DIR_LIB=")){
					String substring = token.substring(12);
					list.add(substring);
				}

			}
	}
	
	private void parseGET_LIB_DIRSString(ElementSynthDataStructure ds
										, String string
										, Vector<String> errorVector
										, Vector<String> cautionVector
										, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		ArrayList<String> list = new ArrayList<String>();
		ds.setLibDirList(list);

		errorFound:
			for(int i=0; i<array.length; i++){

				String token = array[i];

				if(token.startsWith("ERROR=")){
					errorVector.add(token.substring(6));
					break errorFound; 
				}else if(token.startsWith("CAUTION=")){
					cautionVector.add(token.substring(8));
				}else if(token.startsWith("REASON=")){	
					reasonVector.add(token.substring(7));
				}else if(token.startsWith("LIB_DIR=")){
					String substring = token.substring(8);
					list.add(substring);
				}

			}
	}
	
	private void parseGET_LIB_DIRSString(RateLibManDataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		ArrayList<String> list = new ArrayList<String>();
		ds.setLibDirList(list);

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("LIB_DIR=")){
				String substring = token.substring(8);
				list.add(substring);
			}

		}
	}
	
	private void parseGET_LIB_DIR_INFOString(RateLibManDataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		LibraryDirectoryDataStructure ldds = ds.getLibDirDS(); 
		ArrayList<String> libList = new ArrayList<String>();
		ldds.setLibList(libList);

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("NAME=")){
				String substring = token.substring(5);
				ldds.setName(substring);
			}else if(token.startsWith("NOTES=")){
				String substring = token.substring(6);
				ldds.setNotes(substring);
			}else if(token.startsWith("LIB=")){
				String substring = token.substring(4);
				libList.add(substring);
			}else if(token.startsWith("CREATION_DATE=")){
				String substring = token.substring(14);
				ldds.setCreationDate(substring);
			}

		}
	}
	
	private void parseGET_SENS_NETWORK_ISOTOPESString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		TreeMap<Integer, ArrayList<IsotopePoint>> map = new TreeMap<Integer, ArrayList<IsotopePoint>>();
		RateLibDataStructure rlds = ds.getRateLibDataStructure();

		if(rlds==null){
			string = "ERROR=An error has occurred. Rate Library can not be located.";
		}

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("SENS_SIM_WORKFLOW_RUN_INDEX=")){
				ds.setSensSimWorkflowRunIndex(Integer.parseInt(token.substring(28)));
			}else if(token.startsWith("ISOTOPES=")){
				if(token.substring(9).length()>0){
					String substring = token.substring(9);
					String[] subarray = substring.split("\t");
					for(String subsubstring: subarray){
						if(!subsubstring.trim().equals("")){
							String[] ipArray = subsubstring.split(",");
							int z = Integer.valueOf(ipArray[0]);
							int a = Integer.valueOf(ipArray[1]);
							IsotopePoint ip = new IsotopePoint(z, a);
							if(map.get(z)==null){
								map.put(z, new ArrayList<IsotopePoint>());
							}
							map.get(z).add(ip);
						}
					}
				}
			}
			
			
		}
		
		Iterator<ArrayList<IsotopePoint>> itr = map.values().iterator();
		while(itr.hasNext()){
			Collections.sort(itr.next());
		}
		rlds.setIsotopeMapAvailable(map);
	}

	private void parseGET_SENS_NETWORK_REACTIONSString(ElementSynthDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		ReactionDataStructure rds = null;
		IsotopePoint ip = null;
		RateLibDataStructure rlds = ds.getRateLibDataStructure();
		TreeMap<IsotopePoint, ArrayList<ReactionDataStructure>> map = new TreeMap<IsotopePoint, ArrayList<ReactionDataStructure>>();

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("ISOTOPE=")){
				String substring = token.substring(8);
				String[] subarray = substring.split(",");
				int z = Integer.valueOf(subarray[0]);
				int a = Integer.valueOf(subarray[1]);
				ip = new IsotopePoint(z, a);
			}else if(token.startsWith("REACTION_INDEX=")){
				String substring = token.substring(15);
				rds = new ReactionDataStructure();
				rds.setReactionIndex(Integer.valueOf(substring));
				rds.setIsotopePoint(ip);
				if(map.get(ip)==null){
					map.put(ip, new ArrayList<ReactionDataStructure>());
				}
				map.get(ip).add(rds);
			}else if(token.startsWith("REACTION_NAME=")){
				String substring = token.substring(14);
				rds.setReactionName(substring);
			}else if(token.startsWith("DECAY=")){
				String substring = token.substring(6);
				rds.setDecay(ReactionDataStructure.DecayType.getDecayType(substring.trim()));
			}else if(token.startsWith("REACTION_TYPE=")){
				String substring = token.substring(14);
				rds.setReactionType(Integer.valueOf(substring.trim()));
			}
		}

		if(errorVector.size()==0 && rlds!=null){
			rlds.setReactionMap(map);
		}
	}
	
	private void parseGET_SIMSString(ElementManDataStructure ds
														, String string
														, Vector<String> errorVector
														, Vector<String> cautionVector
														, Vector<String> reasonVector){

		TreeMap<String, ElementSimDataStructure> map = new TreeMap<String, ElementSimDataStructure>(new org.nucastrodata.format.StringComparatorIgnoreCase());
		ElementSimDataStructure esds = null;
		FolderType folderType = null;
		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				esds = new ElementSimDataStructure();
				esds.setFolderType(folderType);
				esds.setName(token.substring(5));
				esds.setPath(esds.getFolderType().name() + "/" + esds.getName());
				map.put(esds.getPath(), esds);
			}
		}
		
		ds.setSimMap(map);
	}
	
	private void parseGET_SIMSString(ElementSynthDataStructure ds
										, String string
										, Vector<String> errorVector
										, Vector<String> cautionVector
										, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		FolderType folderType = null;
		NucSimSetDataStructure nssds = null;
		TreeMap<FolderType, ArrayList<NucSimSetDataStructure>> map = new TreeMap<FolderType, ArrayList<NucSimSetDataStructure>>();

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				String name = token.substring(5);
				String path = "";
				if(folderType==FolderType.USER){
					if(!map.containsKey(FolderType.USER)){
						map.put(FolderType.USER, new ArrayList<NucSimSetDataStructure>());
					}
					path = "USER/";
					nssds = new NucSimSetDataStructure();
					nssds.setPath(path + name);
					map.get(FolderType.USER).add(nssds);
				}else if(folderType==FolderType.SHARED){
					if(!map.containsKey(FolderType.SHARED)){
						map.put(FolderType.SHARED, new ArrayList<NucSimSetDataStructure>());
					}
					path = "SHARED/";
					nssds = new NucSimSetDataStructure();
					nssds.setPath(path + name);
					map.get(FolderType.SHARED).add(nssds);
				}
				
			}
			
		}

		Iterator<FolderType> itr = map.keySet().iterator();
		while(itr.hasNext()){
		
			folderType = itr.next();
			ArrayList<NucSimSetDataStructure> list = map.get(folderType);
			NucSimSetDataStructure[] outputArray = new NucSimSetDataStructure[list.size()];
			int counter = 0;
			for(NucSimSetDataStructure d: list){
				outputArray[counter] = d;
				counter++;
			}
			
			if(folderType==FolderType.USER){
			
				ds.setNumberUserNucSimSetDataStructures(list.size());
				ds.setUserNucSimSetDataStructureArray(outputArray);
			
			}else if(folderType==FolderType.SHARED){
				
				ds.setNumberSharedNucSimSetDataStructures(list.size());
				ds.setSharedNucSimSetDataStructureArray(outputArray);
				
			}
		
		}
		
	}

	private void parseGET_SIMSString(ElementVizDataStructure ds
										, String string
										, Vector<String> errorVector
										, Vector<String> cautionVector
										, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		FolderType folderType = null;
		NucSimSetDataStructure nssds = null;
		TreeMap<FolderType, ArrayList<NucSimSetDataStructure>> map = new TreeMap<FolderType, ArrayList<NucSimSetDataStructure>>();

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("FOLDER_TYPE=")){
				folderType = FolderType.valueOf(token.substring(12));
			}else if(token.startsWith("NAME=")){
				String name = token.substring(5);
				String path = "";
				if(folderType==FolderType.USER){
					if(!map.containsKey(FolderType.USER)){
						map.put(FolderType.USER, new ArrayList<NucSimSetDataStructure>());
					}
					path = "USER/";
					nssds = new NucSimSetDataStructure();
					nssds.setPath(path + name);
					map.get(FolderType.USER).add(nssds);
				}else if(folderType==FolderType.SHARED){
					if(!map.containsKey(FolderType.SHARED)){
						map.put(FolderType.SHARED, new ArrayList<NucSimSetDataStructure>());
					}
					path = "SHARED/";
					nssds = new NucSimSetDataStructure();
					nssds.setPath(path + name);
					map.get(FolderType.SHARED).add(nssds);
				}
				
			}
			
		}

		Iterator<FolderType> itr = map.keySet().iterator();
		while(itr.hasNext()){
		
			folderType = itr.next();
			ArrayList<NucSimSetDataStructure> list = map.get(folderType);
			NucSimSetDataStructure[] outputArray = new NucSimSetDataStructure[list.size()];
			int counter = 0;
			for(NucSimSetDataStructure d: list){
				outputArray[counter] = d;
				counter++;
			}
			
			if(folderType==FolderType.USER){
			
				ds.setNumberUserNucSimSetDataStructures(list.size());
				ds.setUserNucSimSetDataStructureArray(outputArray);
			
			}else if(folderType==FolderType.SHARED){
				
				ds.setNumberSharedNucSimSetDataStructures(list.size());
				ds.setSharedNucSimSetDataStructureArray(outputArray);
				
			}
		
		}

	}
	
	private void parseGET_TOTAL_WEIGHTSString(NucSimSetDataStructure ds
					, String string
					, Vector<String> errorVector
					, Vector<String> cautionVector
					, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("TOTAL_WEIGHTS=")){
				String s = token.substring(14);
				ds.setTotalWeights(Double.parseDouble(s));
			}
		}
	}
	
	private void parseGET_SIM_INFOString(ElementManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		String path = "";
		ElementSimDataStructure esds = null;
		TreeMap<SimProperty, String> map = null;
		
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("PATH=")){
				path = token.substring(5);
				esds = ds.getSimMapSelected().get(path);
				map = esds.getPropMap();
			}else if(token.startsWith("FOLDER_TYPE=")){
				esds.setFolderType(FolderType.valueOf(token.substring(12)));
			}else if(token.startsWith("SIM_NAME=")){
				esds.setName(token.substring(9));
			}else if(token.startsWith("THERMO_PATH=")){
				map.put(SimProperty.THERMO_PATH, token.substring(12));
			}else if(token.startsWith("LIBRARY_PATH=")){
				map.put(SimProperty.LIBRARY_PATH, token.substring(13));
			}else if(token.startsWith("AL26_TYPE=")){
				map.put(SimProperty.Al26_TYPE, token.substring(10));
			}else if(token.startsWith("SUNET_PATH=")){
				String[] comps = token.substring(11).split("/");
				map.put(SimProperty.SUNET_PATH, "PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("SIM_TYPE=")){
				map.put(SimProperty.SIM_TYPE, token.substring(9));
			}else if(token.startsWith("NOTES=")){
				map.put(SimProperty.NOTES, token.substring(6));
			}else if(token.startsWith("INIT_ABUND_PATH=")){
				String[] comps = token.substring(16).split("/");
				map.put(SimProperty.INIT_ABUND_PATH, "PUBLIC/" + comps[comps.length-1]);
			}else if(token.startsWith("MAX_TIMESTEPS=")){
				map.put(SimProperty.MAX_TIMESTEPS, token.substring(14));
			}else if(token.startsWith("INCLUDE_WEAK=")){
				map.put(SimProperty.INCLUDE_WEAK, token.substring(13));
			}else if(token.startsWith("INCLUDE_SCREENING=")){
				map.put(SimProperty.INCLUDE_SCREENING, token.substring(18));
			}else if(token.startsWith("START_TIME=")){
				map.put(SimProperty.START_TIME, token.substring(11));
			}else if(token.startsWith("STOP_TIME=")){
				map.put(SimProperty.STOP_TIME, token.substring(10));
			}else if(token.startsWith("ZONES=")){
				map.put(SimProperty.ZONES, token.substring(6));
			}else if(token.startsWith("CREATION_DATE=")){
				map.put(SimProperty.CREATION_DATE, token.substring(14));
			}else if(token.startsWith("SCALE_FACTOR=")){
				map.put(SimProperty.SCALE_FACTOR, token.substring(13));
			}else if(token.startsWith("REACTION_RATE=")){
				map.put(SimProperty.REACTION_RATE, token.substring(14));
			}else if(token.startsWith("PARAMS=")){
				map.put(SimProperty.PARAMS, token.substring(7));
			}else if(token.startsWith("LOOPING_TYPE=")){
				map.put(SimProperty.LOOPING_TYPE, token.substring(13));
			}else if(token.startsWith("LIB_DIR=")){
				map.put(SimProperty.LIB_DIR, token.substring(8));
			}
		}
	}
	
	private void parseERASE_SIMSString(ElementManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseCOPY_SIMS_TO_SHAREDString(ElementManDataStructure ds
			, String string
			, Vector<String> errorVector
			, Vector<String> cautionVector
			, Vector<String> reasonVector){

		String[] array = string.split("\n"); 

		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				errorVector.trimToSize();
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}
		}
	}
	
	private void parseGET_WAITING_POINTSString(WaitingDataStructure ds
												, String string
												, Vector<String> errorVector
												, Vector<String> cautionVector
												, Vector<String> reasonVector){

		String[] array = string.split("\n"); 
		HashMap<PointType, TreeMap<IsotopePoint, WaitingPointDataStructure>> map = null;
		errorFound:
		for(int i=0; i<array.length; i++){

			String token = array[i];

			if(token.startsWith("ERROR=")){
				errorVector.add(token.substring(6));
				break errorFound; 
			}else if(token.startsWith("CAUTION=")){
				cautionVector.add(token.substring(8));
			}else if(token.startsWith("REASON=")){	
				reasonVector.add(token.substring(7));
			}else if(token.startsWith("WAITING_POINTS=")){
				if(!token.substring(15).trim().equals("")){
					map = new HashMap<PointType, TreeMap<IsotopePoint, WaitingPointDataStructure>>();
					TreeMap<IsotopePoint, WaitingPointDataStructure> pointMap = new TreeMap<IsotopePoint, WaitingPointDataStructure>();
					String[] subarray = token.substring(15).split(" ");
					for(String subtoken: subarray){
						String[] subsubarray = subtoken.split(",");
						int z = Integer.valueOf(subsubarray[0]);
						int n = Integer.valueOf(subsubarray[1]);
						WaitingPointDataStructure wpds = new WaitingPointDataStructure();
						IsotopePoint ip = new IsotopePoint(z, z+n); 
						wpds.setPoint(ip);
						wpds.setDecision(Double.valueOf(subsubarray[2]));
						ArrayList<Double> list = new ArrayList<Double>();
						list.add(Double.valueOf(subsubarray[3]));
						list.add(Double.valueOf(subsubarray[4]));
						list.add(Double.valueOf(subsubarray[5]));
						list.add(Double.valueOf(subsubarray[6]));
						list.add(Double.valueOf(subsubarray[7]));
						list.add(Double.valueOf(subsubarray[8]));
						list.add(Double.valueOf(subsubarray[9]));
						wpds.setFlagList(list);
						pointMap.put(ip, wpds);
					}
					map.put(WaitingDataStructure.PointType.YES, pointMap);
				}
			}
		}
		ds.setPointMap(map);
	}
	
	private Reaction getReaction(int zout, int nout, int type){
		
		Reaction r = new Reaction();
		int aout = nout + zout;
		r.setPointOut(new IsotopePoint(zout, aout));
		int zin = 0;
		int nin = 0;
		switch(type){
		
			case 1:
				zin = zout-1;
				nin = nout;
				break;
			case 2:
				zin = zout-1;
				nin = nout-2;
				break;
			case 3:
				zin = zout-2;
				nin = nout-2;
				break;
		
		}
		r.setPointIn(new IsotopePoint(zin, zin + nin));
		return r;
		
	}
	
	private void parseGET_BOTTLENECK_REACTIONSString(BottleDataStructure ds
													, String string
													, Vector<String> errorVector
													, Vector<String> cautionVector
													, Vector<String> reasonVector){
		
		ArrayList<Reaction> listMajor = new ArrayList<Reaction>();
		ArrayList<Reaction> listMinor = new ArrayList<Reaction>();
		TreeMap<Integer, ArrayList<Integer>> failedMapList = new TreeMap<Integer, ArrayList<Integer>>();
		
		if(string.indexOf("ERROR")!=-1){
			errorVector.add("The simulation you have chosen is too old to be used with the Bottleneck Reaction Finder. "
								+ "Only recent simulations contain the data required for the Bottleneck Reaction Finder to work properly. "
								+ "Please try a more recently calculated simulation. Thank you.");
			return;
		}
		
		if(string.startsWith("RESULTS=")){

			string = string.substring(8);
			String[] array = string.split("\n");
			
			for(int i=0; i<array.length; i++){
				
				StringTokenizer st = new StringTokenizer(array[i]);
				String[] subarray = new String[st.countTokens()];
				int counter=0;
				while(st.hasMoreElements()){
					subarray[counter] = st.nextToken();
					counter++;
				}
				
				if(subarray.length==6){
					
					int z = Integer.valueOf(subarray[1]);
					int n = Integer.valueOf(subarray[2]);
					int type = Integer.valueOf(subarray[4]);
					int dec = Integer.valueOf(subarray[5]);
					
					Reaction r = getReaction(z, n, type);
					
					if(dec==1){
						listMinor.add(r);
					}else if(dec==2){
						listMajor.add(r);
					}
					
				}else if(subarray.length==2){
					
					int reason = Integer.valueOf(subarray[1]);
					
					if(!failedMapList.containsKey(reason)){
						failedMapList.put(reason, new ArrayList<Integer>());
					}
					
					failedMapList.get(reason).add(Integer.valueOf(subarray[0]));
					
				}
				
			}
			
		}
		
		ds.setListMajor(listMajor);
		ds.setListMinor(listMinor);
		ds.setFailedMapList(failedMapList);
	}
	
	private Calendar getCalendar(String string){
		Calendar calendar = Calendar.getInstance();
		String day = string.split(" ")[0];
		String time = string.split(" ")[1];
		int year = Integer.valueOf(day.split("-")[0]);
		int month = Integer.valueOf(day.split("-")[1])-1;
		int date = Integer.valueOf(day.split("-")[2]);
		int hourOfDay = Integer.valueOf(time.split(":")[0]);
		int minute = Integer.valueOf(time.split(":")[1]);
		int second = Integer.valueOf(time.split(":")[2]);
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar;
	}
	
	public void actionPerformed(ActionEvent ae){
		
	}
	
}
