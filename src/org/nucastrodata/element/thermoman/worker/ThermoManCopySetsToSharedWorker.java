package org.nucastrodata.element.thermoman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.element.thermoman.ThermoManCopy2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;

public class ThermoManCopySetsToSharedWorker extends SwingWorker<Void, Void>{

	private ThermoManDataStructure ds;
	private ThermoManCopy2Panel parent;
	private PleaseWaitDialog dialog;

	public ThermoManCopySetsToSharedWorker(ThermoManDataStructure ds
											, ThermoManCopy2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.COPY_THERMO_PROFILE_SETS_TO_SHARED
												, Cina.elementManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterCopySetsToShared();
		dialog.close();
	}
	
}
