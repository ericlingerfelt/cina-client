package org.nucastrodata.element.elementviz.worker;

import java.util.TreeMap;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.Dialogs;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.datastructure.util.NucSimDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.element.elementviz.ElementVizToolsPanel;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.ProgressBarDialog;

public class ElementVizScalePlotFrameInitWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private ElementVizToolsPanel parent;
	private ProgressBarDialog dialog;
	private ElementVizFrame frame;
	private CGICom cgiCom;
	private boolean sensSimsFound;

	public ElementVizScalePlotFrameInitWorker(ElementVizDataStructure ds
												, ElementVizFrame frame
												, ElementVizToolsPanel parent
												, CGICom cgiCom){
												
		this.ds  = ds;
		this.parent = parent;
		this.frame = frame;
		this.cgiCom = cgiCom;
		
		dialog = new ProgressBarDialog(frame, "", this);
	}

	protected Void doInBackground(){
	
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
		
			if(isCancelled()){break;}
		
			ds.setPath(ds.getNucSimDataStructureArrayScale()[i].getPath());
			ds.setCurrentNucSimDataStructure(ds.getNucSimDataStructureArrayScale()[i]);
			cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.IS_SIM_SENS, frame);
        }
		
		TreeMap<String, TreeMap<Double, NucSimDataStructure>> map 
			= new TreeMap<String, TreeMap<Double, NucSimDataStructure>>();
		sensSimsFound = false;
		for(int i=0; i<ds.getNumberNucSimDataStructures(); i++){
			NucSimDataStructure sim = ds.getNucSimDataStructureArrayScale()[i];
			if(sim.getIsSimSens()){
				sensSimsFound = true;
				String[] array = sim.getNucSimName().split("\\(");
				String setName = array[0].trim().substring(0, array[0].trim().lastIndexOf("_"));
				if(!map.containsKey(setName)){
					map.put(setName, new TreeMap<Double, NucSimDataStructure>());
				}
				map.get(setName).put(sim.getScaleFactor(), sim);
			}
		}
		ds.setSensMap(map);
		return null;
	}

	protected void done(){
	
		dialog.close();
		
		if(!this.isCancelled()){
		
			if(sensSimsFound){
			
				parent.openScalePlotFrame();
				
			}else{
			 	
		 		String string = "You have not selected any simulations belonging to a sensitivity study.";
		 		Dialogs.createExceptionDialog(string, frame);
		 	
		 	}
	 	
		}

	}
	
}