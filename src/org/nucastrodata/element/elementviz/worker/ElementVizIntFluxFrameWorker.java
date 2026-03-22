package org.nucastrodata.element.elementviz.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizIntFluxFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	private boolean noFluxesAvailable, sumFlux;
	private boolean allGoodTimeMappings
						, allGoodIsotopeMappings
						, allGoodAbundProfiles
						, allGoodReactionMappings
						, allGoodFluxProfiles;	

	public ElementVizIntFluxFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame
											, ElementVizToolsPanel parent
											, boolean sumFlux){
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		this.sumFlux = sumFlux;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		allGoodTimeMappings = true;
    	allGoodIsotopeMappings = true;
		allGoodReactionMappings = true;
		allGoodFluxProfiles = true;
		allGoodAbundProfiles = true;
    	
		noFluxesAvailable = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
		
    	ds.setReactions("");
		ds.setIsotopes("");
		ds.setFinal_step("Y");
		
		if(sumFlux){
			ds.setSum("Y");
		}else{
			ds.setSum("N");
		}

		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;
		
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

			NucSimDataStructure nsds = ds.getNucSimDataStructureArrayIntFlux()[i];
		
			nsds.setAbundTimestepDataStructureArray(null);
			nsds.setFluxTimestepDataStructureArray(null);
		
			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);
			
			if(isCancelled()){break;}
			
			if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS TIME MAPPING", frame)){allGoodTimeMappings = false;}
			
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
			dialog.appendText("Downloading final abundance data for " + nsds.getNucSimName() + "...");
										
    		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", frame, dialog)){
    			dialog.appendText("DONE!\n");
    		}else{
    			allGoodAbundProfiles = false;
    		}	
    		
    		if(isCancelled()){break;}
    		
    		dialog.setCurrentValue(0);
    		dialog.appendText("Downloading reaction flux data for " + nsds.getNucSimName() + "...");
    		
    		if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUX MAPPING", frame)){allGoodReactionMappings = false;}
    		if(nsds.getHasFluxes()){
				
    			if(isCancelled()){break;}
				
				if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS FLUXES", frame, dialog)){
			
					noFluxesAvailable = false;
			
					dialog.appendText("DONE!\n");

					nsds.setFluxTimestepDataStructureArray(ds.getCurrentNucSimDataStructure().getFluxTimestepDataStructureArray());
					nsds.setReactionMapArray(ds.getCurrentNucSimDataStructure().getReactionMapArray());

					dialog.setCurrentValue(0);
					dialog.setMaximum(nsds.getReactionMapArray().length);
					dialog.appendText("Integrating reaction flux data for " + nsds.getNucSimName() + "...");
					
					nsds.setIntFluxArray(getIntFluxArray(nsds));
					
					dialog.appendText("DONE!\n");
					
				}else{
			
					allGoodFluxProfiles = false;

				}	
				
			}else{
			
				dialog.appendText("Reaction flux data is not available!");
			
			}

        }
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(!this.isCancelled()){

			if(!noFluxesAvailable){
				
				if(allGoodFluxProfiles 
					&& allGoodAbundProfiles 
					&& allGoodTimeMappings 
					&& allGoodIsotopeMappings 
					&& allGoodReactionMappings
					){
					
					parent.openIntFluxFrame();
					
				}
			    
			}else{
		 	
		 		String string = "There is no flux data available for the simulations you have chosen.";
		 		Dialogs.createExceptionDialog(string, frame);
		 	
		 	}
	 	
		}
		
	}

	private double[] getIntFluxArray(NucSimDataStructure appnsds){
	    
		double[] intFluxArray = new double[appnsds.getReactionMapArray().length];
		
		for(int i=1; i<appnsds.getTimeArray().length; i++){
		
			dialog.setCurrentValue(i);
		
			double delTime = appnsds.getTimeArray()[i] - appnsds.getTimeArray()[i-1];
		
			for(int j=0; j<appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray().length; j++){
			
				if(appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray()[j]!=-1){
					
					intFluxArray[appnsds.getFluxTimestepDataStructureArray()[i].getIndexArray()[j]] += delTime*appnsds.getFluxTimestepDataStructureArray()[i].getFluxArray()[j];

				}

			}
		
		}
		
		return intFluxArray;
    
    }

}