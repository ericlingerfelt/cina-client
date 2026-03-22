package org.nucastrodata.element.elementsynth;

import info.clearthought.layout.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.tree.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.ElementSynthDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkDataStructure;
import org.nucastrodata.datastructure.util.IsotopePoint;
import org.nucastrodata.datastructure.util.ReactionDataStructure;
import org.nucastrodata.dialogs.GeneralDialog;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.gui.WordWrapLabel;
import org.nucastrodata.Fonts;
import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorChartDialog;
import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorChartListener;
import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorListener;
import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorTreeDialog;
import org.nucastrodata.eval.wizard.reactionselector.ReactionSelectorTreeListener;

public class ElementSynthSensStudyPanel extends JPanel implements ActionListener
																		, ReactionSelectorChartListener
																		, ReactionSelectorTreeListener
																		, ReactionSelectorListener{
	
	private JLabel rateLabel, scaleLabel; 
	private WordWrapLabel topLabel, warningLabel;
	private ElementSynthDataStructure ds;
	private MainDataStructure mds;
	private CGICom cgiCom;
	private ElementSynthFrame parent;
	private JButton chartButton, treeButton;
	private JTextField rateField, scaleField;
	private ReactionSelectorChartDialog chartDialog;
	private ReactionSelectorTreeDialog treeDialog;
	private int gap = 20;
	private JPanel sensPanel;
	
	public ElementSynthSensStudyPanel(ElementSynthDataStructure ds
											, MainDataStructure mds
											, CGICom cgiCom
											, ElementSynthFrame parent){
		
		this.ds = ds;
		this.mds = mds;
		this.cgiCom = cgiCom;
		this.parent = parent;
		
		chartButton = new JButton("Select Reaction from Nuclide Chart");
		chartButton.addActionListener(this);
		chartButton.setFont(Fonts.buttonFont);
		
		treeButton = new JButton("Select Reaction from Tree");
		treeButton.addActionListener(this);
		treeButton.setFont(Fonts.buttonFont);
		
		rateLabel = new JLabel("Selected Rate: ");
		rateLabel.setFont(Fonts.textFont);
		
		scaleLabel = new JLabel("Scale Factors (comma separated list): ");
		scaleLabel.setFont(Fonts.textFont);
		
		rateField = new JTextField();
		rateField.setEditable(false);
		scaleField = new JTextField();
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("Select a rate for the sensitivity study from a tree or a nuclide chart by clicking "
							+ "one of the buttons below. Then enter up to ten scale factors for this rate as a "
							+ "comma-separated list. Scale factors will be automatically reordered from smallest "
							+ "to largest and each corresponding, one-zone simulation will be calculated in that order.");
		
		warningLabel = new WordWrapLabel(true);
		warningLabel.setCyanText("NOTE: A sensitivity study may take up to an hour to complete.");
		
		sensPanel = new JPanel();
		double[] columnSens = {TableLayoutConstants.FILL
							, 10, TableLayoutConstants.FILL};
		double[] rowSens = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		sensPanel.setLayout(new TableLayout(columnSens, rowSens));
		
		sensPanel.add(treeButton, "0, 2, f, c");
		sensPanel.add(chartButton, "2, 2, f, c");
		sensPanel.add(rateLabel, "0, 4, r, c");
		sensPanel.add(rateField, "2, 4, f, c");
		sensPanel.add(scaleLabel, "0, 6, r, c");
		sensPanel.add(scaleField, "2, 6, f, c");
		
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		setLayout(new TableLayout(column, row));
		add(topLabel, "1, 1, f, c");
		add(warningLabel, "1, 3, c, c");
		add(sensPanel, "1, 5, c, c");
	}
	
	public void setChartDialog(ReactionSelectorChartDialog chartDialog){
		this.chartDialog = chartDialog;
	}
	
	public void setTreeDialog(ReactionSelectorTreeDialog treeDialog){
		this.treeDialog = treeDialog;
	}
	
	public void fireIsotopeSelected(IsotopePoint ip){
		ds.setIsotope(ip.getZ() + "," + ip.getA());
		ds.getCurrentSimWorkDataStructure().getReactionDataStructure().setIsotopePoint(ip);
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_SENS_NETWORK_REACTIONS, parent)){
			setSelectorList(ds.getRateLibDataStructure().getReactionMap().get(ip));
		}
	}
	
	public void fireIsotopeSelected(DefaultMutableTreeNode node){
		IsotopePoint ip = (IsotopePoint)node.getUserObject();
		ds.setIsotope(ip.getZ() + "," + ip.getA());
		ds.getCurrentSimWorkDataStructure().getReactionDataStructure().setIsotopePoint(ip);
		if(cgiCom.doCGICall(mds, ds, CGICom.GET_SENS_NETWORK_REACTIONS, parent)){
			setRateNodes(node, ds.getRateLibDataStructure().getReactionMap().get(ip));
		}
	}
	
	public void setRateNodes(DefaultMutableTreeNode node, ArrayList<ReactionDataStructure> list){
		treeDialog.setReactionNodes(node, list);
	}
	
	public void setSelectorList(ArrayList<ReactionDataStructure> list){
		chartDialog.setReactionList(list);
	}
	
	public void fireReactionSubmitted(ReactionDataStructure rdsSubmitted){
		ds.getCurrentSimWorkDataStructure().setReactionDataStructure(rdsSubmitted);
		ds.getCurrentSimWorkDataStructure().setReactionRate(rdsSubmitted.toString());
		rateField.setText(ds.getCurrentSimWorkDataStructure().getReactionRate());
	}
	
	public boolean goodSensData(){
		if(rateField.getText().trim().equals("") || scaleField.getText().trim().equals("")){
			return false;
		}
		if(!goodScaleField()){
			return false;
		}
		return true;
	}
	
	private boolean goodScaleField(){
		try{
			String[] array = scaleField.getText().split(",");
			double[] tempArray = new double[array.length];
			if(tempArray.length>10){
				return false;
			}
			for(int i=0; i<array.length; i++){
				if(Double.valueOf(array[i])<=0){
					return false;
				}
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==chartButton || ae.getSource()==treeButton){
			if(ds.getRateLibDataStructure().getIsotopeMapAvailable().size()>0){
				if(ae.getSource()==chartButton){
					ReactionSelectorChartDialog chartDialog = new ReactionSelectorChartDialog(parent);
					setChartDialog(chartDialog);
					chartDialog.setCurrentState(ds.getCurrentSimWorkDataStructure().getReactionDataStructure(), ds.getRateLibDataStructure(), this, this);
					chartDialog.setLocationRelativeTo(parent);
					chartDialog.setVisible(true);
				}else if(ae.getSource()==treeButton){
					ReactionSelectorTreeDialog treeDialog = new ReactionSelectorTreeDialog(parent);
					setTreeDialog(treeDialog);
					treeDialog.setCurrentState(ds.getCurrentSimWorkDataStructure().getReactionDataStructure(), ds.getRateLibDataStructure(), this);
					treeDialog.setLocationRelativeTo(parent);
					treeDialog.setVisible(true);
				}
			}else{
				String string = "There are no available reactions for this operation.";
				GeneralDialog dialog = new GeneralDialog(parent, string, "Attention!");
				dialog.setVisible(true);
			}
		}
	}
	
	public void setCurrentState(){
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
		scaleField.setText(swds.getScaleFactors());
		rateField.setText(swds.getReactionRate());
	}
	
	public void getCurrentState(){
	
		ElementSimWorkDataStructure swds = ds.getCurrentSimWorkDataStructure();
	
		switch(swds.getSimWorkflowMode()){
			case SINGLE_STANDARD_SENS:
			case SINGLE_CUSTOM_SENS:
			
				String[] array = scaleField.getText().split(",");
				
				double[] tempArray = new double[array.length];
				for(int i=0; i<array.length; i++){
					tempArray[i] = Double.valueOf(array[i]);
				}
				Arrays.sort(tempArray);
			
				String params = "";
				for(int i=0; i<tempArray.length; i++){
					params += getScaledParameters(tempArray[i]) + ":";
				}
				params = params.substring(0, params.length()-1);
				swds.setParams(params);
				
				String scaleFactors = "";
				for(int i=0; i<tempArray.length; i++){
					scaleFactors += tempArray[i] + ",";
				}
				scaleFactors = scaleFactors.substring(0, scaleFactors.length()-1);
				swds.setScaleFactors(scaleFactors);
				
				swds.setReactionRate(swds.getReactionDataStructure().toString());
				
				break;
			default:
				break;
		}
	}
	
	private String getScaledParameters(double factor){
		String string = "";
		double[][] parameterCompArray = ds.getRateDataStructure().getParameterCompArray();
		for(int i=0; i<parameterCompArray.length; i++){
			double param = parameterCompArray[i][0] - Math.log(1.0);
			param = param + Math.log(factor);
			string += param + ", ";
			for(int j=1; j<parameterCompArray[i].length; j++){
				string += parameterCompArray[i][j];
				if(j<parameterCompArray[i].length-1){
					string += ",";
				}
			}
			if(i!=parameterCompArray.length-1){
				string += ",";
			}
		}
		return string;
	}
	
}
