package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthCreateAndExecuteSimWorkflowListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthCreateAndExecuteSimWorkflowRunWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthCreateAndExecuteSimWorkflowListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthCreateAndExecuteSimWorkflowRunWorker(ElementSynthDataStructure ds
															, ElementSynthFrame parent
															, ElementSynthCreateAndExecuteSimWorkflowListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	
		dialog = new PleaseWaitDialog(parent, "Please wait while this element synthesis simulation is started.");
	
	}

	protected Void doInBackground(){
		dialog.open();
		
		/**if(ds.getCurrentSimWorkDataStructure().getIndex() != -1){
			
			ds.setSimWorkflowIndices(String.valueOf(ds.getCurrentSimWorkDataStructure().getIndex()));
		
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
													, ds
													, CGICom.ERASE_SIM_WORKFLOWS
													, parent);
																						
		}**/
		
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds.getCurrentSimWorkDataStructure()
												, CGICom.CREATE_SIM_WORKFLOW
												, parent);
		
		ds.getCurrentSimWorkRunDataStructure().setSimWorkflowIndex(ds.getCurrentSimWorkDataStructure().getIndex());
	
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds.getCurrentSimWorkRunDataStructure()
												, CGICom.CREATE_SIM_WORKFLOW_RUN
												, parent);
		
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds.getCurrentSimWorkRunDataStructure()
												, CGICom.EXECUTE_SIM_WORKFLOW_RUN
												, parent);
												
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthCreateAndExecuteSimWorkflow();
	}
	
}
