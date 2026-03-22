package org.nucastrodata.element.elementworkman.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.element.elementworkman.ElementWorkManFrame;
import org.nucastrodata.io.CGICom;

import org.nucastrodata.dialogs.PleaseWaitDialog;


public class ElementWorkManGetWorkflowInfoWorker extends SwingWorker<Void, Void>{

	private ElementWorkManDataStructure ds;
	private ElementWorkManFrame parent;
	private PleaseWaitDialog dialog;

	public ElementWorkManGetWorkflowInfoWorker(ElementWorkManDataStructure ds
											, ElementWorkManFrame parent
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_SIM_WORKFLOW_INFO
												, Cina.elementWorkManFrame);
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
