package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.Vector;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.AbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.DerAbundTimestepDataStructure;
import org.nucastrodata.datastructure.util.FluxTimestepDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizAnimatorFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	private boolean abundSelected, fluxSelected, sumFlux, noFluxesAvailable;
	private boolean allGoodTimeMappings
						, allGoodIsotopeMappings
						, allGoodAbundProfiles
						, allGoodThermoProfiles
						, allGoodReactionMappings
						, allGoodFluxProfiles;	

	public ElementVizAnimatorFrameWorker(ElementVizDataStructure ds
												, ElementVizFrame frame
												, ElementVizToolsPanel parent
												, boolean abundSelected
												, boolean fluxSelected
												, boolean sumFlux){
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		this.abundSelected = abundSelected;
		this.fluxSelected = fluxSelected;
		this.sumFlux = sumFlux;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){

    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
		
    	if(abundSelected && !fluxSelected){
	
    		allGoodTimeMappings = true;
        	allGoodIsotopeMappings = true;
        	allGoodAbundProfiles = true;
        	allGoodThermoProfiles = true;
	
			ds.setAnimatorType("abund");
			
			ds.setIsotopes("");
			ds.setFinal_step("N");

			String lastSubPath = "";
			NucSimDataStructure lastNSDS = null;

        	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
        	
        		NucSimDataStructure nsds = ds.getNucSimDataStructureArrayAnimator()[i];
        	
        		nsds.setAbundTimestepDataStructureArray(null);
        		nsds.setDerAbundTimestepDataStructureArray(null);
        		nsds.setFluxTimestepDataStructureArray(null);
        	
    			ds.setPath(nsds.getPath());
    			ds.setZones(String.valueOf(nsds.getZone()));
    			ds.setZone(String.valueOf(nsds.getZone()));
    			ds.setCurrentNucSimDataStructure(nsds);
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame)){allGoodTimeMappings = false;}
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", frame)){allGoodThermoProfiles = false;}
    		
    			if(isCancelled()){break;}
    		
    			String currentPath = nsds.getPath();
    			if(currentPath.contains("_dir_")){
    				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
    				if(!currentSubPath.equals(lastSubPath)){
    					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    					lastSubPath = currentSubPath;
    					lastNSDS = nsds;
    				}else{
    					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
    					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
    				}
    			}else{
    				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    			}

    			if(isCancelled()){break;}

    			dialog.setCurrentValue(0);
    			dialog.appendText("Downloading abundance data for "
											+ ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName()
											+ "...");
        		
        		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", frame, dialog)){
        			dialog.appendText("DONE!\n");

					nsds.setDerAbundTimestepDataStructureArray(getDerAbundTimestepDataStructureArray(ds.getCurrentNucSimDataStructure().getAbundTimestepDataStructureArray()
																																		, ds.getCurrentNucSimDataStructure().getTimeArray()
																																		, ds.getCurrentNucSimDataStructure().getZAMapArray()));
					nsds.setDerAbundTimestepDataStructureArray(normalizeDerAbundances(ds.getNucSimDataStructureArrayAnimator()[i]));
						
				}else{
					allGoodAbundProfiles = false;
				}
    		}
			
    	}else if(!abundSelected && fluxSelected){
    	
    		allGoodTimeMappings = true;
        	allGoodIsotopeMappings = true;
        	allGoodThermoProfiles = true;
			allGoodReactionMappings = true;
			allGoodFluxProfiles = true;
    	
    		ds.setAnimatorType("flux");
    		
    		noFluxesAvailable = true;
    		
    		ds.setReactions("");
			
			if(sumFlux){
			
				ds.setSum("Y");
			
			}else{
			
				ds.setSum("N");
			
			}
			
			String lastSubPath = "";
			NucSimDataStructure lastNSDS = null;

        	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
        	
        		NucSimDataStructure nsds = ds.getNucSimDataStructureArrayAnimator()[i];
    			
        		nsds.setAbundTimestepDataStructureArray(null);
        		nsds.setDerAbundTimestepDataStructureArray(null);
        		nsds.setFluxTimestepDataStructureArray(null);
    			
    			ds.setPath(nsds.getPath());
    			ds.setZone(String.valueOf(nsds.getZone()));
    			ds.setZones(String.valueOf(nsds.getZone()));
    			ds.setCurrentNucSimDataStructure(nsds);
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame)){allGoodTimeMappings = false;}
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", frame)){allGoodThermoProfiles = false;}
        		
    			if(isCancelled()){break;}
        		
    			String currentPath = nsds.getPath();
    			if(currentPath.contains("_dir_")){
    				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
    				if(!currentSubPath.equals(lastSubPath)){
    					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    					lastSubPath = currentSubPath;
    					lastNSDS = nsds;
    				}else{
    					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
    					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
    				}
    			}else{
    				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    			}
        		
    			if(isCancelled()){break;}
        		
        		if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUX MAPPING", frame)){allGoodReactionMappings = false;}
        		
        		if(isCancelled()){break;}
        		
        		dialog.setCurrentValue(0);
        		dialog.appendText("Downloading reaction flux data for "
											+ ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName()
											+ "...");	
        			
				if(nsds.getHasFluxes()){
					
					if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUXES", frame, dialog)){
				
						noFluxesAvailable = false;
				
						dialog.appendText("DONE!\n");

						nsds.setFluxTimestepDataStructureArray(normalizeFluxes(ds.getCurrentNucSimDataStructure()));
						nsds.setReactionMapArray(ds.getCurrentNucSimDataStructure().getReactionMapArray());
						
					}else{
				
						allGoodFluxProfiles = false;

					}	
					
				}else{
				
					dialog.appendText("REACTION FLUX DATA NOT AVAILABLE!");
				
				}
    		}
    	
    	}else if(abundSelected && fluxSelected){
    	
    		ds.setAnimatorType("both");
    		
    		allGoodTimeMappings = true;
        	allGoodIsotopeMappings = true;
        	allGoodThermoProfiles = true;
			allGoodReactionMappings = true;
			allGoodFluxProfiles = true;
			allGoodAbundProfiles = true;
			
			noFluxesAvailable = true;
			
			ds.setReactions("");
			ds.setIsotopes("");
			ds.setFinal_step("N");
			
			if(sumFlux){
			
				ds.setSum("Y");
			
			}else{
			
				ds.setSum("N");
			
			}
			
			String lastSubPath = "";
			NucSimDataStructure lastNSDS = null;

        	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
        	
        		NucSimDataStructure nsds = ds.getNucSimDataStructureArrayAnimator()[i];
        	
        		nsds.setAbundTimestepDataStructureArray(null);
        		nsds.setDerAbundTimestepDataStructureArray(null);
        		nsds.setFluxTimestepDataStructureArray(null);
    		
    			ds.setPath(nsds.getPath());
    			ds.setZone(String.valueOf(nsds.getZone()));
    			ds.setZones(String.valueOf(nsds.getZone()));
    			ds.setCurrentNucSimDataStructure(nsds);
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame)){allGoodTimeMappings = false;}
    			
    			if(isCancelled()){break;}
    			
    			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS THERMO PROFILE", frame)){allGoodThermoProfiles = false;}
    			
    			if(isCancelled()){break;}
    			
    			String currentPath = nsds.getPath();
    			if(currentPath.contains("_dir_")){
    				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
    				if(!currentSubPath.equals(lastSubPath)){
    					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    					lastSubPath = currentSubPath;
    					lastNSDS = nsds;
    				}else{
    					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
    					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
    				}
    			}else{
    				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", frame)){allGoodIsotopeMappings = false;}
    			}
    			
    			if(isCancelled()){break;}
    			
				dialog.setCurrentValue(0);
				dialog.appendText("Downloading abundance data for "
											+ ds.getNucSimDataStructureArrayAnimator()[i].getNucSimName()
											+ "...");
        		
        		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", frame, dialog)){
        			dialog.appendText("DONE!\n");
        			
        			nsds.setDerAbundTimestepDataStructureArray(getDerAbundTimestepDataStructureArray(ds.getCurrentNucSimDataStructure().getAbundTimestepDataStructureArray()
																																				, ds.getCurrentNucSimDataStructure().getTimeArray()
																																				, ds.getCurrentNucSimDataStructure().getZAMapArray()));
        			nsds.setDerAbundTimestepDataStructureArray(normalizeDerAbundances(ds.getNucSimDataStructureArrayAnimator()[i]));
					
        		}else{
        		
        			allGoodAbundProfiles = false;
        		
        		}	
        		
        		dialog.setCurrentValue(0);
        		dialog.appendText("Downloading reaction flux data for "
								+ nsds.getNucSimName()
								+ "...");
        		
        		if(isCancelled()){break;}
        		
        		if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUX MAPPING", frame)){allGoodReactionMappings = false;}
        		
        		if(isCancelled()){break;}
        		
        		if(nsds.getHasFluxes()){
					
					if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUXES", frame, dialog)){
				
						noFluxesAvailable = false;
				
						dialog.appendText("DONE!\n");

						nsds.setFluxTimestepDataStructureArray(normalizeFluxes(ds.getCurrentNucSimDataStructure()));
						nsds.setReactionMapArray(ds.getCurrentNucSimDataStructure().getReactionMapArray());
						
					}else{
				
						allGoodFluxProfiles = false;

					}	
					
				}else{
				
					dialog.appendText("REACTION FLUX DATA NOT AVAILABLE!");
				
				}
    		}
    	
    	}else if(!abundSelected && !fluxSelected){
		
			String string = "Please select one or both data type checkboxes.";
			Dialogs.createExceptionDialog(string, frame);
		
		}
   
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(abundSelected && !fluxSelected){
		
			if(allGoodAbundProfiles 
					&& allGoodTimeMappings 
					&& allGoodIsotopeMappings 
					&& allGoodThermoProfiles
					&& !this.isCancelled()){

				ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArrayAnimator()));
	
				parent.openAnimatorFrame();
	    	
			}
		
		}else if(!abundSelected && fluxSelected && !this.isCancelled()){
		
			if(!noFluxesAvailable){
				
				if(allGoodFluxProfiles 
					&& allGoodTimeMappings 
					&& allGoodIsotopeMappings 
					&& allGoodReactionMappings 
					&& allGoodThermoProfiles){

					ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArrayAnimator()));
					
					parent.openAnimatorFrame();
					
				}
				
			}else{

		 		String string = "There is no flux data available for the simulations you have chosen. To view abundance data for these simulations, uncheck the \"Reaction Flux\" checkbox after clicking the \"Element Synthesis Animator\" button.";
		 		Dialogs.createExceptionDialog(string, frame);
		 	
		 	}
		
		}else if(abundSelected && fluxSelected && !this.isCancelled()){
		
			if(!noFluxesAvailable){
				
				if(allGoodFluxProfiles 
					&& allGoodAbundProfiles 
					&& allGoodTimeMappings 
					&& allGoodIsotopeMappings 
					&& allGoodReactionMappings 
					&& allGoodThermoProfiles){

					ds.setNormalTimeArray(getNormalTimeArray(ds.getNucSimDataStructureArrayAnimator()));
					
					parent.openAnimatorFrame();
					
				}
				
			}else{
			 	
		 		String string = "There is no flux data available for the simulations you have chosen. To view abundance data for these simulations, uncheck the \"Reaction Flux\" checkbox after clicking the \"Element Synthesis Animator\" button.";
		 		Dialogs.createExceptionDialog(string, frame);
		 	
		 	}
		
		}
		
	}
	
	private DerAbundTimestepDataStructure[] getDerAbundTimestepDataStructureArray(AbundTimestepDataStructure[] abundArray
			, double[] timeArray
			, Point[] ZAMapArray){

		DerAbundTimestepDataStructure[] outputArray = new DerAbundTimestepDataStructure[timeArray.length-1];

		for(int i=0; i<timeArray.length-1; i++){

			outputArray[i] = new DerAbundTimestepDataStructure();

			Vector indexVector = new Vector();
			Vector derAbundVector = new Vector();

			double delTime = timeArray[i+1] - timeArray[i];

			float currentAbund = 0.0f;
			float nextAbund = 0.0f;

			AbundTimestepDataStructure currentAppatds = abundArray[i];
			AbundTimestepDataStructure nextAppatds = abundArray[i+1];

			for(int j=0; j<ZAMapArray.length; j++){

				foundCurrentAbund:
					for(int k=0; k<currentAppatds.getIndexArray().length; k++){

						if(currentAppatds.getIndexArray()[k]==j){

							currentAbund = currentAppatds.getAbundArray()[k];
							break foundCurrentAbund;

						}else{

							currentAbund = 0.0f;

						}

					}

			foundNextAbund:
				for(int k=0; k<nextAppatds.getIndexArray().length; k++){

					if(nextAppatds.getIndexArray()[k]==j){

						nextAbund = nextAppatds.getAbundArray()[k];
						break foundNextAbund;

					}else{

						nextAbund = 0.0f;

					}

				}

					float derAbund = (float)((nextAbund - currentAbund)/delTime);

					if(derAbund!=0.0 && delTime!=0.0){

						indexVector.addElement(new Integer(j));
						derAbundVector.addElement(new Float(derAbund));

					}

			}

			indexVector.trimToSize();
			derAbundVector.trimToSize();

			int[] indexArray = new int[indexVector.size()];

			float[] derAbundArray = new float[derAbundVector.size()];

			for(int j=0; j<indexArray.length; j++){

				indexArray[j] = ((Integer)(indexVector.elementAt(j))).intValue();
				derAbundArray[j] = ((Float)(derAbundVector.elementAt(j))).floatValue();

			}

			outputArray[i].setIndexArray(indexArray);
			outputArray[i].setDerAbundArray(derAbundArray);

		}

		return outputArray;

	}

	private DerAbundTimestepDataStructure[] normalizeDerAbundances(NucSimDataStructure appnsds){
	    
    	float max = 0.0f;
    	
    	for(int i=0; i<appnsds.getTimeArray().length-1; i++){
		
			for(int j=0; j<appnsds.getDerAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){

    			max = Math.max(max, Math.abs(appnsds.getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j]));
    		
    		}
    	
    	}
    	
    	for(int i=0; i<appnsds.getTimeArray().length-1; i++){
    	
    		for(int j=0; j<appnsds.getDerAbundTimestepDataStructureArray()[i].getIndexArray().length; j++){
    			
    			appnsds.getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j] = (float)(appnsds.getDerAbundTimestepDataStructureArray()[i].getDerAbundArray()[j]/max);
    		
    		}
    	
    	}
    	
    	return appnsds.getDerAbundTimestepDataStructureArray();
    
    }
    
	private FluxTimestepDataStructure[] normalizeFluxes(NucSimDataStructure appnsds){
	    
    	float max = 0.0f;
    	
    	for(int i=0; i<appnsds.getTimeArray().length; i++){
		
			for(int j=0; j<appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
    				
    			max = Math.max(max, Math.abs(appnsds.getFluxTimestepDataStructureArray()[i].getFluxArray()[j]));
    		
    		}
    	
    	}
    	
    	appnsds.setFluxNorm(max);
    	
    	for(int i=0; i<appnsds.getTimeArray().length; i++){
    	
    		for(int j=0; j<appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
    			
    			appnsds.getFluxTimestepDataStructureArray()[i].getFluxArray()[j] = (float)(appnsds.getFluxTimestepDataStructureArray()[i].getFluxArray()[j]/max);
    		
    		}
    	
    	}
    	
    	return appnsds.getFluxTimestepDataStructureArray();
    
	}    
	
	private double[] getNormalTimeArray(NucSimDataStructure[] appnsdsa){
	    
    	double[] normalTimeArray = new double[appnsdsa.length];

		for(int i=0; i<normalTimeArray.length; i++){

			double currentTemp = 0.0;
			double previousTemp = 0.0;
			boolean foundDifferentTemp = false;
			
			foundDifferentTemp:
			for(int j=1; j<appnsdsa[i].getTempArray().length; j++){
			
				previousTemp = appnsdsa[i].getTempArray()[j-1];
				currentTemp = appnsdsa[i].getTempArray()[j];
				
				if(previousTemp!=currentTemp){
					foundDifferentTemp = true;
					break foundDifferentTemp;
				}
				
			}
			
			if(foundDifferentTemp){
			
				double maxTemp = 0.0;
				
				for(int j=0; j<appnsdsa[i].getTempArray().length; j++){
				
					maxTemp = Math.max(maxTemp, appnsdsa[i].getTempArray()[j]);
					
					if(maxTemp==appnsdsa[i].getTempArray()[j]){
					
						normalTimeArray[i] = appnsdsa[i].getTimeArray()[j];
					
					}
					
				}
			
			}else{
				
				normalTimeArray[i] = appnsdsa[i].getTimeArray()[0];
				
			}
			
		}	
	
		return normalTimeArray;
    
    }
	
}