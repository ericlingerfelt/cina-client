package org.nucastrodata;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.FeatureSetPanel;
import org.nucastrodata.IntroPanel;

import java.awt.event.*;
import info.clearthought.layout.*;
import java.util.*;


public class IntroPanel extends JPanel{
	
	JRadioButton RateGenRadioButton, RateParamRadioButton, RateManRadioButton, 
					RateLibManRadioButton, RateViewerRadioButton, ElementWorkManRadioButton, 
					ElementSynthRadioButton, ElementVizRadioButton, ElementManRadioButton, ThermoManRadioButton,
					DataEvalRadioButton, DataViewerRadioButton, DataManRadioButton, DataMassRadioButton,
					HelpRadioButton, AboutRadioButton, RegisterRadioButton;

	ButtonGroup dataButtonGroup, rateButtonGroup, elementButtonGroup, suiteButtonGroup, evalButtonGroup, repoButtonGroup; 
	JPanel leftPanel, rightPanel, topPanel;
	JLabel label1, label2;
	TreeMap<FeatureSet, FeatureSetPanel> featurePanelMap;

	enum FeatureSet {
		DATA, 
		RATE, 
		ELEMENT, 
		SUITE
	}
	
	/**
	 * Instantiates a new intro panel.
	 */
	public IntroPanel(){
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));
	
		featurePanelMap = new TreeMap<FeatureSet, FeatureSetPanel>();
		featurePanelMap.put(FeatureSet.DATA, new FeatureSetPanel("<html>Nuclear Data</html>", FeatureSet.DATA, this));
		featurePanelMap.put(FeatureSet.RATE, new FeatureSetPanel("<html>Reaction Rates and<p>Rate Libraries</html>", FeatureSet.RATE, this));
		featurePanelMap.put(FeatureSet.ELEMENT, new FeatureSetPanel("<html>Element Synthesis<p>Simulations</html>", FeatureSet.ELEMENT, this));
		featurePanelMap.put(FeatureSet.SUITE, new FeatureSetPanel("<html>Suite Information and<p>Registration</html>", FeatureSet.SUITE, this));
	
		dataButtonGroup = new ButtonGroup();
		rateButtonGroup = new ButtonGroup();
		elementButtonGroup = new ButtonGroup();
		suiteButtonGroup = new ButtonGroup();
		repoButtonGroup = new ButtonGroup();
		
		label1 = new JLabel("Computational Infrastructure");
		label1.setFont(Fonts.bigTitleFont);
		label2 = new JLabel("for Nuclear Astrophysics");
		label2.setFont(Fonts.bigTitleFont);
		
		DataMassRadioButton = new JRadioButton("Mass Model Evaluator", false);
		DataMassRadioButton.setEnabled(false);
		DataMassRadioButton.setFont(Fonts.textFont);

		DataEvalRadioButton = new JRadioButton("Nuclear Data Evaluator's Toolkit", false);
		DataEvalRadioButton.setEnabled(false);
		DataEvalRadioButton.setFont(Fonts.textFont);
		DataEvalRadioButton.setSelected(true);
		
		DataViewerRadioButton = new JRadioButton("Nuclear Data Viewer", false);
		DataViewerRadioButton.setEnabled(false);
		DataViewerRadioButton.setFont(Fonts.textFont);
		
		DataManRadioButton = new JRadioButton("Nuclear Data Manager", false);
		DataManRadioButton.setEnabled(false);
		DataManRadioButton.setFont(Fonts.textFont);
		
		dataButtonGroup.add(DataEvalRadioButton);
		dataButtonGroup.add(DataMassRadioButton);
		dataButtonGroup.add(DataViewerRadioButton);
		dataButtonGroup.add(DataManRadioButton);
		
		RateGenRadioButton = new JRadioButton("Rate Generator", false);
		RateGenRadioButton.setEnabled(false);
		RateGenRadioButton.setFont(Fonts.textFont);
		RateGenRadioButton.setSelected(true);
		
		RateParamRadioButton = new JRadioButton("Rate Parameterizer", false);
		RateParamRadioButton.setEnabled(false);
		RateParamRadioButton.setFont(Fonts.textFont);
		RateParamRadioButton.setSelected(true);
		
		RateManRadioButton = new JRadioButton("Rate Manager", false);
		RateManRadioButton.setEnabled(false);
		RateManRadioButton.setFont(Fonts.textFont);
				
		RateLibManRadioButton = new JRadioButton("Rate Library Manager", false);
		RateLibManRadioButton.setEnabled(false);
		RateLibManRadioButton.setFont(Fonts.textFont);
		
		RateViewerRadioButton = new JRadioButton("Rate Viewer", false);
		RateViewerRadioButton.setEnabled(false);
		RateViewerRadioButton.setFont(Fonts.textFont);
		
		rateButtonGroup.add(RateGenRadioButton);
		rateButtonGroup.add(RateParamRadioButton);
		rateButtonGroup.add(RateManRadioButton);
		rateButtonGroup.add(RateLibManRadioButton);
		rateButtonGroup.add(RateViewerRadioButton);
		
		ElementSynthRadioButton = new JRadioButton("Element Synthesis Simulator", false);
		ElementSynthRadioButton.setEnabled(false);
		ElementSynthRadioButton.setFont(Fonts.textFont);
		ElementSynthRadioButton.setSelected(true);
		
		ElementVizRadioButton = new JRadioButton("Element Synthesis Visualizer", false);
		ElementVizRadioButton.setEnabled(false);
		ElementVizRadioButton.setFont(Fonts.textFont);
		
		ElementManRadioButton = new JRadioButton("Element Synthesis Manager", false);
		ElementManRadioButton.setEnabled(false);
		ElementManRadioButton.setFont(Fonts.textFont);
		
		ElementWorkManRadioButton = new JRadioButton("Element Synthesis Workflow Manager", false);
		ElementWorkManRadioButton.setEnabled(false);
		ElementWorkManRadioButton.setFont(Fonts.textFont);
		
		ThermoManRadioButton = new JRadioButton("Thermodynamic Profile Manager", false);
		ThermoManRadioButton.setEnabled(false);
		ElementManRadioButton.setFont(Fonts.textFont);
		
		elementButtonGroup.add(ElementSynthRadioButton);
		elementButtonGroup.add(ElementVizRadioButton);
		elementButtonGroup.add(ElementManRadioButton);
		elementButtonGroup.add(ElementWorkManRadioButton);
		elementButtonGroup.add(ThermoManRadioButton);
		
		AboutRadioButton = new JRadioButton("General Information", false);
		AboutRadioButton.setEnabled(false);
		AboutRadioButton.setFont(Fonts.textFont);
		AboutRadioButton.setSelected(true);
		
		HelpRadioButton = new JRadioButton("Help", false);
		HelpRadioButton.setEnabled(false);
		HelpRadioButton.setFont(Fonts.textFont);
		
		RegisterRadioButton = new JRadioButton("REGISTER!", false);
		RegisterRadioButton.setEnabled(false);
		RegisterRadioButton.setFont(Fonts.textFont);
		
		suiteButtonGroup.add(AboutRadioButton);
		suiteButtonGroup.add(HelpRadioButton);
		suiteButtonGroup.add(RegisterRadioButton);	
		
		topPanel = new JPanel();
		double[] columnTop = {TableLayoutConstants.PREFERRED};
		double[] rowTop = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		topPanel.setLayout(new TableLayout(columnTop, rowTop));
		topPanel.add(label1, "0, 0, c, c");
		topPanel.add(label2, "0, 2, c, c");

		leftPanel = new JPanel();
		double[] columnLeft = {TableLayoutConstants.FILL};
		double[] rowLeft = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		leftPanel.setLayout(new TableLayout(columnLeft, rowLeft));
		int counter = 0;
		Iterator<FeatureSetPanel> itr = featurePanelMap.values().iterator();
		while(itr.hasNext()){
			leftPanel.add(itr.next(), "0, " + counter + ", l, c");
			counter +=2;
		}
		
		rightPanel = new JPanel();
		double[] columnRight = {TableLayoutConstants.FILL};
		double[] rowRight = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		rightPanel.setLayout(new TableLayout(columnRight, rowRight));
		
		add(topPanel, "1, 1, 3, 1, c, c");
		add(leftPanel, "1, 3, c, c");
		add(rightPanel, "3, 3, l, t");
	}
	
	/**
	 * Initialize.
	 */
	public void initialize(){
		DataEvalRadioButton.setSelected(true);
		RateGenRadioButton.setSelected(true);
		ElementSynthRadioButton.setSelected(true);
		AboutRadioButton.setSelected(true);
		setRightPanel();
		setLabelColors();
	}
	
	/**
	 * Sets the right panel.
	 */
	public void setRightPanel(){
		rightPanel.removeAll();
		String string = Cina.cinaMainDataStructure.getCurrentFeatureSet();
		if(string.equals("data")){
			rightPanel.add(DataEvalRadioButton, 	"0, 0, l, c");
			rightPanel.add(DataMassRadioButton, 	"0, 2, l, c");					
			rightPanel.add(DataManRadioButton, 		"0, 4, l, c");					
			rightPanel.add(DataViewerRadioButton, 	"0, 6, l, c");	
			rightPanel.add(new JLabel(), 			"0, 8, l, c");
		}else if(string.equals("rate")){		
			rightPanel.add(RateGenRadioButton, 		"0, 0, l, c");	
			rightPanel.add(RateParamRadioButton, 	"0, 2, l, c");					
			rightPanel.add(RateManRadioButton, 		"0, 4, l, c");								
			rightPanel.add(RateLibManRadioButton, 	"0, 6, l, c");					
			rightPanel.add(RateViewerRadioButton,	"0, 8, l, c");		
		}else if(string.equals("element")){		
			rightPanel.add(ElementSynthRadioButton, 	"0, 0, l, c");					
			rightPanel.add(ElementManRadioButton, 		"0, 2, l, c");	
			if (Cina.cinaMainDataStructure.isMasterUser()){
				rightPanel.add(ElementWorkManRadioButton, 	"0, 4, l, c");	
				rightPanel.add(ThermoManRadioButton, 		"0, 6, l, c");	
				rightPanel.add(ElementVizRadioButton, 		"0, 8, l, c");	
			}else{	
				rightPanel.add(ThermoManRadioButton, 	"0, 4, l, c");	
				rightPanel.add(ElementVizRadioButton, 	"0, 6, l, c");
				rightPanel.add(new JLabel(), 			"0, 8, l, c");
			}
		}else if(string.equals("suite")){		
			rightPanel.add(AboutRadioButton, 		"0, 0, l, c");				
			rightPanel.add(HelpRadioButton, 		"0, 2, l, c");	
			rightPanel.add(RegisterRadioButton, 	"0, 4, l, c");
			rightPanel.add(new JLabel(), 			"0, 6, l, c");
			rightPanel.add(new JLabel(), 			"0, 8, l, c");
		}
		validate();
		repaint();
	}

	public void setLabelColors(){
		Iterator<FeatureSetPanel> itr = featurePanelMap.values().iterator();
		while(itr.hasNext()){
			itr.next().setColor(ColorFormat.frontColor);
		}
		featurePanelMap.get(FeatureSet.valueOf(Cina.cinaMainDataStructure.getCurrentFeatureSet().toUpperCase())).setColor(Color.cyan);
	}
	
}

class FeatureSetPanel extends JPanel implements MouseListener{
	
	private JLabel label;
	private IntroPanel.FeatureSet set;
	private IntroPanel parent;
	
	public FeatureSetPanel(String labelString, IntroPanel.FeatureSet set, IntroPanel parent){
		
		this.set = set;
		this.parent = parent;
		label = new JLabel(labelString);
		add(label);
		addMouseListener(this);
	}
	
	public void setColor(Color color){
		label.setForeground(color);
	}
	
	public void mouseEntered(MouseEvent me){
		Cina.cinaMainDataStructure.setCurrentFeatureSet(set.name().toLowerCase());
		parent.setRightPanel();
		parent.setLabelColors();
	}
	
	public void mouseClicked(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	
}


