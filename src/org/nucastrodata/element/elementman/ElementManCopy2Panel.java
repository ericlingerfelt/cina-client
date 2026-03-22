package org.nucastrodata.element.elementman;

import javax.swing.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementManDataStructure;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.Fonts;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.PleaseWaitDialog;
import org.nucastrodata.element.elementman.ElementManFrame;
import org.nucastrodata.element.elementman.worker.ElementManCopySimsToSharedWorker;

public class ElementManCopy2Panel extends JPanel implements ActionListener{

	private ElementManDataStructure ds;
	private MainDataStructure mds;
	private CGICom cgiCom;
	private ElementManFrame frame;
	private JButton copyButton;
	private WordWrapLabel topLabel;
	private TableLayout layout;
	private CautionDialog cautionDialog;
	private JTextArea area;
	
	public ElementManCopy2Panel(MainDataStructure mds
								, ElementManDataStructure ds
								, CGICom cgiCom
								, ElementManFrame frame){
		
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
		topLabel.setText("<html>Click the button below to copy the selected element synthesis "
									+ "simulations from your User storage folder to the Shared storage folder.</html>");
		
		copyButton = new JButton("Copy Selected Element Synthesis Simulations To Shared Folder");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 20, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		setLayout(layout);
		
		add(topLabel, 		"1, 1, c, c");
		add(sp,			 	"1, 3, f, f");
		add(copyButton, 	"1, 5, c, c");
		
	}

	public void actionPerformed(ActionEvent ae){
		
		if(cautionDialog!=null){
		
			if(ae.getSource()==cautionDialog.getYesButton()){
		
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
		
				PleaseWaitDialog dialog = new PleaseWaitDialog(frame, "Please wait while the selected simulations are copied to the Shared storage folder.");
				dialog.open();
				ElementManCopySimsToSharedWorker worker = new ElementManCopySimsToSharedWorker(ds, this, dialog);
				worker.execute();
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			String string = "You are about to copy the selected element synthesis simulations "
								+ "from your User storage folder to the Shared storage folder. Do you wish to continue?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
	} 

	public void updateAfterCopySimsToShared(){
	
		ds.setSimMapSelected(null);
		
		removeAll();
		topLabel.setText("<html>You have successfully copied the selected element synthesis simulations "
							+ "from your User storage folder to the Shared storage folder.</html>");
		
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
		String[] array = ds.getPaths().split(",");
		
		for(int i=0; i<array.length; i++){
			String path = array[i];
			String name = path.substring(path.lastIndexOf("/")+1);
			string += name + "\n";
		}
		
		area.setText(string);
		
	}

	public void getCurrentState(){}
	
}


