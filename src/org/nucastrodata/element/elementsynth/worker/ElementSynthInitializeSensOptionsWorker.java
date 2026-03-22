package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthInitializeSensOptionsListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthInitializeSensOptionsWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthInitializeSensOptionsListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthInitializeSensOptionsWorker(ElementSynthDataStructure ds
													, ElementSynthFrame parent
													, ElementSynthInitializeSensOptionsListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
		
		dialog = new PleaseWaitDialog(parent, "Please wait while sensitivity study options are initialized.");
	}

	protected Void doInBackground(){
		dialog.open();
		String tmpLib = ds.getCurrentSimWorkDataStructure().getLibrary();
		Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds, CGICom.GET_SENS_NETWORK_ISOTOPES
												, parent);
		ds.getCurrentSimWorkDataStructure().setLibrary("ReaclibV2.2");
		Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY ISOTOPES", parent);
		ds.getCurrentSimWorkDataStructure().setLibrary(tmpLib);
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthInitializeSensOptions();
	}
	
}
