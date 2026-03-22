package org.nucastrodata.rate.ratelibman.worker;

import java.awt.Frame;
import java.io.File;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.dialogs.DelayDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.rate.ratelibman.listener.RateLibManImportSingleRateLibraryListener;

public class RateLibManImportSingleRateLibraryWorker extends SwingWorker<Void, Void> {

	private RateLibManImportSingleRateLibraryListener listener;
	private Frame owner;
	private File file;
	private LibraryDataStructure lds;
	private DelayDialog dialog;
	
	/**
	 * Instantiates a new gets the dir listing worker.
	 *
	 * @param parent the parent
	 * @param frame the frame
	 * @param tree the tree
	 * @param node the node
	 */
	public RateLibManImportSingleRateLibraryWorker(RateLibManImportSingleRateLibraryListener listener, 
														LibraryDataStructure lds, 
														File file, 
														Frame owner){
		this.listener = listener;
		this.file = file;
		this.owner = owner;
		this.lds = lds;
		dialog = new DelayDialog(owner, "Please wait while this Rate Library is imported.", "Please wait...");
	}

	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#doInBackground()
	 */
	protected Void doInBackground(){
		dialog.openDelayDialog();
		CGICom cgiCom = new CGICom();
		cgiCom.doCGICall(Cina.cinaMainDataStructure, lds, CGICom.IMPORT_RATE_LIBRARY, owner, file);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#done()
	 */
	protected void done(){
		dialog.closeDelayDialog();
		listener.updateAfterImportSingleRateLibrary(lds);
	}
}
