package org.nucastrodata.element.elementviz.animator.sum;

import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.SumDataStructure;
import org.nucastrodata.dialogs.*;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.wizard.*;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.GeneralDialog;

public class SumFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private SumDataStructure ds;
	
	/** The intro panel. */
	private SumIntroPanel introPanel;
	
	/** The sim panel. */
	private SumSimPanel simPanel;
	
	/** The output panel. */
	private SumOutputPanel outputPanel;
	
	/** The delay dialog. */
	private JDialog delayDialog;
	
	/** The close dialog. */
	private CautionDialog closeDialog;
	
	/**
	 * Instantiates a new sum frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 * @param ds the ds
	 */
	public SumFrame(MainDataStructure mds
						, CGICom cgiCom
						, JFrame frame
						, SumDataStructure ds){
		
		super(mds
				, cgiCom
				, null
				, "Abundance Summer"
				, ""
				, new Dimension(650, 500)
				, 3
				, false);
		
		this.ds = ds;
		
		setNavActionListeners(this);
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				SumFrame.this.setVisible(false);
				SumFrame.this.dispose();
			}
		});
		introPanel = new SumIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public SumDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(closeDialog!=null){
			if(ae.getSource()==closeDialog.getYesButton()){
				closeDialog.setVisible(false);
				closeDialog.dispose();
				closeDialog = null;
				closeWizard(WizardFrame.CLOSE);
			}else if(ae.getSource()==closeDialog.getNoButton()){
				closeDialog.setVisible(false);
				closeDialog.dispose();
				closeDialog = null;
			}
		}
		
		if(ae.getSource()==continueButton){
			
			switch(panelIndex){
			
				case 0:
					addFullButtons();
					simPanel = new SumSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(introPanel, simPanel, 1, 2, "Select Simulation Parameters", CENTER);
					break;
					
				case 1:
					if(simPanel.goodData()){
						simPanel.getCurrentState();
						simPanel.calcSum();
						addEndButtons();
						outputPanel = new SumOutputPanel(ds, this);
						outputPanel.setCurrentState();
						setContentPanel(simPanel, outputPanel, 2, 2, "Results", FULL);
					}else{
						String string = "All fields must contain positive integer values. Time step min must be greater than 0 " +
											"and time step max must be less than or equal to " + (ds.getSim().getAbundTimestepDataStructureArray().length-1)
											+ " Time step min must be less than or equal to time step max " +
											"and A min must be less than or equal to A max.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!"); 
						dialog.setVisible(true);
					}
					break;
			
			}
			
		}else if(ae.getSource()==backButton){
			
			switch(panelIndex){
			
				case 1:
					addIntroButtons();
					simPanel.setVisible(false);
					setContentPanel(simPanel, introPanel, 0, "", CENTER);
					break;
				
				case 2:
					addFullButtons();
					simPanel = new SumSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(outputPanel, simPanel, 1, 2, "Select Simulation Parameters", CENTER);
					break;
			
			}
			
		}
		
		validate();
		repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){

	}
	
}