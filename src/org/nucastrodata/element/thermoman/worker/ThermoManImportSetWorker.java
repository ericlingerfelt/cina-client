package org.nucastrodata.element.thermoman.worker;

import java.io.File;
import java.util.TreeMap;
import java.util.UUID;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.dialogs.DelayDialog;
import org.nucastrodata.element.thermoman.ThermoManFrame;
import org.nucastrodata.io.CGICom;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ThermoManImportSetWorker extends SwingWorker<Void, Void> {

	private ThermoManDataStructure ds;
	private DelayDialog dialog;
	private ThermoManFrame frame;
	
	public ThermoManImportSetWorker(ThermoManFrame frame, 
										ThermoManDataStructure ds){
		this.frame = frame;
		this.ds = ds;
		dialog = new DelayDialog(frame, "Please wait while this set of thermodynamic profiles is imported.", "Please wait...");
	}

	protected Void doInBackground(){
		dialog.openDelayDialog();
		File tempFile = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
		ThermoProfileSetDataStructure tpsds = ds.getImportedThermoProfileSetDataStructure();
		createZipFile(tempFile, new File(tpsds.getDir()));
		CGICom cgiCom = new CGICom();
		cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.IMPORT_THERMO_PROFILE_SET, frame, tempFile);
		tempFile.delete();
		ds.setPaths(tpsds.getPath());
		TreeMap<String, ThermoProfileSetDataStructure> map = new TreeMap<String, ThermoProfileSetDataStructure>();
		map.put(tpsds.getPath(), tpsds);
		ds.setSetMapSelected(map);
		cgiCom = new CGICom();
		cgiCom.doCGICall(Cina.cinaMainDataStructure, ds, CGICom.GET_THERMO_PROFILE_SET_INFO, frame);
		return null;
	}
	
	public void createZipFile(File inZip, File dir){
		try {
			ZipFile zipFile = new ZipFile(inZip);
			ZipParameters parameters = new ZipParameters();
			parameters.setIncludeRootFolder(false);
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipFile.addFolder(dir, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		frame.gotoImport2Panel();
	}
}

