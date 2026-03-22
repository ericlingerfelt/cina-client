package org.nucastrodata.element.elementviz.animator.wait;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.WaitingPointDataStructure;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.PrintfFormat;
import org.nucastrodata.dialogs.GeneralDialog;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;


/**
 * The Class WaitingOutputPanel.
 */
public class WaitingOutputPanel extends JPanel implements ActionListener{

	/** The ds. */
	private WaitingDataStructure ds;
	
	/** The pane. */
	private PrintableEditorPane pane;
	
	/** The print button. */
	private JButton submitButton, saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/** The box. */
	private JCheckBox box;
	
	/** The top label. */
	private JLabel topLabel;
	
	/** The parent. */
	private WaitingFrame parent;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The report label. */
	private JLabel reportLabel;
	
	/**
	 * Instantiates a new waiting output panel.
	 *
	 * @param ds the ds
	 * @param parent the parent
	 */
	public WaitingOutputPanel(WaitingDataStructure ds, WaitingFrame parent){
	
		this.parent = parent;
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		topLabel = new JLabel();
		
		reportLabel = new JLabel("Waiting Point Finder Report: ");
		reportLabel.setFont(Fonts.textFont);
		
		pane = new PrintableEditorPane();
		pane.setEditable(false);
		pane.setEditorKit(new HTMLEditorKit());
		sp = new JScrollPane(pane);
		sp.setPreferredSize(new Dimension(500, 135));
		
		submitButton = new JButton("Submit Waiting Points");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		box = new JCheckBox("View Detailed Report", false);
		box.setFont(Fonts.textFont);
		box.addActionListener(this);
		
		saveButtonText = new JButton("Save as Text");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);
		
