package org.nucastrodata.element.elementman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import org.nucastrodata.element.elementman.ElementManCopy2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementManCopySimsToSharedWorker extends SwingWorker<Void, Void>{

	private ElementManDataStructure ds;
	private ElementManCopy2Panel parent;
	private PleaseWaitDialog dialog;

	public ElementManCopySimsToSharedWorker(ElementManDataStructure ds
											, ElementManCopy2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.COPY_SIMS_TO_SHARED
												, Cina.elementManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterCopySimsToShared();
		dialog.close();
	}
	
}
