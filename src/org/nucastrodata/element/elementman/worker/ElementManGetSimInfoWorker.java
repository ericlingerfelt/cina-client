package org.nucastrodata.element.elementman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.element.elementman.ElementManFrame;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementManGetSimInfoWorker extends SwingWorker<Void, Void>{

	private ElementManDataStructure ds;
	private ElementManFrame parent;
	private PleaseWaitDialog dialog;

	public ElementManGetSimInfoWorker(ElementManDataStructure ds
											, ElementManFrame parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_SIM_INFO
												, Cina.elementManFrame);
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
