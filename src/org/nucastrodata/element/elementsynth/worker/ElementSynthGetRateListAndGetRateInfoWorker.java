package org.nucastrodata.element.elementsynth.worker;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetRateListAndGetRateInfoListener;

public class ElementSynthGetRateListAndGetRateInfoWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthGetRateListAndGetRateInfoListener listener;
	private PleaseWaitDialog dialog;

	public ElementSynthGetRateListAndGetRateInfoWorker(ElementSynthDataStructure ds
														, ElementSynthFrame parent
														, ElementSynthGetRateListAndGetRateInfoListener listener){
		this.ds = ds;
		this.parent = parent;
		this.listener = listener;
	
		dialog = new PleaseWaitDialog(parent, "Please wait while selected reaction rate data is loaded.");
	
	}

	protected Void doInBackground(){
		dialog.open();
		Cina.cinaCGIComm.doCGICall("GET RATE LIST", parent);
		String rateID = getRateID();
		if(!rateID.equals("")){
			ds.setRates(rateID);
			ds.getCurrentSimWorkDataStructure().setReactionRateID(rateID);
			ds.setReactionRateID(rateID);
			ds.setProperties("");
			Cina.cinaCGIComm.doCGICall("GET RATE INFO", parent);
		}
		return null;
	}

	private String getRateID(){
		for(String rateID: ds.getRateIDArray()){
			if(rateID.indexOf(ds.getCurrentSimWorkDataStructure().getReactionDataStructure().getReactionName())!=-1){
				return rateID;
			}
		}
		return "";
	}

	protected void done(){
		dialog.close();
		listener.updateAfterElementSynthGetRateListAndGetRateInfo();
	}
	
}

