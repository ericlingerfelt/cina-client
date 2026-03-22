package org.nucastrodata.element.elementviz.sum;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.io.FileGetter;
import org.nucastrodata.wizard.gui.WordWrapLabel;

public class ElementVizNucSimListPanel extends JPanel implements ItemListener, ActionListener{

	ArrayList<JCheckBox> boxList;
	ArrayList<String> zOnlyList;
	int numberNucSims;
	GridBagConstraints gbc;
	JButton selectAllButton, unselectAllButton;
	ElementVizSumPlotFrame parent;
	JRadioButton abundButton, diffButton, linButton, logButton;
	JLabel y1Label, y2Label, diffLabel, scaleLabel, solarLabel, haloLabel, simsLabel;
	WordWrapLabel warningLabel;
	JComboBox y1Box, y2Box, diffBox;
	JPanel choicePanel, simsPanel, scalePanel, diffPanel, solarPanel, haloPanel;
	TreeMap<String, String> solarMap;
	TreeMap<String, String> haloMap;

	public ElementVizNucSimListPanel(ElementVizSumPlotFrame parent){
    	
		this.parent = parent;
		
        selectAllButton = new JButton("Select All Simulations");
		selectAllButton.addActionListener(this);
		unselectAllButton = new JButton("Unselect All Simulations");
		unselectAllButton.addActionListener(this);
		
		abundButton = new JRadioButton("Final Abundance", true);
		abundButton.addActionListener(this);
		diffButton = new JRadioButton("Fractional Abundance Difference", false);
		diffButton.addActionListener(this);
		
		linButton = new JRadioButton("Lin", true);
		linButton.addActionListener(this);
		logButton = new JRadioButton("Log", false);
		logButton.addActionListener(this);
		
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(abundButton);
		buttonGroup1.add(diffButton);
		
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(linButton);
		buttonGroup2.add(logButton);
		
		y1Box = new JComboBox();
		y2Box = new JComboBox();
		
		diffBox = new JComboBox();
		diffBox.addItem("(Y2-Y1)/Y1");
		diffBox.addItem("(Y2-Y1)/(1/2)(Y2+Y1)");
		diffBox.addActionListener(this);
    
		y1Label = new JLabel("Y1");
		y2Label = new JLabel("Y2");
		diffLabel = new JLabel("Diff Type");
		scaleLabel = new JLabel("Diff Scale");
		simsLabel = new JLabel("<html><b>Nucleosynthesis Simulations</b></html>");
		solarLabel = new JLabel("<html><b>Observational Solar Abundances</b></html>");
		haloLabel = new JLabel("<html><b>Observational R-Process Abundances</b></html>");
		
		choicePanel = new JPanel();
		simsPanel = new JPanel();
		scalePanel = new JPanel();
		diffPanel = new JPanel();
		solarPanel = new JPanel();
		haloPanel = new JPanel();
		
    }
    
	public boolean inZOnlyList(String name){
		return zOnlyList.contains(name);
	}
	
	public void setZOnlyEnabled(boolean flag){
		for(JCheckBox box: boxList){
			if(zOnlyList.contains(box.getText())){
				box.setEnabled(flag);
				if(!flag){
					box.removeItemListener(this);
					box.setSelected(flag);
					box.addItemListener(this);
				}
			}
		}
	}
	
	public void setYComboBoxItems(String xType){
		y1Box.removeActionListener(this);
		y2Box.removeActionListener(this);
		y1Box.removeAllItems();
		y2Box.removeAllItems();
		for(JCheckBox box: boxList){
			String name = box.getText();
			if(xType.equals("Z") || (!xType.equals("Z") && !inZOnlyList(name))){
				y1Box.addItem(name);
				y2Box.addItem(name);
			}
		}
    	y1Box.setSelectedIndex(0);
    	y2Box.setSelectedIndex(1);
    	y1Box.addActionListener(this);
		y2Box.addActionListener(this);
	}

