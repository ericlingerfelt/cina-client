package org.nucastrodata.element.elementviz.abund;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundChartControlsPanel;
import org.nucastrodata.element.elementviz.abund.ElementVizAbundPlotControlsPanel;
import org.nucastrodata.element.elementviz.worker.ElementVizAbundPlotFrameWorker;


public class ElementVizAbundPlotFrame extends JFrame implements WindowListener, ActionListener{

	public JButton continueButton, backButton;
	JPanel continueButtonPanel, backButtonPanel;	
	GridBagConstraints gbc;
	public Container c;
	public ElementVizAbundChartControlsPanel elementVizAbundChartControlsPanel;
	public ElementVizAbundPlotControlsPanel elementVizAbundPlotControlsPanel;

    private ElementVizDataStructure ds;
    
	public ElementVizAbundPlotFrame(ElementVizDataStructure ds){
		
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
		if(elementVizAbundPlotControlsPanel!=null){
			if(elementVizAbundPlotControlsPanel.elementVizAbundTableFrame!=null){
				elementVizAbundPlotControlsPanel.elementVizAbundTableFrame.setVisible(false);
				elementVizAbundPlotControlsPanel.elementVizAbundTableFrame.dispose();
			}
		}
	}
	
	public void setCurrentState(){
		c.removeAll();
		addContinueButtonPanel();
		elementVizAbundChartControlsPanel = new ElementVizAbundChartControlsPanel(ds);
		elementVizAbundChartControlsPanel.setCurrentState();
		c.add(elementVizAbundChartControlsPanel, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){

			elementVizAbundChartControlsPanel.elementVizAbundChartPanel.getCurrentState();
			ds.getIsotopeViktorAbund().trimToSize();
			if(ds.getIsotopeViktorAbund().size()!=0){
				
				ElementVizAbundPlotFrameWorker worker  = new ElementVizAbundPlotFrameWorker(ds, this);
				worker.execute();
				
			}else{
			
				String string = "Please select isotopes for abundance plot.";
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}else if(ae.getSource()==backButton){
				
			c.remove(elementVizAbundPlotControlsPanel);
			elementVizAbundChartControlsPanel = new ElementVizAbundChartControlsPanel(ds);
			c.add(elementVizAbundChartControlsPanel, BorderLayout.CENTER);
			elementVizAbundChartControlsPanel.setCurrentState();
			removeBackButtonPanel();
			addContinueButtonPanel();
			c.validate();
			
		}
		
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

	public void gotoControlsPanel() {
		removeContinueButtonPanel();
		c.remove(elementVizAbundChartControlsPanel);
		elementVizAbundPlotControlsPanel = new ElementVizAbundPlotControlsPanel(ds);	
		elementVizAbundPlotControlsPanel.redrawPlot();
		c.add(elementVizAbundPlotControlsPanel, BorderLayout.CENTER);
		addBackButtonPanel();
		c.validate();
	}
	
}