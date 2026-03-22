package org.nucastrodata.element.elementviz.animator.sum;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.feature.SumDataStructure;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;
import org.nucastrodata.PrintfFormat;
import org.nucastrodata.dialogs.GeneralDialog;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class SumOutputPanel extends JPanel implements ActionListener{

	/** The ds. */
	private SumDataStructure ds;
	
	/** The pane. */
	private PrintableEditorPane pane;
	
	/** The print button. */
	private JButton submitButton, saveButtonText, saveButtonHTML
						, copyButton, printButton;
	
	/** The top label. */
	private WordWrapLabel topLabel;
	
	/** The parent. */
	private SumFrame parent;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The report label. */
	private JLabel reportLabel;
	
	/** The reason map. */
	private HashMap<Integer, String> reasonMap;
	
	public SumOutputPanel(SumDataStructure ds, SumFrame parent){
	
		this.parent = parent;
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.FILL
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Click <i>Submit Abundance "
				+ "Sum</i> to display these results with the Animator. Click <i>Close Abundance Summer</i> " 
				+ "to close the Abundance Summer and not display the results.</html>");
		
		reportLabel = new JLabel("Abundance Summer Report: ");
		reportLabel.setFont(Fonts.textFont);
		
		pane = new PrintableEditorPane();
		pane.setEditable(false);
		pane.setEditorKit(new HTMLEditorKit());
		sp = new JScrollPane(pane);
		sp.setPreferredSize(new Dimension(500, 135));
		
		submitButton = new JButton("Submit Abundance Sum");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		saveButtonHTML = new JButton("Save");
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
									, gap, TableLayoutConstants.PREFERRED};
		double[] rowButton = {10, TableLayoutConstants.PREFERRED, 10};
		
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(saveButtonHTML, "0, 1, c, c");
		buttonPanel.add(copyButton, "2, 1, c, c");
		buttonPanel.add(printButton, "4, 1, c, c");
		
		add(topLabel, "0, 1, 2, 1, f, c");
		add(reportLabel, "0, 3, l, c");
		add(sp, "0, 5, 2, 5, f, f");
		add(buttonPanel, "0, 7, 2, 7, c, c");
		add(submitButton, "0, 9, 2, 9, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==submitButton){
			Cina.elementVizFrame.elementVizAnimatorFrame.initializeAnimatorSum();
			String string = "The abundance sum you calculated has been submitted to and is now displayed in the Element Synthesis Animator.";
			GeneralDialog dialog = new GeneralDialog(parent, string, "Attention!");
			dialog.setVisible(true);
		}else if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), parent, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(pane.getText(), parent, Cina.cinaMainDataStructure);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
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
	public String getTextString(){
		String string = "";
		string += "Selected Simulation\t" + ds.getPath() + "\n";
		string += "Timestep min/max\t" 
					+ ds.getTimeMap().get(SumDataStructure.Time.TIME_STEP).get(0).intValue()
					+ "\t"
					+ ds.getTimeMap().get(SumDataStructure.Time.TIME_STEP).get(1).intValue()
					+ "\n";
		string += "Time (sec) min/max\t" 
					+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(SumDataStructure.Time.TIME_SEC).get(0))
					+ "\t"
					+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(SumDataStructure.Time.TIME_SEC).get(1))
					+ "\n";
		if(ds.getRange()==SumDataStructure.Range.DEFAULT){
			string += "Mass Range\tDefault (all masses)\n";
		}else if(ds.getRange()==SumDataStructure.Range.CUSTOM){
			string += "Mass Range\tCustom Range\n";
			string += "A min\t" + (int)(ds.getA().getX()) + "\n";
			string += "A max\t" + (int)(ds.getA().getY()) + "\n";
		}
		string += "Abundance Sum\t" + NumberFormats.getFormattedValueLong(ds.getSum()) + "\n";
		return string;
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param fullReport the new current state
	 */
	public void setCurrentState(){

		String string = "";
		string += "<html><body>";
		string += "<table border=\"1\">";
		string += "<tr><td colspan=\"3\"><b>Selected Simulation</b></td><td colspan=\"8\">" + ds.getPath() + "</td></tr>";
		string += "<tr><td colspan=\"3\"><b>Timestep min/max</b></td><td  colspan=\"4\">" 
					+ ds.getTimeMap().get(SumDataStructure.Time.TIME_STEP).get(0).intValue()
					+ "</td><td colspan=\"4\">"
					+ ds.getTimeMap().get(SumDataStructure.Time.TIME_STEP).get(1).intValue()
					+ "</td></tr>";
		string += "<tr><td colspan=\"3\"><b>Time (sec) min/max</b></td><td colspan=\"4\">" 
					+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(SumDataStructure.Time.TIME_SEC).get(0))
					+ "</td><td colspan=\"4\">"
					+ new PrintfFormat("%13.3E").sprintf(ds.getTimeMap().get(SumDataStructure.Time.TIME_SEC).get(1))
					+ "</td></tr>";
		if(ds.getRange()==SumDataStructure.Range.DEFAULT){
			string += "<tr><td colspan=\"3\"><b>Mass Range<b></td><td colspan=\"8\">Default (all masses)</td></tr>";
		}else if(ds.getRange()==SumDataStructure.Range.CUSTOM){
			string += "<tr><td colspan=\"3\"><b>Mass Range<b></td><td colspan=\"8\">Custom Range</td></tr>";
			string += "<tr><td colspan=\"3\"> </td><td colspan=\"2\">A min</td><td colspan=\"2\">" + (int)(ds.getA().getX()) + "</td>";
			string += "<td colspan=\"2\">A max</td><td colspan=\"2\">" + (int)(ds.getA().getY()) + "</td></tr>";
		}
		string += "<tr><td colspan=\"3\"><b>Abundance Sum</b></td><td colspan=\"8\">" + NumberFormats.getFormattedValueLong(ds.getSum()) + "</td></tr>";
		string += "</table>";
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

