package org.nucastrodata.element.elementsynth;

import java.awt.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.*;
import org.nucastrodata.dialogs.*;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import java.awt.event.*;
import org.nucastrodata.*;
import org.nucastrodata.element.elementsynth.listener.*;
import org.nucastrodata.element.elementsynth.worker.*;
import org.nucastrodata.element.elementviz.*;
import org.nucastrodata.enums.SimCat;
import org.nucastrodata.wizard.WizardFrame;

public class ElementSynthFrame extends WizardFrame implements ActionListener, 
																ElementSynthCreateAndExecuteSimWorkflowListener, 
																ElementSynthGetRateListAndGetRateInfoListener, 
																ElementSynthInitializeSensOptionsListener, 
																ElementSynthGetLibDirLibsListener, 
																ElementSynthGetLibDirsListener, 
																ElementSynthGetThermoProfileSetsAndInfoListener,
																ElementSynthInitializeListener{
	
	public ElementSynthWorkflowPanel elementSynthWorkflowPanel;			
	public ElementSynthSelectTypePanel elementSynthSelectTypePanel;
	public ElementSynthSelectNetworkPanel elementSynthSelectNetworkPanel;
	public ElementSynthSelectLibPanel elementSynthSelectLibPanel;
	public ElementSynthSelectZonesPanel elementSynthSelectZonesPanel;
	public ElementSynthSelectThermoPanel elementSynthSelectThermoPanel;
	public ElementSynthOptionsPanel elementSynthOptionsPanel;
	public ElementSynthSensStudyPanel elementSynthSensStudyPanel;
	public ElementSynthStatusPanel elementSynthStatusPanel;
	public ElementSynthResultsPanel elementSynthResultsPanel;
	public ElementSynthSelectLibDirPanel elementSynthSelectLibDirPanel;
	public ElementSynthDataFileFrame elementSynthSunetFileFrame;
	public ElementSynthDataFileFrame elementSynthAbundFileFrame;
	public ElementSynthDataFileFrame elementSynthThermoFileFrame;
	public ElementSynthDataFileFrame elementSynthWeightFileFrame;
	public ElementSynthDataFileFrame elementSynthNetworkSummeryFrame;

	private ElementSynthDataStructure ds;
	private CautionDialog simSavedDialog;

	public ElementSynthFrame(MainDataStructure mds
								, CGICom cgiCom
								, Cina frame
								, ElementSynthDataStructure ds){
		
		super(mds
				, cgiCom
				, frame
				, "Element Synthesis Simulator"
				, "Element Synthesis Visualizer"
				, new Dimension(860, 560)
				, 2);
		
		this.ds = ds;
		setNavActionListeners(this);
		introPanel = new ElementSynthIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
		worker.execute();
	}
	
	public int getPanelIndex(){return panelIndex;}
	public ElementSynthDataStructure getDataStructure(){return ds;}
	public void setContinueEnabled(boolean flag){
		continueButton.setEnabled(flag);
	}
	public void setBackEnabled(boolean flag){
		backButton.setEnabled(flag);
	}

	protected void restart(){
	
		ds.setStatusText("");
		continueButton.setEnabled(true);
		
		ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
		worker.execute();
		
		elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
		elementSynthWorkflowPanel.setCurrentState();
		
		if(mds.isMasterUser()){

			switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
				case SINGLE_STANDARD:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
					break;
				case SINGLE_STANDARD_SENS:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
					break;
				case SINGLE_CUSTOM:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
					break;
				case SINGLE_CUSTOM_SENS:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
					break;
				case DIR_STANDARD:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
					break;
				case DIR_CUSTOM_DOUBLE_LOOPING:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
					break;
				case DIR_CUSTOM_SINGLE_LOOPING:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 7, "Select Simulation Workflow", CENTER);
					break;
				default:
					break;
			}
			
		}else{
			
			switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
				case SINGLE_STANDARD:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
					break;
				case SINGLE_STANDARD_SENS:
					setContentPanel(elementSynthStatusPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
					break;
				default:
					break;
			}
			
		}
		
		
		validate();
	}
	
	////////////CONTINUE///////////////////////////////////////////////////////////////////////////
	private void performSINGLE_STANDARDWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();		
				setContentPanel(elementSynthWorkflowPanel, elementSynthSelectTypePanel, 2, 8, "Select Simulation Type", FULL);
				break;
			case 2:
				elementSynthSelectTypePanel.getCurrentState();
				ds.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
					elementSynthSelectLibPanel.setCurrentState();	
					setContentPanel(elementSynthSelectTypePanel, elementSynthSelectLibPanel, 3, 8, "Select Reaction Rate Library", CENTER);
				}
				break;
			case 3:
				elementSynthSelectLibPanel.getCurrentState();
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();	
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectNetworkPanel, 4, 8, "Select Reaction Rate Network", FULL);
				break;
			case 4:
				elementSynthSelectNetworkPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
				elementSynthOptionsPanel.setCurrentState();		
				setContentPanel(elementSynthSelectZonesPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case 6:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 7:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 8, 8, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	}
	
	private void performSINGLE_STANDARD_SENSWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();		
				setContentPanel(elementSynthWorkflowPanel, elementSynthSelectTypePanel, 2, 9, "Select Simulation Type", FULL);
				break;
			case 2:
				elementSynthSelectTypePanel.getCurrentState();
				ds.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
					elementSynthSelectLibPanel.setCurrentState();	
					setContentPanel(elementSynthSelectTypePanel, elementSynthSelectLibPanel, 3, 9, "Select Reaction Rate Library", CENTER);
				}
				break;
			case 3:
				elementSynthSelectLibPanel.getCurrentState();
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();	
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectNetworkPanel, 4, 9, "Select Reaction Rate Network", FULL);
				break;
			case 4:
				elementSynthSelectNetworkPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectZonesPanel, 5, 9, "Select Zone", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				ElementSynthInitializeSensOptionsWorker elementSynthInitializeSensOptionsWorker = new ElementSynthInitializeSensOptionsWorker(ds, this, this);
				elementSynthInitializeSensOptionsWorker.execute();
				break;
			case 6:
				if(elementSynthSensStudyPanel.goodSensData()){
					ElementSynthGetRateListAndGetRateInfoWorker elementSynthGetRateInfoWorker = new ElementSynthGetRateListAndGetRateInfoWorker(ds, this, this);
					elementSynthGetRateInfoWorker.execute();
				}else{
					String string = "Please select a reaction and enter up to ten scale factors greater than zero as a comma-separated "
										+ "list for the sensitivity study.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
				}
				break;
			case 7:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 8:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 9, 9, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	}
	
	private void performSINGLE_CUSTOMWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				ds.getCurrentSimWorkDataStructure().setSimTypeDataStructure(ds.getSimTypeMap().get(SimCat.THERMO).get(0));
				ds.getCurrentSimWorkDataStructure().setSimType(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName());
				ds.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
					elementSynthSelectLibPanel.setCurrentState();	
					setContentPanel(elementSynthWorkflowPanel, elementSynthSelectLibPanel, 2, 8, "Select Reaction Rate Library", CENTER);
				}
				break;
			case 2:
				elementSynthSelectLibPanel.getCurrentState();
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();	
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectNetworkPanel, 3, 8, "Select Reaction Rate Network", FULL);
				break;
			case 3:
				elementSynthSelectNetworkPanel.getCurrentState();
				PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while thermodynamic profile set list is loaded.");
				dialog.open();
				ElementSynthGetThermoProfileSetsAndInfoWorker worker2 = new ElementSynthGetThermoProfileSetsAndInfoWorker(ds, this, dialog, this);
				worker2.execute();
				break;
			case 4:
				elementSynthSelectThermoPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
				elementSynthOptionsPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case 6:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 7:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 8, 8, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	}
	
	private void performSINGLE_CUSTOM_SENSWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				ds.getCurrentSimWorkDataStructure().setSimTypeDataStructure(ds.getSimTypeMap().get(SimCat.THERMO).get(0));
				ds.getCurrentSimWorkDataStructure().setSimType(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName());
				ds.setLibGroup("PUBLIC");
				boolean goodPublicDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("SHARED");
				boolean goodSharedDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				ds.setLibGroup("USER");
				boolean goodUserDataSets = Cina.cinaCGIComm.doCGICall("GET RATE LIBRARY LIST", this);
				if(goodPublicDataSets && goodSharedDataSets && goodUserDataSets){
					elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
					elementSynthSelectLibPanel.setCurrentState();	
					setContentPanel(elementSynthWorkflowPanel, elementSynthSelectLibPanel, 2, 9, "Select Reaction Rate Library", CENTER);
				}
				break;
			case 2:
				elementSynthSelectLibPanel.getCurrentState();
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();	
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectNetworkPanel, 3, 9, "Select Reaction Rate Network", FULL);
				break;
			case 3:
				elementSynthSelectNetworkPanel.getCurrentState();
				PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while thermodynamic profile set list is loaded.");
				dialog.open();
				ElementSynthGetThermoProfileSetsAndInfoWorker worker2 = new ElementSynthGetThermoProfileSetsAndInfoWorker(ds, this, dialog, this);
				worker2.execute();
				break;
			case 4:
				elementSynthSelectThermoPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectZonesPanel, 5, 9, "Select Zone", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				ElementSynthInitializeSensOptionsWorker elementSynthInitializeSensOptionsWorker = new ElementSynthInitializeSensOptionsWorker(ds, this, this);
				elementSynthInitializeSensOptionsWorker.execute();
				break;
			case 6:
				if(elementSynthSensStudyPanel.goodSensData()){
					ElementSynthGetRateListAndGetRateInfoWorker elementSynthGetRateInfoWorker = new ElementSynthGetRateListAndGetRateInfoWorker(ds, this, this);
					elementSynthGetRateInfoWorker.execute();
				}else{
					String string = "Please select a reaction and enter up to ten scale factors greater than zero as a comma-separated "
										+ "list for the sensitivity study.";
					GeneralDialog dialog1 = new GeneralDialog(this, string, "Attention!");
					dialog1.setVisible(true);
				}
				break;
			case 7:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 8:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 9, 9, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	}
	
	private void performDIR_STANDARDWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();		
				setContentPanel(elementSynthWorkflowPanel, elementSynthSelectTypePanel, 2, 8, "Select Simulation Type", FULL);
				break;
			case 2:
				elementSynthSelectTypePanel.getCurrentState();
				PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
				dialog.open();
				ElementSynthGetLibDirsWorker worker2 = new ElementSynthGetLibDirsWorker(ds, this, this, dialog);
				worker2.execute();
				break;
			case 3:
				elementSynthSelectLibDirPanel.getCurrentState();
				PleaseWaitDialog dialog2 = new PleaseWaitDialog(this, "Please wait while the rate library list is loaded from this directory.");
				dialog2.open();
				ElementSynthGetLibDirLibsWorker worker3 = new ElementSynthGetLibDirLibsWorker(ds, this, this, dialog2);
				worker3.execute();
				break;
			case 4:
				elementSynthSelectNetworkPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
				elementSynthOptionsPanel.setCurrentState();		
				setContentPanel(elementSynthSelectZonesPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case 6:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 7:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 8, 8, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	}
	
	private void performDIR_CUSTOM_DOUBLE_LOOPINGWorkflowContinue(){
	
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				ds.getCurrentSimWorkDataStructure().setSimTypeDataStructure(ds.getSimTypeMap().get(SimCat.THERMO).get(0));
				ds.getCurrentSimWorkDataStructure().setSimType(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName());
				PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
				dialog.open();
				ElementSynthGetLibDirsWorker worker2 = new ElementSynthGetLibDirsWorker(ds, this, this, dialog);
				worker2.execute();
				break;
			case 2:
				elementSynthSelectLibDirPanel.getCurrentState();
				PleaseWaitDialog dialog2 = new PleaseWaitDialog(this, "Please wait while the rate library list is loaded from this directory.");
				dialog2.open();
				ElementSynthGetLibDirLibsWorker worker3 = new ElementSynthGetLibDirLibsWorker(ds, this, this, dialog2);
				worker3.execute();
				break;
			case 3:
				elementSynthSelectNetworkPanel.getCurrentState();
				PleaseWaitDialog dialog1 = new PleaseWaitDialog(this, "Please wait while thermodynamic profile set list is loaded.");
				dialog1.open();
				ElementSynthGetThermoProfileSetsAndInfoWorker worker1 = new ElementSynthGetThermoProfileSetsAndInfoWorker(ds, this, dialog1, this);
				worker1.execute();
				break;
			case 4:
				elementSynthSelectThermoPanel.getCurrentState();
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.getCurrentState();
				elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
				elementSynthOptionsPanel.setCurrentState();		
				setContentPanel(elementSynthSelectZonesPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case 6:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 7:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 8, 8, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	
	}
	
	private void performDIR_CUSTOM_SINGLE_LOOPINGWorkflowContinue(){
		
		switch(panelIndex){
		
			case 0:
				ElementSynthInitializeWorker worker = new ElementSynthInitializeWorker(ds, this, this);
				worker.execute();
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();		
				setContentPanel(introPanel, elementSynthWorkflowPanel, 1, 7, "Select Simulation Workflow", CENTER);
				addFullButtons();
				break;
			case 1:
				elementSynthWorkflowPanel.getCurrentState();
				AttentionDialog.createDialog(this, "Please make sure that the number of rate libraries is equal to the number of custom thermo profiles.");
				ds.getCurrentSimWorkDataStructure().setSimTypeDataStructure(ds.getSimTypeMap().get(SimCat.THERMO).get(0));
				ds.getCurrentSimWorkDataStructure().setSimType(ds.getCurrentSimWorkDataStructure().getSimTypeDataStructure().getSimTypeName());
				PleaseWaitDialog dialog = new PleaseWaitDialog(this, "Please wait while rate library directory list is loaded.");
				dialog.open();
				ElementSynthGetLibDirsWorker worker2 = new ElementSynthGetLibDirsWorker(ds, this, this, dialog);
				worker2.execute();
				break;
			case 2:
				elementSynthSelectLibDirPanel.getCurrentState();
				PleaseWaitDialog dialog2 = new PleaseWaitDialog(this, "Please wait while the rate library list is loaded from this directory.");
				dialog2.open();
				ElementSynthGetLibDirLibsWorker worker3 = new ElementSynthGetLibDirLibsWorker(ds, this, this, dialog2);
				worker3.execute();
				break;
			case 3:
				elementSynthSelectNetworkPanel.getCurrentState();
				PleaseWaitDialog dialog1 = new PleaseWaitDialog(this, "Please wait while thermodynamic profile set list is loaded.");
				dialog1.open();
				ElementSynthGetThermoProfileSetsAndInfoWorker worker1 = new ElementSynthGetThermoProfileSetsAndInfoWorker(ds, this, dialog1, this);
				worker1.execute();
				break;
			case 4:
				elementSynthSelectThermoPanel.getCurrentState();
				int numProfiles = ds.getCurrentSimWorkDataStructure().getThermoProfileSet().getNumProfiles();
				int numLibs = ds.getLibDirLibList().size();
				int numSims = Math.min(numProfiles, numLibs);
				ds.setNumSingleLoopingSims(numSims);
				if(numProfiles != numLibs){
					String error = "You have chosen " + numLibs + " rate libraries and " + numProfiles + " custom thermo profiles. "
							+ "For your set of simulations, we will automatically select the first " + numSims + " of each of these for this single-looping study "
							+ "where " + numSims + " is the minimum of " + numLibs + " and " + numProfiles + ".";
					AttentionDialog.createDialog(this, error);
				}
				elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
				elementSynthOptionsPanel.setCurrentState();	
				setContentPanel(elementSynthSelectThermoPanel, elementSynthOptionsPanel, 5, 7, "Simulation Options", CENTER);
				break;
			case 5:
				elementSynthOptionsPanel.getCurrentState();
				if(!elementSynthOptionsPanel.isSimWorkModified()){
					ElementSynthCreateAndExecuteSimWorkflowRunWorker createAndExecuteSimWorkflowWorker = new ElementSynthCreateAndExecuteSimWorkflowRunWorker(ds, this, this);
					createAndExecuteSimWorkflowWorker.execute();
				}
				break;
			case 6:
				elementSynthStatusPanel.getCurrentState();
				elementSynthResultsPanel = new ElementSynthResultsPanel(ds, mds, this);	
				elementSynthResultsPanel.setCurrentState();
				setContentPanel(elementSynthStatusPanel, elementSynthResultsPanel, 7, 7, "Simulation Results", FULL);
				addEndButtons();
				break;
			
		}
	
	}
	
	////////////BACK///////////////////////////////////////////////////////////////////////////
	private void performSINGLE_STANDARDWorkflowBack(){
		
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectTypePanel.setVisible(false);					
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectTypePanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectLibPanel.setVisible(false);					
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectTypePanel, 2, 8, "Select Simulation Type", FULL);
				break;
			case 4:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
				elementSynthSelectLibPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibPanel, 3, 8, "Select Reaction Rate Library", CENTER);
				break;
			case 5:	
				elementSynthSelectZonesPanel.setVisible(false);			
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectNetworkPanel, 4, 8, "Select Reaction Rate Network", FULL);
				break;
			case 6:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthOptionsPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 7:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					elementSynthStatusPanel.statusTextArea.setText("");
					ds.setStatusText("");
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
					continueButton.setEnabled(true);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						elementSynthStatusPanel.statusTextArea.setText("");
						ds.setStatusText("");
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
						continueButton.setEnabled(true);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 8:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
		
	}
	
	private void performSINGLE_STANDARD_SENSWorkflowBack(){
	
		switch(panelIndex){
		
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectTypePanel.setVisible(false);					
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectTypePanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectLibPanel.setVisible(false);					
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();
				setContentPanel(elementSynthSelectLibPanel, elementSynthSelectTypePanel, 2, 9, "Select Simulation Type", FULL);
				break;
			case 4:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
				elementSynthSelectLibPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibPanel, 3, 9, "Select Reaction Rate Library", CENTER);
				break;
			case 5:		
				elementSynthSelectZonesPanel.setVisible(false);			
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectNetworkPanel, 4, 9, "Select Reaction Rate Network", FULL);
				break;
			case 6:
				elementSynthSensStudyPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();		
				setContentPanel(elementSynthSensStudyPanel, elementSynthSelectZonesPanel, 5, 9, "Select Zone", CENTER);
				break;
			case 7:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSensStudyPanel = new ElementSynthSensStudyPanel(ds, mds, cgiCom, this);
				elementSynthSensStudyPanel.setCurrentState();
				setContentPanel(elementSynthOptionsPanel, elementSynthSensStudyPanel, 6, 9, "Sensitivity Study Options", CENTER);
				break;
			case 8:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					ds.setStatusText("");
					continueButton.setEnabled(true);
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					elementSynthStatusPanel.statusTextArea.setText("");
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						ds.setStatusText("");
						continueButton.setEnabled(true);
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						elementSynthStatusPanel.statusTextArea.setText("");
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 9:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
	
	}
	
	private void performSINGLE_CUSTOMWorkflowBack(){
		
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectLibPanel.setVisible(false);					
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectLibPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
				elementSynthSelectLibPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibPanel, 2, 8, "Select Reaction Rate Library", CENTER);
				break;
			case 4:
				continueButton.setEnabled(true);
				elementSynthSelectThermoPanel.setVisible(false);					
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectNetworkPanel, 3, 8, "Select Reaction Rate Network", FULL);
				break;
			case 5:
				elementSynthSelectZonesPanel.setVisible(false);	
				elementSynthSelectThermoPanel = new ElementSynthSelectThermoPanel(ds);
				elementSynthSelectThermoPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectThermoPanel, 4, 8, "Select Thermodymic Profiles", FULL);
				break;
			case 6:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthOptionsPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 7:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					elementSynthStatusPanel.statusTextArea.setText("");
					ds.setStatusText("");
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
					continueButton.setEnabled(true);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						elementSynthStatusPanel.statusTextArea.setText("");
						ds.setStatusText("");
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
						continueButton.setEnabled(true);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 8:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
		
	}
	
	private void performSINGLE_CUSTOM_SENSWorkflowBack(){
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectLibPanel.setVisible(false);					
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectLibPanel, elementSynthWorkflowPanel, 1, 9, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibPanel = new ElementSynthSelectLibPanel(ds);
				elementSynthSelectLibPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibPanel, 2, 9, "Select Reaction Rate Library", CENTER);
				break;
			case 4:
				continueButton.setEnabled(true);
				elementSynthSelectThermoPanel.setVisible(false);	
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectNetworkPanel, 3, 9, "Select Reaction Rate Network", FULL);
				break;
			case 5:
				elementSynthSelectZonesPanel.setVisible(false);	
				elementSynthSelectThermoPanel = new ElementSynthSelectThermoPanel(ds);
				elementSynthSelectThermoPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectThermoPanel, 4, 9, "Select Thermodymic Profiles", FULL);
				break;
			case 6:
				elementSynthSensStudyPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthSensStudyPanel, elementSynthSelectZonesPanel, 5, 9, "Select Zone", CENTER);
				break;
			case 7:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSensStudyPanel = new ElementSynthSensStudyPanel(ds, mds, cgiCom, this);
				elementSynthSensStudyPanel.setCurrentState();
				setContentPanel(elementSynthOptionsPanel, elementSynthSensStudyPanel, 6, 9, "Sensitivity Study Options", CENTER);
				break;
			case 8:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					ds.setStatusText("");
					continueButton.setEnabled(true);
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					elementSynthStatusPanel.statusTextArea.setText("");
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						ds.setStatusText("");
						continueButton.setEnabled(true);
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						elementSynthStatusPanel.statusTextArea.setText("");
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 9:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
	
	}
	
	private void performDIR_STANDARDWorkflowBack(){
	
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectTypePanel.setVisible(false);					
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectTypePanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectLibDirPanel.setVisible(false);					
				elementSynthSelectTypePanel = new ElementSynthSelectTypePanel(ds, this, mds);
				elementSynthSelectTypePanel.setCurrentState();
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthSelectTypePanel, 2, 8, "Select Simulation Type", FULL);
				break;
			case 4:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibDirPanel = new ElementSynthSelectLibDirPanel(ds);
				elementSynthSelectLibDirPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibDirPanel, 3, 8, "Select Reaction Rate Library Directory", CENTER);
				break;
			case 5:
				elementSynthSelectZonesPanel.setVisible(false);					
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectNetworkPanel, 4, 8, "Select Reaction Rate Network", FULL);
				break;
			case 6:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthOptionsPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 7:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					elementSynthStatusPanel.statusTextArea.setText("");
					ds.setStatusText("");
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
					continueButton.setEnabled(true);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						elementSynthStatusPanel.statusTextArea.setText("");
						ds.setStatusText("");
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
						continueButton.setEnabled(true);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 8:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
	
	}
	
	private void performDIR_CUSTOM_DOUBLE_LOOPINGWorkflowBack(){
	
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectLibDirPanel.setVisible(false);	
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthWorkflowPanel, 1, 8, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibDirPanel = new ElementSynthSelectLibDirPanel(ds);
				elementSynthSelectLibDirPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibDirPanel, 2, 8, "Select Reaction Rate Library Directory", CENTER);
				break;
			case 4:
				continueButton.setEnabled(true);
				elementSynthSelectThermoPanel.setVisible(false);					
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectNetworkPanel, 3, 8, "Select Reaction Rate Network", FULL);
				break;
			case 5:
				elementSynthSelectZonesPanel.setVisible(false);
				elementSynthSelectThermoPanel = new ElementSynthSelectThermoPanel(ds);
				elementSynthSelectThermoPanel.setCurrentState();
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSelectThermoPanel, 4, 8, "Select Thermodymic Profiles", FULL);
				break;
			case 6:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSelectZonesPanel = new ElementSynthSelectZonesPanel(ds, mds);
				elementSynthSelectZonesPanel.setCurrentState();	
				setContentPanel(elementSynthOptionsPanel, elementSynthSelectZonesPanel, 5, 8, "Select Zones", CENTER);
				break;
			case 7:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					elementSynthStatusPanel.statusTextArea.setText("");
					ds.setStatusText("");
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
					continueButton.setEnabled(true);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						elementSynthStatusPanel.statusTextArea.setText("");
						ds.setStatusText("");
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
						continueButton.setEnabled(true);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 8:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
	
	}
	
	private void performDIR_CUSTOM_SINGLE_LOOPINGWorkflowBack(){
		
		switch(panelIndex){
			case 1:
				elementSynthWorkflowPanel.setVisible(false);
				setContentPanel(elementSynthWorkflowPanel, introPanel, 0, "", CENTER);
				continueButton.setEnabled(true);
				addIntroButtons();
				break;
			case 2:
				elementSynthSelectLibDirPanel.setVisible(false);	
				elementSynthWorkflowPanel = new ElementSynthWorkflowPanel(ds, mds);
				elementSynthWorkflowPanel.setCurrentState();
				continueButton.setEnabled(true);
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthWorkflowPanel, 1, 7, "Select Simulation Workflow", CENTER);
				break;
			case 3:
				elementSynthSelectNetworkPanel.setVisible(false);					
				elementSynthSelectLibDirPanel = new ElementSynthSelectLibDirPanel(ds);
				elementSynthSelectLibDirPanel.setCurrentState();
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectLibDirPanel, 2, 7, "Select Reaction Rate Library Directory", CENTER);
				break;
			case 4:
				continueButton.setEnabled(true);
				elementSynthSelectThermoPanel.setVisible(false);					
				elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
				elementSynthSelectNetworkPanel.setCurrentState();
				setContentPanel(elementSynthSelectThermoPanel, elementSynthSelectNetworkPanel, 3, 7, "Select Reaction Rate Network", FULL);
				break;
			case 5:
				elementSynthOptionsPanel.setVisible(false);
				elementSynthSelectThermoPanel = new ElementSynthSelectThermoPanel(ds);
				elementSynthSelectThermoPanel.setCurrentState();
				setContentPanel(elementSynthOptionsPanel, elementSynthSelectThermoPanel, 4, 7, "Select Thermodymic Profiles", FULL);
				break;
			case 6:
				if(ds.getSimDone()){
					ds.setSimSaved(false);
					ds.setNucSimVector(null);
					elementSynthStatusPanel.statusTextArea.setText("");
					ds.setStatusText("");
					elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
					elementSynthOptionsPanel.setCurrentState();
					setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 5, 7, "Simulation Options", CENTER);
					continueButton.setEnabled(true);
				}else{
					String string = "";
					if(Cina.cinaMainDataStructure.isMasterUser() 
					&& Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						string = "You must abort this element synthesis simulation or make it available offline before before going back.";
						Dialogs.createExceptionDialog(string, this);
					}else if(Cina.cinaMainDataStructure.isMasterUser() 
							&& !Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure().getName().equals("")){
						Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
						ds.setSimSaved(false);
						ds.setNucSimVector(null);
						elementSynthStatusPanel.statusTextArea.setText("");
						ds.setStatusText("");
						elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
						elementSynthOptionsPanel.setCurrentState();
						setContentPanel(elementSynthStatusPanel, elementSynthOptionsPanel, 5, 7, "Simulation Options", CENTER);
						continueButton.setEnabled(true);
					}else{
						string = "You must abort this element synthesis simulation before going back.";
						Dialogs.createExceptionDialog(string, this);
					}
				}
				break;
			case 7:
				addFullButtons();
				elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
				elementSynthStatusPanel.setCurrentState();
				setContentPanel(elementSynthResultsPanel, elementSynthStatusPanel, 6, 7, "Simulation Status", FULL);
				elementSynthStatusPanel.statusLabel.setText("Status of Element Synthesis Simulation: Program Complete");
				break;
		}
	
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(simSavedDialog!=null){
			if(ae.getSource()==simSavedDialog.getYesButton()){
				simSavedDialog.setVisible(false);
				simSavedDialog.dispose();
				elementSynthResultsPanel.createSaveNucSimSetDialog(this);
			}else if(ae.getSource()==simSavedDialog.getNoButton()){
				simSavedDialog.setVisible(false);
				simSavedDialog.dispose();
				if(Cina.elementVizFrame==null){
					Cina.elementVizFrame = new ElementVizFrame(Cina.elementVizDataStructure, cgiCom);
					Cina.elementVizFrame.setSize(600,450);
					Cina.elementVizFrame.setResizable(true);
	    				Cina.elementVizFrame.setTitle("Element Synthesis Visualizer");
	    				Cina.elementVizFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);	
				}
		        	if(ds.getNucSimVector()!=null && ds.getNucSimVector().size()>0){
		        		Cina.elementVizFrame.initialize(ds.getNucSimVector());
		        	}
				Cina.elementVizFrame.setVisible(true);
			}	
		}
		
		if(ae.getSource()==continueButton){
		
			if(panelIndex==1){
				elementSynthWorkflowPanel.getCurrentState();
			}

			switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
				case SINGLE_STANDARD:
					performSINGLE_STANDARDWorkflowContinue();
					break;
				case SINGLE_STANDARD_SENS:
					performSINGLE_STANDARD_SENSWorkflowContinue();
					break;
				case SINGLE_CUSTOM:
					performSINGLE_CUSTOMWorkflowContinue();
					break;
				case SINGLE_CUSTOM_SENS:
					performSINGLE_CUSTOM_SENSWorkflowContinue();
					break;
				case DIR_STANDARD:
					performDIR_STANDARDWorkflowContinue();
					break;
				case DIR_CUSTOM_DOUBLE_LOOPING:
					performDIR_CUSTOM_DOUBLE_LOOPINGWorkflowContinue();
					break;
				case DIR_CUSTOM_SINGLE_LOOPING:
					performDIR_CUSTOM_SINGLE_LOOPINGWorkflowContinue();
					break;
			}

		}else if(ae.getSource()==backButton){
		
			switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
				case SINGLE_STANDARD:
					performSINGLE_STANDARDWorkflowBack();
					break;
				case SINGLE_STANDARD_SENS:
					performSINGLE_STANDARD_SENSWorkflowBack();
					break;
				case SINGLE_CUSTOM:
					performSINGLE_CUSTOMWorkflowBack();
					break;
				case SINGLE_CUSTOM_SENS:
					performSINGLE_CUSTOM_SENSWorkflowBack();
					break;
				case DIR_STANDARD:
					performDIR_STANDARDWorkflowBack();
					break;
				case DIR_CUSTOM_DOUBLE_LOOPING:
					performDIR_CUSTOM_DOUBLE_LOOPINGWorkflowBack();
					break;
				case DIR_CUSTOM_SINGLE_LOOPING:
					performDIR_CUSTOM_SINGLE_LOOPINGWorkflowBack();
					break;
			}
		
		}
		
		validate();
		
    	if(ae.getSource()==continueOnButton){
        	
    		if(!ds.getSimSaved()){
    			
    			String string = "You have not saved the current simulation. Would you like to now?";
    			simSavedDialog = new CautionDialog(this, this, string, "Attention!");
    			simSavedDialog.setVisible(true);
    			
    		}else{
    		
	        	if(Cina.elementVizFrame==null){
					Cina.elementVizFrame = new ElementVizFrame(Cina.elementVizDataStructure, cgiCom);
					Cina.elementVizFrame.setSize(600,450);
					Cina.elementVizFrame.setResizable(true);
	    			Cina.elementVizFrame.setTitle("Element Synthesis Visualizer");
	    			Cina.elementVizFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);	
	        	}
	        	Cina.elementVizFrame.setVisible(true);
	        	if(ds.getNucSimVector()!=null && ds.getNucSimVector().size()>0){
	        		Cina.elementVizFrame.initialize(ds.getNucSimVector());
	        	}
    		}
    	}
	}
	
	private void gotoSelectLibDirPanel(){
		elementSynthSelectLibDirPanel = new ElementSynthSelectLibDirPanel(ds);
		elementSynthSelectLibDirPanel.setCurrentState();	
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case DIR_STANDARD:
				setContentPanel(elementSynthSelectTypePanel, elementSynthSelectLibDirPanel, 3, 8, "Select Reaction Rate Library Directory", CENTER);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthSelectLibDirPanel, 2, 8, "Select Reaction Rate Library Directory", CENTER);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthSelectLibDirPanel, 2, 7, "Select Reaction Rate Library Directory", CENTER);
				break;
			default:
				break;	
		}
		
	}
	
	private void gotoInputThermoPanel(){
		elementSynthSelectThermoPanel = new ElementSynthSelectThermoPanel(ds);
		elementSynthSelectThermoPanel.setCurrentState();
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_CUSTOM:
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectThermoPanel, 4, 8, "Select Thermodymic Profiles", FULL);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectThermoPanel, 4, 9, "Select Thermodymic Profiles", FULL);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectThermoPanel, 4, 8, "Select Thermodymic Profiles", FULL);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				int numLibs = ds.getLibDirLibList().size();
				AttentionDialog.createDialog(this, "You have chosen " + numLibs + " rate libraries, please make sure that the number of custom thermo profiles are the same.");
				setContentPanel(elementSynthSelectNetworkPanel, elementSynthSelectThermoPanel, 4, 7, "Select Thermodymic Profiles", FULL);
				break;
			default:
				break;	
		}
	}
	
	private void gotoSelectNetworkPanel(){
		elementSynthSelectNetworkPanel = new ElementSynthSelectNetworkPanel(ds);
		elementSynthSelectNetworkPanel.setCurrentState();	
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case DIR_STANDARD:
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthSelectNetworkPanel, 4, 8, "Select Reaction Rate Network", FULL);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthSelectNetworkPanel, 3, 8, "Select Reaction Rate Network", FULL);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				setContentPanel(elementSynthSelectLibDirPanel, elementSynthSelectNetworkPanel, 3, 7, "Select Reaction Rate Network", FULL);
				break;
			default:
				break;
		}
	}
	
	private void gotoSensStudy(){
		elementSynthSensStudyPanel = new ElementSynthSensStudyPanel(ds, mds, cgiCom, this);
		elementSynthSensStudyPanel.setCurrentState();
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSensStudyPanel, 6, 9, "Sensitivity Study Options", CENTER);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthSelectZonesPanel, elementSynthSensStudyPanel, 6, 9, "Sensitivity Study Options", CENTER);
				break;
			default:
				break;	
		}
	}
	
	private void gotoOptionsPanel(){
		elementSynthSensStudyPanel.getCurrentState();
		elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
		elementSynthOptionsPanel.setCurrentState();	
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
				setContentPanel(elementSynthSensStudyPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthSensStudyPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				break;
			default:
				break;	
		}
	}
	
	public void gotoOptionsPanelFromWorkflowPanel(){
	
		elementSynthOptionsPanel = new ElementSynthOptionsPanel(ds);
		elementSynthOptionsPanel.setCurrentState();	
		
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case SINGLE_STANDARD_SENS:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				break;
			case SINGLE_CUSTOM:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 7, 9, "Simulation Options", CENTER);
				break;
			case DIR_STANDARD:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 6, 8, "Simulation Options", CENTER);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthOptionsPanel, 5, 7, "Simulation Options", CENTER);
				break;
				
			default:
				break;	
		}
	}
	
	public void gotoStatusPanelFromWorkflowPanel(){
		
		ds.setSimSaved(false);
		ds.setSimDone(false);
		ds.setNucSimVector(null);
		elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
		elementSynthStatusPanel.statusTextArea.setText("");
		ds.setStatusText("");
		continueButton.setEnabled(false);
		elementSynthStatusPanel.abortButton.setEnabled(true);
		elementSynthStatusPanel.restartButton.setEnabled(false);
		elementSynthStatusPanel.setCurrentState();
		
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case SINGLE_STANDARD_SENS:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				break;
			case SINGLE_CUSTOM:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				break;
			case DIR_STANDARD:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				setContentPanel(elementSynthWorkflowPanel, elementSynthStatusPanel, 6, 7, "Simulation Status", FULL);
				break;
			default:
				break;	
		}
		
		elementSynthStatusPanel.startElementSynthesisGetSimWorkflowRunStatusController();
		
	}
	
	public void gotoStatusPanel(){
	
		elementSynthOptionsPanel.getCurrentState();
		ds.setSimSaved(false);
		ds.setSimDone(false);
		ds.setNucSimVector(null);
		elementSynthStatusPanel = new ElementSynthStatusPanel(ds, this);
		elementSynthStatusPanel.statusTextArea.setText("");
		ds.setStatusText("");
		continueButton.setEnabled(false);
		elementSynthStatusPanel.abortButton.setEnabled(true);
		elementSynthStatusPanel.restartButton.setEnabled(false);
		elementSynthStatusPanel.setCurrentState();
		
		switch(ds.getCurrentSimWorkDataStructure().getSimWorkflowMode()){
			case SINGLE_STANDARD:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case SINGLE_STANDARD_SENS:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				break;
			case SINGLE_CUSTOM:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case SINGLE_CUSTOM_SENS:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 8, 9, "Simulation Status", FULL);
				break;
			case DIR_STANDARD:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case DIR_CUSTOM_DOUBLE_LOOPING:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 7, 8, "Simulation Status", FULL);
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				setContentPanel(elementSynthOptionsPanel, elementSynthStatusPanel, 6, 7, "Simulation Status", FULL);
				break;
			default:
				break;	
		}
		
		elementSynthStatusPanel.startElementSynthesisGetSimWorkflowRunStatusController();
		
	}
	
	public void closeAllFrames(){
	
		if(elementSynthSunetFileFrame!=null){
			elementSynthSunetFileFrame.setVisible(false);
			elementSynthSunetFileFrame.dispose();
		}
		if(elementSynthAbundFileFrame!=null){
			elementSynthAbundFileFrame.setVisible(false);
			elementSynthAbundFileFrame.dispose();
		}
		if(elementSynthThermoFileFrame!=null){
			elementSynthThermoFileFrame.setVisible(false);
			elementSynthThermoFileFrame.dispose();
		}
		if(elementSynthWeightFileFrame!=null){
			elementSynthWeightFileFrame.setVisible(false);
			elementSynthWeightFileFrame.dispose();
		}
		if(elementSynthNetworkSummeryFrame!=null){
			elementSynthNetworkSummeryFrame.setVisible(false);
			elementSynthNetworkSummeryFrame.dispose();
		}
		
	}

	public void updateAfterElementSynthCreateAndExecuteSimWorkflow(){
		gotoStatusPanel();
	}

	public void updateAfterElementSynthGetRateListAndGetRateInfo() {
		gotoOptionsPanel();
	}
	
	public void updateAfterElementSynthInitializeSensOptions() {
		gotoSensStudy();
	}

	public void updateAfterElementSynthGetLibDirLibs() {
		gotoSelectNetworkPanel();
	}

	public void updateAfterElementSynthGetLibDirs() {
		gotoSelectLibDirPanel();
	}

	public void updateAfterElementSynthGetThermoProfileSetsAndInfo() {
		gotoInputThermoPanel();
	}

	public void updateAfterElementSynthInitialize(){}

}