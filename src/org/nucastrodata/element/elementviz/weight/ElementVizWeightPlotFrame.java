package org.nucastrodata.element.elementviz.weight;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightChartControlsPanel;
import org.nucastrodata.element.elementviz.weight.ElementVizWeightPlotControlsPanel;
import org.nucastrodata.element.elementviz.worker.ElementVizWeightPlotFrameWorker;

public class ElementVizWeightPlotFrame extends JFrame implements WindowListener, ActionListener{
	
	public JButton continueButton, backButton;
	JPanel continueButtonPanel, backButtonPanel;	
	GridBagConstraints gbc;
	public Container c;	
	public ElementVizWeightChartControlsPanel elementVizWeightChartControlsPanel;
	public ElementVizWeightPlotControlsPanel elementVizWeightPlotControlsPanel;
    
    private ElementVizDataStructure ds;
    
	public ElementVizWeightPlotFrame(ElementVizDataStructure ds){
		
		this.ds = ds;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		
		backButton = new JButton("< Back");
		backButton.addActionListener(this);
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.addActionListener(this);
		continueButton.setFont(Fonts.buttonFont);

		addWindowListener(this);

	}
	
	public void closeAllFrames(){
		if(elementVizWeightPlotControlsPanel!=null){
			if(elementVizWeightPlotControlsPanel.elementVizWeightTableFrame!=null){
				elementVizWeightPlotControlsPanel.elementVizWeightTableFrame.setVisible(false);
				elementVizWeightPlotControlsPanel.elementVizWeightTableFrame.dispose();
			}
		}
	}

	public void setCurrentState(){
		
		c.removeAll();
		
		addContinueButtonPanel();
		
		elementVizWeightChartControlsPanel = new ElementVizWeightChartControlsPanel(ds);
		elementVizWeightChartControlsPanel.setCurrentState();

		c.add(elementVizWeightChartControlsPanel, BorderLayout.CENTER);
	
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){

			elementVizWeightChartControlsPanel.elementVizWeightChartPanel.getCurrentState();
			
			ds.getIsotopeViktorWeight().trimToSize();
			
			if(ds.getIsotopeViktorWeight().size()!=0){

				ElementVizWeightPlotFrameWorker worker = new ElementVizWeightPlotFrameWorker(ds, this);
				worker.execute();
				
			}else{
			
				String string = "Please select isotopes for weighted abundance plot.";
				Dialogs.createExceptionDialog(string, this);
			
			}
			
			c.validate();
		
		//Else if back button hit	
		}else if(ae.getSource()==backButton){
				
			//remove it
			c.remove(elementVizWeightPlotControlsPanel);
			
			elementVizWeightChartControlsPanel = new ElementVizWeightChartControlsPanel(ds);
			
			//add the param status panel
			c.add(elementVizWeightChartControlsPanel, BorderLayout.CENTER);
			
			//set the current state of the param status panel
			elementVizWeightChartControlsPanel.setCurrentState();
			
			removeBackButtonPanel();
				
			addContinueButtonPanel();
			
			c.validate();
			
		}
				
		
		
		//Redo layout of frame
		c.validate();
		
	}
	
	public void addContinueButtonPanel(){
		continueButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		continueButtonPanel.add(continueButton, gbc);

		c.add(continueButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		
	}
	
	public void removeContinueButtonPanel(){
	
		c.remove(continueButtonPanel);
	}
	
	public void addBackButtonPanel(){
		backButtonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 10, 0);
		backButtonPanel.add(backButton, gbc);

		c.add(backButtonPanel, BorderLayout.SOUTH);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
	}
	
	public void removeBackButtonPanel(){
		c.remove(backButtonPanel);
	}
	
	public void windowClosing(WindowEvent we) {   
	
		setVisible(false);
		dispose();
		
    } 
    
    public void windowActivated(WindowEvent we){}
    public void windowClosed(WindowEvent we){}
    public void windowDeactivated(WindowEvent we){}
    public void windowDeiconified(WindowEvent we){}
    public void windowIconified(WindowEvent we){}
    public void windowOpened(WindowEvent we){}

	public void gotoControlsPanel(){
	
		removeContinueButtonPanel();

		c.remove(elementVizWeightChartControlsPanel);

		elementVizWeightPlotControlsPanel = new ElementVizWeightPlotControlsPanel(ds);	
	
		elementVizWeightPlotControlsPanel.setFormatPanelState();
		elementVizWeightPlotControlsPanel.setIsotopeListPanel();
		elementVizWeightPlotControlsPanel.redrawPlot();
	
		//Add the new panel			
		c.add(elementVizWeightPlotControlsPanel, BorderLayout.CENTER);
		
		//Add the full button panel (forward and back)
		addBackButtonPanel();
		

		c.validate();
		
	}
   
}