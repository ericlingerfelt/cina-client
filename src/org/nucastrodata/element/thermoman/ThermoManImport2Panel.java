package org.nucastrodata.element.thermoman;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ThermoManDataStructure;
import org.nucastrodata.datastructure.util.ThermoProfileSetDataStructure;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.wizard.gui.WordWrapLabel;

import java.awt.event.*;
import info.clearthought.layout.*;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.awt.*;
import org.nucastrodata.Fonts;

public class ThermoManImport2Panel extends JPanel implements ActionListener{

	private WordWrapLabel topLabel;
	private ThermoManDataStructure ds;
	private MainDataStructure mds;
	private ThermoManFrame frame;
	private PrintableEditorPane textPane;
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;

	public ThermoManImport2Panel(MainDataStructure mds, ThermoManDataStructure ds, ThermoManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED, gap, 
						TableLayoutConstants.FILL, gap, 
						TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		topLabel = new WordWrapLabel();
		topLabel.setText("<b>Your set of thermodynamic profiles has been imported.</b>");
		
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

		add(topLabel, 		"0, 1, c, c");
		add(sp, 			"0, 3, f, f");
		add(buttonPanel, 	"0, 5, c, c");
	
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
		StringBuilder sb = new StringBuilder();
		sb.append("Thermodynamic Profile Report\n\n");
		ThermoProfileSetDataStructure tpsds = ds.getSetMapSelected().get(ds.getImportedThermoProfileSetDataStructure().getPath());
		sb.append("Thermodynamic Profile\t" + tpsds.getPath() + "\n");
		sb.append("Creation Date\t" + tpsds.getCreationDate() + "\n");
		sb.append("Notes\t" + tpsds.getDesc() + "\n");
		sb.append("Number of Profiles\t" + tpsds.getNumProfiles() + "\n");
		sb.append("Start Time\t" + tpsds.getStartTime() + "\n");
		sb.append("Stop Time\t" + tpsds.getStopTime() + "\n");
		sb.append("\n\n");
		return sb.toString();
	}
	
	private String getInfoReport(){
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		sb.append("<b>Thermodynamic Profile Report</b><br><br>");
		sb.append("<table border=\"1\">");
		ThermoProfileSetDataStructure tpsds = ds.getSetMapSelected().get(ds.getImportedThermoProfileSetDataStructure().getPath());
		sb.append("<tr><td><b>Thermodynamic Profile</b></td><td><b>" + tpsds.getPath() + "</b></td></tr>");
		sb.append("<tr><td><b>Creation Date</b></td><td>" + tpsds.getCreationDate() + "</td></tr>");
		sb.append("<tr><td><b>Description</b></td><td>" + tpsds.getDesc() + "</td></tr>");
		sb.append("<tr><td><b>Number of Profiles</b></td><td>" + tpsds.getNumProfiles() + "</td></tr>");
		sb.append("<tr><td><b>Start Time</b></td><td>" + tpsds.getStartTime() + "</td></tr>");
		sb.append("<tr><td><b>Stop Time</b></td><td>" + tpsds.getStopTime() + "</td></tr>");
		sb.append("</table><br>");
		return sb.toString();
	}
	
	public void setCurrentState(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	public void getCurrentState(){}
}

