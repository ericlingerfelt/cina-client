package org.nucastrodata.element.elementviz.abundtable;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import org.nucastrodata.element.elementviz.worker.ElementVizAbundTableFrameWorker;
import java.awt.event.*;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;

public class ElementVizAbundTableFrame extends JFrame implements WindowListener, ActionListener{
	
	public JButton continueButton, backButton;
	
	JPanel continueButtonPanel, backButtonPanel;	

	GridBagConstraints gbc;

	public Container c;	
	public ElementVizAbundTableChartControlsPanel elementVizAbundTableChartControlsPanel;
	public ElementVizAbundTableTablePanel elementVizAbundTableTablePanel;
    private ElementVizDataStructure ds;
    
	public ElementVizAbundTableFrame(ElementVizDataStructure ds){
		
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

	}
	
	public void setCurrentState(){
		
		c.removeAll();
		addContinueButtonPanel();
		
		elementVizAbundTableChartControlsPanel = new ElementVizAbundTableChartControlsPanel(ds);
		elementVizAbundTableChartControlsPanel.setCurrentState();
		
		c.add(elementVizAbundTableChartControlsPanel, BorderLayout.CENTER);

		validate();
		repaint();

	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){

			ElementVizAbundTableChartControlsPanel.elementVizAbundTableChartPanel.getCurrentState();
			
			ds.getIsotopeViktorAbundTable().trimToSize();
			
			if(ds.getIsotopeViktorAbundTable().size()!=0){
			
				ElementVizAbundTableFrameWorker worker  = new ElementVizAbundTableFrameWorker(ds, this);
				worker.execute();
			
			}else{
			
				String string = "Please select isotopes for final abundance table of points.";
				Dialogs.createExceptionDialog(string, this);
			
			}
		
		}else if(ae.getSource()==backButton){
						
			c.remove(elementVizAbundTableTablePanel);
			
			elementVizAbundTableChartControlsPanel = new ElementVizAbundTableChartControlsPanel(ds);
			c.add(elementVizAbundTableChartControlsPanel, BorderLayout.CENTER);
			elementVizAbundTableChartControlsPanel.setCurrentState();
			
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

	public void gotoTablePanel() {
		removeContinueButtonPanel();
		c.remove(elementVizAbundTableChartControlsPanel);
		elementVizAbundTableTablePanel = new ElementVizAbundTableTablePanel(ds);	
		elementVizAbundTableTablePanel.setCurrentState();
		c.add(elementVizAbundTableTablePanel, BorderLayout.CENTER);
		addBackButtonPanel();
		c.validate();
	}
    
}