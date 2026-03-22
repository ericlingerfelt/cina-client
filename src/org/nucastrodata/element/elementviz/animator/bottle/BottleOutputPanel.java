package org.nucastrodata.element.elementviz.animator.bottle;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.datastructure.util.Reaction;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.element.elementviz.animator.bottle.BottleFrame;
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
 * The Class BottleOutputPanel.
 */
public class BottleOutputPanel extends JPanel implements ActionListener{

	/** The ds. */
	private BottleDataStructure ds;
	
	/** The pane. */
	private PrintableEditorPane pane;
	
	/** The print button. */
	private JButton submitButton, saveButtonText, saveButtonHTML
						, copyButton, printButton;
	
	/** The box. */
	private JCheckBox box;
	
	/** The top label. */
	private WordWrapLabel topLabel;
	
	/** The parent. */
	private BottleFrame parent;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The report label. */
	private JLabel reportLabel;
	
	/** The reason map. */
	private HashMap<Integer, String> reasonMap;
	
	/**
	 * Instantiates a new bottle output panel.
	 *
	 * @param ds the ds
	 * @param parent the parent
	 */
	public BottleOutputPanel(BottleDataStructure ds, BottleFrame parent){
	
		this.parent = parent;
		this.ds = ds;
		
		reasonMap = new HashMap<Integer, String>();
		reasonMap.put(3, "Flux Separation Test failed");
		reasonMap.put(4, "High Flux Threshold Test failed [too many reactions]");
		reasonMap.put(6, "Low Flux Threshold Test failed [too few reactions]");
		reasonMap.put(7, "Failed both Flux Separation Test and High Flux Threshold Test [too many reactions]");
		reasonMap.put(8, "Failed both Flux Separation Test and High Flux Threshold Test [too few reactions]");
		reasonMap.put(9, "Failed both Flux Separation Test and Low Flux Threshold Test [too few reactions]");
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.FILL
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Below is a list of reactions that are bottlenecks for the synthesis of nuclei "
				+ "with masses greater than or equal to the Bottleneck Mass. Click <i>Submit Bottleneck "
				+ "Reactions</i> to visualize these results with the Animator. Click <i>Close Bottleneck Reaction Finder</i> to close the Bottleneck Reaction Finder and not submit the results. "
				+ "Check <i>View Detailed Report</i> to view the user input as well as the output of the Finder.</html>");
		
		reportLabel = new JLabel("Bottleneck Reaction Finder Report: ");
		reportLabel.setFont(Fonts.textFont);
		
		pane = new PrintableEditorPane();
		pane.setEditable(false);
		pane.setEditorKit(new HTMLEditorKit());
		sp = new JScrollPane(pane);
		sp.setPreferredSize(new Dimension(500, 135));
		
		submitButton = new JButton("Submit Bottleneck Reactions");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		box = new JCheckBox("View Detailed Report", false);
		box.setFont(Fonts.textFont);
		box.addActionListener(this);
		
