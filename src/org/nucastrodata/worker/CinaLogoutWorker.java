package org.nucastrodata.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.ElementSynthSimWorkflowRunStatus;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.PleaseWaitDialog;

public class CinaLogoutWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private PleaseWaitDialog dialog;
	private Cina frame;
	private ElementSynthFrame esf;

	public CinaLogoutWorker(ElementSynthDataStructure ds, Cina frame, ElementSynthFrame esf){
		this.ds = ds;					
		this.frame = frame;
		this.esf = esf;
		dialog = new PleaseWaitDialog(frame, "Please wait while your session is logged out.");
	}

	protected Void doInBackground(){
    	dialog.open();
    	ElementSimWorkRunDataStructure eswrds = ds.getCurrentSimWorkRunDataStructure();
    	if (eswrds != null){
	    	if(eswrds.getName().equals("") && eswrds.getCurrentStatus()==ElementSynthSimWorkflowRunStatus.RUNNING){
	    		esf.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
				Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
									, ds
									, CGICom.ABORT_SIM_WORKFLOW_RUN
									, frame);
			}
    	}
		Cina.cinaCGIComm.doCGIComm("LOGOUT", frame);
		return null;
	}
	
	protected void done(){
		dialog.close();
		frame.dispose();
		frame.setVisible(false);
		System.exit(0);	
	}
	
}