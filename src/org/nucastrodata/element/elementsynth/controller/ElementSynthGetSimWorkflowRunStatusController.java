package org.nucastrodata.element.elementsynth.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.ElementSynthStatusPanel;

public class ElementSynthGetSimWorkflowRunStatusController implements ActionListener{

	private ElementSynthDataStructure ds;
	private ElementSynthStatusPanel statusPanel;
	private ElementSynthFrame frame;
	private javax.swing.Timer timer;

	public ElementSynthGetSimWorkflowRunStatusController(ElementSynthDataStructure ds
															, ElementSynthStatusPanel statusPanel
															, ElementSynthFrame frame) {
		this.ds = ds;
		this.statusPanel = statusPanel;
		this.frame = frame;
		timer = new Timer(0, this);
		timer.setInitialDelay(500);
		timer.setDelay(1000);
	}

	public void stop(){
		timer.stop();
	}
	
	public void start(){
		timer.start();
	}
	
	public void restart(){
		timer.restart();
	}

	public void actionPerformed(ActionEvent ae) {
		try{
			CGICom cgiCom = new CGICom();
			cgiCom.doCGICall(Cina.cinaMainDataStructure, ds.getCurrentSimWorkRunDataStructure(), CGICom.GET_SIM_WORKFLOW_RUN_STATUS, frame);
			statusPanel.updateAfterGetSimWorkflowStatus();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