	public boolean isSimSelected(String name){
		for(JCheckBox box: boxList){
			if(box.getText().equals(name)){
				return box.isSelected();
			}
		}
		return false;
	}
	
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==selectAllButton){
    		for(JCheckBox box: boxList){
    			box.setSelected(true);
    		}
    	}else if(ae.getSource()==unselectAllButton){
    		for(JCheckBox box: boxList){
    			box.setSelected(false);
    		}
    	}else if(ae.getSource()==abundButton){
    		if(abundButton.isSelected()){
    			setListState("abund");
	    		Cina.elementVizFrame.elementVizSumPlotFrame.setFormatPanelState("abund", true);
    		}
    	}else if(ae.getSource()==diffButton){
    		if(diffButton.isSelected()){
    			setListState("diff");
    			Cina.elementVizFrame.elementVizSumPlotFrame.calcFracDiffMap();
	    		Cina.elementVizFrame.elementVizSumPlotFrame.setFormatPanelState("diff", linButton.isSelected());
    		}
    	}else if(ae.getSource()==logButton){
    		if(logButton.isSelected()){
    			Cina.elementVizFrame.elementVizSumPlotFrame.setFormatPanelState("diff", linButton.isSelected());
    		}
    	}else if(ae.getSource()==linButton){
    		if(linButton.isSelected()){
    			Cina.elementVizFrame.elementVizSumPlotFrame.setFormatPanelState("diff", linButton.isSelected());
    		}
    	}else if(ae.getSource()==y1Box || ae.getSource()==y2Box || ae.getSource()==diffBox){
    		if(y1Box.getSelectedItem().toString().equals(y2Box.getSelectedItem().toString())){
    			AttentionDialog.createDialog(Cina.elementVizFrame.elementVizSumPlotFrame, "Please select different datasets for Y1 and Y2.");
    			y1Box.removeActionListener(this);
    			y2Box.removeActionListener(this);
    			y1Box.setSelectedIndex(0);
    	    	y2Box.setSelectedIndex(1);
    	    	y1Box.addActionListener(this);
    			y2Box.addActionListener(this);
    		}
    		Cina.elementVizFrame.elementVizSumPlotFrame.calcFracDiffMap();
    		Cina.elementVizFrame.elementVizSumPlotFrame.setFormatPanelState("diff", linButton.isSelected());
    	}
    }
    
    public void setListState(String type){
    	
    	removeAll();
    	
    	gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(25, 25, 25, 25);
		add(warningLabel, gbc);
    	
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.NORTH;
		add(choicePanel, gbc);
		
    	if(type.equals("abund")){
			
    		remove(diffPanel);
    		remove(scalePanel);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 2;
    		gbc.gridwidth = 2;
    		gbc.insets = new Insets(15, 25, 5, 5);
    		gbc.fill = GridBagConstraints.NONE;
    		gbc.anchor = GridBagConstraints.CENTER;	
    		add(selectAllButton, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 3;
    		gbc.insets = new Insets(5, 25, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(unselectAllButton, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 4;
    		gbc.insets = new Insets(15, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(simsLabel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 5;
    		gbc.insets = new Insets(15, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(simsPanel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 6;
    		gbc.insets = new Insets(15, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(haloLabel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 7;
    		gbc.insets = new Insets(5, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(haloPanel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 8;
    		gbc.insets = new Insets(15, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(solarLabel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 9;
    		gbc.insets = new Insets(5, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(solarPanel, gbc);
    		
		}else if(type.equals("diff")){
			
			remove(selectAllButton);
    		remove(unselectAllButton);
    		remove(simsLabel);
    		remove(simsPanel);
    		remove(solarPanel);
    		remove(haloPanel);
    		remove(solarLabel);
    		remove(haloLabel);
			
    		gbc.gridx = 0;
    		gbc.gridy = 2;
    		gbc.gridwidth = 2;
    		gbc.insets = new Insets(25, 5, 5, 5);
    		gbc.fill = GridBagConstraints.NONE;
    		gbc.anchor = GridBagConstraints.CENTER;	
    		add(scalePanel, gbc);
    		
    		gbc.gridx = 0;
    		gbc.gridy = 3;
    		gbc.insets = new Insets(25, 5, 5, 5);
    		gbc.anchor = GridBagConstraints.CENTER;	
    		gbc.fill = GridBagConstraints.NONE;
    		add(diffPanel, gbc);

		}
    	
    	validate();
    }
    
    private String getDataDesc(String name, String type){
    	String desc = "<html>";
    	String data = new String(FileGetter.getFileByName("AbundanceData/" + type + "/" + name + ".dat"));
		String[] lineArray = data.split("\\n");
		for(int i=0; i<lineArray.length; i++){
			String line = lineArray[i];
			if(line.indexOf("#")!=-1){
				desc += line.substring(1) + "<br>";
			}else{
				desc += "</html>";
				return desc;
			}
		}
		return desc;
    }
    
    public void initialize(){
    	
    	zOnlyList = new ArrayList<String>();
    	Iterator<String> itr = parent.getFinalAbundMap().keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(parent.getFinalAbundMap().get(name).get("A").size()==0){
    			zOnlyList.add(name);
    		}
    	}
    	
    	String solarListContents = new String(FileGetter.getFileByName("AbundanceData/Solar/list"));
		String[] solarArray = solarListContents.split("\\n");
		solarMap = new TreeMap<String, String>();
		
		for(int i=0; i<solarArray.length; i++){
			solarMap.put(solarArray[i], getDataDesc(solarArray[i], "Solar"));
		}
		
		String haloListContents = new String(FileGetter.getFileByName("AbundanceData/R_Process/list"));
		String[] haloArray = haloListContents.split("\\n");
		haloMap = new TreeMap<String, String>();
		
		for(int i=0; i<haloArray.length; i++){
			haloMap.put(haloArray[i], getDataDesc(haloArray[i], "R_Process"));
		}
    	
		gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
	
		warningLabel = new WordWrapLabel();
		String text = "Choose either final abundance or fractional abundance<br>difference using the radio buttons below. " +
						"Place your<br>mouse over each observational abundance checkbox to<br>view its citation as a tooltip. " +
						"Disabled observational<br>abundance checkboxes indicate that data is only available<br>versus proton number (Z) for that dataset. " +
						"If a log scale is<br>selected for fractional abundance difference, then the<br>absolute value of the difference will be plotted.";
		warningLabel.setText(text);
		
		ArrayList<String> nucSimList = new ArrayList<String>();
		
		itr = parent.getFinalAbundMap().keySet().iterator();
    	while(itr.hasNext()){
    		String name = itr.next();
    		if(!haloMap.keySet().contains(name) && !solarMap.keySet().contains(name)){
    			nucSimList.add(name);
    		}
    	}
		
    	numberNucSims = nucSimList.size();

		abundButton.setSelected(true);
		diffButton.setSelected(false);
		linButton.setSelected(true);
		
		choicePanel.setLayout(new GridBagLayout());
    	
    	gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		choicePanel.add(abundButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;	
		choicePanel.add(diffButton, gbc);
		
		boxList = new ArrayList<JCheckBox>();
		
		simsPanel.setLayout(new GridBagLayout());
		gbc.gridy = 0;
		
    	for(String name: nucSimList){
			JCheckBox box = new JCheckBox();
			box.removeItemListener(this);
			box.setSelected(true);
			box.addItemListener(this);
			box.setFont(Fonts.textFont);
			box.setText(name);
			boxList.add(box);
			
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			simsPanel.add(box, gbc);
			gbc.gridy += 1;
    	}

		haloPanel.setLayout(new GridBagLayout());
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		itr = haloMap.keySet().iterator();
		while(itr.hasNext()){
			
			String name = itr.next();
			
			JCheckBox box = new JCheckBox();
			box.removeItemListener(this);
			box.setSelected(false);
			box.addItemListener(this);
			box.setFont(Fonts.textFont);
			box.setText(name);
			box.setToolTipText(haloMap.get(name));
			boxList.add(box);
			
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			haloPanel.add(box, gbc);
			gbc.gridy += 1;
			
		}

		solarPanel.setLayout(new GridBagLayout());
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;
		
		itr = solarMap.keySet().iterator();
		while(itr.hasNext()){
			
			String name = itr.next();
			
			JCheckBox box = new JCheckBox();
			box.removeItemListener(this);
			box.setSelected(false);
			box.addItemListener(this);
			box.setFont(Fonts.textFont);
			box.setText(name);
			box.setToolTipText(solarMap.get(name));
			boxList.add(box);
			
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			solarPanel.add(box, gbc);
			gbc.gridy += 1;
			
		}
		
		scalePanel.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;	
		scalePanel.add(scaleLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;	
		scalePanel.add(linButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;	
		scalePanel.add(logButton, gbc);
		
		diffPanel.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;	
		diffPanel.add(diffLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;	
		diffPanel.add(diffBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;	
		diffPanel.add(y1Label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;	
		diffPanel.add(y1Box, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 25, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;	
		diffPanel.add(y2Label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;	
		diffPanel.add(y2Box, gbc);
		
		setListState("abund");
		setYComboBoxItems("Z");
		validate();

    }

    //Method for Item Listener
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    	
    	if(ie.getSource() instanceof JCheckBox){
	
			//rewdraw Sum profile plot
    		Cina.elementVizFrame.elementVizSumPlotFrame.redrawPlot();
        
        }
    }

}    