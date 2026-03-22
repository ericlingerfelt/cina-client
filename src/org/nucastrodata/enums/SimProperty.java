package org.nucastrodata.enums;

public enum SimProperty {

	CREATION_DATE("Creation Date")
	, SIM_TYPE("Simulation Type")
	, THERMO_PATH("Thermo Profile Set") 
	, MAX_TIMESTEPS("Number of Timesteps Before Exit")
	, START_TIME("Start Time (sec)")
	, STOP_TIME("Stop Time (sec)")
	, INCLUDE_WEAK("Include Weak Reactions")
	, INCLUDE_SCREENING("Include Screening")
	, NOTES("User Notes")
	, ZONES("Selected Zones")
	, Al26_TYPE("26Al State")
	, INIT_ABUND_PATH("Initial Abundance Profile")
	, SUNET_PATH("Sunet File")
	, LIBRARY_PATH("Rate Library")
	, REACTION_RATE("Reaction Rate")
	, SCALE_FACTOR("Scale Factor")
	, PARAMS("Scaled Parameters")
	, LIB_DIR("Rate Library Directory")
	, LOOPING_TYPE("Looping Type");
	
	private String string;

	SimProperty(String string){
		this.string = string;
	}
	
	public String toString(){return string;}
	
}
