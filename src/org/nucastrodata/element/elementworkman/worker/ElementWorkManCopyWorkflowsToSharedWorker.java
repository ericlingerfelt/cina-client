package org.nucastrodata.element.elementworkman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.element.elementworkman.ElementWorkManCopy2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementWorkManCopyWorkflowsToSharedWorker extends SwingWorker<Void, Void>{

	private ElementWorkManDataStructure ds;
	private ElementWorkManCopy2Panel parent;
	private PleaseWaitDialog dialog;

	public ElementWorkManCopyWorkflowsToSharedWorker(ElementWorkManDataStructure ds
											, ElementWorkManCopy2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.COPY_SIM_WORKFLOWS_TO_SHARED
												, Cina.elementWorkManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterCopySimWorkflowsToShared();
		dialog.close();
	}
	
}
