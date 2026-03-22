package org.nucastrodata.element.elementviz.worker;

import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.ElementVizFrame;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.PleaseWaitDialog;

public class ElementVizFrameWorker extends SwingWorker<Void, Void>{

	private ElementVizDataStructure ds;
	private PleaseWaitDialog dialog;
	private ElementVizFrame frame;
	private boolean goodSharedNucSimSets
					, goodUserNucSimSets;	

	public ElementVizFrameWorker(ElementVizDataStructure ds
											, ElementVizFrame frame){
											
		this.ds  = ds;
		this.frame = frame;
		
		dialog = new PleaseWaitDialog(frame, "Please wait while Element Synthesis Simulations are loaded.");
	}

	protected Void doInBackground(){
    		
    	dialog.open();
		
		ds.setFolderType(FolderType.SHARED);
		goodSharedNucSimSets = Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_SIMS, frame);
		
		ds.setFolderType(FolderType.USER);
		goodUserNucSimSets = Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_SIMS, frame);

		return null;
	}
	
	protected void done(){
	
		dialog.close();

		if(goodSharedNucSimSets
				&& goodUserNucSimSets){

			frame.gotoSelectSimsPanel();	
		
		}
		
	}
	
}