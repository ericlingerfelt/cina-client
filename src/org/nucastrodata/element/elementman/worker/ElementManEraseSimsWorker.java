package org.nucastrodata.element.elementman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.element.elementman.ElementManErase2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementManEraseSimsWorker extends SwingWorker<Void, Void>{

	private ElementManDataStructure ds;
	private ElementManErase2Panel parent;
	private PleaseWaitDialog dialog;

	public ElementManEraseSimsWorker(ElementManDataStructure ds
											, ElementManErase2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.ERASE_SIMS
												, Cina.elementManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterEraseSims();
		dialog.close();
	}
	
}
