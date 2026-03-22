package org.nucastrodata.element.elementviz.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.datastructure.util.NucSimSetDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizFinalAbundFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	
	boolean allGoodIsotopeMappings
			, allGoodFinalAbundProfiles;	

	public ElementVizFinalAbundFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame
											, ElementVizToolsPanel parent){
											
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		allGoodIsotopeMappings = true;
		allGoodFinalAbundProfiles = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
		
    	ds.setIsotopes("");
		
		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;
		
		for(int i=0; i<ds.getNumberNucSimSetDataStructures(); i++){
		
			NucSimSetDataStructure nssds = ds.getFinalNucSimSetDataStructureArray()[i];
			NucSimDataStructure nsds = new NucSimDataStructure();
			nsds.setNucSimName(nssds.getPath().substring(nssds.getPath().lastIndexOf("/")+1));
			nsds.setPath(nssds.getPath());
		
			nssds.setFinalAbundArray(null);
			nssds.setZoneAbundArray(null);

			ds.setPath(nssds.getPath());
			ds.setCurrentNucSimSetDataStructure(nssds);
			ds.setCurrentNucSimDataStructure(nsds);
			
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
			
			nssds.setZAMapArray(ds.getCurrentNucSimDataStructure().getZAMapArray());
			nssds.setIsotopeLabelMapArray(ds.getCurrentNucSimDataStructure().getIsotopeLabelMapArray());

			if(isCancelled()){break;}

			dialog.appendText("Downloading final weighted abundance data for " + nsds.getNucSimName() + "...");
			
			if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS WEIGHTED ABUNDANCES", frame)){
				dialog.appendText("DONE!\n");
    			dialog.setCurrentValue(i+1);
    			allGoodFinalAbundProfiles = true;
    		}else{
    			allGoodFinalAbundProfiles = false;
    		}
    		
			if(isCancelled()){break;}
    		
    		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, nssds, CGICom.GET_TOTAL_WEIGHTS, frame);
			
			for(int j=0; j<nssds.getFinalAbundArray().length; j++){
				nssds.getFinalAbundArray()[j] = nssds.getFinalAbundArray()[j] / nssds.getTotalWeights();
			}
			
        }

		return null;
	}
	
	protected void done(){
	
		dialog.close();
		
		if(allGoodFinalAbundProfiles 
			&& allGoodIsotopeMappings
			&& !this.isCancelled()){
		
			parent.openFinalAbundFrame();
    		
		}
		
	}
	
}