package org.nucastrodata.element.elementviz;

import java.awt.*;
import javax.swing.*;

import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotFrame;
import org.nucastrodata.element.elementviz.abundtable.ElementVizAbundTableFrame;
import org.nucastrodata.element.elementviz.animator.ElementVizAnimatorFrame;
import org.nucastrodata.element.elementviz.edot.ElementVizEdotPlotFrame;
import org.nucastrodata.element.elementviz.finalabund.ElementVizFinalAbundFrame;
import org.nucastrodata.element.elementviz.intflux.ElementVizIntFluxFrame;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotFrame;
import org.nucastrodata.element.elementviz.sum.ElementVizSumPlotFrame;
import org.nucastrodata.element.elementviz.thermo.ElementVizThermoPlotFrame;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotFrame;
import org.nucastrodata.element.elementviz.worker.ElementVizFrameWorker;
import org.nucastrodata.enums.FolderType;
import org.nucastrodata.io.CGICom;

import java.awt.event.*;
import java.util.Vector;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;

public class ElementVizFrame extends JFrame implements WindowListener, ActionListener{
	
	public JButton continueButton, backButton, endButton;
	ButtonGroup choiceButtonGroup;
	JRadioButton saveAndCloseRadioButton, closeRadioButton, doNotCloseRadioButton;
	JButton submitButton, okButton, cancelButton, okButtonPaste, cancelButtonPaste;
	JPanel introButtonPanel, fullButtonPanel, endButtonPanel;
	JDialog saveDialog, confirmDialog, confirmDialogPaste;
	public static int currentPanelIndex;	
	GridBagConstraints gbc;
	public Container c;	
	public ElementVizIntroPanel elementVizIntroPanel = new ElementVizIntroPanel();
	public ElementVizSelectSimsPanel elementVizSelectSimsPanel;
	public ElementVizToolsPanel elementVizToolsPanel;
	public ElementVizThermoPlotFrame elementVizThermoPlotFrame;
	public ElementVizEdotPlotFrame elementVizEdotPlotFrame;
	public ElementVizIntFluxFrame elementVizIntFluxFrame;
	public ElementVizAnimatorFrame elementVizAnimatorFrame;
	public ElementVizAbundPlotFrame elementVizAbundPlotFrame;
	public ElementVizFinalAbundFrame elementVizFinalAbundFrame;
	public ElementVizWeightPlotFrame elementVizWeightPlotFrame;
	public ElementVizScalePlotFrame elementVizScalePlotFrame;
	public ElementVizSumPlotFrame elementVizSumPlotFrame;
	public ElementVizSampleFrame elementVizSampleFrame;
	public ElementVizAbundTableFrame elementVizAbundTableFrame;
	private ElementVizDataStructure ds;
	private CGICom cgiCom;
	
	public ElementVizFrame(ElementVizDataStructure ds, CGICom cgiCom){
		
		this.ds = ds;
		this.cgiCom = cgiCom;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Close Element Synthesis Visualizer");
		endButton.addActionListener(this);
		endButton.setFont(Fonts.buttonFont);
		
		setCurrentPanelIndex(0);
		addIntroPanel();
		addIntroButtonPanel();
		addWindowListener(this);
	}
	
	public void addIntroPanel(){
		c.add(elementVizIntroPanel, BorderLayout.CENTER);
		c.validate();
	}

	public void setCurrentPanelIndex(int index){
		currentPanelIndex = index;
	}
	
