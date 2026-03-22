package org.nucastrodata.rate.ratelibman.worker;

import java.awt.Frame;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.rate.ratelibman.listener.RateLibManEraseLibDirListener;

public class RateLibManEraseLibDirWorker extends SwingWorker<Void, Void> {

	private RateLibManEraseLibDirListener listener;
	private Frame owner;
	private RateLibManDataStructure rlmds;
	private PleaseWaitDialog dialog;
	
	/**
	 * Instantiates a new gets the dir listing worker.
	 *
	 * @param parent the parent
	 * @param frame the frame
	 * @param tree the tree
	 * @param node the node
	 */
	public RateLibManEraseLibDirWorker(RateLibManEraseLibDirListener listener, 
														RateLibManDataStructure rlmds, 
														Frame owner){
		this.listener = listener;
		this.owner = owner;
		this.rlmds = rlmds;
		dialog = new PleaseWaitDialog(owner, "Please wait while the selected Rate Library Directory is erased.");
	}

	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#doInBackground()
	 */
	protected Void doInBackground(){
		dialog.open();
		CGICom cgiCom = new CGICom();
		cgiCom.doCGICall(Cina.cinaMainDataStructure, rlmds, CGICom.ERASE_LIB_DIR, owner);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#done()
	 */
	protected void done(){
		dialog.close();
		listener.updateAfterEraseLibDir();
	}
}

