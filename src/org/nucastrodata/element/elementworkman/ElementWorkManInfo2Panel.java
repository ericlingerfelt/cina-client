package org.nucastrodata.element.elementworkman;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementWorkManDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.awt.*;
import org.nucastrodata.Fonts;

public class ElementWorkManInfo2Panel extends JPanel implements ActionListener{

	private ElementWorkManDataStructure ds;
	private MainDataStructure mds;
	private ElementWorkManFrame frame;
	private PrintableEditorPane textPane;
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;

	public ElementWorkManInfo2Panel(MainDataStructure mds, ElementWorkManDataStructure ds, ElementWorkManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL, gap, 
							TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		JScrollPane sp = new JScrollPane(textPane);
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);

		add(sp, "0, 1, f, f");
		add(buttonPanel, "0, 3, c, c");
	
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), frame, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), frame, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
		}else if(ae.getSource()==printButton){
			textPane.print();
		}
	}
	
	private String getTextString(){
		String string = "";
		
		string = "Element Synthesis Workflow Report\n\n";
		Iterator<String> itr = ds.getSimWorkMapSelected().keySet().iterator();
		while(itr.hasNext()){
		
			ElementSimWorkDataStructure eswds = ds.getSimWorkMapSelected().get(itr.next());
			string += "Element Synthesis Workflow\t" + eswds.getFolderType().name() + "/" + eswds.getName() + "\n";
			
			
			string += "Element Synthesis Simulation Type\t" + eswds.getSimType() + "\n";
			switch(eswds.getSimWorkflowMode()){
				case SINGLE_CUSTOM_SENS:
				case SINGLE_CUSTOM:
				case DIR_CUSTOM_DOUBLE_LOOPING:
				case DIR_CUSTOM_SINGLE_LOOPING:
					string += "Thermo Profile Set\t" + eswds.getThermoPath() + "\n";
					break;
				default:
					break;
			}
			string += "Number of Timesteps Before Exit\t" + eswds.getMaxTimesteps() + "\n";
			string += "Start Time (sec)\t" + eswds.getStartTime() + "\n";
			string += "Stop Time (sec)\t" + eswds.getStopTime() + "\n";
			string += "Include Weak Reactions\t" + (eswds.getIncludeWeak() ? "Yes" : "No") + "\n";
			string += "Include Screening\t" + (eswds.getIncludeScreening() ? "Yes" : "No") + "\n";
			string += "User Notes\t" + (eswds.getNotes().trim().equals("") ? "N/A" : eswds.getNotes().trim()) + "\n";
			string += "Selected Zones\t" + eswds.getZones() + "\n";
			
			string += "26Al State\t" 
					+ (eswds.getAl26Type().equals("STABLE") ? "only stable": "metastable + ground")  + "\n";
			string += "Initial Abundance Profile\t" + "PUBLIC/" + eswds.getInitAbundPath().substring(eswds.getInitAbundPath().lastIndexOf("/")+1) + "\n";
			string += "Sunet File\t" 
					+ "PUBLIC/" + eswds.getSunetPath().substring(eswds.getSunetPath().lastIndexOf("/")+1) 
					+ "\n";
			
			switch(eswds.getSimWorkflowMode()){
				case DIR_CUSTOM_DOUBLE_LOOPING:
					string += "Reaction Rate Library Directory\t" + eswds.getLibDir() + "\n";
					string += "Looping Type\tDouble Looping\n";
					break;
				case DIR_STANDARD:
					string += "Reaction Rate Library Directory\t" + eswds.getLibDir() + "\n";
					break;
				case DIR_CUSTOM_SINGLE_LOOPING:
					string += "Reaction Rate Library Directory\t" + eswds.getLibDir() + "\n";
					string += "Looping Type\tSingle Looping\n";
					break;
				case SINGLE_CUSTOM:
				case SINGLE_CUSTOM_SENS:
				case SINGLE_STANDARD:
				case SINGLE_STANDARD_SENS:
					string += "Reaction Rate Library\t" + eswds.getLibraryPath() + "\n";
					break;
			}
			
			switch(eswds.getSimWorkflowMode()){
				case SINGLE_STANDARD_SENS:
				case SINGLE_CUSTOM_SENS:
					string += "Selected Reaction Rate\t" + eswds.getReactionRate() + "\n";
					string += "Scale Factors\t" + eswds.getScaleFactors() + "\n";
					break;
				default:
					break;
			}
			string += "\n\n";
		}
		return string;
	}
	
	private String getInfoReport(){
	
		String string = "";
		string += "<html><body>";
		string += "<b>Element Synthesis Workflow Report</b><br><br>";
		string += "<table border=\"1\">";
		
		Iterator<String> itr = ds.getSimWorkMapSelected().keySet().iterator();
		int counter = 0;
		while(itr.hasNext()){
		
			ElementSimWorkDataStructure eswds = ds.getSimWorkMapSelected().get(itr.next());
			string += "<tr><td><b>Element Synthesis Workflow</b></td><td>" + eswds.getFolderType().name() + "/" + eswds.getName() + "</td></tr>";
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
			if(counter<ds.getSimWorkMapSelected().size()-1){
				string += "<tr><td></td><td></td></tr>";
				counter++;
			}
			
		}
		string += "</table></body></html>";
		return string;
	}
	
	public void setCurrentState(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	public void getCurrentState(){}
}

