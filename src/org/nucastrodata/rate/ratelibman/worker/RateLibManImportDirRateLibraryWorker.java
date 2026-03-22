package org.nucastrodata.rate.ratelibman.worker;

import java.awt.Frame;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.RateLibManDataStructure;
import org.nucastrodata.datastructure.util.LibraryDataStructure;
import org.nucastrodata.dialogs.ImportDirRateLibraryDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.rate.ratelibman.listener.RateLibManImportDirRateLibraryListener;

public class RateLibManImportDirRateLibraryWorker extends SwingWorker<Void, Void> {

	private RateLibManImportDirRateLibraryListener listener;
	private Frame owner;
	private File dir;
	private RateLibManDataStructure rlmds;
	private ImportDirRateLibraryDialog dialog;
	private String notes; 
	private String libDirName;
	private ArrayList<File> fileList;
	
	/**
	 * Instantiates a new gets the dir listing worker.
	 *
	 * @param parent the parent
	 * @param frame the frame
	 * @param tree the tree
	 * @param node the node
	 */
	public RateLibManImportDirRateLibraryWorker(RateLibManImportDirRateLibraryListener listener, 
														RateLibManDataStructure rlmds, 
														File dir, 
														Frame owner, 
														String notes, 
														String libDirName){
		this.listener = listener;
		this.dir = dir;
		this.owner = owner;
		this.rlmds = rlmds;
		this.notes = notes;
		this.libDirName = libDirName;
		
		fileList = new ArrayList<File>();
		File[] files = dir.listFiles();
		Arrays.sort(files);
		for(int i=0; i<files.length; i++){
			File file = files[i];
			if(!file.isHidden() && file.isFile()){
				fileList.add(file);
			}
		}
		int numLibs = fileList.size();
		dialog = new ImportDirRateLibraryDialog(owner, libDirName, numLibs, fileList.get(0).getName().replaceFirst("[.][^.]+$", ""));
	}

	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#doInBackground()
	 */
	protected Void doInBackground(){
		dialog.open();
		CGICom cgiCom = new CGICom();
		Iterator<File> itr = fileList.iterator();
		int numLibs = fileList.size();
		int fileCounter = 0;
		while(itr.hasNext()){
			File file = itr.next();
			dialog.update(fileCounter, file.getName().replaceFirst("[.][^.]+$", ""));
			LibraryDataStructure lds = new LibraryDataStructure();
			lds.setLibName(libDirName + "_dir_" + file.getName().replaceFirst("[.][^.]+$", ""));
			lds.setLibNotes(notes);
			lds.setLibDir(libDirName);
			cgiCom.doCGICall(Cina.cinaMainDataStructure, lds, CGICom.IMPORT_RATE_LIBRARY, owner, file);
			fileCounter++;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.bellerophon.gui.util.swingworker.SwingWorker#done()
	 */
	protected void done(){
		dialog.close();
		listener.updateAfterImportDirRateLibrary();
	}
}
