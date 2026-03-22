package org.nucastrodata.element.elementsynth.worker;

import java.util.Iterator;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthInitializeListener;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.io.CGICom;

public class ElementSynthInitializeWorker extends SwingWorker<Void, Void>{

	private ElementSynthInitializeListener listener;
	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;

	public ElementSynthInitializeWorker(ElementSynthDataStructure ds
											, ElementSynthFrame parent
											, ElementSynthInitializeListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	}

	protected Void doInBackground(){
		try{
		
			ds.setFolderType(FolderType.ALL);
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_THERMO_PROFILE_SETS, parent);
		
			String paths = "";
			Iterator<ThermoProfileSetDataStructure> itr = ds.getSetMap().values().iterator();
			while(itr.hasNext()){
				ThermoProfileSetDataStructure tpsds = itr.next();
				paths += tpsds.getPath() + ",";
			}
			paths = paths.substring(0, paths.length()-1);
		
			ds.setPaths(paths);
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_THERMO_PROFILE_SET_INFO, parent);							
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_SIM_WORKFLOW_TYPES, parent);		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_LIB_DIRS, parent);		
			
			ds.setLibGroup("PUBLIC");
			Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", parent);
			ds.setLibGroup("SHARED");
			Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", parent);
			ds.setLibGroup("USER");
			Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", parent);		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		listener.updateAfterElementSynthInitialize();
	}
	
}
