package org.nucastrodata.rate.ratelibman.worker;

import java.awt.Frame;
import javax.swing.SwingWorker;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.rate.ratelibman.listener.RateLibManGetLibDirsListener;

public class RateLibManGetLibDirsWorker extends SwingWorker<Void, Void>{

	private RateLibManGetLibDirsListener listener;
	private RateLibManDataStructure ds;
	private Frame frame;
	private PleaseWaitDialog dialog;

	public RateLibManGetLibDirsWorker(RateLibManDataStructure ds
											, Frame frame
											, PleaseWaitDialog dialog
											, RateLibManGetLibDirsListener listener){
		this.ds  = ds;
		this.frame = frame;
		this.dialog = dialog;
		this.listener = listener;
	}

	protected Void doInBackground(){
		try{
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
												, ds
												, CGICom.GET_LIB_DIRS
												, frame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void done(){
		dialog.close();
		listener.updateAfterGetLibDirs();
	}
	
}