		saveButtonHTML = new JButton("Save as HTML");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		buttonPanel  = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED
									, gap, TableLayoutConstants.PREFERRED};
		double[] rowButton = {10, TableLayoutConstants.PREFERRED, 10};
		
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(saveButtonText, "0, 1, c, c");
		buttonPanel.add(saveButtonHTML, "2, 1, c, c");
		buttonPanel.add(copyButton, "4, 1, c, c");
		buttonPanel.add(printButton, "6, 1, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==submitButton){
			Cina.elementVizFrame.elementVizAnimatorFrame.initializeAnimatorWaiting();
			String string = "The waiting points you found have been submitted to and are now available in the Element Synthesis Animator.";
			GeneralDialog dialog = new GeneralDialog(parent, string, "Attention!");
			dialog.setVisible(true);
		}else if(ae.getSource()==box){
			setCurrentState(box.isSelected());
		}else if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(box.isSelected()), parent, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(pane.getText(), parent, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString(box.isSelected()));
		}else if(ae.getSource()==printButton){
			pane.print();
		}
	}
	
	/**
	 * Gets the text string.
	 *
	 * @param fullReport the full report
	 * @return the text string
	 */
	public String getTextString(boolean fullReport){
		String string = "";
		
		if(fullReport){
			string += "Selected Simulation\t" + ds.getPath() + "\n";
			string += "Timestep min/max\t" 
						+ ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(0).intValue()
						+ "\t"
						+ ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(1).intValue()
						+ "\n";
			string += "Time (sec) min/max\t" 
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(0))
						+ "\t"
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(1))
						+ "\n";
			if(ds.getRange()==WaitingDataStructure.Range.DEFAULT){
				string += "Nuclei Range\tDefault (all isotopes)\n";
			}else if(ds.getRange()==WaitingDataStructure.Range.CUSTOM){
				string += "Nuclei Range\tCustom Range\n";
				string += "Z min\t" + (int)(ds.getZ().getX());
				string += "Z max\t" + (int)(ds.getZ().getY());
				string += "N min\t" + (int)(ds.getN().getX());
				string += "N max\t" + (int)(ds.getN().getY()) + "\n";
			}
			string += "<tr><td colspan=\"3\"><b>Min Abundance</b></td><td colspan=\"8\">" + ds.getAbund() + "</td>\n";
			string += "Min Effective Lifetime\t" + ds.getEffLifetime() + "\n";
			string += "Min Ratio of Effective Lifetime\t" + ds.getRatio() + "\n";
		}
		
		if(fullReport){
			string += "Isotope\tZ\tN\tDecision\tRT1\tRT2\tRT3\tRT4\tAT1\t\tAT3\n";
		}else{
			string += "Isotope\tZ\tN\n";
		}
		TreeMap<IsotopePoint, WaitingPointDataStructure> map = ds.getPointMap().get(WaitingDataStructure.PointType.YES);
		Iterator<IsotopePoint> itr = map.keySet().iterator();
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			string += ip.getA()
						+ MainDataStructure.getElementSymbol(ip.getZ())  
						+ "\t";
			string += ip.getZ() + "\t";
			string += (ip.getA()-ip.getZ()) + "\t";
			if(fullReport){
				WaitingPointDataStructure wpds = map.get(ip);
				string += wpds.getDecision() + "\t";
				for(Double test: wpds.getFlagList()){
					string += test + "\t";
				}
			}
			string += "\n";
		}
		return string;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param fullReport the new current state
	 */
	public void setCurrentState(boolean fullReport){
		
		removeAll();
		
		if(ds.getPointMap()==null){
			topLabel.setText("No waiting points were found using your selections.");
			add(topLabel, "0, 1, 2, 1, c, c");
		}else{
			String string = "";
			topLabel.setText("<html>Below is a list of possible waiting points. Click <i>Submit Waiting Points</i> to close the Waiting Point "
					+ "<p>Finder and visualize these results with the Animator. "
					+ "Click <i>Close Waiting Point Finder</i> to close<p>the Waiting Point Finder and not submit the results. "
					+ "Check <i>View Detailed Report</i> to view the user<p>input as well as the output of the Finder.</html>");
			add(topLabel, "0, 1, 2, 1, c, c");
			add(reportLabel, "0, 3, l, c");
			add(box, "2, 3, r, c");
			add(sp, "0, 5, 2, 5, f, f");
			add(buttonPanel, "0, 7, 2, 7, c, c");
			add(submitButton, "0, 9, 2, 9, c, c");
			
			string += "<html><body>";
			string += "<table border=\"1\">";
			
			if(fullReport){
				string += "<tr><td colspan=\"3\"><b>Selected Simulation</b></td><td colspan=\"8\">" + ds.getPath() + "</td></tr>";
				string += "<tr><td colspan=\"3\"><b>Timestep min/max</b></td><td  colspan=\"4\">" 
							+ ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(0).intValue()
							+ "</td><td colspan=\"4\">"
							+ ds.getTimeMap().get(WaitingDataStructure.Time.TIME_STEP).get(1).intValue()
							+ "</td></tr>";
				string += "<tr><td colspan=\"3\"><b>Time (sec) min/max</b></td><td colspan=\"4\">" 
							+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(0))
							+ "</td><td colspan=\"4\">"
							+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(WaitingDataStructure.Time.TIME_SEC).get(1))
							+ "</td></tr>";
				if(ds.getRange()==WaitingDataStructure.Range.DEFAULT){
					string += "<tr><td colspan=\"3\"><b>Nuclei Range<b></td><td colspan=\"8\">Default (all isotopes)</td></tr>";
				}else if(ds.getRange()==WaitingDataStructure.Range.CUSTOM){
					string += "<tr><td colspan=\"3\"><b>Nuclei Range<b></td><td colspan=\"8\">Custom Range</td></tr>";
					string += "<tr><td colspan=\"3\"> </td><td>Z min</td><td>" + (int)(ds.getZ().getX()) + "</td>";
					string += "<td>Z max</td><td>" + (int)(ds.getZ().getY()) + "</td>";
					string += "<td>N min</td><td>" + (int)(ds.getN().getX()) + "</td>";
					string += "<td>N max</td><td>" + (int)(ds.getN().getY()) + "</td></tr>";
				}
				string += "<tr><td colspan=\"3\"><b>Min Abundance</b></td><td colspan=\"8\">" + ds.getAbund() + "</td></tr>";
				string += "<tr><td colspan=\"3\"><b>Min Effective Lifetime</b></td><td colspan=\"8\">" + ds.getEffLifetime() + "</td></tr>";
				string += "<tr><td colspan=\"3\"><b>Min Ratio of Effective Lifetime</b></td><td colspan=\"8\">" + ds.getRatio() + "</td></tr>";
			}
			
			if(fullReport){
				string += "<tr><td><b>Isotope</b></td><td><b>Z</b></td><td><b>N</b></td><td><b>Decision</b></td>"
							+"<td><b>RT1</b></td><td><b>RT2</b></td><td><b>RT3</b></td><td><b>RT4</b></td>"
							+"<td><b>AT1</b></td><td><b>AT2</b></td><td><b>AT3</b></td></tr>";
			}else{
				string += "<tr><td><b>Isotope</b></td><td><b>Z</b></td><td><b>N</b></td></tr>";
			}
			TreeMap<IsotopePoint, WaitingPointDataStructure> map = ds.getPointMap().get(WaitingDataStructure.PointType.YES);
			Iterator<IsotopePoint> itr = map.keySet().iterator();
			while(itr.hasNext()){
				IsotopePoint ip = itr.next();
				string += "<tr>";
				string += "<td>" 
							+ ip.getA()
							+ MainDataStructure.getElementSymbol(ip.getZ())  
							+ "</td>";
				string += "<td>" + ip.getZ() + "</td>";
				string += "<td>" + (ip.getA()-ip.getZ()) + "</td>";
				if(fullReport){
					WaitingPointDataStructure wpds = map.get(ip);
					string += "<td>" + wpds.getDecision() + "</td>";
					for(Double test: wpds.getFlagList()){
						string += "<td>" + test + "</td>";
					}
				}
				string += "</tr>";
			}
			string += "</table></body></html>";
			pane.setText(string);
			pane.setCaretPosition(0);
		}
		
		validate();
		repaint();
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
}
