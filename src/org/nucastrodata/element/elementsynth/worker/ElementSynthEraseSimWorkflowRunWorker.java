package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthEraseSimWorkflowListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthEraseSimWorkflowRunWorker extends SwingWorker<Void, Void>{

	private ElementSimWorkRunDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthEraseSimWorkflowListener listener;

	public ElementSynthEraseSimWorkflowRunWorker(ElementSimWorkRunDataStructure ds
												, ElementSynthFrame parent
												, ElementSynthEraseSimWorkflowListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	
	}

	protected Void doInBackground(){
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.ERASE_SIM_WORKFLOW_RUN
												, parent);
		return null;
	}

	protected void done(){
		listener.updateAfterElementSynthEraseSimWorkflow(ds);
	}
	
}

