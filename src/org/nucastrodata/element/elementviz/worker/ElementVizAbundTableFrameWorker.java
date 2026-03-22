package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.Vector;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.abundtable.ElementVizAbundTableFrame;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizAbundTableFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizAbundTableFrame parent;
	private ProgressBarDialog dialog;
	
	boolean allGoodIsotopeMappings
				, allGoodAbundProfiles;

	public ElementVizAbundTableFrameWorker(ElementVizDataStructure ds
											, ElementVizAbundTableFrame parent){
		this.ds  = ds;
		this.parent = parent;
		
		dialog = new ProgressBarDialog(parent, "", this);
	}

	protected Void doInBackground(){
	
		allGoodIsotopeMappings = true;
    	allGoodAbundProfiles = true;
    	
    	dialog.open();

    	dialog.setCurrentValue(0);
    	dialog.setMaximum(ds.getNumberNucSimDataStructures());
		
    	ds.setIsotopes(getIsotopes());
		ds.setFinal_step("Y");
		
		String lastSubPath = "";
		NucSimDataStructure lastNSDS = null;
		
    	for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){

    		NucSimDataStructure nsds = ds.getNucSimDataStructureArray()[i];
			ds.setPath(nsds.getPath());
			ds.setZone(String.valueOf(nsds.getZone()));
			ds.setZones(String.valueOf(nsds.getZone()));
			ds.setCurrentNucSimDataStructure(nsds);

			if(isCancelled()){break;}

			String currentPath = nsds.getPath();
			if(currentPath.contains("_dir_")){
				String currentSubPath = currentPath.substring(0, currentPath.lastIndexOf("_dir_"));
				if(!currentSubPath.equals(lastSubPath)){
					if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", parent)){allGoodIsotopeMappings = false;}
					lastSubPath = currentSubPath;
					lastNSDS = nsds;
				}else{
					nsds.setZAMapArray(lastNSDS.getZAMapArray().clone());
					nsds.setIsotopeLabelMapArray(lastNSDS.getIsotopeLabelMapArray().clone());
				}
			}else{
				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", parent)){allGoodIsotopeMappings = false;}
			}

			if(isCancelled()){break;}

			dialog.appendText("Downloading final abundance data for " + nsds.getNucSimName() + "...");

    		if(Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", parent, dialog)){
    			dialog.appendText("DONE!\n");
    			dialog.setCurrentValue(i+1);
    			allGoodAbundProfiles = true;
    		}else{
    			allGoodAbundProfiles = false;
    		}

		}
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(!this.isCancelled()){

			if(allGoodAbundProfiles 
					&& allGoodIsotopeMappings){
	
	        	parent.gotoTablePanel();
		
			}else{
		
				String string = "The isotopes you have selected are not present in the simulations you have selected.";
				Dialogs.createExceptionDialog(string, parent);
			
			}
		
		}
		
	}
	
	private String getIsotopes(){
	
    	Vector viktor = ds.getIsotopeViktorAbundTable();
    	String string = "";
    	
    	for(int i=0; i<viktor.size(); i++){
    	
    		Point point = (Point)viktor.elementAt(i);
    		String Z = String.valueOf((int)point.getX());
    		String A = String.valueOf((int)point.getX() + (int)point.getY());
    	
    		if(i!=(viktor.size()-1)){
    			string = string + Z + "," + A + "\u0009";
    		}else{
    			string = string + Z + "," + A;
    		}
    	}
    	
    	return string;
    
    }
	
}
