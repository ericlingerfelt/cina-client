package org.nucastrodata.element.elementworkman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.element.elementworkman.ElementWorkManErase2Panel;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementWorkManEraseWorkflowsWorker extends SwingWorker<Void, Void>{

	private ElementWorkManDataStructure ds;
	private ElementWorkManErase2Panel parent;
	private PleaseWaitDialog dialog;

	public ElementWorkManEraseWorkflowsWorker(ElementWorkManDataStructure ds
											, ElementWorkManErase2Panel parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.ERASE_SIM_WORKFLOWS
												, Cina.elementWorkManFrame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		parent.updateAfterEraseSimWorkflows();
		dialog.close();
	}
	
}
