package org.nucastrodata.element.elementviz.animator.flux;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.datastructure.feature.FluxDataStructure.Time;
import org.nucastrodata.export.copy.TextCopier;
import org.nucastrodata.export.save.TextSaver;
import org.nucastrodata.export.print.PrintableEditorPane;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.NumberFormats;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

public class FluxOutputPanel extends JPanel implements ActionListener{

	/** The ds. */
	private FluxDataStructure ds;
	
	/** The pane. */
	private PrintableEditorPane pane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/** The top label. */
	private JLabel topLabel;
	
	/** The parent. */
	private FluxFrame parent;
	
	/** The button panel. */
	private JPanel buttonPanel;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The report label. */
	private JLabel reportLabel;
	
	private TreeMap<Integer, ArrayList<ArrayList<Number>>> map;
	
	public FluxOutputPanel(FluxDataStructure ds, FluxFrame parent){
	
		this.parent = parent;
		this.ds = ds;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.FILL
							, 5, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));
		
		topLabel = new JLabel();
		topLabel.setText("<html>Below is a table fo reaction flux values for the reactions and timesteps you requested.</html>");
		
		reportLabel = new JLabel("Table of Reaction Flux Values");
		reportLabel.setFont(Fonts.textFont);
		
		pane = new PrintableEditorPane();
		pane.setEditable(false);
		pane.setEditorKit(new HTMLEditorKit());
		sp = new JScrollPane(pane);
		sp.setPreferredSize(new Dimension(500, 135));
		
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
		
		add(topLabel,    "0, 1, c, c");
		add(reportLabel, "0, 3, l, c");
		add(sp,          "0, 5, f, f");
		add(buttonPanel, "0, 7, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
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
		string += "Selected Simulation = " + ds.getPath() + "\n\n";
		string += String.format("%1$6s%2$6s%3$6s%4$6s%5$14s", "Z In", "A In", "Z Out", "A Out", "Reaction Flux");
		string += "\n\n";
		Iterator<Integer> itrTime = map.keySet().iterator();
		while(itrTime.hasNext()){
			int timestep = itrTime.next();
			string += "Timestep = " + timestep + "\n";
			ArrayList<ArrayList<Number>> reactionList = map.get(timestep);
			for(ArrayList<Number> list: reactionList){
				string += String.format("%1$6d%2$6d%3$6d%4$6d", list.get(0), list.get(1), list.get(2), list.get(3));
				string += " " + NumberFormats.getFormattedValueLong((Double)(list.get(5))) + "\n";
			}
			string += "\n";
		}
		string += "Sum Over Timestep " + map.firstKey() + " to " + map.lastKey() + "\n";
		ArrayList<ArrayList<Number>> sumList = new ArrayList<ArrayList<Number>>();
		ArrayList<ArrayList<Number>> reactionList = map.get(map.firstKey());
		sumList.addAll(reactionList);
		
		for(ArrayList<Number> list: sumList){
			list.set(5, 0);
		}
		
		itrTime = map.keySet().iterator();
		while(itrTime.hasNext()){
			int timestep = itrTime.next();
			reactionList = map.get(timestep);
			for(int i=0; i<reactionList.size(); i++){
				sumList.get(i).set(5, sumList.get(i).get(5).doubleValue() + reactionList.get(i).get(5).doubleValue());
			}
		}
		
		for(ArrayList<Number> list: sumList){
			string += String.format("%1$6d%2$6d%3$6d%4$6d", list.get(0), list.get(1), list.get(2), list.get(3));
			string += " " + NumberFormats.getFormattedValueLong((Double)(list.get(5))) + "\n";
		}
		return string;
	}
	
	public String getHTMLString(){
		StringBuilder string = new StringBuilder();
		string.append("<html><body>");
		string.append("<table border=\"1\">");
		string.append("<tr><td colspan=\"4\"><b>Selected Simulation</b></td><td colspan=\"1\">" + ds.getPath() + "</td></tr>");
		string.append("<tr><td><b>Z In</b></td><td><b>A In</b></td><td><b>Z Out</b></td><td><b>A Out</b></td><td><b>Reaction Flux</b></td></tr>");
		Iterator<Integer> itrTime = map.keySet().iterator();
		while(itrTime.hasNext()){
			int timestep = itrTime.next();
			string.append("<tr><td colspan=\"4\">Timestep</td><td colspan=\"1\">" + timestep + "</td></tr>");
			ArrayList<ArrayList<Number>> reactionList = map.get(timestep);
			for(ArrayList<Number> list: reactionList){
				string.append("<tr><td>" 
							+ list.get(0) + "</td><td>" 
							+ list.get(1) + "</td><td>" 
							+ list.get(2) + "</td><td>" 
							+ list.get(3) + "</td><td>" 
							+ NumberFormats.getFormattedValueLong((Double)(list.get(5))) + "</td></tr>");
			}
		}
		string.append("<tr><td colspan=\"5\">Sum Over Timestep " + map.firstKey() + " to " + map.lastKey() + "</td></tr>");
		ArrayList<ArrayList<Number>> sumList = new ArrayList<ArrayList<Number>>();
		ArrayList<ArrayList<Number>> reactionList = map.get(map.firstKey());
		sumList.addAll(reactionList);
		
		for(ArrayList<Number> list: sumList){
			list.set(5, 0);
		}
		
		itrTime = map.keySet().iterator();
		while(itrTime.hasNext()){
			int timestep = itrTime.next();
			reactionList = map.get(timestep);
			for(int i=0; i<reactionList.size(); i++){
				sumList.get(i).set(5, sumList.get(i).get(5).doubleValue() + reactionList.get(i).get(5).doubleValue());
			}
		}
		
		for(ArrayList<Number> list: sumList){
			string.append("<tr><td>" 
						+ list.get(0) + "</td><td>" 
						+ list.get(1) + "</td><td>" 
						+ list.get(2) + "</td><td>" 
						+ list.get(3) + "</td><td>" 
						+ NumberFormats.getFormattedValueLong((Double)(list.get(5))) + "</td></tr>");
		}
		
		string.append("</table></body></html>");
		return string.toString();
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param fullReport the new current state
	 */
	public void setCurrentState(){
		pane.setText(getHTMLString());
		pane.setCaretPosition(0);
	}
	
	public boolean goodReactions(){
		ArrayList<ArrayList<Integer>> reactionList = new ArrayList<ArrayList<Integer>>();
		String s = ds.getReactions();
		String[] lines = s.split("\n");
		try{
			for(String line: lines){
				StringTokenizer st = new StringTokenizer(line);
				int zIn = Integer.valueOf(st.nextToken());
				int aIn = Integer.valueOf(st.nextToken());
				int zOut = Integer.valueOf(st.nextToken());
				int aOut = Integer.valueOf(st.nextToken());
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(zIn);
				list.add(aIn);
				list.add(zOut);
				list.add(aOut);
				list.add(getReactionMapIndex(zIn, aIn, zOut, aOut));
				reactionList.add(list);
			}
		}catch(Exception e){
			return false;
		}

		int timestepMin = ds.getTimeMap().get(Time.TIME_STEP).get(0).intValue();
		int timestepMax = ds.getTimeMap().get(Time.TIME_STEP).get(1).intValue();
		map = new TreeMap<Integer, ArrayList<ArrayList<Number>>>();
		
		for(int i=timestepMin; i<=timestepMax; i++){
			ArrayList<ArrayList<Number>> timeList = new ArrayList<ArrayList<Number>>();
			for(ArrayList<Integer> list: reactionList){
				ArrayList<Number> tempList = new ArrayList<Number>();
				double flux = getFluxValue(list.get(4), i);
				tempList.addAll(list);
				tempList.add(flux);
				timeList.add(tempList);
			}
			map.put(i, timeList);
		}
		
		return true;
	}
	
	private int getReactionMapIndex(int zIn, int aIn, int zOut, int aOut){
		int inIndex = 0;
		int outIndex = 0;
		int reactionIndex = 0;
		
		foundIsoIn:
		for(int i=0; i<ds.getSim().getZAMapArray().length; i++){
			
			if((int)(ds.getSim().getZAMapArray()[i].getX())==zIn
					&& (int)(ds.getSim().getZAMapArray()[i].getY())==aIn){
			
				inIndex = i;
				break foundIsoIn;
			
			}
			
		}
		
		foundIsoOut:
		for(int i=0; i<ds.getSim().getZAMapArray().length; i++){
			
			if((int)(ds.getSim().getZAMapArray()[i].getX())==zOut
					&& (int)(ds.getSim().getZAMapArray()[i].getY())==aOut){
			
				outIndex = i;
				break foundIsoOut;
			
			}
			
		}
		
		foundReaction:
		for(int i=0; i<ds.getSim().getReactionMapArray().length; i++){
		
			if((int)(ds.getSim().getReactionMapArray()[i].getX())==inIndex
					&& (int)(ds.getSim().getReactionMapArray()[i].getY())==outIndex){
			
				reactionIndex = i;
				break foundReaction;
			
			}
		
		}
		
		return reactionIndex;
	}
	
	private double getFluxValue(int index, int step){
		double fluxValue = 0.0;
		
		
		foundFlux:
		for(int i=0; i<ds.getSim().getFluxTimestepDataStructureArray()[step].getIndexArray().length; i++){
		
			if(ds.getSim().getFluxTimestepDataStructureArray()[step].getIndexArray()[i]==index){
			
				fluxValue = ds.getSim().getFluxTimestepDataStructureArray()[step].getFluxArray()[i];
				break foundFlux;
			
			}
		
		}
		
		return fluxValue;
	
	}
	
	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){
		
	}
	
}
