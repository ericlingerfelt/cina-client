package org.nucastrodata.element.thermoman;

import java.awt.event.*;
import java.awt.*;
import org.nucastrodata.Cina;
import org.nucastrodata.CinaCGIComm;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.*;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.GeneralDialog;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.thermoman.worker.ThermoManGetSetInfoWorker;
import org.nucastrodata.element.thermoman.worker.ThermoManImportSetWorker;
import org.nucastrodata.enums.FolderType;

public class ThermoManFrame extends WizardFrame implements ActionListener{

	private ThermoManDataStructure ds;
	private ThermoManIntroPanel introPanel;
	private ThermoManInfo1Panel info1Panel;
	private ThermoManInfo2Panel info2Panel;
	private ThermoManErase1Panel erase1Panel;
	private ThermoManErase2Panel erase2Panel;
	private ThermoManCopy1Panel copy1Panel;
	private ThermoManCopy2Panel copy2Panel;
	private ThermoManImport1Panel import1Panel;
	private ThermoManImport2Panel import2Panel;
	
	public static final Dimension DIMENSION  = new Dimension(675, 500); 
	private Tool tool;
	private enum Tool{INFO, ERASE, COPY, IMPORT};

	public ThermoManFrame(MainDataStructure mds
							, CGICom cgiCom
							, CinaCGIComm appCGICom
							, Cina frame
							, ThermoManDataStructure ds){
		
		super(mds
				, cgiCom
				, frame
				, "Thermodynamic Profile Manager"
				, ""
				, DIMENSION
				, 10);
		this.ds = ds;
		setNavActionListeners(this);
		introPanel = new ThermoManIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
	}

