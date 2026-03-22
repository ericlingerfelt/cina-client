package org.nucastrodata.element.elementsynth.worker;

import java.util.Iterator;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetThermoProfileSetsAndInfoListener;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;

public class ElementSynthGetThermoProfileSetsAndInfoWorker extends SwingWorker<Void, Void>{

	private ElementSynthGetThermoProfileSetsAndInfoListener listener;
	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private PleaseWaitDialog dialog;

	public ElementSynthGetThermoProfileSetsAndInfoWorker(ElementSynthDataStructure ds
											, ElementSynthFrame parent
											, PleaseWaitDialog dialog
											, ElementSynthGetThermoProfileSetsAndInfoListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
		this.listener = listener;
	}

	protected Void doInBackground(){
		try{
		
			ds.setFolderType(FolderType.ALL);
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_THERMO_PROFILE_SETS
												, parent);
		
			String paths = "";
			Iterator<ThermoProfileSetDataStructure> itr = ds.getSetMap().values().iterator();
			while(itr.hasNext()){
				ThermoProfileSetDataStructure tpsds = itr.next();
				paths += tpsds.getPath() + ",";
			}
			paths = paths.substring(0, paths.length()-1);
		
			ds.setPaths(paths);
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_THERMO_PROFILE_SET_INFO
												, parent);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthGetThermoProfileSetsAndInfo();
	}
	
}