	public void initialize(Vector<String> v){
		
		ds.setNucSimVector(v);
		
		if(fullButtonPanel!=null){
			removeFullButtonPanel();
		}
		if(introButtonPanel!=null){
			removeIntroButtonPanel();
		}
		if(endButtonPanel!=null){
			removeEndButtonPanel();
		}
		if(elementVizIntroPanel!=null){
			c.remove(elementVizIntroPanel);
		}
		if(elementVizToolsPanel!=null){
			c.remove(elementVizToolsPanel);
		}
		if(elementVizSelectSimsPanel!=null){
			c.remove(elementVizSelectSimsPanel);
		}

		ElementVizFrameWorker worker = new ElementVizFrameWorker(ds, this);
		worker.execute();
	
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
		
			switch(currentPanelIndex){
			
				case 0:
					
					ElementVizFrameWorker worker = new ElementVizFrameWorker(ds, this);
					worker.execute();
					
					break; 
				
				case 1:
				
					elementVizSelectSimsPanel.getCurrentState();
					if(!elementVizSelectSimsPanel.emptyNucSims()){
						elementVizSelectSimsPanel.createElementVizNucSimDataStructures();
						elementVizSelectSimsPanel.createElementVizNucSimSetDataStructures();
						c.remove(elementVizSelectSimsPanel);
						elementVizToolsPanel = new ElementVizToolsPanel(ds, this, cgiCom);		
						c.add(elementVizToolsPanel, BorderLayout.CENTER);
						elementVizToolsPanel.setCurrentState();
						removeFullButtonPanel();
						addEndButtonPanel();
					}else{
						String string = "Please select at least one simulation from the <i>Element Synthesis Simulations</i> tree on the left.";
						AttentionDialog.createDialog(Cina.elementVizFrame, string);
					}
					break;	
			}
		
			c.validate();
		}else if(ae.getSource()==backButton){
			
			switch(currentPanelIndex){
				case 1:
					
					elementVizSelectSimsPanel.setVisible(false);
					c.remove(elementVizSelectSimsPanel);
					removeFullButtonPanel();
					setCurrentPanelIndex(0);
					addIntroPanel();
					addIntroButtonPanel();
					break; 
					
				case 2:
				
					c.remove(elementVizToolsPanel);
					c.add(elementVizSelectSimsPanel, BorderLayout.CENTER);
					setCurrentPanelIndex(1);
					removeEndButtonPanel();
					addFullButtonPanel();
					break;
			}
			
			c.validate();
			c.repaint();
			
		}else if(ae.getSource()==endButton){
        	createSaveDialog(currentPanelIndex);
		}else if(ae.getSource()==submitButton){

			if(saveAndCloseRadioButton.isSelected()){
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				switch(currentPanelIndex){
					
					case 1:
						elementVizSelectSimsPanel.getCurrentState();
						break;
				
					case 2:
						elementVizToolsPanel.getCurrentState();
						break;
						
				}
				
				saveDialog.setVisible(false);
				saveDialog.dispose();
				setCurrentPanelIndex(0);
         		setVisible(false);
        		c.removeAll();
				addIntroButtonPanel();
        		closeAllFrames();
        		dispose();

			}else if(closeRadioButton.isSelected()){
				
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				ds.initialize();
				saveDialog.setVisible(false);
				saveDialog.dispose();
				setCurrentPanelIndex(0);
         		setVisible(false);
        		c.removeAll();
				addIntroButtonPanel();
				addIntroPanel();
        		closeAllFrames();
        		dispose();
        		
			}else if(doNotCloseRadioButton.isSelected()){
				
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				saveDialog.setVisible(false);
				saveDialog.dispose();
	
			}
			
		}
		c.validate();
		
	}
	
	public void closeAllFrames(){
	
		if(elementVizSampleFrame!=null){
			elementVizSampleFrame.setVisible(false);
			elementVizSampleFrame.dispose();
		}
	
		if(elementVizAnimatorFrame!=null){
			elementVizAnimatorFrame.closeAllFrames();
			elementVizAnimatorFrame.setVisible(false);
			elementVizAnimatorFrame.dispose();
		}
	
		if(elementVizThermoPlotFrame!=null){
			elementVizThermoPlotFrame.closeAllFrames();
			elementVizThermoPlotFrame.setVisible(false);
			elementVizThermoPlotFrame.dispose();
		}
		
		if(elementVizEdotPlotFrame!=null){
			elementVizEdotPlotFrame.closeAllFrames();
			elementVizEdotPlotFrame.setVisible(false);
			elementVizEdotPlotFrame.dispose();
		}
		
		if(elementVizIntFluxFrame!=null){
			elementVizIntFluxFrame.closeAllFrames();
			elementVizIntFluxFrame.setVisible(false);
			elementVizIntFluxFrame.dispose();
		}
		
		if(elementVizAbundPlotFrame!=null){
			elementVizAbundPlotFrame.closeAllFrames();
			elementVizAbundPlotFrame.setVisible(false);
			elementVizAbundPlotFrame.dispose();
		}
		
		if(elementVizAbundTableFrame!=null){
			elementVizAbundTableFrame.closeAllFrames();
			elementVizAbundTableFrame.setVisible(false);
			elementVizAbundTableFrame.dispose();
		}
		
		if(elementVizSumPlotFrame!=null){
			elementVizSumPlotFrame.closeAllFrames();
			elementVizSumPlotFrame.setVisible(false);
			elementVizSumPlotFrame.dispose();
		}
		
		if(elementVizScalePlotFrame!=null){
			elementVizScalePlotFrame.closeAllFrames();
			elementVizScalePlotFrame.setVisible(false);
			elementVizScalePlotFrame.dispose();	
		}
		
		if(elementVizWeightPlotFrame!=null){
			elementVizWeightPlotFrame.closeAllFrames();
			elementVizWeightPlotFrame.setVisible(false);
			elementVizWeightPlotFrame.dispose();	
		}
		
		if(elementVizFinalAbundFrame!=null){
			elementVizFinalAbundFrame.closeAllFrames();
			elementVizFinalAbundFrame.setVisible(false);
			elementVizFinalAbundFrame.dispose();
		}
		
	}
	
	public void addIntroButtonPanel(){
		
		introButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		introButtonPanel.add(continueButton, gbc);

		c.add(introButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		
	}
	
	public void removeIntroButtonPanel(){
	
		c.remove(introButtonPanel);
	}
	
	public void addFullButtonPanel(){
		fullButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		fullButtonPanel.add(backButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		fullButtonPanel.add(continueButton, gbc);

		c.add(fullButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
	}

	public void removeFullButtonPanel(){
		
		c.remove(fullButtonPanel);
	}
	
	public void addEndButtonPanel(){
		endButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		endButtonPanel.add(backButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		endButtonPanel.add(endButton, gbc);

		c.add(endButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		
	}
	
	public void removeEndButtonPanel(){
	
		c.remove(endButtonPanel);
		
	}
	
	public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  
		   
        	createSaveDialog(currentPanelIndex);

    	}else if(we.getSource()==saveDialog){

    		saveDialog.setVisible(false);
    		saveDialog.dispose();
    	}
    } 
    
    public void windowActivated(WindowEvent we){}
    public void windowClosed(WindowEvent we){}
    public void windowDeactivated(WindowEvent we){}
    public void windowDeiconified(WindowEvent we){}
    public void windowIconified(WindowEvent we){}
    public void windowOpened(WindowEvent we){}
    
    public void createSaveDialog(int index){
    	
    	saveDialog = new JDialog(this, "Element Synthesis Visualizer Exit", true);
    	saveDialog.setSize(457, 180);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	choiceButtonGroup = new ButtonGroup();
    	
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain Element Synthesis Visualizer session input.", true);
    	saveAndCloseRadioButton.setFont(Fonts.textFont);
    	
    	closeRadioButton = new JRadioButton("Exit and erase Element Synthesis Visualizer session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the Element Synthesis Visualizer.", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		
		choiceButtonGroup.add(saveAndCloseRadioButton);
		choiceButtonGroup.add(closeRadioButton);
		choiceButtonGroup.add(doNotCloseRadioButton);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		saveDialog.getContentPane().add(saveAndCloseRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		saveDialog.getContentPane().add(closeRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		saveDialog.getContentPane().add(doNotCloseRadioButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		saveDialog.getContentPane().add(submitButton, gbc);

		saveDialog.setVisible(true);

    }

	public void gotoSelectSimsPanel(){
		
		removeIntroButtonPanel();
		c.remove(elementVizIntroPanel);
		elementVizSelectSimsPanel = new ElementVizSelectSimsPanel(ds);
		elementVizSelectSimsPanel.createSetNodes();		
		c.add(elementVizSelectSimsPanel, BorderLayout.CENTER);
		elementVizSelectSimsPanel.setCurrentState();
		addFullButtonPanel();
		
		c.validate();
		c.repaint();
		
	}
    
}