package org.nucastrodata.element.elementviz.worker;

import java.awt.Point;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotFrame;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizScalePlotFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizScalePlotFrame parent;
	private ProgressBarDialog dialog;
	private boolean allGoodIsotopeMappings
						, allGoodScaleAbundProfiles
						, allGoodValues;	

	public ElementVizScalePlotFrameWorker(ElementVizDataStructure ds
											, ElementVizScalePlotFrame parent){
		this.ds  = ds;
		this.parent = parent;
		
		dialog = new ProgressBarDialog(parent, "", this);
	}

	protected Void doInBackground(){
		allGoodIsotopeMappings = true;
    	allGoodScaleAbundProfiles = true;
    	allGoodValues = true;
    	
    	dialog.open();
    	dialog.setCurrentValue(0);
    	
		ds.setIsotopes(getIsotopes());

		Iterator<TreeMap<Double, NucSimDataStructure>> itr = ds.getSensMap().values().iterator();
		dialog.setMaximum(ds.getSensMap().values().size());
		
		int itrCounter = 0;
		
		while(itr.hasNext()){
		
			TreeMap<Double, NucSimDataStructure> map = itr.next();
			Iterator<NucSimDataStructure> itrInner = map.values().iterator(); 
			
			while(itrInner.hasNext()){

				NucSimDataStructure sim = itrInner.next();
				
				dialog.appendText("Downloading abundance data for " + sim.getNucSimName() + "...");
				
				ds.setZones(String.valueOf(sim.getZone()));
				ds.setZone(String.valueOf(sim.getZone()));
    			ds.setPath(sim.getPath());
    			ds.setFinal_step("Y");
    			ds.setCurrentNucSimDataStructure(sim);
    			
    			if(isCancelled()){break;}
    			
				if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ISOTOPE MAPPING", parent)){
					allGoodIsotopeMappings = false;
				}
				
				if(isCancelled()){break;}
				
        		if(!Cina.cinaCGIComm.doCGICall("GET ELEMENT SYNTHESIS ABUNDANCES", parent, dialog)){
        			allGoodScaleAbundProfiles = false;
        		}
        		
        		if(allGoodValues){
        			dialog.appendText("DONE!\n");
        			allGoodValues = sim.getAbundTimestepDataStructureArray()[0].getIndexArray()[0]!=-1;
        		}
        		
			}	
			
			itrCounter++;
			dialog.setCurrentValue(itrCounter);
		
        }
		return null;
	}

	protected void done(){
	
		dialog.close();

		if(!this.isCancelled()){

			if(allGoodScaleAbundProfiles 
						&& allGoodIsotopeMappings 
						&& allGoodValues){
						
						
				parent.gotoControlsPanel();
						
				
			}else{
			
				String string = "The isotopes you have selected are not present in the simulations you have selected.";			        			
				Dialogs.createExceptionDialog(string, parent);
				
			}
		
		}
    		
	}
	
	private String getIsotopes(){
    	Vector viktor = ds.getIsotopeViktorScale();
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