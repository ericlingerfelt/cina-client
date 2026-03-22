package org.nucastrodata.element.elementworkman;

import javax.swing.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import java.awt.event.*;
import java.util.Iterator;

import info.clearthought.layout.*;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.Fonts;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementworkman.worker.ElementWorkManEraseWorkflowsWorker;

public class ElementWorkManErase2Panel extends JPanel implements ActionListener{

	private ElementWorkManDataStructure ds;
	private MainDataStructure mds;
	private CGICom cgiCom;
	private ElementWorkManFrame frame;
	private JButton deleteButton;
	private WordWrapLabel topLabel;
	private TableLayout layout;
	private CautionDialog cautionDialog;
	private JTextArea area;
	
	public ElementWorkManErase2Panel(MainDataStructure mds, ElementWorkManDataStructure ds, CGICom cgiCom, ElementWorkManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		area = new JTextArea();
		area.setEditable(false);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		
		JScrollPane sp = new JScrollPane(area);
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Click the button below to erase the selected element synthesis workflows from your User storage folder.</html>");
		
		deleteButton = new JButton("Erase Selected Element Synthesis Workflows");
		deleteButton.setFont(Fonts.buttonFont);
		deleteButton.addActionListener(this);

		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		setLayout(layout);
		
		add(topLabel, 		"1, 1, c, c");
		add(sp,			 	"1, 3, f, f");
		add(deleteButton, 	"1, 5, c, c");
		
	}
	
	public void actionPerformed(ActionEvent ae){
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
		
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
				
				PleaseWaitDialog dialog = new PleaseWaitDialog(frame, "Please wait while the selected workflows are erased.");
				dialog.open();
				ElementWorkManEraseWorkflowsWorker worker = new ElementWorkManEraseWorkflowsWorker(ds, this, dialog);
				worker.execute();
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==deleteButton){
			String string = "You are about to erase the selected element synthesis workflows. Do you wish to continue?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
	} 
	
	public void updateAfterEraseSimWorkflows(){
		
		ds.setSimWorkMapSelected(null);
		
		removeAll();
		topLabel.setText("<html>You have successfully erased the selected element synthesis workflows from your User storage folder.</html>");
		
		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, gap};
		layout = new TableLayout(column, row);
		setLayout(layout);
		
		add(topLabel, 		"1, 1, c, c");
		
		validate();
		repaint();
	
	}
	
	public void setCurrentState(){
		
		String string = "";
		
		Iterator<String> itr = ds.getSimWorkMapSelected().keySet().iterator(); 
		while (itr.hasNext()){
			string += itr.next() + "\n";
		}
	
		area.setText(string);
		
	}

	public void getCurrentState(){}
	
}

