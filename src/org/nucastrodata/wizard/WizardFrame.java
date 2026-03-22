package org.nucastrodata.wizard;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.nucastrodata.Cina;
import org.nucastrodata.Fonts;
import org.nucastrodata.datastructure.DataStructure;
import org.nucastrodata.datastructure.MainDataStructure;
import org.nucastrodata.datastructure.util.ElementSimWorkRunDataStructure;
import org.nucastrodata.io.CGICom;
import org.nucastrodata.dialogs.AttentionDialog;
import org.nucastrodata.dialogs.CautionDialog;
import org.nucastrodata.dialogs.FeatureClosingDialog;
import org.nucastrodata.element.elementsynth.ElementSynthFrame;
import org.nucastrodata.element.elementsynth.ElementSynthSimWorkflowRunStatus;
import org.nucastrodata.wizard.CloseWizardActionListener;
import org.nucastrodata.wizard.StepPanel;
import org.nucastrodata.wizard.TitlePanel;
import org.nucastrodata.wizard.WizardFrame;
import info.clearthought.layout.*;

public abstract class WizardFrame extends JFrame{

	protected MainDataStructure mds;
	protected CGICom cgiCom;
	protected Cina frame;
	protected DataStructure ds;
	protected JPanel introPanel;
	protected FeatureClosingDialog closingDialog;
	protected CautionDialog continueOnDialog;
	protected Container c;
	protected JPanel buttonPanel;
	protected JButton backButton, endButton, continueOnButton, continueButton, homeButton;
	protected int panelIndex;
	protected String panelString;
	protected TitlePanel titlePanel;
	protected StepPanel stepPanel;
	private int numberOfSteps;
	private JPanel currentPanel;
	private Dimension size;
	protected static final String TITLE = "1, 1, l, t";
	protected static final String STEP = "3, 1, r, t";
	protected static final String CENTER = "1, 3, 3, 3, c, c";
	protected static final String FULL = "1, 3, 3, 3, f, f";
	protected static final String FULL_WIDTH = "1, 3, 3, 3, f, c";
	protected static final String BUTTON = "1, 5, 3, 5, c, b";
	public static final int SAVE_AND_CLOSE = 0;
	public static final int CLOSE = 1;
	public static final int DO_NOT_CLOSE = 2;
	
