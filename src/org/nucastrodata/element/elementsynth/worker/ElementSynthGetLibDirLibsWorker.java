package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetLibDirLibsListener;
import org.nucastrodata.io.CGICom;

public class ElementSynthGetLibDirLibsWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthGetLibDirLibsListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthGetLibDirLibsWorker(ElementSynthDataStructure ds
											, ElementSynthFrame parent
											, ElementSynthGetLibDirLibsListener listener
											, PleaseWaitDialog dialog){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
		this.dialog = dialog;
	}

	protected Void doInBackground(){
		try{
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_LIB_DIR_LIBS
												, parent);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthGetLibDirLibs();
	}
	
}