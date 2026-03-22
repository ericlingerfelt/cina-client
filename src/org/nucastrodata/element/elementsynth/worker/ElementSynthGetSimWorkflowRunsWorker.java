package org.nucastrodata.element.elementsynth.worker;


import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingWorker;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.datastructure.util.SimTypeDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.listener.ElementSynthGetSimWorkflowRunsListener;
import org.nucastrodata.enums.SimCat;
import org.nucastrodata.io.CGICom;

public class ElementSynthGetSimWorkflowRunsWorker extends SwingWorker<Void, Void>{

	private ElementSynthDataStructure ds;
	private ElementSynthFrame parent;
	private ElementSynthGetSimWorkflowRunsListener listener;

	public ElementSynthGetSimWorkflowRunsWorker(ElementSynthDataStructure ds
											, ElementSynthFrame parent
											, ElementSynthGetSimWorkflowRunsListener listener){
		this.ds  = ds;
		this.parent = parent;
		this.listener = listener;
	}

	protected Void doInBackground(){
		try{

			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
					, ds
					, CGICom.GET_SIM_WORKFLOWS
					, parent);

			if(ds.getSimWorkMap().size()!=0){

				String indices = "";

				Iterator<ElementSimWorkDataStructure> itr = ds.getSimWorkMap().values().iterator();
				while(itr.hasNext()){
					indices += itr.next().getIndex() + ",";
				}
				indices = indices.substring(0, indices.lastIndexOf(","));
				ds.setSimWorkflowIndices(indices);

				Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
						, ds
						, CGICom.GET_SIM_WORKFLOW_INFO
						, parent);

				itr = ds.getSimWorkMap().values().iterator();
				while(itr.hasNext()){
					ElementSimWorkDataStructure eswd = itr.next();
					setThermoProfileSet(eswd);
					setSimTypeDataStructure(eswd);
					if(eswd.getSimTypeDataStructure().getSimCat().equals(SimCat.THERMO)){
						int numZones = eswd.getThermoProfileSet().getNumProfiles();
						int[] zoneArray = new int[numZones];
						for(int index=0; index<zoneArray.length; index++){
							zoneArray[index] = Integer.valueOf(index+1);
						}
						eswd.getSimTypeDataStructure().setZoneArray(zoneArray);
					}
				}
			}
	
			Cina.cgiCom.doCGICall(Cina.cinaMainDataStructure
					, ds
					, CGICom.GET_SIM_WORKFLOW_RUNS
					, parent);
					
			if(ds.getSimWorkRunMap().size()!=0){
			
				Iterator<ElementSimWorkRunDataStructure> itr2 = ds.getSimWorkRunMap().values().iterator();
				while(itr2.hasNext()){
					ElementSimWorkRunDataStructure eswrds = itr2.next();
					setElementSimWorkDataStructure(eswrds);
				}
			
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	protected void setElementSimWorkDataStructure(ElementSimWorkRunDataStructure eswrds){
		int simWorkflowIndex = eswrds.getSimWorkflowIndex();
		Iterator<ElementSimWorkDataStructure> itr = ds.getSimWorkMap().values().iterator();
		while(itr.hasNext()){
			ElementSimWorkDataStructure elementSimWorkDataStructure = itr.next();
			if(simWorkflowIndex == elementSimWorkDataStructure.getIndex()){
				eswrds.setSimWorkDataStructure(elementSimWorkDataStructure);
				return;
			}
		}
	}

	protected void setThermoProfileSet(ElementSimWorkDataStructure eswd){
		ThermoProfileSetDataStructure tpsds = eswd.getThermoProfileSet();
		String thermoPath = eswd.getThermoPath();
		Iterator<ThermoProfileSetDataStructure> itr = ds.getSetMap().values().iterator();
		while(itr.hasNext()){
			ThermoProfileSetDataStructure thermoProfileSetDataStructure = itr.next();
			if(thermoPath.equals(thermoProfileSetDataStructure.getPath())){
				eswd.setThermoProfileSet(thermoProfileSetDataStructure);
				return;
			}
		}
	}
	
	protected void setSimTypeDataStructure(ElementSimWorkDataStructure eswd){
		SimTypeDataStructure stds = eswd.getSimTypeDataStructure();
		String simType = eswd.getSimType();
		Iterator<ArrayList<SimTypeDataStructure>> itr = ds.getSimTypeMap().values().iterator();
		while(itr.hasNext()){
			ArrayList<SimTypeDataStructure> list = itr.next();
			for(SimTypeDataStructure simTypeDataStructure: list){
				if(simType.equals(simTypeDataStructure.getSimTypeName())){
					eswd.setSimTypeDataStructure(simTypeDataStructure);
					return;
				}
			}
		}
	}

	protected void done(){
		listener.updateAfterElementSynthGetSimWorkflowRuns();
	}
	
}