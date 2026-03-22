package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthSaveSimWorkflowRunListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthSaveSimWorkflowRunWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthSaveSimWorkflowRunListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthSaveSimWorkflowRunWorker(ElementSynthDataStructure ds
												, ElementSynthFrame parent
												, ElementSynthSaveSimWorkflowRunListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	
		dialog = new PleaseWaitDialog(parent, "Please wait while this element synthesis simulation "
													+ "is saved to your User storage space.");
	
	}

	protected Void doInBackground(){
		dialog.open();
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.SAVE_SIM_WORKFLOW_RUN
												, parent);
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthSaveSimWorkflowRun();
	}
	
}

