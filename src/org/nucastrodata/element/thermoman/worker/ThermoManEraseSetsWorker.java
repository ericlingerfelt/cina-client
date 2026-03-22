package org.nucastrodata.element.thermoman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.element.thermoman.ThermoManErase2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;

public class ThermoManEraseSetsWorker extends SwingWorker<Void, Void>{

	private ThermoManDataStructure ds;
	private ThermoManErase2Panel parent;
	private PleaseWaitDialog dialog;

	public ThermoManEraseSetsWorker(ThermoManDataStructure ds
											, ThermoManErase2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.ERASE_THERMO_PROFILE_SETS
												, Cina.elementManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterEraseSets();
		dialog.close();
	}
	
}
