package org.nucastrodata.element.thermoman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.element.thermoman.ThermoManFrame;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;

public class ThermoManGetSetInfoWorker extends SwingWorker<Void, Void>{

	private ThermoManDataStructure ds;
	private ThermoManFrame parent;
	private PleaseWaitDialog dialog;

	public ThermoManGetSetInfoWorker(ThermoManDataStructure ds
											, ThermoManFrame parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
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
		parent.gotoInfo2Panel();
		dialog.close();
	}
	
}