	public WizardFrame(MainDataStructure mds
			, CGICom cgiCom
			, Cina frame
			, final String title
			, String continueOnTitle
			, Dimension size
			, int numberOfSteps
			, boolean addCloseListener){

		this.mds = mds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		this.size = size;
		this.numberOfSteps = numberOfSteps;

		setTitle(title);
		setSize(size);

		titlePanel = new TitlePanel(title, panelString);
		stepPanel = new StepPanel(panelIndex, numberOfSteps);

		CloseWizardActionListener al = new CloseWizardActionListener(WizardFrame.this);
		closingDialog = new FeatureClosingDialog(WizardFrame.this, al, title);
		al.setClosingDialog(closingDialog);

		if(addCloseListener){
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we){   
					if(we.getSource()==WizardFrame.this){  
						if(WizardFrame.this instanceof ElementSynthFrame){
							ElementSimWorkRunDataStructure eswrds = Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure();
					    	if (eswrds != null){
					    		if(eswrds.getCurrentStatus()==ElementSynthSimWorkflowRunStatus.RUNNING){
					    			if(eswrds.getName().equals("")){
					    				String string = "";
					    				if(Cina.cinaMainDataStructure.isMasterUser()){
					    					string = "You must abort this element synthesis simulation or make it available offline before closing the Element Synthesis Simulator.";
					    				}else{
					    					string = "You must abort this element synthesis simulation before closing the Element Synthesis Simulator.";
					    				}
					        			AttentionDialog.createDialog(WizardFrame.this, string);
					        			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					        			return;
					    			}else{
					    				Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
					    			}
					    		}
					    	}
						
						}
						closingDialog.setLocationRelativeTo(WizardFrame.this);
						closingDialog.setVisible(true);
					}
				}
			});
		}

		double border = 5;
		double gap = 5;
		double[] col = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, border};
		double[] row = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, border};

		c = getContentPane();
		c.setLayout(new TableLayout(col, row));

		backButton = new JButton("< Back");
		backButton.setFont(Fonts.buttonFont);

		continueButton = new JButton("Continue >");
		continueButton.setFont(Fonts.buttonFont);

		endButton = new JButton("Close " + title);
		endButton.setFont(Fonts.buttonFont);
		if(addCloseListener){
			endButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(WizardFrame.this instanceof ElementSynthFrame){
						ElementSimWorkRunDataStructure eswrds = Cina.elementSynthDataStructure.getCurrentSimWorkRunDataStructure();
				    	if (eswrds != null){
				    		if(eswrds.getCurrentStatus()==ElementSynthSimWorkflowRunStatus.RUNNING){
				    			if(eswrds.getName().equals("")){
				    				String string = "";
				    				if(Cina.cinaMainDataStructure.isMasterUser()){
				    					string = "You must abort this element synthesis simulation or make it available offline before closing the Element Synthesis Simulator.";
				    				}else{
				    					string = "You must abort this element synthesis simulation before closing the Element Synthesis Simulator.";
				    				}
				        			AttentionDialog.createDialog(WizardFrame.this, string);
				        			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				        			return;
				    			}else{
				    				Cina.elementSynthFrame.elementSynthStatusPanel.stopElementSynthesisGetSimWorkflowRunStatusController();
				    			}
				    		}
				    	}
					
					}
					closingDialog.setLocationRelativeTo(WizardFrame.this);
					closingDialog.setVisible(true);
				}
			});
		}

		continueOnButton = new JButton(continueOnTitle);
		continueOnButton.setFont(Fonts.buttonFont);

		homeButton = new JButton(title + " Home");
		homeButton.setFont(Fonts.buttonFont);

		panelIndex = 0;
		buttonPanel = new JPanel();

		c.add(titlePanel, TITLE);
		c.add(stepPanel, STEP);
		c.add(buttonPanel, BUTTON);

		addIntroButtons();

	}
	
	public WizardFrame(MainDataStructure mds
						, CGICom cgiCom
						, Cina frame
						, final String title
						, String continueOnTitle
						, Dimension size
						, int numberOfSteps){
		
		this(mds, cgiCom, frame, title, continueOnTitle, size, numberOfSteps, true);
	}
	
	public void setDataStructure(DataStructure ds){this.ds = ds;}
	protected void setIntroPanel(JPanel introPanel){this.introPanel = introPanel;}
	
	protected void setContentPanel(JPanel currentPanel, JPanel newPanel, int panelIndex, int numberOfSteps, String panelString, String constraints){
		
		this.panelIndex = panelIndex;
		this.numberOfSteps = numberOfSteps;
		this.currentPanel = currentPanel;
		stepPanel.setNumberOfSteps(numberOfSteps);
		stepPanel.setCurrentStep(panelIndex);
		titlePanel.setPanelString(panelString);
		stepPanel.setVisible(panelIndex!=0);
		titlePanel.setVisible(panelIndex!=0);
		if(currentPanel!=null){
			c.remove(currentPanel);
		}
		if(newPanel!=null){
			c.add(newPanel, constraints);
		}
		this.currentPanel = newPanel;
		c.repaint();
		
		validate();
	}
	
	protected void setContentPanel(JPanel oldPanel, JPanel newPanel, int panelIndex, String panelString, String constraints){
		setContentPanel(oldPanel, newPanel, panelIndex, numberOfSteps, panelString, constraints);
	}
	
	protected void setContentPanel(JPanel newPanel, int panelIndex, String panelString, String constraints){
		setContentPanel(currentPanel, newPanel, panelIndex, numberOfSteps, panelString, constraints);
	}
	
	protected void addIntroButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(continueButton);
	}
	
	protected void addFullButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(backButton);
		buttonPanel.add(continueButton);
	}
	
	protected void addEndButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(backButton);
		buttonPanel.add(endButton);
		buttonPanel.add(homeButton);
		if(!continueOnButton.getText().equals("")){
			buttonPanel.add(continueOnButton);
		}
		
	}
	
	protected void setNavActionListeners(ActionListener al){
		backButton.addActionListener(al);
		continueButton.addActionListener(al);
		endButton.addActionListener(al);
		continueOnButton.addActionListener(al);
		homeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				c.removeAll();
				c.add(titlePanel, TITLE);
				c.add(stepPanel, STEP);
				c.add(buttonPanel, BUTTON);
				addIntroButtons();
				setContentPanel(introPanel, 0, "", CENTER);
				setSize(size);
				validate();
			}
		});
	}
	
	public void closeWizard(int type){
		
		closingDialog.setVisible(false);
		closingDialog.dispose();
		
		if(type==DO_NOT_CLOSE){
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}else{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			closeAllFrames();
	 		setVisible(false);
	 		setSize(size);
			c.removeAll();
			c.add(titlePanel, TITLE);
			c.add(stepPanel, STEP);
			c.add(buttonPanel, BUTTON);
			addIntroButtons();
			setContentPanel(introPanel, 0, "", CENTER);
			if(type==CLOSE){
				ds.initialize();
			}
			dispose();
		}

	}

	public abstract void closeAllFrames();
	
}

class CloseWizardActionListener implements ActionListener{
	
	private FeatureClosingDialog closingDialog;
	private WizardFrame frame;
	
	public CloseWizardActionListener(WizardFrame frame){
		this.frame = frame;
	}
	
	public void setClosingDialog(FeatureClosingDialog closingDialog){
		this.closingDialog = closingDialog;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==closingDialog.getSubmitButton()){
			if(closingDialog.getSaveAndCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.SAVE_AND_CLOSE);
			}else if(closingDialog.getCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.CLOSE);
			}else if(closingDialog.getDoNotCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.DO_NOT_CLOSE);
			}
		}
	}
}

class TitlePanel extends JPanel{

	private JLabel label;
	private String featureString;
	
	public TitlePanel(String featureString, String panelString){
		this.featureString = featureString;
		setLayout(new FlowLayout());
		label = new JLabel(featureString + " | " + panelString);
		add(label);
	}
	
	public void setPanelString(String panelString){
		label.setText(featureString + " | " + panelString);
	}
	
}

class StepPanel extends JPanel{
	
	private JLabel label;
	private int numberOfSteps, currentStep;
	
	public StepPanel(int currentStep, int numberOfSteps){
		this.numberOfSteps = numberOfSteps;
		setLayout(new FlowLayout());
		label = new JLabel("Step " + String.valueOf(currentStep) + " of " + String.valueOf(numberOfSteps));
		add(label);
	}
	
	public void setCurrentStep(int currentStep){
		this.currentStep = currentStep;
		label.setText("Step " + currentStep + " of " + numberOfSteps);
	}
	
	public void setNumberOfSteps(int numberOfSteps){
		this.numberOfSteps = numberOfSteps;
		label.setText("Step " + currentStep + " of " + numberOfSteps);
	}
	
}

