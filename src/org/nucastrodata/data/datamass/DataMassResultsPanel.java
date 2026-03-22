package org.nucastrodata.data.datamass;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.*;
import org.nucastrodata.datastructure.*;
import org.nucastrodata.datastructure.feature.DataMassDataStructure;

import java.util.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.WizardPanel;
import org.nucastrodata.data.datamass.DataMassChartFrame;
import org.nucastrodata.data.datamass.DataMassPlotFrame;


/**
 * The Class DataMassResultsPanel.
 */
public class DataMassResultsPanel extends WizardPanel implements ActionListener{

	/** The gbc. */
	GridBagConstraints gbc;

	/** The chart button. */
	JButton plotButton, chartButton;

	/** The rp label. */
	JLabel theoryLabel, expLabel, numberIsotopesLabel
				, theoryValueLabel, expValueLabel
				, plotLabel, chartLabel, rpLabel;
				
	/** The ds. */
	private DataMassDataStructure ds;
	
	/**
	 * Instantiates a new data mass results panel.
	 *
	 * @param ds the ds
	 */
	public DataMassResultsPanel(DataMassDataStructure ds){
	
		this.ds = ds;
	
		Cina.dataMassFrame.setCurrentPanelIndex(4);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		addWizardPanel("Mass Model Evaluator", "Mass Model Results", "4", "4");

		plotLabel = new JLabel("<html>Click the <i>Open Mass Differences/RMS Plotter (1-D)</i> button to make 1-D plots of mass excess<p>differences and the RMS values of mass excess differences.</html>");
		plotLabel.setFont(Fonts.textFont);
		
		chartLabel = new JLabel("<html>Click the <i>Open Interactive Nuclide Chart (2-D)</i> button to view "
									+ "2-D plots of n, 2n, p, 2p, and alpha<p>separation energies and (alpha, n), "
									+ "(alpha, p), and (p, n) Q-values for theoretical and reference<p>mass models, "
									+ "as well as the difference and absolute difference of these values.</html>");
		chartLabel.setFont(Fonts.textFont);
		
		rpLabel = new JLabel("<html>r-Process path from<p><b>NUCLEAR PROPERTIES FOR ASTROPHYSICAL AND RADIOACTIVE-ION-BEAM APPLICATIONS</b><p>"
								+ "P. M�LLER, J. R. NIX, K. -L. KRATZ, Atomic Data Nuclear Data Tables 66 (1997) 131.<p>using FRDM95 model.</html>");
		rpLabel.setFont(Fonts.textFont);
		
		theoryLabel = new JLabel("");
		expLabel = new JLabel("");
		
		theoryValueLabel = new JLabel("");
		theoryValueLabel.setFont(Fonts.textFont);
		
		expValueLabel = new JLabel("");
		expValueLabel.setFont(Fonts.textFont);
		
		plotButton = new JButton("Open Mass Differences/RMS Plotter (1-D)");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		chartButton = new JButton("Open Interactive Nuclide Chart (2-D)");
		chartButton.setFont(Fonts.buttonFont);
		chartButton.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		mainPanel.add(theoryLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(theoryValueLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(expLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		
		mainPanel.add(expValueLabel, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(plotLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(plotButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(chartLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(chartButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(rpLabel, gbc);
		
		gbc.gridwidth = 1;
		
		add(mainPanel, BorderLayout.CENTER);
		validate();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==plotButton){
			
			if(Cina.dataMassFrame.dataMassPlotFrame!=null){
		
				Cina.dataMassFrame.dataMassPlotFrame.setFormatPanelState();
				Cina.dataMassFrame.dataMassPlotFrame.redrawPlot();
				Cina.dataMassFrame.dataMassPlotFrame.setVisible(true);
				
			}else{
			
				Cina.dataMassFrame.dataMassPlotFrame = new DataMassPlotFrame(ds);
							
			}
		
		}else if(ae.getSource()==chartButton){
			
			if(Cina.dataMassFrame.dataMassChartFrame!=null){
		
				Cina.dataMassFrame.dataMassChartFrame.initialize(2, 0);
				Cina.dataMassFrame.dataMassChartFrame.setVisible(true);
				Cina.dataMassFrame.dataMassChartFrame.sp.getHorizontalScrollBar().setValue(Cina.dataMassFrame.dataMassChartFrame.sp.getHorizontalScrollBar().getMinimum());
				Cina.dataMassFrame.dataMassChartFrame.sp.getVerticalScrollBar().setValue(Cina.dataMassFrame.dataMassChartFrame.sp.getVerticalScrollBar().getMaximum());
				
			}else{
			
				Cina.dataMassFrame.dataMassChartFrame = new DataMassChartFrame(ds);
				Cina.dataMassFrame.dataMassChartFrame.initialize(2, 0);
				Cina.dataMassFrame.dataMassChartFrame.setVisible(true);
				Cina.dataMassFrame.dataMassChartFrame.sp.getHorizontalScrollBar().setValue(Cina.dataMassFrame.dataMassChartFrame.sp.getHorizontalScrollBar().getMinimum());
				Cina.dataMassFrame.dataMassChartFrame.sp.getVerticalScrollBar().setValue(Cina.dataMassFrame.dataMassChartFrame.sp.getVerticalScrollBar().getMaximum());
									
			}
				
		}
	
	}

	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public void getCurrentState(){}
	
	/**
	 * Sets the current state.
	 */
	public void setCurrentState(){

		theoryLabel.setText("Theoretical Mass Model: ");
								
		expLabel.setText("Reference Mass Model: ");
																				
		theoryValueLabel.setText(ds.getTheoryModelDataStructure().getModelName());
								
		expValueLabel.setText(ds.getExpModelDataStructure().getModelName());

	}
	
}