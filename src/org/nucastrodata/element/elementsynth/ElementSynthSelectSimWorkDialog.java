package org.nucastrodata.element.elementsynth;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.export.print.PrintableEditorPane;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class ElementSynthSelectSimWorkDialog extends JDialog implements ActionListener, TreeSelectionListener{

	private ElementSynthDataStructure ds;
	private ElementSynthSimWorkflowTree tree;
	private ElementSimWorkDataStructure eswds;
	private JButton submitButton, cancelButton;
	private PrintableEditorPane textPane;

	public ElementSynthSelectSimWorkDialog(JFrame owner, ElementSynthDataStructure ds){
		
		super(owner, true);
		
		this.ds = ds;
		
		setSize(750, 500);
		setTitle("Select Element Synthesis Workflow");
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select an element synthesis workflow to load using the options below.</html>");
		
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree = new ElementSynthSimWorkflowTree();
		tree.setSelectionModel(treeSelectionModel);
		tree.addTreeSelectionListener(this);
		
		JScrollPane sp1 = new JScrollPane(tree);
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		JScrollPane sp2 = new JScrollPane(textPane);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);	
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
		tree.removeTreeSelectionListener(this);
		tree.setCurrentState(ds.getSimWorkMap(), false);
		tree.addTreeSelectionListener(this);
	}

	private String getInfoReport(){
		
		String string = "";
		if(eswds != null){
			string += "<html><body>";
			string += "<b>Element Synthesis Workflow Report</b><br><br>";
			string += "<table border=\"1\"><tr><td><b>Element Synthesis Workflow</b></td><td>" + eswds.getFolderType().name() + "/" + eswds.getName() + "</td></tr>";
			string += "<tr><td><b>Element Synthesis Workflow Type</b></td><td>" + eswds.getSimType() + "</td></tr>";
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
		}
		return string;
	}

	private void setText(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==submitButton){
			if(tree.getSelectionPaths().length == 1){
				eswds = tree.getSelectedObjects(tree.getSelectionPaths()).get(0);
				setVisible(false);
			}else{
				AttentionDialog.createDialog(this, "Please select an Element Synthesis Workflow from the tree on the left.");
			}
		}else if(ae.getSource()==cancelButton){
			eswds = null;
			setVisible(false);
		}
	}
	
	public static ElementSimWorkDataStructure createElementSynthSelectSimWorkflowDialog(JFrame owner, ElementSynthDataStructure ds){
		ElementSynthSelectSimWorkDialog dialog = new ElementSynthSelectSimWorkDialog(owner, ds);
		dialog.setCurrentState();
		dialog.setVisible(true);
		return dialog.eswds;
	}

	public void valueChanged(TreeSelectionEvent tse) {
		if(tree.getSelectionPaths().length > 0){
			if(tree.getSelectedObjects(tree.getSelectionPaths()).size() > 0){
				eswds = tree.getSelectedObjects(tree.getSelectionPaths()).get(0);
			}
		}
		setText();
	}
	
}
