package org.nucastrodata.element.elementviz.animator.flux;

import javax.swing.*;

import org.nucastrodata.SwingWorker;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.feature.FluxDataStructure;
import org.nucastrodata.io.CGICom;
import java.awt.*;
import java.awt.event.*;
import org.nucastrodata.wizard.WizardFrame;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.GeneralDialog;

public class FluxFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private FluxDataStructure ds;
	
	/** The intro panel. */
	private FluxIntroPanel introPanel;
	
	/** The sim panel. */
	private FluxSimPanel simPanel;
	
	/** The param panel. */
	private FluxParamPanel paramPanel;
	
	/** The output panel. */
	private FluxOutputPanel outputPanel;
	
	/** The close dialog. */
	private CautionDialog closeDialog;
	
	private  JDialog delayDialog;
	
	/**
	 * Instantiates a new bottle frame.
	 *
	 * @param mds the mds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 * @param ds the ds
	 */
	public FluxFrame(MainDataStructure mds
							, CGICom cgiCom
							, JFrame frame
							, FluxDataStructure ds){
		
		super(mds
				, cgiCom
				, null
				, "Table of Reaction Flux Values"
				, ""
				, new Dimension(650, 500)
				, 3
				, false);
		
		this.ds = ds;
		
		setNavActionListeners(this);
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				FluxFrame.this.setVisible(false);
				FluxFrame.this.dispose();
			}
		});
		introPanel = new FluxIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		
	}
	
	public FluxDataStructure getDataStructure(){return ds;}
	
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
					simPanel = new FluxSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(introPanel, simPanel, 1, 3, "Select Timestep Window", CENTER);
					break;
					
				case 1:
					if(simPanel.goodData()){
						simPanel.getCurrentState();
						paramPanel = new FluxParamPanel(ds);
						paramPanel.setCurrentState();
						setContentPanel(simPanel, paramPanel, 2, 3, "Enter Reactions", FULL);
					}else{
						String string = "All fields must contain integer values.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!"); 
						dialog.setVisible(true);
					}
					break;
				case 2:
					if(paramPanel.goodData()){
						paramPanel.getCurrentState();
						outputPanel = new FluxOutputPanel(ds, this);
							openDelayDialog(this);
							final SwingWorker worker = new SwingWorker(){
								boolean goodCall;
								public Object construct(){
									goodCall = outputPanel.goodReactions();
									if(!goodCall){
										return new Object();
									}
									outputPanel.setCurrentState();
									return new Object();
								}
								
								public void finished(){
									closeDelayDialog();
									if(goodCall){
										FluxFrame.this.gotoOutputPanel();
									}else{
										String string = "Please check that the reaction list you entered is in the correct format.";
										GeneralDialog dialog = new GeneralDialog(FluxFrame.this, string, "Attention!"); 
										dialog.setVisible(true);
									}
									
								}
								
							};
							
							worker.start();
							
					}else{
						String string = "Please enter at least one reaction in the text area.";
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
					simPanel = new FluxSimPanel(ds);
					simPanel.setCurrentState();
					setContentPanel(paramPanel, simPanel, 1, 3, "Select Timestep Window", CENTER);
					break;
			
				case 3:
					addFullButtons();
					paramPanel = new FluxParamPanel(ds);
					paramPanel.setCurrentState();
					setContentPanel(outputPanel, paramPanel, 2, 3, "Enter Reactions", FULL);
					break;
			
			}
			
		}
		
		validate();
		repaint();
		
	}
	
	public void gotoOutputPanel(){
		addEndButtons();
		setContentPanel(paramPanel, outputPanel, 3, 3, "Reaction Flux Values", FULL);
		validate();
		repaint();
	}
	
	public void closeAllFrames(){

	}
	
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

		String delayString = "Please wait while the table of reaction flux values is created.";
		
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

	public void closeDelayDialog(){
		delayDialog.setVisible(false);
		delayDialog.dispose();
		delayDialog=null;
	}
	
}