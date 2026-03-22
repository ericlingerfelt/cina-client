package org.nucastrodata.element.elementviz.animator.wait;

import javax.swing.*;
import org.nucastrodata.Cina;
import org.nucastrodata.SwingWorker;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.WaitingDataStructure;
import org.nucastrodata.io.CGICom;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.GeneralDialog;


/**
 * The Class WaitingFrame.
 */
public class WaitingFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private WaitingDataStructure ds;
	
	/** The intro panel. */
	private WaitingIntroPanel introPanel;
	
	/** The sim panel. */
	private WaitingSimPanel simPanel;
	
	/** The param panel. */
	private WaitingParamPanel paramPanel;
	
	/** The output panel. */
	private WaitingOutputPanel outputPanel;
	//JAVA 6 CODE
	//private PleaseWaitDialog dialog;
	
	//JAVA 5 CODE
	/** The delay dialog. */
	private  JDialog delayDialog;
	
	/** The close dialog. */
	private CautionDialog closeDialog;
	//JAVA 6 CODE
	//private WaitingCGICallTask task;
	
	/**
	 * Instantiates a new waiting frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 * @param ds the ds
	 */
	public WaitingFrame(MainDataStructure mds
							, CGICom cgiCom
							, JFrame frame
							, WaitingDataStructure ds){
		
		super(mds
				, cgiCom
				, null
				, "Waiting Point Finder"
				, ""
				, new Dimension(650, 500)
				, 3
				, false);
		
		this.ds = ds;
		
		setNavActionListeners(this);
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				WaitingFrame.this.setVisible(false);
				WaitingFrame.this.dispose();
			}
		});
		introPanel = new WaitingIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		
	}
	
	/* (non-Javadoc)
	 * @see org.nucastrodata.wizard.WizardFrame#closeAllFrames()
	 */
	public void closeAllFrames(){
		
	}
	
	/**
	 * Gets the data structure.
	 *
	 * @return the data structure
	 */
	public WaitingDataStructure getDataStructure(){return ds;}
	
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
					simPanel = new WaitingSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(introPanel, simPanel, 1, 3, "Select Simulation Parameters", CENTER);
					break;
					
				case 1:
					if(simPanel.goodData()){
						if(simPanel.goodTimesteps()){
							simPanel.getCurrentState();
							paramPanel = new WaitingParamPanel(ds, Cina.cinaMainDataStructure, this, Cina.cgiCom);
							paramPanel.setCurrentState();
							setContentPanel(simPanel, paramPanel, 2, 3, "Select Waiting Point Criteria", CENTER);
						}else{
							String string = "Please enter a time or timestep interval with less than 1000 timesteps.";
							GeneralDialog dialog = new GeneralDialog(this, string, "Attention!"); 
							dialog.setVisible(true);
						}
					}else{
						String string = "All fields must contain values and be integer values with the except of Time (sec) min and max.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!"); 
						dialog.setVisible(true);
					}
					break;
					
				case 2:
					if(paramPanel.goodData()){
						paramPanel.getCurrentState();
						
						//dialog.open();
						//JAVA 6 CODE
						//(task = new WaitingCGICallTask(ds, mds, cgiCom, this, dialog)).execute();
						
						//START JAVA 5 CODE
						openDelayDialog(this);
						final SwingWorker worker = new SwingWorker(){
							
							boolean goodCall;
							
							public Object construct(){
								
								goodCall = cgiCom.doCGICall(mds, ds, CGICom.GET_WAITING_POINTS, WaitingFrame.this);
								
								return new Object();
								
							}
							
							public void finished(){
								if(goodCall){
									WaitingFrame.this.gotoOutputPanel();
								}
								//dialog.close();
								closeDelayDialog();
							}
							
						};
						
						worker.start();
						//END JAVA 5 CODE
						
					}else{
						String string = "All fields must contain values and be numeric values.";
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
					simPanel = new WaitingSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(paramPanel, simPanel, 1, 3, "Select Simulation Parameters", CENTER);
					break;
			
				case 3:
					addFullButtons();
					paramPanel = new WaitingParamPanel(ds, Cina.cinaMainDataStructure, this, Cina.cgiCom);
					paramPanel.setCurrentState();
					setContentPanel(outputPanel, paramPanel, 2, 3, "Select Waiting Point Criteria", CENTER);
					break;
			
			}
			
		}
		
		validate();
		repaint();
		
	}
	
	/**
	 * Goto output panel.
	 */
	public void gotoOutputPanel(){
		//dialog.close();
		addEndButtons();
		outputPanel = new WaitingOutputPanel(ds, this);
		outputPanel.setCurrentState(false);
		setContentPanel(paramPanel, outputPanel, 3, 3, "Results", CENTER);
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

		String delayString = "Please wait while waiting points are found.";
		
		delayTextArea.setText(delayString);
		
		delayTextArea.setCaretPosition(0);
		
		GridBagConstraints gbc = new GridBagConstraints(); 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		
		delayDialog.getContentPane().add(sp, gbc);
		
		//Cina.setFrameColors(delayDialog);	
		
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



/*class WaitingCGICallTask extends SwingWorker<Void, Void>{
	
	private boolean goodCall;
	private WaitingDataStructure ds;
	private CinaMainDataStructure mds;
	private CGICom cgiCom;
	private WaitingFrame parent;
	private PleaseWaitDialog dialog;
	
	public WaitingCGICallTask(WaitingDataStructure ds
							, CinaMainDataStructure mds
							, CGICom cgiCom
							, WaitingFrame parent
							, PleaseWaitDialog dialog){
		this.ds = ds;
		this.mds = mds;
		this.cgiCom = cgiCom;
		this.parent = parent;
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		goodCall = cgiCom.doCGICall(mds, ds, CGICom.GET_WAITING_POINTS, parent);
		return null;
	}
	
	protected void done(){
		dialog.close();
		if(goodCall){
			parent.gotoOutputPanel();
		}
	}
	
}*/
