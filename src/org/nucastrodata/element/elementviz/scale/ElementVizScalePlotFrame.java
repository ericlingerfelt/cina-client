package org.nucastrodata.element.elementviz.scale;

import java.awt.*;
import javax.swing.*;
import org.nucastrodata.datastructure.feature.ElementVizDataStructure;
import java.awt.event.*;
import org.nucastrodata.Dialogs;
import org.nucastrodata.Fonts;
import org.nucastrodata.element.elementviz.scale.ElementVizScaleChartControlsPanel;
import org.nucastrodata.element.elementviz.scale.ElementVizScalePlotControlsPanel;
import org.nucastrodata.element.elementviz.worker.ElementVizScalePlotFrameWorker;

public class ElementVizScalePlotFrame extends JFrame implements WindowListener, ActionListener{

	private JButton continueButton, backButton;
	private JPanel continueButtonPanel, backButtonPanel;	
	private GridBagConstraints gbc;
	private Container c;	
	private ElementVizScaleChartControlsPanel chartControlsPanel;
	private ElementVizScalePlotControlsPanel plotControlsPanel;
	
    private ElementVizDataStructure ds;
    
	public ElementVizScalePlotFrame(ElementVizDataStructure ds){
		
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
		if(plotControlsPanel!=null){
			plotControlsPanel.closeAllFrames();
		}
	}
	
	public void setCurrentState(){
		c.removeAll();
		addContinueButtonPanel();
		chartControlsPanel = new ElementVizScaleChartControlsPanel(ds);
		chartControlsPanel.setCurrentState();
		c.add(chartControlsPanel, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){

			chartControlsPanel.getCurrentState();
			ds.getIsotopeViktorScale().trimToSize();
			
			if(ds.getIsotopeViktorScale().size()!=0){

				ElementVizScalePlotFrameWorker worker  = new ElementVizScalePlotFrameWorker(ds, this);
				worker.execute();

			}else{
			
				String string = "Please select isotopes for scaled abundance plot.";
				Dialogs.createExceptionDialog(string, this);
			
			}
			
			c.validate();
			
		}else if(ae.getSource()==backButton){
				
			c.remove(plotControlsPanel);
			chartControlsPanel = new ElementVizScaleChartControlsPanel(ds);
			c.add(chartControlsPanel, BorderLayout.CENTER);
			chartControlsPanel.setCurrentState();
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
		c.remove(chartControlsPanel);
		plotControlsPanel = new ElementVizScalePlotControlsPanel(ds);	
		plotControlsPanel.setCurrentState();
		c.add(plotControlsPanel, BorderLayout.CENTER);
		addBackButtonPanel();
		c.validate();
	}
    
}