		saveButtonHTML = new JButton("Save as HTML");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		saveButtonText = new JButton("Save as Text");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);
		
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
		
		add(topLabel, "0, 1, 2, 1, f, c");
		add(reportLabel, "0, 3, l, c");
		add(box, "2, 3, r, c");
		add(sp, "0, 5, 2, 5, f, f");
		add(buttonPanel, "0, 7, 2, 7, c, c");
		add(submitButton, "0, 9, 2, 9, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==submitButton){
			Cina.elementVizFrame.elementVizAnimatorFrame.initializeAnimatorBottle();
			String string = "The bottleneck reactions you found have been submitted to and are now available in the Element Synthesis Animator.";
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
						+ ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(0).intValue()
						+ "\t"
						+ ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(1).intValue()
						+ "\n";
			string += "Time (sec) min/max\t" 
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(0))
						+ "\t"
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(1))
						+ "\n";
			if(ds.getRange()==BottleDataStructure.Range.DEFAULT){
				string += "Mass Range\tDefault (all masses)\n";
			}else if(ds.getRange()==BottleDataStructure.Range.CUSTOM){
				string += "Mass Range\tCustom Range\n";
				string += "A min\t" + (int)(ds.getA().getX()) + "\n";
				string += "A max\t" + (int)(ds.getA().getY()) + "\n";
			}
			string += "Low Flux Threshold\t" + ds.getLowThreshold() + "\n";
			string += "High Flux Threshold\t" + ds.getHighThreshold() + "\n";
			string += "Flux Separation Factor\t" + ds.getSepFactor() + "\n";
		}
		string += "\n\n";
		if(ds.getListMajor().size()>0 || ds.getListMinor().size()>0){
			string += "Bottleneck Mass\tReaction\tDecision\n";
			for(Reaction r: ds.getListMajor()){
				string += r.getPointOut().getA() 
							+ "\t" + r.toString() 
							+ "\tMajor Bottleneck\n";
			}
			
			for(Reaction r: ds.getListMinor()){
				string += r.getPointOut().getA() 
							+ "\t" + r.toString() 
							+ "\tMinor Bottleneck\n";
			}
		}
		
		if(fullReport){
			string += "\n\n";
			string += "Mass\tReason for no bottleneck reaction\n";
			Iterator<Integer> itr  = ds.getFailedMapList().keySet().iterator();
			while(itr.hasNext()){
				int reason = itr.next();
				ArrayList<Integer> list = ds.getFailedMapList().get(reason);
				Collections.sort(list);
				String massString = "";
				for(Integer mass: list){
					massString += mass + " ";
				}
				string += massString + "\t" + reasonMap.get(reason) + "\n";
			}
		}

		return string;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param fullReport the new current state
	 */
	public void setCurrentState(boolean fullReport){

		String string = "";
		string += "<html><body>";
		
		if(fullReport){
			string += "<table border=\"1\">";
			string += "<tr><td colspan=\"3\"><b>Selected Simulation</b></td><td colspan=\"8\">" + ds.getPath() + "</td></tr>";
			string += "<tr><td colspan=\"3\"><b>Timestep min/max</b></td><td  colspan=\"4\">" 
						+ ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(0).intValue()
						+ "</td><td colspan=\"4\">"
						+ ds.getTimeMap().get(BottleDataStructure.Time.TIME_STEP).get(1).intValue()
						+ "</td></tr>";
			string += "<tr><td colspan=\"3\"><b>Time (sec) min/max</b></td><td colspan=\"4\">" 
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(0))
						+ "</td><td colspan=\"4\">"
						+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(BottleDataStructure.Time.TIME_SEC).get(1))
						+ "</td></tr>";
			if(ds.getRange()==BottleDataStructure.Range.DEFAULT){
				string += "<tr><td colspan=\"3\"><b>Mass Range<b></td><td colspan=\"8\">Default (all masses)</td></tr>";
			}else if(ds.getRange()==BottleDataStructure.Range.CUSTOM){
				string += "<tr><td colspan=\"3\"><b>Mass Range<b></td><td colspan=\"8\">Custom Range</td></tr>";
				string += "<tr><td colspan=\"3\"> </td><td colspan=\"2\">A min</td><td colspan=\"2\">" + (int)(ds.getA().getX()) + "</td>";
				string += "<td colspan=\"2\">A max</td><td colspan=\"2\">" + (int)(ds.getA().getY()) + "</td></tr>";
			}
			string += "<tr><td colspan=\"3\"><b>Low Flux Threshold</b></td><td colspan=\"8\">" + ds.getLowThreshold() + "</td></tr>";
			string += "<tr><td colspan=\"3\"><b>High Flux Threshold</b></td><td colspan=\"8\">" + ds.getHighThreshold() + "</td></tr>";
			string += "<tr><td colspan=\"3\"><b>Flux Separation Factor</b></td><td colspan=\"8\">" + ds.getSepFactor() + "</td></tr>";
			string += "</table>";
		}
		string += "<br>";
		if(ds.getListMajor().size()>0 || ds.getListMinor().size()>0){
			string += "<table border=\"1\">";
			string += "<tr><td><b>Bottleneck Mass</b></td><td><b>Reaction</b></td><td><b>Decision</b></td></tr>";
			for(Reaction r: ds.getListMajor()){
				string += "<tr><td>" + r.getPointOut().getA() 
							+ "</td><td>" + r.toString() 
							+ "</td><td>Major Bottleneck</td></tr>";
			}
			
			for(Reaction r: ds.getListMinor()){
				string += "<tr><td>" + r.getPointOut().getA() 
							+ "</td><td>" + r.toString() 
							+ "</td><td>Minor Bottleneck</td></tr>";
			}
			string += "</table>";
		}
		
		if(fullReport){
			string += "<br>";
			string += "<table border=\"1\">";
			string += "<tr><td><b>Mass</b></td><td><b>Reason for no bottleneck reaction</b></td></tr>";
			Iterator<Integer> itr  = ds.getFailedMapList().keySet().iterator();
			while(itr.hasNext()){
				int reason = itr.next();
				ArrayList<Integer> list = ds.getFailedMapList().get(reason);
				Collections.sort(list);
				String massString = "";
				for(Integer mass: list){
					massString += mass + "<br>";
				}
				string += "<tr><td>" + massString + "</td><td valign=\"top\">" + reasonMap.get(reason) + "</td></tr>";
			}
			string += "</table>";
		}
		
		string += "</body></html>";
		pane.setText(string);
		pane.setCaretPosition(0);

	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
}

