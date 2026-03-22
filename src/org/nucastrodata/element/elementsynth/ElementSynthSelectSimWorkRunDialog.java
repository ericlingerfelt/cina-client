package org.nucastrodata.element.elementsynth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import org.nucastrodata.Cina;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.element.elementsynth.listener.ElementSynthEraseSimWorkflowListener;
import org.nucastrodata.element.elementsynth.worker.ElementSynthEraseSimWorkflowRunWorker;
import org.nucastrodata.export.print.PrintableEditorPane;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementSynthSelectSimWorkRunDialog extends JDialog implements ActionListener, ListSelectionListener, ElementSynthEraseSimWorkflowListener{

	private ElementSynthDataStructure ds;
	private DefaultListModel<ElementSimWorkRunDataStructure> listModel;
	private JList<ElementSimWorkRunDataStructure> list;
	private ElementSimWorkRunDataStructure eswrds;
	private JButton submitButton, cancelButton, deleteButton;
	private PrintableEditorPane textPane;

	public ElementSynthSelectSimWorkRunDialog(JFrame owner, ElementSynthDataStructure ds){
		
		super(owner, true);

		this.ds = ds;
		
		setSize(750, 500);
		setTitle("Select Offline Element Synthesis Simulation");
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select an offline element synthesis simulation to load using the options below. "
				+ "If the selected offline simulation is completed or has exited with an error, you may delete the selected offline simulation by clicking <i>Erase</i>.</html>");
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		JLabel listTitleLabel = new JLabel("Available Offline Simulations");

		JScrollPane sp2 = new JScrollPane(textPane);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		deleteButton = new JButton("Erase");
		deleteButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(cancelButton);

		listModel = new DefaultListModel<ElementSimWorkRunDataStructure>();
		list = new JList<ElementSimWorkRunDataStructure>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		
		JScrollPane sp1 = new JScrollPane(list);
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.add(listTitleLabel, BorderLayout.NORTH);
		listPanel.add(sp1, BorderLayout.CENTER);
		
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, sp2);	
		sp.setDividerLocation(250);
		
		double gap = 10;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap};

		setLayout(new TableLayout(column, row));
		
		add(topLabel, 		"1, 1, c, c");
		add(sp, 			"1, 3, f, f");
		add(buttonPanel, 	"1, 5, c, c");
		
	}
	
	public void setCurrentState(){
		list.removeListSelectionListener(this);
		listModel.removeAllElements();
		Iterator<ElementSimWorkRunDataStructure> itr = ds.getSimWorkRunMap().values().iterator();
		while(itr.hasNext()){
			listModel.addElement(itr.next());
		}
		list.addListSelectionListener(this);
		list.setSelectedIndex(0);
		eswrds = list.getSelectedValue();
		setText();
	}

	private String getInfoReport(){
		
		String string = "";
		string += "<html><body>";
		string += "<b>Offline Element Synthesis Simulation Report</b><br><br>";
		string += "<table border=\"1\"><tr><td><b>Offline Element Synthesis Simulation Name</b></td><td><b>" + eswrds.getName() + "</b></td></tr>";
		string += "<tr><td><b>Current Status</b></td><td>" + eswrds.getCurrentStatus().toString() + "</td></tr>";
		string += "<tr><td><b>Current Library</b></td><td>" + eswrds.getCurrentLib() + "</td></tr>";
		string += "<tr><td><b>Current Zone</b></td><td>" + eswrds.getCurrentZone() + "</td></tr>";
		string += "<tr><td><b>Current Scale Factor</b></td><td>" + eswrds.getCurrentScaleFactor() + "</td></tr>";
		string += "<tr><td><b>Start Date</b></td><td>" + eswrds.getStartDate() + "</td></tr>";
		string += "<tr><td><b>End Date</b></td><td>" + eswrds.getEndDate() + "</td></tr>";
		
		ElementSimWorkDataStructure eswds = eswrds.getSimWorkDataStructure();
		
		string += "<tr><td><b>Element Synthesis Simulation Type</b></td><td>" + eswds.getSimType() + "</td></tr>";
		switch(eswds.getSimWorkflowMode()){
			case SINGLE_CUSTOM_SENS:
			case SINGLE_CUSTOM:
			case DIR_CUSTOM_DOUBLE_LOOPING:
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "<tr><td><b>Thermo Profile Set</b></td><td>" + eswds.getThermoPath() + "</td></tr>";
				break;
			default:
				break;
		}
		string += "<tr><td><b>Number of Timesteps Before Exit</b></td><td>" + eswds.getMaxTimesteps() + "</td></tr>";
		string += "<tr><td><b>Start Time (sec)</b></td><td>" + eswds.getStartTime() + "</td></tr>";
		string += "<tr><td><b>Stop Time (sec)</b></td><td>" + eswds.getStopTime() + "</td></tr>";
		string += "<tr><td><b>Include Weak Reactions</b></td><td>" + (eswds.getIncludeWeak() ? "Yes" : "No") + "</td></tr>";
		string += "<tr><td><b>Include Screening</b></td><td>" + (eswds.getIncludeScreening() ? "Yes" : "No") + "</td></tr>";
		string += "<tr><td><b>User Notes</b></td><td>" + (eswds.getNotes().trim().equals("") ? "N/A" : eswds.getNotes().trim()) + "</td></tr>";
		string += "<tr><td><b>Selected Zones</b></td><td>" + eswds.getZones() + "</td></tr>";
		string += "<tr><td><b>26Al State</b></td><td>" 
				+ (eswds.getAl26Type().equals("STABLE") ? "only stable": "metastable + ground")  + "</td></tr>";
		string += "<tr><td><b>Initial Abundance Profile</b></td><td>" + "PUBLIC/" + eswds.getInitAbundPath().substring(eswds.getInitAbundPath().lastIndexOf("/")+1) + "</td></tr>";
		string += "<tr><td><b>Sunet File</b></td><td>" 
				+ "PUBLIC/" + eswds.getSunetPath().substring(eswds.getSunetPath().lastIndexOf("/")+1) 
				+ "</td></tr>";
		
		switch(eswds.getSimWorkflowMode()){
			case DIR_CUSTOM_DOUBLE_LOOPING:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + eswds.getLibDir() + "</td></tr>";
				string += "<tr><td><b>Looping Type</b></td><td>Double Looping</td></tr>";
				break;
			case DIR_CUSTOM_SINGLE_LOOPING:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + eswds.getLibDir() + "</td></tr>";
				string += "<tr><td><b>Looping Type</b></td><td>Single Looping</td></tr>";
				break;
			case DIR_STANDARD:
				string += "<tr><td><b>Reaction Rate Library Directory</b></td><td>" + eswds.getLibDir() + "</td></tr>";
				break;
			case SINGLE_CUSTOM:
			case SINGLE_CUSTOM_SENS:
			case SINGLE_STANDARD:
			case SINGLE_STANDARD_SENS:
				string += "<tr><td><b>Reaction Rate Library</b></td><td>" + eswds.getLibraryPath() + "</td></tr>";
				break;
		}
		
		switch(eswds.getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
				string += "<tr><td><b>Selected Reaction Rate</b></td><td>" + eswds.getReactionRate() + "</td></tr>";
				string += "<tr><td><b>Scale Factors</b></td><td>" + eswds.getScaleFactors() + "</td></tr>";
				break;
			default:
				break;
		}
		string += "</table></body></html>";
		return string;
	}

	private void setText(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==submitButton){
			if(list.getSelectedValue() != null){
				eswrds = list.getSelectedValue();
				setVisible(false);
			}else{
				AttentionDialog.createDialog(this, "Please select an Offline Element Synthesis Simulation from the list on the left.");
			}
		}else if(ae.getSource()==cancelButton){
			eswrds = null;
			setVisible(false);
		}else if(ae.getSource()==deleteButton){
			ElementSynthEraseSimWorkflowRunWorker worker = new ElementSynthEraseSimWorkflowRunWorker(eswrds, Cina.elementSynthFrame, this);
			worker.execute();
		}
	}
	
	
	public static ElementSimWorkRunDataStructure createElementSynthSelectSimWorkRunDialog(JFrame owner, ElementSynthDataStructure ds){
		ElementSynthSelectSimWorkRunDialog dialog = new ElementSynthSelectSimWorkRunDialog(owner, ds);
		dialog.setCurrentState();
		dialog.setVisible(true);
		return dialog.eswrds;
	}

	public void valueChanged(ListSelectionEvent lse) {
		eswrds = list.getSelectedValue();
		if(eswrds.getCurrentStatus() == ElementSynthSimWorkflowRunStatus.ERROR 
				|| eswrds.getCurrentStatus() == ElementSynthSimWorkflowRunStatus.COMPLETE){
			deleteButton.setEnabled(true);
		}else{
			deleteButton.setEnabled(false);
		}
		setText();
	}

	public void updateAfterElementSynthEraseSimWorkflow(ElementSimWorkRunDataStructure eswrds){
		ds.getSimWorkRunMap().remove(eswrds.getName());
		if(ds.getSimWorkRunMap().size()==0){
			this.eswrds = null;
			setVisible(false);
			AttentionDialog.createDialog(Cina.elementSynthFrame, "There are currently no offline element synthesis simulations to load.");
		}else{
			setCurrentState();
		}
	}

	
}
