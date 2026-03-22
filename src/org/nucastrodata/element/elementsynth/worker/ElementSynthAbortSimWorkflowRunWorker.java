package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthAbortSimWorkflowListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthAbortSimWorkflowRunWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthAbortSimWorkflowListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthAbortSimWorkflowRunWorker(ElementSynthDataStructure ds
												, ElementSynthFrame parent
												, ElementSynthAbortSimWorkflowListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	
		dialog = new PleaseWaitDialog(parent, "Please wait while this element synthesis simulation is aborted.");
	
	}

	protected Void doInBackground(){
		dialog.open();
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.ABORT_SIM_WORKFLOW_RUN
												, parent);
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthAbortSimWorkflow();
	}
	
}

