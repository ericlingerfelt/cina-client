package org.nucastrodata.element.elementworkman;

import java.awt.event.*;
import java.awt.*;
import org.nucastrodata.Cina;
import org.nucastrodata.CinaCGIComm;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.GeneralDialog;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementworkman.worker.ElementWorkManGetWorkflowInfoWorker;
import org.nucastrodata.enums.FolderType;

public class ElementWorkManFrame extends WizardFrame implements ActionListener{

	private ElementWorkManDataStructure ds;
	private ElementWorkManIntroPanel introPanel;
	private ElementWorkManInfo1Panel info1Panel;
	private ElementWorkManInfo2Panel info2Panel;
	private ElementWorkManErase1Panel erase1Panel;
	private ElementWorkManErase2Panel erase2Panel;
	private ElementWorkManCopy1Panel copy1Panel;
	private ElementWorkManCopy2Panel copy2Panel;
	
	public static final Dimension DIMENSION  = new Dimension(675, 500); 
	private Tool tool;
	private enum Tool{INFO, ERASE, COPY};

	public ElementWorkManFrame(MainDataStructure mds
							, CGICom cgiCom
							, CinaCGIComm appCGICom
							, Cina frame
							, ElementWorkManDataStructure ds){
		
		super(mds
				, cgiCom
				, frame
				, "Element Synthesis Workflow Manager"
				, ""
				, DIMENSION
				, 10);
		this.ds = ds;
		setNavActionListeners(this);
		introPanel = new ElementWorkManIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
	}

	public ElementWorkManDataStructure getDataStructure(){return ds;}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
			
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					tool = Tool.INFO;
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
							ds.setSimWorkMapSelected(null);
							ds.setFolderType(FolderType.ALL);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_SIM_WORKFLOWS, this)){
								if(!ds.getSimWorkMap().keySet().isEmpty()){
									addFullButtons();
									info1Panel = new ElementWorkManInfo1Panel(ds);
									info1Panel.setCurrentState();
									setContentPanel(introPanel, info1Panel, 1, 2, "Element Synthesis Workflow Information", FULL);
								}else{
									String string = "You do not currently have any element synthesis workflows.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}
							break;
						case 1:
							if(!info1Panel.isListEmpty()){
								info1Panel.getCurrentState();
								PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while element synthesis workflow information is loaded.");
								dialog.open();
								ElementWorkManGetWorkflowInfoWorker worker = new ElementWorkManGetWorkflowInfoWorker(ds, this, dialog);
								worker.execute();
							}else{
								String string = "Please select at least one element synthesis workflow from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					}
					break;
					
				case COPY:
					
					switch(panelIndex){
					
						case 0:
							ds.setSimWorkMapSelected(null);
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_SIM_WORKFLOWS, this)){
								if(!ds.getSimWorkMap().keySet().isEmpty()){
									addFullButtons();
									copy1Panel = new ElementWorkManCopy1Panel(ds);
									copy1Panel.setCurrentState();
									setContentPanel(introPanel, copy1Panel, 1, 2, "Copy Element Synthesis Workflows to Shared Folder", FULL);
								}else{
									String string = "You do not currently have any element synthesis workflows to copy.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}
							
							break;
							
						case 1:
							if(!copy1Panel.isListEmpty()){
								if(!copy1Panel.sharedSimExists()){
									copy1Panel.getCurrentState();
									addEndButtons();
									copy2Panel = new ElementWorkManCopy2Panel(mds, ds, cgiCom, this);
									copy2Panel.setCurrentState();
									setContentPanel(copy1Panel, copy2Panel, 2, 2, "Copy Element Synthesis Workflows to Shared Folder", FULL);
								}else{
									String string = "There are currently one or more selected element synthesis workflows with the same name as ones in the Shared storage folder. "
															+ "Please remove these workflows from your selection.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}else{
								String string = "Please select at least one element synthesis workflow from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					

						}
					break;
					
				case ERASE:
					
					switch(panelIndex){
						case 0:
							ds.setSimWorkMapSelected(null);
							ds.setFolderType(FolderType.USER);
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_SIM_WORKFLOWS, this)){
								if(!ds.getSimWorkMap().keySet().isEmpty()){
									addFullButtons();
									erase1Panel = new ElementWorkManErase1Panel(ds);
									erase1Panel.setCurrentState();
									setContentPanel(introPanel, erase1Panel, 1, 2, "Erase Element Synthesis Workflows", FULL);
								}else{
									String string = "You do not currently have any element synthesis workflows to erase.";
									GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
									dialog.setVisible(true);
								}
							}
							
							break;
						case 1:
							if(!erase1Panel.isListEmpty()){
								erase1Panel.getCurrentState();
								if(!erase1Panel.containsInUseWorkflows()){
									addEndButtons();
									erase2Panel = new ElementWorkManErase2Panel(mds, ds, cgiCom, this);
									erase2Panel.setCurrentState();
									setContentPanel(erase1Panel, erase2Panel, 2, 2, "Erase Element Synthesis Workflows", FULL);
								}
							}else{
								String string = "Please select at least one element synthesis workflow from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
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
							info1Panel = new ElementWorkManInfo1Panel(ds);
							info1Panel.setCurrentState();
							setContentPanel(info2Panel, info1Panel, 1, 2, "Element Synthesis Workflow Information", FULL);
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
							copy1Panel = new ElementWorkManCopy1Panel(ds);
							copy1Panel.setCurrentState();
							setContentPanel(copy2Panel, copy1Panel, 1, 2, "Copy Element Synthesis Workflows to Shared Folder", FULL);
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
							if(cgiCom.doCGICall(mds, ds, CGICom.GET_SIMS, this)){
								addFullButtons();
								erase1Panel = new ElementWorkManErase1Panel(ds);
								erase1Panel.setCurrentState();
								setContentPanel(erase2Panel, erase1Panel, 1, 2, "Erase Element Synthesis Workflows", FULL);
							}
							break;
					
					}
					break;
				
			}
			
		}
		
	}
	
	public void gotoInfo2Panel(){
		addEndButtons();
		info2Panel = new ElementWorkManInfo2Panel(mds, ds, this);
		info2Panel.setCurrentState();
		setContentPanel(info1Panel, info2Panel, 2, 2, "Element Synthesis Workflow Information", FULL);
	}
	
	public void closeAllFrames(){
		
	}
	
}