	public ThermoManDataStructure getDataStructure(){return ds;}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
			
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					tool = Tool.INFO;
				}else if(introPanel.importRadioButton.isSelected()){
					tool = Tool.IMPORT;
				}else if(introPanel.copyRadioButton.isSelected()){
					tool = Tool.COPY;
				}else if(introPanel.eraseRadioButton.isSelected()){
					tool = Tool.ERASE;
				}
			}

			switch(tool){
			
				case INFO:
					
					switch(panelIndex){
						case 0:
							ds.setSetMapSelected(null);
							ds.setFolderType(FolderType.ALL);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_THERMO_PROFILE_SETS, this)){
								addFullButtons();
								info1Panel = new ThermoManInfo1Panel(ds);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "Thermodynamic Profile Information", FULL);
							}
							break;
						case 1:
							if(!info1Panel.isListEmpty()){
								info1Panel.getCurrentState();
								PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while thermodynamic profile information is loaded.");
								dialog.open();
								ThermoManGetSetInfoWorker worker = new ThermoManGetSetInfoWorker(ds, this, dialog);
								worker.execute();
							}else{
								String string = "Please select at least one set of thermodynamic profiles from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					}
					break;
					
				case COPY:
					
					switch(panelIndex){
					
						case 0:
							ds.setSetMapSelected(null);
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_THERMO_PROFILE_SETS, this)){
								if(!ds.getSetMap().keySet().isEmpty()){
									addFullButtons();
									copy1Panel = new ThermoManCopy1Panel(ds);
									copy1Panel.setCurrentState();
									setContentPanel(introPanel, copy1Panel, 1, 2, "Copy Thermodynamic Profiles to Shared Folder", FULL);
								}else{
									String string = "You do not currently have any thermodynamic profiles to copy.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}
							
							break;
							
						case 1:
							if(!copy1Panel.isListEmpty()){
								if(!copy1Panel.sharedSetExists()){
									copy1Panel.getCurrentState();
									addEndButtons();
									copy2Panel = new ThermoManCopy2Panel(mds, ds, cgiCom, this);
									copy2Panel.setCurrentState();
									setContentPanel(copy1Panel, copy2Panel, 2, 2, "Copy Thermodynamic Profiles to Shared Folder", FULL);
								}else{
									String string = "There are currently one or more sets of thermodynamic profiles you have selected with the same name as ones in the Shared storage folder. "
															+ "Please remove these sets of thermodynamic profiles from your selection.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}else{
								String string = "Please select at least one set of thermodynamic profiles from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					

						}
					break;
					
				case ERASE:
					
					switch(panelIndex){
						case 0:
							ds.setSetMapSelected(null);
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_THERMO_PROFILE_SETS, this)){
								if(!ds.getSetMap().keySet().isEmpty()){
									addFullButtons();
									erase1Panel = new ThermoManErase1Panel(ds);
									erase1Panel.setCurrentState();
									setContentPanel(introPanel, erase1Panel, 1, 2, "Erase Thermodynamic Profiles", FULL);
								}else{
									String string = "You do not currently have any thermodynamic profiles to erase.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}
							
							break;
						case 1:
							if(!erase1Panel.isListEmpty()){
								erase1Panel.getCurrentState();
								addEndButtons();
								erase2Panel = new ThermoManErase2Panel(mds, ds, cgiCom, this);
								erase2Panel.setCurrentState();
								setContentPanel(erase1Panel, erase2Panel, 2, 2, "Erase Thermodynamic Profiles", FULL);
							}else{
								String string = "Please select at least one thermodynamic profile set from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					}
					break;
					
				case IMPORT:
				
					switch(panelIndex){
						case 0:
							ds.setSetMapSelected(null);
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_THERMO_PROFILE_SETS, this)){
								addFullButtons();
								import1Panel = new ThermoManImport1Panel(this, ds, mds);
								import1Panel.setCurrentState();
								setContentPanel(introPanel, import1Panel, 1, 1, "Import Thermodynamic Profiles", FULL);
							}
							
							break;
						case 1:
							if(import1Panel.goodData()){
								if(import1Panel.checkPath()){
									import1Panel.getCurrentState();
									ThermoManImportSetWorker worker = new ThermoManImportSetWorker(this, ds);
									worker.execute();
								}
							}
							
							break;
							
					}
				
					break;
					
			}
			
		}else if(ae.getSource()==backButton){
			
			switch(tool){
			
				case INFO:
					switch(panelIndex){
						case 1:
							info1Panel.setVisible(false);
							setContentPanel(info1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							addFullButtons();
							info1Panel = new ThermoManInfo1Panel(ds);
							info1Panel.setCurrentState();
							setContentPanel(info2Panel, info1Panel, 1, 2, "Thermodynamic Profile Set Information", FULL);
							break;
					}
					break;
				case COPY:
					switch(panelIndex){
						case 1:
							copy1Panel.setVisible(false);
							setContentPanel(copy1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							addFullButtons();
							copy1Panel = new ThermoManCopy1Panel(ds);
							copy1Panel.setCurrentState();
							setContentPanel(copy2Panel, copy1Panel, 1, 2, "Copy Thermodynamic Profiles to Shared Folder", FULL);
							break;
						
						}
						break;
				case ERASE:
					switch(panelIndex){
						case 1:
							erase1Panel.setVisible(false);
							setContentPanel(erase1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_THERMO_PROFILE_SETS, this)){
								addFullButtons();
								erase1Panel = new ThermoManErase1Panel(ds);
								erase1Panel.setCurrentState();
								setContentPanel(erase2Panel, erase1Panel, 1, 2, "Erase Thermodynamic Profiles", FULL);
							}
							break;
					
					}
					break;
					
				case IMPORT:
					
					switch(panelIndex){
						case 1:
							import1Panel.setVisible(false);
							setContentPanel(import1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2: 
							addFullButtons();
							import1Panel = new ThermoManImport1Panel(this, ds, mds);
							import1Panel.setCurrentState();
							setContentPanel(import2Panel, import1Panel, 1, 2, "Import Thermodynamic Profiles", FULL);
					}
					break;
				
			}
			
		}
		
	}
	
	public void gotoInfo2Panel(){
		addEndButtons();
		info2Panel = new ThermoManInfo2Panel(mds, ds, this);
		info2Panel.setCurrentState();
		setContentPanel(info1Panel, info2Panel, 2, 2, "Thermodynamic Profile Set Information", FULL);
	}
	
	public void gotoImport2Panel(){
		addEndButtons();
		import2Panel = new ThermoManImport2Panel(mds, ds, this);
		import2Panel.setCurrentState();
		setContentPanel(import1Panel, import2Panel, 2, 2, "Import Thermodynamic Profiles", FULL);
	}
	
	public void closeAllFrames(){
		
	}
	
}