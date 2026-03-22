package org.nucastrodata.element.elementviz.animator.bottle;

import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.BottleDataStructure;
import org.nucastrodata.io.CGICom;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.GeneralDialog;
import org.nucastrodata.element.elementviz.animator.bottle.BottleFrame;
import org.nucastrodata.element.elementviz.animator.bottle.BottleIntroPanel;
import org.nucastrodata.element.elementviz.animator.bottle.BottleOutputPanel;
import org.nucastrodata.element.elementviz.animator.bottle.BottleParamPanel;
import org.nucastrodata.element.elementviz.animator.bottle.BottleSimPanel;


/**
 * The Class BottleFrame.
 */
public class BottleFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private BottleDataStructure ds;
	
	/** The intro panel. */
	private BottleIntroPanel introPanel;
	
	/** The sim panel. */
	private BottleSimPanel simPanel;
	
	/** The param panel. */
	private BottleParamPanel paramPanel;
	
	/** The output panel. */
	private BottleOutputPanel outputPanel;
	
	/** The delay dialog. */
	private JDialog delayDialog;
	
	/** The close dialog. */
	private CautionDialog closeDialog;
	
	/**
	 * Instantiates a new bottle frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 * @param ds the ds
	 */
	public BottleFrame(MainDataStructure mds
							, CGICom cgiCom
							, JFrame frame
							, BottleDataStructure ds){
		
		super(mds
				, cgiCom
				, null
				, "Bottleneck Reaction Finder"
				, ""
				, new Dimension(650, 500)
				, 3
				, false);
		
		this.ds = ds;
		
		setNavActionListeners(this);
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				BottleFrame.this.setVisible(false);
				BottleFrame.this.dispose();
			}
		});
		introPanel = new BottleIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public BottleDataStructure getDataStructure(){return ds;}
	
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
					simPanel = new BottleSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(introPanel, simPanel, 1, 3, "Select Simulation Parameters", CENTER);
					break;
					
				case 1:
					if(simPanel.goodData()){
						simPanel.getCurrentState();
						paramPanel = new BottleParamPanel(ds, Cina.cinaMainDataStructure, this, Cina.cgiCom);
						paramPanel.setCurrentState();
						setContentPanel(simPanel, paramPanel, 2, 3, "Select Bottleneck Reaction Finder Criteria", CENTER);
					}else{
						String string = "All fields must contain values and be integer values with the except of Time (sec) min and max.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!"); 
						dialog.setVisible(true);
					}
					break;
					
				case 2:
					if(paramPanel.goodData()){
						paramPanel.getCurrentState();
					
						openDelayDialog(this);
						final SwingWorker worker = new SwingWorker(){
							
							boolean goodCall;
							
							public Object construct(){
								
								goodCall = cgiCom.doCGICall(mds, ds, CGICom.GET_BOTTLENECK_REACTIONS, BottleFrame.this);
								
								return new Object();
								
							}
							
							public void finished(){
								if(goodCall){
									BottleFrame.this.gotoOutputPanel();
								}
								closeDelayDialog();
							}
							
						};
						
						worker.start();
						
					}else{
						String string = "All fields must contain values and be numeric values. The Flux Separation Factor must be between 0 and 1.";
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
					simPanel = new BottleSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(paramPanel, simPanel, 1, 3, "Select Simulation Parameters", CENTER);
					break;
			
				case 3:
					addFullButtons();
					paramPanel = new BottleParamPanel(ds, Cina.cinaMainDataStructure, this, Cina.cgiCom);
					paramPanel.setCurrentState();
					setContentPanel(outputPanel, paramPanel, 2, 3, "Select Bottleneck Reaction Finder Criteria", CENTER);
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
	
	/**
	 * Goto output panel.
	 */
	public void gotoOutputPanel(){
		addEndButtons();
		outputPanel = new BottleOutputPanel(ds, this);
		outputPanel.setCurrentState(false);
		setContentPanel(paramPanel, outputPanel, 3, 3, "Results", FULL);
		validate();
		repaint();
	}

	/**
	 * Open delay dialog.
	 *
	 * @param frame the frame
	 */
	public void openDelayDialog(Frame frame){
		
		delayDialog = new JDialog(frame, "Please wait...", false);
		delayDialog.setSize(340, 200);
		delayDialog.getContentPane().setLayout(new GridBagLayout());
		delayDialog.setLocationRelativeTo(frame);
		
		JTextArea delayTextArea = new JTextArea("", 5, 30);
		delayTextArea.setLineWrap(true);
		delayTextArea.setWrapStyleWord(true);
		delayTextArea.setEditable(false);
		
		JScrollPane sp = new JScrollPane(delayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));

		String delayString = "Please wait while bottlenecks are being calculated.";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		GridBagConstraints gbc = new GridBagConstraints(); 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		

		delayDialog.validate();
		
		delayDialog.setVisible(true);
		
	}

	/**
	 * Close delay dialog.
	 */
	public void closeDelayDialog(){
		
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;

	}
	
}